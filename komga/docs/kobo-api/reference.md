> **Payload navigation:** [Full per-endpoint JSON index](payloads/README.md) · [Feedback JSON](payloads/19.md) · [All captures](captures/README.md). Sections numbered §8.x are in the per-endpoint payload pages; this file preserves sections 1–7.

# Kobo API × Komga — Full JSON Wire Reference

**Version:** 22 September 2026 · **Basis:** supplied Komga fork source, redacted e-reader capture and redacted Komga server capture. This is a captured-contract reference, not Kobo's official API specification.

**Read this first.** All JSON blocks under §8 are complete *for the cited, successfully parsed capture*, except that long **string values** have been shortened and sensitive scalar values redacted. Field names, nesting, arrays, nulls and primitive JSON types are preserved. A full object seen in one capture does not prove no other fields exist under other conditions. §10 lists all resource names advertised at initialization, including endpoints for which **no request or response JSON is available**. The companion [all-captures ledger](captures/README.md) enumerates every parseable captured body, including repeated and alternative responses; the `fixtures/` directory contains individually valid, machine-readable examples, and `field-path-index.json` provides the observed field-path index.

**Important distinction:** `DEVICE response` is what the e-reader actually receives from Komga and may use local IDs. `UPSTREAM response` is what Komga logged receiving from Kobo, generally with native Kobo IDs. These are **not automatically transaction-matched** just because they share a route: the log captures contain concurrent and repeated requests. Correlation must use explicit timestamps and IDs, not neighboring log lines. `UPSTREAM` records log neither the HTTP request method nor an outbound request body, unless a separate device request or source code establishes it.

**Sanitized-capture qualification:** 38 device-response lines in the provided *sanitized text* cannot be parsed as standalone JSON (see §8.A). This does **not** establish malformed or truncated Kobo HTTP responses: the logging, export, or sanitization process may have changed the recorded text. In particular, do not compare the original `Content-Length` header with the redacted/sanitized body's length. A separately captured upstream Kobo JSON body may document the upstream schema, but must not be presented as an exact copy of Komga's device-facing body.

## 1. How to read this reference

**Evidence classes:** **DEVICE** = the e-reader emitted the request and/or recorded its response; **SERVER** = Komga logged its processing or the upstream Kobo exchange; **CODE** = a route or behavior exists in the supplied Kotlin source; **ADVERTISED** = a URL was listed by `/v1/initialization` but no invocation is proven. An observed HTTP status applies only to the captured request, not every possible input. A route's existence is not proof that it was exercised, and a proxied `200` is not proof that Komga implements the remote API.

**Base URLs.** Device-facing local API: `https://{host}/kobo/{token}/v1/...`; upstream catalog/store API: `https://storeapi.kobo.com/v1/...` or `/v2/...`; independent services: `readingservices.kobo.com`, `ereaderfiles.kobo.com`, `api.kobobooks.com`, `cdn.kobo.com`, `storedownloads.kobo.com`, `getbook.kobo.com`. Local public endpoints are exposed under one path but may execute different branches depending on whether an identifier belongs to Komga. HTTP examples use the device-facing prefix unless explicitly marked upstream.

**Reference labels.** `nickel:L2516` means line 2516 of `Full - Copy.redacted.txt`; `komga:L105` means line 105 of `komga.redacted.txt`; `KoboController.kt:289` identifies the line within that file in the source archive. Captures may contain parallel device requests; a request and another thread's subsequent response must not be correlated solely by neighboring line numbers.


## 2. Architectural contract and routing

| Layer | Implementation | Main effect |
|---|---|---|
| Device-facing controller | `KoboController.kt` | Initialization, device-auth fallback, sync, metadata, state, content, thumbnails, collections, catch-all. |
| Hybrid catalog controller | `KoboRemainingEndpointsController.kt` | Local-and-remote book details, series memberships, product prices. |
| Identity-aware store proxy | `KoboProxy.kt` + `KoboProductResponseTranslator.kt` | Forwards method/query/body, substitutes resolvable local product IDs in supported paths/queries, adapts certain response identities and covers. |
| Raw store proxy | `KoboRawStoreProxy.kt` | Forwards store request without generic Komga response-identity rewriting; used by hybrid catalog and cloud-tag paths. |
| Outbound safety boundary | `KoboOutboundRequestGuard.kt` | Checks final Kobo-bound URI and body for actual, untranslated Komga book/series/read-list IDs; rejects such requests with `400`. |
| Stateful services | sync point/read-list/archive/reading-progress repositories and services | Per-user library sync and archives; shared Komga read-list mutations require admin rights. |

**Standard proxy and raw proxy are not interchangeable.** The standard proxy can remap product identifiers in requests and selected response structures. Raw proxy deliberately leaves the upstream JSON identity untouched. Both forward selected request headers, strip most upstream response headers, and retain `x-kobo-*`; thus a native upstream `ETag`, `Cache-Control`, or `Content-Length` need not survive the proxy. The raw proxy intentionally excludes `x-kobo-synctoken` from routine forwarding. Both paths are subject to the outbound request guard. Source: `KoboProxy.kt:131–337`, `KoboRawStoreProxy.kt:69–176`, `KoboOutboundRequestGuard.kt:43–111`.

