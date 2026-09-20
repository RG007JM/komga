package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Sanitized, representative layout fragments based on the supplied audit; no live Kobo requests. */
class KoboWebsiteFixturesTest {
  private val isbn = "9781974702015"
  private val productId = "fa183d0f-6794-4e38-b57d-3ebd6cbaeb2c"
  private val seriesId = "708f4ca7-757f-56cb-afa9-39ecd9ebaf1b"
  private val parser = KoboProductPageParser(ObjectMapper())

  private fun fixture(name: String) =
    checkNotNull(javaClass.getResource("/kobo/identity/$name.html")) {
      "Missing identity fixture $name"
    }.readText()

  @Test
  fun `legacy and modern primary layouts reject unrelated recommendation product IDs`() {
    for (name in listOf("legacy-primary", "modern-primary")) {
      val identity = parser.parse(fixture(name), "https://www.kobo.com/ww/en/ebook/correct", isbn)
      assertThat(identity).isEqualTo(KoboProductPageIdentity(productId, seriesId))
    }
  }

  @Test
  fun `series absent is not equivalent to book identity absent`() {
    assertThat(parser.parse(fixture("modern-no-series"), "https://www.kobo.com/ww/en/ebook/correct", isbn))
      .isEqualTo(KoboProductPageIdentity(productId, null))
  }

  @Test
  fun `a recommendation UUID is never promoted to missing primary book identity`() {
    assertThat(parser.parse(fixture("missing-primary-id"), "https://www.kobo.com/ww/en/ebook/correct", isbn)).isNull()
  }

  @Test
  fun `search result candidate must be verified against the exact primary ISBN`() {
    val calls = mutableListOf<String>()
    val lookup =
      KoboWebsiteIsbnSearch({ url ->
        calls += url.encodedPath
        when (url.encodedPath) {
          "/ww/en/search" -> KoboWebsitePage(url, fixture("search-with-recommendations"))
          "/ww/en/ebook/wrong" -> KoboWebsitePage(url, fixture("legacy-primary").replace(isbn, "9781974701193"))
          "/ww/en/ebook/correct" -> KoboWebsitePage(url, fixture("legacy-primary"))
          else -> error("Unexpected request: $url")
        }
      }, parser)
    assertThat(lookup.find(isbn, "it")).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(calls).containsExactly("/ww/en/search", "/ww/en/ebook/wrong", "/ww/en/ebook/correct")
  }

  @Test
  fun `Finnish no result marker has priority over suggested ebooks`() {
    val lookup =
      KoboWebsiteIsbnSearch({ url ->
        assertThat(url.encodedPath).isIn("/ww/en/search", "/jp/ja/search", "/gb/en/search")
        KoboWebsitePage(url, fixture("localized-no-results"))
      }, parser)
    assertThat(lookup.find(isbn, "ja")).isEqualTo(KoboProductLookupResult.NotFound)
  }

  @Test
  fun `known primary product can refresh series with one verified product page request`() {
    val url = "https://www.kobo.com/ww/en/ebook/correct".toHttpUrl()
    var calls = 0
    val checker =
      KoboKnownProductPageCheck({ requested ->
        assertThat(requested).isEqualTo(url)
        calls++
        KoboWebsitePage(requested, fixture("modern-primary"))
      }, parser)
    assertThat(checker.check(isbn, productId, url)).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(calls).isEqualTo(1)
  }

  @Test
  fun `known page validates the previous Product ID and never silently replaces it`() {
    val url = "https://www.kobo.com/ww/en/ebook/correct".toHttpUrl()
    val checker = KoboKnownProductPageCheck({ KoboWebsitePage(url, fixture("modern-primary")) }, parser)
    assertThat(checker.check(isbn, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", url))
      .isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `known URL 404 may fall back to ISBN search while a 429 must not`() {
    val url = "https://www.kobo.com/ww/en/ebook/old-slug".toHttpUrl()
    val oldSlug = KoboKnownProductPageCheck({ throw KoboWebsiteHttpException(404) }, parser)
    val rateLimited = KoboKnownProductPageCheck({ throw KoboWebsiteHttpException(429) }, parser)
    assertThat(oldSlug.check(isbn, productId, url)).isNull()
    assertThat(rateLimited.check(isbn, productId, url))
      .isInstanceOf(KoboProductLookupResult.Failed::class.java)
  }

  @Test
  fun `search redirect reuses its already downloaded verified HTML`() {
    var calls = 0
    val lookup =
      KoboWebsiteIsbnSearch({ _ ->
        calls++
        KoboWebsitePage("https://www.kobo.com/ww/en/ebook/correct?sId=discard-this".toHttpUrl(), fixture("legacy-primary"))
      }, parser)
    assertThat(lookup.find(isbn, "it")).isEqualTo(KoboProductLookupResult.Found(productId, seriesId))
    assertThat(calls).isEqualTo(1)
  }
}
