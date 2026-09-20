package org.gotson.komga.infrastructure.kobo

import org.apache.commons.validator.routines.ISBNValidator
import java.util.Locale

/** ISBN-only boundary for the public Kobo website lookup (not Kobo's opaque Book IDs). */
internal object KoboLookupIsbn {
  // Use the same validator and ISBN-10 -> ISBN-13 conversion policy as Komga's metadata providers.
  private val validator = ISBNValidator(true)
  private val isbn13 = Regex("""97[89]\d{10}""")
  private val separators = Regex("""[\s-]""")

  fun normalize(value: String?): String? {
    val candidate = value?.trim()?.replace(separators, "")?.uppercase(Locale.ROOT) ?: return null
    if (candidate.isEmpty()) return null
    return validator.validate(candidate)?.takeIf(isbn13::matches)
  }
}
