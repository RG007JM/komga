package org.gotson.komga.infrastructure.jooq.main

import org.gotson.komga.domain.persistence.KoboExternalCollectionMemberRepository
import org.gotson.komga.infrastructure.jooq.SplitDslDaoBase
import org.gotson.komga.jooq.main.Tables
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class KoboExternalCollectionMemberDao(
  dslRW: DSLContext,
  @Qualifier("dslContextRO") dslRO: DSLContext,
) : SplitDslDaoBase(dslRW, dslRO),
  KoboExternalCollectionMemberRepository {
  private val members = Tables.KOBO_EXTERNAL_COLLECTION_MEMBER

  override fun add(
    userId: String,
    readListId: String,
    revisionIds: Collection<String>,
  ) {
    revisionIds.distinct().forEach { revisionId ->
      dslRW
        .insertInto(members, members.USER_ID, members.READLIST_ID, members.REVISION_ID)
        .values(userId, readListId, revisionId)
        .onDuplicateKeyIgnore()
        .execute()
    }
  }

  override fun remove(
    userId: String,
    readListId: String,
    revisionIds: Collection<String>,
  ) {
    if (revisionIds.isEmpty()) return
    dslRW
      .deleteFrom(members)
      .where(members.USER_ID.eq(userId))
      .and(members.READLIST_ID.eq(readListId))
      .and(members.REVISION_ID.`in`(revisionIds))
      .execute()
  }

  override fun findByReadListIds(
    userId: String,
    readListIds: Collection<String>,
  ): Map<String, List<String>> {
    if (readListIds.isEmpty()) return emptyMap()
    return dslRO
      .select(members.READLIST_ID, members.REVISION_ID)
      .from(members)
      .where(members.USER_ID.eq(userId))
      .and(members.READLIST_ID.`in`(readListIds))
      .orderBy(members.READLIST_ID, members.REVISION_ID)
      .fetch()
      .groupBy({ it.value1() }, { it.value2() })
  }
}
