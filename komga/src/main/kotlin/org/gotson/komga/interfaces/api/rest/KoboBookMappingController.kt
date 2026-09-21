package org.gotson.komga.interfaces.api.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.model.SearchContext
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.infrastructure.kobo.KoboLookupAttempt
import org.gotson.komga.infrastructure.kobo.KoboLookupDiagnostics
import org.gotson.komga.infrastructure.kobo.KoboLookupIsbn
import org.gotson.komga.infrastructure.openapi.OpenApiConfiguration
import org.gotson.komga.infrastructure.openapi.PageableWithoutSortAsQueryParam
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.gotson.komga.interfaces.api.persistence.BookDtoRepository
import org.gotson.komga.interfaces.api.rest.dto.BookDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
  private val diagnostics: KoboLookupDiagnostics,
) {
  /** Inspect existing mappings only. A state-filtered request scans all accessible books so
   * totalElements is the count of matching books BEFORE pagination, not the current page size.
   */
  @Operation(summary = "List cached Kobo ProductId mappings, optionally filtered by state", tags = [OpenApiConfiguration.TagNames.BOOKS])
  @PageableWithoutSortAsQueryParam
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("kobo-mappings")
  fun listMappings(
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @Parameter(hidden = true) page: Pageable,
    @RequestParam(required = false) state: KoboBookMappingState? = null,
  ): Page<KoboBookMappingDto> {
    val context = SearchContext(principal.user)
    if (state == null) {
      val books = bookDtoRepository.findAll(context, page)
      val mappings = mappingRepository.findByBookIds(books.content.map { it.id }).associateBy { it.bookId }
      return books.map { it.toKoboMappingDto(mappings[it.id]) }
    }

    val matching = ArrayList<KoboBookMappingDto>(page.pageSize)
    val start = page.offset
    val end = if (Long.MAX_VALUE - start < page.pageSize) Long.MAX_VALUE else start + page.pageSize
    var totalMatches = 0L
    forEachMapping(context) { mapping ->
      if (mapping.state == state) {
        if (totalMatches >= start && totalMatches < end) matching.add(mapping)
        totalMatches++
      }
    }
    return PageImpl(matching, page, totalMatches)
  }

  /** Count all accessible books once, grouped by the same state used by the list endpoint. */
  @Operation(summary = "Count cached Kobo mappings by state", tags = [OpenApiConfiguration.TagNames.BOOKS])
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("kobo-mappings/counts")
  fun countMappings(
    @AuthenticationPrincipal principal: KomgaPrincipal,
  ): KoboBookMappingCountsDto {
    val counts = KoboBookMappingState.entries.associateWith { 0L }.toMutableMap()
    var total = 0L
    forEachMapping(SearchContext(principal.user)) { mapping ->
      counts[mapping.state] = counts.getValue(mapping.state) + 1
      total++
    }
    return KoboBookMappingCountsDto(total, counts)
  }

  /** Batch the read to avoid loading the entire library into memory. The source repository
   * applies the authenticated user's content restrictions on each page.
   */
  private fun forEachMapping(
    context: SearchContext,
    visit: (KoboBookMappingDto) -> Unit,
  ) {
    var batchNumber = 0
    do {
      val batch = bookDtoRepository.findAll(context, PageRequest.of(batchNumber, MAPPING_SCAN_BATCH_SIZE))
      if (batch.isEmpty) break
      val mappings = mappingRepository.findByBookIds(batch.content.map { it.id }).associateBy { it.bookId }
      batch.content.forEach { book -> visit(book.toKoboMappingDto(mappings[book.id])) }
      batchNumber++
    } while (batch.hasNext())
  }

  companion object {
    private const val MAPPING_SCAN_BATCH_SIZE = 250
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

  /** Read-only, admin-only diagnostics. This endpoint NEVER starts a Kobo website request. */
  @Operation(summary = "Inspect cached Kobo book identity and recent website lookup", tags = [OpenApiConfiguration.TagNames.BOOKS])
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("{bookId}/kobo-identity-diagnostic")
  fun getIdentityDiagnostic(
    @AuthenticationPrincipal principal: KomgaPrincipal,
    @PathVariable bookId: String,
  ): KoboIdentityDiagnosticDto {
    val book =
      bookDtoRepository.findByIdOrNull(bookId, principal.user.id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    contentRestrictionChecker.checkContentRestrictionBook(principal.user, book)
    val mapping = mappingRepository.findByBookId(bookId)
    val isbn = KoboLookupIsbn.normalize(book.metadata.isbn)
    return KoboIdentityDiagnosticDto(
      book.toKoboMappingDto(mapping),
      mapping?.seriesCheckedAt,
      mapping?.seriesLookupFailedAt,
      diagnostics.latest(isbn),
    )
  }
}

data class KoboBookMappingCountsDto(
  val totalBooks: Long,
  val byState: Map<KoboBookMappingState, Long>,
)

data class KoboIdentityDiagnosticDto(
  val mapping: KoboBookMappingDto,
  val seriesCheckedAt: LocalDateTime?,
  val seriesLookupFailedAt: LocalDateTime?,
  val recentWebsiteAttempt: KoboLookupAttempt?,
)

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
  val currentIsbn = KoboLookupIsbn.normalize(metadata.isbn)
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
