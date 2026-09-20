package org.gotson.komga.infrastructure.kobo

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.text.Normalizer
import java.util.Locale

/**
 * Japanese print ISBN -> Rakuten's explicitly paired digital edition -> Kobo Japan identity.
 * Public website HTML only. No StoreAPI, guessed Book IDs, or unverified title-only mapping.
 * The original ISBN remains the Komga mapping key; the opaque ebook ID is verified separately.
 */
internal class KoboJapaneseEditionBridge(
  private val fetch: (HttpUrl) -> KoboWebsitePage,
  private val parser: KoboProductPageParser,
  private val onVerified: (printIsbn: String, ebookBookId: String, productId: String, url: HttpUrl) -> Unit = { _, _, _, _ -> },
) {
  // Includes both Rakuten and Kobo top-level requests; redirects may require additional HTTP hops.
  private val requestCount = ThreadLocal.withInitial { 0 }

  private fun fetchBounded(url: HttpUrl): KoboWebsitePage {
    val count = requestCount.get()
    if (count >= MAX_REQUESTS) throw IllegalStateException("Japanese edition bridge request budget exhausted")
    requestCount.set(count + 1)
    return fetch(url)
  }

  /** An inconclusive Rakuten result must NEVER become a durable ISBN NOT_FOUND. */
  fun find(printIsbn: String): KoboProductLookupResult {
    val isbn =
      KoboLookupIsbn.normalize(printIsbn)
        ?: return failed("Japanese edition bridge requires a valid ISBN")
    requestCount.set(0)
    return try {
      val searchUrl =
        RAKUTEN_SEARCH
          .newBuilder()
          .addQueryParameter("g", "000")
          .addQueryParameter("sitem", isbn)
          .build()
      val search = fetchRakuten(searchUrl, "search")
      val searchDocument = Jsoup.parse(search.html, search.url.toString())
      val directPrint = safeRakuten(search.url.toString(), "rb")
      val pairs = if (directPrint == null) pairedSearchCards(searchDocument) else emptyList()
      val candidate: RakutenEdition? =
        if (pairs.size == 1) {
          val (printUrl, ebookUrl, titleVariants) = pairs.single()
          val ebook = readEbook(fetchRakuten(ebookUrl, "rk"))
          if (ebook != null && titleVariants.any { it == ebook.metadata.title }) {
            RakutenEdition(ebook.bookId, ebook.metadata, printUrl, ebookUrl, false)
          } else {
            null
          }
        } else {
          null
        }

      val edition =
        candidate ?: findVerifiedEdition(searchDocument, isbn, search, directPrint)
          ?: return failed("Rakuten print/ebook edition association could not be verified")
      verifyKoboJapan(isbn, edition.bookId)
    } catch (e: Exception) {
      KoboProductLookupResult.Failed(e)
    } finally {
      requestCount.remove()
    }
  }

  /** Fast card: one local print/ebook pair, one ebook URL, usable titles; does not assert primary print ISBN verification. */
  private fun pairedSearchCards(document: Document): List<SearchPair> {
    val found = linkedMapOf<HttpUrl, SearchPair>()
    for (anchor in document.select("a[href]")) {
      val print = safeRakuten(anchor.attr("abs:href"), "rb") ?: continue
      if (inUnrelated(anchor)) continue
      var ancestor: Element? = anchor.parent()
      repeat(4) {
        val wrapper = ancestor ?: return@repeat
        if (inUnrelated(wrapper) || wrapper.outerHtml().length > 16_000) return@repeat
        val printUrls = wrapper.select("a[href]").mapNotNull { safeRakuten(it.attr("abs:href"), "rb") }.distinct()
        if (printUrls.size > 1) return@repeat
        val ebooks =
          wrapper
            .select("a[href]")
            .mapNotNull { link ->
              val ebook = safeRakuten(link.attr("abs:href"), "rk") ?: return@mapNotNull null
              val context = (link.text() + " " + (link.parent()?.text().orEmpty())).take(220)
              ebook.takeIf { EBOOK_LABELS.any(context::contains) }
            }.distinct()
        if (ebooks.size == 1 && printUrls.singleOrNull() == print) {
          val titles =
            document
              .select("a[href]")
              .filter { safeRakuten(it.attr("abs:href"), "rb") == print }
              .map { normalizeTitle(it.text().ifBlank { it.selectFirst("img[alt]")?.attr("alt").orEmpty() }) }
              .filter { it.length >= 5 }
              .toSet()
          if (titles.isNotEmpty()) found[print] = SearchPair(print, ebooks.single(), titles)
        }
        ancestor = wrapper.parent()
      }
    }
    return found.values.toList()
  }

  private fun findVerifiedEdition(
    searchDocument: Document,
    isbn: String,
    preloadedSearch: KoboWebsitePage,
    directPrint: HttpUrl?,
  ): RakutenEdition? {
    val prints =
      if (directPrint != null)
        listOf(directPrint)
      else
        searchDocument
          .select("a[href]")
          .mapNotNull { safeRakuten(it.attr("abs:href"), "rb") }
          .distinct()
          .take(MAX_PRINT_CANDIDATES)
    val verified = mutableListOf<RakutenEdition>()
    for (printUrl in prints) {
      val paper = if (printUrl == directPrint) preloadedSearch else fetchRakuten(printUrl, "rb")
      val source = Jsoup.parse(paper.html, paper.url.toString())
      if (primaryPrintIsbn(source) != isbn) continue
      val sourceMetadata = metadata(source)
      val primaryLinks = editionLinks(source)
      // The original ISBN search card can contain the explicit counterpart link even
      // when the independently verified print product page omits it.
      val pairedLinks =
        if (primaryLinks.isEmpty() && directPrint == null) {
          pairedSearchCards(searchDocument).filter { it.printUrl == printUrl }.map { it.ebookUrl }.distinct()
        } else {
          emptyList()
        }
      val direct = primaryLinks.isNotEmpty() || pairedLinks.isNotEmpty()
      val links =
        when {
          primaryLinks.isNotEmpty() -> primaryLinks
          pairedLinks.isNotEmpty() -> pairedLinks
          sourceMetadata.title.isNotEmpty() -> catalogueLinks(sourceMetadata.title)
          else -> emptyList()
        }
      if (links.size > MAX_EBOOK_CANDIDATES) return null // Cannot exclude another plausible edition.
      for (ebookUrl in links) {
        val ebook = readEbook(fetchRakuten(ebookUrl, "rk")) ?: continue
        if (sourceMetadata.title.isNotEmpty() && sourceMetadata.title != ebook.metadata.title) continue
        if (sourceMetadata.publisher.isNotEmpty() && ebook.metadata.publisher.isNotEmpty() &&
          sourceMetadata.publisher != ebook.metadata.publisher
        )
          continue
        if (!direct && (
            sourceMetadata.publisher.isEmpty() || ebook.metadata.publisher.isEmpty() ||
              sourceMetadata.authors.isEmpty() || ebook.metadata.authors.isEmpty() ||
              sourceMetadata.authors.intersect(ebook.metadata.authors).isEmpty()
          )
        )
          continue
        verified += RakutenEdition(ebook.bookId, ebook.metadata, printUrl, ebookUrl, true)
      }
    }
    // Do not arbitrarily pick an ISBN search candidate, an ebook edition, or a volume.
    return verified.distinctBy { it.bookId }.singleOrNull()
  }

  private fun editionLinks(source: Document): List<HttpUrl> =
    source
      .select("a[href]")
      .filterNot(::inUnrelated)
      .mapNotNull { link ->
        val ebook = safeRakuten(link.attr("abs:href"), "rk") ?: return@mapNotNull null
        val context = (link.text() + " " + link.parent()?.text().orEmpty()).take(240)
        ebook.takeIf { EBOOK_LABELS.any(context::contains) }
      }.distinct()

  private fun catalogueLinks(title: String): List<HttpUrl> {
    val request =
      RAKUTEN_SEARCH
        .newBuilder()
        .addQueryParameter("g", "101")
        .addQueryParameter("sitem", title)
        .build()
    val listing = fetchRakuten(request, "search")
    val document = Jsoup.parse(listing.html, listing.url.toString())
    val candidates =
      document
        .select("a[href]")
        .filterNot(::inUnrelated)
        .mapNotNull { link ->
          val url = safeRakuten(link.attr("abs:href"), "rk") ?: return@mapNotNull null
          url to normalizeTitle(link.text().ifBlank { link.selectFirst("img[alt]")?.attr("alt").orEmpty() })
        }.distinctBy { it.first }
    val exact = candidates.filter { it.second == title }
    val selected = if (exact.isNotEmpty()) exact else candidates
    // Never accept a single candidate if other plausible editions were left unchecked.
    if (selected.size > MAX_EBOOK_CANDIDATES) return emptyList()
    return selected.map { it.first }
  }

  private fun readEbook(page: KoboWebsitePage): Ebook? {
    val document = Jsoup.parse(page.html, page.url.toString())
    val ids =
      document
        .select("meta[property='books:isbn']")
        .filterNot(::inUnrelated)
        .map { it.attr("content").trim() }
        .filter { BOOK_ID.matches(it) }
        .toSet()
    if (ids.size > 1) return null
    val id =
      ids.singleOrNull() ?: run {
        val primary = primaryInfo(document)
        BOOK_ID_LABEL
          .find(primary.text())
          ?.groupValues
          ?.get(1)
          ?.filter(Char::isDigit)
          ?: document.select("#productDetailedDescription li.productInfo").firstNotNullOfOrNull { row ->
            val label =
              row
                .selectFirst(".category")
                ?.text()
                ?.trim()
                ?.trim(':')
                ?.lowercase(Locale.ROOT)
            val value = row.selectFirst(".categoryValue")?.text()?.filter(Char::isDigit)
            value?.takeIf { label == "item number" && BOOK_ID.matches(it) }
          }
      }
    if (id == null || !BOOK_ID.matches(id)) return null
    val info = metadata(document)
    if (info.title.isEmpty()) return null
    return Ebook(id, info)
  }

  private fun primaryPrintIsbn(document: Document): String? {
    val ids = mutableSetOf<String>()
    document
      .select("[itemprop=isbn], meta[name=isbn], meta[property='book:isbn'], meta[property='books:isbn']")
      .filterNot(::inUnrelated)
      .forEach { node ->
        KoboLookupIsbn.normalize(node.attr("content").ifBlank { node.text() })?.let(ids::add)
      }
    document.select("script[type=application/ld+json]").forEach { script ->
      Regex("\"isbn\"\\s*:\\s*\"([\\d-]{10,18})\"").findAll(script.data()).forEach { match ->
        KoboLookupIsbn.normalize(match.groupValues[1])?.let(ids::add)
      }
    }
    val primary = primaryInfo(document)
    primary.select("dt").filter { it.text().trim().equals("ISBN", ignoreCase = true) }.forEach { label ->
      KoboLookupIsbn.normalize(label.nextElementSibling()?.text())?.let(ids::add)
    }
    PRINT_ISBN_LABEL.findAll(primary.text()).forEach { match ->
      KoboLookupIsbn.normalize(match.groupValues[1])?.let(ids::add)
    }
    return ids.singleOrNull()
  }

  private fun metadata(document: Document): Bibliography {
    val title = document.selectFirst("h1")?.text().orEmpty()
    val info = primaryInfo(document).text()
    val english =
      document
        .select("#productDetailedDescription li.productInfo")
        .mapNotNull { row ->
          val label =
            row
              .selectFirst(".category")
              ?.text()
              ?.trim()
              ?.trim(':')
              ?.lowercase(Locale.ROOT)
          val value = row.selectFirst(".categoryValue")?.text()
          if (label == null || value == null) null else label to value
        }.toMap()
    val publisher =
      INFO_PUBLISHER.find(info)?.groupValues?.get(1)
        ?: english["publishers"] ?: english["publisher"].orEmpty()
    val authorText =
      INFO_AUTHORS.find(info)?.groupValues?.get(1)
        ?: english["authors"] ?: english["author"].orEmpty()
    val authors =
      authorText
        .split(',', '，', '、', '/', '／')
        .map(::normalizeValue)
        .filter(String::isNotEmpty)
        .toSet()
    return Bibliography(normalizeTitle(title), normalizeValue(publisher), authors)
  }

  private fun primaryInfo(document: Document): Element {
    val details = document.selectFirst("#itemDetail, #item-detail, .item-detail, #itemInfo, .item-info, #productDetailedDescription")
    // Rakuten may place ISBN in #itemDetail and authors/publisher in a sibling 商品情報 panel.
    // Never widen the scope to the whole page merely to find one of those fields.
    val panel =
      document
        .select("section, div")
        .filter {
          it != details && it.outerHtml().length < 16_000 &&
            (it.ownText().trim() == "商品情報" || it.selectFirst("h2")?.text()?.trim() == "商品情報")
        }.minByOrNull { it.outerHtml().length }
    val scopes = listOfNotNull(details, panel).distinct()
    val combined =
      if (scopes.isEmpty())
        document.body().clone()
      else
        Jsoup.parseBodyFragment(scopes.joinToString(" ") { it.outerHtml() }).body()
    return combined.also { copy ->
      copy.select("script, style, noscript, .recommendations, .carousel, .related, [class*=recommend], [id*=recommend], [class*=ranking], [id*=ranking]").remove()
    }
  }

  private fun verifyKoboJapan(
    printIsbn: String,
    ebookId: String,
  ): KoboProductLookupResult {
    // The opaque ebook ID is used ONLY here, never sent into the ISBN-only global search.
    val searchUrl =
      KOBO_JAPAN_SEARCH
        .newBuilder()
        .addQueryParameter("query", ebookId)
        .addQueryParameter("pagenumber", "1")
        .build()
    val search = fetchBounded(searchUrl)
    val direct = productUrl(search.url)
    val candidates =
      if (direct != null) {
        listOf(direct)
      } else {
        if (!isJapaneseSearch(search.url)) return failed("Kobo Japan search redirected away from Japan")
        val document = Jsoup.parse(search.html, search.url.toString())
        if (document.selectFirst("[data-testid=no-result]") != null) return failed("Kobo Japan did not find the Rakuten ebook ID")
        val root = document.selectFirst("main") ?: document.body()
        root
          .select("a[href*=/ebook/]")
          .mapNotNull { link ->
            productUrl(search.url.resolve(link.attr("href")) ?: return@mapNotNull null)
          }.distinct()
      }
    if (candidates.isEmpty()) return failed("Kobo Japan search candidates missing or incomplete")
    var uncertain = candidates.size > MAX_EBOOK_CANDIDATES
    for (candidate in candidates.take(MAX_EBOOK_CANDIDATES)) {
      if (!isJapaneseProduct(candidate)) continue
      val detail = if (candidate == direct) search else fetchBounded(candidate)
      val actual = productUrl(detail.url)
      if (actual == null || !isJapaneseProduct(actual)) {
        uncertain = true
        continue
      }
      val identity = parser.parse(detail.html, actual.toString(), ebookId)
      if (identity != null) {
        onVerified(printIsbn, ebookId, identity.productId, actual)
        return KoboProductLookupResult.Found(identity.productId, identity.seriesId)
      }
      if (detail.html.contains(ebookId)) uncertain = true
    }
    return failed(if (uncertain) "Kobo Japan primary ebook identity was incomplete" else "Kobo Japan returned no verified ebook identity")
  }

  private fun fetchRakuten(
    url: HttpUrl,
    kind: String,
  ): KoboWebsitePage {
    val result = fetchBounded(url)
    if (result.url.scheme != "https" || result.url.host != "books.rakuten.co.jp" ||
      (
        kind == "search" && result.url.encodedPath.trimEnd('/') != "/search" &&
          safeRakuten(result.url.toString(), "rb") == null
      ) ||
      (kind != "search" && safeRakuten(result.url.toString(), kind) == null)
    ) {
      throw IllegalStateException("Rakuten request left the expected public product/search page")
    }
    return result
  }

  private fun safeRakuten(
    raw: String,
    kind: String,
  ): HttpUrl? {
    val url = runCatching { raw.toHttpUrl() }.getOrNull() ?: return null
    if (url.scheme != "https" || url.host != "books.rakuten.co.jp" ||
      url.username.isNotEmpty() || url.password.isNotEmpty()
    )
      return null
    val pattern = if (kind == "rb") PRINT_PATH else EBOOK_PATH
    if (!pattern.matches(url.encodedPath)) return null
    return url
      .newBuilder()
      .query(null)
      .fragment(null)
      .build()
  }

  private fun productUrl(url: HttpUrl): HttpUrl? {
    val path = url.pathSegments.dropLastWhile(String::isEmpty)
    if (url.scheme != "https" || url.host !in KOBO_HOSTS || path.size != 4 ||
      path[2] != "ebook" || path[3].isEmpty() || url.encodedPath.contains("%2f", ignoreCase = true)
    )
      return null
    return url
      .newBuilder()
      .query(null)
      .fragment(null)
      .build()
  }

  private fun isJapaneseProduct(url: HttpUrl): Boolean = url.pathSegments.take(3) == listOf("jp", "ja", "ebook")

  private fun isJapaneseSearch(url: HttpUrl): Boolean = url.scheme == "https" && url.host in KOBO_HOSTS && url.encodedPath.trimEnd('/') == "/jp/ja/search"

  private fun inUnrelated(node: Element): Boolean =
    (node.parents() + node).any { ancestor ->
      val marker = (ancestor.className() + " " + ancestor.id() + " " + ancestor.attr("data-testid")).lowercase()
      UNRELATED.any(marker::contains)
    }

  private fun normalizeTitle(raw: String): String {
    var title = Normalizer.normalize(raw, Normalizer.Form.NFKC).trim()
    repeat(4) {
      val next = title.replace(EDITION_SUFFIX, "").replace(IMPRINT_SUFFIX, "").trim()
      if (next == title) return@repeat
      title = next
    }
    return normalizeValue(title.replace(Regex("""\s*(?:\[?電子書籍版\]?|電子書籍)$"""), ""))
      .removePrefix("商品画像:")
      .removePrefix("商品画像：")
  }

  private fun normalizeValue(raw: String) =
    Normalizer
      .normalize(raw, Normalizer.Form.NFKC)
      .replace(Regex("\\s+"), "")
      .lowercase(Locale.ROOT)

  private fun failed(message: String) = KoboProductLookupResult.Failed(IllegalStateException(message))

  private data class SearchPair(
    val printUrl: HttpUrl,
    val ebookUrl: HttpUrl,
    val titleVariants: Set<String>,
  )

  private data class Bibliography(
    val title: String,
    val publisher: String,
    val authors: Set<String>,
  )

  private data class Ebook(
    val bookId: String,
    val metadata: Bibliography,
  )

  private data class RakutenEdition(
    val bookId: String,
    val metadata: Bibliography,
    val printUrl: HttpUrl,
    val ebookUrl: HttpUrl,
    val primaryPrintVerified: Boolean,
  )

  private companion object {
    val RAKUTEN_SEARCH = "https://books.rakuten.co.jp/search".toHttpUrl()
    val KOBO_JAPAN_SEARCH = "https://www.kobo.com/jp/ja/search".toHttpUrl()
    val PRINT_PATH = Regex("/rb/\\d+/?")
    val EBOOK_PATH = Regex("/rk/[A-Za-z0-9]+/?")
    val BOOK_ID = Regex("\\d{13}")
    val BOOK_ID_LABEL = Regex("""商品番号\s*[：:]\s*([\d\s-]{13,23})""")
    val PRINT_ISBN_LABEL = Regex("""ISBN\s*[：:]\s*([\d\s-]{13,23})""", RegexOption.IGNORE_CASE)
    val INFO_PUBLISHER = Regex("""出版社\s*[：:]\s*(\S+)""")
    val INFO_AUTHORS = Regex("""(?:著者／編集|著者/編集|著者)\s*[：:]\s*(.*?)(?=\s+(?:出版社|シリーズ名|レーベル|発売日|商品番号|ISBN)\s*[：:]|$)""")
    val EDITION_SUFFIX = Regex("""(?:\[電子書籍版]|\(電子書籍版\)|電子書籍版)$""")
    val IMPRINT_SUFFIX = Regex("""\([^()]{0,90}(?:コミックス|DIGITAL|文庫|新書|電子書籍)[^()]{0,90}\)$""")
    val EBOOK_LABELS = listOf("楽天Kobo", "電子書籍版", "電子版", "電子書籍")
    val UNRELATED = listOf("recommend", "carousel", "ranking", "related", "history")
    val KOBO_HOSTS = setOf("www.kobo.com", "kobo.com")
    const val MAX_REQUESTS = 12
    const val MAX_PRINT_CANDIDATES = 8
    const val MAX_EBOOK_CANDIDATES = 3
  }
}
