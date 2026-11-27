// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import java.time.Duration
import java.time.Instant

/** Mangathr release-window offsets. Keep an announced release through the full window. */
internal object SourceReleasePolling {
  private val offsets = listOf(0L, 1L, 2L, 3L, 4L, 5L, 10L, 15L, 20L, 25L, 30L)

  fun expected(
    now: Instant,
    known: Instant?,
    previous: Instant?,
  ): Instant? {
    if (previous != null && !previous.isAfter(now) && now.isBefore(previous.plus(Duration.ofMinutes(30)))) return previous
    return known?.takeIf { now.isBefore(it.plus(Duration.ofMinutes(30))) }
      ?: previous?.takeIf { it.isAfter(now) }
  }

  fun next(
    now: Instant,
    release: Instant?,
  ): Instant {
    val normal = now.plus(Duration.ofMinutes(30))
    val scheduled = release?.let { time -> offsets.map { time.plus(Duration.ofMinutes(it)) }.firstOrNull { it.isAfter(now) } }
    return scheduled?.let { minOf(it, normal) } ?: normal
  }
}
