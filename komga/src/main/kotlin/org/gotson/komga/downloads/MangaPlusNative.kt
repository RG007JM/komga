// Adapted from the user-supplied Mangathr native downloader.
package org.gotson.komga.downloads

import io.github.oshai.kotlinlogging.KotlinLogging
import java.net.URI
import java.time.Instant
import java.util.HexFormat
import javax.imageio.ImageIO

private val nativeCatalogueLogger = KotlinLogging.logger {}

internal class MangaPlusNative(
  private val http: ProviderHttp,
  private val secret: String,
  private val osVersion: String,
  private val appVersion: String,
  private val countryCode: String,
  private val language: Int,
) {
  init {
    require(language in 0..6) { "Invalid native language" }
    require(secret.matches(Regex("[0-9a-fA-F]{32}"))) { "Invalid MANGA Plus native device credential" }
    require(osVersion.matches(Regex("[0-9]{1,6}")) && appVersion.matches(Regex("[0-9]{1,6}"))) { "Invalid native client version" }
    require(countryCode.matches(Regex("[A-Z]{2}"))) { "Invalid native country code" }
  }

  private val languageCode = listOf("eng", "spa", "fra", "ind", "por", "rus", "tha")[language]
  private val chapterLanguage = listOf("en", "es", "fr", "id", "pt-br", "ru", "th")[language]
  private val apiHost = "jumpg-api.tokyo-cdn.com"
  private val assetHosts = setOf("jumpg-assets.tokyo-cdn.com", "jumpg-assets2.tokyo-cdn.com", "jumpg-assets3.tokyo-cdn.com")
  private val headers = mapOf("Accept" to "*/*", "User-Agent" to "okhttp/4.12.0")

  private fun request(
    endpoint: String,
    parameters: Map<String, String>,
  ): NativeMangaPlusResponse {
    val query =
      (mapOf("os" to "android", "os_ver" to osVersion, "app_ver" to appVersion, "secret" to secret, "country_code" to countryCode) + parameters)
        .entries
        .joinToString("&") { (key, value) -> "$key=${encodeProviderQuery(value)}" }
    return http.nativeMangaPlusResponse(URI.create("https://$apiHost/api/$endpoint?$query"), setOf(apiHost), headers, false)
  }

  fun detail(id: Long): ByteArray = request("title_detailV3", mapOf("title_id" to id.toString(), "lang" to languageCode, "clang" to languageCode)).body

  fun search(query: String): List<SourceSeries> {
    val languageParameters = mapOf("lang" to languageCode, "clang" to languageCode)
    // The standalone native downloader tries SearchView before the catalogue buckets.
    val primary = catalogue("title_list/search", languageParameters, "search")
    val titles =
      if (primary.isNotEmpty()) {
        primary
      } else {
        // Only a valid, successful response with no recognized titles may use this fallback.
        // An error envelope, network failure or rate limit stops the operation.
        listOf("serializing", "completed", "one_shot").flatMap { bucket ->
          catalogue("title_list/all_v3", languageParameters + ("type" to bucket), bucket)
        }
      }.distinctBy { it.id }
    check(titles.isNotEmpty()) { "Native MANGA Plus catalogue returned no recognizable titles for the configured language" }
    return titles.filter { it.title.contains(query, ignoreCase = true) }.sortedBy { it.title.lowercase() }
  }

  private fun catalogue(
    endpoint: String,
    parameters: Map<String, String>,
    label: String,
  ): List<SourceSeries> {
    val data = request(endpoint, parameters).body
    val titles =
      try {
        mangaPlusNativeTitles(data, language)
      } catch (e: IllegalStateException) {
        throw IllegalStateException("Native MANGA Plus catalogue ($label): ${e.message}", e)
      } catch (e: IllegalArgumentException) {
        throw IllegalStateException("Native MANGA Plus catalogue ($label): malformed protobuf (${data.size} bytes)", e)
      }
    nativeCatalogueLogger.info { "MANGA Plus native catalogue ($label): ${titles.size} distinct titles from ${data.size} metadata bytes" }
    return titles
  }

  fun chapters(id: Long): List<SourceChapter> {
    val raw = detail(id)
    val entries = mangaPlusNativeChapters(raw, id)
    return entries
      .mapIndexed { index, chapter ->
        val number =
          nativeMangaPlusNumber(chapter.name)
            ?: entries
              .getOrNull(index - 1)
              ?.name
              ?.let(::nativeMangaPlusNumber)
              ?.toBigDecimalOrNull()
              ?.add("0.5".toBigDecimal())
              ?.toPlainString()
            ?: entries
              .getOrNull(index + 1)
              ?.name
              ?.let(::nativeMangaPlusNumber)
              ?.toBigDecimalOrNull()
              ?.subtract("0.5".toBigDecimal())
              ?.toPlainString()
            ?: "0"
        SourceChapter(
          chapter.id.toString(),
          number.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString() ?: number,
          mangaPlusChapterSubtitle(chapter.subtitle),
          chapter.publishedAt,
          chapterLanguage,
          "https://mangaplus.shueisha.co.jp/viewer/${chapter.id}",
          setOf("MangaPlus"),
        )
      }.sortedByDescending { it.publishedAt }
  }

  fun nextRelease(id: Long): Instant? {
    val detail = mangaPlusNativeSuccess(detail(id), 8)
    require(detail.wireMessage(1).wireValue(1) == id) { "Native title detail ID mismatch" }
    return nativeMangaPlusTimestamp(detail.wireValue(5))
  }

  fun pages(
    id: Long,
    split: String,
    quality: String,
  ): List<SourcePage> {
    val response =
      request(
        "manga_viewer_v3",
        mapOf(
          "chapter_id" to id.toString(),
          "split" to split,
          "img_quality" to quality,
          "ticket_reading" to "no",
          "free_reading" to "yes",
          "subscription_reading" to "no",
          "viewer_mode" to "horizontal",
          "clang" to languageCode,
        ),
      )
    val viewer = mangaPlusNativeSuccess(response.body, 10)
    val token = viewer.wireText(19).trim().takeIf { it.isNotEmpty() }
    val cookie = response.viewerCookie ?: token
    require(token == null || validNativeToken(token)) { "Invalid native viewer token" }
    require(cookie == null || validNativeToken(cookie)) { "Invalid native viewer cookie" }
    val imageHeaders = headers + listOfNotNull(token?.let { "Plus-Vw-Token" to it }, cookie?.let { "Cookie" to "plus_vw_token=$it" }).toMap()
    val pages = nativeMangaPlusPageRecords(viewer, id, assetHosts)
    // Like the original native CLI, inspect the actual image type, not the URL suffix.
    // Bound the per-chapter memory footprint; the common archiver validates every image again.
    var total = 0L
    return pages.mapIndexed { index, page ->
      val body = http.nativeMangaPlusResponse(page.uri, assetHosts, imageHeaders, true).body
      val image = nativeMangaPlusImage(body, page.key, index + 1)
      total += image.size
      check(total <= 256L * 1024 * 1024) { "Native chapter exceeds the 256 MiB staging limit" }
      val extension = nativeImageExtension(image)
      SourcePage("${(index + 1).toString().padStart(4, '0')}.$extension") { image.inputStream() }
    }
  }
}

