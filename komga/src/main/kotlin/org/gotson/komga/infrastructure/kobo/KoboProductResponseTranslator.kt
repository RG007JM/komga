package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboProductResponseTranslator(
  private val koboProductResolver: KoboProductResolver,
) {
  fun translate(
    path: String,
    body: JsonNode,
  ): JsonNode {
    val normalizedPath =
      path.lowercase()

    return when {
      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/recommendations$"""),
      ) ->
        translateRecommendations(body)

      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/nextread$"""),
      ) ->
        translateNextRead(body)

      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/reviews$"""),
      ) ->
        translateReviews(body)

      /*
       * We deliberately do not rewrite /related yet.
       *
       * We do not have a real related response body, so its structure
       * remains unknown.
       */
      else ->
        body
    }
  }

  /**
   * Recommendations response:
   *
   * {
   *   "Items": [
   *     {
   *       "Book": {
   *         "Id": "<Kobo ProductId>"
   *       }
   *     }
   *   ]
   * }
   */
  private fun translateRecommendations(
    body: JsonNode,
  ): JsonNode {
    val items =
      body.get("Items")
        ?: return body

    if (!items.isArray) {
      return body
    }

    val cache =
      mutableMapOf<String, String?>()

    items.forEach { item ->
      val book =
        item.get("Book")

      if (book is ObjectNode) {
        translateTextField(
          node = book,
          fieldName = "Id",
          cache = cache,
        )
      }
    }

    return body
  }

  /**
   * Nextread response:
   *
   * {
   *   "<requested Kobo ProductId>": [
   *     {
   *       "Id": "<Kobo ProductId>"
   *     }
   *   ]
   * }
   *
   * Both the top-level key and returned book Id values need translation.
   */
  private fun translateNextRead(
    body: JsonNode,
  ): JsonNode {
    if (body !is ObjectNode) {
      return body
    }

    val cache =
      mutableMapOf<String, String?>()

    val translatedBody =
      body.objectNode()

    val fields =
      body.fields()

    while (fields.hasNext()) {
      val entry =
        fields.next()

      val koboProductId =
        entry.key

      val translatedKey =
        resolveBookId(
          productId = koboProductId,
          cache = cache,
        ) ?: koboProductId

      val value =
        entry.value

      if (value.isArray) {
        value.forEach { item ->
          if (item is ObjectNode) {
            translateTextField(
              node = item,
              fieldName = "Id",
              cache = cache,
            )
          }
        }
      }

      translatedBody.set<JsonNode>(
        translatedKey,
        value,
      )
    }

    return translatedBody
  }

  /**
   * Reviews response:
   *
   * {
   *   "Items": [
   *     {
   *       "ProductId": "<Kobo ProductId>",
   *       "RevisionId": "...",
   *       "CrossRevisionId": "..."
   *     }
   *   ]
   * }
   *
   * Only ProductId is translated.
   */
  private fun translateReviews(
    body: JsonNode,
  ): JsonNode {
    val items =
      body.get("Items")
        ?: return body

    if (!items.isArray) {
      return body
    }

    val cache =
      mutableMapOf<String, String?>()

    items.forEach { item ->
      if (item is ObjectNode) {
        translateTextField(
          node = item,
          fieldName = "ProductId",
          cache = cache,
        )
      }
    }

    return body
  }

  private fun translateTextField(
    node: ObjectNode,
    fieldName: String,
    cache: MutableMap<String, String?>,
  ) {
    val value =
      node
        .get(fieldName)
        ?.takeIf {
          it.isTextual
        }?.asText()
        ?.takeIf {
          it.isNotBlank()
        }
        ?: return

    val bookId =
      resolveBookId(
        productId = value,
        cache = cache,
      ) ?: return

    if (bookId == value) {
      return
    }

    logger.debug {
      "Translated Kobo response ProductId $value to Komga book ID $bookId"
    }

    node.put(
      fieldName,
      bookId,
    )
  }

  private fun resolveBookId(
    productId: String,
    cache: MutableMap<String, String?>,
  ): String? {
    if (cache.containsKey(productId)) {
      return cache[productId]
    }

    val bookId =
      koboProductResolver
        .resolveBookId(productId)

    cache[productId] =
      bookId

    return bookId
  }
}
