package org.gotson.komga.downloads

import jakarta.annotation.PreDestroy
import org.gotson.komga.application.tasks.TaskEmitter
import org.gotson.komga.downloads.kcc.KccOptions
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.time.Duration
import java.time.Instant
import java.util.UUID
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

data class TrackRequest(
  val provider: String,
  val seriesId: String,
  val title: String,
  val destination: DownloadDestination,
  val filters: ChapterFilters = ChapterFilters(),
  val enabled: Boolean = true,
  val kccProfileId: String? = null,
)

data class ManualRequest(
  val provider: String,
  val seriesId: String,
  val title: String,
  val destination: DownloadDestination,
  val chapterIds: Set<String>,
  val filters: ChapterFilters = ChapterFilters(),
  val kccProfileId: String? = null,
)

@Service
class DownloadService(
  providers: List<DownloadProvider>,
  private val store: DownloadStore,
  private val files: DownloadFiles,
  private val converter: DownloadKcc,
  private val tasks: TaskEmitter,
) {
  val providers =
    providers.associateBy { it.id }.also {
      require(it.size == providers.size) { "Duplicate download provider IDs" }
    }
  private val executor =
    ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, ArrayBlockingQueue(32)) { runnable ->
      Thread(runnable, "manga-downloads").apply { isDaemon = true }
    }
  private val checking = AtomicBoolean()
  private val checkingIds =
    java.util.concurrent.ConcurrentHashMap
      .newKeySet<String>()

  @Volatile
  final var workerError: String? = null
    private set

  fun provider(id: String) = requireNotNull(providers[id]) { "Unknown download provider" }

  fun state() = store.read()

  fun track(request: TrackRequest): TrackedManga {
    validate(request.destination, request.kccProfileId)
    val existing = state().tracked.find { it.provider == request.provider && it.seriesId == request.seriesId }
    val seen = existing?.seen ?: provider(request.provider).chapters(request.seriesId, request.filters).map { it.id }.toSet()
    val tracked =
      TrackedManga(
        existing?.id ?: UUID.randomUUID().toString(),
        request.provider,
        request.seriesId,
        request.title,
        request.destination,
        request.filters,
        request.enabled,
        request.kccProfileId,
        seen,
        existing?.lastCheck,
        if (request.enabled) Instant.now() else null,
        existing?.expectedRelease,
        existing?.releaseBasis,
        existing?.result,
        existing?.error,
      )
    store.update { current ->
      val previous = current.tracked.find { it.provider == request.provider && it.seriesId == request.seriesId }
      val updated = previous?.let { tracked.copy(id = it.id, seen = it.seen) } ?: tracked
      current.copy(tracked = current.tracked.filterNot { it.id == updated.id } + updated)
    }
    return state().tracked.first { it.provider == request.provider && it.seriesId == request.seriesId }
  }

  fun stop(id: String) {
    store.update { current -> current.copy(tracked = current.tracked.filterNot { it.id == id }) }
  }

  fun check(id: String) {
    require(state().tracked.any { it.id == id }) { "Unknown auto-fetch entry" }
    if (!checkingIds.add(id)) return
    try {
      submit {
        try {
          runCheck(id, false)
        } finally {
          checkingIds.remove(id)
        }
      }
    } catch (e: Exception) {
      checkingIds.remove(id)
      throw e
    }
  }

  @Scheduled(fixedDelay = 60_000, initialDelay = 60_000)
  fun tick() {
    if (!checking.compareAndSet(false, true)) return
    try {
      submit {
        try {
          val now = Instant.now()
          state().tracked.filter { it.enabled && (it.nextCheck == null || !it.nextCheck.isAfter(now)) }.forEach {
            if (checkingIds.add(it.id)) {
              try {
                runCheck(it.id, true)
              } finally {
                checkingIds.remove(it.id)
              }
            }
          }
        } finally {
          checking.set(false)
        }
      }
    } catch (e: Exception) {
      checking.set(false)
      workerError = e.message
    }
  }

  fun download(request: ManualRequest) {
    validate(request.destination, request.kccProfileId)
    require(request.chapterIds.size in 1..100) { "Select 1 to 100 chapters" }
    val source = provider(request.provider)
    submit {
      val chapters = source.chapters(request.seriesId, request.filters).filter { it.id in request.chapterIds }
      check(chapters.size == request.chapterIds.size) { "One or more selected chapters are unavailable" }
      val errors =
        chapters.mapNotNull { chapter ->
          runCatching {
            save(source, request.seriesId, request.title, request.destination, chapter, "manual", request.kccProfileId)
          }.exceptionOrNull()
        }
      if (errors.isNotEmpty()) error(errors.size.toString() + " manual downloads failed: " + errors.first().message)
    }
  }

  fun kcc(settings: KccSettings) {
    val ids = settings.profiles.map { it.id }.toSet()
    require(ids.size == settings.profiles.size && "off" !in ids && settings.profiles.size <= 100)
    require(listOf(settings.autoProfileId, settings.manualProfileId).all { it == "off" || it in ids })
    settings.profiles.forEach { profile ->
      require(profile.name.isNotBlank()) { "Enter a KCC profile name" }
      KccOptions.validate(profile)
      require(profile.outputFolder.isNotBlank()) { "Enter a library-relative KCC output folder" }
      val path =
        java.nio.file.Path
          .of(profile.outputFolder)
      require(path.root == null && path.none { it.toString() in setOf("..", ".") }) { "Enter a library-relative KCC output folder" }
    }
    store.update { current ->
      require(current.tracked.none { it.kccProfileId != null && it.kccProfileId != "off" && it.kccProfileId !in ids }) { "A tracked manga still uses this profile" }
      current.copy(kcc = settings)
    }
  }

  fun retry(id: String) {
    val record = requireNotNull(state().history.find { it.id == id }) { "Download not found" }
    require(record.conversion?.status in setOf("Failed", "Queued", "Converting")) { "Conversion is not retryable" }
    submit {
      val current = state().history.first { it.id == id }
      if (current.conversion?.status in setOf("Failed", "Queued", "Converting")) {
        val previous = checkNotNull(current.conversion).profile
        val profile = state().kcc.profiles.find { it.id == previous.id } ?: previous
        convert(current, profile)
      }
    }
  }

  fun health(): Map<String, String> {
    val snapshot = runCatching { state() }.getOrNull()
    val destinations =
      snapshot
        ?.tracked
        .orEmpty()
        .filter { it.enabled }
        .map { it.destination }
    val pathsAvailable =
      destinations.all {
        runCatching {
          val root = files.root(it.libraryId)
          val target = files.target(root, it.folder, false)
          Files.isWritable(generateSequence(target, java.nio.file.Path::getParent).first { path -> Files.exists(path) })
        }.getOrDefault(false)
      }
    val activeKccDestinations =
      snapshot?.tracked.orEmpty().filter { it.enabled }.mapNotNull { tracked ->
        val id = tracked.kccProfileId ?: snapshot?.kcc?.autoProfileId
        snapshot
          ?.kcc
          ?.profiles
          ?.find { it.id == id }
          ?.let { tracked.destination.libraryId to it.outputFolder }
      }
    val kccPathsAvailable =
      activeKccDestinations.all { (libraryId, folder) ->
        runCatching {
          val target = files.target(files.root(libraryId), folder, false)
          Files.isWritable(generateSequence(target, java.nio.file.Path::getParent).first { Files.exists(it) })
        }.getOrDefault(false)
      }
    return mapOf(
      "KCC output directories" to
        if (activeKccDestinations.isEmpty())
          "No auto-fetch conversion destinations"
        else if (kccPathsAvailable)
          "Writable"
        else
          "Unavailable",
      "Worker" to if (executor.isShutdown) "Offline" else "Online",
      "Tracking storage" to if (store.health()) "Available" else "Unavailable",
      "Scheduler" to if (checking.get()) "Checking" else "Idle",
      "Download directories" to
        if (destinations.isEmpty())
          "No destinations configured"
        else if (pathsAvailable)
          "Writable"
        else
          "Unavailable",
      "Komga scan" to "Uses Komga task queue",
      "KCC executable" to converter.status(),
      "KCC archive utility" to converter.archiveStatus(),
    )
  }

  @PreDestroy
  fun close() {
    executor.shutdownNow()
  }

  private fun validate(
    destination: DownloadDestination,
    profile: String?,
  ) {
    files.target(files.root(destination.libraryId), destination.folder, false)
    require(profile == null || profile == "off" || state().kcc.profiles.any { it.id == profile }) { "Unknown KCC profile" }
  }

  private fun submit(action: () -> Unit) =
    executor.execute {
      try {
        action()
        workerError = null
      } catch (e: Exception) {
        workerError = e.message ?: "Download worker failed"
      }
    }

  private fun runCheck(
    id: String,
    scheduled: Boolean,
  ) {
    val tracked = state().tracked.find { it.id == id } ?: return
    if (scheduled && !tracked.enabled) return
    val now = Instant.now()
    try {
      val source = provider(tracked.provider)
      val chapters = source.chapters(tracked.seriesId, tracked.filters)
      val fresh = chapters.filterNot { it.id in tracked.seen }.sortedBy { it.publishedAt }
      val completed = mutableSetOf<String>()
      var failure: String? = null
      fresh.forEach { chapter ->
        val current = state().tracked.find { it.id == id } ?: return@forEach
        if (scheduled && !current.enabled) return@forEach
        runCatching { save(source, current.seriesId, current.title, current.destination, chapter, "auto", current.kccProfileId) }
          .onSuccess { record ->
            completed += chapter.id
            if (record.conversion?.status == "Failed" || record.scan.startsWith("Scan enqueue failed")) {
              failure = record.conversion?.error ?: record.scan
            }
          }.onFailure { failure = it.message ?: "Chapter download failed" }
      }
      val known = runCatching { source.nextRelease(tracked.seriesId) }.getOrNull()
      val history =
        state()
          .history
          .filter { it.provider == tracked.provider && it.seriesId == tracked.seriesId }
          .distinctBy { it.chapter.number.ifBlank { it.chapter.id } }
          .sortedByDescending { it.downloadedAt }
          .take(2)
      val timestamps = history.mapNotNull { it.chapter.publishedAt }.sortedDescending()
      val estimateDates = if (timestamps.size == 2 && timestamps[0] != timestamps[1]) timestamps else history.map { it.downloadedAt }
      val interval = estimateDates.takeIf { it.size == 2 }?.let { Duration.between(it[1], it[0]) }
      val estimate = interval?.takeIf { it >= Duration.ofHours(1) }?.let { estimateDates.first().plus(it) }
      val expected = known ?: estimate
      val nextPoll = now.plus(Duration.ofMinutes(30))
      val next = known?.takeIf { it.isAfter(now) && it.isBefore(nextPoll) } ?: nextPoll
      updateTrack(id) {
        it.copy(
          seen = it.seen + completed,
          lastCheck = now,
          nextCheck = next.takeIf { _ -> it.enabled },
          expectedRelease = expected,
          releaseBasis =
            when {
              known != null -> "Source schedule"
              estimate == null -> null
              estimateDates === timestamps -> "Estimated from publication dates"
              else -> "Estimated from download times"
            },
          result = if (fresh.isEmpty()) "No new chapters" else completed.size.toString() + " chapters processed",
          error = failure,
        )
      }
    } catch (e: Exception) {
      updateTrack(id) { it.copy(lastCheck = now, nextCheck = now.plus(Duration.ofMinutes(30)).takeIf { _ -> it.enabled }, error = e.message ?: "Source check failed") }
    }
  }

  private fun updateTrack(
    id: String,
    mutate: (TrackedManga) -> TrackedManga,
  ) {
    store.update { current -> current.copy(tracked = current.tracked.map { if (it.id == id) mutate(it) else it }) }
  }

  private fun save(
    source: DownloadProvider,
    seriesId: String,
    title: String,
    destination: DownloadDestination,
    chapter: SourceChapter,
    mode: String,
    profileId: String? = null,
  ): DownloadRecord {
    val current = state()
    current.history
      .find {
        it.provider == source.id && it.chapter.id == chapter.id && it.destination == destination
      }?.let { return it }
    val settings = current.kcc
    val selected = profileId ?: if (mode == "auto") settings.autoProfileId else settings.manualProfileId
    val profile = settings.profiles.find { it.id == selected }
    val file = files.archive(destination, title, chapter, source.pages(chapter.id))
    val record =
      DownloadRecord(
        UUID.randomUUID().toString(),
        source.id,
        seriesId,
        title,
        chapter,
        destination,
        file,
        Instant.now(),
        mode,
        if (profile == null) "Pending scan" else "Awaiting conversion",
        profile?.let { Conversion(it, "Queued") },
      )
    store.update { it.copy(history = listOf(record) + it.history) }
    if (profile == null) scan(record) else convert(record, profile)
    return state().history.first { it.id == record.id }
  }

  private fun convert(
    record: DownloadRecord,
    profile: KccProfile,
  ) {
    updateRecord(record.id) { it.copy(conversion = Conversion(profile, "Converting")) }
    val outputs =
      try {
        converter.convert(record, profile)
      } catch (e: Exception) {
        updateRecord(record.id) { it.copy(scan = "Conversion failed", conversion = Conversion(profile, "Failed", error = e.message ?: "KCC failed")) }
        return
      }
    updateRecord(record.id) { it.copy(conversion = Conversion(profile, "Converted", outputs.first(), files = outputs)) }
    if (!profile.keepOriginal) {
      runCatching { files.removeOriginal(record) }.onFailure { error ->
        updateRecord(record.id) {
          it.copy(conversion = Conversion(profile, "Converted", outputs.first(), "Original retained: ${error.message}", outputs))
        }
      }
    }
    scan(record)
  }

  private fun scan(record: DownloadRecord) {
    val status =
      runCatching { tasks.scanLibrary(record.destination.libraryId) }
        .fold({ "Scan queued" }, { "Scan enqueue failed: " + it.message })
    updateRecord(record.id) { it.copy(scan = status) }
  }

  private fun updateRecord(
    id: String,
    mutate: (DownloadRecord) -> DownloadRecord,
  ) {
    store.update { current -> current.copy(history = current.history.map { if (it.id == id) mutate(it) else it }) }
  }
}
