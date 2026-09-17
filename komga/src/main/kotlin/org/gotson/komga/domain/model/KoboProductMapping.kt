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
)

enum class KoboProductMappingStatus {
  FOUND,
  NOT_FOUND,
}
