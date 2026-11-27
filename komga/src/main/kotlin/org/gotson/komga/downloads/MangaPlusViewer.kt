// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import java.net.URI
import java.util.HexFormat
import javax.imageio.ImageIO

/** Map the source's successful viewer response into ordered, reopenable page streams. */
internal fun mangaPlusViewerPages(
  data: ByteArray,
  imageHosts: Set<String>,
  http: ProviderHttp,
): List<SourcePage> {
  val viewer = mangaPlusFields(data).wireMessage(1).wireMessage(10)
  check(viewer.isNotEmpty()) { "MANGA Plus viewer did not return an available chapter" }
  // Use only the credential issued by this successful response. Never forge or reuse another chapter's token.
  val token = viewer.wireText(19).trim()
  check(token.isNotBlank() && token.length <= 8192 && token.none { it.isISOControl() }) {
    "MANGA Plus viewer did not return a valid image credential"
  }
  val pages =
    viewer.wireMessages(1).mapNotNull { item ->
      if (item.none { it.number == 1 && it.wire == 2 })
        null
      else
        item.wireMessage(1).also {
          check(it.isNotEmpty()) { "MANGA Plus viewer returned an incomplete image record" }
        }
    }
  check(pages.isNotEmpty() && pages.size <= 1000) { "MANGA Plus viewer returned no usable pages or too many pages" }
  val headers =
    mapOf(
      "Accept" to "*/*",
      "Accept-Language" to "en-GB,en;q=0.9",
      "Origin" to "https://mangaplus.shueisha.co.jp",
      "Referer" to "https://mangaplus.shueisha.co.jp/",
      "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:153.0) Gecko/20100101 Firefox/153.0",
      "Sec-Fetch-Dest" to "empty",
      "Sec-Fetch-Mode" to "cors",
      "Sec-Fetch-Site" to "cross-site",
      "Plus-Vw-Token" to token,
    )
  return pages.mapIndexed { index, page ->
    val uri = runCatching { URI.create(page.wireText(1)) }.getOrNull()
    requireNotNull(uri) { "MANGA Plus viewer returned an invalid image URL" }
    ProviderHttp.validateUri(uri, imageHosts)
    val extension = uri.path.substringAfterLast('.', "").lowercase()
    require(extension in setOf("jpg", "jpeg", "png", "webp", "gif", "avif", "bmp")) { "Unsupported MANGA Plus image extension" }
    val key =
      page.wireText(5).takeIf { it.isNotEmpty() }?.let {
        check(it.length <= 8192 && it.length % 2 == 0 && it.all { c -> c in "0123456789abcdefABCDEF" }) {
          "MANGA Plus viewer returned invalid image encoding metadata"
        }
        HexFormat.of().parseHex(it)
      }
    val filename = (index + 1).toString().padStart(4, '0') + "." + extension
    SourcePage(filename) {
      val bytes = http.imageBytes(uri, imageHosts, headers)
      val image = mangaPlusDecodeImage(bytes, key)
      image.inputStream()
    }
  }
}

/** Interpret the representation specified by the source's own successful viewer response. */
internal fun mangaPlusDecodeImage(
  bytes: ByteArray,
  key: ByteArray?,
): ByteArray {
  check(bytes.isNotEmpty()) { "MANGA Plus returned an empty image" }
  val image =
    if (key == null) {
      bytes
    } else {
      check(key.isNotEmpty()) { "MANGA Plus returned invalid image encoding metadata" }
      ByteArray(bytes.size) { i -> (bytes[i].toInt() xor key[i % key.size].toInt()).toByte() }
    }
  ImageIO.createImageInputStream(image.inputStream()).use { stream ->
    check(stream != null && ImageIO.getImageReaders(stream).hasNext()) {
      "MANGA Plus response is not a supported image"
    }
  }
  return image
}
