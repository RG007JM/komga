package org.gotson.komga.infrastructure.kobo

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.BookMetadata
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.domain.persistence.KoboSeriesMappingRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KoboSeriesIdResolverTest {
  private val bookRepository = mockk<BookRepository>()
  private val bookMetadataRepository = mockk<BookMetadataRepository>()
  private val productMappingRepository = mockk<KoboProductMappingRepository>()
  private val seriesMappingRepository = mockk<KoboSeriesMappingRepository>(relaxed = true)

  private val resolver =
    KoboSeriesIdResolver(
      bookRepository = bookRepository,
      bookMetadataRepository = bookMetadataRepository,
      koboProductMappingRepository = productMappingRepository,
      koboSeriesMappingRepository = seriesMappingRepository,
    )

  @BeforeEach
  fun resetMocks() {
    clearMocks(
      bookRepository,
      bookMetadataRepository,
      productMappingRepository,
      seriesMappingRepository,
    )
  }

  @Test
  fun `strict majority of non-null observations becomes effective series id`() {
    val seriesId = "komga-series"
    val koboA = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val koboB = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"

    prepareSeries(
      seriesId = seriesId,
      observations =
        listOf(
          observation("book-1", koboA),
          observation("book-2", koboA),
          observation("book-3", null),
          observation("book-4", koboB),
        ),
    )

    every {
      seriesMappingRepository.findKoboSeriesId(seriesId)
    } returns null

    val result = resolver.resolveSeriesId(seriesId)

    assertThat(result).isEqualTo(koboA)

    verify(exactly = 1) {
      seriesMappingRepository.upsert(
        seriesId = seriesId,
        koboSeriesId = koboA,
      )
    }
  }

  @Test
  fun `null observations abstain from majority vote`() {
    val seriesId = "komga-series"
    val koboA = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"

    prepareSeries(
      seriesId = seriesId,
      observations =
        listOf(
          observation("book-1", koboA),
          observation("book-2", null),
          observation("book-3", null),
        ),
    )

    every {
      seriesMappingRepository.findKoboSeriesId(seriesId)
    } returns null

    assertThat(
      resolver.resolveSeriesId(seriesId),
    ).isEqualTo(koboA)
  }

  @Test
  fun `tie keeps existing effective series id`() {
    val seriesId = "komga-series"
    val existing = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val other = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"

    prepareSeries(
      seriesId = seriesId,
      observations =
        listOf(
          observation("book-1", existing),
          observation("book-2", other),
        ),
    )

    every {
      seriesMappingRepository.findKoboSeriesId(seriesId)
    } returns existing

    assertThat(
      resolver.resolveSeriesId(seriesId),
    ).isEqualTo(existing)

    verify(exactly = 0) {
      seriesMappingRepository.upsert(any(), any())
    }
  }

  @Test
  fun `new strict majority replaces existing effective series id`() {
    val seriesId = "komga-series"
    val old = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val replacement = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"

    prepareSeries(
      seriesId = seriesId,
      observations =
        listOf(
          observation("book-1", replacement),
          observation("book-2", replacement),
          observation("book-3", old),
        ),
    )

    every {
      seriesMappingRepository.findKoboSeriesId(seriesId)
    } returns old

    assertThat(
      resolver.resolveSeriesId(seriesId),
    ).isEqualTo(replacement)

    verify(exactly = 1) {
      seriesMappingRepository.upsert(
        seriesId = seriesId,
        koboSeriesId = replacement,
      )
    }
  }

  @Test
  fun `stale or invalid ISBN observations do not vote`() {
    val seriesId = "komga-series"
    val validSeriesId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val staleSeriesId = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"

    val bookIds = listOf("book-1", "book-2", "book-3")

    every {
      bookRepository.findAllIdsBySeriesId(seriesId)
    } returns bookIds

    every {
      bookMetadataRepository.findAllByIds(bookIds)
    } returns
      listOf(
        metadata("book-1", "9781974753246"),
        metadata("book-2", "9780000000000"),
        metadata("book-3", "not-an-isbn"),
      )

    every {
      productMappingRepository.findByBookIds(bookIds)
    } returns
      listOf(
        observation("book-1", validSeriesId, isbn = "9781974753246"),
        observation("book-2", staleSeriesId, isbn = "9781111111111"),
        observation("book-3", staleSeriesId, isbn = "9782222222222"),
      )

    every {
      seriesMappingRepository.findKoboSeriesId(seriesId)
    } returns null

    assertThat(
      resolver.resolveSeriesId(seriesId),
    ).isEqualTo(validSeriesId)
  }

  private fun prepareSeries(
    seriesId: String,
    observations: List<KoboProductMapping>,
  ) {
    val bookIds = observations.map { it.bookId }

    every {
      bookRepository.findAllIdsBySeriesId(seriesId)
    } returns bookIds

    every {
      bookMetadataRepository.findAllByIds(bookIds)
    } returns
      observations.map {
        metadata(
          bookId = it.bookId,
          isbn = it.isbn,
        )
      }

    every {
      productMappingRepository.findByBookIds(bookIds)
    } returns observations
  }

  private fun metadata(
    bookId: String,
    isbn: String,
  ) = BookMetadata(
    title = bookId,
    number = "1",
    numberSort = 1F,
    bookId = bookId,
    isbn = isbn,
  )

  private fun observation(
    bookId: String,
    seriesId: String?,
    isbn: String = "9781974753246",
  ) = KoboProductMapping(
    bookId = bookId,
    isbn = isbn,
    productId = "product-$bookId",
    observedKoboSeriesId = seriesId,
    status = KoboProductMappingStatus.FOUND,
    checkedAt = LocalDateTime.now(),
    seriesCheckedAt = LocalDateTime.now(),
  )
}
