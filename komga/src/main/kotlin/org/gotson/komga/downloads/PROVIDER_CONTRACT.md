# DownloadProvider contract (v1)

This document is the standard for native Kotlin providers in the Komga fork.
The executable contract is `DownloadProvider.kt` in this directory. Keep the
interface and this document synchronized when extending it. New providers must
implement the contract rather than implementing a separate download pipeline.

## Registration and responsibilities

- Implement `DownloadProvider` as a Spring-managed Kotlin component (normally
  `@Component`). Its `id` must be a unique, permanent, lowercase machine key;
  its `label` is the human-readable source name. `DownloadService` registers
  components by ID and exposes them through the admin-only downloader API.
- Providers own catalogue discovery, remote series/chapter IDs, pagination,
  source-specific request metadata, response parsing, chapter and page order,
  source-specific availability, and authorized access policies. A provider must
  not assume another provider has the same protocol.
- Komga owns the destination library, filesystem validation, page validation,
  CBZ creation, temporary storage, KCC configuration and conversion, manual
  and automatic download queues, persistent history, deduplication and scans.
  Providers must not write books, run KCC, update the database, or scan a library.
- A source is a singleton, not a mutable CLI scraper selection. Methods can be
  called by the admin UI and worker concurrently. No selected-series global,
  search-result index, or required prior search. Cache only bounded,
  thread-safe data, and never rely on a cache for identifier validity.

## Method contracts

| Method | Input | Result and obligations |
| --- | --- | --- |
| `search(query)` | Trimmed title, ID or documented source URL | Ranked `SourceSeries` values with stable IDs and display titles. Return an empty list for a genuine no-match result. Throw on upstream failure rather than treating an unavailable catalogue as no matches. |
| `chapters(seriesId, filters)` | Previously returned series ID; language and optional scanlation-group filters | Deduplicated `SourceChapter` records, normally newest first; retain source order when it is semantically significant. Support independent calls after restart. |
| `pages(chapterId)` | Previously returned chapter ID | Complete ordered `SourcePage` manifest for one available chapter. Throw for unavailable, incomplete, access-denied or unsupported content; never silently publish a truncated chapter. |
| `nextRelease(seriesId)` | Previously returned series ID | Absolute UTC `Instant` for a source-announced next release, or `null` if unknown/unsupported. Do not invent a date. Komga owns estimates and polling. |

### Identifier rules

`SourceSeries.id` and `SourceChapter.id` are opaque source identifiers, not
UI row numbers, titles, array positions, or temporary URLs. They must be stable
across restarts and chapter-list refreshes. Chapter IDs must be unique within
the provider: current history deduplicates by provider ID, chapter ID and
destination. Encode composite IDs safely for a single REST path segment, and
avoid slash, backslash, percent escapes, query strings and credentials. Do not
change previously persisted IDs without a documented migration. Long chapter
IDs are compacted by the common filename helper, not by the adapter. Never
interpret an identifier as a local filesystem path.

### Chapter metadata

- `number`: source chapter number as a string, preserving decimal and split
  chapter distinctions. Use `"0"` for unnumbered content when the source has
  no better number. Do not substitute the source chapter ID as the number.
- `title`: subtitle/short display title, possibly empty; Komga supplies the
  `Chapter <number>` filename prefix. Remove unrelated UI widgets, markup and
  control characters. Keep chapter order independently of titles.
- `publishedAt`: actual publication timestamp if available, otherwise `null`.
  Reader-history timestamps, retrieval times and estimates are not publication
  dates. `language` is an ISO-style source language code or empty when unknown.
- `url`: canonical public chapter link, not an ephemeral image or access URL.
- `groups`: real source-provided group names, or empty when unsupported. Do not
  invent groups for filter matching. Providers supporting group selection use
  case-insensitive include/exclude matching, with exclusion taking precedence.
  Where a source offers no group selection, document that group filters are
  ignored. Do not interpret an unsupported filter as an empty chapter feed.
  Language-selection capabilities are source-specific and must be documented.

### Page manifest

`SourcePage.filename` must end with an extension supported by `DownloadFiles`
(`jpg`, `jpeg`, `png`, `webp`, `gif`, `avif`, `bmp`). It must correspond to the
actual image type and must not contain a path or untrusted URL. The list is in
reading order: Komga generates numbered archive names from list order, not
from the source filename. The filename need not be unique.

`SourcePage.open` is a lazy function that returns a **new** readable
`InputStream` each time. Komga closes it. Dimensions preview and archive creation
are separate operations and may reopen pages. The function must return actual
image bytes, not HTML, an error response, a placeholder, or an inaccessible
resource. Never swallow a page retrieval error. Reject unsupported pages before
publication; the common archive pipeline also checks size, image decoding and
pixel limits. Do not assume that previewing a page reserves it for download.

