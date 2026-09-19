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
import org.gotson.komga.domain.persistence.KoboExternalCollectionMemberRepository
import org.gotson.komga.domain.persistence.KomgaUserRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.MediaRepository
import org.gotson.komga.domain.persistence.ReadListRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.gotson.komga.domain.service.KomgaUserLifecycle
import org.gotson.komga.domain.service.ReadListLifecycle
import org.gotson.komga.domain.service.SeriesLifecycle
import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.gotson.komga.infrastructure.kobo.KoboHeaders
import org.gotson.komga.infrastructure.kobo.KomgaSyncTokenGenerator
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
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
  @Autowired private val externalMembers: KoboExternalCollectionMemberRepository,
  @Autowired private val readListRepository: ReadListRepository,
  @Autowired private val readListLifecycle: ReadListLifecycle,
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

  // The test class shares a library and users, but every test must start with fresh books
  // and ReadLists so a previous mixed-collection test cannot leak into archive assertions.
  @BeforeEach
  fun cleanTestContent() {
    readListRepository.deleteAll()
    seriesLifecycle.deleteMany(seriesRepository.findAll())
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

  @Test
  fun `mixed collection retains Kobo-only members after a Komga ReadList edit without showing them to another user`() {
    val books = (1..2).map { makeBook("mixed-collection-$it", libraryId = library.id) }
    val series = seriesLifecycle.createSeries(makeSeries("mixed-collection-series", libraryId = library.id))
    seriesLifecycle.addBooks(series, books)
    books.forEach { book ->
      mediaRepository.findById(book.id).let { media ->
        mediaRepository.update(media.copy(status = Media.Status.READY, mediaType = MediaType.EPUB.type))
      }
    }

    val initialA = completeSync(apiKeyA)
    val initialB = completeSync(apiKeyB)
    val storeRevisionId = "00000000-0000-4000-8000-000000000999"
    val collectionName = "mixed-local-and-store-collection"
    val createBody =
      objectMapper.writeValueAsBytes(
        mapOf(
          "Name" to collectionName,
          "Items" to
            listOf(
              mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to books[0].id),
              mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to storeRevisionId),
            ),
        ),
      )
    val created =
      mockMvc
        .perform(
          MockMvcRequestBuilders
            .post("/kobo/$apiKeyA/v1/library/tags")
            .contentType("application/json")
            .content(createBody),
        ).andExpect(status().isCreated)
        .andReturn()
    val readListId = objectMapper.readTree(created.response.contentAsString).asText()
    val localReadList = readListRepository.findByIdOrNull(readListId)!!
    assertThat(localReadList.bookIds.values).containsExactly(books[0].id)
    assertThat(localReadList.bookIds.values).doesNotContain(storeRevisionId)

    val createdA = completeSync(apiKeyA, initialA.token)
    val createdB = completeSync(apiKeyB, initialB.token)

    readListLifecycle.updateReadList(
      readListRepository.findByIdOrNull(readListId)!!.copy(
        bookIds = sortedMapOf(0 to books[0].id, 1 to books[1].id),
      ),
    )
    val changedA = completeSync(apiKeyA, createdA.token)
    val changedB = completeSync(apiKeyB, createdB.token)

    fun SyncTrace.tagItems(kind: String): List<String> =
      events
        .filter {
          it
            .path(kind)
            .path("Tag")
            .path("Id")
            .asText() == readListId
        }.flatMap { entry ->
          entry
            .path(kind)
            .path("Tag")
            .path("Items")
            .map { it.path("RevisionId").asText() }
        }

    // Komga remains unaware of the purchased member; only the originating user's
    // Kobo collection receives it when Komga republishes the changed ReadList.
    assertThat(readListRepository.findByIdOrNull(readListId)!!.bookIds.values)
      .containsExactly(books[0].id, books[1].id)
    assertThat(createdA.tagItems("NewTag")).contains(storeRevisionId)
    assertThat(changedA.tagItems("ChangedTag"))
      .contains(books[0].id, books[1].id, storeRevisionId)
    assertThat(createdB.tagItems("NewTag")).doesNotContain(storeRevisionId)
    assertThat(changedB.tagItems("ChangedTag")).doesNotContain(storeRevisionId)
  }

  @Test
  fun `only Kobo membership requests remove external members and deleting a ReadList cascades`() {
    val book = makeBook("external-member-lifecycle", libraryId = library.id)
    val series = seriesLifecycle.createSeries(makeSeries("external-member-series", libraryId = library.id))
    seriesLifecycle.addBooks(series, listOf(book))
    val firstStoreId = "aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"
    val secondStoreId = "bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"
    val collectionName = "external-member-lifecycle-collection"

    fun itemsBody(vararg ids: String): ByteArray =
      objectMapper.writeValueAsBytes(
        mapOf("Items" to ids.map { mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to it) }),
      )

    val createBody =
      objectMapper.writeValueAsBytes(
        mapOf(
          "Name" to collectionName,
          "Items" to
            listOf(book.id, firstStoreId).map {
              mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to it)
            },
        ),
      )
    val created =
      mockMvc
        .perform(
          MockMvcRequestBuilders
            .post("/kobo/$apiKeyA/v1/library/tags")
            .contentType("application/json")
            .content(createBody),
        ).andExpect(status().isCreated)
        .andReturn()
    val readListId = objectMapper.readTree(created.response.contentAsString).asText()

    assertThat(externalMembers.findByReadListIds(userA.id, listOf(readListId))[readListId])
      .containsExactly(firstStoreId)
    assertThat(externalMembers.findByReadListIds(userB.id, listOf(readListId))).isEmpty()

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/kobo/$apiKeyA/v1/library/tags/$readListId/items")
          .contentType("application/json")
          .content(itemsBody(secondStoreId)),
      ).andExpect(status().isCreated)
    assertThat(externalMembers.findByReadListIds(userA.id, listOf(readListId))[readListId])
      .containsExactly(firstStoreId, secondStoreId)

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/kobo/$apiKeyA/v1/library/tags/$readListId/items/delete")
          .contentType("application/json")
          .content(itemsBody(firstStoreId)),
      ).andExpect(status().isOk)
    assertThat(externalMembers.findByReadListIds(userA.id, listOf(readListId))[readListId])
      .containsExactly(secondStoreId)
    assertThat(readListRepository.findByIdOrNull(readListId)!!.bookIds.values)
      .containsExactly(book.id)

    readListLifecycle.deleteReadList(readListRepository.findByIdOrNull(readListId)!!)
    assertThat(readListRepository.findByIdOrNull(readListId)).isNull()
    assertThat(externalMembers.findByReadListIds(userA.id, listOf(readListId))).isEmpty()
  }

  @Test
  fun `external-only collection edits propagate between device keys without modifying Komga ReadList`() {
    val book = makeBook("external-only-sync", libraryId = library.id)
    val series = seriesLifecycle.createSeries(makeSeries("external-only-series", libraryId = library.id))
    seriesLifecycle.addBooks(series, listOf(book))
    mediaRepository.findById(book.id).let { media ->
      mediaRepository.update(media.copy(status = Media.Status.READY, mediaType = MediaType.EPUB.type))
    }

    // Distinct device credentials for the same Komga user; never share an API key between devices.
    val secondDeviceKey = userLifecycle.createApiKey(userA, "external-only-second-device")!!.key
    val initialFirst = completeSync(apiKeyA)
    val initialSecond = completeSync(secondDeviceKey)
    val initialOtherUser = completeSync(apiKeyB)
    val firstStoreId = "aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa"
    val secondStoreId = "bbbbbbbb-bbbb-4bbb-8bbb-bbbbbbbbbbbb"

    fun itemsBody(vararg ids: String): ByteArray =
      objectMapper.writeValueAsBytes(
        mapOf("Items" to ids.map { mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to it) }),
      )

    val createBody =
      objectMapper.writeValueAsBytes(
        mapOf(
          "Name" to "external-only-sync-collection",
          "Items" to
            listOf(book.id, firstStoreId).map {
              mapOf("Type" to "ProductRevisionTagItem", "RevisionId" to it)
            },
        ),
      )
    val created =
      mockMvc
        .perform(
          MockMvcRequestBuilders
            .post("/kobo/$apiKeyA/v1/library/tags")
            .contentType("application/json")
            .content(createBody),
        ).andExpect(status().isCreated)
        .andReturn()
    val readListId = objectMapper.readTree(created.response.contentAsString).asText()

    fun SyncTrace.tagItems(kind: String): List<String> =
      events
        .filter {
          it
            .path(kind)
            .path("Tag")
            .path("Id")
            .asText() == readListId
        }.flatMap { entry ->
          entry
            .path(kind)
            .path("Tag")
            .path("Items")
            .map { it.path("RevisionId").asText() }
        }

    val firstAfterCreate = completeSync(apiKeyA, initialFirst.token)
    val secondAfterCreate = completeSync(secondDeviceKey, initialSecond.token)
    val otherAfterCreate = completeSync(apiKeyB, initialOtherUser.token)
    assertThat(firstAfterCreate.tagItems("NewTag")).contains(book.id, firstStoreId)
    assertThat(secondAfterCreate.tagItems("NewTag")).contains(book.id, firstStoreId)
    assertThat(otherAfterCreate.tagItems("NewTag")).doesNotContain(firstStoreId)

    // This operation must not change the Komga ReadList or rely on an unrelated Komga edit.
    val originalReadList = readListRepository.findByIdOrNull(readListId)!!
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/kobo/$apiKeyA/v1/library/tags/$readListId/items")
          .contentType("application/json")
          .content(itemsBody(secondStoreId)),
      ).andExpect(status().isCreated)
    assertThat(readListRepository.findByIdOrNull(readListId)!!).isEqualTo(originalReadList)

    val secondAfterAdd = completeSync(secondDeviceKey, secondAfterCreate.token)
    assertThat(secondAfterAdd.tagItems("ChangedTag"))
      .contains(book.id, firstStoreId, secondStoreId)
    val otherAfterAdd = completeSync(apiKeyB, otherAfterCreate.token)
    assertThat(otherAfterAdd.tagItems("ChangedTag")).doesNotContain(firstStoreId, secondStoreId)

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/kobo/$apiKeyA/v1/library/tags/$readListId/items/delete")
          .contentType("application/json")
          .content(itemsBody(firstStoreId)),
      ).andExpect(status().isOk)
    assertThat(readListRepository.findByIdOrNull(readListId)!!).isEqualTo(originalReadList)

    val secondAfterRemove = completeSync(secondDeviceKey, secondAfterAdd.token)
    assertThat(secondAfterRemove.tagItems("ChangedTag"))
      .contains(book.id, secondStoreId)
      .doesNotContain(firstStoreId)
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
