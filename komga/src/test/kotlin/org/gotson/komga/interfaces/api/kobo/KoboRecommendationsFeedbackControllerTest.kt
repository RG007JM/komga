
package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

class KoboRecommendationsFeedbackControllerTest {
  private val proxy = mockk<KoboRawStoreProxy>()
  private val translator = mockk<KoboRecommendationsFeedbackTranslator>()
  private val principal = mockk<KomgaPrincipal>()
  private val controller = KoboRecommendationsFeedbackController(proxy, translator)

  @BeforeEach
  fun resetMocks() {
    clearMocks(proxy, translator, principal)
  }

  @Test
  fun `forwards the translated POST body and preserves the Kobo response status`() {
    val original = """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"0RDTATKHXHPS9"}]}""".toByteArray()
    val translated = """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"a-kobo-id"}]}""".toByteArray()
    val koboResponse = ResponseEntity.status(202).body<JsonNode>(ObjectMapper().readTree("{}"))

    every { proxy.isEnabled() } returns true
    every { translator.translate(original, principal) } returns translated
    every { proxy.proxyCurrentRequest(body = translated) } returns koboResponse

    val actual = controller.postFeedback(principal, original)

    assertThat(actual.statusCode.value()).isEqualTo(202)

    verify(exactly = 1) { translator.translate(original, principal) }
    verify(exactly = 1) { proxy.proxyCurrentRequest(body = translated) }
  }

  @Test
  fun `disabled cloud proxy does not report unpersisted feedback as successful`() {
    every { proxy.isEnabled() } returns false

    val error =
      runCatching {
        controller.postFeedback(principal, "{}".toByteArray())
      }.exceptionOrNull()

    assertThat(error).isInstanceOf(ResponseStatusException::class.java)
    assertThat((error as ResponseStatusException).statusCode.value()).isEqualTo(503)

    verify(exactly = 0) { translator.translate(any(), any()) }
    verify(exactly = 0) { proxy.proxyCurrentRequest(body = any()) }
  }
}
