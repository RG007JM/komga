package org.gotson.komga.interfaces.api.rest

import io.mockk.every
import io.mockk.verify
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboLookupDiagnostics
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.gotson.komga.interfaces.api.persistence.BookDtoRepository
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.interfaces.api.rest.dto.BookDto
import org.gotson.komga.interfaces.api.rest.dto.BookMetadataDto
import org.junit.jupiter.api.Test
import org.springframework.security.access.prepost.PreAuthorize
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
  fun `admin diagnostic reads current mapping and lookup status without starting website discovery`() {
    val books = mockk<BookDtoRepository>()
    val mappings = mockk<KoboProductMappingRepository>()
    val restrictions = mockk<ContentRestrictionChecker>(relaxed = true)
    val principal = mockk<KomgaPrincipal>()
    val user = mockk<KomgaUser>()
    val diagnostics = KoboLookupDiagnostics()
    diagnostics.record("9781974753246", "INCONCLUSIVE")
    every { principal.user } returns user
    every { user.id } returns "admin"
    every { books.findByIdOrNull("book-1", "admin") } returns book
    every { mappings.findByBookId("book-1") } returns mapping()

    val controller = KoboBookMappingController(books, mappings, restrictions, diagnostics)
    val snapshot = controller.getIdentityDiagnostic(principal, "book-1")

    assertThat(snapshot.mapping.productId).isEqualTo("product-1")
    assertThat(snapshot.recentWebsiteAttempt?.outcome).isEqualTo("INCONCLUSIVE")
    verify(exactly = 1) { restrictions.checkContentRestrictionBook(user, book) }
    verify(exactly = 1) { mappings.findByBookId("book-1") }
    val method = KoboBookMappingController::class.java.getDeclaredMethod(
      "getIdentityDiagnostic", KomgaPrincipal::class.java, String::class.java,
    )
    assertThat(method.getAnnotation(PreAuthorize::class.java)?.value).isEqualTo("hasRole('ADMIN')")
  }

  @Test
  fun `ISBN checksum validity not only thirteen digit length controls mapping validity`() {
    val invalidBook = mockk<BookDto> {
      every { id } returns "invalid"
      every { metadata } returns mockk<BookMetadataDto> {
        every { title } returns "Invalid"
        every { isbn } returns "9781974702014"
      }
    }
    assertThat(invalidBook.toKoboMappingDto(null).state).isEqualTo(KoboBookMappingState.NO_VALID_ISBN)
  }

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
