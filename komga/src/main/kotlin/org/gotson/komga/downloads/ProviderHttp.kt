// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import java.time.Duration
import java.time.Instant
import java.util.HexFormat
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream

/** Native HTTP transport: bounded responses, checked redirects and no challenge solver. */
@Component
class ProviderHttp {
  private val client =
    HttpClient
      .newBuilder()
      .connectTimeout(Duration.ofSeconds(10))
      .cookieHandler(CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER))
      .followRedirects(HttpClient.Redirect.NEVER)
      .build()
  private val nextRequest = mutableMapOf<String, Long>()

  @Synchronized
  private fun pace(host: String) {
    val delay = (nextRequest[host] ?: System.nanoTime()) - System.nanoTime()
    if (delay > 0) TimeUnit.NANOSECONDS.sleep(delay)
    nextRequest[host] = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(300)
  }

  fun bytes(
    uri: URI,
    hosts: Set<String>,
    headers: Map<String, String> = emptyMap(),
  ): ByteArray = fetch(uri, hosts, headers, if (uri.host in setOf("weebcentral.com", "www.weebcentral.com", "jumpg-webapi.tokyo-cdn.com")) 64 * 1024 * 1024 else 8 * 1024 * 1024)

  fun text(
    uri: URI,
    hosts: Set<String>,
    headers: Map<String, String> = emptyMap(),
  ): String = bytes(uri, hosts, headers).toString(Charsets.UTF_8)

  fun page(
    uri: URI,
    hosts: Set<String>,
    headers: Map<String, String> = emptyMap(),
  ): SourcePage {
    validateUri(uri, hosts)
    val extension = uri.path.substringAfterLast('.', "").lowercase()
    require(extension in setOf("jpg", "jpeg", "png", "webp", "gif", "avif", "bmp")) { "Unsupported source image extension" }
    return SourcePage("page.$extension") { fetch(uri, hosts, headers, 32 * 1024 * 1024).inputStream() }
  }

  /** Fetch an authorized image asset; validation also accepts binary image representations. */
  fun imageBytes(
    uri: URI,
    hosts: Set<String>,
    headers: Map<String, String>,
  ): ByteArray = fetch(uri, hosts, headers, 32 * 1024 * 1024, imageResponse = true)

  private fun fetch(
    uri: URI,
    hosts: Set<String>,
    headers: Map<String, String>,
    limit: Int,
    imageResponse: Boolean = false,
  ): ByteArray {
    var target = uri
    var redirects = 0
    var retries = 0
    while (true) {
      validateUri(target, hosts)
      // A server-issued viewer credential belongs only to its original asset host.
      check(target.host.equals(uri.host, true) || headers.keys.none { it.equals("Plus-Vw-Token", true) }) {
        "Provider image redirect changes the viewer credential host"
      }
      pace(requireNotNull(target.host).lowercase())
      val request =
        providerRequest(
          target,
          if (target.host.equals(uri.host, true))
            headers
          else
            headers.filterKeys {
              !it.equals("Session-Token", true) && !it.equals("Authorization", true) && !it.equals("Cookie", true)
            },
        )
      val response =
        try {
          client.send(request.GET().build(), HttpResponse.BodyHandlers.ofInputStream())
        } catch (e: IOException) {
          if (++retries >= 3) throw e
          Thread.sleep(retries * 500L)
          continue
        }
      val status = response.statusCode()
      val encoding = response.headers().firstValue("Content-Encoding").orElse("")
      val body = response.body().use { readProviderBody(it, encoding, limit) }
      val contentType = response.headers().firstValue("Content-Type").orElse("")
      val prefix = body.copyOfRange(0, minOf(body.size, 16_384)).toString(Charsets.UTF_8)
      val html = if (contentType.contains("html", true) || prefix.trimStart().startsWith("<")) prefix else ""
      check(
        !response
          .headers()
          .firstValue("cf-mitigated")
          .orElse("")
          .equals("challenge", true) && !isProviderChallenge(html),
      ) {
        "Provider requires an interactive challenge; direct HTTP cannot continue"
      }
      if (status in setOf(301, 302, 303, 307, 308)) {
        check(++redirects <= 3) { "Provider redirect limit exceeded" }
        target = target.resolve(response.headers().firstValue("Location").orElseThrow())
        continue
      }
      if (status in 200..299) {
        if (imageResponse) validateProviderImageContentType(contentType)
        check(body.isNotEmpty()) { "${target.host}${target.path} returned an empty response (HTTP $status)" }
        return body
      }
      if ((status == 429 || status >= 500) && ++retries < 3) {
        val retryHeader = response.headers().firstValue("Retry-After").orElse("")
        val retryAfter = retryHeader.toLongOrNull()
        if (retryHeader.isNotBlank() && retryAfter == null) error("Provider requested a cooldown; retry later")
        // Never shorten a source's explicit cooldown to force a retry.
        if (retryAfter != null && retryAfter > 15) error("Provider rate limited the request; retry later")
        Thread.sleep((retryAfter?.coerceAtLeast(1) ?: retries.toLong()) * 1000)
        continue
      }
      error("${target.host}${target.path} returned HTTP $status")
    }
  }

  companion object {
    internal fun validateUri(
      uri: URI,
      hosts: Set<String>,
    ) {
      require(
        uri.scheme == "https" && uri.host?.lowercase() in hosts && uri.userInfo == null &&
          uri.port in listOf(-1, 443) && uri.fragment == null && uri.normalize() == uri &&
          uri.path.split('/').none { it == "." || it == ".." },
      ) { "Unsupported provider URL or host: ${uri.host ?: "missing host"}" }
    }
  }
}

