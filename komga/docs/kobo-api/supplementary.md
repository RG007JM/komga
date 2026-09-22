# Supplementary evidence, registry, and later captures

[← Kobo API index](../kobo-api.md) · [Endpoint reference](reference.md) · [JSON payloads](payloads/README.md)

### 8.A. 38 device-response lines that do not parse as JSON after sanitization — exact locator register

The register identifies **38 distinct lines in the supplied sanitized device log, not 38 different endpoints or 38 failed network responses**. All are associated with a device-recorded HTTP `200`. When extracted as standalone JSON from the *sanitized text supplied here*, these entries do not parse. That is a limitation of these extracted artifacts, **not a finding that Kobo returned invalid or incomplete JSON**. Some lines repeat the same visible text. They are excluded from the parseable-fixture collection rather than being silently repaired.

Request URLs and statuses were correlated on the same Nickel thread. Reported `Content-Length` is an HTTP header from before sanitization and is **not** compared against the length of the sanitized text; neither the parser error nor a byte-count comparison can establish whether the original response was incomplete. A sanitizer replacement, logging/export behavior, or another capture transformation may explain the difference; its cause has not been verified.

| Original device line | HTTP | Request URL after `/kobo/{token}` | Parser result for sanitized text |
|---:|:---:|---|---|
| `nickel:L2516` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L2869` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATKG1HM0V?ExcludeOwned=false&PageSize=100` | Expecting ':' delimiter |
| `nickel:L3727` | `200` | `GET /kobo/{token}/v1/products/books/series/0RKGS49PPFETD?ExcludeOwned=false&PageSize=100` | Expecting value |
| `nickel:L4791` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATKF5HM5A?ExcludeOwned=false&PageSize=100` | Expecting ',' delimiter |
| `nickel:L5632` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATKG1HM0V?ExcludeOwned=false&PageSize=100` | Expecting ':' delimiter |
| `nickel:L18498` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/recommendations?page_index=0&page_size=5` | Unterminated string starting at |
| `nickel:L18914` | `200` | `GET /kobo/{token}/v1/products/0RDTATJQNHSQ0/recommendations?page_index=0&page_size=5` | Expecting ',' delimiter |
| `nickel:L19294` | `200` | `GET /kobo/{token}/v1/products/cb4922b2-7577-4fa6-878a-d19d1891322a/recommendations?page_index=0&page_size=5` | Unterminated string starting at |
| `nickel:L19650` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L19698` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATKG1HM0V?ExcludeOwned=false&PageSize=100` | Expecting ':' delimiter |
| `nickel:L19857` | `200` | `GET /kobo/{token}/v1/products/featured/81844d90-8f90-40b0-98e4-f0cb6c3534e7?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L19969` | `200` | `GET /kobo/{token}/v1/products/featured/5f08e984-b1c0-c28e-aab3-08dc44f596cd?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Expecting value |
| `nickel:L20025` | `200` | `GET /kobo/{token}/v1/products/featured/fc521346-a71d-c639-329e-08dabb85e7c5?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L20081` | `200` | `GET /kobo/{token}/v1/products/featured/d037e29d-052a-c92d-da3c-08dabc0dd8ed?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L20137` | `200` | `GET /kobo/{token}/v1/products/featured/0bd27bb0-0159-c3b8-3cd0-08dabc0e6802?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Expecting ',' delimiter |
| `nickel:L20193` | `200` | `GET /kobo/{token}/v1/products/featured/7c088551-070f-ccdf-1740-08dabc0eb416?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Expecting ',' delimiter |
| `nickel:L20249` | `200` | `GET /kobo/{token}/v1/products/featured/4db8e924-6cf8-c7a3-e468-08dabc0ffed4?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Expecting ',' delimiter |
| `nickel:L20471` | `200` | `GET /kobo/{token}/v1/products/featured/47218d05-b1e0-cf64-c055-08d84aa5aa4b?page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook` | Expecting ',' delimiter |
| `nickel:L25146` | `200` | `GET /kobo/{token}/v1/products/featured/81844d90-8f90-40b0-98e4-f0cb6c3534e7?page_index=0&page_size=3&Filters=%7B%7D&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L25792` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/nextread` | Unterminated string starting at |
| `nickel:L30505` | `200` | `GET /kobo/{token}/v1/products/0RDTATKHXHPS9/reviews?PageSize=50&SortBy=MostLikedFirst` | Expecting ',' delimiter |
| `nickel:L31214` | `200` | `GET /kobo/{token}/v1/products?q=0RDTATKG1HM0V&Filters=%7B%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L31588` | `200` | `GET /kobo/{token}/v1/products/books/series/0RE3XW0WTEPX6?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L33069` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L33584` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/nextread` | Unterminated string starting at |
| `nickel:L33821` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJ4DHY6Z?ExcludeOwned=false&PageSize=100` | Expecting ',' delimiter |
| `nickel:L35603` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L35651` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJ4DHY6Z?ExcludeOwned=false&PageSize=100` | Expecting ',' delimiter |
| `nickel:L36139` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/nextread` | Unterminated string starting at |
| `nickel:L37150` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L37198` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJ4DHY6Z?ExcludeOwned=false&PageSize=100` | Expecting ',' delimiter |
| `nickel:L37686` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/nextread` | Unterminated string starting at |
| `nickel:L38719` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJHXHZRM?ExcludeOwned=false&PageSize=100` | Unterminated string starting at |
| `nickel:L38767` | `200` | `GET /kobo/{token}/v1/products/books/series/0RDTATJ4DHY6Z?ExcludeOwned=false&PageSize=100` | Expecting ',' delimiter |
| `nickel:L39255` | `200` | `GET /kobo/{token}/v1/products/42ed6d94-1f32-4df9-9d97-023335a4eb9c/nextread` | Unterminated string starting at |
| `nickel:L41976` | `200` | `GET /kobo/{token}/v1/products/92329c27-e655-4caf-aa15-54f1354ae9d3/recommendations?page_index=0&page_size=100` | Unterminated string starting at |
| `nickel:L45274` | `200` | `GET /kobo/{token}/v1/products?q=frieren&Filters=%7BLanguage:en%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook` | Unterminated string starting at |
| `nickel:L45630` | `200` | `GET /kobo/{token}/v1/products?q=frieren&Filters=%7BLanguage:en%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook` | Unterminated string starting at |

**Grouping (not endpoint-counting):** Series listing 16; Recommendations 4; Featured product list 9; Next-read recommendations 5; Public review list 1; Product search 3.

