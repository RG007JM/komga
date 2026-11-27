// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.net.URI
import java.time.Instant
import java.util.UUID

private val logger = KotlinLogging.logger {}

/** Native MANGA Plus adapter for chapters made available by the source. */
@Component
@ConditionalOnProperty(name = ["komga.downloads.mangaplus.enabled"], havingValue = "true")
class MangaPlusDownloadProvider(
  private val http: ProviderHttp,
  @param:Value("\${komga.downloads.mangaplus.language:0}") private val language: Int = 0,
  @param:Value("\${komga.downloads.mangaplus.image-quality:super_high}") private val imageQuality: String = "super_high",
  @param:Value("\${komga.downloads.mangaplus.split:no}") private val split: String = "no",
) : DownloadProvider {
  init {
    require(language in 0..6) { "Invalid MANGA Plus catalogue language" }
    require(imageQuality in setOf("super_high", "high", "low")) { "Invalid MANGA Plus image quality" }
    require(split in setOf("yes", "no")) { "Invalid MANGA Plus split option" }
  }

  private val session = UUID.randomUUID().toString()
  override val id = "mangaplus"
  override val label = "MANGA Plus (experimental)"
  private val base = "https://jumpg-webapi.tokyo-cdn.com/api"
  private val hosts = setOf("jumpg-webapi.tokyo-cdn.com")
  private val website = "https://mangaplus.shueisha.co.jp"
  private val imageHosts = setOf("jumpg-assets.tokyo-cdn.com", "jumpg-assets2.tokyo-cdn.com", "jumpg-assets3.tokyo-cdn.com", "jumpg-assets4.tokyo-cdn.com")

  override fun search(query: String): List<SourceSeries> {
    val value = query.trim()
    val exact =
      if (value.startsWith("https://")) {
        val uri = URI.create(value)
        ProviderHttp.validateUri(uri, setOf("mangaplus.shueisha.co.jp"))
        requireNotNull(Regex("/titles/([0-9]+)/?").matchEntire(uri.path)?.groupValues?.get(1)) { "Enter a MANGA Plus title URL" }.let(::titleId)
      } else {
        value.toLongOrNull()?.let { titleId(value) }
      }
    if (exact != null) return listOf(series(detail(exact).wireMessage(1)))
    require(value.isNotEmpty()) { "Enter a MANGA Plus title or ID" }
    val data = get("/title_list/allV2", "$website/manga_list/all")
    return try {
      mangaPlusCatalog(data, value, language)
    } catch (e: IllegalArgumentException) {
      throw IllegalStateException("MANGA Plus catalogue returned malformed protobuf metadata", e)
    }
  }

  override fun chapters(
    seriesId: String,
    filters: ChapterFilters,
  ): List<SourceChapter> {
    val id = titleId(seriesId)
    // Mangathr selects language at catalogue lookup and ignores group filters.
    val data = detailResponse(id)
    val detail = parseDetail(data, id)
    val edition = (detail.wireMessage(1).wireValue(7) ?: 0L).toInt()
    val chapterLanguage = listOf("en", "es", "fr", "id", "pt-br", "ru", "th").getOrElse(edition) { "" }
    val chapters = mangaPlusChapters(detail, id, mangaPlusFields(data))
    logger.info { "MANGA Plus title $id: parsed ${chapters.size} distinct chapters from ${data.size} metadata bytes" }
    return chapters
      .mapIndexed { index, chapter ->
        val number =
          mangaPlusChapterNumber(chapter.name)
            ?: chapters
              .getOrNull(index - 1)
              ?.name
              ?.let(::mangaPlusChapterNumber)
              ?.toBigDecimalOrNull()
              ?.add("0.5".toBigDecimal())
              ?.toPlainString()
            ?: chapters
              .getOrNull(index + 1)
              ?.name
              ?.let(::mangaPlusChapterNumber)
              ?.toBigDecimalOrNull()
              ?.subtract("0.5".toBigDecimal())
              ?.toPlainString()
            ?: "0"
        SourceChapter(
          chapter.id.toString(),
          number.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString() ?: number,
          mangaPlusChapterSubtitle(chapter.subtitle),
          null,
          chapterLanguage,
          "$website/viewer/${chapter.id}",
          setOf("MangaPlus"),
        )
      }.asReversed()
      .sortedByDescending { it.number.toBigDecimalOrNull() ?: java.math.BigDecimal.ZERO }
  }

  override fun nextRelease(seriesId: String): Instant? = mangaPlusRelease(detail(titleId(seriesId)))

  override fun pages(chapterId: String): List<SourcePage> {
    val id = titleId(chapterId)
    val viewer = get("/manga_viewer_v3?chapter_id=$id&split=$split&img_quality=$imageQuality&viewer_mode=horizontal&clang=eng", "$website/viewer/$id")
    return mangaPlusViewerPages(viewer, imageHosts, http)
  }

  private fun detailResponse(id: Long): ByteArray = get("/title_detailV4?title_id=$id&clang=eng", "$website/titles/$id")

  private fun detail(id: Long): List<MangaPlusField> = parseDetail(detailResponse(id), id)

  private fun parseDetail(
    data: ByteArray,
    id: Long,
  ): List<MangaPlusField> =
    try {
      mangaPlusDetail(data, id)
    } catch (e: IllegalArgumentException) {
      throw IllegalStateException("MANGA Plus title detail invalid for $id: ${e.message}", e)
    }

  private fun series(title: List<MangaPlusField>): SourceSeries {
    val id = title.wireValue(1)
    val name = title.wireText(2).trim()
    check(id != null && id > 0 && name.isNotEmpty()) { "Invalid MANGA Plus title" }
    return SourceSeries(id.toString(), name)
  }

  private fun titleId(value: String): Long = requireNotNull(value.toLongOrNull()?.takeIf { it in 1..0xffff_ffffL }) { "Invalid MANGA Plus ID" }

  private fun get(
    path: String,
    referer: String,
  ): ByteArray {
    val headers =
      mapOf(
        "Referer" to referer,
        "Origin" to website,
        "Accept" to "application/x-protobuf, application/octet-stream, */*",
        "Session-Token" to session,
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36",
      )
    return http.bytes(URI.create(base + path), hosts, headers)
  }
}