/** Build exactly one value per request header; never send a fabricated access credential. */
internal fun providerRequest(
  uri: URI,
  headers: Map<String, String>,
): HttpRequest.Builder =
  HttpRequest
    .newBuilder(uri)
    .timeout(Duration.ofSeconds(45))
    .header("User-Agent", "Komga-downloads/0.1")
    .header("Accept-Encoding", "gzip")
    .also { request -> headers.forEach { (key, value) -> request.setHeader(key, value) } }

internal fun readProviderBody(
  input: InputStream,
  encoding: String,
  limit: Int,
): ByteArray {
  val stream =
    when (encoding.trim().lowercase()) {
      "", "identity" -> input
      "gzip", "x-gzip" -> GZIPInputStream(input)
      else -> error("Unsupported provider response encoding")
    }
  return stream.use { it.readNBytes(limit + 1) }.also {
    check(it.size <= limit) { "Provider response exceeds size limit" }
  }
}

internal fun encodeProviderQuery(value: String): String = URLEncoder.encode(value, Charsets.UTF_8)

internal fun parseProviderDate(value: String): Instant? =
  runCatching { Instant.parse(value) }.getOrNull()
    ?: value.toLongOrNull()?.takeIf { it > 0 }?.let { runCatching { Instant.ofEpochSecond(it) }.getOrNull() }

internal fun ChapterFilters.acceptsGroups(groups: Set<String>): Boolean {
  val normalized = groups.map { it.lowercase() }.toSet()
  return (includeGroups.isEmpty() || includeGroups.any { it.lowercase() in normalized }) &&
    excludeGroups.none { it.lowercase() in normalized }
}

/** Keep existing short source IDs unchanged; long opaque identifiers get stable compact filenames. */
internal fun downloadChapterFileId(id: String): String =
  if (id.length <= 64)
    id
  else
    HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(id.toByteArray(Charsets.UTF_8))).take(32)

internal fun isProviderChallenge(html: String): Boolean {
  val lower = html.lowercase()
  return "cf-chl-" in lower || "__cf_chl_tk" in lower ||
    ("cloudflare" in lower && ("just a moment" in lower || "challenge" in lower))
}

internal fun validateProviderImageContentType(contentType: String) {
  val mediaType = contentType.substringBefore(';').trim().lowercase()
  check(mediaType.isEmpty() || mediaType.startsWith("image/") || mediaType == "application/octet-stream") {
    "Provider returned a non-image content type"
  }
}
