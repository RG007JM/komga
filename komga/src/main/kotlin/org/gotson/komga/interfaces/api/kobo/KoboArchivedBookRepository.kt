package org.gotson.komga.interfaces.api.kobo

import org.springframework.stereotype.Repository
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.sql.DataSource

@Repository
class KoboArchivedBookRepository(
  private val dataSource: DataSource,
) {
  fun archive(
    userId: String,
    bookId: String,
  ) {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          INSERT INTO KOBO_ARCHIVED_BOOK (
            USER_ID,
            BOOK_ID,
            ARCHIVED_AT
          )
          VALUES (?, ?, ?)
          ON CONFLICT(USER_ID, BOOK_ID) DO UPDATE SET
            ARCHIVED_AT = excluded.ARCHIVED_AT
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, userId)
          statement.setString(2, bookId)
          statement.setString(3, ZonedDateTime.now(ZoneOffset.UTC).toString())
          statement.executeUpdate()
        }
    }
  }

  fun unarchive(
    userId: String,
    bookId: String,
  ): Boolean {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          DELETE FROM KOBO_ARCHIVED_BOOK
          WHERE USER_ID = ?
            AND BOOK_ID = ?
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, userId)
          statement.setString(2, bookId)
          return statement.executeUpdate() > 0
        }
    }
  }

  fun findArchivedBookIds(userId: String): Set<String> {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          SELECT BOOK_ID
          FROM KOBO_ARCHIVED_BOOK
          WHERE USER_ID = ?
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, userId)
          statement.executeQuery().use { resultSet ->
            return buildSet {
              while (resultSet.next()) add(resultSet.getString("BOOK_ID"))
            }
          }
        }
    }
  }

  fun isArchived(
    userId: String,
    bookId: String,
  ): Boolean {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          SELECT 1
          FROM KOBO_ARCHIVED_BOOK
          WHERE USER_ID = ?
            AND BOOK_ID = ?
          LIMIT 1
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, userId)
          statement.setString(2, bookId)
          statement.executeQuery().use { resultSet ->
            return resultSet.next()
          }
        }
    }
  }
}
