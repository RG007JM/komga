package org.gotson.komga.interfaces.api.rest

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.model.SearchContext
import org.gotson.komga.domain.model.UserRoles
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboLookupDiagnostics
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.gotson.komga.interfaces.api.persistence.BookDtoRepository
import org.gotson.komga.interfaces.api.rest.dto.BookDto
import org.gotson.komga.interfaces.api.rest.dto.BookMetadataDto
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
    val method =
      KoboBookMappingController::class.java.getDeclaredMethod(
        "getIdentityDiagnostic",
        KomgaPrincipal::class.java,
        String::class.java,
      )
    assertThat(method.getAnnotation(PreAuthorize::class.java)?.value).isEqualTo("hasRole('ADMIN')")
  }

  @Test
  fun `state filter paginates matches and reports total matching books`() {
    val books = mockk<BookDtoRepository>()
    val mappings = mockk<KoboProductMappingRepository>()
    val principal = mockk<KomgaPrincipal>()
    val user =
      KomgaUser(
        email = "admin@example.com",
        password = "unused",
        roles = setOf(UserRoles.ADMIN),
      )
    every { principal.user } returns user

    fun bookWith(
      bookId: String,
      isbnValue: String,
    ): BookDto =
      mockk {
        every { id } returns bookId
        every { metadata } returns
          mockk<BookMetadataDto> {
            every { title } returns bookId
            every { isbn } returns isbnValue
          }
      }
    val isbn = "9781974753246"
    val allBooks =
      listOf(
        bookWith("found", isbn),
        bookWith("missing-one", isbn),
        bookWith("unchecked", isbn),
        bookWith("invalid", "invalid"),
        bookWith("stale", isbn),
        bookWith("missing-two", isbn),
      )
    every { books.findAll(any<SearchContext>(), any<Pageable>()) } answers {
      val page = secondArg<Pageable>()
      PageImpl(allBooks.drop(page.offset.toInt()).take(page.pageSize), page, allBooks.size.toLong())
    }
    val stored =
      listOf(
        KoboProductMapping("found", isbn, "found-product", status = KoboProductMappingStatus.FOUND, checkedAt = LocalDateTime.of(2026, 9, 19, 12, 0)),
        KoboProductMapping("missing-one", isbn, null, status = KoboProductMappingStatus.NOT_FOUND, checkedAt = LocalDateTime.of(2026, 9, 19, 12, 0)),
        KoboProductMapping("stale", "9784088917542", "stale-product", status = KoboProductMappingStatus.FOUND, checkedAt = LocalDateTime.of(2026, 9, 19, 12, 0)),
        KoboProductMapping("missing-two", isbn, null, status = KoboProductMappingStatus.NOT_FOUND, checkedAt = LocalDateTime.of(2026, 9, 19, 12, 0)),
      )
    every { mappings.findByBookIds(any()) } answers {
      val ids = firstArg<Collection<String>>()
      stored.filter { it.bookId in ids }
    }
    val controller = KoboBookMappingController(books, mappings, mockk(relaxed = true), KoboLookupDiagnostics())

    val first = controller.listMappings(principal, PageRequest.of(0, 1), KoboBookMappingState.NOT_FOUND)
    val second = controller.listMappings(principal, PageRequest.of(1, 1), KoboBookMappingState.NOT_FOUND)
    assertThat(first.totalElements).isEqualTo(2)
    assertThat(first.content.map { it.bookId }).containsExactly("missing-one")
    assertThat(second.totalElements).isEqualTo(2)
    assertThat(second.content.map { it.bookId }).containsExactly("missing-two")

    val counts = controller.countMappings(principal)
    assertThat(counts.totalBooks).isEqualTo(6)
    assertThat(counts.byState).containsEntry(KoboBookMappingState.NOT_FOUND, 2L)
    assertThat(counts.byState).containsEntry(KoboBookMappingState.MAPPED, 1L)
    assertThat(counts.byState).containsEntry(KoboBookMappingState.NOT_CHECKED, 1L)
    assertThat(counts.byState).containsEntry(KoboBookMappingState.NO_VALID_ISBN, 1L)
    assertThat(counts.byState).containsEntry(KoboBookMappingState.STALE_ISBN, 1L)
  }

  @Test
  fun `ISBN checksum validity not only thirteen digit length controls mapping validity`() {
    val invalidBook =
      mockk<BookDto> {
        every { id } returns "invalid"
        every { metadata } returns
          mockk<BookMetadataDto> {
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
