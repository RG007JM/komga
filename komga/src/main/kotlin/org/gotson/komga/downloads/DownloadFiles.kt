package org.gotson.komga.downloads

import org.gotson.komga.domain.persistence.LibraryRepository
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
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
    forConversion: Boolean = false,
    prependWhitePage: Boolean = false,
  ): String {
    require(pages.isNotEmpty()) { "Source returned no pages" }
    val root = root(destination.libraryId)
    val name = safeName("Chapter " + chapter.number + " - " + chapter.title) + " [" + safeName(downloadChapterFileId(chapter.id)) + "].cbz"
    val folder =
      if (forConversion) {
        target(root, destination.folder, false)
        val temp = systemTemp()
        require(!temp.startsWith(root)) { "System temporary directory is inside the library" }
        require(
          libraries.findAll().none { library ->
            val configured = Path.of(library.root.toURI()).toAbsolutePath().normalize()
            val libraryRoot = runCatching { configured.toRealPath() }.getOrDefault(configured)
            temp.startsWith(libraryRoot)
          },
        ) { "System temporary directory is inside a Komga library" }
        Files.createTempDirectory(temp, "komga-download-")
      } else {
        target(root, destination.folder, true)
      }
    val output = folder.resolve(name)
    var temporary: Path? = null
    var completed = false
    try {
      val partial = Files.createTempFile(folder, ".manga-download-", ".tmp")
      temporary = partial
      ZipOutputStream(Files.newOutputStream(partial)).use { zip ->
        pages.forEachIndexed { index, page ->
          val extension = page.filename.substringAfterLast('.', "").lowercase()
          require(extension in setOf("jpeg", "jpg", "png", "webp", "gif", "avif", "bmp")) { "Unsupported source page type" }
          val validated = validatedPage(page)
          if (prependWhitePage && index == 0) {
            zip.putNextEntry(ZipEntry("0000.png"))
            zip.write(whitePage(validated.width, validated.height))
            zip.closeEntry()
          }
          zip.putNextEntry(ZipEntry((index + 1).toString().padStart(4, '0') + "." + extension))
          zip.write(validated.bytes)
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
      Files.move(partial, output)
      completed = true
      return if (forConversion) output.toString() else root.relativize(output).toString()
    } finally {
      temporary?.let { Files.deleteIfExists(it) }
      if (forConversion && !completed) Files.deleteIfExists(folder)
    }
  }

  private data class ValidatedPage(
    val bytes: ByteArray,
    val width: Int,
    val height: Int,
  )

  private fun whitePage(
    width: Int,
    height: Int,
  ): ByteArray {
    val image = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)
    val graphics = image.createGraphics()
    try {
      graphics.color = Color.WHITE
      graphics.fillRect(0, 0, width, height)
    } finally {
      graphics.dispose()
    }
    return ByteArrayOutputStream().use { output ->
      check(ImageIO.write(image, "png", output)) { "Unable to create white page" }
      output.toByteArray()
    }
  }

  private fun validatedPage(page: SourcePage): ValidatedPage {
    val bytes = page.open().use { it.readNBytes(32 * 1024 * 1024 + 1) }
    require(bytes.size in 1..(32 * 1024 * 1024)) { "Invalid source page size" }
    ImageIO.createImageInputStream(bytes.inputStream()).use { image ->
      val readers = ImageIO.getImageReaders(image)
      require(readers.hasNext()) { "Source page is not a supported image: ${page.filename}" }
      val reader = readers.next()
      try {
        reader.setInput(image, true, true)
        val width = reader.getWidth(0)
        val height = reader.getHeight(0)
        val pixels = width.toLong() * height
        require(pixels in 1..40_000_000L) { "Source page exceeds the image size limit" }
        require(reader.read(0) != null) { "Source page could not be decoded" }
        return ValidatedPage(bytes, width, height)
      } finally {
        reader.dispose()
      }
    }
  }

  private fun systemTemp(): Path = Path.of(System.getProperty("java.io.tmpdir")).toRealPath()

  fun sourceArchive(record: DownloadRecord): Path {
    val path = Path.of(record.file)
    val original =
      if (path.isAbsolute) {
        val parent = requireNotNull(path.parent) { "Invalid staged CBZ path" }
        require(path == path.normalize() && parent.parent == systemTemp() && parent.fileName.toString().startsWith("komga-download-")) { "Invalid staged CBZ path" }
        require(Files.isDirectory(parent, NOFOLLOW_LINKS)) { "Staged download directory is unavailable" }
        path
      } else {
        // Retry records created by earlier downloader versions.
        require(path.root == null && path.parent != null) { "Invalid original path" }
        target(root(record.destination.libraryId), path.parent.toString(), false).resolve(path.fileName)
      }
    require(original.fileName.toString().endsWith(".cbz", ignoreCase = true)) { "Invalid source archive type" }
    check(Files.isRegularFile(original, NOFOLLOW_LINKS)) { "Original CBZ is unavailable" }
    return original
  }

  fun removeOriginal(record: DownloadRecord) {
    val original = sourceArchive(record)
    Files.delete(original)
    if (Path.of(record.file).isAbsolute) Files.deleteIfExists(original.parent)
  }

  fun safeName(value: String): String =
    value
      .replace(Regex("[^\\p{L}\\p{N} ._()-]"), "_")
      .trim(' ', '.')
      .take(110)
      .ifBlank { "Manga" }
}
