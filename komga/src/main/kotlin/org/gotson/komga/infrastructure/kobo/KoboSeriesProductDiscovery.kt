package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

private val logger = KotlinLogging.logger {}

/** Learns book identities only from an already-proxied device-initiated series response. */
@Component
class KoboSeriesProductDiscovery(
  private val bookMetadataRepository: BookMetadataRepository,
  private val mappingRepository: KoboProductMappingRepository,
) {
  fun learn(
    localBookIds: Collection<String>,
    upstreamSeries: JsonNode?,
  ) {
    val items = upstreamSeries?.path("Items")?.takeIf { it.isArray } ?: return
    if (localBookIds.isEmpty()) return

    // Duplicate local ISBNs cannot identify a unique Komga book within this series.
    val localBooksByIsbn =
      bookMetadataRepository
        .findAllByIds(localBookIds)
        .mapNotNull { metadata ->
          normalizeIsbn(metadata.isbn)?.let { isbn -> isbn to metadata.bookId }
        }.groupBy({ it.first }, { it.second })

    // Ambiguous upstream ISBNs must never depend on response order.
    val upstreamByIsbn =
      items
        .mapNotNull { item ->
          val book = item.path("Book")
          val isbn =
            normalizeIsbn(book.path("ISBN").takeIf { it.isTextual }?.asText())
              ?: return@mapNotNull null
          val productId =
            book.path("Id").takeIf { it.isTextual }?.asText()
              ?: return@mapNotNull null
          if (runCatching { UUID.fromString(productId) }.isFailure) return@mapNotNull null
          isbn to book
        }.groupBy({ it.first }, { it.second })

    for ((isbn, books) in upstreamByIsbn) {
      if (books.size != 1) continue
      val bookId = localBooksByIsbn[isbn]?.singleOrNull() ?: continue
      val book = books.single()
      val productId = book.path("Id").asText()
      val existing = mappingRepository.findByBookId(bookId)
      if (
        existing != null &&
        existing.isbn == isbn &&
        existing.status == KoboProductMappingStatus.FOUND &&
        existing.productId != productId
      ) {
        logger.debug { "Ignoring conflicting Kobo ProductId discovered for local book $bookId" }
        continue
      }
      if (existing != null && existing.isbn == isbn && existing.status == KoboProductMappingStatus.FOUND) continue

      // Do not adopt a ProductId already tied to a different ISBN.
      if (
        mappingRepository.findByProductId(productId).any {
          it.status == KoboProductMappingStatus.FOUND && it.isbn != isbn
        }
      ) {
        logger.debug { "Ignoring Kobo ProductId already associated with another ISBN for local book $bookId" }
        continue
      }

      val observedSeriesId =
        book
          .path("SeriesId")
          .takeIf { it.isTextual }
          ?.asText()
          ?.takeIf { it.isNotBlank() }
      val checkedAt = LocalDateTime.now()
      mappingRepository.save(
        KoboProductMapping(
          bookId = bookId,
          isbn = isbn,
          productId = productId,
          observedKoboSeriesId = observedSeriesId,
          status = KoboProductMappingStatus.FOUND,
          checkedAt = checkedAt,
          // Absent series data in this response is not an ISBN-page check.
          seriesCheckedAt = checkedAt.takeIf { observedSeriesId != null },
        ),
      )
    }
  }

  private fun normalizeIsbn(value: String?): String? =
    value
      ?.filter(Char::isDigit)
      ?.takeIf { it.length == 13 }
}
