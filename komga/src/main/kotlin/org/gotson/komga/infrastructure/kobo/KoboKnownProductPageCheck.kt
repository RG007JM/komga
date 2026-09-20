package org.gotson.komga.infrastructure.kobo

import okhttp3.HttpUrl

/** A product-only series recheck, backed by a previously ISBN-verified public product URL. */
internal class KoboKnownProductPageCheck(
  private val fetch: (HttpUrl) -> KoboWebsitePage,
  private val parser: KoboProductPageParser,
) {
  /** Null means the old slug was removed and a full ISBN rediscovery may be attempted. */
  fun check(
    isbn: String,
    productId: String,
    url: HttpUrl,
  ): KoboProductLookupResult? =
    try {
      val page = fetch(url)
      val segments = page.url.pathSegments
      if (page.url.scheme != "https" || page.url.host !in setOf("www.kobo.com", "kobo.com") ||
        segments.size != 4 || segments[2] != "ebook"
      ) {
        KoboProductLookupResult.Failed(IllegalStateException("Kobo known product URL no longer returned an ebook page"))
      } else {
        val observed = parser.parse(page.html, page.url.toString(), isbn)
        if (observed == null || observed.productId != productId) {
          KoboProductLookupResult.Failed(IllegalStateException("Verified Kobo product page identity changed"))
        } else {
          KoboProductLookupResult.Found(observed.productId, observed.seriesId)
        }
      }
    } catch (e: KoboWebsiteHttpException) {
      if (e.statusCode == 404 || e.statusCode == 410) null else KoboProductLookupResult.Failed(e)
    } catch (e: Exception) {
      KoboProductLookupResult.Failed(e)
    }
}
