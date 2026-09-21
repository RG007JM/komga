package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

/**
 * Observes responses to requests made by a Kobo device. This component never contacts Store API.
 * Only a previously verified local ProductId/ISBN mapping may gain a SeriesId this way.
 */
@Component
class KoboDeviceSeriesObserver(
  private val mappings: KoboProductMappingRepository,
  private val metadata: BookMetadataRepository,
  private val books: BookRepository,
  private val seriesIds: KoboSeriesIdResolver,
) {
  /** The locally rewritten BookDetails response must NOT be passed here. */
  fun observeBookDetails(
    requestedProductId: String,
    upstream: JsonNode?,
    localBookId: String? = null,
    canObserve: (String) -> Boolean = { true },
  ) {
    val productId = uuid(requestedProductId) ?: return
    val book = upstream?.path("Book")?.takeIf { it.isObject } ?: upstream ?: return
    if (uuid(book.path("Id").takeIf { it.isTextual }?.asText()) != productId) return
    val seriesId = uuid(book.path("SeriesId").takeIf { it.isTextual }?.asText()) ?: return
    observe(productId, seriesId, localBookId, canObserve)
  }

  /** For /v1/products/books/series/{KoboSeriesId}; do not use a Komga series ID here. */
  fun observeSeriesResponse(
    requestedSeriesId: String?,
    upstream: JsonNode?,
    canObserve: (String) -> Boolean = { true },
  ) {
    val seriesId = uuid(requestedSeriesId) ?: return
    val items = upstream?.path("Items")?.takeIf { it.isArray } ?: return
    val products =
      items
        .mapNotNull { item ->
          val book = item.path("Book").takeIf { it.isObject } ?: return@mapNotNull null
          val productId = uuid(book.path("Id").takeIf { it.isTextual }?.asText()) ?: return@mapNotNull null
          val responseSeriesId = book.path("SeriesId").takeIf { it.isTextual }?.asText()
          // A conflicting ID in the actual book record is not an observation of this series.
          if (responseSeriesId != null && uuid(responseSeriesId) != seriesId) return@mapNotNull null
          productId
        }.groupingBy { it }
        .eachCount()
    // One response item per ProductId: never pick an arbitrary duplicate.
    products.filterValues { it == 1 }.keys.forEach { productId ->
      observe(productId, seriesId, null, canObserve)
    }
  }

  private fun observe(
    productId: String,
    seriesId: String,
    localBookId: String?,
    canObserve: (String) -> Boolean,
  ) {
    val candidates =
      if (localBookId != null) {
        listOfNotNull(mappings.findByBookId(localBookId))
      } else {
        mappings.findByProductId(productId).toList()
      }
    // A Store ProductId without one unambiguous, current local identity is insufficient.
    val current =
      candidates.singleOrNull { mapping ->
        mapping.status == KoboProductMappingStatus.FOUND &&
          uuid(mapping.productId) == productId &&
          KoboLookupIsbn.normalize(metadata.findByIdOrNull(mapping.bookId)?.isbn) == mapping.isbn
      } ?: return
    if (!canObserve(current.bookId)) return
    // The ISBN lookup may already have verified this identity. Device responses only fill a gap;
    // they must not replace an existing SeriesId, whether equal to or different from this one.
    if (current.observedKoboSeriesId != null) return

    // Re-check before saving: an ISBN refresh may have populated the SeriesId in the meantime.
    val latest = mappings.findByBookId(current.bookId) ?: return
    if (latest != current || latest.observedKoboSeriesId != null) return
    if (KoboLookupIsbn.normalize(metadata.findByIdOrNull(current.bookId)?.isbn) != current.isbn) return
    val komgaSeriesId = books.getSeriesIdOrNull(current.bookId) ?: return
    mappings.save(
      current.copy(
        observedKoboSeriesId = seriesId,
        seriesCheckedAt = LocalDateTime.now(),
        seriesLookupFailedAt = null,
      ),
    )
    seriesIds.resolveSeriesId(komgaSeriesId)
    logger.debug { "Observed Kobo SeriesId $seriesId for local book ${current.bookId} from a device-initiated Store response" }
  }

  private fun uuid(value: String?): String? =
    value?.let { candidate ->
      runCatching { UUID.fromString(candidate).toString() }
        .getOrNull()
        ?.takeIf { it.equals(candidate, ignoreCase = true) }
    }
}
