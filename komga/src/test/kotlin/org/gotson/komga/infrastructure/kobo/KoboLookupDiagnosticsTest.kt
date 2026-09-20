package org.gotson.komga.infrastructure.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KoboLookupDiagnosticsTest {
  @Test
  fun `inconclusive is distinct from not found and diagnostics store only identifiers and outcomes`() {
    val diagnostics = KoboLookupDiagnostics()
    val isbn = "9781974702015"
    diagnostics.record(isbn, "INCONCLUSIVE")
    assertThat(diagnostics.latest(isbn)?.outcome).isEqualTo("INCONCLUSIVE")
    diagnostics.record(isbn, "FOUND", "it/it")
    val attempt = diagnostics.latest(isbn)!!
    assertThat(attempt.matchedStorefront).isEqualTo("it/it")
    assertThat(attempt.toString()).doesNotContain("https://", "cookie", "sId=")
  }

  @Test
  fun `in-memory diagnostics are bounded and never initiate website calls`() {
    val diagnostics = KoboLookupDiagnostics()
    for (i in 0..256) diagnostics.record("isbn-$i", "NOT_FOUND")
    assertThat(diagnostics.latest("isbn-0")).isNull()
    assertThat(diagnostics.latest("isbn-256")).isNotNull()
  }
}
