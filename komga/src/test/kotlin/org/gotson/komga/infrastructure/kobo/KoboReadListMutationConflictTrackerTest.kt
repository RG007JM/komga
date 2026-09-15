package org.gotson.komga.infrastructure.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KoboReadListMutationConflictTrackerTest {
  private lateinit var tracker: KoboReadListMutationConflictTracker

  @BeforeEach
  fun setUp() {
    tracker = KoboReadListMutationConflictTracker()
  }

  @Test
  fun `stale add remains blocked across SyncPoint rollover in same session`() {
    val first = decide("sp-1", setOf("book-e"), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD)
    assertThat(first.acceptedBookIds).isEmpty()
    assertThat(first.ignoredBookIds).containsExactly("book-e")

    val duplicate = decide("sp-2", emptySet(), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD)
    assertThat(duplicate.acceptedBookIds).isEmpty()
    assertThat(duplicate.ignoredBookIds).containsExactly("book-e")
  }

  @Test
  fun `new device session clears old conflict and permits later legitimate add`() {
    decide("sp-1", setOf("book-e"), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD)
    decide("sp-2", emptySet(), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD)
    tracker.beginDeviceSession(DEVICE)

    val result = decide("sp-2", emptySet(), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD)
    assertThat(result.acceptedBookIds).containsExactly("book-e")
    assertThat(result.ignoredBookIds).isEmpty()
  }

  @Test
  fun `conflict is per item so unrelated legitimate add still applies`() {
    val result =
      decide(
        "sp-1",
        setOf("stale-book"),
        emptySet(),
        listOf("stale-book", "new-book"),
        KoboReadListMutationGuard.Operation.ADD,
      )
    assertThat(result.ignoredBookIds).containsExactly("stale-book")
    assertThat(result.acceptedBookIds).containsExactly("new-book")
  }

  @Test
  fun `legitimate Kobo remove is accepted`() {
    val result = decide("sp-1", setOf("book-a"), setOf("book-a"), listOf("book-a"), KoboReadListMutationGuard.Operation.REMOVE)
    assertThat(result.acceptedBookIds).containsExactly("book-a")
    assertThat(result.ignoredBookIds).isEmpty()
  }

  @Test
  fun `accepted operation advances expected state in same session`() {
    val add = decide("sp-1", emptySet(), emptySet(), listOf("book-a"), KoboReadListMutationGuard.Operation.ADD)
    assertThat(add.acceptedBookIds).containsExactly("book-a")

    val remove = decide("sp-1", emptySet(), setOf("book-a"), listOf("book-a"), KoboReadListMutationGuard.Operation.REMOVE)
    assertThat(remove.acceptedBookIds).containsExactly("book-a")
    assertThat(remove.ignoredBookIds).isEmpty()
  }

  @Test
  fun `device sessions are isolated`() {
    decide("sp-1", setOf("book-e"), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD, "device-a")
    tracker.beginDeviceSession("device-b")
    val result = decide("sp-2", emptySet(), emptySet(), listOf("book-e"), KoboReadListMutationGuard.Operation.ADD, "device-a")
    assertThat(result.ignoredBookIds).containsExactly("book-e")
  }

  private fun decide(
    baseline: String,
    baselineBooks: Set<String>,
    liveBooks: Set<String>,
    requestedBooks: Collection<String>,
    operation: KoboReadListMutationGuard.Operation,
    device: String = DEVICE,
  ): KoboReadListMutationGuard.Decision =
    tracker.decide(
      userId = USER,
      deviceId = device,
      readListId = READLIST,
      baselineSyncPointId = baseline,
      baselineBookIds = baselineBooks,
      liveBookIds = liveBooks,
      requestedBookIds = requestedBooks,
      operation = operation,
    )

  private companion object {
    const val USER = "user"
    const val DEVICE = "device"
    const val READLIST = "readlist"
  }
}
