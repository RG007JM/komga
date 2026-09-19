package org.gotson.komga.infrastructure.kobo

import org.gotson.komga.domain.persistence.BookMetadataRepository
import org.springframework.stereotype.Component

@Component
class KoboLocalBookLookup(
  private val bookMetadataRepository: BookMetadataRepository,
) {
  fun findUniqueBookIdByIsbn(isbn: String): String? = bookMetadataRepository.findUniqueBookIdByIsbn(isbn)
}
