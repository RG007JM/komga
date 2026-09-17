package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KoboProductPageParserTest {
  private val parser =
    KoboProductPageParser(
      ObjectMapper(),
    )

  @Test
  fun `extracts ProductId and SeriesId from Italian Kobo product page`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div
                class="item-primary-metadata book-primary-metadata"
                data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'
              ></div>
              <div>ID del libro: 9788834923627</div>
              <a
                class="view-all"
                aria-label="Visualizza tutto In questa serie"
                href="/it/it/search?query=Frieren.%20Oltre%20la%20fine%20del%20viaggio&amp;fcsearchfield=Series&amp;seriesId=e99dd828-368e-5b4d-b728-5386dc0c2307"
              >Visualizza tutto</a>
            </body>
          </html>
          """.trimIndent(),
        baseUrl =
          "https://www.kobo.com/it/it/ebook/frieren-oltre-la-fine-del-viaggio-vol-1",
        isbn = "9788834923627",
      )

    assertThat(result)
      .isEqualTo(
        KoboProductPageIdentity(
          productId = "cf59cd34-5e8f-4f52-bd89-7aef25ca82a9",
          seriesId = "e99dd828-368e-5b4d-b728-5386dc0c2307",
        ),
      )
  }

  @Test
  fun `returns ProductId with null SeriesId for standalone Kobo product`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div
                class="item-primary-metadata book-primary-metadata"
                data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'
              ></div>
              <div>ID del libro: 9788834923627</div>
            </body>
          </html>
          """.trimIndent(),
        baseUrl = "https://www.kobo.com/it/it/ebook/example",
        isbn = "9788834923627",
      )

    assertThat(result)
      .isEqualTo(
        KoboProductPageIdentity(
          productId = "cf59cd34-5e8f-4f52-bd89-7aef25ca82a9",
          seriesId = null,
        ),
      )
  }

  @Test
  fun `rejects page when ISBN does not match`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div
                class="item-primary-metadata book-primary-metadata"
                data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'
              ></div>
              <div>ID del libro: 9788834923627</div>
            </body>
          </html>
          """.trimIndent(),
        baseUrl = "https://www.kobo.com/it/it/ebook/example",
        isbn = "9781234567890",
      )

    assertThat(result).isNull()
  }

  @Test
  fun `rejects ambiguous page with multiple ProductIds`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div
                class="item-primary-metadata book-primary-metadata"
                data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'
              ></div>
              <div
                class="item-primary-metadata book-primary-metadata"
                data-track-info='{"productId":"b30a5381-d68a-4197-a439-1b87b3d24113"}'
              ></div>
              <div>ID del libro: 9788834923627</div>
            </body>
          </html>
          """.trimIndent(),
        baseUrl = "https://www.kobo.com/it/it/search?query=9788834923627",
        isbn = "9788834923627",
      )

    assertThat(result).isNull()
  }
}
