package org.gotson.komga.infrastructure.jooq.main

import org.gotson.komga.domain.persistence.KoboArchivedBookRepository
import org.gotson.komga.infrastructure.jooq.SplitDslDaoBase
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Component
class KoboArchivedBookDao(
  dslRW: DSLContext,
  @Qualifier("dslContextRO") dslRO: DSLContext,
) : SplitDslDaoBase(dslRW, dslRO),
  KoboArchivedBookRepository {
  private val archived = Tables.KOBO_ARCHIVED_BOOK
  private val syncBooks = Tables.SYNC_POINT_BOOK

  override fun archive(
    userId: String,
    bookId: String,
  ) {
    val now = ZonedDateTime.now(ZoneOffset.UTC).toString()
    dslRW
      .insertInto(archived, archived.USER_ID, archived.BOOK_ID, archived.ARCHIVED_AT)
      .values(userId, bookId, now)
      .onDuplicateKeyUpdate()
      .set(archived.ARCHIVED_AT, now)
      .execute()
  }

  override fun unarchive(
    userId: String,
    bookId: String,
  ): Boolean =
    dslRW
      .deleteFrom(archived)
      .where(archived.USER_ID.eq(userId))
      .and(archived.BOOK_ID.eq(bookId))
      .execute() > 0

  override fun findArchivedBookIds(userId: String): Set<String> =
    dslRO
      .select(archived.BOOK_ID)
      .from(archived)
      .where(archived.USER_ID.eq(userId))
      .fetch(archived.BOOK_ID)
      .toSet()

  override fun snapshotArchiveState(
    syncPointId: String,
    userId: String,
  ) {
    dslRW
      .update(syncBooks)
      .set(
        syncBooks.KOBO_ARCHIVED,
        DSL.exists(
          dslRW
            .selectOne()
            .from(archived)
            .where(archived.USER_ID.eq(userId))
            .and(archived.BOOK_ID.eq(syncBooks.BOOK_ID)),
        ),
      ).where(syncBooks.SYNC_POINT_ID.eq(syncPointId))
      .execute()
  }

  override fun findPendingArchiveChanges(
    fromSyncPointId: String,
    toSyncPointId: String,
    itemLimit: Int,
  ): KoboArchivedBookRepository.ArchivePage {
    require(itemLimit >= 0)

    val current = syncBooks.`as`("current")
    val previous = syncBooks.`as`("previous")
    val bookIds =
      dslRO
        .select(current.BOOK_ID)
        .from(current)
        .leftJoin(previous)
        .on(previous.SYNC_POINT_ID.eq(fromSyncPointId))
        .and(previous.BOOK_ID.eq(current.BOOK_ID))
        .where(current.SYNC_POINT_ID.eq(toSyncPointId))
        .and(current.KOBO_ARCHIVED.isTrue)
        .and(current.KOBO_ARCHIVE_SYNCED.isFalse)
        .and(previous.BOOK_ID.isNull.or(previous.KOBO_ARCHIVED.isFalse))
        .orderBy(current.BOOK_ID)
        .limit(itemLimit + 1)
        .fetch(current.BOOK_ID)

    return KoboArchivedBookRepository.ArchivePage(
      bookIds = bookIds.take(itemLimit),
      hasMore = bookIds.size > itemLimit,
    )
  }

  override fun markArchiveChangesSynced(
    syncPointId: String,
    bookIds: Collection<String>,
  ) {
    if (bookIds.isEmpty()) return

    dslRW
      .update(syncBooks)
      .set(syncBooks.KOBO_ARCHIVE_SYNCED, true)
      .where(syncBooks.SYNC_POINT_ID.eq(syncPointId))
      .and(syncBooks.BOOK_ID.`in`(bookIds))
      .execute()
  }

  override fun isArchived(
    userId: String,
    bookId: String,
  ): Boolean =
    dslRO.fetchExists(
      dslRO
        .selectOne()
        .from(archived)
        .where(archived.USER_ID.eq(userId))
        .and(archived.BOOK_ID.eq(bookId)),
    )
}