internal fun mangaPlusNativeSuccess(
  data: ByteArray,
  field: Int,
): List<MangaPlusField> {
  val envelope = mangaPlusFields(data)
  check(envelope.none { it.number == 2 && it.wire == 2 }) { "MANGA Plus native request was denied or unavailable" }
  return envelope.wireMessage(1).wireMessage(field).also {
    check(it.isNotEmpty()) { "MANGA Plus native response did not contain the requested success record" }
  }
}

/** The native CLI's bounded wire fallback: verify title and chapter IDs in the thumbnail path. */
internal fun mangaPlusNativeChapters(
  data: ByteArray,
  titleId: Long,
): List<MangaPlusChapter> {
  val detail = mangaPlusNativeSuccess(data, 8)
  require(detail.wireMessage(1).wireValue(1) == titleId) { "Native title detail ID mismatch" }
  val found = linkedMapOf<Long, MangaPlusChapter>()
  var visited = 0

  fun walk(
    fields: List<MangaPlusField>,
    depth: Int,
  ) {
    if (depth > 14) return
    check(++visited <= 100_000) { "Native chapter traversal limit exceeded" }
    val id = fields.wireValue(2)
    val name = fields.wireText(3).trim()
    if (fields.wireValue(1) == titleId && id != null && id in 1..0xffff_ffffL && name.isNotEmpty() &&
      fields.any { it.number == 4 && it.wire == 2 } && fields.wireText(5).contains("/secure/title/$titleId/chapter/$id/chapter_thumbnail/")
    ) {
      found.putIfAbsent(id, MangaPlusChapter(id, name, fields.wireText(4).trim(), nativeMangaPlusTimestamp(fields.wireValue(6))))
    }
    fields.filter { it.wire == 2 && it.bytes.isNotEmpty() }.forEach { field ->
      runCatching { mangaPlusFields(field.bytes) }.getOrNull()?.let { walk(it, depth + 1) }
    }
  }
  walk(mangaPlusFields(data), 0)
  // Retain known typed chapter blocks from the bundled Go schema as well.
  for (block in detail.filter { it.number == 28 && it.wire == 2 }.mapNotNull { runCatching { mangaPlusFields(it.bytes) }.getOrNull() }) {
    for (field in listOf(2, 4)) {
      for (chapter in block.filter { it.number == field && it.wire == 2 }.mapNotNull { runCatching { mangaPlusFields(it.bytes) }.getOrNull() }) {
        val id = chapter.wireValue(2) ?: continue
        val name = chapter.wireText(3).trim()
        if (chapter.wireValue(1) == titleId && id in 1..0xffff_ffffL && name.isNotBlank()) {
          found.putIfAbsent(id, MangaPlusChapter(id, name, chapter.wireText(4).trim(), nativeMangaPlusTimestamp(chapter.wireValue(6))))
        }
      }
    }
  }
  check(found.isNotEmpty()) { "No recognizable native chapter entries" }
  return found.values.toList()
}

