// Filename normalization adapted from Mangathr (MIT), see MANGATHR-LICENSE.txt.
package org.gotson.komga.downloads

internal object DownloadFileNames {
  fun stem(chapter: SourceChapter): String {
    val prefix = "Chapter ${chapter.number}"
    val title = chapter.title.trim()
    val numbered = Regex("(?i)^chapter\\s+#?${Regex.escape(chapter.number)}(?=\\s|[:–—-]|$)")
    return safeStem(
      when {
        title.isBlank() -> prefix
        numbered.containsMatchIn(title) -> title
        else -> "$prefix - $title"
      },
    )
  }

  fun safeStem(value: String): String {
    val cleaned =
      value
        .trim()
        .map { ch ->
          if (ch in "<>:\"/\\|?*") '-' else ch
        }.filterNot { it.isISOControl() }
        .joinToString("")
        .trim(' ', '.')
    // Bound UTF-8 bytes as well as avoiding invalid Windows characters.
    val result = StringBuilder()
    var bytes = 0
    for (cp in cleaned.codePoints().toArray()) {
      val count = String(Character.toChars(cp)).toByteArray(Charsets.UTF_8).size
      if (bytes + count > 180) break
      result.appendCodePoint(cp)
      bytes += count
    }
    return result.toString().trimEnd(' ', '.').ifBlank { "Chapter" }
  }

  fun converted(
    chapter: SourceChapter,
    outputName: String,
    part: Int,
    count: Int,
  ): String {
    val extension = listOf(".kepub.epub", ".epub", ".cbz", ".pdf").firstOrNull { outputName.endsWith(it, ignoreCase = true) }
    requireNotNull(extension) { "Unexpected KCC output extension" }
    val suffix = if (count > 1) " (part $part)" else ""
    return stem(chapter) + suffix + extension
  }
}