**Schema recovery:** For each line, consult another successfully parsed *device-facing* capture of the same endpoint if available; otherwise consult the independently captured upstream response and explicitly label it **UPSTREAM**. Komga may translate identifiers, covers, series fields, and product metadata on the way back to the device. Do not fabricate the missing characters or treat a parser error after sanitization as an API error. If the original unsanitized capture can be parsed locally, sanitize individual JSON string values with a structure-preserving JSON parser rather than trying to repair the redacted text.


### 8.B. Missing contracts and known integration boundary

The boot-time initialization advertises many endpoints with **no corresponding request/response capture**; those remain URL templates, not verified method/JSON contracts. Local controller code can establish a method and DTO class for certain routes, but a DTO definition is not a captured upstream response. Important unobserved or unproven flows include: complete device-auth success/failure, auth refresh, purchasing/checkout, active entitlements and downloads, full tag add/remove/delete round trips, rate-limit and auth-error schemas, all storefronts other than the captured context, and every variant of the book/library JSON under unusual flags.

**Historical failure and later observed acknowledgment, not silently conflated:** The original server log shows `POST /v1/user/recommendations/feedback` blocked for an untranslated `FeedbackItems[].RevisionId`. A **later** device-only log captures a new two-item feedback request receiving `204 No Content`. Neither a later server-side upstream request body nor a later upstream Store response was included; the new device success alone does not prove that product/revision identity translation was deployed or performed.

**Review submission:** separate from recommendation feedback. The original and later device `POST /v1/products/{komgaId}/reviews` requests received HTTP `200` with **empty bodies**. In the source fork, product-path translation can resolve a local product ID; the server log supports the observed upstream acceptance for the captured mapped product. Publication/moderation was not established.


### 8.C. Translation-layer field map (source code versus captured JSON)

These entries are **source-code behavior for the supplied fork**, not invented Kobo-native contracts. Inbound = device → Komga → Store. Outbound = Store → Komga → device. Do not use a single generic `Id` converter for all endpoint families: several fields and even a next-read **object key** carry different identities.

| Endpoint / layer | Exact path or URL component | Supplied-fork behavior / implementation requirement | Source |
|---|---|---|---|
| Book lists `/v1/products` and `/{id}/recommendations`, outbound | `Items[].Book.Id` | If a Kobo product has a visible unique local match, replace the product ID with the local Komga book ID. A known mapping takes priority; an unambiguous ISBN lookup is fallback. Unmatched Store products remain native. | `KoboProductResponseTranslator.kt:68–188` |
| Same, outbound | `Items[].Book.CrossRevisionId`, `.WorkId`, `.RevisionId` | Rewrite an existing textual field for locally matched items; do **not** synthesize a field that was absent. These fields are presented locally as Komga identity but may represent different native Kobo identifiers. | `KoboProductResponseTranslator.kt:153–177` |
| Same, outbound | `Items[].Book.ImageId`, `.Description`, `.SeriesId`, `.SeriesName`, `.SeriesNumber` (series tuple) | Matched book may receive local Komga metadata/cover and series overlay. Unmatched Store items retain upstream fields. The JSON fixtures contain real nested price, accessibility, contributor and other fields which are not generic identity fields. | `KoboProductResponseTranslator.kt:93–99,179–190`, local builder |
| `/{id}/nextread`, outbound | **Root object key** `/{requestedProductId}` and each array element's `.Id`, `.RevisionId`, `.CrossRevisionId`, `.WorkId`, `.ImageId` | Root keys are IDs, not an `Items` array. Translate root key and matched item identities separately, preserving other fields/array ordering. | `KoboProductResponseTranslator.kt:193–327` |
| `/{id}/reviews`, outbound | `Items[].ProductId` or supported cross-revision representation | Review JSON is a different object family from `Items[].Book`. Follow the review-specific translator and inspect actual capture before changing `RevisionId` or `CrossRevisionId`. | `KoboProductResponseTranslator.kt:47–55,330–407` |
| Product writes/read lookup, inbound | `/v1/products/{productIds}/reviews`, `/recommendations`, `/nextread`, `/related`; `?ProductIds=...` on `/v1/user/reviews` | Translate supported *path/query product identifiers* using the verified resolver, including comma-separated IDs. Preserve native UUIDs; the outbound guard prevents forwarding an unresolved genuine Komga TSID. | `KoboProxy.kt:68–91,417–500` |
| Review submission, inbound | `POST /v1/products/{productId}/reviews` URL and JSON `{authorDisplayName, body, creation_date, rating, title}` | Product ID is translated in URL; request body does **not** carry this book identity in the observed request. Upstream `200` has an empty response, not `{}`. Publishing/moderation is not observed. | Device `nickel:L27349–27387`; server review mapping logs |
| Recommendation feedback, inbound | `POST /v1/user/recommendations/feedback` → `FeedbackItems[].RevisionId` | **Original fork had an unhandled request-body identity.** Original guard blocked the observed local ID. A later device-facing `204` is observed, but this alone does not establish that the server version was changed, translated the body, or contacted the Store. A Kobo *product* match is not proof that this endpoint accepts the product UUID in a `RevisionId` field. | Device `nickel:L33081–33114`; server `komga:L1260–1262`; `KoboProxy.kt` |
| Tag requests, inbound | `Items[].RevisionId`, plus `Items[].Type` and root `Name` | Local tag mutation uses read-list IDs and local/external membership. `KoboTagRequestTranslator` can translate a local member to a resolved Kobo ProductId for forwarding where that translator is invoked; preserve `Type`, `Name`, and other fields. Do not forward local-only IDs blindly. | `KoboTagRequestTranslator.kt:17–82`; `KoboController.kt:922–1159` |
| Book detail, hybrid | `/v1/products/books/{bookId}` and nested `Id`, `CrossRevisionId`, `WorkId`, `SeriesId`, `ImageId` | For a local book, resolve the Kobo ProductId, fetch raw upstream book JSON if available, and let the local builder overlay local metadata. For a native Kobo ID, return upstream raw book details. No universal field-by-field mapping can be inferred just from a raw body. | `KoboRemainingEndpointsController.kt:38–109` |
| Series, hybrid | `/v1/products/books/series/{seriesId}`; `Items[].Book.SeriesId`, `.Id`, `.ISBN`, `.SeriesNumber` | Resolve local series ID separately from product ID, query upstream series when mapped, retain Komga's authoritative series membership, and enrich local members. An upstream series may include Kobo-only products; it is not a replacement for the local membership list. | `KoboRemainingEndpointsController.kt:111–175` |
| Prices, hybrid | `/v1/products/{productIds}/prices` and `Items[]` | Split comma-separated local and native product IDs; synthesize local items and merge upstream Kobo prices for nonlocal IDs. Use the captured price item JSON, not assumptions from book-detail `Price`. | `KoboRemainingEndpointsController.kt:206–259` |
| Library sync, local+Store | `NewEntitlement`, `ChangedEntitlement`, tag/read-state envelopes, `x-kobo-synctoken` | Local checkpoint/continuation state and upstream Store sync tokens are distinct. Treat the response as an **array of variant envelopes**; don't use ordinary product-list translation on it. The sync token is a header and is redacted in fixtures. | `KoboController.kt:289–632` |

