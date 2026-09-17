// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.interfaces.api.kobo

// KOBO_STORE_HYBRID_V2

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(
  value = ["/kobo/{authToken}/"],
  produces = ["application/json; charset=utf-8"],
)
class KoboRemainingEndpointsController(
  private val koboRawStoreProxy: KoboRawStoreProxy,
  private val koboProductResolver: KoboProductResolver,
  private val koboSeriesIdResolver: KoboSeriesIdResolver,
  private val koboLocalStoreResponseBuilder: KoboLocalStoreResponseBuilder,
) {
  @GetMapping(
    value = [
      "/v1/products/books/{bookId}",
      "/v1/products/books/{bookId}/",
    ],
  )
  fun getBookDetails(
    @PathVariable bookId: String,
  ): ResponseEntity<JsonNode> {
    // Spring can route these static Store paths through {bookId} depending on
    // mapping specificity; they are not local book lookups.
    if (
      bookId.equals("subscriptions", ignoreCase = true) ||
      bookId.equals("series", ignoreCase = true)
    ) {
      return koboRawStoreProxy.proxyCurrentRequest()
    }

    if (!koboLocalStoreResponseBuilder.isLocalBook(bookId)) {
      return koboRawStoreProxy.proxyCurrentRequest()
    }

    val productId = koboProductResolver.resolveProductId(bookId)
    val upstream =
      productId?.let {
        try {
          koboRawStoreProxy.proxyCurrentRequest(
            overridePath = "/v1/products/books/$it/",
          )
        } catch (e: Exception) {
          logger.debug(e) {
            "BookDetails enrichment failed for local book $bookId via Kobo ProductId $it; using local fallback"
          }
          null
        }
      }

    val body =
      koboLocalStoreResponseBuilder.buildBookDetails(
        bookId = bookId,
        upstreamBook = upstream?.body,
      ) ?: throw org.springframework.web.server.ResponseStatusException(
        org.springframework.http.HttpStatus.NOT_FOUND,
      )

    logger.debug {
      if (upstream != null) {
        "BookDetails: enriched local Komga book $bookId from Kobo ProductId $productId, then overlaid Komga metadata"
      } else {
        "BookDetails: built local Komga fallback for $bookId without Kobo Stats/rating"
      }
    }

    return if (upstream != null) {
      // KoboRawStoreProxy already strips ordinary entity headers and retains
      // only x-kobo-* response headers. Preserve those (for example
      // x-kobo-apitoken) while returning the rewritten JSON body.
      ResponseEntity
        .status(upstream.statusCode)
        .headers(upstream.headers)
        .body(body)
    } else {
      ResponseEntity.ok(body)
    }
  }

  @GetMapping("/v1/products/books/series/{seriesId}")
  fun getSeries(
    @PathVariable seriesId: String,
  ): ResponseEntity<JsonNode> {
    val localBookIds = koboLocalStoreResponseBuilder.localSeriesBookIds(seriesId)
    if (localBookIds.isEmpty()) {
      return koboRawStoreProxy.proxyCurrentRequest()
    }

    val koboSeriesId = koboSeriesIdResolver.resolveSeriesId(seriesId)
    val upstream =
      koboSeriesId?.let {
        try {
          koboRawStoreProxy.proxyCurrentRequest(
            overridePath = "/v1/products/books/series/$it",
          )
        } catch (e: Exception) {
          logger.debug(e) {
            "Series enrichment failed for local series $seriesId via Kobo SeriesId $it; using local fallback"
          }
          null
        }
      }

    val body =
      koboLocalStoreResponseBuilder.buildSeries(
        seriesId = seriesId,
        upstreamSeries = upstream?.body,
      ) ?: throw org.springframework.web.server.ResponseStatusException(
        org.springframework.http.HttpStatus.NOT_FOUND,
      )

    logger.debug {
      if (upstream != null) {
        "Series: kept Komga membership for $seriesId and enriched matching members from Kobo SeriesId $koboSeriesId"
      } else {
        "Series: built local Komga-only fallback for $seriesId"
      }
    }

    return if (upstream != null) {
      // KoboRawStoreProxy already strips ordinary entity headers and retains
      // only x-kobo-* response headers. Preserve those (for example
      // x-kobo-apitoken) while returning the rewritten JSON body.
      ResponseEntity
        .status(upstream.statusCode)
        .headers(upstream.headers)
        .body(body)
    } else {
      ResponseEntity.ok(body)
    }
  }

  @GetMapping("/v1/products/{productIds}/prices")
  fun getPrices(
    @PathVariable productIds: String,
  ): ResponseEntity<JsonNode> {
    val requestedIds =
      productIds
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    if (requestedIds.none(koboLocalStoreResponseBuilder::isLocalBook)) {
      return koboRawStoreProxy.proxyCurrentRequest()
    }

    val koboOnlyIds =
      requestedIds.filterNot(koboLocalStoreResponseBuilder::isLocalBook)

    val upstream =
      if (koboOnlyIds.isEmpty()) {
        null
      } else {
        koboRawStoreProxy.proxyCurrentRequest(
          overridePath =
            "/v1/products/${koboOnlyIds.joinToString(",")}/prices",
        )
      }

    val body =
      koboLocalStoreResponseBuilder.buildPrices(
        requestedIds = requestedIds,
        upstreamPrices = upstream?.body,
      )

    logger.debug {
      "Prices: synthesized ${requestedIds.size - koboOnlyIds.size} local item(s) and proxied ${koboOnlyIds.size} Kobo item(s)"
    }

    return if (upstream != null) {
      // KoboRawStoreProxy already strips ordinary entity headers and retains
      // only x-kobo-* response headers. Preserve those (for example
      // x-kobo-apitoken) while returning the rewritten JSON body.
      ResponseEntity
        .status(upstream.statusCode)
        .headers(upstream.headers)
        .body(body)
    } else {
      ResponseEntity.ok(body)
    }
  }
}
