package org.gotson.komga.interfaces.api.kobo

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

/** Exercises actual Kobo API-key authentication and local Store endpoints without proxy requests. */
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class KoboStoreAuthorizationIntegrationTest(
  @Autowired private val mockMvc: MockMvc,
  @Autowired private val libraryRepository: LibraryRepository,
  @Autowired private val userRepository: KomgaUserRepository,
  @Autowired private val userLifecycle: KomgaUserLifecycle,
  @Autowired private val seriesLifecycle: SeriesLifecycle,
  @Autowired private val settingsProvider: KomgaSettingsProvider,
) {
  private val libraryA = makeLibrary("kobo-store-authorization-a")
  private val libraryB = makeLibrary("kobo-store-authorization-b")
  private val seriesA = makeSeries("kobo-store-authorized-a", libraryId = libraryA.id)
  private val seriesB = makeSeries("kobo-store-authorized-b", libraryId = libraryB.id)
  private val bookA = makeBook("kobo-store-authorized-book-a", libraryId = libraryA.id)
  private val bookB = makeBook("kobo-store-authorized-book-b", libraryId = libraryB.id)
  private val userA =
    KomgaUser(
      email = "kobo-store-authorization-a@example.org",
      password = "",
      roles = setOf(UserRoles.KOBO_SYNC),
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryA.id),
    )
  private val userB =
    KomgaUser(
      email = "kobo-store-authorization-b@example.org",
      password = "",
      roles = setOf(UserRoles.KOBO_SYNC),
      sharedAllLibraries = false,
      sharedLibrariesIds = setOf(libraryB.id),
    )
  private lateinit var apiKeyA: String
  private lateinit var apiKeyB: String
  private var previousProxySetting = false

  @BeforeAll
  fun setUp() {
    previousProxySetting = settingsProvider.koboProxy
    settingsProvider.koboProxy = false // Never contact the authenticated Kobo Store API.
    libraryRepository.insert(libraryA)
    libraryRepository.insert(libraryB)
    seriesLifecycle.addBooks(seriesLifecycle.createSeries(seriesA), listOf(bookA))
    seriesLifecycle.addBooks(seriesLifecycle.createSeries(seriesB), listOf(bookB))
    userRepository.insert(userA)
    userRepository.insert(userB)
    apiKeyA = userLifecycle.createApiKey(userA, "kobo-store-authorization-a")!!.key
    apiKeyB = userLifecycle.createApiKey(userB, "kobo-store-authorization-b")!!.key
  }

  @AfterAll
  fun tearDown() {
    userLifecycle.deleteUser(userA)
    userLifecycle.deleteUser(userB)
    seriesLifecycle.deleteMany(listOf(seriesA, seriesB))
    libraryRepository.deleteAll()
    settingsProvider.koboProxy = previousProxySetting
  }

  @Test
  fun `each user can request local book details from their permitted library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/books/${bookA.id}").andExpect {
      status { isOk() }
      jsonPath("$.Id") { value(bookA.id) }
    }
    mockMvc.get("/kobo/$apiKeyB/v1/products/books/${bookB.id}").andExpect {
      status { isOk() }
      jsonPath("$.Id") { value(bookB.id) }
    }
  }

  @Test
  fun `each user is forbidden from requesting local book details in the other library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/books/${bookB.id}").andExpect { status { isForbidden() } }
    mockMvc.get("/kobo/$apiKeyB/v1/products/books/${bookA.id}").andExpect { status { isForbidden() } }
  }

  @Test
  fun `each user can request a local series from their permitted library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/books/series/${seriesA.id}").andExpect {
      status { isOk() }
      jsonPath("$.Items[0].Book.Id") { value(bookA.id) }
    }
    mockMvc.get("/kobo/$apiKeyB/v1/products/books/series/${seriesB.id}").andExpect {
      status { isOk() }
      jsonPath("$.Items[0].Book.Id") { value(bookB.id) }
    }
  }

  @Test
  fun `each user is forbidden from requesting local series in the other library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/books/series/${seriesB.id}").andExpect { status { isForbidden() } }
    mockMvc.get("/kobo/$apiKeyB/v1/products/books/series/${seriesA.id}").andExpect { status { isForbidden() } }
  }

  @Test
  fun `each user can request local book prices from their permitted library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/${bookA.id}/prices").andExpect {
      status { isOk() }
      jsonPath("$.Items[0].Id") { value(bookA.id) }
    }
    mockMvc.get("/kobo/$apiKeyB/v1/products/${bookB.id}/prices").andExpect {
      status { isOk() }
      jsonPath("$.Items[0].Id") { value(bookB.id) }
    }
  }

  @Test
  fun `each user is forbidden from requesting local book prices in the other library`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/${bookB.id}/prices").andExpect { status { isForbidden() } }
    mockMvc.get("/kobo/$apiKeyB/v1/products/${bookA.id}/prices").andExpect { status { isForbidden() } }
  }

  @Test
  fun `mixed local book prices are forbidden if any requested book is restricted`() {
    mockMvc.get("/kobo/$apiKeyA/v1/products/${bookA.id},${bookB.id}/prices").andExpect {
      status { isForbidden() }
    }
    mockMvc.get("/kobo/$apiKeyB/v1/products/${bookA.id},${bookB.id}/prices").andExpect {
      status { isForbidden() }
    }
  }
}
