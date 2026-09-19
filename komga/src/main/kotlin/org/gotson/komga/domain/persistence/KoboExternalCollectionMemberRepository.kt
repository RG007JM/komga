package org.gotson.komga.domain.persistence

/** Opaque Kobo Store members. Never exposed as Komga ReadList books. */
interface KoboExternalCollectionMemberRepository {
  fun add(
    userId: String,
    readListId: String,
    revisionIds: Collection<String>,
  )

  fun remove(
    userId: String,
    readListId: String,
    revisionIds: Collection<String>,
  )

  fun findByReadListIds(
    userId: String,
    readListIds: Collection<String>,
  ): Map<String, List<String>>
}