**Identity taxonomy.** A Komga book ID is a local TSID; a Kobo `Id` is generally a product UUID; a Kobo `CrossRevisionId`/`WorkId` can identify a work or revision and may differ from the product ID; a Kobo `SeriesId` is separate from the local Komga series TSID. `ISBN` is bibliographic metadata, not interchangeable with any of these. Komga's local device-facing representation may deliberately put a local book ID in `Id`/`CrossRevisionId`/`WorkId`/`RevisionId`; only a confirmed resolver mapping may be substituted into an upstream request. See `KoboProductResponseTranslator.kt:68–99`, `KoboProxy.kt:351–482`.

**Localization and store context.** The captured device identifies itself with `x-kobo-appversion`, `x-kobo-deviceid`, `x-kobo-devicemodel`, `x-kobo-platformid`, `x-kobo-affiliatename`, `Accept-Language` and an Authorization header. Profile reports `StoreFront=IT`, `CountryCode=IT`, and `IsoCultureCode=en-US`; storefront and language are separate inputs, and observed catalog availability must not be extrapolated to all regions. The initialization response lists many feature flags; a `True` flag does not prove that Komga implements the corresponding feature. Evidence: `nickel:L203–246` and `nickel:L197`.


## 3. End-to-end request and sync lifecycles

**Boot/ordinary sync (captured):** device downloads its label/update resources, requests initialization, user profile, loyalty and subscriptions, reads deals/assets/tests, and sends `GET /v1/library/sync`. Komga snapshots a local sync point, serializes local books/read states/read lists in pages, and only after the Komga page stream finishes does it merge a Kobo Store sync batch. The response supplies a Komga-encoded `x-kobo-synctoken`; `x-kobo-sync: continue` indicates more pages. On a completed sync, the old checkpoint is deleted and the newly completed checkpoint becomes the baseline. An upstream sync failure is logged and local items can still be returned. Code: `KoboController.kt:289–632`; evidence: `nickel:L521–545`, `komga:L33–35`, `komga:L102–104`.

**Book details:** local book ID → content-permission check → attempt resolver to upstream Kobo ProductId → use raw Kobo `books/{id}` result if available → overlay local content/identity/cover/series → return enriched local book, or local fallback without Kobo stats/rating. Genuine Kobo ID → upstream raw response and optional passive observation. Source: `KoboRemainingEndpointsController.kt:38–109`.

**Series:** local series membership remains authoritative. A mapped Kobo series may enrich matching local members; the remote series result must not replace the local list. The device-triggered series lookup can teach product/series observation helpers. Genuine Kobo series ID is passed through. Parameters `PageSize` and `PageIndex` paginate the local result. Source: `KoboRemainingEndpointsController.kt:111–175`; observed upstream query at `komga:L105`.

**Collections:** Kobo calls shelves/collections “tags”. Local tag ID ↔ Komga read-list ID. Local membership can combine local books with stored external Kobo revision IDs. Unknown tag IDs and collections containing only remote items can be delegated to Kobo. Local tag mutation requires Komga admin privilege. A special synthetic `KOMGA-ONDECK` is handled without persistence. Stale device add/remove operations are filtered against a read-list sync baseline. Source: `KoboController.kt:922–1187`.

**Archives vs delete:** `DELETE /v1/library/{id}` with a local entitlement records a *per-user archive* and returns `204`; it does not remove the source book from Komga. Subsequent sync encodes the archived entitlement state. Source: `KoboController.kt:651–673`; observed `nickel:L32403–32439`.


## 4. HTTP/serialization rules and status boundaries

| Status / header | What the supplied evidence establishes | What it does not establish |
|---|---|---|
| `200 OK` | Captured successful JSON calls, local state reads/writes, store-proxy catalog responses. | All error branches, all regions, all methods. |
| `201 Created` | Local collection creation; local collection item addition in code. | A generic success code for all Kobo collection APIs. |
| `204 No Content` | Local book archival; **later device-facing recommendation-feedback POST** (`review_v2:L9314–9330`), with an empty response body. | Physical deletion of a Komga book; proof that the feedback was forwarded upstream. |
| `304 Not Modified` | Device cached wishlist and some local thumbnail exchanges. | That the upstream Kobo API also returned `304` in the wishlist exchange: server capture shows upstream `200` during other wishlist calls. |
| `307 Temporary Redirect` | Some cover URLs redirect to Kobo's image CDN; exact redirect may vary by image identity. | A generic redirect policy for book downloads. |
| `400 Bad Request` | Malformed local reading-state updates; outbound guard blocks surviving actual Komga IDs before transmission; duplicate-name handling for read lists. | The full error schema or every possible validation path. |
| `403 Forbidden` | Local read-list mutation requires admin; download role and content restrictions apply in relevant routes. | A single universal access-control policy across all proxied endpoints. |
| `404 Not Found` | Missing locally addressed book/thumbnail, non-proxy local entitlement, missing converted file. | That a Kobo Store not-found would have the same body. |
| `503 Service Unavailable` | Explicit local KEPUB conversion failure. | A general upstream availability guarantee. |
| `x-kobo-synctoken` | Carries hybrid Komga/Kobo continuation state; protect as secret. | That tokens are portable between users/devices or meaningful when redacted. |
| `x-kobo-apitoken` | Returned in observed store responses and locally initialized to an innocuous placeholder `e30=`. | That the redacted real header value can be reconstructed. |

