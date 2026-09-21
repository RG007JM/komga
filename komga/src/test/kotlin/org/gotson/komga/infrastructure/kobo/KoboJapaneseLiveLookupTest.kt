package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.gotson.komga.domain.model.SeriesMetadata
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.SeriesMetadataRepository
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.reflect.InvocationTargetException
import java.util.UUID

/** Temporary opt-in LIVE test. Uses the real KoboProductClient, HTTP transport, and parsers.
 * Only the local repositories are mocked to give the test book a ja-JP series language.
 * No database writes, persistent cookies, or StoreAPI/device credentials.
 * Run with KOMGA_KOBO_LIVE_TEST=1, and remove this file after diagnosis.
 */
@Tag("live")
class KoboJapaneseLiveLookupTest {
  @Test
  fun `print ISBN resolves through the production Japanese lookup`() {
    assumeTrue(System.getenv("KOMGA_KOBO_LIVE_TEST") == "1", "Set KOMGA_KOBO_LIVE_TEST=1 to enable live HTTP")

    val printIsbn = "9784088911793"
    val ebookId = "4970100807748"
    val bookId = "japanese-live-book"
    val books = mockk<BookRepository>()
    val series = mockk<SeriesMetadataRepository>()
    every { books.getSeriesIdOrNull(bookId) } returns "japanese-live-series"
    every { series.findByIdOrNull("japanese-live-series") } returns
      SeriesMetadata(title = "Live Japanese lookup", language = "ja-JP")

    // IMPORTANT: This is the real production client, not a reimplementation of its requests.
    val client = KoboProductClient(ObjectMapper(), books, series, KoboLookupDiagnostics())
    val policy = client.lookupPolicy(bookId)
    println("KOBO LIVE: ISBN=$printIsbn locale=${client.localeForBook(bookId)} policy=$policy")
    check(policy == "ww/en,jp/ja") {
      "Japanese route must be ww/en -> jp/ja -> Rakuten (not GB); actual storefront policy=$policy"
    }

    val result = client.findProductForBook(printIsbn, bookId)
    when (result) {
      is KoboProductLookupResult.Found -> {
        UUID.fromString(result.productId) // Assert a verified Kobo ProductId, not the URL slug.
        check(result.productId == "d3656ecd-c898-4b0e-a9a6-1c35ce88b461") {
          "Unexpected Kobo ProductId for Rakuten ebook $ebookId: ${result.productId}"
        }
        println("KOBO LIVE FOUND: printIsbn=$printIsbn productId=${result.productId} seriesId=${result.seriesId}")
      }
      else -> {
        val outcome = describe(result)
        val diagnostic =
          if (result is KoboProductLookupResult.Failed && result.cause is KoboWebsiteBlockedException) {
            "Additional probes skipped after HTTP 403/429."
          } else {
            // Only if the real end-to-end lookup fails: use the SAME private production getPage()
            // transport to isolate whether the bridge or primary Kobo ebook-page parser failed.
            diagnoseThroughProductionTransport(client, printIsbn, ebookId)
          }
        throw AssertionError("Production Komga Japanese ISBN lookup FAILED: $outcome. $diagnostic")
      }
    }
  }

  private fun diagnoseThroughProductionTransport(
    client: KoboProductClient,
    printIsbn: String,
    ebookId: String,
  ): String =
    runCatching {
      val getPage = KoboProductClient::class.java.getDeclaredMethod("getPage", HttpUrl::class.java)
      getPage.isAccessible = true
      val fetch: (HttpUrl) -> KoboWebsitePage = { url ->
        try {
          getPage.invoke(client, url) as KoboWebsitePage
        } catch (e: InvocationTargetException) {
          throw e.targetException
        }
      }
      val parser = KoboProductPageParser(ObjectMapper())
      val bridge = KoboJapaneseEditionBridge(fetch, parser)
      val bridgeResult = bridge.find(printIsbn)
      val bridgeMessage = "Direct production Rakuten bridge=${describe(bridgeResult)}"
      if (bridgeResult is KoboProductLookupResult.Failed && bridgeResult.cause is KoboWebsiteBlockedException) {
        bridgeMessage
      } else {
        val productMessage =
          runCatching {
            val target = "https://www.kobo.com/jp/ja/ebook/JUdlwTLVcjKPoyLWRNTfcg".toHttpUrl()
            val page = fetch(target)
            val identity = parser.parse(page.html, page.url.toString(), ebookId)
            "Known Kobo JP ebook page primary identity=${identity ?: "UNVERIFIED (parser returned null)"}"
          }.getOrElse { "Known Kobo JP ebook page probe failed: ${it.javaClass.simpleName}: ${it.message.orEmpty().take(180)}" }
        "$bridgeMessage; $productMessage"
      }
    }.getOrElse { "Production-transport diagnostic failed: ${it.javaClass.simpleName}: ${it.message.orEmpty().take(180)}" }

  private fun describe(result: KoboProductLookupResult): String =
    when (result) {
      is KoboProductLookupResult.Found -> "FOUND productId=${result.productId} seriesId=${result.seriesId}"
      KoboProductLookupResult.NotFound -> "NOT_FOUND"
      is KoboProductLookupResult.Failed ->
        "INCONCLUSIVE ${result.cause.javaClass.simpleName}: ${result.cause.message.orEmpty().take(320)}"
    }
}
