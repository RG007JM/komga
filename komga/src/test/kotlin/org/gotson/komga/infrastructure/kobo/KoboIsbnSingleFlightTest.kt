package org.gotson.komga.infrastructure.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class KoboIsbnSingleFlightTest {
  @Test
  fun `simultaneous requests for the same ISBN share one lookup`() {
    val flight = KoboIsbnSingleFlight<String>()
    val started = CountDownLatch(1)
    val release = CountDownLatch(1)
    val calls = AtomicInteger()
    val pool = Executors.newFixedThreadPool(2)
    try {
      val one =
        pool.submit<String> {
          flight.run("9781974702015") {
            calls.incrementAndGet()
            started.countDown()
            check(release.await(3, TimeUnit.SECONDS))
            "verified-product"
          }
        }
      assertThat(started.await(3, TimeUnit.SECONDS)).isTrue()
      val followerReady = CountDownLatch(1)
      val two =
        pool.submit<String> {
          followerReady.countDown()
          flight.run("9781974702015") {
            calls.incrementAndGet()
            "must-not-run"
          }
        }
      assertThat(followerReady.await(3, TimeUnit.SECONDS)).isTrue()
      // The leader is blocked until after the follower begins its run; allow thread scheduling.
      Thread.sleep(30)
      release.countDown()
      assertThat(one.get(3, TimeUnit.SECONDS)).isEqualTo("verified-product")
      assertThat(two.get(3, TimeUnit.SECONDS)).isEqualTo("verified-product")
      assertThat(calls.get()).isEqualTo(1)
      // Completed lookups are NOT cached indefinitely; only the repository owns durable caching.
      assertThat(flight.run("9781974702015") { "next-run" }).isEqualTo("next-run")
    } finally {
      release.countDown()
      pool.shutdownNow()
    }
  }

  @Test
  fun `failed leader does not poison later retries`() {
    val flight = KoboIsbnSingleFlight<String>()
    val error = runCatching { flight.run("9781974702015") { error("offline") } }.exceptionOrNull()
    assertThat(error).isInstanceOf(IllegalStateException::class.java)
    assertThat(flight.run("9781974702015") { "recovered" }).isEqualTo("recovered")
  }
}
