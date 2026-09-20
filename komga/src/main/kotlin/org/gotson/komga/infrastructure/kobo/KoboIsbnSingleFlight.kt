package org.gotson.komga.infrastructure.kobo

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/** Share an in-progress lookup, not its result indefinitely. Database cache rules remain authoritative. */
internal class KoboIsbnSingleFlight<T> {
  private val running = ConcurrentHashMap<String, CompletableFuture<T>>()

  fun run(isbn: String, lookup: () -> T): T {
    val mine = CompletableFuture<T>()
    val current = running.putIfAbsent(isbn, mine)
    if (current != null) return current.join()
    try {
      val result = lookup()
      mine.complete(result)
      return result
    } catch (failure: Throwable) {
      mine.completeExceptionally(failure)
      throw failure
    } finally {
      running.remove(isbn, mine)
    }
  }
}
