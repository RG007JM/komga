# Kobo Store ISBN Lookup — implementation and regression reference

**Source of truth:** `komga-kobo-current.zip` supplied 2026-09-22. All links and line numbers below refer to files in **that extracted snapshot**, not necessarily to another release or later commit. Keep this file at `komga/docs/kobo/KOBO_STORE_ISBN_LOOKUP.md` within the repository. Relative source links below are written for that location. The code was inspected statically for this document; the existence of tests is not a claim that they were executed for this review.

**Scope:** public Kobo **website** ISBN → Kobo Product ID discovery in this Komga fork, Japanese print→digital edition bridging, persistent mapping, and its device integration. This is **not** a guide to authenticated Store API endpoints, an ISBN→Kobo Series ID guarantee, or a universal ISBN catalog service. The embedded test ISBNs and product IDs illustrate code paths; their continued live availability is not guaranteed.

## 1. Identity and outcome contract

| Value | Meaning | Do not conflate with |
| --- | --- | --- |
| Komga book ID (`bookId`) | Local book/revision identifier; lookup is associated with this book | Kobo Product ID |
| Valid normalized ISBN-13 (`isbn`) | Local edition's ISBN; search and persistent mapping key | An arbitrary 13-digit Kobo ebook **Book ID** |
| Kobo ebook Book ID (`ebookId`) | On some Japanese digital editions, a 13-digit product-page identifier that **does not pass ISBN validation** | The print ISBN or UUID Product ID |
| Kobo Product ID (`productId`) | Verified UUID of the *primary* Kobo ebook product; used by store proxy integration | Ebook URL slug, ISBN, Series ID |
| Kobo Series ID (`seriesId`) | Optional series identity found on the verified product page | Komga series ID |

