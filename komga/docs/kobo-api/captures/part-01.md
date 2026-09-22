# Captures 1–68

[← Capture index](README.md) · [API index](../../kobo-api.md)

## 1. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L110`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L110.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 2. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L148`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L148.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 3. GET `/v1/initialization` — device capture response

Source: `device:L197`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_initialization/response_L197.json`.

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


## 4. GET `/v1/user/profile` — device capture response

Source: `device:L246`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L246.json`.

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
    "LoyaltyStartDate": "2026-09-22T11:36:42.3087917+00:00",
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


## 5. GET `/v1/user/loyalty/benefits` — device capture response

Source: `device:L294`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_user_loyalty_benefits/response_L294.json`.

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


## 6. GET `/v1/products/books/subscriptions` — device capture response

Source: `device:L342`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `device/GET_v1_products_books_subscriptions/response_L342.json`.

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
        "Id": "853f44cf-0699-4082-b74a-72460b53f1f7",
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
        "Id": "d3499076-85a0-41c9-8fda-566fe2797958",
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
        "Id": "4c8f0acc-206f-4057-92b7-817e427e02bc",
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


## 7. GET `/v1/deals` — device capture response

Source: `device:L395`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_deals/response_L395.json`.

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


## 8. GET `/v1/assets` — device capture response

Source: `device:L446`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_v1_assets/response_L446.json`.

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


## 9. POST `/v1/analytics/gettests` — device capture request

Source: `device:L477`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/request_L477.json`.

```json
{
  "AffiliateName": "Kobo",
  "ApplicationVersion": "4.46.23836",
  "PlatformId": "00000000-0000-0000-0000-000000000390",
  "SerialNumber": "[REDACTED]",
  "TestKey": "00000000-0000-4000-8000-000000000000"
}
```


## 10. POST `/v1/analytics/gettests` — device capture response

Source: `device:L498`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/POST_v1_analytics_gettests/response_L498.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 11. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L2570`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L2570.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Hajime Kamoshida"
      },
      {
        "Name": "Keji Mizoguchi"
      }
    ],
    "Contributors": [
      "Hajime Kamoshida",
      "Keji Mizoguchi"
    ],
    "CoverImageId": "0RK4D9QSWQCRR",
    "CrossRevisionId": "0RDTATJQNHSQ0",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "WHAT WILL IT TAKE TO MAKE HER WISH COME TRUE?After a draining December, Sakuta is quickly nearing the end of his second year of high school. Since Mai is a third-year student, they donΓÇÖt h… [TRUNCATED 464 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "KEPUB",
        "Platform": "Generic",
        "Size": 6656328,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RDTATJQNHSQ0/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RDTATJQNHSQ0",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781975312671",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2022-08-22T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "Yen Press"
    },
    "RevisionId": "0RDTATJQNHSQ0",
    "Series": {
      "Id": "0RDTATJHXHZRM",
      "Name": "Rascal Does Not Dream (light novel)",
      "Number": "8",
      "NumberFloat": 8
    },
    "Title": "Rascal Does Not Dream of a Sister Venturing Out (light novel)",
    "WorkId": "0RDTATJQNHSQ0"
  }
]
```


## 12. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L2740`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L2740.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 13. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L2923`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L2923.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Tatsuya Endo"
      }
    ],
    "Contributors": [
      "Tatsuya Endo"
    ],
    "CoverImageId": "0RPA2DAJ0JTT2",
    "CrossRevisionId": "0RDTATKHXHPS9",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 443 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "EPUB3FL",
        "Platform": "Generic",
        "Size": 72781277,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RDTATKHXHPS9/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RDTATKHXHPS9",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781974753246",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2025-01-13T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "VIZ Media"
    },
    "RevisionId": "0RDTATKHXHPS9",
    "Series": {
      "Id": "0RDTATKG1HM0V",
      "Name": "Spy x Family",
      "Number": "13",
      "NumberFloat": 13
    },
    "Title": "Spy X Family, Vol. 13",
    "WorkId": "0RDTATKHXHPS9"
  }
]
```


## 14. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L3430`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L3430.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 15. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L3774`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L3774.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Yukinobu Tatsu"
      }
    ],
    "Contributors": [
      "Yukinobu Tatsu"
    ],
    "CoverImageId": "0RPA2MWERJNWK",
    "CrossRevisionId": "0RKJK260M7AGR",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "Momo Ayase and Okarun are on opposite sides of the paranormal spectrum regarding what theyΓÇÖll believe in and what they wonΓÇÖt. Their quest to prove each other wrong leads them down a path… [TRUNCATED 86 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "EPUB3FL",
        "Platform": "Generic",
        "Size": 110537637,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RKJK260M7AGR/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RKJK260M7AGR",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781974763474",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2026-02-02T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "VIZ Media"
    },
    "RevisionId": "0RKJK260M7AGR",
    "Series": {
      "Id": "0RKGS49PPFETD",
      "Name": "Dandadan",
      "Number": "17",
      "NumberFloat": 17
    },
    "Title": "Dandadan, Vol. 17",
    "WorkId": "0RKJK260M7AGR"
  }
]
```


## 16. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L4311`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L4311.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 17. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L4864`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L4864.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Tsukumizu"
      }
    ],
    "Contributors": [
      "Tsukumizu"
    ],
    "CoverImageId": "0RPA2D6B8JQ97",
    "CrossRevisionId": "0RDTATKFHHSYW",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "Even after a mysterious life form tells Chito and Yuri the end of the earth is near, still they continue their everyday adventures together, traveling slowly toward the city on the top-most … [TRUNCATED 140 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "EPUB3FL",
        "Platform": "Generic",
        "Size": 67166847,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RDTATKFHHSYW/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RDTATKFHHSYW",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781975329006",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2018-12-10T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "Yen Press"
    },
    "RevisionId": "0RDTATKFHHSYW",
    "Series": {
      "Id": "0RDTATKF5HM5A",
      "Name": "Girls' Last Tour",
      "Number": "5",
      "NumberFloat": 5
    },
    "Title": "Girls' Last Tour, Vol. 5",
    "WorkId": "0RDTATKFHHSYW"
  }
]
```


## 18. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L5373`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L5373.json`.

```json
{
  "annotations": [],
  "nextPageOffsetToken": null
}
```


## 19. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L5684`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L5684.json`.

```json
[
  {
    "Categories": [
      "00000000-0000-0000-0000-000000000001"
    ],
    "ContributorRoles": [
      {
        "Name": "Tatsuya Endo"
      }
    ],
    "Contributors": [
      "Tatsuya Endo"
    ],
    "CoverImageId": "0RPA2D8YGJPSV",
    "CrossRevisionId": "0RDTATKH5HXZS",
    "CurrentDisplayPrice": {
      "CurrencyCode": "USD",
      "TotalAmount": 0
    },
    "CurrentLoveDisplayPrice": {
      "TotalAmount": 0
    },
    "Description": "The midterm examsΓÇöwith stella stars and tonitrus bolts on the lineΓÇöare over. Has AnyaΓÇÖs hard work studying classical language paid off? Also, in hopes of advancing her plan B at the cl… [TRUNCATED 51 chars; original string value]",
    "DownloadUrls": [
      {
        "DrmType": "None",
        "Format": "EPUB3FL",
        "Platform": "Generic",
        "Size": 71303327,
        "Url": "https://komga.com/kobo/{token}/v1/books/0RDTATKH5HXZS/file/epub?convert_kepub=false"
      }
    ],
    "EntitlementId": "0RDTATKH5HXZS",
    "ExternalIds": [],
    "Genre": "00000000-0000-0000-0000-000000000001",
    "IsEligibleForKoboLove": false,
    "IsInternetArchive": false,
    "IsPreOrder": false,
    "IsSocialEnabled": true,
    "Isbn": "9781974757466",
    "Language": "en",
    "PhoneticPronunciations": {},
    "PublicationDate": "2025-08-04T00:00:00Z",
    "Publisher": {
      "Imprint": "",
      "Name": "VIZ Media"
    },
    "RevisionId": "0RDTATKH5HXZS",
    "Series": {
      "Id": "0RDTATKG1HM0V",
      "Name": "Spy x Family",
      "Number": "14",
      "NumberFloat": 14
    },
    "Title": "Spy X Family, Vol. 14",
    "WorkId": "0RDTATKH5HXZS"
  }
]
```


## 20. GET `/v1/user/profile` — device capture response

Source: `device:L6649`. HTTP: `200`. Captured type: `object (25 top-level keys)`. Fixture: `device/GET_v1_user_profile/response_L6649.json`.

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
    "LoyaltyStartDate": "2026-09-22T11:36:42.3087917+00:00",
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


## 21. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L18323`. HTTP: `200`. Captured type: `array (7 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L18323.json`.

```json
[
  {
    "ContentId": "42ed6d94-1f32-4df9-9d97-023335a4eb9c",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKFHHSYW",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATJQNHSQ0",
    "etag": "[REDACTED]"
  },
  {
    "ContentId": "0RDTATKHXHPS9",
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
    "ContentId": "0RDTATKH5HXZS",
    "etag": "[REDACTED]"
  }
]
```


## 22. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L18339`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L18339.json`.

```json
[]
```


## 23. GET `/api/UserStorage/Metadata` — device capture response

Source: `device:L18398`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_UserStorage_Metadata/response_L18398.json`.

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


## 24. GET `/v1/products/{komgaId}/recommendations` — device capture response

Source: `device:L18828`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_recommendations/response_L18828.json`.

```json
{
  "CurrentPageIndex": 0,
  "ItemCount": 5,
  "Items": [
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tsukumizu"
          },
          {
            "Name": "Amanda Haley"
          },
          {
            "Name": "Abigail Blackman"
          }
        ],
        "Contributors": "Tsukumizu,Amanda Haley,Abigail Blackman",
        "CrossRevisionId": "0RDTATKFHHSYY",
        "Description": "Civilization is dead, but not Chito and Yuuri. Time to hop aboard their beloved Kettenkrad motorbike and wander what's left of the world! Sharing a can of soup or scouting for spare parts mi… [TRUNCATED 85 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9780316470636",
        "Id": "0RDTATKFHHSYY",
        "ImageId": "0RPA2D6XGJJ72",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PublicationDate": "2017-05-23T00:00:00.0000000Z",
        "PublisherName": "Yen Press",
        "Rating": 4.833333,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 5973281,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKF5HM5A",
        "SeriesName": "Girls' Last Tour",
        "SeriesNumber": "1",
        "SeriesNumberFloat": 1,
        "Slug": "girls-last-tour-vol-1-2",
        "Title": "Girls' Last Tour, Vol. 1",
        "TotalRating": 18,
        "WorkId": "0RDTATKFHHSYY"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tsukumizu"
          },
          {
            "Name": "Amanda Haley"
          }
        ],
        "Contributors": "Tsukumizu,Amanda Haley",
        "CrossRevisionId": "0RDTATKFNHKTK",
        "Description": "Distant lights illuminating the darkness pique Chito's and Yuuri's curiosity, so the two hop aboard their beloved Kettenkrad and head for the horizon. What they find may not be what they wer… [TRUNCATED 180 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9780316470650",
        "Id": "0RDTATKFNHKTK",
        "ImageId": "0RPA2D7MGJZ8E",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PublicationDate": "2017-08-22T00:00:00.0000000Z",
        "PublisherName": "Yen Press",
        "Rating": 4.642857,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 5286566,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKF5HM5A",
        "SeriesName": "Girls' Last Tour",
        "SeriesNumber": "2",
        "SeriesNumberFloat": 2,
        "Slug": "girls-last-tour-vol-2-2",
        "Title": "Girls' Last Tour, Vol. 2",
        "TotalRating": 14,
        "WorkId": "0RDTATKFNHKTK"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tsukumizu"
          },
          {
            "Name": "Amanda Haley"
          }
        ],
        "Contributors": "Tsukumizu,Amanda Haley",
        "CrossRevisionId": "0RDTATKFHHSYX",
        "Description": "Chito and Yuuri have arrived at the highest stratum, but their journey will not be over until they reach the very top of the city. Along the way, they continue to explore the evidences of hu… [TRUNCATED 218 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781975329044",
        "Id": "0RDTATKFHHSYX",
        "ImageId": "0RPA2D6FGJJXF",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PublicationDate": "2019-02-19T00:00:00.0000000Z",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 4492768,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKF5HM5A",
        "SeriesName": "Girls' Last Tour",
        "SeriesNumber": "6",
        "SeriesNumberFloat": 6,
        "Slug": "girls-last-tour-vol-6",
        "Title": "Girls' Last Tour, Vol. 6",
        "TotalRating": 8,
        "WorkId": "0RDTATKFHHSYX"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tsukumizu"
          },
          {
            "Name": "Amanda Haley"
          }
        ],
        "Contributors": "Tsukumizu,Amanda Haley",
        "CrossRevisionId": "0RDTATKFNHKTH",
        "Description": "Chito and Yuri's post-apocalyptic, everyday adventures continue as the two discover a mysterious living creature!",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781975326111",
        "Id": "0RDTATKFNHKTH",
        "ImageId": "0RPA2D76RJJF2",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PublicationDate": "2018-02-27T00:00:00.0000000Z",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 5987086,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKF5HM5A",
        "SeriesName": "Girls' Last Tour",
        "SeriesNumber": "4",
        "SeriesNumberFloat": 4,
        "Slug": "girls-last-tour-vol-4",
        "Title": "Girls' Last Tour, Vol. 4",
        "TotalRating": 5,
        "WorkId": "0RDTATKFNHKTH"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tsukumizu"
          },
          {
            "Name": "Amanda Haley"
          },
          {
            "Name": "Abigail Blackman"
          }
        ],
        "Contributors": "Tsukumizu,Amanda Haley,Abigail Blackman",
        "CrossRevisionId": "0RDTATKFNHKTJ",
        "Description": "(Volume 2)Titus and Yuri continue traveling in a world where the civilization collapsed. What did the two who ventured to the upper level of the city find?",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9780316470674",
        "Id": "0RDTATKFNHKTJ",
        "ImageId": "0RPA2D7DGJW8J",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 5.99
        },
        "PublicationDate": "2017-11-14T00:00:00.0000000Z",
        "PublisherName": "Yen Press",
        "Rating": 5,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 6295101,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKF5HM5A",
        "SeriesName": "Girls' Last Tour",
        "SeriesNumber": "3",
        "SeriesNumberFloat": 3,
        "Slug": "girls-last-tour-vol-3-2",
        "Title": "Girls' Last Tour, Vol. 3",
        "TotalRating": 4,
        "WorkId": "0RDTATKFNHKTJ"
      }
    }
  ],
  "ItemsPerPage": 5,
  "TotalItemCount": 95,
  "TotalPageCount": 19,
  "VersionCode": 2
}
```