Transport errors, proxy failures, rate limits, unexercised `401` flows and unusual upstream codes were **not exhaustively sampled**; the API-wide error-code matrix cannot be asserted from these captures. Spring and the upstream service can generate additional statuses. Device `Authorization`, token-bearing URL segments, cookies, ETags, sync tokens, signed download URLs and user profile fields should be redacted before sharing traces.


## 5. Endpoint reference — local and hybrid API

The endpoint cards below describe behavior and route ownership. Exact captured JSON, separated by device-visible and upstream Kobo layer, follows in the full-payload section. Missing bodies are explicitly identified rather than reconstructed.

### 5.1 Initialization

`GET /v1/initialization`  
**Request:** No body. Authentication via Komga token-bearing path and accepted device headers.  
**Response:** `{"Resources":{…}}`; see advertised resource catalog in §9. Local result rewrites image host and image URL templates to Komga URLs; emits `x-kobo-apitoken: e30=`.  
**Side effects:** Client discovers Store and local media endpoints; no purchase/sync side effect.  
**Komga route:** LOCAL + upstream initialization; if upstream fails for reasons other than `401`, falls back to `nativeKoboResources`.  
**Evidence / observed HTTP status:** 200 DEVICE/SERVER; nickel:L162–197; komga:L13–15; KoboController.kt:212–252.


### 5.2 Device authentication

`POST /v1/auth/device`  
**Request:** JSON includes a `UserKey` in the fallback branch; complete device request not present in this capture.  
**Response:** Normally upstream auth reply; on upstream failure, local dummy `AuthDto` with randomized access/refresh/tracking values and echoed `UserKey`.  
**Side effects:** Keeps device handshake functional; **not** Komga authorization.  
**Komga route:** LOCAL HANDLER with upstream-first fallback.  
**Evidence / observed HTTP status:** CODE ONLY for method/body/status; KoboController.kt:254–276.


### 5.3 Library synchronization

`GET /v1/library/sync?Filter=ALL&DownloadUrlFilter=Generic,Android&PrioritizeRecentReads=true`  
**Request:** No body; optional `x-kobo-synctoken` request header; captured Filter, DownloadUrlFilter and PrioritizeRecentReads shown.  
**Response:** JSON array of sync envelopes: e.g. `NewEntitlement`, `ChangedEntitlement`, reading-state updates, `NewTag`; reply headers `x-kobo-synctoken` and optional `x-kobo-sync: continue`.  
**Side effects:** Creates/freezes checkpoints, sends local changes, appends upstream Kobo items at end; archived books represented as archived entitlements; excludes synthetic On Deck tag.  
**Komga route:** LOCAL + STANDARD PROXY with upstream token preservation and eventual snapshot cleanup.  
**Evidence / observed HTTP status:** 200 DEVICE/SERVER; nickel:L521–545; komga:L33–35,L102–104; KoboController.kt:289–632.


### 5.4 Entitlement metadata

`GET /v1/library/{komgaBookId}/metadata`  
**Request:** Single path ID; no body.  
**Response:** JSON array of book metadata records, with local cover image ID and Komga book identity and computed download URLs.  
**Side effects:** Read-only.  
**Komga route:** LOCAL if book exists; else STANDARD PROXY when enabled; otherwise local permission/not-found path.  
**Evidence / observed HTTP status:** 200 DEVICE; nickel:L2532–2570; KoboController.kt:634–649.


### 5.5 Archive entitlement / remote delete

`DELETE /v1/library/{entitlementId}`  
**Request:** Path entitlement ID; no observed body.  
**Response:** Local success `204` with empty body; remote behavior forwarded if not local.  
**Side effects:** LOCAL: per-user archived state, not filesystem/database deletion; may later be reflected in sync.  
**Komga route:** LOCAL / STANDARD PROXY for unknown ID; non-proxy unknown ID → `404`.  
**Evidence / observed HTTP status:** 204 DEVICE; nickel:L32403–32439; KoboController.kt:651–673.

### 5.6 Read state

`GET /v1/library/{komgaBookId}/state`  
**Request:** Path entitlement ID.  
**Response:** Array of `ReadingStateDto` records; fields include `EntitlementId`, `CurrentBookmark`, `Statistics`, `StatusInfo`, timestamps.  
**Side effects:** Read-only.  
**Komga route:** LOCAL / STANDARD PROXY for unknown ID.  
**Evidence / observed HTTP status:** 200 DEVICE; nickel:L27179–27227; KoboController.kt:675–694.


