package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.zhkl0228.impersonator.ImpersonatorFactory
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClientFactory
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Component
class KoboProductClient(
  private val objectMapper: ObjectMapper,
) {
  private val requestLock =
    ReentrantLock()

  @Volatile
  private var lastRequestAt =
    0L

  private val impersonator =
    ImpersonatorFactory.macChrome()

  private val userAgent =
    impersonator
      .javaClass
      .getMethod("getUserAgent")
      .invoke(impersonator)
      .toString()

  private val client =
    OkHttpClientFactory
      .create(impersonator)
      .newHttpClient()

  fun findProductByIsbn(
    isbn: String,
  ): KoboProductLookupResult {
    val normalizedIsbn =
      isbn
        .replace("-", "")
        .replace(" ", "")
        .trim()

    if (normalizedIsbn.isBlank()) {
      return KoboProductLookupResult.NotFound
    }

    val url =
      KOBO_SEARCH_URL
        .toHttpUrl()
        .newBuilder()
        .addQueryParameter(
          "query",
          normalizedIsbn,
        )
        .addQueryParameter(
          "fcmedia",
          "Book",
        )
        .addQueryParameter(
          "pageNumber",
          "1",
        )
        .build()

    return try {
      requestLock.withLock {
        throttle()

        lookup(
          url = url,
          isbn = normalizedIsbn,
        )
      }
    } catch (e: Exception) {
      KoboProductLookupResult.Failed(e)
    }
  }

  private fun lookup(
    url: HttpUrl,
    isbn: String,
  ): KoboProductLookupResult {
    val request =
      Request
        .Builder()
        .url(url)
        .header(
          "User-Agent",
          userAgent,
        )
        .header(
          "Accept",
          "text/html,application/xhtml+xml,application/xml;q=0.9," +
            "image/avif,image/webp,image/apng,*/*;q=0.8",
        )
        .header(
          "Accept-Language",
          "en-GB,en;q=0.9",
        )
        .header(
          "Upgrade-Insecure-Requests",
          "1",
        )
        .header(
          "Sec-Fetch-Dest",
          "document",
        )
        .header(
          "Sec-Fetch-Mode",
          "navigate",
        )
        .header(
          "Sec-Fetch-Site",
          "none",
        )
        .header(
          "Sec-Fetch-User",
          "?1",
        )
        .get()
        .build()

    return client
      .newCall(request)
      .execute()
      .use { response ->
        val body =
          response.body
            ?.string()
            .orEmpty()

        if (
          response.header("cf-mitigated")
            ?.equals(
              "challenge",
              ignoreCase = true,
            ) == true
        ) {
          return@use KoboProductLookupResult.Failed(
            IllegalStateException(
              "Kobo request was blocked by a Cloudflare challenge",
            ),
          )
        }

        if (!response.isSuccessful) {
          return@use KoboProductLookupResult.Failed(
            IllegalStateException(
              "Kobo returned HTTP ${response.code}",
            ),
          )
        }

        if (body.isCloudflareChallengePage()) {
          return@use KoboProductLookupResult.Failed(
            IllegalStateException(
              "Kobo returned a Cloudflare challenge page",
            ),
          )
        }

        val document =
          Jsoup.parse(
            body,
            response.request.url.toString(),
          )

        val productId =
          extractProductId(document)
            ?: return@use KoboProductLookupResult.NotFound

        if (!pageMatchesIsbn(
            document = document,
            isbn = isbn,
          )
        ) {
          return@use KoboProductLookupResult.NotFound
        }

        KoboProductLookupResult.Found(
          productId,
        )
      }
  }

  private fun throttle() {
    val now =
      System.currentTimeMillis()

    val elapsed =
      now - lastRequestAt

    if (elapsed < MIN_REQUEST_INTERVAL_MS) {
      Thread.sleep(
        MIN_REQUEST_INTERVAL_MS - elapsed,
      )
    }

    lastRequestAt =
      System.currentTimeMillis()
  }

  private fun extractProductId(
    document: Document,
  ): String? {
    val element =
      document.selectFirst(
        ".item-primary-metadata.book-primary-metadata[data-track-info]",
      )
        ?: return null

    val trackInfo =
      element.attr(
        "data-track-info",
      )

    val productId =
      runCatching {
        objectMapper
          .readTree(trackInfo)
          .path("productId")
          .asText()
      }.getOrNull()
        ?.takeIf {
          it.isNotBlank()
        }
        ?: return null

    return productId.takeIf {
      runCatching {
        UUID.fromString(it)
      }.isSuccess
    }
  }

  private fun pageMatchesIsbn(
    document: Document,
    isbn: String,
  ): Boolean {
    val normalizedDocument =
      document
        .html()
        .replace("-", "")
        .replace(" ", "")

    return normalizedDocument.contains(
      isbn,
    )
  }

  private fun String.isCloudflareChallengePage(): Boolean {
    val value =
      lowercase()

    return value.contains(
      "challenge-form",
    ) ||
      value.contains(
        "/cdn-cgi/challenge-platform/",
      ) ||
      value.contains(
        "window._cf_chl_opt",
      ) ||
      value.contains(
        "cf-chl-widget",
      )
  }

  private companion object {
    const val KOBO_SEARCH_URL =
      "https://www.kobo.com/gb/en/search"

    const val MIN_REQUEST_INTERVAL_MS =
      1_000L
  }
}