package org.gotson.komga.interfaces.api.kobo

import org.gotson.komga.domain.model.Book

/** A cached conversion is valid only for the same book and known source-file version. */
internal fun Book.koboKepubCacheKey(): String? =
  fileHash
    .takeIf { it.isNotBlank() }
    ?.let { hash -> "$id-$fileLastModified-$hash" }
