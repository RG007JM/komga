package org.gotson.komga.infrastructure.jooq.main

import org.assertj.core.api.Assertions.assertThat
import org.gotson.komga.domain.model.makeLibrary
import org.gotson.komga.domain.model.makeSeries
import org.gotson.komga.domain.persistence.KoboSeriesMappingRepository
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.domain.persistence.SeriesRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KoboSeriesMappingDaoTest(
  @Autowired private val mappingRepository: KoboSeriesMappingRepository,
  @Autowired private val seriesRepository: SeriesRepository,
  @Autowired private val libraryRepository: LibraryRepository,
) {
  private val library = makeLibrary(name = "Kobo series mapping test")
  private val createdSeriesIds = mutableListOf<String>()

  @BeforeAll
  fun setup() {
    libraryRepository.insert(library)
  }

  @AfterEach
  fun cleanup() {
    createdSeriesIds.forEach(seriesRepository::delete)
    createdSeriesIds.clear()
  }

  @AfterAll
  fun tearDown() {
    libraryRepository.delete(library.id)
  }

  @Test
  fun `unmapped series returns null`() {
    val seriesId = newSeries()

    assertThat(mappingRepository.findKoboSeriesId(seriesId)).isNull()
  }

  @Test
  fun `upsert persists a series mapping and updates an existing one`() {
    val seriesId = newSeries()
    val original = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    val replacement = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"

    assertThat(mappingRepository.upsert(seriesId, original)).isTrue()
    assertThat(mappingRepository.findKoboSeriesId(seriesId)).isEqualTo(original)

    assertThat(mappingRepository.upsert(seriesId, replacement)).isTrue()
    assertThat(mappingRepository.findKoboSeriesId(seriesId)).isEqualTo(replacement)
  }

  @Test
  fun `mappings are scoped to their Komga series`() {
    val firstId = newSeries()
    val secondId = newSeries()
    val koboId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"

    mappingRepository.upsert(firstId, koboId)

    assertThat(mappingRepository.findKoboSeriesId(firstId)).isEqualTo(koboId)
    assertThat(mappingRepository.findKoboSeriesId(secondId)).isNull()
  }

  @Test
  fun `deleting Komga series cascades to its Kobo mapping`() {
    val seriesId = newSeries()
    mappingRepository.upsert(seriesId, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")

    seriesRepository.delete(seriesId)
    createdSeriesIds.remove(seriesId)

    assertThat(mappingRepository.findKoboSeriesId(seriesId)).isNull()
  }

  private fun newSeries(): String {
    val series = makeSeries("Kobo mapping test", libraryId = library.id)
    seriesRepository.insert(series)
    createdSeriesIds.add(series.id)
    return series.id
  }
}