## 25. GET `/v1/products/{komgaId}/recommendations` — device capture response

Source: `device:L19000`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_recommendations/response_L19000.json`.

```json
{
  "CurrentPageIndex": 0,
  "ItemCount": 5,
  "Items": [
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH5HXZM",
        "Description": "Not one to depend on others, Twilight has his work cut out for him procuring both a wife and a child for his mission to infiltrate an elite private school. What he doesnΓÇÖt know is that the… [TRUNCATED 78 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974720286",
        "Id": "0RDTATKH5HXZM",
        "ImageId": "0RPA2D7X0JMTC",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2020-06-02T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.765162,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 8824530,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "1",
        "SeriesNumberFloat": 1,
        "Slug": "spy-x-family-vol-1",
        "Title": "Spy x Family, Vol. 1",
        "TotalRating": 775,
        "WorkId": "0RDTATKH5HXZM"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH5HXZS",
        "Description": "The midterm examsΓÇöwith stella stars and tonitrus bolts on the lineΓÇöare over. Has AnyaΓÇÖs hard work studying classical language paid off? Also, in hopes of advancing her plan B at the cl… [TRUNCATED 51 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974757466",
        "Id": "0RDTATKH5HXZS",
        "ImageId": "0RPA2D8YGJPSV",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-08-05T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.842105,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 2451645,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "14",
        "SeriesNumberFloat": 14,
        "Slug": "spy-x-family-vol-14",
        "Title": "Spy x Family, Vol. 14",
        "TotalRating": 190,
        "WorkId": "0RDTATKH5HXZS"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0E",
        "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 401 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974760602",
        "Id": "0RDTATKH9HV0E",
        "ImageId": "0RPA2D9K8JSGY",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-12-02T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.660131,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 7394220,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "15",
        "SeriesNumberFloat": 15,
        "Slug": "spy-x-family-vol-15",
        "Title": "Spy x Family, Vol. 15",
        "TotalRating": 153,
        "WorkId": "0RDTATKH9HV0E"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0D",
        "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 452 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974748600",
        "Id": "0RDTATKH9HV0D",
        "ImageId": "0RPA2D9E8JJ8K",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2024-08-06T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.721925,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 3941152,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "12",
        "SeriesNumberFloat": 12,
        "Slug": "spy-x-family-vol-12",
        "Title": "Spy x Family, Vol. 12",
        "TotalRating": 187,
        "WorkId": "0RDTATKH9HV0D"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0G",
        "Description": "Threatened by YorΓÇÖs relationship with Melinda Desmond, Anya gets serious about her own friendship scheme. On the way to a museum field trip, however, she and her class become the target of… [TRUNCATED 65 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974745067",
        "Id": "0RDTATKH9HV0G",
        "ImageId": "0RPA2D9VMJWZX",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2024-03-19T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.766667,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 5902058,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "11",
        "SeriesNumberFloat": 11,
        "Slug": "spy-x-family-vol-11",
        "Title": "Spy x Family, Vol. 11",
        "TotalRating": 270,
        "WorkId": "0RDTATKH9HV0G"
      }
    }
  ],
  "ItemsPerPage": 5,
  "TotalItemCount": 86,
  "TotalPageCount": 18,
  "VersionCode": 2
}
```


## 26. GET `/v1/products/{komgaId}/recommendations` — device capture response

Source: `device:L19086`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_recommendations/response_L19086.json`.

```json
{
  "CurrentPageIndex": 0,
  "ItemCount": 5,
  "Items": [
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Yukinobu Tatsu"
          }
        ],
        "Contributors": "Yukinobu Tatsu",
        "CrossRevisionId": "0RKJK260C7AX8",
        "Description": "Momo Ayase and Okarun are on opposite sides of the paranormal spectrum regarding what theyΓÇÖll believe in and what they wonΓÇÖt. Their quest to prove each other wrong leads them down a path… [TRUNCATED 86 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974760688",
        "Id": "0RKJK260C7AX8",
        "ImageId": "0RPA2MVW4JHT4",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-12-09T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.5,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 8769335,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RKGS49PPFETD",
        "SeriesName": "Dandadan",
        "SeriesNumber": "16",
        "SeriesNumberFloat": 16,
        "Slug": "dandadan-vol-16-1",
        "Title": "Dandadan, Vol. 16",
        "TotalRating": 16,
        "WorkId": "0RKJK260C7AX8"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Yukinobu Tatsu"
          }
        ],
        "Contributors": "Yukinobu Tatsu",
        "CrossRevisionId": "bff30543-74bf-35e0-a393-65f41094fd3a",
        "Description": "<p>Momo Ayase and Okarun are on opposite sides of the paranormal spectrum regarding what theyΓÇÖll believe in and what they wonΓÇÖt. Their quest to prove each other wrong leads them down a p… [TRUNCATED 93 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974764686",
        "Id": "5e090b20-6a0e-4dc9-9a8a-d7a560e0f14e",
        "ImageId": "ad09ace9-57ef-4d86-ba32-d36635117fe8",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2026-04-07T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.6,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 8755887,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "Slug": "dandadan-vol-18-1",
        "Title": "Dandadan, Vol. 18",
        "TotalRating": 10,
        "WorkId": "bff30543-74bf-35e0-a393-65f41094fd3a"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Yukinobu Tatsu"
          }
        ],
        "Contributors": "Yukinobu Tatsu",
        "CrossRevisionId": "bc5d8e22-bf26-3a77-90da-73fa6df0bea9",
        "Description": "<p>Momo Ayase and Okarun are on opposite sides of the paranormal spectrum regarding what theyΓÇÖll believe in and what they wonΓÇÖt. Their quest to prove each other wrong leads them down a p… [TRUNCATED 93 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974767403",
        "Id": "10283b21-b4ae-4654-a482-5cf1eea6afcc",
        "ImageId": "13562750-0239-4c41-ba2e-b48624dd8e82",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2026-06-09T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 5,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 10512819,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "Slug": "dandadan-vol-19-1",
        "Title": "Dandadan, Vol. 19",
        "TotalRating": 6,
        "WorkId": "bc5d8e22-bf26-3a77-90da-73fa6df0bea9"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Yukinobu Tatsu"
          }
        ],
        "Contributors": "Yukinobu Tatsu",
        "CrossRevisionId": "0RKJK260G7109",
        "Description": "Momo Ayase and Okarun are on opposite sides of the paranormal spectrum regarding what theyΓÇÖll believe in and what they wonΓÇÖt. Their quest to prove each other wrong leads them down a path… [TRUNCATED 86 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974759859",
        "Id": "0RKJK260G7109",
        "ImageId": "0RPA2MW0MJPBE",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-10-07T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.793103,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 3400504,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RKGS49PPFETD",
        "SeriesName": "Dandadan",
        "SeriesNumber": "15",
        "SeriesNumberFloat": 15,
        "Slug": "dandadan-vol-15-1",
        "Title": "Dandadan, Vol. 15",
        "TotalRating": 29,
        "WorkId": "0RKJK260G7109"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Yukinobu Tatsu"
          }
        ],
        "Contributors": "Yukinobu Tatsu",
        "CrossRevisionId": "0RKJK25XC75MK",
        "Description": "As Momo and the others fight desperately against the alien invaders, a recovering Okarun finally comes to and is able to join them. Unfortunately for him, itΓÇÖs Tuesday, and that means one … [TRUNCATED 304 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974757411",
        "Id": "0RKJK25XC75MK",
        "ImageId": "0RPA2MVJ0JXRM",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-08-05T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.730769,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 5407535,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RKGS49PPFETD",
        "SeriesName": "Dandadan",
        "SeriesNumber": "14",
        "SeriesNumberFloat": 14,
        "Slug": "dandadan-vol-14-1",
        "Title": "Dandadan, Vol. 14",
        "TotalRating": 26,
        "WorkId": "0RKJK25XC75MK"
      }
    }
  ],
  "ItemsPerPage": 5,
  "TotalItemCount": 89,
  "TotalPageCount": 18,
  "VersionCode": 2
}
```


## 27. GET `/v1/products/{komgaId}/recommendations` — device capture response

Source: `device:L19562`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_recommendations/response_L19562.json`.

```json
{
  "CurrentPageIndex": 0,
  "ItemCount": 5,
  "Items": [
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH5HXZM",
        "Description": "Not one to depend on others, Twilight has his work cut out for him procuring both a wife and a child for his mission to infiltrate an elite private school. What he doesnΓÇÖt know is that the… [TRUNCATED 78 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974720286",
        "Id": "0RDTATKH5HXZM",
        "ImageId": "0RPA2D7X0JMTC",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2020-06-02T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.765162,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 8824530,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "1",
        "SeriesNumberFloat": 1,
        "Slug": "spy-x-family-vol-1",
        "Title": "Spy x Family, Vol. 1",
        "TotalRating": 775,
        "WorkId": "0RDTATKH5HXZM"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0F",
        "Description": "Twilight must infiltrate the prestigious Eden Academy to get close to his target Donovan Desmond, but has he ruined his daughter AnyaΓÇÖs chances with his outburst during the admissions inte… [TRUNCATED 167 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974722518",
        "Id": "0RDTATKH9HV0F",
        "ImageId": "0RPA2D9Q8JGH5",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2020-09-01T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.800376,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 2996736,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "2",
        "SeriesNumberFloat": 2,
        "Slug": "spy-x-family-vol-2",
        "Title": "Spy x Family, Vol. 2",
        "TotalRating": 531,
        "WorkId": "0RDTATKH9HV0F"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0E",
        "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 401 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974760602",
        "Id": "0RDTATKH9HV0E",
        "ImageId": "0RPA2D9K8JSGY",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-12-02T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.660131,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 7394220,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "15",
        "SeriesNumberFloat": 15,
        "Slug": "spy-x-family-vol-15",
        "Title": "Spy x Family, Vol. 15",
        "TotalRating": 153,
        "WorkId": "0RDTATKH9HV0E"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKHXHPS9",
        "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 443 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974753246",
        "Id": "0RDTATKHXHPS9",
        "ImageId": "0RPA2DAJ0JTT2",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2025-01-14T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.75,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 3070484,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "13",
        "SeriesNumberFloat": 13,
        "Slug": "spy-x-family-vol-13",
        "Title": "Spy x Family, Vol. 13",
        "TotalRating": 236,
        "WorkId": "0RDTATKHXHPS9"
      }
    },
    {
      "Book": {
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Tatsuya Endo"
          }
        ],
        "Contributors": "Tatsuya Endo",
        "CrossRevisionId": "0RDTATKH9HV0D",
        "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 452 chars; original string value]",
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781974748600",
        "Id": "0RDTATKH9HV0D",
        "ImageId": "0RPA2D9E8JJ8K",
        "IsContentSharingEnabled": true,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LovePointsPrice": 3200,
        "Price": {
          "Currency": "EUR",
          "Price": 6.24
        },
        "PublicationDate": "2024-08-06T00:00:00.0000000Z",
        "PublisherName": "VIZ Media",
        "Rating": 4.721925,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Platform": "Generic",
            "Size": 3941152,
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
          }
        ],
        "SeriesId": "0RDTATKG1HM0V",
        "SeriesName": "Spy x Family",
        "SeriesNumber": "12",
        "SeriesNumberFloat": 12,
        "Slug": "spy-x-family-vol-12",
        "Title": "Spy x Family, Vol. 12",
        "TotalRating": 187,
        "WorkId": "0RDTATKH9HV0D"
      }
    }
  ],
  "ItemsPerPage": 5,
  "TotalItemCount": 84,
  "TotalPageCount": 17,
  "VersionCode": 2
}
```


## 28. GET `/v1/user/recommendations` — device capture response

Source: `device:L19756`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_user_recommendations/response_L19756.json`.

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


## 29. GET `/v1/products/featured/` — device capture response

Source: `device:L19810`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `device/GET_v1_products_featured/response_L19810.json`.

```json
{
  "CurrentPageIndex": 0,
  "ItemCount": 18,
  "Items": [
    {
      "Id": "81844d90-8f90-40b0-98e4-f0cb6c3534e7",
      "Name": "Top 50 Books",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "1b49078a-87c3-49d1-9124-b00a5c242d6f",
      "Name": "Top 50 Audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "6230542a-b29c-4ccd-98bd-d091d0241a44",
      "Name": "Top Kobo Plus Books",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "5f08e984-b1c0-c28e-aab3-08dc44f596cd",
      "Name": "Colourful stories the way theyΓÇÖre meant to be seen",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "fc521346-a71d-c639-329e-08dabb85e7c5",
      "Name": "New releases you don't want to miss",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "d037e29d-052a-c92d-da3c-08dabc0dd8ed",
      "Name": "Fantastic fiction",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "0bd27bb0-0159-c3b8-3cd0-08dabc0e6802",
      "Name": "Guaranteed chills and thrills with these mysteries and thrillers",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "7c088551-070f-ccdf-1740-08dabc0eb416",
      "Name": "Romantic reads to fall in love with",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "4db8e924-6cf8-c7a3-e468-08dabc0ffed4",
      "Name": "Kobo Originals, books you won't read anywhere else",
      "ProductTypes": [
        "Book"
      ]
    },
    {
      "Id": "b1a7868b-69d5-cf71-bd21-08d4d9ec79f7",
      "Name": "New & Hot Audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "dd08675a-e472-c113-b74b-08d4eff7539b",
      "Name": "Start a new audiobook series",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "c4e4068d-f9cb-c60c-327d-08d62ae1a638",
      "Name": "Better life, better you",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "47218d05-b1e0-cf64-c055-08d84aa5aa4b",
      "Name": "The latest from GraphicAudio",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "a0c44b04-ff6d-c0e6-4345-08d63524c7bf",
      "Name": "Cinematic audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "fd08edf2-5e88-cfcb-d09c-08d609cdc1b1",
      "Name": "Staff Picks in Audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "aedad156-30ba-c295-8b38-08d6b12f51b8",
      "Name": "Enthralling audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "9ff082fd-0481-cce1-52b3-08d7cd37caa0",
      "Name": "Timeless classics now in audiobook",
      "ProductTypes": [
        "Audiobook"
      ]
    },
    {
      "Id": "b0d1b883-70ee-c833-4cb8-08d790600c26",
      "Name": "Coming soon to audiobooks",
      "ProductTypes": [
        "Audiobook"
      ]
    }
  ],
  "ItemsPerPage": 100,
  "TotalItemCount": 18,
  "TotalPageCount": 1,
  "VersionCode": 2
}
```


