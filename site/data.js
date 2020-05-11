"use strict"

const chartData = {
  "IntToInt": [
    {
      "name": "EC",
      "data": [
        {
          "size": 10000,
          "get": 1024,
          "put": 1364,
          "remove": 2373
        },
        {
          "size": 100000,
          "get": 1565,
          "put": 1948,
          "remove": 2666
        },
        {
          "size": 1000000,
          "get": 2771,
          "put": 5982,
          "remove": 5332
        },
        {
          "size": 10000000,
          "get": 4285,
          "put": 7811,
          "remove": 8192
        },
        {
          "size": 100000000,
          "get": 5478,
          "put": 9591,
          "remove": 8246
        }
      ]
    },
    {
      "name": "Fastutil",
      "data": [
        {
          "size": 10000,
          "get": 778,
          "put": 1034,
          "remove": 728
        },
        {
          "size": 100000,
          "get": 1188,
          "put": 1553,
          "remove": 1315
        },
        {
          "size": 1000000,
          "get": 2739,
          "put": 4542,
          "remove": 3141
        },
        {
          "size": 10000000,
          "get": 4065,
          "put": 9159,
          "remove": 6283
        },
        {
          "size": 100000000,
          "get": 5588,
          "put": 11489,
          "remove": 8316
        }
      ]
    },
    {
      "name": "HPPC",
      "data": [
        {
          "size": 10000,
          "get": 838,
          "put": 1214,
          "remove": 1159
        },
        {
          "size": 100000,
          "get": 1413,
          "put": 1681,
          "remove": 1581
        },
        {
          "size": 1000000,
          "get": 3172,
          "put": 4682,
          "remove": 3157
        },
        {
          "size": 10000000,
          "get": 4711,
          "put": 8800,
          "remove": 6282
        },
        {
          "size": 100000000,
          "get": 5879,
          "put": 11261,
          "remove": 7988
        }
      ]
    },
    {
      "name": "Koloboke",
      "data": [
        {
          "size": 10000,
          "get": 669,
          "put": 928,
          "remove": 647
        },
        {
          "size": 100000,
          "get": 1336,
          "put": 1907,
          "remove": 1277
        },
        {
          "size": 1000000,
          "get": 2667,
          "put": 4310,
          "remove": 2631
        },
        {
          "size": 10000000,
          "get": 3177,
          "put": 5677,
          "remove": 5506
        },
        {
          "size": 100000000,
          "get": 4179,
          "put": 8061,
          "remove": 6544
        }
      ]
    },
    {
      "name": "Trove",
      "data": [
        {
          "size": 10000,
          "get": 2068,
          "put": 2509,
          "remove": 2764
        },
        {
          "size": 100000,
          "get": 2442,
          "put": 3228,
          "remove": 3226
        },
        {
          "size": 1000000,
          "get": 5196,
          "put": 8155,
          "remove": 4889
        },
        {
          "size": 10000000,
          "get": 9604,
          "put": 18844,
          "remove": 13822
        },
        {
          "size": 100000000,
          "get": 12656,
          "put": 24063,
          "remove": 18543
        }
      ]
    },
    {
      "name": "Trove JB",
      "data": [
        {
          "size": 10000,
          "get": 2225,
          "put": 2564,
          "remove": 3182
        },
        {
          "size": 100000,
          "get": 2696,
          "put": 3512,
          "remove": 3678
        },
        {
          "size": 1000000,
          "get": 5490,
          "put": 8302,
          "remove": 5593
        },
        {
          "size": 10000000,
          "get": 9955,
          "put": 16099,
          "remove": 13031
        },
        {
          "size": 100000000,
          "get": 13075,
          "put": 22556,
          "remove": 19636
        }
      ]
    }
  ],
  "ObjectToObject": [
    {
      "name": "EC",
      "data": [
        {
          "size": 10000,
          "get": 1236,
          "put": 2786,
          "remove": 1855
        },
        {
          "size": 100000,
          "get": 1884,
          "put": 8522,
          "remove": 2591
        },
        {
          "size": 1000000,
          "get": 6857,
          "put": 17859,
          "remove": 9092
        },
        {
          "size": 10000000,
          "get": 7226,
          "put": 19364,
          "remove": 11791
        },
        {
          "size": 100000000,
          "get": 9975,
          "put": 41120,
          "remove": 19595
        }
      ]
    },
    {
      "name": "Fastutil",
      "data": [
        {
          "size": 10000,
          "get": 1097,
          "put": 2098,
          "remove": 1647
        },
        {
          "size": 100000,
          "get": 1748,
          "put": 3054,
          "remove": 2421
        },
        {
          "size": 1000000,
          "get": 5872,
          "put": 25508,
          "remove": 9915
        },
        {
          "size": 10000000,
          "get": 6400,
          "put": 22043,
          "remove": 15988
        },
        {
          "size": 100000000,
          "get": 10570,
          "put": 42876,
          "remove": 28093
        }
      ]
    },
    {
      "name": "HPPC",
      "data": [
        {
          "size": 10000,
          "get": 1336,
          "put": 2503,
          "remove": 1947
        },
        {
          "size": 100000,
          "get": 2189,
          "put": 3525,
          "remove": 2790
        },
        {
          "size": 1000000,
          "get": 6761,
          "put": 27779,
          "remove": 10013
        },
        {
          "size": 10000000,
          "get": 7368,
          "put": 22771,
          "remove": 13400
        },
        {
          "size": 100000000,
          "get": 11865,
          "put": 51795,
          "remove": 28400
        }
      ]
    },
    {
      "name": "JDK",
      "data": [
        {
          "size": 10000,
          "get": 954,
          "put": 4636,
          "remove": 2605
        },
        {
          "size": 100000,
          "get": 1185,
          "put": 7372,
          "remove": 3155
        },
        {
          "size": 1000000,
          "get": 5123,
          "put": 19003,
          "remove": 8010
        },
        {
          "size": 10000000,
          "get": 6136,
          "put": 33358,
          "remove": 17539
        },
        {
          "size": 100000000,
          "get": 8378,
          "put": 61827,
          "remove": 37747
        }
      ]
    },
    {
      "name": "JDK (different capacity)",
      "data": [
        {
          "size": 10000,
          "get": 947,
          "put": 3165,
          "remove": 1712
        },
        {
          "size": 100000,
          "get": 1138,
          "put": 5109,
          "remove": 2451
        },
        {
          "size": 1000000,
          "get": 5103,
          "put": 16574,
          "remove": 6914
        },
        {
          "size": 10000000,
          "get": 5126,
          "put": 25991,
          "remove": 12798
        },
        {
          "size": 100000000,
          "get": 8196,
          "put": 55876,
          "remove": 36407
        }
      ]
    },
    {
      "name": "Koloboke",
      "data": [
        {
          "size": 10000,
          "get": 1517,
          "put": 2489,
          "remove": 1899
        },
        {
          "size": 100000,
          "get": 1999,
          "put": 5833,
          "remove": 2583
        },
        {
          "size": 1000000,
          "get": 7885,
          "put": 27332,
          "remove": 10306
        },
        {
          "size": 10000000,
          "get": 7563,
          "put": 19196,
          "remove": 13240
        },
        {
          "size": 100000000,
          "get": 11994,
          "put": 34898,
          "remove": 22608
        }
      ]
    },
    {
      "name": "Koloboke mixing keys' hashes",
      "data": [
        {
          "size": 10000,
          "get": 1219,
          "put": 3075,
          "remove": 2039
        },
        {
          "size": 100000,
          "get": 1774,
          "put": 6591,
          "remove": 2762
        },
        {
          "size": 1000000,
          "get": 6280,
          "put": 29269,
          "remove": 11089
        },
        {
          "size": 10000000,
          "get": 6069,
          "put": 22280,
          "remove": 14344
        },
        {
          "size": 100000000,
          "get": 9131,
          "put": 41858,
          "remove": 22537
        }
      ]
    },
    {
      "name": "Koloboke not null key",
      "data": [
        {
          "size": 10000,
          "get": 1515,
          "put": 2566,
          "remove": 2018
        },
        {
          "size": 100000,
          "get": 2003,
          "put": 5869,
          "remove": 2605
        },
        {
          "size": 1000000,
          "get": 7874,
          "put": 27526,
          "remove": 10330
        },
        {
          "size": 10000000,
          "get": 7572,
          "put": 19702,
          "remove": 13140
        },
        {
          "size": 100000000,
          "get": 12122,
          "put": 34584,
          "remove": 22444
        }
      ]
    },
    {
      "name": "Trove",
      "data": [
        {
          "size": 10000,
          "get": 2329,
          "put": 4223,
          "remove": 4053
        },
        {
          "size": 100000,
          "get": 3020,
          "put": 5898,
          "remove": 4773
        },
        {
          "size": 1000000,
          "get": 9125,
          "put": 33808,
          "remove": 13146
        },
        {
          "size": 10000000,
          "get": 13025,
          "put": 29488,
          "remove": 20224
        },
        {
          "size": 100000000,
          "get": 18374,
          "put": 53873,
          "remove": 33872
        }
      ]
    },
    {
      "name": "Trove JB",
      "data": [
        {
          "size": 10000,
          "get": 2543,
          "put": 4577,
          "remove": 3649
        },
        {
          "size": 100000,
          "get": 3105,
          "put": 6110,
          "remove": 4419
        },
        {
          "size": 1000000,
          "get": 8926,
          "put": 27078,
          "remove": 11957
        },
        {
          "size": 10000000,
          "get": 12625,
          "put": 33461,
          "remove": 19113
        },
        {
          "size": 100000000,
          "get": 17437,
          "put": 64130,
          "remove": 36608
        }
      ]
    }
  ],
  "IntToObject": [
    {
      "name": "EC",
      "data": [
        {
          "size": 10000,
          "get": 841,
          "put": 1311,
          "remove": 2728
        },
        {
          "size": 100000,
          "get": 1711,
          "put": 2226,
          "remove": 3072
        },
        {
          "size": 1000000,
          "get": 3532,
          "put": 6305,
          "remove": 7335
        },
        {
          "size": 10000000,
          "get": 4756,
          "put": 9435,
          "remove": 10980
        },
        {
          "size": 100000000,
          "get": 6471,
          "put": 12576,
          "remove": 13811
        }
      ]
    },
    {
      "name": "Fastutil",
      "data": [
        {
          "size": 10000,
          "get": 787,
          "put": 1101,
          "remove": 1203
        },
        {
          "size": 100000,
          "get": 1118,
          "put": 1635,
          "remove": 1811
        },
        {
          "size": 1000000,
          "get": 3021,
          "put": 4687,
          "remove": 4360
        },
        {
          "size": 10000000,
          "get": 4298,
          "put": 8540,
          "remove": 8918
        },
        {
          "size": 100000000,
          "get": 5642,
          "put": 11601,
          "remove": 13988
        }
      ]
    },
    {
      "name": "HPPC",
      "data": [
        {
          "size": 10000,
          "get": 1028,
          "put": 1491,
          "remove": 1640
        },
        {
          "size": 100000,
          "get": 1492,
          "put": 2105,
          "remove": 2225
        },
        {
          "size": 1000000,
          "get": 3294,
          "put": 5594,
          "remove": 4551
        },
        {
          "size": 10000000,
          "get": 4785,
          "put": 8777,
          "remove": 9311
        },
        {
          "size": 100000000,
          "get": 6018,
          "put": 11087,
          "remove": 13859
        }
      ]
    },
    {
      "name": "Koloboke",
      "data": [
        {
          "size": 10000,
          "get": 926,
          "put": 1073,
          "remove": 1290
        },
        {
          "size": 100000,
          "get": 1194,
          "put": 1838,
          "remove": 2026
        },
        {
          "size": 1000000,
          "get": 3097,
          "put": 5303,
          "remove": 4301
        },
        {
          "size": 10000000,
          "get": 4269,
          "put": 8597,
          "remove": 8888
        },
        {
          "size": 100000000,
          "get": 5367,
          "put": 11652,
          "remove": 13560
        }
      ]
    },
    {
      "name": "Trove",
      "data": [
        {
          "size": 10000,
          "get": 2038,
          "put": 2851,
          "remove": 3156
        },
        {
          "size": 100000,
          "get": 2528,
          "put": 3683,
          "remove": 3638
        },
        {
          "size": 1000000,
          "get": 5132,
          "put": 9140,
          "remove": 6615
        },
        {
          "size": 10000000,
          "get": 9517,
          "put": 18716,
          "remove": 15089
        },
        {
          "size": 100000000,
          "get": 12449,
          "put": 23786,
          "remove": 18283
        }
      ]
    },
    {
      "name": "Trove JB",
      "data": [
        {
          "size": 10000,
          "get": 2159,
          "put": 3436,
          "remove": 3263
        },
        {
          "size": 100000,
          "get": 2739,
          "put": 4415,
          "remove": 4152
        },
        {
          "size": 1000000,
          "get": 6165,
          "put": 13440,
          "remove": 7446
        },
        {
          "size": 10000000,
          "get": 10161,
          "put": 18801,
          "remove": 18012
        },
        {
          "size": 100000000,
          "get": 11995,
          "put": 36144,
          "remove": 25799
        }
      ]
    }
  ],
  "ObjectToInt": [
    {
      "name": "EC",
      "data": [
        {
          "size": 10000,
          "get": 1544,
          "put": 1964,
          "remove": 3257
        },
        {
          "size": 100000,
          "get": 2067,
          "put": 3037,
          "remove": 3582
        },
        {
          "size": 1000000,
          "get": 7992,
          "put": 12852,
          "remove": 10860
        },
        {
          "size": 10000000,
          "get": 8341,
          "put": 13639,
          "remove": 16530
        },
        {
          "size": 100000000,
          "get": 14747,
          "put": 28667,
          "remove": 26582
        }
      ]
    },
    {
      "name": "Fastutil",
      "data": [
        {
          "size": 10000,
          "get": 1217,
          "put": 1724,
          "remove": 1296
        },
        {
          "size": 100000,
          "get": 1591,
          "put": 2649,
          "remove": 2063
        },
        {
          "size": 1000000,
          "get": 5983,
          "put": 11064,
          "remove": 8392
        },
        {
          "size": 10000000,
          "get": 6305,
          "put": 11653,
          "remove": 10552
        },
        {
          "size": 100000000,
          "get": 11087,
          "put": 26687,
          "remove": 17445
        }
      ]
    },
    {
      "name": "HPPC",
      "data": [
        {
          "size": 10000,
          "get": 1372,
          "put": 2157,
          "remove": 1636
        },
        {
          "size": 100000,
          "get": 1947,
          "put": 2966,
          "remove": 2381
        },
        {
          "size": 1000000,
          "get": 6882,
          "put": 13191,
          "remove": 8371
        },
        {
          "size": 10000000,
          "get": 7000,
          "put": 13783,
          "remove": 11463
        },
        {
          "size": 100000000,
          "get": 11821,
          "put": 27869,
          "remove": 18508
        }
      ]
    },
    {
      "name": "Koloboke",
      "data": [
        {
          "size": 10000,
          "get": 1355,
          "put": 2197,
          "remove": 1754
        },
        {
          "size": 100000,
          "get": 1731,
          "put": 3002,
          "remove": 2497
        },
        {
          "size": 1000000,
          "get": 7392,
          "put": 13157,
          "remove": 8821
        },
        {
          "size": 10000000,
          "get": 7210,
          "put": 14026,
          "remove": 12209
        },
        {
          "size": 100000000,
          "get": 13834,
          "put": 27518,
          "remove": 20123
        }
      ]
    },
    {
      "name": "Trove",
      "data": [
        {
          "size": 10000,
          "get": 2436,
          "put": 4249,
          "remove": 3694
        },
        {
          "size": 100000,
          "get": 2911,
          "put": 4999,
          "remove": 4416
        },
        {
          "size": 1000000,
          "get": 9149,
          "put": 21417,
          "remove": 11907
        },
        {
          "size": 10000000,
          "get": 12165,
          "put": 25005,
          "remove": 20045
        },
        {
          "size": 100000000,
          "get": 16622,
          "put": 35397,
          "remove": 25940
        }
      ]
    },
    {
      "name": "Trove JB",
      "data": [
        {
          "size": 10000,
          "get": 2538,
          "put": 3868,
          "remove": 3374
        },
        {
          "size": 100000,
          "get": 3097,
          "put": 5179,
          "remove": 4173
        },
        {
          "size": 1000000,
          "get": 9544,
          "put": 27425,
          "remove": 11235
        },
        {
          "size": 10000000,
          "get": 12659,
          "put": 24943,
          "remove": 19049
        },
        {
          "size": 100000000,
          "get": 17151,
          "put": 42197,
          "remove": 27455
        }
      ]
    }
  ],
  "ReferenceToObject": [
    {
      "name": "EC",
      "data": [
        {
          "size": 10000,
          "get": 833,
          "put": 2819,
          "remove": 1971
        },
        {
          "size": 100000,
          "get": 1171,
          "put": 6571,
          "remove": 2451
        },
        {
          "size": 1000000,
          "get": 4295,
          "put": 15614,
          "remove": 8292
        },
        {
          "size": 10000000,
          "get": 4551,
          "put": 17618,
          "remove": 11212
        },
        {
          "size": 100000000,
          "get": 5911,
          "put": 38272,
          "remove": 18915
        }
      ]
    },
    {
      "name": "Fastutil",
      "data": [
        {
          "size": 10000,
          "get": 651,
          "put": 1921,
          "remove": 1932
        },
        {
          "size": 100000,
          "get": 1295,
          "put": 2561,
          "remove": 2756
        },
        {
          "size": 1000000,
          "get": 3321,
          "put": 32489,
          "remove": 11257
        },
        {
          "size": 10000000,
          "get": 3855,
          "put": 28169,
          "remove": 14721
        },
        {
          "size": 100000000,
          "get": 4653,
          "put": 37780,
          "remove": 25794
        }
      ]
    },
    {
      "name": "HPPC",
      "data": [
        {
          "size": 10000,
          "get": 875,
          "put": 2286,
          "remove": 2027
        },
        {
          "size": 100000,
          "get": 1464,
          "put": 2985,
          "remove": 2784
        },
        {
          "size": 1000000,
          "get": 3297,
          "put": 34278,
          "remove": 11449
        },
        {
          "size": 10000000,
          "get": 4334,
          "put": 17796,
          "remove": 13050
        },
        {
          "size": 100000000,
          "get": 5167,
          "put": 39791,
          "remove": 25820
        }
      ]
    },
    {
      "name": "JDK",
      "data": [
        {
          "size": 10000,
          "get": 1534,
          "put": 2822,
          "remove": 2447
        },
        {
          "size": 100000,
          "get": 1238,
          "put": 4106,
          "remove": 2441
        },
        {
          "size": 1000000,
          "get": 3749,
          "put": 30916,
          "remove": 10525
        },
        {
          "size": 10000000,
          "get": 6971,
          "put": 21216,
          "remove": 14958
        },
        {
          "size": 100000000,
          "get": 5175,
          "put": 28797,
          "remove": 15984
        }
      ]
    },
    {
      "name": "Koloboke",
      "data": [
        {
          "size": 10000,
          "get": 967,
          "put": 2924,
          "remove": 2104
        },
        {
          "size": 100000,
          "get": 1524,
          "put": 5781,
          "remove": 2839
        },
        {
          "size": 1000000,
          "get": 4494,
          "put": 13872,
          "remove": 10134
        },
        {
          "size": 10000000,
          "get": 4319,
          "put": 23498,
          "remove": 16473
        },
        {
          "size": 100000000,
          "get": 5456,
          "put": 39224,
          "remove": 22269
        }
      ]
    },
    {
      "name": "Trove",
      "data": [
        {
          "size": 10000,
          "get": 2489,
          "put": 4124,
          "remove": 4034
        },
        {
          "size": 100000,
          "get": 2844,
          "put": 5259,
          "remove": 5087
        },
        {
          "size": 1000000,
          "get": 4851,
          "put": 39640,
          "remove": 13771
        },
        {
          "size": 10000000,
          "get": 9398,
          "put": 26626,
          "remove": 20401
        },
        {
          "size": 100000000,
          "get": 11036,
          "put": 46892,
          "remove": 49110
        }
      ]
    },
    {
      "name": "Trove JB",
      "data": [
        {
          "size": 10000,
          "get": 2225,
          "put": 4375,
          "remove": 3728
        },
        {
          "size": 100000,
          "get": 2752,
          "put": 5206,
          "remove": 4370
        },
        {
          "size": 1000000,
          "get": 5150,
          "put": 37342,
          "remove": 13175
        },
        {
          "size": 10000000,
          "get": 9730,
          "put": 29673,
          "remove": 18446
        },
        {
          "size": 100000000,
          "get": 11265,
          "put": 56418,
          "remove": 31471
        }
      ]
    }
  ]
}