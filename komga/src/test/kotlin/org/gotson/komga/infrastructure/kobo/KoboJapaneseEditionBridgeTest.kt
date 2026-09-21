package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** All network responses are synthetic: no Rakuten or Kobo requests leave the test process. */
class KoboJapaneseEditionBridgeTest {
  private val isbn = "9784088917542"
  private val ebookId = "4972000032980" // Kobo Book ID, not a valid ISBN.
  private val productId = "dc45ebc9-c9fe-4cce-8c0d-5d2c14c5d188"
  private val seriesId = "16966b5c-102e-5f22-80e9-8559ef25a1e9"
  private val printUrl = "https://books.rakuten.co.jp/rb/16594407/"
  private val ebookUrl = "https://books.rakuten.co.jp/rk/f947c5a0ffeb32f3ae80b2214b14922a/"
  private val koboUrl = "https://www.kobo.com/jp/ja/ebook/voy_BUz4-D2yFrMo73mJsQ"
  private val parser = KoboProductPageParser(ObjectMapper())

  private fun searchCard(title: String = "かぐや様を語りたい 4") =
    """
    <section class='result-card'>
      <a href='$printUrl'>$title</a>
      <div>電子書籍版 <a href='$ebookUrl?tracking=secret'>電子書籍版</a></div>
    </section>
    """.trimIndent()

  private fun rakutenEbook(
    title: String = "かぐや様を語りたい 4 [電子書籍版]",
    id: String = ebookId,
  ) = """
    <html><head><meta property='books:isbn' content='$id'></head><body>
      <h1>$title</h1><div id='itemDetail'>商品番号： $id</div>
      <section class='recommendations'><p>商品番号： 4340008630131</p></section>
    </body></html>
    """.trimIndent()

  private fun koboEbook(
    id: String = ebookId,
    product: String = productId,
    series: String? = seriesId,
  ) = """
    <html><ul class='bookitem-secondary-metadata'><li>Book ID: $id</li></ul>
      <input type='hidden' id='ratItemId' name='rat' value='$product'>
      ${if (series == null) "" else "<div class='books-in-series'><a href='/jp/ja/search?seriesId=$series'>Series</a></div>"}
      <section class='recommendations'><input value='aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'></section>
    </html>
    """.trimIndent()

  private fun page(
    url: String,
    html: String,
  ) = KoboWebsitePage(url.toHttpUrl(), html)

  private fun empty(url: HttpUrl) = KoboWebsitePage(url, "<h2 data-testid='no-result'>No results</h2>")

  @Test
  fun `Japanese ISBN miss bridges a uniquely paired Rakuten edition and verifies opaque ID only in Japan`() {
    val requests = mutableListOf<HttpUrl>()
    val verified = mutableListOf<List<String>>()
    val fetch: (HttpUrl) -> KoboWebsitePage = { url ->
      requests += url
      when {
        (url.encodedPath == "/ww/en/search") ||
          (url.encodedPath == "/jp/ja/search" && url.queryParameter("query") == isbn) -> empty(url)

        url.host == "books.rakuten.co.jp" && url.encodedPath == "/search" ->
          page(url.toString(), searchCard())

        url.encodedPath.startsWith("/rk/") ->
          page(ebookUrl, rakutenEbook())

        url.encodedPath == "/jp/ja/search" && url.queryParameter("query") == ebookId ->
          page("$koboUrl?sId=secret", koboEbook())

        else -> error("Unexpected request $url")
      }
    }
    val bridge =
      KoboJapaneseEditionBridge(fetch, parser) { print, digital, product, url ->
        verified += listOf(print, digital, product, url.toString())
      }
    val search = KoboWebsiteIsbnSearch(fetch, parser, afterJapaneseMiss = bridge::find)
    assertThat(search.find(isbn, "ja-JP")).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(requests.map { it.encodedPath }).containsExactly(
      "/ww/en/search",
      "/jp/ja/search",
      "/search",
      "/rk/f947c5a0ffeb32f3ae80b2214b14922a/",
      "/jp/ja/search",
    )
    assertThat(requests.last().queryParameter("query")).isEqualTo(ebookId)
    assertThat(verified).containsExactly(listOf(isbn, ebookId, productId, koboUrl))
    assertThat(requests.none { it.encodedPath.startsWith("/gb/") }).isTrue()
  }

