package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.sqlite.SQLiteDataSource
import java.nio.file.Path
import javax.sql.DataSource

class KoboKepubSizeCacheRepositoryTest {
  @TempDir
  lateinit var tempDir: Path

  private lateinit var dataSource: DataSource
  private lateinit var repository: KoboKepubSizeCacheRepository

  @BeforeEach
  fun setUp() {
    val database =
      tempDir
        .resolve("kepub-cache-test.sqlite")
        .toAbsolutePath()

    dataSource =
      SQLiteDataSource().apply {
        url = "jdbc:sqlite:$database"
      }

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
          CREATE TABLE KOBO_KEPUB_SIZE_CACHE (
            BOOK_ID TEXT NOT NULL PRIMARY KEY,
            SOURCE_FILE_HASH TEXT NOT NULL,
            KEPUB_FILE_SIZE INTEGER NOT NULL,

            FOREIGN KEY (BOOK_ID)
              REFERENCES BOOK(ID)
              ON DELETE CASCADE
          )
          """.trimIndent(),
        )
      }
    }

    repository =
      KoboKepubSizeCacheRepository(
        dataSource,
      )
  }

  @Test
  fun `upsert stores kepub size and source hash`() {
    insertBook("book-1")

    repository.upsert(
      bookId = "book-1",
      sourceFileHash = "hash-a",
      kepubFileSize = 12_345L,
    )

    assertThat(
      repository.findByBookId("book-1"),
    ).isEqualTo(
      KoboKepubSizeCacheRepository.Entry(
        sourceFileHash = "hash-a",
        kepubFileSize = 12_345L,
      ),
    )
  }

  @Test
  fun `upsert replaces stale hash and kepub size`() {
    insertBook("book-1")

    repository.upsert(
      bookId = "book-1",
      sourceFileHash = "hash-a",
      kepubFileSize = 12_345L,
    )

    repository.upsert(
      bookId = "book-1",
      sourceFileHash = "hash-b",
      kepubFileSize = 67_890L,
    )

    assertThat(
      repository.findByBookId("book-1"),
    ).isEqualTo(
      KoboKepubSizeCacheRepository.Entry(
        sourceFileHash = "hash-b",
        kepubFileSize = 67_890L,
      ),
    )
  }

  @Test
  fun `deleting book removes kepub size cache entry`() {
    insertBook("book-1")

    repository.upsert(
      bookId = "book-1",
      sourceFileHash = "hash-a",
      kepubFileSize = 12_345L,
    )

    assertThat(
      repository.findByBookId("book-1"),
    ).isNotNull()

    dataSource.connection.use { connection ->
      connection.createStatement().use { statement ->
        statement.execute("PRAGMA foreign_keys = ON")
      }

      connection
        .prepareStatement(
          "DELETE FROM BOOK WHERE ID = ?",
        ).use { statement ->
          statement.setString(
            1,
            "book-1",
          )
          statement.executeUpdate()
        }
    }

    assertThat(
      repository.findByBookId("book-1"),
    ).isNull()
  }

  private fun insertBook(
    bookId: String,
  ) {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          "INSERT INTO BOOK (ID) VALUES (?)",
        ).use { statement ->
          statement.setString(
            1,
            bookId,
          )
          statement.executeUpdate()
        }
    }
  }
}
