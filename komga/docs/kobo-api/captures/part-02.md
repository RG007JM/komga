# Captures 69–174

[← Capture index](README.md) · [API index](../../kobo-api.md)

## 69. GET `/v1/initialization` — device capture response

Source: `device:L32088`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_initialization/response_L32088.json`.

```json
{
  "Resources": {
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "display_accessibility_enabled": "True",
    "display_parental_controls_enabled": "True",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "fixed_layout_page_cache_enabled": "True",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "gpb_flow_enabled": "False",
    "help_page": "https://www.kobo.com/help",
    "image_host": "https://komga.com",
    "image_url_quality_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "image_url_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/false/image.jpg",
    "instapaper_enabled": "True",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_display_price": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_nativeborrow_enabled": "False",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "kobo_redeem_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_subscriptions_enabled": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_wishlist_enabled": "True",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "oauth_host": "https://oauth.kobo.com",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "products": "https://storeapi.kobo.com/v1/products",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "reading_services_host": "https://readingservices.kobo.com",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "redeem_interstitial_page": "https://www.kobo.com",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "reflowable_page_cache_enabled": "True",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "store_host": "www.kobo.com",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "text_to_speech_region_override": "False",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "use_one_store": "True",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks"
  }
}
```


## 70. GET `/v1/user/profile` — device capture response

Source: `device:L32137`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L32137.json`.

```json
{
  "AffiliateName": "Mondadori",
  "AudiobooksEnabled": true,
  "ContactEmail": "[REDACTED]",
  "CountryCode": "IT",
  "Geo": "IT",
  "HasPurchased": true,
  "HasPurchasedAudiobook": false,
  "HasPurchasedBook": true,
  "IsChildAccount": false,
  "IsEligibleForOrangeDeal": false,
  "IsLibraryMigrated": false,
  "IsOneStore": true,
  "IsOrangeAffiliated": false,
  "IsoCultureCode": "en-US",
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "LoyaltyDetails": {
    "LoyaltyCurrentBalance": 0,
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyTags": 2
  },
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PrivacyPermissions": [],
  "SafeSearch": false,
  "StoreFront": "IT",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "UserId": "[REDACTED]",
  "VipMembershipPurchased": false
}
```


## 71. GET `/v1/user/loyalty/benefits` — device capture response

Source: `device:L32185`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_user_loyalty_benefits/response_L32185.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "MembershipLevel": "KoboLoveBasic",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 200,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "DiscountRate": 0,
      "LoveProduct": {
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9",
        "IsPreOrder": false,
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "Title": "Kobo VIP Membership"
      },
      "MembershipLevel": "KoboLoveVIP",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 400
    }
  }
}
```


## 72. GET `/v1/products/books/subscriptions` — device capture response

Source: `device:L32233`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_products_books_subscriptions/response_L32233.json`.

```json
[
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "808f53b4-3910-4bd3-9c81-9ea25d9c8533",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "9bb633fd-6273-401b-bf6e-968b7b552b96",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513",
    "IsPreOrder": false,
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "c3e5161b-c4d7-42ed-af13-470fa76ffd1b",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  }
]
```


## 73. GET `/v1/deals` — device capture response

Source: `device:L32285`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_deals/response_L32285.json`.

```json
{
  "Deals": [
    {
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z",
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans"
    }
  ]
}
```


## 74. GET `/v1/assets` — device capture response

Source: `device:L32336`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_v1_assets/response_L32336.json`.

```json
{
  "AssetGroups": [
    {
      "Assets": [],
      "ETag": "[REDACTED]",
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 75. POST `/v1/analytics/gettests` — device capture request

Source: `device:L32367`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/request_L32367.json`.

```json
{
  "AffiliateName": "Kobo",
  "ApplicationVersion": "4.46.23836",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "SerialNumber": "[REDACTED]",
  "TestKey": "00000000-0000-4000-8000-000000000000"
}
```


## 76. POST `/v1/analytics/gettests` — device capture response

Source: `device:L32388`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/response_L32388.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 77. GET `/v1/library/sync` — device capture response

Source: `device:L32506`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_sync/response_L32506.json`.

```json
[
  {
    "ChangedEntitlement": {
      "BookEntitlement": {
        "Accessibility": "Full",
        "ActivePeriod": {
          "From": "2026-09-22T11:47:37.042132466Z"
        },
        "Created": "2026-08-26T23:18:48Z",
        "CrossRevisionId": "0RE3XW4NEEGG4",
        "Id": "0RE3XW4NEEGG4",
        "IsHiddenFromArchive": false,
        "IsLocked": false,
        "IsRemoved": true,
        "LastModified": "2026-08-26T23:17:36.27Z",
        "OriginCategory": "Purchased",
        "RevisionId": "0RE3XW4NEEGG4",
        "Status": "Active"
      },
      "BookMetadata": {
        "Categories": [
          "00000000-0000-0000-0000-000000000001"
        ],
        "ContributorRoles": [],
        "Contributors": [],
        "CoverImageId": "0RE3XW4NEEGG4",
        "CrossRevisionId": "0RE3XW4NEEGG4",
        "CurrentDisplayPrice": {
          "CurrencyCode": "USD",
          "TotalAmount": 0
        },
        "CurrentLoveDisplayPrice": {
          "TotalAmount": 0
        },
        "DownloadUrls": [],
        "EntitlementId": "0RE3XW4NEEGG4",
        "ExternalIds": [],
        "Genre": "00000000-0000-0000-0000-000000000001",
        "IsEligibleForKoboLove": false,
        "IsInternetArchive": false,
        "IsPreOrder": false,
        "IsSocialEnabled": true,
        "Language": "en",
        "PhoneticPronunciations": {},
        "RevisionId": "0RE3XW4NEEGG4",
        "Title": "0RE3XW4NEEGG4",
        "WorkId": "0RE3XW4NEEGG4"
      },
      "ReadingState": null
    }
  }
]
```


## 78. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L32704`. HTTP: `200`. Captured type: `array (7 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L32704.json`.

```json
[
  {
    "ContentId": "0RDTATJQNHSQ0",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKFHHSYW",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKH5HXZS",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RKJK260M7AGR",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "cb4922b2-7577-4fa6-878a-d19d1891322a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
    "etag": "[REDACTED]"
  }
]
```


## 79. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L32720`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L32720.json`.

```json
[]
```


## 80. GET `/api/v3/content/{uuid}/annotations` — device capture response

Source: `device:L32760`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_uuid_annotations/response_L32760.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 81. GET `/api/v3/content/{uuid}/annotations` — device capture response

Source: `device:L32802`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_uuid_annotations/response_L32802.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 82. GET `/api/v3/content/{uuid}/annotations` — device capture response

Source: `device:L32844`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_uuid_annotations/response_L32844.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 83. GET `/api/v3/content/{uuid}/annotations` — device capture response

Source: `device:L32886`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_uuid_annotations/response_L32886.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 84. GET `/api/UserStorage/Metadata` — device capture response

Source: `device:L32967`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_UserStorage_Metadata/response_L32967.json`.

```json
{
  "continuationToken": "[REDACTED]",
  "metadata": [
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 3",
      "eTag": "[REDACTED]",
      "fileSize": "104910",
      "id": "00eb6c19-5c8f-43e0-951a-a93d1212e154",
      "lastModifiedUtc": "2026-03-03T23:21:18.6887053Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/linear part 3.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "yay",
      "eTag": "[REDACTED]",
      "fileSize": "6803",
      "id": "70c1f771-7d6b-4307-aad8-06fa5db8a6ca",
      "lastModifiedUtc": "2026-03-03T23:21:18.0032945Z",
      "notebookTotalPages": 1,
      "path": "/mnt/onboard/My Notebooks/yay.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "1"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linears tricks",
      "eTag": "[REDACTED]",
      "fileSize": "21013",
      "id": "91ec20ca-74e1-4225-a070-d0327dad0dd9",
      "lastModifiedUtc": "2026-03-03T23:21:17.4094012Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/linears tricks.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "0"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 2",
      "eTag": "[REDACTED]",
      "fileSize": "439972",
      "id": "e01cd5a4-cf50-495c-b639-74c0dc1cfd49",
      "lastModifiedUtc": "2026-03-03T23:21:16.7385072Z",
      "notebookTotalPages": 13,
      "path": "/mnt/onboard/My Notebooks/linear part 2.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "13"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 1",
      "eTag": "[REDACTED]",
      "fileSize": "828647",
      "id": "49cfdb4c-d138-4397-a186-64dbf7f2918e",
      "lastModifiedUtc": "2026-03-03T23:21:14.7715381Z",
      "notebookTotalPages": 21,
      "path": "/mnt/onboard/My Notebooks/linear part 1.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "21"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "gggg",
      "eTag": "[REDACTED]",
      "fileSize": "313401",
      "id": "41dc7d5c-e73a-4607-b136-c81acee82bdc",
      "lastModifiedUtc": "2026-03-03T23:21:11.7715015Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/gggg.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ummmmm",
      "eTag": "[REDACTED]",
      "fileSize": "63603",
      "id": "bace44b4-86ed-425c-b4c8-fb26bdcd851b",
      "lastModifiedUtc": "2026-03-03T23:21:09.5426114Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/ummmmm.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ffff",
      "eTag": "[REDACTED]",
      "fileSize": "55469",
      "id": "e77a3652-989c-4e87-b2bc-074ccbe58c49",
      "lastModifiedUtc": "2025-08-28T19:17:43.9940235Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/ffff.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pihole",
      "eTag": "[REDACTED]",
      "fileSize": "24385",
      "id": "341cf01b-017e-48bf-af1a-d83782149b9f",
      "lastModifiedUtc": "2025-07-17T11:01:19.7041363Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pihole.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "hh",
      "eTag": "[REDACTED]",
      "fileSize": "12026",
      "id": "e09bc5fc-7881-4a70-b8a3-b6c26834c51f",
      "lastModifiedUtc": "2025-07-17T11:01:18.7710131Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/hh.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig",
      "eTag": "[REDACTED]",
      "fileSize": "43396",
      "id": "fd63e3ed-c5fd-4d3a-ba08-fe86e01c2518",
      "lastModifiedUtc": "2025-05-31T10:02:03.5527255Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig new",
      "eTag": "[REDACTED]",
      "fileSize": "44018",
      "id": "f5fd5042-2fbc-4ad9-80b0-ae3fa0d6ac91",
      "lastModifiedUtc": "2025-05-31T10:02:02.5072792Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig new.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pop os requirements",
      "eTag": "[REDACTED]",
      "fileSize": "17753",
      "id": "621eae83-c0f5-4442-a821-bcb7f736cf2a",
      "lastModifiedUtc": "2025-05-31T10:02:01.7350793Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pop os requirements.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "test",
      "eTag": "[REDACTED]",
      "fileSize": "191042",
      "id": "01cc7b36-13b5-4aa4-aae1-ae5fa9a84787",
      "lastModifiedUtc": "2025-05-31T10:02:00.6303265Z",
      "notebookTotalPages": 8,
      "path": "/mnt/onboard/My Notebooks/test.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "8"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "accounts",
      "eTag": "[REDACTED]",
      "fileSize": "393164",
      "id": "af62a542-6319-42f9-9b98-4675ef30d5b3",
      "lastModifiedUtc": "2025-05-31T10:01:59.5473629Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/accounts.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "r",
      "eTag": "[REDACTED]",
      "fileSize": "123260",
      "id": "619e9996-676d-477d-bdfb-15f510b22b4f",
      "lastModifiedUtc": "2025-05-31T10:01:58.3375753Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/r.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear",
      "eTag": "[REDACTED]",
      "fileSize": "640992",
      "id": "629c708e-7500-44ee-a2de-b891b80b96ba",
      "lastModifiedUtc": "2025-05-31T10:01:57.4756975Z",
      "notebookTotalPages": 19,
      "path": "/mnt/onboard/My Notebooks/linear.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "19"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "testsss",
      "eTag": "[REDACTED]",
      "fileSize": "167094",
      "id": "9e9568bb-96d9-4cc8-9bd0-d974d242e5b6",
      "lastModifiedUtc": "2025-05-31T10:01:55.9996291Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/testsss.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "reb",
      "eTag": "[REDACTED]",
      "fileSize": "32109",
      "id": "74292cb5-158e-4290-8d70-f7d0da4285a3",
      "lastModifiedUtc": "2025-05-31T10:01:54.8273139Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/reb.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "InfluencerNight",
      "eTag": "[REDACTED]",
      "fileSize": "144292",
      "id": "d78019a0-c056-445c-afaa-30247f009656",
      "lastModifiedUtc": "2025-04-25T11:13:31.0920381Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/InfluencerNight.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "t",
      "eTag": "[REDACTED]",
      "fileSize": "83320",
      "id": "22c5b060-8463-4c62-9dff-87c478b4a5ab",
      "lastModifiedUtc": "2025-04-12T23:16:39.3675739Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/t.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "Linear Algebra Partial",
      "eTag": "[REDACTED]",
      "fileSize": "355842",
      "id": "24388e6e-aaf8-4b2c-bf18-6108f495ce72",
      "lastModifiedUtc": "2025-04-12T23:16:38.5194657Z",
      "notebookTotalPages": 14,
      "path": "/mnt/onboard/My Notebooks/Linear Algebra Partial.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "14"
      }
    }
  ]
}
```


## 85. GET `/v1/products/books/series/{komgaId}` — device capture response

Source: `device:L33079`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_products_books_series_komgaId/response_L33079.json`.

