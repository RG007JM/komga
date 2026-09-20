package org.gotson.komga.interfaces.scheduler

import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.gotson.komga.infrastructure.kobo.KoboSeriesObservationReconciler
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/** Small background batches; never blocks Kobo device requests. */
@Profile("!test")
@Component
class KoboSeriesObservationScheduler(
  private val reconciler: KoboSeriesObservationReconciler,
  private val settingsProvider: KomgaSettingsProvider,
) {
  @Scheduled(fixedDelay = 3_600_000, initialDelay = 60_000)
  fun reconcile() {
    // This setting can change at runtime; do not capture its value when the bean is created.
    if (!settingsProvider.koboProxy) return
    reconciler.reconcileDueBatch()
  }
}
