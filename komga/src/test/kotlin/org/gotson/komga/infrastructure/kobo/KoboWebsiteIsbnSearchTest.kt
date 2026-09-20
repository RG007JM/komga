package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class KoboWebsiteIsbnSearchTest {
  private val isbn = "9781974702015"
  private val product = "fa183d0f-6794-4e38-b57d-3ebd6cbaeb2c"
  private val series = "708f4ca7-757f-56cb-afa9-39ecd9ebaf1b"
  private val parser = KoboProductPageParser(ObjectMapper())

  private fun page(store: String, bookIsbn: String = isbn, bookProduct: String = product, bookSeries: String? = series): String =
    """
    <div class="item-detail"><div class="books-in-series">
      ${if (bookSeries == null) "" else "<a href='/$store/search?fcsearchfield=Series&amp;seriesId=$bookSeries'>All</a>"}
    </div></div>
    <div class="item-primary-metadata book-primary-metadata" data-track-info='{"productId":"$bookProduct"}'></div>
    <div class="bookitem-secondary-metadata"><ul><li>Book ID: $bookIsbn</li></ul></div>
    <section class="recommendations"><div data-track-info='{"productId":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"}'></div></section>
    """.trimIndent()

  @Test
  fun `all 47 storefronts are unique and worldwide is first`() {
    val stores = KoboWebsiteStorefronts.plan("it-IT")
    assertThat(stores).hasSize(47)
    assertThat(stores.distinct()).hasSize(47)
    assertThat(stores.take(2)).containsExactly("ww/en", "it/it")
    assertThat(KoboWebsiteStorefronts.plan("ja-JP")).containsExactly("ww/en", "jp/ja", "gb/en")
  }

  @Test
  fun `region is parsed only from the BCP47 core and overrides language`() {
    assertThat(KoboWebsiteStorefronts.second("fr-CA")).isEqualTo("ca/en")
    assertThat(KoboWebsiteStorefronts.second("zh-Hant-TW-x-us")).isEqualTo("tw/zh")
    assertThat(KoboWebsiteStorefronts.second("pt-BR")).isEqualTo("br/pt")
    assertThat(KoboWebsiteStorefronts.second("en-u-ca-gregory")).isEqualTo("gb/en")
    assertThat(KoboWebsiteStorefronts.second("CA")).isEqualTo("gb/en") // Catalan is not Canada.
    assertThat(KoboWebsiteStorefronts.second("it-KR")).isEqualTo("gb/en")
    assertThat(KoboWebsiteStorefronts.second(null)).isEqualTo("gb/en")
  }

  @Test
  fun `verified primary product URL callback strips all session query parameters`() {
    val recorded = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ _ ->
      KoboWebsitePage("https://www.kobo.com/ww/en/ebook/correct?sId=private&ssId=other".toHttpUrl(), page("ww/en"))
    }, parser) { observedIsbn, observedProductId, url ->
      assertThat(observedIsbn).isEqualTo(isbn)
      assertThat(observedProductId).isEqualTo(product)
      recorded += url.toString()
    }
    assertThat(lookup.find(isbn, "it")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(recorded).containsExactly("https://www.kobo.com/ww/en/ebook/correct")
  }

  @Test
  fun `search redirect reuses product body in one network call`() {
    val calls = mutableListOf<HttpUrl>()
    val search = KoboWebsiteIsbnSearch({ url ->
      calls += url
      KoboWebsitePage("https://www.kobo.com/ww/en/ebook/the-promised-neverland-vol-2?sId=secret".toHttpUrl(), page("ww/en"))
    }, parser)
    assertThat(search.find(isbn, "it-IT")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(calls).hasSize(1)
    assertThat(calls.single().queryParameter("query")).isEqualTo(isbn)
    assertThat(calls.single().queryParameter("pagenumber")).isEqualTo("1")
  }

  @Test
  fun `language-only Italian locale chooses Italy second without visiting a third storefront`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      when (url.encodedPath) {
        "/ww/en/search" -> KoboWebsitePage(url, "<h2 data-testid='no-result'>No</h2>")
        "/it/it/search" -> KoboWebsitePage("https://www.kobo.com/it/it/ebook/italian-book".toHttpUrl(), page("it/it"))
        else -> error("Unexpected storefront: $url")
      }
    }, parser)
    assertThat(lookup.find(isbn, "it")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(calls).containsExactly("/ww/en/search", "/it/it/search")
  }

  @Test
  fun `worldwide miss tries selected region and never checks other stores after match`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      if (url.encodedPath.startsWith("/ww/")) KoboWebsitePage(url, "<h2 data-testid='no-result'>No</h2>")
      else KoboWebsitePage("https://www.kobo.com/it/it/ebook/italian-book".toHttpUrl(), page("it/it", bookSeries = null))
    }, parser)
    assertThat(lookup.find(isbn, "it-IT")).isEqualTo(KoboProductLookupResult.Found(product, null))
    assertThat(calls).containsExactly("/ww/en/search", "/it/it/search")
  }

  @Test
  fun `an ordinary results page checks the primary book and ignores unrelated recommendation IDs`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      when (url.encodedPath) {
        "/ww/en/search" -> KoboWebsitePage(url, "<main><a href='/ww/en/ebook/wrong'>Wrong</a><a href='/ww/en/ebook/right?sId=secret'>Right</a></main>")
        "/ww/en/ebook/wrong" -> KoboWebsitePage(url, page("ww/en", bookIsbn = "9781974701193"))
        "/ww/en/ebook/right" -> KoboWebsitePage(url, page("ww/en"))
        else -> error("Unexpected fetch $url")
      }
    }, parser)
    assertThat(lookup.find(isbn, "en-GB")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(calls).containsExactly("/ww/en/search", "/ww/en/ebook/wrong", "/ww/en/ebook/right")
  }

  @Test
  fun `unknown result page must never be cached as not found`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      KoboWebsitePage(url, "<main>Unrecognized search layout</main>")
    }, parser)
    assertThat(lookup.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).containsExactly("/ww/en/search", "/jp/ja/search", "/gb/en/search")
  }

  @Test
  fun `an explicit empty result on every Japanese store is a real scoped negative`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      KoboWebsitePage(url, "<h2 data-testid='no-result'>找不到結果</h2>")
    }, parser)
    assertThat(lookup.find(isbn, "ja-JP")).isEqualTo(KoboProductLookupResult.NotFound)
    assertThat(calls).containsExactly("/ww/en/search", "/jp/ja/search", "/gb/en/search")
  }

  @Test
  fun `a cross storefront redirect cannot claim a match in its requested storefront`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      if (url.encodedPath.startsWith("/ww/")) KoboWebsitePage("https://www.kobo.com/ca/en/ebook/book".toHttpUrl(), page("ca/en"))
      else KoboWebsitePage(url, "<h2 data-testid='no-result'>No</h2>")
    }, parser)
    assertThat(lookup.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).containsExactly("/ww/en/search", "/jp/ja/search", "/gb/en/search")
  }

  @Test
  fun `one indeterminate storefront prevents a durable negative even if others explicitly miss`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      if (url.encodedPath == "/ww/en/search") KoboWebsitePage(url, "<main>Unknown layout</main>")
      else KoboWebsitePage(url, "<h2 data-testid='no-result'>No results</h2>")
    }, parser)
    assertThat(lookup.find(isbn, "it")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).hasSize(47)
    assertThat(calls.first()).isEqualTo("/ww/en/search")
    assertThat(calls[1]).isEqualTo("/it/it/search")
  }

  @Test
  fun `forty seven explicit empty storefronts produce a completed negative`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      KoboWebsitePage(url, "<h2 data-testid='no-result'>No results</h2>")
    }, parser)
    assertThat(lookup.find(isbn, "it")).isEqualTo(KoboProductLookupResult.NotFound)
    assertThat(calls).hasSize(47)
    assertThat(calls.distinct()).hasSize(47)
  }

  @Test
  fun `a thrown network exception halts lookup instead of becoming not found`() {
    var calls = 0
    val lookup = KoboWebsiteIsbnSearch({ _ ->
      calls++
      throw java.io.IOException("offline")
    }, parser)
    assertThatThrownBy { lookup.find(isbn, "it") }
      .isInstanceOf(java.io.IOException::class.java)
      .hasMessage("offline")
    assertThat(calls).isEqualTo(1)
  }

  @Test
  fun `opaque Japanese Kobo Book ID is rejected before any website request`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      error("Invalid ISBN must not trigger any website request")
    }, parser)
    assertThat(lookup.find("4972000027092", "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).isEmpty()
  }

  @Test
  fun `Japanese opaque product slug is allowed when starting from a valid ISBN`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      if (url.encodedPath.startsWith("/ww/")) KoboWebsitePage(url, "<h2 data-testid='no-result'>No</h2>")
      else KoboWebsitePage("https://www.kobo.com/jp/ja/ebook/voy_BUz4-D2yFrMo73mJsQ?sId=secret".toHttpUrl(), page("jp/ja"))
    }, parser)
    assertThat(lookup.find(isbn, "ja-JP")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(calls).containsExactly("/ww/en/search", "/jp/ja/search")
  }

  @Test
  fun `ISBN10 is converted before a website search`() {
    val calls = mutableListOf<HttpUrl>()
    val normalizedIsbn = "9780306406157"
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url
      KoboWebsitePage("https://www.kobo.com/ww/en/ebook/valid-book".toHttpUrl(), page("ww/en", bookIsbn = normalizedIsbn))
    }, parser)
    assertThat(lookup.find("0-306-40615-2", "en")).isEqualTo(KoboProductLookupResult.Found(product, series))
    assertThat(calls).hasSize(1)
    assertThat(calls.single().queryParameter("query")).isEqualTo(normalizedIsbn)
  }

  @Test
  fun `invalid checksum and identifier-like input never request Kobo or produce NotFound`() {
    val calls = mutableListOf<HttpUrl>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url
      error("Invalid ISBN must not trigger any website request")
    }, parser)
    listOf("9781974702014", "9780000000001", "978197470201", "97819747020150", "4972000027092", "9781974702015extra", "").forEach { invalid ->
      assertThat(lookup.find(invalid, "it-IT")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    }
    assertThat(calls).isEmpty()
  }

  @Test
  fun `matching ISBN with missing primary Product ID is inconclusive`() {
    val lookup = KoboWebsiteIsbnSearch({ url ->
      if (url.encodedPath.endsWith("/search")) {
        KoboWebsitePage(url, "<main><a href='/ww/en/ebook/missing'>Book</a></main>")
      } else {
        KoboWebsitePage(url, "<li class='flex flex-row'>ISBN: $isbn</li>")
      }
    }, parser)
    assertThat(lookup.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

}
