# Kobo API integration — observed wire reference

> **Unofficial snapshot (22 September 2026).** This reference describes the supplied Komga fork and sanitized Kobo device/Komga logs; it is not Kobo's official API, nor the generated Komga [OpenAPI definition](openapi.json). Some upstream routes are advertised by Kobo but were not exercised in the captures.

**Where to start:** [Endpoint behavior, HTTP codes, routing and translation](kobo-api/reference.md) · [Captured JSON by endpoint](kobo-api/payloads/README.md) · [Every parseable JSON capture](kobo-api/captures/README.md) · [Supplementary evidence, resource registry and known gaps](kobo-api/supplementary.md).

For machine-readable input to translation-layer work, use the [individual JSON fixtures](kobo-api/fixtures/README.txt), [fixture manifest](kobo-api/fixtures/manifest.json), [field-path index](kobo-api/field-path-index.json), and [38-line sanitized-log parsing register](kobo-api/sanitized-log-parsing-register.csv). The register covers lines that fail JSON parsing **after sanitization**; it is not evidence of malformed, short, or failed HTTP responses. **Do not compare a recorded `Content-Length` to a sanitized log string.**

## Recommendation feedback

`POST /v1/user/recommendations/feedback` is documented separately from written-review submission. [Its endpoint card](kobo-api/reference.md#614-recommendation-feedback), [both captured request bodies, including the later two-item `Rate4Star` request](kobo-api/payloads/19.md), and the [later request JSON fixture](kobo-api/fixtures/review_v2/POST_v1_user_recommendations_feedback/request_L9291.json) are included. The later request received **device-facing HTTP 204 No Content**, with **no response JSON**. No paired later server-side outbound log was supplied, so actual upstream forwarding and the target identifier type for `FeedbackItems[].RevisionId` remain unverified; do not infer that `RevisionId` necessarily accepts a product UUID.

## Provenance and publication safety

Examples are sanitized samples, not complete normative schemas. Long text values may be shortened, personal review content is redacted, and identity fields remain representative of the specific captured source. The original logs and private source archive **are not included**. Publicly review the sample JSON and metadata for any residual personally identifying data, secrets, externally signed download URLs, and third-party text before committing. This documentation does not assert that Kobo or upstream Komga endorses the integration.

`openapi.json` is intentionally left unchanged. There is no need to alter API generation, build configuration, or the backend code to publish these Markdown and JSON resources.
