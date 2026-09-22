# Captures 228–252

[← Capture index](README.md) · [API index](../../kobo-api.md)

## 228. not logged `/v1/initialization` — Komga upstream standard proxy log response

Source: `server:L1229`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_initialization/response_L1229.json`.

```json
{
  "Resources": {
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "products": "https://storeapi.kobo.com/v1/products",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "image_host": "//cdn.kobo.com/book-images/",
    "store_host": "www.kobo.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "reading_services_host": "https://readingservices.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "oauth_host": "https://oauth.kobo.com",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "redeem_interstitial_page": "https://www.kobo.com",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "help_page": "https://www.kobo.com/help",
    "image_url_template": "https://cdn.kobo.com/book-images/{ImageId}/{Width}/{Height}/false/image.jpg",
    "image_url_quality_template": "https://cdn.kobo.com/book-images/{ImageId}/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "use_one_store": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_subscriptions_enabled": "True",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_nativeborrow_enabled": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_wishlist_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_redeem_enabled": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_display_price": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "gpb_flow_enabled": "False",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "display_parental_controls_enabled": "True",
    "display_accessibility_enabled": "True",
    "text_to_speech_region_override": "False",
    "reflowable_page_cache_enabled": "True",
    "fixed_layout_page_cache_enabled": "True",
    "instapaper_enabled": "True",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo"
  }
}
```


## 229. not logged `/v1/user/profile` — Komga upstream standard proxy log response

Source: `server:L1232`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `upstream/unknown_v1_user_profile/response_L1232.json`.

```json
{
  "IsOneStore": true,
  "IsChildAccount": false,
  "CountryCode": "IT",
  "Geo": "IT",
  "StoreFront": "IT",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "AffiliateName": "Mondadori",
  "IsoCultureCode": "en-US",
  "LoyaltyDetails": {
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTags": 2,
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyCurrentBalance": 0
  },
  "IsLibraryMigrated": false,
  "VipMembershipPurchased": false,
  "HasPurchased": true,
  "HasPurchasedBook": true,
  "HasPurchasedAudiobook": false,
  "SafeSearch": false,
  "AudiobooksEnabled": true,
  "IsOrangeAffiliated": false,
  "IsEligibleForOrangeDeal": false,
  "PrivacyPermissions": [],
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "UserId": "[REDACTED]",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "ContactEmail": "[REDACTED]"
}
```


## 230. not logged `/v1/user/loyalty/benefits` — Komga upstream standard proxy log response

Source: `server:L1235`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_user_loyalty_benefits/response_L1235.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "MembershipLevel": "KoboLoveBasic",
      "PointEarnRate": 200,
      "CurrencySpendRate": 10.0,
      "CurrencyType": "EUR",
      "MinimumPointRedemption": 2400,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "MembershipLevel": "KoboLoveVIP",
      "PointEarnRate": 400,
      "CurrencySpendRate": 10.0,
      "CurrencyType": "EUR",
      "DiscountRate": 0.0,
      "MinimumPointRedemption": 2400,
      "LoveProduct": {
        "Title": "Kobo VIP Membership",
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "LovePointsPrice": 8000,
        "IsPreOrder": false,
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9"
      }
    }
  }
}
```


## 231. not logged `/v1/products/books/subscriptions` — Komga upstream raw proxy log response

Source: `server:L1237`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `upstream/unknown_v1_products_books_subscriptions/response_L1237.json`.

```json
[
  {
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Id": "808f53b4-3910-4bd3-9c81-9ea25d9c8533",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc"
  },
  {
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Id": "9bb633fd-6273-401b-bf6e-968b7b552b96",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e"
  },
  {
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Id": "c3e5161b-c4d7-42ed-af13-470fa76ffd1b",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513"
  }
]
```


## 232. not logged `/v1/deals` — Komga upstream standard proxy log response

