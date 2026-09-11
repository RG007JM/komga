package org.gotson.komga.infrastructure.kobo

sealed interface KoboProductLookupResult {
  data class Found(
    val productId: String,
  ) : KoboProductLookupResult

  data object NotFound : KoboProductLookupResult

  data class Failed(
    val cause: Throwable,
  ) : KoboProductLookupResult
}
