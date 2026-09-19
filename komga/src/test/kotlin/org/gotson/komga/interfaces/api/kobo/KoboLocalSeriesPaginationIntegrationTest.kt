package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.model.UserRoles
import org.gotson.komga.domain.model.makeBook
import org.gotson.komga.domain.model.makeLibrary
import org.gotson.komga.domain.model.makeSeries
import org.gotson.komga.domain.persistence.KomgaUserRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.service.KomgaUserLifecycle
import org.gotson.komga.domain.service.SeriesLifecycle
import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/** Verifies local Store series pagination through the actual Kobo endpoint without outbound requests. */
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class KoboLocalSeriesPaginationIntegrationTest(
  @Autowired private val mockMvc: MockMvc,
  @Autowired private val objectMapper: ObjectMapper,
  @Autowired private val libraryRepository: LibraryRepository,
  @Autowired private val userRepository: KomgaUserRepository,
  @Autowired private val userLifecycle: KomgaUserLifecycle,
  @Autowired private val seriesLifecycle: SeriesLifecycle,
  @Autowired private val settingsProvider: KomgaSettingsProvider,
) {
  private val library = makeLibrary("kobo-local-series-pagination")
  private val series = makeSeries("kobo-local-series-pagination", libraryId = library.id)
  private val books = (1..5).map { makeBook("pagination-volume-$it", libraryId = library.id) }
  private val user =
    KomgaUser(
      email = "kobo-series-pagination@example.org",
      password = "",
      roles = setOf(UserRoles.KOBO_SYNC),
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(library.id),
    )
  private lateinit var apiKey: String
  private var previousProxySetting = false

  @BeforeAll
  fun setUp() {
    previousProxySetting = settingsProvider.koboProxy
    settingsProvider.koboProxy = false // No requests to the authenticated Kobo Store API.
    libraryRepository.insert(library)
    seriesLifecycle.addBooks(seriesLifecycle.createSeries(series), books)
    userRepository.insert(user)
    apiKey = userLifecycle.createApiKey(user, "kobo-local-series-pagination")!!.key
  }

  @AfterAll
  fun tearDown() {
    userLifecycle.deleteUser(user)
    seriesLifecycle.deleteMany(listOf(series))
    libraryRepository.deleteAll()
    settingsProvider.koboProxy = previousProxySetting
  }

  private fun getSeriesPage(pageIndex: Int): JsonNode {
    val response =
      mockMvc
        .get("/kobo/$apiKey/v1/products/books/series/${series.id}?PageSize=2&PageIndex=$pageIndex")
        .andExpect { status { isOk() } }
        .andReturn()

    return objectMapper.readTree(response.response.contentAsString)
  }

  @Test
  fun `series with five local books returns distinct pages with consistent totals`() {
    val pages = (0..2).map(::getSeriesPage)

    pages.forEachIndexed { index, page ->
      assertThat(page.path("CurrentPageIndex").asInt()).isEqualTo(index)
      assertThat(page.path("ItemsPerPage").asInt()).isEqualTo(2)
      assertThat(page.path("TotalItemCount").asInt()).isEqualTo(5)
      assertThat(page.path("TotalPageCount").asInt()).isEqualTo(3)
      assertThat(page.path("ItemCount").asInt()).isEqualTo(page.path("Items").size())
    }

    assertThat(pages.map { it.path("Items").size() }).containsExactly(2, 2, 1)

    val returnedIds =
      pages.flatMap { page ->
        page.path("Items").map { item -> item.path("Book").path("Id").asText() }
      }
    assertThat(returnedIds).hasSize(5).doesNotHaveDuplicates()
    assertThat(returnedIds.toSet()).isEqualTo(books.map { it.id }.toSet())
  }

  @Test
  fun `out-of-range page has no books but preserves series totals`() {
    val page = getSeriesPage(3)

    assertThat(page.path("CurrentPageIndex").asInt()).isEqualTo(3)
    assertThat(page.path("ItemsPerPage").asInt()).isEqualTo(2)
    assertThat(page.path("TotalItemCount").asInt()).isEqualTo(5)
    assertThat(page.path("TotalPageCount").asInt()).isEqualTo(3)
    assertThat(page.path("ItemCount").asInt()).isZero()
    assertThat(page.path("Items").size()).isZero()
  }
}
