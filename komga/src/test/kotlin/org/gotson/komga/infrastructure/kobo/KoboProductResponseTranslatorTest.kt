package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.service.KoboProductResolver
import org.junit.jupiter.api.Test

class KoboProductResponseTranslatorTest {
  private val objectMapper =
    ObjectMapper()

  private val resolver =
    mockk<KoboProductResolver>()

  private val translator =
    KoboProductResponseTranslator(
      resolver,
    )

  @Test
  fun `recommendations translates local Kobo ProductIds to Komga book IDs`() {
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
                "Title": "Local book"
              }
            },
            {
              "Book": {
                "Id": "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
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
      result["Items"][1]["Book"]["Id"].asText(),
    ).isEqualTo(
      "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
    )
  }

  @Test
  fun `nextread translates response key and local book IDs`() {
    every {
      resolver.resolveBookId(
        "b996d901-4a00-4783-8476-8494252d3415",
      )
    } returns "komga-source-book"

    every {
      resolver.resolveBookId(
        "9af58355-1e62-4a09-9b34-8ac5758699fe",
      )
    } returns "komga-next-book"

    every {
      resolver.resolveBookId(
        "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
      )
    } returns null

    val body =
      objectMapper.readTree(
        """
        {
          "b996d901-4a00-4783-8476-8494252d3415": [
            {
              "Id": "9af58355-1e62-4a09-9b34-8ac5758699fe",
              "Title": "Local next book"
            },
            {
              "Id": "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
              "Title": "Kobo-only next book"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/komga-source-book/nextread",
        body = body,
      )

    assertThat(
      result.has("komga-source-book"),
    ).isTrue()

    assertThat(
      result.has(
        "b996d901-4a00-4783-8476-8494252d3415",
      ),
    ).isFalse()

    assertThat(
      result["komga-source-book"][0]["Id"].asText(),
    ).isEqualTo(
      "komga-next-book",
    )

    assertThat(
      result["komga-source-book"][1]["Id"].asText(),
    ).isEqualTo(
      "93145ea6-79f0-4f60-ad1d-10e051d38c7b",
    )
  }

  @Test
  fun `product reviews translates ProductId only`() {
    every {
      resolver.resolveBookId(
        "b996d901-4a00-4783-8476-8494252d3415",
      )
    } returns "komga-book-id"

    val body =
      objectMapper.readTree(
        """
        {
          "Items": [
            {
              "RevisionId": "b996d901-4a00-4783-8476-8494252d3415",
              "ProductId": "b996d901-4a00-4783-8476-8494252d3415",
              "CrossRevisionId": "173aa35d-7533-3879-a143-f023e62316db",
              "Title": "Review"
            }
          ]
        }
        """.trimIndent(),
      )

    val result =
      translator.translate(
        path =
          "/v1/products/komga-book-id/reviews",
        body = body,
      )

    val review =
      result["Items"][0]

    assertThat(
      review["ProductId"].asText(),
    ).isEqualTo(
      "komga-book-id",
    )

    assertThat(
      review["RevisionId"].asText(),
    ).isEqualTo(
      "b996d901-4a00-4783-8476-8494252d3415",
    )

    assertThat(
      review["CrossRevisionId"].asText(),
    ).isEqualTo(
      "173aa35d-7533-3879-a143-f023e62316db",
    )
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
  }
}
