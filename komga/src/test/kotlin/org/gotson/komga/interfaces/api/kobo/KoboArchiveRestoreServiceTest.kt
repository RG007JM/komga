package org.gotson.komga.interfaces.api.kobo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.gotson.komga.domain.persistence.KoboArchivedBookRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboArchiveRestoreServiceTest {
  private lateinit var archiveRepository: KoboArchivedBookRepository
  private lateinit var metadataRepository: BookMetadataRepository
  private lateinit var service: KoboArchiveRestoreService

  @BeforeEach
  fun setUp() {
    archiveRepository = mockk()
    metadataRepository = mockk()
    service = KoboArchiveRestoreService(archiveRepository, metadataRepository)
  }

  @Test
  fun `manual refresh restores only archived books and touches their metadata timestamp first`() {
    every { archiveRepository.findArchivedBookIds("user-a") } returns setOf("book-1")
    every { metadataRepository.touchLastModifiedDate("book-1") } returns true
    every { archiveRepository.unarchive("user-a", "book-1") } returns true

    service.restoreOnExplicitMetadataRefresh("user-a", listOf("book-1", "book-2", "book-1"))

    verify(exactly = 1) { metadataRepository.touchLastModifiedDate("book-1") }
    verify(exactly = 0) { metadataRepository.touchLastModifiedDate("book-2") }
    verify(exactly = 1) { archiveRepository.unarchive("user-a", "book-1") }
    verifyOrder {
      metadataRepository.touchLastModifiedDate("book-1")
      archiveRepository.unarchive("user-a", "book-1")
    }
  }

  @Test
  fun `manual refresh does nothing when the user has no archived books`() {
    every { archiveRepository.findArchivedBookIds("user-a") } returns emptySet()

    service.restoreOnExplicitMetadataRefresh("user-a", listOf("book-1"))

    verify(exactly = 0) { metadataRepository.touchLastModifiedDate(any()) }
    verify(exactly = 0) { archiveRepository.unarchive(any(), any()) }
  }

  @Test
  fun `missing book metadata cannot silently clear archive state`() {
    every { archiveRepository.findArchivedBookIds("user-a") } returns setOf("book-1")
    every { metadataRepository.touchLastModifiedDate("book-1") } returns false

    service.restoreOnExplicitMetadataRefresh("user-a", listOf("book-1"))

    verify(exactly = 0) { archiveRepository.unarchive(any(), any()) }
  }

  @Test
  fun `failed metadata touch leaves book archived`() {
    every { archiveRepository.findArchivedBookIds("user-a") } returns setOf("book-1")
    every { metadataRepository.touchLastModifiedDate("book-1") } throws IllegalStateException("database error")

    assertThatThrownBy { service.restoreOnExplicitMetadataRefresh("user-a", listOf("book-1")) }
      .isInstanceOf(IllegalStateException::class.java)

    verify(exactly = 0) { archiveRepository.unarchive(any(), any()) }
  }
}
