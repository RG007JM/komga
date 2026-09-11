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
    } returns KoboProductLookupResult.Found(productId)

    val result = resolver.resolveProductId(bookId)

    assertThat(result).isEqualTo(productId)

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
}
