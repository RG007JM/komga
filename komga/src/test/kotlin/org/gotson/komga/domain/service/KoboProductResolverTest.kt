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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

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
      productClient.findProductByIsbn(isbn)
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
      productClient.findProductByIsbn(isbn)
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

    val oldIsbn = "9781111111111"
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
      productClient.findProductByIsbn(newIsbn)
    } returns KoboProductLookupResult.Found(newProductId)

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(newProductId)

    verify(exactly = 0) {
      productClient.findProductByIsbn(oldIsbn)
    }

    verify(exactly = 1) {
      productClient.findProductByIsbn(newIsbn)
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
      productClient.findProductByIsbn(isbn)
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
      productClient.findProductByIsbn(isbn)
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
