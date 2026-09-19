package org.gotson.komga.interfaces.api.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.model.SearchContext
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.openapi.OpenApiConfiguration
import org.gotson.komga.infrastructure.openapi.PageableWithoutSortAsQueryParam
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.gotson.komga.interfaces.api.persistence.BookDtoRepository
import org.gotson.komga.interfaces.api.rest.dto.BookDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

/** An inspection-only API: never resolves ISBNs or contacts the Kobo website. */
@RestController
@RequestMapping("api/v1/books", produces = [MediaType.APPLICATION_JSON_VALUE])
class KoboBookMappingController(
  private val bookDtoRepository: BookDtoRepository,
  private val mappingRepository: KoboProductMappingRepository,
  private val contentRestrictionChecker: ContentRestrictionChecker,
) {
  @Operation(summary = "List cached Kobo ProductId mappings", tags = [OpenApiConfiguration.TagNames.BOOKS])
  @PageableWithoutSortAsQueryParam
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("kobo-mappings")
  fun listMappings(
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @Parameter(hidden = true) page: Pageable,
  ): Page<KoboBookMappingDto> {
    val books = bookDtoRepository.findAll(SearchContext(principal.user), page)
    val mappings = mappingRepository.findByBookIds(books.content.map { it.id }).associateBy { it.bookId }
    return books.map { it.toKoboMappingDto(mappings[it.id]) }
  }

  @Operation(summary = "Get cached Kobo ProductId mapping for a book", tags = [OpenApiConfiguration.TagNames.BOOKS])
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("{bookId}/kobo-mapping")
  fun getMapping(
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @PathVariable bookId: String,
  ): KoboBookMappingDto {
    val book =
      bookDtoRepository.findByIdOrNull(bookId, principal.user.id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    contentRestrictionChecker.checkContentRestrictionBook(principal.user, book)
    return book.toKoboMappingDto(mappingRepository.findByBookId(bookId))
  }
}

enum class KoboBookMappingState {
  MAPPED,
  NO_VALID_ISBN,
  NOT_CHECKED,
  NOT_FOUND,
  STALE_ISBN,
}

data class KoboBookMappingDto(
  val bookId: String,
  val title: String,
  val isbn: String,
  val state: KoboBookMappingState,
  val hasKoboProductId: Boolean,
  val productId: String?,
  val observedKoboSeriesId: String?,
  val cachedIsbn: String?,
  val checkedAt: LocalDateTime?,
)

internal fun BookDto.toKoboMappingDto(mapping: KoboProductMapping?): KoboBookMappingDto {
  val currentIsbn = metadata.isbn.filter(Char::isDigit).takeIf { it.length == 13 }
  val isCurrent = currentIsbn != null && currentIsbn == mapping?.isbn
  val mapped = isCurrent && mapping?.status == KoboProductMappingStatus.FOUND && !mapping?.productId.isNullOrBlank()
  val state =
    when {
      currentIsbn == null -> KoboBookMappingState.NO_VALID_ISBN
      mapping == null -> KoboBookMappingState.NOT_CHECKED
      !isCurrent -> KoboBookMappingState.STALE_ISBN
      mapped -> KoboBookMappingState.MAPPED
      mapping.status == KoboProductMappingStatus.NOT_FOUND -> KoboBookMappingState.NOT_FOUND
      else -> KoboBookMappingState.NOT_CHECKED
    }
  return KoboBookMappingDto(
    bookId = id,
    title = metadata.title,
    isbn = metadata.isbn,
    state = state,
    hasKoboProductId = mapped,
    productId = mapping?.productId?.takeIf { mapped },
    observedKoboSeriesId = mapping?.observedKoboSeriesId?.takeIf { mapped },
    cachedIsbn = mapping?.isbn,
    checkedAt = mapping?.checkedAt,
  )
}
