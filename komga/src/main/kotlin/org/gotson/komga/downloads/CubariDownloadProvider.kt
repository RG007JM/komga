// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.URI
import java.security.MessageDigest
import java.util.Base64

@Component
class CubariDownloadProvider(
  private val mapper: ObjectMapper,
  private val http: ProviderHttp,
) : DownloadProvider {
  override val id = "cubari"
  override val label = "Cubari (series link)"

  private val base = "https://cubari.moe"
  private val hosts = setOf("cubari.moe")
  private val rawHosts = setOf("raw.githubusercontent.com", "gist.githubusercontent.com")
  private val imageHosts = rawHosts + setOf("i.imgur.com", "files.catbox.moe", "cdn.imgchest.com", "images.imgchest.com", "i.postimg.cc")
  private val sources = setOf("gist", "imgur", "catbox", "imgchest")
  private val slug = Regex("[A-Za-z0-9_=-]{1,2048}")

  override fun search(query: String): List<SourceSeries> {
    val seriesId = parseSeries(query.trim())
    val data = manifest(seriesId)
    val title =
      data
        .path("title")
        .asText()
        .ifBlank { data.path("series_name").asText() }
        .trim()
    check(title.isNotEmpty()) { "Cubari manifest has no title" }
    return listOf(SourceSeries(seriesId, title))
  }

  override fun chapters(
    seriesId: String,
    filters: ChapterFilters,
  ): List<SourceChapter> {
    val data = manifest(seriesId)
    val chapterMap = data.path("chapters")
    check(chapterMap.isObject) { "Cubari manifest has no chapter map" }
    val seriesLanguage = data.path("language").asText("")
    return chapterMap
      .fields()
      .asSequence()
      .flatMap { (number, chapter) ->
        val language = chapter.path("language").asText(seriesLanguage).ifBlank { filters.language }
        val groups = chapter.path("groups")
        check(groups.isObject) { "Cubari chapter has no group map" }
        if (!language.equals(filters.language, true)) return@flatMap emptySequence()
        groups
          .fields()
          .asSequence()
          .map { (group, _) ->
            val groupName = data.path("groups").path(group).asText(group)
            val names = setOf(group, groupName) + groupName.split('/').map { it.trim() }
            Triple(group, groupName, names)
          }.filter { filters.acceptsGroups(it.third) }
          .map { (group, groupName, _) ->
            val payload = encode(mapper.writeValueAsString(listOf(seriesId, number, group)))
            SourceChapter(
              "${fingerprint(payload)}.$payload",
              number,
              chapter.path("title").asText(""),
              parseProviderDate(chapter.path("release_date").path(group).asText(""))
                ?: parseProviderDate(chapter.path("last_updated").asText("")),
              language,
              readerUrl(seriesId),
              setOf(groupName),
            )
          }
      }.toList()
      .sortedWith(compareByDescending<SourceChapter> { it.number.toBigDecimalOrNull() }.thenBy { it.groups.firstOrNull() })
  }

  override fun pages(chapterId: String): List<SourcePage> {
    val payload = chapterId.substringAfter('.', "")
    require(payload.length in 1..8192 && chapterId.substringBefore('.') == fingerprint(payload)) { "Invalid Cubari chapter ID" }
    val key = mapper.readTree(decode(payload))
    require(key.isArray && key.size() == 3 && key.all { it.isTextual }) { "Invalid Cubari chapter ID" }
    val data = manifest(key[0].asText())
    val group =
      data
        .path("chapters")
        .path(key[1].asText())
        .path("groups")
        .path(key[2].asText())
    check(!group.isMissingNode) { "Cubari chapter or group is no longer available" }
    val images = if (group.isTextual) proxy(group.asText()) else group
    check(images.isArray && !images.isEmpty) { "Cubari chapter has no image list" }
    require(images.size() <= 1000) { "Chapter exceeds the supported page limit" }
    return images.map { image ->
      val value = if (image.isTextual) image.asText() else image.path("src").asText("")
      require(value.isNotBlank()) { "Invalid Cubari page entry" }
      http.page(URI.create(value), imageHosts, mapOf("Referer" to "$base/"))
    }
  }

  private fun parseSeries(value: String): String {
    val uri = runCatching { URI.create(value) }.getOrNull() ?: throw IllegalArgumentException("Enter a Cubari series link or raw GitHub JSON URL")
    if (uri.host?.lowercase() == "imgur.com") {
      ProviderHttp.validateUri(uri, setOf("imgur.com"))
      val id = Regex("/a/([A-Za-z0-9]{4,8})/?").matchEntire(uri.path)?.groupValues?.get(1)
      requireNotNull(id) { "Enter an Imgur album URL" }
      return "imgur.$id"
    }
    if (uri.host?.lowercase() in rawHosts) {
      ProviderHttp.validateUri(uri, rawHosts)
      require(uri.rawQuery == null && uri.path.length in 2..2048) { "Invalid raw GitHub manifest URL" }
      return "raw.${encode(uri.toASCIIString())}"
    }
    ProviderHttp.validateUri(uri, hosts)
    val parts = uri.path.trim('/').split('/')
    require(parts.size >= 3 && parts[0] == "read" && parts[1] in sources && slug.matches(parts[2])) { "Enter a supported Cubari series link" }
    return "${parts[1]}.${parts[2]}"
  }

  private fun manifest(seriesId: String): JsonNode {
    val source = seriesId.substringBefore('.')
    val key = seriesId.substringAfter('.', "")
    require(slug.matches(key) || (source == "raw" && key.length in 1..4096 && key.matches(Regex("[A-Za-z0-9_-]+")))) { "Invalid Cubari series ID" }
    val data =
      if (source == "raw") {
        val uri = URI.create(decode(key))
        ProviderHttp.validateUri(uri, rawHosts)
        require(uri.rawQuery == null) { "Invalid raw GitHub manifest URL" }
        mapper.readTree(http.text(uri, rawHosts))
      } else {
        require(source in sources) { "Unsupported Cubari source" }
        mapper.readTree(http.text(URI.create("$base/read/api/$source/series/$key/"), hosts))
      }
    check(data.isObject && data.path("chapters").isObject) { "Invalid Cubari series manifest" }
    return data
  }

  private fun proxy(path: String): JsonNode {
    require(path.matches(Regex("/proxy/api/(imgur|catbox|imgchest)/chapter/[A-Za-z0-9_-]{1,200}/?"))) { "Unsupported Cubari chapter proxy" }
    val data = mapper.readTree(http.text(URI.create(base + path), hosts))
    return if (data.isArray) data else data.path("pages")
  }

  private fun readerUrl(seriesId: String): String = if (seriesId.startsWith("raw.")) decode(seriesId.substringAfter('.')) else "$base/read/${seriesId.replaceFirst('.', '/')}/"

  private fun encode(value: String): String = Base64.getUrlEncoder().withoutPadding().encodeToString(value.toByteArray(Charsets.UTF_8))

  private fun decode(value: String): String = Base64.getUrlDecoder().decode(value).toString(Charsets.UTF_8)

  private fun fingerprint(value: String): String =
    MessageDigest
      .getInstance("SHA-256")
      .digest(value.toByteArray(Charsets.UTF_8))
      .take(12)
      .joinToString("") { "%02x".format(it) }
}
