// KOBO_STORE_HYBRID_V2
package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.gotson.komga.interfaces.api.kobo.dto.KoboBookMetadataDto
import org.gotson.komga.interfaces.api.kobo.persistence.KoboDtoRepository
import org.springframework.stereotype.Component
import javax.sql.DataSource

private const val LOCAL_PRICE_CURRENCY = "EUR"
private const val LOCAL_PRICE_AMOUNT = 1.0

/**
 * Builds Store-shaped local responses without inventing Kobo-only information.
 *
 * When an upstream Kobo Book object is supplied it is used as an enrichment
 * template. Store-only fields such as Stats, Rating, TotalRating, accessibility,
 * preview information, etc. survive unless they conflict with Komga-owned
 * identity/metadata below.
 */
@Component
class KoboLocalStoreResponseBuilder(
  private val objectMapper: ObjectMapper,
  private val bookRepository: BookRepository,
  private val koboDtoRepository: KoboDtoRepository,
  private val koboProductResolver: KoboProductResolver,
  private val dataSource: DataSource,
  private val koboSeriesIdResolver: KoboSeriesIdResolver,
) {
  fun isLocalBook(bookId: String): Boolean = bookRepository.existsById(bookId)

  fun localSeriesBookIds(seriesId: String): List<String> =
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          "SELECT ID FROM BOOK WHERE SERIES_ID = ?",
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

  fun buildBookDetails(
    bookId: String,
    upstreamBook: JsonNode?,
  ): JsonNode? {
    val metadata = metadataByIds(listOf(bookId))[bookId] ?: return null
    return overlayLocalBook(
      bookId = bookId,
      metadata = metadata,
      upstreamBook = upstreamBook,
    )
  }

  fun buildSeries(
    seriesId: String,
    upstreamSeries: JsonNode?,
  ): JsonNode? {
    val bookIds = localSeriesBookIds(seriesId)
    if (bookIds.isEmpty()) return null

    val metadataById = metadataByIds(bookIds)
    val orderedMetadata =
      bookIds
        .mapNotNull(metadataById::get)
        .sortedWith(
          compareBy<KoboBookMetadataDto> {
            val series = metadataNode(it).path("Series")
            if (series.path("NumberFloat").isNumber) {
              series.path("NumberFloat").asDouble()
            } else {
              Double.MAX_VALUE
            }
          }.thenBy { it.title.lowercase() }
            .thenBy { it.entitlementId },
        )

    val upstreamBooksByProductId = mutableMapOf<String, JsonNode>()
    upstreamSeries
      ?.path("Items")
      ?.takeIf { it.isArray }
      ?.forEach { item ->
        val book = item.path("Book")
        val productId = book.path("Id").takeIf { it.isTextual }?.asText()
        if (!productId.isNullOrBlank() && book.isObject) {
          upstreamBooksByProductId[productId] = book
        }
      }

    val result =
      (upstreamSeries as? ObjectNode)?.deepCopy()
        ?: objectMapper.createObjectNode()

    val items = objectMapper.createArrayNode()
    orderedMetadata.forEach { metadata ->
      val bookId = metadata.entitlementId
      val productId = koboProductResolver.resolveProductId(bookId)
      val upstreamBook = productId?.let(upstreamBooksByProductId::get)

      val wrapped = objectMapper.createObjectNode()
      wrapped.set<JsonNode>(
        "Book",
        overlayLocalBook(
          bookId = bookId,
          metadata = metadata,
          upstreamBook = upstreamBook,
        ),
      )
      items.add(wrapped)
    }

    result.put("CurrentPageIndex", 0)
    result.put("ItemCount", items.size())
    result.set<JsonNode>("Items", items)
    result.put("TotalItemCount", items.size())
    result.put("TotalPageCount", if (items.size() == 0) 0 else 1)

    if (!result.has("ItemsPerPage")) {
      result.put("ItemsPerPage", 100)
    }

    if (!result.has("Filters")) {
      val filters = objectMapper.createObjectNode()
      filters.set<JsonNode>(
        "KoboLoveEnabled",
        objectMapper.createArrayNode().add("True").add("False"),
      )
      filters.set<JsonNode>(
        "SubscriptionsAvailable",
        objectMapper.createArrayNode().add("True").add("False"),
      )
      result.set<JsonNode>("Filters", filters)
    }

    // Native captures use VersionCode=2 on this paged Store family. Only add it
    // when there was no upstream response to preserve.
    if (upstreamSeries == null && !result.has("VersionCode")) {
      result.put("VersionCode", 2)
    }

    return result
  }

  fun buildPrices(
    requestedIds: List<String>,
    upstreamPrices: JsonNode?,
  ): JsonNode {
    val upstreamItemsById = mutableMapOf<String, JsonNode>()
    upstreamPrices
      ?.path("Items")
      ?.takeIf { it.isArray }
      ?.forEach { item ->
        val id = item.path("Id").takeIf { it.isTextual }?.asText()
        if (!id.isNullOrBlank()) {
          upstreamItemsById[id] = item
        }
      }

    val result =
      (upstreamPrices as? ObjectNode)?.deepCopy()
        ?: objectMapper.createObjectNode()
    val items = objectMapper.createArrayNode()

    requestedIds.forEach { id ->
      if (isLocalBook(id)) {
        items.add(localPriceItem(id))
      } else {
        upstreamItemsById[id]?.let { upstream ->
          items.add(upstream.deepCopy<JsonNode>())
        }
      }
    }

    result.set<JsonNode>("Items", items)
    return result
  }

  private fun metadataByIds(bookIds: Collection<String>): Map<String, KoboBookMetadataDto> =
    koboDtoRepository
      .findBookMetadataByIds(bookIds)
      .associateBy { it.entitlementId }

  private fun metadataNode(metadata: KoboBookMetadataDto): JsonNode = objectMapper.valueToTree(metadata)

  private fun overlayLocalBook(
    bookId: String,
    metadata: KoboBookMetadataDto,
    upstreamBook: JsonNode?,
  ): ObjectNode {
    val target =
      (upstreamBook as? ObjectNode)?.deepCopy()
        ?: objectMapper.createObjectNode()

    target
      .get("SeriesId")
      ?.takeIf { it.isTextual }
      ?.asText()
      ?.takeIf { it.isNotBlank() }
      ?.let { koboSeriesId ->
        koboSeriesIdResolver.rememberForBook(
          bookId = bookId,
          koboSeriesId = koboSeriesId,
        )
      }
    val local = metadataNode(metadata)

    // Komga identity model.
    target.put("Id", bookId)
    target.put("CrossRevisionId", bookId)
    target.put("WorkId", bookId)
    // Do not invent RevisionId on Store schemas that did not contain it.
    // If Kobo supplied one on this particular shape, localize it.
    if (target.has("RevisionId")) {
      target.put("RevisionId", bookId)
    }

    copyOrRemove(target, "Title", local.path("Title"))
    copyOrRemove(target, "Subtitle", local.path("SubTitle"))
    copyOrRemove(target, "Description", local.path("Description"))
    copyOrRemove(target, "Language", local.path("Language"))
    copyOrRemove(target, "PublicationDate", local.path("PublicationDate"))
    copyOrRemove(target, "ISBN", local.path("Isbn"))
    copyOrRemove(target, "ImageId", local.path("CoverImageId"))
    copyOrRemove(target, "Slug", local.path("Slug"))

    val publisherName = local.path("Publisher").path("Name")
    copyOrRemove(target, "PublisherName", publisherName)

    val contributors = local.path("Contributors")
    if (contributors.isArray) {
      target.put(
        "Contributors",
        contributors
          .mapNotNull { node -> node.takeIf { it.isTextual }?.asText() }
          .joinToString(", "),
      )
    } else {
      target.remove("Contributors")
    }

    val contributorRoles = local.path("ContributorRoles")
    if (contributorRoles.isArray) {
      target.set<JsonNode>("ContributorRoles", contributorRoles.deepCopy<JsonNode>())
    } else {
      target.remove("ContributorRoles")
    }

    val series = local.path("Series")
    if (series.isObject) {
      copyOrRemove(target, "SeriesId", series.path("Id"))
      copyOrRemove(target, "SeriesName", series.path("Name"))
      copyOrRemove(target, "SeriesNumber", series.path("Number"))
      copyOrRemove(target, "SeriesNumberFloat", series.path("NumberFloat"))
    } else {
      target.remove(
        listOf(
          "SeriesId",
          "SeriesName",
          "SeriesNumber",
          "SeriesNumberFloat",
        ),
      )
    }

    // SeriesSlug and RelatedGroupId are Kobo identity/navigation metadata. Once
    // SeriesId/Book identity is local, retaining them would describe a different
    // identity graph.
    target.remove("SeriesSlug")
    target.remove("RelatedGroupId")

    target.put("IsInternetArchive", false)
    target.put("IsPreOrder", local.path("IsPreOrder").asBoolean(false))

    // These fields follow the synthetic local EUR 1 price policy.
    target.put("IsFree", false)
    target.put("EligibleForKoboLoveDiscount", false)
    target.put("PromoCodeAllowed", false)
    target.remove("LovePointsPrice")

    val price = objectMapper.createObjectNode()
    price.put("Currency", LOCAL_PRICE_CURRENCY)
    price.put("Price", LOCAL_PRICE_AMOUNT)
    target.set<JsonNode>("Price", price)

    // For a fully-local fallback, provide safe structural defaults but do NOT
    // fabricate Rating/TotalRating/Stats. For an enriched Kobo template, keep
    // genuine Kobo-only/contextual values already present.
    if (upstreamBook == null) {
      target.put("IsContentSharingEnabled", true)
      target.put("IsRecommendation", false)
      target.put("AgeVerificationRequired", false)
      target.set<JsonNode>("ApplicableSubscriptions", objectMapper.createArrayNode())
      target.put("HasPreview", false)
      target.put("InWishlist", false)
      target.set<JsonNode>("RedirectPreviewUrls", objectMapper.createArrayNode())

      val accessibility = objectMapper.createObjectNode()
      accessibility.set<JsonNode>("ContentTypes", objectMapper.createArrayNode())
      accessibility.set<JsonNode>("EPubAccessibilities", objectMapper.createArrayNode())
      accessibility.set<JsonNode>("HazardWarningTypes", objectMapper.createArrayNode())
      accessibility.put("IsAccessible", false)
      accessibility.put("IsFixedLayout", metadata.isPrePaginated)
      accessibility.put("IsTextToSpeechAllowed", false)
      accessibility.put("PrimaryContentType", "")
      target.set<JsonNode>("AccessibilityDetails", accessibility)

      target.remove("Rating")
      target.remove("TotalRating")
      target.remove("Stats")
    }

    return target
  }

  private fun copyOrRemove(
    target: ObjectNode,
    targetField: String,
    source: JsonNode,
  ) {
    if (!source.isMissingNode && !source.isNull) {
      target.set<JsonNode>(targetField, source.deepCopy<JsonNode>())
    } else {
      target.remove(targetField)
    }
  }

  private fun localPriceItem(bookId: String): ObjectNode {
    val item = objectMapper.createObjectNode()
    item.put("CrossRevisionId", bookId)
    item.put("EligibleForKoboLoveDiscount", false)
    item.put("Id", bookId)
    item.put("IsPreOrder", false)

    val price = objectMapper.createObjectNode()
    price.put("Currency", LOCAL_PRICE_CURRENCY)
    price.put("Price", LOCAL_PRICE_AMOUNT)
    item.set<JsonNode>("Price", price)

    return item
  }
}
