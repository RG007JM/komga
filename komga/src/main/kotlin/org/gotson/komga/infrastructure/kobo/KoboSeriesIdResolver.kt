// KOBO_PERSISTENT_SERIES_MAPPING_V2
package org.gotson.komga.infrastructure.kobo

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.domain.persistence.KoboSeriesMappingRepository
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboSeriesIdResolver(
  private val bookRepository: BookRepository,
  private val bookMetadataRepository: BookMetadataRepository,
  private val koboProductMappingRepository: KoboProductMappingRepository,
  private val koboSeriesMappingRepository: KoboSeriesMappingRepository,
) {
  fun resolveSeriesId(komgaSeriesId: String): String? {
    val existing =
      koboSeriesMappingRepository.findKoboSeriesId(komgaSeriesId)

    val bookIds =
      bookRepository.findAllIdsBySeriesId(komgaSeriesId)

    if (bookIds.isEmpty()) {
      return existing
    }

    val metadataByBookId =
      bookMetadataRepository
        .findAllByIds(bookIds)
        .associateBy { it.bookId }

    val observations =
      koboProductMappingRepository
        .findByBookIds(bookIds)
        .asSequence()
        .filter { it.status == KoboProductMappingStatus.FOUND }
        .filter { mapping ->
          normalizeIsbn(
            metadataByBookId[mapping.bookId]?.isbn,
          ) == mapping.isbn
        }.mapNotNull { it.observedKoboSeriesId }
        .filter { it.isNotBlank() }
        .toList()

    val winner =
      strictMajority(observations)

    if (
      winner != null &&
      winner != existing
    ) {
      koboSeriesMappingRepository.upsert(
        seriesId = komgaSeriesId,
        koboSeriesId = winner,
      )

      logger.debug {
        "Updated Kobo SeriesId mapping for Komga series $komgaSeriesId from $existing to $winner"
      }

      return winner
    }

    return winner ?: existing
  }

  fun resolveSeriesIdIfLocal(komgaSeriesId: String): String? = resolveSeriesId(komgaSeriesId)

  private fun strictMajority(
    observations: Collection<String>,
  ): String? {
    if (observations.isEmpty()) return null

    val counts =
      observations
        .groupingBy { it }
        .eachCount()

    val winner =
      counts.maxByOrNull { it.value }
        ?: return null

    return winner.key.takeIf {
      winner.value * 2 > observations.size
    }
  }

  private fun normalizeIsbn(value: String?): String? {
    val normalized =
      value
        ?.filter(Char::isDigit)
        .orEmpty()

    return normalized.takeIf {
      it.length == 13
    }
  }
}
