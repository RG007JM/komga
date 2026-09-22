# Captures 272–289

[← Capture index](README.md) · [API index](../../kobo-api.md)

## 272. not logged `/v1/products/books/subscriptions` — Komga upstream raw proxy log response

Source: `server:L1373`. HTTP: `200`. Captured type: `array (3 entries)`. Fixture: `upstream/unknown_v1_products_books_subscriptions/response_L1373.json`.

```json
[
  {
    "CrossRevisionId": "be9e9ac0-5823-4047-8281-069986c626b9",
    "Name": "Kobo Plus Read",
    "Tiers": [
      {
        "Id": "dac8bce4-2fe3-40ae-9b07-d72c7f99212a",
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
        "Id": "1fa62dd8-c2a9-41ce-8887-7444c07b7b3e",
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
        "Id": "b8ef55a1-b896-46f3-ba3d-44ef3f774093",
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


## 273. not logged `/v1/deals` — Komga upstream standard proxy log response

Source: `server:L1376`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_deals/response_L1376.json`.

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


## 274. not logged `/v1/assets` — Komga upstream standard proxy log response

Source: `server:L1379`. HTTP: `200`. Captured type: `object (2 top-level keys)`. Fixture: `upstream/unknown_v1_assets/response_L1379.json`.

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


## 275. not logged `/v1/analytics/gettests` — Komga upstream standard proxy log response

Source: `server:L1382`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `upstream/unknown_v1_analytics_gettests/response_L1382.json`.

```json
{
  "Result": "Success",
  "TestKey": "00000000-0000-4000-8000-000000000000",
  "Tests": {}
}
```


## 276. not logged `/v1/library/sync` — Komga upstream standard proxy log response

Source: `server:L1389`. HTTP: `200`. Captured type: `array (0 entries)`. Fixture: `upstream/unknown_v1_library_sync/response_L1389.json`.

```json
[]
```


## 277. not logged `/v1/user/wishlist` — Komga upstream standard proxy log response

Source: `server:L1392`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_wishlist/response_L1392.json`.

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


## 278. not logged `/v1/products/books/series/{uuid}` — Komga upstream raw proxy log response

Source: `server:L1394`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_series_uuid/response_L1394.json`.

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


## 279. not logged `/v1/products/books/series/{uuid}` — Komga upstream raw proxy log response

