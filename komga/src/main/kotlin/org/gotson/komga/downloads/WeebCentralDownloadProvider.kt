// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import com.fasterxml.jackson.databind.ObjectMapper
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.net.URI

/** Native port of Mangathr's WeebCentral metadata and page parsing. */
@Component
class WeebCentralDownloadProvider(
  private val http: ProviderHttp,
  private val mapper: ObjectMapper,
) : DownloadProvider {
  override val id = "weebcentral"
  override val label = "WeebCentral"
  private val base = "https://weebcentral.com"
  private val hosts = setOf("weebcentral.com", "www.weebcentral.com")
  private val imageHosts = WEEB_IMAGE_HOSTS + hosts
  private val seriesIdentifier = Regex("[A-Z0-9]{26}", RegexOption.IGNORE_CASE)
  private val chapterIdentifier = Regex("[A-Z0-9]{10,40}", RegexOption.IGNORE_CASE)

  override fun search(query: String): List<SourceSeries> {
    val value = query.trim()
    require(value.isNotEmpty()) { "Enter a WeebCentral title or URL" }
    val exact =
      if (seriesIdentifier.matches(value)) {
        value.uppercase()
      } else if (value.startsWith("https://")) {
        requireNotNull(readId(value, "series")) { "Invalid WeebCentral series URL" }
      } else if (value.startsWith("/series/")) {
        readId(resolve(value), "series")
      } else {
        null
      }
    if (exact != null) {
      val fallback =
        runCatching {
          URI
            .create(value)
            .path
            .trim('/')
            .split('/')
            .getOrNull(2)
        }.getOrNull()
          ?.takeIf { it.isNotBlank() }
          ?.let(::normalizeWeebQuery)
      val title =
        runCatching {
          Jsoup
            .parse(get("/series/$exact", "$base/search", false), base)
            .selectFirst("h1")
            ?.text()
            .orEmpty()
        }.getOrDefault("")
      return listOf(SourceSeries(exact, title.ifBlank { fallback ?: exact }))
    }
    val normalized = normalizeWeebQuery(value)
    val path = "/search/data?author=&text=${encodeProviderQuery(normalized)}&sort=Best+Match&order=Descending&official=Any&anime=Any&adult=Any&display_mode=Full+Display"
    val body = get(path, "$base/search", false)
    val candidates =
      Jsoup
        .parse(body, base)
        .select("a[href]")
        .mapNotNull { link ->
          val url = link.absUrl("href")
          val seriesId = readId(url, "series") ?: return@mapNotNull null
          val slug =
            URI
              .create(url)
              .path
              .trim('/')
              .split('/')
              .getOrNull(2)
              .orEmpty()
          val slugTitle = normalizeWeebQuery(slug)
          val visible = link.text().ifBlank { link.selectFirst("img[alt]")?.attr("alt").orEmpty() }
          val title = if (slugTitle.isBlank() || weebSearchScore(visible, value) > weebSearchScore(slugTitle, value)) visible else slugTitle
          SourceSeries(seriesId, title.ifBlank { seriesId })
        }.ifEmpty {
          Regex("(?i)/series/([A-Z0-9]{26})(?:/([A-Za-z0-9_-]+))?")
            .findAll(body)
            .map {
              SourceSeries(it.groupValues[1].uppercase(), normalizeWeebQuery(it.groupValues[2]).ifBlank { it.groupValues[1] })
            }.toList()
        }
    return candidates
      .withIndex()
      .groupBy { it.value.id }
      .values
      .map { matches ->
        matches.maxBy { weebSearchScore(it.value.title, value) }
      }.sortedWith(compareByDescending<IndexedValue<SourceSeries>> { weebSearchScore(it.value.title, value) }.thenBy { it.index })
      .map { it.value }
  }

  override fun chapters(
    seriesId: String,
    filters: ChapterFilters,
  ): List<SourceChapter> {
    require(seriesIdentifier.matches(seriesId)) { "Invalid WeebCentral series ID" }
    // The source has no language/group selection; Mangathr intentionally ignores these filters.
    val path = "/series/${seriesId.uppercase()}"
    val body = get("$path/full-chapter-list", "$base$path", true)
    val json = runCatching { mapper.readTree(body) }.getOrNull()
    val entries =
      if (json?.path("chapters")?.isArray == true) {
        json.path("chapters").mapNotNull { entry ->
          val link = resolve(entry.path("url").asText())
          val id =
            readId(link, "chapters")
              ?: entry
                .path("id")
                .asText()
                .trim()
                .uppercase()
                .takeIf(chapterIdentifier::matches) ?: return@mapNotNull null
          val label = entry.path("title").asText().takeIf { it.isNotBlank() } ?: return@mapNotNull null
          chapter(id, label, link.takeIf { readId(it, "chapters") == id })
        }
      } else {
        emptyList()
      }
    val htmlEntries =
      entries.ifEmpty {
        Jsoup
          .parse(body, base)
          .select("a[href]")
          .mapNotNull { link ->
            val id = readId(link.absUrl("href"), "chapters") ?: return@mapNotNull null
            val label =
              link.selectFirst("span[class='']")?.text()?.takeIf { it.isNotBlank() }
                ?: link.text()
            if (label.isBlank()) return@mapNotNull null
            chapter(id, label, link.absUrl("href"))
          }.ifEmpty {
            // Older HTMX fragments contain a label and hidden chapter ID, not an anchor.
            Regex("(?is)<span\\b[^>]*>\\s*([^<]+?)\\s*</span>.{0,1024}?value=[\"']([A-Z0-9]{10,40})[\"']")
              .findAll(body)
              .map { match ->
                chapter(match.groupValues[2].uppercase(), Jsoup.parse(match.groupValues[1], base).text(), null)
              }.toList()
          }
      }
    check(htmlEntries.isNotEmpty()) { "WeebCentral returned no recognizable chapters" }
    return htmlEntries.distinctBy { it.id }.sortedByDescending { it.number.toBigDecimalOrNull() }
  }

  private fun chapter(
    id: String,
    label: String,
    link: String?,
  ): SourceChapter {
    val (number, title) = weebChapterParts(label)
    // The card timestamp is reading history, not a publication timestamp.
    return SourceChapter(id, number, title, null, "en", link ?: "$base/chapters/$id")
  }

  override fun pages(chapterId: String): List<SourcePage> {
    require(chapterIdentifier.matches(chapterId)) { "Invalid WeebCentral chapter ID" }
    val path = "/chapters/${chapterId.uppercase()}"
    val images = "$path/images?is_prev=False&current_page=1&reading_style=long_strip"
    val body = get(images, "$base$path", true)
    val json = runCatching { mapper.readTree(body) }.getOrNull()
    val jsonUrls = if (json?.path("images")?.isArray == true) json.path("images").map { it.path("src").asText() } else emptyList()
    val urls =
      weebUsableImages(jsonUrls).ifEmpty {
        weebUsableImages(Jsoup.parse(body, base).select("img[src], img[data-src]").map { it.absUrl("src").ifBlank { it.absUrl("data-src") } })
      }
    val pages = urls.map { URI.create(resolve(it)) }

    check(pages.isNotEmpty() && pages.size <= 1000) { "Invalid WeebCentral image list" }
    return pages.map { uri ->
      http.page(
        uri,
        imageHosts,
        mapOf(
          "Referer" to "$base$images",
          "User-Agent" to WEEB_USER_AGENT,
          "Accept" to "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
          "Accept-Language" to "en-GB,en;q=0.9",
          "Sec-Fetch-Dest" to "image",
          "Sec-Fetch-Mode" to "no-cors",
          "Sec-Fetch-Site" to if (uri.host == "weebcentral.com") "same-origin" else "cross-site",
        ),
      )
    }
  }

  private fun resolve(value: String): String = runCatching { URI.create("$base/").resolve(value).toString() }.getOrDefault("")

  private fun readId(
    value: String,
    kind: String,
  ): String? {
    val uri = runCatching { URI.create(value) }.getOrNull() ?: return null
    if (runCatching { ProviderHttp.validateUri(uri, hosts) }.isFailure) return null
    val parts = uri.path.trim('/').split('/')
    val pattern = if (kind == "series") seriesIdentifier else chapterIdentifier
    return parts.getOrNull(1)?.takeIf { parts.firstOrNull() == kind && pattern.matches(it) }?.uppercase()
  }

  private fun get(
    path: String,
    referer: String,
    fragment: Boolean,
  ): String {
    val headers =
      mutableMapOf(
        "Referer" to referer,
        "Accept" to if (fragment) "*/*" else "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "en-GB,en;q=0.9",
        "User-Agent" to WEEB_USER_AGENT,
        "Cache-Control" to "no-cache",
        "Pragma" to "no-cache",
      )
    if (fragment)
      headers +=
        mapOf(
          "HX-Request" to "true",
          "HX-Current-URL" to referer,
          "Origin" to base,
          "Sec-Fetch-Dest" to "empty",
          "Sec-Fetch-Mode" to "cors",
          "Sec-Fetch-Site" to "same-origin",
        )
    else
      headers += mapOf("Sec-Fetch-Dest" to "document", "Sec-Fetch-Mode" to "navigate", "Sec-Fetch-Site" to "same-origin", "Upgrade-Insecure-Requests" to "1")
    return http.text(URI.create(base + path), hosts, headers)
  }
}

