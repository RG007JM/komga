package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.sqlite.SQLiteDataSource
import java.nio.file.Path
import javax.sql.DataSource

class KoboArchivedBookRepositoryTest {
  @TempDir
  lateinit var tempDir: Path

  private lateinit var dataSource: DataSource
  private lateinit var repository: KoboArchivedBookRepository

  @BeforeEach
  fun setUp() {
    val database = tempDir.resolve("kobo-archive-test.sqlite").toAbsolutePath()
    dataSource = SQLiteDataSource().apply { url = "jdbc:sqlite:$database" }

    dataSource.connection.use { connection ->
      connection.createStatement().use { statement ->
        statement.execute("PRAGMA foreign_keys = ON")
        statement.execute(
          """
          CREATE TABLE BOOK (
            ID TEXT NOT NULL PRIMARY KEY
          )
          """.trimIndent(),
        )
        statement.execute(
          """
          CREATE TABLE SYNC_POINT_BOOK (
            SYNC_POINT_ID TEXT NOT NULL,
            BOOK_ID TEXT NOT NULL,
            KOBO_ARCHIVED BOOLEAN NOT NULL DEFAULT FALSE,
            KOBO_ARCHIVE_SYNCED BOOLEAN NOT NULL DEFAULT FALSE,
            PRIMARY KEY (SYNC_POINT_ID, BOOK_ID)
          )
          """.trimIndent(),
        )
        statement.execute(
          """
          CREATE TABLE KOBO_ARCHIVED_BOOK (
            USER_ID TEXT NOT NULL,
            BOOK_ID TEXT NOT NULL,
            ARCHIVED_AT TEXT NOT NULL,
            PRIMARY KEY (USER_ID, BOOK_ID),
            FOREIGN KEY (BOOK_ID)
              REFERENCES BOOK(ID)
              ON DELETE CASCADE
          )
          """.trimIndent(),
        )
      }
    }

    repository = KoboArchivedBookRepository(dataSource)
  }

  @Test
  fun `archive is per user`() {
    insertBook("book-1")
    repository.archive("user-a", "book-1")
    assertThat(repository.isArchived("user-a", "book-1")).isTrue()
    assertThat(repository.isArchived("user-b", "book-1")).isFalse()
    assertThat(repository.findArchivedBookIds("user-a")).containsExactly("book-1")
  }

  @Test
  fun `archive is idempotent`() {
    insertBook("book-1")
    repository.archive("user-a", "book-1")
    repository.archive("user-a", "book-1")
    assertThat(repository.findArchivedBookIds("user-a")).containsExactly("book-1")
  }

  @Test
  fun `unarchive only affects selected user`() {
    insertBook("book-1")
    repository.archive("user-a", "book-1")
    repository.archive("user-b", "book-1")
    assertThat(repository.unarchive("user-a", "book-1")).isTrue()
    assertThat(repository.isArchived("user-a", "book-1")).isFalse()
    assertThat(repository.isArchived("user-b", "book-1")).isTrue()
  }

  @Test
  fun `deleting book cascades archive rows`() {
    insertBook("book-1")
    repository.archive("user-a", "book-1")

    dataSource.connection.use { connection ->
      connection.createStatement().use { it.execute("PRAGMA foreign_keys = ON") }
      connection.prepareStatement("DELETE FROM BOOK WHERE ID = ?").use { statement ->
        statement.setString(1, "book-1")
        statement.executeUpdate()
      }
    }

    assertThat(repository.findArchivedBookIds("user-a")).isEmpty()
  }

  @Test
  fun `archive changes are delivered once and respect the remaining sync item budget`() {
    listOf("book-1", "book-2", "book-3").forEach { bookId ->
      insertBook(bookId)
      insertSyncPointBook("from", bookId)
    }
    repository.archive("user-a", "book-1")
    repository.archive("user-a", "book-2")
    repository.archive("user-b", "book-3")
    listOf("book-1", "book-2", "book-3").forEach { insertSyncPointBook("to", it) }
    repository.snapshotArchiveState("to", "user-a")

    val first = repository.findPendingArchiveChanges("from", "to", 1)
    assertThat(first.bookIds).containsExactly("book-1")
    assertThat(first.hasMore).isTrue()
    repository.markArchiveChangesSynced("to", first.bookIds)

    val second = repository.findPendingArchiveChanges("from", "to", 1)
    assertThat(second.bookIds).containsExactly("book-2")
    assertThat(second.hasMore).isFalse()
    repository.markArchiveChangesSynced("to", second.bookIds)
    assertThat(repository.findPendingArchiveChanges("from", "to", 1).bookIds).isEmpty()

    listOf("book-1", "book-2", "book-3").forEach { insertSyncPointBook("next", it) }
    repository.snapshotArchiveState("next", "user-a")
    assertThat(repository.findPendingArchiveChanges("to", "next", 5).bookIds).isEmpty()
  }

  @Test
  fun `zero remaining sync budget requests continuation without consuming archive entries`() {
    insertBook("book-1")
    insertSyncPointBook("from", "book-1")
    repository.archive("user-a", "book-1")
    insertSyncPointBook("to", "book-1")
    repository.snapshotArchiveState("to", "user-a")

    val noBudget = repository.findPendingArchiveChanges("from", "to", 0)
    assertThat(noBudget.bookIds).isEmpty()
    assertThat(noBudget.hasMore).isTrue()
    assertThat(repository.findPendingArchiveChanges("from", "to", 1).bookIds)
      .containsExactly("book-1")
  }

  @Test
  fun `archive during ongoing sync is picked up by the next snapshot`() {
    insertBook("book-1")
    insertSyncPointBook("from", "book-1")
    insertSyncPointBook("to", "book-1")
    repository.snapshotArchiveState("to", "user-a")
    repository.archive("user-a", "book-1")
    assertThat(repository.findPendingArchiveChanges("from", "to", 1).bookIds).isEmpty()

    insertSyncPointBook("next", "book-1")
    repository.snapshotArchiveState("next", "user-a")
    assertThat(repository.findPendingArchiveChanges("to", "next", 1).bookIds)
      .containsExactly("book-1")
  }

  @Test
  fun `explicit restoration does not produce another archive change`() {
    insertBook("book-1")
    insertSyncPointBook("from", "book-1")
    repository.archive("user-a", "book-1")
    insertSyncPointBook("archived", "book-1")
    repository.snapshotArchiveState("archived", "user-a")
    assertThat(repository.unarchive("user-a", "book-1")).isTrue()
    insertSyncPointBook("restored", "book-1")
    repository.snapshotArchiveState("restored", "user-a")
    assertThat(repository.findPendingArchiveChanges("archived", "restored", 1).bookIds).isEmpty()
  }

  private fun insertSyncPointBook(
    syncPointId: String,
    bookId: String,
  ) {
    dataSource.connection.use { connection ->
      connection.prepareStatement("INSERT INTO SYNC_POINT_BOOK (SYNC_POINT_ID, BOOK_ID) VALUES (?, ?)").use { statement ->
        statement.setString(1, syncPointId)
        statement.setString(2, bookId)
        statement.executeUpdate()
      }
    }
  }

  private fun insertBook(bookId: String) {
    dataSource.connection.use { connection ->
      connection.prepareStatement("INSERT INTO BOOK (ID) VALUES (?)").use { statement ->
        statement.setString(1, bookId)
        statement.executeUpdate()
      }
    }
  }
}