## 30. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L19913`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L19913.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Marco Baliani",
            "Role": "Narrator"
          },
          {
            "Name": "Milan Kundera",
            "Role": "Author"
          },
          {
            "Name": "Giuseppe Dierna",
            "Role": "Translator"
          }
        ],
        "Contributors": "Milan Kundera,Marco Baliani,Giuseppe Dierna",
        "CrossRevisionId": "c43427ce-fbd5-315a-988b-245d55a3635f",
        "Description": "<p>Si tratta sempre di amore, in queste storie. Ma un amore a cui si accompagna ogni volta la mistificazione, con effetto deflagrante. Il mondo va felicemente in pezzi sotto i nostri occhi, … [TRUNCATED 192 chars; original string value]",
        "Duration": 25132,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9788869869600",
        "Id": "638a88e7-4aff-4476-86fb-2570c94455c8",
        "ImageId": "0fc72229-131e-4a8e-b867-f469ed2185d2",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "it",
        "LifeCycleDates": {
          "ActivationDate": "2023-02-24T05:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "ita",
          "ScriptCode": ""
        },
        "LovePointsPrice": 4000,
        "Price": {
          "Currency": "EUR",
          "Price": 7.9
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2023-02-24T05:00:00.0000000Z",
        "PublisherName": "Emons Audiolibri",
        "Rating": 0,
        "RelatedGroupId": "a90dc454-1795-6fce-0000-000000000000",
        "Slug": "amori-ridicoli-1",
        "Title": "Amori ridicoli",
        "TotalBytes": 610999044,
        "TotalRating": 0,
        "WorkId": "c43427ce-fbd5-315a-988b-245d55a3635f"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10"
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Martina Levato",
            "Role": "Narrator"
          },
          {
            "Name": "Rebecca Yarros",
            "Role": "Author"
          }
        ],
        "Contributors": "Rebecca Yarros,Martina Levato",
        "CrossRevisionId": "1bcdd56c-d783-3fb5-8c33-f8b041540a5f",
        "Description": "<p>Dopo aver trascorso quasi diciotto mesi nellΓÇÖaccademia militare di Basgiath, Violet Sorrengail sa che non cΓÇÖ├¿ pi├╣ tempo per le lezioni. O per lΓÇÖincertezza. La guerra ├¿ davvero in… [TRUNCATED 324 chars; original string value]",
        "Duration": 90913,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9788820082352",
        "Id": "bba20f50-a6d0-4b09-b816-04651c720fbf",
        "ImageId": "41a3eb8e-7d9f-4e9d-8e27-4ecace3454b5",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "it",
        "LifeCycleDates": {
          "ActivationDate": "2025-01-22T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "ita",
          "ScriptCode": ""
        },
        "LovePointsPrice": 6800,
        "Price": {
          "Currency": "EUR",
          "Price": 14.99
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-01-22T00:00:00.0000000Z",
        "PublisherName": "SPERLING KUPFER",
        "Rating": 4.222222,
        "RelatedGroupId": "a91b524d-9aec-90b3-0000-000000000000",
        "SeriesId": "685dc169-a96f-51a2-b185-793b406cc96a",
        "SeriesName": "The Empyrean Series",
        "SeriesNumber": "3",
        "SeriesNumberFloat": 3,
        "SeriesSlug": "the-empyrean-series",
        "Slug": "onyx-storm-edizione-italiana-1",
        "Subtitle": "Edizione italiana",
        "Title": "Onyx Storm",
        "TotalBytes": 727318344,
        "TotalRating": 9,
        "WorkId": "1bcdd56c-d783-3fb5-8c33-f8b041540a5f"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "10"
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Alice Torriani",
            "Role": "Narrator"
          },
          {
            "Name": "Irene Di Liberto",
            "Role": "Author"
          }
        ],
        "Contributors": "Irene Di Liberto,Alice Torriani",
        "CrossRevisionId": "0199ffd1-db26-3990-abdb-14dd8b04cd88",
        "Description": "<p>Sicilia, inizio Novecento. A Castellatani gli uomini sono destinati a respirare la polvere dello zolfo. La grande miniera sulla collina ├¿ un dio crudele che divora i carusi e restituisce… [TRUNCATED 316 chars; original string value]",
        "Duration": 38246,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9788858554777",
        "Id": "39b0bdc5-d360-4258-b09e-639e5ec511cc",
        "ImageId": "64c9a81d-5fde-47d9-9036-179b64d81fd7",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "it",
        "LifeCycleDates": {
          "ActivationDate": "2026-06-17T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "ita",
          "ScriptCode": ""
        },
        "LovePointsPrice": 6000,
        "Price": {
          "Currency": "EUR",
          "Price": 12.99
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-06-17T00:00:00.0000000Z",
        "PublisherName": "PIEMME",
        "Rating": 0,
        "RelatedGroupId": "916512d2-54f9-69a5-0000-000000000000",
        "Slug": "la-zolfatara",
        "Title": "La zolfatara",
        "TotalBytes": 306021426,
        "TotalRating": 0,
        "WorkId": "0199ffd1-db26-3990-abdb-14dd8b04cd88"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Massimo De Lorenzo",
            "Role": "Narrator"
          },
          {
            "Name": "Jorge Luis Borges",
            "Role": "Author"
          }
        ],
        "Contributors": "Jorge Luis Borges,Massimo De Lorenzo",
        "CrossRevisionId": "09553d85-8216-3487-8847-466bc8cdb1e0",
        "Description": "<p>L'Aleph ├¿ una raccolta di racconti che prende il nome dall'ultima storia presente nella raccolta, in cui uno dei protagonisti descrive Aleph come \"il luogo dove si trovano, senza confond… [TRUNCATED 188 chars; original string value]",
        "Duration": 18911,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9788869868979",
        "Id": "06911015-0179-4a1d-96db-e9f47ab9a406",
        "ImageId": "017cd555-eab7-4a07-8a8b-6992fe3c8d4e",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "it",
        "LifeCycleDates": {
          "ActivationDate": "2022-08-19T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "ita",
          "ScriptCode": ""
        },
        "LovePointsPrice": 4000,
        "Price": {
          "Currency": "EUR",
          "Price": 7.9
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2022-08-19T00:00:00.0000000Z",
        "PublisherName": "Emons Audiolibri",
        "Rating": 5,
        "RelatedGroupId": "5bc8f19c-ba11-3d8e-0000-000000000000",
        "Slug": "l-aleph",
        "Title": "L'Aleph",
        "TotalBytes": 151308462,
        "TotalRating": 2,
        "WorkId": "09553d85-8216-3487-8847-466bc8cdb1e0"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 50,
  "TotalPageCount": 13,
  "VersionCode": 2
}
```


## 31. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20305`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20305.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Kirsten Potter",
            "Role": "Narrator"
          },
          {
            "Name": "Kimberly Farr",
            "Role": "Narrator"
          },
          {
            "Name": "Peter Ganim",
            "Role": "Narrator"
          },
          {
            "Name": "Catherine Ho",
            "Role": "Narrator"
          },
          {
            "Name": "James Aaron Oh",
            "Role": "Narrator"
          },
          {
            "Name": "Emily St. John Mandel",
            "Role": "Author"
          }
        ],
        "Contributors": "Emily St. John Mandel,Kirsten Potter,Kimberly Farr,Peter Ganim,Catherine Ho,James Aaron Oh",
        "CrossRevisionId": "26a11372-2281-3faa-9502-fc7ac0892b58",
        "Description": "<p><strong>The award-winning, bestselling author of <em>Station Eleven</em> and <em>Sea of Tranquility</em> returns with a breathtaking novel of doubles, shadow worlds, and fractured timelin… [TRUNCATED 313 chars; original string value]",
        "Duration": 44996,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798217406500",
        "Id": "9394b74c-9282-49e4-83bd-7d64006530e5",
        "ImageId": "7ee5b05e-f8be-4b76-9f50-a34b50da9356",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-15T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 9200,
        "Price": {
          "Currency": "EUR",
          "Price": 21.42
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-15T00:00:00.0000000Z",
        "PublisherName": "Penguin Random House Audio Publishing Group",
        "Rating": 0,
        "RelatedGroupId": "24c845fa-22d9-a97c-0000-000000000000",
        "Slug": "exit-party-5",
        "Subtitle": "A Novel",
        "Title": "Exit Party",
        "TotalBytes": 359976972,
        "TotalRating": 0,
        "WorkId": "26a11372-2281-3faa-9502-fc7ac0892b58"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Rebecca Soler",
            "Role": "Narrator"
          },
          {
            "Name": "MacLeod Andrews",
            "Role": "Narrator"
          },
          {
            "Name": "Julia Whelan",
            "Role": "Narrator"
          },
          {
            "Name": "Jodi Picoult",
            "Role": "Narrator"
          },
          {
            "Name": "Jodi Picoult",
            "Role": "Author"
          }
        ],
        "Contributors": "Jodi Picoult,Rebecca Soler,MacLeod Andrews,Julia Whelan,Jodi Picoult",
        "CrossRevisionId": "81316695-479b-392a-885e-1a7b79e5569f",
        "Description": "<p><strong>From the #1 <em>New York Times</em> bestselling author of <em>By Any Other Name</em> comes a riveting novel about the risks we take to protect the ones we love in a world where cr… [TRUNCATED 311 chars; original string value]",
        "Duration": 56382,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798217404483",
        "Id": "6abdbf44-d487-4f68-ad5a-9423a8f2408e",
        "ImageId": "1aa2371c-45be-4f16-bb2c-81da5fc66bae",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-15T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 10800,
        "Price": {
          "Currency": "EUR",
          "Price": 24.99
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-15T00:00:00.0000000Z",
        "PublisherName": "Penguin Random House Audio Publishing Group",
        "Rating": 0,
        "RelatedGroupId": "44ce2e9a-f680-22bd-0000-000000000000",
        "Slug": "hollow-bones-10",
        "Subtitle": "A Novel",
        "Title": "Hollow Bones",
        "TotalBytes": 451073810,
        "TotalRating": 0,
        "WorkId": "81316695-479b-392a-885e-1a7b79e5569f"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Victoria Villarreal",
            "Role": "Narrator"
          },
          {
            "Name": "Isabel Allende",
            "Role": "Author"
          }
        ],
        "Contributors": "Isabel Allende,Victoria Villarreal",
        "CrossRevisionId": "cbbf201c-7b7a-38d7-8baf-ad2122a525d3",
        "Description": "<p><strong>A profound exploration of the art and magic of writing by <em>New York Times</em> bestselling author Isabel Allende.</strong></p><p>In this deeply personal and insightful book, Is… [TRUNCATED 308 chars; original string value]",
        "Duration": 16381,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798217404582",
        "Id": "892850e9-1608-43dc-bfcd-216eddc163c9",
        "ImageId": "c1f576d6-4156-4148-b0d1-7bdeac811dd5",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-15T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 6000,
        "Price": {
          "Currency": "EUR",
          "Price": 13.39
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-15T00:00:00.0000000Z",
        "PublisherName": "Penguin Random House Audio Publishing Group",
        "Rating": 0,
        "RelatedGroupId": "1c02082c-161b-b2c2-0000-000000000000",
        "Slug": "story-telling-4",
        "Subtitle": "A Writing Life",
        "Title": "Story Telling",
        "TotalBytes": 131062380,
        "TotalRating": 0,
        "WorkId": "cbbf201c-7b7a-38d7-8baf-ad2122a525d3"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Andrew D. Huberman, Ph.D.",
            "Role": "Narrator"
          },
          {
            "Name": "Andrew D. Huberman, Ph.D.",
            "Role": "Author"
          }
        ],
        "Contributors": "Andrew D. Huberman, Ph.D.,Andrew D. Huberman, Ph.D.",
        "CrossRevisionId": "e778c5a6-9e89-39ec-ab0a-f6b8ceabb0e0",
        "Description": "<p><strong>Andrew Huberman, host of the worldΓÇÖs leading health and science podcast <em>Huberman Lab</em> and neuroscientist and professor at Stanford School of Medicine, presents an essent… [TRUNCATED 311 chars; original string value]",
        "Duration": 65134,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781797182919",
        "Id": "7a2ce24e-6212-4665-af7c-60c0fde008c0",
        "ImageId": "cc30be14-0314-40e1-9d5b-3feddea02f5c",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-15T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 13200,
        "Price": {
          "Currency": "EUR",
          "Price": 31.23
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-15T00:00:00.0000000Z",
        "PublisherName": "Simon & Schuster Audio",
        "Rating": 0,
        "RelatedGroupId": "61de8caa-3a7c-e6e4-0000-000000000000",
        "Slug": "protocols-2",
        "Subtitle": "An Operating Manual for the Human Body",
        "Title": "Protocols",
        "TotalBytes": 521121150,
        "TotalRating": 0,
        "WorkId": "e778c5a6-9e89-39ec-ab0a-f6b8ceabb0e0"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 167,
  "TotalPageCount": 42,
  "VersionCode": 2
}
```


## 32. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20361`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20361.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Samantha Brentmoor",
            "Role": "Narrator"
          },
          {
            "Name": "Jason Clarke",
            "Role": "Narrator"
          },
          {
            "Name": "Devney Perry",
            "Role": "Author"
          }
        ],
        "Contributors": "Devney Perry,Samantha Brentmoor,Jason Clarke",
        "CrossRevisionId": "1a3277ee-3648-3afb-9682-8c9a57506d79",
        "Description": "<p><strong>Now in development as a feature film with Amazon MGM Studios, with <em>John Wick</em> screenwriter Derek Kolstad attached to write the script!</strong></p><p><strong><em>Shield of… [TRUNCATED 310 chars; original string value]",
        "Duration": 71504,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798331924867",
        "Id": "b0358ef9-1ac6-4fc9-9ee1-c88c5199e3da",
        "ImageId": "b381e31c-77e1-4383-b046-dba404ea635c",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2025-05-06T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 11600,
        "Price": {
          "Currency": "EUR",
          "Price": 27.66
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-05-06T00:00:00.0000000Z",
        "PublisherName": "Tantor Media, Inc",
        "Rating": 4,
        "RelatedGroupId": "57a6cab8-78b3-b8e2-0000-000000000000",
        "SeriesId": "a4a43771-dd4f-5211-9668-165b941e20bd",
        "SeriesName": "Shield of Sparrows",
        "SeriesSlug": "shield-of-sparrows",
        "Slug": "shield-of-sparrows-1",
        "Title": "Shield of Sparrows",
        "TotalBytes": 572075224,
        "TotalRating": 14,
        "WorkId": "1a3277ee-3648-3afb-9682-8c9a57506d79"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Simon Vance",
            "Role": "Narrator"
          },
          {
            "Name": "Mark Dawson",
            "Role": "Author"
          }
        ],
        "Contributors": "Mark Dawson,Simon Vance",
        "CrossRevisionId": "bee1a629-a2cd-33e1-86d3-d5f5706c8248",
        "Description": "<p><strong>Four murders. Two detectives. One mystifying crime.</strong></p><p>On Christmas Eve, DCI Mackenzie Jones is called to a shooting at a remote farmhouse. Ralph Mallender believes hi… [TRUNCATED 308 chars; original string value]",
        "Duration": 35831,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781705228548",
        "Id": "40022698-c35e-4224-b00f-92b584ac1ce1",
        "ImageId": "10742dcb-e881-4131-804d-639e5c444b17",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2020-05-19T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 9600,
        "Price": {
          "Currency": "EUR",
          "Price": 22.31
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2020-05-19T00:00:00.0000000Z",
        "PublisherName": "Tantor Media, Inc",
        "Rating": 4.37037,
        "RelatedGroupId": "8f141198-c50b-b54e-0000-000000000000",
        "SeriesId": "c0b97b98-1654-59cd-ac69-b25ff1e51c29",
        "SeriesName": "Atticus Priest",
        "SeriesSlug": "atticus-priest",
        "Slug": "the-house-in-the-woods-3",
        "Title": "The House in the Woods",
        "TotalBytes": 286646436,
        "TotalRating": 27,
        "WorkId": "bee1a629-a2cd-33e1-86d3-d5f5706c8248"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Winston James",
            "Role": "Narrator"
          },
          {
            "Name": "Alyx Monroe",
            "Role": "Narrator"
          },
          {
            "Name": "Love Belvin",
            "Role": "Author"
          }
        ],
        "Contributors": "Love Belvin,Winston James,Alyx Monroe",
        "CrossRevisionId": "5d15bddc-45e3-3932-bc9f-b66884b8949a",
        "Description": "<p>He's an Oxonian, licensed therapist, and man of faith, preparing to take over the largest Pentecostal church in New York.</p><p>She's an around-the-way girl from Harlem, struggling with t… [TRUNCATED 250 chars; original string value]",
        "Duration": 44386,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798855597240",
        "Id": "4cb4e549-8a0d-4344-8605-2abcf92f0881",
        "ImageId": "f3090613-6f2f-459c-a80e-bf906c85d456",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2025-05-20T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 10000,
        "Price": {
          "Currency": "EUR",
          "Price": 23.2
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-05-20T00:00:00.0000000Z",
        "PublisherName": "Tantor Media, Inc",
        "Rating": 5,
        "RelatedGroupId": "3a10dff2-c6e0-e164-0000-000000000000",
        "SeriesId": "7a52044f-65d3-5151-9077-1fca3cd770a3",
        "SeriesName": "Love Unaccounted",
        "SeriesSlug": "love-unaccounted",
        "Slug": "in-covenant-with-ezra",
        "Title": "In Covenant with Ezra",
        "TotalBytes": 355095525,
        "TotalRating": 1,
        "WorkId": "5d15bddc-45e3-3932-bc9f-b66884b8949a"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Paul Woodson",
            "Role": "Narrator"
          },
          {
            "Name": "Keira Montclair",
            "Role": "Author"
          }
        ],
        "Contributors": "Keira Montclair,Paul Woodson",
        "CrossRevisionId": "76e36c3b-1d3b-3cc0-9778-f4d99c7ebbf3",
        "Description": "<p><strong>First in a beloved multi-generational saga of two Scottish clans that spans over forty books.</strong></p><p>An abused woman. A fierce Highlander. Is he strong enough to battle th… [TRUNCATED 307 chars; original string value]",
        "Duration": 31534,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798331960247",
        "Id": "9e3b958e-5952-4dd2-b4bf-483c4422f863",
        "ImageId": "c03000db-ee57-46da-812d-05057705c1e5",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2025-06-10T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 8400,
        "Price": {
          "Currency": "EUR",
          "Price": 18.74
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-06-10T00:00:00.0000000Z",
        "PublisherName": "Tantor Media, Inc",
        "Rating": 0,
        "RelatedGroupId": "973f547d-aea0-9767-0000-000000000000",
        "SeriesId": "e167df78-d0ed-5c19-92d1-d323d643b29b",
        "SeriesName": "Clan Grant",
        "SeriesSlug": "clan-grant",
        "Slug": "rescued-by-a-highlander-4",
        "Title": "Rescued by a Highlander",
        "TotalBytes": 252292209,
        "TotalRating": 0,
        "WorkId": "76e36c3b-1d3b-3cc0-9778-f4d99c7ebbf3"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 169,
  "TotalPageCount": 43,
  "VersionCode": 2
}
```


## 33. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20417`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20417.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Andrew D. Huberman, Ph.D.",
            "Role": "Narrator"
          },
          {
            "Name": "Andrew D. Huberman, Ph.D.",
            "Role": "Author"
          }
        ],
        "Contributors": "Andrew D. Huberman, Ph.D.,Andrew D. Huberman, Ph.D.",
        "CrossRevisionId": "e778c5a6-9e89-39ec-ab0a-f6b8ceabb0e0",
        "Description": "<p><strong>Andrew Huberman, host of the worldΓÇÖs leading health and science podcast <em>Huberman Lab</em> and neuroscientist and professor at Stanford School of Medicine, presents an essent… [TRUNCATED 311 chars; original string value]",
        "Duration": 65134,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781797182919",
        "Id": "7a2ce24e-6212-4665-af7c-60c0fde008c0",
        "ImageId": "cc30be14-0314-40e1-9d5b-3feddea02f5c",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-15T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 13200,
        "Price": {
          "Currency": "EUR",
          "Price": 31.23
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-15T00:00:00.0000000Z",
        "PublisherName": "Simon & Schuster Audio",
        "Rating": 0,
        "RelatedGroupId": "61de8caa-3a7c-e6e4-0000-000000000000",
        "Slug": "protocols-2",
        "Subtitle": "An Operating Manual for the Human Body",
        "Title": "Protocols",
        "TotalBytes": 521121150,
        "TotalRating": 0,
        "WorkId": "e778c5a6-9e89-39ec-ab0a-f6b8ceabb0e0"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Priya Parker",
            "Role": "Narrator"
          },
          {
            "Name": "Priya Parker",
            "Role": "Author"
          }
        ],
        "Contributors": "Priya Parker,Priya Parker",
        "CrossRevisionId": "82e82c63-2374-3bd4-b16f-ecfc6a1294db",
        "Description": "<p>**AN INSTANT <em>NEW YORK TIMES</em> BESTSELLER</p><p>FEATURED AT NBCΓÇÖS TODAY SHOW, <em>NEW YORK TIMES MAGAZINE</em>, <em>THE WALL STREET JOURNAL</em>, AND MANY MORE</p><p>ΓÇ£This is no… [TRUNCATED 322 chars; original string value]",
        "Duration": 36230,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9798217352876",
        "Id": "9ed66439-2769-4b81-91e9-10134e5b582a",
        "ImageId": "de4eb8dc-9295-47b8-a344-ba04a0b9f157",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-08T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 8400,
        "Price": {
          "Currency": "EUR",
          "Price": 19.64
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-08T00:00:00.0000000Z",
        "PublisherName": "Penguin Random House Audio Publishing Group",
        "Rating": 0,
        "RelatedGroupId": "073a1ec2-6dcd-6c8f-0000-000000000000",
        "Slug": "the-art-of-fighting-3",
        "Subtitle": "The Transformative Power of Conflict",
        "Title": "The Art of Fighting",
        "TotalBytes": 289854825,
        "TotalRating": 0,
        "WorkId": "82e82c63-2374-3bd4-b16f-ecfc6a1294db"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Jim Curtis",
            "Role": "Narrator"
          },
          {
            "Name": "Jim Curtis",
            "Role": "Author"
          }
        ],
        "Contributors": "Jim Curtis,Jim Curtis",
        "CrossRevisionId": "c648769f-7d30-3fff-a829-4689917b51f0",
        "Description": "<p><strong>For fans of <em>Manifest</em> and <em>DonΓÇÖt Believe Everything You Think</em>, a practical and compassionate guide to healing from transformational coach Jim Curtis, using decad… [TRUNCATED 313 chars; original string value]",
        "Duration": 30447,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781668189528",
        "Id": "a10ba9b2-703a-4452-b50f-6fe873da4406",
        "ImageId": "90f338d3-064e-44bd-8e48-3ebeb7ea8462",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-08T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 10400,
        "Price": {
          "Currency": "EUR",
          "Price": 24.09
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-08T00:00:00.0000000Z",
        "PublisherName": "Simon & Schuster Audio",
        "Rating": 0,
        "RelatedGroupId": "5227a630-3fc2-86a6-0000-000000000000",
        "Slug": "book-of-possibility",
        "Title": "Book of Possibility",
        "TotalBytes": 243594247,
        "TotalRating": 0,
        "WorkId": "c648769f-7d30-3fff-a829-4689917b51f0"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Rob Reiner",
            "Role": "Narrator"
          },
          {
            "Name": "Christopher Guest",
            "Role": "Narrator"
          },
          {
            "Name": "Michael McKean",
            "Role": "Narrator"
          },
          {
            "Name": "Harry Shearer",
            "Role": "Narrator"
          },
          {
            "Name": "Rob Reiner",
            "Role": "Author"
          },
          {
            "Name": "Christopher Guest",
            "Role": "Author"
          },
          {
            "Name": "Michael McKean",
            "Role": "Author"
          },
          {
            "Name": "Harry Shearer",
            "Role": "Author"
          }
        ],
        "Contributors": "Rob Reiner,Rob Reiner,Christopher Guest,Michael McKean,Harry Shearer,Christopher Guest,Michael McKean,Harry Shearer",
        "CrossRevisionId": "6df79d3a-111f-31f3-810d-f8f8106b36ce",
        "Description": "<p><strong>A 2026 Audie Award Finalist for Nonfiction</strong></p><p>**<em>NEW YORK TIMES</em> BESTSELLER</p><p>For the first time, director Rob Reiner and cocreators Christopher Guest, Mich… [TRUNCATED 307 chars; original string value]",
        "Duration": 26769,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781668123904",
        "Id": "03b9e096-365a-4136-bdb5-4e19d64e79a4",
        "ImageId": "7707f9d7-9872-44b4-9999-bf5f5d699ab8",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2025-09-09T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 9600,
        "Price": {
          "Currency": "EUR",
          "Price": 22.31
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-09-09T00:00:00.0000000Z",
        "PublisherName": "Simon & Schuster Audio",
        "Rating": 0,
        "RelatedGroupId": "d8d880ce-3ad1-bd8f-0000-000000000000",
        "Slug": "a-fine-line-between-stupid-and-clever",
        "Subtitle": "The Story of Spinal Tap",
        "Title": "A Fine Line Between Stupid and Clever",
        "TotalBytes": 214179068,
        "TotalRating": 0,
        "WorkId": "6df79d3a-111f-31f3-810d-f8f8106b36ce"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 252,
  "TotalPageCount": 63,
  "VersionCode": 2
}
```


## 34. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20527`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20527.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Lesley Manville",
            "Role": "Narrator"
          },
          {
            "Name": "Richard Osman",
            "Role": "Narrator"
          },
          {
            "Name": "Marian Keyes",
            "Role": "Narrator"
          },
          {
            "Name": "Richard Osman",
            "Role": "Author"
          }
        ],
        "Contributors": "Richard Osman,Lesley Manville,Richard Osman,Marian Keyes",
        "CrossRevisionId": "7e2af4d2-f65b-3cbd-b78d-44da15a4f4d2",
        "Description": "<p><strong>THE FIRST NOVEL IN THE RECORD-BREAKING, MILLION-COPY BESTSELLING THURSDAY MURDER CLUB SERIES AND SOON TO BE A MAJOR NETFLIX MOVIE RELEASE!</strong></p><p><strong>Featuring an excl… [TRUNCATED 307 chars; original string value]",
        "Duration": 44709,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9780241988299",
        "Id": "58a5cdfb-11ba-4710-9f69-4143f60dd577",
        "ImageId": "e500b5a6-276b-4541-8257-2e92eee22948",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2020-09-03T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 8800,
        "Price": {
          "Currency": "EUR",
          "Price": 20.74
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2020-09-03T00:00:00.0000000Z",
        "PublisherName": "Penguin Books Ltd",
        "Rating": 4.407407,
        "RelatedGroupId": "30aca1d5-cd34-82c2-0000-000000000000",
        "SeriesId": "90fe0ce7-06c9-52e4-a366-fe419177b2a1",
        "SeriesName": "The Thursday Murder Club",
        "SeriesSlug": "the-thursday-murder-club-1",
        "Slug": "the-thursday-murder-club-1",
        "Subtitle": "The first novel in the multi-million copy bestselling murder mystery series",
        "Title": "The Thursday Murder Club",
        "TotalBytes": 357746990,
        "TotalRating": 189,
        "WorkId": "7e2af4d2-f65b-3cbd-b78d-44da15a4f4d2"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Tor Thom",
            "Role": "Narrator"
          },
          {
            "Name": "Rachel Reid",
            "Role": "Author"
          }
        ],
        "Contributors": "Rachel Reid,Tor Thom",
        "CrossRevisionId": "dfa83752-dcd5-346b-8118-9327b7f816dd",
        "Description": "<p><strong>Nothing interferes with Shane Hollander's gameΓÇödefinitely not the sexy rival he loves to hate.</strong></p><p>Pro hockey star Shane Hollander isn't just crazy talented, he's got… [TRUNCATED 310 chars; original string value]",
        "Duration": 34355,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781541431638",
        "Id": "244d67ea-7d20-4363-bba4-6b63072b8dfa",
        "ImageId": "ea6cadf3-3a95-42d9-a057-4260d09bf6ac",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2019-10-22T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 8400,
        "Price": {
          "Currency": "EUR",
          "Price": 18.74
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2019-10-22T00:00:00.0000000Z",
        "PublisherName": "Tantor Media, Inc",
        "Rating": 4.461538,
        "RelatedGroupId": "97581d87-264d-8ec7-0000-000000000000",
        "SeriesId": "470aa4bb-0255-5d5c-9d29-bfe89f448fa3",
        "SeriesName": "Game Changers: Heated Rivalry Book Series",
        "SeriesSlug": "game-changers-heated-rivalry-book-series-1",
        "Slug": "heated-rivalry-3",
        "Title": "Heated Rivalry",
        "TotalBytes": 274837625,
        "TotalRating": 26,
        "WorkId": "dfa83752-dcd5-346b-8118-9327b7f816dd"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Marin Ireland",
            "Role": "Narrator"
          },
          {
            "Name": "Michael Urie",
            "Role": "Narrator"
          },
          {
            "Name": "Shelby Van Pelt",
            "Role": "Author"
          }
        ],
        "Contributors": "Shelby Van Pelt,Marin Ireland,Michael Urie",
        "CrossRevisionId": "2c348c5d-4a82-3716-98ba-72d850ff6dc4",
        "Description": "<p><strong>This audio edition is read by Marin Ireland with Michael Urie as the voice of Marcellus.</strong></p><p><strong>An Audie Award Finalist - Audiobook of the Year. *</strong> <strong… [TRUNCATED 310 chars; original string value]",
        "Duration": 40611,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9780063204188",
        "Id": "5c09f23b-8375-41fb-979b-9603e039482e",
        "ImageId": "31110303-2dbf-467c-bb29-ef8ac193f21c",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2022-05-03T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 10000,
        "Price": {
          "Currency": "EUR",
          "Price": 23.91
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2022-05-03T00:00:00.0000000Z",
        "PublisherName": "HarperCollins",
        "Rating": 4.554622,
        "RelatedGroupId": "f6ee0d41-c106-8f9a-0000-000000000000",
        "Slug": "remarkably-bright-creatures-1",
        "Subtitle": "A Novel",
        "Title": "Remarkably Bright Creatures",
        "TotalBytes": 324929300,
        "TotalRating": 119,
        "WorkId": "2c348c5d-4a82-3716-98ba-72d850ff6dc4"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Selena Simmons",
            "Role": "Narrator"
          },
          {
            "Name": "Emily Bront├½",
            "Role": "Author"
          }
        ],
        "Contributors": "Emily Bront├½,Selena Simmons",
        "CrossRevisionId": "f84c22fd-b85f-332d-b8b3-addd544000d5",
        "Description": "<p>Wuthering Heights is Emily Bront├½'s only novel. It was first published in 1847 under the pseudonym Ellis Bell, and a posthumous second edition was edited by her sister Charlotte. The nam… [TRUNCATED 311 chars; original string value]",
        "Duration": 36390,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9782291963318",
        "Id": "7216dfea-739f-4790-b313-ff8f280a2259",
        "ImageId": "6b42c6c3-06ab-4e9b-8ab7-ff0b056d62d4",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2024-06-11T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 2400,
        "Price": {
          "Currency": "EUR",
          "Price": 0.49
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2024-06-11T00:00:00.0000000Z",
        "PublisherName": "NTMC",
        "Rating": 4.333333,
        "RelatedGroupId": "7c15cd88-023a-3679-0000-000000000000",
        "Slug": "wuthering-heights-554",
        "Title": "Wuthering Heights",
        "TotalBytes": 404855559,
        "TotalRating": 3,
        "WorkId": "f84c22fd-b85f-332d-b8b3-addd544000d5"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 196,
  "TotalPageCount": 49,
  "VersionCode": 2
}
```


## 35. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20583`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20583.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Cory Doctorow",
            "Role": "Narrator"
          },
          {
            "Name": "Cory Doctorow",
            "Role": "Author"
          }
        ],
        "Contributors": "Cory Doctorow,Cory Doctorow",
        "CrossRevisionId": "ddcde401-ade9-358f-a220-cde45f377c1c",
        "Description": "<p><strong>A short, provocative guide to what's good, bad, and stupid about AI and the discourse around AI, by the author of <em>Enshittification</em>.</strong> This program is read by the a… [TRUNCATED 310 chars; original string value]",
        "Duration": 19699,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781250472007",
        "Id": "4e498e0d-b577-4b26-888a-14199528f96c",
        "ImageId": "e64015c8-16d4-4162-bba6-984235195688",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-06-23T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 8800,
        "Price": {
          "Currency": "EUR",
          "Price": 20.52
        },
        "PromoCodeAllowed": false,
        "PublicationDate": "2026-06-23T00:00:00.0000000Z",
        "PublisherName": "Macmillan Audio",
        "Rating": 5,
        "RelatedGroupId": "199ba7f4-0e94-96f8-0000-000000000000",
        "Slug": "the-reverse-centaur-s-guide-to-life-after-ai-1",
        "Subtitle": "How to Think About Artificial IntelligenceΓÇöBefore It's Too Late",
        "Title": "The Reverse Centaur's Guide to Life After AI",
        "TotalBytes": 157602232,
        "TotalRating": 1,
        "WorkId": "ddcde401-ade9-358f-a220-cde45f377c1c"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Christine Lakin",
            "Role": "Narrator"
          },
          {
            "Name": "Madeline Cash",
            "Role": "Author"
          }
        ],
        "Contributors": "Madeline Cash,Christine Lakin",
        "CrossRevisionId": "2cc89b85-911e-39e3-9053-716d890ef5fb",
        "Description": "<p><strong>\"Christine Lakin's reading is terrific. Her narration is wonderfully deadpan...and her characterization is pure ear candy. Lakin grants each character complexity and humanity...\" … [TRUNCATED 319 chars; original string value]",
        "Duration": 32580,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781250435958",
        "Id": "4f32528f-a491-4297-bf2b-5421199d1749",
        "ImageId": "271b4716-fc27-4f6f-bb49-53aa6e9f2991",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-01-13T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 10400,
        "Price": {
          "Currency": "EUR",
          "Price": 24.09
        },
        "PromoCodeAllowed": false,
        "PublicationDate": "2026-01-13T00:00:00.0000000Z",
        "PublisherName": "Macmillan Audio",
        "Rating": 4.25,
        "RelatedGroupId": "e4ec77eb-fa27-5a1f-0000-000000000000",
        "Slug": "lost-lambs-1",
        "Subtitle": "A Novel",
        "Title": "Lost Lambs",
        "TotalBytes": 260664958,
        "TotalRating": 4,
        "WorkId": "2cc89b85-911e-39e3-9053-716d890ef5fb"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Christine Lakin",
            "Role": "Narrator"
          },
          {
            "Name": "Madeline Cash",
            "Role": "Author"
          }
        ],
        "Contributors": "Madeline Cash,Christine Lakin",
        "CrossRevisionId": "26d6f81a-4b5d-3f16-9187-c05d7e9fb3cd",
        "Description": "<p><strong>Brought to you by Penguin.</strong></p><p><strong>Think your family is dysfunctional? Meet the Flynns.</strong></p><p>For the three Flynn daughters, itΓÇÖs been disastrous since t… [TRUNCATED 314 chars; original string value]",
        "Duration": 32583,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781529970395",
        "Id": "c8e4fa67-4679-40a3-b1ad-cc649cd37c74",
        "ImageId": "acc8b2b6-1a16-42cb-95a7-1df1667a2301",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-02-05T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 7200,
        "Price": {
          "Currency": "EUR",
          "Price": 16.13
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-02-05T00:00:00.0000000Z",
        "PublisherName": "Transworld",
        "Rating": 0,
        "RelatedGroupId": "4abef1c2-807e-5269-0000-000000000000",
        "Slug": "lost-lambs-4",
        "Subtitle": "The Sunday Times Bestseller ΓÇô 2026ΓÇÖs standout debut, a funny, sharp, dysfunctional-family novel",
        "Title": "Lost Lambs",
        "TotalBytes": 260683317,
        "TotalRating": 0,
        "WorkId": "26d6f81a-4b5d-3f16-9187-c05d7e9fb3cd"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Kristen Sieh",
            "Role": "Narrator"
          },
          {
            "Name": "Kate Folk",
            "Role": "Author"
          }
        ],
        "Contributors": "Kate Folk,Kristen Sieh",
        "CrossRevisionId": "ecf8d638-f2cf-38dc-aaa5-2f58fdbe8aa1",
        "Description": "<p>A BOOK OF THE YEAR IN <em>TIME</em>, <em>VOX</em>, AND <em>VULTURE</em></p><p>'Batty and brilliant'<br /><em><strong>THE TIMES</strong></em></p><p>'Truly original . . . deeply weird, deep… [TRUNCATED 302 chars; original string value]",
        "Duration": 33807,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781529372694",
        "Id": "9be80921-0ccc-49bd-9c5f-0b84faed190b",
        "ImageId": "efce7785-519f-4190-954a-166f54147c5f",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2025-04-08T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "LovePointsPrice": 12000,
        "Price": {
          "Currency": "EUR",
          "Price": 28.56
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-04-08T00:00:00.0000000Z",
        "PublisherName": "Hodder & Stoughton",
        "Rating": 0,
        "RelatedGroupId": "fce91969-ece7-3a87-0000-000000000000",
        "Slug": "sky-daddy-4",
        "Subtitle": "'Truly original, deeply weird' - Daily Telegraph",
        "Title": "Sky Daddy",
        "TotalBytes": 270476026,
        "TotalRating": 0,
        "WorkId": "ecf8d638-f2cf-38dc-aaa5-2f58fdbe8aa1"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 198,
  "TotalPageCount": 50,
  "VersionCode": 2
}
```


## 36. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20639`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20639.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Stephen Noir",
            "Role": "Narrator"
          },
          {
            "Name": "Stephen Noir",
            "Role": "Author"
          }
        ],
        "Contributors": "Stephen Noir,Stephen Noir",
        "CrossRevisionId": "c1bba774-8058-3766-84ef-88cd8a95f460",
        "Description": "<p><strong>Egypt, 1926. A tomb sealed for three thousand years. A curse that only kills the guilty.</strong></p><p>When epigraphist Cecily Am helps break the seal on an untouched royal tomb … [TRUNCATED 308 chars; original string value]",
        "Duration": 31194,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "1230010324844",
        "Id": "a6dd43ac-01d8-4046-94e9-e92294c94de9",
        "ImageId": "1660afa2-e4fd-4d7a-9ef7-1126c57301f8",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-01T08:00:00.0000000Z"
        },
        "LovePointsPrice": 2400,
        "Price": {
          "Currency": "EUR",
          "Price": 3
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-01T00:00:00.0000000Z",
        "PublisherName": "Stephen Noir",
        "Rating": 0,
        "RelatedGroupId": "3ef918b0-0bee-1401-0000-000000000000",
        "Slug": "the-hieroglyph-murders-1",
        "Title": "The Hieroglyph Murders",
        "TotalBytes": 187161840,
        "TotalRating": 0,
        "WorkId": "c1bba774-8058-3766-84ef-88cd8a95f460"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Lawrence Thunell",
            "Role": "Narrator"
          },
          {
            "Name": "C.N. Steinhour",
            "Role": "Author"
          }
        ],
        "Contributors": "C.N. Steinhour,Lawrence Thunell",
        "CrossRevisionId": "363e6cb8-f5d4-3972-89d0-5556e5983802",
        "Description": "<p><strong>Silver winner of the 2026 IBPA Book Award for Children's/Young Adult Audiobook</strong></p><p><strong>\"As I drop to my knees beside him, I feel a spark of recognition: Tristan Che… [TRUNCATED 307 chars; original string value]",
        "Duration": 29228,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781737106845",
        "Id": "5a959e50-93fc-46eb-8832-cfb4dafa7a79",
        "ImageId": "8d3bbd56-949a-4ae4-b680-b8e9cd7350ba",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-08-27T08:00:00.0000000Z"
        },
        "LovePointsPrice": 8400,
        "Price": {
          "Currency": "EUR",
          "Price": 19
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2025-11-26T00:00:00.0000000Z",
        "PublisherName": "Hidden Shield Publishing",
        "Rating": 0,
        "RelatedGroupId": "5fea5c06-0c73-7af2-0000-000000000000",
        "Slug": "the-lifesaver-2",
        "Title": "The Lifesaver",
        "TotalBytes": 233845095,
        "TotalRating": 0,
        "WorkId": "363e6cb8-f5d4-3972-89d0-5556e5983802"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Meridith Gibbens",
            "Role": "Narrator"
          },
          {
            "Name": "Meridith Gibbens",
            "Role": "Author"
          }
        ],
        "Contributors": "Meridith Gibbens,Meridith Gibbens",
        "CrossRevisionId": "7ddb3135-d39b-39c6-be17-94ef35af4a91",
        "Description": "<p><em>She holds a secret powerful men will kill for.</em></p><p><em>He disappeared twelve years ago without a trace.</em></p><p><em>Now fate has brought them back togetherΓÇª if they surviv… [TRUNCATED 308 chars; original string value]",
        "Duration": 37048,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "1230010314852",
        "Id": "d49f0e9d-d8fc-498a-8b15-7160b22cfb9d",
        "ImageId": "0ddbb6d1-da54-48f3-941f-95ad157b397e",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-08-29T09:00:00.0000000Z"
        },
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 17.99
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-08-29T00:00:00.0000000Z",
        "PublisherName": "Meridith Gibbens Books",
        "Rating": 0,
        "RelatedGroupId": "0ae78007-2dc3-0e58-0000-000000000000",
        "SeriesId": "4b82af37-ecae-5ee4-bec4-46cc4bd332ea",
        "SeriesName": "The Argent Code",
        "SeriesNumber": "1",
        "SeriesNumberFloat": 1,
        "SeriesSlug": "the-argent-code",
        "Slug": "code-compromised",
        "Title": "Code Compromised",
        "TotalBytes": 296394824,
        "TotalRating": 0,
        "WorkId": "7ddb3135-d39b-39c6-be17-94ef35af4a91"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Michael Ferraiuolo",
            "Role": "Narrator"
          },
          {
            "Name": "Nick J. Russo",
            "Role": "Narrator"
          },
          {
            "Name": "Alexa Piper",
            "Role": "Author"
          }
        ],
        "Contributors": "Alexa Piper,Michael Ferraiuolo,Nick J. Russo",
        "CrossRevisionId": "de34692d-2567-3111-b2b3-a2eb607e9f2e",
        "Description": "<p>Leopold has been searching for the supernatural for most of his life. What he finds is not as mystical as he first thought. There are classes and tests, and more bureaucracy than wonder.<… [TRUNCATED 308 chars; original string value]",
        "Duration": 28318,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "1230010137963",
        "Id": "ef8293e5-af72-40cd-9572-a0609bee2013",
        "ImageId": "d74696fd-db65-4ced-bdf5-351d580c8a09",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-08-28T04:00:00.0000000Z"
        },
        "LovePointsPrice": 8000,
        "Price": {
          "Currency": "EUR",
          "Price": 17.99
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-08-28T00:00:00.0000000Z",
        "PublisherName": "Alexa Piper",
        "Rating": 0,
        "RelatedGroupId": "35aedcaf-41b7-29d1-0000-000000000000",
        "SeriesId": "6191ac88-d2b0-58f2-8449-7b310ad63bf6",
        "SeriesName": "Phoenix Immortal: Hive",
        "SeriesNumber": "1",
        "SeriesNumberFloat": 1,
        "SeriesSlug": "phoenix-immortal-hive-1",
        "Slug": "five-to-love-him-1",
        "Title": "Five to Love Him",
        "TotalBytes": 226571032,
        "TotalRating": 0,
        "WorkId": "de34692d-2567-3111-b2b3-a2eb607e9f2e"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 90,
  "TotalPageCount": 23,
  "VersionCode": 2
}
```


## 37. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20695`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20695.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Lauren Baldwin",
            "Role": "Narrator"
          },
          {
            "Name": "Lewis Carroll",
            "Role": "Author"
          }
        ],
        "Contributors": "Lewis Carroll,Lauren Baldwin",
        "CrossRevisionId": "e1191bf2-8605-3490-85b4-824d708f71c2",
        "Description": "<p>Lewis CarrollΓÇÖs beloved classic is literary nonsense at its finest and has delighted readers since it was originally published in 1865.</p><p>After tumbling down the rabbit hole while c… [TRUNCATED 311 chars; original string value]",
        "Duration": 9872,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781989510988",
        "Id": "1b57843d-c18e-4246-9d25-408bf7fee104",
        "ImageId": "ccf4ddfd-d8ba-434a-8460-e9a53ff8af00",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2019-12-30T05:00:00.0000000Z"
        },
        "LovePointsPrice": 2800,
        "Price": {
          "Currency": "EUR",
          "Price": 4.54
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2019-12-30T05:00:00.0000000Z",
        "PublisherName": "Kobo Editions",
        "Rating": 4.318182,
        "RelatedGroupId": "a919a016-a76e-8d3b-0000-000000000000",
        "SeriesId": "2576797e-adc9-5dca-859e-38832511875f",
        "SeriesName": "The Works of Lewis Carroll presented by Kobo Editions",
        "SeriesSlug": "the-works-of-lewis-carroll-presented-by-kobo-editions-1",
        "Slug": "alice-s-adventures-in-wonderland-491",
        "Title": "Alice's Adventures in Wonderland",
        "TotalBytes": 78984019,
        "TotalRating": 22,
        "WorkId": "e1191bf2-8605-3490-85b4-824d708f71c2"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Mike Rogers",
            "Role": "Narrator"
          },
          {
            "Name": "Robert Louis Stevenson",
            "Role": "Author"
          }
        ],
        "Contributors": "Robert Louis Stevenson,Mike Rogers",
        "CrossRevisionId": "11a0e5f4-03d2-394e-9aef-5bd4bd02a806",
        "Description": "<p>A violent, sinister stranger wreaks havoc on the streets of 1880s London, while a revered scientist mysteriously retreats into solitude. A few drops of an experimental drug are all that k… [TRUNCATED 289 chars; original string value]",
        "Duration": 10161,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781989510964",
        "Id": "c216106a-5722-462f-9731-81463bf488c6",
        "ImageId": "86bef5dc-8da2-493e-862d-37ba766f5d56",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2019-12-30T05:00:00.0000000Z"
        },
        "LovePointsPrice": 2800,
        "Price": {
          "Currency": "EUR",
          "Price": 4.54
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2019-12-30T05:00:00.0000000Z",
        "PublisherName": "Kobo Editions",
        "Rating": 0,
        "RelatedGroupId": "6a881c6e-a52c-0041-0000-000000000000",
        "SeriesId": "3fb5d537-4e70-5327-a74b-47ff3e1d7ba0",
        "SeriesName": "The Works of Robert Louis Stevenson presented by Kobo Editions",
        "SeriesSlug": "the-works-of-robert-louis-stevenson-presented-by-kobo-editions-1",
        "Slug": "the-strange-case-of-dr-jekyll-and-mr-hyde-230",
        "Title": "The Strange Case of Dr. Jekyll and Mr. Hyde",
        "TotalBytes": 81293863,
        "TotalRating": 28,
        "WorkId": "11a0e5f4-03d2-394e-9aef-5bd4bd02a806"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Assaf Cohen",
            "Role": "Narrator"
          },
          {
            "Name": "Kahlil Gibran",
            "Role": "Author"
          }
        ],
        "Contributors": "Kahlil Gibran,Assaf Cohen",
        "CrossRevisionId": "88320ca3-69d5-3b3c-9de3-207651964a5e",
        "Description": "<p>This collection of 26 poetic essays offers spiritual yet practical advice from a figure known only as The Prophet on such central-to-life subjects as love, marriage, beauty, life and deat… [TRUNCATED 253 chars; original string value]",
        "Duration": 4654,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781989510810",
        "Id": "45db8c91-ae60-4f8c-b8cb-f96977d971b8",
        "ImageId": "15c4a9e3-e176-4d3b-a5ef-725eac841daf",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2019-12-30T05:00:00.0000000Z"
        },
        "LovePointsPrice": 2800,
        "Price": {
          "Currency": "EUR",
          "Price": 4.54
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2019-12-30T05:00:00.0000000Z",
        "PublisherName": "Kobo Editions",
        "Rating": 4.230769,
        "RelatedGroupId": "2cc6ea54-1be8-e06c-0000-000000000000",
        "Slug": "the-prophet-235",
        "Title": "The Prophet",
        "TotalBytes": 37235437,
        "TotalRating": 13,
        "WorkId": "88320ca3-69d5-3b3c-9de3-207651964a5e"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ApplicableSubscriptions": [
          "93dd7c18-51f1-493f-bb5f-d9e62b974bee",
          "416bfce4-e4ae-4730-8314-5857dece58a6"
        ],
        "ContributorRoles": [
          {
            "Name": "Jan Cramer",
            "Role": "Narrator"
          },
          {
            "Name": "Jane Austen",
            "Role": "Author"
          }
        ],
        "Contributors": "Jane Austen,Jan Cramer",
        "CrossRevisionId": "6140e992-5002-3268-a172-edc813fd110b",
        "Description": "<p>There are few more iconic love stories than Georgian-era romance PRIDE AND PREJUDICE, arguably Jane AustenΓÇÖs most beloved and influential work.</p><p>Mrs Bennet wants nothing more than … [TRUNCATED 311 chars; original string value]",
        "Duration": 44259,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": true,
        "ISBN": "9781989510865",
        "Id": "dce1c344-edac-4744-9205-8974b4984fc4",
        "ImageId": "1b0b6263-48ed-43e3-b613-52a9af3d4e1e",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": false,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2019-12-30T05:00:00.0000000Z"
        },
        "LovePointsPrice": 4800,
        "Price": {
          "Currency": "EUR",
          "Price": 9.74
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2019-12-30T05:00:00.0000000Z",
        "PublisherName": "Kobo Editions",
        "Rating": 4.240741,
        "RelatedGroupId": "ded666e1-8262-ff88-0000-000000000000",
        "SeriesId": "95f9dcb3-ccc9-535f-91a3-29dd120066b8",
        "SeriesName": "The Works of Jane Austen presented by Kobo Editions",
        "SeriesSlug": "the-works-of-jane-austen-presented-by-kobo-editions-1",
        "Slug": "pride-and-prejudice-650",
        "Title": "Pride and Prejudice",
        "TotalBytes": 354104691,
        "TotalRating": 54,
        "WorkId": "6140e992-5002-3268-a172-edc813fd110b"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 10,
  "TotalPageCount": 3,
  "VersionCode": 2
}
```


## 38. GET `/v1/products/featured/{uuid}` — device capture response

Source: `device:L20751`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `device/GET_v1_products_featured_uuid/response_L20751.json`.

```json
{
  "CurrentPageIndex": 0,
  "Filters": {
    "KoboLoveEnabled": [
      "True",
      "False"
    ],
    "PreOrders": [
      "True",
      "False"
    ],
    "SubscriptionsAvailable": [
      "True",
      "False"
    ]
  },
  "ItemCount": 4,
  "Items": [
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Brian Alan Hill",
            "Role": "Narrator"
          },
          {
            "Name": "David Scott Hay",
            "Role": "Author"
          }
        ],
        "Contributors": "David Scott Hay,Brian Alan Hill",
        "CrossRevisionId": "d184f5f2-7d27-3845-b820-9c360ec5653e",
        "Description": "<p>**GOODREADS #1 HORROR TO READ IN 2026!!</p><p>A riveting, epic novel about a man who mustjourney across an unforgiving landscape in order to oseek redemption by doing the most unholy thin… [TRUNCATED 306 chars; original string value]",
        "Duration": 25200,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": false,
        "ISBN": "9798347213979",
        "Id": "0c31fda9-8540-457e-92c2-175605322ac3",
        "ImageId": "06fc6121-1945-482f-8f4f-5dd53da8ab62",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": true,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-10-20T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "Price": {
          "Currency": "EUR",
          "Price": 21.41
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-10-20T00:00:00.0000000Z",
        "PublisherName": "Simon Maverick",
        "Rating": 0,
        "RelatedGroupId": "56548c9a-87d0-07f5-0000-000000000000",
        "Slug": "the-butcher-of-nazareth",
        "Title": "The Butcher of Nazareth",
        "TotalBytes": 0,
        "TotalRating": 0,
        "WorkId": "d184f5f2-7d27-3845-b820-9c360ec5653e"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": "01"
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Neil Newbon",
            "Role": "Narrator"
          },
          {
            "Name": "T. Kingfisher",
            "Role": "Author"
          }
        ],
        "Contributors": "T. Kingfisher,Neil Newbon",
        "CrossRevisionId": "92c3d0d1-2562-31c5-806e-d113b9c29f3f",
        "Description": "<p><strong>Narrated by Neil NewbonΓÇöthe multi-award-winning actor who brought Astarion to life in BaldurΓÇÖs Gate 3ΓÇöthis captivating audiobook offers listeners a rare chance to experience… [TRUNCATED 316 chars; original string value]",
        "Duration": 36066,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": false,
        "ISBN": "9798217408931",
        "Id": "3265274c-d0fc-4d6d-9d66-dae2e36739d3",
        "ImageId": "fad168b2-2efb-4b7a-8aeb-b26420558337",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": true,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-09-29T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "Price": {
          "Currency": "EUR",
          "Price": 19.64
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-09-29T00:00:00.0000000Z",
        "PublisherName": "Penguin Random House Audio Publishing Group",
        "Rating": 0,
        "RelatedGroupId": "6404b5a0-9381-fd79-0000-000000000000",
        "SeriesId": "010a3031-1103-55a2-9c26-73aa0e9d6bce",
        "SeriesName": "Baldur's Gate 3",
        "SeriesSlug": "baldur-s-gate-3",
        "Slug": "baldur-s-gate-3-astarion",
        "Title": "Baldur's Gate 3: Astarion",
        "TotalBytes": 288536670,
        "TotalRating": 0,
        "WorkId": "92c3d0d1-2562-31c5-806e-d113b9c29f3f"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Corry L. Lee",
            "Role": "Author"
          }
        ],
        "Contributors": "Corry L. Lee",
        "CrossRevisionId": "42ec4476-c13b-3334-8b39-40d0e706a56d",
        "Description": "<p><strong>War. Rebellion. Magic.</strong></p><p>CelkaΓÇÖs innovative magic let her assassinate the StormhawkΓÇöBourshkanyaΓÇÖs unkillable dictator. Alongside Filip, a State-trained mage and… [TRUNCATED 322 chars; original string value]",
        "Duration": 57600,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": false,
        "ISBN": "9798896792819",
        "Id": "07fe2486-3ba9-44fd-8936-9c0edd322d00",
        "ImageId": "da178233-3e81-40fb-86d5-9e6925f39b63",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": true,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2026-10-20T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "Price": {
          "Currency": "EUR",
          "Price": 26.77
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2026-10-20T00:00:00.0000000Z",
        "PublisherName": "Recorded Books, Inc.",
        "Rating": 0,
        "RelatedGroupId": "4666fcb5-18d9-6b61-0000-000000000000",
        "SeriesId": "3315b1e5-e049-5008-a01f-0905e0d029ab",
        "SeriesName": "Bourshkanya Trilogy",
        "SeriesSlug": "bourshkanya-trilogy",
        "Slug": "imbue-the-sky-2",
        "Title": "Imbue the Sky",
        "TotalBytes": 0,
        "TotalRating": 0,
        "WorkId": "42ec4476-c13b-3334-8b39-40d0e706a56d"
      }
    },
    {
      "Audiobook": {
        "AccessibilityDetails": {
          "ContentTypes": [],
          "EPubAccessibilities": [],
          "HazardWarningTypes": [],
          "IsAccessible": false,
          "IsFixedLayout": false,
          "IsTextToSpeechAllowed": false,
          "PrimaryContentType": ""
        },
        "AgeVerificationRequired": false,
        "ContributorRoles": [
          {
            "Name": "Mathew Rodriguez",
            "Role": "Author"
          }
        ],
        "Contributors": "Mathew Rodriguez",
        "CrossRevisionId": "88ef990b-7100-3837-ba04-e9b220229262",
        "Description": "<p><strong>A powerful hybrid memoir exploring the connections between father and son, addiction and disease, and what it really means to be a man, from an award-winning queer journalist</str… [TRUNCATED 309 chars; original string value]",
        "Duration": 42240,
        "EligibleForKoboLoveDiscount": false,
        "HasPreview": false,
        "ISBN": "9798898840556",
        "Id": "a645bbf4-1e92-4c8c-b156-d81f1bc03e09",
        "ImageId": "be193a36-7139-457a-9272-6b2c2ec2415f",
        "IsAbridged": false,
        "IsContentSharingEnabled": false,
        "IsFree": false,
        "IsPreOrder": true,
        "IsRecommendation": false,
        "Language": "en",
        "LifeCycleDates": {
          "ActivationDate": "2027-01-19T00:00:00.0000000Z"
        },
        "Locale": {
          "CountryCode": "",
          "LanguageCode": "eng",
          "ScriptCode": ""
        },
        "Price": {
          "Currency": "EUR",
          "Price": 23.2
        },
        "PromoCodeAllowed": true,
        "PublicationDate": "2027-01-19T00:00:00.0000000Z",
        "PublisherName": "Recorded Books, Inc.",
        "Rating": 0,
        "RelatedGroupId": "74450669-25c1-594f-0000-000000000000",
        "Slug": "tough-guy-19",
        "Subtitle": "A Memoir and a Search",
        "Title": "Tough Guy",
        "TotalBytes": 0,
        "TotalRating": 0,
        "WorkId": "88ef990b-7100-3837-ba04-e9b220229262"
      }
    }
  ],
  "ItemsPerPage": 4,
  "TotalItemCount": 500,
  "TotalPageCount": 125,
  "VersionCode": 2
}
```


## 39. GET `/v1/products/books/{uuid}/` — device capture response

Source: `device:L25877`. HTTP: `200`. Captured type: `object (37 top-level keys)`. Fixture: `device/GET_v1_products_books_uuid/response_L25877.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false,
    "PrimaryContentType": "10"
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
      "Role": "Illustrator"
    },
    {
      "Name": "Matteo Cremaschi",
      "Role": "Translator"
    }
  ],
  "Contributors": "Kanehito Yamada",
  "CrossRevisionId": "1e78804b-759e-3e91-aa19-a6a7def6e301",
  "Description": "<p>La maga Frieren fa visita alla capitale imperiale, il fulcro della civilt├á magica fondata dalla sua maestra Flamme. Qualcuno sta pianificando l'assassinio di Serie e l'Istituto di magia … [TRUNCATED 232 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9788834937211",
  "Id": "bb14a4c2-04be-43c7-935b-55d6ef25055a",
  "ImageId": "284ebc78-3bfd-4d28-b632-d9aa8b0adc75",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "it",
  "Locale": {
    "LanguageCode": "ita"
  },
  "Price": {
    "Currency": "EUR",
    "Price": 3.99
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2025-06-24T00:00:00.0000000Z",
  "PublisherName": "J-POP Manga",
  "Rating": 5,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 0,
    "5": 4
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 85245253,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "RelatedGroupId": "d91fa13a-7be8-64da-0000-000000000000",
  "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
  "SeriesName": "Frieren. Oltre la fine del viaggio",
  "SeriesNumber": "14",
  "SeriesNumberFloat": 14,
  "Slug": "frieren-oltre-la-fine-del-viaggio-vol-14",
  "Subtitle": "",
  "Title": "Frieren. Oltre la fine del viaggio (Vol. 14)",
  "TotalRating": 4,
  "WorkId": "1e78804b-759e-3e91-aa19-a6a7def6e301"
}
```


## 40. GET `/v1/products/books/{uuid}/` — device capture response

Source: `device:L26088`. HTTP: `200`. Captured type: `object (37 top-level keys)`. Fixture: `device/GET_v1_products_books_uuid/response_L26088.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [
      {
        "Type": "08"
      }
    ],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false,
    "PrimaryContentType": "10"
  },
  "AgeVerificationRequired": false,
  "ApplicableSubscriptions": [],
  "ContributorRoles": [
    {
      "Name": "Kanehito Yamada",
      "Role": "Author"
    }
  ],
  "Contributors": "Kanehito Yamada",
  "CrossRevisionId": "43593869-0f5a-3134-a33e-72c37f7de520",
  "Description": "<p>La maga Frieren ripercorre il camino intrapreso con lΓÇÖeroe Himmel, mentre ne tiene vivo il ricordo. La sua coscienza torna indietro nel tempo, dove si trova nuovamente a fronteggiare le… [TRUNCATED 162 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9788834931608",
  "Id": "2618a30b-165d-4bdf-8c48-f20c3fce40a3",
  "ImageId": "7273ec92-846d-458d-9925-5d4ff33b1694",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "it",
  "Locale": {
    "LanguageCode": "ita"
  },
  "Price": {
    "Currency": "EUR",
    "Price": 3.99
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2024-09-24T00:00:00.0000000Z",
  "PublisherName": "J-POP Manga",
  "Rating": 5,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 0,
    "5": 2
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 29465190,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "RelatedGroupId": "d131f1db-5067-6f6d-0000-000000000000",
  "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
  "SeriesName": "Frieren. Oltre la fine del viaggio",
  "SeriesNumber": "13",
  "SeriesNumberFloat": 13,
  "Slug": "frieren-oltre-la-fine-del-viaggio-vol-13",
  "Subtitle": "",
  "Title": "Frieren. Oltre la fine del viaggio (Vol. 13)",
  "TotalRating": 2,
  "WorkId": "43593869-0f5a-3134-a33e-72c37f7de520"
}
```


## 41. GET `/v1/products/books/{uuid}/` — device capture response

Source: `device:L26231`. HTTP: `200`. Captured type: `object (37 top-level keys)`. Fixture: `device/GET_v1_products_books_uuid/response_L26231.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [
      {
        "Type": "08"
      }
    ],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false,
    "PrimaryContentType": "10"
  },
  "AgeVerificationRequired": false,
  "ApplicableSubscriptions": [],
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
  "Contributors": "Kanehito Yamada",
  "CrossRevisionId": "17cdaf56-209a-31d3-9d9c-29e3980da9d3",
  "Description": "<p>Frieren ├¿ la maga elfa che insieme alla compagnia dellΓÇÖeroe ha sconfitto il Re Demone. La sua vita continua a lungo dopo quellΓÇÖavventura ma, quando tocca la Stele della Dea, la sua c… [TRUNCATED 186 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9788834928684",
  "Id": "f3212cd2-0ca8-49ec-9dea-4263d50b057e",
  "ImageId": "0f13b8cd-9045-4d50-a7a0-40e285902d10",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "it",
  "Locale": {
    "LanguageCode": "ita"
  },
  "Price": {
    "Currency": "EUR",
    "Price": 3.99
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2024-04-23T00:00:00.0000000Z",
  "PublisherName": "J-POP Manga",
  "Rating": 5,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 0,
    "5": 4
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 38734693,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "RelatedGroupId": "e48b4ebe-226c-2a4b-0000-000000000000",
  "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
  "SeriesName": "Frieren. Oltre la fine del viaggio",
  "SeriesNumber": "12",
  "SeriesNumberFloat": 12,
  "Slug": "frieren-oltre-la-fine-del-viaggio-vol-12",
  "Subtitle": "",
  "Title": "Frieren. Oltre la fine del viaggio (Vol.12)",
  "TotalRating": 4,
  "WorkId": "17cdaf56-209a-31d3-9d9c-29e3980da9d3"
}
```


## 42. GET `/v1/products/books/{uuid}/` — device capture response

Source: `device:L26374`. HTTP: `200`. Captured type: `object (37 top-level keys)`. Fixture: `device/GET_v1_products_books_uuid/response_L26374.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [
      {
        "Type": "08"
      }
    ],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false,
    "PrimaryContentType": "10"
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
    },
    {
      "Name": "Matteo Cremaschi",
      "Role": "Translator"
    }
  ],
  "Contributors": "Kanehito Yamada, Tsukasa Abe",
  "CrossRevisionId": "82921db9-82e1-38df-862b-11a7532b40d1",
  "Description": "<p>LΓÇÖelfo mago Frieren e i suoi coraggiosi compagni dΓÇÖavventura hanno sconfitto il Re Demone, portando finalmente la pace nella loro terra. Ora gli eroi possono intraprendere strade dive… [TRUNCATED 370 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9788834923719",
  "Id": "62f623f6-4b4d-43ec-afc1-85cd5ce99a06",
  "ImageId": "888d918c-f169-4514-b375-04762dc963f8",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "it",
  "Locale": {
    "LanguageCode": "ita"
  },
  "Price": {
    "Currency": "EUR",
    "Price": 3.99
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2023-11-22T00:00:00.0000000Z",
  "PublisherName": "J-POP Manga",
  "Rating": 5,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 0,
    "5": 3
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 29951838,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "RelatedGroupId": "5958f7f7-0d4f-c618-0000-000000000000",
  "SeriesId": "01a4fb8f-759f-5c5e-ac54-a44f37b142f4",
  "SeriesName": "Frieren. Oltre la fine del viaggio",
  "SeriesNumber": "10",
  "SeriesNumberFloat": 10,
  "Slug": "frieren-oltre-la-fine-del-viaggio-vol-10",
  "Subtitle": "",
  "Title": "Frieren. Oltre la fine del viaggio (Vol.10)",
  "TotalRating": 3,
  "WorkId": "82921db9-82e1-38df-862b-11a7532b40d1"
}
```


## 43. GET `/v1/library/{komgaId}/state` — device capture response

Source: `device:L27227`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_state/response_L27227.json`.