### 5.7 Update read state

`PUT /v1/library/{komgaBookId}/state`  
**Request:** `{"ReadingStates":[{"EntitlementId":"…","CurrentBookmark":{"Location":{"Source":"…","Type":"KoboSpan","Value":"…"},"ContentSourceProgressPercent":0,"ProgressPercent":100},"StatusInfo":{"Status":"Finished"},"Statistics":{…}}]}`.  
**Response:** `{"RequestResult":"Success|Failure","UpdateResults":[{"EntitlementId":"…","CurrentBookmarkResult":{…},"StatisticsResult":{…},"StatusInfoResult":{…}}]}`; code may reply HTTP 200 even if per-item result is `Failure`.  
**Side effects:** Converts update to R2 progression; `Finished` position uses last EPUB position rather than Kobo-provided first resource; statistics result marked Ignored on success.  
**Komga route:** LOCAL / STANDARD PROXY for unknown ID; missing state/location/percentage → `400`.  
**Evidence / observed HTTP status:** 200 DEVICE; nickel:L27405–27450; KoboController.kt:696–787.


### 5.8 Download local EPUB/KEPUB

`GET /v1/books/{komgaBookId}/file/epub?convert_kepub=false`  
**Request:** Optional `convert_kepub` Boolean; captured false. Requires Komga `FILE_DOWNLOAD` role.  
**Response:** Binary EPUB stream (not JSON). On true, converted `.kepub.epub` with attachment filename and content length.  
**Side effects:** True: hash-aware KEPUB conversion/cache and size persistence; false: ordinary file download.  
**Komga route:** LOCAL; `404` missing file/book; `503` conversion failure; not a Kobo upstream product-file endpoint.  
**Evidence / observed HTTP status:** DEVICE download captured; nickel:L2572–2606; KoboController.kt:790–876.

### 5.9 Local or remote cover image

`GET /v1/books/{thumbnailId}/thumbnail/{width}/{height}/{quality}/{isGreyScale}/image.jpg`  
**Request:** Also supports variant without quality segment.  
**Response:** JPEG bytes for local covers; for nonlocal IDs with recognized UUID, may redirect to Kobo CDN. Conditional cache response can be `304`.  
**Side effects:** Image fetching/caching only.  
**Komga route:** LOCAL or REDIRECT; thumbnail permission check for local IDs.  
**Evidence / observed HTTP status:** 200,304,307 DEVICE; nickel:L928–961,L18504–18592; KoboController.kt:878–920.

### 5.10 Create collection/tag

`POST /v1/library/tags`  
**Request:** `{"Name":"Example shelf","Items":[{"RevisionId":"{komgaBookId}","Type":"ProductRevisionTagItem"}]}`.  
**Response:** On local creation HTTP `201` with a **top-level JSON string** containing canonical local read-list ID.  
**Side effects:** Create/merge read list and store permitted external revision IDs.  
**Komga route:** LOCAL if it contains any matched Komga books or no items; exclusively cloud revision IDs → RAW PROXY. Local mutation admin-only.  
**Evidence / observed HTTP status:** 201 DEVICE; nickel:L35135–35169; KoboController.kt:922–974.


### 5.11 Rename collection/tag

`PUT /v1/library/tags/{tagId}`  
**Request:** `{"Name":"New shelf name"}`.  
**Response:** Local `200` with empty body.  
**Side effects:** Rename local read list; synthetic `KOMGA-ONDECK` is no-op.  
**Komga route:** LOCAL if tag ID exists; otherwise RAW PROXY; admin-only for local.  
**Evidence / observed HTTP status:** 200 DEVICE; nickel:L36686–36720; KoboController.kt:976–1002.


### 5.12 Delete collection/tag

`DELETE /v1/library/tags/{tagId}`  
**Request:** Path tag ID; optional body.  
**Response:** Local `200` with empty body.  
**Side effects:** Deletes corresponding local read list; special synthetic On Deck no-op.  
**Komga route:** LOCAL or RAW PROXY for unknown tag; local admin-only.  
**Evidence / observed HTTP status:** CODE ONLY (not observed device DELETE tag); KoboController.kt:1004–1028.

### 5.13 Add collection items

`POST /v1/library/tags/{tagId}/Items`  
**Request:** Also lowercase `/items`; JSON `Items` of revision IDs / `ProductRevisionTagItem`.  
**Response:** Local `201` empty body.  
**Side effects:** Adds authorized local books and records external revision IDs, with stale device-mutation guard.  
**Komga route:** LOCAL or RAW PROXY for unknown tag; admin-only local.  
**Evidence / observed HTTP status:** CODE ONLY; KoboController.kt:1030–1095.


### 5.14 Remove collection items

