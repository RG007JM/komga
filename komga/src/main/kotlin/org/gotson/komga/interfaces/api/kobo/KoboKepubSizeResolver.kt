package org.gotson.komga.interfaces.api.kobo

import org.gotson.komga.domain.persistence.KoboKepubSizeCacheRepository

internal object KoboKepubSizeResolver {
  fun advertisedSize(
    sourceFileSize: Long,
    currentFileHash: String?,
    cached: KoboKepubSizeCacheRepository.Entry?,
  ): Long =
    when {
      currentFileHash.isNullOrBlank() || cached == null ->
        sourceFileSize

      cached.sourceFileHash == currentFileHash ->
        cached.kepubFileSize

      sourceFileSize == cached.kepubFileSize ->
        sourceFileSize + 1L

      else ->
        sourceFileSize
    }
}
