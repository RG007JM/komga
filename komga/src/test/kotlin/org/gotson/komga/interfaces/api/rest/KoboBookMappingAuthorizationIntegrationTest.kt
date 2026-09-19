package org.gotson.komga.interfaces.api.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/** Exercises the real HTTP security chain and controller method authorization. */
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class KoboBookMappingAuthorizationIntegrationTest(
  @Autowired private val mockMvc: MockMvc,
) {
  @Test
  @WithMockCustomUser
  fun `non admin cannot list Kobo book mappings`() {
    mockMvc.get("/api/v1/books/kobo-mappings").andExpect {
      status { isForbidden() }
    }
  }

  @Test
  @WithMockCustomUser
  fun `non admin cannot inspect a book Kobo mapping`() {
    mockMvc.get("/api/v1/books/book-1/kobo-mapping").andExpect {
      status { isForbidden() }
    }
  }

  @Test
  @WithMockCustomUser(roles = ["ADMIN"])
  fun `admin can access the mapping listing`() {
    mockMvc.get("/api/v1/books/kobo-mappings").andExpect {
      status { isOk() }
      jsonPath("$.content") { isArray() }
    }
  }
}
