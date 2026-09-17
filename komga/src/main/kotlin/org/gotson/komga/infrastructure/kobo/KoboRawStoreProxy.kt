// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.infrastructure.kobo.KoboHeaders.X_KOBO_SYNCTOKEN
import org.gotson.komga.infrastructure.web.getCurrentRequest
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.DefaultUriBuilderFactory
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private val logger = KotlinLogging.logger {}

@Component
class KoboRawStoreProxy(
  private val koboOutboundRequestGuard: KoboOutboundRequestGuard,
) {
  private val koboApiClient: RestClient =
    RestClient
      .builder()
      .uriBuilderFactory(
        DefaultUriBuilderFactory("https://storeapi.kobo.com")
          .apply {
            encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
          },
      ).requestFactory(
        ClientHttpRequestFactoryBuilder.reactor().build(
          ClientHttpRequestFactorySettings
            .defaults()
            .withReadTimeout(1.minutes.toJavaDuration())
            .withConnectTimeout(1.minutes.toJavaDuration()),
        ),
      ).requestInterceptor(
        koboOutboundRequestGuard,
      ).build()

  private val pathRegex = "/kobo/[-\\w]*(.*)".toRegex()

  private val headersOutInclude =
    setOf(
      HttpHeaders.AUTHORIZATION,
      HttpHeaders.USER_AGENT,
      HttpHeaders.ACCEPT,
      HttpHeaders.ACCEPT_LANGUAGE,
      HttpHeaders.CONTENT_TYPE,
    )

  private fun isKoboHeader(headerName: String) =
    headerName.startsWith(
      prefix = "x-kobo-",
      ignoreCase = true,
    )

  private fun shouldForwardRequestHeader(headerName: String): Boolean {
    if (headerName.equals(X_KOBO_SYNCTOKEN, ignoreCase = true)) {
      return false
    }

    return headersOutInclude.any {
      it.equals(
        other = headerName,
        ignoreCase = true,
      )
    } || isKoboHeader(headerName)
  }

  /**
   * Deliberately raw proxy for the experimental remaining-endpoint pass.
   *
   * No Komga response identity translation is applied here.
   */
  fun proxyCurrentRequest(
    body: ByteArray? = null,
    overridePath: String? = null,
  ): ResponseEntity<JsonNode> {
    val request = getCurrentRequest()

    val originalPath =
      pathRegex
        .find(request.requestURI)
        ?.destructured
        ?.component1()
        ?: throw IllegalStateException(
          "Could not get Kobo path from current request",
        )

    val path = overridePath ?: originalPath

    val response =
      koboApiClient
        .method(HttpMethod.valueOf(request.method))
        .uri { uriBuilder ->
          uriBuilder
            .path(path)
            .apply {
              request.queryString?.let {
                query(it)
              }
            }.build()
            .also {
              logger.debug {
                "Raw Kobo proxy URL: $it"
              }
            }
        }.headers { headersOut ->
          request.headerNames
            .toList()
            .filter(::shouldForwardRequestHeader)
            .forEach { headerName ->
              headersOut.addAll(
                headerName,
                request
                  .getHeaders(headerName)
                  ?.toList()
                  ?: emptyList(),
              )
            }
        }.apply {
          if (body != null) {
            body(body)
          }
        }.retrieve()
        .onStatus(HttpStatusCode::isError) { _, upstream ->
          val upstreamBody =
            upstream.body
              .bufferedReader()
              .use { it.readText() }

          logger.debug {
            "Raw Kobo response: " +
              "${upstream.statusCode}: $upstreamBody"
          }

          throw ResponseStatusException(
            upstream.statusCode,
            upstream.statusText,
          )
        }.toEntity<JsonNode>()

    logger.debug {
      "Raw Kobo response status=${response.statusCode}, " +
        "body=${response.body}"
    }

    val headersToReturn =
      response.headers
        .filterKeys(::isKoboHeader)
        .toMutableMap()

    return ResponseEntity(
      response.body,
      LinkedMultiValueMap(headersToReturn),
      response.statusCode,
    )
  }
}
