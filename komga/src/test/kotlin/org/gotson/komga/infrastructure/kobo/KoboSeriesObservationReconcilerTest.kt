package org.gotson.komga.infrastructure.kobo

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gotson.komga.domain.model.BookMetadata
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/** All lookups are mocked: tests must never contact the authenticated Kobo Store API or its website. */
class KoboSeriesObservationReconcilerTest {
  private val mappings = mockk<KoboProductMappingRepository>(relaxed = true)
  private val metadata = mockk<BookMetadataRepository>()
  private val books = mockk<BookRepository>()
  private val seriesIds = mockk<KoboSeriesIdResolver>()
  private val client = mockk<KoboProductClient>()
  private val reconciler = KoboSeriesObservationReconciler(mappings, metadata, books, seriesIds, client)

  private val isbn = "9781974755998"
  private val productId = "6852e6c6-96d3-4000-9851-c4fb8d2cbaa4"
  private val majorityId = "4b64f394-81ca-59b8-96c0-57790e01ecc7"
  private val otherId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"

  @BeforeEach
  fun resetMocks() {
    clearMocks(mappings, metadata, books, seriesIds, client)
  }

  private fun mapping(
    seriesId: String? = null,
    status: KoboProductMappingStatus = KoboProductMappingStatus.FOUND,
    mappedProductId: String? = productId,
    checkedAt: LocalDateTime? = LocalDateTime.now().minusDays(31),
  ) = KoboProductMapping(
    bookId = "book-16",
    isbn = isbn,
    productId = mappedProductId,
    observedKoboSeriesId = seriesId,
    status = status,
    checkedAt = LocalDateTime.now().minusDays(60),
    seriesCheckedAt = checkedAt,
  )

  private fun prepare(
    mapping: KoboProductMapping,
    effective: String? = majorityId,
    bookIsbn: String = isbn,
  ) {
    every { mappings.findSeriesReconciliationCandidates(any(), any()) } returns listOf(mapping)
    every { mappings.findByBookId(mapping.bookId) } returns mapping
    every { metadata.findByIdOrNull(mapping.bookId) } returns
      BookMetadata(title = "Spy x Family Vol. 16", number = "16", numberSort = 16F, bookId = mapping.bookId, isbn = bookIsbn)
    every { books.getSeriesIdOrNull(mapping.bookId) } returns "komga-spy-series"
    every { seriesIds.resolveSeriesId("komga-spy-series") } returns effective
  }

  @Test
  fun `matching product id updates missing observed series without touching product identity`() {
    val original = mapping(seriesId = null)
    prepare(original)
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Found(productId, majorityId)

    reconciler.reconcileDueBatch()

    verify(exactly = 1) {
      mappings.save(
        match {
          it.bookId == original.bookId && it.productId == productId && it.isbn == isbn &&
            it.checkedAt == original.checkedAt && it.observedKoboSeriesId == majorityId &&
            it.seriesCheckedAt != original.seriesCheckedAt
        },
      )
    }
    verify(exactly = 1) { client.findProductByIsbn(isbn) }
    verify(exactly = 2) { seriesIds.resolveSeriesId("komga-spy-series") }
  }

  @Test
  fun `unexpected product id never changes identity or observed series`() {
    val original = mapping(seriesId = otherId)
    prepare(original)
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Found("new-product-id", majorityId)

    reconciler.reconcileDueBatch()

    verify(exactly = 1) {
      mappings.save(
        match {
          it.productId == productId && it.observedKoboSeriesId == otherId &&
            it.checkedAt == original.checkedAt && it.seriesCheckedAt != original.seriesCheckedAt
        },
      )
    }
    verify(exactly = 1) { seriesIds.resolveSeriesId("komga-spy-series") }
  }

