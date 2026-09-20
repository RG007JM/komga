package org.gotson.komga.interfaces.scheduler

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gotson.komga.infrastructure.configuration.KomgaSettingsProvider
import org.gotson.komga.infrastructure.kobo.KoboSeriesObservationReconciler
import org.junit.jupiter.api.Test

class KoboSeriesObservationSchedulerTest {
  @Test
  fun `scheduled reconciliation respects runtime Kobo Store Proxy setting`() {
    val settings = mockk<KomgaSettingsProvider>()
    val reconciler = mockk<KoboSeriesObservationReconciler>(relaxed = true)
    val scheduler = KoboSeriesObservationScheduler(reconciler, settings)

    every { settings.koboProxy } returns false
    scheduler.reconcile()
    verify(exactly = 0) { reconciler.reconcileDueBatch() }

    every { settings.koboProxy } returns true
    scheduler.reconcile()
    verify(exactly = 1) { reconciler.reconcileDueBatch() }
  }
}
