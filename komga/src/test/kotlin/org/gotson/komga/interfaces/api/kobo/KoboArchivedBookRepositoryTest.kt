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

  private fun insertBook(bookId: String) {
    dataSource.connection.use { connection ->
      connection.prepareStatement("INSERT INTO BOOK (ID) VALUES (?)").use { statement ->
        statement.setString(1, bookId)
        statement.executeUpdate()
      }
    }
  }
}
