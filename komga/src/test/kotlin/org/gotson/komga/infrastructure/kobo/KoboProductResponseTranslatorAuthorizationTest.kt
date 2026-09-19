package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.ContentRestrictions
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.model.SeriesMetadata
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.SeriesMetadataRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.gotson.komga.domain.persistence.ThumbnailBookRepository
import org.gotson.komga.domain.persistence.ThumbnailSeriesRepository
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.gotson.komga.interfaces.api.kobo.persistence.KoboDtoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboProductResponseTranslatorAuthorizationTest {
  private val mapper = ObjectMapper()
  private val resolver = mockk<KoboProductResolver>()
  private val isbnLookup = mockk<KoboLocalBookLookup>()
  private val dtoRepository = mockk<KoboDtoRepository>()
  private val bookRepository = mockk<BookRepository>()
  private val seriesMetadataRepository = mockk<SeriesMetadataRepository>()
  private val checker =
    ContentRestrictionChecker(
      seriesMetadataRepository,
      bookRepository,
      mockk<ThumbnailBookRepository>(),
      mockk<SeriesRepository>(),
      mockk<ThumbnailSeriesRepository>(),
    )
  private val translator =
    KoboProductResponseTranslator(resolver, isbnLookup, dtoRepository, checker)
  private val alice =
    KomgaUser(
      email = "alice@example.invalid",
      password = "test",
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf("library-a"),
    )
  private val bob =
    KomgaUser(
      email = "bob@example.invalid",
      password = "test",
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf("library-b"),
    )

  @BeforeEach
  fun resetMocks() {
    clearMocks(
      resolver,
      isbnLookup,
      dtoRepository,
      bookRepository,
      seriesMetadataRepository,
    )
  }

  @Test
  fun `mapped and ISBN matched products preserve upstream content for unauthorized user`() {
    every { resolver.resolveBookId("mapped-product") } returns "private-book"
    every { resolver.resolveBookId("isbn-product") } returns null
    every { isbnLookup.findUniqueBookIdByIsbn("9781974753246") } returns "private-book"
    every { bookRepository.getLibraryIdOrNull("private-book") } returns "library-a"
    every { dtoRepository.findBookMetadataByIds(any()) } returns emptyList()

    val upstream =
      mapper.readTree(
        """{"Items":[{"Book":{"Id":"mapped-product","ISBN":"9781974753246","ImageId":"store-cover","Description":"store-description","SeriesId":"store-series"}},{"Book":{"Id":"isbn-product","ISBN":"9781974753246","ImageId":"store-cover","Description":"store-description","SeriesId":"store-series"}}]}""",
      )
    val expected = upstream.deepCopy<JsonNode>()

    val result = translator.translate("/v1/products", upstream, bob)

    assertThat(result).isEqualTo(expected)
    verify(exactly = 0) { dtoRepository.findBookMetadataByIds(any()) }
    // A denied known ProductId must not trigger an ISBN fallback.
    verify(exactly = 1) { isbnLookup.findUniqueBookIdByIsbn("9781974753246") }

    val allowed = translator.translate("/v1/products", expected.deepCopy<JsonNode>(), alice)
    assertThat(allowed["Items"][0]["Book"]["Id"].asText()).isEqualTo("private-book")
    assertThat(allowed["Items"][1]["Book"]["Id"].asText()).isEqualTo("private-book")
  }

  @Test
  fun `nextread keys and reviews retain Kobo identifiers for unauthorized user`() {
    every { resolver.resolveBookId("mapped-product") } returns "private-book"
    every { bookRepository.getLibraryIdOrNull("private-book") } returns "library-a"

    val nextread = mapper.readTree("""{"mapped-product":[{"Id":"mapped-product","RevisionId":"store-revision"}]}""")
    val expectedNextread = nextread.deepCopy<JsonNode>()
    val translatedNextread =
      translator.translate("/v1/products/mapped-product/nextread", nextread, bob)
    assertThat(translatedNextread).isEqualTo(expectedNextread)
    assertThat(translatedNextread.has("private-book")).isFalse()

    val reviews =
      mapper.readTree(
        """{"Items":[{"ProductId":"mapped-product","RevisionId":"store-revision","CrossRevisionId":"store-cross"}],"ReviewSummary":{"store-cross":{"OpinionCount":4}}}""",
      )
    val expectedReviews = reviews.deepCopy<JsonNode>()
    val translatedReviews =
      translator.translate("/v1/products/mapped-product/reviews", reviews, bob)
    assertThat(translatedReviews).isEqualTo(expectedReviews)
    assertThat(translatedReviews["ReviewSummary"].has("private-book")).isFalse()
    verify(exactly = 0) { dtoRepository.findBookMetadataByIds(any()) }
  }

  @Test
  fun `series sharing-label restriction blocks otherwise accessible book`() {
    val restrictedAlice =
      alice.copy(restrictions = ContentRestrictions(labelsExclude = setOf("private")))
    every { resolver.resolveBookId("mapped-product") } returns "private-book"
    every { bookRepository.getLibraryIdOrNull("private-book") } returns "library-a"
    every { bookRepository.getSeriesIdOrNull("private-book") } returns "private-series"
    every { seriesMetadataRepository.findById("private-series") } returns
      SeriesMetadata(title = "Private series", sharingLabels = setOf("private"))

    val upstream = mapper.readTree("""{"Items":[{"Book":{"Id":"mapped-product","SeriesId":"store-series"}}]}""")
    val result = translator.translate("/v1/products", upstream, restrictedAlice)
    assertThat(result["Items"][0]["Book"]["Id"].asText()).isEqualTo("mapped-product")
    assertThat(result["Items"][0]["Book"]["SeriesId"].asText()).isEqualTo("store-series")
  }

  @Test
  fun `missing authenticated user leaves upstream product unchanged`() {
    val upstream = mapper.readTree("""{"Items":[{"Book":{"Id":"mapped-product"}}]}""")
    assertThat(translator.translate("/v1/products", upstream, null)).isEqualTo(upstream)
    verify(exactly = 0) { resolver.resolveBookId(any()) }
  }
}
