package org.gotson.komga.downloads

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gotson.komga.application.tasks.TaskEmitter
import org.gotson.komga.infrastructure.configuration.KomgaProperties
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.time.Instant
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DownloadServiceTest {
  @TempDir
  lateinit var directory: Path

  private val destination = DownloadDestination("library", "Series")
  private val old = SourceChapter("old", "1", "Old", Instant.parse("2026-01-01T00:00:00Z"), "en", "https://example.org/old")
  private val fresh = old.copy(id = "fresh", number = "2", title = "New")
  private val source = mockk<DownloadProvider>()
  private val files = mockk<DownloadFiles>()
  private val converter = mockk<DownloadKcc>()
  private val tasks = mockk<TaskEmitter>(relaxed = true)
  private lateinit var service: DownloadService
  private val global = KccProfile("global", "Global", "KoLC", keepOriginal = false)
  private val specific = KccProfile("specific", "Specific", "KoLC", keepOriginal = false)

  @BeforeEach
  fun setup() {
    val properties = mockk<KomgaProperties>()
    every { properties.configDir } returns directory.toString()
    val store = DownloadStore(properties, jacksonObjectMapper().findAndRegisterModules())
    every { source.id } returns "fixture"
    every { source.chapters("series", any()) } returns listOf(old)
    every { source.nextRelease("series") } returns null
    every { source.pages(any()) } returns emptyList()
    every { files.root("library") } returns directory
    every { files.target(directory, "Series", false) } returns directory.resolve("Series")
    every { files.archive(any(), any(), any(), any(), any(), any()) } returns "Series/chapter.cbz"
    every { converter.convert(any(), any()) } returns listOf("Series/chapter.epub", "Series/chapter 2.epub")
    every { files.removeOriginal(any()) } answers {
      val record = firstArg<DownloadRecord>()
      assertEqualValue(
        "Converted",
        service
          .state()
          .history
          .single { it.id == record.id }
          .conversion
          ?.status,
      )
    }
    service = DownloadService(listOf(source), store, files, converter, tasks)
    service.kcc(KccSettings(autoProfileId = global.id, profiles = listOf(global, specific)))
  }

  @AfterEach
  fun cleanup() {
    if (::service.isInitialized) service.close()
  }

  private fun track(
    profile: String? = null,
    enabled: Boolean = true,
  ) = service.track(TrackRequest("fixture", "series", "Series", destination, enabled = enabled, kccProfileId = profile))

  private fun awaitCheck(id: String) {
    val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10)
    while (System.nanoTime() < deadline) {
      if (service
          .state()
          .tracked
          .single { it.id == id }
          .lastCheck != null
      )
        return
      Thread.sleep(10)
    }
    error("Worker did not finish check: ${service.workerError}")
  }

  private fun checkFresh(profile: String?): DownloadRecord {
    val entry = track(profile)
    every { source.chapters("series", any()) } returns listOf(old, fresh)
    service.check(entry.id)
    awaitCheck(entry.id)
    assertEqualValue(
      setOf(old.id, fresh.id),
      service
        .state()
        .tracked
        .single()
        .seen,
    )
    return service.state().history.single()
  }

  @Test
  fun `new subscription seeds old chapters without downloading them`() {
    val entry = track()
    assertEqualValue(setOf(old.id), entry.seen)
    assertTrueValue(service.state().history.isEmpty())
    verify(exactly = 0) { files.archive(any(), any(), any(), any(), any(), any()) }
  }

  @Test
  fun `selected existing chapters are fetched with series profile and white page`() {
    every { source.chapters("series", any()) } returns listOf(old, fresh)
    val entry = service.track(TrackRequest("fixture", "series", "Series", destination, kccProfileId = "off", prependWhitePage = true, initialChapterIds = setOf(fresh.id)))
    assertEqualValue(setOf(fresh.id), entry.pendingInitialChapterIds)
    service.check(entry.id)
    awaitCheck(entry.id)
    assertEqualValue(
      fresh.id,
      service
        .state()
        .history
        .single()
        .chapter.id,
    )
    assertTrueValue(
      service
        .state()
        .history
        .single()
        .prependWhitePage,
    )
    assertTrueValue(
      service
        .state()
        .tracked
        .single()
        .pendingInitialChapterIds
        .isEmpty(),
    )
    verify(exactly = 1) { files.archive(any(), any(), any(), any(), false, true) }
  }

  @Test
  fun `unknown initial chapter is rejected before tracking`() {
    assertThrowsType(IllegalArgumentException::class.java) {
      service.track(TrackRequest("fixture", "series", "Series", destination, initialChapterIds = setOf("unknown")))
    }
    assertTrueValue(service.state().tracked.isEmpty())
  }

  @Test
  fun `publication estimate uses source dates even before downloads`() {
    val first = Instant.parse("2026-01-01T12:00:00Z")
    val second = first.plusSeconds(7 * 86400L)
    every { source.chapters("series", any()) } returns listOf(old.copy(publishedAt = first), fresh.copy(publishedAt = second))
    val entry = track("off")
    service.check(entry.id)
    awaitCheck(entry.id)
    assertEqualValue(
      second.plusSeconds(7 * 86400L),
      service
        .state()
        .tracked
        .single()
        .expectedRelease,
    )
    assertEqualValue(
      "Estimated from publication dates",
      service
        .state()
        .tracked
        .single()
        .releaseBasis,
    )
    assertTrueValue(service.state().history.isEmpty())
  }

  @Test
  fun `one dated chapter cannot establish release cadence`() {
    assertNullValue(PublicationReleaseEstimate.expected(listOf(old), Instant.parse("2026-02-01T00:00:00Z")))
    assertNullValue(PublicationReleaseEstimate.expected(listOf(old, old.copy(id = "alternate")), Instant.parse("2026-02-01T00:00:00Z")))
  }

  @Test
  fun `disabled subscription has no scheduled check`() {
    val entry = track(enabled = false)
    assertFalseValue(entry.enabled)
    assertNullValue(entry.nextCheck)
  }

  @Test
  fun `auto fetch inherits the global profile`() {
    val record = checkFresh(null)
    assertEqualValue(fresh.id, record.chapter.id)
    assertEqualValue(global, record.conversion?.profile)
    assertEqualValue("Converted", record.conversion?.status)
    assertEqualValue(listOf("Series/chapter.epub", "Series/chapter 2.epub"), record.conversion?.files)
    assertEqualValue("Scan queued", record.scan)
    verify(exactly = 1) { files.archive(any(), any(), any(), any(), true, false) }
    verify(exactly = 1) { converter.convert(any(), global) }
    verify(exactly = 1) { files.removeOriginal(any()) }
  }

  @Test
  fun `series profile overrides the global profile`() {
    assertEqualValue(specific, checkFresh(specific.id).conversion?.profile)
    verify(exactly = 1) { converter.convert(any(), specific) }
  }

  @Test
  fun `off overrides the global profile`() {
    val record = checkFresh("off")
    assertNullValue(record.conversion)
    assertEqualValue("Scan queued", record.scan)
    verify(exactly = 1) { files.archive(any(), any(), any(), any(), false, false) }
    verify(exactly = 0) { converter.convert(any(), any()) }
  }

  @Test
  fun `download failure does not mark new chapter as seen`() {
    val entry = track("off")
    every { source.chapters("series", any()) } returns listOf(old, fresh)
    every { files.archive(any(), any(), any(), any(), any(), any()) } throws IllegalStateException("fixture failure")
    service.check(entry.id)
    awaitCheck(entry.id)
    assertEqualValue(
      setOf(old.id),
      service
        .state()
        .tracked
        .single()
        .seen,
    )
    assertEqualValue(
      "fixture failure",
      service
        .state()
        .tracked
        .single()
        .error,
    )
    assertTrueValue(service.state().history.isEmpty())
  }

  @Test
  fun `conversion failure is recorded for retry`() {
    every { converter.convert(any(), specific) } throws IllegalStateException("fixture KCC failure")
    val record = checkFresh(specific.id)
    assertEqualValue("Failed", record.conversion?.status)
    assertEqualValue("fixture KCC failure", record.conversion?.error)
    assertEqualValue("Conversion failed", record.scan)
    assertEqualValue(
      "fixture KCC failure",
      service
        .state()
        .tracked
        .single()
        .error,
    )
    verify(exactly = 0) { files.removeOriginal(any()) }
    verify(exactly = 0) { tasks.scanLibrary(any()) }
  }

  @Test
  fun `manual request overrides the global manual profile`() {
    service.kcc(KccSettings(manualProfileId = global.id, profiles = listOf(global, specific)))
    service.download(ManualRequest("fixture", "series", "Series", destination, setOf(old.id), kccProfileId = specific.id))
    val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10)
    while (System.nanoTime() < deadline) {
      val record = service.state().history.singleOrNull()
      if (record?.scan == "Scan queued") {
        assertEqualValue(specific, record.conversion?.profile)
        verify(exactly = 1) { converter.convert(any(), specific) }
        verify(exactly = 0) { converter.convert(any(), global) }
        return
      }
      Thread.sleep(10)
    }
    error("Manual download did not finish: ${service.workerError}")
  }

  @Test
  fun `auto fetch applies persistent white page setting`() {
    val entry = service.track(TrackRequest("fixture", "series", "Series", destination, kccProfileId = "off", prependWhitePage = true))
    assertTrueValue(entry.prependWhitePage)
    every { source.chapters("series", any()) } returns listOf(old, fresh)
    service.check(entry.id)
    awaitCheck(entry.id)
    assertTrueValue(
      service
        .state()
        .history
        .single()
        .prependWhitePage,
    )
    verify(exactly = 1) { files.archive(any(), any(), any(), any(), false, true) }
  }

  @Test
  fun `manual white page setting applies to every selected chapter`() {
    every { source.chapters("series", any()) } returns listOf(old, fresh)
    service.download(ManualRequest("fixture", "series", "Series", destination, setOf(old.id, fresh.id), kccProfileId = "off", prependWhitePage = true))
    val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10)
    while (System.nanoTime() < deadline) {
      val history = service.state().history
      if (history.size == 2 && history.all { it.scan == "Scan queued" }) {
        assertTrueValue(history.all { it.prependWhitePage })
        verify(exactly = 2) { files.archive(any(), any(), any(), any(), false, true) }
        return
      }
      Thread.sleep(10)
    }
    error("Manual downloads did not finish: ${service.workerError}")
  }

  @Test
  fun `unknown profile and removal of referenced profile are rejected`() {
    assertThrowsType(IllegalArgumentException::class.java) { track("unknown") }
    track(specific.id)
    assertThrowsType(IllegalArgumentException::class.java) {
      service.kcc(KccSettings(autoProfileId = global.id, profiles = listOf(global)))
    }
  }

  @Test
  fun `unsafe converter arguments are rejected`() {
    assertThrowsType(IllegalArgumentException::class.java) {
      service.kcc(KccSettings(profiles = listOf(global.copy(extraArgs = listOf("--output=/tmp")))))
    }
  }
}
