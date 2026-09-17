package org.gotson.komga.infrastructure.kobo

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboSeriesSearchQueryTranslatorTest {
  private val seriesIdResolver =
    mockk<KoboSeriesIdResolver>()

  private val translator =
    KoboSeriesSearchQueryTranslator(
      koboSeriesIdResolver = seriesIdResolver,
    )

  @BeforeEach
  fun setUp() {
    clearMocks(seriesIdResolver)
  }

  @Test
  fun `translates exact local series q and preserves remaining query`() {
    every {
      seriesIdResolver.resolveSeriesIdIfLocal("0RDTATKG1HM0V")
    } returns "e99dd828-368e-5b4d-b728-5386dc0c2307"

    val result =
      translator.translate(
        "q=0RDTATKG1HM0V&Filters=%7B%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook",
      )

    assertThat(result)
      .isEqualTo(
        "q=e99dd828-368e-5b4d-b728-5386dc0c2307&Filters=%7B%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook",
      )

    verify(exactly = 1) {
      seriesIdResolver.resolveSeriesIdIfLocal("0RDTATKG1HM0V")
    }
  }

  @Test
  fun `ordinary text q is untouched without resolver lookup`() {
    val query =
      "q=spy x family&Filters=%7B%7D&page_index=0&page_size=200"

    assertThat(
      translator.translate(query),
    ).isEqualTo(query)

    verify(exactly = 0) {
      seriesIdResolver.resolveSeriesIdIfLocal(any())
    }
  }

  @Test
  fun `numeric ISBN-looking q is verified and left unchanged when not a series`() {
    every {
      seriesIdResolver.resolveSeriesIdIfLocal("9781234567890")
    } returns null

    val query =
      "q=9781234567890&Filters=%7B%7D"

    assertThat(
      translator.translate(query),
    ).isEqualTo(query)

    verify(exactly = 1) {
      seriesIdResolver.resolveSeriesIdIfLocal("9781234567890")
    }
  }

  @Test
  fun `non-q parameters remain unchanged`() {
    val query =
      "ProductIds=0RDTATKHXHPS9&Filters=%7B%7D"

    assertThat(
      translator.translate(query),
    ).isEqualTo(query)

    verify(exactly = 0) {
      seriesIdResolver.resolveSeriesIdIfLocal(any())
    }
  }

  @Test
  fun `preserves null query`() {
    assertThat(
      translator.translate(null),
    ).isNull()

    verify(exactly = 0) {
      seriesIdResolver.resolveSeriesIdIfLocal(any())
    }
  }
}
