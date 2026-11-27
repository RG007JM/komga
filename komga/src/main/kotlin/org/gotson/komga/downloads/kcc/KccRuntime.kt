package org.gotson.komga.downloads.kcc

import org.gotson.komga.infrastructure.configuration.KomgaProperties
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit

/** The runtime is installed by the administrator, never by a web request. */
@Component
class KccRuntime(
  properties: KomgaProperties,
) {
  private val directory = (properties.configDir?.let { Path.of(it) } ?: Path.of(properties.database.file).toAbsolutePath().parent).toAbsolutePath().resolve("kcc")
  private val windows = System.getProperty("os.name").startsWith("Windows", ignoreCase = true)
  private val executableName = if (windows) "kcc-c2e.exe" else "kcc-c2e"
  private val mac = System.getProperty("os.name").startsWith("Mac", ignoreCase = true)
  private val archiveName =
    if (windows)
      "7z.exe"
    else if (mac)
      "7zz"
    else
      "7z"
  private val pathDirectories: List<Path>
    get() =
      buildList {
        add(directory.resolve("runtime-${KccOptions.VERSION}/bin"))
        add(directory.resolve("bin"))
        if (windows) {
          System.getenv("ProgramFiles")?.let { add(Path.of(it, "7-Zip")) }
        }
        if (mac) {
          add(Path.of("/opt/homebrew/bin"))
          add(Path.of("/usr/local/bin"))
        }
        addAll(
          System
            .getenv("PATH")
            .orEmpty()
            .split(File.pathSeparatorChar)
            .filter { it.isNotBlank() }
            .map { Path.of(it) },
        )
      }

  fun archiveStatus(): String = if (pathDirectories.any { Files.isRegularFile(it.resolve(archiveName)) && Files.isExecutable(it.resolve(archiveName)) }) "Available ($archiveName)" else "Not found ($archiveName)"

  private var lastProbe: Pair<Instant, String>? = null

  fun executable(): Path? {
    val explicit = System.getenv("KOMGA_KCC_C2E")?.takeIf { it.isNotBlank() }
    if (explicit != null) return Path.of(explicit).toAbsolutePath().takeIf { Files.isRegularFile(it) && Files.isExecutable(it) }
    return pathDirectories
      .map { it.resolve(executableName) }
      .firstOrNull { Files.isRegularFile(it) && Files.isExecutable(it) }
      ?.toAbsolutePath()
  }

  @Synchronized
  fun status(): String {
    lastProbe?.let { (at, result) -> if (Duration.between(at, Instant.now()) < Duration.ofMinutes(2)) return result }
    val result =
      runCatching {
        val binary = executable() ?: return@runCatching "Not found"
        val log = Files.createTempFile("komga-kcc-probe-", ".log")
        try {
          run(listOf(binary.toString(), "--help"), directory = null, log = log, timeout = Duration.ofSeconds(45))
          val banner =
            Files.newBufferedReader(log).use {
              it
                .lineSequence()
                .take(20)
                .firstOrNull { line -> line.startsWith("comic2ebook v") }
                .orEmpty()
            }
          if (banner.startsWith("comic2ebook v${KccOptions.VERSION} ")) "Available (${KccOptions.VERSION})" else "Unsupported version: ${banner.take(160)}"
        } finally {
          Files.deleteIfExists(log)
        }
      }.getOrElse { "Unavailable: ${it.message.orEmpty().take(200)}" }
    lastProbe = Instant.now() to result
    return result
  }

  fun logFile(): Path {
    val logs = Files.createDirectories(directory.resolve("logs"))
    return Files.createTempFile(logs, "conversion-", ".log")
  }

  fun run(
    command: List<String>,
    directory: Path?,
    log: Path,
    timeout: Duration,
  ) {
    val builder = ProcessBuilder(command).redirectErrorStream(true).redirectOutput(log.toFile())
    builder.environment()["PATH"] = pathDirectories.joinToString(File.pathSeparator) { it.toString() }
    // KCC processes do not need the downloader's native identity.
    builder.environment().keys.removeIf { it.equals("MANGAPLUS_DEVICE_SECRET", ignoreCase = true) }
    if (directory != null) builder.directory(directory.toFile())
    val process = builder.start()
    try {
      process.outputStream.close()
      check(process.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS)) { "KCC timed out; see ${log.fileName}" }
      check(process.exitValue() == 0) { "KCC exited with code ${process.exitValue()}; see ${log.fileName}" }
    } finally {
      if (process.isAlive) {
        val children = process.descendants().use { it.toList() }
        children.asReversed().forEach { it.destroyForcibly() }
        process.destroyForcibly()
        process.waitFor(5, TimeUnit.SECONDS)
      }
    }
  }
}