Source: `server:L1240`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_deals/response_L1240.json`.

```json
{
  "Deals": [
    {
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans",
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z"
    }
  ]
}
```


## 233. not logged `/v1/assets` — Komga upstream standard proxy log response

Source: `server:L1243`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `upstream/unknown_v1_assets/response_L1243.json`.

```json
{
  "AssetGroups": [
    {
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "ETag": "[REDACTED]",
      "Assets": [],
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 234. not logged `/v1/analytics/gettests` — Komga upstream standard proxy log response

Source: `server:L1246`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `upstream/unknown_v1_analytics_gettests/response_L1246.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 235. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L1253`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L1253.json`.

```json
[]
```


## 236. not logged `/v1/user/wishlist` — Komga upstream standard proxy log response

Source: `server:L1256`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_wishlist/response_L1256.json`.

```json
{
  "TotalCountByProductType": {},
  "Items": [],
  "ItemCount": 0,
  "TotalPageCount": 0,
  "TotalItemCount": 0,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "VersionCode": 2
}
```


## 237. not logged `/v1/products/books/series/{uuid}` — Komga upstream raw proxy log response

Source: `server:L1258`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_series_uuid/response_L1258.json`.

```json
{
  "Items": [
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "c2c1cce7-be77-3f2d-90b6-0cf69be4d06a",
        "SeriesNumber": "1",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 1,
        "IsFree": false,
        "ISBN": "9781975312534",
        "PublicationDate": "2020-04-28T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "c2c1cce7-be77-3f2d-90b6-0cf69be4d06a",
        "Title": "Rascal Does Not Dream of Bunny Girl Senpai (light novel)",
        "Description": "<p>Out of sight, out of mind!Bunny girls do not live in libraries. This is simply common sense. And yet, that’s exactly where Sakuta finds one in the wild. More bewildering is who the bunny … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e757659b-1336-4558-b891-3b5882f93b2c",
        "PublisherName": "Yen Press",
        "Rating": 4.62963,
        "TotalRating": 27,
        "Slug": "rascal-does-not-dream-of-bunny-girl-senpai-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6390130
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "348c332b-4382-8b12-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-04-28T00:00:00.0000000Z"
        },
        "Id": "b123ce7a-d0ca-4591-a967-4b567e04a7e0"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2bb34ec2-0004-39f6-907c-c246cacc4a69",
        "SeriesNumber": "2",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 2,
        "IsFree": false,
        "ISBN": "9781975312558",
        "PublicationDate": "2020-08-18T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2bb34ec2-0004-39f6-907c-c246cacc4a69",
        "Title": "Rascal Does Not Dream of Petite Devil Kohai (light novel)",
        "Description": "<p>How far can a fake relationship go?Mai is no longer invisible--she’s acting again, and she finally said yes when Sakuta asked her out for the millionth time. Life couldn’t be better for S… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "24bdbcd3-21b3-4570-a933-809ce3a12ccf",
        "PublisherName": "Yen Press",
        "Rating": 4.866667,
        "TotalRating": 15,
        "Slug": "rascal-does-not-dream-of-petite-devil-kohai-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5163024
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "f8459494-f564-e466-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-08-18T00:00:00.0000000Z"
        },
        "Id": "65d8e847-7cc5-474b-b161-8020795a7b52"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "f4922d85-aa3a-3dc0-94aa-7fe2785de89e",
        "SeriesNumber": "3",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 3,
        "IsFree": false,
        "ISBN": "9781975312572",
        "PublicationDate": "2020-11-17T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "f4922d85-aa3a-3dc0-94aa-7fe2785de89e",
        "Title": "Rascal Does Not Dream of Logical Witch (light novel)",
        "Description": "<p>Just before summer break, Sakuta encounters Shouko Makinohara, a girl in junior high with the same name and face as his first love. Of course, that should be impossible, since she would b… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "d41461c1-f8e9-45ce-a916-9fad7b4c5f7c",
        "PublisherName": "Yen Press",
        "Rating": 4.7,
        "TotalRating": 10,
        "Slug": "rascal-does-not-dream-of-logical-witch-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4833019
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "20809053-25a5-3878-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-11-17T00:00:00.0000000Z"
        },
        "Id": "8d71e6c4-700b-4d60-8798-1a87aa701151"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "9199d101-9b75-387d-bd8d-130509958d9e",
        "SeriesNumber": "4",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 4,
        "IsFree": false,
        "ISBN": "9781975312596",
        "PublicationDate": "2021-03-30T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "9199d101-9b75-387d-bd8d-130509958d9e",
        "Title": "Rascal Does Not Dream of Siscon Idol (light novel)",
        "Description": "<p>DOUBLE TROUBLE?! Sakuta is eager to finally have a chance to spend time with Mai again, but instead, he's greeted by a stranger who looks just like her. Apparently, Adolescence Syndrome i… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "352d16d5-1522-4366-9700-85074f7abc26",
        "PublisherName": "Yen Press",
        "Rating": 4.888889,
        "TotalRating": 9,
        "Slug": "rascal-does-not-dream-of-siscon-idol-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5465410
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "7c2a9d66-821d-edc0-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-03-30T00:00:00.0000000Z"
        },
        "Id": "4d5e26b1-5fdc-4abc-9cce-51a1624645d2"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "c3506cc6-5eb0-3d72-869f-ad7889086fdb",
        "SeriesNumber": "5",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 5,
        "IsFree": false,
        "ISBN": "9781975312619",
        "PublicationDate": "2021-07-27T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "c3506cc6-5eb0-3d72-869f-ad7889086fdb",
        "Title": "Rascal Does Not Dream of a Sister Home Alone (light novel)",
        "Description": "<p>GUESS WHO’S BACK? After years without contact, Sakuta has received a letter from his first love, Shouko, asking to meet at Shichirigahama Beach. Of course, now that he’s in a happy relati… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "31d683e9-deeb-4fad-9cb3-f4ce5248b382",
        "PublisherName": "Yen Press",
        "Rating": 4.7,
        "TotalRating": 10,
        "Slug": "rascal-does-not-dream-of-a-sister-home-alone-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4782787
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "2f371f81-64c5-112e-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-07-27T00:00:00.0000000Z"
        },
        "Id": "2bb10209-81a7-4a8d-b43c-bfff25b234ec"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2e269e63-8dfc-376d-a71c-8a68c8accf5b",
        "SeriesNumber": "6",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 6,
        "IsFree": false,
        "ISBN": "9781975312633",
        "PublicationDate": "2021-11-30T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2e269e63-8dfc-376d-a71c-8a68c8accf5b",
        "Title": "Rascal Does Not Dream of a Dreaming Girl (light novel)",
        "Description": "<p>SUPERSTRING (OF FATE) THEORY! Sakuta is doomed. His girlfriend, Mai, has just discovered that while she was away, he’s been living under one roof with his first love, the now-college-aged… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "553661b7-b450-4483-a56b-24a4231da870",
        "PublisherName": "Yen Press",
        "Rating": 4.583333,
        "TotalRating": 12,
        "Slug": "rascal-does-not-dream-of-a-dreaming-girl-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4199725
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "69b1a8d7-3ba0-ba56-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-11-30T00:00:00.0000000Z"
        },
        "Id": "9ba39b5f-b943-4677-bb76-72ee7b48c70f"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "dbe8d574-5f86-351e-b2dd-3f5fb2d2d806",
        "SeriesNumber": "7",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 7,
        "IsFree": false,
        "ISBN": "9781975312657",
        "PublicationDate": "2022-05-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "dbe8d574-5f86-351e-b2dd-3f5fb2d2d806",
        "Title": "Rascal Does Not Dream of His First Love (light novel)",
        "Description": "<p>UNDOING WHAT CAN’T BE UNDONE.Sakuta had been ready to give up his life, but nothing could have prepared him to keep living like this. Now he must find a way to carry on even when it feels… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e6e3428d-fc8b-4264-97fc-d4a18d5c9d2d",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 7,
        "Slug": "rascal-does-not-dream-of-his-first-love-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5087025
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "7b9ffbd1-8e2c-492e-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-05-03T00:00:00.0000000Z"
        },
        "Id": "5d439238-39d2-4494-b1be-470982a3c3b7"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "8407f486-face-38b6-bba3-5c0509e393eb",
        "SeriesNumber": "8",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 8,
        "IsFree": false,
        "ISBN": "9781975312671",
        "PublicationDate": "2022-08-23T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "8407f486-face-38b6-bba3-5c0509e393eb",
        "Title": "Rascal Does Not Dream of a Sister Venturing Out (light novel)",
        "Description": "<p>WHAT WILL IT TAKE TO MAKE HER WISH COME TRUE?After a draining December, Sakuta is quickly nearing the end of his second year of high school. Since Mai is a third-year student, they don’t … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "d92e808f-586a-4c2c-9092-132c91b4af2b",
        "PublisherName": "Yen Press",
        "Rating": 4.571429,
        "TotalRating": 7,
        "Slug": "rascal-does-not-dream-of-a-sister-venturing-out-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3980876
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "9894b315-4a3e-609b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-08-23T00:00:00.0000000Z"
        },
        "Id": "23253e95-2f31-4ed4-ba2a-e7c164714f48"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "678130e0-fe7d-32a6-9d92-b1d1db1b15a0",
        "SeriesNumber": "9",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 9,
        "IsFree": false,
        "ISBN": "9781975312695",
        "PublicationDate": "2022-12-13T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "678130e0-fe7d-32a6-9d92-b1d1db1b15a0",
        "Title": "Rascal Does Not Dream of a Knapsack Kid (light novel)",
        "Description": "<p>March has arrived, meaning there’s only one month left in the third term. Mai’s graduation has come and gone. This might be a time for change, but that isn’t nearly enough to explain why … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "9b2b4a4c-1245-45f8-b82b-5a315a9d78fc",
        "PublisherName": "Yen Press",
        "Rating": 4.875,
        "TotalRating": 8,
        "Slug": "rascal-does-not-dream-of-a-knapsack-kid-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3203816
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "0330e7a9-49ad-8151-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-12-13T00:00:00.0000000Z"
        },
        "Id": "79e9587a-2413-4acb-bd3e-cfd03047c32e"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "95b0319d-9cab-3ed5-8beb-c786962e9b31",
        "SeriesNumber": "10",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 10,
        "IsFree": false,
        "ISBN": "9781975318529",
        "PublicationDate": "2023-03-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "95b0319d-9cab-3ed5-8beb-c786962e9b31",
        "Title": "Rascal Does Not Dream of a Lost Singer (light novel)",
        "Description": "<p>Becoming a college student means starting a whole new life...right? Sakuta honestly isn’t sure. Some things have changed, but some things are still the same. His reputation definitely pre… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "a9c73cf8-2a63-418d-bff8-de71099df732",
        "PublisherName": "Yen Press",
        "Rating": 3.5,
        "TotalRating": 6,
        "Slug": "rascal-does-not-dream-of-a-lost-singer-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5217872
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "25bd2df1-efc2-1b2b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-03-21T00:00:00.0000000Z"
        },
        "Id": "930b106d-1155-40c8-83c3-b30fdcd59aeb"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "77ee2397-da3d-30b7-8ded-08fcac7bfa83",
        "SeriesNumber": "11",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 11,
        "IsFree": false,
        "ISBN": "9781975343514",
        "PublicationDate": "2023-06-20T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "77ee2397-da3d-30b7-8ded-08fcac7bfa83",
        "Title": "Rascal Does Not Dream of a Nightingale (light novel)",
        "Description": "<p>WORST CHRISTMAS GIFT EVER…A miniskirt-wearing Santa appears before Sakuta, calling herself Touko Kirishima and cheerfully informing him that she’s been handing out presents—Adolescence Sy… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "6487d49e-9002-42b5-86a8-c279282c0da1",
        "PublisherName": "Yen Press",
        "Rating": 4.6,
        "TotalRating": 5,
        "Slug": "rascal-does-not-dream-of-a-nightingale-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5043655
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "c5082778-235d-a0af-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-06-20T00:00:00.0000000Z"
        },
        "Id": "d8471959-e30c-4112-bf82-95431c740659"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "7ca60ca9-6a1d-39d5-9c5c-dbd65784d31e",
        "SeriesNumber": "12",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 12,
        "IsFree": false,
        "ISBN": "9781975375287",
        "PublicationDate": "2023-11-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "7ca60ca9-6a1d-39d5-9c5c-dbd65784d31e",
        "Title": "Rascal Does Not Dream of His Student (light novel)",
        "Description": "<p>MINISKIRT SANTA’S COMING TO TOWN!Mai is in danger, and the only clue Sakuta has is a cryptic message from himself saying he needs to find Touko Kirishima. The problem is that even though … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "40f2719c-ac15-4212-889b-52c622454cb8",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 4,
        "Slug": "rascal-does-not-dream-of-his-student-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6936098
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "ea17f08d-9253-f7c9-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-11-21T00:00:00.0000000Z"
        },
        "Id": "758b89ac-740e-4282-b68e-e29f3be349f8"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "cda7d480-de4b-3acc-8fb9-416f6b08af80",
        "SeriesNumber": "13",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 13,
        "IsFree": false,
        "ISBN": "9781975391614",
        "PublicationDate": "2024-08-20T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "cda7d480-de4b-3acc-8fb9-416f6b08af80",
        "Title": "Rascal Does Not Dream of Santa Claus (light novel)",
        "Description": "<p>IS THE REAL MINISKIRT SANTA IN A SEA OF FAKES? Sakuta thought he’d already accomplished step one: Find Touko Kirishima. The next step was convincing her to help protect Mai, but that prov… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "976e4895-485f-4917-917c-95cdc538e1a2",
        "PublisherName": "Yen Press",
        "Rating": 4.8,
        "TotalRating": 5,
        "Slug": "rascal-does-not-dream-of-santa-claus-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4028488
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "ddf6fe26-d52f-690a-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2024-08-20T00:00:00.0000000Z"
        },
        "Id": "8f8cbcb2-efa8-495e-b878-145be2dc5740"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2107df94-a206-3768-9d5e-1b9f7ca29821",
        "SeriesNumber": "14",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 14,
        "IsFree": false,
        "ISBN": "9798855418309",
        "PublicationDate": "2025-06-10T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2107df94-a206-3768-9d5e-1b9f7ca29821",
        "Title": "Rascal Does Not Dream of His Girlfriend (light novel)",
        "Description": "<p>AN UNBELIEVABLE ANNOUNCEMENT!The day Sakuta dreamed about is finally here. His search for Touko Kirishima has led him to a music festival where Mai is performing. Filled with concern and … [TRUNCATED 292 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e48a878d-19c4-4b7a-b990-ee30d582cf6c",
        "PublisherName": "Yen Press",
        "Rating": 3,
        "TotalRating": 2,
        "Slug": "rascal-does-not-dream-of-his-girlfriend-light-novel-1",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 2342620
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "8290d577-8bbb-ea0c-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2025-06-10T00:00:00.0000000Z"
        },
        "Id": "40433014-01f1-4c1d-8c9d-12383ec9ca52"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "4cedc735-a995-3217-bb27-782c0059b685",
        "SeriesNumber": "15",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 15,
        "IsFree": false,
        "ISBN": "9798855422443",
        "PublicationDate": "2026-01-27T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "4cedc735-a995-3217-bb27-782c0059b685",
        "Title": "Rascal Does Not Dream of a Dear Friend (light novel)",
        "Description": "<p>one last dream…As the truth about Miori and Touko Kirishima comes to light, Sakuta must make a choice that will decide everything…for him…for Mai…for Shouko…for Miori…and for Touko. Only … [TRUNCATED 95 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "2e9d763d-e3ea-45eb-8798-821824801bf3",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 1,
        "Slug": "rascal-does-not-dream-of-a-dear-friend-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3875098
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "b1ff2847-0082-8193-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2026-01-27T00:00:00.0000000Z"
        },
        "Id": "bbd7b113-0a68-4a0d-bbdc-faa4986233f5"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "feb56b0a-f0b5-33a7-be3e-17b286c0f589",
        "SeriesNumber": "16",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 16,
        "IsFree": false,
        "ISBN": "9798855434460",
        "PublicationDate": "2026-08-11T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "feb56b0a-f0b5-33a7-be3e-17b286c0f589",
        "Title": "Rascal Does Not Dream of a Beach Queen + (light novel)",
        "Description": "<p>It’s the day of the Minegahara Sports Festival! Sakuta has never been the type to get fired up about sports, so he’s quite content to be blue team’s substitute player. That is, until he g… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "a72b3ce8-5026-4f8f-8636-23b5e172fa2a",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 2,
        "Slug": "rascal-does-not-dream-of-a-beach-queen-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4176268
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "2a944b0f-8ba8-e8fc-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2026-08-11T00:00:00.0000000Z"
        },
        "Id": "5dabec55-4dcf-43e8-9d61-753a83b0fbb7"
      }
    }
  ],
  "ItemCount": 16,
  "TotalPageCount": 1,
  "TotalItemCount": 16,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "VersionCode": 2
}
```


## 238. not logged `/v1/user/recommendations` — Komga upstream standard proxy log response

Source: `server:L1265`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_recommendations/response_L1265.json`.