```json
[
  {
    "Created": "2026-08-26T18:22:23Z",
    "CurrentBookmark": {
      "ContentSourceProgressPercent": 0,
      "LastModified": "2026-09-22T10:09:17.752Z",
      "Location": {
        "Source": "OEBPS/Text/kcc-0097-kcc-x.xhtml",
        "Type": "KoboSpan",
        "Value": "kobo.1.1"
      },
      "ProgressPercent": 48.756218
    },
    "EntitlementId": "0RDTATKHXHPS9",
    "LastModified": "2026-09-22T10:09:17.752Z",
    "PriorityTimestamp": "2026-09-22T10:09:17.752Z",
    "Statistics": {
      "LastModified": "2026-09-22T10:09:17.752Z"
    },
    "StatusInfo": {
      "LastModified": "2026-09-22T10:09:17.752Z",
      "Status": "Reading",
      "TimesStartedReading": 1
    }
  }
]
```


## 44. GET `/v1/products/{komgaId}/prices` — device capture response

Source: `device:L27279`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_prices/response_L27279.json`.

```json
{
  "Items": [
    {
      "CrossRevisionId": "0RDTATKHXHPS9",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKHXHPS9",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    }
  ]
}
```


## 45. GET `/v1/user/reviews` — device capture response

Source: `device:L27345`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_user_reviews/response_L27345.json`.

```json
{
  "CurrentPageIndex": 1,
  "Items": [],
  "TotalPageCount": 0
}
```


## 46. POST `/v1/products/{komgaId}/reviews` — device capture request

Source: `device:L27367`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/POST_v1_products_komgaId_reviews/request_L27367.json`.

```json
{
  "authorDisplayName": "[REDACTED]",
  "body": "[REVIEW_TEXT_REDACTED]",
  "creation_date": "2026-09-22T11:45:47Z",
  "rating": 5,
  "title": "Best book"
}
```


## 47. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L27427`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L27427.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:46:02Z",
        "Location": {
          "Source": "OEBPS/Text/kcc-0001-kcc-x.xhtml",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 100
      },
      "EntitlementId": "0RDTATKHXHPS9",
      "LastModified": "2026-09-22T11:46:02Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:46:02Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 1
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:46:02Z",
        "Status": "Finished"
      }
    }
  ]
}
```


## 48. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L27456`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L27456.json`.

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


## 49. POST `/api/v3/content/checkforchanges` — device capture request

Source: `device:L27490`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/request_L27490.json`.

