package org.gotson.komga.downloads

import com.fasterxml.jackson.databind.ObjectMapper
import org.gotson.komga.infrastructure.configuration.KomgaProperties
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption.ATOMIC_MOVE
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.time.Instant

data class DownloadDestination(
  val libraryId: String,
  val folder: String,
)

data class TrackedManga(
  val id: String,
  val provider: String,
  val seriesId: String,
  val title: String,
  val destination: DownloadDestination,
  val filters: ChapterFilters = ChapterFilters(),
  val enabled: Boolean = true,
  val kccProfileId: String? = null, // null inherits; "off" always skips.
  val seen: Set<String> = emptySet(),
  val lastCheck: Instant? = null,
  val nextCheck: Instant? = null,
  val expectedRelease: Instant? = null,
  val releaseBasis: String? = null,
  val result: String? = null,
  val error: String? = null,
  val prependWhitePage: Boolean = false,
  val pendingInitialChapterIds: Set<String> = emptySet(),
  val catalogueVersion: String? = null,
  val publicationFloor: Instant? = null,
  val chapterNumberFloor: String? = null,
  val revision: String = "legacy",
  val pendingAutoChapterIds: Set<String> = emptySet(),
  val failedChapterAttempts: Map<String, Int> = emptyMap(),
)

data class DownloadRecord(
  val id: String,
  val provider: String,
  val seriesId: String,
  val title: String,
  val chapter: SourceChapter,
  val destination: DownloadDestination,
  val file: String,
  val downloadedAt: Instant,
  val mode: String,
  val scan: String,
  val conversion: Conversion? = null,
  val prependWhitePage: Boolean = false,
)

data class Conversion(
  val profile: KccProfile,
  val status: String,
  val file: String? = null,
  val error: String? = null,
  val files: List<String> = emptyList(),
)

data class KccProfile(
  val id: String,
  val name: String,
  val device: String,
  val format: String = "EPUB",
  val manga: Boolean = true,
  // Legacy serialized fields: retained for compatibility, no longer control publication.
  val keepOriginal: Boolean = false,
  val outputFolder: String = "",
  val extraArgs: List<String> = emptyList(),
)

data class KccSettings(
  val autoProfileId: String = "off",
  val manualProfileId: String = "off",
  val profiles: List<KccProfile> = emptyList(),
)

data class DownloadState(
  val tracked: List<TrackedManga> = emptyList(),
  val history: List<DownloadRecord> = emptyList(),
  val kcc: KccSettings = KccSettings(),
)

@Component
class DownloadStore(
  properties: KomgaProperties,
  private val mapper: ObjectMapper,
) {
  private val directory =
    properties.configDir?.let { Path.of(it).toAbsolutePath() }
      ?: Path.of(properties.database.file).toAbsolutePath().parent
  private val path = directory.resolve("manga-downloads.json")
  private var state: DownloadState? = null

  @Synchronized
  fun read(): DownloadState =
    state ?: (
      if (Files.exists(path))
        mapper.readValue(path.toFile(), DownloadState::class.java)
      else
        DownloadState()
    ).also { state = it }

  @Synchronized
  fun update(mutate: (DownloadState) -> DownloadState): DownloadState {
    val previous = read()
    val next = mutate(previous)
    if (next == previous) return previous
    Files.createDirectories(directory)
    val temporary = Files.createTempFile(directory, ".manga-downloads-", ".tmp")
    try {
      mapper.writeValue(temporary.toFile(), next)
      java.nio.channels.FileChannel
        .open(temporary, java.nio.file.StandardOpenOption.WRITE)
        .use { it.force(true) }
      Files.move(temporary, path, ATOMIC_MOVE, REPLACE_EXISTING)
      state = next
    } finally {
      Files.deleteIfExists(temporary)
    }
    return next
  }

  fun health(): Boolean =
    runCatching {
      read()
      val parent = generateSequence(directory, Path::getParent).first { Files.exists(it) }
      Files.isDirectory(parent) && Files.isWritable(parent)
    }.getOrDefault(false)
}
