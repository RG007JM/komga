package org.gotson.komga.domain.persistence

import org.gotson.komga.domain.model.KoboProductMapping
import java.time.LocalDateTime

interface KoboProductMappingRepository {
  fun findByBookId(bookId: String): KoboProductMapping?

  fun findByIsbn(isbn: String): Collection<KoboProductMapping>

  fun findByProductId(productId: String): Collection<KoboProductMapping>

  fun findByBookIds(bookIds: Collection<String>): Collection<KoboProductMapping>

  /** A small, due-only batch; a cached FOUND ProductId is required. */
  fun findSeriesReconciliationCandidates(
    olderThan: LocalDateTime,
    limit: Int,
  ): Collection<KoboProductMapping>

  fun save(mapping: KoboProductMapping)

  fun deleteByBookId(bookId: String)
}
