package org.gotson.komga.infrastructure.kobo

/** Snapshot of Kobo's country-selector routes. A website language is not the book language. */
internal object KoboWebsiteStorefronts {
  val all =
    (
      "ca/en us/en in/en za/en au/en hk/zh jp/ja my/en nz/en ph/en sg/en tw/zh th/en " +
        "at/de be/fr cy/en cz/cs dk/da ee/en fi/fi fr/fr de/de gr/en ie/en it/it lt/en " +
        "lu/fr mt/en nl/nl no/nb pl/pl pt/pt ro/ro sk/en si/en es/es se/sv ch/fr tr/tr " +
        "gb/en ar/es br/pt cl/es co/es mx/es pe/es ww/en"
    ).split(' ')

  private val byCountry = all.associateBy { it.substringBefore('/') }
  private val languageDefaults =
    mapOf(
      "cs" to "cz/cs",
      "da" to "dk/da",
      "de" to "de/de",
      "el" to "gr/en",
      "en" to "gb/en",
      "es" to "es/es",
      "et" to "ee/en",
      "fi" to "fi/fi",
      "fr" to "fr/fr",
      "it" to "it/it",
      "lt" to "lt/en",
      "nb" to "no/nb",
      "nl" to "nl/nl",
      "pl" to "pl/pl",
      "pt" to "pt/pt",
      "ro" to "ro/ro",
      "sk" to "sk/en",
      "sv" to "se/sv",
      "tr" to "tr/tr",
    )

  /** Region is read only from the *core* BCP-47 tag, never extensions or private use. */
  fun second(locale: String?): String {
    val tag =
      locale
        ?.trim()
        ?.replace('_', '-')
        ?.lowercase()
        .orEmpty()
    if (tag.isEmpty()) return "gb/en"
    if (tag == "en-gb-oed") return "gb/en"
    if (tag.startsWith("x-") || tag.startsWith("i-") || tag.startsWith("sgn-")) return "gb/en"
    val parts = tag.split('-')
    if (!parts[0].matches(Regex("[a-z]{2,8}"))) return "gb/en"
    val language = parts[0]
    if (language == "ja") return "jp/ja"
    var index = 1
    if (language.length in 2..3) {
      var extlangs = 0
      while (index < parts.size && extlangs < 3 && parts[index].matches(Regex("[a-z]{3}"))) {
        index++
        extlangs++
      }
    }
    if (index < parts.size && parts[index].matches(Regex("[a-z]{4}"))) index++ // script
    val region = parts.getOrNull(index)?.takeIf { it.matches(Regex("[a-z]{2}|[0-9]{3}")) }
    if (region != null) return byCountry[if (region == "uk") "gb" else region] ?: "gb/en"
    return languageDefaults[language] ?: "gb/en"
  }

  fun plan(locale: String?): List<String> {
    val japanese =
      locale
        ?.trim()
        ?.replace('_', '-')
        ?.lowercase()
        ?.let { it == "ja" || it.startsWith("ja-") } == true
    // Device traffic probes GB via the original one-request path first. The full
    // background/global policy retains its existing worldwide/locale storefront order.
    val first = listOf("ww/en", second(locale)).distinct()
    return if (japanese) first else first + all.filterNot { it in first }
  }
}
