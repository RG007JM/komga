package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.gotson.komga.domain.model.SeriesMetadata
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.SeriesMetadataRepository
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.UUID

/** Temporary opt-in live test of the NORMAL, non-Japanese production ISBN lookup.
 * Uses the real KoboProductClient and real HTTP transport; only the local book/series
 * repositories are mocked. Does not write to the database or use device credentials.
 */
@Tag("live")
class KoboStandardLiveLookupTest {
  @Test
  fun `normal ISBN resolves through the production non Japanese lookup`() {
    assumeTrue(System.getenv("KOMGA_KOBO_LIVE_TEST") == "1", "Set KOMGA_KOBO_LIVE_TEST=1 to enable live HTTP")

    val isbn = "9781421581514"
    val bookId = "standard-live-book"
    val books = mockk<BookRepository>()
    val series = mockk<SeriesMetadataRepository>()
    every { books.getSeriesIdOrNull(bookId) } returns "standard-live-series"
    every { series.findByIdOrNull("standard-live-series") } returns
      SeriesMetadata(title = "Live standard lookup", language = "en-US")

    // Identical production entry point to the Japanese live test; no test-only HTTP path.
    val client = KoboProductClient(ObjectMapper(), books, series, KoboLookupDiagnostics())
    val policy = client.lookupPolicy(bookId)
    println("KOBO LIVE NORMAL: ISBN=$isbn locale=${client.localeForBook(bookId)} policy=$policy")
    check(policy.split(',').firstOrNull() == "ww/en") {
      "Non-Japanese lookup must start at ww/en; actual policy=$policy"
    }

    when (val result = client.findProductForBook(isbn, bookId)) {
      is KoboProductLookupResult.Found -> {
        UUID.fromString(result.productId) // Require the verified Kobo ProductId, not an ebook URL slug.
        println("KOBO LIVE NORMAL FOUND: isbn=$isbn productId=${result.productId} seriesId=${result.seriesId}")
      }
      KoboProductLookupResult.NotFound ->
        throw AssertionError("Production Komga normal ISBN lookup returned NOT_FOUND for $isbn (policy=$policy)")
      is KoboProductLookupResult.Failed ->
        throw AssertionError(
          "Production Komga normal ISBN lookup INCONCLUSIVE for $isbn (policy=$policy): " +
            "${result.cause.javaClass.simpleName}: ${result.cause.message.orEmpty().take(400)}",
          result.cause,
        )
    }
  }
}