**Machine-readable field-path index:** The companion `Kobo_Komga_JSON_Field_Paths_2026-09-22.json` and ZIP member `field_path_index.json` enumerate every *observed* nested field path and primitive/container type by endpoint, request/response leg and source layer, including optional fields that occur in only one recorded response. `observedInFixtures` measures the number of distinct JSON fixtures containing a path, **not** field frequency inside arrays or validation requiredness. This is a capture-derived data dictionary, not a fabricated OpenAPI schema.


## 9. Payload shapes and semantics that must not be conflated

| Family | Top-level JSON | Important nested content | Typical use |
|---|---|---|---|
| Sync | Array | `NewEntitlement`, `ChangedEntitlement`, `NewTag`, progress changes | Device library state machine. |
| Product search/recommendations/featured/series | Object containing `Items` | Usually `Items[].Book` (or other product variants), paging + filters | Catalog grids and series view. |
| Next-read | Object keyed by ProductId | Product object arrays under each requested key | Continue-series hints. |
| Product detail | Single product object | `Id`, `CrossRevisionId`, `ISBN`, series tuple, image, price, subscription metadata | Book detail view. |
| Product reviews | Object | `ReviewSummary`, `Items`, optional `Cursor`, paging | Public review display. |
| User reviews | Object | `Items`, current/total page indices | Account-specific review list. |
| User wishlist | Object | `TotalCountByProductType`, `Items`, paging | Account wishlisting. |
| Review submission | Request object | `authorDisplayName`, `body`, `creation_date`, `rating`, `title` | Write; should not log full text. |
| Reading state | Array for GET; object for PUT | Bookmark, location, status, statistics; `RequestResult` and `UpdateResults` on PUT response | Local progress persistence. |
| Tag creation | Request object; **JSON string** response on local success | `Name`, `Items[].RevisionId` | Canonical new collection identity. |

### 9.1 Additional short JSON examples

**Device feature test** (`POST /v1/analytics/gettests`), with the identifying request fields replaced:

```http
POST /kobo/{token}/v1/analytics/gettests HTTP/1.1
Content-Type: application/json

{"AffiliateName":"Kobo","ApplicationVersion":"4.46.23836","PlatformId":"00000000-0000-0000-0000-000000000390","SerialNumber":"[REDACTED]","TestKey":"{testKey}"}
```

```json
{"Result":"Success","TestKey":"{testKey}","Tests":{}}
```

**Captured empty user review search** (`GET /v1/user/reviews?ProductIds={komgaBookId}`):

```json
{"CurrentPageIndex":1,"Items":[],"TotalPageCount":0}
```

**Captured feedback payload with confirmed rejection** (`POST /v1/user/recommendations/feedback`):

```json
{"FeedbackItems":[{"FeedbackType":"Rate5Star","RevisionId":"{komgaBookId}"}]}
```

**Captured wishlist mutation** (`POST /v1/user/wishlist/items`):

```json
{"Add":[{"CrossRevisionId":"{koboRevisionId}","DateAdded":"2026-09-22T11:53:24Z"}],"Remove":[]}
```

```json
{"SuccessfulIds":["{koboRevisionId}"]}
```

**Captured collection creation** (`POST /v1/library/tags`):

```json
{"Name":"Example shelf","Items":[{"RevisionId":"{komgaBookId}","Type":"ProductRevisionTagItem"}]}
```

```json
"{canonicalKomgaReadListId}"
```

The examples above preserve the observed field names and object/array/string distinctions. The illustrative values and ID placeholders do **not** constitute a complete schema or validation contract.


## 10. Initialization resource registry — advertised is not implemented

The 22 September device initialization reply contains **183 distinct resource/feature keys**. The registry below covers the complete keys observed in that reply rather than presenting each advertised URL as a confirmed invocation. `METHOD NOT PROVEN` means `/v1/initialization` alone supplies a URL, not its verbs, payload, status, or permissions. Individual exercised routes are documented in §§5–7. `Feature flag`, `page`, `host`, and `configuration object` entries are **not HTTP endpoints** despite appearing in the same `Resources` object. Evidence: `nickel:L197`, `komga:L13–15`.

