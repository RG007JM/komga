package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.model.Media
import org.gotson.komga.domain.model.MediaType
import org.gotson.komga.domain.model.ReadList
import org.gotson.komga.domain.model.UserRoles
import org.gotson.komga.domain.model.makeBook
import org.gotson.komga.domain.model.makeLibrary
import org.gotson.komga.domain.model.makeSeries
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboArchivedBookRepository
import org.gotson.komga.domain.persistence.KomgaUserRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.MediaRepository
import org.gotson.komga.domain.persistence.ReadListRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.gotson.komga.domain.service.KomgaUserLifecycle
import org.gotson.komga.domain.service.SeriesLifecycle
import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.gotson.komga.infrastructure.kobo.KoboHeaders
import org.gotson.komga.infrastructure.kobo.KomgaSyncTokenGenerator
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/** Exercises the real SyncPoint and archive DAOs through consecutive local HTTP sync requests. */
@SpringBootTest(properties = ["komga.kobo.sync-item-limit=1"])
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class KoboArchiveSyncIntegrationTest(
  @Autowired private val mockMvc: MockMvc,
  @Autowired private val objectMapper: ObjectMapper,
  @Autowired private val libraryRepository: LibraryRepository,
  @Autowired private val userRepository: KomgaUserRepository,
  @Autowired private val userLifecycle: KomgaUserLifecycle,
  @Autowired private val seriesRepository: SeriesRepository,
  @Autowired private val seriesLifecycle: SeriesLifecycle,
  @Autowired private val mediaRepository: MediaRepository,
  @Autowired private val metadataRepository: BookMetadataRepository,
  @Autowired private val archiveRepository: KoboArchivedBookRepository,
  @Autowired private val readListRepository: ReadListRepository,
  @Autowired private val syncTokenGenerator: KomgaSyncTokenGenerator,
  @Autowired private val settingsProvider: KomgaSettingsProvider,
) {
  private val library = makeLibrary("archive sync integration")
  private val userA = KomgaUser("archive-sync-a@example.org", "", roles = setOf(UserRoles.ADMIN, UserRoles.KOBO_SYNC))
  private val userB = KomgaUser("archive-sync-b@example.org", "", roles = setOf(UserRoles.KOBO_SYNC))
  private lateinit var apiKeyA: String
  private lateinit var apiKeyB: String
  private var originalProxyEnabled = false

  @BeforeAll
  fun setUp() {
    originalProxyEnabled = settingsProvider.koboProxy
    settingsProvider.koboProxy = false // No requests to Kobo's authenticated Store API.
    libraryRepository.insert(library)
    userRepository.insert(userA)
    userRepository.insert(userB)
    apiKeyA = userLifecycle.createApiKey(userA, "archive-sync-test-a")!!.key
    apiKeyB = userLifecycle.createApiKey(userB, "archive-sync-test-b")!!.key
  }

  @AfterAll
  fun tearDown() {
    readListRepository.deleteAll()
    seriesLifecycle.deleteMany(seriesRepository.findAll())
    userLifecycle.deleteUser(userA)
    userLifecycle.deleteUser(userB)
    libraryRepository.deleteAll()
    settingsProvider.koboProxy = originalProxyEnabled
  }

  @Test
  fun `archiving and manual restoration remain user specific across paginated syncs`() {
    val books = (1..3).map { makeBook("archive-integration-$it", libraryId = library.id) }
    val series = seriesLifecycle.createSeries(makeSeries("archive-integration-series", libraryId = library.id))
    seriesLifecycle.addBooks(series, books)
    books.forEach { book ->
      mediaRepository.findById(book.id).let { media ->
        mediaRepository.update(media.copy(status = Media.Status.READY, mediaType = MediaType.EPUB.type))
      }
    }

    val initialA = completeSync(apiKeyA)
    val initialB = completeSync(apiKeyB)
    assertThat(initialA.ids("NewEntitlement", "BookEntitlement", "Id"))
      .containsExactlyInAnyOrderElementsOf(books.map { it.id })
    assertThat(initialB.ids("NewEntitlement", "BookEntitlement", "Id"))
      .containsExactlyInAnyOrderElementsOf(books.map { it.id })

    // Archive state is per user, while a book update and a new ReadList compete for sync pages.
    archiveRepository.archive(userA.id, books[1].id)
    archiveRepository.archive(userA.id, books[2].id)
    archiveRepository.archive(userB.id, books[1].id)
    assertThat(metadataRepository.touchLastModifiedDate(books[0].id)).isTrue()
    readListRepository.insert(ReadList("archive-integration-collection", bookIds = sortedMapOf(0 to books[0].id)))

    val changedA = completeSync(apiKeyA, initialA.token)
    val changedB = completeSync(apiKeyB, initialB.token)
    assertThat(changedA.removedIds()).containsExactlyInAnyOrder(books[1].id, books[2].id)
    assertThat(changedB.removedIds()).containsExactly(books[1].id)
    assertThat(changedA.pages).isGreaterThanOrEqualTo(3)
    assertThat(changedA.ids("ChangedProductMetadata", "EntitlementId")).contains(books[0].id)
    assertThat(changedB.ids("ChangedProductMetadata", "EntitlementId")).contains(books[0].id)
    assertThat(changedA.events.any { it.has("NewTag") }).isTrue()

    // A successful SyncPoint must not emit the same archive events on the next incremental sync.
    val stableA = completeSync(apiKeyA, changedA.token)
    val stableB = completeSync(apiKeyB, changedB.token)
    assertThat(stableA.removedIds()).isEmpty()
    assertThat(stableB.removedIds()).isEmpty()

    // This is the existing Refresh Metadata REST action, not a new Unarchive endpoint.
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/v1/books/${books[1].id}/metadata/refresh")
          .with(user(KomgaPrincipal(userA))),
      ).andExpect(status().isAccepted)

    assertThat(archiveRepository.isArchived(userA.id, books[1].id)).isFalse()
    assertThat(archiveRepository.isArchived(userA.id, books[2].id)).isTrue()
    assertThat(archiveRepository.isArchived(userB.id, books[1].id)).isTrue()

    val restoredA = completeSync(apiKeyA, stableA.token)
    val unchangedB = completeSync(apiKeyB, stableB.token)
    assertThat(restoredA.ids("ChangedProductMetadata", "EntitlementId")).contains(books[1].id)
    assertThat(restoredA.removedIds()).isEmpty()
    assertThat(unchangedB.removedIds()).isEmpty()
    assertThat(archiveRepository.isArchived(userB.id, books[1].id)).isTrue()
  }

  private data class SyncTrace(
    val events: List<JsonNode>,
    val token: String,
    val pages: Int,
  ) {
    fun ids(
      kind: String,
      vararg fields: String,
    ): List<String> =
      events
        .filter { it.has(kind) }
        .map { item -> fields.fold(item.path(kind)) { node, field -> node.path(field) }.asText() }

    fun removedIds(): List<String> =
      events
        .filter { it.has("ChangedEntitlement") }
        .map { it.path("ChangedEntitlement").path("BookEntitlement") }
        .filter { it.path("IsRemoved").asBoolean() }
        .map { it.path("Id").asText() }
  }

  private fun completeSync(
    apiKey: String,
    previousToken: String? = null,
  ): SyncTrace {
    var token = previousToken
    var pages = 0
    val events = mutableListOf<JsonNode>()

    while (true) {
      pages++
      assertThat(pages).isLessThan(20) // Fails rather than hanging if a sync cannot advance.
      val response =
        mockMvc
          .get("/kobo/$apiKey/v1/library/sync") {
            token?.let { header(KoboHeaders.X_KOBO_SYNCTOKEN, it) }
          }.andExpect { status { isOk() } }
          .andReturn()
          .response
      val body = objectMapper.readTree(response.contentAsString)
      assertThat(body.isArray).isTrue()
      body.forEach { events.add(it) }
      token = response.getHeader(KoboHeaders.X_KOBO_SYNCTOKEN)
      assertThat(token).isNotBlank()

      val next = syncTokenGenerator.fromBase64(token!!)
      if (response.getHeader(KoboHeaders.X_KOBO_SYNC) == "continue") {
        assertThat(next.ongoingSyncPointId).isNotBlank()
      } else {
        assertThat(next.ongoingSyncPointId).isNull()
        assertThat(next.lastSuccessfulSyncPointId).isNotBlank()
        return SyncTrace(events, token, pages)
      }
    }
  }
}
