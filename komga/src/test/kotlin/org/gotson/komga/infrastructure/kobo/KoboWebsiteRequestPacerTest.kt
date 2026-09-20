package org.gotson.komga.infrastructure.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class KoboWebsiteRequestPacerTest {
  @Test
  fun `starts on the same host are spaced by 1500ms without delaying a separate host`() {
    var nanos = 10_000_000_000L
    val pauses = mutableListOf<Long>()
    val pacer = KoboWebsiteRequestPacer(1_500L, clock = { nanos }, pause = { ms ->
      pauses += ms
      nanos += ms * 1_000_000L
    })

    pacer.awaitTurn("www.kobo.com")
    pacer.awaitTurn("books.rakuten.co.jp")
    assertThat(pauses).isEmpty()
    pacer.awaitTurn("www.kobo.com")
    assertThat(pauses).containsExactly(1_500L)
    pacer.awaitTurn("books.rakuten.co.jp")
    assertThat(pauses).containsExactly(1_500L)
  }

  @Test
  fun `a waiting Kobo request does not block an independent Rakuten host`() {
    val koboWaiting = CountDownLatch(1)
    val releaseKobo = CountDownLatch(1)
    val rakutenFinished = CountDownLatch(1)
    val pacer = KoboWebsiteRequestPacer(1_500L, clock = { 10_000_000_000L }, pause = {
      koboWaiting.countDown()
      releaseKobo.await(2, TimeUnit.SECONDS)
    })
    pacer.awaitTurn("www.kobo.com")
    val kobo = Thread { pacer.awaitTurn("www.kobo.com") }
    val rakuten = Thread {
      pacer.awaitTurn("books.rakuten.co.jp")
      rakutenFinished.countDown()
    }
    try {
      kobo.start()
      assertThat(koboWaiting.await(1, TimeUnit.SECONDS)).isTrue()
      rakuten.start()
      assertThat(rakutenFinished.await(1, TimeUnit.SECONDS)).isTrue()
    } finally {
      releaseKobo.countDown()
      kobo.join(2_000)
      rakuten.join(2_000)
    }
  }

  @Test
  fun `negative nanoTime origin never makes the first request wait`() {
    var nanos = -10_000_000_000L
    val pauses = mutableListOf<Long>()
    val pacer = KoboWebsiteRequestPacer(1_500L, clock = { nanos }, pause = { ms ->
      pauses += ms
      nanos += ms * 1_000_000L
    })
    pacer.awaitTurn("www.kobo.com")
    assertThat(pauses).isEmpty()
    pacer.awaitTurn("www.kobo.com")
    assertThat(pauses).containsExactly(1_500L)
  }

  @Test
  fun `the pacing clock is monotonic and does not depend on wall clock changes`() {
    var nanos = 1_000_000_000L
    val pauses = mutableListOf<Long>()
    val pacer = KoboWebsiteRequestPacer(1_500L, clock = { nanos }, pause = { ms ->
      pauses += ms
      nanos += ms * 1_000_000L
    })
    pacer.awaitTurn("www.kobo.com")
    nanos += 1_000_000_000L
    pacer.awaitTurn("www.kobo.com")
    assertThat(pauses).containsExactly(500L)
  }
}