```json
{
  "FeedbackItems": [
    {
      "FeedbackType": "Rate5Star",
      "RevisionId": "0RDTATKHXHPS9"
    }
  ]
}
```


## 86. POST `/v1/user/recommendations/feedback` — device capture request

Source: `device:L33099`. HTTP: `not proved`. Captured type: `object (1 top-level keys)`. Fixture: `device/POST_v1_user_recommendations_feedback/request_L33099.json`.

```json
{
  "FeedbackItems": [
    {
      "FeedbackType": "Rate5Star",
      "RevisionId": "0RDTATKHXHPS9"
    }
  ]
}
```


## 87. GET `/v1/user/recommendations` — device capture response

Source: `device:L33154`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_user_recommendations/response_L33154.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 0,
  "Items": [],
  "ItemsPerPage": 50,
  "TotalItemCount": 0,
  "TotalPageCount": 0,
  "VersionCode": 2
}
```


## 88. GET `/v1/library/sync` — device capture response

Source: `device:L33738`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/GET_v1_library_sync/response_L33738.json`.

```json
[]
```


## 89. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L33873`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L33873.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Yusei Matsui"
      }
    ],
    "Contributors": [
      "Yusei Matsui"
    ],
    "CoverImageId": "0RPA2CBC4JVY8",
    "CrossRevisionId": "0RDTATJ7XHNW3",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "English teacher IrinaΓÇÖs assassination mentor makes an appearance and the two compete-using special agent Karasuma as their target. Another transfer student/would-be assassin joins the clas… [TRUNCATED 321 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "EPUB3FL",
        "Platform": "Generic",
        "Size": 66830227,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RDTATJ7XHNW3/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RDTATJ7XHNW3",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781421584591",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2015-06-01T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "VIZ Media"
    },
    "RevisionId": "0RDTATJ7XHNW3",
    "Series": {
      "Id": "0RDTATJ4DHY6Z",
      "Name": "Assassination Classroom",
      "Number": "4",
      "NumberFloat": 4
    },
    "Title": "Assassination Classroom, Vol. 4",
    "WorkId": "0RDTATJ7XHNW3"
  }
]
```


## 90. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L34319`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L34319.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 91. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L34730`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L34730.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 92. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L34768`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L34768.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 93. GET `/v1/initialization` — device capture response

Source: `device:L34817`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_initialization/response_L34817.json`.

```json
{
  "Resources": {
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "display_accessibility_enabled": "True",
    "display_parental_controls_enabled": "True",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "fixed_layout_page_cache_enabled": "True",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "gpb_flow_enabled": "False",
    "help_page": "https://www.kobo.com/help",
    "image_host": "https://komga.com",
    "image_url_quality_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "image_url_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/false/image.jpg",
    "instapaper_enabled": "True",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_display_price": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_nativeborrow_enabled": "False",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "kobo_redeem_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_subscriptions_enabled": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_wishlist_enabled": "True",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "oauth_host": "https://oauth.kobo.com",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "products": "https://storeapi.kobo.com/v1/products",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "reading_services_host": "https://readingservices.kobo.com",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "redeem_interstitial_page": "https://www.kobo.com",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "reflowable_page_cache_enabled": "True",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "store_host": "www.kobo.com",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "text_to_speech_region_override": "False",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "use_one_store": "True",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks"
  }
}
```


## 94. GET `/v1/user/profile` — device capture response

Source: `device:L34866`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L34866.json`.

```json
{
  "AffiliateName": "Mondadori",
  "AudiobooksEnabled": true,
  "ContactEmail": "[REDACTED]",
  "CountryCode": "IT",
  "Geo": "IT",
  "HasPurchased": true,
  "HasPurchasedAudiobook": false,
  "HasPurchasedBook": true,
  "IsChildAccount": false,
  "IsEligibleForOrangeDeal": false,
  "IsLibraryMigrated": false,
  "IsOneStore": true,
  "IsOrangeAffiliated": false,
  "IsoCultureCode": "en-US",
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "LoyaltyDetails": {
    "LoyaltyCurrentBalance": 0,
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyTags": 2
  },
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PrivacyPermissions": [],
  "SafeSearch": false,
  "StoreFront": "IT",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "UserId": "[REDACTED]",
  "VipMembershipPurchased": false
}
```


## 95. GET `/v1/user/loyalty/benefits` — device capture response

Source: `device:L34914`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_user_loyalty_benefits/response_L34914.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "MembershipLevel": "KoboLoveBasic",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 200,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "DiscountRate": 0,
      "LoveProduct": {
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9",
        "IsPreOrder": false,
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "Title": "Kobo VIP Membership"
      },
      "MembershipLevel": "KoboLoveVIP",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 400
    }
  }
}
```


## 96. GET `/v1/products/books/subscriptions` — device capture response

Source: `device:L34962`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_products_books_subscriptions/response_L34962.json`.

```json
[
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "d41549e4-50e9-41c1-ac86-7941f96c122b",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "9697bdb6-539d-45ca-b681-fd5161ed58d6",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513",
    "IsPreOrder": false,
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "c5bedc21-8f47-4761-a651-fee1af49dc22",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  }
]
```


## 97. GET `/v1/deals` — device capture response

Source: `device:L35014`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_deals/response_L35014.json`.

```json
{
  "Deals": [
    {
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z",
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans"
    }
  ]
}
```


## 98. GET `/v1/assets` — device capture response

Source: `device:L35065`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_v1_assets/response_L35065.json`.

```json
{
  "AssetGroups": [
    {
      "Assets": [],
      "ETag": "[REDACTED]",
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 99. POST `/v1/analytics/gettests` — device capture request

Source: `device:L35096`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/request_L35096.json`.

```json
{
  "AffiliateName": "Kobo",
  "ApplicationVersion": "4.46.23836",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "SerialNumber": "[REDACTED]",
  "TestKey": "00000000-0000-4000-8000-000000000000"
}
```


## 100. POST `/v1/analytics/gettests` — device capture response

Source: `device:L35117`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/response_L35117.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 101. POST `/v1/library/tags` — device capture request

Source: `device:L35153`. HTTP: `201`. Captured type: `object (2 top-level keys)`. Fixture: `device/POST_v1_library_tags/request_L35153.json`.

```json
{
  "Items": [
    {
      "RevisionId": "0RDTATM01HY4H",
      "Type": "ProductRevisionTagItem"
    }
  ],
  "Name": "testing"
}
```


## 102. GET `/v1/library/sync` — device capture response

Source: `device:L35231`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_sync/response_L35231.json`.

```json
[
  {
    "NewTag": {
      "Tag": {
        "Created": "2026-09-22T11:48:22Z",
        "Id": "0RPN2SADWHMRB",
        "Items": [
          {
            "RevisionId": "0RDTATM01HY4H",
            "Type": "ProductRevisionTagItem"
          }
        ],
        "LastModified": "2026-09-22T11:48:22Z",
        "Name": "testing",
        "Type": "UserTag"
      }
    }
  }
]
```


## 103. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L35426`. HTTP: `200`. Captured type: `array (12 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L35426.json`.

```json
[
  {
    "ContentId": "62f623f6-4b4d-43ec-afc1-85cd5ce99a06",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "2618a30b-165d-4bdf-8c48-f20c3fce40a3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJQNHSQ0",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "bb14a4c2-04be-43c7-935b-55d6ef25055a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJ7XHNW3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKFHHSYW",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKH5HXZS",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RKJK260M7AGR",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "f3212cd2-0ca8-49ec-9dea-4263d50b057e",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "cb4922b2-7577-4fa6-878a-d19d1891322a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
    "etag": "[REDACTED]"
  }
]
```


## 104. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L35442`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L35442.json`.

```json
[]
```


## 105. GET `/api/UserStorage/Metadata` — device capture response

