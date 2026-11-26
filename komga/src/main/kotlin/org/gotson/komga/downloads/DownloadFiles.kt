package org.gotson.komga.downloads

import org.gotson.komga.domain.persistence.LibraryRepository
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.LinkOption.NOFOLLOW_LINKS
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO
import javax.xml.stream.XMLOutputFactory

data class PageDimensions(
  val number: Int,
  val width: Int? = null,
  val height: Int? = null,
)

@Component
class DownloadFiles(
  private val libraries: LibraryRepository,
) {
  fun root(libraryId: String): Path {
    val library = requireNotNull(libraries.findByIdOrNull(libraryId)) { "Library no longer exists" }
    return Path.of(library.root.toURI()).toRealPath()
  }

  fun target(
    root: Path,
    folder: String,
    create: Boolean,
  ): Path {
    require(folder.isNotBlank()) { "Enter a library-relative folder" }
    val relative = Path.of(folder)
    require(relative.root == null && relative.none { it.toString() in setOf("..", ".") }) { "Enter a library-relative folder" }
    var current = root
    for (part in relative) {
      current = current.resolve(part)
      if (create && !Files.exists(current, NOFOLLOW_LINKS)) Files.createDirectory(current)
      if (Files.exists(current, NOFOLLOW_LINKS)) {
        require(Files.isDirectory(current, NOFOLLOW_LINKS)) { "Destination is not a normal directory" }
      }
    }
    return current
  }

  fun dimensions(pages: List<SourcePage>): List<PageDimensions> =
    pages.mapIndexed { index, page ->
      runCatching {
        page.open().use { stream ->
          ImageIO.createImageInputStream(stream).use imageStream@{ image ->
            val readers = ImageIO.getImageReaders(image)
            if (!readers.hasNext()) return@imageStream PageDimensions(index + 1)
            val reader = readers.next()
            try {
              reader.setInput(image, true, true)
              PageDimensions(index + 1, reader.getWidth(0), reader.getHeight(0))
            } finally {
              reader.dispose()
            }
          }
        }
      }.getOrElse { PageDimensions(index + 1) }
    }

  fun archive(
    destination: DownloadDestination,
    title: String,
    chapter: SourceChapter,
    pages: List<SourcePage>,
  ): String {
    require(pages.isNotEmpty()) { "Source returned no pages" }
    val root = root(destination.libraryId)
    val folder = target(root, destination.folder, true)
    val name = safeName("Chapter " + chapter.number + " - " + chapter.title) + " [" + safeName(chapter.id) + "].cbz"
    val output = folder.resolve(name)
    val temporary = Files.createTempFile(folder, ".manga-download-", ".tmp")
    try {
      ZipOutputStream(Files.newOutputStream(temporary)).use { zip ->
        pages.forEachIndexed { index, page ->
          val extension = page.filename.substringAfterLast('.', "").lowercase()
          require(extension in setOf("jpeg", "jpg", "png", "webp", "gif", "avif", "bmp")) { "Unsupported source page type" }
          zip.putNextEntry(ZipEntry((index + 1).toString().padStart(4, '0') + "." + extension))
          zip.write(validatedPage(page))
          zip.closeEntry()
        }
        zip.putNextEntry(ZipEntry("ComicInfo.xml"))
        val xml = XMLOutputFactory.newFactory().createXMLStreamWriter(zip, "UTF-8")
        xml.writeStartDocument("UTF-8", "1.0")
        xml.writeStartElement("ComicInfo")
        val date = chapter.publishedAt?.atOffset(java.time.ZoneOffset.UTC)
        val fields =
          mapOf(
            "Title" to chapter.title.ifBlank { "Chapter " + chapter.number },
            "Series" to title,
            "Number" to chapter.number,
            "LanguageISO" to chapter.language,
            "Web" to chapter.url,
            "Year" to date?.year?.toString(),
            "Month" to date?.monthValue?.toString(),
            "Day" to date?.dayOfMonth?.toString(),
          )
        fields.forEach { (key, value) ->
          if (!value.isNullOrBlank()) {
            xml.writeStartElement(key)
            xml.writeCharacters(value)
            xml.writeEndElement()
          }
        }
        xml.writeEndElement()
        xml.writeEndDocument()
        xml.flush()
        zip.closeEntry()
      }
      Files.move(temporary, output)
      return root.relativize(output).toString()
    } finally {
      Files.deleteIfExists(temporary)
    }
  }

  private fun validatedPage(page: SourcePage): ByteArray {
    val bytes = page.open().use { it.readNBytes(32 * 1024 * 1024 + 1) }
    require(bytes.size in 1..(32 * 1024 * 1024)) { "Invalid source page size" }
    ImageIO.createImageInputStream(bytes.inputStream()).use { image ->
      val readers = ImageIO.getImageReaders(image)
      require(readers.hasNext()) { "Source page is not a supported image: ${page.filename}" }
      val reader = readers.next()
      try {
        reader.setInput(image, true, true)
        val pixels = reader.getWidth(0).toLong() * reader.getHeight(0)
        require(pixels in 1..40_000_000L) { "Source page exceeds the image size limit" }
        require(reader.read(0) != null) { "Source page could not be decoded" }
      } finally {
        reader.dispose()
      }
    }
    return bytes
  }

  fun removeOriginal(record: DownloadRecord) {
    val root = root(record.destination.libraryId)
    val relative = Path.of(record.file)
    require(relative.root == null && relative.parent != null) { "Invalid original path" }
    val original = target(root, relative.parent.toString(), false).resolve(relative.fileName)
    check(Files.isRegularFile(original, NOFOLLOW_LINKS)) { "Original CBZ is unavailable" }
    Files.delete(original)
  }

  fun safeName(value: String): String =
    value
      .replace(Regex("[^\\p{L}\\p{N} ._()-]"), "_")
      .trim(' ', '.')
      .take(110)
      .ifBlank { "Manga" }
}
