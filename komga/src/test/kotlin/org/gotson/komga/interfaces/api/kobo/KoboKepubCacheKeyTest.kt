package org.gotson.komga.interfaces.api.kobo

import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.makeBook
import org.junit.jupiter.api.Test

class KoboKepubCacheKeyTest {
  @Test
  fun `a source with a different hash cannot reuse its earlier conversion even when the timestamp is unchanged`() {
    val original = makeBook("book", id = "book-1").copy(fileHash = "old-hash")
    val replaced = original.copy(fileHash = "new-hash")

    assertThat(original.koboKepubCacheKey()).isNotEqualTo(replaced.koboKepubCacheKey())
  }

  @Test
  fun `an unchanged source retains its conversion cache key`() {
    val book = makeBook("book", id = "book-1").copy(fileHash = "known-hash")

    assertThat(book.koboKepubCacheKey()).isEqualTo(book.copy().koboKepubCacheKey())
  }

  @Test
  fun `a book without a source hash cannot reuse a converted file`() {
    val book = makeBook("book", id = "book-1")

    assertThat(book.koboKepubCacheKey()).isNull()
  }
}
