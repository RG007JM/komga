package org.gotson.komga.domain.persistence

interface KoboArchivedBookRepository {
  fun archive(
    userId: String,
    bookId: String,
  )

  fun unarchive(
    userId: String,
    bookId: String,
  ): Boolean

  fun findArchivedBookIds(userId: String): Set<String>

  fun snapshotArchiveState(
    syncPointId: String,
    userId: String,
  )

  data class ArchivePage(
    val bookIds: List<String>,
    val hasMore: Boolean,
  )

  fun findPendingArchiveChanges(
    fromSyncPointId: String,
    toSyncPointId: String,
    itemLimit: Int,
  ): ArchivePage

  fun markArchiveChangesSynced(
    syncPointId: String,
    bookIds: Collection<String>,
  )

  fun isArchived(
    userId: String,
    bookId: String,
  ): Boolean
}
