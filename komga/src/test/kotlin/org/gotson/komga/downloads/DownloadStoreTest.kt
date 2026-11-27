package org.gotson.komga.downloads

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.gotson.komga.infrastructure.configuration.KomgaProperties
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant

class DownloadStoreTest {
  @TempDir
  lateinit var directory: Path

  private fun store(): DownloadStore {
    val properties = mockk<KomgaProperties>()
    every { properties.configDir } returns directory.toString()
    return DownloadStore(properties, jacksonObjectMapper().findAndRegisterModules())
  }

  @Test
  fun `new store is empty and healthy`() {
    val store = store()
    assertEqualValue(DownloadState(), store.read())
    assertTrueValue(store.health())
  }

  @Test
  fun `profiles and tracked state persist across store restart`() {
    val profile = KccProfile("kobo", "Kobo", "KoLC")
    val tracked = TrackedManga("entry", "fixture", "series", "Series", DownloadDestination("library", "Series"), kccProfileId = profile.id, seen = setOf("a"), lastCheck = Instant.parse("2026-01-01T00:00:00Z"))
    val expected = DownloadState(tracked = listOf(tracked), kcc = KccSettings(autoProfileId = profile.id, profiles = listOf(profile)))
    store().update { expected }
    assertEqualValue(expected, store().read())
    assertTrueValue(Files.isRegularFile(directory.resolve("manga-downloads.json")))
    Files.list(directory).use { paths -> assertFalseValue(paths.anyMatch { it.fileName.toString().endsWith(".tmp") }) }
  }

  @Test
  fun `white page setting persists and legacy entries default to disabled`() {
    val enabled = TrackedManga("entry", "fixture", "series", "Series", DownloadDestination("library", "Series"), prependWhitePage = true)
    store().update { DownloadState(tracked = listOf(enabled)) }
    assertTrueValue(
      store()
        .read()
        .tracked
        .single()
        .prependWhitePage,
    )
    val path = directory.resolve("manga-downloads.json")
    val mapper = jacksonObjectMapper()
    val json = mapper.readTree(path.toFile())
    (json.path("tracked").get(0) as com.fasterxml.jackson.databind.node.ObjectNode).remove("prependWhitePage")
    mapper.writeValue(path.toFile(), json)
    assertFalseValue(
      store()
        .read()
        .tracked
        .single()
        .prependWhitePage,
    )
  }

  @Test
  fun `release baseline and pending automatic chapters survive restart with legacy defaults`() {
    val tracked = TrackedManga("entry", "fixture", "series", "Series", DownloadDestination("library", "Series"), catalogueVersion = "native", publicationFloor = Instant.parse("2026-01-01T00:00:00Z"), chapterNumberFloor = "145.5", revision = "revision", pendingAutoChapterIds = setOf("pending"))
    val state = DownloadState(tracked = listOf(tracked))
    store().update { state }
    assertEqualValue(state, store().read())
    val path = directory.resolve("manga-downloads.json")
    val mapper = jacksonObjectMapper()
    val json = mapper.readTree(path.toFile())
    val legacy = json.path("tracked").get(0) as com.fasterxml.jackson.databind.node.ObjectNode
    listOf("catalogueVersion", "publicationFloor", "chapterNumberFloor", "revision", "pendingAutoChapterIds").forEach { legacy.remove(it) }
    mapper.writeValue(path.toFile(), json)
    val restored = store().read().tracked.single()
    assertNullValue(restored.catalogueVersion)
    assertNullValue(restored.publicationFloor)
    assertTrueValue(restored.pendingAutoChapterIds.isEmpty())
  }

  @Test
  fun `corrupt state is reported as unhealthy`() {
    Files.writeString(directory.resolve("manga-downloads.json"), "{broken")
    assertFalseValue(store().health())
  }
}