`POST /v1/library/tags/{tagId}/Items/delete`  
**Request:** Also lowercase `/items/delete`; JSON `Items` of revision IDs.  
**Response:** Local `200` empty body.  
**Side effects:** Removes accepted memberships/external IDs; stale device-mutation guard.  
**Komga route:** LOCAL or RAW PROXY for unknown tag; admin-only local.  
**Evidence / observed HTTP status:** CODE ONLY; KoboController.kt:1097–1159.


### 5.15 Book detail

`GET /v1/products/books/{bookId}/?SendToBrowseHistory=true&SendBookStats=true`  
**Request:** Optional device-observed `SendToBrowseHistory`, `SendBookStats` parameters; may have trailing slash.  
**Response:** Single Kobo-style book product object with `Id`, `ISBN`, `Title`, `SeriesId`, `SeriesNumber`, `ImageId`, availability/ratings/prices; locally overlaid for matched Komga books.  
**Side effects:** Read-only locally; optional upstream browse-history behavior is not independently established. Device response can feed passive ID/series observation.  
**Komga route:** HYBRID if local ID; RAW PROXY for external ID; literal `subscriptions`/`series` are never treated as a local book.  
**Evidence / observed HTTP status:** 200 DEVICE/SERVER; nickel:L25838–25880,L27554; komga:L299–301; KoboRemainingEndpointsController.kt:38–109.


### 5.16 Series member listing

`GET /v1/products/books/series/{seriesId}?ExcludeOwned=false&PageSize=100`  
**Request:** `PageSize` and optional `PageIndex` used for local paging; device also sends `ExcludeOwned`.  
**Response:** Kobo paginated shape: `Items:[{"Book":{…}}]`, `ItemCount`, `TotalItemCount`, `CurrentPageIndex`, `TotalPageCount`, `ItemsPerPage`, `Filters`, `VersionCode`.  
**Side effects:** Local membership authoritative; optional upstream enrichment/observation can record local↔Kobo series/product associations.  
**Komga route:** HYBRID local series or RAW PROXY native Kobo series.  
**Evidence / observed HTTP status:** 200 DEVICE/SERVER; nickel:L2477–2516; komga:L105–106; KoboRemainingEndpointsController.kt:111–175.


### 5.17 Product prices

`GET /v1/products/{productIds}/prices`  
**Request:** Comma-separated one or more IDs, possibly a mixture of local TSIDs and native Kobo UUIDs.  
**Response:** `{"Items":[…]}`. Local items get synthetic price data; external items use genuine upstream response.  
**Side effects:** Read-only.  
**Komga route:** HYBRID mixed list; RAW PROXY for entirely remote IDs.  
**Evidence / observed HTTP status:** 200 DEVICE; nickel:L27240–27276,L30582–31162; KoboRemainingEndpointsController.kt:206–259.


### 5.18 Catch-all Store endpoint

`GET, PUT, POST, DELETE, PATCH /v1/{unmatchedPath}`  
**Request:** Passes incoming path, method, supported headers, query string and optional body.  
**Response:** Store response JSON from identity-aware proxy; when disabled, returns HTTP `200` and empty `{}` (which is not semantically equivalent to the real Store reply).  
**Side effects:** Dependent on upstream call; the outgoing request is guard-validated.  
**Komga route:** STANDARD PROXY or EMPTY LOCAL FALLBACK. Not a separate implementation of each advertised route.  
**Evidence / observed HTTP status:** CODE; KoboController.kt:1263–1273; KoboProxy.kt:131–337.

### 5.19 Health ping (outside `/v1/`)

`GET /ping` on the same `/kobo/{token}` service base returns the literal string `pong`; it is an explicit local code route rather than a Kobo Store endpoint. Its successful invocation and exact Content-Type are **not captured** in these logs. Source: `KoboController.kt:209–210`.


## 6. Endpoint reference — Kobo Store and user services (through Komga)

Unless otherwise stated, these endpoints are handled by `KoboController.catchAll()` and **STANDARD PROXY**, not implemented by dedicated Komga controllers. A `200` in the capture means a request passed through the device-facing Komga URL; it does not make the underlying Store operation local. The `KoboProxy` response translator has explicit branches only for `/v1/products`, `/v1/products/{id}/recommendations`, `/v1/products/{id}/nextread`, and `/v1/products/{id}/reviews`. Unhandled JSON structures pass through without generic identity rewriting (`KoboProductResponseTranslator.kt:24–65`).

### 6.1 User profile

`GET /v1/user/profile`  
**Request:** No body.  
**Response:** Account/storefront metadata: `IsOneStore`, `StoreFront`, `CountryCode`, `Geo`, `IsoCultureCode`, `LoyaltyDetails`, `HasPurchased`, feature/account flags and redacted personal fields.  
**Observed HTTP/evidence:** 200; nickel:L204–246; komga:L10–12.  
**Komga behavior / limitations:** No local profile persistence in Komga route demonstrated; upstream profile influences device Store context.

### 6.2 Loyalty benefits

`GET /v1/user/loyalty/benefits`  
**Request:** No body.  
**Response:** `Benefits.KoboLoveBasic`, `Benefits.KoboLoveVIP`, earning/spending rates and membership product fields.  
**Observed HTTP/evidence:** 200; nickel:L252–294; komga:L19–21.  
**Komga behavior / limitations:** Store account feature; not a Komga rewards implementation.