```json
{
  "Items": [],
  "ItemCount": 0,
  "TotalPageCount": 0,
  "TotalItemCount": 0,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 50,
  "Filters": {
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "VersionCode": 2
}
```


## 239. not logged `/v1/products/{uuid}/nextread` — Komga upstream standard proxy log response

Source: `server:L1268`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_products_uuid_nextread/response_L1268.json`.

```json
{
  "42ed6d94-1f32-4df9-9d97-023335a4eb9c": [
    {
      "Contributors": "Kanehito Yamada,Tsukasa Abe,Matteo Cremaschi",
      "WorkId": "1e78804b-759e-3e91-aa19-a6a7def6e301",
      "SeriesNumber": "14",
      "SeriesName": "Frieren. Oltre la fine del viaggio",
      "SeriesSlug": "frieren-oltre-la-fine-del-viaggio-1",
      "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
      "SeriesNumberFloat": 14,
      "IsFree": false,
      "ISBN": "9788834937211",
      "PublicationDate": "2025-06-24T00:00:00.0000000Z",
      "ContributorRoles": [
        {
          "Name": "Kanehito Yamada",
          "Role": "Author"
        },
        {
          "Name": "Tsukasa Abe",
          "Role": "Illustrator"
        },
        {
          "Name": "Matteo Cremaschi",
          "Role": "Translator"
        }
      ],
      "IsInternetArchive": false,
      "IsRecommendation": false,
      "CrossRevisionId": "1e78804b-759e-3e91-aa19-a6a7def6e301",
      "Title": "Frieren. Oltre la fine del viaggio (Vol. 14)",
      "Description": "<p>La maga Frieren fa visita alla capitale imperiale, il fulcro della civiltà magica fondata dalla sua maestra Flamme. Qualcuno sta pianificando l'assassinio di Serie e l'Istituto di magia d… [TRUNCATED 230 chars; original string value]",
      "Language": "it",
      "Locale": {
        "LanguageCode": "ita",
        "ScriptCode": "",
        "CountryCode": ""
      },
      "ImageId": "284ebc78-3bfd-4d28-b632-d9aa8b0adc75",
      "PublisherName": "J-POP Manga",
      "Rating": 5,
      "TotalRating": 4,
      "Slug": "frieren-oltre-la-fine-del-viaggio-vol-14",
      "IsContentSharingEnabled": true,
      "RedirectPreviewUrls": [
        {
          "DrmType": "None",
          "Format": "EPUB3FL_SAMPLE",
          "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
          "Platform": "Generic",
          "Size": 85245253
        }
      ],
      "HasPreview": true,
      "Price": {
        "Currency": "EUR",
        "Price": 3.99
      },
      "PromoCodeAllowed": false,
      "EligibleForKoboLoveDiscount": false,
      "IsPreOrder": false,
      "RelatedGroupId": "d91fa13a-7be8-64da-0000-000000000000",
      "AgeVerificationRequired": false,
      "AccessibilityDetails": {
        "IsFixedLayout": true,
        "IsTextToSpeechAllowed": false,
        "PrimaryContentType": "10",
        "ContentTypes": [],
        "EPubAccessibilities": [],
        "HazardWarningTypes": [],
        "IsAccessible": false
      },
      "LifeCycleDates": {
        "ActivationDate": "2025-06-24T00:00:00.0000000Z"
      },
      "Id": "bb14a4c2-04be-43c7-935b-55d6ef25055a"
    },
    {
      "Contributors": "Kanehito Yamada",
      "WorkId": "43593869-0f5a-3134-a33e-72c37f7de520",
      "SeriesNumber": "13",
      "SeriesName": "Frieren. Oltre la fine del viaggio",
      "SeriesSlug": "frieren-oltre-la-fine-del-viaggio-1",
      "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
      "SeriesNumberFloat": 13,
      "IsFree": false,
      "ISBN": "9788834931608",
      "PublicationDate": "2024-09-24T00:00:00.0000000Z",
      "ContributorRoles": [
        {
          "Name": "Kanehito Yamada",
          "Role": "Author"
        }
      ],
      "IsInternetArchive": false,
      "IsRecommendation": false,
      "CrossRevisionId": "43593869-0f5a-3134-a33e-72c37f7de520",
      "Title": "Frieren. Oltre la fine del viaggio (Vol. 13)",
      "Description": "<p>La maga Frieren ripercorre il camino intrapreso con l’eroe Himmel, mentre ne tiene vivo il ricordo. La sua coscienza torna indietro nel tempo, dove si trova nuovamente a fronteggiare le a… [TRUNCATED 156 chars; original string value]",
      "Language": "it",
      "Locale": {
        "LanguageCode": "ita",
        "ScriptCode": "",
        "CountryCode": ""
      },
      "ImageId": "7273ec92-846d-458d-9925-5d4ff33b1694",
      "PublisherName": "J-POP Manga",
      "Rating": 5,
      "TotalRating": 2,
      "Slug": "frieren-oltre-la-fine-del-viaggio-vol-13",
      "IsContentSharingEnabled": true,
      "RedirectPreviewUrls": [
        {
          "DrmType": "None",
          "Format": "EPUB3FL_SAMPLE",
          "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
          "Platform": "Generic",
          "Size": 29465190
        }
      ],
      "HasPreview": true,
      "Price": {
        "Currency": "EUR",
        "Price": 3.99
      },
      "PromoCodeAllowed": false,
      "EligibleForKoboLoveDiscount": false,
      "IsPreOrder": false,
      "RelatedGroupId": "d131f1db-5067-6f6d-0000-000000000000",
      "AgeVerificationRequired": false,
      "AccessibilityDetails": {
        "IsFixedLayout": true,
        "IsTextToSpeechAllowed": false,
        "PrimaryContentType": "10",
        "ContentTypes": [],
        "EPubAccessibilities": [
          {
            "Type": "08"
          }
        ],
        "HazardWarningTypes": [],
        "IsAccessible": false
      },
      "LifeCycleDates": {
        "ActivationDate": "2024-09-24T00:00:00.0000000Z"
      },
      "Id": "2618a30b-165d-4bdf-8c48-f20c3fce40a3"
    },
    {
      "Contributors": "Kanehito Yamada,Abe Tsukasa,Matteo Cremaschi",
      "WorkId": "17cdaf56-209a-31d3-9d9c-29e3980da9d3",
      "SeriesNumber": "12",
      "SeriesName": "Frieren. Oltre la fine del viaggio",
      "SeriesSlug": "frieren-oltre-la-fine-del-viaggio-1",
      "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
      "SeriesNumberFloat": 12,
      "IsFree": false,
      "ISBN": "9788834928684",
      "PublicationDate": "2024-04-23T00:00:00.0000000Z",
      "ContributorRoles": [
        {
          "Name": "Kanehito Yamada",
          "Role": "Author"
        },
        {
          "Name": "Abe Tsukasa",
          "Role": "Illustrator"
        },
        {
          "Name": "Matteo Cremaschi",
          "Role": "Translator"
        }
      ],
      "IsInternetArchive": false,
      "IsRecommendation": false,
      "CrossRevisionId": "17cdaf56-209a-31d3-9d9c-29e3980da9d3",
      "Title": "Frieren. Oltre la fine del viaggio (Vol.12)",
      "Description": "<p>Frieren è la maga elfa che insieme alla compagnia dell’eroe ha sconfitto il Re Demone. La sua vita continua a lungo dopo quell’avventura ma, quando tocca la Stele della Dea, la sua coscie… [TRUNCATED 177 chars; original string value]",
      "Language": "it",
      "Locale": {
        "LanguageCode": "ita",
        "ScriptCode": "",
        "CountryCode": ""
      },
      "ImageId": "0f13b8cd-9045-4d50-a7a0-40e285902d10",
      "PublisherName": "J-POP Manga",
      "Rating": 5,
      "TotalRating": 4,
      "Slug": "frieren-oltre-la-fine-del-viaggio-vol-12",
      "IsContentSharingEnabled": true,
      "RedirectPreviewUrls": [
        {
          "DrmType": "None",
          "Format": "EPUB3FL_SAMPLE",
          "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
          "Platform": "Generic",
          "Size": 38734693
        }
      ],
      "HasPreview": true,
      "Price": {
        "Currency": "EUR",
        "Price": 3.99
      },
      "PromoCodeAllowed": false,
      "EligibleForKoboLoveDiscount": false,
      "IsPreOrder": false,
      "RelatedGroupId": "e48b4ebe-226c-2a4b-0000-000000000000",
      "AgeVerificationRequired": false,
      "AccessibilityDetails": {
        "IsFixedLayout": true,
        "IsTextToSpeechAllowed": false,
        "PrimaryContentType": "10",
        "ContentTypes": [],
        "EPubAccessibilities": [
          {
            "Type": "08"
          }
        ],
        "HazardWarningTypes": [],
        "IsAccessible": false
      },
      "LifeCycleDates": {
        "ActivationDate": "2024-04-23T00:00:00.0000000Z"
      },
      "Id": "f3212cd2-0ca8-49ec-9dea-4263d50b057e"
    },
    {
      "Contributors": "Kanehito Yamada,Tsukasa Abe,Matteo Cremaschi",
      "WorkId": "82921db9-82e1-38df-862b-11a7532b40d1",
      "SeriesNumber": "10",
      "SeriesName": "Frieren. Oltre la fine del viaggio",
      "SeriesSlug": "frieren-oltre-la-fine-del-viaggio-1",
      "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
      "SeriesNumberFloat": 10,
      "IsFree": false,
      "ISBN": "9788834923719",
      "PublicationDate": "2023-11-22T00:00:00.0000000Z",
      "ContributorRoles": [
        {
          "Name": "Kanehito Yamada",
          "Role": "Author"
        },
        {
          "Name": "Tsukasa Abe",
          "Role": "Author"
        },
        {
          "Name": "Matteo Cremaschi",
          "Role": "Translator"
        }
      ],
      "IsInternetArchive": false,
      "IsRecommendation": false,
      "CrossRevisionId": "82921db9-82e1-38df-862b-11a7532b40d1",
      "Title": "Frieren. Oltre la fine del viaggio (Vol.10)",
      "Description": "<p>L’elfo mago Frieren e i suoi coraggiosi compagni d’avventura hanno sconfitto il Re Demone, portando finalmente la pace nella loro terra. Ora gli eroi possono intraprendere strade diverse,… [TRUNCATED 310 chars; original string value]",
      "Language": "it",
      "Locale": {
        "LanguageCode": "ita",
        "ScriptCode": "",
        "CountryCode": ""
      },
      "ImageId": "888d918c-f169-4514-b375-04762dc963f8",
      "PublisherName": "J-POP Manga",
      "Rating": 5,
      "TotalRating": 3,
      "Slug": "frieren-oltre-la-fine-del-viaggio-vol-10",
      "IsContentSharingEnabled": true,
      "RedirectPreviewUrls": [
        {
          "DrmType": "None",
          "Format": "EPUB3FL_SAMPLE",
          "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
          "Platform": "Generic",
          "Size": 29951838
        }
      ],
      "HasPreview": true,
      "Price": {
        "Currency": "EUR",
        "Price": 3.99
      },
      "PromoCodeAllowed": false,
      "EligibleForKoboLoveDiscount": false,
      "IsPreOrder": false,
      "RelatedGroupId": "5958f7f7-0d4f-c618-0000-000000000000",
      "AgeVerificationRequired": false,
      "AccessibilityDetails": {
        "IsFixedLayout": true,
        "IsTextToSpeechAllowed": false,
        "PrimaryContentType": "10",
        "ContentTypes": [],
        "EPubAccessibilities": [
          {
            "Type": "08"
          }
        ],
        "HazardWarningTypes": [],
        "IsAccessible": false
      },
      "LifeCycleDates": {
        "ActivationDate": "2023-11-22T00:00:00.0000000Z"
      },
      "Id": "62f623f6-4b4d-43ec-afc1-85cd5ce99a06"
    },
    {
      "Contributors": "Tsukasa Abe,Kanehito Yamada,Matteo Cremaschi",
      "WorkId": "5b275628-f6e1-3ec8-8eed-88a983b6fd5a",
      "SeriesNumber": "8",
      "SeriesName": "Frieren. Oltre la fine del viaggio",
      "SeriesSlug": "frieren-oltre-la-fine-del-viaggio",
      "SeriesId": "e99dd828-368e-5b4d-b728-5386dc0c2307",
      "SeriesNumberFloat": 8,
      "IsFree": false,
      "ISBN": "9788834923696",
      "PublicationDate": "2023-09-27T00:00:00.0000000Z",
      "ContributorRoles": [
        {
          "Name": "Tsukasa Abe",
          "Role": "Author"
        },
        {
          "Name": "Kanehito Yamada",
          "Role": "Author"
        },
        {
          "Name": "Matteo Cremaschi",
          "Role": "Translator"
        }
      ],
      "IsInternetArchive": false,
      "IsRecommendation": false,
      "CrossRevisionId": "5b275628-f6e1-3ec8-8eed-88a983b6fd5a",
      "Title": "Frieren. Oltre la fine del viaggio (Vol.8)",
      "Description": "<p>Ripercorrendo il viaggio intrapreso tempo addietro con i suoi eroici compagni. la maga Frieren sta per entrare nel pericolosissimo Altopiano del Nord, a cui si può accedere solo in compag… [TRUNCATED 210 chars; original string value]",
      "Language": "it",
      "Locale": {
        "LanguageCode": "ita",
        "ScriptCode": "",
        "CountryCode": ""
      },
      "ImageId": "25eb8e4b-9eec-42ac-beaf-7c153deaf183",
      "PublisherName": "J-POP Manga",
      "Rating": 4.833333,
      "TotalRating": 6,
      "Slug": "frieren-oltre-la-fine-del-viaggio-vol-8",
      "IsContentSharingEnabled": true,
      "RedirectPreviewUrls": [
        {
          "DrmType": "None",
          "Format": "EPUB3FL_SAMPLE",
          "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
          "Platform": "Generic",
          "Size": 30305329
        }
      ],
      "HasPreview": true,
      "Price": {
        "Currency": "EUR",
        "Price": 3.99
      },
      "PromoCodeAllowed": false,
      "EligibleForKoboLoveDiscount": false,
      "IsPreOrder": false,
      "RelatedGroupId": "6efef492-3942-b021-0000-000000000000",
      "AgeVerificationRequired": false,
      "AccessibilityDetails": {
        "IsFixedLayout": true,
        "IsTextToSpeechAllowed": false,
        "PrimaryContentType": "10",
        "ContentTypes": [],
        "EPubAccessibilities": [
          {
            "Type": "08"
          }
        ],
        "HazardWarningTypes": [],
        "IsAccessible": false
      },
      "LifeCycleDates": {
        "ActivationDate": "2023-09-27T00:00:00.0000000Z"
      },
      "Id": "cb4922b2-7577-4fa6-878a-d19d1891322a"
    }
  ]
}
```


## 240. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L1274`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L1274.json`.

```json
[]
```


## 241. not logged `/v1/products/books/series/{uuid}` — Komga upstream raw proxy log response

Source: `server:L1276`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_series_uuid/response_L1276.json`.

