package org.gotson.komga.infrastructure.kobo

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class KoboRawStoreProxyTest {
  private val settingsProvider = mockk<KomgaSettingsProvider>()

  private val proxy =
    KoboRawStoreProxy(
      koboOutboundRequestGuard = mockk(),
      komgaSettingsProvider = settingsProvider,
      objectMapper = ObjectMapper(),
    )

  @Test
  fun `disabled Store proxy returns an empty fallback without an HTTP request`() {
    every { settingsProvider.koboProxy } returns false

    // No servlet request is bound to the test thread. If proxyCurrentRequest
    // attempts to construct or send a Store request, this test fails offline.
    val result = proxy.proxyCurrentRequest()

    assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(result.body).isEqualTo(ObjectMapper().createObjectNode())
  }

  @Test
  fun `disabled Store proxy does not forward overridden paths or request bodies`() {
    every { settingsProvider.koboProxy } returns false

    val result =
      proxy.proxyCurrentRequest(
        body = "private request body".toByteArray(),
        overridePath = "/v1/products/books/remote-product-id",
      )

    assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(result.body).isEqualTo(ObjectMapper().createObjectNode())
  }
}
