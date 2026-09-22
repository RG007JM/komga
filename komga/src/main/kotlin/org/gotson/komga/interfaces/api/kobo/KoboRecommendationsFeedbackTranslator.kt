package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

/**
 * Preserve the Kobo request verbatim unless a RevisionId refers to a local
 * Komga book. Translate only those IDs, without mutating the feedback kind or
 * assuming that all feedback items have the same book or the same feedback type.
 */
@Component
class KoboRecommendationsFeedbackTranslator(
  private val objectMapper: ObjectMapper,
  private val bookRepository: BookRepository,
  private val koboProductResolver: KoboProductResolver,
  private val contentRestrictionChecker: ContentRestrictionChecker,
) {
  fun translate(
    body: ByteArray,
    principal: KomgaPrincipal,
  ): ByteArray {
    val root =
      try {
        objectMapper.readTree(body)
      } catch (_: Exception) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Kobo feedback JSON")
      }

    val items = root?.get("FeedbackItems")
    if (root !is ObjectNode || items == null || !items.isArray || items.size() == 0) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "FeedbackItems must be a non-empty array")
    }

    val resolved = mutableMapOf<String, String>()
    var changed = false
    items.forEach { item ->
      val revisionId = item.get("RevisionId")?.takeIf { it.isTextual }?.asText()
      val feedbackType = item.get("FeedbackType")?.takeIf { it.isTextual }?.asText()
      if (item !is ObjectNode || revisionId.isNullOrBlank() || feedbackType.isNullOrBlank()) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Feedback item needs FeedbackType and RevisionId")
      }

      // Genuine Kobo revision IDs are not Komga books and must stay unchanged.
      val localBook = bookRepository.findByIdOrNull(revisionId) ?: return@forEach
      contentRestrictionChecker.checkContentRestrictionBook(principal.user, localBook)

      val koboId =
        resolved.getOrPut(revisionId) {
          koboProductResolver.resolveProductIdForDevice(revisionId)
            ?: throw ResponseStatusException(
              HttpStatus.UNPROCESSABLE_ENTITY,
              "No verified Kobo product ID for local feedback item",
            )
        }
      if (koboId != revisionId) {
        item.put("RevisionId", koboId)
        changed = true
      }
    }
    return if (changed) objectMapper.writeValueAsBytes(root) else body
  }
}
