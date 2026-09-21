package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class KoboDeviceSeriesObserverTest {
  private val mapper = ObjectMapper()
  private val mappings = mockk<KoboProductMappingRepository>(relaxed = true)
  private val metadata = mockk<BookMetadataRepository>()
  private val books = mockk<BookRepository>()
  private val seriesIds = mockk<KoboSeriesIdResolver>()
  private val observer = KoboDeviceSeriesObserver(mappings, metadata, books, seriesIds)

  private val productId = "d3656ecd-c898-4b0e-a9a6-1c35ce88b461"
  private val seriesId = "adc9b50c-41c3-5bb9-9cbe-9b4ebb12be7d"
  private val isbn = "9784088911793"
  private val current =
    KoboProductMapping(
      bookId = "book-1",
      isbn = isbn,
      productId = productId,
      status = KoboProductMappingStatus.FOUND,
      checkedAt = LocalDateTime.of(2026, 9, 21, 1, 0),
    )

  @BeforeEach
  fun setup() {
    every { mappings.findByBookId("book-1") } returns current
    every { mappings.findByProductId(productId) } returns listOf(current)
    every { metadata.findByIdOrNull("book-1") } returns
      BookMetadata(title = "Test", number = "1", numberSort = 1F, bookId = "book-1", isbn = isbn)
    every { books.getSeriesIdOrNull("book-1") } returns "local-series"
    every { seriesIds.resolveSeriesId("local-series") } returns seriesId
  }

  @Test
  fun `device BookDetails learns series from matching mapped Kobo ProductId`() {
    observer.observeBookDetails(
      productId,
      mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId","ISBN":"4970100807748"}"""),
      localBookId = "book-1",
    )
    verify(exactly = 1) {
      mappings.save(match { it.bookId == "book-1" && it.isbn == isbn && it.productId == productId && it.observedKoboSeriesId == seriesId && it.seriesCheckedAt != null })
    }
    verify(exactly = 1) { seriesIds.resolveSeriesId("local-series") }
  }

  @Test
  fun `BookDetails does not update a SeriesId already found during ISBN lookup`() {
    val existing = current.copy(observedKoboSeriesId = seriesId)
    every { mappings.findByBookId("book-1") } returns existing
    observer.observeBookDetails(
      productId,
      mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""),
      localBookId = "book-1",
    )
    verify(exactly = 0) { mappings.save(any()) }
    verify(exactly = 0) { seriesIds.resolveSeriesId(any()) }
  }

  @Test
  fun `BookDetails cannot overwrite a previously verified SeriesId with a conflicting one`() {
    val previous = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val existing = current.copy(observedKoboSeriesId = previous)
    every { mappings.findByBookId("book-1") } returns existing
    observer.observeBookDetails(
      productId,
      mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""),
      localBookId = "book-1",
    )
    verify(exactly = 0) { mappings.save(any()) }
    verify(exactly = 0) { seriesIds.resolveSeriesId(any()) }
  }

  @Test
  fun `device series response cannot overwrite an existing verified SeriesId`() {
    val previous = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val existing = current.copy(observedKoboSeriesId = previous)
    every { mappings.findByProductId(productId) } returns listOf(existing)
    observer.observeSeriesResponse(
      seriesId,
      mapper.readTree("""{"Items":[{"Book":{"Id":"$productId","SeriesId":"$seriesId"}}]}"""),
    )
    verify(exactly = 0) { mappings.save(any()) }
    verify(exactly = 0) { seriesIds.resolveSeriesId(any()) }
  }

  @Test
  fun `device BookDetails requested by Kobo UUID also learns current local mapping`() {
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""))
    verify(exactly = 1) { mappings.save(match { it.observedKoboSeriesId == seriesId }) }
  }

  @Test
  fun `device BookDetails cannot borrow another ProductId or arbitrary SeriesId`() {
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa","SeriesId":"$seriesId"}"""))
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"$productId","SeriesId":"not-a-uuid"}"""))
    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `device series endpoint learns mapped member from Kobo UUID without Komga series route`() {
    val response = mapper.readTree("""{"Items":[{"Book":{"Id":"$productId","SeriesId":"$seriesId"}}]}""")
    observer.observeSeriesResponse(seriesId, response)
    verify(exactly = 1) { mappings.save(match { it.observedKoboSeriesId == seriesId }) }
  }

  @Test
  fun `device series endpoint may use requested UUID when item omits SeriesId`() {
    val response = mapper.readTree("""{"Items":[{"Book":{"Id":"$productId"}}]}""")
    observer.observeSeriesResponse(seriesId, response)
    verify(exactly = 1) { mappings.save(match { it.observedKoboSeriesId == seriesId }) }
  }

  @Test
  fun `device series endpoint rejects conflicting member SeriesId and duplicates`() {
    val other = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    observer.observeSeriesResponse(seriesId, mapper.readTree("""{"Items":[{"Book":{"Id":"$productId","SeriesId":"$other"}}]}"""))
    observer.observeSeriesResponse(seriesId, mapper.readTree("""{"Items":[{"Book":{"Id":"$productId"}},{"Book":{"Id":"$productId"}}]}"""))
    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `cannot update an inaccessible or stale local identity`() {
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""), canObserve = { false })
    every { metadata.findByIdOrNull("book-1") } returns
      BookMetadata(title = "Test", number = "1", numberSort = 1F, bookId = "book-1", isbn = "9781421581514")
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""))
    verify(exactly = 0) { mappings.save(any()) }
  }

  @Test
  fun `ambiguous ProductId mapping cannot attach a SeriesId`() {
    every { mappings.findByProductId(productId) } returns listOf(current, current.copy(bookId = "book-2"))
    every { metadata.findByIdOrNull("book-2") } returns
      BookMetadata(title = "Other", number = "2", numberSort = 2F, bookId = "book-2", isbn = isbn)
    observer.observeBookDetails(productId, mapper.readTree("""{"Id":"$productId","SeriesId":"$seriesId"}"""))
    verify(exactly = 0) { mappings.save(any()) }
  }
}