[`KoboProductLookupResult.kt` L3–13](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductLookupResult.kt#L3-L13) defines exactly three outcomes:

```kotlin
Found(productId: String, seriesId: String? = null)
NotFound
Failed(cause: Throwable)
```

`Found` requires positive product evidence. `NotFound` is a completed, scoped search. `Failed` means the evidence was incomplete: network failure, challenge/rate limit, changed layout, truncated scan, missing primary identifier, etc. **Never write a durable negative for `Failed`**, or for a single storefront miss. See [`KoboProductResolver.kt` L148–193](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L148-L193) and [`KoboWebsiteIsbnSearch.kt` L149–164](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L149-L164).

## 2. Full execution flow

```text
Komga book ID
  └─ read current BookMetadata.isbn; validate/checksum/normalize
       ├─ invalid or absent → clear stale mapping; no website request
       ├─ current FOUND mapping → return UUID immediately
       ├─ compatible, unambiguous positive mapping for same ISBN → reuse
       ├─ fresh NOT_FOUND for exactly current storefront policy → no retry yet
       └─ no reusable mapping
            ├─ device: ONE original-format gb/en search response
            │    ├─ verified main book → save FOUND; return UUID
            │    └─ scoped miss → return null; queue full lookup off device thread
            └─ full: ww/en → book's chosen storefront → remaining storefronts
                 ├─ valid primary match → save FOUND (+ optional Series ID)
                 ├─ Japanese book, after jp/ja miss → verified Rakuten edition bridge
                 ├─ every applicable route conclusively empty → save NOT_FOUND
                 └─ failed/inconclusive route → Failed; do not cache absence
```

**Device entry:** [`KoboProductResolver.kt` L27–65](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L27-L65) provides `resolveProductIdForDevice(bookId)`: GB-only probe, then a single-worker daemon executor with a bounded queue of 32 tasks on a scoped miss. `pendingLookups` prevents repeatedly enqueueing the same book; a full search is **not** completed synchronously on the Kobo request thread. **Full entry:** [`KoboProductResolver.kt` L47–49, L69–193](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L47-L49) exposes `resolveProductId(bookId)`; it may run a long website search synchronously **on its own caller** (the device path deliberately queues it instead).

**Important nuance:** `resolveProductIdForDevice()` can return `null` **temporarily**, even for a cataloged book, while the background search runs. A later device request sees the persisted mapping. A queue rejection logs and permits retry on another device request; it does not prove absence ([`KoboProductResolver.kt` L51–65](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L51-L65)).

## 3. The main mechanisms, with exact source lines

### 3.1 ISBN-only boundary: never mistake a Kobo ebook identifier for an ISBN

[`KoboLookupIsbn.kt` L6–17](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboLookupIsbn.kt#L6-L17) removes spaces/hyphens, uppercases, validates the check digit with `ISBNValidator(true)`, converts valid ISBN-10 to ISBN-13, and requires a 13-digit `978…` or `979…` result. Invalid input produces `null` without HTTP. This boundary is applied at both the client and website search ([`KoboProductClient.kt` L72–99, L119–125](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L72-L99); [`KoboWebsiteIsbnSearch.kt` L20–28](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L20-L28)).

Example: `0-306-40615-2` becomes `9780306406157`; `4972000027092` is **not** a valid ISBN even though it looks like a 13-digit book number ([`KoboLookupIsbnTest.kt` L7–37](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboLookupIsbnTest.kt#L7-L37)). For Japanese editions, the latter kind of number is accepted **only after** it has been extracted as a verified corresponding ebook ID, and only by the specific Kobo Japan ebook verification step; it is never fed into the global ISBN search ([`KoboJapaneseEditionBridge.kt` L353–394](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L353-L394)).

### 3.2 Book-specific locale, not a global ISBN-only mapping

The book's owning series language is read with `bookRepository.getSeriesIdOrNull(bookId) → seriesMetadataRepository.findByIdOrNull(…) → language` ([`KoboProductClient.kt` L101–105](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L101-L105)). A plain ISBN-only entry point exists for callers lacking book context, but it uses the default route rather than being able to infer the correct region ([`KoboProductClient.kt` L86–88](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L86-L88)).

[`KoboWebsiteStorefronts.kt` L5–35](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteStorefronts.kt#L5-L35) defines **47** distinct country/language website routes and language defaults. [`L37–64`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteStorefronts.kt#L37-L64) parses the core locale, including a region overriding the simple language default; it deliberately does not treat a region hidden in BCP-47 extensions/private use as the book's country. The storefront language in a route is **not** necessarily the book language (e.g. `fr-CA` maps to `ca/en`).

[`KoboWebsiteStorefronts.kt` L66–77](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteStorefronts.kt#L66-L77) builds the route:

| Series language | Full lookup route | Device's immediate lookup |
| --- | --- | --- |
| `it` or `it-IT` | `ww/en → it/it →` remaining 45 routes, only if needed | `gb/en` only |
| `pt-BR` | `ww/en → br/pt →` remaining routes | `gb/en` only |
| `fr-CA` | `ww/en → ca/en →` remaining routes | `gb/en` only |
| `ja` or `ja-JP` | `ww/en → jp/ja → Rakuten print/ebook bridge after the Japan miss` | `gb/en` only |
| `null`, unknown, or simple `en` | `ww/en → gb/en →` remaining routes | `gb/en` only |

A positive match stops the route immediately. The Japanese route **does not** do a 47-store sweep or automatically try GB after Japan. Conversely, a *non-Japanese* route can eventually visit `jp/ja` in its broad sweep, but **does not** invoke the Rakuten Japanese-edition bridge ([`KoboWebsiteIsbnSearch.kt` L30–36, L149–159](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L30-L36)). The 47 routes are a maintained country-selector snapshot, not a promise that every route will always be reachable or expose every book.

### 3.3 Exact website search request: preserve the working query and redirect behavior

Constructed at [`KoboWebsiteIsbnSearch.kt` L36–60](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L36-L60):

```http
GET https://www.kobo.com/ww/en/search?query=9781974702015&fcmedia=Book&pageNumber=1
GET https://www.kobo.com/it/it/search?query=9781974702015&fcmedia=Book&pageNumber=1
```

The exact query keys are **`query`**, **`fcmedia=Book`**, and **`pageNumber`** (capital `N`), and `pageNumber` advances to `2` only when page 1 actually links to a subsequent page ([`KoboWebsiteIsbnSearch.kt` L135–147](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L135-L147)). The source explicitly records that altering the media filter and spelling together with the expanded routing was a regression ([`L44–48`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L44-L48)); the test asserts the original keys and absence of `pagenumber` ([`KoboWebsiteIsbnSearchTest.kt` L66–79](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L66-L79)). **Do not “simplify” these back to an unfiltered search or lowercase `pagenumber` without revalidating actual Kobo behavior.** Note the *Japanese bridge's* ebook-ID search uses lowercase `pagenumber` in its separate path; it is intentionally not the global ISBN request ([`KoboJapaneseEditionBridge.kt` L353–364](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L353-L364)).

If `/search` redirects directly to a product URL, the response's **already fetched HTML** is verified immediately—no redundant product-page GET ([`KoboWebsiteIsbnSearch.kt` L61–89](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L61-L89)). If the response is a normal results page, extract `main a[href*=/ebook/]`, visit candidates, and verify each product page's **primary** ISBN and identity ([`L90–130`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L90-L130)). Cross-store redirects cannot prove a match *in the requested storefront*; accepted product URLs must be HTTPS, use the Kobo hostname, match `/{country}/{language}/ebook/{slug}`, and be stripped of all query parameters (including `sId`/`ssId`) and fragments before reuse ([`L73–86, L166–192`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L166-L192)).

The search is bounded: at most **2 result pages per route**, **10 product candidates per results page**, and **300 top-level fetch calls total**, according to [`KoboWebsiteIsbnSearch.kt` L194–207](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L194-L207). Exceeding a budget becomes `Failed`, not `NotFound` ([`L37–39, L104–106`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L37-L39)). This is a bound on fetch invocations within the search loop, **not** a guarantee about HTTP redirect hops.

### 3.4 Verify the *primary book* before accepting a Product ID

The page parser at [`KoboProductPageParser.kt` L19–93](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductPageParser.kt#L19-L93) first proves that the main book's `Book ID`/ISBN equals the **expected** identifier. It looks in primary metadata, a cleaned main page, supported JSON-LD, and (when applicable) constrained Next Flight embedded `itemDetails`. It rejects zero or multiple competing main-book identifiers, or an identifier differing from the searched ISBN. It then extracts a **unique UUID** from the primary `data-track-info.productId`, `input#ratItemId`, or the constrained embedded primary object. A missing/ambiguous primary Product ID is **not** replaced with a UUID from a recommendation tile or series list ([`L24–93, L129–180`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductPageParser.kt#L129-L180)).

Series ID is separately collected from primary series links / embedded primary series data; it may legitimately be `null` when the book identity is nevertheless verified ([`KoboProductPageParser.kt` L83–93](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductPageParser.kt#L83-L93)). When a candidate appears to have missing primary identity, the search marks the route **inconclusive** rather than persisting absence ([`KoboWebsiteIsbnSearch.kt` L120–129](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L120-L129)).

### 3.5 Japanese print ISBN → Rakuten digital counterpart → Kobo Japan UUID

A Japanese print edition can have a valid ISBN that **does not equal** the Book ID displayed on the corresponding Kobo digital edition. Searching the print ISBN through the usual Kobo website routes is therefore not enough for every Japanese book. A guessed ebook number, a title-only fuzzy match, or a UUID scraped from any recommendation is unsafe.

The bounded bridge in [`KoboJapaneseEditionBridge.kt` L11–71](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L11-L71) is called **after** the `ww/en` and `jp/ja` ISBN attempts when the latter has no verified match ([`KoboWebsiteIsbnSearch.kt` L148–159](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L148-L159)). Its sequence is:

1. Search Rakuten Books by the **original print ISBN**: `https://books.rakuten.co.jp/search?g=000&sitem={PRINT_ISBN}` ([`KoboJapaneseEditionBridge.kt` L37–45](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L37-L45)).
2. Prefer a uniquely paired print (`/rb/.../`) and digital (`/rk/.../`) result card; verify the corresponding ebook's title. Otherwise visit bounded print candidates, verify the print page's **primary ISBN**, and inspect explicit ebook-edition links or a constrained digital-title catalogue search ([`L46–65, L73–194`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L73-L194)). Unlinked catalogue matches need matching title/volume, publisher, and author, not just the title ([`L165–184`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L165-L184)).
3. Extract the **primary ebook Book ID** from its Rakuten page, not related-book cards ([`L231–267`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L231-L267)); enforce exact/unique edition selection rather than arbitrarily choosing a volume ([`L152–194`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L152-L194)).
4. Search **only Kobo Japan** using the now-established digital Book ID: `https://www.kobo.com/jp/ja/search?query={EBOOK_ID}&pagenumber=1`; verify a Japanese `/ebook/` page against that **ebook ID**, then extract its **primary Kobo UUID Product ID** ([`L353–398`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L353-L398)).
5. Save the returned UUID **under the original Komga print ISBN**, not under the unvalidated ebook ID ([`KoboProductClient.kt` L62–69](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L62-L69); [`KoboProductResolver.kt` L148–162](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L148-L162)). The verified product page's *ebook ID* is retained **only in the in-process verified-URL cache** for later page/series rechecks ([`KoboProductClient.kt` L36–39, L62–69, L165–180](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L165-L180)).

The bridge is limited to **12 top-level fetches**, at most **8 print candidates**, **3 Kobo Japan ebook candidates**, and **8 explicitly linked Rakuten ebook candidates**, with ambiguity/truncation reported as `Failed` ([`KoboJapaneseEditionBridge.kt` L24–29, L523–527](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L523-L527)). Do not say every Japanese miss becomes `NotFound`: when Rakuten fails to verify an association, production returns `Failed` ([`L61–69`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L61-L69)).

### 3.6 Why HTTP transport is kept separate from storefront routing

[`KoboProductClient.kt` L41–59](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L41-L59) deliberately keeps the original working browser-impersonating Kobo client:

```kotlin
private val impersonator = ImpersonatorFactory.macChrome()
private val client = OkHttpClientFactory.create(impersonator).newHttpClient()
private val rakutenClient = OkHttpClient.Builder().build()
```

**No persistent cookie jar** is installed on either client. The earlier attempted `.newBuilder().cookieJar(cookies)` change introduced cookie/public-suffix parsing, which encountered `publicsuffixes.gz` loading trouble in the packaged application; that regression is documented in the current source comments, rather than being a claim that cookies can never work ([`KoboProductClient.kt` L41–44](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L41-L44)). Rakuten has its *own plain OkHttp transport* because the impersonating client's TLS stack had trouble handshaking with some Rakuten hosts; Kobo retains the impersonating client ([`L55–59`](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L55-L59)).

The actual GET supplies the impersonator's User-Agent plus browser-style navigation headers, chooses the transport by **host**, checks for 403/429 or Cloudflare-style challenges, requires successful **HTML** responses, and records the final redirected URL together with HTML ([`KoboProductClient.kt` L183–223](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L183-L223)). A blocked request is not converted into a storefront miss. A 404/410 can allow the search to advance to another storefront, but leaves that full route inconclusive so a final durable negative is prevented ([`KoboWebsiteIsbnSearch.kt` L50–60](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L50-L60)).

**Rate/concurrency controls:** at most **2 simultaneous top-level website fetches**, with request starts spaced **1,000 ms per host** in production ([`KoboProductClient.kt` L32–35, L183–186](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L32-L35)); [`KoboWebsiteRequestPacer.kt` L3–25](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteRequestPacer.kt#L3-L25) spaces starts without locking an entire long lookup and uses a monotonic clock. The pacer's default constructor interval is **1,500 ms**; production explicitly overrides it to 1,000 ms. Redirect hops are handled by OkHttp, not individually metered by this pacer.

### 3.7 Persistent results, negative-policy invalidation, and stale data

[`KoboProductResolver.kt` L73–147](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L73-L147) checks the current book's normalized ISBN before trusting an existing mapping, reuses a positive from another book **only** when all known positive mappings for that ISBN agree and the two books have the same storefront plan, and **never copies another book's negative**. A fresh negative is usable only if it belongs to the same book, same ISBN **and same current storefront policy** ([`L126–135`](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L126-L135)). A newly verified positive stores optional observed Series ID; a full, conclusive negative stores `lookupPolicy`; `Failed` does not overwrite the mapping ([`L148–193`](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L148-L193)). The negative TTL is **30 days** ([`L368–379`](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L368-L379)).

The schema/model/DAO carrying that policy are [`KoboProductMapping.kt` L5–21](../../src/main/kotlin/org/gotson/komga/domain/model/KoboProductMapping.kt#L5-L21), [`V20260921000100__kobo_negative_lookup_policy.sql` L1–3](../../src/flyway/resources/db/migration/sqlite/V20260921000100__kobo_negative_lookup_policy.sql#L1-L3), and [`KoboProductMappingDao.kt` L96–149](../../src/main/kotlin/org/gotson/komga/infrastructure/jooq/main/KoboProductMappingDao.kt#L96-L149). A legacy negative with `lookupPolicy=null` is not reused as authoritative after the routing upgrade. The resolver re-reads ISBN, mapping and lookup policy **after** a potentially slow request to discard a result that became stale during the HTTP work ([`KoboProductResolver.kt` L140–147, L245–253](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L245-L253)).

A **positive** mapping for the *unchanged* ISBN is deliberately stable on ordinary identity resolution and explicit metadata refresh; an unrelated metadata edit does not start another network lookup ([`KoboProductResolver.kt` L90–96, L202–214](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L202-L214)). If the ISBN actually changes, an old positive is not silently reused, and the new ISBN is resolved separately ([`L196–295`](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L196-L295)). A changed storefront policy *does* invalidate a previous negative, but the current code does **not** automatically invalidate a positive solely because the book language changes; this is a deliberate observable behavior, not a stronger promise.

`KoboIsbnSingleFlight` shares an **in-progress** lookup, not a permanent result ([`KoboIsbnSingleFlight.kt` L6–27](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboIsbnSingleFlight.kt#L6-L27)). The request key includes ISBN **plus full routing policy** (and the GB-only fast-path key is distinct), so Japanese and non-Japanese searches with identical ISBN digits cannot collapse into the wrong route ([`KoboProductClient.kt` L90–99, L119–129](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L119-L129)).

### 3.8 Optional Series ID refresh is *not* another untrusted ISBN lookup

Verified product URLs are kept in a bounded 256-entry **in-process** cache and stripped of URL query parameters ([`KoboProductClient.kt` L36–39, L62–69](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L36-L69)). [`KoboProductClient.kt` L162–181](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L162-L181) can revisit a known product page and [`KoboKnownProductPageCheck.kt` L11–35](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboKnownProductPageCheck.kt#L11-L35) verifies that both the **page's primary identifier** and previously established **Product ID** still agree. A vanished old URL (404/410) allows rediscovery; a page identity change or blocked access does not silently replace the existing UUID. For a bridged Japanese book, the expected primary identifier for this recheck is the ebook ID, not the print ISBN ([`KoboProductClient.kt` L172–175](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L172-L175)).

[`KoboSeriesObservationReconciler.kt` L47–100](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboSeriesObservationReconciler.kt#L47-L100) prefers that known-page recheck, falling back to book-context ISBN rediscovery if the URL is unavailable. It refuses to replace the established Product ID during Series ID refresh. A Kobo Series ID is evidence for series reconciliation; it does **not** change the Komga series to which a book belongs.

## 4. Why the other attempted mechanisms were insufficient

The distinctions below are **specific to this fork's observed regressions, current code and accompanying tests**. They do not assert that other approaches are categorically unusable with a different implementation or future Kobo website.

| Earlier approach / tempting shortcut | Failure mode or limitation | Mechanism now used; evidence |
| --- | --- | --- |
| **Search `ww/en` only** | Regional catalogs can omit a title that is visible in its local storefront. A worldwide miss alone cannot establish global absence. | Worldwide **first**, book-context locale **second**, then remaining routes for non-Japanese books; [`Storefronts` L66–77](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteStorefronts.kt#L66-L77), [`WebsiteIsbnSearchTest` L180–192](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L180-L192). |
| **Search GB only** | Preserves the old fast response but misses region-specific books. | Retain GB **solely** for the device fast path; queue the broader lookup after a scoped miss; [`Resolver` L27–65](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L27-L65), [`ResolverTest` L75–104](../../src/test/kotlin/org/gotson/komga/domain/service/KoboProductResolverTest.kt#L75-L104). |
| **Immediately sweep all storefronts on every device request** | Excessive device latency, duplicated work and more outbound requests. | Stop on verified match, bounded page/candidate/request limits, single-worker background queue, single-flight and caching; [`WebsiteIsbnSearch` L36–49, L104–106](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L36-L49), [`Resolver` L27–65](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L27-L65). |
| **Change `fcmedia=Book` or rename `pageNumber` to `pagenumber` during global-search refactor** | Alters the previously working website request shape; route expansion and HTTP-request changes cannot then be diagnosed independently. | Restore **both** exact keys on ISBN search; [`WebsiteIsbnSearch` L40–49](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearch.kt#L40-L49), [`WebsiteIsbnSearchTest` L66–79, L122–147](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L122-L147). |
| **Attach a persistent website cookie jar to the impersonating client** | In the attempted packaged build, OkHttp website cookie parsing required `publicsuffixes.gz` and failed; it was an introduced transport regression, not a storefront-selection issue. | Original `OkHttpClientFactory.create(impersonator).newHttpClient()` without persistent cookie jar; separate plain OkHttp for Rakuten TLS compatibility; [`ProductClient` L41–59](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L41-L59). |
| **Use the authenticated Store API / one shared Store API search as the ISBN resolver** | The device's Store API is a separate authenticated, storefront-scoped workflow; it does not supply this implementation's complete public-website ISBN → verified product-page evidence or Japanese print/digital pairing. An empty response from one API route is not a global negative. | Isolate public `www.kobo.com` + Rakuten website discovery from the authenticated device proxy; [`ProductClient` L24–26, L183–223](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L183-L223). Use the resolved UUID later for proxied Store API BookDetails; [`RemainingEndpointsController` L65–94](../../src/main/kotlin/org/gotson/komga/interfaces/api/kobo/KoboRemainingEndpointsController.kt#L65-L94). |
| **Search every Japanese storefront for the *print* ISBN, then declare not found** | An ebook may expose a different digital Book ID. That mismatch is not evidence that the digital product is absent. | Japan-specific, **verified** Rakuten print→ebook association followed by Kobo Japan ebook-ID→UUID verification; [`JapaneseEditionBridge` L31–69, L353–398](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L353-L398), [`JapaneseEditionBridgeTest` L57–95](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L57-L95). |
| **Treat any 13-digit Kobo book number as an ISBN or search a guessed ID globally** | Kobo digital identifiers may fail ISBN checksum/prefix rules; searching them under the wrong semantic contract can create bogus conclusions. | Validate actual ISBN at entry; use a Rakuten-confirmed digital ID **only** on the bounded Kobo Japan bridge; [`LookupIsbn` L13–17](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboLookupIsbn.kt#L13-L17), [`JapaneseEditionBridgeTest` L198–202](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L198-L202). |
| **Match Japanese digital editions by title alone or pick the first candidate** | Wrong volume/edition or multiple plausible ebooks may be selected. | Prefer an explicit print/ebook pair; otherwise primary-print-ISBN, title/volume, publisher/author and unique candidate verification; [`JapaneseEditionBridge` L73–194](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L73-L194), [`JapaneseEditionBridgeTest` L137–172](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L137-L172). |
| **Take the first UUID/Book ID anywhere in the HTML, including recommendations or `seriesItems`** | The UUID may refer to a related product, not the requested book. | Scope to **primary** identity, insist on exact expected book identifier and unique UUID; [`ProductPageParser` L24–93, L129–164](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductPageParser.kt#L129-L164), [`WebsiteFixturesTest` L20–53](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteFixturesTest.kt#L20-L53). |
| **Cache all misses by ISBN, or convert HTTP/challenge/unknown HTML into `NOT_FOUND`** | A different book's storefront, a policy change, transient failure, or an unrecognized page could make a real product permanently unfindable. | Per-book, policy-aware 30-day negative, `Failed` separate from `NotFound`, current-ISBN recheck; [`Resolver` L126–147, L167–193, L368–379](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L126-L147), [`WebsiteIsbnSearchTest` L251–292](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L251-L292). |

**What “works” means here:** the current source implements the intended request shapes, matching rules, error distinctions and cache behavior, and the included unit/fixture tests check those behaviors. An actual external lookup can still fail because Kobo/Rakuten block a request, change page structure, lack a listing, or provide no uniquely verifiable print/ebook association. No implementation can promise to locate every valid ISBN or prove global nonexistence after an inconclusive search.

## 5. Actual integration points (not hypothetical public ISBN endpoints)

| Integration | Exact implementation |
| --- | --- |
| Local Kobo BookDetails (`/kobo/{authToken}/v1/products/books/{bookId}`) | [`KoboRemainingEndpointsController.kt` L24–45, L56–94](../../src/main/kotlin/org/gotson/komga/interfaces/api/kobo/KoboRemainingEndpointsController.kt#L56-L94): resolve the local Komga ID via the **device fast path**, then optionally request upstream `/v1/products/books/{resolved-product-uuid}/` and overlay local metadata. |
| Local series/store response composition | [`KoboLocalStoreResponseBuilder.kt` L102–115](../../src/main/kotlin/org/gotson/komga/interfaces/api/kobo/KoboLocalStoreResponseBuilder.kt#L102-L115): resolve each local entitlement/book ID to match upstream Product ID data. |
| Proxy translation of `ProductIds` | [`KoboProxy.kt` L445–475](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProxy.kt#L445-L475): translate comma-separated local IDs using the device fast path; if unresolved, retain the original ID. |
| Explicit user metadata refresh | [`BookController.kt` L640–652](../../src/main/kotlin/org/gotson/komga/interfaces/api/rest/BookController.kt#L640-L652) queues `RefreshBookMetadataAndKoboIdentity`; [`TaskHandler.kt` L107–115](../../src/main/kotlin/org/gotson/komga/application/tasks/TaskHandler.kt#L107-L115) refreshes **local metadata first**, then resolves identity; [`KoboProductResolver.kt` L202–295](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L202-L295) avoids re-querying an unchanged positive ISBN. |
| Admin-only inspection, **no lookup side effect** | [`KoboBookMappingController.kt` L34–53, L112–145](../../src/main/kotlin/org/gotson/komga/interfaces/api/rest/KoboBookMappingController.kt#L112-L145): cached mapping and diagnostic GETs; counts/list are at [`L55–89`](../../src/main/kotlin/org/gotson/komga/interfaces/api/rest/KoboBookMappingController.kt#L55-L89). |

## 6. Short, reproducible usage examples

### A. Website request only (manual inspection, not a substitute for primary-page verification)

```http
GET https://www.kobo.com/ww/en/search?query=9781974702015&fcmedia=Book&pageNumber=1
GET https://www.kobo.com/it/it/search?query=9781974702015&fcmedia=Book&pageNumber=1
```

If the worldwide page does not produce a verified primary match and the Komga book's series language is `it`, the full lookup tries Italy next. Do **not** use “first ebook URL/UUID in the response” as the match; the source's product-page parser decides whether the match is verified.

### B. Internal Kotlin call from code that already has a Komga book ID

```kotlin
// In code with KoboProductResolver injected:
val deviceSafeProductId: String? = koboProductResolver.resolveProductIdForDevice(bookId)
// null can mean a queued background lookup; do not immediately conclude 'absent'.

// In a worker / explicit task rather than a device HTTP request:
val fullyResolvedProductId: String? = koboProductResolver.resolveProductId(bookId)
```

For a *direct client* result that preserves the outcome classification:

```kotlin
when (val result = koboProductClient.findProductForBook(isbn, bookId)) {
  is KoboProductLookupResult.Found -> println("Kobo UUID=${result.productId}; Series=${result.seriesId}")
  KoboProductLookupResult.NotFound -> println("Completed the route; no verified product")
  is KoboProductLookupResult.Failed -> println("Inconclusive: ${result.cause.message}")
}
```

`findProductForBook` is a **Kotlin/Spring service method**, not an exposed `GET /api/.../isbn-lookup` endpoint. Calling it directly does **not**, by itself, save a DB mapping; `KoboProductResolver` handles persistent mappings.

### C. Explicit refresh via the actual Komga REST endpoint

```http
POST /api/v1/books/{KOMGA_BOOK_ID}/metadata/refresh
Authorization: <your normal authenticated Komga credentials>
```

The endpoint is **admin-only** and responds **202 Accepted** for a queued task, not a synchronously returned Kobo Product ID ([`BookController.kt` L640–652](../../src/main/kotlin/org/gotson/komga/interfaces/api/rest/BookController.kt#L640-L652)). It refreshes local metadata first; unchanged already-verified ISBN mappings do **not** force a new website lookup. A book with an invalid or absent ISBN cannot be looked up by this route.

### D. Inspect the mapping / last lookup, without triggering a search

```http
GET /api/v1/books/{KOMGA_BOOK_ID}/kobo-mapping
GET /api/v1/books/{KOMGA_BOOK_ID}/kobo-identity-diagnostic
GET /api/v1/books/kobo-mappings?state=NOT_FOUND
GET /api/v1/books/kobo-mappings/counts
Authorization: <admin credentials>
```

All four are **admin-only read-only** inspection APIs ([`KoboBookMappingController.kt` L34–89, L112–145](../../src/main/kotlin/org/gotson/komga/interfaces/api/rest/KoboBookMappingController.kt#L112-L145)). An example **illustrative**, not live-captured, diagnostic response shape:

```json
{
  "mapping": {
    "bookId": "<KOMGA_BOOK_ID>",
    "title": "<LOCAL_BOOK_TITLE>",
    "isbn": "9781974702015",
    "state": "MAPPED",
    "hasKoboProductId": true,
    "productId": "<VERIFIED_KOBO_PRODUCT_UUID>",
    "observedKoboSeriesId": null,
    "cachedIsbn": "9781974702015",
    "checkedAt": "<LOCAL_DATE_TIME>"
  },
  "seriesCheckedAt": "<LOCAL_DATE_TIME_OR_NULL>",
  "seriesLookupFailedAt": null,
  "recentWebsiteAttempt": {
    "isbn": "9781974702015",
    "outcome": "FOUND",
    "matchedStorefront": "it/it",
    "checkedAt": "<ISO_INSTANT>"
  }
}
```

`recentWebsiteAttempt` is an **in-memory, bounded 256-entry** diagnostic, not a complete persistent audit. It can be `null` after restart or without a recent full client lookup, and the `matchedStorefront` can be `null` when the verified URL is not available ([`KoboLookupDiagnostics.kt` L6–31](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboLookupDiagnostics.kt#L6-L31); [`KoboProductClient.kt` L135–155](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L135-L155)). A `NOT_FOUND` mapping or an `INCONCLUSIVE` recent attempt should not be confused with a globally authoritative catalog verdict.

### E. Japanese print→ebook illustration

```text
Komga ISBN: 9784088917542                (valid print ISBN, synthetic-fixture path)
  ww/en search print ISBN               → explicit miss
  jp/ja search print ISBN               → explicit miss
  books.rakuten.co.jp/search?g=000&sitem=9784088917542
  Rakuten paired ebook product          → verified digital Book ID 4972000032980
  www.kobo.com/jp/ja/search?query=4972000032980&pagenumber=1
  Kobo Japan ebook primary Book ID      → verify 4972000032980
  Kobo Japan primary UUID               → return + map under 9784088917542
```

This sequence is **an offline fixture test**, not a claim that these example product URLs still resolve on the live site ([`KoboJapaneseEditionBridgeTest.kt` L10–17, L57–95](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L57-L95)). The production opt-in live test uses another print ISBN and verifies a specific expected UUID; see below.

## 7. Test map: what checks what

All paths below are `komga/src/test/kotlin/org/gotson/komga/…` unless specified. Unit/fixture tests fake HTTP; they do **not** demonstrate current external availability.

| Test / exact lines | Proven behavior in test |
| --- | --- |
| [`infrastructure/kobo/KoboLookupIsbnTest.kt` L7–38](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboLookupIsbnTest.kt#L7-L38) | Hyphens/spaces, valid ISBN-10 conversion, invalid checksum, opaque ebook ID and malformed input rejection. |
| [`infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt` L31–48](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L31-L48) | 47 distinct routes; worldwide/locale order; Japanese plan and BCP-47 parsing. |
| [`infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt` L66–96, L122–147](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L66-L96) | Exact `query`/`fcmedia`/`pageNumber` shape, reuse of redirect body, single GB device request, pagination. |
| [`infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt` L164–192](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L164-L192) | Worldwide miss → selected region, stop once matched. |
| [`infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt` L195–292](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L195-L292) | Wrong result/recommendations skipped, no-result vs unknown layout, cross-store redirect, incomplete negative, explicit completed 47-store miss, network exception. |
| [`infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt` L295–389](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteIsbnSearchTest.kt#L295-L389) | Reject opaque IDs as ISBN; allow opaque ebook **URL slug** after starting from valid ISBN; ISBN-10 conversion; no Rakuten bridge for non-Japanese book; specific Japanese bridge failure propagation. |
| [`infrastructure/kobo/KoboWebsiteFixturesTest.kt` L20–53](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteFixturesTest.kt#L20-L53) | Legacy/modern primary HTML and wrong-candidate/recommendation defenses. Fixtures: [`komga/src/test/resources/kobo/identity/`](../../src/test/resources/kobo/identity/). |
| [`infrastructure/kobo/KoboWebsiteFixturesTest.kt` L66–107](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteFixturesTest.kt#L66-L107) | Verified known-page series refresh, mismatch refusal, vanished URL vs rate limit, redirect reuse. |
| [`infrastructure/kobo/KoboWebsiteMissingIdentifierTest.kt` L12–30](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteMissingIdentifierTest.kt#L12-L30) | Missing primary identifier means inconclusive, not `NOT_FOUND`. |
| [`infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt` L57–135](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L57-L135) | Entire mock print→Rakuten ebook→Kobo Japan flow; bypass Rakuten when ISBN already matched; inconclusive route does not cache absence. |
| [`infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt` L137–202](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L137-L202) | Reject ambiguous pairs/wrong volumes, preserve failure, verify digital ID on known Japan page, disallow opaque ID as global ISBN. |
| [`infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt` L232–286, L395–492](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridgeTest.kt#L395-L492) | Verify linked ebook/print association, bounded multi-candidate selection, and title-less catalog candidate refusal. |
| [`infrastructure/kobo/KoboProductClientValidationTest.kt` L14–40](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClientValidationTest.kt#L14-L40) | Same ISBN can have different book-specific locale; direct client rejects invalid input without network. |
| [`infrastructure/kobo/KoboIsbnSingleFlightTest.kt` L11–59](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboIsbnSingleFlightTest.kt#L11-L59) | One simultaneous request per key, later retries after success or error. |
| [`infrastructure/kobo/KoboWebsiteRequestPacerTest.kt` L9–85](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboWebsiteRequestPacerTest.kt#L9-L85) | Per-host pacing, independence across hosts, monotonic timing. |
| [`domain/service/KoboProductResolverTest.kt` L52–119, L121–176](../../src/test/kotlin/org/gotson/komga/domain/service/KoboProductResolverTest.kt#L52-L176) | Invalidating outdated policy, fast GB miss queues full search, immediate fast hit, invalid ISBN behavior, stable unchanged positive. |
| [`domain/service/KoboProductResolverTest.kt` L241–376, L718–801](../../src/test/kotlin/org/gotson/komga/domain/service/KoboProductResolverTest.kt#L241-L376) | Stale-in-flight protection, per-book policy, unambiguous sharing, no cross-book negative leakage, persisted positive/negative vs uncached failure. |
| [`infrastructure/kobo/KoboStandardLiveLookupTest.kt` L14–54](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboStandardLiveLookupTest.kt#L14-L54) | **Opt-in live** production client/transport test of a non-Japanese ISBN; asserts a UUID on `Found`. |
| [`infrastructure/kobo/KoboJapaneseLiveLookupTest.kt` L17–66](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseLiveLookupTest.kt#L17-L66) | **Opt-in live** production Japanese route, including a known print ISBN/ebook ID/expected Kobo Product ID; failure diagnostics in [`L69–109`](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseLiveLookupTest.kt#L69-L109). |

**Test commands, run from the repository root (PowerShell):**

```powershell
# Offline-ish tests: these classes mock HTTP, but Gradle may need dependencies installed.
.\gradlew.bat :komga:test --tests "org.gotson.komga.infrastructure.kobo.KoboLookupIsbnTest" --tests "org.gotson.komga.infrastructure.kobo.KoboWebsiteIsbnSearchTest" --tests "org.gotson.komga.infrastructure.kobo.KoboWebsiteFixturesTest" --tests "org.gotson.komga.infrastructure.kobo.KoboJapaneseEditionBridgeTest" --tests "org.gotson.komga.infrastructure.kobo.KoboProductClientValidationTest" --tests "org.gotson.komga.infrastructure.kobo.KoboIsbnSingleFlightTest" --tests "org.gotson.komga.infrastructure.kobo.KoboWebsiteRequestPacerTest" --tests "org.gotson.komga.domain.service.KoboProductResolverTest"

# Opt-in REAL external HTTP through production client. Run separately; current site/rate limits can affect it.
$env:KOMGA_KOBO_LIVE_TEST = "1"
.\gradlew.bat :komga:test --tests "org.gotson.komga.infrastructure.kobo.KoboStandardLiveLookupTest" --tests "org.gotson.komga.infrastructure.kobo.KoboJapaneseLiveLookupTest" --rerun-tasks
Remove-Item Env:KOMGA_KOBO_LIVE_TEST
```

Both live tests are guarded by the `KOMGA_KOBO_LIVE_TEST=1` assumption ([`StandardLiveLookupTest.kt` L18–22](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboStandardLiveLookupTest.kt#L18-L22); [`JapaneseLiveLookupTest.kt` L22–26](../../src/test/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseLiveLookupTest.kt#L22-L26)). They use a real `KoboProductClient`, **do not** write to the DB, and **do not** require authenticated Kobo device credentials. The Japanese test contains a fixed expected product UUID and is sensitive to any real catalog change. Do not interpret an external HTTP block as a deterministic unit-test regression.

## 8. Quick troubleshooting rules

| Symptom | Inspect first | Consequence |
| --- | --- | --- |
| Device gets local BookDetails without Store enrichment on first try | Cached mapping; GB fast path; pending background search ([`Resolver` L46–65](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L46-L65)) | `null` initially is not catalog absence. |
| Worldwide miss but book appears in local site | Series language on **requesting Komga book**; `plan(locale)` ([`ProductClient` L101–116](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L101-L116)) | Verify the second storefront is the intended one. |
| Unexpected `NOT_FOUND` after changing language | `lookupPolicy`, its migration, and 30-day TTL ([`Resolver` L126–135, L368–379](../../src/main/kotlin/org/gotson/komga/domain/service/KoboProductResolver.kt#L368-L379)) | Old policy negatives should trigger a new lookup; positives remain stable for unchanged ISBN. |
| `Failed`/`INCONCLUSIVE` with 403 or 429 | [`ProductClient` L201–218](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L201-L218) | Do not cache a miss or blindly retry every storefront. |
| `Failed` after getting an ebook search result | Primary ID/UUID parser and missing-identifier handling ([`PageParser` L19–93](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductPageParser.kt#L19-L93)) | A recommendation ID or a product slug alone is not valid identity proof. |
| Japanese print ISBN does not find ebook | Rakuten paired print/ebook metadata and primary ebook ID; Kobo `jp/ja` ebook verification ([`JapaneseEditionBridge` L31–69, L353–398](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboJapaneseEditionBridge.kt#L353-L398)) | A verified print→ebook bridge may be required; an ambiguous pair stays `Failed`. |
| Packaged lookup fails after HTTP-client changes | Whether a persistent cookie jar was added, or Rakuten is using the impersonating transport ([`ProductClient` L41–59](../../src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProductClient.kt#L41-L59)) | Keep routing changes independent of the previously working Kobo HTTP stack. |

**Boundary to preserve when modifying this feature:** normalize **ISBN only** at the public website lookup entry; select the **requesting book's** route; preserve `fcmedia=Book&pageNumber=N` for ISBN search; verify the **primary book and UUID**; treat blocked/ambiguous requests as `Failed`; bridge Japanese print ISBN through a **uniquely verified** digital edition; save identity only if the book's ISBN/mapping/policy is still current; keep global lookup off the device request path.
