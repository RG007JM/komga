// KOBO_PERSISTENT_SERIES_MAPPING_V2
package org.gotson.komga.infrastructure.kobo

import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class KoboSeriesMappingRepository(
  private val dataSource: DataSource,
) {
  fun findKoboSeriesId(komgaSeriesId: String): String? =
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          "SELECT KOBO_SERIES_ID FROM KOBO_SERIES_MAPPING WHERE SERIES_ID = ?",
        ).use { statement ->
          statement.setString(1, komgaSeriesId)
          statement.executeQuery().use { resultSet ->
            if (resultSet.next()) {
              resultSet.getString("KOBO_SERIES_ID")
            } else {
              null
            }
          }
        }
    }

  fun upsertForBook(
    bookId: String,
    koboSeriesId: String,
  ): Boolean =
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          INSERT INTO KOBO_SERIES_MAPPING (SERIES_ID, KOBO_SERIES_ID)
          SELECT SERIES_ID, ?
          FROM BOOK
          WHERE ID = ?
            AND SERIES_ID IS NOT NULL
            AND SERIES_ID <> ''
          ON CONFLICT(SERIES_ID) DO UPDATE SET
            KOBO_SERIES_ID = excluded.KOBO_SERIES_ID
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, koboSeriesId)
          statement.setString(2, bookId)
          statement.executeUpdate() > 0
        }
    }
}
