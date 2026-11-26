package org.gotson.komga.downloads

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.time.Instant
import java.util.UUID

@Component
class MangaDexDownloadProvider(
  private val mapper: ObjectMapper,
) : DownloadProvider {
  override val id = "mangadex"
  override val label = "MangaDex"
  private val http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()

  override fun search(query: String): List<SourceSeries> {
    val exactId = runCatching { UUID.fromString(query).toString() }.getOrNull()
    val result =
      if (exactId == null)
        json("/manga?limit=20&title=" + encode(query)).path("data").toList()
      else
        listOf(json("/manga/" + exactId).path("data"))
    return result.map { series ->
      val titles = series.path("attributes").path("title")
      SourceSeries(series.path("id").asText(), titles.path("en").asText(titles.firstOrNull()?.asText() ?: "Untitled"))
    }
  }

  override fun chapters(
    seriesId: String,
    filters: ChapterFilters,
  ): List<SourceChapter> {
    val result = mutableListOf<SourceChapter>()
    val id = UUID.fromString(seriesId).toString()
    var offset = 0
    while (true) {
      val feed =
        json(
          "/manga/" + id + "/feed?limit=100&offset=" + offset +
            "&translatedLanguage%5B%5D=" + encode(filters.language) + "&includes%5B%5D=scanlation_group&order%5Bchapter%5D=desc",
        )
      val entries = feed.path("data")
      for (entry in entries) {
        val attributes = entry.path("attributes")
        if (attributes.path("pages").asInt() == 0 || attributes.path("externalUrl").isTextual) continue
        val groups =
          entry
            .path("relationships")
            .filter { it.path("type").asText() == "scanlation_group" }
            .mapNotNull { it.path("attributes").path("name").textValue() }
            .toSet()
        val normalized = groups.map { it.lowercase() }.toSet()
        if (filters.includeGroups.isNotEmpty() && filters.includeGroups.none { it.lowercase() in normalized }) continue
        if (filters.excludeGroups.any { it.lowercase() in normalized }) continue
        val chapterId = entry.path("id").asText()
        result +=
          SourceChapter(
            chapterId,
            attributes.path("chapter").asText(""),
            attributes.path("title").asText(""),
            runCatching { Instant.parse(attributes.path("publishAt").asText()) }.getOrNull(),
            attributes.path("translatedLanguage").asText(),
            "https://mangadex.org/chapter/" + chapterId,
            groups,
          )
      }
      offset += entries.size()
      if (entries.isEmpty || offset >= feed.path("total").asInt()) break
      check(offset < 10_000) { "MangaDex feed exceeds the supported page limit" }
    }
    return result.distinctBy { it.id }
  }

  override fun pages(chapterId: String): List<SourcePage> {
    val manifest = json("/at-home/server/" + UUID.fromString(chapterId) + "?forcePort443=true")
    val base = URI.create(manifest.path("baseUrl").asText())
    val host = base.host?.lowercase()
    // This draft limits image reads to official MangaDex endpoints.
    check(
      base.scheme == "https" && base.userInfo == null && base.port in listOf(-1, 443) &&
        host != null && (host == "uploads.mangadex.org" || host.endsWith(".mangadex.network")),
    ) {
      "MangaDex returned an unsupported image host"
    }
    val chapter = manifest.path("chapter")
    val hash = chapter.path("hash").asText()
    check(hash.matches(Regex("[a-fA-F0-9]+"))) { "Invalid MangaDex chapter hash" }
    return chapter.path("data").map { image ->
      val filename = image.asText()
      check(filename.matches(Regex("[a-zA-Z0-9._-]+")) && filename !in setOf(".", "..")) { "Invalid MangaDex page filename" }
      SourcePage(filename) {
        val response = http.send(request(base.resolve("/data/" + hash + "/" + filename)), HttpResponse.BodyHandlers.ofInputStream())
        if (response.statusCode() != 200) {
          response.body().close()
          error("MangaDex image request returned HTTP " + response.statusCode())
        }
        response.body()
      }
    }
  }

  private fun json(path: String): JsonNode {
    val response = http.send(request(URI.create("https://api.mangadex.org" + path)), HttpResponse.BodyHandlers.ofString())
    check(response.statusCode() == 200) { "MangaDex API returned HTTP " + response.statusCode() }
    return mapper.readTree(response.body())
  }

  private fun request(uri: URI) =
    HttpRequest
      .newBuilder(uri)
      .header("User-Agent", "Komga-downloads/0.1")
      .timeout(Duration.ofSeconds(30))
      .GET()
      .build()

  private fun encode(value: String) = URLEncoder.encode(value, Charsets.UTF_8)
}