```json
[
  {
    "ContentId": "0RDTATKHXHPS9",
    "etag": "[REDACTED]"
  }
]
```


## 50. POST `/api/v3/content/checkforchanges` — device capture response

Source: `device:L27506`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `device/POST_api_v3_content_checkforchanges/response_L27506.json`.

```json
[]
```


## 51. GET `/v1/products/{komgaId}/reviews` — device capture response

Source: `device:L27621`. HTTP: `200`. Captured type: `object (5 top-level keys)`. Fixture: `device/GET_v1_products_komgaId_reviews/response_L27621.json`.

```json
{
  "CurrentPageIndex": 1,
  "Cursor": "1",
  "Items": [
    {
      "AuthorDisplayName": "[REDACTED]",
      "Body": "Omg I want this book to come out so bad!!!!!!! even though I haven't read it I know it's going to be good but first I want to know Twilights secret name. I want to buy ALL THEIR STUFF!!!!!!!… [TRUNCATED 274 chars; original string value]",
      "CreationDate": "2024-10-13T00:30:02.0488670-04:00",
      "CrossRevisionId": "0RDTATKHXHPS9",
      "Dislikes": 26,
      "Id": "Book-dad29474-df99-34bd-8b4b-30bbb51b849d-G0asfd4QkzY85qM0e9t0AuUJO9vVO2p42AhZl5f304=",
      "Likes": 85,
      "ModerationStatus": "Approved",
      "ProductId": "0RDTATKHXHPS9",
      "PublicationId": "00000000-0000-0000-0000-000000000000",
      "Rating": 5,
      "RevisionId": "0RDTATKHXHPS9",
      "Title": "Twilight"
    },
    {
      "AuthorDisplayName": "[REDACTED]",
      "Body": "I can't wait! Anya is such a funny character and I love the relationship dynamics starting to appear with Anya and Damian!! Yors marital crisis is also quite silly.\nOverall, I cannot say how… [TRUNCATED 77 chars; original string value]",
      "CreationDate": "2024-10-09T23:49:06.1147011-04:00",
      "CrossRevisionId": "0RDTATKHXHPS9",
      "Dislikes": 3,
      "Id": "Book-dad29474-df99-34bd-8b4b-30bbb51b849d-AAkBeayDYMAkFeduOtBOkPFP6dXf8mhNd553tOuKBv4=",
      "Likes": 45,
      "ModerationStatus": "Approved",
      "ProductId": "0RDTATKHXHPS9",
      "PublicationId": "00000000-0000-0000-0000-000000000000",
      "Rating": 5,
      "RevisionId": "0RDTATKHXHPS9",
      "Title": "Aaaaaaah"
    },
    {
      "AuthorDisplayName": "[REDACTED]",
      "Body": "So about the cliffhanger in book 12, Nightfall beats Handler and Yuri comes to visit.\n\nIn a part of the story, Yuri sees Loid's arm twitch and suspects he is the person who impersonated him.… [TRUNCATED 489 chars; original string value]",
      "CreationDate": "2025-02-17T06:40:38.6649063-05:00",
      "CrossRevisionId": "0RDTATKHXHPS9",
      "Dislikes": 8,
      "Id": "Book-dad29474-df99-34bd-8b4b-30bbb51b849d-EsryQunba3TAb4dO1c3oNCMrBbPj7Jio7wCXyPalkM=",
      "Likes": 33,
      "ModerationStatus": "Approved",
      "ProductId": "0RDTATKHXHPS9",
      "PublicationId": "00000000-0000-0000-0000-000000000000",
      "Rating": 5,
      "RevisionId": "0RDTATKHXHPS9",
      "Title": "SPOILERS"
    }
  ],
  "ReviewSummary": {
    "0RDTATKHXHPS9": {
      "AvgRating": 4.75,
      "NumberOfReviews": 85,
      "OpinionCount": 236,
      "RatingHistogram": {
        "1": 5,
        "3": 6,
        "4": 27,
        "5": 198
      }
    }
  },
  "TotalPageCount": 29
}
```