```json
{
  "Items": [
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
        "SeriesNumber": "1",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 1,
        "IsFree": false,
        "ISBN": "9781421581514",
        "PublicationDate": "2014-12-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "7aa68593-e607-3a3a-8c9f-bf4723bd7638",
        "Title": "Assassination Classroom, Vol. 1",
        "Description": "<p>Meet the would-be assassins of class 3-E: Sugino, who let his grades slip and got kicked off the baseball team. Karma, who’s doing well in his classes but keeps getting suspended for figh… [TRUNCATED 308 chars; original string value]",
        "Language": "en",
        "ImageId": "d05b2324-4b83-487f-83d4-8fce0e17389b",
        "PublisherName": "VIZ Media",
        "Rating": 4.746835,
        "TotalRating": 158,
        "Slug": "assassination-classroom-vol-1",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 8639468
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "42a9ddd1-ddb2-31d8-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2014-12-02T00:00:00.0000000Z"
        },
        "Id": "ef8c966c-b59d-4768-85f0-586f932245d8"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "86910b17-9f8f-3e97-81a7-7a60c3757cd6",
        "SeriesNumber": "2",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 2,
        "IsFree": false,
        "ISBN": "9781421582252",
        "PublicationDate": "2015-02-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "86910b17-9f8f-3e97-81a7-7a60c3757cd6",
        "Title": "Assassination Classroom, Vol. 2",
        "Description": "<p>A sexy new teacher comes to Class 3-E to do the students’ job for them. When the 3-E students begin exhibiting signs of self-esteem, Principal Asano demands that Koro Sensei crush their s… [TRUNCATED 199 chars; original string value]",
        "Language": "en",
        "ImageId": "c35f96bf-0960-4a6a-988c-cadc6d896722",
        "PublisherName": "VIZ Media",
        "Rating": 4.659341,
        "TotalRating": 91,
        "Slug": "assassination-classroom-vol-2",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 8481895
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "0cd4a006-ac68-3527-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-02-03T00:00:00.0000000Z"
        },
        "Id": "6ac9b650-c351-4db7-a56a-4e6fba57ae16"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "59c8e513-4c13-3c7b-8d3c-d1112c45efc6",
        "SeriesNumber": "3",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 3,
        "IsFree": false,
        "ISBN": "9781421583532",
        "PublicationDate": "2015-04-07T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "59c8e513-4c13-3c7b-8d3c-d1112c45efc6",
        "Title": "Assassination Classroom, Vol. 3",
        "Description": "<p>The latest addition to the misfit students of Class 3-E is a Norwegian robot specially designed to assassinate their teacher—but sadly lacking in social skills. Nothing Koro Sensei can’t … [TRUNCATED 277 chars; original string value]",
        "Language": "en",
        "ImageId": "c5d3d0f2-db98-4a5e-977d-5736d868ae12",
        "PublisherName": "VIZ Media",
        "Rating": 4.864407,
        "TotalRating": 59,
        "Slug": "assassination-classroom-vol-3",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 9140937
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "90191d55-cb9e-3280-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-04-07T00:00:00.0000000Z"
        },
        "Id": "7422721f-7ab4-4e7c-8b9f-38d8a9365dd8"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "8ac9d87e-6bba-3eec-8fcd-37545efd6846",
        "SeriesNumber": "4",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 4,
        "IsFree": false,
        "ISBN": "9781421584591",
        "PublicationDate": "2015-06-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "8ac9d87e-6bba-3eec-8fcd-37545efd6846",
        "Title": "Assassination Classroom, Vol. 4",
        "Description": "<p>English teacher Irina’s assassination mentor makes an appearance and the two compete-using special agent Karasuma as their target. Another transfer student/would-be assassin joins the cla… [TRUNCATED 309 chars; original string value]",
        "Language": "en",
        "ImageId": "8391a75d-321d-4337-b6fd-479e33b7cb13",
        "PublisherName": "VIZ Media",
        "Rating": 4.887097,
        "TotalRating": 62,
        "Slug": "assassination-classroom-vol-4",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4110331
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "c2785c82-db5c-693e-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-06-02T00:00:00.0000000Z"
        },
        "Id": "4ffaf36e-4499-482b-bc8c-092eaca98333"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "967b0aa8-34be-31d4-b245-e3b18285a2af",
        "SeriesNumber": "5",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 5,
        "IsFree": false,
        "ISBN": "9781421585932",
        "PublicationDate": "2015-08-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "967b0aa8-34be-31d4-b245-e3b18285a2af",
        "Title": "Assassination Classroom, Vol. 5",
        "Description": "<p>Intramural relations are at an all-time low: Principal Asano wants to crush Koro Sensei’s 3-E baseball team while Koro Sensei wants to avenge Principal Asano’s sabotage of 3-E’s midterms.… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "1692d027-3217-4bbf-9f54-d581843e87c5",
        "PublisherName": "VIZ Media",
        "Rating": 4.862069,
        "TotalRating": 58,
        "Slug": "assassination-classroom-vol-5",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 12405574
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "d0c23000-b73e-08f3-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-08-04T00:00:00.0000000Z"
        },
        "Id": "017ef755-8f18-4d08-8498-d4a756ee05c0"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "3b567237-b447-3365-a275-a7194da15d37",
        "SeriesNumber": "6",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 6,
        "IsFree": false,
        "ISBN": "9781421587295",
        "PublicationDate": "2015-10-06T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "3b567237-b447-3365-a275-a7194da15d37",
        "Title": "Assassination Classroom, Vol. 6",
        "Description": "<p>The 3-E students discover that Koro Sensei’s greatest weakness might be a common substance. Will they be able to use it to assassinate him while he helps Meg, formerly of the varsity swim… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "2792d6be-a59f-4cf1-9334-28d9b23a2e40",
        "PublisherName": "VIZ Media",
        "Rating": 4.803922,
        "TotalRating": 51,
        "Slug": "assassination-classroom-vol-6",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4440228
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "97d822d8-d393-6733-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-10-06T00:00:00.0000000Z"
        },
        "Id": "4b05fb4e-f593-45a2-83e8-29e2c743e5d6"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "fdc4dc89-f2e2-321a-b2f6-320b48129396",
        "SeriesNumber": "7",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 7,
        "IsFree": false,
        "ISBN": "9781421588759",
        "PublicationDate": "2015-12-01T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "fdc4dc89-f2e2-321a-b2f6-320b48129396",
        "Title": "Assassination Classroom, Vol. 7",
        "Description": "<p>The 3-E students hope to kill on their final exams to win not only respect but a special reward. Over summer break, Nagisa, Sugino, and Maehara play amateur entomologist with…a girl? Afte… [TRUNCATED 191 chars; original string value]",
        "Language": "en",
        "ImageId": "1c023e1b-ac06-4c26-9f7a-2b4bdc25e07d",
        "PublisherName": "VIZ Media",
        "Rating": 4.924528,
        "TotalRating": 53,
        "Slug": "assassination-classroom-vol-7",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4242268
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "d0c23000-b73e-08f3-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2015-12-01T00:00:00.0000000Z"
        },
        "Id": "bda21916-7a11-464b-be46-90c61f67e519"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "f0a354cd-501c-33a4-a91a-e935a72be11b",
        "SeriesNumber": "8",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 8,
        "IsFree": false,
        "ISBN": "9781421589411",
        "PublicationDate": "2016-02-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "f0a354cd-501c-33a4-a91a-e935a72be11b",
        "Title": "Assassination Classroom, Vol. 8",
        "Description": "<p>The 3-E students head for the lair of the enemy to stop a deadly biological attack. But blocking their path are three master assassins: Smog, Grip and Gastro, who excel, respectively, in … [TRUNCATED 278 chars; original string value]",
        "Language": "en",
        "ImageId": "fc7a8e96-dbb1-4901-af7f-cee76b99d58e",
        "PublisherName": "VIZ Media",
        "Rating": 4.804878,
        "TotalRating": 41,
        "Slug": "assassination-classroom-vol-8",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4304793
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "d0c23000-b73e-08f3-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-02-02T00:00:00.0000000Z"
        },
        "Id": "b73681b8-47cf-47d3-b36c-848a73e41265"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "ed24f870-f263-31b6-9562-7d65678f483d",
        "SeriesNumber": "9",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 9,
        "IsFree": false,
        "ISBN": "9781421589978",
        "PublicationDate": "2016-04-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "ed24f870-f263-31b6-9562-7d65678f483d",
        "Title": "Assassination Classroom, Vol. 9",
        "Description": "<p>Nagisa risks it all in an attempt to take down the mastermind behind the biological attack on his classmates. After the dust settles, Koro Sensei gives his all to ignite summer romance be… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "5d45c81b-2f02-47fa-aedc-ffb493935054",
        "PublisherName": "VIZ Media",
        "Rating": 4.840909,
        "TotalRating": 44,
        "Slug": "assassination-classroom-vol-9",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3462855
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "d0c23000-b73e-08f3-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-04-05T00:00:00.0000000Z"
        },
        "Id": "ba5e6127-67b2-47d9-8e8b-0204038a83b9"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "0b7e3984-efa0-363d-8836-65137e4eb98d",
        "SeriesNumber": "10",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 10,
        "IsFree": false,
        "ISBN": "9781421590769",
        "PublicationDate": "2016-06-07T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "0b7e3984-efa0-363d-8836-65137e4eb98d",
        "Title": "Assassination Classroom, Vol. 10",
        "Description": "<p>The students of 3-E turn a surplus of eggs to good use—in pursuit of their usual goal. Karasuma teaches them the art of parkour while Koro Sensei teaches them the game of cops and robbers… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "8e620c0b-9b7b-4a45-9d7c-7c557e5a5616",
        "PublisherName": "VIZ Media",
        "Rating": 4.888889,
        "TotalRating": 36,
        "Slug": "assassination-classroom-vol-10",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3521733
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "c4c40c7a-df39-0960-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-06-07T00:00:00.0000000Z"
        },
        "Id": "85b5e085-321a-413e-9214-0604639ba05a"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "a146a737-b23c-3d1a-bcef-92a077ce50fe",
        "SeriesNumber": "11",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 11,
        "IsFree": false,
        "ISBN": "9781421591896",
        "PublicationDate": "2016-08-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "a146a737-b23c-3d1a-bcef-92a077ce50fe",
        "Title": "Assassination Classroom, Vol. 11",
        "Description": "<p>3-E student Isogai breaks the school rules by working part-time to help support his family. When Gakushu and rest of the Big Four students find out, they use his secret as leverage to for… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "406d67d4-9111-4a5d-8eac-cd225585477c",
        "PublisherName": "VIZ Media",
        "Rating": 4.902439,
        "TotalRating": 41,
        "Slug": "assassination-classroom-vol-11",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4814783
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "4f040b41-df39-0960-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-08-02T00:00:00.0000000Z"
        },
        "Id": "92613adb-f6a6-40c6-ab09-748485440869"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "30177c4d-f633-3800-be1e-bdcd973113cb",
        "SeriesNumber": "12",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 12,
        "IsFree": false,
        "ISBN": "9781421592800",
        "PublicationDate": "2016-10-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "30177c4d-f633-3800-be1e-bdcd973113cb",
        "Title": "Assassination Classroom, Vol. 12",
        "Description": "<p>Mr. Karasuma gives the 3-E students superpowered uniforms…with great results. What amazing feats will they perform in their snazzy new outfits? Then, Mr. Karasuma gives Ms. Vitch a bouque… [TRUNCATED 309 chars; original string value]",
        "Language": "en",
        "ImageId": "d454ce09-ec2d-4bdc-8c01-e0f66b3ef770",
        "PublisherName": "VIZ Media",
        "Rating": 4.72973,
        "TotalRating": 37,
        "Slug": "assassination-classroom-vol-12",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3525981
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "f8040702-b6d8-0c97-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-10-04T00:00:00.0000000Z"
        },
        "Id": "dad7f4b8-503a-4f3f-9660-4cff504fb8cb"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "3015ac48-6a97-3733-a017-11a0f8cf6f83",
        "SeriesNumber": "13",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 13,
        "IsFree": false,
        "ISBN": "9781421594613",
        "PublicationDate": "2016-12-06T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "3015ac48-6a97-3733-a017-11a0f8cf6f83",
        "Title": "Assassination Classroom, Vol. 13",
        "Description": "<p>The Grim Reaper sets up a lethal trap that buries Mr. Karasuma and Ms. Vitch alive. Will Mr. Karasuma’s repressed feelings surface before he and Ms. Vitch do? And how will Mr. Karasuma fa… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "4820c3e4-8ba5-4ce1-927b-c6092aeb70c6",
        "PublisherName": "VIZ Media",
        "Rating": 4.823529,
        "TotalRating": 34,
        "Slug": "assassination-classroom-vol-13",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4346748
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "e1040a0c-30bd-09ec-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2016-12-06T00:00:00.0000000Z"
        },
        "Id": "4fa9defa-9533-48da-9034-9b7877d1491f"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "b8dc670e-03d8-3afe-b738-747b09a35753",
        "SeriesNumber": "14",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 14,
        "IsFree": false,
        "ISBN": "9781421595610",
        "PublicationDate": "2017-02-07T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "b8dc670e-03d8-3afe-b738-747b09a35753",
        "Title": "Assassination Classroom, Vol. 14",
        "Description": "<p>Lowly Class E and elite Class A compete for the most customers at their school festival booths. So far, the students of Class E are holding their own, despite their location on the mounta… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "6b22bce6-a4d4-484d-be10-a01c7276ecbb",
        "PublisherName": "VIZ Media",
        "Rating": 4.828571,
        "TotalRating": 35,
        "Slug": "assassination-classroom-vol-14",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3775055
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "c284006d-1ab3-0a9b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-02-07T00:00:00.0000000Z"
        },
        "Id": "95a341d8-3c99-4fc7-83f6-3316153ca7d2"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "b05b1d24-b83b-3379-ac28-deb12c54bb67",
        "SeriesNumber": "15",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 15,
        "IsFree": false,
        "ISBN": "9781421596686",
        "PublicationDate": "2017-04-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "b05b1d24-b83b-3379-ac28-deb12c54bb67",
        "Title": "Assassination Classroom, Vol. 15",
        "Description": "<p>Several secret pasts are finally revealed this volume: The tragedy that led Principal Asano to develop his harsh—some would say brutal, sadistic and inhumane—pedagogical methods, the rela… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "199f4df0-8ccd-43e5-864d-e3b9976d0800",
        "PublisherName": "VIZ Media",
        "Rating": 4.868421,
        "TotalRating": 38,
        "Slug": "assassination-classroom-vol-15",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3417714
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "aa84016f-1ab5-0a9b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-04-04T00:00:00.0000000Z"
        },
        "Id": "afe60cd6-0e2f-4ed8-b61f-8467b730a40f"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "aaa32e7c-0a4c-3e18-94b5-a940b3275f69",
        "SeriesNumber": "16",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 16,
        "IsFree": false,
        "ISBN": "9781421597829",
        "PublicationDate": "2017-06-06T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "aaa32e7c-0a4c-3e18-94b5-a940b3275f69",
        "Title": "Assassination Classroom, Vol. 16",
        "Description": "<p>Koro Sensei tells all: his former profession, his nickname, the mad scientists responsible for his unique cephalopod physiognomy, the love of his life, and why he wants to teach the 3-E s… [TRUNCATED 218 chars; original string value]",
        "Language": "en",
        "ImageId": "d7443998-c2b6-4728-8da4-72975519447e",
        "PublisherName": "VIZ Media",
        "Rating": 4.837838,
        "TotalRating": 37,
        "Slug": "assassination-classroom-vol-16",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4439726
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "a6389896-77b9-51a4-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-06-06T00:00:00.0000000Z"
        },
        "Id": "e2bc01ad-6a2c-450a-a9cd-cf17fddd219e"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "f18aff66-8c1f-35bd-bfa5-ead1e81952c7",
        "SeriesNumber": "17",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 17,
        "IsFree": false,
        "ISBN": "9781421598802",
        "PublicationDate": "2017-08-01T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "f18aff66-8c1f-35bd-bfa5-ead1e81952c7",
        "Title": "Assassination Classroom, Vol. 17",
        "Description": "<p>The class is divided: one half wants to carry on with the plan to assassinate Koro Sensei, while the other half wants to save him. So Koro Sensei splits them into two teams, led by Nagisa… [TRUNCATED 247 chars; original string value]",
        "Language": "en",
        "ImageId": "acc3a856-e1c5-48ca-b446-1a5dd7e02733",
        "PublisherName": "VIZ Media",
        "Rating": 4.78125,
        "TotalRating": 32,
        "Slug": "assassination-classroom-vol-17",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5057867
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "8caf4225-3af8-5019-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-08-01T00:00:00.0000000Z"
        },
        "Id": "40b77ba7-f3b3-4069-bee6-8d090b54e1c1"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "5b33e643-5c8c-3d22-9276-4b4fb439cee1",
        "SeriesNumber": "18",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 18,
        "IsFree": false,
        "ISBN": "9781421599830",
        "PublicationDate": "2017-10-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "5b33e643-5c8c-3d22-9276-4b4fb439cee1",
        "Title": "Assassination Classroom, Vol. 18",
        "Description": "<p>Nagisa and Karma travel to the International Space Station in hopes of learning the secret to saving Koro Sensei’s life. Meanwhile, Yanagisawa and the upstart Grim Reaper II train ever ha… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "3001f45f-367f-41d5-84b5-d7a6479795ef",
        "PublisherName": "VIZ Media",
        "Rating": 4.864865,
        "TotalRating": 37,
        "Slug": "assassination-classroom-vol-18",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4449790
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "2d82307a-c0cb-0d73-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-10-03T00:00:00.0000000Z"
        },
        "Id": "11842257-e12e-43b6-a735-ade86380c293"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "6de3a253-988b-3054-b767-0688dfab903c",
        "SeriesNumber": "19",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 19,
        "IsFree": false,
        "ISBN": "9781974701148",
        "PublicationDate": "2017-12-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "6de3a253-988b-3054-b767-0688dfab903c",
        "Title": "Assassination Classroom, Vol. 19",
        "Description": "<p>Koro Sensei’s lessons in verbal defense are put to the test when Karma must use his brains instead of his brawn to rescue a classmate. Then things finally begin to go smoothly for the stu… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "c44b44e7-744c-49fd-9ce9-0aeda9abeb3e",
        "PublisherName": "VIZ Media",
        "Rating": 4.852941,
        "TotalRating": 34,
        "Slug": "assassination-classroom-vol-19",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4300287
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "fc423013-d186-0d73-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2017-12-05T00:00:00.0000000Z"
        },
        "Id": "08ff5116-8a96-4745-83dc-63e12c3244b6"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "cb315d39-fd83-3aae-942a-913320760ed4",
        "SeriesNumber": "20",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 20,
        "IsFree": false,
        "ISBN": "9781974701933",
        "PublicationDate": "2018-02-06T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "cb315d39-fd83-3aae-942a-913320760ed4",
        "Title": "Assassination Classroom, Vol. 20",
        "Description": "<p>The battle for Koro Sensei’s life against a multinational operation’s soldiers and weaponry is difficult enough, but now his creator, mad scientist Yanagisawa, and his former protégé, Gri… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "ImageId": "9615fdae-1848-4b2a-9c2a-ed6441878eba",
        "PublisherName": "VIZ Media",
        "Rating": 4.927273,
        "TotalRating": 55,
        "Slug": "assassination-classroom-vol-20",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4920330
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "08023041-c2dd-0d73-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2018-02-06T00:00:00.0000000Z"
        },
        "Id": "f983f4de-7423-4723-962b-a37bff0bd201"
      }
    },
    {
      "Book": {
        "Contributors": "Yusei Matsui",
        "WorkId": "00671a42-1b57-32fc-8976-0cc695688799",
        "SeriesNumber": "21",
        "SeriesName": "Assassination Classroom",
        "SeriesSlug": "assassination-classroom",
        "SeriesId": "a1f4ca41-e3e3-58e4-aeab-cb486a44f5c3",
        "SeriesNumberFloat": 21,
        "IsFree": false,
        "ISBN": "9781974702763",
        "PublicationDate": "2018-04-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yusei Matsui",
            "Role": "Author"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "00671a42-1b57-32fc-8976-0cc695688799",
        "Title": "Assassination Classroom, Vol. 21",
        "Description": "<p>In the aftermath of tragedy, the students of 3-E nevertheless march proudly in their graduation ceremony. Will their futures still unfold as planned? And what will they do with their rewa… [TRUNCATED 265 chars; original string value]",
        "Language": "en",
        "ImageId": "bf2a74f3-783e-4a4c-9795-42ed92aac89d",
        "PublisherName": "VIZ Media",
        "Rating": 4.849057,
        "TotalRating": 53,
        "Slug": "assassination-classroom-vol-21",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5259280
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PromoCodeAllowed": true,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "8502309b-cc14-0d73-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2018-04-03T00:00:00.0000000Z"
        },
        "Id": "7d21c738-bdca-4194-bd5e-a9387f3970af"
      }
    }
  ],
  "ItemCount": 21,
  "TotalPageCount": 1,
  "TotalItemCount": 21,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "VersionCode": 2
}
```


