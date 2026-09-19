package org.gotson.komga.interfaces.scheduler

import org.gotson.komga.infrastructure.kobo.KoboSeriesObservationReconciler
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/** Small background batches; never blocks Kobo device requests. */
@Profile("!test")
@Component
class KoboSeriesObservationScheduler(
  private val reconciler: KoboSeriesObservationReconciler,
) {
  @Scheduled(fixedDelay = 3_600_000, initialDelay = 60_000)
  fun reconcile() {
    reconciler.reconcileDueBatch()
  }
}