## 52. GET `/v1/products/books/{komgaId}/` — device capture response

Source: `device:L27696`. HTTP: `200`. Captured type: `object (34 top-level keys)`. Fixture: `device/GET_v1_products_books_komgaId/response_L27696.json`.

```json
{
  "AccessibilityDetails": {
    "EPubAccessibilities": [],
    "HazardWarningTypes": [],
    "IsAccessible": false,
    "IsFixedLayout": false,
    "IsTextToSpeechAllowed": false
  },
  "AgeVerificationRequired": false,
  "ApplicableSubscriptions": [],
  "ContributorRoles": [
    {
      "Name": "Tatsuya Endo"
    }
  ],
  "Contributors": "Tatsuya Endo",
  "CrossRevisionId": "0RDTATKHXHPS9",
  "Description": "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for th… [TRUNCATED 443 chars; original string value]",
  "EligibleForKoboLoveDiscount": false,
  "HasPreview": true,
  "ISBN": "9781974753246",
  "Id": "0RDTATKHXHPS9",
  "ImageId": "0RPA2DAJ0JTT2",
  "InWishlist": false,
  "IsContentSharingEnabled": true,
  "IsFree": false,
  "IsInternetArchive": false,
  "IsPreOrder": false,
  "IsRecommendation": false,
  "Language": "en",
  "Price": {
    "Currency": "EUR",
    "Price": 1
  },
  "PromoCodeAllowed": false,
  "PublicationDate": "2025-01-13T00:00:00Z",
  "PublisherName": "VIZ Media",
  "Rating": 4.75,
  "RatingHistogram": {
    "1": 5,
    "2": 0,
    "3": 6,
    "4": 27,
    "5": 198
  },
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Platform": "Generic",
      "Size": 3070484,
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]"
    }
  ],
  "SeriesId": "0RDTATKG1HM0V",
  "SeriesName": "Spy x Family",
  "SeriesNumber": "13",
  "SeriesNumberFloat": 13,
  "Stats": {
    "WordCount": 0
  },
  "Title": "Spy X Family, Vol. 13",
  "TotalRating": 236,
  "WorkId": "0RDTATKHXHPS9"
}
```