## Transport and error handling

- Use native JVM HTTP and the existing dependencies; no per-provider CLI
  executables or browser automation. `ProviderHttp` is a shared helper, not a
  compulsory protocol: provider-specific request construction stays local.
- Validate input IDs and all outgoing URLs; restrict schemes/hosts/ports and
  validate redirects. Never allow arbitrary local or private hosts. Do not
  loosen CDN allowlists based on an untrusted manifest. Keep provider session
  state/cookies scoped correctly; never log tokens or raw protected responses.
- Bound metadata and image responses, including decompression. Retry only
  transient failures, respect Retry-After/cooldowns, and stop on access
  restrictions or interactive challenges. Do not bypass publisher protections.
- Invalid user input should raise `IllegalArgumentException`. Upstream I/O,
  malformed protocol and unavailable content must raise informative errors;
  include provider, operation, status or safe schema diagnostics, not raw
  credentials. The worker records failures; an unsuccessful chapter should
  not be reported as a successful download.
- No fabricated results, empty-list masking of remote errors, or success based
  on HTTP 200 alone. Check the expected schema and source identity.

## Existing provider capabilities and deviations

- **MangaDex** (`mangadex`): existing adapter, untouched by this revision. Native
  API, per-request language/group filters; external/unhosted chapters are
  omitted. Provider IDs remain unchanged.
- **WeebCentral** (`weebcentral`): reference behavior from Mangathr's
  `internal/sources/weebcentral`. Title, ULID and series-link search, relevance
  ranking, JSON/HTML chapter and page parsing, canonical chapter links, ordered
  images, placeholder rejection. Group and language filters are ignored for
  this single-catalogue source. Chapter card dates are not publication dates.
  Source request headers and known CDN allowlist are explicit. Komga does not
  have Mangathr's separate select-manga step; direct ID/link lookup reads the
  canonical heading, title search uses ranked result names. Host restrictions
  and stable path-safe ID validation are intentional differences. The shared
  page helper currently requires a supported image extension in the URL.
- **Manga Plus** (`mangaplus`): opt-in via
  `komga.downloads.mangaplus.enabled=true`. Metadata uses group-title matching,
  first matching language edition, V4 wire parsing plus field-28 legacy
  fallback, split numbering and source release timestamps. Group/language
  request filters are ignored; `MangaPlus` is retained as a compatibility
  metadata label, not a selectable scanlation group. Catalogue language is
  selected by the native
  Spring property `komga.downloads.mangaplus.language` (0 English, 1 Spanish,
  2 French, 3 Indonesian, 4 Brazilian Portuguese, 5 Russian, 6 Thai). Public
  viewer configuration properties are `komga.downloads.mangaplus.image-quality`
  (`super_high`, `high`, `low`) and `komga.downloads.mangaplus.split` (`no`,
  `yes`). Defaults match Mangathr. For a chapter the source makes available,
  the viewer adapter uses the credential and image representation metadata
  issued in the successful response. Image streams are ordered and reopenable;
  denied responses are not retried as another account or treated as images.
  Image requests use an explicit CDN allowlist, and viewer credentials are not
  sent to a different host on redirect. No entitlement or regional restrictions
  are overridden. Actual upstream response compatibility must be established
  by live smoke testing.
- **Cubari** (`cubari`): optional manifest provider; not the focus of this
  revision. Existing IDs and implementation are retained.

Mangathr adaptation attribution is retained in `MANGATHR-LICENSE.txt` in the
packaged resources. These adapters do not depend on the Go runtime.

## Acceptance checks for a new or revised provider

1. Test exact request construction and reference response fixtures (including
   alternate titles, pagination, missing fields, malformed payloads, duplicate
   IDs, chapter ordering, decimal chapter numbers and image ordering).
2. Test failed upstream HTTP, unsafe URLs and redirects, bounded decoding,
   unsupported filters, unavailable content and placeholders. Do not replace
   real parsers with mock parser results. Mock transport only for fixture
   isolation; also test actual HTTP request/response helpers.
3. Test independent/repeated calls using persisted IDs and multiple provider
   instances. Check thread safety and absence of global selected-series state.
4. Test authorized live search, chapter discovery, page dimensions, complete
   retrieval, download with KCC off/on, auto-fetch and library scan. Validate
   Windows and Linux when relevant. Do not label a provider as live-validated
   solely on the basis of mocked tests. Record unsupported content explicitly.
5. Run downloader tests, Komga architecture tests and ktlint. Keep frontend
   builds in the established remote workflow if frontend changes are needed.

Preserve the common download pipeline and existing provider IDs when adding an
adapter. The purpose of this standard is interoperability, not forcing all
sources to expose identical capabilities.