**`account_page`** — advertised URL: `https://www.kobo.com/account/settings`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`account_page_rakuten`** — advertised URL: `https://my.rakuten.co.jp/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`add_device`** — advertised API template: `https://storeapi.kobo.com/v1/user/add-device`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`add_entitlement`** — advertised API template: `https://storeapi.kobo.com/v1/library/{RevisionIds}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`affiliaterequest`** — advertised API template: `https://storeapi.kobo.com/v1/affiliate`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`assets`** — advertised API template: `https://storeapi.kobo.com/v1/assets`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook`** — advertised API template: `https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook_detail_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/audiobook/{slug}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook_landing_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/audiobooks`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook_preview`** — advertised API template: `https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook_purchase_withcredit`** — advertised API template: `https://storeapi.kobo.com/v1/store/audiobook/{Id}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`audiobook_subscription_orange_deal_inclusion_url`** — advertised URL: `https://authorize.kobo.com/inclusion`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`authorproduct_recommendations`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/authors/recommendations`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`autocomplete`** — advertised API template: `https://storeapi.kobo.com/v1/products/autocomplete`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`bam`** — advertised API template: `https://storeapi.kobo.com/v2/activity/bam/success`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`blackstone_header`** — configuration object: `key: x-amz-request-payer, value: requester`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`book`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/{ProductId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`book_detail_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/ebook/{slug}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`book_detail_page_rakuten`** — advertised URL: `http://books.rakuten.co.jp/rk/{crossrevisionid}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`book_landing_page`** — advertised URL: `https://www.kobo.com/ebooks`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`book_subscription`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/subscriptions`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`browse_history`** — advertised API template: `https://storeapi.kobo.com/v1/user/browsehistory`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`categories`** — advertised API template: `https://storeapi.kobo.com/v1/categories`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`categories_page`** — advertised URL: `https://www.kobo.com/ebooks/categories`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`categoriesv2`** — advertised API template: `https://storeapi.kobo.com/api/v2/Categories/Top`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`category`** — advertised API template: `https://storeapi.kobo.com/v1/categories/{CategoryId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`category_featured_lists`** — advertised API template: `https://storeapi.kobo.com/v1/categories/{CategoryId}/featured`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`category_products`** — advertised API template: `https://storeapi.kobo.com/v1/categories/{CategoryId}/products`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`checkout_borrowed_book`** — advertised API template: `https://storeapi.kobo.com/v1/library/borrow`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`client_authd_referral`** — advertised API template: `https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`configuration_data`** — advertised API template: `https://storeapi.kobo.com/v1/configuration`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`content_access_book`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/{ProductId}/access`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`contributorsv2`** — advertised API template: `https://storeapi.kobo.com/v2/contributors/author`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`createpurchaseifallowed_url`** — advertised URL: `https://www.kobo.com/checkout/createpurchaseifallowed`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`customer_care_live_chat`** — advertised URL: `https://v2.zopim.com/widget/livechat.html?key=Y6gwUmnu4OATxN3Tli4Av9bYN319BTdO`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`daily_deal`** — advertised API template: `https://storeapi.kobo.com/v1/products/dailydeal`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`deals`** — advertised API template: `https://storeapi.kobo.com/v1/deals`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`delete_entitlement`** — advertised API template: `https://storeapi.kobo.com/v1/library/{Ids}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`delete_tag`** — advertised API template: `https://storeapi.kobo.com/v1/library/tags/{TagId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`delete_tag_items`** — advertised API template: `https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`delete_user_linked_accounts`** — advertised API template: `https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`device_auth`** — advertised API template: `https://storeapi.kobo.com/v1/auth/device`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`device_refresh`** — advertised API template: `https://storeapi.kobo.com/v1/auth/refresh`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`dictionary_host`** — advertised URL: `https://ereaderfiles.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`discovery_host`** — advertised URL: `https://discovery.kobobooks.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`display_accessibility_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`display_parental_controls_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`dropbox_link_account_poll`** — advertised URL: `https://authorize.kobo.com/{region}/{language}/LinkDropbox`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`dropbox_link_account_start`** — advertised URL: `https://authorize.kobo.com/LinkDropbox/start`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`elabel_url`** — advertised URL: `https://ereaderfiles.kobo.com/elabels/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`ereaderdevices`** — advertised API template: `https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`eula_page`** — advertised URL: `https://www.kobo.com/termsofuse?style=onestore`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`exchange_auth`** — advertised API template: `https://storeapi.kobo.com/v1/auth/exchange`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`external_book`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/external/{Ids}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`facebook_sso_page`** — advertised URL: `https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`featured_list`** — advertised API template: `https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`featured_lists`** — advertised API template: `https://storeapi.kobo.com/v1/products/featured`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`featuredlist2`** — advertised API template: `https://storeapi.kobo.com/v2/products/list/featured`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`fixed_layout_page_cache_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`free_books_page`** — configuration object: `EN: https://www.kobo.com/{region}/{language}/p/free-ebooks, FR: https://www.kobo.com/{region}/{language}/p/livres-gratuits, IT: https://www.kobo.com/{region}/{language}/p/libri-gratuiti, NL: https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg, PT: https://www.kobo.com/{region}/{language}/p/livros-gratis`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`funnel_metrics`** — advertised API template: `https://storeapi.kobo.com/v1/funnelmetrics`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`geography_data`** — advertised API template: `https://storeapi.kobo.com/v2/configuration/geography/country`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`get_download_keys`** — advertised API template: `https://storeapi.kobo.com/v1/library/downloadkeys`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`get_download_link`** — advertised API template: `https://storeapi.kobo.com/v1/library/downloadlink`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`get_tests_request`** — advertised API template: `https://storeapi.kobo.com/v1/analytics/gettests`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`giftcard_epd_redeem_url`** — advertised URL: `https://www.kobo.com/{storefront}/{language}/redeem-ereader`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`giftcard_redeem_url`** — advertised URL: `https://www.kobo.com/{storefront}/{language}/redeem`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`googledrive_link_account_start`** — advertised URL: `https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`gpb_flow_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`help_page`** — advertised URL: `https://www.kobo.com/help`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`image_host`** — advertised URL: `https://komga.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`image_url_quality_template`** — advertised API template: `https://komga.com/kobo/komga_token/v1/books/{ImageId}/thumbnail/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`image_url_template`** — advertised API template: `https://komga.com/kobo/komga_token/v1/books/{ImageId}/thumbnail/{Width}/{Height}/false/image.jpg`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`instapaper_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`instapaper_env_url`** — advertised API template: `https://www.instapaper.com/api/kobo`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`instapaper_link_account_start`** — advertised URL: `https://authorize.kobo.com/{region}/{language}/linkinstapaper`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_audiobooks_credit_redemption`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_audiobooks_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_audiobooks_orange_deal_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_audiobooks_subscriptions_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_display_price`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_dropbox_link_account_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_google_tax`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_googledrive_link_account_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_nativeborrow_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_onedrive_link_account_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_onestorelibrary_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_privacyCentre_url`** — advertised URL: `https://www.kobo.com/privacy`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_redeem_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_shelfie_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_shopping_cart_enabled`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_subscriptions_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_superpoints_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`kobo_wishlist_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_book`** — advertised API template: `https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_items`** — advertised API template: `https://storeapi.kobo.com/v1/user/library`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_metadata`** — advertised API template: `https://storeapi.kobo.com/v1/library/{Ids}/metadata`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_prices`** — advertised API template: `https://storeapi.kobo.com/v1/user/library/previews/prices`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_search`** — advertised API template: `https://storeapi.kobo.com/v1/library/search`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`library_sync`** — advertised API template: `https://storeapi.kobo.com/v1/library/sync`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`love_dashboard_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/kobosuperpoints`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`love_points_redemption_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`magazine_landing_page`** — advertised URL: `https://www.kobo.com/emagazines`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`more_sign_in_options`** — advertised URL: `https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`morebyauthor`** — advertised API template: `https://storeapi.kobo.com/v2/products/recommendations/morebyauthor`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`notebooks`** — advertised API template: `https://storeapi.kobo.com/api/internal/notebooks`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`notifications_registration_issue`** — advertised API template: `https://storeapi.kobo.com/v1/notifications/registration`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`oauth_host`** — advertised URL: `https://oauth.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`password_retrieval_page`** — advertised URL: `https://www.kobo.com/passwordretrieval.html`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`patch_user_linked_accounts`** — advertised API template: `https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`personalizedrecommendations`** — advertised API template: `https://storeapi.kobo.com/v2/users/personalizedrecommendations`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`pocket_link_account_start`** — advertised URL: `https://authorize.kobo.com/{region}/{language}/linkpocket`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`post_analytics_event`** — advertised API template: `https://storeapi.kobo.com/v1/analytics/event`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`ppx_purchasing_url`** — advertised URL: `https://purchasing.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`privacy_page`** — advertised URL: `https://www.kobo.com/privacypolicy?style=onestore`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`product_nextread`** — advertised API template: `https://storeapi.kobo.com/v1/products/{ProductIds}/nextread`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`product_prices`** — advertised API template: `https://storeapi.kobo.com/v1/products/{ProductIds}/prices`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`product_recommendations`** — advertised API template: `https://storeapi.kobo.com/v1/products/{ProductId}/recommendations`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`product_reviews`** — advertised API template: `https://storeapi.kobo.com/v1/products/{ProductIds}/reviews`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`productbyid`** — advertised API template: `https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`productbyslug`** — advertised API template: `https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`products`** — advertised API template: `https://storeapi.kobo.com/v1/products`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`productstatebyid`** — advertised API template: `https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`productstatebyslug`** — advertised API template: `https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`productsv2`** — advertised API template: `https://storeapi.kobo.com/v2/products`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`provider_external_sign_in_page`** — advertised URL: `https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`purchase_buy`** — advertised URL: `https://www.kobo.com/checkoutoption/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`purchase_buy_templated`** — advertised URL: `https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`quickbuy_checkout`** — advertised API template: `https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`quickbuy_create`** — advertised API template: `https://storeapi.kobo.com/v1/store/quickbuy/purchase`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`rakuten_token_exchange`** — advertised API template: `https://storeapi.kobo.com/v1/auth/rakuten_token_exchange`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`rating`** — advertised API template: `https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`reading_services_host`** — advertised URL: `https://readingservices.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`reading_state`** — advertised API template: `https://storeapi.kobo.com/v1/library/{Ids}/state`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`recommendations`** — advertised API template: `https://storeapi.kobo.com/v1/products/bulk`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`redeem_interstitial_page`** — advertised URL: `https://www.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`redeem_loyalty_points`** — advertised API template: `https://storeapi.kobo.com/v1/user/loyalty/redeem`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`reflowable_page_cache_enabled`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`registration_page`** — advertised URL: `https://authorize.kobo.com/signup?returnUrl=https://kobo.com/`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`related`** — advertised API template: `https://storeapi.kobo.com/v2/products/recommendations/related`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`related_items`** — advertised API template: `https://storeapi.kobo.com/v1/products/{Id}/related`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`remaining_book_series`** — advertised API template: `https://storeapi.kobo.com/v1/products/books/series/{SeriesId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`rename_tag`** — advertised API template: `https://storeapi.kobo.com/v1/library/tags/{TagId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`review`** — advertised API template: `https://storeapi.kobo.com/v1/products/reviews/{ReviewId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`review_sentiment`** — advertised API template: `https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`sepa_banks`** — advertised API template: `https://storeapi.kobo.com/v2/purchasing/sepa/banks`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`shelfie_recommendations`** — advertised API template: `https://storeapi.kobo.com/v1/user/recommendations/shelfie`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`sign_in_page`** — advertised URL: `https://auth.kobobooks.com/ActivateOnWeb`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`social_authorization_host`** — advertised URL: `https://social.kobobooks.com:8443`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`social_host`** — advertised URL: `https://social.kobobooks.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`store_home`** — advertised URL: `www.kobo.com/{region}/{language}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`store_host`** — advertised URL: `www.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`store_newreleases`** — advertised URL: `https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`store_search`** — advertised URL: `https://www.kobo.com/{region}/{language}/Search?Query={query}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`store_top50`** — advertised URL: `https://www.kobo.com/{region}/{language}/ebooks/Top`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`subs_landing_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/plus`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`subs_management_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/account/subscriptions`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`subs_plans_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/plus/plans`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`subs_purchase_buy_templated`** — advertised URL: `https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`tag_items`** — advertised API template: `https://storeapi.kobo.com/v1/library/tags/{TagId}/Items`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`tags`** — advertised API template: `https://storeapi.kobo.com/v1/library/tags`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`terms_of_sale_page`** — advertised URL: `https://authorize.kobo.com/{region}/{language}/terms/termsofsale`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`text_to_speech_region_override`** — feature flag: `False`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`topproducts`** — advertised API template: `https://storeapi.kobo.com/v2/products/list/topproducts`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`tracking`** — advertised API template: `https://storeapi.kobo.com/v2/tracking/searchperformed`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`update_accessibility_to_preview`** — advertised API template: `https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`use_one_store`** — feature flag: `True`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_currencyconversion`** — advertised API template: `https://storeapi.kobo.com/v1/user/currency/convert`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_linked_accounts`** — advertised API template: `https://storeapi.kobo.com/v1/user/linkedaccounts`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_loyalty_benefits`** — advertised API template: `https://storeapi.kobo.com/v1/user/loyalty/benefits`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_loyalty_membership`** — advertised API template: `https://storeapi.kobo.com/v1/user/loyalty/membership`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_platform`** — advertised API template: `https://storeapi.kobo.com/v1/user/platform`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_profile`** — advertised API template: `https://storeapi.kobo.com/v1/user/profile`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_ratings`** — advertised API template: `https://storeapi.kobo.com/v1/user/ratings`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_recommendations`** — advertised API template: `https://storeapi.kobo.com/v1/user/recommendations`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_reviews`** — advertised API template: `https://storeapi.kobo.com/v1/user/reviews`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_subscription_koboplus`** — advertised API template: `https://storeapi.kobo.com/v1/user/subscription/kp/state`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_tasteprofile_complete`** — advertised API template: `https://storeapi.kobo.com/v2/user/tasteprofile/complete`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_tasteprofile_genre`** — advertised API template: `https://storeapi.kobo.com/v2/user/tasteprofile/genre`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`user_wishlist`** — advertised API template: `https://storeapi.kobo.com/v1/user/wishlist`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`userguide_host`** — advertised URL: `https://ereaderfiles.kobo.com`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`wishlist_page`** — advertised URL: `https://www.kobo.com/{region}/{language}/account/wishlist`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.