Source: `device:L35501`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_UserStorage_Metadata/response_L35501.json`.

```json
{
  "continuationToken": "[REDACTED]",
  "metadata": [
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 3",
      "eTag": "[REDACTED]",
      "fileSize": "104910",
      "id": "00eb6c19-5c8f-43e0-951a-a93d1212e154",
      "lastModifiedUtc": "2026-03-03T23:21:18.6887053Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/linear part 3.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "yay",
      "eTag": "[REDACTED]",
      "fileSize": "6803",
      "id": "70c1f771-7d6b-4307-aad8-06fa5db8a6ca",
      "lastModifiedUtc": "2026-03-03T23:21:18.0032945Z",
      "notebookTotalPages": 1,
      "path": "/mnt/onboard/My Notebooks/yay.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "1"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linears tricks",
      "eTag": "[REDACTED]",
      "fileSize": "21013",
      "id": "91ec20ca-74e1-4225-a070-d0327dad0dd9",
      "lastModifiedUtc": "2026-03-03T23:21:17.4094012Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/linears tricks.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "0"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 2",
      "eTag": "[REDACTED]",
      "fileSize": "439972",
      "id": "e01cd5a4-cf50-495c-b639-74c0dc1cfd49",
      "lastModifiedUtc": "2026-03-03T23:21:16.7385072Z",
      "notebookTotalPages": 13,
      "path": "/mnt/onboard/My Notebooks/linear part 2.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "13"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 1",
      "eTag": "[REDACTED]",
      "fileSize": "828647",
      "id": "49cfdb4c-d138-4397-a186-64dbf7f2918e",
      "lastModifiedUtc": "2026-03-03T23:21:14.7715381Z",
      "notebookTotalPages": 21,
      "path": "/mnt/onboard/My Notebooks/linear part 1.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "21"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "gggg",
      "eTag": "[REDACTED]",
      "fileSize": "313401",
      "id": "41dc7d5c-e73a-4607-b136-c81acee82bdc",
      "lastModifiedUtc": "2026-03-03T23:21:11.7715015Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/gggg.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ummmmm",
      "eTag": "[REDACTED]",
      "fileSize": "63603",
      "id": "bace44b4-86ed-425c-b4c8-fb26bdcd851b",
      "lastModifiedUtc": "2026-03-03T23:21:09.5426114Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/ummmmm.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ffff",
      "eTag": "[REDACTED]",
      "fileSize": "55469",
      "id": "e77a3652-989c-4e87-b2bc-074ccbe58c49",
      "lastModifiedUtc": "2025-08-28T19:17:43.9940235Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/ffff.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pihole",
      "eTag": "[REDACTED]",
      "fileSize": "24385",
      "id": "341cf01b-017e-48bf-af1a-d83782149b9f",
      "lastModifiedUtc": "2025-07-17T11:01:19.7041363Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pihole.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "hh",
      "eTag": "[REDACTED]",
      "fileSize": "12026",
      "id": "e09bc5fc-7881-4a70-b8a3-b6c26834c51f",
      "lastModifiedUtc": "2025-07-17T11:01:18.7710131Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/hh.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig",
      "eTag": "[REDACTED]",
      "fileSize": "43396",
      "id": "fd63e3ed-c5fd-4d3a-ba08-fe86e01c2518",
      "lastModifiedUtc": "2025-05-31T10:02:03.5527255Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig new",
      "eTag": "[REDACTED]",
      "fileSize": "44018",
      "id": "f5fd5042-2fbc-4ad9-80b0-ae3fa0d6ac91",
      "lastModifiedUtc": "2025-05-31T10:02:02.5072792Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig new.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pop os requirements",
      "eTag": "[REDACTED]",
      "fileSize": "17753",
      "id": "621eae83-c0f5-4442-a821-bcb7f736cf2a",
      "lastModifiedUtc": "2025-05-31T10:02:01.7350793Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pop os requirements.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "test",
      "eTag": "[REDACTED]",
      "fileSize": "191042",
      "id": "01cc7b36-13b5-4aa4-aae1-ae5fa9a84787",
      "lastModifiedUtc": "2025-05-31T10:02:00.6303265Z",
      "notebookTotalPages": 8,
      "path": "/mnt/onboard/My Notebooks/test.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "8"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "accounts",
      "eTag": "[REDACTED]",
      "fileSize": "393164",
      "id": "af62a542-6319-42f9-9b98-4675ef30d5b3",
      "lastModifiedUtc": "2025-05-31T10:01:59.5473629Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/accounts.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "r",
      "eTag": "[REDACTED]",
      "fileSize": "123260",
      "id": "619e9996-676d-477d-bdfb-15f510b22b4f",
      "lastModifiedUtc": "2025-05-31T10:01:58.3375753Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/r.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear",
      "eTag": "[REDACTED]",
      "fileSize": "640992",
      "id": "629c708e-7500-44ee-a2de-b891b80b96ba",
      "lastModifiedUtc": "2025-05-31T10:01:57.4756975Z",
      "notebookTotalPages": 19,
      "path": "/mnt/onboard/My Notebooks/linear.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "19"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "testsss",
      "eTag": "[REDACTED]",
      "fileSize": "167094",
      "id": "9e9568bb-96d9-4cc8-9bd0-d974d242e5b6",
      "lastModifiedUtc": "2025-05-31T10:01:55.9996291Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/testsss.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "reb",
      "eTag": "[REDACTED]",
      "fileSize": "32109",
      "id": "74292cb5-158e-4290-8d70-f7d0da4285a3",
      "lastModifiedUtc": "2025-05-31T10:01:54.8273139Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/reb.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "InfluencerNight",
      "eTag": "[REDACTED]",
      "fileSize": "144292",
      "id": "d78019a0-c056-445c-afaa-30247f009656",
      "lastModifiedUtc": "2025-04-25T11:13:31.0920381Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/InfluencerNight.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "t",
      "eTag": "[REDACTED]",
      "fileSize": "83320",
      "id": "22c5b060-8463-4c62-9dff-87c478b4a5ab",
      "lastModifiedUtc": "2025-04-12T23:16:39.3675739Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/t.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "Linear Algebra Partial",
      "eTag": "[REDACTED]",
      "fileSize": "355842",
      "id": "24388e6e-aaf8-4b2c-bf18-6108f495ce72",
      "lastModifiedUtc": "2025-04-12T23:16:38.5194657Z",
      "notebookTotalPages": 14,
      "path": "/mnt/onboard/My Notebooks/Linear Algebra Partial.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "14"
      }
    }
  ]
}
```


## 106. GET `/v1/user/recommendations` — device capture response

Source: `device:L35709`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_user_recommendations/response_L35709.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 0,
  "Items": [],
  "ItemsPerPage": 50,
  "TotalItemCount": 0,
  "TotalPageCount": 0,
  "VersionCode": 2
}
```


## 107. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L36281`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L36281.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 108. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L36319`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L36319.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 109. GET `/v1/initialization` — device capture response

Source: `device:L36368`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_initialization/response_L36368.json`.

```json
{
  "Resources": {
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "display_accessibility_enabled": "True",
    "display_parental_controls_enabled": "True",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "fixed_layout_page_cache_enabled": "True",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "gpb_flow_enabled": "False",
    "help_page": "https://www.kobo.com/help",
    "image_host": "https://komga.com",
    "image_url_quality_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "image_url_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/false/image.jpg",
    "instapaper_enabled": "True",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_display_price": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_nativeborrow_enabled": "False",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "kobo_redeem_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_subscriptions_enabled": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_wishlist_enabled": "True",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "oauth_host": "https://oauth.kobo.com",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "products": "https://storeapi.kobo.com/v1/products",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "reading_services_host": "https://readingservices.kobo.com",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "redeem_interstitial_page": "https://www.kobo.com",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "reflowable_page_cache_enabled": "True",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "store_host": "www.kobo.com",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "text_to_speech_region_override": "False",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "use_one_store": "True",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks"
  }
}
```


## 110. GET `/v1/user/profile` — device capture response

Source: `device:L36417`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L36417.json`.

```json
{
  "AffiliateName": "Mondadori",
  "AudiobooksEnabled": true,
  "ContactEmail": "[REDACTED]",
  "CountryCode": "IT",
  "Geo": "IT",
  "HasPurchased": true,
  "HasPurchasedAudiobook": false,
  "HasPurchasedBook": true,
  "IsChildAccount": false,
  "IsEligibleForOrangeDeal": false,
  "IsLibraryMigrated": false,
  "IsOneStore": true,
  "IsOrangeAffiliated": false,
  "IsoCultureCode": "en-US",
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "LoyaltyDetails": {
    "LoyaltyCurrentBalance": 0,
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyTags": 2
  },
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PrivacyPermissions": [],
  "SafeSearch": false,
  "StoreFront": "IT",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "UserId": "[REDACTED]",
  "VipMembershipPurchased": false
}
```


## 111. GET `/v1/user/loyalty/benefits` — device capture response

Source: `device:L36465`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_user_loyalty_benefits/response_L36465.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "MembershipLevel": "KoboLoveBasic",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 200,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "DiscountRate": 0,
      "LoveProduct": {
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9",
        "IsPreOrder": false,
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "Title": "Kobo VIP Membership"
      },
      "MembershipLevel": "KoboLoveVIP",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 400
    }
  }
}
```


## 112. GET `/v1/products/books/subscriptions` — device capture response

Source: `device:L36513`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_products_books_subscriptions/response_L36513.json`.

```json
[
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "c662d999-2aba-4b85-b41a-98fcab94804a",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "467a3bd6-bb9b-4333-9a1f-9adb999d6195",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513",
    "IsPreOrder": false,
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "9e16bed9-99ff-45fe-bff3-2a489aa7bfbc",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  }
]
```


## 113. GET `/v1/deals` — device capture response

Source: `device:L36565`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_deals/response_L36565.json`.

```json
{
  "Deals": [
    {
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z",
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans"
    }
  ]
}
```


## 114. GET `/v1/assets` — device capture response

Source: `device:L36616`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_v1_assets/response_L36616.json`.

```json
{
  "AssetGroups": [
    {
      "Assets": [],
      "ETag": "[REDACTED]",
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 115. POST `/v1/analytics/gettests` — device capture request

Source: `device:L36647`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/request_L36647.json`.

```json
{
  "AffiliateName": "Kobo",
  "ApplicationVersion": "4.46.23836",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "SerialNumber": "[REDACTED]",
  "TestKey": "00000000-0000-4000-8000-000000000000"
}
```


## 116. POST `/v1/analytics/gettests` — device capture response

Source: `device:L36668`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/response_L36668.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 117. PUT `/v1/library/tags/{komgaId}` — device capture request

Source: `device:L36704`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_tags_komgaId/request_L36704.json`.

```json
{
  "Name": "testing2"
}
```


## 118. GET `/v1/library/sync` — device capture response

Source: `device:L36777`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_sync/response_L36777.json`.

```json
[
  {
    "ChangedTag": {
      "Tag": {
        "Created": "2026-09-22T11:48:22Z",
        "Id": "0RPN2SADWHMRB",
        "Items": [
          {
            "RevisionId": "0RDTATM01HY4H",
            "Type": "ProductRevisionTagItem"
          }
        ],
        "LastModified": "2026-09-22T11:49:26.264Z",
        "Name": "testing2",
        "Type": "UserTag"
      }
    }
  }
]
```


## 119. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L36973`. HTTP: `200`. Captured type: `array (12 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L36973.json`.

```json
[
  {
    "ContentId": "62f623f6-4b4d-43ec-afc1-85cd5ce99a06",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "2618a30b-165d-4bdf-8c48-f20c3fce40a3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJQNHSQ0",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "bb14a4c2-04be-43c7-935b-55d6ef25055a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJ7XHNW3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKFHHSYW",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKH5HXZS",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RKJK260M7AGR",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "f3212cd2-0ca8-49ec-9dea-4263d50b057e",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "cb4922b2-7577-4fa6-878a-d19d1891322a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
    "etag": "[REDACTED]"
  }
]
```


## 120. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L36989`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L36989.json`.

```json
[]
```


## 121. GET `/api/UserStorage/Metadata` — device capture response

