
package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.Book
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class KoboRecommendationsFeedbackTranslatorTest {
  private val mapper = ObjectMapper()
  private val repository = mockk<BookRepository>()
  private val resolver = mockk<KoboProductResolver>()
  private val restrictions = mockk<ContentRestrictionChecker>()
  private val principal = mockk<KomgaPrincipal>()
  private val user = mockk<KomgaUser>()
  private val translator = KoboRecommendationsFeedbackTranslator(mapper, repository, resolver, restrictions)

  private val localId = "0RDTATKHXHPS9"
  private val koboId = "8201afa9-c23b-429b-a642-a4bcf1c8b638"
  private val nativeKoboId = "907a784e-c23b-429b-a642-a4bcf1c8b638"
  private val localBook = mockk<Book>()

  private fun localBookExists() {
    every { repository.findByIdOrNull(localId) } returns localBook
    every { principal.user } returns user
    every { restrictions.checkContentRestrictionBook(user, localBook) } returns Unit
  }

  @Test
  fun `translates all matching local items and retains feedback types and extra fields`() {
    localBookExists()

    every { resolver.resolveProductIdForDevice(localId) } returns koboId
    every { repository.findByIdOrNull(nativeKoboId) } returns null

    val input =
      """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"$localId","Note":"preserve"},""" +
        """{"FeedbackType":"Rate1Star","RevisionId":"$nativeKoboId"},""" +
        """{"FeedbackType":"Rate5Star","RevisionId":"$localId"}],"UnknownProperty":true}"""

    val output = mapper.readTree(translator.translate(input.toByteArray(), principal))

    assertThat(output.path("FeedbackItems").size()).isEqualTo(3)

    assertThat(
      output
        .path("FeedbackItems")
        .path(0)
        .path("RevisionId")
        .asText(),
    ).isEqualTo(koboId)

    assertThat(
      output
        .path("FeedbackItems")
        .path(1)
        .path("RevisionId")
        .asText(),
    ).isEqualTo(nativeKoboId)

    assertThat(
      output
        .path("FeedbackItems")
        .path(2)
        .path("RevisionId")
        .asText(),
    ).isEqualTo(koboId)

    assertThat(
      output
        .path("FeedbackItems")
        .path(1)
        .path("FeedbackType")
        .asText(),
    ).isEqualTo("Rate1Star")

    assertThat(
      output
        .path("FeedbackItems")
        .path(0)
        .path("Note")
        .asText(),
    ).isEqualTo("preserve")

    assertThat(output.path("UnknownProperty").asBoolean()).isTrue()

    verify(exactly = 1) { resolver.resolveProductIdForDevice(localId) }
  }

  @Test
  fun `preserves the original body when all feedback is for Kobo-owned products`() {
    every { repository.findByIdOrNull(nativeKoboId) } returns null

    val input = """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"$nativeKoboId"}]}""".toByteArray()

    assertThat(translator.translate(input, principal)).isEqualTo(input)

    verify(exactly = 0) { resolver.resolveProductIdForDevice(any()) }
  }

  @Test
  fun `does not forward an unknown local mapping as a Kobo product`() {
    localBookExists()

    every { resolver.resolveProductIdForDevice(localId) } returns null

    val body = """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"$localId"}]}""".toByteArray()

    val error =
      runCatching {
        translator.translate(body, principal)
      }.exceptionOrNull()

    assertThat(error).isInstanceOf(ResponseStatusException::class.java)
    assertThat((error as ResponseStatusException).statusCode.value()).isEqualTo(422)
  }

  @Test
  fun `checks book access before resolving its external identity`() {
    every { repository.findByIdOrNull(localId) } returns localBook
    every { principal.user } returns user
    every { restrictions.checkContentRestrictionBook(user, localBook) } throws
      ResponseStatusException(HttpStatus.FORBIDDEN)

    val body = """{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"$localId"}]}""".toByteArray()

    val error =
      runCatching {
        translator.translate(body, principal)
      }.exceptionOrNull()

    assertThat(error).isInstanceOf(ResponseStatusException::class.java)
    assertThat((error as ResponseStatusException).statusCode.value()).isEqualTo(403)

    verify(exactly = 0) { resolver.resolveProductIdForDevice(any()) }
  }

  @Test
  fun `rejects malformed and empty feedback instead of acknowledging it`() {
    val invalidBodies =
      listOf(
        "{",
        "{}",
        """{"FeedbackItems":[]}""",
        """{"FeedbackItems":[{"FeedbackType":"Rate5Star"}]}""",
        """{"FeedbackItems":[{"FeedbackType":"","RevisionId":"a"}]}""",
      )

    invalidBodies.forEach { body ->
      val error =
        runCatching {
          translator.translate(body.toByteArray(), principal)
        }.exceptionOrNull()

      assertThat(error).isInstanceOf(ResponseStatusException::class.java)
      assertThat((error as ResponseStatusException).statusCode.value()).isEqualTo(400)
    }

    verify(exactly = 0) { resolver.resolveProductIdForDevice(any()) }
  }
}
