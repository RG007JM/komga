package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * Kobo feedback is a *write* to the Kobo account, not an update to Komga's local
 * reading progress. It must use Kobo revision IDs; local entitlement IDs cannot
 * be forwarded as-is. This specific mapping takes precedence over catchAll().
 */
@RestController
@RequestMapping(value = ["/kobo/{authToken}/"], produces = ["application/json; charset=utf-8"])
class KoboRecommendationsFeedbackController(
  private val koboRawStoreProxy: KoboRawStoreProxy,
  private val koboRecommendationsFeedbackTranslator: KoboRecommendationsFeedbackTranslator,
) {
  @PostMapping(value = ["v1/user/recommendations/feedback"], consumes = ["application/json"])
  fun postFeedback(
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @RequestBody rawBody: ByteArray,
  ): ResponseEntity<JsonNode> {
    // Do not acknowledge cloud feedback that we have neither persisted nor sent.
    if (!koboRawStoreProxy.isEnabled()) {
      throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Kobo Store proxy is disabled")
    }

    val translated = koboRecommendationsFeedbackTranslator.translate(rawBody, principal)
    return koboRawStoreProxy.proxyCurrentRequest(body = translated)
  }
}
