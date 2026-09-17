package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.interfaces.api.kobo.dto.KoboBookMetadataDto
import org.gotson.komga.interfaces.api.kobo.persistence.KoboDtoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboProductResponseTranslatorTest {
  private val objectMapper =
    ObjectMapper()

  private val resolver =
    mockk<KoboProductResolver>()

  private val koboLocalBookLookup =
    mockk<KoboLocalBookLookup>()

  private val koboDtoRepository =
    mockk<KoboDtoRepository>()

  private val translator =
    KoboProductResponseTranslator(
      koboProductResolver = resolver,
      koboLocalBookLookup = koboLocalBookLookup,
      koboDtoRepository = koboDtoRepository,
    )

  @BeforeEach
  fun setUp() {
    clearMocks(
      resolver,
      koboLocalBookLookup,
      koboDtoRepository,
    )
    every {
      koboDtoRepository.findBookMetadataByIds(any())
    } answers {
      firstArg<Collection<String>>()
        .map { bookId ->
          localMetadata(
            bookId = bookId,
            coverImageId = "cover-$bookId",
          )
        }
    }
  }

  @Test
  fun `recommendations translates mapped book identity to Komga book IDs`() {
    every {
      resolver.resolveBookId(
        "9af58355-1e62-4a09-9b34-8ac5758699fe",
      )
    } returns "komga-book-6"

    every {
      resolver.resolveBookId(
        "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
      )
    } returns null

    val body =
      objectMapper.readTree(
        """
        {
          "Items": [
            {
              "Book": {
                "Id": "9af58355-1e62-4a09-9b34-8ac5758699fe",
                "CrossRevisionId": "original-cross-revision",
                "WorkId": "original-work-id",
                "ImageId": "original-image-id",
                "Title": "Local book"
              }
            },
            {
              "Book": {
                "Id": "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
                "CrossRevisionId": "kobo-only-cross-revision",
                "WorkId": "kobo-only-work-id",
                "Title": "Kobo-only book"
              }
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/b996d901-4a00-4783-8476-8494252d3415/recommendations",
        body = body,
      )

    assertThat(
      result["Items"][0]["Book"]["Id"].asText(),
    ).isEqualTo(
      "komga-book-6",
    )

    assertThat(
      result["Items"][0]["Book"]["CrossRevisionId"].asText(),
    ).isEqualTo(
      "komga-book-6",
    )

    assertThat(
      result["Items"][0]["Book"]["WorkId"].asText(),
    ).isEqualTo(
      "komga-book-6",
    )

    assertThat(
      result["Items"][0]["Book"]["ImageId"].asText(),
    ).isEqualTo(
      "cover-komga-book-6",
    )

    assertThat(
      result["Items"][1]["Book"]["Id"].asText(),
    ).isEqualTo(
      "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
    )

    assertThat(
      result["Items"][1]["Book"]["CrossRevisionId"].asText(),
    ).isEqualTo(
      "kobo-only-cross-revision",
    )

    assertThat(
      result["Items"][1]["Book"]["WorkId"].asText(),
    ).isEqualTo(
      "kobo-only-work-id",
    )
  }

  @Test
  fun `recommendations discovers local book from ISBN when ProductId mapping is missing`() {
    val koboProductId =
      "ef8c966c-b59d-4768-85f0-586f932245d8"

    val isbn =
      "9781421581514"

    val komgaBookId =
      "komga-assassination-classroom-1"

    every {
      resolver.resolveBookId(koboProductId)
    } returns null

    every {
      koboLocalBookLookup.findUniqueBookIdByIsbn(isbn)
    } returns komgaBookId

    val body =
      objectMapper.readTree(
        """
        {
          "Items": [
            {
              "Book": {
                "Id": "$koboProductId",
                "ISBN": "$isbn",
                "CrossRevisionId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
                "WorkId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
                "RevisionId": "$koboProductId",
                "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
                "ImageId": "d05b2324-4b83-487f-83d4-8fce0e17389b",
                "Title": "Assassination Classroom, Vol. 1"
              }
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/source-product-id/recommendations?page_index=0&page_size=100",
        body = body,
      )

    val book =
      result
        .path("Items")
        .path(0)
        .path("Book")

    assertThat(
      book.path("Id").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("CrossRevisionId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("WorkId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("RevisionId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("ISBN").asText(),
    ).isEqualTo(
      isbn,
    )

    assertThat(
      book.path("SeriesId").asText(),
    ).isEqualTo(
      "series-komga-assassination-classroom-1",
    )

    assertThat(
      book.path("ImageId").asText(),
    ).isEqualTo(
      "cover-komga-assassination-classroom-1",
    )

    verify(exactly = 1) {
      resolver.resolveBookId(koboProductId)
    }

    verify(exactly = 1) {
      koboLocalBookLookup.findUniqueBookIdByIsbn(isbn)
    }
  }

  @Test
  fun `recommendations leaves book untouched when ProductId and ISBN do not match a local book`() {
    val koboProductId =
      "ef8c966c-b59d-4768-85f0-586f932245d8"

    every {
      resolver.resolveBookId(koboProductId)
    } returns null

    every {
      koboLocalBookLookup.findUniqueBookIdByIsbn(
        "9781421581514",
      )
    } returns null

    val body =
      objectMapper.readTree(
        """
        {
          "Items": [
            {
              "Book": {
                "Id": "$koboProductId",
                "ISBN": "9781421581514",
                "CrossRevisionId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
                "WorkId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638"
              }
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/source-product-id/recommendations",
        body = body,
      )

    val book =
      result
        .path("Items")
        .path(0)
        .path("Book")

    assertThat(
      book.path("Id").asText(),
    ).isEqualTo(
      koboProductId,
    )

    assertThat(
      book.path("CrossRevisionId").asText(),
    ).isEqualTo(
      "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
    )

    assertThat(
      book.path("WorkId").asText(),
    ).isEqualTo(
      "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
    )
  }

  @Test
  fun `nextread discovers local book from ISBN when ProductId mapping is missing`() {
    val sourceProductId =
      "b996d901-4a00-4783-8476-8494252d3415"

    val sourceKomgaBookId =
      "komga-source-book"

    val nextProductId =
      "3af004dd-4978-4800-bd16-c8527537acc2"

    val nextIsbn =
      "9781974759859"

    val nextKomgaBookId =
      "komga-next-book"

    every {
      resolver.resolveBookId(sourceProductId)
    } returns sourceKomgaBookId

    every {
      resolver.resolveBookId(nextProductId)
    } returns null

    every {
      koboLocalBookLookup.findUniqueBookIdByIsbn(nextIsbn)
    } returns nextKomgaBookId

    val body =
      objectMapper.readTree(
        """
        {
          "$sourceProductId": [
            {
              "Id": "$nextProductId",
              "ISBN": "$nextIsbn",
              "RevisionId": "$nextProductId",
              "CrossRevisionId": "4f8854bb-056d-30dc-bda0-8a52c2ac2a4d",
              "WorkId": "4f8854bb-056d-30dc-bda0-8a52c2ac2a4d",
              "ImageId": "6a77a4da-ca2b-424d-b3d6-f67cb54eca08",
              "Title": "Dandadan, Vol. 15"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/$sourceKomgaBookId/nextread",
        body = body,
      )

    assertThat(
      result.has(sourceKomgaBookId),
    ).isTrue()

    assertThat(
      result.has(sourceProductId),
    ).isFalse()

    val book =
      result[sourceKomgaBookId][0]

    assertThat(
      book["Id"].asText(),
    ).isEqualTo(nextKomgaBookId)

    assertThat(
      book["RevisionId"].asText(),
    ).isEqualTo(nextKomgaBookId)

    assertThat(
      book["CrossRevisionId"].asText(),
    ).isEqualTo(nextKomgaBookId)

    assertThat(
      book["WorkId"].asText(),
    ).isEqualTo(nextKomgaBookId)

    // Kobo metadata must stay Kobo metadata.
    assertThat(
      book["ISBN"].asText(),
    ).isEqualTo(nextIsbn)

    assertThat(
      book["ImageId"].asText(),
    ).isEqualTo(
      "cover-komga-next-book",
    )

    verify(exactly = 1) {
      resolver.resolveBookId(nextProductId)
    }

    verify(exactly = 1) {
      koboLocalBookLookup.findUniqueBookIdByIsbn(nextIsbn)
    }
  }

  @Test
  fun `product reviews translates matching CrossRevisionId and ReviewSummary`() {
    val productId = "b996d901-4a00-4783-8476-8494252d3415"
    val crossRevisionId = "173aa35d-7533-3879-a143-f023e62316db"
    val bookId = "komga-book-id"

    every {
      resolver.resolveBookId(productId)
    } returns bookId

    val body =
      objectMapper.readTree(
        """
        {
          "ReviewSummary": {
            "$crossRevisionId": {
              "AvgRating": 4.9,
              "OpinionCount": 53,
              "NumberOfReviews": 5
            }
          },
          "Items": [
            {
              "RevisionId": "$productId",
              "ProductId": "$productId",
              "CrossRevisionId": "$crossRevisionId",
              "Title": "Review A"
            },
            {
              "RevisionId": "$productId",
              "ProductId": "$productId",
              "CrossRevisionId": "$crossRevisionId",
              "Title": "Review B"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path = "/v1/products/$bookId/reviews",
        body = body,
      )

    assertThat(result["Items"][0]["ProductId"].asText()).isEqualTo(bookId)
    assertThat(result["Items"][0]["CrossRevisionId"].asText()).isEqualTo(bookId)
    assertThat(result["Items"][0]["RevisionId"].asText()).isEqualTo(bookId)
    assertThat(result["Items"][1]["CrossRevisionId"].asText()).isEqualTo(bookId)
    assertThat(result["ReviewSummary"].has(bookId)).isTrue()
    assertThat(result["ReviewSummary"].has(crossRevisionId)).isFalse()
    assertThat(result["ReviewSummary"][bookId]["OpinionCount"].asInt()).isEqualTo(53)
  }

  @Test
  fun `product reviews keeps CrossRevisionId when summary identity does not match`() {
    val productId = "b996d901-4a00-4783-8476-8494252d3415"
    val reviewCrossRevisionId = "173aa35d-7533-3879-a143-f023e62316db"
    val summaryCrossRevisionId = "different-cross-revision"
    val bookId = "komga-book-id"

    every {
      resolver.resolveBookId(productId)
    } returns bookId

    val body =
      objectMapper.readTree(
        """
        {
          "ReviewSummary": {
            "$summaryCrossRevisionId": {
              "AvgRating": 4.9,
              "OpinionCount": 53
            }
          },
          "Items": [
            {
              "RevisionId": "$productId",
              "ProductId": "$productId",
              "CrossRevisionId": "$reviewCrossRevisionId"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path = "/v1/products/$bookId/reviews",
        body = body,
      )

    assertThat(result["Items"][0]["ProductId"].asText()).isEqualTo(bookId)
    assertThat(result["Items"][0]["CrossRevisionId"].asText())
      .isEqualTo(reviewCrossRevisionId)
    assertThat(result["Items"][0]["RevisionId"].asText()).isEqualTo(bookId)
    assertThat(result["ReviewSummary"].has(summaryCrossRevisionId)).isTrue()
    assertThat(result["ReviewSummary"].has(bookId)).isFalse()
  }

  @Test
  fun `user reviews response is never translated`() {
    val body =
      objectMapper.readTree(
        """
        {
          "Items": [
            {
              "ProductId": "b996d901-4a00-4783-8476-8494252d3415"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path = "/v1/user/reviews",
        body = body,
      )

    assertThat(
      result["Items"][0]["ProductId"].asText(),
    ).isEqualTo(
      "b996d901-4a00-4783-8476-8494252d3415",
    )

    verify(exactly = 0) {
      resolver.resolveBookId(any())
    }

    verify(exactly = 0) {
      koboLocalBookLookup.findUniqueBookIdByIsbn(any())
    }
  }

  @Test
  fun `product search rewrites mapped book identity fields`() {
    val koboProductId =
      "42ed6d94-1f32-4df9-9d97-023335a4eb9c"

    val komgaBookId =
      "0KOMGA-FRIEREN-ANTHOLOGY"

    every {
      resolver.resolveBookId(koboProductId)
    } returns komgaBookId

    val body =
      objectMapper.readTree(
        """
        {
          "CurrentPageIndex": 0,
          "ItemCount": 1,
          "Items": [
            {
              "Book": {
                "CrossRevisionId": "9d842c0c-4cfe-3238-90e8-fbafe9d7f173",
                "ISBN": "9788834939192",
                "Id": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
                "ImageId": "d2e5f054-ea39-41fb-8af9-6e3c87d1837f",
                "RevisionId": "original-revision-id",
                "SeriesId": "original-series-id",
                "RelatedGroupId": "original-related-group-id",
                "Title": "Frieren. Oltre la fine del viaggio. Anthology",
                "WorkId": "9d842c0c-4cfe-3238-90e8-fbafe9d7f173"
              }
            }
          ],
          "ItemsPerPage": 200,
          "TotalItemCount": 1
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products?q=frieren%20anthology&Filters=%7BLanguage:it%7D&page_index=0&page_size=200&TypesToInclude=book",
        body = body,
      )

    val book =
      result
        .path("Items")
        .path(0)
        .path("Book")

    assertThat(
      book.path("Id").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("CrossRevisionId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("WorkId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("RevisionId").asText(),
    ).isEqualTo(
      komgaBookId,
    )

    assertThat(
      book.path("SeriesId").asText(),
    ).isEqualTo(
      "series-0KOMGA-FRIEREN-ANTHOLOGY",
    )

    assertThat(
      book.path("ImageId").asText(),
    ).isEqualTo(
      "cover-0KOMGA-FRIEREN-ANTHOLOGY",
    )

    assertThat(
      book.path("Description").asText(),
    ).isEqualTo(
      "description-0KOMGA-FRIEREN-ANTHOLOGY",
    )

    assertThat(
      book.has("RelatedGroupId"),
    ).isFalse()

    assertThat(
      book.path("ISBN").asText(),
    ).isEqualTo(
      "9788834939192",
    )

    assertThat(
      book.path("Title").asText(),
    ).isEqualTo(
      "Frieren. Oltre la fine del viaggio. Anthology",
    )

    verify(exactly = 1) {
      resolver.resolveBookId(koboProductId)
    }

    verify(exactly = 0) {
      koboLocalBookLookup.findUniqueBookIdByIsbn(any())
    }
  }

  private fun localMetadata(
    bookId: String,
    coverImageId: String,
  ): KoboBookMetadataDto {
    val metadata =
      mockk<KoboBookMetadataDto>()

    val series =
      mockk<org.gotson.komga.interfaces.api.kobo.dto.KoboSeriesDto>(
        relaxed = true,
      )

    every {
      metadata.entitlementId
    } returns bookId

    every {
      metadata.coverImageId
    } returns coverImageId

    every {
      metadata.description
    } returns "description-$bookId"

    every {
      metadata.series
    } returns series

    every {
      series.id
    } returns "series-$bookId"

    every {
      series.name
    } returns "Series $bookId"

    every {
      series.number
    } returns "1"

    return metadata
  }
}
