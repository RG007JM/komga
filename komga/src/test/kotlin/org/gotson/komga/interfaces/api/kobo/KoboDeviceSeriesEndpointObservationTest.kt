package org.gotson.komga.interfaces.api.kobo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.infrastructure.kobo.KoboDeviceSeriesObserver
import org.gotson.komga.infrastructure.kobo.KoboRawStoreProxy
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.gotson.komga.infrastructure.kobo.KoboSeriesProductDiscovery
import org.gotson.komga.infrastructure.security.KomgaPrincipal
import org.gotson.komga.interfaces.api.ContentRestrictionChecker
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity

/** Does not start a server or call Store API; verifies the device-proxy observation hooks. */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class KoboDeviceSeriesEndpointObservationTest {
  private val proxy = mockk<KoboRawStoreProxy>()
  private val resolver = mockk<KoboProductResolver>()
  private val seriesIds = mockk<KoboSeriesIdResolver>()
  private val observer = mockk<KoboDeviceSeriesObserver>(relaxed = true)
  private val discovery = mockk<KoboSeriesProductDiscovery>()
  private val builder = mockk<KoboLocalStoreResponseBuilder>()
  private val restrictions = mockk<ContentRestrictionChecker>(relaxed = true)
  private val principal = mockk<KomgaPrincipal>()
  private val controller =
    KoboRemainingEndpointsController(proxy, resolver, seriesIds, observer, discovery, builder, restrictions)

  @Test
  fun `real ProductId device BookDetails proxies once and observes original JSON without altering response`() {
    val productId = "d3656ecd-c898-4b0e-a9a6-1c35ce88b461"
    val upstream: JsonNode = ObjectMapper().readTree("""{"Id":"$productId","SeriesId":"adc9b50c-41c3-5bb9-9cbe-9b4ebb12be7d"}""")
    every { builder.isLocalBook(productId) } returns false
    every { proxy.proxyCurrentRequest(null, null) } returns ResponseEntity.ok(upstream)

    val response = controller.getBookDetails(principal, productId)

    assertThat(response.body).isSameAs(upstream)
    verify(exactly = 1) { proxy.proxyCurrentRequest(null, null) }
    verify(exactly = 1) { observer.observeBookDetails(productId, upstream, null, any()) }
  }

  @Test
  fun `real Kobo Series UUID device endpoint proxies once and observes original JSON`() {
    val seriesId = "adc9b50c-41c3-5bb9-9cbe-9b4ebb12be7d"
    val upstream: JsonNode = ObjectMapper().readTree("""{"Items":[{"Book":{"Id":"d3656ecd-c898-4b0e-a9a6-1c35ce88b461"}}]}""")
    every { builder.localSeriesBookIds(seriesId) } returns emptyList()
    every { proxy.proxyCurrentRequest(null, null) } returns ResponseEntity.ok(upstream)

    val response = controller.getSeries(principal, seriesId, 100, 0)

    assertThat(response.body).isSameAs(upstream)
    verify(exactly = 1) { proxy.proxyCurrentRequest(null, null) }
    verify(exactly = 1) { observer.observeSeriesResponse(seriesId, upstream, any()) }
  }

  @Test
  fun `local BookDetails observes the untouched upstream SeriesId before overlay`() {
    val localBookId = "book-local"
    val productId = "d3656ecd-c898-4b0e-a9a6-1c35ce88b461"
    val upstream: JsonNode = ObjectMapper().readTree("""{"Id":"$productId","SeriesId":"adc9b50c-41c3-5bb9-9cbe-9b4ebb12be7d"}""")
    val local: JsonNode = ObjectMapper().readTree("""{"Id":"$localBookId","SeriesId":"local-series"}""")
    every { builder.isLocalBook(localBookId) } returns true
    every { principal.user } returns mockk(relaxed = true)
    every { resolver.resolveProductIdForDevice(localBookId) } returns productId
    every { proxy.isEnabled() } returns true
    every { proxy.proxyCurrentRequest(null, "/v1/products/books/$productId/") } returns ResponseEntity.ok(upstream)
    every { builder.buildBookDetails(localBookId, upstream) } returns local

    val response = controller.getBookDetails(principal, localBookId)

    assertThat(response.body).isSameAs(local)
    verify(exactly = 1) { observer.observeBookDetails(productId, upstream, localBookId, any()) }
  }

  @Test
  fun `local series response observes upstream Kobo UUID after existing ISBN discovery`() {
    val localSeriesId = "local-series"
    val koboSeriesId = "adc9b50c-41c3-5bb9-9cbe-9b4ebb12be7d"
    val upstream: JsonNode = ObjectMapper().readTree("""{"Items":[{"Book":{"Id":"d3656ecd-c898-4b0e-a9a6-1c35ce88b461","SeriesId":"$koboSeriesId"}}]}""")
    val local: JsonNode = ObjectMapper().readTree("""{"Items":[]}""")
    every { builder.localSeriesBookIds(localSeriesId) } returns listOf("book-local")
    every { principal.user } returns mockk(relaxed = true)
    every { seriesIds.resolveSeriesId(localSeriesId) } returns koboSeriesId
    every { proxy.isEnabled() } returns true
    every { proxy.proxyCurrentRequest(null, "/v1/products/books/series/$koboSeriesId") } returns ResponseEntity.ok(upstream)
    every { discovery.learn(listOf("book-local"), upstream) } returns Unit
    every { builder.buildSeries(localSeriesId, upstream, 100, 0) } returns local

    val response = controller.getSeries(principal, localSeriesId, 100, 0)

    assertThat(response.body).isSameAs(local)
    verify(exactly = 1) { discovery.learn(listOf("book-local"), upstream) }
    verify(exactly = 1) { observer.observeSeriesResponse(koboSeriesId, upstream, any()) }
  }
}
