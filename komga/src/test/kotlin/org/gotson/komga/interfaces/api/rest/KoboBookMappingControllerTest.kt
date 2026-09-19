package org.gotson.komga.interfaces.api.rest

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.interfaces.api.rest.dto.BookDto
import org.gotson.komga.interfaces.api.rest.dto.BookMetadataDto
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KoboBookMappingControllerTest {
  private val book =
    mockk<BookDto> {
      every { id } returns "book-1"
      every { metadata } returns
        mockk<BookMetadataDto> {
          every { title } returns "Book One"
          every { isbn } returns "978-1-9747-5324-6"
        }
    }

  private fun mapping(
    isbn: String = "9781974753246",
    productId: String? = "product-1",
    status: KoboProductMappingStatus = KoboProductMappingStatus.FOUND,
  ) = KoboProductMapping(
    bookId = "book-1",
    isbn = isbn,
    productId = productId,
    status = status,
    checkedAt = LocalDateTime.of(2026, 9, 19, 12, 0),
  )

  @Test
  fun `matching found mapping is reported as mapped`() {
    val result = book.toKoboMappingDto(mapping())
    assertThat(result.state).isEqualTo(KoboBookMappingState.MAPPED)
    assertThat(result.hasKoboProductId).isTrue()
    assertThat(result.productId).isEqualTo("product-1")
  }

  @Test
  fun `stale ISBN is not reported as a usable Kobo identity`() {
    val result = book.toKoboMappingDto(mapping(isbn = "9780000000000"))
    assertThat(result.state).isEqualTo(KoboBookMappingState.STALE_ISBN)
    assertThat(result.hasKoboProductId).isFalse()
    assertThat(result.productId).isNull()
  }

  @Test
  fun `cached not found is distinct from not checked`() {
    assertThat(book.toKoboMappingDto(null).state).isEqualTo(KoboBookMappingState.NOT_CHECKED)
    val notFound = book.toKoboMappingDto(mapping(productId = null, status = KoboProductMappingStatus.NOT_FOUND))
    assertThat(notFound.state).isEqualTo(KoboBookMappingState.NOT_FOUND)
    assertThat(notFound.hasKoboProductId).isFalse()
  }
}
