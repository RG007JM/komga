package org.gotson.komga.infrastructure.kobo

// KOBO_STORE_HYBRID_V2

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.interfaces.api.kobo.persistence.KoboDtoRepository
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboProductResponseTranslator(
  private val koboProductResolver: KoboProductResolver,
  private val koboLocalBookLookup: KoboLocalBookLookup,
  private val koboDtoRepository: KoboDtoRepository,
) {
  fun translate(
    path: String,
    body: JsonNode,
  ): JsonNode {
    val normalizedPath =
      path
        .substringBefore("?")
        .trimEnd('/')
        .lowercase()

    return when {
      normalizedPath == "/v1/products" ->
        translateBookItems(body)

      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/recommendations$"""),
      ) ->
        translateBookItems(body)

      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/nextread$"""),
      ) ->
        translateNextRead(body)

      normalizedPath.matches(
        Regex("""^/v1/products/[^/]+/reviews$"""),
      ) ->
        translateProductReviewsWithLocalCrossRevision(body)

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
   * Product search and recommendations responses:
   *
   * {
   *   "Items": [
   *     {
   *       "Book": {
   *         "Id": "<Kobo ProductId>",
   *         "CrossRevisionId": "...",
   *         "WorkId": "...",
   *         "RevisionId": "...",
   *         "SeriesId": "...",
   *         "ImageId": "...",
   *         "ISBN": "..."
   *       }
   *     }
   *   ]
   * }
   *
   * If the ProductId already has a known Komga mapping, that mapping is
   * used directly.
   *
   * If the ProductId is unknown, the Kobo ISBN is used as a fallback to
   * locate exactly one corresponding Komga book.
   *
   * For matched Komga books, Id, CrossRevisionId, WorkId, and RevisionId
   * are translated to the Komga book ID to match Komga's Kobo identity
   * model.
   *
   * ImageId, Description, and the complete Series tuple are overlaid from
   * Komga when local metadata is available. Kobo-only results remain
   * untouched; unrelated store enrichment remains Kobo-native.
   */
  private fun translateBookItems(
    body: JsonNode,
  ): JsonNode {
    val booksNeedingLocalCovers =
      mutableListOf<Pair<ObjectNode, String>>()
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

      if (book !is ObjectNode) {
        return@forEach
      }

      val productId =
        book
          .get("Id")
          ?.takeIf { it.isTextual }
          ?.asText()
          ?.takeIf { it.isNotBlank() }
          ?: return@forEach

      val isbn =
        normalizeIsbn(
          book
            .get("ISBN")
            ?.takeIf { it.isTextual }
            ?.asText(),
        )

      val bookId =
        resolveBookId(
          productId = productId,
          isbn = isbn,
          cache = cache,
        ) ?: return@forEach

      if (bookId == productId) {
        return@forEach
      }

      book.put(
        "Id",
        bookId,
      )

      if (book.get("CrossRevisionId")?.isTextual == true) {
        book.put(
          "CrossRevisionId",
          bookId,
        )
      }

      if (book.get("WorkId")?.isTextual == true) {
        book.put(
          "WorkId",
          bookId,
        )
      }

      if (book.get("RevisionId")?.isTextual == true) {
        book.put(
          "RevisionId",
          bookId,
        )
      }

      if (book.get("ImageId")?.isTextual == true) {
        booksNeedingLocalCovers += book to bookId
      }

      logger.debug {
        "Translated Kobo book identity $productId to Komga book ID $bookId"
      }
    }

    applyLocalCoverImageIds(booksNeedingLocalCovers)

    return body
  }

  /**
   * Nextread response:
   *
   * {
   *   "<requested Kobo ProductId>": [
   *     {
   *       "Id": "<Kobo ProductId>",
   *       "ISBN": "...",
   *       "RevisionId": "...",
   *       "CrossRevisionId": "...",
   *       "WorkId": "..."
   *     }
   *   ]
   * }
   *
   * The top-level key identifies the source book and needs translation
   * from the native Kobo ProductId back to the Komga book ID.
   *
   * Returned books use an existing ProductId mapping where available,
   * otherwise their ISBN is used to discover exactly one local Komga
   * book.
   *
   * For matched books, Id, RevisionId, CrossRevisionId, and WorkId are
   * translated to the Komga book ID. ImageId, Description, and the complete
   * Series tuple are overlaid from Komga when local metadata is available;
   * unrelated Kobo store metadata remains untouched.
   */
  private fun translateNextRead(
    body: JsonNode,
  ): JsonNode {
    val booksNeedingLocalCovers =
      mutableListOf<Pair<ObjectNode, String>>()
    if (!body.isObject) {
      return body
    }

    val cache =
      mutableMapOf<String, String?>()
    val translatedBody =
      (body as ObjectNode).objectNode()

    body.properties().forEach { entry ->
      val sourceProductId =
        entry.key

      val translatedKey =
        resolveBookId(
          productId = sourceProductId,
          cache = cache,
        ) ?: sourceProductId

      val translatedItems =
        entry.value.deepCopy<JsonNode>()

      if (translatedItems.isArray) {
        translatedItems.forEach { item ->
          if (item !is ObjectNode) {
            return@forEach
          }

          val productId =
            item
              .get("Id")
              ?.takeIf { it.isTextual }
              ?.asText()
              ?.takeIf { it.isNotBlank() }
              ?: return@forEach

          val isbn =
            normalizeIsbn(
              item
                .get("ISBN")
                ?.takeIf { it.isTextual }
                ?.asText(),
            )

          val bookId =
            resolveBookId(
              productId = productId,
              isbn = isbn,
              cache = cache,
            ) ?: return@forEach

          if (bookId == productId) {
            return@forEach
          }

          item.put(
            "Id",
            bookId,
          )

          if (item.get("RevisionId")?.isTextual == true) {
            item.put(
              "RevisionId",
              bookId,
            )
          }

          if (item.get("CrossRevisionId")?.isTextual == true) {
            item.put(
              "CrossRevisionId",
              bookId,
            )
          }

          if (item.get("WorkId")?.isTextual == true) {
            item.put(
              "WorkId",
              bookId,
            )
          }

          if (item.get("ImageId")?.isTextual == true) {
            booksNeedingLocalCovers += item to bookId
          }

          logger.debug {
            "Translated Kobo nextread book identity $productId to Komga book ID $bookId"
          }
        }
      }

      translatedBody.set<JsonNode>(
        translatedKey,
        translatedItems,
      )
    }

    applyLocalCoverImageIds(booksNeedingLocalCovers)

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
        ?.takeIf { it.isTextual }
        ?.asText()
        ?.takeIf { it.isNotBlank() }
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
      "Translated Kobo response $fieldName $value to Komga book ID $bookId"
    }

    node.put(
      fieldName,
      bookId,
    )
  }

  // KOBO_LOCAL_IMAGE_ID_V1
  // KOBO_STORE_IDENTITY_FINAL_CLEANUP_V1
  private fun applyLocalCoverImageIds(
    books: Collection<Pair<ObjectNode, String>>,
  ) {
    if (books.isEmpty()) {
      return
    }

    val bookIds =
      books
        .map { it.second }
        .distinct()

    val metadataByBookId =
      koboDtoRepository
        .findBookMetadataByIds(bookIds)
        .associateBy { it.entitlementId }

    books.forEach { (book, bookId) ->
      val metadata =
        metadataByBookId[bookId]
          ?: return@forEach

      metadata.coverImageId
        ?.takeIf { it.isNotBlank() }
        ?.let { coverImageId ->
          book.put(
            "ImageId",
            coverImageId,
          )

          logger.debug {
            "Translated Kobo ImageId to Komga cover image ID $coverImageId for book $bookId"
          }
        }

      metadata.description?.let { description ->
        book.put(
          "Description",
          description,
        )
      }

      val series = metadata.series

      if (series != null) {
        book.put(
          "SeriesId",
          series.id,
        )
        book.put(
          "SeriesName",
          series.name,
        )
        book.putPOJO(
          "SeriesNumber",
          series.number,
        )
        book.putPOJO(
          "SeriesNumberFloat",
          series.numberFloat,
        )

        // These are Kobo-native series/group identities and no longer match
        // once the returned book is represented as a local Komga book.
        book.remove("SeriesSlug")
        book.remove("RelatedGroupId")
      } else {
        // Local one-shots must not retain a Kobo series identity.
        book.remove("SeriesId")
        book.remove("SeriesName")
        book.remove("SeriesNumber")
        book.remove("SeriesNumberFloat")
        book.remove("SeriesSlug")
        book.remove("RelatedGroupId")
      }

      logger.debug {
        "Overlaid Komga Description/Series metadata for book $bookId"
      }
    }
  }

  private fun resolveBookId(
    productId: String,
    isbn: String?,
    cache: MutableMap<String, String?>,
  ): String? {
    if (cache.containsKey(productId)) {
      return cache[productId]
    }

    val mappedBookId =
      koboProductResolver
        .resolveBookId(productId)

    if (mappedBookId != null) {
      cache[productId] =
        mappedBookId

      return mappedBookId
    }

    val discoveredBookId =
      isbn
        ?.let {
          koboLocalBookLookup
            .findUniqueBookIdByIsbn(it)
        }

    if (discoveredBookId != null) {
      logger.debug {
        "Discovered Komga book ID $discoveredBookId from Kobo ProductId $productId via ISBN $isbn"
      }
    }

    cache[productId] =
      discoveredBookId

    return discoveredBookId
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

  private fun normalizeIsbn(
    value: String?,
  ): String? =
    value
      ?.filter { it.isDigit() }
      ?.takeIf { it.length == 13 }

  /**
   * Product reviews are still Kobo-owned. ProductId is translated to the local
   * Komga book as before. CrossRevisionId is translated only when the response
   * itself proves the identity is coherent: every review for that ProductId has
   * one identical CrossRevisionId and ReviewSummary is keyed by that same id.
   *
   * RevisionId is always translated to the local Komga book for mapped reviews.
   */
  private fun translateProductReviewsWithLocalCrossRevision(
    body: JsonNode,
  ): JsonNode {
    if (body !is ObjectNode) {
      return body
    }

    val items = body.path("Items")
    if (!items.isArray) {
      return body
    }

    val reviewItems = items.filterIsInstance<ObjectNode>()
    val reviewSummary = body.get("ReviewSummary") as? ObjectNode
    val cache = mutableMapOf<String, String?>()

    val crossRevisionByProductId =
      reviewItems
        .groupBy {
          it
            .get("ProductId")
            ?.takeIf { it.isTextual }
            ?.asText()
            .orEmpty()
        }.mapNotNull { (productId, reviews) ->
          if (productId.isBlank()) return@mapNotNull null

          val crossRevisionIds =
            reviews.mapNotNull {
              it
                .get("CrossRevisionId")
                ?.takeIf { it.isTextual }
                ?.asText()
                ?.takeIf { it.isNotBlank() }
            }

          if (crossRevisionIds.size != reviews.size) return@mapNotNull null

          val distinct = crossRevisionIds.toSet()
          if (distinct.size != 1) return@mapNotNull null

          val crossRevisionId = distinct.single()
          if (reviewSummary?.has(crossRevisionId) != true) return@mapNotNull null

          productId to crossRevisionId
        }.toMap()

    val summaryMoves = mutableMapOf<String, String>()

    reviewItems.forEach { review ->
      val productId =
        review
          .get("ProductId")
          ?.takeIf { it.isTextual }
          ?.asText()
          ?.takeIf { it.isNotBlank() }
          ?: return@forEach

      val bookId =
        resolveBookId(
          productId = productId,
          cache = cache,
        ) ?: return@forEach

      if (bookId == productId) {
        return@forEach
      }

      review.put("ProductId", bookId)
      review.put("RevisionId", bookId)

      val expectedCrossRevisionId = crossRevisionByProductId[productId]
      if (
        expectedCrossRevisionId != null &&
        review.path("CrossRevisionId").asText() == expectedCrossRevisionId
      ) {
        review.put("CrossRevisionId", bookId)
        summaryMoves[expectedCrossRevisionId] = bookId
      }
    }

    if (reviewSummary != null) {
      summaryMoves.forEach { (koboCrossRevisionId, bookId) ->
        val summary = reviewSummary.remove(koboCrossRevisionId)
        if (summary != null) {
          reviewSummary.set<JsonNode>(bookId, summary)
          logger.debug {
            "Translated Kobo review CrossRevisionId/ReviewSummary $koboCrossRevisionId to Komga book ID $bookId"
          }
        }
      }
    }

    return body
  }
}