**`workbooks`** — advertised API template: `https://storeapi.kobo.com/v2/products/workbooks`. **Invocation:** see §§5–7 if separately evidenced; otherwise METHOD / STATUS / BODY NOT PROVEN BY INITIALIZATION.


## 11. Exceptions, mismatches and evidence gaps

1. **Feedback: original rejection; later device-facing 204.** The older paired device/server logs show a genuine local Komga TSID rejected before Store transmission (`nickel:L33081–33099`; `komga:L1260–1262`). The new device-only `Review_v2.redacted.txt` trace shows a different two-item request accepted at the device/Komga boundary with `204 No Content` (`review_v2:L9290–9334`). Actual Store forwarding and identifier translation remain unverified without the contemporaneous Komga outbound request/response log.
2. **Generic catch-all must not be claimed as native Komga implementation.** Many Store operations (wishlist, deals, reviews, search, featured lists, recommendations) succeed via upstream forwarding and/or selective ID translation; the fork does not implement local purchasing, store catalog ownership, or all 183 advertised resources. Evidence: `KoboController.kt:1263–1273`; `KoboProxy.kt:131–337`.
3. **Review creation acceptance is observed; publication remains unverified.** The device sent review JSON and received HTTP 200; the correlated server logs show product-ID translation and upstream HTTP 200 with no response body. Publication or moderation is not demonstrated. Evidence: `nickel:L27349–27388`.
4. **Cache status appears at the device leg.** Wishlist `304` and image `304` should not be assumed to originate identically from upstream, because proxies rewrite/omit cache entity headers. Evidence: `nickel:L31785`; `komga:L7–9`; `KoboRawStoreProxy.kt:162–176`.
5. **Tag add/delete operations are in source but not demonstrated end-to-end by this capture.** The log establishes tag creation and rename; it does not establish every cloud-only, mixed, stale, unauthorized, or deletion scenario. Source: `KoboController.kt:1004–1159`.
6. **Device authorization fallback is code-only here.** The observed sync starts from an already authenticated profile; no complete successful device-auth handshake/request/response pair is present. Source: `KoboController.kt:254–276`.
7. **Empty results ≠ unavailable endpoints.** `Products` query, `user/reviews` and some personalized/related recommendations can return empty arrays with HTTP 200. A server-side resolution error or region/identifier mismatch is a separate proposition requiring targeted capture. Evidence: `nickel:L27300–27345,L31173–31217,L33115–33154`.
8. **Missing capture for advertised methods.** Purchase, auth-refresh, linked accounts, rating mutations, paid entitlement changes, most v2 catalog methods, all unsupported URL templates, and many Store error cases are advertised but not exercised here. No method, JSON, status or functional guarantee is invented for them.
9. **Independent reading-services integration is not demonstrated.** The device communicates directly with `readingservices.kobo.com` for annotations and user storage; these are not proved to be mirrored into Komga's API or database. Evidence: `nickel:L18304–18363`.
10. **Security and redaction.** Account IDs/emails, local read activity, live URLs with tokens, raw body text, cookies, author/display names, user-created timestamps, outgoing request bodies, and full server debug entries can be sensitive. Keep test logs sanitized and test outbound translations without disclosing genuine credentials.