Source: `device:L37048`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_UserStorage_Metadata/response_L37048.json`.

```json
{
  "continuationToken": "[REDACTED]",
  "metadata": [
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 3",
      "eTag": "[REDACTED]",
      "fileSize": "104910",
      "id": "00eb6c19-5c8f-43e0-951a-a93d1212e154",
      "lastModifiedUtc": "2026-03-03T23:21:18.6887053Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/linear part 3.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "yay",
      "eTag": "[REDACTED]",
      "fileSize": "6803",
      "id": "70c1f771-7d6b-4307-aad8-06fa5db8a6ca",
      "lastModifiedUtc": "2026-03-03T23:21:18.0032945Z",
      "notebookTotalPages": 1,
      "path": "/mnt/onboard/My Notebooks/yay.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "1"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linears tricks",
      "eTag": "[REDACTED]",
      "fileSize": "21013",
      "id": "91ec20ca-74e1-4225-a070-d0327dad0dd9",
      "lastModifiedUtc": "2026-03-03T23:21:17.4094012Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/linears tricks.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "0"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 2",
      "eTag": "[REDACTED]",
      "fileSize": "439972",
      "id": "e01cd5a4-cf50-495c-b639-74c0dc1cfd49",
      "lastModifiedUtc": "2026-03-03T23:21:16.7385072Z",
      "notebookTotalPages": 13,
      "path": "/mnt/onboard/My Notebooks/linear part 2.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "13"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 1",
      "eTag": "[REDACTED]",
      "fileSize": "828647",
      "id": "49cfdb4c-d138-4397-a186-64dbf7f2918e",
      "lastModifiedUtc": "2026-03-03T23:21:14.7715381Z",
      "notebookTotalPages": 21,
      "path": "/mnt/onboard/My Notebooks/linear part 1.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "21"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "gggg",
      "eTag": "[REDACTED]",
      "fileSize": "313401",
      "id": "41dc7d5c-e73a-4607-b136-c81acee82bdc",
      "lastModifiedUtc": "2026-03-03T23:21:11.7715015Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/gggg.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ummmmm",
      "eTag": "[REDACTED]",
      "fileSize": "63603",
      "id": "bace44b4-86ed-425c-b4c8-fb26bdcd851b",
      "lastModifiedUtc": "2026-03-03T23:21:09.5426114Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/ummmmm.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ffff",
      "eTag": "[REDACTED]",
      "fileSize": "55469",
      "id": "e77a3652-989c-4e87-b2bc-074ccbe58c49",
      "lastModifiedUtc": "2025-08-28T19:17:43.9940235Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/ffff.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pihole",
      "eTag": "[REDACTED]",
      "fileSize": "24385",
      "id": "341cf01b-017e-48bf-af1a-d83782149b9f",
      "lastModifiedUtc": "2025-07-17T11:01:19.7041363Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pihole.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "hh",
      "eTag": "[REDACTED]",
      "fileSize": "12026",
      "id": "e09bc5fc-7881-4a70-b8a3-b6c26834c51f",
      "lastModifiedUtc": "2025-07-17T11:01:18.7710131Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/hh.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig",
      "eTag": "[REDACTED]",
      "fileSize": "43396",
      "id": "fd63e3ed-c5fd-4d3a-ba08-fe86e01c2518",
      "lastModifiedUtc": "2025-05-31T10:02:03.5527255Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig new",
      "eTag": "[REDACTED]",
      "fileSize": "44018",
      "id": "f5fd5042-2fbc-4ad9-80b0-ae3fa0d6ac91",
      "lastModifiedUtc": "2025-05-31T10:02:02.5072792Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig new.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pop os requirements",
      "eTag": "[REDACTED]",
      "fileSize": "17753",
      "id": "621eae83-c0f5-4442-a821-bcb7f736cf2a",
      "lastModifiedUtc": "2025-05-31T10:02:01.7350793Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pop os requirements.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "test",
      "eTag": "[REDACTED]",
      "fileSize": "191042",
      "id": "01cc7b36-13b5-4aa4-aae1-ae5fa9a84787",
      "lastModifiedUtc": "2025-05-31T10:02:00.6303265Z",
      "notebookTotalPages": 8,
      "path": "/mnt/onboard/My Notebooks/test.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "8"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "accounts",
      "eTag": "[REDACTED]",
      "fileSize": "393164",
      "id": "af62a542-6319-42f9-9b98-4675ef30d5b3",
      "lastModifiedUtc": "2025-05-31T10:01:59.5473629Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/accounts.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "r",
      "eTag": "[REDACTED]",
      "fileSize": "123260",
      "id": "619e9996-676d-477d-bdfb-15f510b22b4f",
      "lastModifiedUtc": "2025-05-31T10:01:58.3375753Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/r.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear",
      "eTag": "[REDACTED]",
      "fileSize": "640992",
      "id": "629c708e-7500-44ee-a2de-b891b80b96ba",
      "lastModifiedUtc": "2025-05-31T10:01:57.4756975Z",
      "notebookTotalPages": 19,
      "path": "/mnt/onboard/My Notebooks/linear.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "19"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "testsss",
      "eTag": "[REDACTED]",
      "fileSize": "167094",
      "id": "9e9568bb-96d9-4cc8-9bd0-d974d242e5b6",
      "lastModifiedUtc": "2025-05-31T10:01:55.9996291Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/testsss.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "reb",
      "eTag": "[REDACTED]",
      "fileSize": "32109",
      "id": "74292cb5-158e-4290-8d70-f7d0da4285a3",
      "lastModifiedUtc": "2025-05-31T10:01:54.8273139Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/reb.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "InfluencerNight",
      "eTag": "[REDACTED]",
      "fileSize": "144292",
      "id": "d78019a0-c056-445c-afaa-30247f009656",
      "lastModifiedUtc": "2025-04-25T11:13:31.0920381Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/InfluencerNight.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "t",
      "eTag": "[REDACTED]",
      "fileSize": "83320",
      "id": "22c5b060-8463-4c62-9dff-87c478b4a5ab",
      "lastModifiedUtc": "2025-04-12T23:16:39.3675739Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/t.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "Linear Algebra Partial",
      "eTag": "[REDACTED]",
      "fileSize": "355842",
      "id": "24388e6e-aaf8-4b2c-bf18-6108f495ce72",
      "lastModifiedUtc": "2025-04-12T23:16:38.5194657Z",
      "notebookTotalPages": 14,
      "path": "/mnt/onboard/My Notebooks/Linear Algebra Partial.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "14"
      }
    }
  ]
}
```


## 122. GET `/v1/user/recommendations` — device capture response

Source: `device:L37256`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_user_recommendations/response_L37256.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 0,
  "Items": [],
  "ItemsPerPage": 50,
  "TotalItemCount": 0,
  "TotalPageCount": 0,
  "VersionCode": 2
}
```


## 123. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L37819`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L37819.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 124. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L37857`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L37857.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 125. GET `/v1/initialization` — device capture response

