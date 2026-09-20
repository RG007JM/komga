package org.gotson.komga.infrastructure.kobo

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup

internal data class KoboWebsitePage(
  val url: HttpUrl,
  val html: String,
)

/** Network-free orchestration. Production injects the browser-impersonating, cookie-preserving client. */
internal class KoboWebsiteIsbnSearch(
  private val fetch: (HttpUrl) -> KoboWebsitePage,
  private val parser: KoboProductPageParser,
  private val afterJapaneseMiss: (String) -> KoboProductLookupResult? = { null },
  private val onVerified: (isbn: String, productId: String, url: HttpUrl) -> Unit = { _, _, _ -> },
) {
  fun find(
    identifier: String,
    locale: String?,
  ): KoboProductLookupResult {
    val isbn =
      KoboLookupIsbn.normalize(identifier)
        ?: return KoboProductLookupResult.Failed(IllegalArgumentException("Not a valid ISBN-10 or ISBN-13"))
    var inconclusive = false
    var requests = 0
    val japanese =
      locale
        ?.trim()
        ?.replace('_', '-')
        ?.lowercase()
        ?.let { it == "ja" || it.startsWith("ja-") } == true
    for (store in KoboWebsiteStorefronts.plan(locale)) {
      var storefrontIncomplete = false
      for (page in 1..MAX_PAGES) {
        if (requests >= MAX_REQUESTS) return KoboProductLookupResult.Failed(IllegalStateException("Kobo website lookup request budget exhausted"))
        val url =
          "https://www.kobo.com/$store/search"
            .toHttpUrl()
            .newBuilder()
            .addQueryParameter("query", isbn)
            .addQueryParameter("pagenumber", page.toString())
            .build()
        requests++
        val search = fetch(url)
        val direct = productUrl(search.url)
        if (direct == null && !isSearchPage(search.url, store)) {
          inconclusive = true
          storefrontIncomplete = true
          break // A search redirected to another storefront is not a result in this one.
        }
        if (direct != null) {
          if (storeFor(direct) != store) {
            inconclusive = true
            storefrontIncomplete = true
            break
          }
          parser.parse(search.html, direct.toString(), isbn)?.let { identity ->
            onVerified(isbn, identity.productId, direct)
            return KoboProductLookupResult.Found(identity.productId, identity.seriesId)
          }
          inconclusive = true // Main-book fields missing/mismatched; never cache a negative.
          storefrontIncomplete = true
          break
        }

        val document = Jsoup.parse(search.html, search.url.toString())
        if (document.selectFirst("[data-testid=no-result]") != null) break
        val root = document.selectFirst("main") ?: document.body()
        val candidates =
          root
            .select("a[href*=/ebook/]")
            .mapNotNull { element ->
              productUrl(search.url.resolve(element.attr("href")) ?: return@mapNotNull null)
            }.distinct()
        if (candidates.isEmpty()) {
          if (EMPTY_TEXT.any { it.containsMatchIn(root.text().lowercase()) }) break
          inconclusive = true // An unfamiliar search layout is NOT a verified no-result page.
          storefrontIncomplete = true
          break
        }
        for (candidate in candidates.take(MAX_CANDIDATES)) {
          if (requests >= MAX_REQUESTS) return KoboProductLookupResult.Failed(IllegalStateException("Kobo website lookup request budget exhausted"))
          requests++
          val detail = fetch(candidate)
          val resolved = productUrl(detail.url)
          if (resolved == null || storeFor(resolved) != store) {
            inconclusive = true
            storefrontIncomplete = true
            continue
          }
          parser.parse(detail.html, resolved.toString(), isbn)?.let { identity ->
            onVerified(isbn, identity.productId, resolved)
            return KoboProductLookupResult.Found(identity.productId, identity.seriesId)
          }
          // A missing primary identifier OR matching identifier without a primary Product ID
          // is inconclusive. Do not use arbitrary identifiers in recommendations as evidence.
          if (parser.primaryIdentifierMissing(detail.html, resolved.toString()) || detail.html.contains(isbn)) {
            inconclusive = true
            storefrontIncomplete = true
          }
          // A wrong candidate is not an error in itself; other candidates may match.
        }
        if (candidates.size > MAX_CANDIDATES) {
          inconclusive = true
          storefrontIncomplete = true
          break
        }
        val more =
          root.select("a[href]").any { link ->
            val next = search.url.resolve(link.attr("href"))
            next?.encodedPath?.endsWith("/search") == true &&
              (next.queryParameter("pagenumber") ?: next.queryParameter("page"))?.toIntOrNull()?.let { it > page } == true
          }
        if (!more) break
        if (page == MAX_PAGES) {
          inconclusive = true
          storefrontIncomplete = true
        }
      }
      // Rakuten is invoked only after a completed Japan lookup failed to verify the print ISBN.
      // A challenge, unknown layout or truncated result list is not evidence of absence.
      if (japanese && store == "jp/ja" && !storefrontIncomplete) {
        when (val bridged = afterJapaneseMiss(isbn)) {
          is KoboProductLookupResult.Found -> return bridged
          is KoboProductLookupResult.Failed -> {
            if (bridged.cause is KoboWebsiteBlockedException) return bridged
            inconclusive = true
          }
          else -> Unit
        }
      }
    }
    return if (inconclusive)
      KoboProductLookupResult.Failed(IllegalStateException("Kobo ISBN search was inconclusive in at least one storefront"))
    else
      KoboProductLookupResult.NotFound
  }

  private fun isSearchPage(
    url: HttpUrl,
    store: String,
  ) = url.host in KOBO_HOSTS && url.encodedPath.trimEnd('/').lowercase() == "/$store/search"

  private fun storeFor(url: HttpUrl): String =
    url.pathSegments
      .take(2)
      .joinToString("/")
      .lowercase()

  private fun productUrl(url: HttpUrl): HttpUrl? {
    val segments = url.pathSegments.dropLastWhile(String::isEmpty)
    if (url.scheme != "https" || url.host !in KOBO_HOSTS || segments.size != 4 ||
      segments[2] != "ebook" || segments[3].isBlank() || segments[3] in listOf(".", "..") ||
      url.encodedPathSegments.lastOrNull()?.contains("%2f", ignoreCase = true) == true ||
      segments.take(2).any { !it.matches(Regex("[a-z]{2}")) }
    )
      return null
    // Discard sId, ssId and every other query value before reusing a product URL.
    return url
      .newBuilder()
      .encodedPath(url.encodedPath.trimEnd('/'))
      .query(null)
      .fragment(null)
      .build()
  }

  private companion object {
    val KOBO_HOSTS = setOf("www.kobo.com", "kobo.com")
    val EMPTY_TEXT =
      listOf(
        Regex("\\b(?:0|zero)\\s+(?:search\\s+)?results\\b"),
        Regex("\\bno\\s+(?:search\\s+)?results\\b"),
        Regex("\\baucun\\s+r[ée]sultat\\b"),
        Regex("\\bnessun\\s+risultat\\b"),
        Regex("\\bsin\\s+resultados\\b"),
        Regex("\\bkeine\\s+ergebnisse\\b"),
      )
    const val MAX_PAGES = 2
    const val MAX_CANDIDATES = 10
    const val MAX_REQUESTS = 300
  }
}