## 12. Targeted validation matrix (not claimed as executed)

| Scenario to test | Expected boundary to verify | Evidence state |
|---|---|---|
| Cold sync → paginated local items → upstream delta | No duplicate entitlement/tag; stable combined token; correct `continue`. | Source + successful sample sync; edge cases untested. |
| Sync with upstream Kobo outage | Local data still syncs; token/continuation recovery safe. | Exception path exists; outage not exercised. |
| Local book metadata → details → series → prices | Same local ID, correct cover/series tuple and local-authoritative membership. | Captured individual calls; multi-branch consistency unverified. |
| Nonlocal Kobo ID passed to book/series/prices | Raw upstream content and no unintended local identity rewrite. | Observed raw proxy examples. |
| Local/remote/mixed collection create/add/remove/rename/delete | Correct local read-list and cloud revisions, admin enforcement, stale protection. | Create + rename captured; remaining cases source-only. |
| Local book archive → sync → optional restore → re-sync | Correct per-user tombstone and no underlying file deletion. | Archive + sync implementation documented; full restore round-trip unverified here. |
| Reading state update for `Reading`, `Finished`, malformed bookmark | Correct progression, per-item `RequestResult`, `400` for invalid payload. | Success captured; failure scenarios not captured. |
| Local ID inside feedback/review/wishlist bodies | Either validated conversion or explicit rejection **before** upstream. | Feedback rejection proved; other writes require tests. |
| Endpoint returns `304`, `307`, `4xx`, `5xx` | Correct cache/redirect/error propagation with safe response headers. | Only selected codes observed. |
| Storefront IT/en-US vs another storefront/language | Product and series identity resolution stays region-aware. | IT/en-US device observed; other locale behavior not captured here. |


## 13. Source bibliography and reproducibility

**Primary input A:** `Full - Copy.redacted.txt` (46,299 lines); device packetdump and Nickel events dated 22 September 2026.  
**Primary input B:** `komga.redacted.txt` (1,557 lines); Komga outgoing-proxy and controller debug events from the same day.  
**Primary input C:** `komga-1.27.0-4.kobo-identity-series-observation.zip` (supplied fork), especially `komga/src/main/kotlin/org/gotson/komga/interfaces/api/kobo/KoboController.kt`, `KoboRemainingEndpointsController.kt`, `KoboLocalStoreResponseBuilder.kt`, and `komga/src/main/kotlin/org/gotson/komga/infrastructure/kobo/KoboProxy.kt`, `KoboRawStoreProxy.kt`, `KoboProductResponseTranslator.kt`, `KoboOutboundRequestGuard.kt`, `KomgaSyncTokenGenerator.kt`.

The document is a **source-specific snapshot**, not a live compatibility test or a claim that upstream mainline Komga behaves identically. No live Kobo credentials or external account calls were used. Representative examples retain the observed JSON keys, nesting, types and array elements from selected parseable captures; long string values may be shortened. The original sanitized captures remain the primary source for unmodified values.


## Appendix A. Observed method/route evidence ledger

These entries are indexed from actual device request lines; raw media-only URL families appear in Appendix B. A missing status means no explicitly paired HTTP status was extracted for that route. Because concurrent requests can interleave, an extracted response status is an evidence locator rather than a definitive correlation to every occurrence; use the individually cross-checked cards in §§5–7 and the original timestamped packetdump for protocol assertions.

- **`GET api.kobobooks.com/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED`** — 10 request(s); captured status: **200**; device `nickel:L79`.

- **`GET komga.com/kobo/{token}/v1/initialization`** — 5 request(s); captured status: **200**; device `nickel:L165`.

- **`GET komga.com/kobo/{token}/v1/user/profile`** — 6 request(s); captured status: **200**; device `nickel:L207`.

- **`GET komga.com/kobo/{token}/v1/user/loyalty/benefits`** — 5 request(s); captured status: **200**; device `nickel:L255`.

- **`GET komga.com/kobo/{token}/v1/products/books/subscriptions`** — 5 request(s); captured status: **200**; device `nickel:L303`.

