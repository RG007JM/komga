// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboTagRequestTranslator(
  private val objectMapper: ObjectMapper,
  private val koboProductResolver: KoboProductResolver,
) {
  /** Translate local Items[].RevisionId values to real Kobo ProductIds. */
  fun translate(body: ByteArray?): ByteArray? {
    if (body == null || body.isEmpty()) {
      return body
    }

    val root =
      try {
        objectMapper.readTree(body)
      } catch (e: Exception) {
        logger.debug(e) {
          "Could not parse Kobo tags request body; proxying it unchanged"
        }
        return body
      }

    val items = root.path("Items")
    if (!items.isArray) {
      return body
    }

    var changed = false

    items.forEach { item ->
      if (item !is ObjectNode) {
        return@forEach
      }

      val revisionIdNode = item.get("RevisionId")
      if (revisionIdNode?.isTextual != true) {
        return@forEach
      }

      val revisionId =
        revisionIdNode
          .asText()
          .takeIf { it.isNotBlank() }
          ?: return@forEach

      val productId =
        koboProductResolver.resolveProductId(revisionId)
          ?: return@forEach

      if (productId == revisionId) {
        return@forEach
      }

      item.put(
        "RevisionId",
        productId,
      )
      changed = true

      logger.debug {
        "Translated Kobo tag RevisionId " +
          "$revisionId to ProductId $productId"
      }
    }

    return if (changed) {
      objectMapper.writeValueAsBytes(root)
    } else {
      body
    }
  }
}
