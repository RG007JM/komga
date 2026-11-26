package org.gotson.komga.downloads

import io.mockk.every
import io.mockk.mockk
import org.gotson.komga.domain.persistence.LibraryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.util.zip.ZipFile
import javax.imageio.ImageIO
import javax.xml.parsers.DocumentBuilderFactory

class DownloadFilesTest {
  @TempDir
  lateinit var root: Path

  private fun files(): DownloadFiles {
    val libraries = mockk<LibraryRepository>()
    val libraryUrl = root.toUri().toURL()
    every { libraries.findByIdOrNull("library") } returns
      mockk {
        every { root } returns libraryUrl
      }
    return DownloadFiles(libraries)
  }

  private fun png(
    width: Int,
    height: Int,
  ): ByteArray =
    ByteArrayOutputStream().use {
      check(ImageIO.write(BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), "png", it))
      it.toByteArray()
    }

  private val chapter = SourceChapter("chapter-id", "1.5", "A & B", Instant.parse("2026-01-02T03:04:05Z"), "en", "https://example.org/chapter")
  private val destination = DownloadDestination("library", "Series")

  @Test
  fun `dimensions preserve page order and report unreadable images`() {
    val pages =
      listOf(
        SourcePage("a.png") { png(23, 42).inputStream() },
        SourcePage("bad.png") { byteArrayOf(1, 2, 3).inputStream() },
        SourcePage("b.png") { png(100, 200).inputStream() },
      )
    assertEqualValue(listOf(PageDimensions(1, 23, 42), PageDimensions(2), PageDimensions(3, 100, 200)), files().dimensions(pages))
  }

  @Test
  fun `target rejects absolute and traversal paths`() {
    val files = files()
    listOf("../outside", "inside/../outside", "./inside", root.toAbsolutePath().toString(), " ").forEach { folder ->
      assertThrowsType(IllegalArgumentException::class.java) { files.target(root, folder, true) }
    }
  }

  @Test
  fun `target refuses a regular file as a directory`() {
    Files.writeString(root.resolve("file"), "not a directory")
    assertThrowsType(IllegalArgumentException::class.java) { files().target(root, "file/subfolder", true) }
  }

  @Test
  fun `archive contains ordered pages and escaped ComicInfo metadata`() {
    val first = png(23, 42)
    val second = png(10, 20)
    val file =
      files().archive(
        destination,
        "A < B & C",
        chapter,
        listOf(SourcePage("z.png") { first.inputStream() }, SourcePage("a.png") { second.inputStream() }),
      )
    ZipFile(root.resolve(file).toFile()).use { zip ->
      assertEqualValue(listOf("0001.png", "0002.png", "ComicInfo.xml"), zip.entries().toList().map { it.name })
      assertTrueValue(zip.getInputStream(zip.getEntry("0001.png")).use { it.readBytes().contentEquals(first) })
      assertTrueValue(zip.getInputStream(zip.getEntry("0002.png")).use { it.readBytes().contentEquals(second) })
      val xml = zip.getInputStream(zip.getEntry("ComicInfo.xml")).use { DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it) }
      assertEqualValue("A < B & C", xml.getElementsByTagName("Series").item(0).textContent)
      assertEqualValue("A & B", xml.getElementsByTagName("Title").item(0).textContent)
      assertEqualValue("1.5", xml.getElementsByTagName("Number").item(0).textContent)
      assertEqualValue("2026", xml.getElementsByTagName("Year").item(0).textContent)
    }
  }

  @Test
  fun `failed page download leaves neither partial archive nor temporary file`() {
    val pages = listOf(SourcePage("a.png") { png(23, 42).inputStream() }, SourcePage("b.png") { throw IOException("fixture failure") })
    assertThrowsType(IOException::class.java) { files().archive(destination, "Series", chapter, pages) }
    Files.list(root.resolve("Series")).use { assertFalseValue(it.findAny().isPresent) }
  }

  @Test
  fun `invalid image is rejected without creating an archive`() {
    val pages = listOf(SourcePage("bad.png") { "not an image".byteInputStream() })
    assertThrowsType(IllegalArgumentException::class.java) { files().archive(destination, "Series", chapter, pages) }
    Files.list(root.resolve("Series")).use { assertFalseValue(it.findAny().isPresent) }
  }

  @Test
  fun `invalid KCC output is rejected`() {
    val output = root.resolve("broken.epub")
    Files.writeString(output, "not an epub")
    assertThrowsType(java.util.zip.ZipException::class.java) { DownloadKcc(files()).validateOutput(output, "EPUB") }
  }

  @Test
  fun `empty chapter and unsupported extension are rejected`() {
    val files = files()
    assertThrowsType(IllegalArgumentException::class.java) { files.archive(destination, "Series", chapter, emptyList()) }
    assertThrowsType(IllegalArgumentException::class.java) {
      files.archive(destination, "Series", chapter, listOf(SourcePage("page.txt") { byteArrayOf(1).inputStream() }))
    }
  }
}
