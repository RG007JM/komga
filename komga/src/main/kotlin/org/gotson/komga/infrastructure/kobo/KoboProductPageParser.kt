package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.UUID

internal data class KoboProductPageIdentity(
  val productId: String,
  val seriesId: String?,
)

/** Only accept IDs that belong to the main book; recommendations are not identity evidence. */
internal class KoboProductPageParser(
  private val objectMapper: ObjectMapper,
) {
  fun parse(html: String, baseUrl: String, isbn: String): KoboProductPageIdentity? {
    val document = Jsoup.parse(html, baseUrl)
    val bookIds = mutableSetOf<String>()
    document.select(".bookitem-secondary-metadata li, li.flex.flex-row").filterNot(::nonPrimary).forEach { li ->
      BOOK_NUMBER.findAll(li.text()).forEach { bookIds += it.value }
    }
    // Older Kobo layouts can put a labeled Book ID outside the secondary-metadata div.
    if (bookIds.isEmpty()) {
      val primary = document.body().clone()
      primary.select(".recommendations, [class*=recommend], [id*=recommend], .carousel, .related, .series-item, .book-card, .bookcard, .search-result, .also-bought, .upsell, .slider, script, style").remove()
      LABELLED_BOOK_ID.findAll(primary.text()).forEach { match ->
        bookIds += match.groupValues[1].filter(Char::isDigit)
      }
    }
    // Legacy pages can put the main book identifier in structured data instead
    // of a visible detail row. Multiple competing Book/Product objects are rejected.
    if (bookIds.isEmpty()) {
      document.select("script[type=application/ld+json]").filterNot(::nonPrimary).forEach { script ->
        val json = runCatching { objectMapper.readTree(script.data()) }.getOrNull()
          ?: return@forEach
        val entries = if (json.isArray) json.toList() else listOf(json)
        entries.forEach { entry ->
          if (entry.path("@type").asText() in setOf("Book", "Product")) {
            entry.path("isbn").asText().takeIf(BOOK_NUMBER::matches)?.let(bookIds::add)
          }
        }
      }
    }
    // The serialized primary item-details object is the fallback for the Next Flight page.
    val nextFlight = html.contains("__next_f") || html.contains("__NEXT_DATA__") || document.selectFirst("input#ratItemId") != null
    val scriptIdentity = if (nextFlight) extractEmbeddedPrimary(document, isbn) else null
    if (bookIds.isEmpty() && scriptIdentity != null) bookIds += isbn
    if (bookIds.size != 1 || bookIds.single() != isbn) return null

    val primaryIds = mutableSetOf<String>()
    document.select(".item-primary-metadata.book-primary-metadata[data-track-info]")
      .filterNot(::nonPrimary).forEach { element ->
        runCatching { objectMapper.readTree(element.attr("data-track-info")).path("productId").asText() }
          .getOrNull()?.let(::uuid)?.let(primaryIds::add)
      }
    val rat = document.select("input#ratItemId")
    if (rat.isNotEmpty()) {
      if (rat.size != 1 || nonPrimary(rat.single()) ||
        rat.single().attr("type") != "hidden" || rat.single().attr("name") != "rat") return null
      uuid(rat.single().attr("value"))?.let(primaryIds::add) ?: return null
    }
    if (primaryIds.isEmpty()) scriptIdentity?.first?.let(primaryIds::add)
    if (primaryIds.size != 1) return null

    val seriesIds = mutableSetOf<String>()
    document.select(".books-in-series a[href], a.view-all[href*=seriesId]")
      .filterNot(::nonPrimary).forEach { a ->
        val url = a.attr("href").toHttpUrlOrNull() ?: document.location().toHttpUrlOrNull()?.resolve(a.attr("href"))
        url?.queryParameter("seriesId")?.let(::uuid)?.let(seriesIds::add)
      }
    scriptIdentity?.second?.let(seriesIds::add)
    if (seriesIds.size > 1) return null
    return KoboProductPageIdentity(primaryIds.single(), seriesIds.singleOrNull())
  }

  /** An absent primary identifier is inconclusive, even when the search result contains a link. */
  fun primaryIdentifierMissing(html: String, baseUrl: String): Boolean {
    val document = Jsoup.parse(html, baseUrl)
    if (document.select(".bookitem-secondary-metadata li, li.flex.flex-row")
        .filterNot(::nonPrimary).any { BOOK_NUMBER.containsMatchIn(it.text()) }) return false
    val primary = document.body().clone()
    primary.select(".recommendations, [class*=recommend], [id*=recommend], .carousel, .related, .series-item, .book-card, .bookcard, .search-result, .also-bought, .upsell, .slider, script, style").remove()
    if (LABELLED_BOOK_ID.containsMatchIn(primary.text())) return false
    if (document.select("script[type=application/ld+json]").filterNot(::nonPrimary).any { script ->
        val json = runCatching { objectMapper.readTree(script.data()) }.getOrNull()
        val entries = when {
          json == null -> emptyList()
          json.isArray -> json.toList()
          else -> listOf(json)
        }
        entries.any { entry -> entry.path("@type").asText() in setOf("Book", "Product") &&
          BOOK_NUMBER.matches(entry.path("isbn").asText()) }
      }) return false
    return true
  }

  private fun extractEmbeddedPrimary(document: Document, isbn: String): Pair<String?, String?>? {
    // Next Flight HTML serializes itemDetails beside metadata.isbn. Never scan the
    // whole script for the first ProductId: unrelated seriesItems contain other IDs.
    val productPattern = Regex("""["\\]productId["\\]\s*:\s*["\\]($UUID_REGEX)["\\]""", RegexOption.IGNORE_CASE)
    val isbnPattern = Regex("""["\\]isbn["\\]\s*:\s*["\\]${Regex.escape(isbn)}["\\]""", RegexOption.IGNORE_CASE)
    val seriesPattern = Regex("""["\\]series["\\]\s*:\s*\{\s*["\\]id["\\]\s*:\s*["\\]($UUID_REGEX)["\\]""", RegexOption.IGNORE_CASE)
    val results = mutableSetOf<Pair<String?, String?>>()
    document.select("script").forEach { script ->
      val raw = script.data().ifEmpty { script.html() }
      if (!raw.contains("itemDetails") || !raw.contains(isbn)) return@forEach
      val normalized = raw.replace("\\\\\\\"", "\\\"").replace("\\\"", "\"")
      isbnPattern.findAll(normalized).forEach { found ->
        val before = normalized.substring(maxOf(0, found.range.first - 1800), found.range.first)
        val anchor = before.lastIndexOf("itemDetails")
        if (anchor < 0 || before.substring(anchor).contains("seriesItems")) return@forEach
        val product = productPattern.findAll(before.substring(anchor)).lastOrNull()?.groupValues?.get(1)?.let(::uuid)
        val after = normalized.substring(found.range.last + 1, minOf(normalized.length, found.range.last + 1500))
        val series = seriesPattern.find(after)?.groupValues?.get(1)?.let(::uuid)
        if (product != null) results += product to series
      }
    }
    return results.singleOrNull()
  }

  private fun uuid(raw: String): String? =
    raw.takeIf { UUID_PATTERN.matches(it) && runCatching { UUID.fromString(it) }.isSuccess }?.lowercase()

  private fun nonPrimary(node: Element): Boolean =
    node.parents().plus(node).any { ancestor ->
      val labels = listOf(ancestor.className(), ancestor.id(), ancestor.attr("data-testid")).joinToString(" ").lowercase()
      UNRELATED.any(labels::contains)
    }

  private companion object {
    const val UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"
    val UUID_PATTERN = Regex(UUID_REGEX, RegexOption.IGNORE_CASE)
    val BOOK_NUMBER = Regex("(?<!\\d)\\d{13}(?!\\d)")
    val LABELLED_BOOK_ID = Regex("""(?:ID del libro|Book ID|ISBN)\s*[:：]?\s*((?:\d[\s-]?){12}\d)(?!\d)""", RegexOption.IGNORE_CASE)
    val UNRELATED = listOf("recommend", "carousel", "related", "bookcard", "book-card", "search-result", "also-bought", "upsell", "slider", "series-item")
  }
}
