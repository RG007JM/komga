// KOBO_PERSISTENT_SERIES_MAPPING_V2
package org.gotson.komga.infrastructure.kobo

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboSeriesIdResolver(
  private val koboSeriesMappingRepository: KoboSeriesMappingRepository,
) {
  fun resolveSeriesId(komgaSeriesId: String): String? = koboSeriesMappingRepository.findKoboSeriesId(komgaSeriesId)

  fun resolveSeriesIdIfLocal(komgaSeriesId: String): String? = koboSeriesMappingRepository.findKoboSeriesId(komgaSeriesId)

  fun rememberForBook(
    bookId: String,
    koboSeriesId: String,
  ) {
    if (bookId.isBlank() || koboSeriesId.isBlank() || bookId == koboSeriesId) {
      return
    }

    if (
      koboSeriesMappingRepository.upsertForBook(
        bookId = bookId,
        koboSeriesId = koboSeriesId,
      )
    ) {
      logger.debug {
        "Remembered Kobo SeriesId $koboSeriesId from mapped Komga book $bookId"
      }
    }
  }
}
