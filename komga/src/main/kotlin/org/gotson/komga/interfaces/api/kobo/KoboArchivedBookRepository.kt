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

  /** Persist a user-specific archive snapshot on the books already present in a new SyncPoint. */
  fun snapshotArchiveState(
    syncPointId: String,
    userId: String,
  ) {
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          UPDATE SYNC_POINT_BOOK
          SET KOBO_ARCHIVED = EXISTS (
            SELECT 1 FROM KOBO_ARCHIVED_BOOK a
            WHERE a.USER_ID = ? AND a.BOOK_ID = SYNC_POINT_BOOK.BOOK_ID
          )
          WHERE SYNC_POINT_ID = ?
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, userId)
          statement.setString(2, syncPointId)
          statement.executeUpdate()
        }
    }
  }

  data class ArchivePage(
    val bookIds: List<String>,
    val hasMore: Boolean,
  )

  /**
   * Compare frozen archive states, never the live archive table. This keeps pages stable
   * if a book is archived or restored while a Kobo sync is in progress.
   */
  fun findPendingArchiveChanges(
    fromSyncPointId: String,
    toSyncPointId: String,
    itemLimit: Int,
  ): ArchivePage {
    require(itemLimit >= 0)
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          SELECT current.BOOK_ID
          FROM SYNC_POINT_BOOK current
          LEFT JOIN SYNC_POINT_BOOK previous
            ON previous.SYNC_POINT_ID = ? AND previous.BOOK_ID = current.BOOK_ID
          WHERE current.SYNC_POINT_ID = ?
            AND current.KOBO_ARCHIVED = 1
            AND current.KOBO_ARCHIVE_SYNCED = 0
            AND (previous.BOOK_ID IS NULL OR previous.KOBO_ARCHIVED = 0)
          ORDER BY current.BOOK_ID
          LIMIT ?
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, fromSyncPointId)
          statement.setString(2, toSyncPointId)
          statement.setInt(3, itemLimit + 1)
          statement.executeQuery().use { results ->
            val ids =
              buildList {
                while (results.next()) add(results.getString("BOOK_ID"))
              }
            return ArchivePage(ids.take(itemLimit), ids.size > itemLimit)
          }
        }
    }
  }

  fun markArchiveChangesSynced(
    syncPointId: String,
    bookIds: Collection<String>,
  ) {
    if (bookIds.isEmpty()) return
    dataSource.connection.use { connection ->
      connection
        .prepareStatement(
          """
          UPDATE SYNC_POINT_BOOK
          SET KOBO_ARCHIVE_SYNCED = 1
          WHERE SYNC_POINT_ID = ? AND BOOK_ID = ?
          """.trimIndent(),
        ).use { statement ->
          bookIds.forEach { bookId ->
            statement.setString(1, syncPointId)
            statement.setString(2, bookId)
            statement.addBatch()
          }
          statement.executeBatch()
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