Source: `device:L37906`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_initialization/response_L37906.json`.

```json
{
  "Resources": {
    "account_page": "https://www.kobo.com/account/settings",
    "account_page_rakuten": "https://my.rakuten.co.jp/",
    "add_device": "https://storeapi.kobo.com/v1/user/add-device",
    "add_entitlement": "https://storeapi.kobo.com/v1/library/{RevisionIds}",
    "affiliaterequest": "https://storeapi.kobo.com/v1/affiliate",
    "assets": "https://storeapi.kobo.com/v1/assets",
    "audiobook": "https://storeapi.kobo.com/v1/products/audiobooks/{ProductId}",
    "audiobook_detail_page": "https://www.kobo.com/{region}/{language}/audiobook/{slug}",
    "audiobook_landing_page": "https://www.kobo.com/{region}/{language}/audiobooks",
    "audiobook_preview": "https://storeapi.kobo.com/v1/products/audiobooks/{Id}/preview",
    "audiobook_purchase_withcredit": "https://storeapi.kobo.com/v1/store/audiobook/{Id}",
    "audiobook_subscription_orange_deal_inclusion_url": "https://authorize.kobo.com/inclusion",
    "authorproduct_recommendations": "https://storeapi.kobo.com/v1/products/books/authors/recommendations",
    "autocomplete": "https://storeapi.kobo.com/v1/products/autocomplete",
    "bam": "https://storeapi.kobo.com/v2/activity/bam/success",
    "blackstone_header": {
      "key": "x-amz-request-payer",
      "value": "requester"
    },
    "book": "https://storeapi.kobo.com/v1/products/books/{ProductId}",
    "book_detail_page": "https://www.kobo.com/{region}/{language}/ebook/{slug}",
    "book_detail_page_rakuten": "http://books.rakuten.co.jp/rk/{crossrevisionid}",
    "book_landing_page": "https://www.kobo.com/ebooks",
    "book_subscription": "https://storeapi.kobo.com/v1/products/books/subscriptions",
    "browse_history": "https://storeapi.kobo.com/v1/user/browsehistory",
    "categories": "https://storeapi.kobo.com/v1/categories",
    "categories_page": "https://www.kobo.com/ebooks/categories",
    "categoriesv2": "https://storeapi.kobo.com/api/v2/Categories/Top",
    "category": "https://storeapi.kobo.com/v1/categories/{CategoryId}",
    "category_featured_lists": "https://storeapi.kobo.com/v1/categories/{CategoryId}/featured",
    "category_products": "https://storeapi.kobo.com/v1/categories/{CategoryId}/products",
    "checkout_borrowed_book": "https://storeapi.kobo.com/v1/library/borrow",
    "client_authd_referral": "https://authorize.kobo.com/api/AuthenticatedReferral/client/v1/getLink",
    "configuration_data": "https://storeapi.kobo.com/v1/configuration",
    "content_access_book": "https://storeapi.kobo.com/v1/products/books/{ProductId}/access",
    "contributorsv2": "https://storeapi.kobo.com/v2/contributors/author",
    "createpurchaseifallowed_url": "https://www.kobo.com/checkout/createpurchaseifallowed",
    "customer_care_live_chat": "https://v2.zopim.com/widget/livechat.html?key=[REDACTED]",
    "daily_deal": "https://storeapi.kobo.com/v1/products/dailydeal",
    "deals": "https://storeapi.kobo.com/v1/deals",
    "delete_entitlement": "https://storeapi.kobo.com/v1/library/{Ids}",
    "delete_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "delete_tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/items/delete",
    "delete_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "device_auth": "https://storeapi.kobo.com/v1/auth/device",
    "device_refresh": "https://storeapi.kobo.com/v1/auth/refresh",
    "dictionary_host": "https://ereaderfiles.kobo.com",
    "discovery_host": "https://discovery.kobobooks.com",
    "display_accessibility_enabled": "True",
    "display_parental_controls_enabled": "True",
    "dropbox_link_account_poll": "https://authorize.kobo.com/{region}/{language}/LinkDropbox",
    "dropbox_link_account_start": "https://authorize.kobo.com/LinkDropbox/start",
    "elabel_url": "https://ereaderfiles.kobo.com/elabels/",
    "ereaderdevices": "https://storeapi.kobo.com/v2/products/EReaderDeviceFeeds",
    "eula_page": "https://www.kobo.com/termsofuse?style=onestore",
    "exchange_auth": "https://storeapi.kobo.com/v1/auth/exchange",
    "external_book": "https://storeapi.kobo.com/v1/products/books/external/{Ids}",
    "facebook_sso_page": "https://authorize.kobo.com/signin/provider/Facebook/login?returnUrl=https://kobo.com/",
    "featured_list": "https://storeapi.kobo.com/v1/products/featured/{FeaturedListId}",
    "featured_lists": "https://storeapi.kobo.com/v1/products/featured",
    "featuredlist2": "https://storeapi.kobo.com/v2/products/list/featured",
    "fixed_layout_page_cache_enabled": "True",
    "free_books_page": {
      "EN": "https://www.kobo.com/{region}/{language}/p/free-ebooks",
      "FR": "https://www.kobo.com/{region}/{language}/p/livres-gratuits",
      "IT": "https://www.kobo.com/{region}/{language}/p/libri-gratuiti",
      "NL": "https://www.kobo.com/{region}/{language}/List/bekijk-het-overzicht-van-gratis-ebooks/QpkkVWnUw8sxmgjSlCbJRg",
      "PT": "https://www.kobo.com/{region}/{language}/p/livros-gratis"
    },
    "funnel_metrics": "https://storeapi.kobo.com/v1/funnelmetrics",
    "geography_data": "https://storeapi.kobo.com/v2/configuration/geography/country",
    "get_download_keys": "https://storeapi.kobo.com/v1/library/downloadkeys",
    "get_download_link": "https://storeapi.kobo.com/v1/library/downloadlink",
    "get_tests_request": "https://storeapi.kobo.com/v1/analytics/gettests",
    "giftcard_epd_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem-ereader",
    "giftcard_redeem_url": "https://www.kobo.com/{storefront}/{language}/redeem",
    "googledrive_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkcloudstorage/provider/google_drive",
    "gpb_flow_enabled": "False",
    "help_page": "https://www.kobo.com/help",
    "image_host": "https://komga.com",
    "image_url_quality_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/{Quality}/{IsGreyscale}/image.jpg",
    "image_url_template": "https://komga.com/kobo/{token}/v1/books/{ImageId}/thumbnail/{Width}/{Height}/false/image.jpg",
    "instapaper_enabled": "True",
    "instapaper_env_url": "https://www.instapaper.com/api/kobo",
    "instapaper_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkinstapaper",
    "kobo_audiobooks_credit_redemption": "False",
    "kobo_audiobooks_enabled": "True",
    "kobo_audiobooks_orange_deal_enabled": "False",
    "kobo_audiobooks_subscriptions_enabled": "False",
    "kobo_display_price": "True",
    "kobo_dropbox_link_account_enabled": "True",
    "kobo_google_tax": "False",
    "kobo_googledrive_link_account_enabled": "True",
    "kobo_nativeborrow_enabled": "False",
    "kobo_onedrive_link_account_enabled": "False",
    "kobo_onestorelibrary_enabled": "False",
    "kobo_privacyCentre_url": "https://www.kobo.com/privacy",
    "kobo_redeem_enabled": "True",
    "kobo_shelfie_enabled": "False",
    "kobo_shopping_cart_enabled": "False",
    "kobo_subscriptions_enabled": "True",
    "kobo_superpoints_enabled": "True",
    "kobo_wishlist_enabled": "True",
    "library_book": "https://storeapi.kobo.com/v1/user/library/books/{LibraryItemId}",
    "library_items": "https://storeapi.kobo.com/v1/user/library",
    "library_metadata": "https://storeapi.kobo.com/v1/library/{Ids}/metadata",
    "library_prices": "https://storeapi.kobo.com/v1/user/library/previews/prices",
    "library_search": "https://storeapi.kobo.com/v1/library/search",
    "library_sync": "https://storeapi.kobo.com/v1/library/sync",
    "love_dashboard_page": "https://www.kobo.com/{region}/{language}/kobosuperpoints",
    "love_points_redemption_page": "https://www.kobo.com/{region}/{language}/KoboSuperPointsRedemption?productId={ProductId}",
    "magazine_landing_page": "https://www.kobo.com/emagazines",
    "more_sign_in_options": "https://authorize.kobo.com/signin?returnUrl=https://kobo.com/#allProviders",
    "morebyauthor": "https://storeapi.kobo.com/v2/products/recommendations/morebyauthor",
    "notebooks": "https://storeapi.kobo.com/api/internal/notebooks",
    "notifications_registration_issue": "https://storeapi.kobo.com/v1/notifications/registration",
    "oauth_host": "https://oauth.kobo.com",
    "password_retrieval_page": "https://www.kobo.com/passwordretrieval.html",
    "patch_user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts/{Id}",
    "personalizedrecommendations": "https://storeapi.kobo.com/v2/users/personalizedrecommendations",
    "pocket_link_account_start": "https://authorize.kobo.com/{region}/{language}/linkpocket",
    "post_analytics_event": "https://storeapi.kobo.com/v1/analytics/event",
    "ppx_purchasing_url": "https://purchasing.kobo.com",
    "privacy_page": "https://www.kobo.com/privacypolicy?style=onestore",
    "product_nextread": "https://storeapi.kobo.com/v1/products/{ProductIds}/nextread",
    "product_prices": "https://storeapi.kobo.com/v1/products/{ProductIds}/prices",
    "product_recommendations": "https://storeapi.kobo.com/v1/products/{ProductId}/recommendations",
    "product_reviews": "https://storeapi.kobo.com/v1/products/{ProductIds}/reviews",
    "productbyid": "https://storeapi.kobo.com/v2/products/itemDetailById/{ProductType}/{Id}",
    "productbyslug": "https://storeapi.kobo.com/v2/products/itemDetail/{ProductType}/{Slug}",
    "products": "https://storeapi.kobo.com/v1/products",
    "productstatebyid": "https://storeapi.kobo.com/v2/products/itemStateById/{ProductType}/{Id}",
    "productstatebyslug": "https://storeapi.kobo.com/v2/products/itemState/{ProductType}/{Slug}",
    "productsv2": "https://storeapi.kobo.com/v2/products",
    "provider_external_sign_in_page": "https://authorize.kobo.com/ExternalSignIn/{providerName}?returnUrl=https://kobo.com/",
    "purchase_buy": "https://www.kobo.com/checkoutoption/",
    "purchase_buy_templated": "https://www.kobo.com/{region}/{language}/checkoutoption/{ProductId}",
    "quickbuy_checkout": "https://storeapi.kobo.com/v1/store/quickbuy/{PurchaseId}/checkout",
    "quickbuy_create": "https://storeapi.kobo.com/v1/store/quickbuy/purchase",
    "rakuten_token_exchange": "https://storeapi.kobo.com/v1/auth/rakuten_token_exchange",
    "rating": "https://storeapi.kobo.com/v1/products/{ProductId}/rating/{Rating}",
    "reading_services_host": "https://readingservices.kobo.com",
    "reading_state": "https://storeapi.kobo.com/v1/library/{Ids}/state",
    "recommendations": "https://storeapi.kobo.com/v1/products/bulk",
    "redeem_interstitial_page": "https://www.kobo.com",
    "redeem_loyalty_points": "https://storeapi.kobo.com/v1/user/loyalty/redeem",
    "reflowable_page_cache_enabled": "True",
    "registration_page": "https://authorize.kobo.com/signup?returnUrl=https://kobo.com/",
    "related": "https://storeapi.kobo.com/v2/products/recommendations/related",
    "related_items": "https://storeapi.kobo.com/v1/products/{Id}/related",
    "remaining_book_series": "https://storeapi.kobo.com/v1/products/books/series/{SeriesId}",
    "rename_tag": "https://storeapi.kobo.com/v1/library/tags/{TagId}",
    "review": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}",
    "review_sentiment": "https://storeapi.kobo.com/v1/products/reviews/{ReviewId}/sentiment/{Sentiment}",
    "sepa_banks": "https://storeapi.kobo.com/v2/purchasing/sepa/banks",
    "shelfie_recommendations": "https://storeapi.kobo.com/v1/user/recommendations/shelfie",
    "sign_in_page": "https://auth.kobobooks.com/ActivateOnWeb",
    "social_authorization_host": "https://social.kobobooks.com:8443",
    "social_host": "https://social.kobobooks.com",
    "store_home": "www.kobo.com/{region}/{language}",
    "store_host": "www.kobo.com",
    "store_newreleases": "https://www.kobo.com/{region}/{language}/List/new-releases/961XUjtsU0qxkFItWOutGA",
    "store_search": "https://www.kobo.com/{region}/{language}/Search?Query={query}",
    "store_top50": "https://www.kobo.com/{region}/{language}/ebooks/Top",
    "subs_landing_page": "https://www.kobo.com/{region}/{language}/plus",
    "subs_management_page": "https://www.kobo.com/{region}/{language}/account/subscriptions",
    "subs_plans_page": "https://www.kobo.com/{region}/{language}/plus/plans",
    "subs_purchase_buy_templated": "https://www.kobo.com/{region}/{language}/Checkoutoption/{ProductId}/{TierId}",
    "tag_items": "https://storeapi.kobo.com/v1/library/tags/{TagId}/Items",
    "tags": "https://storeapi.kobo.com/v1/library/tags",
    "terms_of_sale_page": "https://authorize.kobo.com/{region}/{language}/terms/termsofsale",
    "text_to_speech_region_override": "False",
    "topproducts": "https://storeapi.kobo.com/v2/products/list/topproducts",
    "tracking": "https://storeapi.kobo.com/v2/tracking/searchperformed",
    "update_accessibility_to_preview": "https://storeapi.kobo.com/v1/library/{EntitlementIds}/preview",
    "use_one_store": "True",
    "user_currencyconversion": "https://storeapi.kobo.com/v1/user/currency/convert",
    "user_linked_accounts": "https://storeapi.kobo.com/v1/user/linkedaccounts",
    "user_loyalty_benefits": "https://storeapi.kobo.com/v1/user/loyalty/benefits",
    "user_loyalty_membership": "https://storeapi.kobo.com/v1/user/loyalty/membership",
    "user_platform": "https://storeapi.kobo.com/v1/user/platform",
    "user_profile": "https://storeapi.kobo.com/v1/user/profile",
    "user_ratings": "https://storeapi.kobo.com/v1/user/ratings",
    "user_recommendations": "https://storeapi.kobo.com/v1/user/recommendations",
    "user_reviews": "https://storeapi.kobo.com/v1/user/reviews",
    "user_subscription_koboplus": "https://storeapi.kobo.com/v1/user/subscription/kp/state",
    "user_tasteprofile_complete": "https://storeapi.kobo.com/v2/user/tasteprofile/complete",
    "user_tasteprofile_genre": "https://storeapi.kobo.com/v2/user/tasteprofile/genre",
    "user_wishlist": "https://storeapi.kobo.com/v1/user/wishlist",
    "userguide_host": "https://ereaderfiles.kobo.com",
    "wishlist_page": "https://www.kobo.com/{region}/{language}/account/wishlist",
    "workbooks": "https://storeapi.kobo.com/v2/products/workbooks"
  }
}
```


## 126. GET `/v1/user/profile` — device capture response

Source: `device:L37955`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L37955.json`.

