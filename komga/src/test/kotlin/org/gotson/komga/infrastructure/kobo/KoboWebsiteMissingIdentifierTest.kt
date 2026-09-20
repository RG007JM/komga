package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Primary identity must be present; an unrelated recommendation cannot make missing metadata conclusive. */
class KoboWebsiteMissingIdentifierTest {
  private val isbn = "9781974702015"
  private val parser = KoboProductPageParser(ObjectMapper())

  @Test
  fun `candidate without any primary identifier is inconclusive rather than a cached negative`() {
    val calls = mutableListOf<String>()
    val lookup = KoboWebsiteIsbnSearch({ url ->
      calls += url.encodedPath
      when {
        url.encodedPath == "/ww/en/search" -> KoboWebsitePage(url, "<main><a href='/ww/en/ebook/unreadable'>Book</a></main>")
        url.encodedPath == "/ww/en/ebook/unreadable" -> KoboWebsitePage(url,
          "<html><h1>Book</h1><section class='recommendations'>ISBN: $isbn</section></html>")
        else -> KoboWebsitePage(url, "<h2 data-testid='no-result'>No results</h2>")
      }
    }, parser)

    assertThat(lookup.find(isbn, "ja-JP")).isInstanceOf(KoboProductLookupResult.Failed::class.java)
    assertThat(calls).contains("/ww/en/ebook/unreadable")
  }

}
