// KOBO_STORE_IDENTITY_FINAL_CLEANUP_V1
package org.gotson.komga.infrastructure.kobo

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KoboSeriesSearchQueryTranslator(
  private val koboSeriesIdResolver: KoboSeriesIdResolver,
) {
  private val komgaIdRegex =
    Regex(
      pattern = "^[0-9A-Z]{13}$",
      option = RegexOption.IGNORE_CASE,
    )

  /**
   * Nickel can issue /v1/products?q=<SeriesId>.
   *
   * Safe to call for any proxied query string:
   * - ordinary text q values do not hit the series resolver;
   * - TSID-looking values are still verified as actual local Komga series;
   * - numeric ISBN-like values stay unchanged unless they are truly a series ID.
   */
  fun translate(query: String?): String? {
    if (query.isNullOrBlank()) {
      return query
    }

    var changed = false

    val translated =
      query
        .split("&")
        .joinToString("&") { parameter ->
          val separator = parameter.indexOf('=')

          if (separator < 0) {
            return@joinToString parameter
          }

          val name = parameter.substring(0, separator)
          val value = parameter.substring(separator + 1)

          if (
            !name.equals("q", ignoreCase = true) ||
            value.isBlank() ||
            !komgaIdRegex.matches(value)
          ) {
            return@joinToString parameter
          }

          val koboSeriesId =
            koboSeriesIdResolver.resolveSeriesIdIfLocal(value)
              ?: return@joinToString parameter

          if (koboSeriesId == value) {
            return@joinToString parameter
          }

          changed = true

          logger.debug {
            "Translated Kobo product-search q from Komga SeriesId " +
              "$value to Kobo SeriesId $koboSeriesId"
          }

          "$name=$koboSeriesId"
        }

    return if (changed) translated else query
  }
}
