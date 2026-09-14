// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.gotson.komga.infrastructure.kobo.KoboTagRequestTranslator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
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
  private val koboTagRequestTranslator: KoboTagRequestTranslator,
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
    // Static products/books/subscriptions also matches {bookId}.
    // It requires no translation, so raw proxy it unchanged.
    if (
      bookId.equals("subscriptions", ignoreCase = true) ||
      bookId.equals("series", ignoreCase = true)
    ) {
      return koboRawStoreProxy.proxyCurrentRequest()
    }

    val productId =
      koboProductResolver.resolveProductId(bookId)
        ?: bookId

    if (productId != bookId) {
      logger.debug {
        "BookDetails: translated Komga book " +
          "$bookId to Kobo ProductId $productId"
      }
    }

    return koboRawStoreProxy.proxyCurrentRequest(
      overridePath =
        "/v1/products/books/$productId/",
    )
  }

  @GetMapping("/v1/products/books/series/{seriesId}")
  fun getSeries(
    @PathVariable seriesId: String,
  ): ResponseEntity<JsonNode> {
    val koboSeriesId =
      koboSeriesIdResolver.resolveSeriesId(seriesId)
        ?: seriesId

    if (koboSeriesId != seriesId) {
      logger.debug {
        "Series: translated Komga series " +
          "$seriesId to Kobo SeriesId $koboSeriesId"
      }
    }

    return koboRawStoreProxy.proxyCurrentRequest(
      overridePath =
        "/v1/products/books/series/$koboSeriesId",
    )
  }

  @GetMapping("/v1/products/{productIds}/prices")
  fun getPrices(
    @PathVariable productIds: String,
  ): ResponseEntity<JsonNode> {
    val translatedIds =
      productIds
        .split(",")
        .joinToString(",") { id ->
          val cleanId = id.trim()

          koboProductResolver
            .resolveProductId(cleanId)
            ?: cleanId
        }

    if (translatedIds != productIds) {
      logger.debug {
        "Prices: translated $productIds -> $translatedIds"
      }
    }

    return koboRawStoreProxy.proxyCurrentRequest(
      overridePath =
        "/v1/products/$translatedIds/prices",
    )
  }

  @PostMapping("/v1/library/tags")
  fun createTag(
    @RequestBody(required = false) body: ByteArray?,
  ): ResponseEntity<JsonNode> =
    koboRawStoreProxy.proxyCurrentRequest(
      body =
        koboTagRequestTranslator.translate(body),
    )

  @RequestMapping(
    value = ["/v1/library/tags/{tagId}/Items"],
    method = [
      RequestMethod.POST,
      RequestMethod.PUT,
      RequestMethod.PATCH,
      RequestMethod.DELETE,
    ],
  )
  fun mutateTagItems(
    @PathVariable tagId: String,
    @RequestBody(required = false) body: ByteArray?,
  ): ResponseEntity<JsonNode> {
    logger.debug {
      "Proxying Kobo tag item mutation for TagId $tagId"
    }

    return koboRawStoreProxy.proxyCurrentRequest(
      body =
        koboTagRequestTranslator.translate(body),
    )
  }
}