```json
{
  "AffiliateName": "Mondadori",
  "AudiobooksEnabled": true,
  "ContactEmail": "[REDACTED]",
  "CountryCode": "IT",
  "Geo": "IT",
  "HasPurchased": true,
  "HasPurchasedAudiobook": false,
  "HasPurchasedBook": true,
  "IsChildAccount": false,
  "IsEligibleForOrangeDeal": false,
  "IsLibraryMigrated": false,
  "IsOneStore": true,
  "IsOrangeAffiliated": false,
  "IsoCultureCode": "en-US",
  "KoboCrmOptInSetting": "ExplicitlyConsentedNo",
  "LinkedAccounts": [],
  "LoyaltyDetails": {
    "LoyaltyCurrentBalance": 0,
    "LoyaltyIsActive": true,
    "LoyaltyStartDate": "2026-09-22T11:46:07.9970930+00:00",
    "LoyaltyTagString": "KoboLoveBasic",
    "LoyaltyTags": 2
  },
  "PartnerId": "00000000-0000-0000-0000-000000000001",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "PrivacyPermissions": [],
  "SafeSearch": false,
  "StoreFront": "IT",
  "UserCreated": "2025-03-20T18:46:32.0000000Z",
  "UserId": "[REDACTED]",
  "VipMembershipPurchased": false
}
```


## 127. GET `/v1/user/loyalty/benefits` — device capture response

Source: `device:L38003`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_user_loyalty_benefits/response_L38003.json`.

```json
{
  "Benefits": {
    "KoboLoveBasic": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "MembershipLevel": "KoboLoveBasic",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 200,
      "UpgradeLevel": "KoboLoveVIP"
    },
    "KoboLoveVIP": {
      "CurrencySpendRate": 10,
      "CurrencyType": "EUR",
      "DiscountRate": 0,
      "LoveProduct": {
        "Id": "9df286d2-9318-44b3-82f4-2099bb96c1a9",
        "IsPreOrder": false,
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 12
        },
        "Title": "Kobo VIP Membership"
      },
      "MembershipLevel": "KoboLoveVIP",
      "MinimumPointRedemption": 2400,
      "PointEarnRate": 400
    }
  }
}
```


## 128. GET `/v1/products/books/subscriptions` — device capture response

Source: `device:L38051`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_products_books_subscriptions/response_L38051.json`.

```json
[
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Id": "a532a22f-fac5-45a8-a7eb-2dcc596eaadc",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "dac8bce4-2fe3-40ae-9b07-d72c7f99212a",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "416bfce4-e4ae-4730-8314-5857dece58a6",
    "Id": "27ef4486-eed0-4bbc-ac55-ded89007f68e",
    "IsPreOrder": false,
    "Name": "Kobo Plus Read & Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "1fa62dd8-c2a9-41ce-8887-7444c07b7b3e",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 12.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  },
  {
    "ActivationDate": "2023-05-26T21:59:59.0000000Z",
    "CrossRevisionId": "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
    "Id": "1ecb8932-4938-4d65-a623-e94c28609513",
    "IsPreOrder": false,
    "Name": "Kobo Plus Listen",
    "Tiers": [
      {
        "Description": "",
        "Headline": "",
        "Id": "b8ef55a1-b896-46f3-ba3d-44ef3f774093",
        "Phases": [
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 0
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 0,
            "PhaseType": "Trial",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 0,
                "TaxIn": true,
                "ValidFrom": "2022-07-23T22:00:00.0000000Z"
              }
            ]
          },
          {
            "BillingSpecification": {
              "InitialBillingDateOffset": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              },
              "RebillInterval": {
                "IntervalUnitType": "Days",
                "IntervalUnits": 30
              }
            },
            "Duration": {
              "Number": 30,
              "Unit": "Days"
            },
            "Order": 2,
            "PhaseType": "Active",
            "Prices": [
              {
                "Currency": "EUR",
                "Geo": "IT",
                "Price": 9.99,
                "TaxIn": true,
                "ValidFrom": "2023-05-26T21:59:59.0000000Z"
              }
            ]
          }
        ]
      }
    ]
  }
]
```


## 129. GET `/v1/deals` — device capture response

Source: `device:L38103`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_deals/response_L38103.json`.

```json
{
  "Deals": [
    {
      "AssetGroup": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "From": "2021-10-01T00:00:00.0000000Z",
      "Id": "77c91545-d80f-46be-86d9-357079015fb2",
      "Name": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Url": "https://www.kobo.com/{region}/{language}/plus/plans"
    }
  ]
}
```


## 130. GET `/v1/assets` — device capture response

Source: `device:L38154`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_v1_assets/response_L38154.json`.

```json
{
  "AssetGroups": [
    {
      "Assets": [],
      "ETag": "[REDACTED]",
      "Key": "EPD-KoboPlus-ReadOnly-NeverSubscribed",
      "Template": "Subscriptions-Single"
    }
  ],
  "KeepGoing": false
}
```


## 131. POST `/v1/analytics/gettests` — device capture request

Source: `device:L38185`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/request_L38185.json`.

```json
{
  "AffiliateName": "Kobo",
  "ApplicationVersion": "4.46.23836",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "SerialNumber": "[REDACTED]",
  "TestKey": "00000000-0000-4000-8000-000000000000"
}
```


## 132. POST `/v1/analytics/gettests` — device capture response

Source: `device:L38206`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/response_L38206.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 133. GET `/v1/library/sync` — device capture response

Source: `device:L38270`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_library_sync/response_L38270.json`.

```json
[
  {
    "NewEntitlement": {
      "BookEntitlement": {
        "Accessibility": "Full",
        "ActivePeriod": {
          "From": "2026-09-22T11:51:07.402427238Z"
        },
        "Created": "2026-08-26T23:18:48Z",
        "CrossRevisionId": "0RE3XW4NEEGG4",
        "Id": "0RE3XW4NEEGG4",
        "IsHiddenFromArchive": false,
        "IsLocked": false,
        "IsRemoved": false,
        "LastModified": "2026-08-27T01:17:36.27Z",
        "OriginCategory": "Purchased",
        "RevisionId": "0RE3XW4NEEGG4",
        "Status": "Active"
      },
      "BookMetadata": {
        "Categories": [
          "00000000-0000-0000-0000-000000000001"
        ],
        "ContributorRoles": [
          {
            "Name": "Jeremy Clarkson"
          }
        ],
        "Contributors": [
          "Jeremy Clarkson"
        ],
        "CoverImageId": "0RPN3C0TRHV3K",
        "CrossRevisionId": "0RE3XW4NEEGG4",
        "CurrentDisplayPrice": {
          "CurrencyCode": "USD",
          "TotalAmount": 0
        },
        "CurrentLoveDisplayPrice": {
          "TotalAmount": 0
        },
        "Description": "In And Another Thing... the outspoken and outrageous presenter Jeremy Clarkson, shares his opinions on just about everything. Jeremy Clarkson finds the world such a perplexing place that he … [TRUNCATED 1682 chars; original string value]",
        "DownloadUrls": [
          {
            "DrmType": "None",
            "Format": "KEPUB",
            "Platform": "Generic",
            "Size": 408047,
            "Url": "https://komga.com/kobo/{token}/v1/books/0RE3XW4NEEGG4/file/epub?convert_kepub=true"
          }
        ],
        "EntitlementId": "0RE3XW4NEEGG4",
        "ExternalIds": [],
        "Genre": "00000000-0000-0000-0000-000000000001",
        "IsEligibleForKoboLove": false,
        "IsInternetArchive": false,
        "IsPreOrder": false,
        "IsSocialEnabled": true,
        "Isbn": "9780141901404",
        "Language": "en",
        "PhoneticPronunciations": {},
        "PublicationDate": "2007-10-03T00:00:00Z",
        "Publisher": {
          "Imprint": "",
          "Name": "Penguin Books Ltd"
        },
        "RevisionId": "0RE3XW4NEEGG4",
        "Series": {
          "Id": "0RE3XW0WTEPX6",
          "Name": "The World According to Clarkson",
          "Number": "1",
          "NumberFloat": 1
        },
        "Title": "And Another Thing",
        "WorkId": "0RE3XW4NEEGG4"
      },
      "ReadingState": {
        "Created": "2026-08-26T23:43:06Z",
        "CurrentBookmark": {
          "ContentSourceProgressPercent": 1,
          "LastModified": "2026-09-13T00:51:10.004Z",
          "Location": {
            "Source": "And_Another_Thing_The_World_Acc_split_001.html",
            "Type": "KoboSpan",
            "Value": "kobo.0.30"
          },
          "ProgressPercent": 0.7662835
        },
        "EntitlementId": "0RE3XW4NEEGG4",
        "LastModified": "2026-09-13T00:51:10.004Z",
        "PriorityTimestamp": "2026-09-13T00:51:10.004Z",
        "Statistics": {
          "LastModified": "2026-09-13T00:51:10.004Z"
        },
        "StatusInfo": {
          "LastModified": "2026-09-13T00:51:10.004Z",
          "Status": "Reading",
          "TimesStartedReading": 1
        }
      }
    }
  },
  {
    "ChangedProductMetadata": {
      "Categories": [
        "00000000-0000-0000-0000-000000000001"
      ],
      "ContributorRoles": [
        {
          "Name": "Jeremy Clarkson"
        }
      ],
      "Contributors": [
        "Jeremy Clarkson"
      ],
      "CoverImageId": "0RPN3C0TRHV3K",
      "CrossRevisionId": "0RE3XW4NEEGG4",
      "CurrentDisplayPrice": {
        "CurrencyCode": "USD",
        "TotalAmount": 0
      },
      "CurrentLoveDisplayPrice": {
        "TotalAmount": 0
      },
      "Description": "In And Another Thing... the outspoken and outrageous presenter Jeremy Clarkson, shares his opinions on just about everything. Jeremy Clarkson finds the world such a perplexing place that he … [TRUNCATED 1682 chars; original string value]",
      "DownloadUrls": [
        {
          "DrmType": "None",
          "Format": "KEPUB",
          "Platform": "Generic",
          "Size": 408047,
          "Url": "https://komga.com/kobo/{token}/v1/books/0RE3XW4NEEGG4/file/epub?convert_kepub=true"
        }
      ],
      "EntitlementId": "0RE3XW4NEEGG4",
      "ExternalIds": [],
      "Genre": "00000000-0000-0000-0000-000000000001",
      "IsEligibleForKoboLove": false,
      "IsInternetArchive": false,
      "IsPreOrder": false,
      "IsSocialEnabled": true,
      "Isbn": "9780141901404",
      "Language": "en",
      "PhoneticPronunciations": {},
      "PublicationDate": "2007-10-03T00:00:00Z",
      "Publisher": {
        "Imprint": "",
        "Name": "Penguin Books Ltd"
      },
      "RevisionId": "0RE3XW4NEEGG4",
      "Series": {
        "Id": "0RE3XW0WTEPX6",
        "Name": "The World According to Clarkson",
        "Number": "1",
        "NumberFloat": 1
      },
      "Title": "And Another Thing",
      "WorkId": "0RE3XW4NEEGG4"
    }
  },
  {
    "ChangedReadingState": {
      "ReadingState": {
        "Created": "2026-08-26T23:43:06Z",
        "CurrentBookmark": {
          "ContentSourceProgressPercent": 1,
          "LastModified": "2026-09-13T00:51:10.004Z",
          "Location": {
            "Source": "And_Another_Thing_The_World_Acc_split_001.html",
            "Type": "KoboSpan",
            "Value": "kobo.0.30"
          },
          "ProgressPercent": 0.7662835
        },
        "EntitlementId": "0RE3XW4NEEGG4",
        "LastModified": "2026-09-13T00:51:10.004Z",
        "PriorityTimestamp": "2026-09-13T00:51:10.004Z",
        "Statistics": {
          "LastModified": "2026-09-13T00:51:10.004Z"
        },
        "StatusInfo": {
          "LastModified": "2026-09-13T00:51:10.004Z",
          "Status": "Reading",
          "TimesStartedReading": 1
        }
      }
    }
  }
]
```


