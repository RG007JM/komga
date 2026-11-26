package org.gotson.komga.downloads

import org.gotson.komga.downloads.kcc.KccOptions
import org.gotson.komga.downloads.kcc.KccRuntime
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.LinkOption.NOFOLLOW_LINKS
import java.nio.file.Path
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.time.Duration
import java.util.zip.ZipFile

@Component
class DownloadKcc(
  private val files: DownloadFiles,
  private val runtime: KccRuntime,
) {
  fun available(): Boolean = runtime.status().startsWith("Available (")

  fun status(): String = runtime.status()

  fun archiveStatus(): String = runtime.archiveStatus()

  internal fun validateOutput(
    output: Path,
    format: String,
  ) {
    if (format == "PDF") {
      org.apache.pdfbox.Loader
        .loadPDF(output.toFile())
        .use { pdf -> check(pdf.numberOfPages > 0) { "KCC PDF has no pages" } }
      return
    }
    ZipFile(output.toFile()).use { zip ->
      if (format == "EPUB") {
        val mimetype = checkNotNull(zip.getEntry("mimetype")) { "KCC EPUB has no mimetype" }
        check(zip.getInputStream(mimetype).use { it.readNBytes(21).toString(Charsets.UTF_8) } == "application/epub+zip") { "Invalid KCC EPUB mimetype" }
        check(zip.getEntry("META-INF/container.xml") != null) { "KCC EPUB has no container" }
      } else {
        check(zip.entries().asSequence().any { !it.isDirectory && it.name.substringAfterLast('.').lowercase() in setOf("jpg", "jpeg", "png", "webp", "gif", "avif", "bmp") }) { "KCC CBZ has no images" }
      }
      val buffer = ByteArray(8192)
      var total = 0L
      zip.entries().asSequence().filterNot { it.isDirectory }.forEach { entry ->
        val crc = java.util.zip.CRC32()
        var size = 0L
        zip.getInputStream(entry).use { input ->
          while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            size += count
            total += count
            check(total <= 4L * 1024 * 1024 * 1024) { "KCC output exceeds validation limit" }
            crc.update(buffer, 0, count)
          }
        }
        check(size == entry.size && crc.value == entry.crc) { "Corrupt KCC output entry" }
      }
    }
  }

  fun convert(
    record: DownloadRecord,
    profile: KccProfile,
  ): List<String> {
    val args = KccOptions.arguments(profile)
    check(available()) { "KCC runtime: ${runtime.status()}" }
    check(archiveStatus().startsWith("Available (")) { "KCC archive utility: ${archiveStatus()}" }
    val binary = checkNotNull(runtime.executable()) { "KCC executable disappeared" }
    val root = files.root(record.destination.libraryId)
    val relative = Path.of(record.file)
    val input = files.target(root, relative.parent.toString(), false).resolve(relative.fileName)
    check(Files.isRegularFile(input, NOFOLLOW_LINKS)) { "Source CBZ is unavailable" }
    val destination = files.target(root, profile.outputFolder + "/" + input.parent.fileName, true)
    val stage = Files.createTempDirectory("komga-kcc-")
    val published = mutableListOf<Path>()
    try {
      val copied = Files.copy(input, stage.resolve(input.fileName))
      val out = Files.createDirectory(stage.resolve("output"))
      val log = runtime.logFile()
      val command = listOf(binary.toString()) + args + listOf("--output=${out.toAbsolutePath()}", "--", copied.toAbsolutePath().toString())
      runtime.run(command, stage, log, Duration.ofMinutes(30))
      val outputs = Files.list(out).use { paths -> paths.sorted().toList() }
      check(outputs.isNotEmpty() && outputs.size <= 100) { "Invalid KCC output count; see ${log.fileName}" }
      outputs.forEach { output ->
        check(Files.isRegularFile(output, NOFOLLOW_LINKS) && output.fileName.toString().endsWith(".${profile.format.lowercase()}", ignoreCase = true)) { "Unexpected KCC output; see ${log.fileName}" }
        validateOutput(output, profile.format)
      }
      val targets = outputs.map { output -> destination.resolve(output.fileName) }
      check(targets.none { Files.exists(it, NOFOLLOW_LINKS) }) { "KCC output already exists; original retained" }
      outputs.zip(targets).forEach { (output, target) ->
        val temporary = Files.createTempFile(destination, ".manga-conversion-", ".tmp")
        try {
          Files.copy(output, temporary, REPLACE_EXISTING)
          Files.move(temporary, target)
          published.add(target)
        } finally {
          Files.deleteIfExists(temporary)
        }
      }
      return published.map { root.relativize(it).toString() }
    } catch (e: Exception) {
      published.forEach { runCatching { Files.deleteIfExists(it) } }
      throw e
    } finally {
      stage.toFile().deleteRecursively()
    }
  }
}
