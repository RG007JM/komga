package org.gotson.komga.downloads

import io.mockk.every
import io.mockk.mockk
import org.gotson.komga.domain.model.Book
import org.gotson.komga.domain.model.Media
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.MediaRepository
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.time.Instant

class DownloadImportStatusTest {
  @Test
  fun `queued scan is not proof of import and all parts must be ready`() {
    val files = mockk<DownloadFiles>()
    val books = mockk<BookRepository>()
    val media = mockk<MediaRepository>()
    val status = DownloadImportStatus(files, books, media)
    val chapter = SourceChapter("chapter", "1", "Title", null, "en", "https://example.org")
    val record = DownloadRecord("id", "fixture", "series", "Series", chapter, DownloadDestination("library", "Series"), "Series/a.cbz", Instant.now(), "manual", "Scan queued")
    val first = Path.of("first.epub").toAbsolutePath()
    val second = Path.of("second.epub").toAbsolutePath()
    every { files.publishedPaths(record) } returns listOf(first, second)
    every { books.findNotDeletedByLibraryIdAndUrlOrNull("library", any()) } returns null
    assertEqualValue("Not yet verified in library", status.status(record))
    val one = mockk<Book> { every { id } returns "one" }
    val two = mockk<Book> { every { id } returns "two" }
    every { books.findNotDeletedByLibraryIdAndUrlOrNull("library", first.toUri().toURL()) } returns one
    every { media.findByIdOrNull("one") } returns Media(status = Media.Status.READY)
    assertEqualValue("Import or analysis pending", status.status(record))
    every { books.findNotDeletedByLibraryIdAndUrlOrNull("library", second.toUri().toURL()) } returns two
    every { media.findByIdOrNull("two") } returns Media(status = Media.Status.ERROR)
    assertEqualValue("Import or analysis failed", status.status(record))
    every { media.findByIdOrNull("two") } returns Media(status = Media.Status.READY)
    assertEqualValue("Imported and ready", status.status(record))
  }
}
