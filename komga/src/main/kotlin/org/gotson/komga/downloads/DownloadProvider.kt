package org.gotson.komga.downloads

import java.io.InputStream
import java.time.Instant

/** Providers own source IDs, pagination, authentication, page ordering and access policies. */
interface DownloadProvider {
  val id: String
  val label: String

  fun search(query: String): List<SourceSeries>

  fun chapters(
    seriesId: String,
    filters: ChapterFilters,
  ): List<SourceChapter>

  fun pages(chapterId: String): List<SourcePage>

  fun nextRelease(seriesId: String): Instant? = null
}

data class SourceSeries(
  val id: String,
  val title: String,
)

data class SourceChapter(
  val id: String,
  val number: String,
  val title: String,
  val publishedAt: Instant?,
  val language: String,
  val url: String,
  val groups: Set<String> = emptySet(),
)

data class ChapterFilters(
  val language: String = "en",
  val includeGroups: Set<String> = emptySet(),
  val excludeGroups: Set<String> = emptySet(),
)

data class SourcePage(
  val filename: String,
  val open: () -> InputStream,
)