## 242. not logged `/v1/user/wishlist` — Komga upstream standard proxy log response

Source: `server:L1280`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_wishlist/response_L1280.json`.

```json
{
  "TotalCountByProductType": {},
  "Items": [],
  "ItemCount": 0,
  "TotalPageCount": 0,
  "TotalItemCount": 0,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "VersionCode": 2
}
```


## 243. not logged `/v1/initialization` — Komga upstream standard proxy log response

Source: `server:L1283`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_initialization/response_L1283.json`.

```json
{
  "Resources": {
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "products": "https://storeapi.kobo.com/v1/products",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "image_host": "//cdn.kobo.com/book-images/",
    "store_host": "www.kobo.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "reading_services_host": "https://readingservices.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "oauth_host": "https://oauth.kobo.com",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "redeem_interstitial_page": "https://www.kobo.com",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "help_page": "https://www.kobo.com/help",
    "image_url_template": "https://cdn.kobo.com/book-images/{ImageId}/{Width}/{Height}/false/image.jpg",
    "image_url_quality_template": "https://cdn.kobo.com/book-images/{ImageId}/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "use_one_store": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_subscriptions_enabled": "True",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_nativeborrow_enabled": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_wishlist_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_redeem_enabled": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_display_price": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "gpb_flow_enabled": "False",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "display_parental_controls_enabled": "True",
    "display_accessibility_enabled": "True",
    "text_to_speech_region_override": "False",
    "reflowable_page_cache_enabled": "True",
    "fixed_layout_page_cache_enabled": "True",
    "instapaper_enabled": "True",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo"
  }
}
```


