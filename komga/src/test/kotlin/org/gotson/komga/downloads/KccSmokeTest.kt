package org.gotson.komga.downloads

import io.mockk.every
import io.mockk.mockk
import org.apache.pdfbox.Loader
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.downloads.kcc.KccRuntime
import org.gotson.komga.infrastructure.configuration.KomgaProperties
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.util.Base64
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO
import javax.xml.parsers.DocumentBuilderFactory

/** Opt-in real KCC conversion, with no network or native ImageIO decoder needed for the fixture. */
@EnabledIfEnvironmentVariable(named = "KOMGA_TEST_KCC", matches = "true")
class KccSmokeTest {
  @TempDir
  lateinit var root: Path

  @ParameterizedTest
  @ValueSource(strings = ["CBZ", "EPUB", "PDF"])
  fun `real KCC converts mixed pages and preserves the original and Unicode metadata`(format: String) {
    val library = Files.createDirectory(root.resolve("library")).toRealPath()
    val sourceDir = Files.createDirectory(library.resolve("source"))
    val source = sourceDir.resolve("Chapter 7.5 - Unicode\u30fbchapter.cbz")
    val chapter = SourceChapter("fixture", "7.5", "Unicode\u30fbchapter", null, "en", "https://example.org/chapter")
    val title = "Fixture \u65e5\u672c\u8a9e"
    writeFixture(source, title, chapter.title)
    val original = Files.readAllBytes(source)
    val libraryUrl = library.toUri().toURL()
    val repository = mockk<LibraryRepository>()
    every { repository.findByIdOrNull("fixture-library") } returns
      mockk {
        every { root } returns libraryUrl
      }
    val files = DownloadFiles(repository)
    val config = root.resolve("config")
    val properties = mockk<KomgaProperties>()
    every { properties.configDir } returns config.toString()
    val runtime = KccRuntime(properties)
    val converter = DownloadKcc(files, runtime)
    val record =
      DownloadRecord(
        id = "fixture-record",
        provider = "fixture",
        seriesId = "fixture-series",
        title = title,
        chapter = chapter,
        destination = DownloadDestination("fixture-library", "output"),
        file = library.relativize(source).toString(),
        downloadedAt = Instant.EPOCH,
        mode = "manual",
        scan = "Pending",
      )
    val profile =
      KccProfile(
        "fixture-profile",
        "Fixture",
        "KoLC",
        format = format,
        extraArgs = listOf("--forcecolor", "--cropping=0", "--keepcomicinfo=1", "--metadatatitle=2"),
      )
    val published =
      try {
        converter.convert(record, profile)
      } catch (failure: Exception) {
        val logs = config.resolve("kcc/logs")
        val detail =
          if (Files.isDirectory(logs)) {
            Files.list(logs).use { paths ->
              paths.sorted().toList().joinToString("\n") { log ->
                Files.newInputStream(log).use { it.readNBytes(8192).toString(Charsets.UTF_8) }
              }
            }
          } else {
            "No conversion log was created. Verify KOMGA_KCC_C2E and the archive utility."
          }
        throw AssertionError("KCC $format conversion failed: $detail", failure)
      }
    assertEqualValue(1, published.size)
    val output = library.resolve(published.single())
    assertEqualValue(library.resolve("output"), output.parent)
    assertTrueValue(output.fileName.toString().startsWith("Chapter 7.5 - Unicode\u30fbchapter."))
    assertTrueValue(Files.readAllBytes(source).contentEquals(original))
    converter.validateOutput(output, format)
    when (format) {
      "PDF" -> Loader.loadPDF(output.toFile()).use { assertEqualValue(3, it.numberOfPages) }
      "CBZ" ->
        ZipFile(output.toFile()).use { zip ->
          assertEqualValue(3, zip.entries().asSequence().count { it.name.substringAfterLast('.').lowercase() in setOf("jpg", "jpeg", "png", "webp") })
          val metadata = zip.getInputStream(checkNotNull(zip.getEntry("ComicInfo.xml"))).use { it.readBytes().toString(Charsets.UTF_8) }
          assertTrueValue(metadata.contains(chapter.title))
          assertTrueValue(metadata.contains(title))
        }
      "EPUB" ->
        ZipFile(output.toFile()).use { zip ->
          val factory = DocumentBuilderFactory.newInstance().apply { isNamespaceAware = true }
          val container = zip.getInputStream(checkNotNull(zip.getEntry("META-INF/container.xml"))).use { factory.newDocumentBuilder().parse(it) }
          val packagePath =
            container
              .getElementsByTagNameNS("*", "rootfile")
              .item(0)
              .attributes
              .getNamedItem("full-path")
              .nodeValue
          val opf = zip.getInputStream(checkNotNull(zip.getEntry(packagePath))).use { factory.newDocumentBuilder().parse(it) }
          assertEqualValue(DownloadFileNames.stem(chapter), opf.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "title").item(0).textContent)
          assertEqualValue(3, opf.getElementsByTagNameNS("*", "itemref").length)
          assertTrueValue(output.fileName.toString().endsWith(".kepub.epub"))
          val metadata = opf.getElementsByTagNameNS("*", "meta")
          val values = (0 until metadata.length).map { metadata.item(it) as org.w3c.dom.Element }
          assertTrueValue(values.any { it.getAttribute("name") == "calibre:series" && it.getAttribute("content") == title })
          assertTrueValue(values.any { it.getAttribute("name") == "calibre:series_index" && it.getAttribute("content") == chapter.number })
        }
    }
  }

  private fun writeFixture(
    source: Path,
    title: String,
    chapterTitle: String,
  ) {
    ZipOutputStream(Files.newOutputStream(source)).use { zip ->
      fun add(
        name: String,
        bytes: ByteArray,
      ) {
        zip.putNextEntry(ZipEntry(name))
        zip.write(bytes)
        zip.closeEntry()
      }
      add("0001.avif", Base64.getMimeDecoder().decode(avifFixture))
      for (page in 2..3) {
        val image = BufferedImage(800, 1200, BufferedImage.TYPE_INT_RGB)
        // Paint pixels directly: no display server, font renderer or native decoder needed.
        val blue = Color(40, 100, 160).rgb
        val red = Color(160, 60, 40).rgb
        val pixels =
          IntArray(image.width * image.height) { index ->
            val x = index % image.width
            val y = index / image.width
            when {
              x in 50..750 && y in 50..1150 && (x <= 58 || x >= 742 || y <= 58 || y >= 1142) -> Color.BLACK.rgb
              x in 150..650 && y in 150..400 -> blue
              x in 200..600 && y in 500..900 -> red
              x in 100..(100 + page * 100) && y in 100..120 -> Color.BLACK.rgb
              else -> Color.WHITE.rgb
            }
          }
        image.setRGB(0, 0, image.width, image.height, pixels, 0, image.width)
        val bytes =
          ByteArrayOutputStream().use {
            check(ImageIO.write(image, "png", it))
            it.toByteArray()
          }
        add("000$page.png", bytes)
      }
      add("ComicInfo.xml", "<ComicInfo><Title>$chapterTitle</Title><Series>$title</Series><Number>7.5</Number></ComicInfo>".toByteArray(Charsets.UTF_8))
    }
  }

  private companion object {
    // Synthetic 800x1200 AVIF, kept as test data so KCC is the only decoder being tested here.
    val avifFixture =
      """
      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
      """.trimIndent()
  }
}