### 6.3 Subscription products

`GET /v1/products/books/subscriptions`  
**Request:** No body.  
**Response:** Array of plan objects with `Id`, `Name`, `CrossRevisionId`, `Tiers[].Phases[]`, prices and billing intervals.  
**Observed HTTP/evidence:** 200; nickel:L300–342; komga:L22–23.  
**Komga behavior / limitations:** Specifically forwarded RAW by the hybrid book-detail controller for the reserved `subscriptions` segment.

### 6.4 Deals

`GET /v1/deals`  
**Request:** No body.  
**Response:** `{"Deals":[{"Id":"…","Name":"…","AssetGroup":"…","Url":"…","From":"…"}]}`.  
**Observed HTTP/evidence:** 200; nickel:L356–391; komga:L24–26.  
**Komga behavior / limitations:** Feeds promotions/assets; not a Komga local deal engine.

### 6.5 Asset diff

`GET /v1/assets?DiffRequests=[…]`  
**Request:** `DiffRequests` query carries asset keys and ETags; sample references subscription promotion group.  
**Response:** `{"AssetGroups":[{"Key":"…","ETag":"…","Assets":[],"Template":"Subscriptions-Single"}],"KeepGoing":false}`.  
**Observed HTTP/evidence:** 200; nickel:L407–456; komga:L27–29.  
**Komga behavior / limitations:** Read-only; do not treat ETags as authorization credentials, but keep them redacted as requested.

### 6.6 Analytics tests

`POST /v1/analytics/gettests`  
**Request:** `{"AffiliateName":"Kobo","ApplicationVersion":"4.46.23836","PlatformId":"…","SerialNumber":"[REDACTED]","TestKey":"…"}`.  
**Response:** `{"Result":"Success","TestKey":"…","Tests":{}}`.  
**Observed HTTP/evidence:** 200; nickel:L459–498; komga:L30–32.  
**Komga behavior / limitations:** A local `analyticsGetTests` function exists but its mapping is **commented out**; the POST is proxied.

### 6.7 Product search

`GET /v1/products?q={query}&Filters=%7B%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook`  
**Request:** `q` can contain text or a local Komga series/book ID; filters and type inclusion are query parameters.  
**Response:** Paginated `Items:[{"Book":{…}}]` plus `ItemCount`, `TotalItemCount`, `CurrentPageIndex`, `ItemsPerPage`, `Filters`, `VersionCode`. A *captured individual request* returned an empty result.  
**Observed HTTP/evidence:** 200; nickel:L31173–31217; komga:L640–659.  
**Komga behavior / limitations:** Standard proxy can translate recognized local series search terms and mapped response identities; an empty search result is not a missing HTTP endpoint.

### 6.8 Product recommendation list

`GET /v1/products/{productId}/recommendations?page_index=0&page_size=5`  
**Request:** Product ID can be native Kobo UUID or local Komga ID with resolvable mapping.  
**Response:** Paginated `Items:[{"Book":{…}}]`, count/page fields and `VersionCode`; captured local-ID result contains translated Komga book identifiers.  
**Observed HTTP/evidence:** 200; nickel:L18459–18502,L18789–18828; komga:L130–155.  
**Komga behavior / limitations:** Standard proxy translates request ID, selected response book identities, cover/description/series tuple when locally matched. A particular response may legitimately have zero items.

### 6.9 Next-read recommendation

`GET /v1/products/{productIds}/nextread`  
**Request:** One or more Kobo ProductIds in the path; captures show one UUID.  
**Response:** JSON **object keyed by requested product ID**, each value an array of next-read book objects. Not the same shape as `/recommendations`.  
**Observed HTTP/evidence:** 200; nickel:L25753–25792; komga:L296–298.  
**Komga behavior / limitations:** Standard proxy has a dedicated next-read translation branch; do not collapse into generic `Items` schema.

### 6.10 Product review list

`GET /v1/products/{productIds}/reviews?PageSize=3&SortBy=MostLikedFirst`  
**Request:** One or more IDs; optional `PageSize`, `SortBy`.  
**Response:** `ReviewSummary`, `Items`, `CurrentPageIndex`, `TotalPageCount`; one captured variant includes `Cursor`.  
**Observed HTTP/evidence:** 200; nickel:L27531–27552,L41732–41780; komga:L315–320.  
**Komga behavior / limitations:** Standard proxy can adapt review summaries to local `CrossRevisionId` when mapping is unambiguous.

### 6.11 Submit product review

`POST /v1/products/{productId}/reviews`  
**Request:** Captured request fields: `authorDisplayName`, `body`, `creation_date`, `rating`, `title`. Do not log review text or display name.  
**Response:** The original and later device traces capture `200 OK` with **zero response-body bytes** for review submission. Do not deserialize the successful POST response as a review object.  
**Observed HTTP/evidence:** 200; original `nickel:L27349–27387`, later `review_v2:L56–97,L8001–8047,L8117–8149`.  
**Komga behavior / limitations:** Sensitive write operation forwarded by catch-all; confirm whether the product ID was resolved upstream before relying on this flow. This is not a Komga-local review database.

