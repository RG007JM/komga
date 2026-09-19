package org.gotson.komga.infrastructure.jooq.main

import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.jooq.SplitDslDaoBase
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class KoboProductMappingDao(
  dslRW: DSLContext,
  @Qualifier("dslContextRO") dslRO: DSLContext,
) : SplitDslDaoBase(dslRW, dslRO),
  KoboProductMappingRepository {
  private val k = Tables.KOBO_PRODUCT_MAPPING

  override fun findByBookId(bookId: String): KoboProductMapping? =
    dslRO
      .selectFrom(k)
      .where(k.BOOK_ID.eq(bookId))
      .fetchOne()
      ?.toDomain()

  override fun findByIsbn(isbn: String): Collection<KoboProductMapping> =
    dslRO
      .selectFrom(k)
      .where(k.ISBN.eq(isbn))
      .fetch()
      .map { it.toDomain() }

  override fun findByProductId(productId: String): Collection<KoboProductMapping> =
    dslRO
      .selectFrom(k)
      .where(k.PRODUCT_ID.eq(productId))
      .fetch()
      .map { it.toDomain() }

  override fun findByBookIds(bookIds: Collection<String>): Collection<KoboProductMapping> {
    if (bookIds.isEmpty()) return emptyList()

    return dslRO
      .selectFrom(k)
      .where(k.BOOK_ID.`in`(bookIds))
      .fetch()
      .map { it.toDomain() }
  }

  override fun save(mapping: KoboProductMapping) {
    dslRW
      .insertInto(
        k,
        k.BOOK_ID,
        k.ISBN,
        k.PRODUCT_ID,
        k.OBSERVED_KOBO_SERIES_ID,
        k.STATUS,
        k.CHECKED_AT,
        k.SERIES_CHECKED_AT,
      ).values(
        mapping.bookId,
        mapping.isbn,
        mapping.productId,
        mapping.observedKoboSeriesId,
        mapping.status.name,
        mapping.checkedAt,
        mapping.seriesCheckedAt,
      ).onDuplicateKeyUpdate()
      .set(k.ISBN, mapping.isbn)
      .set(k.PRODUCT_ID, mapping.productId)
      .set(k.OBSERVED_KOBO_SERIES_ID, mapping.observedKoboSeriesId)
      .set(k.STATUS, mapping.status.name)
      .set(k.CHECKED_AT, mapping.checkedAt)
      .set(k.SERIES_CHECKED_AT, mapping.seriesCheckedAt)
      .execute()
  }

  override fun deleteByBookId(bookId: String) {
    dslRW
      .deleteFrom(k)
      .where(k.BOOK_ID.eq(bookId))
      .execute()
  }

  private fun org.gotson.komga.jooq.main.tables.records.KoboProductMappingRecord.toDomain() =
    KoboProductMapping(
      bookId = bookId,
      isbn = isbn,
      productId = productId,
      observedKoboSeriesId = observedKoboSeriesId,
      status = KoboProductMappingStatus.valueOf(status),
      checkedAt = checkedAt,
      seriesCheckedAt = seriesCheckedAt,
    )
}
