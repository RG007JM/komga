package org.gotson.komga.application.tasks

import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.Book
import org.gotson.komga.domain.model.BookMetadataPatchCapability
import org.gotson.komga.domain.persistence.BookRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.gotson.komga.domain.service.BookConverter
import org.gotson.komga.domain.service.BookImporter
import org.gotson.komga.domain.service.BookLifecycle
import org.gotson.komga.domain.service.BookMetadataLifecycle
import org.gotson.komga.domain.service.BookPageEditor
import org.gotson.komga.domain.service.KoboProductResolver
import org.gotson.komga.domain.service.LibraryContentLifecycle
import org.gotson.komga.domain.service.LocalArtworkLifecycle
import org.gotson.komga.domain.service.PageHashLifecycle
import org.gotson.komga.domain.service.SeriesLifecycle
import org.gotson.komga.domain.service.SeriesMetadataLifecycle
import org.gotson.komga.infrastructure.kobo.KoboSeriesIdResolver
import org.gotson.komga.infrastructure.search.SearchIndexLifecycle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ApplicationEventPublisher

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class KoboExplicitMetadataRefreshTaskTest {
  private val bookRepository = mockk<BookRepository>()
  private val metadataLifecycle = mockk<BookMetadataLifecycle>(relaxed = true)
  private val resolver = mockk<KoboProductResolver>()
  private val seriesIdResolver = mockk<KoboSeriesIdResolver>(relaxed = true)
  private val emitter = mockk<TaskEmitter>(relaxed = true)
  private val book =
    mockk<Book> {
      every { id } returns "book-1"
      every { seriesId } returns "series-1"
    }

  private fun handler() =
    TaskHandler(
      taskEmitter = emitter,
      libraryRepository = mockk<LibraryRepository>(),
      bookRepository = bookRepository,
      seriesRepository = mockk<SeriesRepository>(),
      libraryContentLifecycle = mockk<LibraryContentLifecycle>(),
      bookLifecycle = mockk<BookLifecycle>(),
      bookMetadataLifecycle = metadataLifecycle,
      seriesLifecycle = mockk<SeriesLifecycle>(),
      seriesMetadataLifecycle = mockk<SeriesMetadataLifecycle>(),
      localArtworkLifecycle = mockk<LocalArtworkLifecycle>(),
      bookImporter = mockk<BookImporter>(),
      bookConverter = mockk<BookConverter>(),
      bookPageEditor = mockk<BookPageEditor>(),
      searchIndexLifecycle = mockk<SearchIndexLifecycle>(),
      pageHashLifecycle = mockk<PageHashLifecycle>(),
      koboProductResolver = resolver,
      koboSeriesIdResolver = seriesIdResolver,
      meterRegistry = SimpleMeterRegistry(),
    )

  @Test
  fun `explicit refresh runs local metadata before Kobo identity and series consensus`() {
    every { bookRepository.findByIdOrNull("book-1") } returns book
    every { resolver.refreshKoboIdentity("book-1") } returns true
    val task =
      Task.RefreshBookMetadataAndKoboIdentity(
        "book-1",
        BookMetadataPatchCapability.entries.toSet(),
        HIGH_PRIORITY,
        "series-1",
      )

    handler().handleTask(task)

    verifyOrder {
      metadataLifecycle.refreshMetadata(book, task.capabilities)
      resolver.refreshKoboIdentity("book-1")
      seriesIdResolver.resolveSeriesId("series-1")
      emitter.refreshSeriesMetadata("series-1", priority = HIGH_PRIORITY - 1)
    }
  }

  @Test
  fun `automatic metadata refresh does not query Kobo`() {
    every { bookRepository.findByIdOrNull("book-1") } returns book
    val task = Task.RefreshBookMetadata("book-1", BookMetadataPatchCapability.entries.toSet(), HIGH_PRIORITY, "series-1")

    handler().handleTask(task)

    verify(exactly = 1) { metadataLifecycle.refreshMetadata(book, task.capabilities) }
    verify(exactly = 0) { resolver.refreshKoboIdentity(any()) }
    verify(exactly = 0) { seriesIdResolver.resolveSeriesId(any()) }
  }

  @Test
  fun `failed identity refresh does not update effective series identity`() {
    every { bookRepository.findByIdOrNull("book-1") } returns book
    every { resolver.refreshKoboIdentity("book-1") } returns false

    handler().handleTask(
      Task.RefreshBookMetadataAndKoboIdentity("book-1", BookMetadataPatchCapability.entries.toSet(), HIGH_PRIORITY, "series-1"),
    )

    verify(exactly = 1) { resolver.refreshKoboIdentity("book-1") }
    verify(exactly = 0) { seriesIdResolver.resolveSeriesId(any()) }
  }

  @Test
  fun `explicit task uses a distinct queue key from automatic metadata refresh`() {
    val caps = BookMetadataPatchCapability.entries.toSet()
    val automatic = Task.RefreshBookMetadata("book-1", caps, HIGH_PRIORITY, "series-1")
    val explicit = Task.RefreshBookMetadataAndKoboIdentity("book-1", caps, HIGH_PRIORITY, "series-1")

    assertThat(explicit.uniqueId).isNotEqualTo(automatic.uniqueId)
    assertThat(explicit.groupId).isEqualTo(automatic.groupId)
  }

  @Test
  fun `explicit emitter enqueues identity-aware task without altering ordinary refresh`() {
    val tasksRepository = mockk<TasksRepository>(relaxed = true)
    val testEmitter =
      TaskEmitter(
        bookRepository = mockk<BookRepository>(),
        bookConverter = mockk<BookConverter>(),
        tasksRepository = tasksRepository,
        eventPublisher = mockk<ApplicationEventPublisher>(relaxed = true),
      )

    testEmitter.refreshBookMetadataAndKoboIdentity(book, HIGH_PRIORITY)

    verify(exactly = 1) {
      tasksRepository.save(
        match<Task> {
          it is Task.RefreshBookMetadataAndKoboIdentity && it.bookId == "book-1" && it.priority == HIGH_PRIORITY
        },
      )
    }
  }
}