Source: `server:L1397`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_series_uuid/response_L1397.json`.

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


## 280. not logged `/v1/user/recommendations` — Komga upstream standard proxy log response

Source: `server:L1401`. HTTP: `200`. Captured type: `object (8 top-level keys)`. Fixture: `upstream/unknown_v1_user_recommendations/response_L1401.json`.

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


## 281. not logged `/v1/products/{uuid}/nextread` — Komga upstream standard proxy log response

Source: `server:L1404`. HTTP: `200`. Captured type: `object (1 top-level keys)`. Fixture: `upstream/unknown_v1_products_uuid_nextread/response_L1404.json`.

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


## 282. not logged `/v1/products` — Komga upstream standard proxy log response

Source: `server:L1407`. HTTP: `200`. Captured type: `object (9 top-level keys)`. Fixture: `upstream/unknown_v1_products/response_L1407.json`.

**Complete captured JSON:** [Open the standalone JSON fixture](../payloads/large-json/ledger-large-009.json) (same keys, types, nesting and array entries; shown separately to keep the Markdown page renderable).


## 283. not logged `/v1/products` — Komga upstream standard proxy log response

Source: `server:L1410`. HTTP: `200`. Captured type: `object (9 top-level keys)`. Fixture: `upstream/unknown_v1_products/response_L1410.json`.

**Complete captured JSON:** [Open the standalone JSON fixture](../payloads/large-json/ledger-large-010.json) (same keys, types, nesting and array entries; shown separately to keep the Markdown page renderable).


## 284. not logged `/v1/products/{uuid}/reviews` — Komga upstream standard proxy log response

Source: `server:L1416`. HTTP: `200`. Captured type: `object (4 top-level keys)`. Fixture: `upstream/unknown_v1_products_uuid_reviews/response_L1416.json`.

```json
{
  "ReviewSummary": {
    "b8dc5f22-1050-333f-aebf-4b3b5000d1e4": {
      "AvgRating": 4.75,
      "OpinionCount": 16,
      "RatingHistogram": {
        "5": 12,
        "4": 4
      },
      "NumberOfReviews": 1
    }
  },
  "Items": [
    {
      "Id": "Book-b8dc5f22-1050-333f-aebf-4b3b5000d1e4-yQOV9wdevs55AvIhXPV3TRr63dXBGTOT7uBu4Wms=",
      "RevisionId": "92329c27-e655-4caf-aa15-54f1354ae9d3",
      "ProductId": "92329c27-e655-4caf-aa15-54f1354ae9d3",
      "CrossRevisionId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4",
      "PublicationId": "00000000-0000-0000-0000-000000000000",
      "Title": "Mon avis",
      "Body": "L'histoire est un peu moins bie construite que les autres tomes. Ça m'a beaucoup déçue. Par contre, les dessins sont toujours aussi beaux ! Je n'ai rien à redire sur ce dernier point.",
      "AuthorDisplayName": "[REDACTED]",
      "Rating": 4,
      "CreationDate": "2024-01-29T15:31:09.9314987-05:00",
      "Likes": 3,
      "Dislikes": 0,
      "ModerationStatus": "Approved"
    }
  ],
  "TotalPageCount": 1,
  "CurrentPageIndex": 1
}
```


## 285. not logged `/v1/products/books/{uuid}/` — Komga upstream raw proxy log response

Source: `server:L1421`. HTTP: `200`. Captured type: `object (38 top-level keys)`. Fixture: `upstream/unknown_v1_products_books_uuid/response_L1421.json`.

```json
{
  "Contributors": "Kanehito Yamada, Tsukasa Abe",
  "WorkId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4",
  "SeriesNumber": "",
  "SeriesName": "Frieren",
  "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
  "Subtitle": "",
  "IsFree": false,
  "ISBN": "9791032714010",
  "PublicationDate": "2023-06-08T00:00:00.0000000Z",
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
  "IsInternetArchive": false,
  "IsRecommendation": false,
  "CrossRevisionId": "b8dc5f22-1050-333f-aebf-4b3b5000d1e4",
  "Title": "Frieren T08",
  "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p>\n<p>Alors que Frieren et ses compagnons font halte dans la citadelle de Heiß, Stark propose à Fern d'aller en rendez-vous, sur le ton … [TRUNCATED 520 chars; original string value]",
  "Language": "fr",
  "Locale": {
    "LanguageCode": "fre"
  },
  "ImageId": "7d0540e1-f54e-420a-a312-18a07d0d00bf",
  "PublisherName": "AC MEDIA",
  "Rating": 4.75,
  "TotalRating": 16,
  "RatingHistogram": {
    "1": 0,
    "2": 0,
    "3": 0,
    "4": 4,
    "5": 12
  },
  "Slug": "frieren-t08",
  "IsContentSharingEnabled": true,
  "RedirectPreviewUrls": [
    {
      "DrmType": "None",
      "Format": "EPUB3FL_SAMPLE",
      "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
      "Platform": "Generic",
      "Size": 20734284
    }
  ],
  "HasPreview": true,
  "Stats": {
    "WordCount": 0
  },
  "Price": {
    "Currency": "EUR",
    "Price": 4.99
  },
  "PromoCodeAllowed": false,
  "LovePointsPrice": 2800,
  "EligibleForKoboLoveDiscount": false,
  "IsPreOrder": false,
  "InWishlist": false,
  "RelatedGroupId": "b4655498-0089-03e0-0000-000000000000",
  "ApplicableSubscriptions": [],
  "AgeVerificationRequired": false,
  "AccessibilityDetails": {
    "IsFixedLayout": true,
    "IsTextToSpeechAllowed": false,
    "EPubAccessibilities": [],
    "HazardWarningTypes": [],
    "IsAccessible": false
  },
  "Id": "92329c27-e655-4caf-aa15-54f1354ae9d3"
}
```


## 286. not logged `/v1/products/{uuid}/recommendations` — Komga upstream standard proxy log response

Source: `server:L1420`. HTTP: `200`. Captured type: `object (7 top-level keys)`. Fixture: `upstream/unknown_v1_products_uuid_recommendations/response_L1420.json`.

```json
{
  "Items": [
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "16e0d8dc-0c78-3a52-ab8b-d53832dba6a1",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032714065",
        "PublicationDate": "2023-08-24T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "16e0d8dc-0c78-3a52-ab8b-d53832dba6a1",
        "Title": "Frieren T09",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Dans la région de Drachen, Frieren et ses compagnons tombent sur un village en piteux état… L'elfe accepte de débarrasser les habit… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "98283d7e-af22-40ef-a9a5-b181be421d60",
        "PublisherName": "AC MEDIA",
        "Rating": 4.941176,
        "TotalRating": 17,
        "Slug": "frieren-t09",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 20451270
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "344733c5-d464-4a67-8c02-ec4d19ff0266"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "cd41b901-9399-34ff-9c90-760f7669e581",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032714096",
        "PublicationDate": "2023-09-28T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "cd41b901-9399-34ff-9c90-760f7669e581",
        "Title": "Frieren T10",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>À l'issue d'une discussion stérile avec Macht des Terres d'Or, Frieren a néanmoins appris une chose: s'il est impossible pour elle … [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "4d3198ad-f58a-4409-900e-051307ec64a0",
        "PublisherName": "AC MEDIA",
        "Rating": 4.928571,
        "TotalRating": 14,
        "Slug": "frieren-t10",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 18641457
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "84f366a2-28d7-45b2-9a32-1451974bd593"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "6442940d-b960-3918-b13a-503986df447c",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032713976",
        "PublicationDate": "2023-03-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "6442940d-b960-3918-b13a-503986df447c",
        "Title": "Frieren T07",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Douze candidats ont réussi le test de Sense, sauf qu'aux yeux de Serie, qui dirige la société des mages du continent, c'est douze d… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "4c7d0826-7324-4e38-96c5-f68fd4bd1bcf",
        "PublisherName": "AC MEDIA",
        "Rating": 4.736842,
        "TotalRating": 19,
        "Slug": "frieren-t07",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 16995627
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "18c73beb-fef3-49c1-96ce-f015fd7e892b"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "37748b7e-d821-3600-80e4-613177ff0259",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032716083",
        "PublicationDate": "2024-01-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "37748b7e-d821-3600-80e4-613177ff0259",
        "Title": "Frieren T11",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Pour faire gagner du temps à Frieren, qui doit finir d'analyser les souvenirs de Macht, Denken se charge de retenir le démon, tandi… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "92e895f8-eda4-4347-9820-e3bbb017f8c2",
        "PublisherName": "AC MEDIA",
        "Rating": 4.8125,
        "TotalRating": 16,
        "Slug": "frieren-t11",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 18809719
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "81e32293-8f89-41a4-b1ad-c2214cfd6fe1"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Géraldine Oudin",
        "WorkId": "191236bc-af2f-34f7-a77a-f416e0ac4d6e",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032716106",
        "PublicationDate": "2024-05-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Géraldine Oudin"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "191236bc-af2f-34f7-a77a-f416e0ac4d6e",
        "Title": "Frieren T12",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Frieren, Fern et Stark s'apprêtent à quitter le haut-plateau du nord pour entrer sur les terres de l'Empire, au niveau du col de K… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "95d6041f-8b31-4842-b614-1edfc899df97",
        "PublisherName": "AC MEDIA",
        "Rating": 4.5,
        "TotalRating": 14,
        "Slug": "frieren-t12",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 20437825
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "15ea0783-f890-482a-903b-480d8d703c40"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "9cf8e22d-756e-3704-9d21-35914a487359",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032713969",
        "PublicationDate": "2023-01-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "9cf8e22d-756e-3704-9d21-35914a487359",
        "Title": "Frieren T06",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Après la première épreuve de l'examen de magie, les candidats restants peuvent prendre un peu de repos. Seulement, Frieren et Stark… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "85c6d2c7-f8e8-4994-9f49-85cd5b7ac6e5",
        "PublisherName": "AC MEDIA",
        "Rating": 4.923077,
        "TotalRating": 13,
        "Slug": "frieren-t06",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 17765367
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "311897aa-9966-4fd1-9bd4-efcc8de704a5"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "6716d953-0e5d-3e02-bf0a-0ffa0101dc19",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032716120",
        "PublicationDate": "2024-12-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "6716d953-0e5d-3e02-bf0a-0ffa0101dc19",
        "Title": "Frieren T13",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Les démons ont remarqué les interférences spatio-temporelles provoquées par Frieren, et ils comptent bien lui soutirer le maximum … [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "ef00c4e2-229e-4ab6-a969-b719f38a523b",
        "PublisherName": "AC MEDIA",
        "Rating": 4.333333,
        "TotalRating": 15,
        "Slug": "frieren-t13",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 21562436
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "cf010cad-490a-46d3-8966-425fc63b06e0"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Géraldine Oudin",
        "WorkId": "d915a22d-fce0-347a-9b4a-95aca52ba15b",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032712559",
        "PublicationDate": "2022-10-13T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Géraldine Oudin"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "d915a22d-fce0-347a-9b4a-95aca52ba15b",
        "Title": "Frieren T05",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Tandis que Sein fait route seul vers Tür afin de retrouver son ami disparu, Fern et Frieren s'apprêtent à passer l'examen pour deve… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "b426790f-c1e2-4e4e-971d-d845f9b0f125",
        "PublisherName": "AC MEDIA",
        "Rating": 4.95,
        "TotalRating": 20,
        "Slug": "frieren-t05",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 20097178
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "3402b376-15c9-45e3-8ba0-7a8eb5007679"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "be7d4cf2-85b2-3dd7-ad2c-1eca8e7ff4c9",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032712351",
        "PublicationDate": "2022-07-07T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "be7d4cf2-85b2-3dd7-ad2c-1eca8e7ff4c9",
        "Title": "Frieren T04",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>En pleine forêt, Stark se fait mordre par un monstre venimeux ! Hélas, Frieren ne s'y connaît pas vraiment en poisons, il va lui fa… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "787ce930-6235-48da-ac4b-e5546d5cc5f7",
        "PublisherName": "AC MEDIA",
        "Rating": 4.809524,
        "TotalRating": 21,
        "Slug": "frieren-t04",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 16558453
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "937cf696-2e47-46ee-a43d-f55ed130f034"
      }
    },
    {
      "Book": {
        "Contributors": "Tsukasa Abe,Kanehito Yamada",
        "WorkId": "8f759571-3dc6-3916-92f2-cf29771032f5",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032724835",
        "PublicationDate": "2025-07-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Kanehito Yamada"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "8f759571-3dc6-3916-92f2-cf29771032f5",
        "Title": "Frieren T14",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>À peine arrivés à Eiseberg, la capitale, Frieren et ses compagnons sont appréhendés par Sense, qui a besoin de Fern pour une missio… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "3080e894-ba80-4e66-9ac4-3eb67712ad9b",
        "PublisherName": "AC MEDIA",
        "Rating": 4.142857,
        "TotalRating": 7,
        "Slug": "frieren-t14",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 21919511
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "5c931559-7e0f-4a9d-bc52-bc2582eb9084"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Géraldine Oudin",
        "WorkId": "2aa94509-062b-3c91-b7db-68724583936e",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032712399",
        "PublicationDate": "2022-05-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Géraldine Oudin"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "2aa94509-062b-3c91-b7db-68724583936e",
        "Title": "Frieren T03",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Stark, guerrier et disciple d'Eisen, a rejoint les deux mages dans leur périple. Ensemble, ils arrivent dans le comté de Granat, ma… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "26133556-0068-42da-8dde-3fb3a7e83ca6",
        "PublisherName": "AC MEDIA",
        "Rating": 4.565217,
        "TotalRating": 23,
        "Slug": "frieren-t03",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 19650134
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "edb408d4-48fc-46c1-94cf-4966641ddb84"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Géraldine Oudin",
        "WorkId": "cee70386-899d-3409-9850-f0106f8f62cb",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032711606",
        "PublicationDate": "2022-03-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Géraldine Oudin"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "cee70386-899d-3409-9850-f0106f8f62cb",
        "Title": "Frieren T02",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Frieren a désormais une compagne de route, Fern, car l'elfe a promis de veiller sur la protégée de Heiter à la mort de son ami. Ens… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "2394968b-872e-40f6-9f24-d4cebfef514f",
        "PublisherName": "AC MEDIA",
        "Rating": 4.761905,
        "TotalRating": 21,
        "Slug": "frieren-t02",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 19924621
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "8d27367b-782e-42b2-ba03-541ee3ef9fc6"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "192a879d-c317-37c2-88d0-8cfcbac6b745",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032724828",
        "PublicationDate": "2026-04-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "192a879d-c317-37c2-88d0-8cfcbac6b745",
        "Title": "Frieren T15",
        "Description": "<p>Que deviennent les héros une fois le mal vaincu ?</p><p>Alors que Frieren et Fern cherchent une solution pour soigner Stark, empoisonné par une flèche ennemie, un sauveur inattendu fait s… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "67e54286-101c-40cc-bd40-93af364fd665",
        "PublisherName": "AC MEDIA",
        "Rating": 4.9,
        "TotalRating": 10,
        "Slug": "frieren-t15",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 18236005
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "f882d705-8216-471e-9fa0-40a44c5953a7"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Géraldine Oudin",
        "WorkId": "7aad241f-f35f-36d7-bbe4-32dc10b65e9f",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032711613",
        "PublicationDate": "2022-03-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Géraldine Oudin"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "7aad241f-f35f-36d7-bbe4-32dc10b65e9f",
        "Title": "Frieren T01",
        "Description": "<p>Que deviennent les héros, une fois le mal vaincu ?</p><p>Le jeune héros Himmel et ses compagnons, l'elfe Frieren, le nain Eisen et le prêtre Heiter, rentrent victorieux de leur combat con… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "9edfdff5-5955-4213-8865-6e72ebe46719",
        "PublisherName": "AC MEDIA",
        "Rating": 4.590909,
        "TotalRating": 44,
        "Slug": "frieren-t01",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 16010234
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "46a46d5e-d54d-44f8-b783-0d2e46e981d0"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe",
        "WorkId": "9af0da93-320a-382f-9e6c-72e37e5c09c2",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032724842",
        "PublicationDate": "2024-12-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "9af0da93-320a-382f-9e6c-72e37e5c09c2",
        "Title": "Frieren Anthologie",
        "Description": "<p>Un recueil de 5 récits inédits dans l'univers de Frieren !</p><p>Avez-vous déjà imaginé Frieren en train de préparer à manger pour ses compagnons grâce à ses sorts, l'amitié naissante ent… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "98f2cd09-0c37-410c-b8e4-24d368dea1e8",
        "PublisherName": "AC MEDIA",
        "Rating": 4,
        "TotalRating": 4,
        "Slug": "frieren-anthologie",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 11436318
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "04c5bbdb-9475-4df1-834f-0a969b8c2070"
      }
    },
    {
      "Book": {
        "Contributors": "Kanehito Yamada,Tsukasa Abe,Mei Hachimoku,Leticia Jabur",
        "WorkId": "1bd5675b-8e38-3ad5-9f70-a6caa38c6a38",
        "SeriesName": "Frieren",
        "SeriesId": "01db7780-884a-5899-b844-6402d91ae926",
        "IsFree": false,
        "ISBN": "9791032721964",
        "PublicationDate": "2026-06-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Kanehito Yamada"
          },
          {
            "Name": "Tsukasa Abe"
          },
          {
            "Name": "Mei Hachimoku"
          },
          {
            "Name": "Leticia Jabur"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "1bd5675b-8e38-3ad5-9f70-a6caa38c6a38",
        "Title": "Frieren - Prélude",
        "Description": "<p>Prélude est un roman passionnant qui explore l'univers de Frieren avec justesse, en nous plongeant dans le quotidien à la fois ordinaire et empli de magie de ses personnages.</p><p>Retrou… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "7aede8a9-45fd-4b8e-ab9a-179501c3d9d2",
        "PublisherName": "AC MEDIA",
        "Rating": 5,
        "TotalRating": 2,
        "Slug": "frieren-prelude",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3548188
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 9.99
        },
        "LovePointsPrice": 4800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "099bea03-05d6-47a9-9344-ee9d97f7e941"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "f61e519a-7e23-3484-922b-25e3a750b2fd",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032716274",
        "PublicationDate": "2024-10-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "f61e519a-7e23-3484-922b-25e3a750b2fd",
        "Title": "Jujutsu Kaisen T25",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Megumi pensait pouvoir retrouver sa sœur durant la Traque meurtrière… mais c'est en réalité une exorciste du passé, Yorozu, qui a p… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "3da0c44e-f81f-4e5e-9a99-a6266257b833",
        "PublisherName": "AC MEDIA",
        "Rating": 4.703704,
        "TotalRating": 27,
        "Slug": "jujutsu-kaisen-t25",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 24455733
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "a3c0d3be-2efc-4c71-a1bc-08148df9a286"
      }
    },
    {
      "Book": {
        "Contributors": "Miyako Slocombe,Megumi Morino",
        "WorkId": "c5e24a10-22d7-3dba-96ed-7d82e805b067",
        "SeriesNumber": "13",
        "SeriesName": "À tes côtés",
        "SeriesId": "7b52f316-889b-570a-a3ef-4fdb41ae326d",
        "SeriesNumberFloat": 13,
        "IsFree": false,
        "ISBN": "9782385310134",
        "PublicationDate": "2024-03-14T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Miyako Slocombe"
          },
          {
            "Name": "Megumi Morino"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "e42d23c2-83fb-3c90-828c-c9e5d70c2196",
        "Title": "À tes côtés - Tome 13",
        "Description": "<p>Quand un après-midi d’hiver enneigé, Hotaru tend son parapluie à Hananoï, un de ses camarades de lycée qui vient de se faire larguer, elle n’imaginait pas encore que c’était le début d’un… [TRUNCATED 310 chars; original string value]",
        "Language": "fr",
        "ImageId": "b1d81335-091e-4cef-b2c5-6c4a35ac633e",
        "PublisherName": "Akata",
        "Rating": 4.5,
        "TotalRating": 4,
        "Slug": "a-tes-cotes-tome-13",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3474289
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.49
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "f36cc50d-4d4f-4466-b768-e75604873dea"
      }
    },
    {
      "Book": {
        "Contributors": "Yuto Suzuki",
        "WorkId": "b39261c3-3065-3c32-9507-a9440fe7b92f",
        "SeriesNumber": "13",
        "SeriesName": "Sakamoto Days",
        "SeriesId": "e168c97d-1b99-5d2b-9587-bd4394457ccc",
        "SeriesNumberFloat": 13,
        "IsFree": false,
        "ISBN": "9782331080562",
        "PublicationDate": "2024-05-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yuto Suzuki"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "b39261c3-3065-3c32-9507-a9440fe7b92f",
        "Title": "Sakamoto Days - Tome 13",
        "Description": "<p>Quelques années auparavant, alors qu’ils sont encore étudiants, Sakamoto, Nagumo et Akao font régner la terreur sur le campus de la JCC. Bientôt, la sanction tombe ! S’ils ne veulent pas … [TRUNCATED 251 chars; original string value]",
        "Language": "fr",
        "ImageId": "9a90c456-1618-4a29-9021-efa567a35af0",
        "PublisherName": "Glénat Manga",
        "Rating": 4.769231,
        "TotalRating": 13,
        "Slug": "sakamoto-days-tome-13",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6887992
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "c251a08b-8d91-410a-abb4-4d479d5f28b7"
      }
    },
    {
      "Book": {
        "Contributors": "Rifujin na Magonote,Yuka Fujikawa,Jean-Benoît Silvestre",
        "WorkId": "0656f5e4-ec46-3837-a89b-dcef4a0ea907",
        "IsFree": false,
        "ISBN": "9791041107896",
        "PublicationDate": "2024-01-10T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Rifujin na Magonote"
          },
          {
            "Name": "Yuka Fujikawa"
          },
          {
            "Name": "Jean-Benoît Silvestre"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "0656f5e4-ec46-3837-a89b-dcef4a0ea907",
        "Title": "Mushoku Tensei - Tome 19",
        "Description": "<p>On pardonne encore moins facilement une faute lorsqu’elle est commise par un membre de sa famille.\"Je me suis donné du mal ! Pourquoi c’est Norn qui est récompensée ?\"Louiseld a accompagn… [TRUNCATED 310 chars; original string value]",
        "Language": "fr",
        "ImageId": "c7d7097a-db54-4e8a-baea-54f6057a7cec",
        "PublisherName": "Bamboo",
        "Rating": 4.5,
        "TotalRating": 4,
        "Slug": "mushoku-tensei-tome-19",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3638823
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "fc237293-5818-43c7-8db5-9ab42795393b"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "9ce46a76-98e1-3b45-869c-8503bca0ae20",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032716236",
        "PublicationDate": "2024-04-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "9ce46a76-98e1-3b45-869c-8503bca0ae20",
        "Title": "Jujutsu Kaisen T23",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Flanqué de Fumihiko, un humoriste pour le moins détonnant, Yuji retrouve enfin Megumi, récupéré juste à temps par Hana après son … [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "556d76a8-eb32-49eb-a9f7-312e22d53a21",
        "PublisherName": "AC MEDIA",
        "Rating": 4.733333,
        "TotalRating": 30,
        "Slug": "jujutsu-kaisen-t23",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 22249030
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "4192a527-7d89-443a-9fb5-e884ed612e09"
      }
    },
    {
      "Book": {
        "Contributors": "Yuto Suzuki",
        "WorkId": "5ee9ec11-23f5-3f25-baeb-f092164e68b2",
        "SeriesNumber": "12",
        "SeriesName": "Sakamoto Days",
        "SeriesId": "e168c97d-1b99-5d2b-9587-bd4394457ccc",
        "SeriesNumberFloat": 12,
        "IsFree": false,
        "ISBN": "9782331080319",
        "PublicationDate": "2024-02-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yuto Suzuki"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "5ee9ec11-23f5-3f25-baeb-f092164e68b2",
        "Title": "Sakamoto Days - Tome 12",
        "Description": "<p>Les duels de Shishiba contre Yotsumura et d’Osaragi contre une geiko transforment la ville de Kyoto en champ de bataille sanglant ! Shishiba et Yotsumura, autrefois élève et maître, sont … [TRUNCATED 206 chars; original string value]",
        "Language": "fr",
        "ImageId": "e77eb70b-c106-45ed-886f-0b3ebc5e9ebf",
        "PublisherName": "Glénat Manga",
        "Rating": 4.6,
        "TotalRating": 10,
        "Slug": "sakamoto-days-tome-12",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6262454
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "0ba6a7cb-db16-4e8a-a5a3-3940fbfddd24"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "735ef4db-6915-33c1-b054-fc20de61bf3d",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032720448",
        "PublicationDate": "2025-04-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "735ef4db-6915-33c1-b054-fc20de61bf3d",
        "Title": "Jujutsu Kaisen T26",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Grâce à l'ange, le sceau qui retenait Satoru Gojo prisonnier de la Lisière du supplice a enfin été levé ! Ainsi, l'exorciste le plu… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "4fd0608a-4707-4e18-9dc9-0f6f37186b03",
        "PublisherName": "AC MEDIA",
        "Rating": 4.793103,
        "TotalRating": 29,
        "Slug": "jujutsu-kaisen-t26",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 21383403
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "4889edbd-b067-4547-983e-673a33fc8d20"
      }
    },
    {
      "Book": {
        "Contributors": "Tail Yuzuhara,Sora",
        "WorkId": "d55ebe42-b72b-329d-9802-a091789310a6",
        "SeriesName": "Sorcière d'un autre monde",
        "SeriesId": "b877f10e-aaaa-5f23-b69f-ccb90c65749e",
        "IsFree": false,
        "ISBN": "9782413056287",
        "PublicationDate": "2023-07-05T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Tail Yuzuhara"
          },
          {
            "Name": "Sora"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "3b5f8815-2331-39e8-825f-01ebfb93c19f",
        "Title": "Sorcière d'un autre monde T03",
        "Description": "<p>Sena et Keith regagnent Beclair après avoir apaisé Rishkall, le Roi-dragon du Vent. Tout le royaume est en liesse suite au retour triomphal de l'invocatrice, mais une missive de l'empire … [TRUNCATED 212 chars; original string value]",
        "Language": "fr",
        "ImageId": "b65aecb1-aaf5-431a-b3a3-d7297a0cac6c",
        "PublisherName": "GROUPE DELCOURT BD",
        "Rating": 4.75,
        "TotalRating": 4,
        "Slug": "sorciere-d-un-autre-monde-t03",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 21149408
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "30054149-0253-44fa-adc3-fd09e81149d8"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "75a9d548-36c4-38fb-a300-f0c42cd752c4",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032716250",
        "PublicationDate": "2024-07-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "75a9d548-36c4-38fb-a300-f0c42cd752c4",
        "Title": "Jujutsu Kaisen T24",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Malgré un combat dantesque face à Yuki Tsukumo, Kenjaku parvient à tirer son épingle du jeu afin de poursuivre son but : permettre … [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "7db677ec-9f4c-47b1-914e-d1bca980c453",
        "PublisherName": "AC MEDIA",
        "Rating": 4.9,
        "TotalRating": 30,
        "Slug": "jujutsu-kaisen-t24",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 20896465
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "5d1ef60d-5d37-4072-8ddb-5ae7b34eef72"
      }
    },
    {
      "Book": {
        "Contributors": "Mozuku Sora,Higoro Toumori",
        "WorkId": "eb1cac1e-1569-328b-a4bf-e689481c574d",
        "SeriesName": "The Bugle Call",
        "SeriesId": "b92c9233-9e0e-5be1-8f74-2437ed012156",
        "IsFree": false,
        "ISBN": "9791032717660",
        "PublicationDate": "2024-03-07T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Mozuku Sora"
          },
          {
            "Name": "Higoro Toumori"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "eb1cac1e-1569-328b-a4bf-e689481c574d",
        "Title": "The Bugle Call T01",
        "Description": "<p>Transcender le fracas des armes, décider du sort de la bataille !</p><p>Luka, orphelin recueilli par le chef d'un groupe de mercenaires, n'est pas comme les autres : une branche pousse su… [TRUNCATED 309 chars; original string value]",
        "Language": "fr",
        "ImageId": "93dd4490-23d1-458d-8b65-785be98f92d2",
        "PublisherName": "AC MEDIA",
        "Rating": 4.6,
        "TotalRating": 5,
        "Slug": "the-bugle-call-t01",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 26944006
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "d653591c-9e8f-4425-9010-07fe4d37a158"
      }
    },
    {
      "Book": {
        "Contributors": "Yuto Suzuki",
        "WorkId": "db38972b-0fc5-3881-a7c5-17a26fe4fc00",
        "SeriesNumber": "14",
        "SeriesName": "Sakamoto Days",
        "SeriesId": "e168c97d-1b99-5d2b-9587-bd4394457ccc",
        "SeriesNumberFloat": 14,
        "IsFree": false,
        "ISBN": "9782331080739",
        "PublicationDate": "2024-07-03T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Yuto Suzuki"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "db38972b-0fc5-3881-a7c5-17a26fe4fc00",
        "Title": "Sakamoto Days - Tome 14",
        "Description": "<p>Sakamoto, Nagumo et Akao, le trio d’étudiants terribles, escortent des VIP en compagnie de Kindaka, un membre de l’Ordre. Ils sont aidés dans cette mission par un autre étudiant, Uzuki ! … [TRUNCATED 257 chars; original string value]",
        "Language": "fr",
        "ImageId": "5d4f0cb7-e42a-4df9-b101-fe315dcf334a",
        "PublisherName": "Glénat Manga",
        "Rating": 4.2,
        "TotalRating": 5,
        "Slug": "sakamoto-days-tome-14",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6287649
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "84c8d38c-efad-407c-9f8e-2b9060eca64a"
      }
    },
    {
      "Book": {
        "Contributors": "Mozuku Sora,Higoro Toumori",
        "WorkId": "2c34e431-a396-309f-ba3b-83829da356b3",
        "SeriesName": "The Bugle Call",
        "SeriesId": "b92c9233-9e0e-5be1-8f74-2437ed012156",
        "IsFree": false,
        "ISBN": "9791032717677",
        "PublicationDate": "2024-05-02T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Mozuku Sora"
          },
          {
            "Name": "Higoro Toumori"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "2c34e431-a396-309f-ba3b-83829da356b3",
        "Title": "The Bugle Call T02",
        "Description": "<p>Transcender le fracas des armes, décider du sort de la bataille !</p><p>Luka a accepté de travailler pour le pape à la condition que ce dernier finance ses études de musique, mais le fait… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "db3c9419-85e5-494d-819b-27319275e1e7",
        "PublisherName": "AC MEDIA",
        "Rating": 4.8,
        "TotalRating": 5,
        "Slug": "the-bugle-call-t02",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 24348461
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "5848bc83-4914-4ae9-9e0d-013067668019"
      }
    },
    {
      "Book": {
        "Contributors": "Tail Yuzuhara,Sora",
        "WorkId": "8d10b3c6-42ac-3c74-ace0-7103040801e1",
        "SeriesName": "Sorcière d'un autre monde",
        "SeriesId": "b877f10e-aaaa-5f23-b69f-ccb90c65749e",
        "IsFree": false,
        "ISBN": "9782413059103",
        "PublicationDate": "2023-11-15T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Tail Yuzuhara"
          },
          {
            "Name": "Sora"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "f89c2289-f376-339c-8ce1-77ed121281a2",
        "Title": "Sorcière d'un autre monde T04",
        "Description": "<p>Convoquée par Isaac, l'illustre chef suprême considéré comme le plus puissant du continent, Sena accepte de se rendre à Fonduna, empire voisin de Beclair. Accompagnée de Keith, elle y ren… [TRUNCATED 250 chars; original string value]",
        "Language": "fr",
        "ImageId": "c220dc12-f0e7-4b65-b90a-c035dc7fed77",
        "PublisherName": "GROUPE DELCOURT BD",
        "Rating": 4.75,
        "TotalRating": 4,
        "Slug": "sorciere-d-un-autre-monde-t04",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 22644506
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "50d1318c-3fde-4d67-b55a-b8c46cd2f77c"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "7d5f1433-2db2-3963-937a-e36275d10110",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032716212",
        "PublicationDate": "2024-01-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "7d5f1433-2db2-3963-937a-e36275d10110",
        "Title": "Jujutsu Kaisen T22",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Après avoir montré toute l'étendue de sa force à ses deux adversaires, Kinji s'allie avec Hajime Kashimo, un exorciste du passé qui… [TRUNCATED 290 chars; original string value]",
        "Language": "fr",
        "ImageId": "2213bbec-5200-40a6-9169-633cd6b802ee",
        "PublisherName": "AC MEDIA",
        "Rating": 4.756757,
        "TotalRating": 37,
        "Slug": "jujutsu-kaisen-t22",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 6761617
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "d76407a9-eccf-46d7-bb65-85b3b9469abe"
      }
    },
    {
      "Book": {
        "Contributors": "Rifujin na Magonote,Yuka Fujikawa,Jean-Benoît Silvestre",
        "WorkId": "edb1eb3d-c8a1-3861-8f05-865982b6b5a4",
        "SeriesNumber": "20",
        "SeriesName": "Mushoku Tensei",
        "SeriesId": "42d14458-247b-5049-92b4-6403c7d66e43",
        "SeriesNumberFloat": 20,
        "IsFree": false,
        "ISBN": "9791041112265",
        "PublicationDate": "2024-08-21T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Rifujin na Magonote"
          },
          {
            "Name": "Yuka Fujikawa"
          },
          {
            "Name": "Jean-Benoît Silvestre"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "edb1eb3d-c8a1-3861-8f05-865982b6b5a4",
        "Title": "Mushoku Tensei - Tome 20",
        "Description": "<p>Norn s’est cloîtrée dans sa chambre à cause de Rudeus ?! Mais cette fois-ci, il ne cherchera pas à fuir ses responsabilités !</p><p>\"La vie d’étudiante de Norn est perturbée par un terrib… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "8f3b37e0-e427-4fb1-81c4-973225f62c24",
        "PublisherName": "Bamboo",
        "Rating": 5,
        "TotalRating": 2,
        "Slug": "mushoku-tensei-tome-20",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 3435467
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "6b59f70c-c8fb-41e9-a1da-d434a040646f"
      }
    },
    {
      "Book": {
        "Contributors": "You Chiba",
        "WorkId": "dd869f49-f6fa-33e5-9de1-1de8c822bde1",
        "SeriesName": "Kindergarten Wars",
        "SeriesId": "2042eff9-6c58-57df-a2d2-29f8167764e3",
        "IsFree": false,
        "ISBN": "9791032718070",
        "PublicationDate": "2024-04-04T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "You Chiba"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "dd869f49-f6fa-33e5-9de1-1de8c822bde1",
        "Title": "Kindergarten Wars T01",
        "Description": "<p>Découvrez la maternelle la plus sécurisée du monde et ses enseignants au tempérament… explosif !</p><p>Rita est la dernière recrue du “Kindergarten Black”, surnommé “la maternelle la plus… [TRUNCATED 309 chars; original string value]",
        "Language": "fr",
        "ImageId": "2a2a8b10-fadf-45a3-940a-0a56923128e6",
        "PublisherName": "AC MEDIA",
        "Rating": 4.8,
        "TotalRating": 25,
        "Slug": "kindergarten-wars-t01",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 21965734
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "cdd54c63-83fd-4e81-a7c6-d55523f8d468"
      }
    },
    {
      "Book": {
        "Contributors": "Gege Akutami",
        "WorkId": "ab15ca93-e7e8-3b2d-beac-cfa31a3d5e70",
        "SeriesName": "Jujutsu Kaisen",
        "SeriesId": "1c80d5b8-5314-5554-a2ef-8d085b227513",
        "IsFree": false,
        "ISBN": "9791032714058",
        "PublicationDate": "2023-07-06T00:00:00.0000000Z",
        "ContributorRoles": [
          {
            "Name": "Gege Akutami"
          }
        ],
        "IsRecommendation": false,
        "CrossRevisionId": "ab15ca93-e7e8-3b2d-beac-cfa31a3d5e70",
        "Title": "Jujutsu Kaisen T20",
        "Description": "<p>Engloutir les ténèbres pour mieux les combattre !</p><p>Après un combat éprouvant contre Hiromi Higuruma, un exorciste doté d'un sort étonnant capable d'innocenter ou de condamner un adve… [TRUNCATED 308 chars; original string value]",
        "Language": "fr",
        "ImageId": "e97c5951-1e6f-4122-b89f-8beef15a0a45",
        "PublisherName": "AC MEDIA",
        "Rating": 4.757576,
        "TotalRating": 33,
        "Slug": "jujutsu-kaisen-t20",
        "IsContentSharingEnabled": true,
        "RedirectPreviewUrls": [
          {
            "DrmType": "None",
            "Format": "EPUB3FL_SAMPLE",
            "Url": "https://storedownloads.kobo.com/download?downloadToken=[REDACTED]",
            "Platform": "Generic",
            "Size": 7935961
          }
        ],
        "HasPreview": true,
        "Price": {
          "Currency": "EUR",
          "Price": 4.99
        },
        "LovePointsPrice": 2800,
        "EligibleForKoboLoveDiscount": false,
        "IsPreOrder": false,
        "AgeVerificationRequired": false,
        "Id": "67e8d12f-7c64-46b0-b7a0-bde0ca5de851"
      }
    }
  ],
  "ItemCount": 33,
  "TotalPageCount": 1,
  "TotalItemCount": 33,
  "CurrentPageIndex": 0,
  "ItemsPerPage": 100,
  "VersionCode": 2
}
```


## 287. not logged `/v1/user/reviews` — Komga upstream standard proxy log response

Source: `server:L1419`. HTTP: `200`. Captured type: `object (3 top-level keys)`. Fixture: `upstream/unknown_v1_user_reviews/response_L1419.json`.

```json
{
  "Items": [],
  "TotalPageCount": 0,
  "CurrentPageIndex": 1
}
```


## 288. not logged `/v1/products` — Komga upstream standard proxy log response

Source: `server:L1424`. HTTP: `200`. Captured type: `object (9 top-level keys)`. Fixture: `upstream/unknown_v1_products/response_L1424.json`.

**Complete captured JSON:** [Open the standalone JSON fixture](../payloads/large-json/ledger-large-011.json) (same keys, types, nesting and array entries; shown separately to keep the Markdown page renderable).


## 289. not logged `/v1/products` — Komga upstream standard proxy log response

Source: `server:L1487`. HTTP: `200`. Captured type: `object (9 top-level keys)`. Fixture: `upstream/unknown_v1_products/response_L1487.json`.

**Complete captured JSON:** [Open the standalone JSON fixture](../payloads/large-json/ledger-large-012.json) (same keys, types, nesting and array entries; shown separately to keep the Markdown page renderable).

