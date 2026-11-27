package org.gotson.komga.downloads

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URI
import javax.imageio.ImageIO

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MangaPlusViewerTest {
  private val http = mockk<ProviderHttp>()
  private val host = "jumpg-assets3.tokyo-cdn.com"
  private val hosts = setOf(host)
  private val a = "https://$host/fixture/1.png"
  private val b = "https://$host/fixture/2.png"

  @Test
  fun `source viewer metadata supplies ordered reopenable authorized pages`() {
    val png =
      ByteArrayOutputStream()
        .also {
          ImageIO.write(BufferedImage(4, 6, BufferedImage.TYPE_INT_RGB), "png", it)
        }.toByteArray()
    val fixtureKey = byteArrayOf(1, 2, 3)
    val encoded = ByteArray(png.size) { (png[it].toInt() xor fixtureKey[it % fixtureKey.size].toInt()).toByte() }
    val response = viewer(page(a, "010203") + wireBytes(1, byteArrayOf()) + page(b), "fixture-token")
    every { http.imageBytes(URI.create(a), hosts, any()) } returns encoded
    every { http.imageBytes(URI.create(b), hosts, any()) } returns png
    val pages = mangaPlusViewerPages(response, hosts, http)
    assertEqualValue(listOf("0001.png", "0002.png"), pages.map { it.filename })
    verify(exactly = 0) { http.imageBytes(any(), any(), any()) }
    repeat(2) {
      pages.forEach { assertTrueValue(it.open().use { stream -> stream.readAllBytes() }.contentEquals(png)) }
    }
    verify(exactly = 2) { http.imageBytes(URI.create(a), hosts, match { it["Plus-Vw-Token"] == "fixture-token" && it["Origin"] == "https://mangaplus.shueisha.co.jp" }) }
  }

  @Test
  fun `missing viewer credential invalid metadata and untrusted host fail before fetching`() {
    assertThrowsType(IllegalStateException::class.java) { mangaPlusViewerPages(viewer(page(a), ""), hosts, http) }
    assertThrowsType(IllegalStateException::class.java) { mangaPlusViewerPages(viewer(page(a, "not-hex"), "fixture-token"), hosts, http) }
    assertThrowsType(IllegalArgumentException::class.java) { mangaPlusViewerPages(viewer(page("https://example.test/a.png"), "fixture-token"), hosts, http) }
    verify(exactly = 0) { http.imageBytes(any(), any(), any()) }
  }

  @Test
  fun `image validation rejects error documents`() {
    validateProviderImageContentType("application/octet-stream")
    validateProviderImageContentType("image/png; charset=binary")
    assertThrowsType(IllegalStateException::class.java) { validateProviderImageContentType("application/json") }
    assertThrowsType(IllegalStateException::class.java) { mangaPlusDecodeImage("not an image".toByteArray(), null) }
  }

  @Test
  fun `viewer request uses same source session as metadata`() {
    val base = "https://jumpg-webapi.tokyo-cdn.com/api"
    val catalogue = URI.create("$base/title_list/allV2")
    val chapter = URI.create("$base/manga_viewer_v3?chapter_id=2&split=no&img_quality=super_high&viewer_mode=horizontal&clang=eng")
    val calls = mutableListOf<String>()
    val title = byteArrayOf(8, 1) + wireString(2, "Fixture")
    val group = wireString(1, "Fixture") + wireBytes(2, title)
    every { http.bytes(catalogue, any(), any()) } answers {
      calls += requireNotNull(thirdArg<Map<String, String>>()["Session-Token"])
      wireBytes(1, wireBytes(25, wireBytes(1, group)))
    }
    every { http.bytes(chapter, any(), any()) } answers {
      calls += requireNotNull(thirdArg<Map<String, String>>()["Session-Token"])
      viewer(page(a), "fixture-token")
    }
    val provider = MangaPlusDownloadProvider(http)
    assertEqualValue("1", provider.search("Fixture").single().id)
    assertEqualValue(1, provider.pages("2").size)
    assertEqualValue(2, calls.size)
    assertEqualValue(1, calls.distinct().size)
  }

  private fun viewer(
    pages: ByteArray,
    token: String,
  ): ByteArray = wireBytes(1, wireBytes(10, pages + wireString(19, token)))

  private fun page(
    url: String,
    key: String = "",
  ): ByteArray = wireBytes(1, wireBytes(1, wireString(1, url) + wireString(5, key)))

  private fun varint(value: Int): ByteArray {
    var current = value
    val bytes = mutableListOf<Byte>()
    while (current >= 128) {
      bytes += ((current and 127) or 128).toByte()
      current = current ushr 7
    }
    bytes += current.toByte()
    return bytes.toByteArray()
  }

  private fun wireBytes(
    field: Int,
    value: ByteArray,
  ): ByteArray = varint(field * 8 + 2) + varint(value.size) + value

  private fun wireString(
    field: Int,
    value: String,
  ): ByteArray = wireBytes(field, value.toByteArray())
}
