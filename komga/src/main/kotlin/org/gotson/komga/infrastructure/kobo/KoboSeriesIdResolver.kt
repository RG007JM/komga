// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboHeaders.X_KOBO_SYNCTOKEN
import org.gotson.komga.infrastructure.web.getCurrentRequest
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException
import org.springframework.web.util.DefaultUriBuilderFactory
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private val logger = KotlinLogging.logger {}

@Component
class KoboSeriesIdResolver(
  private val dataSource: DataSource,
  private val koboProductResolver: KoboProductResolver,
) {
  private val cache =
    ConcurrentHashMap<String, String>()

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
      ).build()

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
   * Resolve Komga SeriesId -> Kobo SeriesId through a confirmed book mapping.
   * Series names are never used as the identity match.
   */
  fun resolveSeriesId(komgaSeriesId: String): String? {
    cache[komgaSeriesId]?.let {
      return it
    }

    val bookIds = findLocalBookIds(komgaSeriesId)

    for (bookId in bookIds) {
      val productId =
        koboProductResolver.resolveProductId(bookId)
          ?: continue

      val koboSeriesId =
        fetchKoboSeriesId(productId)
          ?: continue

      cache[komgaSeriesId] = koboSeriesId

      logger.debug {
        "Resolved Komga series $komgaSeriesId " +
          "to Kobo SeriesId $koboSeriesId " +
          "via book $bookId / ProductId $productId"
      }

      return koboSeriesId
    }

    logger.warn {
      "Could not resolve Kobo SeriesId for " +
        "Komga series $komgaSeriesId"
    }

    return null
  }

  private fun findLocalBookIds(seriesId: String): List<String> =
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          "SELECT ID FROM BOOK WHERE SERIES_ID = ? ORDER BY ID",
        ).use { statement ->
          statement.setString(1, seriesId)

          statement.executeQuery().use { resultSet ->
            buildList {
              while (resultSet.next()) {
                add(resultSet.getString(1))
              }
            }
          }
        }
    }

  private fun fetchKoboSeriesId(
    productId: String,
  ): String? {
    val request = getCurrentRequest()

    return try {
      val body =
        koboApiClient
          .get()
          .uri(
            "/v1/products/books/$productId/" +
              "?SendToBrowseHistory=false&SendBookStats=false",
          ).headers { headersOut ->
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
          }.retrieve()
          .body(JsonNode::class.java)

      body
        ?.path("SeriesId")
        ?.takeIf { it.isTextual }
        ?.asText()
        ?.takeIf { it.isNotBlank() }
    } catch (e: RestClientException) {
      logger.debug(e) {
        "Kobo BookDetails lookup failed while " +
          "discovering SeriesId for ProductId $productId"
      }
      null
    }
  }
}
