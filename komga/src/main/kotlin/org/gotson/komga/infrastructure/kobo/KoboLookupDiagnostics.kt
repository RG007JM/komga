package org.gotson.komga.infrastructure.kobo

import org.springframework.stereotype.Component
import java.time.Instant

/** Bounded, memory-only administrative diagnostics. Never saves cookies, raw HTML or search URLs. */
data class KoboLookupAttempt(
  val isbn: String,
  val outcome: String,
  val matchedStorefront: String?,
  val checkedAt: Instant,
)

@Component
class KoboLookupDiagnostics {
  private val recent =
    object : LinkedHashMap<String, KoboLookupAttempt>(128, 0.75f, true) {
      override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, KoboLookupAttempt>?) = size > 256
    }

  @Synchronized
  fun record(
    isbn: String,
    outcome: String,
    matchedStorefront: String? = null,
  ) {
    recent[isbn] = KoboLookupAttempt(isbn, outcome, matchedStorefront, Instant.now())
  }

  @Synchronized
  fun latest(isbn: String?): KoboLookupAttempt? = isbn?.let(recent::get)
}
