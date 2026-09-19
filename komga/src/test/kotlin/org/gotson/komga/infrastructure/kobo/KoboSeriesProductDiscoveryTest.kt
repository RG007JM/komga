package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gotson.komga.domain.model.BookMetadata
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/** Entirely offline: JSON models a sanitized device-initiated Store response. */
class KoboSeriesProductDiscoveryTest {
  private val metadataRepository = mockk<BookMetadataRepository>()
  private val mappingRepository = mockk<KoboProductMappingRepository>(relaxed = true)
  private val discovery = KoboSeriesProductDiscovery(metadataRepository, mappingRepository)
  private val objectMapper = ObjectMapper()

  private val isbn = "9788834923627"
  private val productId = "b996d901-4a00-4783-8476-8494252d3415"
  private val seriesId = "e99dd828-368e-5b4d-b728-5386dc0c2307"

  @BeforeEach
  fun resetMocks() {
    clearMocks(metadataRepository, mappingRepository)
  }

  private fun upstream(
    isbn: String = this.isbn,
    productId: String = this.productId,
    seriesId: String? = this.seriesId,
  ) = objectMapper.readTree(
    """
    {
      "Items": [
        {"Book": {
          "ISBN": "$isbn",
          "Id": "$productId"
          ${seriesId?.let { ", \"SeriesId\": \"$it\"" }.orEmpty()}
        }}
      ]
    }
    """.trimIndent(),
  )

  private fun local(vararg books: Pair<String, String>) {
    every { metadataRepository.findAllByIds(any()) } returns
      books.map { (bookId, isbn) ->
        BookMetadata(title = "Test", number = "1", numberSort = 1F, bookId = bookId, isbn = isbn)
      }
  }

  @Test
  fun `matches a uniquely identified local book from proxied series response`() {
    local("book-1" to isbn)
    every { mappingRepository.findByBookId("book-1") } returns null
    every { mappingRepository.findByProductId(productId) } returns emptyList()

    discovery.learn(listOf("book-1"), upstream())

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.bookId == "book-1" &&
            it.isbn == isbn &&
            it.productId == productId &&
            it.observedKoboSeriesId == seriesId &&
            it.status == KoboProductMappingStatus.FOUND &&
            it.seriesCheckedAt != null
        },
      )
    }
  }

  @Test
  fun `a missing observed series id does not prevent learning product id`() {
    local("book-1" to isbn)
    every { mappingRepository.findByBookId("book-1") } returns null
    every { mappingRepository.findByProductId(productId) } returns emptyList()

    discovery.learn(listOf("book-1"), upstream(seriesId = null))

    verify(exactly = 1) {
      mappingRepository.save(
        match {
          it.productId == productId && it.observedKoboSeriesId == null && it.seriesCheckedAt == null
        },
      )
    }
  }

  @Test
  fun `existing found product id is not overwritten by conflicting upstream id`() {
    local("book-1" to isbn)
    every { mappingRepository.findByBookId("book-1") } returns
      KoboProductMapping(
        bookId = "book-1",
        isbn = isbn,
        productId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = LocalDateTime.now(),
      )

    discovery.learn(listOf("book-1"), upstream())

    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `duplicate local isbn is ambiguous`() {
    local("book-1" to isbn, "book-2" to isbn)

    discovery.learn(listOf("book-1", "book-2"), upstream())

    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `duplicate upstream isbn is ambiguous`() {
    local("book-1" to isbn)
    val upstream =
      objectMapper.readTree(
        """
        {"Items": [
          {"Book": {"ISBN": "$isbn", "Id": "$productId"}},
          {"Book": {"ISBN": "$isbn", "Id": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"}}
        ]}
        """.trimIndent(),
      )

    discovery.learn(listOf("book-1"), upstream)

    verify(exactly = 0) { mappingRepository.save(any()) }
  }

  @Test
  fun `unmatched isbn is not attached to a different local volume`() {
    local("book-1" to "9781974755998")

    discovery.learn(listOf("book-1"), upstream())

    verify(exactly = 0) { mappingRepository.save(any()) }
  }
}