## 244. not logged `/v1/user/profile` — Komga upstream standard proxy log response

Source: `server:L1286`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `upstream/unknown_v1_user_profile/response_L1286.json`.

```json
{
  "IsOneStore": true,
  "IsChildAccount": false,
  "CountryCode": "IT",
  "Geo": "IT",
  "StoreFront": "IT",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "AffiliateName": "Mondadori",
  "IsoCultureCode": "en-US",
  "LoyaltyDetails": {
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTags": 2,
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyCurrentBalance": 0
  },
  "IsLibraryMigrated": false,
  "VipMembershipPurchased": false,
  "HasPurchased": true,
  "HasPurchasedBook": true,
  "HasPurchasedAudiobook": false,
  "SafeSearch": false,
  "AudiobooksEnabled": true,
  "IsOrangeAffiliated": false,
  "IsEligibleForOrangeDeal": false,
  "PrivacyPermissions": [],
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "UserId": "[REDACTED]",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "ContactEmail": "[REDACTED]"
}
```


## 245. not logged `/v1/user/loyalty/benefits` — Komga upstream standard proxy log response

Source: `server:L1289`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_user_loyalty_benefits/response_L1289.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "MembershipLevel": "KoboLoveBasic",
      "PointEarnRate": 200,
      "CurrencySpendRate": 10.0,
      "CurrencyType": "EUR",
      "MinimumPointRedemption": 2400,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "MembershipLevel": "KoboLoveVIP",
      "PointEarnRate": 400,
      "CurrencySpendRate": 10.0,
      "CurrencyType": "EUR",
      "DiscountRate": 0.0,
      "MinimumPointRedemption": 2400,
      "LoveProduct": {
        "Title": "Kobo VIP Membership",
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "LovePointsPrice": 8000,
        "IsPreOrder": false,
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9"
      }
    }
  }
}
```


## 246. not logged `/v1/products/books/subscriptions` — Komga upstream raw proxy log response

Source: `server:L1291`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `upstream/unknown_v1_products_books_subscriptions/response_L1291.json`.

```json
[
  {
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Id": "d41549e4-50e9-41c1-ac86-7941f96c122b",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc"
  },
  {
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Id": "9697bdb6-539d-45ca-b681-fd5161ed58d6",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e"
  },
  {
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Id": "c5bedc21-8f47-4761-a651-fee1af49dc22",
        "Description": "",
        "Headline": "",
        "Phases": [
          {
            "Order": 0,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0.0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 0,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Trial"
          },
          {
            "Order": 2,
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ],
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              },
              "RebillInterval": {
                "IntervalUnits": 30,
                "IntervalUnitType": "Days"
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "PhaseType": "Active"
          }
        ]
      }
    ],
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "IsPreOrder": false,
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513"
  }
]
```


## 247. not logged `/v1/deals` — Komga upstream standard proxy log response

Source: `server:L1294`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_deals/response_L1294.json`.

