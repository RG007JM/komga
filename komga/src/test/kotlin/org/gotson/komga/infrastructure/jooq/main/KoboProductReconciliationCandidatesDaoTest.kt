package org.gotson.komga.infrastructure.jooq.main

import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KoboProductMapping
import org.gotson.komga.domain.model.KoboProductMappingStatus
import org.gotson.komga.domain.model.makeBook
import org.gotson.komga.domain.model.makeLibrary
import org.gotson.komga.domain.model.makeSeries
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.KoboProductMappingRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class KoboProductReconciliationCandidatesDaoTest(
  @Autowired private val mappingRepository: KoboProductMappingRepository,
  @Autowired private val bookRepository: BookRepository,
  @Autowired private val seriesRepository: SeriesRepository,
  @Autowired private val libraryRepository: LibraryRepository,
) {
  private val library = makeLibrary(name = "Kobo reconciliation candidates test")
  private val createdBookIds = mutableListOf<String>()
  private val createdSeriesIds = mutableListOf<String>()
  private val olderThan = LocalDateTime.now().minusDays(30)

  @BeforeAll
  fun setup() {
    libraryRepository.insert(library)
  }

  @AfterEach
  fun cleanup() {
    createdBookIds.forEach(bookRepository::delete)
    createdBookIds.clear()
    createdSeriesIds.forEach(seriesRepository::delete)
    createdSeriesIds.clear()
  }

  @AfterAll
  fun tearDown() {
    libraryRepository.delete(library.id)
  }

  @Test
  fun `one-shot books do not fill the batch ahead of a due series book`() {
    val standaloneSeriesId = newSeries("Standalone", oneshot = true)
    repeat(9) { index ->
      newMappedBook(
        name = "Standalone $index",
        seriesId = standaloneSeriesId,
        seriesCheckedAt = olderThan.minusDays(20),
      )
    }
    val seriesId = newSeries("Spy x Family")
    val eligibleBookId =
      newMappedBook(
        name = "Spy x Family 16",
        seriesId = seriesId,
        seriesCheckedAt = olderThan.minusDays(1),
      )

    val candidates = mappingRepository.findSeriesReconciliationCandidates(olderThan, 1)

    assertThat(candidates.map { it.bookId }).containsExactly(eligibleBookId)
  }

  @Test
  fun `book flagged one-shot is excluded even when its series is not`() {
    val seriesId = newSeries("Mixed series")
    val oneShotId = newMappedBook("One-shot", seriesId, bookOneshot = true)
    val regularId = newMappedBook("Regular volume", seriesId)

    val candidates = mappingRepository.findSeriesReconciliationCandidates(olderThan, 8)

    assertThat(candidates.map { it.bookId }).contains(regularId).doesNotContain(oneShotId)
  }

  @Test
  fun `unmapped regular series remains eligible to establish a first Kobo series identity`() {
    val seriesId = newSeries("New series")
    val bookId = newMappedBook("Volume 1", seriesId, seriesCheckedAt = null)

    val candidates = mappingRepository.findSeriesReconciliationCandidates(olderThan, 1)

    assertThat(candidates.map { it.bookId }).containsExactly(bookId)
  }

  private fun newSeries(
    name: String,
    oneshot: Boolean = false,
  ): String {
    val series = makeSeries(name, libraryId = library.id).copy(oneshot = oneshot)
    seriesRepository.insert(series)
    createdSeriesIds.add(series.id)
    return series.id
  }

  private fun newMappedBook(
    name: String,
    seriesId: String,
    bookOneshot: Boolean = false,
    seriesCheckedAt: LocalDateTime? = olderThan.minusDays(1),
  ): String {
    val book =
      makeBook(name, libraryId = library.id, seriesId = seriesId).copy(oneshot = bookOneshot)
    bookRepository.insert(book)
    createdBookIds.add(book.id)
    mappingRepository.save(
      KoboProductMapping(
        bookId = book.id,
        isbn = "9781974755998",
        productId = "6852e6c6-96d3-4000-9851-c4fb8d2cbaa4",
        status = KoboProductMappingStatus.FOUND,
        checkedAt = olderThan.minusDays(30),
        seriesCheckedAt = seriesCheckedAt,
      ),
    )
    return book.id
  }
}