### 6.12 User review lookup

`GET /v1/user/reviews?ProductIds={productIds}`  
**Request:** Query parameter is `ProductIds`, **not** `/products/{id}/reviews`.  
**Response:** Observed `{"CurrentPageIndex":1,"Items":[],"TotalPageCount":0}` for one device request.  
**Observed HTTP/evidence:** 200; original `nickel:L27300–27345`, `komga:L310–314`; later `review_v2:L13–55,L7950–8001,L8065–8117`.  
**Komga behavior / limitations:** Proxy translates `ProductIds` query if known local mapping exists; an empty personal review list does not prove general absence of public reviews.

### 6.13 Personal recommendations

`GET /v1/user/recommendations?page_index=0&page_size=50&Filters=%7B%7D`  
**Request:** Pagination and filter query.  
**Response:** Standard paginated product `Items` envelope; captured request yielded `Items:[]` in a later interaction.  
**Observed HTTP/evidence:** 200; nickel:L19717–19769,L33115–33154; komga:L236–238.  
**Komga behavior / limitations:** Store-personalized recommendations; not a Komga On Deck feed.

### 6.14 Recommendation feedback

`POST /v1/user/recommendations/feedback`  
**Request:** Captured body `{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"{komgaBookId}"}]}`.  
**Response:** In the original trace, the server logs guard rejection of an untranslated local ID. In the later `Review_v2.redacted.txt` trace, the device receives **204 No Content** for a new request containing two feedback items. HTTP 204 has **no JSON response body**.  
**Observed HTTP/evidence:** Original device `nickel:L33081–33114`, original server `komga:L1260–1262`; later device `review_v2:L9290–9334`.  
**Komga behavior / limitations:** The original fork had an unhandled body-identity path. The later success proves a device-facing acknowledgment only; because no paired later server log is provided, whether Komga translated `RevisionId` and forwarded the request to Kobo Store is **unknown**.

#### Feedback wire contract and translation checklist

**Purpose and relationship to review creation.** This path carries feedback about recommendations, not the `POST /v1/products/{ProductId}/reviews` written-review object. The observed `Rate5Star` and `Rate4Star` values show star-rating feedback types; they do not establish the complete enum of possible feedback types. The device sends one or more entries under `FeedbackItems` and no other top-level keys are present in the two captured requests.

| Leg / capture | Method and URL | Identity / payload | Observed status and response |
|---|---|---|---|
| Original device request, `nickel:L33081–33114` | `POST /kobo/{token}/v1/user/recommendations/feedback` | One `Rate5Star` item, local `RevisionId=0RDTATKHXHPS9` | Paired original Komga server log shows outbound guard rejection (`komga:L1260–1262`); **no successful upstream reply proven**. |
| Later device request, `review_v2:L9290–9334` | Same path and method | Two `Rate4Star` items, local `RevisionId` values as shown in §8.19 | **204 No Content** from the device-facing Komga URL; **no JSON response body**. |
| Upstream Kobo Store | `https://storeapi.kobo.com/v1/user/recommendations/feedback` | The original Komga log shows an attempted proxy target only; it does not log a successful translated outbound feedback body. | **Unknown** for the later transaction. No contemporaneous later Komga server trace was supplied. |

**Request headers observed in the later device POST:** `Accept: application/json`, `Content-Type: application/json`, `Accept-Encoding: gzip`, `Authorization` (redacted), `Accept-Language`, `User-Agent`, and Kobo device/app/platform identification headers. This is an observed header set, not a claim that every header is required by the endpoint. The device-facing response has HTTP status `204`; the later log records no response entity body, and the command completes afterward.

**Implementer note — identity domains must be verified:** `FeedbackItems[i].RevisionId` is the JSON field sent by the device and currently contains a local Komga book ID for local books. The original code's outbound guard detected the untranslated ID. A verified local Komga ID → Kobo catalog mapping is necessary, but the field name alone does not prove whether Kobo Store expects a product UUID, cross-revision UUID, or another revision identity for this operation. Determine that using a *paired, authorized* upstream request/response or an authoritative contract before choosing the translation target. For multi-item requests, preserve `FeedbackType`, item count, order, and any additional JSON fields; check every local ID and run the outbound guard on the final Kobo-bound body. Do not forward an unrecognized local TSID, fabricate a feedback result for unmatched books, or claim the later `204` proves Store delivery.

**Current evidence is not a deployed-code guarantee.** The original source archive documents the original fork; the later device log does not include the updated source or an upstream exchange. The two observations must not be merged into an invented end-to-end successful forwarding transaction.

### 6.15 Wishlist list

