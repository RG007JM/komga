package org.gotson.komga.infrastructure.kobo

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource

/**
 * Protects a Komga ReadList from stale Kobo tag-item commands.
 *
 * Kobo tag mutations do not carry the Komga SyncPoint token. The latest
 * ReadList snapshot is used as the membership state the device was expected
 * to know. Conflicts survive SyncPoint rollover inside one Kobo sync run and
 * are cleared when a new /v1/initialization starts for that device.
 */
@Component
class KoboReadListMutationGuard(
  private val dataSource: DataSource,
) {
  enum class Operation {
    ADD,
    REMOVE,
  }

  data class Decision(
    val acceptedBookIds: List<String>,
    val ignoredBookIds: List<String>,
    val baselineSyncPointId: String?,
  )

  private val tracker = KoboReadListMutationConflictTracker()

  fun beginDeviceSession(deviceId: String?) {
    tracker.beginDeviceSession(normalizeDeviceId(deviceId))
  }

  fun decide(
    userId: String,
    deviceId: String?,
    readListId: String,
    liveBookIds: Set<String>,
    requestedBookIds: Collection<String>,
    operation: Operation,
  ): Decision {
    val requested = requestedBookIds.distinct()
    if (requested.isEmpty()) {
      return Decision(emptyList(), emptyList(), null)
    }

    val snapshot =
      findLatestSnapshot(userId, readListId)
        ?: return Decision(requested, emptyList(), null)

    return tracker.decide(
      userId = userId,
      deviceId = normalizeDeviceId(deviceId),
      readListId = readListId,
      baselineSyncPointId = snapshot.syncPointId,
      baselineBookIds = snapshot.bookIds,
      liveBookIds = liveBookIds,
      requestedBookIds = requested,
      operation = operation,
    )
  }

  private fun normalizeDeviceId(deviceId: String?): String = deviceId?.takeIf { it.isNotBlank() } ?: UNKNOWN_DEVICE

  private fun findLatestSnapshot(
    userId: String,
    readListId: String,
  ): Snapshot? {
    dataSource.connection.use { connection ->
      val syncPointId =
        connection
          .prepareStatement(
            """
            SELECT sprl.SYNC_POINT_ID
            FROM SYNC_POINT_READLIST sprl
            JOIN SYNC_POINT sp ON sp.ID = sprl.SYNC_POINT_ID
            WHERE sprl.READLIST_ID = ?
              AND sp.USER_ID = ?
            ORDER BY sp.CREATED_DATE DESC
            LIMIT 1
            """.trimIndent(),
          ).use { statement ->
            statement.setString(1, readListId)
            statement.setString(2, userId)
            statement.executeQuery().use { resultSet ->
              if (!resultSet.next()) return null
              resultSet.getString("SYNC_POINT_ID")
            }
          }

      val bookIds = linkedSetOf<String>()
      connection
        .prepareStatement(
          """
          SELECT BOOK_ID
          FROM SYNC_POINT_READLIST_BOOK
          WHERE SYNC_POINT_ID = ?
            AND READLIST_ID = ?
          ORDER BY BOOK_ID
          """.trimIndent(),
        ).use { statement ->
          statement.setString(1, syncPointId)
          statement.setString(2, readListId)
          statement.executeQuery().use { resultSet ->
            while (resultSet.next()) bookIds += resultSet.getString("BOOK_ID")
          }
        }

      return Snapshot(syncPointId, bookIds)
    }
  }

  private data class Snapshot(
    val syncPointId: String,
    val bookIds: Set<String>,
  )

  private companion object {
    const val UNKNOWN_DEVICE = "<unknown-device>"
  }
}

/** Pure conflict state, separated so rollover behavior can be unit tested. */
internal class KoboReadListMutationConflictTracker {
  private data class Key(
    val userId: String,
    val deviceId: String,
    val readListId: String,
  )

  private data class State(
    var baselineSyncPointId: String,
    val expectedBookIds: MutableSet<String>,
    val conflictedBookIds: MutableSet<String> = mutableSetOf(),
  )

  private val states = ConcurrentHashMap<Key, State>()

  fun beginDeviceSession(deviceId: String) {
    states.keys.removeIf { it.deviceId == deviceId }
  }

  fun decide(
    userId: String,
    deviceId: String,
    readListId: String,
    baselineSyncPointId: String,
    baselineBookIds: Set<String>,
    liveBookIds: Set<String>,
    requestedBookIds: Collection<String>,
    operation: KoboReadListMutationGuard.Operation,
  ): KoboReadListMutationGuard.Decision {
    val key = Key(userId, deviceId, readListId)
    val state =
      states.computeIfAbsent(key) {
        State(baselineSyncPointId, baselineBookIds.toMutableSet())
      }

    synchronized(state) {
      // A new server snapshot can be created before Kobo has finished sending
      // queued mutations. Refresh expected membership but retain conflicts.
      if (state.baselineSyncPointId != baselineSyncPointId) {
        state.baselineSyncPointId = baselineSyncPointId
        state.expectedBookIds.clear()
        state.expectedBookIds.addAll(baselineBookIds)
      }

      val accepted = mutableListOf<String>()
      val ignored = mutableListOf<String>()

      for (bookId in requestedBookIds.distinct()) {
        if (bookId in state.conflictedBookIds) {
          ignored += bookId
          continue
        }

        val expectedPresent = bookId in state.expectedBookIds
        val livePresent = bookId in liveBookIds
        if (livePresent != expectedPresent) {
          state.conflictedBookIds += bookId
          ignored += bookId
          continue
        }

        accepted += bookId
        when (operation) {
          KoboReadListMutationGuard.Operation.ADD -> state.expectedBookIds += bookId
          KoboReadListMutationGuard.Operation.REMOVE -> state.expectedBookIds -= bookId
        }
      }

      return KoboReadListMutationGuard.Decision(
        acceptedBookIds = accepted,
        ignoredBookIds = ignored,
        baselineSyncPointId = baselineSyncPointId,
      )
    }
  }
}
