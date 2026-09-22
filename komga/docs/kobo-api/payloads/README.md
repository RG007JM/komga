# Captured JSON by endpoint

This index separates **device-visible** JSON from **upstream** JSON. The captures were sanitized; a parser failure in a sanitized log is not evidence that the original HTTP response was malformed. Large parsed JSON values are stored in linked `.json` files rather than inside oversized Markdown pages.

- [§8.1. `/v1/initialization`](./01.md)
- [§8.2. `/v1/library/sync`](./02.md)
- [§8.3. `/v1/library/{id}/metadata`](./03.md)
- [§8.4. `/v1/library/{id}/state`](./04.md)
- [§8.5. `/v1/library/tags`](./05.md)
- [§8.6. `/v1/library/tags/{id}`](./06.md)
- [§8.7. `/v1/products/books/{id}`](./07.md)
- [§8.8. `/v1/products/books/series/{id}`](./08.md)
- [§8.9. `/v1/products/{id}/prices`](./09.md)
- [§8.10. `/v1/products/{id}/reviews`](./10.md)
- [§8.11. `/v1/products/{id}/recommendations`](./11.md)
- [§8.12. `/v1/products/{id}/nextread`](./12.md)
- [§8.13. `/v1/products`](./13.md)
- [§8.14. `/v1/products/books/subscriptions`](./14.md)
- [§8.15. `/v1/products/featured`](./15.md)
- [§8.16. `/v1/products/featured/{id}`](./16.md)
- [§8.17. `/v1/products/{productids}/prices`](./17.md)
- [§8.18. `/v1/user/reviews`](./18.md)
- [§8.19. `/v1/user/recommendations/feedback` — both captured request bodies, device response, and translation boundary](./19.md)
- [§8.20. `/v1/user/wishlist/items`](./20.md)
- [§8.21. `/v1/user/profile`](./21.md)
- [§8.22. `/1.0/upgradecheck/device/{id}/kobo/4.46.23836/n428490202644`](./22.md)
- [§8.23. `/api/userstorage/metadata`](./23.md)
- [§8.24. `/api/v3/content/checkforchanges`](./24.md)
- [§8.25. `/api/v3/content/{id}/annotations`](./25.md)
- [§8.26. `/v1/analytics/gettests`](./26.md)
- [§8.27. `/v1/assets`](./27.md)
- [§8.28. `/v1/deals`](./28.md)
- [§8.29. `/v1/user/loyalty/benefits`](./29.md)
- [§8.30. `/v1/user/recommendations`](./30.md)
- [§8.31. `/v1/user/wishlist`](./31.md)

## 8. Complete captured JSON by endpoint and layer

Every example below is a single, valid JSON value reconstructed from a *complete recorded JSON body*, with no dropped keys or array elements. Descriptions and similarly long text values may be shortened; their parent keys and string type are unchanged. The server's upstream response body is logged **before** Komga's identity-aware response translation. A server record is never treated as evidence of the exact device response solely because the routes agree. Lines refer to the original provided logs. Repeated instances and alternate captures, including responses too large to repeat here, are included in the companion full ledger and ZIP.

If a captured request or response has no JSON body, this section records it explicitly. HTTP `204`, HTTP `304`, and a `200` with zero-length body are **not** replaced with `{}`. A binary download is not fabricated as JSON. For the upstream server excerpts, HTTP method is only specified in the device entries or when established by controller source, as method is absent from the upstream proxy log line.

