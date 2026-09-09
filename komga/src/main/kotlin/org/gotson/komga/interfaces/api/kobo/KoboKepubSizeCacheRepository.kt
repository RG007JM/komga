package org.gotson.komga.interfaces.api.kobo

import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class KoboKepubSizeCacheRepository(
  private val dataSource: DataSource,
) {
  data class Entry(
    val sourceFileHash: String,
    val kepubFileSize: Long,
  )

  fun findByBookId(bookId: String): Entry? {
    dataSource.connection.use { connection ->
      connection.prepareStatement(
        """
        SELECT SOURCE_FILE_HASH, KEPUB_FILE_SIZE
        FROM KOBO_KEPUB_SIZE_CACHE
        WHERE BOOK_ID = ?
        """.trimIndent(),
      ).use { statement ->
        statement.setString(1, bookId)

        statement.executeQuery().use { resultSet ->
          return if (resultSet.next()) {
            Entry(
              sourceFileHash = resultSet.getString("SOURCE_FILE_HASH"),
              kepubFileSize = resultSet.getLong("KEPUB_FILE_SIZE"),
            )
          } else {
            null
          }
        }
      }
    }
  }

  fun upsert(
    bookId: String,
    sourceFileHash: String,
    kepubFileSize: Long,
  ) {
    dataSource.connection.use { connection ->
      connection.prepareStatement(
        """
        INSERT INTO KOBO_KEPUB_SIZE_CACHE (
          BOOK_ID,
          SOURCE_FILE_HASH,
          KEPUB_FILE_SIZE
        )
        VALUES (?, ?, ?)
        ON CONFLICT(BOOK_ID) DO UPDATE SET
          SOURCE_FILE_HASH = excluded.SOURCE_FILE_HASH,
          KEPUB_FILE_SIZE = excluded.KEPUB_FILE_SIZE
        """.trimIndent(),
      ).use { statement ->
        statement.setString(1, bookId)
        statement.setString(2, sourceFileHash)
        statement.setLong(3, kepubFileSize)
        statement.executeUpdate()
      }
    }
  }
}