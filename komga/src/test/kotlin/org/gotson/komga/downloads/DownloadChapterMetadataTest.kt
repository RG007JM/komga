package org.gotson.komga.downloads

import org.apache.commons.validator.routines.ISBNValidator
import org.gotson.komga.domain.model.BookMetadataPatchCapability
import org.gotson.komga.domain.model.BookWithMedia
import org.gotson.komga.domain.model.Media
import org.gotson.komga.domain.model.makeBook
import org.gotson.komga.infrastructure.metadata.epub.EpubMetadataProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class DownloadChapterMetadataTest {
  @TempDir
  lateinit var root: Path

  private val chapter = SourceChapter("source-id", "132.5", "A & B", null, "en", "https://example.org/chapter")
  private val series = "Series & Friends"
  private val image = byteArrayOf(1, 2, 3, 4)
  private val container = """<container xmlns="urn:oasis:names:tc:opendocument:xmlns:container"><rootfiles><rootfile full-path="OPS/book.opf" media-type="application/oebps-package+xml"/></rootfiles></container>"""
  private val opf = """<package xmlns="http://www.idpf.org/2007/opf" version="3.0" xmlns:dc="http://purl.org/dc/elements/1.1/"><metadata><dc:title>Old title</dc:title><meta property="belongs-to-collection" id="old">Old series</meta><meta refines="#old" property="group-position">1</meta><meta name="calibre:series_index" content="1"/></metadata><manifest/><spine/></package>"""

  private fun archive(
    path: Path,
    entries: Map<String, ByteArray>,
  ) {
    ZipOutputStream(Files.newOutputStream(path)).use { zip ->
      entries.forEach { (name, bytes) ->
        zip.putNextEntry(ZipEntry(name))
        zip.write(bytes)
        zip.closeEntry()
      }
    }
  }

  @Test
  fun `KEPUB embeds source number and series metadata readable by Komga`() {
    val output = root.resolve("chapter.kepub.epub")
    archive(output, linkedMapOf("META-INF/container.xml" to container.toByteArray(), "OPS/book.opf" to opf.toByteArray(), "mimetype" to "application/epub+zip".toByteArray(), "OPS/image.jpg" to image))
    repeat(2) { DownloadChapterMetadata.restore(output, root.resolve("unused.cbz"), series, chapter, "EPUB") }
    val provider = EpubMetadataProvider(ISBNValidator(true))
    val patch = checkNotNull(provider.getBookMetadataFromBook(BookWithMedia(makeBook("chapter", url = output.toUri().toURL()), Media(status = Media.Status.READY, mediaType = "application/epub+zip"))))
    assertEqualValue("132.5", patch.number)
    assertEqualValue(132.5f, patch.numberSort)
    assertEqualValue("Chapter 132.5 - A & B", patch.title)
    assertTrueValue(BookMetadataPatchCapability.NUMBER in provider.capabilities)
    assertTrueValue(BookMetadataPatchCapability.NUMBER_SORT in provider.capabilities)
    ZipFile(output.toFile()).use { zip ->
      assertEqualValue("mimetype", zip.entries().nextElement().name)
      assertEqualValue(ZipEntry.STORED, zip.getEntry("mimetype").method)
      assertTrueValue(zip.getInputStream(zip.getEntry("OPS/image.jpg")).use { it.readBytes().contentEquals(image) })
      val metadata = zip.getInputStream(zip.getEntry("OPS/book.opf")).use { it.readBytes().toString(Charsets.UTF_8) }
      assertTrueValue(metadata.contains("Series &amp; Friends"))
      assertEqualValue(1, Regex("property=\"belongs-to-collection\"").findAll(metadata).count())
    }
  }

  @Test
  fun `converted CBZ retains original ComicInfo after KCC strips it`() {
    val source = root.resolve("source.cbz")
    val output = root.resolve("converted.cbz")
    val comicInfo = "<ComicInfo><Number>132.5</Number><Series>Series &amp; Friends</Series></ComicInfo>".toByteArray()
    archive(source, mapOf("ComicInfo.xml" to comicInfo))
    archive(output, mapOf("0001.jpg" to image, "comicinfo.xml" to "<ComicInfo/>".toByteArray()))
    DownloadChapterMetadata.restore(output, source, series, chapter, "CBZ")
    ZipFile(output.toFile()).use { zip ->
      assertEqualValue(1, zip.entries().toList().count { it.name.equals("ComicInfo.xml", ignoreCase = true) })
      assertTrueValue(zip.getInputStream(zip.getEntry("ComicInfo.xml")).use { it.readBytes().contentEquals(comicInfo) })
      assertTrueValue(zip.getInputStream(zip.getEntry("0001.jpg")).use { it.readBytes().contentEquals(image) })
    }
  }
}
