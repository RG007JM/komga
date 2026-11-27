package org.gotson.komga.downloads

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URI
import java.time.Instant
import javax.imageio.ImageIO

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MangaPlusNativeTest {
  private val http = mockk<ProviderHttp>()
  private val secret = "0".repeat(32)
  private val provider = MangaPlusDownloadProvider(http, nativeSecret = secret)
  private val host = "jumpg-api.tokyo-cdn.com"
  private val titleId = 100056L
  private val date = 1_800_000_000L
  private val title = num(1, titleId) + str(2, "Fixture") + num(7, 0)

  @Test
  fun `native title search uses the standalone search endpoint first`() {
    every { http.nativeMangaPlusResponse(any(), setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(1, bytes(32, bytes(2, title))), null)
    assertEqualValue(listOf(SourceSeries(titleId.toString(), "Fixture")), provider.search("fixture"))
    verify(exactly = 1) { http.nativeMangaPlusResponse(match { it.host == host && it.path == "/api/title_list/search" }, setOf(host), any(), false) }
    verify(exactly = 0) { http.nativeMangaPlusResponse(match { it.path == "/api/title_list/all_v3" }, any(), any(), false) }
  }

  @Test
  fun `empty successful search loads the three catalogue buckets`() {
    every { http.nativeMangaPlusResponse(match { it.path == "/api/title_list/search" }, setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(1, byteArrayOf()), null)
    every { http.nativeMangaPlusResponse(match { it.path == "/api/title_list/all_v3" }, setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(1, bytes(32, bytes(2, title))), null)
    assertEqualValue(listOf(SourceSeries(titleId.toString(), "Fixture")), provider.search("fixture"))
    verify(exactly = 3) { http.nativeMangaPlusResponse(match { it.host == host && it.path == "/api/title_list/all_v3" && it.query.contains("lang=eng") }, setOf(host), any(), false) }
  }

  @Test
  fun `catalogue error envelope stops search and does not fall back`() {
    every { http.nativeMangaPlusResponse(any(), setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(2, str(1, "Denied")), null)
    assertThrowsType(IllegalStateException::class.java) { provider.search("fixture") }
    verify(exactly = 1) { http.nativeMangaPlusResponse(any(), setOf(host), any(), false) }
    verify(exactly = 0) { http.nativeMangaPlusResponse(match { it.path == "/api/title_list/all_v3" }, any(), any(), false) }
  }

  @Test
  fun `native catalogue applies proto3 default language when field is omitted`() {
    val implicitEnglish = num(1, titleId) + str(2, "Fixture")
    val french = num(1, titleId + 1) + str(2, "French fixture") + num(7, 2)
    val malformed = num(1, titleId + 2) + str(2, "Invalid language fixture") + str(7, "invalid")
    val catalogue = bytes(1, bytes(32, bytes(2, implicitEnglish) + bytes(2, title) + bytes(2, french) + bytes(2, malformed)))
    assertEqualValue(listOf(SourceSeries(titleId.toString(), "Fixture")), mangaPlusNativeTitles(catalogue, 0))
    assertEqualValue(listOf(SourceSeries((titleId + 1).toString(), "French fixture")), mangaPlusNativeTitles(catalogue, 2))
    assertTrueValue(mangaPlusNativeTitles(catalogue, 1).isEmpty())
    assertThrowsType(IllegalStateException::class.java) { mangaPlusNativeTitles(catalogue + bytes(2, str(1, "Denied")), 0) }
  }

  @Test
  fun `catalogue diagnostics distinguish missing success and error envelopes`() {
    val denied = runCatching { mangaPlusNativeTitles(bytes(2, str(1, "PRIVATE_FIXTURE")), 0) }.exceptionOrNull()
    assertTrueValue(denied is IllegalStateException)
    assertTrueValue(denied?.message.orEmpty().contains("error envelope"))
    assertTrueValue(!denied?.message.orEmpty().contains("PRIVATE_FIXTURE"))
    val unknown = runCatching { mangaPlusNativeTitles(bytes(35, title), 0) }.exceptionOrNull()
    assertTrueValue(unknown?.message.orEmpty().contains("lacks a unique success envelope"))
    assertTrueValue(mangaPlusNativeTitles(bytes(1, byteArrayOf()), 0).isEmpty())
  }

  @Test
  fun `native chapter list uses publication ordering and official release time`() {
    val detail = bytes(1, title) + num(5, date + 86400) + bytes(47, bytes(2, chapter(1, "#132", date)) + bytes(2, chapter(2, "#130-1", date + 100)))
    every { http.nativeMangaPlusResponse(any(), setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(1, bytes(8, detail)), null)
    val chapters = provider.chapters(titleId.toString(), ChapterFilters())
    assertEqualValue(listOf("130.1", "132"), chapters.map { it.number })
    assertEqualValue(Instant.ofEpochSecond(date + 100), chapters.first().publishedAt)
    assertEqualValue(Instant.ofEpochSecond(date + 86400), provider.nextRelease(titleId.toString()))
    assertTrueValue(!provider.pagePreviewSupported)
    assertTrueValue(MangaPlusDownloadProvider(http).pagePreviewSupported)
    verify(exactly = 2) { http.nativeMangaPlusResponse(match { it.host == host && it.path == "/api/title_detailV3" }, setOf(host), any(), false) }
  }

  @Test
  fun `native free reading viewer accepts only successful chapter and preserves actual image format`() {
    val imageUrl = "https://jumpg-assets.tokyo-cdn.com/secure/title/$titleId/chapter/2/manga_page/fixture"
    val png = ByteArrayOutputStream().also { ImageIO.write(BufferedImage(5, 6, BufferedImage.TYPE_INT_RGB), "png", it) }.toByteArray()
    every { http.nativeMangaPlusResponse(match { it.host == host && it.path == "/api/manga_viewer_v3" && it.query.contains("free_reading=yes") && it.query.contains("ticket_reading=no") && it.query.contains("subscription_reading=no") }, setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(1, bytes(10, bytes(1, bytes(1, str(1, imageUrl))))), "fixture-cookie")
    every { http.nativeMangaPlusResponse(URI.create(imageUrl), any(), match { it["Cookie"] == "plus_vw_token=fixture-cookie" }, true) } returns NativeMangaPlusResponse(png, null)
    val pages = provider.pages("2")
    assertEqualValue("0001.png", pages.single().filename)
    assertTrueValue(pages.single().open().use { it.readBytes().contentEquals(png) })
    verify(exactly = 1) { http.nativeMangaPlusResponse(URI.create(imageUrl), any(), any(), true) }
  }

  @Test
  fun `native denial does not fetch any pages or retry via web`() {
    every { http.nativeMangaPlusResponse(any(), setOf(host), any(), false) } returns NativeMangaPlusResponse(bytes(2, str(1, "Denied")), null)
    assertThrowsType(IllegalStateException::class.java) { provider.pages("2") }
    verify(exactly = 0) { http.nativeMangaPlusResponse(any(), any(), any(), true) }
    verify(exactly = 0) { http.bytes(any(), any(), any()) }
  }

  @Test
  fun `native ordinary images retain their bytes and still require an image reader`() {
    val png =
      ByteArrayOutputStream()
        .also {
          ImageIO.write(BufferedImage(5, 6, BufferedImage.TYPE_INT_RGB), "png", it)
        }.toByteArray()
    assertEqualValue("png", nativeMangaPlusImageSignature(png))
    assertTrueValue(nativeMangaPlusImage(png, null, 1).contentEquals(png))
    assertTrueValue(nativeMangaPlusImage(png, byteArrayOf(1), 1).contentEquals(png))
    assertEqualValue("webp", nativeMangaPlusImageSignature("RIFF0000WEBP".toByteArray()))
    assertThrowsType(IllegalStateException::class.java) {
      nativeMangaPlusImage("not an image".toByteArray(), null, 3)
    }
  }

  private fun chapter(
    id: Long,
    name: String,
    timestamp: Long,
  ): ByteArray = num(1, titleId) + num(2, id) + str(3, name) + str(4, "Subtitle") + str(5, "/secure/title/$titleId/chapter/$id/chapter_thumbnail/fixture.jpg") + num(6, timestamp)

  private fun v(value: Long): ByteArray {
    var current = value
    val result = mutableListOf<Byte>()
    while (current >= 128) {
      result += ((current.toInt() and 127) or 128).toByte()
      current = current ushr 7
    }
    result += current.toByte()
    return result.toByteArray()
  }

  private fun num(
    field: Int,
    value: Long,
  ): ByteArray = v(field.toLong() * 8) + v(value)

  private fun bytes(
    field: Int,
    value: ByteArray,
  ): ByteArray = v(field.toLong() * 8 + 2) + v(value.size.toLong()) + value

  private fun str(
    field: Int,
    value: String,
  ): ByteArray = bytes(field, value.toByteArray())
}
