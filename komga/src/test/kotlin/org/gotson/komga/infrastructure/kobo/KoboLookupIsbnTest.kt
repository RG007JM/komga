package org.gotson.komga.infrastructure.kobo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KoboLookupIsbnTest {
  @Test
  fun `accepts valid ISBN13 with hyphens or spaces`() {
    assertThat(KoboLookupIsbn.normalize("9781974702015")).isEqualTo("9781974702015")
    assertThat(KoboLookupIsbn.normalize("978-1-9747-0201-5")).isEqualTo("9781974702015")
    assertThat(KoboLookupIsbn.normalize(" 978 1 9747 0201 5 ")).isEqualTo("9781974702015")
    assertThat(KoboLookupIsbn.normalize("9798855405668")).isEqualTo("9798855405668")
  }

  @Test
  fun `converts valid ISBN10 including X checksum to ISBN13`() {
    assertThat(KoboLookupIsbn.normalize("0-306-40615-2")).isEqualTo("9780306406157")
    assertThat(KoboLookupIsbn.normalize("0-8044-2957-x")).isEqualTo("9780804429573")
  }

  @Test
  fun `rejects checksum errors opaque book IDs and malformed metadata`() {
    listOf(
      null,
      "",
      " ",
      "9781974702014",
      "9780000000001",
      "978197470201",
      "97819747020150",
      "4972000027092",
      "0-306-40615-3",
      "9781974702015extra",
      "isbn:9781974702015",
      "9781974702015/9781974701193",
    ).forEach { value ->
      assertThat(KoboLookupIsbn.normalize(value)).describedAs("input = $value").isNull()
    }
  }
}
