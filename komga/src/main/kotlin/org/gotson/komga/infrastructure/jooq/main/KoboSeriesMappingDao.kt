package org.gotson.komga.infrastructure.jooq.main

import org.gotson.komga.domain.persistence.KoboSeriesMappingRepository
import org.gotson.komga.infrastructure.jooq.SplitDslDaoBase
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class KoboSeriesMappingDao(
  dslRW: DSLContext,
  @Qualifier("dslContextRO") dslRO: DSLContext,
) : SplitDslDaoBase(dslRW, dslRO),
  KoboSeriesMappingRepository {
  private val k = Tables.KOBO_SERIES_MAPPING

  override fun findKoboSeriesId(komgaSeriesId: String): String? =
    dslRO
      .select(k.KOBO_SERIES_ID)
      .from(k)
      .where(k.SERIES_ID.eq(komgaSeriesId))
      .fetchOne(k.KOBO_SERIES_ID)

  override fun upsert(
    seriesId: String,
    koboSeriesId: String,
  ): Boolean =
    dslRW
      .insertInto(k, k.SERIES_ID, k.KOBO_SERIES_ID)
      .values(seriesId, koboSeriesId)
      .onDuplicateKeyUpdate()
      .set(k.KOBO_SERIES_ID, koboSeriesId)
      .execute() > 0
}
