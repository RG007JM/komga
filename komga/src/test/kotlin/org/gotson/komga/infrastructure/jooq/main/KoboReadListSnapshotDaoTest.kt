package org.gotson.komga.infrastructure.jooq.main

import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.KomgaUser
import org.gotson.komga.domain.persistence.KomgaUserRepository
import org.gotson.komga.domain.persistence.SyncPointRepository
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class KoboReadListSnapshotDaoTest(
  @Autowired private val syncPointRepository: SyncPointRepository,
  @Autowired private val userRepository: KomgaUserRepository,
  @Autowired private val dsl: DSLContext,
) {
  private val userA = KomgaUser("kobo-snapshot-a@example.org", "password")
  private val userB = KomgaUser("kobo-snapshot-b@example.org", "password")

  private val sp = Tables.SYNC_POINT
  private val sprl = Tables.SYNC_POINT_READLIST
  private val sprlb = Tables.SYNC_POINT_READLIST_BOOK

  private val syncPointIds = listOf("kobo-snapshot-a-1", "kobo-snapshot-a-2", "kobo-snapshot-b-1", "kobo-snapshot-a-other")

  @BeforeAll
  fun setup() {
    userRepository.insert(userA)
    userRepository.insert(userB)
  }

  @AfterEach
  fun cleanup() {
    dsl.deleteFrom(sprlb).where(sprlb.SYNC_POINT_ID.`in`(syncPointIds)).execute()
    dsl.deleteFrom(sprl).where(sprl.SYNC_POINT_ID.`in`(syncPointIds)).execute()
    dsl.deleteFrom(sp).where(sp.ID.`in`(syncPointIds)).execute()
  }

  @AfterAll
  fun tearDown() {
    userRepository.delete(userA.id)
    userRepository.delete(userB.id)
  }

  @Test
  fun `returns null when user has no snapshot for readlist`() {
    insertSnapshot("kobo-snapshot-b-1", userB.id, "readlist-1", "2026-09-01T11:00:00", "book-b")

    assertThat(syncPointRepository.findLatestReadListSnapshot(userA.id, "readlist-1")).isNull()
  }

  @Test
  fun `returns latest matching readlist snapshot scoped to user with ordered membership`() {
    insertSnapshot("kobo-snapshot-a-1", userA.id, "readlist-1", "2026-09-01T11:00:00", "old-book")
    insertSnapshot("kobo-snapshot-a-2", userA.id, "readlist-1", "2026-09-02T11:00:00", "book-z", "book-a")
    insertSnapshot("kobo-snapshot-b-1", userB.id, "readlist-1", "2026-09-03T11:00:00", "other-user-book")

    val result = syncPointRepository.findLatestReadListSnapshot(userA.id, "readlist-1")

    assertThat(result?.syncPointId).isEqualTo("kobo-snapshot-a-2")
    assertThat(result?.bookIds).containsExactly("book-a", "book-z")
  }

  @Test
  fun `ignores more recent syncpoint when it does not contain requested readlist`() {
    insertSnapshot("kobo-snapshot-a-1", userA.id, "readlist-1", "2026-09-01T11:00:00", "book-a")
    insertSnapshot("kobo-snapshot-a-other", userA.id, "readlist-2", "2026-09-02T11:00:00", "book-b")

    val result = syncPointRepository.findLatestReadListSnapshot(userA.id, "readlist-1")

    assertThat(result?.syncPointId).isEqualTo("kobo-snapshot-a-1")
    assertThat(result?.bookIds).containsExactly("book-a")
  }

  @Test
  fun `snapshot with no membership still exists`() {
    insertSnapshot("kobo-snapshot-a-1", userA.id, "readlist-1", "2026-09-01T11:00:00")

    val result = syncPointRepository.findLatestReadListSnapshot(userA.id, "readlist-1")

    assertThat(result?.syncPointId).isEqualTo("kobo-snapshot-a-1")
    assertThat(result?.bookIds).isEmpty()
  }

  private fun insertSnapshot(
    syncPointId: String,
    userId: String,
    readListId: String,
    createdDate: String,
    vararg bookIds: String,
  ) {
    val date = LocalDateTime.parse(createdDate)
    dsl
      .insertInto(sp, sp.ID, sp.USER_ID, sp.CREATED_DATE)
      .values(syncPointId, userId, date)
      .execute()
    dsl
      .insertInto(sprl, sprl.SYNC_POINT_ID, sprl.READLIST_ID, sprl.READLIST_NAME, sprl.READLIST_CREATED_DATE, sprl.READLIST_LAST_MODIFIED_DATE)
      .values(syncPointId, readListId, "Test ReadList", date, date)
      .execute()
    bookIds.forEach { bookId ->
      dsl
        .insertInto(sprlb, sprlb.SYNC_POINT_ID, sprlb.READLIST_ID, sprlb.BOOK_ID)
        .values(syncPointId, readListId, bookId)
        .execute()
    }
  }
}