## 53. GET `/v1/user/reviews` — device capture response

Source: `device:L27664`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_user_reviews/response_L27664.json`.

```json
{
  "CurrentPageIndex": 1,
  "Items": [],
  "TotalPageCount": 1
}
```


## 54. GET `/v1/user/reviews` — device capture response

Source: `device:L30621`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_user_reviews/response_L30621.json`.

```json
{
  "CurrentPageIndex": 1,
  "Items": [],
  "TotalPageCount": 1
}
```


## 55. GET `/v1/products/{productIds}/prices` — device capture response

Source: `device:L30643`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_products_productIds_prices/response_L30643.json`.

```json
{
  "Items": [
    {
      "CrossRevisionId": "de2fd89f-eb4d-3205-8503-82068935f567",
      "EligibleForKoboLoveDiscount": false,
      "Id": "2af5ad58-c530-48ef-9de0-f1c3708f6d8e",
      "IsPreOrder": false,
      "LovePointsPrice": 3200,
      "Price": {
        "Currency": "EUR",
        "Price": 6.24
      }
    },
    {
      "CrossRevisionId": "9ed2acb0-e4da-3725-afe0-25efa0d7c0bd",
      "EligibleForKoboLoveDiscount": false,
      "Id": "331d106f-731e-4565-857d-46981fcd3fed",
      "IsPreOrder": false,
      "LovePointsPrice": 3200,
      "Price": {
        "Currency": "EUR",
        "Price": 6.24
      }
    },
    {
      "CrossRevisionId": "ed980d29-c48c-3ed4-a57a-64097ebdd891",
      "EligibleForKoboLoveDiscount": false,
      "Id": "45368193-f16e-42a8-8411-81a25a2d4832",
      "IsPreOrder": false,
      "LovePointsPrice": 3600,
      "Price": {
        "Currency": "EUR",
        "Price": 5.71
      }
    },
    {
      "CrossRevisionId": "9d50b83b-29c1-324a-8b9f-33f40513baa2",
      "EligibleForKoboLoveDiscount": false,
      "Id": "6852e6c6-96d3-4000-9851-c4fb8d2cbaa4",
      "IsPreOrder": false,
      "LovePointsPrice": 3200,
      "Price": {
        "Currency": "EUR",
        "Price": 6.24
      }
    },
    {
      "CrossRevisionId": "82b03a01-91d7-31d3-8549-a0ec81f716fb",
      "EligibleForKoboLoveDiscount": false,
      "Id": "9f7fc08a-2351-452f-933a-f044d2073343",
      "IsPreOrder": false,
      "LovePointsPrice": 3600,
      "Price": {
        "Currency": "EUR",
        "Price": 5.71
      }
    }
  ]
}
```


## 56. GET `/v1/products/{productIds}/prices` — device capture response

Source: `device:L31167`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/GET_v1_products_productIds_prices/response_L31167.json`.

