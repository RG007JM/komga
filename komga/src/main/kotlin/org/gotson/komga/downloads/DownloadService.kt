package org.gotson.komga.downloads

import jakarta.annotation.PreDestroy
import org.gotson.komga.application.tasks.TaskEmitter
import org.gotson.komga.downloads.kcc.KccOptions
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
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
  private var activeBatch: String? = null

  @Volatile
  private var providerProbe: Map<String, String> = emptyMap()

  fun queueStatus(): Map<String, Any?> = mapOf("active" to activeBatch, "queued" to executor.queue.size, "busy" to (executor.activeCount > 0))

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
    val version = source.catalogueVersion
    val reseed = existing == null || existing.filters != request.filters || existing.catalogueVersion != version || existing.publicationFloor == null || existing.destination != request.destination
    val available = if (reseed || request.initialChapterIds.isNotEmpty()) source.chapters(request.seriesId, request.filters) else emptyList()
    val availableIds = available.map { it.id }.toSet()
    require(availableIds.containsAll(request.initialChapterIds)) { "One or more selected chapters are unavailable under the current filters" }
    val now = Instant.now()
    val proposed =
      (existing ?: TrackedManga(UUID.randomUUID().toString(), request.provider, request.seriesId, request.title, request.destination)).copy(
        title = request.title,
        destination = request.destination,
        filters = request.filters,
        enabled = request.enabled,
        kccProfileId = request.kccProfileId,
        nextCheck = now.takeIf { request.enabled },
        prependWhitePage = request.prependWhitePage,
        revision = UUID.randomUUID().toString(),
      )
    val seeded = if (reseed) AutoFetchPolicy.baseline(proposed, available, version, now) else proposed
    val saved =
      store.update { current ->
        val previous = current.tracked.find { it.provider == request.provider && it.seriesId == request.seriesId }
        require(previous?.revision == existing?.revision) { "Auto-fetch settings changed; reload and try again" }
        // A concurrent check can complete chapters while the edit is fetching metadata.
        val previousPending = previous?.pendingInitialChapterIds.orEmpty()
        val retained = if (reseed) previousPending.intersect(availableIds) else previousPending
        val pending = retained + request.initialChapterIds
        require(pending.size <= 100) { "At most 100 initial chapters may be pending" }
        val updated =
          seeded.copy(
            seen = seeded.seen + previous?.seen.orEmpty(),
            catalogueVersion = if (reseed) seeded.catalogueVersion else previous?.catalogueVersion,
            publicationFloor = if (reseed) seeded.publicationFloor else previous?.publicationFloor,
            chapterNumberFloor = if (reseed) seeded.chapterNumberFloor else previous?.chapterNumberFloor,
            pendingAutoChapterIds = if (reseed) emptySet() else previous?.pendingAutoChapterIds.orEmpty(),
            failedChapterAttempts = if (reseed) emptyMap() else previous?.failedChapterAttempts.orEmpty(),
            pendingInitialChapterIds = pending,
            lastCheck = previous?.lastCheck,
            expectedRelease = previous?.expectedRelease.takeUnless { reseed },
            releaseBasis = previous?.releaseBasis.takeUnless { reseed },
            result = if (reseed) "Catalogue baseline recorded; ${previousPending.size - retained.size} previous selections no longer match" else previous?.result,
            error = if (reseed) null else previous?.error,
          )
        current.copy(tracked = current.tracked.filterNot { it.id == updated.id } + updated)
      }
    return saved.tracked.first { it.provider == request.provider && it.seriesId == request.seriesId }
  }

  fun setEnabled(
    id: String,
    enabled: Boolean,
  ) {
    store.update { current ->
      require(current.tracked.any { it.id == id }) { "Unknown auto-fetch entry" }
      current.copy(
        tracked =
          current.tracked.map {
            if (it.id == id) it.copy(enabled = enabled, nextCheck = Instant.now().takeIf { enabled }, revision = UUID.randomUUID().toString()) else it
          },
      )
    }
  }

  fun stop(id: String) {
    store.update { current -> current.copy(tracked = current.tracked.filterNot { it.id == id }) }
  }

  fun check(id: String) {
    require(state().tracked.any { it.id == id && it.enabled }) { "Auto-fetch entry is paused or no longer exists" }
    if (!checkingIds.add(id)) return
    try {
      submit {
        try {
          runCheck(id)
        } finally {
          checkingIds.remove(id)
        }
      }
    } catch (e: Exception) {
      checkingIds.remove(id)
      throw e
    }
  }

  @EventListener(ApplicationReadyEvent::class)
  fun recoverInterruptedWork() {
    submit {
      val interrupted = state().history.filter { it.conversion?.status in setOf("Queued", "Converting") }
      if (interrupted.isNotEmpty()) {
        val ids = interrupted.map { it.id }.toSet()
        store.update { current ->
          current.copy(
            history =
              current.history.map { record ->
                if (record.id !in ids)
                  record
                else
                  record.copy(
                    scan = "Conversion interrupted",
                    conversion = checkNotNull(record.conversion).copy(status = "Failed", error = "Conversion interrupted by restart; inspect output and retry the retained source if available"),
                  )
              },
          )
        }
      }
      state()
        .history
        .filter { record ->
          (record.conversion == null || record.conversion.status == "Converted") &&
            (record.scan == "Pending scan" || record.scan == "Awaiting conversion" || record.scan.startsWith("Scan enqueue failed")) &&
            files.presence(record) == DownloadPresence.PRESENT
        }.forEach { if (!executor.isShutdown) scan(it) }
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
            if (executor.isShutdown || Thread.currentThread().isInterrupted) return@submit
            if (checkingIds.add(it.id)) {
              try {
                runCheck(it.id)
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
      val errors = mutableListOf<Throwable>()
      try {
        for ((index, chapter) in chapters.withIndex()) {
          if (executor.isShutdown || Thread.currentThread().isInterrupted) break
          activeBatch = "Manual download: ${index + 1}/${chapters.size}"
          runCatching {
            val record = save(source, request.seriesId, request.title, request.destination, chapter, "manual", request.kccProfileId, request.prependWhitePage)
            check(record.conversion?.status != "Failed") { record.conversion?.error ?: "Conversion failed" }
            check(!record.scan.startsWith("Scan enqueue failed")) { record.scan }
          }.exceptionOrNull()?.let { errors.add(it) }
        }
        if (errors.isNotEmpty()) error(errors.size.toString() + " manual downloads failed: " + errors.first().message)
      } finally {
        activeBatch = null
      }
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
    require(record.conversion?.status == "Failed") { "Conversion is not retryable" }
    require(files.presence(record) == DownloadPresence.STAGED) { "Original staged CBZ is unavailable; select the chapter manually to download again" }
    submit {
      val current = state().history.firstOrNull { it.id == id } ?: return@submit
      if (current.conversion?.status == "Failed" && files.presence(current) == DownloadPresence.STAGED) {
        val previous = checkNotNull(current.conversion).profile
        val profile = state().kcc.profiles.find { it.id == previous.id } ?: previous
        convert(current, profile)
      }
    }
  }

  fun rescan(id: String) {
    val record = requireNotNull(state().history.find { it.id == id }) { "Download not found" }
    require(files.presence(record) == DownloadPresence.PRESENT) { "Downloaded output is not available" }
    require(record.conversion == null || record.conversion.status == "Converted") { "Conversion is not complete" }
    submit {
      val current = state().history.find { it.id == id }
      if (current != null && files.presence(current) == DownloadPresence.PRESENT) scan(current)
    }
  }

  /** Metadata only, explicitly requested by an administrator. Never opens a chapter viewer. */
  fun probeProviders() {
    submit {
      val representative = state().tracked.groupBy { it.provider }.mapValues { (_, entries) -> entries.firstOrNull { it.enabled } ?: entries.first() }
      providerProbe =
        (providers.keys + representative.keys).associate { id ->
          val source = providers[id]
          val tracked = representative[id]
          "Provider: $id" to
            when {
              executor.isShutdown -> "Not checked (worker stopping)"
              source == null -> "Unavailable (provider disabled)"
              tracked == null -> "Not checked (no tracked series)"
              else ->
                runCatching {
                  val chapters = source.chapters(tracked.seriesId, tracked.filters)
                  if (chapters.isEmpty()) "Unavailable (empty catalogue)" else "Metadata reachable (${chapters.size} chapters)"
                }.getOrDefault("Unavailable (metadata request failed)")
            }
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
      "Komga scan" to "See per-book import status",
      "AVIF decoder" to
        if (javax.imageio.ImageIO
            .getImageReadersByMIMEType("image/avif")
            .hasNext()
        )
          "Reader registered"
        else
          "Unavailable",
      "Provider checks" to if (providerProbe.isEmpty()) "Not probed; use Run healthcheck" else "Last explicit metadata probe",
      "Failed conversions" to "${snapshot?.history.orEmpty().count { it.conversion?.status == "Failed" }} recorded",
      "Queued jobs" to executor.queue.size.toString(),
      "KCC executable" to converter.status(),
      "KCC archive utility" to converter.archiveStatus(),
    ) + providerProbe
  }

  @PreDestroy
  fun close() {
    executor.shutdownNow()
    try {
      executor.awaitTermination(10, TimeUnit.SECONDS)
    } catch (_: InterruptedException) {
      Thread.currentThread().interrupt()
    }
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

  private fun runCheck(id: String) {
    val tracked = state().tracked.find { it.id == id && it.enabled } ?: return
    val now = Instant.now()
    try {
      val source = provider(tracked.provider)
      val chapters = source.chapters(tracked.seriesId, tracked.filters).distinctBy { it.id }
      check(chapters.isNotEmpty()) { "Source returned an empty catalogue; baseline retained" }
      if (tracked.catalogueVersion != source.catalogueVersion || tracked.publicationFloor == null) {
        val baseline = AutoFetchPolicy.baseline(tracked, chapters, source.catalogueVersion, now)
        updateTrack(id, tracked.revision) {
          baseline.copy(
            lastCheck = now,
            nextCheck = now,
            result = "Catalogue changed; historical chapters recorded without downloading",
            error = null,
          )
        }
        return
      }
      val historical = AutoFetchPolicy.historical(tracked, chapters, now)
      val fresh =
        chapters.filter { AutoFetchPolicy.eligible(tracked, it, now) }.sortedWith(
          AutoFetchPolicy.batchOrder(tracked),
        )
      val numberFloor = listOfNotNull(tracked.chapterNumberFloor?.let(AutoFetchPolicy::number), AutoFetchPolicy.highestNumber(chapters, now)).maxOrNull()
      // Record eligible IDs before raising the watermark so interrupted and bounded batches remain retryable.
      updateTrack(id, tracked.revision) {
        it.copy(
          seen = it.seen + historical,
          chapterNumberFloor = numberFloor?.toPlainString(),
          pendingAutoChapterIds = it.pendingAutoChapterIds + fresh.filterNot { chapter -> chapter.id in tracked.pendingInitialChapterIds }.map { chapter -> chapter.id },
        )
      }
      val batch = fresh.take(AutoFetchPolicy.BATCH_LIMIT)
      val completed = mutableSetOf<String>()
      val unavailableInitial = tracked.pendingInitialChapterIds - chapters.map { it.id }.toSet()
      val unavailableAuto = tracked.pendingAutoChapterIds - chapters.map { it.id }.toSet()
      var failure: String? =
        when {
          unavailableInitial.isNotEmpty() -> "${unavailableInitial.size} selected initial chapters are no longer available"
          unavailableAuto.isNotEmpty() -> "${unavailableAuto.size} pending new chapters are no longer available"
          else -> null
        }
      for ((index, chapter) in batch.withIndex()) {
        if (executor.isShutdown || Thread.currentThread().isInterrupted) return
        val current = state().tracked.find { it.id == id && it.enabled && it.revision == tracked.revision } ?: return
        activeBatch = "Auto-fetch: ${index + 1}/${batch.size} (${fresh.size} eligible)"
        runCatching {
          save(source, current.seriesId, current.title, current.destination, chapter, "auto", current.kccProfileId, current.prependWhitePage, chapter.id in current.pendingInitialChapterIds)
        }.onSuccess { record ->
          completed += chapter.id
          // Persist each completion before starting another chapter, including retained conversion failures.
          updateTrack(id, tracked.revision) {
            it.copy(
              seen = it.seen + chapter.id,
              pendingInitialChapterIds = it.pendingInitialChapterIds - chapter.id,
              pendingAutoChapterIds = it.pendingAutoChapterIds - chapter.id,
              failedChapterAttempts = it.failedChapterAttempts - chapter.id,
            )
          }
          if (record.conversion?.status == "Failed" || record.scan.startsWith("Scan enqueue failed")) {
            failure = record.conversion?.error ?: record.scan
          }
        }.onFailure { error ->
          failure = error.message ?: "Chapter download failed"
          updateTrack(id, tracked.revision) {
            it.copy(failedChapterAttempts = AutoFetchPolicy.recordFailure(it, chapter.id))
          }
        }
      }
      if (state().tracked.none { it.id == id && it.enabled && it.revision == tracked.revision }) return
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
      updateTrack(id, tracked.revision) {
        it.copy(
          seen = it.seen + completed,
          pendingInitialChapterIds = it.pendingInitialChapterIds - completed,
          pendingAutoChapterIds = it.pendingAutoChapterIds - completed,
          lastCheck = now,
          nextCheck = (if (fresh.size > batch.size && failure == null) Instant.now().plusSeconds(60) else next).takeIf { _ -> it.enabled },
          expectedRelease = expected,
          releaseBasis =
            when {
              sourceSchedule != null -> "Source schedule"
              estimate == null -> null
              else -> "Estimated from publication dates"
            },
          result = if (fresh.isEmpty()) "No new chapters" else "${completed.size} chapters processed; ${fresh.size - completed.size} still eligible",
          error = failure,
        )
      }
    } catch (e: Exception) {
      updateTrack(id, tracked.revision) { it.copy(lastCheck = now, nextCheck = (if (tracked.provider == "mangaplus") SourceReleasePolling.next(Instant.now(), tracked.expectedRelease.takeIf { tracked.releaseBasis == "Source schedule" }) else now.plus(Duration.ofMinutes(30))).takeIf { _ -> it.enabled }, error = e.message ?: "Source check failed") }
    } finally {
      activeBatch = null
    }
  }

  private fun updateTrack(
    id: String,
    revision: String,
    mutate: (TrackedManga) -> TrackedManga,
  ) {
    store.update { current -> current.copy(tracked = current.tracked.map { if (it.id == id && it.revision == revision) mutate(it) else it }) }
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
    explicitlySelected: Boolean = false,
  ): DownloadRecord {
    check(!executor.isShutdown) { "Download worker is stopping" }
    val current = state()
    val existing =
      current.history.find {
        it.provider == source.id && it.seriesId == seriesId && it.chapter.id == chapter.id && it.destination == destination
      }
    if (existing != null) {
      if (mode == "auto" && !explicitlySelected) return existing
      when (files.presence(existing)) {
        DownloadPresence.PRESENT, DownloadPresence.STAGED -> return existing
        DownloadPresence.MISSING -> Unit
        DownloadPresence.INCOMPLETE -> error("Some converted parts remain on disk. Restore the missing parts or remove the remaining parts before downloading again.")
        DownloadPresence.UNAVAILABLE -> error("Recorded download location is unavailable. Check the library before downloading again.")
      }
    }
    val settings = current.kcc
    val selected = profileId ?: if (mode == "auto") settings.autoProfileId else settings.manualProfileId
    val profile =
      if (selected == "off")
        null
      else
        requireNotNull(settings.profiles.find { it.id == selected }) {
          "Selected KCC profile no longer exists; choose an available profile"
        }
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
      store.update { it.copy(history = listOf(record) + it.history.filterNot { previous -> previous.id == existing?.id }) }
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
