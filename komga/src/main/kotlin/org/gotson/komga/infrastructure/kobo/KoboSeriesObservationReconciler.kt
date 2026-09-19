package org.gotson.komga.infrastructure.kobo

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

/** Refreshes only already-identified Kobo books using the public ISBN website search. */
@Component
class KoboSeriesObservationReconciler(
  private val mappingRepository: KoboProductMappingRepository,
  private val bookMetadataRepository: BookMetadataRepository,
  private val bookRepository: BookRepository,
  private val seriesIdResolver: KoboSeriesIdResolver,
  private val productClient: KoboProductClient,
) {
  fun reconcileDueBatch() {
    val olderThan = LocalDateTime.now().minusDays(RETRY_DAYS)
    val candidates = mappingRepository.findSeriesReconciliationCandidates(olderThan, BATCH_SIZE)
    // Multiple local files with the same ISBN should share one website search per batch.
    val resultsByIsbn = mutableMapOf<String, KoboProductLookupResult>()

    candidates.forEach { candidate ->
      try {
        reconcile(candidate, olderThan, resultsByIsbn)
      } catch (e: Exception) {
        logger.warn(e) { "Could not reconcile Kobo SeriesId for book ${candidate.bookId}" }
      }
    }
  }

  private fun reconcile(
    candidate: KoboProductMapping,
    olderThan: LocalDateTime,
    resultsByIsbn: MutableMap<String, KoboProductLookupResult>,
  ) {
    // The candidate may have changed while it was waiting in the scheduled batch.
    val current = mappingRepository.findByBookId(candidate.bookId) ?: return
    if (current.status != KoboProductMappingStatus.FOUND || current.productId.isNullOrBlank()) return
    if (current.isbn != candidate.isbn || current.productId != candidate.productId) return
    if (current.seriesCheckedAt?.isAfter(olderThan) == true) return

    val metadata = bookMetadataRepository.findByIdOrNull(current.bookId)
    val currentIsbn = metadata?.isbn?.filter(Char::isDigit)?.takeIf { it.length == 13 }
    if (currentIsbn != current.isbn) {
      // Stale mappings cannot participate in consensus or repeatedly occupy batch slots.
      mappingRepository.deleteByBookId(current.bookId)
      return
    }

    val komgaSeriesId = bookRepository.getSeriesIdOrNull(current.bookId) ?: return
    val effectiveSeriesId = seriesIdResolver.resolveSeriesId(komgaSeriesId)
    if (effectiveSeriesId != null && current.observedKoboSeriesId == effectiveSeriesId) return

    val result =
      resultsByIsbn.getOrPut(current.isbn) {
        productClient.findProductByIsbn(current.isbn)
      }

    // A concurrent ISBN/identity refresh must not be overwritten by an older website result.
    val latest = mappingRepository.findByBookId(current.bookId) ?: return
    if (latest != current) return
    val latestIsbn =
      bookMetadataRepository.findByIdOrNull(current.bookId)?.isbn?.filter(Char::isDigit)
    if (latestIsbn != current.isbn) return

    val attemptAt = LocalDateTime.now()
    when (result) {
      is KoboProductLookupResult.Found -> {
        if (result.productId != current.productId) {
          logger.warn {
            "Ignoring Kobo SeriesId refresh for book ${current.bookId}: ISBN ${current.isbn} returned a different ProductId"
          }
          mappingRepository.save(current.copy(seriesCheckedAt = attemptAt))
          return
        }

        mappingRepository.save(
          current.copy(
            observedKoboSeriesId = result.seriesId,
            seriesCheckedAt = attemptAt,
          ),
        )
        // Recalculate the mapping from the accepted book observation; never use last-writer-wins.
        if (result.seriesId != current.observedKoboSeriesId) {
          seriesIdResolver.resolveSeriesId(komgaSeriesId)
        }
      }

      KoboProductLookupResult.NotFound -> {
        // A missing public page cannot erase an established ProductId/SeriesId.
        mappingRepository.save(current.copy(seriesCheckedAt = attemptAt))
      }

      is KoboProductLookupResult.Failed -> {
        logger.debug(result.cause) { "Kobo SeriesId refresh unavailable for book ${current.bookId}" }
        mappingRepository.save(current.copy(seriesCheckedAt = attemptAt))
      }
    }
  }

  private companion object {
    const val RETRY_DAYS = 30L
    const val BATCH_SIZE = 8
  }
}