```json
{
  "Items": [
    {
      "CrossRevisionId": "0RDTATKH5HXZM",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKH5HXZM",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    },
    {
      "CrossRevisionId": "0RDTATKH5HXZS",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKH5HXZS",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    },
    {
      "CrossRevisionId": "0RDTATKH9HV0E",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKH9HV0E",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    },
    {
      "CrossRevisionId": "0RDTATKH9HV0D",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKH9HV0D",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    },
    {
      "CrossRevisionId": "0RDTATKH9HV0G",
      "EligibleForKoboLoveDiscount": false,
      "Id": "0RDTATKH9HV0G",
      "IsPreOrder": false,
      "Price": {
        "Currency": "EUR",
        "Price": 1
      }
    }
  ]
}
```


## 57. GET `/v1/products` — device capture response

Source: `device:L31217`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `device/GET_v1_products/response_L31217.json`.

```json
{
  "Items": [],
  "ItemCount": 0,
  "TotalItemCount": 0
}
```


## 58. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L31330`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L31330.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:44:32Z",
        "Location": {
          "Source": "",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 0
      },
      "EntitlementId": "0RDTATKH9HV0E",
      "LastModified": "2026-09-22T11:44:32Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:44:32Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 0
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:44:32Z",
        "Status": "ReadyToRead"
      }
    }
  ]
}
```


## 59. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L31350`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L31350.json`.

```json
{
  "RequestResult": "Failure",
  "UpdateResults": [
    {
      "CurrentBookmarkResult": {
        "Result": "Failure"
      },
      "EntitlementId": "0RDTATKH9HV0E",
      "StatisticsResult": {
        "Result": "Failure"
      },
      "StatusInfoResult": {
        "Result": "Failure"
      }
    }
  ]
}
```


## 60. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L31377`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L31377.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:44:36Z",
        "Location": {
          "Source": "",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 0
      },
      "EntitlementId": "0RDTATKH9HV0K",
      "LastModified": "2026-09-22T11:44:36Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:44:36Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 0
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:44:36Z",
        "Status": "ReadyToRead"
      }
    }
  ]
}
```


## 61. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L31397`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L31397.json`.

```json
{
  "RequestResult": "Failure",
  "UpdateResults": [
    {
      "CurrentBookmarkResult": {
        "Result": "Failure"
      },
      "EntitlementId": "0RDTATKH9HV0K",
      "StatisticsResult": {
        "Result": "Failure"
      },
      "StatusInfoResult": {
        "Result": "Failure"
      }
    }
  ]
}
```


## 62. PUT `/v1/library/{komgaId}/state` — device capture request

Source: `device:L31424`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/request_L31424.json`.

```json
{
  "ReadingStates": [
    {
      "CurrentBookmark": {
        "ContentSourceProgressPercent": 0,
        "LastModified": "2026-09-22T11:44:28Z",
        "Location": {
          "Source": "0RDTATKH5HXZS!OEBPS!Text/kcc-0001-kcc-x.xhtml",
          "Type": "KoboSpan",
          "Value": "kobo.1.1"
        },
        "ProgressPercent": 0
      },
      "EntitlementId": "0RDTATKH5HXZS",
      "LastModified": "2026-09-22T11:44:28Z",
      "Statistics": {
        "LastModified": "2026-09-22T11:44:28Z",
        "RemainingTimeMinutes": 0,
        "SpentReadingMinutes": 0
      },
      "StatusInfo": {
        "LastModified": "2026-09-22T11:44:28Z",
        "Status": "ReadyToRead"
      }
    }
  ]
}
```


## 63. PUT `/v1/library/{komgaId}/state` — device capture response

Source: `device:L31444`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/PUT_v1_library_komgaId_state/response_L31444.json`.

```json
{
  "RequestResult": "Failure",
  "UpdateResults": [
    {
      "CurrentBookmarkResult": {
        "Result": "Failure"
      },
      "EntitlementId": "0RDTATKH5HXZS",
      "StatisticsResult": {
        "Result": "Failure"
      },
      "StatusInfoResult": {
        "Result": "Failure"
      }
    }
  ]
}
```


## 64. GET `/v1/library/sync` — device capture response

Source: `device:L31502`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_sync/response_L31502.json`.

```json
[
  {
    "ChangedReadingState": {
      "ReadingState": {
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
    }
  }
]
```


## 65. GET `/v1/library/{komgaId}/metadata` — device capture response

Source: `device:L31640`. HTTP: `200`. Captured type: `array (1 entries)`. Fixture: `device/GET_v1_library_komgaId_metadata/response_L31640.json`.

```json
[
  {
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
    "CoverImageId": "0RPMBCR98HX7E",
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
]
```


## 66. GET `/api/v3/content/{komgaId}/annotations` — device capture response

Source: `device:L31741`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `device/GET_api_v3_content_komgaId_annotations/response_L31741.json`.

```json
{
  "annotations": [
    {
      "attachments": {},
      "clientLastModifiedUtc": "2026-08-26T23:43:06.3447996Z",
      "context": null,
      "highlightColor": "#F6F3B3",
      "highlightedText": "But thereΓÇÖs",
      "id": "8738fd0e-4258-4f8f-9f57-92d7a937b836",
      "location": {
        "span": {
          "chapterFilename": "And_Another_Thing_The_World_Acc_split_001.html",
          "chapterProgress": 0.012195121951219513,
          "chapterTitle": "And Another Thing",
          "endChar": 11,
          "endPath": "span#kobo\\.0\\.11",
          "startChar": 0,
          "startPath": "span#kobo\\.0\\.11"
        }
      },
      "type": "highlight"
    }
  ],
  "nextPageOffsetToken": null
}
```


## 67. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L32001`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L32001.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```


## 68. GET `/1.0/UpgradeCheck/Device/{uuid}/Kobo/4.46.23836/REDACTED` — device capture response

Source: `device:L32039`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `device/GET_1_0_UpgradeCheck_Device_uuid_Kobo_4_46_23836_REDACTED/response_L32039.json`.

```json
{
  "Data": null,
  "ReleaseNoteURL": null,
  "UpgradeType": 0,
  "UpgradeURL": null
}
```

