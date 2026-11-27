package org.gotson.komga.downloads

import org.gotson.komga.domain.model.Media
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.MediaRepository
import org.springframework.stereotype.Component

/** Live verification against Komga's indexed books, not merely task enqueue success. */
@Component
class DownloadImportStatus(
  private val files: DownloadFiles,
  private val books: BookRepository,
  private val media: MediaRepository,
) {
  fun status(record: DownloadRecord): String =
    runCatching {
      val paths = files.publishedPaths(record)
      if (paths.isEmpty()) return@runCatching "No complete published output"
      val states =
        paths.map { path ->
          books.findNotDeletedByLibraryIdAndUrlOrNull(record.destination.libraryId, path.toUri().toURL())?.let { book ->
            media.findByIdOrNull(book.id)?.status
          }
        }
      when {
        states.all { it == Media.Status.READY } -> "Imported and ready"
        states.any { it == Media.Status.ERROR || it == Media.Status.UNSUPPORTED } -> "Import or analysis failed"
        states.all { it == null } -> "Not yet verified in library"
        else -> "Import or analysis pending"
      }
    }.getOrDefault("Import verification unavailable")
}