  @Test
  fun `worldwide or Japanese verified ISBN short circuits Rakuten entirely`() {
    for (store in listOf("ww/en", "jp/ja")) {
      val requests = mutableListOf<String>()
      val search =
        KoboWebsiteIsbnSearch({ url ->
          requests += url.encodedPath
          if (url.encodedPath == "/ww/en/search" && store == "jp/ja")
            empty(url)
          else
            page(
              "https://www.kobo.com/$store/ebook/existing",
              """
              <div class='bookitem-secondary-metadata'><li>Book ID: $isbn</li></div>
              <div class='item-primary-metadata book-primary-metadata' data-track-info='{"productId":"$productId"}'></div>
              """.trimIndent(),
            )
        }, parser, afterJapaneseMiss = { error("Rakuten must not be queried for a verified direct ISBN") })
      assertThat(search.find(isbn, "ja")).isEqualTo(KoboProductLookupResult.Found(productId, null))
      assertThat(requests).isEqualTo(if (store == "ww/en") listOf("/ww/en/search") else listOf("/ww/en/search", "/jp/ja/search"))
    }
  }

  @Test
  fun `inconclusive Japanese search invokes Rakuten bridge without caching a negative`() {
    var bridgeCalls = 0
    val search =
      KoboWebsiteIsbnSearch({ url ->
        if (url.encodedPath == "/jp/ja/search")
          KoboWebsitePage(url, "<main>Unknown search layout</main>")
        else
          empty(url)
      }, parser, afterJapaneseMiss = {
        bridgeCalls++
        KoboProductLookupResult.NotFound
      })
    assertThat(search.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(bridgeCalls).isEqualTo(1)
  }

  @Test
  fun `ambiguous Rakuten pairs and unmatched editions cannot create a Product ID or NOT_FOUND`() {
    val secondPrint = "https://books.rakuten.co.jp/rb/999999/"
    val secondEbook = "https://books.rakuten.co.jp/rk/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/"
    val pages = mutableListOf<String>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        pages += url.encodedPath
        when {
          url.encodedPath == "/search" ->
            page(
              url.toString(),
              searchCard() + searchCard().replace(printUrl, secondPrint).replace(ebookUrl, secondEbook),
            )
          url.encodedPath.startsWith("/rb/") -> page(url.toString(), "<h1>かぐや様を語りたい 4</h1><div id='itemDetail'>ISBN： $isbn</div>")
          url.encodedPath.startsWith("/rk/") -> page(url.toString(), rakutenEbook(id = if (url.toString() == ebookUrl) ebookId else "4340008630131"))
          else -> error("Unexpected Kobo verification request")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(pages.none { it.startsWith("/jp/") }).isTrue()
  }

  @Test
  fun `a different volume is rejected even if Rakuten provides a book ID`() {
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        when {
          url.encodedPath == "/search" -> page(url.toString(), searchCard())
          url.encodedPath.startsWith("/rk/") -> page(ebookUrl, rakutenEbook("かぐや様を語りたい 3"))
          url.encodedPath.startsWith("/rb/") -> page(printUrl, "<h1>かぐや様を語りたい 4</h1><div id='itemDetail'>ISBN： $isbn</div>")
          else -> error("Unexpected Kobo request")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `failed Rakuten request does not become a durable negative result`() {
    val search =
      KoboWebsiteIsbnSearch({ url ->
        when (url.host) {
          "www.kobo.com" -> empty(url)
          "books.rakuten.co.jp" -> throw IllegalStateException("HTTP 429 blocked")
          else -> error("Unexpected host")
        }
      }, parser, afterJapaneseMiss = { isbn ->
        KoboJapaneseEditionBridge({ throw IllegalStateException("HTTP 429 blocked") }, parser).find(isbn)
      })
    assertThat(search.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `known Japanese product page can be rechecked with its ebook ID instead of print ISBN`() {
    val check = KoboKnownProductPageCheck({ page(koboUrl, koboEbook(series = null)) }, parser)
    assertThat(check.check(ebookId, productId, koboUrl.toHttpUrl()))
      .isEqualTo(KoboProductLookupResult.Found(productId, null))
    assertThat(check.check(isbn, productId, koboUrl.toHttpUrl()))
      .isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `opaque ID cannot be searched through the normal ISBN-only resolver`() {
    val search = KoboWebsiteIsbnSearch({ error("No request should be made") }, parser)
    assertThat(search.find(ebookId, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `redirected Rakuten print search reuses primary ISBN HTML instead of fetching print page again`() {
    val calls = mutableListOf<HttpUrl>()
    val paper =
      """
      <h1>かぐや様を語りたい 4</h1>
      <div id='itemDetail'>ISBN： $isbn
        <a href='$ebookUrl'>楽天Kobo 電子書籍版</a>
      </div>
      """.trimIndent()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        calls += url
        when {
          url.encodedPath == "/search" -> page(printUrl, paper)
          url.encodedPath.startsWith("/rk/") -> page(ebookUrl, rakutenEbook())
          url.encodedPath == "/jp/ja/search" -> page(koboUrl, koboEbook(series = null))
          else -> error("The already fetched print page must be reused: $url")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isEqualTo(KoboProductLookupResult.Found(productId, null))
    assertThat(calls.map { it.encodedPath }).containsExactly(
      "/search",
      "/rk/f947c5a0ffeb32f3ae80b2214b14922a/",
      "/jp/ja/search",
    )
  }

  @Test
  fun `strict print verification reuses edition link paired with its search card`() {
    val otherPrint = "https://books.rakuten.co.jp/rb/999999/"
    val otherEbook = "https://books.rakuten.co.jp/rk/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/"
    val requests = mutableListOf<HttpUrl>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        requests += url
        when {
          url.encodedPath == "/search" && url.queryParameter("g") == "000" ->
            page(url.toString(), searchCard() + searchCard().replace(printUrl, otherPrint).replace(ebookUrl, otherEbook))
          url.encodedPath == "/rb/16594407/" ->
            page(printUrl, "<h1>かぐや様を語りたい 4</h1><div id='itemDetail'>ISBN： $isbn</div>")
          url.encodedPath == "/rb/999999/" ->
            page(otherPrint, "<h1>別の作品 1</h1><div id='itemDetail'>ISBN： 9784106100031</div>")
          url.encodedPath.startsWith("/rk/") -> page(ebookUrl, rakutenEbook())
          url.encodedPath == "/jp/ja/search" -> page(koboUrl, koboEbook())
          else -> error("A linked ebook must not require an ebook catalogue sweep: $url")
        }
      }, parser)

    assertThat(bridge.find(isbn)).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(requests.none { it.encodedPath == "/search" && it.queryParameter("g") == "101" }).isTrue()
    assertThat(requests.count { it.encodedPath.startsWith("/rk/") }).isEqualTo(1)
  }

  @Test
  fun `unlinked catalogue candidate requires matching title volume publisher and author`() {
    val calls = mutableListOf<HttpUrl>()
    val paper =
      """
      <h1>かぐや様を語りたい 4 （ヤングジャンプコミックス）</h1>
      <div id='itemDetail'>ISBN： $isbn 著者／編集： G3井田, 赤坂アカ 出版社： 集英社</div>
      """.trimIndent()
    val digital =
      """
      <head><meta property='books:isbn' content='$ebookId'></head>
      <h1>かぐや様を語りたい 4 （ヤングジャンプコミックスDIGITAL） [電子書籍版]</h1>
      <div id='itemDetail'>著者： 赤坂アカ, G3井田 出版社： 集英社</div>
      """.trimIndent()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        calls += url
        when {
          url.encodedPath == "/search" && url.queryParameter("g") == "000" -> page(url.toString(), "<main><a href='$printUrl'>Print</a></main>")
          url.encodedPath.startsWith("/rb/") -> page(printUrl, paper)
          url.encodedPath == "/search" && url.queryParameter("g") == "101" ->
            page(url.toString(), "<main><a href='$ebookUrl'>かぐや様を語りたい 4 [電子書籍版]</a></main>")
          url.encodedPath.startsWith("/rk/") -> page(ebookUrl, digital)
          url.encodedPath == "/jp/ja/search" -> page(koboUrl, koboEbook())
          else -> error("Unexpected request: $url")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(calls.map { it.encodedPath }).containsExactly(
      "/search",
      "/rb/16594407/",
      "/search",
      "/rk/f947c5a0ffeb32f3ae80b2214b14922a/",
      "/jp/ja/search",
    )
  }

  @Test
  fun `Kobo Japan must confirm the exact digital Book ID not an unrelated primary product`() {
    val calls = mutableListOf<String>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        calls += url.encodedPath
        when {
          url.host == "books.rakuten.co.jp" && url.encodedPath == "/search" -> page(url.toString(), searchCard())
          url.host == "books.rakuten.co.jp" -> page(ebookUrl, rakutenEbook())
          url.host == "www.kobo.com" -> page(koboUrl, koboEbook(id = "4340008630131"))
          else -> error("Unexpected request: $url")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls.last()).isEqualTo("/jp/ja/search")
  }

  @Test
  fun `Rakuten or Kobo rate limits stop the Japanese lookup without visiting GB`() {
    val calls = mutableListOf<String>()
    val lookup =
      KoboWebsiteIsbnSearch({ url ->
        calls += url.encodedPath
        empty(url)
      }, parser, afterJapaneseMiss = { KoboProductLookupResult.Failed(KoboWebsiteBlockedException(429)) })
    assertThat(lookup.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).containsExactly("/ww/en/search", "/jp/ja/search")
  }

  @Test
  fun `invalid print identifiers never open Rakuten or Kobo`() {
    val bridge = KoboJapaneseEditionBridge({ error("Invalid ISBN must not trigger a request") }, parser)
    assertThat(bridge.find(ebookId)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(bridge.find("9784088917543")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `untrusted Rakuten links never become outbound requests`() {
    val calls = mutableListOf<HttpUrl>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        calls += url
        when (url.encodedPath) {
          "/search" ->
            page(
              url.toString(),
              "<main><a href='https://evil.example/rb/16594407/'>Print</a>" +
                "<a href='https://evil.example/rk/aaaaaaaaaaaaaaaa/'>電子書籍版</a></main>",
            )
          else -> error("Unexpected outbound request $url")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls.map { it.host }).containsExactly("books.rakuten.co.jp")
  }

  @Test
  fun `Kobo Japan redirect to a different country cannot verify the ebook`() {
    val calls = mutableListOf<String>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        calls += url.encodedPath
        when {
          url.host == "books.rakuten.co.jp" && url.encodedPath == "/search" -> page(url.toString(), searchCard())
          url.host == "books.rakuten.co.jp" -> page(ebookUrl, rakutenEbook())
          url.host == "www.kobo.com" -> page("https://www.kobo.com/ww/en/ebook/other", koboEbook())
          else -> error("Unexpected request $url")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls.last()).isEqualTo("/jp/ja/search")
  }

  @Test
  fun `Japanese bridge has a global top-level request cap and never caches a partial scan as missing`() {
    val requests = mutableListOf<HttpUrl>()
    val links = (1..8).joinToString("") { "<a href='https://books.rakuten.co.jp/rb/$it/'>Print</a>" }
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        requests += url
        when {
          url.encodedPath == "/search" -> page(url.toString(), "<main>$links</main>")
          url.encodedPath.startsWith("/rb/") ->
            page(
              url.toString(),
              """
              <h1>かぐや様を語りたい 4</h1><div id='itemDetail'>ISBN： $isbn
              <a href='$ebookUrl'>楽天Kobo 電子書籍版</a></div>
              """.trimIndent(),
            )
          url.encodedPath.startsWith("/rk/") -> page(ebookUrl, rakutenEbook(title = "かぐや様を語りたい 3"))
          else -> error("No Kobo request should happen without a verified edition")
        }
      }, parser)
    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(requests.size).isLessThanOrEqualTo(12)
    assertThat(requests.none { it.host == "www.kobo.com" }).isTrue()
  }

  @Test
  fun `six digital links on a verified print page do not hide the matching edition`() {
    val unrelatedUrls =
      (1..5).map { "https://books.rakuten.co.jp/rk/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa$it/" }
    val print =
      """
      <h1>かぐや様を語りたい 4</h1>
      <div id='itemDetail'>ISBN： $isbn
        ${unrelatedUrls.joinToString(" ") { "<a href='$it'>電子書籍版</a>" }}
        <a href='$ebookUrl'>楽天Kobo 電子書籍版</a>
      </div>
      <script type='application/ld+json'>{"isbn":"9784088911793"}</script>
      """.trimIndent()
    val requested = mutableListOf<String>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        requested += url.toString()
        when {
          url.encodedPath == "/search" -> page(url.toString(), "<a href='$printUrl'>Print</a>")
          url.encodedPath.startsWith("/rb/") -> page(printUrl, print)
          url.toString() == ebookUrl -> page(ebookUrl, rakutenEbook())
          url.encodedPath.startsWith("/rk/") -> page(url.toString(), rakutenEbook(title = "別の作品 1", id = "4330000000000"))
          url.encodedPath == "/jp/ja/search" -> page(koboUrl, koboEbook())
          else -> error("Unexpected request $url")
        }
      }, parser)

    assertThat(bridge.find(isbn)).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(requested.count { "/rk/" in it }).isEqualTo(6)
    assertThat(requested.last()).contains("/jp/ja/search?query=$ebookId")
  }

  @Test
  fun `ebook with verified primary ID and no h1 is accepted only via the explicit print-edition link`() {
    val requests = mutableListOf<HttpUrl>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        requests += url
        when {
          url.host == "books.rakuten.co.jp" && url.encodedPath == "/search" ->
            page(url.toString(), "<a href='$printUrl'>Print product</a>")
          url.encodedPath == "/rb/16594407/" ->
            page(
              printUrl,
              """
              <h1>かぐや様を語りたい 4</h1>
              <div id='itemDetail'>ISBN： $isbn
                <a href='$ebookUrl'>楽天Kobo 電子書籍版</a>
              </div>
              """.trimIndent(),
            )
          url.encodedPath == "/rk/f947c5a0ffeb32f3ae80b2214b14922a/" ->
            page(
              ebookUrl,
              """
              <html><head><meta property='books:isbn' content='$ebookId'></head>
              <body><div id='productDetailedDescription'><li class='productInfo'>
                <span class='category'>商品番号</span><span class='categoryValue'>$ebookId</span>
              </li></div></body></html>
              """.trimIndent(),
            )
          url.encodedPath == "/jp/ja/search" && url.queryParameter("query") == ebookId ->
            page(koboUrl, koboEbook(series = null))
          else -> error("Unexpected request $url")
        }
      }, parser)

    assertThat(bridge.find(isbn)).isEqualTo(KoboProductLookupResult.Found(productId, null))
    assertThat(requests.map { it.encodedPath }).containsExactly(
      "/search",
      "/rb/16594407/",
      "/rk/f947c5a0ffeb32f3ae80b2214b14922a/",
      "/jp/ja/search",
    )
    assertThat(requests.last().queryParameter("query")).isEqualTo(ebookId)
  }

  @Test
  fun `title-less catalogue ebook without explicit print edition link is rejected`() {
    val requests = mutableListOf<HttpUrl>()
    val bridge =
      KoboJapaneseEditionBridge({ url ->
        requests += url
        when {
          url.encodedPath == "/search" && url.queryParameter("g") == "000" ->
            page(url.toString(), "<a href='$printUrl'>Print product</a>")
          url.encodedPath == "/rb/16594407/" ->
            page(printUrl, "<h1>かぐや様を語りたい 4</h1><div id='itemDetail'>ISBN： $isbn</div>")
          url.encodedPath == "/search" && url.queryParameter("g") == "101" ->
            page(url.toString(), "<a href='$ebookUrl'>かぐや様を語りたい 4</a>")
          url.encodedPath == "/rk/f947c5a0ffeb32f3ae80b2214b14922a/" ->
            page(ebookUrl, "<meta property='books:isbn' content='$ebookId'>")
          else -> error("An unpaired title-less ebook must not reach Kobo: $url")
        }
      }, parser)

    assertThat(bridge.find(isbn)).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(requests.none { it.host == "www.kobo.com" }).isTrue()
  }
}
