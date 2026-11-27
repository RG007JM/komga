package org.gotson.komga.downloads

import java.math.BigDecimal
import java.time.Instant

/** A catalogue expansion is not a publication event. Undated releases require forward numbering. */
internal object AutoFetchPolicy {
  const val BATCH_LIMIT = 10
  private val numberPattern = Regex("[0-9]{1,9}(\\.[0-9]{1,6})?")

  fun number(value: String): BigDecimal? = value.takeIf { numberPattern.matches(it) }?.toBigDecimalOrNull()

  fun highestNumber(
    chapters: List<SourceChapter>,
    now: Instant,
  ): BigDecimal? = chapters.filter { it.publishedAt?.isAfter(now) != true }.mapNotNull { number(it.number) }.maxOrNull()

  fun baseline(
    tracked: TrackedManga,
    chapters: List<SourceChapter>,
    version: String,
    now: Instant,
  ): TrackedManga {
    require(chapters.isNotEmpty()) { "Cannot establish an auto-fetch baseline from an empty catalogue" }
    val published = chapters.filter { it.publishedAt?.isAfter(now) != true }
    return tracked.copy(
      seen = tracked.seen + published.map { it.id },
      catalogueVersion = version,
      publicationFloor = now,
      chapterNumberFloor = highestNumber(published, now)?.toPlainString(),
      pendingAutoChapterIds = emptySet(),
      failedChapterAttempts = emptyMap(),
    )
  }

  fun eligible(
    tracked: TrackedManga,
    chapter: SourceChapter,
    now: Instant,
  ): Boolean {
    if (chapter.publishedAt?.isAfter(now) == true) return false
    if (chapter.id in tracked.pendingInitialChapterIds) return true
    if (chapter.id in tracked.seen) return false
    val floor = tracked.publicationFloor ?: return false
    if (chapter.id in tracked.pendingAutoChapterIds) return true
    val number = number(chapter.number)
    val numberFloor = tracked.chapterNumberFloor?.let(::number)
    // Republished chapters can have a new availability timestamp. Do not backfill old numbers.
    if (number != null && numberFloor != null && number <= numberFloor) return false
    chapter.publishedAt?.let { return it.isAfter(floor) }
    return number != null && numberFloor != null && number > numberFloor
  }

  /** Prefer unattempted or less-attempted chapters so a failing batch cannot starve the rest. */
  fun batchOrder(tracked: TrackedManga): Comparator<SourceChapter> =
    compareBy<SourceChapter> { tracked.failedChapterAttempts[it.id] ?: 0 }
      .thenBy { it.id !in tracked.pendingInitialChapterIds }
      .thenBy { it.publishedAt }
      .thenBy { number(it.number) }
      .thenBy { it.id }

  fun recordFailure(
    tracked: TrackedManga,
    chapterId: String,
  ): Map<String, Int> = tracked.failedChapterAttempts + (chapterId to ((tracked.failedChapterAttempts[chapterId] ?: 0).coerceIn(0, Int.MAX_VALUE - 1) + 1))

  fun historical(
    tracked: TrackedManga,
    chapters: List<SourceChapter>,
    now: Instant,
  ): Set<String> =
    chapters
      .filter { it.publishedAt?.isAfter(now) != true && !eligible(tracked, it, now) }
      .map { it.id }
      .toSet()
}
