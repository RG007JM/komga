package org.gotson.komga.domain.persistence

interface KoboSeriesMappingRepository {
  fun findKoboSeriesId(komgaSeriesId: String): String?

  fun upsert(
    seriesId: String,
    koboSeriesId: String,
  ): Boolean
}
