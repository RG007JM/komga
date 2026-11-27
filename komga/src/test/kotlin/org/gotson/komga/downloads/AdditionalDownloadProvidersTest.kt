package org.gotson.komga.downloads

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.ByteArrayOutputStream
import java.net.URI
import java.time.Instant
import java.util.zip.GZIPOutputStream

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AdditionalDownloadProvidersTest {
  private val mapper = ObjectMapper()
  private val http = mockk<ProviderHttp>()
  private val png = "https://i.imgur.com/fixture.png"
  private val raw = "https://raw.githubusercontent.com/example/owned-comics/main/manifest.json"

  private fun text(
    url: String,
    body: String,
  ) {
    every { http.text(URI.create(url), any(), any()) } returns body
  }

  private fun page(url: String) {
    every { http.page(URI.create(url), any(), any()) } returns SourcePage(url.substringAfterLast('/')) { byteArrayOf(1).inputStream() }
  }

  @Test
  fun `Cubari groups have stable IDs and retain ordered pages after new provider instance`() {
    text(
      raw,
      """{"title":"Fixture","chapters":{"1.5":{"title":"Test","release_date":{"Group A":"1767225600","Group B":1767225601},"groups":{"Group A":["$png","$png"],"Group B":["https://i.imgur.com/second.jpg"]}}}}""",
    )
    page(png)
    val provider = CubariDownloadProvider(mapper, http)
    val series = provider.search(raw).single()
    val all = provider.chapters(series.id, ChapterFilters())
    assertEqualValue(2, all.size)
    val only = provider.chapters(series.id, ChapterFilters(includeGroups = setOf("group a"))).single()
    assertEqualValue("1.5", only.number)
    assertEqualValue(Instant.parse("2026-01-01T00:00:00Z"), only.publishedAt)
    assertEqualValue(only.id, all.first { it.groups == setOf("Group A") }.id)
    assertEqualValue(2, CubariDownloadProvider(mapper, http).pages(only.id).size)
    assertEqualValue(32, downloadChapterFileId(only.id).length)
    assertTrueValue(downloadChapterFileId(all[0].id) != downloadChapterFileId(all[1].id))
    assertEqualValue("short-id", downloadChapterFileId("short-id"))
    assertEqualValue(1, provider.chapters(series.id, ChapterFilters(excludeGroups = setOf("GROUP B"))).size)
  }

  @Test
  fun `Cubari resolves proxy image lists and rejects unsupported manifest hosts`() {
    val source = "https://cubari.moe/read/gist/fixture/"
    text("https://cubari.moe/read/api/gist/series/fixture/", """{"title":"Fixture","chapters":{"2":{"groups":{"Owner":"/proxy/api/imgur/chapter/abc/"}}}}""")
    text("https://cubari.moe/proxy/api/imgur/chapter/abc/", """[{"src":"$png"}]""")
    page(png)
    val provider = CubariDownloadProvider(mapper, http)
    val series = provider.search(source).single()
    assertEqualValue("gist.fixture", series.id)
    assertEqualValue(1, provider.pages(provider.chapters(series.id, ChapterFilters()).single().id).size)
    assertThrowsType(IllegalArgumentException::class.java) { provider.search("https://localhost/manifest.json") }
    assertThrowsType(IllegalArgumentException::class.java) { provider.pages("incorrect.invalid") }
  }

  @Test
  fun `WeebCentral parses title and chapter links and ordered reader images`() {
    val seriesId = "01J76XYCPSY3C4BNPBRY8JMCBE"
    val chapterId = "01J76XYCPSY3C4BNPBRY8JMCBF"
    val base = "https://weebcentral.com"
    text("$base/search/data?author=&text=Fixture&sort=Best+Match&order=Descending&official=Any&anime=Any&adult=Any&display_mode=Full+Display", """<a href="/series/$seriesId/Fixture"><img alt="Fixture" /></a>""")
    text("$base/series/$seriesId/full-chapter-list", """<a href="/chapters/$chapterId"><span class="">Chapter 1.5 - Test</span><span>Last Read</span><time datetime="2026-01-01T00:00:00Z"></time></a>""")
    val first = "https://scans.lastation.us/fixture/002.png"
    val second = "https://scans-hot.planeptune.us/fixture/001.png"
    text("$base/chapters/$chapterId/images?is_prev=False&current_page=1&reading_style=long_strip", """<img src="$first"><img src="https://weebcentral.com/broken_image.jpg?foo=1"><img src="$second">""")
    page(first)
    page(second)
    val provider = WeebCentralDownloadProvider(http, mapper)
    assertEqualValue(SourceSeries(seriesId, "Fixture"), provider.search("Fixture").single())
    val chapter = provider.chapters(seriesId, ChapterFilters()).single()
    assertEqualValue(chapterId, chapter.id)
    assertEqualValue("1.5", chapter.number)
    assertEqualValue("Test", chapter.title)
    assertEqualValue(null, chapter.publishedAt)
    assertEqualValue(listOf(chapter), provider.chapters(seriesId, ChapterFilters(language = "fr", includeGroups = setOf("unknown"))))
    assertEqualValue(listOf("002.png", "001.png"), provider.pages(chapter.id).map { it.filename })
    verify { http.page(URI.create(first), match { "scans.lastation.us" in it }, any()) }
    ProviderHttp.validateUri(URI.create(first), WEEB_IMAGE_HOSTS)
  }

  @Test
  fun `WeebCentral reference search retains alternate title and canonical chapter URLs`() {
    val a = "01J76XYCPSY3C4BNPBRY8JMCBE"
    val b = "01J76XYC2K8QWFZZRYYCZMN6EF"
    val base = "https://weebcentral.com"
    val search = "$base/search/data?author=&text=Witch+Hat+Atelier&sort=Best+Match&order=Descending&official=Any&anime=Any&adult=Any&display_mode=Full+Display"
    text(search, """<nav><a href="/series/$b/tongari-booshi-no-atorie">Tongari Booshi No Atorie</a></nav><div><a href="/series/$a/witch-hat-atelier"><span>Witch Hat Atelier</span></a></div>""")
    text("$base/series/$a", """<h1>Witch Hat Atelier</h1>""")
    text("$base/series/$a/full-chapter-list", """{"chapters":[{"title":"Chapter 12 - The Return","url":"/chapters/$a/the-return"},{"title":"Chapter 11.5","url":"/chapters/$b/extra"}]}""")
    val provider = WeebCentralDownloadProvider(http, mapper)
    assertEqualValue(listOf(a, b), provider.search("Witch-Hat_Atelier").map { it.id })
    assertEqualValue("Witch Hat Atelier", provider.search("$base/series/$a/witch-hat-atelier").single().title)
    val chapters = provider.chapters(a, ChapterFilters(includeGroups = setOf("irrelevant")))
    assertEqualValue(listOf("12", "11.5"), chapters.map { it.number })
    assertEqualValue("The Return", chapters.first().title)
    assertEqualValue("$base/chapters/$a/the-return", chapters.first().url)
    verify { http.text(URI.create(search), any(), match { it["Accept-Language"] == "en-GB,en;q=0.9" }) }
  }

  @Test
  fun `Manga Plus V4 protobuf schedule and split chapter metadata`() {
    val base = "https://jumpg-webapi.tokyo-cdn.com/api"
    val title = wireInt(1, 1) + wireString(2, "Fixture")
    val chapter =
      wireInt(1, 1) + wireInt(2, 2) + wireString(3, "#139-2") + wireString(4, "Test") +
        wireString(5, "https://example.test/secure/title/1/chapter/2/chapter_thumbnail/a.jpg")
    val detail = wireBytes(1, title) + wireInt(5, 4102444800L) + wireBytes(47, wireBytes(2, chapter))
    every { http.bytes(URI.create("$base/title_detailV4?title_id=1&clang=eng"), any(), any()) } returns wireBytes(1, wireBytes(8, detail))
    val viewer = wireBytes(1, wireBytes(1, wireString(1, "https://jumpg-assets3.tokyo-cdn.com/a.png") + wireString(5, "protected")))
    every { http.bytes(URI.create("$base/manga_viewer_v3?chapter_id=2&split=no&img_quality=super_high&viewer_mode=horizontal&clang=eng"), any(), any()) } returns wireBytes(1, wireBytes(10, viewer))
    val provider = MangaPlusDownloadProvider(http)
    assertEqualValue("1", provider.search("1").single().id)
    assertEqualValue("139.2", provider.chapters("1", ChapterFilters()).single().number)
    assertEqualValue(Instant.parse("2100-01-01T00:00:00Z"), provider.nextRelease("1"))
    assertThrowsType(IllegalStateException::class.java) { provider.pages("2") }
    verify(exactly = 0) { http.page(any(), any(), any()) }
    assertThrowsType(IllegalArgumentException::class.java) { mangaPlusDetail(wireBytes(1, wireBytes(8, detail)), 99) }
    assertThrowsType(IllegalArgumentException::class.java) { mangaPlusFields(byteArrayOf(10, 5, 1)) }
  }

  @Test
  fun `source release window preserves announced timestamp and polling offsets`() {
    val release = Instant.parse("2026-01-01T00:00:00Z")
    assertEqualValue(release, SourceReleasePolling.next(release.minusSeconds(30), release))
    val offsets = listOf(0L, 1L, 2L, 3L, 4L, 5L, 10L, 15L, 20L, 25L, 30L)
    offsets.zipWithNext().forEach { (current, next) ->
      assertEqualValue(release.plusSeconds(next * 60), SourceReleasePolling.next(release.plusSeconds(current * 60), release))
    }
    val future = release.plusSeconds(86400)
    assertEqualValue(release, SourceReleasePolling.expected(release.plusSeconds(60), future, release))
    assertEqualValue(future, SourceReleasePolling.expected(release.plusSeconds(1800), future, release))
    assertEqualValue(release.plusSeconds(3600), SourceReleasePolling.next(release.plusSeconds(1800), release))
  }

  @Test
  fun `Mangathr parsing fixtures keep aliases and normalize chapter labels`() {
    assertEqualValue("Witch Hat Atelier", normalizeWeebQuery("Witch-Hat_Atelier"))
    assertTrueValue(weebSearchScore("Witch Hat Atelier", "Witch Hat") > weebSearchScore("Witch", "Witch Hat"))
    assertEqualValue("12.5" to "A new start", weebChapterParts("Chapter 12.5 - A new start Last Read 3 days ago"))
    assertTrueValue(isWeebBrokenImage("https://example.test/BROKEN_IMAGE.JPG?cache=1"))
    assertEqualValue("0" to "Pilot", weebChapterParts("Oneshot - Pilot"))
    assertEqualValue("98" to "", weebChapterParts("Chapter 98\r\nLast Read\r\n2026-08-06T02:30:47Z"))
    assertEqualValue(listOf("https://scans.lastation.us/fixture.png"), weebUsableImages(listOf("", "https://weebcentral.com/static/images/broken_image.jpg", "https://scans.lastation.us/fixture.png")))
    assertTrueValue(isProviderChallenge("Just a moment - Cloudflare"))
    assertFalseValue(isProviderChallenge("Normal manga page"))
  }

  @Test
  fun `Manga Plus catalogue selects first language edition and uses stable metadata session`() {
    val base = "https://jumpg-webapi.tokyo-cdn.com/api"
    val endpoint = URI.create("$base/title_list/allV2")
    val en = wireInt(1, 1) + wireString(2, "Fixture English")
    val fr = wireInt(1, 2) + wireString(2, "Fixture French") + wireInt(7, 2)
    val en2 = wireInt(1, 3) + wireString(2, "Fixture other English")
    val group = wireString(1, "Fixture") + wireBytes(2, fr) + wireBytes(2, en) + wireBytes(2, en2)
    val other = wireString(1, "Unrelated") + wireBytes(2, en)
    val payload = wireBytes(1, wireBytes(25, wireBytes(1, group) + wireBytes(1, other)))
    val sessions = mutableListOf<String>()
    every { http.bytes(endpoint, any(), any()) } answers {
      val headers = thirdArg<Map<String, String>>()
      sessions += requireNotNull(headers["Session-Token"])
      payload
    }
    val provider = MangaPlusDownloadProvider(http)
    assertEqualValue(listOf(SourceSeries("1", "Fixture English")), provider.search("fixture"))
    assertTrueValue(provider.search("English").isEmpty())
    assertEqualValue(1, sessions.distinct().size)
    assertEqualValue(listOf(SourceSeries("2", "Fixture French")), MangaPlusDownloadProvider(http, language = 2).search("Fixture"))
    assertThrowsType(IllegalStateException::class.java) { mangaPlusCatalog(wireBytes(2, byteArrayOf(1)), "Fixture", 0) }
  }

  @Test
  fun `Manga Plus legacy chapter blocks preserve first and last list order`() {
    val title = wireInt(1, 100056) + wireString(2, "Fixture")

    fun chapter(
      id: Long,
      name: String,
    ) = wireInt(1, 100056) + wireInt(2, id) + wireString(3, name) + wireString(4, "Test subtitle")
    val block = wireBytes(2, chapter(1001834, "#139")) + wireBytes(4, chapter(1001835, "Chapter #139-1"))
    val detail = mangaPlusDetail(wireBytes(1, wireBytes(8, wireBytes(1, title) + wireBytes(28, block))), 100056)
    assertEqualValue(listOf(1001834L, 1001835L), mangaPlusChapters(detail, 100056).map { it.id })
    assertEqualValue("139.1", mangaPlusChapterNumber("Chapter #139-1"))
    assertEqualValue("139.2", mangaPlusChapterNumber("Chapter #139-2"))
    assertEqualValue("The Return", mangaPlusChapterSubtitle("Chapter 12: The Return"))
  }

  @Test
  fun `transport decodes bounded gzip and replaces the default header`() {
    val bytes = "metadata fixture".toByteArray()
    val compressed = ByteArrayOutputStream().also { out -> GZIPOutputStream(out).use { it.write(bytes) } }.toByteArray()
    assertTrueValue(bytes.contentEquals(readProviderBody(compressed.inputStream(), "gzip", 1024)))
    assertThrowsType(IllegalStateException::class.java) { readProviderBody(compressed.inputStream(), "gzip", 3) }
    val request = providerRequest(URI.create("https://example.test/"), mapOf("User-Agent" to "Fixture/1")).GET().build()
    assertEqualValue(listOf("Fixture/1"), request.headers().allValues("User-Agent"))
  }

  private fun varint(value: Long): ByteArray {
    var current = value
    val bytes = mutableListOf<Byte>()
    while (current >= 128) {
      bytes += ((current and 127) or 128).toByte()
      current = current ushr 7
    }
    bytes += current.toByte()
    return bytes.toByteArray()
  }

  private fun wireInt(
    field: Int,
    value: Long,
  ) = varint((field * 8).toLong()) + varint(value)

  private fun wireBytes(
    field: Int,
    value: ByteArray,
  ) = varint((field * 8 + 2).toLong()) + varint(value.size.toLong()) + value

  private fun wireString(
    field: Int,
    value: String,
  ) = wireBytes(field, value.toByteArray(Charsets.UTF_8))

  @Test
  fun `transport rejects local or substituted hosts and non HTTPS URLs`() {
    val hosts = setOf("i.imgur.com")
    listOf("http://i.imgur.com/a.png", "https://localhost/a.png", "https://i.imgur.com.evil.example/a.png", "https://i.imgur.com:8443/a.png", "https://user@i.imgur.com/a.png", "https://i.imgur.com/../a.png", "https://i.imgur.com/%2e%2e/a.png").forEach {
      assertThrowsType(IllegalArgumentException::class.java) { ProviderHttp.validateUri(URI.create(it), hosts) }
    }
    ProviderHttp.validateUri(URI.create("https://i.imgur.com/a.png"), hosts)
    assertFalseValue(ChapterFilters(includeGroups = setOf("Known")).acceptsGroups(emptySet()))
  }
}
