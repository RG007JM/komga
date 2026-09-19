package org.gotson.komga.interfaces.api.kobo

import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.springframework.stereotype.Service

/** The administrator's explicit metadata-refresh action also restores their archived Kobo books. */
@Service
class KoboArchiveRestoreService(
  private val koboArchivedBookRepository: KoboArchivedBookRepository,
  private val bookMetadataRepository: BookMetadataRepository,
) {
  fun restoreOnExplicitMetadataRefresh(
    userId: String,
    bookIds: Collection<String>,
  ) {
    if (bookIds.isEmpty()) return

    val archivedIds = koboArchivedBookRepository.findArchivedBookIds(userId)
    bookIds.toSet().filter { it in archivedIds }.forEach { bookId ->
      // SyncPointDao compares the metadata timestamp, not BOOK.LAST_MODIFIED_DATE.
      // Touch only the timestamp so an unchanged metadata refresh is still delivered as a restoration.
      if (bookMetadataRepository.touchLastModifiedDate(bookId)) {
        koboArchivedBookRepository.unarchive(userId, bookId)
      }
    }
  }
}
