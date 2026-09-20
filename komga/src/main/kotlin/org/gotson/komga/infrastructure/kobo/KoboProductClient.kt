package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.zhkl0228.impersonator.ImpersonatorFactory
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClientFactory
import okhttp3.Request
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.SeriesMetadataRepository
import org.springframework.stereotype.Component
import java.util.concurrent.Semaphore

/** HTTP non-success cannot be interpreted as ISBN absence. */
internal class KoboWebsiteHttpException(val statusCode: Int) :
  IllegalStateException("Kobo website returned HTTP $statusCode")

/** A Kobo or Rakuten rate limit/challenge stops the current lookup instead of trying other sites. */
internal class KoboWebsiteBlockedException(val statusCode: Int) :
  IllegalStateException("Public website blocked or rate-limited the lookup (HTTP $statusCode)")

/** Public Kobo WEBSITE lookups, never StoreAPI or an authenticated device request. */
@Component
class KoboProductClient(
  private val objectMapper: ObjectMapper,
  private val bookRepository: BookRepository,
  private val seriesMetadataRepository: SeriesMetadataRepository,
  private val diagnostics: KoboLookupDiagnostics,
) {
  // Keep only request STARTS spaced per host; no lock around a whole 47-storefront lookup.
  private val requestPacer = KoboWebsiteRequestPacer(1_500L)
  private val outboundSlots = Semaphore(2, true)
  private val inFlight = KoboIsbnSingleFlight<KoboProductLookupResult>()
  private val verifiedPages = object : LinkedHashMap<String, Triple<String, String, HttpUrl>>(128, 0.75f, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Triple<String, String, HttpUrl>>?) = size > 256
  }

  // Keep Komga's existing browser fingerprint rather than replacing it with a plain Java HTTP client.
  // cloudscraper in the reference Python probe also maintains a browser session; no guarantee is made
  // that these client properties solve a Cloudflare JavaScript challenge.
  private val impersonator = ImpersonatorFactory.macChrome()
  private val userAgent = impersonator.javaClass.getMethod("getUserAgent").invoke(impersonator).toString()
  private val cookies = object : CookieJar {
    private val stored = mutableListOf<Cookie>()
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
      synchronized(stored) {
        stored.removeAll { existing -> cookies.any { it.name == existing.name && it.domain == existing.domain && it.path == existing.path } }
        stored.addAll(cookies)
      }
    }
    override fun loadForRequest(url: HttpUrl): List<Cookie> = synchronized(stored) {
      stored.removeAll { it.expiresAt <= System.currentTimeMillis() }
      stored.filter { it.matches(url) }
    }
  }
  private val client = OkHttpClientFactory.create(impersonator).newHttpClient().newBuilder()
    .cookieJar(cookies).followRedirects(true).build()
  private val pageParser = KoboProductPageParser(objectMapper)
  private val knownProductCheck = KoboKnownProductPageCheck(::getPage, pageParser)
  private val japaneseEditionBridge = KoboJapaneseEditionBridge(::getPage, pageParser) { isbn, ebookId, productId, url ->
    synchronized(verifiedPages) { verifiedPages["$isbn|$productId"] = Triple(ebookId, productId, url) }
  }
  private val websiteSearch = KoboWebsiteIsbnSearch(::getPage, pageParser, afterJapaneseMiss = japaneseEditionBridge::find) { isbn, productId, url ->
    synchronized(verifiedPages) { verifiedPages["$isbn|$productId"] = Triple(isbn, productId, url) }
  }

  /** Use the requesting book, not a globally unique ISBN lookup, to determine its series language. */
  fun findProductForBook(isbn: String, bookId: String): KoboProductLookupResult {
    val normalized = KoboLookupIsbn.normalize(isbn)
      ?: return KoboProductLookupResult.Failed(IllegalArgumentException("Not a valid ISBN-10 or ISBN-13"))
    return try {
      findWithLocale(normalized, localeForBook(bookId))
    } catch (e: Exception) {
      KoboProductLookupResult.Failed(e)
    }
  }

  /** For callers without a Komga book context, use the documented Worldwide/GB default. */
  fun findProductByIsbn(isbn: String): KoboProductLookupResult = findWithLocale(isbn, null)

  internal fun localeForBook(bookId: String): String? = bookRepository.getSeriesIdOrNull(bookId)
    ?.let(seriesMetadataRepository::findByIdOrNull)?.language

  /** Compare full lookup policy, including the Japanese bridge route, before sharing cached identities. */
  fun canShareMapping(leftBookId: String, rightBookId: String): Boolean =
    KoboWebsiteStorefronts.plan(localeForBook(leftBookId)) ==
      KoboWebsiteStorefronts.plan(localeForBook(rightBookId))

  private fun findWithLocale(isbn: String, locale: String?): KoboProductLookupResult {
    val normalized = KoboLookupIsbn.normalize(isbn)
      ?: return KoboProductLookupResult.Failed(IllegalArgumentException("Not a valid ISBN-10 or ISBN-13"))
    // A Japanese print ISBN and a non-Japanese book with the same digits must not share a flight.
    val routingKey = KoboWebsiteStorefronts.plan(locale).joinToString(",")
    return try {
      inFlight.run("$normalized|$routingKey") {
        val result = try {
          websiteSearch.find(normalized, locale)
        } catch (e: Exception) {
          KoboProductLookupResult.Failed(e)
        }
        val found = result as? KoboProductLookupResult.Found
        val store = found?.let {
          synchronized(verifiedPages) {
            verifiedPages["$normalized|${it.productId}"]?.third?.pathSegments?.take(2)?.joinToString("/")
          }
        }
        diagnostics.record(normalized, when (result) {
          is KoboProductLookupResult.Found -> "FOUND"
          KoboProductLookupResult.NotFound -> "NOT_FOUND"
          is KoboProductLookupResult.Failed -> "INCONCLUSIVE"
        }, store)
        result
      }
    } catch (e: Exception) {
      KoboProductLookupResult.Failed(e)
    }
  }

  /** Re-check a previously verified primary product page when still in this process's bounded cache.
   * Returns null when no trusted URL is available (e.g. after restart): caller may search by ISBN.
   */
  fun refreshKnownProduct(isbn: String, expectedProductId: String): KoboProductLookupResult? {
    val normalized = KoboLookupIsbn.normalize(isbn) ?: return null
    val key = "$normalized|$expectedProductId"
    val known = synchronized(verifiedPages) { verifiedPages[key] } ?: return null
    val (verifiedBookId, cachedId, url) = known
    if (cachedId != expectedProductId) return null
    val result = knownProductCheck.check(verifiedBookId, expectedProductId, url)
    if (result == null) synchronized(verifiedPages) {
      // Do not delete a newer mapping inserted while the HTTP call was in progress.
      if (verifiedPages[key] == known) verifiedPages.remove(key)
    }
    return result
  }

  private fun getPage(url: HttpUrl): KoboWebsitePage {
    outboundSlots.acquire()
    try {
      requestPacer.awaitTurn(url.host)
      val request = Request.Builder().url(url)
        .header("User-Agent", userAgent)
        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        .header("Accept-Language", "en-GB,en;q=0.9")
        .header("Upgrade-Insecure-Requests", "1")
        .header("Sec-Fetch-Dest", "document")
        .header("Sec-Fetch-Mode", "navigate")
        .header("Sec-Fetch-Site", "none")
        .header("Sec-Fetch-User", "?1")
        .get().build()
      return client.newCall(request).execute().use { response ->
        if (response.header("cf-mitigated")?.equals("challenge", ignoreCase = true) == true ||
          response.code == 403 || response.code == 429) {
          throw KoboWebsiteBlockedException(response.code)
        }
        if (!response.isSuccessful) throw KoboWebsiteHttpException(response.code)
        if (!response.header("Content-Type", "text/html").orEmpty().contains("text/html", ignoreCase = true)) {
          throw IllegalStateException("Kobo website returned a non-HTML response")
        }
        val body = response.body?.string().orEmpty()
        if (listOf("challenge-form", "cf-chl", "/cdn-cgi/challenge-platform/", "verify you are human")
            .any { body.contains(it, ignoreCase = true) }) {
          throw KoboWebsiteBlockedException(response.code)
        }
        KoboWebsitePage(response.request.url, body)
      }
    } finally {
      outboundSlots.release()
    }
  }

}
