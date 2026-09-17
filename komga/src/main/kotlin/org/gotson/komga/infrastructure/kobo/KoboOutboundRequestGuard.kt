// KOBO_OUTBOUND_ID_GUARD_V1
package org.gotson.komga.infrastructure.kobo

import com.github.f4b6a3.tsid.Tsid
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.ReadListRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.net.URI

private val logger = KotlinLogging.logger {}

/**
 * Final safety boundary for requests that are actually about to leave Komga
 * for the Kobo Store API.
 *
 * Product/series/body translators run before this interceptor. Therefore a
 * local Komga ID that has a confirmed Kobo equivalent is already a Kobo ID by
 * the time this code runs and is allowed through.
 *
 * A surviving ID is blocked only when it is both a valid TSID and an actual
 * Komga book, series, or read-list ID. This avoids confusing ISBNs, device
 * serials, or arbitrary 13-character values with Komga identifiers.
 */
@Component
class KoboOutboundRequestGuard(
  private val bookRepository: BookRepository,
  private val seriesRepository: SeriesRepository,
  private val readListRepository: ReadListRepository,
) : ClientHttpRequestInterceptor {
  private val tsidCandidate =
    Regex(
      pattern = """(?i)(?<![0-9a-z])[0-9a-z]{13}(?![0-9a-z])""",
    )

  override fun intercept(
    request: HttpRequest,
    body: ByteArray,
    execution: ClientHttpRequestExecution,
  ): ClientHttpResponse {
    validate(
      uri = request.uri,
      body = body,
    )

    return execution.execute(
      request,
      body,
    )
  }

  internal fun validate(
    uri: URI,
    body: ByteArray = byteArrayOf(),
  ) {
    val ids =
      buildSet {
        addAll(findKomgaIds(uri.rawPath.orEmpty()))
        addAll(findKomgaIds(uri.rawQuery.orEmpty()))

        if (body.isNotEmpty()) {
          addAll(
            findKomgaIds(
              String(
                body,
                Charsets.UTF_8,
              ),
            ),
          )
        }
      }

    if (ids.isEmpty()) {
      return
    }

    logger.debug {
      "Blocking outbound Kobo Store request with untranslated Komga ID(s) " +
        "${ids.joinToString()}: $uri"
    }

    throw ResponseStatusException(
      HttpStatus.BAD_REQUEST,
      "Unable to bind request",
    )
  }

  private fun findKomgaIds(value: String): List<String> =
    tsidCandidate
      .findAll(value)
      .map { it.value }
      .filter { Tsid.isValid(it) }
      .filter(::isKnownKomgaId)
      .distinct()
      .toList()

  private fun isKnownKomgaId(id: String): Boolean =
    bookRepository.findByIdOrNull(id) != null ||
      seriesRepository.findByIdOrNull(id) != null ||
      readListRepository.findByIdOrNull(id) != null
}
