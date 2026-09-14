// KOBO_REMAINING_ENDPOINTS_V1
package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.service.KoboProductResolver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboTagRequestTranslatorTest {
  private val objectMapper =
    ObjectMapper()

  private val resolver =
    mockk<KoboProductResolver>()

  private val translator =
    KoboTagRequestTranslator(
      objectMapper = objectMapper,
      koboProductResolver = resolver,
    )

  @BeforeEach
  fun setUp() {
    clearMocks(resolver)
  }

  @Test
  fun `translates local RevisionId to Kobo ProductId`() {
    every {
      resolver.resolveProductId("0RLOCALBOOK")
    } returns "8201afa9-c23b-429b-a642-a4bcf1c8b638"

    val result =
      translator.translate(
        """
        {
          "Items": [
            {
              "RevisionId": "0RLOCALBOOK",
              "Type": "ProductRevisionTagItem"
            }
          ],
          "Name": "Test collection"
        }
        """.trimIndent().toByteArray(),
      )

    val json =
      objectMapper.readTree(result)

    assertThat(
      json
        .path("Items")
        .path(0)
        .path("RevisionId")
        .asText(),
    ).isEqualTo(
      "8201afa9-c23b-429b-a642-a4bcf1c8b638",
    )

    assertThat(
      json.path("Name").asText(),
    ).isEqualTo(
      "Test collection",
    )

    verify(exactly = 1) {
      resolver.resolveProductId("0RLOCALBOOK")
    }
  }

  @Test
  fun `leaves unmapped RevisionId unchanged`() {
    every {
      resolver.resolveProductId("already-kobo-or-unmapped")
    } returns null

    val original =
      """
      {
        "Items": [
          {
            "RevisionId": "already-kobo-or-unmapped",
            "Type": "ProductRevisionTagItem"
          }
        ],
        "Name": "Test collection"
      }
      """.trimIndent().toByteArray()

    val result =
      translator.translate(original)

    assertThat(result)
      .isEqualTo(original)

    verify(exactly = 1) {
      resolver.resolveProductId("already-kobo-or-unmapped")
    }
  }

  @Test
  fun `leaves non tag shaped JSON unchanged`() {
    val original =
      """
      {
        "Name": "No items here"
      }
      """.trimIndent().toByteArray()

    val result =
      translator.translate(original)

    assertThat(result)
      .isEqualTo(original)

    verify(exactly = 0) {
      resolver.resolveProductId(any())
    }
  }
}
