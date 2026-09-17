// KOBO_OUTBOUND_ID_GUARD_V1
package org.gotson.komga.infrastructure.kobo

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.ReadListRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.net.URI

class KoboOutboundRequestGuardTest {
  private val bookRepository = mockk<BookRepository>()
  private val seriesRepository = mockk<SeriesRepository>()
  private val readListRepository = mockk<ReadListRepository>()

  private val guard =
    KoboOutboundRequestGuard(
      bookRepository = bookRepository,
      seriesRepository = seriesRepository,
      readListRepository = readListRepository,
    )

  @BeforeEach
  fun setUp() {
    clearMocks(
      bookRepository,
      seriesRepository,
      readListRepository,
    )

    every { bookRepository.findByIdOrNull(any()) } returns null
    every { seriesRepository.findByIdOrNull(any()) } returns null
    every { readListRepository.findByIdOrNull(any()) } returns null
  }

  @Test
  fun `blocks untranslated Komga book ID in final Store path`() {
    val bookId = "0RDTATJ81HV3T"
    every { bookRepository.findByIdOrNull(bookId) } returns mockk()

    assertThatThrownBy {
      guard.validate(
        URI(
          "https://storeapi.kobo.com/v1/products/" +
            "$bookId/reviews",
        ),
      )
    }.isInstanceOfSatisfying(ResponseStatusException::class.java) { exception ->
      assertThat(exception.statusCode)
        .isEqualTo(HttpStatus.BAD_REQUEST)
    }
  }

  @Test
  fun `blocks untranslated Komga series ID in final Store path`() {
    val seriesId = "0RDTATJHXHZRM"
    every { seriesRepository.findByIdOrNull(seriesId) } returns mockk()

    assertThatThrownBy {
      guard.validate(
        URI(
          "https://storeapi.kobo.com/v1/products/books/series/" +
            "$seriesId?ExcludeOwned=false&PageSize=100",
        ),
      )
    }.isInstanceOfSatisfying(ResponseStatusException::class.java) { exception ->
      assertThat(exception.statusCode)
        .isEqualTo(HttpStatus.BAD_REQUEST)
    }
  }

  @Test
  fun `blocks untranslated Komga read-list ID in final Store path`() {
    val readListId = "0RDTATKG1HM0V"
    every { readListRepository.findByIdOrNull(readListId) } returns mockk()

    assertThatThrownBy {
      guard.validate(
        URI(
          "https://storeapi.kobo.com/v1/library/tags/$readListId",
        ),
      )
    }.isInstanceOfSatisfying(ResponseStatusException::class.java) { exception ->
      assertThat(exception.statusCode)
        .isEqualTo(HttpStatus.BAD_REQUEST)
    }
  }

  @Test
  fun `blocks untranslated Komga IDs in final Store query`() {
    val bookId = "0RDTATKHXHPS9"
    every { bookRepository.findByIdOrNull(bookId) } returns mockk()

    assertThatThrownBy {
      guard.validate(
        URI(
          "https://storeapi.kobo.com/v1/user/reviews?" +
            "ProductIds=$bookId",
        ),
      )
    }.isInstanceOfSatisfying(ResponseStatusException::class.java) { exception ->
      assertThat(exception.statusCode)
        .isEqualTo(HttpStatus.BAD_REQUEST)
    }
  }

  @Test
  fun `blocks untranslated Komga ID in final Store request body`() {
    val bookId = "0RDTATKHXHPS9"
    every { bookRepository.findByIdOrNull(bookId) } returns mockk()

    val body =
      """
      {
        "Items": [
          {
            "RevisionId": "$bookId",
            "Type": "ProductRevisionTagItem"
          }
        ]
      }
      """.trimIndent().toByteArray()

    assertThatThrownBy {
      guard.validate(
        uri = URI("https://storeapi.kobo.com/v1/library/tags"),
        body = body,
      )
    }.isInstanceOfSatisfying(ResponseStatusException::class.java) { exception ->
      assertThat(exception.statusCode)
        .isEqualTo(HttpStatus.BAD_REQUEST)
    }
  }

  @Test
  fun `allows translated Kobo UUID in final Store path and query`() {
    assertThatCode {
      guard.validate(
        URI(
          "https://storeapi.kobo.com/v1/products/" +
            "bda21916-7a11-464b-be46-90c61f67e519/reviews?" +
            "ProductIds=bda21916-7a11-464b-be46-90c61f67e519",
        ),
      )
    }.doesNotThrowAnyException()
  }

  @Test
  fun `allows non-Komga TSID-shaped values such as ISBN`() {
    val isbn = "9781421588759"

    assertThatCode {
      guard.validate(
        URI("https://storeapi.kobo.com/v1/products?q=$isbn"),
      )
    }.doesNotThrowAnyException()
  }

  @Test
  fun `does not mistake device serial or UUID body values for Komga IDs`() {
    val body =
      """
      {
        "SerialNumber": "N428490202644",
        "TestKey": "0eb439ae-6c61-4213-8498-0becd8406669"
      }
      """.trimIndent().toByteArray()

    assertThatCode {
      guard.validate(
        uri = URI("https://storeapi.kobo.com/v1/analytics/gettests"),
        body = body,
      )
    }.doesNotThrowAnyException()
  }
}