`GET /v1/user/wishlist?PageSize=100&PageIndex=0`  
**Request:** Pagination query; conditional request headers in device trace.  
**Response:** Store JSON with `TotalCountByProductType`, `Items`, `ItemCount`, `TotalPageCount`, `TotalItemCount`, `CurrentPageIndex`, `ItemsPerPage`, `VersionCode`.  
**Observed HTTP/evidence:** 304 DEVICE for cached exchanges; 200 SERVER in observed upstream calls; nickel:L31785–31820; komga:L7–9.  
**Komga behavior / limitations:** The two codes refer to distinct response legs/requests; do not claim the forwarded Store exchange necessarily returned 304.

### 6.16 Wishlist add/remove batch

`POST /v1/user/wishlist/items`  
**Request:** `{"Add":[{"CrossRevisionId":"{revisionId}","DateAdded":"2026-09-22T11:53:24Z"}],"Remove":[]}`.  
**Response:** `{"SuccessfulIds":["{revisionId}"]}`.  
**Observed HTTP/evidence:** 200 DEVICE; nickel:L45717–45756.  
**Komga behavior / limitations:** Captures show a genuine Kobo revision ID, not local Komga ID; Komga does not implement a local wishlist.

### 6.17 Featured lists

`GET /v1/products/featured/?page_index=0&page_size=100&TypesToInclude=book,audiobook`  
**Request:** Paging and type parameters.  
**Response:** Paginated list with `Items`, `ItemCount`, `TotalItemCount`, `CurrentPageIndex`, `ItemsPerPage`, `VersionCode`.  
**Observed HTTP/evidence:** 200; nickel:L19771–19816; komga:L239–241.  
**Komga behavior / limitations:** Store discovery lists; not interchangeable with featured-list contents.

### 6.18 Featured-list contents

`GET /v1/products/featured/{FeaturedListId}?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook`  
**Request:** FeaturedListId path UUID plus paging and filters.  
**Response:** Paginated `Items` of catalog products; `Filters` may be returned.  
**Observed HTTP/evidence:** 200; nickel:L19818–19860; komga:L242–244.  
**Komga behavior / limitations:** Separate endpoint with same family prefix but different resource.

### 6.19 Product bulk prices

`GET /v1/products/{id1},{id2}/prices`  
**Request:** Multiple UUIDs in one path; actual raw path can be longer.  
**Response:** `{"Items":[…]}`; compare hybrid price route in §5.  
**Observed HTTP/evidence:** 200; nickel:L30582–31162; komga:L489–491.  
**Komga behavior / limitations:** Requests with only remote IDs are handled by raw hybrid controller.


## 7. Adjacent Kobo services outside Komga's `/v1/` Store route

These are **separate network services observed in the e-reader log**. They should not be misclassified as URLs serviced by Komga's catch-all controller merely because the Kobo client uses them during the same sync/session. Their request/response contracts are only partially visible.

| Service and observed request | Captured status / role | Evidence and gap |
|---|---|---|
| `POST https://readingservices.kobo.com/api/v3/content/checkforchanges` | Captured `200`; checks annotation/content changes. | `nickel:L18304` onward; request and body details are service-specific. |
| `GET https://readingservices.kobo.com/api/v3/content/{contentId}/annotations` | Captured `200` for several calls; JSON may include `annotations` and `nextPageOffsetToken`. | `nickel:L2703` onward; do not assume these annotations are mapped into Komga's own reading-progress repository. |
| `GET https://readingservices.kobo.com/api/UserStorage/Metadata[/ {id}]` and `/api/UserStorage/Object/{id}` | Metadata and notebook/user-storage operations; various requests lack a clearly paired status in the capture. | `nickel:L2076–2091,L18363` onward; independent service. |
| `GET https://api.kobobooks.com/1.0/UpgradeCheck/Device/{platformId}/Kobo/{firmware}/{build}` | Captured `200`; example `{"Data":null,"ReleaseNoteURL":null,"UpgradeType":0,"UpgradeURL":null}`. | `nickel:L78–110`; firmware check, not Store API. |
| `GET https://ereaderfiles.kobo.com/elabels/N428.zip` | Download/cache exchange for device e-labels; device records date, binary length, Last-Modified; not consistently explicit HTTP status. | `nickel:L13–42`. |
| `GET https://ereaderfiles.kobo.com/ereader/dictionaries/v3//dicthtml-*.zip`, `/ereader/fonts/*.otf`, `/ereader/iink/v2/*.zip`, `/ereader/guide/monza/userguide_en_US.pdf` | Device resource downloads; ordinary binary/file flows, not JSON Store endpoints. | `nickel:L25167–25713`. |
| `GET https://cdn.kobo.com/book-images/{imageId}/{width}/{height}/false/image.jpg` | Captured `200` or `307` in related image requests. | `nickel:L18592,L39400` onward; image delivery/CDN. |
| `GET https://storedownloads.kobo.com/download?downloadToken={secret}` or files under `getbook.kobo.com/koboid-prod-public/...` | Download URL signed or opaque; content binary, not a metadata API. | `nickel:L25879–26383`; never publish live `downloadToken`. |


