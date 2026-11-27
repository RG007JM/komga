package org.gotson.komga.downloads

import org.junit.jupiter.api.Test
import java.time.Instant

class AutoFetchPolicyTest {
  private val now = Instant.parse("2026-11-27T00:00:00Z")
  private val tracked = TrackedManga("entry", "fixture", "series", "Series", DownloadDestination("library", "Series"))

  private fun chapter(
    id: String,
    number: String,
    publishedAt: Instant? = null,
  ) = SourceChapter(id, number, id, publishedAt, "en", "https://example.org/$id")

  @Test
  fun `historical catalogue expansion is not a release`() {
    val baseline = AutoFetchPolicy.baseline(tracked, listOf(chapter("144", "144", now.minusSeconds(1))), "1", now)
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("5", "5", now.minusSeconds(1000)), now))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("6", "6"), now))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("republished", "6", now.plusSeconds(1)), now.plusSeconds(2)))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("malformed", "1e999999999"), now))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("old-unknown", "extra"), now))
    assertTrueValue(AutoFetchPolicy.eligible(baseline, chapter("145", "145", now.plusSeconds(1)), now.plusSeconds(2)))
    assertTrueValue(AutoFetchPolicy.eligible(baseline, chapter("144.5", "144.5"), now))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, chapter("future", "146", now.plusSeconds(5)), now))
  }

  @Test
  fun `explicit selection bypasses publication floor but not future availability`() {
    val old = chapter("1", "1", now.minusSeconds(100))
    val baseline = AutoFetchPolicy.baseline(tracked.copy(pendingInitialChapterIds = setOf(old.id)), listOf(old), "1", now)
    assertTrueValue(AutoFetchPolicy.eligible(baseline, old, now))
    assertFalseValue(AutoFetchPolicy.eligible(baseline, old.copy(publishedAt = now.plusSeconds(1)), now))
  }

  @Test
  fun `pending releases survive advancing chapter watermark`() {
    val baseline = AutoFetchPolicy.baseline(tracked, listOf(chapter("144", "144")), "1", now)
    val advanced = baseline.copy(chapterNumberFloor = "150", pendingAutoChapterIds = setOf("145"))
    assertTrueValue(AutoFetchPolicy.eligible(advanced, chapter("145", "145"), now))
    assertFalseValue(AutoFetchPolicy.eligible(advanced, chapter("146", "146"), now))
    assertTrueValue(AutoFetchPolicy.eligible(advanced, chapter("151", "151"), now))
  }

  @Test
  fun `failed batches do not starve less attempted chapters`() {
    val chapters = (1..25).map { chapter("$it", "$it") }
    var state = tracked.copy(pendingInitialChapterIds = chapters.map { it.id }.toSet())
    val first = chapters.sortedWith(AutoFetchPolicy.batchOrder(state)).take(AutoFetchPolicy.BATCH_LIMIT)
    first.forEach { state = state.copy(failedChapterAttempts = AutoFetchPolicy.recordFailure(state, it.id)) }
    val second = chapters.sortedWith(AutoFetchPolicy.batchOrder(state)).take(AutoFetchPolicy.BATCH_LIMIT)
    assertTrueValue(first.map { it.id }.intersect(second.map { it.id }.toSet()).isEmpty())
    second.forEach { state = state.copy(failedChapterAttempts = AutoFetchPolicy.recordFailure(state, it.id)) }
    assertTrueValue(chapters.sortedWith(AutoFetchPolicy.batchOrder(state)).take(5).all { it.id.toInt() > 20 })
  }

  @Test
  fun `empty catalogue cannot erase baseline`() {
    assertThrowsType(IllegalArgumentException::class.java) { AutoFetchPolicy.baseline(tracked, emptyList(), "1", now) }
  }
}
