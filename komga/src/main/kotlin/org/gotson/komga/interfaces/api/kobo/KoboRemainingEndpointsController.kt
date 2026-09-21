// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.interfaces.api.kobo

// KOBO_STORE_HYBRID_V2

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboDeviceSeriesObserver
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
  private val koboDeviceSeriesObserver: KoboDeviceSeriesObserver,
  private val koboSeriesProductDiscovery: org.gotson.komga.infrastructure.kobo.KoboSeriesProductDiscovery,
  private val koboLocalStoreResponseBuilder: KoboLocalStoreResponseBuilder,
  private val contentRestrictionChecker: ContentRestrictionChecker,
) {
  @GetMapping(
    value = [
      "/v1/products/books/{bookId}",
      "/v1/products/books/{bookId}/",
    ],
  )
  fun getBookDetails(
    @AuthenticationPrincipal principal: KomgaPrincipal,
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
      val upstream = koboRawStoreProxy.proxyCurrentRequest()
      observeBookDetails(bookId, upstream.body, principal)
      return upstream
    }

    contentRestrictionChecker.checkContentRestrictionBook(principal.user, bookId)

    val productId = koboProductResolver.resolveProductIdForDevice(bookId)
    val upstream =
      productId?.takeIf { koboRawStoreProxy.isEnabled() }?.let {
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

    if (productId != null) observeBookDetails(productId, upstream?.body, principal, bookId)

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
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @PathVariable seriesId: String,
    @RequestParam(name = "PageSize", defaultValue = "100") pageSize: Int,
    @RequestParam(name = "PageIndex", defaultValue = "0") pageIndex: Int,
  ): ResponseEntity<JsonNode> {
    val localBookIds = koboLocalStoreResponseBuilder.localSeriesBookIds(seriesId)
    if (localBookIds.isEmpty()) {
      val upstream = koboRawStoreProxy.proxyCurrentRequest()
      observeSeries(seriesId, upstream.body, principal)
      return upstream
    }

    contentRestrictionChecker.checkContentRestrictionSeries(principal.user, seriesId)

    val koboSeriesId = koboSeriesIdResolver.resolveSeriesId(seriesId)
    val upstream =
      koboSeriesId?.takeIf { koboRawStoreProxy.isEnabled() }?.let {
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

    // Only consume the response from this device-initiated proxy request.
    koboSeriesProductDiscovery.learn(localBookIds, upstream?.body)
    observeSeries(koboSeriesId, upstream?.body, principal)

    val body =
      koboLocalStoreResponseBuilder.buildSeries(
        seriesId = seriesId,
        upstreamSeries = upstream?.body,
        pageSize = pageSize,
        pageIndex = pageIndex,
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

  private fun observeBookDetails(
    productId: String,
    upstream: JsonNode?,
    principal: KomgaPrincipal,
    localBookId: String? = null,
  ) {
    try {
      koboDeviceSeriesObserver.observeBookDetails(productId, upstream, localBookId) { bookId ->
        runCatching { contentRestrictionChecker.checkContentRestrictionBook(principal.user, bookId) }.isSuccess
      }
    } catch (e: Exception) {
      logger.debug(e) { "Could not observe device BookDetails SeriesId" }
    }
  }

  private fun observeSeries(
    seriesId: String?,
    upstream: JsonNode?,
    principal: KomgaPrincipal,
  ) {
    try {
      koboDeviceSeriesObserver.observeSeriesResponse(seriesId, upstream) { bookId ->
        runCatching { contentRestrictionChecker.checkContentRestrictionBook(principal.user, bookId) }.isSuccess
      }
    } catch (e: Exception) {
      logger.debug(e) { "Could not observe device Series response SeriesId" }
    }
  }

  @GetMapping("/v1/products/{productIds}/prices")
  fun getPrices(
    @AuthenticationPrincipal principal: KomgaPrincipal,
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

    requestedIds
      .filter(koboLocalStoreResponseBuilder::isLocalBook)
      .forEach { contentRestrictionChecker.checkContentRestrictionBook(principal.user, it) }

    val koboOnlyIds =
      requestedIds.filterNot(koboLocalStoreResponseBuilder::isLocalBook)

    val upstream =
      if (koboOnlyIds.isEmpty() || !koboRawStoreProxy.isEnabled()) {
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
