package org.gotson.komga.domain.model

import java.time.LocalDateTime

data class KoboProductMapping(
  val bookId: String,
  val isbn: String,
  val productId: String?,
  val observedKoboSeriesId: String? = null,
  val status: KoboProductMappingStatus,
  val checkedAt: LocalDateTime,
  val seriesCheckedAt: LocalDateTime? = null,
  /** Last transient website lookup failure; does not count as a completed series check. */
  val seriesLookupFailedAt: LocalDateTime? = null,
)

enum class KoboProductMappingStatus {
  FOUND,
  NOT_FOUND,
}