internal fun normalizeWeebQuery(value: String): String =
  value
    .replace('-', ' ')
    .replace('_', ' ')
    .trim()
    .replace(Regex("\\s+"), " ")

internal fun weebSearchScore(
  title: String,
  query: String,
): Int {
  val stop = setOf("a", "an", "and", "for", "in", "of", "on", "the", "to")

  fun tokens(value: String) =
    value
      .lowercase()
      .split(Regex("[^\\p{L}\\p{N}]+"))
      .filter { it.isNotBlank() && it !in stop }
      .toSet()
  return tokens(title).intersect(tokens(query)).size
}

internal fun weebChapterParts(raw: String): Pair<String, String> {
  val clean =
    raw
      .replace('\u00a0', ' ')
      .replace(Regex("\\s+"), " ")
      .trim()
      .split(Regex("last read", RegexOption.IGNORE_CASE), limit = 2)
      .first()
      .trim()
      .mapNotNull { c ->
        when {
          c in "<>:\"/\\|?*" -> '-'
          c.code < 32 || c.code == 127 -> null
          else -> c
        }
      }.joinToString("")
  val match =
    Regex("(?i)\\b(?:chapter|chap|ch\\.?)\\s*([0-9]+(?:\\.[0-9]+)?)|#\\s*([0-9]+(?:\\.[0-9]+)?)").find(clean)
      ?: Regex("([0-9]+(?:\\.[0-9]+)?)").find(clean)
  val number = match?.groupValues?.drop(1)?.firstOrNull { it.isNotBlank() } ?: "0"
  val title =
    if (match == null) {
      val separator = clean.indexOfFirst { it in "-–—:|" }
      if (separator >= 0) clean.substring(separator + 1).trim() else ""
    } else {
      clean.substring(match.range.last + 1).trimStart(' ', '.', ':', '-', '–', '—', '|')
    }
  return number to title
}

private const val WEEB_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:153.0) Gecko/20100101 Firefox/153.0"

internal val WEEB_IMAGE_HOSTS = setOf("scans-hot.planeptune.us", "scans-cold.planeptune.us", "scans.planeptune.us", "scans.lastation.us")

internal fun weebUsableImages(values: List<String>): List<String> = values.map(String::trim).filter { it.isNotBlank() && !isWeebBrokenImage(it) }

internal fun isWeebBrokenImage(value: String): Boolean =
  runCatching {
    URI
      .create(value)
      .path
      .substringAfterLast('/')
      .equals("broken_image.jpg", true)
  }.getOrDefault(false)