  @Test
  fun `not found retains established identity and backs off`() {
    val original = mapping(seriesId = otherId)
    prepare(original)
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.NotFound

    reconciler.reconcileDueBatch()

    verify(exactly = 1) {
      mappings.save(
        match {
          it.productId == productId && it.observedKoboSeriesId == otherId &&
            it.seriesCheckedAt != original.seriesCheckedAt
        },
      )
    }
  }

  @Test
  fun `website failure retains identity and backs off`() {
    val original = mapping(seriesId = null)
    prepare(original)
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Failed(IllegalStateException("offline"))

    reconciler.reconcileDueBatch()

    verify(exactly = 1) {
      mappings.save(match { it.productId == productId && it.observedKoboSeriesId == null && it.seriesCheckedAt != original.seriesCheckedAt })
    }
  }

  @Test
  fun `missing ProductId never attempts series website lookup`() {
    val original = mapping(status = KoboProductMappingStatus.NOT_FOUND, mappedProductId = null)
    prepare(original)

    reconciler.reconcileDueBatch()

    verify(exactly = 0) { client.findProductByIsbn(any()) }
    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `matching effective series does not need a lookup`() {
    prepare(mapping(seriesId = majorityId))

    reconciler.reconcileDueBatch()

    verify(exactly = 0) { client.findProductByIsbn(any()) }
    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `recently checked dissenting series does not need a lookup`() {
    prepare(mapping(seriesId = otherId, checkedAt = LocalDateTime.now()))

    reconciler.reconcileDueBatch()

    verify(exactly = 0) { client.findProductByIsbn(any()) }
  }

  @Test
  fun `changed local ISBN invalidates stale product evidence without looking up the series`() {
    val original = mapping(seriesId = otherId)
    prepare(original, bookIsbn = "9781974753246")

    reconciler.reconcileDueBatch()

    verify(exactly = 1) { mappings.deleteByBookId(original.bookId) }
    verify(exactly = 0) { client.findProductByIsbn(any()) }
  }

  @Test
  fun `existing cached product with no series check is eligible for first reconciliation`() {
    val original = mapping(seriesId = null, checkedAt = null)
    prepare(original)
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Found(productId, null)

    reconciler.reconcileDueBatch()

    verify(exactly = 1) {
      mappings.save(match { it.productId == productId && it.observedKoboSeriesId == null && it.seriesCheckedAt != null })
    }
  }

  @Test
  fun `changed product mapping during lookup cannot be overwritten`() {
    val original = mapping(seriesId = otherId)
    prepare(original)
    every { mappings.findByBookId(original.bookId) } returnsMany
      listOf(original, original.copy(productId = "replacement"))
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Found(productId, majorityId)

    reconciler.reconcileDueBatch()

    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `two local copies of one ISBN share a single website request in a batch`() {
    val first = mapping(seriesId = null)
    val second = first.copy(bookId = "book-16-duplicate")
    every { mappings.findSeriesReconciliationCandidates(any(), any()) } returns listOf(first, second)
    every { mappings.findByBookId(first.bookId) } returns first
    every { mappings.findByBookId(second.bookId) } returns second
    every { metadata.findByIdOrNull(any()) } answers {
      val bookId = firstArg<String>()
      BookMetadata(title = bookId, number = "16", numberSort = 16F, bookId = bookId, isbn = isbn)
    }
    every { books.getSeriesIdOrNull(any()) } returns "komga-spy-series"
    every { seriesIds.resolveSeriesId("komga-spy-series") } returns majorityId
    every { client.findProductByIsbn(isbn) } returns KoboProductLookupResult.Found(productId, majorityId)

    reconciler.reconcileDueBatch()

    verify(exactly = 1) { client.findProductByIsbn(isbn) }
    verify(exactly = 2) { mappings.save(any()) }
  }

  @Test
  fun `no candidates results in no website traffic`() {
    every { mappings.findSeriesReconciliationCandidates(any(), any()) } returns emptyList()

    reconciler.reconcileDueBatch()

    verify(exactly = 0) { client.findProductByIsbn(any()) }
    verify(exactly = 0) { mappings.save(any()) }
  }
}
