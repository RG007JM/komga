package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.SeriesMetadata
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.SeriesMetadataRepository
import org.junit.jupiter.api.Test

class KoboProductClientValidationTest {
  @Test
  fun `book specific locale is preserved with duplicate ISBNs and formatting differences`() {
    val books = mockk<BookRepository>()
    val series = mockk<SeriesMetadataRepository>()
    val client = KoboProductClient(ObjectMapper(), books, series, KoboLookupDiagnostics())
    every { books.getSeriesIdOrNull("japanese-book") } returns "japanese-series"
    every { books.getSeriesIdOrNull("italian-book") } returns "italian-series"
    every { series.findByIdOrNull("japanese-series") } returns SeriesMetadata(title = "Japanese", language = "ja-JP")
    every { series.findByIdOrNull("italian-series") } returns SeriesMetadata(title = "Italian", language = "it")

    assertThat(client.localeForBook("japanese-book")).isEqualTo("ja-JP")
    assertThat(client.localeForBook("italian-book")).isEqualTo("it")
    assertThat(client.canShareMapping("japanese-book", "italian-book")).isFalse()
  }

  @Test
  fun `direct client entry point rejects invalid ISBN without querying repositories or network`() {
    val books = mockk<BookRepository>()
    val series = mockk<SeriesMetadataRepository>()
    val client = KoboProductClient(ObjectMapper(), books, series, KoboLookupDiagnostics())

    listOf("4972000027092", "9781974702014", "9780000000001", "").forEach { value ->
      assertThat(client.findProductByIsbn(value)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    }
    verify(exactly = 0) { books.getSeriesIdOrNull(any()) }
    verify(exactly = 0) { series.findByIdOrNull(any()) }
  }
}
