package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.sqlite.SQLiteDataSource
import java.nio.file.Path
import java.sql.Connection

class KoboArchivedBookUserForeignKeyMigrationTest {
  @TempDir
  lateinit var tempDir: Path

  @Test
  fun `migration keeps valid archives removes orphaned users and cascades deletions`() {
    val database = tempDir.resolve("kobo-archive-migration.sqlite").toAbsolutePath()
    val dataSource = SQLiteDataSource().apply { url = "jdbc:sqlite:$database" }

    dataSource.connection.use { connection ->
      connection.createStatement().use { statement ->
        statement.execute("PRAGMA foreign_keys = ON")
        statement.execute("CREATE TABLE USER (ID TEXT NOT NULL PRIMARY KEY)")
        statement.execute("CREATE TABLE BOOK (ID TEXT NOT NULL PRIMARY KEY)")
        statement.execute("INSERT INTO USER (ID) VALUES ('user-a'), ('user-b')")
        statement.execute("INSERT INTO BOOK (ID) VALUES ('book-1'), ('book-2')")
      }

      executeMigration(connection, "V20260914150000__kobo_archived_book.sql")

      connection.createStatement().use { statement ->
        statement.execute("INSERT INTO KOBO_ARCHIVED_BOOK VALUES ('user-a', 'book-1', 'first')")
        statement.execute("INSERT INTO KOBO_ARCHIVED_BOOK VALUES ('user-b', 'book-2', 'second')")
        statement.execute("INSERT INTO KOBO_ARCHIVED_BOOK VALUES ('deleted-user', 'book-1', 'orphan')")
      }

      executeMigration(connection, "V20260919130100__kobo_archived_book_user_fk.sql")

      assertThat(archiveRows(connection)).containsExactly(
        "user-a:book-1:first",
        "user-b:book-2:second",
      )

      connection.createStatement().use { statement ->
        statement.executeQuery("PRAGMA foreign_key_check").use { rows ->
          assertThat(rows.next()).isFalse()
        }
        statement.executeQuery("PRAGMA foreign_key_list(KOBO_ARCHIVED_BOOK)").use { rows ->
          val references = mutableSetOf<String>()
          while (rows.next()) {
            assertThat(rows.getString("on_delete")).isEqualTo("CASCADE")
            references.add(rows.getString("table"))
          }
          assertThat(references).containsExactlyInAnyOrder("USER", "BOOK")
        }
      }

      assertThatThrownBy {
        connection.createStatement().use { statement ->
          statement.execute("INSERT INTO KOBO_ARCHIVED_BOOK VALUES ('missing-user', 'book-1', 'invalid')")
        }
      }.isInstanceOf(java.sql.SQLException::class.java)

      connection.createStatement().use { statement ->
        statement.execute("DELETE FROM USER WHERE ID = 'user-a'")
      }
      assertThat(archiveRows(connection)).containsExactly("user-b:book-2:second")

      connection.createStatement().use { statement ->
        statement.execute("DELETE FROM BOOK WHERE ID = 'book-2'")
      }
      assertThat(archiveRows(connection)).isEmpty()
    }
  }

  private fun executeMigration(
    connection: Connection,
    filename: String,
  ) {
    val sql =
      requireNotNull(javaClass.classLoader.getResourceAsStream("db/migration/sqlite/$filename")) {
        "Missing migration resource: $filename"
      }.bufferedReader().use { it.readText() }

    connection.createStatement().use { statement ->
      sql
        .split(';')
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .forEach { statement.execute(it) }
    }
  }

  private fun archiveRows(connection: Connection): List<String> =
    connection.createStatement().use { statement ->
      statement
        .executeQuery(
          "SELECT USER_ID, BOOK_ID, ARCHIVED_AT FROM KOBO_ARCHIVED_BOOK ORDER BY USER_ID",
        ).use { rows ->
          buildList {
            while (rows.next()) {
              add("${rows.getString("USER_ID")}:${rows.getString("BOOK_ID")}:${rows.getString("ARCHIVED_AT")}")
            }
          }
        }
    }
}
