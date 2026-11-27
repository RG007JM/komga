// EPUB series metadata adapted from Mangathr (MIT), see MANGATHR-LICENSE.txt.
package org.gotson.komga.downloads

import org.w3c.dom.Element
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Enrich private KCC outputs before validation and library publication. */
internal object DownloadChapterMetadata {
  private const val COLLECTION = "komga-download-series"
  private const val XML_LIMIT = 4 * 1024 * 1024

  fun restore(
    output: Path,
    source: Path,
    series: String,
    chapter: SourceChapter,
    format: String,
  ) {
    if (format !in setOf("EPUB", "CBZ")) return
    val temporary = Files.createTempFile(output.parent, ".chapter-metadata-", ".tmp")
    try {
      ZipFile(output.toFile()).use { zip ->
        val replacements =
          if (format == "EPUB") {
            val container = parse(read(zip, "META-INF/container.xml"))
            val packagePath =
              (container.getElementsByTagNameNS("*", "rootfile").item(0) as? Element)
                ?.getAttribute("full-path")
                ?.takeIf { it.isNotBlank() }
                ?: error("KCC EPUB has no package path")
            mapOf(packagePath to opf(read(zip, packagePath), series, chapter))
          } else {
            ZipFile(source.toFile()).use { original ->
              // Downloader ComicInfo contains chapter metadata, not obsolete page indices.
              mapOf("ComicInfo.xml" to read(original, "ComicInfo.xml"))
            }
          }
        ZipOutputStream(Files.newOutputStream(temporary)).use { writer ->
          val remaining = replacements.toMutableMap()
          val entries = zip.entries().toList()
          // EPUB requires the mimetype entry to be first and uncompressed.
          val ordered =
            if (format == "EPUB") {
              listOf(checkNotNull(zip.getEntry("mimetype"))) + entries.filterNot { it.name == "mimetype" }
            } else {
              entries
            }
          ordered.forEach { entry ->
            if (format == "CBZ" && entry.name.equals("ComicInfo.xml", ignoreCase = true)) return@forEach
            val replacement = remaining.remove(entry.name)
            val next = ZipEntry(entry.name)
            if (entry.time >= 0) next.time = entry.time
            if (format == "EPUB" && entry.name == "mimetype") {
              val data = read(zip, "mimetype")
              next.method = ZipEntry.STORED
              next.size = data.size.toLong()
              next.compressedSize = next.size
              next.crc = CRC32().apply { update(data) }.value
              writer.putNextEntry(next)
              writer.write(data)
            } else {
              writer.putNextEntry(next)
              if (replacement != null)
                writer.write(replacement)
              else if (!entry.isDirectory)
                zip.getInputStream(entry).use { it.copyTo(writer) }
            }
            writer.closeEntry()
          }
          remaining.forEach { (name, data) ->
            writer.putNextEntry(ZipEntry(name))
            writer.write(data)
            writer.closeEntry()
          }
        }
      }
      // All input handles must be closed before replacing a ZIP on Windows.
      Files.move(temporary, output, REPLACE_EXISTING)
    } finally {
      Files.deleteIfExists(temporary)
    }
  }

  private fun read(
    zip: ZipFile,
    name: String,
  ): ByteArray {
    val entry = checkNotNull(zip.getEntry(name)) { "Missing metadata entry: $name" }
    return zip.getInputStream(entry).use { it.readNBytes(XML_LIMIT + 1) }.also {
      require(it.size <= XML_LIMIT) { "Metadata entry exceeds size limit" }
    }
  }

  private fun parse(bytes: ByteArray) =
    DocumentBuilderFactory
      .newInstance()
      .apply {
        isNamespaceAware = true
        isXIncludeAware = false
        setExpandEntityReferences(false)
        setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "")
        setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "")
      }.newDocumentBuilder()
      .parse(bytes.inputStream())

  private fun children(element: Element): List<Element> = (0 until element.childNodes.length).mapNotNull { element.childNodes.item(it) as? Element }

  internal fun opf(
    bytes: ByteArray,
    series: String,
    chapter: SourceChapter,
  ): ByteArray {
    require(series.isNotBlank()) { "Missing series title" }
    val document = parse(bytes)
    val metadata =
      document.getElementsByTagNameNS("*", "metadata").item(0) as? Element
        ?: error("EPUB package has no metadata")
    val title =
      children(metadata).firstOrNull { it.localName == "title" && it.namespaceURI == "http://purl.org/dc/elements/1.1/" }
        ?: error("EPUB package has no dc:title")
    title.textContent = DownloadFileNames.stem(chapter)
    val old = children(metadata).filter { it.localName == "meta" }
    val oldCollections = old.filter { it.getAttribute("property") == "belongs-to-collection" }
    val ids = oldCollections.map { it.getAttribute("id") }.filter { it.isNotBlank() }.toSet() + COLLECTION
    old
      .filter {
        it in oldCollections || it.getAttribute("refines").removePrefix("#") in ids ||
          it.getAttribute("name") in setOf("calibre:series", "calibre:series_index")
      }.forEach { metadata.removeChild(it) }

    fun meta(
      attributes: Map<String, String>,
      value: String? = null,
    ) {
      val qualifiedName = metadata.prefix?.let { "$it:meta" } ?: "meta"
      val element = document.createElementNS(metadata.namespaceURI, qualifiedName)
      attributes.forEach { (key, text) -> element.setAttribute(key, text) }
      if (value != null) element.textContent = value
      metadata.appendChild(element)
    }
    meta(mapOf("name" to "calibre:series", "content" to series))
    meta(mapOf("property" to "belongs-to-collection", "id" to COLLECTION), series)
    meta(mapOf("refines" to "#$COLLECTION", "property" to "collection-type"), "series")
    // Unknown/non-numeric chapter labels must not invent a numeric ordering.
    val number = chapter.number.trim()
    if (number.toBigDecimalOrNull() != null && number.toFloatOrNull()?.isFinite() == true) {
      meta(mapOf("name" to "calibre:series_index", "content" to number))
      meta(mapOf("refines" to "#$COLLECTION", "property" to "group-position"), number)
    }
    return ByteArrayOutputStream().use { output ->
      TransformerFactory
        .newInstance()
        .apply {
          setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
          setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "")
          setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "")
        }.newTransformer()
        .transform(DOMSource(document), StreamResult(output))
      output.toByteArray()
    }
  }
}
