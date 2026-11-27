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
  val prependWhitePage: Boolean = false,
  val initialChapterIds: Set<String> = emptySet(),
)

data class ManualRequest(
  val provider: String,
  val seriesId: String,
  val title: String,
  val destination: DownloadDestination,
  val chapterIds: Set<String>,
  val filters: ChapterFilters = ChapterFilters(),
  val kccProfileId: String? = null,
  val prependWhitePage: Boolean = false,
)

internal object PublicationReleaseEstimate {
  fun expected(
    chapters: List<SourceChapter>,
    now: Instant,
  ): Instant? {
    val dates =
      chapters
        .filter { it.publishedAt != null && !it.publishedAt.isAfter(now) }
        .groupBy { it.number.ifBlank { it.id } }
        .values
        .mapNotNull { editions -> editions.mapNotNull { it.publishedAt }.minOrNull() }
        .distinct()
        .sortedDescending()
    val latest = dates.firstOrNull() ?: return null
    val previous = dates.drop(1).firstOrNull { Duration.between(it, latest) >= Duration.ofHours(1) } ?: return null
    return latest.plus(Duration.between(previous, latest))
  }
}

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
    require(request.initialChapterIds.size <= 100) { "Select up to 100 initial chapters" }
    val source = provider(request.provider)
    val available = if (existing == null || request.initialChapterIds.isNotEmpty()) source.chapters(request.seriesId, request.filters) else emptyList()
    val availableIds = available.map { it.id }.toSet()
    require(availableIds.containsAll(request.initialChapterIds)) { "One or more selected chapters are unavailable under the current filters" }
    val seen = existing?.seen ?: availableIds
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
        prependWhitePage = request.prependWhitePage,
        pendingInitialChapterIds = request.initialChapterIds,
      )
    store.update { current ->
      val previous = current.tracked.find { it.provider == request.provider && it.seriesId == request.seriesId }
      val updated =
        previous?.let {
          tracked.copy(id = it.id, seen = it.seen, pendingInitialChapterIds = it.pendingInitialChapterIds + request.initialChapterIds)
        } ?: tracked
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

  @Scheduled(fixedDelay = 15_000, initialDelay = 60_000)
  fun tick() {
    val due = Instant.now()
    if (state().tracked.none { it.enabled && (it.nextCheck == null || !it.nextCheck.isAfter(due)) }) return
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
            save(source, request.seriesId, request.title, request.destination, chapter, "manual", request.kccProfileId, request.prependWhitePage)
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
    }
    store.update { current ->
      require(current.tracked.none { it.kccProfileId != null && it.kccProfileId != "off" && it.kccProfileId !in ids }) { "A tracked manga still uses this profile" }
      current.copy(kcc = settings.copy(profiles = settings.profiles.map { it.copy(keepOriginal = false, outputFolder = "") }))
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
          ?.let { tracked.destination.libraryId to tracked.destination.folder }
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
      val fresh = chapters.filter { it.id !in tracked.seen || it.id in tracked.pendingInitialChapterIds }.sortedBy { it.publishedAt }
      val completed = mutableSetOf<String>()
      val unavailableInitial = tracked.pendingInitialChapterIds - chapters.map { it.id }.toSet()
      var failure: String? = if (unavailableInitial.isEmpty()) null else "${unavailableInitial.size} selected initial chapters are no longer available"
      fresh.forEach { chapter ->
        val current = state().tracked.find { it.id == id } ?: return@forEach
        if (scheduled && !current.enabled) return@forEach
        runCatching { save(source, current.seriesId, current.title, current.destination, chapter, "auto", current.kccProfileId, current.prependWhitePage) }
          .onSuccess { record ->
            completed += chapter.id
            if (record.conversion?.status == "Failed" || record.scan.startsWith("Scan enqueue failed")) {
              failure = record.conversion?.error ?: record.scan
            }
          }.onFailure { failure = it.message ?: "Chapter download failed" }
      }
      val known = runCatching { source.nextRelease(tracked.seriesId) }.getOrNull()
      val estimate =
        if (tracked.provider == "mangaplus") {
          null
        } else {
          val downloaded =
            state()
              .history
              .filter {
                it.provider == tracked.provider && it.seriesId == tracked.seriesId && it.destination == tracked.destination
              }.map { it.chapter }
              .filter { chapter ->
                val groups = chapter.groups.map { it.lowercase() }.toSet()
                (tracked.filters.language.isBlank() || chapter.language.equals(tracked.filters.language, ignoreCase = true)) &&
                  (tracked.filters.includeGroups.isEmpty() || tracked.filters.includeGroups.any { it.lowercase() in groups }) &&
                  tracked.filters.excludeGroups.none { it.lowercase() in groups }
              }
          PublicationReleaseEstimate.expected(chapters + downloaded, Instant.now())
        }
      val previousSchedule = tracked.expectedRelease.takeIf { tracked.releaseBasis == "Source schedule" }
      val sourceSchedule = if (tracked.provider == "mangaplus") SourceReleasePolling.expected(Instant.now(), known, previousSchedule) else known
      val expected = sourceSchedule ?: estimate
      val nextPoll = now.plus(Duration.ofMinutes(30))
      val next =
        if (tracked.provider == "mangaplus")
          SourceReleasePolling.next(Instant.now(), sourceSchedule)
        else
          known?.takeIf { it.isAfter(now) && it.isBefore(nextPoll) } ?: nextPoll
      updateTrack(id) {
        it.copy(
          seen = it.seen + completed,
          pendingInitialChapterIds = it.pendingInitialChapterIds - completed,
          lastCheck = now,
          nextCheck = next.takeIf { _ -> it.enabled },
          expectedRelease = expected,
          releaseBasis =
            when {
              sourceSchedule != null -> "Source schedule"
              estimate == null -> null
              else -> "Estimated from publication dates"
            },
          result = if (fresh.isEmpty()) "No new chapters" else completed.size.toString() + " chapters processed",
          error = failure,
        )
      }
    } catch (e: Exception) {
      updateTrack(id) { it.copy(lastCheck = now, nextCheck = (if (tracked.provider == "mangaplus") SourceReleasePolling.next(Instant.now(), tracked.expectedRelease.takeIf { tracked.releaseBasis == "Source schedule" }) else now.plus(Duration.ofMinutes(30))).takeIf { _ -> it.enabled }, error = e.message ?: "Source check failed") }
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
    prependWhitePage: Boolean = false,
  ): DownloadRecord {
    val current = state()
    current.history
      .find {
        it.provider == source.id && it.chapter.id == chapter.id && it.destination == destination
      }?.let { return it }
    val settings = current.kcc
    val selected = profileId ?: if (mode == "auto") settings.autoProfileId else settings.manualProfileId
    val profile = settings.profiles.find { it.id == selected }
    val file = files.archive(destination, title, chapter, source.pages(chapter.id), forConversion = profile != null, prependWhitePage = prependWhitePage)
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
        prependWhitePage = prependWhitePage,
      )
    try {
      store.update { it.copy(history = listOf(record) + it.history) }
    } catch (e: Exception) {
      if (profile != null) runCatching { files.removeOriginal(record) }
      throw e
    }
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
    runCatching { files.removeOriginal(record) }.onFailure { error ->
      updateRecord(record.id) {
        it.copy(conversion = Conversion(profile, "Converted", outputs.first(), "Temporary source cleanup failed: ${error.message}", outputs))
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
