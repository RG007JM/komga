package org.gotson.komga.domain.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PreDestroy
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboLookupIsbn
import org.gotson.komga.infrastructure.kobo.KoboProductClient
import org.gotson.komga.infrastructure.kobo.KoboProductLookupResult
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

@Service
class KoboProductResolver(
  private val bookMetadataRepository: BookMetadataRepository,
  private val koboProductMappingRepository: KoboProductMappingRepository,
  private val koboProductClient: KoboProductClient,
) {
  // No device request is allowed to walk all 47 storefronts. The bounded daemon worker
  // completes a miss in the background and subsequent requests use the durable mapping.
  private val pendingLookups = ConcurrentHashMap.newKeySet<String>()
  private val lookupExecutor =
    ThreadPoolExecutor(
      1,
      1,
      0L,
      TimeUnit.MILLISECONDS,
      ArrayBlockingQueue<Runnable>(32),
      { task -> Thread(task, "komga-kobo-identity").apply { isDaemon = true } },
      ThreadPoolExecutor.AbortPolicy(),
    )

  @PreDestroy
  fun stopPendingLookups() {
    lookupExecutor.shutdownNow()
  }

  /** Uses the original GB search request only, then queues a bounded full global lookup on a miss. */
  fun resolveProductIdForDevice(bookId: String): String? = resolveProductIdInternal(bookId, preferredOnly = true)

  fun resolveProductId(bookId: String): String? = resolveProductIdInternal(bookId, preferredOnly = false)

  private fun scheduleGlobalLookup(bookId: String) {
    if (!pendingLookups.add(bookId)) return
    try {
      lookupExecutor.execute {
        try {
          resolveProductId(bookId)
        } catch (e: Exception) {
          logger.warn(e) { "Background Kobo identity lookup failed for book $bookId" }
        } finally {
          pendingLookups.remove(bookId)
        }
      }
    } catch (e: java.util.concurrent.RejectedExecutionException) {
      pendingLookups.remove(bookId)
      logger.debug { "Kobo identity lookup queue is full; a later device request can retry $bookId" }
    }
  }

  private fun resolveProductIdInternal(
    bookId: String,
    preferredOnly: Boolean,
  ): String? {
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

    if (
      existing != null &&
      existing.isbn == isbn &&
      existing.status == KoboProductMappingStatus.FOUND
    ) {
      return existing.productId
    }

    if (existing != null && existing.isbn != isbn) {
      logger.debug {
        "ISBN changed for book $bookId from ${existing.isbn} to $isbn; resolving Kobo ProductId again"
      }
    }

    val reusableMappings =
      koboProductMappingRepository
        .findByIsbn(isbn)

    val foundMappings =
      reusableMappings.filter { mapping ->
        mapping.status == KoboProductMappingStatus.FOUND && mapping.productId != null
      }
    val reusableFound =
      foundMappings
        .takeIf { mappings ->
          mappings.mapNotNull { it.productId }.distinct().size == 1
        }?.firstOrNull { mapping ->
          mapping.bookId != bookId && koboProductClient.canShareMapping(bookId, mapping.bookId)
        }

    if (reusableFound != null) {
      if (!isCurrent(bookId, isbn, existing)) return null
      koboProductMappingRepository.save(reusableFound.copy(bookId = bookId))
      return reusableFound.productId
    }

    if (
      existing != null &&
      existing.isbn == isbn &&
      existing.status == KoboProductMappingStatus.NOT_FOUND &&
      existing.lookupPolicy != null &&
      existing.lookupPolicy == koboProductClient.lookupPolicy(bookId) &&
      !isNotFoundExpired(existing)
    ) {
      return null
    }

    // Never copy a negative to another book: the ISBN alone doesn't attest its lookup policy.
    if (preferredOnly && pendingLookups.contains(bookId)) return null

    val lookupPolicy = koboProductClient.lookupPolicy(bookId)
    val result =
      if (preferredOnly)
        koboProductClient.findProductInOriginalStorefront(isbn)
      else
        koboProductClient.findProductForBook(isbn, bookId)
    // A refresh or file scan may have changed this book while its website request ran.
    if (!isCurrent(bookId, isbn, existing) || koboProductClient.lookupPolicy(bookId) != lookupPolicy) return null
    return when (result) {
      is KoboProductLookupResult.Found -> {
        val checkedAt = LocalDateTime.now()

        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = result.productId,
            observedKoboSeriesId = result.seriesId,
            status = KoboProductMappingStatus.FOUND,
            checkedAt = checkedAt,
            seriesCheckedAt = checkedAt,
          ),
        )

        result.productId
      }

      KoboProductLookupResult.NotFound -> {
        if (preferredOnly) {
          scheduleGlobalLookup(bookId)
          return null
        }
        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = null,
            status = KoboProductMappingStatus.NOT_FOUND,
            checkedAt = LocalDateTime.now(),
            lookupPolicy = lookupPolicy,
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
   * Called after a user-requested metadata refresh. A resolved identity for the current ISBN
   * is stable: refreshing unrelated metadata must not replace it or issue a website request.
   * If the ISBN changes, reuse an identity already found for that ISBN before querying Kobo.
   * Returns true only when a FOUND identity was newly associated with this book.
   */
  fun refreshKoboIdentity(bookId: String): Boolean {
    val metadata = bookMetadataRepository.findByIdOrNull(bookId) ?: return false
    val isbn = normalizeIsbn(metadata.isbn)
    val before = koboProductMappingRepository.findByBookId(bookId)

    if (isbn == null) {
      if (before != null) koboProductMappingRepository.deleteByBookId(bookId)
      return false
    }

    if (before?.isbn == isbn && before.status == KoboProductMappingStatus.FOUND && before.productId != null) {
      return false
    }

    val reusableMappings = koboProductMappingRepository.findByIsbn(isbn)
    val foundMappings =
      reusableMappings.filter {
        it.status == KoboProductMappingStatus.FOUND && it.productId != null
      }
    val reusableFound =
      foundMappings
        .takeIf { mappings ->
          mappings.mapNotNull { it.productId }.distinct().size == 1
        }?.firstOrNull { it.bookId != bookId && koboProductClient.canShareMapping(bookId, it.bookId) }

    if (reusableFound != null) {
      if (bookMetadataRepository.findByIdOrNull(bookId)?.isbn?.let(::normalizeIsbn) != isbn ||
        koboProductMappingRepository.findByBookId(bookId) != before
      )
        return false
      koboProductMappingRepository.save(reusableFound.copy(bookId = bookId))
      return true
    }

    if (before?.isbn == isbn && before.status == KoboProductMappingStatus.NOT_FOUND &&
      before.lookupPolicy != null && before.lookupPolicy == koboProductClient.lookupPolicy(bookId) &&
      !isNotFoundExpired(before)
    ) {
      return false
    }

    // Do not reuse a negative cache entry belonging to another book/lookup policy.

    val lookupPolicy = koboProductClient.lookupPolicy(bookId)
    val result = koboProductClient.findProductForBook(isbn, bookId)

    // Do not write a result obtained for an ISBN/mapping that changed while the website was queried.
    val latestIsbn = bookMetadataRepository.findByIdOrNull(bookId)?.isbn?.let(::normalizeIsbn)
    if (latestIsbn != isbn || koboProductMappingRepository.findByBookId(bookId) != before ||
      koboProductClient.lookupPolicy(bookId) != lookupPolicy
    )
      return false

    return when (result) {
      is KoboProductLookupResult.Found -> {
        val checkedAt = LocalDateTime.now()
        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = result.productId,
            observedKoboSeriesId = result.seriesId,
            status = KoboProductMappingStatus.FOUND,
            checkedAt = checkedAt,
            seriesCheckedAt = checkedAt,
          ),
        )
        if (before?.productId != null && before.productId != result.productId) {
          logger.info { "Explicit Kobo identity refresh changed ProductId for book $bookId" }
        }
        true
      }

      KoboProductLookupResult.NotFound -> {
        // The old positive mapping, if any, belongs to a different ISBN and is now stale.
        koboProductMappingRepository.save(
          KoboProductMapping(
            bookId = bookId,
            isbn = isbn,
            productId = null,
            status = KoboProductMappingStatus.NOT_FOUND,
            checkedAt = LocalDateTime.now(),
            lookupPolicy = lookupPolicy,
          ),
        )
        false
      }

      is KoboProductLookupResult.Failed -> {
        logger.warn(result.cause) { "Explicit Kobo identity refresh failed for book $bookId" }
        false
      }
    }
  }

  /**
   * Reverse a real Kobo ProductId back to a local Komga book ID.
   *
   * Only mappings that are still consistent with the book's current ISBN
   * are considered. If multiple local books have the same ProductId, there
   * is no unambiguous reverse identity and the caller keeps the Kobo ID.
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

    val validBookIds = mutableSetOf<String>()

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

      validBookIds += mapping.bookId
    }

    if (validBookIds.size > 1) {
      logger.debug {
        "Kobo ProductId $productId has ${validBookIds.size} valid local book mappings; not choosing an arbitrary book"
      }
    }

    return validBookIds.singleOrNull()
  }

  private fun isCurrent(
    bookId: String,
    isbn: String,
    mapping: KoboProductMapping?,
  ): Boolean =
    normalizeIsbn(bookMetadataRepository.findByIdOrNull(bookId)?.isbn.orEmpty()) == isbn &&
      koboProductMappingRepository.findByBookId(bookId) == mapping

  private fun normalizeIsbn(value: String): String? = KoboLookupIsbn.normalize(value)

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