- **`GET komga.com/kobo/{token}/v1/deals`** — 5 request(s); captured status: **200**; device `nickel:L356`.

- **`GET komga.com/kobo/{token}/v1/assets`** — 5 request(s); captured status: **200**; device `nickel:L407`; query: `DiffRequests=[%7BKey:EPD-KoboPlus-ReadOnly-NeverSubscribed,ETag:W/djJ8UlZCRUxVdHZZbTlRYkhWekxWSmxZV1JQYm14NUxVNWxkbVZ5VTNWaWMyTnlh…`.

- **`POST komga.com/kobo/{token}/v1/analytics/gettests`** — 5 request(s); captured status: **200**; device `nickel:L459`.

- **`GET komga.com/kobo/{token}/v1/library/sync`** — 10 request(s); captured status: **200**; device `nickel:L521`; query: `Filter=ALL&DownloadUrlFilter=Generic,Android&PrioritizeRecentReads=true`.

- **`GET readingservices.kobo.com/api/UserStorage/Metadata/{uuid}`** — 6 request(s); captured status: **not clearly paired**; device `nickel:L2076`.

- **`GET readingservices.kobo.com/api/UserStorage/Object/{uuid}`** — 6 request(s); captured status: **not clearly paired**; device `nickel:L2091`; query: `token=[REDACTED]`.

- **`GET komga.com/kobo/{token}/v1/products/books/series/{komgaId}`** — 16 request(s); captured status: **200**; device `nickel:L2477`; query: `ExcludeOwned=false&PageSize=100`.

- **`GET komga.com/kobo/{token}/v1/library/{komgaId}/metadata`** — 7 request(s); captured status: **200**; device `nickel:L2532`.

- **`GET komga.com/kobo/{token}/v1/books/{komgaId}/file/epub`** — 7 request(s); captured status: **not clearly paired**; device `nickel:L2572`; query: `convert_kepub=false`.

- **`GET readingservices.kobo.com/api/v3/content/{komgaId}/annotations`** — 7 request(s); captured status: **200**; device `nickel:L2703`; query: `limit=100`.

- **`POST readingservices.kobo.com/api/v3/content/checkforchanges`** — 11 request(s); captured status: **200**; device `nickel:L18304`.

- **`GET readingservices.kobo.com/api/UserStorage/Metadata`** — 5 request(s); captured status: **200**; device `nickel:L18363`.

- **`GET komga.com/kobo/{token}/v1/products/{uuid}/recommendations`** — 3 request(s); captured status: **200**; device `nickel:L18459`; query: `page_index=0&page_size=5`.

- **`GET komga.com/kobo/{token}/v1/products/{komgaId}/recommendations`** — 7 request(s); captured status: **200**; device `nickel:L18789`; query: `page_index=0&page_size=5`.

- **`GET komga.com/kobo/{token}/v1/user/recommendations`** — 5 request(s); captured status: **200**; device `nickel:L19717`; query: `page_index=0&page_size=50&Filters=%7B%7D`.

- **`GET komga.com/kobo/{token}/v1/products/featured/`** — 1 request(s); captured status: **200**; device `nickel:L19771`; query: `page_index=0&page_size=100&TypesToInclude=book,audiobook`.

- **`GET komga.com/kobo/{token}/v1/products/featured/{uuid}`** — 18 request(s); captured status: **200**; device `nickel:L19818`; query: `page_index=0&page_size=4&Filters=%7B%7D&TypesToInclude=book,audiobook`.

- **`GET komga.com/kobo/{token}/v1/products/{uuid}/nextread`** — 5 request(s); captured status: **200**; device `nickel:L25753`.

- **`GET komga.com/kobo/{token}/v1/products/books/{uuid}/`** — 5 request(s); captured status: **200**; device `nickel:L25838`; query: `SendToBrowseHistory=false&SendBookStats=false`.

- **`GET storedownloads.kobo.com/download`** — 4 request(s); captured status: **not clearly paired**; device `nickel:L25879`; query: `downloadToken=[REDACTED]`.

- **`GET komga.com/kobo/{token}/v1/library/{komgaId}/state`** — 4 request(s); captured status: **200**; device `nickel:L27179`.

- **`GET komga.com/kobo/{token}/v1/products/{komgaId}/prices`** — 1 request(s); captured status: **200**; device `nickel:L27240`.

- **`GET komga.com/kobo/{token}/v1/user/reviews`** — 4 request(s); captured status: **200**; device `nickel:L27300`; query: `ProductIds=0RDTATKHXHPS9`.

- **`POST komga.com/kobo/{token}/v1/products/{komgaId}/reviews`** — 1 request(s); captured status: **200**; device `nickel:L27349`.

- **`PUT komga.com/kobo/{token}/v1/library/{komgaId}/state`** — 6 request(s); captured status: **200**; device `nickel:L27408`.

- **`GET komga.com/kobo/{token}/v1/products/{komgaId}/reviews`** — 2 request(s); captured status: **200**; device `nickel:L27531`; query: `PageSize=3&SortBy=MostLikedFirst`.

- **`GET komga.com/kobo/{token}/v1/products/books/{komgaId}/`** — 1 request(s); captured status: **200**; device `nickel:L27554`; query: `SendToBrowseHistory=true&SendBookStats=true`.

- **`GET komga.com/kobo/{token}/v1/products/{uuid},331d106f-731e-4565-857d-46981fcd3fed,45368193-f16e-42a8-8411-81a25a2d4832,6852e6c6-96d3-4000-9851-c4fb8d2cbaa4,9f7fc08a-2351-452f-933a-f044d2073343/prices`** — 1 request(s); captured status: **200**; device `nickel:L30582`.

- **`GET komga.com/kobo/{token}/v1/products/{komgaId},0RDTATKH5HXZS,0RDTATKH9HV0E,0RDTATKH9HV0D,0RDTATKH9HV0G/prices`** — 1 request(s); captured status: **200**; device `nickel:L31129`.

- **`GET komga.com/kobo/{token}/v1/products`** — 8 request(s); captured status: **200**; device `nickel:L31173`; query: `q=0RDTATKG1HM0V&Filters=%7B%7D&page_index=0&page_size=200&TypesToInclude=book,audiobook`.

- **`GET komga.com/kobo/{token}/v1/user/wishlist`** — 6 request(s); captured status: **304**; device `nickel:L31785`; query: `PageSize=100&PageIndex=0`.

- **`DELETE komga.com/kobo/{token}/v1/library/{komgaId}`** — 1 request(s); captured status: **204**; device `nickel:L32403`.

- **`GET readingservices.kobo.com/api/v3/content/{uuid}/annotations`** — 4 request(s); captured status: **200**; device `nickel:L32725`; query: `limit=100`.

