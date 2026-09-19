package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.UUID

internal data class KoboProductPageIdentity(
  val productId: String,
  val seriesId: String?,
)

internal class KoboProductPageParser(
  private val objectMapper: ObjectMapper,
) {
  fun parse(
    html: String,
    baseUrl: String,
    isbn: String,
  ): KoboProductPageIdentity? {
    val document =
      Jsoup.parse(
        html,
        baseUrl,
      )

    val productId =
      extractProductId(document)
        ?: return null

    if (!pageMatchesIsbn(document, isbn)) {
      return null
    }

    return KoboProductPageIdentity(
      productId = productId,
      seriesId = extractSeriesId(document),
    )
  }

  private fun extractProductId(
    document: Document,
  ): String? {
    val elements =
      document.select(
        ".item-primary-metadata.book-primary-metadata[data-track-info]",
      )

    if (elements.size != 1) {
      return null
    }

    val trackInfo =
      elements
        .single()
        .attr(
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

  private fun extractSeriesId(
    document: Document,
  ): String? {
    val baseUrl =
      document
        .location()
        .toHttpUrlOrNull()

    return document
      .select("a[href]")
      .asSequence()
      .mapNotNull { element ->
        val href =
          element.attr("href")

        href.toHttpUrlOrNull()
          ?: baseUrl?.resolve(href)
      }.filter { url ->
        url
          .queryParameter("fcsearchfield")
          ?.equals(
            "Series",
            ignoreCase = true,
          ) == true
      }.mapNotNull { url ->
        url
          .queryParameter("seriesId")
          ?.takeIf(String::isNotBlank)
      }.firstOrNull()
  }

  private companion object {
    // Kobo product pages use localized labels; never search the raw HTML for
    // an unqualified 13-digit string.
    val BOOK_ID_PATTERN =
      Regex(
        """(?:ID del libro|Book ID|ISBN)\s*[:：]?\s*((?:\d[\s-]?){12}\d)(?!\d)""",
        RegexOption.IGNORE_CASE,
      )
  }

  private fun pageMatchesIsbn(
    document: Document,
    isbn: String,
  ): Boolean {
    // Only explicit, visible book-ID fields count as identity evidence. An ISBN
    // in a recommendation, a link, or a tracking attribute does not identify
    // the main product. If multiple different book IDs are displayed, do not
    // guess which one belongs to the primary product.
    val displayedBookIds =
      BOOK_ID_PATTERN
        .findAll(document.body().text())
        .map { match -> match.groupValues[1].filter(Char::isDigit) }
        .distinct()
        .toList()

    return displayedBookIds.size == 1 && displayedBookIds.single() == isbn
  }
}
