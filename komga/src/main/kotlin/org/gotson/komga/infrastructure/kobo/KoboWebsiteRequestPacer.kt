package org.gotson.komga.infrastructure.kobo

/** Spaces outgoing request starts on each website without locking a complete ISBN lookup.
 * Network calls and HTML parsing happen OUTSIDE this monitor. Redirect hops are handled by OkHttp.
 */
internal class KoboWebsiteRequestPacer(
  private val intervalMillis: Long = 1_500L,
  private val clock: () -> Long = System::nanoTime,
  private val pause: (Long) -> Unit = Thread::sleep,
) {
  private class HostSlot(
    var nextNanos: Long? = null,
  )

  private val slots = java.util.concurrent.ConcurrentHashMap<String, HostSlot>()

  fun awaitTurn(host: String) {
    val slot = slots.computeIfAbsent(host) { HostSlot() }
    synchronized(slot) {
      val now = clock()
      val delayNanos = ((slot.nextNanos ?: now) - now).coerceAtLeast(0L)
      if (delayNanos > 0L) pause((delayNanos + 999_999L) / 1_000_000L)
      slot.nextNanos = clock() + intervalMillis * 1_000_000L
    }
  }
}
