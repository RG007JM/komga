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
  fun `does not accept an ISBN appearing only in a recommendation`() {
    val html =
      """
      <html>
        <body>
          <div class="item-primary-metadata book-primary-metadata"
               data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'></div>
          <div>ID del libro: 9788834923627</div>
          <section class="recommendations">
            <a href="/ebook/recommended?isbn=9781974755998">9781974755998</a>
          </section>
        </body>
      </html>
      """.trimIndent()

    val baseUrl = "https://www.kobo.com/it/it/ebook/frieren-oltre-la-fine-del-viaggio-vol-1"

    assertThat(parser.parse(html, baseUrl, "9781974755998")).isNull()
    assertThat(parser.parse(html, baseUrl, "9788834923627"))
      .isEqualTo(
        KoboProductPageIdentity(
          productId = "cf59cd34-5e8f-4f52-bd89-7aef25ca82a9",
          seriesId = null,
        ),
      )
  }

  @Test
  fun `rejects conflicting labeled ISBNs rather than using page order`() {
    val html =
      """
      <html>
        <body>
          <div class="item-primary-metadata book-primary-metadata"
               data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'></div>
          <div>ID del libro: 9788834923627</div>
          <section class="recommendations">
            <div>Book ID: 9781974755998</div>
          </section>
        </body>
      </html>
      """.trimIndent()

    val baseUrl = "https://www.kobo.com/it/it/ebook/frieren-oltre-la-fine-del-viaggio-vol-1"

    assertThat(parser.parse(html, baseUrl, "9781974755998")).isNull()
    assertThat(parser.parse(html, baseUrl, "9788834923627")).isNull()
  }

  @Test
  fun `does not trust ISBN appearing only in HTML attributes or scripts`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div class="item-primary-metadata book-primary-metadata"
                   data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'></div>
              <a href="/ebook/related?isbn=9781974755998">Related volume</a>
              <script type="application/json">{"isbn":"9781974755998"}</script>
            </body>
          </html>
          """.trimIndent(),
        baseUrl = "https://www.kobo.com/it/it/ebook/example",
        isbn = "9781974755998",
      )

    assertThat(result).isNull()
  }

  @Test
  fun `accepts labeled ISBN with hyphens`() {
    val result =
      parser.parse(
        html =
          """
          <html>
            <body>
              <div class="item-primary-metadata book-primary-metadata"
                   data-track-info='{"productId":"cf59cd34-5e8f-4f52-bd89-7aef25ca82a9"}'></div>
              <div>Book ID: 978-8-8349-2362-7</div>
            </body>
          </html>
          """.trimIndent(),
        baseUrl = "https://www.kobo.com/gb/en/ebook/example",
        isbn = "9788834923627",
      )

    assertThat(result?.productId)
      .isEqualTo("cf59cd34-5e8f-4f52-bd89-7aef25ca82a9")
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
