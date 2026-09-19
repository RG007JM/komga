package org.gotson.komga.domain.persistence

interface KoboKepubSizeCacheRepository {
  data class Entry(
    val sourceFileHash: String,
    val kepubFileSize: Long,
  )

  fun findByBookId(bookId: String): Entry?

  fun upsert(
    bookId: String,
    sourceFileHash: String,
    kepubFileSize: Long,
  )
}
