package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.model.ReadProgress
import org.gotson.komga.domain.model.UserRoles
import org.gotson.komga.domain.model.makeBook
import org.gotson.komga.domain.model.makeLibrary
import org.gotson.komga.domain.model.makeSeries
import org.gotson.komga.domain.persistence.KomgaUserRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.ReadProgressRepository
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
import org.springframework.test.web.servlet.put

/** Exercise real Kobo API-key authentication and authorization without contacting Kobo. */
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class KoboDownloadReadingStateAuthorizationIntegrationTest(
  @Autowired private val mockMvc: MockMvc,
  @Autowired private val libraryRepository: LibraryRepository,
  @Autowired private val userRepository: KomgaUserRepository,
  @Autowired private val userLifecycle: KomgaUserLifecycle,
  @Autowired private val seriesLifecycle: SeriesLifecycle,
  @Autowired private val readProgressRepository: ReadProgressRepository,
  @Autowired private val settingsProvider: KomgaSettingsProvider,
) {
  private val libraryA = makeLibrary("kobo-download-security-a")
  private val libraryB = makeLibrary("kobo-download-security-b")
  private val seriesA = makeSeries("kobo-download-security-series-a", libraryId = libraryA.id)
  private val seriesB = makeSeries("kobo-download-security-series-b", libraryId = libraryB.id)
  private val bookA = makeBook("kobo-download-security-book-a", libraryId = libraryA.id)
  private val bookB = makeBook("kobo-download-security-book-b", libraryId = libraryB.id)
  private val allowedRoles = setOf(UserRoles.KOBO_SYNC, UserRoles.FILE_DOWNLOAD)
  private val userA =
    KomgaUser(
      email = "kobo-download-security-a@example.org",
      password = "",
      roles = allowedRoles,
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryA.id),
    )
  private val userB =
    KomgaUser(
      email = "kobo-download-security-b@example.org",
      password = "",
      roles = allowedRoles,
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryB.id),
    )
  private val otherUserWithAccess =
    KomgaUser(
      email = "kobo-download-security-shared@example.org",
      password = "",
      roles = allowedRoles,
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryA.id),
    )
  private val userWithoutDownloadRole =
    KomgaUser(
      email = "kobo-download-security-no-download@example.org",
      password = "",
      roles = setOf(UserRoles.KOBO_SYNC),
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryA.id),
    )
  private lateinit var keyA: String
  private lateinit var keyB: String
  private lateinit var sharedKey: String
  private lateinit var noDownloadKey: String
  private var previousProxySetting = false

  @BeforeAll
  fun setUp() {
    previousProxySetting = settingsProvider.koboProxy
    settingsProvider.koboProxy = false
    libraryRepository.insert(libraryA)
    libraryRepository.insert(libraryB)
    seriesLifecycle.addBooks(seriesLifecycle.createSeries(seriesA), listOf(bookA))
    seriesLifecycle.addBooks(seriesLifecycle.createSeries(seriesB), listOf(bookB))
    userRepository.insert(userA)
    userRepository.insert(userB)
    userRepository.insert(otherUserWithAccess)
    userRepository.insert(userWithoutDownloadRole)
    keyA = userLifecycle.createApiKey(userA, "kobo-download-security-a")!!.key
    keyB = userLifecycle.createApiKey(userB, "kobo-download-security-b")!!.key
    sharedKey = userLifecycle.createApiKey(otherUserWithAccess, "kobo-download-security-shared")!!.key
    noDownloadKey = userLifecycle.createApiKey(userWithoutDownloadRole, "kobo-download-security-no-download")!!.key
  }

  @AfterAll
  fun tearDown() {
    try {
      readProgressRepository.deleteByBookId(bookA.id)
      readProgressRepository.deleteByBookId(bookB.id)
      userLifecycle.deleteUser(userWithoutDownloadRole)
      userLifecycle.deleteUser(otherUserWithAccess)
      userLifecycle.deleteUser(userB)
      userLifecycle.deleteUser(userA)
      seriesLifecycle.deleteMany(listOf(seriesA, seriesB))
      libraryRepository.delete(libraryA.id)
      libraryRepository.delete(libraryB.id)
    } finally {
      settingsProvider.koboProxy = previousProxySetting
    }
  }

  @Test
  fun `users cannot download books from a restricted library in either EPUB or KEPUB format`() {
    for ((key, inaccessibleBookId) in listOf(keyA to bookB.id, keyB to bookA.id)) {
      mockMvc.get("/kobo/$key/v1/books/$inaccessibleBookId/file/epub").andExpect {
        status { isForbidden() }
      }
      mockMvc.get("/kobo/$key/v1/books/$inaccessibleBookId/file/epub?convert_kepub=true").andExpect {
        status { isForbidden() }
      }
    }
  }

  @Test
  fun `KOBO_SYNC without FILE_DOWNLOAD cannot download even an accessible book`() {
    mockMvc.get("/kobo/$noDownloadKey/v1/books/${bookA.id}/file/epub").andExpect {
      status { isForbidden() }
    }
    mockMvc.get("/kobo/$noDownloadKey/v1/books/${bookA.id}/file/epub?convert_kepub=true").andExpect {
      status { isForbidden() }
    }
  }

  @Test
  fun `reading-state requests cannot read or update books from a restricted library`() {
    for ((key, inaccessibleBookId) in listOf(keyA to bookB.id, keyB to bookA.id)) {
      mockMvc.get("/kobo/$key/v1/library/$inaccessibleBookId/state").andExpect {
        status { isForbidden() }
      }
      mockMvc
        .put("/kobo/$key/v1/library/$inaccessibleBookId/state") {
          contentType = org.springframework.http.MediaType.APPLICATION_JSON
          content = """{"ReadingStates":[]}"""
        }.andExpect {
          status { isForbidden() }
        }
    }
    assertThat(readProgressRepository.findByBookIdAndUserIdOrNull(bookA.id, userB.id)).isNull()
    assertThat(readProgressRepository.findByBookIdAndUserIdOrNull(bookB.id, userA.id)).isNull()
  }

  @Test
  fun `permitted users can read their books and authorized PUT reaches validation`() {
    mockMvc.get("/kobo/$keyA/v1/library/${bookA.id}/state").andExpect { status { isOk() } }
    mockMvc.get("/kobo/$keyB/v1/library/${bookB.id}/state").andExpect { status { isOk() } }
    mockMvc
      .put("/kobo/$keyA/v1/library/${bookA.id}/state") {
        contentType = org.springframework.http.MediaType.APPLICATION_JSON
        content = """{"ReadingStates":[]}"""
      }.andExpect {
        status { isBadRequest() }
      }
  }

  @Test
  fun `reading progress for a shared book remains specific to the authenticated user`() {
    readProgressRepository.save(
      ReadProgress(
        bookId = bookA.id,
        userId = userA.id,
        page = 2,
        completed = true,
      ),
    )
    mockMvc.get("/kobo/$keyA/v1/library/${bookA.id}/state").andExpect {
      status { isOk() }
      jsonPath("$[0].StatusInfo.Status") { value("Finished") }
    }
    mockMvc.get("/kobo/$sharedKey/v1/library/${bookA.id}/state").andExpect {
      status { isOk() }
      jsonPath("$[0].StatusInfo.Status") { value("ReadyToRead") }
    }
    assertThat(readProgressRepository.findByBookIdAndUserIdOrNull(bookA.id, otherUserWithAccess.id)).isNull()
  }
}
