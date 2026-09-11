package org.gotson.komga.domain.persistence

import org.gotson.komga.domain.model.KoboProductMapping

interface KoboProductMappingRepository {
  fun findByBookId(bookId: String): KoboProductMapping?

  fun findByIsbn(isbn: String): Collection<KoboProductMapping>

  fun findByProductId(productId: String): Collection<KoboProductMapping>

  fun save(mapping: KoboProductMapping)

  fun deleteByBookId(bookId: String)
}
