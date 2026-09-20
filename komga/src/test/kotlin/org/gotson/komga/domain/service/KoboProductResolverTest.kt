package org.gotson.komga.domain.service

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.BookMetadata
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboProductClient
import org.gotson.komga.infrastructure.kobo.KoboProductLookupResult
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class KoboProductResolverTest {
  private val bookMetadataRepository = mockk<BookMetadataRepository>()
  private val mappingRepository = mockk<KoboProductMappingRepository>(relaxed = true)
  private val productClient = mockk<KoboProductClient>()

  private val resolver =
    KoboProductResolver(
      bookMetadataRepository = bookMetadataRepository,
      koboProductMappingRepository = mappingRepository,
      koboProductClient = productClient,
    )

  @BeforeEach
  fun resetMocks() {
    clearMocks(
      bookMetadataRepository,
      mappingRepository,
      productClient,
    )
    every { productClient.canShareMapping(any(), any()) } returns true
    every { productClient.lookupPolicy(any()) } returns "worldwide,locale"
  }

  @AfterEach
  fun stopLookupWorker() {
    resolver.stopPendingLookups()
  }

  @Test
  fun `changing the storefront policy invalidates a fresh negative without changing ISBN`() {
    val bookId = "book-language-changed"
    val isbn = "9781974755998"
    val old =
      KoboProductMapping(
        bookId,
        isbn,
        null,
        status = KoboProductMappingStatus.NOT_FOUND,
        checkedAt = LocalDateTime.now(),
        lookupPolicy = "old-language-plan",
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Book", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns old
    every { mappingRepository.findByIsbn(isbn) } returns listOf(old)
    every { productClient.findProductForBook(isbn, bookId) } returns KoboProductLookupResult.Found("new-product")

    assertThat(resolver.resolveProductId(bookId)).isEqualTo("new-product")
    verify(exactly = 1) { productClient.findProductForBook(isbn, bookId) }
  }

  @Test
  fun `device lookup first probes GB and queues full search only after a miss`() {
    val bookId = "book-fast-miss"
    val isbn = "9781974755998"
    val backgroundStarted = CountDownLatch(1)
    val allowBackgroundToFinish = CountDownLatch(1)
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Book", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns emptyList()
    every { productClient.findProductInOriginalStorefront(isbn) } returns KoboProductLookupResult.NotFound
    every { productClient.findProductForBook(isbn, bookId) } answers {
      backgroundStarted.countDown()
      check(allowBackgroundToFinish.await(15, TimeUnit.SECONDS)) {
        "Device lookup assertions did not release the background worker"
      }
      KoboProductLookupResult.NotFound
    }

    try {
      assertThat(resolver.resolveProductIdForDevice(bookId)).isNull()
      assertThat(backgroundStarted.await(15, TimeUnit.SECONDS))
        .describedAs("The global lookup must be queued after a GB miss")
        .isTrue()
      verify(exactly = 1) { productClient.findProductInOriginalStorefront(isbn) }
      verify(exactly = 1) { productClient.findProductForBook(isbn, bookId) }
    } finally {
      allowBackgroundToFinish.countDown()
    }
  }

  @Test
  fun `device lookup immediately returns a verified original-store identity`() {
    val bookId = "book-fast-found"
    val isbn = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Book", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns emptyList()
    every { productClient.findProductInOriginalStorefront(isbn) } returns KoboProductLookupResult.Found("gb-product")

    assertThat(resolver.resolveProductIdForDevice(bookId)).isEqualTo("gb-product")
    verify(exactly = 0) { productClient.findProductForBook(any(), any()) }
  }

  @Test
  fun `invalid ISBN rejects website lookup and clears a stale positive mapping`() {
    val bookId = "book-invalid"
    val invalidIsbn = "4972000027092" // Kobo's opaque Book ID is not an ISBN.
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "1", numberSort = 1F, isbn = invalidIsbn)
    every { mappingRepository.findByBookId(bookId) } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = invalidIsbn,
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    assertThat(resolver.resolveProductId(bookId)).isNull()
    verify(exactly = 1) { mappingRepository.deleteByBookId(bookId) }
    verify(exactly = 0) { mappingRepository.findByIsbn(any()) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `invalid ISBN on metadata refresh must not start a website lookup`() {
    val bookId = "book-invalid"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "1", numberSort = 1F, isbn = "9781974702014")
    every { mappingRepository.findByBookId(bookId) } returns null

    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `explicit metadata refresh retains an already found ProductId for unchanged ISBN`() {
    val bookId = "book-1"
    val isbn = "9781974753246"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = "old-product",
        observedKoboSeriesId = "old-series",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now().minusDays(7),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(
        bookId = bookId,
        title = "Test",
        number = "13",
        numberSort = 13F,
        isbn = isbn,
      )
    every { mappingRepository.findByBookId(bookId) } returns old
    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `explicit refresh uses changed local ISBN and does not reuse old mapping`() {
    val bookId = "book-1"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    val newIsbn = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(
        bookId = bookId,
        title = "Test",
        number = "16",
        numberSort = 16F,
        isbn = newIsbn,
      )
    every { mappingRepository.findByBookId(bookId) } returns old
    every { mappingRepository.findByIsbn(newIsbn) } returns emptyList()
    every { productClient.findProductForBook(newIsbn, any()) } returns
      KoboProductLookupResult.Found(productId = "new-product")

    assertThat(resolver.refreshKoboIdentity(bookId)).isTrue()
    verify(exactly = 1) {
      mappingRepository.save(match { it.bookId == bookId && it.isbn == newIsbn && it.productId == "new-product" })
    }
    verify(exactly = 0) { productClient.findProductForBook(old.isbn, any()) }
  }

  @Test
  fun `explicit refresh skips website even if website would now return not found for unchanged ISBN`() {
    val bookId = "book-1"
    val isbn = "9781974753246"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(
        bookId = bookId,
        title = "Test",
        number = "13",
        numberSort = 13F,
        isbn = isbn,
      )
    every { mappingRepository.findByBookId(bookId) } returns old
    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
    verify(exactly = 0) { mappingRepository.save(any()) }
    verify(exactly = 0) { mappingRepository.deleteByBookId(any()) }
  }

  @Test
  fun `explicit refresh ignores a website result if ISBN changes during request`() {
    val bookId = "book-1"
    val isbn = "9781974755998"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returnsMany
      listOf(
        BookMetadata(bookId = bookId, title = "Test", number = "13", numberSort = 13F, isbn = isbn),
        BookMetadata(bookId = bookId, title = "Test", number = "13", numberSort = 13F, isbn = "9781974756339"),
      )
    every { mappingRepository.findByBookId(bookId) } returns old
    every { mappingRepository.findByIsbn(isbn) } returns emptyList()
    every { productClient.findProductForBook(isbn, any()) } returns KoboProductLookupResult.Found("new-product")

    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `changed ISBN reuses a valid Kobo ProductId from another book without a website request`() {
    val bookId = "book-1"
    val newIsbn = "9781974755998"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    val reusable = old.copy(bookId = "book-2", isbn = newIsbn, productId = "new-product")
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "16", numberSort = 16F, isbn = newIsbn)
    every { mappingRepository.findByBookId(bookId) } returns old
    every { mappingRepository.findByIsbn(newIsbn) } returns listOf(reusable)

    assertThat(resolver.refreshKoboIdentity(bookId)).isTrue()
    verify(exactly = 1) { mappingRepository.save(reusable.copy(bookId = bookId)) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
  }

  @Test
  fun `same ISBN from a different storefront policy must be resolved for the requesting book`() {
    val bookId = "italian-book"
    val isbn = "9781974755998"
    val foreign =
      KoboProductMapping(
        bookId = "foreign-book",
        isbn = isbn,
        productId = "foreign-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Italian", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns listOf(foreign)
    every { productClient.canShareMapping(bookId, foreign.bookId) } returns false
    every { productClient.findProductForBook(isbn, bookId) } returns KoboProductLookupResult.Found("italian-product")

    assertThat(resolver.resolveProductId(bookId)).isEqualTo("italian-product")
    verify(exactly = 1) { productClient.findProductForBook(isbn, bookId) }
    verify(exactly = 1) { mappingRepository.save(match { it.bookId == bookId && it.productId == "italian-product" }) }
  }

  @Test
  fun `different known Product IDs for one ISBN cannot be reused arbitrarily`() {
    val bookId = "book-conflict"
    val isbn = "9781974755998"
    val first =
      KoboProductMapping(
        bookId = "first",
        isbn = isbn,
        productId = "product-a",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    val second = first.copy(bookId = "second", productId = "product-b")
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Conflict", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns listOf(first, second)
    every { productClient.findProductForBook(isbn, bookId) } returns KoboProductLookupResult.Found("product-b")

    assertThat(resolver.resolveProductId(bookId)).isEqualTo("product-b")
    verify(exactly = 1) { productClient.findProductForBook(isbn, bookId) }
    verify(exactly = 1) { mappingRepository.save(match { it.bookId == bookId && it.productId == "product-b" }) }
  }

  @Test
  fun `another books negative ISBN mapping does not suppress a region specific lookup`() {
    val bookId = "book-in-italy"
    val isbn = "9781974755998"
    val negative =
      KoboProductMapping(
        bookId = "book-in-uk",
        isbn = isbn,
        productId = null,
        status = KoboProductMappingStatus.NOT_FOUND,
        checkedAt = LocalDateTime.now(),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Italian edition", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns listOf(negative)
    every { productClient.findProductForBook(isbn, bookId) } returns KoboProductLookupResult.Found("italian-product")

    assertThat(resolver.resolveProductId(bookId)).isEqualTo("italian-product")
    verify(exactly = 1) { productClient.findProductForBook(isbn, bookId) }
  }

  @Test
  fun `unchanged ISBN with a fresh negative mapping does not query Kobo again`() {
    val bookId = "book-1"
    val isbn = "9781974755998"
    val notFound =
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = null,
        status = KoboProductMappingStatus.NOT_FOUND,
        checkedAt = LocalDateTime.now(),
        lookupPolicy = "worldwide,locale",
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "16", numberSort = 16F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns notFound
    every { mappingRepository.findByIsbn(isbn) } returns listOf(notFound)

    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { mappingRepository.save(any()) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
  }

  @Test
  fun `changed ISBN does not retain old identity if lookup for new ISBN returns not found`() {
    val bookId = "book-1"
    val newIsbn = "9781974755998"
    val old =
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "16", numberSort = 16F, isbn = newIsbn)
    every { mappingRepository.findByBookId(bookId) } returns old
    every { mappingRepository.findByIsbn(newIsbn) } returns emptyList()
    every { productClient.findProductForBook(newIsbn, any()) } returns KoboProductLookupResult.NotFound

    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId && it.isbn == newIsbn &&
            it.status == KoboProductMappingStatus.NOT_FOUND && it.productId == null
        },
      )
    }
    verify(exactly = 0) { productClient.findProductForBook(old.isbn, any()) }
  }

  @Test
  fun `explicit refresh skips ISBN-free book and removes stale mapping`() {
    val bookId = "book-1"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(
        bookId = bookId,
        title = "Test",
        number = "1",
        numberSort = 1F,
        isbn = "",
      )
    every { mappingRepository.findByBookId(bookId) } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "old-product",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 1) { mappingRepository.deleteByBookId(bookId) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
  }

  @Test
  fun `no ISBN does not query Kobo and removes stale mapping`() {
    val bookId = "book-1"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = "",
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = "9781974753246",
        productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isNull()

    verify(exactly = 1) {
      mappingRepository.deleteByBookId(bookId)
    }

    verify(exactly = 0) {
      productClient.findProductByIsbn(any())
    }
  }

  @Test
  fun `found ISBN is persisted and returned`() {
    val bookId = "book-1"
    val isbn = "9781974753246"
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"
    val seriesId = "4b64f394-81ca-59b8-96c0-57790e01ecc7"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns null

    every {
      mappingRepository.findByIsbn(isbn)
    } returns emptyList()

    every {
      productClient.findProductForBook(isbn, any())
    } returns
      KoboProductLookupResult.Found(
        productId = productId,
        seriesId = seriesId,
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(productId)

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId &&
            it.isbn == isbn &&
            it.productId == productId &&
            it.observedKoboSeriesId == seriesId &&
            it.status == KoboProductMappingStatus.FOUND &&
            it.seriesCheckedAt != null &&
            it.seriesCheckedAt == it.checkedAt
        },
      )
    }
  }

  @Test
  fun `found ISBN without series persists checked null series observation`() {
    val bookId = "book-1"
    val isbn = "9781974755998"
    val productId = "6852e6c6-96d3-4000-9851-c4fb8d2cbaa4"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "16",
        numberSort = 16F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns null

    every {
      mappingRepository.findByIsbn(isbn)
    } returns emptyList()

    every {
      productClient.findProductForBook(isbn, any())
    } returns
      KoboProductLookupResult.Found(
        productId = productId,
        seriesId = null,
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(productId)

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId &&
            it.isbn == isbn &&
            it.productId == productId &&
            it.observedKoboSeriesId == null &&
            it.status == KoboProductMappingStatus.FOUND &&
            it.seriesCheckedAt != null &&
            it.seriesCheckedAt == it.checkedAt
        },
      )
    }
  }

  @Test
  fun `same ISBN uses cached found mapping`() {
    val bookId = "book-1"
    val isbn = "9781974753246"
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = productId,
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(productId)

    verify(exactly = 0) {
      productClient.findProductByIsbn(any())
    }
  }

  @Test
  fun `fresh not found mapping does not query Kobo again`() {
    val bookId = "book-1"
    val isbn = "9780000000002"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = null,
        status = KoboProductMappingStatus.NOT_FOUND,
        checkedAt = LocalDateTime.now(),
        lookupPolicy = "worldwide,locale",
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isNull()

    verify(exactly = 0) {
      productClient.findProductByIsbn(any())
    }
  }

  @Test
  fun `ISBN change ignores stale mapping and resolves new ISBN`() {
    val bookId = "book-1"

    val oldIsbn = "9781974701193"
    val newIsbn = "9781974753246"

    val oldProductId = "11111111-1111-1111-1111-111111111111"
    val newProductId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = newIsbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns
      KoboProductMapping(
        bookId = bookId,
        isbn = oldIsbn,
        productId = oldProductId,
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    every {
      mappingRepository.findByIsbn(newIsbn)
    } returns emptyList()

    every {
      productClient.findProductForBook(newIsbn, any())
    } returns KoboProductLookupResult.Found(newProductId)

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(newProductId)

    verify(exactly = 0) {
      productClient.findProductForBook(oldIsbn, any())
    }

    verify(exactly = 1) {
      productClient.findProductForBook(newIsbn, any())
    }

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId &&
            it.isbn == newIsbn &&
            it.productId == newProductId &&
            it.status == KoboProductMappingStatus.FOUND
        },
      )
    }
  }

  @Test
  fun `not found result is persisted`() {
    val bookId = "book-1"
    val isbn = "9780000000002"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns null

    every {
      mappingRepository.findByIsbn(isbn)
    } returns emptyList()

    every {
      productClient.findProductForBook(isbn, any())
    } returns KoboProductLookupResult.NotFound

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isNull()

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId &&
            it.isbn == isbn &&
            it.productId == null &&
            it.status == KoboProductMappingStatus.NOT_FOUND
        },
      )
    }
  }

  @Test
  fun `failed lookup is not persisted`() {
    val bookId = "book-1"
    val isbn = "9781974753246"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns null

    every {
      mappingRepository.findByIsbn(isbn)
    } returns emptyList()

    every {
      productClient.findProductForBook(isbn, any())
    } returns
      KoboProductLookupResult.Failed(
        IllegalStateException("Kobo unavailable"),
      )

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isNull()

    verify(exactly = 0) {
      mappingRepository.save(any())
    }
  }

  @Test
  fun `cached found mapping wins over fresh not found mapping for same ISBN`() {
    val bookId = "book-1"
    val isbn = "9781974753246"
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"

    every {
      bookMetadataRepository.findByIdOrNull(bookId)
    } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = bookId,
        isbn = isbn,
      )

    val notFound =
      KoboProductMapping(
        bookId = bookId,
        isbn = isbn,
        productId = null,
        status = KoboProductMappingStatus.NOT_FOUND,
        checkedAt = LocalDateTime.now(),
      )

    val found =
      KoboProductMapping(
        bookId = "book-2",
        isbn = isbn,
        productId = productId,
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now().minusDays(1),
      )

    every {
      mappingRepository.findByBookId(bookId)
    } returns notFound

    every {
      mappingRepository.findByIsbn(isbn)
    } returns listOf(notFound, found)

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(productId)

    verify(exactly = 0) {
      productClient.findProductByIsbn(any())
    }

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == bookId &&
            it.isbn == isbn &&
            it.productId == productId &&
            it.status == KoboProductMappingStatus.FOUND
        },
      )
    }
  }

  @Test
  fun `temporary website failure cannot overwrite a prior valid identity while ISBN changes`() {
    val bookId = "book-change-failure"
    val previous =
      KoboProductMapping(
        bookId,
        "9781974753246",
        "old-product",
        "old-series",
        KoboProductMappingStatus.FOUND,
        LocalDateTime.now().minusDays(4),
      )
    val changed = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "New edition", number = "1", numberSort = 1F, isbn = changed)
    every { mappingRepository.findByBookId(bookId) } returns previous
    every { mappingRepository.findByIsbn(changed) } returns emptyList()
    every { productClient.findProductForBook(changed, any()) } returns
      KoboProductLookupResult.Failed(IllegalStateException("HTTP 429"))

    assertThat(resolver.resolveProductId(bookId)).isNull()
    assertThat(resolver.refreshKoboIdentity(bookId)).isFalse()
    verify(exactly = 0) { mappingRepository.save(any()) }
    verify(exactly = 0) { productClient.findProductForBook(previous.isbn, any()) }
    // The previous row can remain for inspection but must not be presented as valid for the new ISBN.
    verify(exactly = 0) { mappingRepository.deleteByBookId(bookId) }
  }

  @Test
  fun `changing to a new ISBN with missing series never copies the old series ID`() {
    val bookId = "book-change-series"
    val previous =
      KoboProductMapping(
        bookId,
        "9781974753246",
        "old-product",
        "old-series",
        KoboProductMappingStatus.FOUND,
        LocalDateTime.now().minusDays(4),
      )
    val changed = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "New edition", number = "2", numberSort = 2F, isbn = changed)
    every { mappingRepository.findByBookId(bookId) } returns previous
    every { mappingRepository.findByIsbn(changed) } returns emptyList()
    every { productClient.findProductForBook(changed, any()) } returns KoboProductLookupResult.Found("new-product", null)

    assertThat(resolver.resolveProductId(bookId)).isEqualTo("new-product")
    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.isbn == changed && it.productId == "new-product" && it.observedKoboSeriesId == null &&
            it.status == KoboProductMappingStatus.FOUND
        },
      )
    }
  }

  @Test
  fun `normal resolution discards a completed website result if the ISBN changes in flight`() {
    val bookId = "book-in-flight"
    val requested = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returnsMany
      listOf(
        BookMetadata(bookId = bookId, title = "One", number = "1", numberSort = 1F, isbn = requested),
        BookMetadata(bookId = bookId, title = "Two", number = "2", numberSort = 2F, isbn = "9781974753246"),
      )
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(requested) } returns emptyList()
    every { productClient.findProductForBook(requested, any()) } returns KoboProductLookupResult.Found("stale-product", "stale-series")

    assertThat(resolver.resolveProductId(bookId)).isNull()
    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `failed lookup of an unknown ISBN never creates a negative mapping`() {
    val bookId = "book-incomplete"
    val isbn = "9781974755998"
    every { bookMetadataRepository.findByIdOrNull(bookId) } returns
      BookMetadata(bookId = bookId, title = "Test", number = "1", numberSort = 1F, isbn = isbn)
    every { mappingRepository.findByBookId(bookId) } returns null
    every { mappingRepository.findByIsbn(isbn) } returns emptyList()
    every { productClient.findProductForBook(isbn, any()) } returns
      KoboProductLookupResult.Failed(
        IllegalStateException("unknown HTML or request budget"),
      )

    assertThat(resolver.resolveProductId(bookId)).isNull()
    verify(exactly = 0) { mappingRepository.save(any()) }
    verify(exactly = 1) { productClient.findProductForBook(isbn, any()) }
  }

  @Test
  fun `reverse lookup returns the only valid local book`() {
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"
    val isbn = "9781974753246"
    val mapping =
      KoboProductMapping(
        bookId = "book-1",
        isbn = isbn,
        productId = productId,
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    every { mappingRepository.findByProductId(productId) } returns listOf(mapping)
    every { bookMetadataRepository.findByIdOrNull("book-1") } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = "book-1",
        isbn = isbn,
      )

    assertThat(resolver.resolveBookId(productId)).isEqualTo("book-1")
    verify(exactly = 0) { mappingRepository.deleteByBookId(any()) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
  }

  @Test
  fun `reverse lookup does not choose between two valid books sharing a Kobo ProductId`() {
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"
    val isbn = "9781974753246"
    val mappings =
      listOf("book-a", "book-b").map { bookId ->
        KoboProductMapping(
          bookId = bookId,
          isbn = isbn,
          productId = productId,
          status = KoboProductMappingStatus.FOUND,
          checkedAt = LocalDateTime.now(),
        )
      }

    every { mappingRepository.findByProductId(productId) } returns mappings
    mappings.forEach { mapping ->
      every { bookMetadataRepository.findByIdOrNull(mapping.bookId) } returns
        BookMetadata(
          title = "Test Book",
          number = "1",
          numberSort = 1F,
          bookId = mapping.bookId,
          isbn = isbn,
        )
    }

    assertThat(resolver.resolveBookId(productId)).isNull()
    verify(exactly = 0) { mappingRepository.deleteByBookId(any()) }
    verify(exactly = 0) { productClient.findProductByIsbn(any()) }
  }

  @Test
  fun `reverse lookup discards a stale mapping before checking for ambiguity`() {
    val productId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"
    val isbn = "9781974753246"
    val stale =
      KoboProductMapping(
        bookId = "book-a",
        isbn = isbn,
        productId = productId,
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )
    val valid = stale.copy(bookId = "book-b")

    every { mappingRepository.findByProductId(productId) } returns listOf(stale, valid)
    every { bookMetadataRepository.findByIdOrNull("book-a") } returns
      BookMetadata(
        title = "Changed ISBN",
        number = "1",
        numberSort = 1F,
        bookId = "book-a",
        isbn = "9781974755998",
      )
    every { bookMetadataRepository.findByIdOrNull("book-b") } returns
      BookMetadata(
        title = "Test Book",
        number = "1",
        numberSort = 1F,
        bookId = "book-b",
        isbn = isbn,
      )

    assertThat(resolver.resolveBookId(productId)).isEqualTo("book-b")
    verify(exactly = 1) { mappingRepository.deleteByBookId("book-a") }
    verify(exactly = 0) { mappingRepository.deleteByBookId("book-b") }
  }
}