internal fun nativeMangaPlusTimestamp(value: Long?): Instant? {
  val raw = value ?: return null
  val seconds = if (raw > 100_000_000_000L) raw / 1000 else raw
  return seconds.takeIf { it in 946_684_800L until 4_102_444_800L }?.let(Instant::ofEpochSecond)
}

/** Port of extractWireTitles from the native release-date Go patch. */
internal fun mangaPlusNativeTitles(
  data: ByteArray,
  language: Int,
): List<SourceSeries> {
  val envelope = mangaPlusFields(data)
  // Log only field numbers, wire types and byte lengths, never values, raw bytes or URLs.
  val shape =
    envelope.take(16).joinToString(", ") { field ->
      "${field.number}/${field.wire}:${field.bytes.size}"
    }
  val diagnostic = "${data.size} bytes; top-level field/wire:length=[$shape]"
  check(envelope.none { it.number == 2 && it.wire == 2 }) {
    "response contains an error envelope ($diagnostic); request stopped"
  }
  check(envelope.count { it.number == 1 && it.wire == 2 } == 1) {
    "response lacks a unique success envelope ($diagnostic)"
  }
  val success = envelope.wireMessage(1)
  val found = linkedMapOf<Long, SourceSeries>()
  var visited = 0

  fun walk(
    fields: List<MangaPlusField>,
    depth: Int,
  ) {
    if (depth > 14) return
    check(++visited <= 100_000) { "Native title traversal limit exceeded" }
    val id = fields.wireValue(1)
    val nameBytes = fields.firstOrNull { it.number == 2 && it.wire == 2 }?.bytes
    val name = nameBytes?.toString(Charsets.UTF_8)?.trim().orEmpty()
    if (id != null && id in 1..0xffff_ffffL && (fields.wireValue(7) ?: 0L) == language.toLong() && fields.none { it.number == 7 && it.wire != 0 } &&
      nameBytes != null && nameBytes.size in 1..299 && '\uFFFD' !in name && name.isNotEmpty() &&
      name.none { it.isISOControl() } && !name.startsWith("http://") && !name.startsWith("https://")
    ) {
      found.putIfAbsent(id, SourceSeries(id.toString(), name))
    }
    fields.filter { it.wire == 2 && it.bytes.isNotEmpty() }.forEach { field ->
      runCatching { mangaPlusFields(field.bytes) }.getOrNull()?.let { walk(it, depth + 1) }
    }
  }
  walk(success, 0)
  return found.values.toList()
}

internal fun nativeMangaPlusNumber(name: String): String? =
  Regex("#\\s*(\\d+(?:-\\d+|\\.\\d+)?)")
    .find(name)
    ?.groupValues
    ?.get(1)
    ?.replace('-', '.')
    ?: Regex("(?:chapter|mission|episode)\\s*#?\\s*(\\d+(?:-\\d+|\\.\\d+)?)", RegexOption.IGNORE_CASE)
      .find(name)
      ?.groupValues
      ?.get(1)
      ?.replace('-', '.')
    ?: mangaPlusChapterNumber(name)

