package org.gotson.komga.infrastructure.kobo

import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class KoboLocalBookLookup(
  private val dataSource: DataSource,
) {
  fun findUniqueBookIdByIsbn(
    isbn: String,
  ): String? {
    val bookIds =
      dataSource.connection.use { connection ->
        connection
          .prepareStatement(
            """
            SELECT BOOK_ID
            FROM BOOK_METADATA
            WHERE ISBN = ?
            LIMIT 2
            """.trimIndent(),
          ).use { statement ->
            statement.setString(1, isbn)

            statement.executeQuery().use { resultSet ->
              buildList {
                while (resultSet.next()) {
                  add(resultSet.getString("BOOK_ID"))
                }
              }
            }
          }
      }

    return bookIds.singleOrNull()
  }
}