## 134. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L38542`. HTTP: `200`. Captured type: `array (12 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L38542.json`.

```json
[
  {
    "ContentId": "62f623f6-4b4d-43ec-afc1-85cd5ce99a06",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "2618a30b-165d-4bdf-8c48-f20c3fce40a3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJQNHSQ0",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "bb14a4c2-04be-43c7-935b-55d6ef25055a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJ7XHNW3",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKFHHSYW",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKH5HXZS",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RKJK260M7AGR",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "f3212cd2-0ca8-49ec-9dea-4263d50b057e",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "cb4922b2-7577-4fa6-878a-d19d1891322a",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
    "etag": "[REDACTED]"
  }
]
```


## 135. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L38558`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L38558.json`.

```json
[]
```


## 136. GET `/api/UserStorage/Metadata` — device capture response

Source: `device:L38617`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_UserStorage_Metadata/response_L38617.json`.

```json
{
  "continuationToken": "[REDACTED]",
  "metadata": [
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 3",
      "eTag": "[REDACTED]",
      "fileSize": "104910",
      "id": "00eb6c19-5c8f-43e0-951a-a93d1212e154",
      "lastModifiedUtc": "2026-03-03T23:21:18.6887053Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/linear part 3.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "yay",
      "eTag": "[REDACTED]",
      "fileSize": "6803",
      "id": "70c1f771-7d6b-4307-aad8-06fa5db8a6ca",
      "lastModifiedUtc": "2026-03-03T23:21:18.0032945Z",
      "notebookTotalPages": 1,
      "path": "/mnt/onboard/My Notebooks/yay.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "1"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linears tricks",
      "eTag": "[REDACTED]",
      "fileSize": "21013",
      "id": "91ec20ca-74e1-4225-a070-d0327dad0dd9",
      "lastModifiedUtc": "2026-03-03T23:21:17.4094012Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/linears tricks.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "0"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 2",
      "eTag": "[REDACTED]",
      "fileSize": "439972",
      "id": "e01cd5a4-cf50-495c-b639-74c0dc1cfd49",
      "lastModifiedUtc": "2026-03-03T23:21:16.7385072Z",
      "notebookTotalPages": 13,
      "path": "/mnt/onboard/My Notebooks/linear part 2.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "13"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear part 1",
      "eTag": "[REDACTED]",
      "fileSize": "828647",
      "id": "49cfdb4c-d138-4397-a186-64dbf7f2918e",
      "lastModifiedUtc": "2026-03-03T23:21:14.7715381Z",
      "notebookTotalPages": 21,
      "path": "/mnt/onboard/My Notebooks/linear part 1.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "21"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "gggg",
      "eTag": "[REDACTED]",
      "fileSize": "313401",
      "id": "41dc7d5c-e73a-4607-b136-c81acee82bdc",
      "lastModifiedUtc": "2026-03-03T23:21:11.7715015Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/gggg.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ummmmm",
      "eTag": "[REDACTED]",
      "fileSize": "63603",
      "id": "bace44b4-86ed-425c-b4c8-fb26bdcd851b",
      "lastModifiedUtc": "2026-03-03T23:21:09.5426114Z",
      "notebookTotalPages": 4,
      "path": "/mnt/onboard/My Notebooks/ummmmm.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "4"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "ffff",
      "eTag": "[REDACTED]",
      "fileSize": "55469",
      "id": "e77a3652-989c-4e87-b2bc-074ccbe58c49",
      "lastModifiedUtc": "2025-08-28T19:17:43.9940235Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/ffff.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pihole",
      "eTag": "[REDACTED]",
      "fileSize": "24385",
      "id": "341cf01b-017e-48bf-af1a-d83782149b9f",
      "lastModifiedUtc": "2025-07-17T11:01:19.7041363Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pihole.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "hh",
      "eTag": "[REDACTED]",
      "fileSize": "12026",
      "id": "e09bc5fc-7881-4a70-b8a3-b6c26834c51f",
      "lastModifiedUtc": "2025-07-17T11:01:18.7710131Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/hh.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig",
      "eTag": "[REDACTED]",
      "fileSize": "43396",
      "id": "fd63e3ed-c5fd-4d3a-ba08-fe86e01c2518",
      "lastModifiedUtc": "2025-05-31T10:02:03.5527255Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "dig new",
      "eTag": "[REDACTED]",
      "fileSize": "44018",
      "id": "f5fd5042-2fbc-4ad9-80b0-ae3fa0d6ac91",
      "lastModifiedUtc": "2025-05-31T10:02:02.5072792Z",
      "notebookTotalPages": 3,
      "path": "/mnt/onboard/My Notebooks/dig new.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "3"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "pop os requirements",
      "eTag": "[REDACTED]",
      "fileSize": "17753",
      "id": "621eae83-c0f5-4442-a821-bcb7f736cf2a",
      "lastModifiedUtc": "2025-05-31T10:02:01.7350793Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/pop os requirements.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "test",
      "eTag": "[REDACTED]",
      "fileSize": "191042",
      "id": "01cc7b36-13b5-4aa4-aae1-ae5fa9a84787",
      "lastModifiedUtc": "2025-05-31T10:02:00.6303265Z",
      "notebookTotalPages": 8,
      "path": "/mnt/onboard/My Notebooks/test.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "8"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "accounts",
      "eTag": "[REDACTED]",
      "fileSize": "393164",
      "id": "af62a542-6319-42f9-9b98-4675ef30d5b3",
      "lastModifiedUtc": "2025-05-31T10:01:59.5473629Z",
      "notebookTotalPages": 10,
      "path": "/mnt/onboard/My Notebooks/accounts.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "10"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "r",
      "eTag": "[REDACTED]",
      "fileSize": "123260",
      "id": "619e9996-676d-477d-bdfb-15f510b22b4f",
      "lastModifiedUtc": "2025-05-31T10:01:58.3375753Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/r.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "linear",
      "eTag": "[REDACTED]",
      "fileSize": "640992",
      "id": "629c708e-7500-44ee-a2de-b891b80b96ba",
      "lastModifiedUtc": "2025-05-31T10:01:57.4756975Z",
      "notebookTotalPages": 19,
      "path": "/mnt/onboard/My Notebooks/linear.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "19"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "testsss",
      "eTag": "[REDACTED]",
      "fileSize": "167094",
      "id": "9e9568bb-96d9-4cc8-9bd0-d974d242e5b6",
      "lastModifiedUtc": "2025-05-31T10:01:55.9996291Z",
      "notebookTotalPages": 9,
      "path": "/mnt/onboard/My Notebooks/testsss.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "9"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "reb",
      "eTag": "[REDACTED]",
      "fileSize": "32109",
      "id": "74292cb5-158e-4290-8d70-f7d0da4285a3",
      "lastModifiedUtc": "2025-05-31T10:01:54.8273139Z",
      "notebookTotalPages": 2,
      "path": "/mnt/onboard/My Notebooks/reb.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "2"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "InfluencerNight",
      "eTag": "[REDACTED]",
      "fileSize": "144292",
      "id": "d78019a0-c056-445c-afaa-30247f009656",
      "lastModifiedUtc": "2025-04-25T11:13:31.0920381Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/InfluencerNight.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "t",
      "eTag": "[REDACTED]",
      "fileSize": "83320",
      "id": "22c5b060-8463-4c62-9dff-87c478b4a5ab",
      "lastModifiedUtc": "2025-04-12T23:16:39.3675739Z",
      "notebookTotalPages": 5,
      "path": "/mnt/onboard/My Notebooks/t.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "5"
      }
    },
    {
      "contentType": "application/vnd.myscript.nebo+raw",
      "displayName": "Linear Algebra Partial",
      "eTag": "[REDACTED]",
      "fileSize": "355842",
      "id": "24388e6e-aaf8-4b2c-bf18-6108f495ce72",
      "lastModifiedUtc": "2025-04-12T23:16:38.5194657Z",
      "notebookTotalPages": 14,
      "path": "/mnt/onboard/My Notebooks/Linear Algebra Partial.nebo",
      "tags": {
        "additionalProp1": "1",
        "additionalProp2": "14"
      }
    }
  ]
}
```


## 137. GET `/v1/user/recommendations` — device capture response

Source: `device:L38825`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_user_recommendations/response_L38825.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 0,
  "Items": [],
  "ItemsPerPage": 50,
  "TotalItemCount": 0,
  "TotalPageCount": 0,
  "VersionCode": 2
}
```


## 138. GET `/v1/products` — device capture response

Source: `device:L39369`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_products/response_L39369.json`.

```json
{
  "Items": [
    {
      "Suggestion": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Type": "title",
      "Display": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Title": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc",
      "Authors": [
        "arukuhito"
      ]
    }
  ],
  "ItemCount": 1,
  "TotalItemCount": 1
}
```


## 139. GET `/v1/products` — device capture response

Source: `device:L39685`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_products/response_L39685.json`.

```json
{
  "Items": [
    {
      "Suggestion": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Type": "title",
      "Display": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Title": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc",
      "Authors": [
        "arukuhito"
      ]
    }
  ],
  "ItemCount": 1,
  "TotalItemCount": 1
}
```


## 140. GET `/v1/products/{uuid}/reviews` — device capture response

