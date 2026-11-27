// Adapted from Mangathr, Copyright (c) 2022 Luke Browning (MIT).
// See MANGATHR-LICENSE.txt in the packaged resources.
package org.gotson.komga.downloads

import java.time.Instant

/** Port of Mangathr's bounded wire parser for public catalog/title metadata. */
internal data class MangaPlusField(
  val number: Int,
  val wire: Int,
  val value: Long = 0,
  val bytes: ByteArray = byteArrayOf(),
)

internal fun mangaPlusFields(data: ByteArray): List<MangaPlusField> {
  var cursor = 0

  fun varint(): Long {
    var result = 0L
    repeat(10) { i ->
      require(cursor < data.size) { "Truncated protobuf varint" }
      val b = data[cursor++].toInt() and 255
      require(i != 9 || b <= 1) { "Protobuf varint overflow" }
      result = result or ((b and 127).toLong() shl (i * 7))
      if (b < 128) return result
    }
    error("Invalid protobuf varint")
  }

  fun bytes(count: Long): ByteArray {
    require(count >= 0 && count <= data.size - cursor) { "Truncated protobuf field" }
    return data.copyOfRange(cursor, cursor + count.toInt()).also { cursor += count.toInt() }
  }
  val fields = mutableListOf<MangaPlusField>()
  while (cursor < data.size) {
    require(fields.size < 100_000) { "Too many protobuf fields" }
    val tag = varint()
    require(tag in 8..0xffff_ffffL) { "Invalid protobuf tag" }
    val number = (tag ushr 3).toInt()
    val wire = (tag and 7).toInt()
    fields +=
      when (wire) {
        0 -> MangaPlusField(number, wire, value = varint())
        1 -> MangaPlusField(number, wire, bytes = bytes(8))
        2 -> MangaPlusField(number, wire, bytes = bytes(varint()))
        5 -> MangaPlusField(number, wire, bytes = bytes(4))
        else -> error("Unsupported protobuf wire type")
      }
  }
  return fields
}

internal fun List<MangaPlusField>.wireMessage(number: Int): List<MangaPlusField> = firstOrNull { it.number == number && it.wire == 2 }?.bytes?.let(::mangaPlusFields).orEmpty()

internal fun List<MangaPlusField>.wireMessages(number: Int): List<List<MangaPlusField>> = filter { it.number == number && it.wire == 2 }.map { mangaPlusFields(it.bytes) }

internal fun List<MangaPlusField>.wireValue(number: Int): Long? = firstOrNull { it.number == number && it.wire == 0 }?.value

internal fun List<MangaPlusField>.wireText(number: Int): String = firstOrNull { it.number == number && it.wire == 2 }?.bytes?.toString(Charsets.UTF_8).orEmpty()

internal fun mangaPlusDetail(
  data: ByteArray,
  expectedId: Long,
): List<MangaPlusField> {
  val detail = mangaPlusFields(data).wireMessage(1).wireMessage(8)
  require(detail.wireMessage(1).wireValue(1) == expectedId) { "MANGA Plus title detail ID mismatch or unavailable title" }
  return detail
}

internal fun mangaPlusRelease(detail: List<MangaPlusField>): Instant? = detail.wireValue(5)?.takeIf { it > 0 }?.let { Instant.ofEpochSecond(it) }

internal data class MangaPlusChapter(
  val id: Long,
  val name: String,
  val subtitle: String,
)

internal fun mangaPlusChapters(
  detail: List<MangaPlusField>,
  titleId: Long,
  response: List<MangaPlusField> = detail,
): List<MangaPlusChapter> {
  val found = linkedMapOf<Long, MangaPlusChapter>()
  var visited = 0

  fun walk(
    fields: List<MangaPlusField>,
    depth: Int,
  ) {
    if (depth > 14) return
    check(++visited <= 100_000) { "MANGA Plus chapter metadata traversal limit exceeded" }
    val id = fields.wireValue(2)
    val name = fields.wireText(3)
    if (fields.wireValue(1) == titleId && id != null && id > 0 && name.isNotBlank() &&
      fields.wireText(5).contains("/secure/title/$titleId/chapter/$id/chapter_thumbnail/")
    ) {
      found.putIfAbsent(id, MangaPlusChapter(id, name, fields.wireText(4)))
    }
    fields.filter { it.wire == 2 && it.bytes.isNotEmpty() }.forEach { field ->
      runCatching { mangaPlusFields(field.bytes) }.getOrNull()?.let { walk(it, depth + 1) }
    }
  }
  walk(response, 0)
  // The bundled Go schema defines field 28 as ChapterBlock, first/last lists 2 and 4.
  // Older payloads do not necessarily contain the V4 thumbnail marker.
  for (block in detail.filter { it.number == 28 && it.wire == 2 }.mapNotNull { runCatching { mangaPlusFields(it.bytes) }.getOrNull() }) {
    for (field in listOf(2, 4)) {
      for (chapter in block.filter { it.number == field && it.wire == 2 }.mapNotNull { runCatching { mangaPlusFields(it.bytes) }.getOrNull() }) {
        val id = chapter.wireValue(2) ?: continue
        val name = chapter.wireText(3)
        if (chapter.wireValue(1) != titleId || id !in 1..0xffff_ffffL || name.isBlank()) continue
        found.putIfAbsent(id, MangaPlusChapter(id, name, chapter.wireText(4)))
      }
    }
  }
  check(found.isNotEmpty()) { "No recognizable MANGA Plus chapter metadata (V4 and legacy chapter blocks)" }
  return found.values.toList()
}

internal fun mangaPlusChapterNumber(name: String): String? {
  val value = name.trim().removePrefix("#")
  return value.takeIf { it.matches(Regex("[0-9]+(?:\\.[0-9]+)?")) }
    ?: Regex("#([0-9]+(?:\\.[0-9]+)?(?:-[0-9]+)?)")
      .find(name)
      ?.groupValues
      ?.get(1)
      ?.replace('-', '.')
}

/** Match the Go group-title search and select the first edition for the chosen language. */
internal fun mangaPlusCatalog(
  data: ByteArray,
  query: String,
  language: Int,
): List<SourceSeries> {
  val response = mangaPlusFields(data)
  val success = response.wireMessage(1)
  val view = success.wireMessage(25)
  check(view.isNotEmpty()) {
    "MANGA Plus catalog unavailable (response fields: ${response.map { it.number }.distinct()}, success fields: ${success.map { it.number }.distinct()})"
  }
  return view
    .wireMessages(1)
    .mapNotNull { group ->
      if (!group.wireText(1).contains(query, true)) return@mapNotNull null
      val title = group.wireMessages(2).firstOrNull { (it.wireValue(7) ?: 0L) == language.toLong() } ?: return@mapNotNull null
      val id = title.wireValue(1) ?: return@mapNotNull null
      val name = title.wireText(2).trim()
      if (id !in 1..0xffff_ffffL || name.isBlank()) null else SourceSeries(id.toString(), name)
    }.distinctBy { it.id }
}

internal fun mangaPlusChapterSubtitle(subtitle: String): String = Regex(".+?\\d.? (\\w.+?)$").find(subtitle)?.groupValues?.get(1) ?: subtitle
