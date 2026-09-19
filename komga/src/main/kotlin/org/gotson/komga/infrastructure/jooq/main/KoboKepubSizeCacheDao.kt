package org.gotson.komga.infrastructure.jooq.main

import org.gotson.komga.domain.persistence.KoboKepubSizeCacheRepository
import org.gotson.komga.infrastructure.jooq.SplitDslDaoBase
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class KoboKepubSizeCacheDao(
  dslRW: DSLContext,
  @Qualifier("dslContextRO") dslRO: DSLContext,
) : SplitDslDaoBase(dslRW, dslRO),
  KoboKepubSizeCacheRepository {
  private val k = Tables.KOBO_KEPUB_SIZE_CACHE
  private val sizeField = k.KEPUB_FILE_SIZE.coerce(Long::class.java)

  override fun findByBookId(bookId: String): KoboKepubSizeCacheRepository.Entry? =
    dslRO
      .select(k.SOURCE_FILE_HASH, sizeField)
      .from(k)
      .where(k.BOOK_ID.eq(bookId))
      .fetchOne()
      ?.let {
        KoboKepubSizeCacheRepository.Entry(
          sourceFileHash = it.value1(),
          kepubFileSize = it.value2(),
        )
      }

  override fun upsert(
    bookId: String,
    sourceFileHash: String,
    kepubFileSize: Long,
  ) {
    dslRW
      .insertInto(k, k.BOOK_ID, k.SOURCE_FILE_HASH, sizeField)
      .values(bookId, sourceFileHash, kepubFileSize)
      .onDuplicateKeyUpdate()
      .set(k.SOURCE_FILE_HASH, sourceFileHash)
      .set(sizeField, kepubFileSize)
      .execute()
  }
}