Source: `device:L41862`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_v1_products_uuid_reviews/response_L41862.json`.

```json
{
  "CurrentPageIndex": 1,
  "Items": [
    {
      "AuthorDisplayName": "[REDACTED]",
      "Body": "L'histoire est un peu moins bie construite que les autres tomes. ├ça m'a beaucoup d├⌐├ºue. Par contre, les dessins sont toujours aussi beaux ! Je n'ai rien ├á redire sur ce dernier point.",
      "CreationDate": "2024-01-29T15:31:09.9314987-05:00",
      "CrossRevisionId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4",
      "Dislikes": 0,
      "Id": "Book-b8dc5f22-1050-333f-aebf-4b3b5000d1e4-yQOV9wdevs55AvIhXPV3TRr63dXBGTOT7uBu4Wms=",
      "Likes": 3,
      "ModerationStatus": "Approved",
      "ProductId": "92329c27-e655-4caf-aa15-54f1354ae9d3",
      "PublicationId": "00000000-0000-0000-0000-000000000000",
      "Rating": 4,
      "RevisionId": "92329c27-e655-4caf-aa15-54f1354ae9d3",
      "Title": "Mon avis"
    }
  ],
  "ReviewSummary": {
    "b8dc5f22-1050-333f-aebf-4b3b5000d1e4": {
      "AvgRating": 4.75,
      "NumberOfReviews": 1,
      "OpinionCount": 16,
      "RatingHistogram": {
        "4": 4,
        "5": 12
      }
    }
  },
  "TotalPageCount": 1
}
```


## 141. GET `/v1/products/books/{uuid}/` — device capture response

Source: `device:L41953`. HTTP: `200`. Captured type: `object (38 top-level keys)`. Fixture: `device/GET_v1_products_books_uuid/response_L41953.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false
  },
  "AgeVerificationRequired": false,
  "ApplicableSubscriptions": [],
  "ContributorRoles": [
    {
      "Name": "Kanehito Yamada",
      "Role": "Author"
    },
    {
      "Name": "Tsukasa Abe",
      "Role": "Author"
    }
  ],
  "Contributors": "Kanehito Yamada, Tsukasa Abe",
  "CrossRevisionId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4",
  "Description": "<p>Que deviennent les h├⌐ros une fois le mal vaincu ?</p>\n<p>Alors que Frieren et ses compagnons font halte dans la citadelle de Hei├ƒ, Stark propose ├á Fern d'aller en rendez-vous, sur le t… [TRUNCATED 531 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9791032714010",
  "Id": "92329c27-e655-4caf-aa15-54f1354ae9d3",
  "ImageId": "7d0540e1-f54e-420a-a312-18a07d0d00bf",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "fr",
  "Locale": {
    "LanguageCode": "fre"
  },
  "LovePointsPrice": 2800,
  "Price": {
    "Currency": "EUR",
    "Price": 4.99
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2023-06-08T00:00:00.0000000Z",
  "PublisherName": "AC MEDIA",
  "Rating": 4.75,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 4,
    "5": 12
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 20734284,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "RelatedGroupId": "b4655498-0089-03e0-0000-000000000000",
  "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
  "SeriesName": "Frieren",
  "SeriesNumber": "",
  "Slug": "frieren-t08",
  "Stats": {
    "WordCount": 0
  },
  "Subtitle": "",
  "Title": "Frieren T08",
  "TotalRating": 16,
  "WorkId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4"
}
```


## 142. GET `/v1/user/reviews` — device capture response

Source: `device:L41925`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_user_reviews/response_L41925.json`.

```json
{
  "CurrentPageIndex": 1,
  "Items": [],
  "TotalPageCount": 0
}
```


## 143. GET `/v1/products` — device capture response

Source: `device:L45277`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_products/response_L45277.json`.

```json
{
  "Items": [
    {
      "Suggestion": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Type": "title",
      "Display": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Title": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc",
      "Authors": [
        "arukuhito"
      ]
    }
  ],
  "ItemCount": 1,
  "TotalItemCount": 1
}
```


## 144. GET `/v1/products` — device capture response

Source: `device:L45633`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_products/response_L45633.json`.

```json
{
  "Items": [
    {
      "Suggestion": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Type": "title",
      "Display": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc (arukuhito)",
      "Title": "Isekai Walking: Volume 2 Holy Kingdom of Frieren Arc",
      "Authors": [
        "arukuhito"
      ]
    }
  ],
  "ItemCount": 1,
  "TotalItemCount": 1
}
```


## 145. POST `/v1/user/wishlist/items` — device capture request

Source: `device:L45735`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/POST_v1_user_wishlist_items/request_L45735.json`.

```json
{
  "Add": [
    {
      "CrossRevisionId": "7aad241f-f35f-36d7-bbe4-32dc10b65e9f",
      "DateAdded": "2026-09-22T11:53:24Z"
    }
  ],
  "Remove": []
}
```


## 146. POST `/v1/user/wishlist/items` — device capture response

Source: `device:L45756`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/POST_v1_user_wishlist_items/response_L45756.json`.

```json
{
  "SuccessfulIds": [
    "7aad241f-f35f-36d7-bbe4-32dc10b65e9f"
  ]
}
```


## 147. GET `/v1/library/{komgaId}/state` — device capture response

Source: `device:L45819`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_state/response_L45819.json`.

```json
[
  {
    "Created": "2026-08-26T18:22:23Z",
    "CurrentBookmark": {
      "ContentSourceProgressPercent": 0,
      "LastModified": "2026-09-22T11:46:02.357Z",
      "Location": {
        "Source": "OEBPS/Text/kcc-0200-kcc-x.xhtml",
        "Type": "KoboSpan",
        "Value": "kobo.1.1"
      },
      "ProgressPercent": 100
    },
    "EntitlementId": "0RDTATKHXHPS9",
    "LastModified": "2026-09-22T11:46:02.357Z",
    "PriorityTimestamp": "2026-09-22T11:46:02.357Z",
    "Statistics": {
      "LastModified": "2026-09-22T11:46:02.357Z"
    },
    "StatusInfo": {
      "LastModified": "2026-09-22T11:46:02.357Z",
      "Status": "Finished",
      "TimesStartedReading": 1
    }
  }
]
```


## 148. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L45852`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L45852.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 149. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L45869`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L45869.json`.

```json
[]
```


## 150. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L45905`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L45905.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:53:46Z",
        "Location": {
          "Source": "OEBPS/Text/kcc-0124-kcc-x.xhtml",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 62
      },
      "EntitlementId": "0RDTATKHXHPS9",
      "LastModified": "2026-09-22T11:53:46Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:53:46Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 0
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:53:46Z",
        "Status": "Reading"
      }
    }
  ]
}
```


## 151. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L45926`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L45926.json`.

```json
{
  "RequestResult": "Success",
  "UpdateResults": [
    {
      "CurrentBookmarkResult": {
        "Result": "Success"
      },
      "EntitlementId": "0RDTATKHXHPS9",
      "StatisticsResult": {
        "Result": "Ignored"
      },
      "StatusInfoResult": {
        "Result": "Success"
      }
    }
  ]
}
```


## 152. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L45960`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L45960.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 153. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L45976`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L45976.json`.

```json
[]
```


## 154. GET `/v1/library/{komgaId}/state` — device capture response

Source: `device:L46036`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_state/response_L46036.json`.

```json
[
  {
    "Created": "2026-08-26T18:22:23Z",
    "CurrentBookmark": {
      "ContentSourceProgressPercent": 0,
      "LastModified": "2026-09-22T11:53:52.81Z",
      "Location": {
        "Source": "OEBPS/Text/kcc-0145-kcc-x.xhtml",
        "Type": "KoboSpan",
        "Value": "kobo.1.1"
      },
      "ProgressPercent": 72.63682
    },
    "EntitlementId": "0RDTATKHXHPS9",
    "LastModified": "2026-09-22T11:53:52.81Z",
    "PriorityTimestamp": "2026-09-22T11:53:52.81Z",
    "Statistics": {
      "LastModified": "2026-09-22T11:53:52.81Z"
    },
    "StatusInfo": {
      "LastModified": "2026-09-22T11:53:52.81Z",
      "Status": "Reading",
      "TimesStartedReading": 1
    }
  }
]
```


## 155. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L46069`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L46069.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 156. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L46085`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L46085.json`.

```json
[]
```


## 157. GET `/v1/library/{komgaId}/state` — device capture response

Source: `device:L46134`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_state/response_L46134.json`.

```json
[
  {
    "Created": "2026-08-26T18:22:23Z",
    "CurrentBookmark": {
      "ContentSourceProgressPercent": 0,
      "LastModified": "2026-09-22T11:53:52.81Z",
      "Location": {
        "Source": "OEBPS/Text/kcc-0145-kcc-x.xhtml",
        "Type": "KoboSpan",
        "Value": "kobo.1.1"
      },
      "ProgressPercent": 72.63682
    },
    "EntitlementId": "0RDTATKHXHPS9",
    "LastModified": "2026-09-22T11:53:52.81Z",
    "PriorityTimestamp": "2026-09-22T11:53:52.81Z",
    "Statistics": {
      "LastModified": "2026-09-22T11:53:52.81Z"
    },
    "StatusInfo": {
      "LastModified": "2026-09-22T11:53:52.81Z",
      "Status": "Reading",
      "TimesStartedReading": 1
    }
  }
]
```


## 158. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L46167`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L46167.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 159. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L46183`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L46183.json`.

```json
[]
```


## 160. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L46220`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L46220.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:54:02Z",
        "Location": {
          "Source": "OEBPS/Text/kcc-0145-kcc-x.xhtml",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 72
      },
      "EntitlementId": "0RDTATKHXHPS9",
      "LastModified": "2026-09-22T11:54:02Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:54:02Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 0
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:54:02Z",
        "Status": "Reading"
      }
    }
  ]
}
```


## 161. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L46241`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L46241.json`.

```json
{
  "RequestResult": "Success",
  "UpdateResults": [
    {
      "CurrentBookmarkResult": {
        "Result": "Success"
      },
      "EntitlementId": "0RDTATKHXHPS9",
      "StatisticsResult": {
        "Result": "Ignored"
      },
      "StatusInfoResult": {
        "Result": "Success"
      }
    }
  ]
}
```


## 162. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L46275`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L46275.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 163. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L46291`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L46291.json`.

```json
[]
```


## 164. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L6`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L6.json`.

```json
[]
```


## 165. not logged `/v1/user/wishlist` — Komga upstream standard proxy log response

Source: `server:L9`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_wishlist/response_L9.json`.

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


## 166. not logged `/v1/user/profile` — Komga upstream standard proxy log response

Source: `server:L12`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `upstream/unknown_v1_user_profile/response_L12.json`.

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
    "LoyaltyStartDate": "2026-09-22T11:20:25.7262803+00:00",
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


## 167. not logged `/v1/initialization` — Komga upstream standard proxy log response

Source: `server:L15`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_initialization/response_L15.json`.

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


## 168. not logged `/v1/user/profile` — Komga upstream standard proxy log response

Source: `server:L18`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `upstream/unknown_v1_user_profile/response_L18.json`.

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
    "LoyaltyStartDate": "2026-09-22T11:36:42.3087917+00:00",
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


## 169. not logged `/v1/user/loyalty/benefits` — Komga upstream standard proxy log response

Source: `server:L21`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_user_loyalty_benefits/response_L21.json`.

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


## 170. not logged `/v1/products/books/subscriptions` — Komga upstream raw proxy log response

Source: `server:L23`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `upstream/unknown_v1_products_books_subscriptions/response_L23.json`.

```json
[
  {
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Id": "853f44cf-0699-4082-b74a-72460b53f1f7",
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
        "Id": "d3499076-85a0-41c9-8fda-566fe2797958",
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
        "Id": "4c8f0acc-206f-4057-92b7-817e427e02bc",
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


## 171. not logged `/v1/deals` — Komga upstream standard proxy log response

Source: `server:L26`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_deals/response_L26.json`.

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


## 172. not logged `/v1/assets` — Komga upstream standard proxy log response

Source: `server:L29`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `upstream/unknown_v1_assets/response_L29.json`.

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


## 173. not logged `/v1/analytics/gettests` — Komga upstream standard proxy log response

Source: `server:L32`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `upstream/unknown_v1_analytics_gettests/response_L32.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 174. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L104`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L104.json`.

```json
[]
```

