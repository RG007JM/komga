package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KoboKepubSizeResolverTest {
  @Test
  fun `uses source size when no cache exists`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 10_000L,
        currentFileHash = "hash-a",
        cached = null,
      )

    assertThat(result)
      .isEqualTo(10_000L)
  }

  @Test
  fun `uses source size when current hash is null`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 10_000L,
        currentFileHash = null,
        cached =
          KoboKepubSizeCacheRepository.Entry(
            sourceFileHash = "hash-a",
            kepubFileSize = 12_000L,
          ),
      )

    assertThat(result)
      .isEqualTo(10_000L)
  }

  @Test
  fun `uses source size when current hash is blank`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 10_000L,
        currentFileHash = "",
        cached =
          KoboKepubSizeCacheRepository.Entry(
            sourceFileHash = "hash-a",
            kepubFileSize = 12_000L,
          ),
      )

    assertThat(result)
      .isEqualTo(10_000L)
  }

  @Test
  fun `uses cached kepub size when source hash is unchanged`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 10_000L,
        currentFileHash = "hash-a",
        cached =
          KoboKepubSizeCacheRepository.Entry(
            sourceFileHash = "hash-a",
            kepubFileSize = 12_345L,
          ),
      )

    assertThat(result)
      .isEqualTo(12_345L)
  }

  @Test
  fun `uses current source size when hash changed and size differs from cached kepub`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 10_000L,
        currentFileHash = "hash-b",
        cached =
          KoboKepubSizeCacheRepository.Entry(
            sourceFileHash = "hash-a",
            kepubFileSize = 12_345L,
          ),
      )

    assertThat(result)
      .isEqualTo(10_000L)

    assertThat(result)
      .isNotEqualTo(12_345L)
  }

  @Test
  fun `forces different advertised size when changed source equals old kepub size`() {
    val result =
      KoboKepubSizeResolver.advertisedSize(
        sourceFileSize = 12_345L,
        currentFileHash = "hash-b",
        cached =
          KoboKepubSizeCacheRepository.Entry(
            sourceFileHash = "hash-a",
            kepubFileSize = 12_345L,
          ),
      )

    assertThat(result)
      .isEqualTo(12_346L)

    assertThat(result)
      .isNotEqualTo(12_345L)
  }
}
