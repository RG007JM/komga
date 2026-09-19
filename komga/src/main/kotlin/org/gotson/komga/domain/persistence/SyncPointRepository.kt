package org.gotson.komga.domain.persistence

import org.gotson.komga.domain.model.BookSearch
import org.gotson.komga.domain.model.SearchContext
import org.gotson.komga.domain.model.SyncPoint
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SyncPointRepository {
  fun create(
    apiKeyId: String?,
    search: BookSearch,
    context: SearchContext,
  ): SyncPoint

  fun addOnDeck(
    syncPointId: String,
    context: SearchContext,
    filterOnLibraryIds: List<String>?,
  )

  fun findByIdOrNull(syncPointId: String): SyncPoint?

  fun findBooksById(
    syncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.Book>

  fun findBooksAdded(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.Book>

  fun findBooksRemoved(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.Book>

  fun findBooksChanged(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.Book>

  fun findBooksReadProgressChanged(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.Book>

  fun findReadListsById(
    syncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.ReadList>

  fun findReadListsAdded(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.ReadList>

  fun findReadListsChanged(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.ReadList>

  fun findReadListsRemoved(
    fromSyncPointId: String,
    toSyncPointId: String,
    onlyNotSynced: Boolean,
    pageable: Pageable,
  ): Page<SyncPoint.ReadList>

  fun findLatestReadListSnapshot(
    userId: String,
    readListId: String,
  ): SyncPoint.ReadListSnapshot?

  fun findBookIdsByReadListIds(
    syncPointId: String,
    readListIds: Collection<String>,
  ): List<SyncPoint.ReadList.Book>

  /** User-scoped Kobo Store members frozen with this SyncPoint, never Komga ReadList books. */
  fun findExternalRevisionIdsByReadListIds(
    syncPointId: String,
    readListIds: Collection<String>,
  ): Map<String, List<String>>

  fun markBooksSynced(
    syncPointId: String,
    forRemovedBooks: Boolean,
    bookIds: Collection<String>,
  )

  fun markReadListsSynced(
    syncPointId: String,
    forRemovedReadLists: Boolean,
    readListIds: Collection<String>,
  )

  fun deleteByUserId(userId: String)

  fun deleteByUserIdAndApiKeyIds(
    userId: String,
    apiKeyIds: Collection<String>,
  )

  fun deleteOne(syncPointId: String)

  fun deleteAll()
}