- **`POST komga.com/kobo/{token}/v1/user/recommendations/feedback`** — 1 request(s); captured status: **not clearly paired**; device `nickel:L33081`.

- **`POST komga.com/kobo/{token}/v1/library/tags`** — 1 request(s); captured status: **201**; device `nickel:L35135`.

- **`PUT komga.com/kobo/{token}/v1/library/tags/{komgaId}`** — 1 request(s); captured status: **200**; device `nickel:L36686`.

- **`GET komga.com/kobo/{token}/v1/products/{uuid}/reviews`** — 1 request(s); captured status: **200**; device `nickel:L41732`; query: `PageSize=3&SortBy=MostLikedFirst`.

- **`POST komga.com/kobo/{token}/v1/user/wishlist/items`** — 1 request(s); captured status: **200**; device `nickel:L45717`.


## Appendix B. Captured device media/resource families

**Local cover images:** `GET /kobo/{token}/v1/books/{id}/thumbnail/{width}/{height}/…/image.jpg`. JPEG bytes or redirect, depending on identity and cache.

**Local book downloads:** `GET /kobo/{token}/v1/books/{id}/file/epub`, including captured `convert_kepub=false` query. Binary response.

**Independent covers:** `GET cdn.kobo.com/book-images/{imageId}/…/image.jpg`. These are CDN requests, not dedicated Komga methods.

**Independent device assets:** `GET ereaderfiles.kobo.com/ereader/fonts/*.otf`, `/ereader/iink/v2/*.zip`, `/ereader/dictionaries/v3/*.zip`, `/elabels/N428.zip` and `/ereader/guide/monza/userguide_en_US.pdf` download firmware, dictionary, label or guide assets directly.

**Independent book delivery:** `GET getbook.kobo.com/koboid-prod-public/*.epub` and `GET storedownloads.kobo.com/download?downloadToken=…` are device book-download families; opaque tokens must remain redacted.



## Addendum — September 22, 2026, later `Review_v2.redacted.txt` capture

**Source scope:** This appendix uses the newer device-only `Review_v2.redacted.txt` log. It is *not* a matched new Komga server log and cannot establish the outbound Kobo Store URL, rewritten payload, or upstream HTTP status. The device-facing status codes and empty-body semantics below are captured, not inferred. Personal review text/title and author display name have been replaced with string placeholders while retaining every field, JSON type, and rating.

### 10.1 Three written-review POST transactions and their preceding personal-review lookups

The device calls `GET /v1/user/reviews?ProductIds={localBookId}` before submitting each of these written reviews. The captured response for each lookup is `200 OK` and:

```json
{
  "CurrentPageIndex": 1,
  "Items": [],
  "TotalPageCount": 0
}
```

Each subsequent written-review POST uses the **local Komga book ID in its URL** and five lower-camel/snake-case request-body fields. The POST returns `200 OK` with `Content-Length: 0` — no JSON response. This demonstrates device-side successful acknowledgement, not publication/moderation of the review.

**Device request** (`review_v2:L59`): `POST /v1/products/0RDTATJQNHSQ0/reviews`; request-body capture `review_v2:L57`.

```json
{
  "authorDisplayName": "[REDACTED_DISPLAY_NAME]",
  "body": "[REDACTED_REVIEW_TEXT]",
  "creation_date": "2026-09-22T15:37:38Z",
  "rating": 4,
  "title": "[REDACTED_REVIEW_TITLE]"
}
```

**Device response:** `200 OK`, `Content-Length: 0`, empty body (`review_v2:L81` and adjacent response headers). Request-body privacy placeholders are **not literal network values**; the original device log contains personal review text.

**Device request** (`review_v2:L8004`): `POST /v1/products/0RE3XW4NEEGG4/reviews`; request-body capture `review_v2:L8002`.

```json
{
  "authorDisplayName": "[REDACTED_DISPLAY_NAME]",
  "body": "[REDACTED_REVIEW_TEXT]",
  "creation_date": "2026-09-22T15:51:22Z",
  "rating": 4,
  "title": "[REDACTED_REVIEW_TITLE]"
}
```

**Device response:** `200 OK`, `Content-Length: 0`, empty body (`review_v2:L8026` and adjacent response headers). Request-body privacy placeholders are **not literal network values**; the original device log contains personal review text.

**Device request** (`review_v2:L8120`): `POST /v1/products/0RDTATKFHHSYW/reviews`; request-body capture `review_v2:L8118`.

```json
{
  "authorDisplayName": "[REDACTED_DISPLAY_NAME]",
  "body": "[REDACTED_REVIEW_TEXT]",
  "creation_date": "2026-09-22T15:52:41Z",
  "rating": 4,
  "title": "[REDACTED_REVIEW_TITLE]"
}
```

**Device response:** `200 OK`, `Content-Length: 0`, empty body (`review_v2:L8142` and adjacent response headers). Request-body privacy placeholders are **not literal network values**; the original device log contains personal review text.

### 10.2 Multi-item recommendation feedback — confirmed device-facing 204

**Device request:** `POST /v1/user/recommendations/feedback` at `review_v2:L9290–9313` with `Content-Type: application/json` and the following complete JSON. This request follows the review/rating workflow but is a **separate API operation**, not a written-review POST.

```json
{
  "FeedbackItems": [
    {
      "FeedbackType": "Rate4Star",
      "RevisionId": "0RE3XW4NEEGG4"
    },
    {
      "FeedbackType": "Rate4Star",
      "RevisionId": "0RDTATKFHHSYW"
    }
  ]
}
```

**Device response:** `204 No Content` (`review_v2:L9314–9330`); no JSON or other response body is logged. The `SubmitFeedbackCommand` completes (`review_v2:L9331–9334`). This provides an observed success status for the **device-facing** endpoint, which was missing in the original capture. It does not prove a contemporaneous upstream Store request or confirm whether the local TSIDs were translated.

**Translation-layer boundary:** all `FeedbackItems` elements must be processed independently in a future implementation. Do not substitute a ProductId into a field named `RevisionId` unless the actual Kobo Store contract or a matched outbound success trace verifies that identifier type. Preserving `FeedbackType`, payload cardinality, item ordering, the device's expected `204`, and empty body is part of the observed device contract.

### 10.3 Missing verification after this new capture

To prove genuine Kobo Store forwarding, obtain a matching, properly redacted **Komga server** trace showing the outbound `POST https://storeapi.kobo.com/v1/user/recommendations/feedback`, its complete translated `FeedbackItems` JSON, upstream HTTP status, and the final device-facing `204`. The new device log alone only proves the last step.