internal class NativeMangaPlusPage(
  val uri: URI,
  val key: ByteArray?,
)

internal fun nativeMangaPlusPageRecords(
  viewer: List<MangaPlusField>,
  chapterId: Long,
  hosts: Set<String>,
): List<NativeMangaPlusPage> {
  val pages = viewer.wireMessages(1).filter { item -> item.any { it.number == 1 && it.wire == 2 } }.map { it.wireMessage(1) }
  check(pages.isNotEmpty() && pages.size <= 1000) { "Native viewer has no usable pages or exceeds the page limit" }
  var titleId: String? = null
  return pages.map { page ->
    // The known native MangaPage fields are the primary representation.
    val uri = runCatching { URI.create(page.wireText(1)) }.getOrNull() ?: error("Invalid native image URL")
    ProviderHttp.validateUri(uri, hosts)
    val path = requireNotNull(Regex("/secure/title/([0-9]+)/chapter/$chapterId/manga_page/.+").matchEntire(uri.path)) { "Native page URL does not match the requested chapter" }
    val title = path.groupValues[1]
    require(titleId == null || titleId == title) { "Native viewer mixed different titles" }
    titleId = title
    val key =
      page.wireText(5).takeIf { it.isNotBlank() }?.let {
        require(it.length in 2..8192 && it.length % 2 == 0 && it.all { c -> c in "0123456789abcdefABCDEF" }) { "Invalid native image encoding metadata" }
        HexFormat.of().parseHex(it)
      }
    NativeMangaPlusPage(uri, key)
  }
}

/** Preserve ordinary image responses; retain the existing decoder and validation for other representations. */
internal fun nativeMangaPlusImage(
  body: ByteArray,
  key: ByteArray?,
  pageNumber: Int,
): ByteArray {
  val format = nativeMangaPlusImageSignature(body)
  return try {
    if (format != "unknown") mangaPlusDecodeImage(body, null) else mangaPlusDecodeImage(body, key)
  } catch (e: IllegalStateException) {
    if (e.message != "MANGA Plus response is not a supported image") throw e
    error("Native MANGA Plus page $pageNumber: unsupported image (rawFormat=$format, bytes=${body.size}, representationMetadata=${key != null}); request stopped")
  }
}

/** Diagnostic only: a signature never substitutes for ImageIO or archive validation. */
internal fun nativeMangaPlusImageSignature(body: ByteArray): String {
  fun matches(
    offset: Int,
    signature: ByteArray,
  ): Boolean = body.size >= offset + signature.size && signature.indices.all { body[offset + it] == signature[it] }

  fun ascii(
    offset: Int,
    signature: String,
  ): Boolean = matches(offset, signature.toByteArray(Charsets.US_ASCII))
  return when {
    matches(0, byteArrayOf(0xff.toByte(), 0xd8.toByte(), 0xff.toByte())) -> "jpeg"
    matches(0, byteArrayOf(0x89.toByte(), 0x50, 0x4e, 0x47, 13, 10, 26, 10)) -> "png"
    ascii(0, "GIF87a") || ascii(0, "GIF89a") -> "gif"
    ascii(0, "RIFF") && ascii(8, "WEBP") -> "webp"
    ascii(0, "BM") -> "bmp"
    ascii(4, "ftyp") && (ascii(8, "avif") || ascii(8, "avis")) -> "avif"
    else -> "unknown"
  }
}

private fun validNativeToken(value: String): Boolean = value.length in 1..8192 && value.all { it.code in 33..126 && it !in ";,\"\\" }

private fun nativeImageExtension(bytes: ByteArray): String =
  ImageIO.createImageInputStream(bytes.inputStream()).use { stream ->
    val readers = ImageIO.getImageReaders(stream)
    check(readers.hasNext()) { "Unsupported native image representation" }
    val reader = readers.next()
    try {
      when (reader.formatName.lowercase()) {
        "jpeg", "jpg" -> "jpg"
        "png", "webp", "gif", "bmp", "avif" -> reader.formatName.lowercase()
        else -> error("Unsupported native image format")
      }
    } finally {
      reader.dispose()
    }
  }
