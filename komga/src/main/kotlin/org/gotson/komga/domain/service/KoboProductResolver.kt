package org.gotson.komga.domain.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboProductClient
import org.gotson.komga.infrastructure.kobo.KoboProductLookupResult
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Service
class KoboProductResolver(
  private val bookMetadataRepository: BookMetadataRepository,
  private val koboProductMappingRepository: KoboProductMappingRepository,
  private val koboProductClient: KoboProductClient,
) {
  fun resolveProductId(bookId: String): String? {
    val metadata =
      bookMetadataRepository.findByIdOrNull(bookId)
        ?: return null

    val isbn = normalizeIsbn(metadata.isbn)

    val existing =
      koboProductMappingRepository.findByBookId(bookId)

    if (isbn == null) {
      if (existing != null) {
        koboProductMappingRepository.deleteByBookId(bookId)
      }

      return null
    }

    if (existing != null && existing.isbn == isbn) {
      when (existing.status) {
        KoboProductMappingStatus.FOUND ->
          return existing.productId

        KoboProductMappingStatus.NOT_FOUND -> {
          if (!isNotFoundExpired(existing)) {
            return null
          }
        }
      }
    }

    if (existing != null && existing.isbn != isbn) {
      logger.debug {
        "ISBN changed for book $bookId from ${existing.isbn} to $isbn; resolving Kobo ProductId again"
      }
    }

    val reusable =
      koboProductMappingRepository
        .findByIsbn(isbn)
        .firstOrNull { mapping ->
          when (mapping.status) {
            KoboProductMappingStatus.FOUND ->
              mapping.productId != null

            KoboProductMappingStatus.NOT_FOUND ->
              !isNotFoundExpired(mapping)
          }
        }

    if (reusable != null) {
      koboProductMappingRepository.save(
        reusable.copy(
          bookId = bookId,
        ),
      )

      return reusable.productId
    }

    return when (
      val result =
        koboProductClient.findProductByIsbn(isbn)
    ) {
      is KoboProductLookupResult.Found -> {
        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = result.productId,
            status = KoboProductMappingStatus.FOUND,
            checkedAt = LocalDateTime.now(),
          ),
        )

        result.productId
      }

      KoboProductLookupResult.NotFound -> {
        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = null,
            status = KoboProductMappingStatus.NOT_FOUND,
            checkedAt = LocalDateTime.now(),
          ),
        )

        null
      }

      is KoboProductLookupResult.Failed -> {
        logger.warn(result.cause) {
          "Could not resolve Kobo ProductId for book $bookId with ISBN $isbn"
        }

        null
      }
    }
  }

  /**
   * Reverse a real Kobo ProductId back to a local Komga book ID.
   *
   * Only mappings that are still consistent with the book's current ISBN
   * are returned. This prevents a stale mapping from surviving a manual
   * ISBN change.
   */
  fun resolveBookId(productId: String): String? {
    val mappings =
      koboProductMappingRepository
        .findByProductId(productId)
        .filter {
          it.status == KoboProductMappingStatus.FOUND &&
            it.productId == productId
        }.sortedBy {
          it.bookId
        }

    for (mapping in mappings) {
      val metadata =
        bookMetadataRepository
          .findByIdOrNull(mapping.bookId)

      if (metadata == null) {
        koboProductMappingRepository
          .deleteByBookId(mapping.bookId)

        continue
      }

      val currentIsbn =
        normalizeIsbn(metadata.isbn)

      if (
        currentIsbn == null ||
        currentIsbn != mapping.isbn
      ) {
        logger.debug {
          "Discarding stale Kobo ProductId mapping for book ${mapping.bookId}"
        }

        koboProductMappingRepository
          .deleteByBookId(mapping.bookId)

        continue
      }

      return mapping.bookId
    }

    return null
  }

  private fun normalizeIsbn(value: String): String? {
    val normalized =
      value.filter(Char::isDigit)

    return normalized.takeIf {
      it.length == 13
    }
  }

  private fun isNotFoundExpired(
    mapping: KoboProductMapping,
  ): Boolean =
    mapping.checkedAt.isBefore(
      LocalDateTime
        .now()
        .minusDays(NOT_FOUND_TTL_DAYS),
    )

  companion object {
    private const val NOT_FOUND_TTL_DAYS =
      30L
  }
}