```json
{
  "Deals": [
    {
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans",
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z"
    }
  ]
}
```


## 248. not logged `/v1/assets` — Komga upstream standard proxy log response

Source: `server:L1297`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `upstream/unknown_v1_assets/response_L1297.json`.

```json
{
  "AssetGroups": [
    {
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "ETag": "[REDACTED]",
      "Assets": [],
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 249. not logged `/v1/analytics/gettests` — Komga upstream standard proxy log response

Source: `server:L1300`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `upstream/unknown_v1_analytics_gettests/response_L1300.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 250. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L1306`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L1306.json`.

```json
[]
```


## 251. not logged `/v1/user/wishlist` — Komga upstream standard proxy log response

Source: `server:L1309`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_wishlist/response_L1309.json`.

```json
{
  "TotalCountByProductType": {},
  "Items": [],
  "ItemCount": 0,
  "TotalPageCount": 0,
  "TotalItemCount": 0,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "VersionCode": 2
}
```


## 252. not logged `/v1/products/books/series/{uuid}` — Komga upstream raw proxy log response

Source: `server:L1311`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_series_uuid/response_L1311.json`.

```json
{
  "Items": [
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "c2c1cce7-be77-3f2d-90b6-0cf69be4d06a",
        "SeriesNumber": "1",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 1,
        "IsFree": false,
        "ISBN": "9781975312534",
        "PublicationDate": "2020-04-28T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "c2c1cce7-be77-3f2d-90b6-0cf69be4d06a",
        "Title": "Rascal Does Not Dream of Bunny Girl Senpai (light novel)",
        "Description": "<p>Out of sight, out of mind!Bunny girls do not live in libraries. This is simply common sense. And yet, that’s exactly where Sakuta finds one in the wild. More bewildering is who the bunny … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e757659b-1336-4558-b891-3b5882f93b2c",
        "PublisherName": "Yen Press",
        "Rating": 4.62963,
        "TotalRating": 27,
        "Slug": "rascal-does-not-dream-of-bunny-girl-senpai-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6390130
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "348c332b-4382-8b12-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-04-28T00:00:00.0000000Z"
        },
        "Id": "b123ce7a-d0ca-4591-a967-4b567e04a7e0"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2bb34ec2-0004-39f6-907c-c246cacc4a69",
        "SeriesNumber": "2",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 2,
        "IsFree": false,
        "ISBN": "9781975312558",
        "PublicationDate": "2020-08-18T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2bb34ec2-0004-39f6-907c-c246cacc4a69",
        "Title": "Rascal Does Not Dream of Petite Devil Kohai (light novel)",
        "Description": "<p>How far can a fake relationship go?Mai is no longer invisible--she’s acting again, and she finally said yes when Sakuta asked her out for the millionth time. Life couldn’t be better for S… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "24bdbcd3-21b3-4570-a933-809ce3a12ccf",
        "PublisherName": "Yen Press",
        "Rating": 4.866667,
        "TotalRating": 15,
        "Slug": "rascal-does-not-dream-of-petite-devil-kohai-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5163024
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "f8459494-f564-e466-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-08-18T00:00:00.0000000Z"
        },
        "Id": "65d8e847-7cc5-474b-b161-8020795a7b52"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "f4922d85-aa3a-3dc0-94aa-7fe2785de89e",
        "SeriesNumber": "3",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 3,
        "IsFree": false,
        "ISBN": "9781975312572",
        "PublicationDate": "2020-11-17T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "f4922d85-aa3a-3dc0-94aa-7fe2785de89e",
        "Title": "Rascal Does Not Dream of Logical Witch (light novel)",
        "Description": "<p>Just before summer break, Sakuta encounters Shouko Makinohara, a girl in junior high with the same name and face as his first love. Of course, that should be impossible, since she would b… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "d41461c1-f8e9-45ce-a916-9fad7b4c5f7c",
        "PublisherName": "Yen Press",
        "Rating": 4.7,
        "TotalRating": 10,
        "Slug": "rascal-does-not-dream-of-logical-witch-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4833019
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "20809053-25a5-3878-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2020-11-17T00:00:00.0000000Z"
        },
        "Id": "8d71e6c4-700b-4d60-8798-1a87aa701151"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "9199d101-9b75-387d-bd8d-130509958d9e",
        "SeriesNumber": "4",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 4,
        "IsFree": false,
        "ISBN": "9781975312596",
        "PublicationDate": "2021-03-30T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "9199d101-9b75-387d-bd8d-130509958d9e",
        "Title": "Rascal Does Not Dream of Siscon Idol (light novel)",
        "Description": "<p>DOUBLE TROUBLE?! Sakuta is eager to finally have a chance to spend time with Mai again, but instead, he's greeted by a stranger who looks just like her. Apparently, Adolescence Syndrome i… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "352d16d5-1522-4366-9700-85074f7abc26",
        "PublisherName": "Yen Press",
        "Rating": 4.888889,
        "TotalRating": 9,
        "Slug": "rascal-does-not-dream-of-siscon-idol-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5465410
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "7c2a9d66-821d-edc0-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-03-30T00:00:00.0000000Z"
        },
        "Id": "4d5e26b1-5fdc-4abc-9cce-51a1624645d2"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "c3506cc6-5eb0-3d72-869f-ad7889086fdb",
        "SeriesNumber": "5",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 5,
        "IsFree": false,
        "ISBN": "9781975312619",
        "PublicationDate": "2021-07-27T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "c3506cc6-5eb0-3d72-869f-ad7889086fdb",
        "Title": "Rascal Does Not Dream of a Sister Home Alone (light novel)",
        "Description": "<p>GUESS WHO’S BACK? After years without contact, Sakuta has received a letter from his first love, Shouko, asking to meet at Shichirigahama Beach. Of course, now that he’s in a happy relati… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "31d683e9-deeb-4fad-9cb3-f4ce5248b382",
        "PublisherName": "Yen Press",
        "Rating": 4.7,
        "TotalRating": 10,
        "Slug": "rascal-does-not-dream-of-a-sister-home-alone-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4782787
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "2f371f81-64c5-112e-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-07-27T00:00:00.0000000Z"
        },
        "Id": "2bb10209-81a7-4a8d-b43c-bfff25b234ec"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2e269e63-8dfc-376d-a71c-8a68c8accf5b",
        "SeriesNumber": "6",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 6,
        "IsFree": false,
        "ISBN": "9781975312633",
        "PublicationDate": "2021-11-30T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2e269e63-8dfc-376d-a71c-8a68c8accf5b",
        "Title": "Rascal Does Not Dream of a Dreaming Girl (light novel)",
        "Description": "<p>SUPERSTRING (OF FATE) THEORY! Sakuta is doomed. His girlfriend, Mai, has just discovered that while she was away, he’s been living under one roof with his first love, the now-college-aged… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "553661b7-b450-4483-a56b-24a4231da870",
        "PublisherName": "Yen Press",
        "Rating": 4.583333,
        "TotalRating": 12,
        "Slug": "rascal-does-not-dream-of-a-dreaming-girl-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4199725
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "69b1a8d7-3ba0-ba56-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2021-11-30T00:00:00.0000000Z"
        },
        "Id": "9ba39b5f-b943-4677-bb76-72ee7b48c70f"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "dbe8d574-5f86-351e-b2dd-3f5fb2d2d806",
        "SeriesNumber": "7",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 7,
        "IsFree": false,
        "ISBN": "9781975312657",
        "PublicationDate": "2022-05-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "dbe8d574-5f86-351e-b2dd-3f5fb2d2d806",
        "Title": "Rascal Does Not Dream of His First Love (light novel)",
        "Description": "<p>UNDOING WHAT CAN’T BE UNDONE.Sakuta had been ready to give up his life, but nothing could have prepared him to keep living like this. Now he must find a way to carry on even when it feels… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e6e3428d-fc8b-4264-97fc-d4a18d5c9d2d",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 7,
        "Slug": "rascal-does-not-dream-of-his-first-love-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5087025
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "7b9ffbd1-8e2c-492e-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-05-03T00:00:00.0000000Z"
        },
        "Id": "5d439238-39d2-4494-b1be-470982a3c3b7"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "8407f486-face-38b6-bba3-5c0509e393eb",
        "SeriesNumber": "8",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 8,
        "IsFree": false,
        "ISBN": "9781975312671",
        "PublicationDate": "2022-08-23T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "8407f486-face-38b6-bba3-5c0509e393eb",
        "Title": "Rascal Does Not Dream of a Sister Venturing Out (light novel)",
        "Description": "<p>WHAT WILL IT TAKE TO MAKE HER WISH COME TRUE?After a draining December, Sakuta is quickly nearing the end of his second year of high school. Since Mai is a third-year student, they don’t … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "d92e808f-586a-4c2c-9092-132c91b4af2b",
        "PublisherName": "Yen Press",
        "Rating": 4.571429,
        "TotalRating": 7,
        "Slug": "rascal-does-not-dream-of-a-sister-venturing-out-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3980876
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "9894b315-4a3e-609b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-08-23T00:00:00.0000000Z"
        },
        "Id": "23253e95-2f31-4ed4-ba2a-e7c164714f48"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "678130e0-fe7d-32a6-9d92-b1d1db1b15a0",
        "SeriesNumber": "9",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 9,
        "IsFree": false,
        "ISBN": "9781975312695",
        "PublicationDate": "2022-12-13T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "678130e0-fe7d-32a6-9d92-b1d1db1b15a0",
        "Title": "Rascal Does Not Dream of a Knapsack Kid (light novel)",
        "Description": "<p>March has arrived, meaning there’s only one month left in the third term. Mai’s graduation has come and gone. This might be a time for change, but that isn’t nearly enough to explain why … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "9b2b4a4c-1245-45f8-b82b-5a315a9d78fc",
        "PublisherName": "Yen Press",
        "Rating": 4.875,
        "TotalRating": 8,
        "Slug": "rascal-does-not-dream-of-a-knapsack-kid-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3203816
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "0330e7a9-49ad-8151-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2022-12-13T00:00:00.0000000Z"
        },
        "Id": "79e9587a-2413-4acb-bd3e-cfd03047c32e"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "95b0319d-9cab-3ed5-8beb-c786962e9b31",
        "SeriesNumber": "10",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 10,
        "IsFree": false,
        "ISBN": "9781975318529",
        "PublicationDate": "2023-03-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "95b0319d-9cab-3ed5-8beb-c786962e9b31",
        "Title": "Rascal Does Not Dream of a Lost Singer (light novel)",
        "Description": "<p>Becoming a college student means starting a whole new life...right? Sakuta honestly isn’t sure. Some things have changed, but some things are still the same. His reputation definitely pre… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "a9c73cf8-2a63-418d-bff8-de71099df732",
        "PublisherName": "Yen Press",
        "Rating": 3.5,
        "TotalRating": 6,
        "Slug": "rascal-does-not-dream-of-a-lost-singer-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5217872
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "25bd2df1-efc2-1b2b-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-03-21T00:00:00.0000000Z"
        },
        "Id": "930b106d-1155-40c8-83c3-b30fdcd59aeb"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "77ee2397-da3d-30b7-8ded-08fcac7bfa83",
        "SeriesNumber": "11",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 11,
        "IsFree": false,
        "ISBN": "9781975343514",
        "PublicationDate": "2023-06-20T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "77ee2397-da3d-30b7-8ded-08fcac7bfa83",
        "Title": "Rascal Does Not Dream of a Nightingale (light novel)",
        "Description": "<p>WORST CHRISTMAS GIFT EVER…A miniskirt-wearing Santa appears before Sakuta, calling herself Touko Kirishima and cheerfully informing him that she’s been handing out presents—Adolescence Sy… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "6487d49e-9002-42b5-86a8-c279282c0da1",
        "PublisherName": "Yen Press",
        "Rating": 4.6,
        "TotalRating": 5,
        "Slug": "rascal-does-not-dream-of-a-nightingale-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 5043655
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "c5082778-235d-a0af-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-06-20T00:00:00.0000000Z"
        },
        "Id": "d8471959-e30c-4112-bf82-95431c740659"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "7ca60ca9-6a1d-39d5-9c5c-dbd65784d31e",
        "SeriesNumber": "12",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 12,
        "IsFree": false,
        "ISBN": "9781975375287",
        "PublicationDate": "2023-11-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "7ca60ca9-6a1d-39d5-9c5c-dbd65784d31e",
        "Title": "Rascal Does Not Dream of His Student (light novel)",
        "Description": "<p>MINISKIRT SANTA’S COMING TO TOWN!Mai is in danger, and the only clue Sakuta has is a cryptic message from himself saying he needs to find Touko Kirishima. The problem is that even though … [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "40f2719c-ac15-4212-889b-52c622454cb8",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 4,
        "Slug": "rascal-does-not-dream-of-his-student-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6936098
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 7.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 4000,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "ea17f08d-9253-f7c9-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2023-11-21T00:00:00.0000000Z"
        },
        "Id": "758b89ac-740e-4282-b68e-e29f3be349f8"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "cda7d480-de4b-3acc-8fb9-416f6b08af80",
        "SeriesNumber": "13",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 13,
        "IsFree": false,
        "ISBN": "9781975391614",
        "PublicationDate": "2024-08-20T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "cda7d480-de4b-3acc-8fb9-416f6b08af80",
        "Title": "Rascal Does Not Dream of Santa Claus (light novel)",
        "Description": "<p>IS THE REAL MINISKIRT SANTA IN A SEA OF FAKES? Sakuta thought he’d already accomplished step one: Find Touko Kirishima. The next step was convincing her to help protect Mai, but that prov… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "976e4895-485f-4917-917c-95cdc538e1a2",
        "PublisherName": "Yen Press",
        "Rating": 4.8,
        "TotalRating": 5,
        "Slug": "rascal-does-not-dream-of-santa-claus-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4028488
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "ddf6fe26-d52f-690a-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2024-08-20T00:00:00.0000000Z"
        },
        "Id": "8f8cbcb2-efa8-495e-b878-145be2dc5740"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "2107df94-a206-3768-9d5e-1b9f7ca29821",
        "SeriesNumber": "14",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 14,
        "IsFree": false,
        "ISBN": "9798855418309",
        "PublicationDate": "2025-06-10T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "2107df94-a206-3768-9d5e-1b9f7ca29821",
        "Title": "Rascal Does Not Dream of His Girlfriend (light novel)",
        "Description": "<p>AN UNBELIEVABLE ANNOUNCEMENT!The day Sakuta dreamed about is finally here. His search for Touko Kirishima has led him to a music festival where Mai is performing. Filled with concern and … [TRUNCATED 292 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "e48a878d-19c4-4b7a-b990-ee30d582cf6c",
        "PublisherName": "Yen Press",
        "Rating": 3,
        "TotalRating": 2,
        "Slug": "rascal-does-not-dream-of-his-girlfriend-light-novel-1",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 2342620
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3200,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "8290d577-8bbb-ea0c-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2025-06-10T00:00:00.0000000Z"
        },
        "Id": "40433014-01f1-4c1d-8c9d-12383ec9ca52"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "4cedc735-a995-3217-bb27-782c0059b685",
        "SeriesNumber": "15",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 15,
        "IsFree": false,
        "ISBN": "9798855422443",
        "PublicationDate": "2026-01-27T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "4cedc735-a995-3217-bb27-782c0059b685",
        "Title": "Rascal Does Not Dream of a Dear Friend (light novel)",
        "Description": "<p>one last dream…As the truth about Miori and Touko Kirishima comes to light, Sakuta must make a choice that will decide everything…for him…for Mai…for Shouko…for Miori…and for Touko. Only … [TRUNCATED 95 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "2e9d763d-e3ea-45eb-8798-821824801bf3",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 1,
        "Slug": "rascal-does-not-dream-of-a-dear-friend-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3875098
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "b1ff2847-0082-8193-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2026-01-27T00:00:00.0000000Z"
        },
        "Id": "bbd7b113-0a68-4a0d-bbdc-faa4986233f5"
      }
    },
    {
      "Book": {
        "Contributors": "Hajime Kamoshida,Keji Mizoguchi,Andrew Cunningham",
        "WorkId": "feb56b0a-f0b5-33a7-be3e-17b286c0f589",
        "SeriesNumber": "16",
        "SeriesName": "Rascal Does Not Dream (light novel)",
        "SeriesSlug": "rascal-does-not-dream-light-novel",
        "SeriesId": "1510b8fd-6154-557b-8af6-5d12ef17f4cb",
        "SeriesNumberFloat": 16,
        "IsFree": false,
        "ISBN": "9798855434460",
        "PublicationDate": "2026-08-11T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Hajime Kamoshida",
            "Role": "Author"
          },
          {
            "Name": "Keji Mizoguchi",
            "Role": "Author"
          },
          {
            "Name": "Andrew Cunningham",
            "Role": "Translator"
          }
        ],
        "IsInternetArchive": false,
        "IsRecommendation": false,
        "CrossRevisionId": "feb56b0a-f0b5-33a7-be3e-17b286c0f589",
        "Title": "Rascal Does Not Dream of a Beach Queen + (light novel)",
        "Description": "<p>It’s the day of the Minegahara Sports Festival! Sakuta has never been the type to get fired up about sports, so he’s quite content to be blue team’s substitute player. That is, until he g… [TRUNCATED 310 chars; original string value]",
        "Language": "en",
        "Locale": {
          "LanguageCode": "eng",
          "ScriptCode": "",
          "CountryCode": ""
        },
        "ImageId": "a72b3ce8-5026-4f8f-8636-23b5e172fa2a",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "TotalRating": 2,
        "Slug": "rascal-does-not-dream-of-a-beach-queen-light-novel",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 4176268
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 6.99
        },
        "PromoCodeAllowed": false,
        "LovePointsPrice": 3600,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "RelatedGroupId": "2a944b0f-8ba8-e8fc-0000-000000000000",
        "AgeVerificationRequired": false,
        "AccessibilityDetails": {
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10",
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false
        },
        "LifeCycleDates": {
          "ActivationDate": "2026-08-11T00:00:00.0000000Z"
        },
        "Id": "5dabec55-4dcf-43e8-9d61-753a83b0fbb7"
      }
    }
  ],
  "ItemCount": 16,
  "TotalPageCount": 1,
  "TotalItemCount": 16,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "VersionCode": 2
}
```

