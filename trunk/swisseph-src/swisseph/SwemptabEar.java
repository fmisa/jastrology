/*
   This is a port of the Swiss Ephemeris Free Edition, Version 1.66.00
   of Astrodienst AG, Switzerland from the original C Code to Java. For
   copyright see the original copyright notices below and additional
   copyright notes in the file named LICENSE, or - if this file is not
   available - the copyright notes at http://www.astro.ch/swisseph/ and
   following. 

   For any questions or comments regarding this port to Java, you should
   ONLY contact me and not Astrodienst, as the Astrodienst AG is not involved
   in this port in any way.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 23rd of April 2001

*/
/* Copyright (C) 1997 - 2000 Astrodienst AG, Switzerland.
   All rights reserved.

  This file is part of Swiss Ephemeris Free Edition.

  Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
  or distributor accepts any responsibility for the consequences of using it,
  or for whether it serves any particular purpose or works at all, unless he
  or she says so in writing.  Refer to the Swiss Ephemeris Public License
  ("SEPL" or the "License") for full details.

  Every copy of Swiss Ephemeris must include a copy of the License,
  normally in a plain ASCII text file named LICENSE.  The License grants you
  the right to copy, modify and redistribute Swiss Ephemeris, but only
  under certain conditions described in the License.  Among other things, the
  License requires that the copyright notices and this notice be preserved on
  all copies.

  For uses of the Swiss Ephemeris which do not fall under the definitions
  laid down in the Public License, the Swiss Ephemeris Professional Edition
  must be purchased by the developer before he/she distributes any of his
  software or makes available any product or service built upon the use of
  the Swiss Ephemeris.

  Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl

  The authors of Swiss Ephemeris have no control or influence over any of
  the derived works, i.e. over software or services created by other
  programmers which use Swiss Ephemeris functions.

  The names of the authors or of the copyright holder (Astrodienst) must not
  be used for promoting any software, product or service which uses or contains
  the Swiss Ephemeris. This copyright notice is the ONLY place where the
  names of the authors can legally appear, except in cases where they have
  given special permission in writing.

  The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
  for promoting such software, products or services.
*/
package swisseph;

class SwemptabEar {
  /*
  First date in file = 1228000.50
  Number of records = 264850.0
  Days per record = 6.0
        Julian Years      Lon    Lat    Rad
   -1349.9 to  -1000.0:   0.13   0.06   0.07
   -1000.0 to   -500.0:   0.12   0.06   0.06
    -500.0 to      0.0:   0.12   0.06   0.08
       0.0 to    500.0:   0.12   0.05   0.06
     500.0 to   1000.0:   0.12   0.05   0.07
    1000.0 to   1500.0:   0.11   0.05   0.07
    1500.0 to   2000.0:   0.11   0.05   0.06
    2000.0 to   2500.0:   0.11   0.05   0.06
    2500.0 to   3000.0:   0.14   0.06   0.07
    3000.0 to   3000.8:  0.074  0.048  0.044
  */

  static final double eartabl[] = {
         -65.54655,        -232.74963, 12959774227.57587,      361678.59587,

           2.52679,          -4.93511,           2.46852,          -8.88928,
           6.66257,          -1.94502,

           0.66887,          -0.06141,           0.08893,           0.18971,

           0.00068,          -0.00307,

           0.03092,           0.03214,          -0.14321,           0.22548,

           0.00314,          -0.00221,

           8.98017,           7.25747,          -1.06655,           1.19671,
          -2.42276,           0.29621,           1.55635,           0.99167,

          -0.00026,           0.00187,

           0.00189,           0.02742,

           0.00158,           0.01475,

           0.00353,          -0.02048,

          -0.01775,          -0.01023,           0.01927,          -0.03122,

          -1.55440,          -4.97423,           2.14765,          -2.77045,
           1.02707,           0.55507,          -0.08066,           0.18479,

           0.00750,           0.00583,

          -0.16977,           0.35555,           0.32036,           0.01309,

           0.54625,           0.08167,           0.10681,           0.17231,
          -0.02287,           0.01631,

          -0.00866,          -0.00190,

           0.00016,          -0.01514,

          -0.00073,           0.04205,

          -0.00072,           0.01490,

          -0.38831,           0.41043,          -1.11857,          -0.84329,
           1.15123,          -1.34167,

           0.01026,          -0.00432,

          -0.02833,          -0.00705,          -0.00285,           0.01645,

          -0.01234,           0.05609,          -0.01893,          -0.00171,

          -0.30527,           0.45390,           0.56713,           0.70030,
           1.27125,          -0.76481,           0.34857,          -2.60318,

          -0.00160,           0.00643,

           0.28492,          -0.37998,           0.23347,           0.00540,
           0.00342,           0.04406,

           0.00037,          -0.02449,

           0.01469,           1.59358,           0.24956,           0.71066,
           0.25477,          -0.98371,

          -0.69412,           0.19687,          -0.44423,          -0.83331,
           0.49647,          -0.31021,

           0.05696,          -0.00802,          -0.14423,          -0.04719,

           0.16762,          -0.01234,           0.02481,           0.03465,

           0.01091,           0.02123,

           0.08212,          -0.07375,           0.01524,          -0.07388,

           0.06673,          -0.22486,           0.10026,          -0.00559,

           0.14711,          -0.11680,           0.05460,           0.02749,

          -1.04467,           0.34273,          -0.67582,          -2.15117,
           2.47372,          -0.04332,

           0.05016,          -0.03991,           0.01908,           0.00943,

           0.07321,          -0.23637,           0.10564,          -0.00446,

          -0.09523,          -0.30710,           0.17400,          -0.10681,

           0.05104,          -0.14078,           0.01390,           0.07288,

          -0.26308,          -0.20717,           0.20773,          -0.37096,

          -0.00205,          -0.27274,

          -0.00792,          -0.00183,

           0.02985,           0.04895,           0.03785,          -0.14731,

           0.02976,          -0.02495,          -0.02644,          -0.04085,

          -0.00843,           0.00027,

           0.00090,           0.00611,

           0.00040,           4.83425,

           0.01692,          -0.01335,

           0.04482,          -0.03602,           0.01672,           0.00838,

           0.03682,          -0.11206,           0.05163,          -0.00219,

          -0.08381,          -0.20911,           0.16400,          -0.13325,

          -0.05945,           0.02114,          -0.00710,          -0.04695,

          -0.01657,          -0.00513,

          -0.06999,          -0.23054,           0.13128,          -0.07975,

           0.00054,          -0.00699,

          -0.01253,          -0.04007,           0.00658,          -0.00607,

          -0.48696,           0.31859,          -0.84292,          -0.87950,
           1.30507,          -0.94042,

          -0.00234,           0.00339,

          -0.30647,          -0.24605,           0.24948,          -0.43369,

          -0.64033,           0.20754,          -0.43829,          -1.31801,
           1.55412,          -0.02893,

          -0.02323,           0.02181,          -0.00398,          -0.01548,

          -0.08005,          -0.01537,          -0.00362,          -0.02033,

           0.00028,          -0.03732,          -0.14083,          -7.21175,

          -0.07430,           0.01886,          -0.00223,           0.01915,

          -0.02270,          -0.03702,           0.10167,          -0.02917,

           0.00879,          -2.04198,

          -0.00433,          -0.41764,

           0.00671,          -0.00030,

           0.00070,          -0.01066,

           0.01144,          -0.03190,

          -0.29653,           0.38638,          -0.16611,          -0.07661,

           0.22071,           0.14665,           0.02487,           0.13524,

        -275.60942,        -335.52251,        -413.89009,         359.65390,
        1396.49813,        1118.56095,        2559.41622,       -3393.39088,
       -6717.66079,       -1543.17403,

          -1.90405,          -0.22958,          -0.57989,          -0.36584,
          -0.04547,          -0.14164,

           0.00749,          -0.03973,

           0.00033,           0.01842,

          -0.08301,          -0.03523,          -0.00408,          -0.02008,

           0.00008,           0.00778,

          -0.00046,           0.02760,

          -0.03135,           0.07710,           0.06130,           0.04003,

          -0.04703,           0.00671,          -0.00754,          -0.01000,

          -0.01902,          -0.00125,

          -0.00264,          -0.00903,

          -0.02672,           0.12765,

          -0.03872,           0.03532,          -0.01534,          -0.00710,

          -0.01087,           0.01124,

          -0.01664,           0.06304,          -0.02779,           0.00214,

          -0.01279,          -5.51814,

           0.05847,          -0.02093,           0.03950,           0.06696,
          -0.04064,           0.02687,

           0.01478,          -0.02169,           0.05821,           0.03301,
          -0.03861,           0.07535,

           0.00290,          -0.00644,

           0.00631,           0.12905,

           0.02400,           0.13194,          -0.14339,           0.00529,

           0.00343,           0.00819,

           0.02692,          -0.03332,          -0.07284,          -0.02064,

           0.07038,           0.03999,           0.02759,           0.07599,

           0.00033,           0.00641,

           0.00128,           0.02032,          -0.00852,           0.00680,

           0.23019,           0.17100,           0.09861,           0.55013,

          -0.00192,           0.00953,

          -0.00943,           0.01783,

           0.05975,           0.01486,           0.00160,           0.01558,

          -0.01629,          -0.02035,           0.01533,           2.73176,

           0.05858,          -0.01327,           0.00209,          -0.01506,

           0.00755,           0.03300,

          -0.00796,          -0.65270,

           0.02305,           0.00165,

          -0.02512,           0.06560,           0.16108,          -0.02087,

           0.00016,           0.10729,

           0.04175,           0.00559,

           0.01176,           0.00110,

          15.15730,          -0.52460,         -37.16535,         -25.85564,
         -60.94577,           4.29961,          57.11617,          67.96463,
          31.41414,         -64.75731,

           0.00848,           0.02971,          -0.03690,          -0.00010,

          -0.03568,           0.06325,           0.11311,           0.02431,

          -0.00383,           0.00421,

          -0.00140,           0.00680,

           0.00069,          -0.21036,

           0.00386,           0.04210,

          -0.01324,           0.16454,

          -0.01398,          -0.00109,

           0.02548,          -0.03842,          -0.06504,          -0.02204,

           0.01359,           0.00232,

           0.07634,          -1.64648,          -1.73103,           0.89176,
           0.81398,           0.65209,

           0.00021,          -0.08441,

          -0.00012,           0.01262,

          -0.00666,          -0.00050,

          -0.00130,           0.01596,

          -0.00485,          -0.00213,

           0.00009,          -0.03941,

          -0.02266,          -0.04421,          -0.01341,           0.01083,

          -0.00011,           0.00004,           0.00003,          -0.02017,

           0.00003,          -0.01096,

           0.00002,          -0.00623,

  };
  static final double eartabb[] = {
         -41.97860,         -48.43539,          74.72897,           0.00075,

          -0.12774,          -0.10188,          -0.00943,          -0.04574,
           0.00265,          -0.00217,

           0.00254,           0.00168,           0.00008,           0.00026,

          -0.00000,          -0.00000,

           0.00004,          -0.00003,           0.00001,          -0.00003,

          -0.00002,          -0.00006,

           0.03351,          -0.02699,           0.00896,          -0.01315,
          -0.00019,          -0.00054,          -0.00020,          -0.00003,

           0.00002,           0.00001,

          -0.00000,           0.00000,

          -0.00002,          -0.00001,

          -0.00001,           0.00003,

           0.00017,          -0.00008,           0.00000,          -0.00003,

           0.00501,          -0.00083,           0.00414,           0.00202,
           0.00051,           0.00060,           0.00002,           0.00000,

          -0.00002,           0.00002,

          -0.00016,          -0.00443,          -0.00083,          -0.00031,

          -0.00394,           0.00148,          -0.00035,           0.00099,
           0.00005,           0.00009,

           0.00004,          -0.00002,

          -0.00001,          -0.00002,

           0.00012,          -0.00005,

           0.00001,           0.00001,

          -0.00577,          -0.00631,          -0.00017,           0.01993,
          -0.00234,          -0.00218,

          -0.00001,           0.00002,

          -0.00101,          -0.00044,          -0.00036,           0.00041,

           0.00294,          -0.00109,           0.00043,          -0.00006,

           0.09650,           0.15003,           0.01087,           0.04905,
           0.00093,          -0.06986,          -0.01471,          -0.00221,

          -0.00002,          -0.00003,

           0.00440,          -0.00083,           0.00102,          -0.00024,
           0.00005,          -0.00002,

          -0.00004,           0.00001,

           0.00505,           0.00930,          -0.01609,          -0.00183,
          -0.00113,           0.00214,

           0.00439,          -0.00295,          -0.00280,           0.00402,
          -0.00047,          -0.00145,

          -0.00114,          -0.00178,           0.00097,           0.00022,

           0.00019,           0.00002,           0.00009,          -0.00005,

          -0.00002,           0.00006,

          -0.01618,          -0.01033,          -0.00372,           0.00301,

          -0.00199,           0.00003,           0.00012,          -0.00068,

          -0.00027,          -0.00011,           0.00009,          -0.00020,

          -0.00618,           0.00129,           0.00452,           0.00620,
          -0.06411,          -0.01524,

          -0.00207,          -0.00140,           0.00005,          -0.00036,

          -0.00009,           0.00005,           0.00012,          -0.00053,

           0.00050,          -0.00068,          -0.00059,          -0.00132,

           0.00719,          -0.13368,          -0.08789,          -0.02072,

           0.00031,          -0.00360,          -0.00241,          -0.00182,

           0.00284,           0.00196,

           0.00083,           0.00008,

           0.00203,          -0.00097,          -0.00120,           0.00748,

           0.00326,          -0.00145,          -0.00276,           0.00236,

          -0.00048,          -0.00258,

           0.00011,           0.00001,

          -0.00284,           0.00795,

          -0.00156,           0.00106,

          -0.00040,          -0.00069,           0.00026,          -0.00039,

          -0.00102,          -0.00098,           0.00017,          -0.00125,

          -0.00180,          -0.01103,          -0.01854,           0.00742,

          -0.02751,          -0.00773,          -0.00263,           0.01059,

           0.00152,           0.00047,

          -0.00106,          -0.00034,          -0.00126,          -0.00291,

          -0.00014,           0.00006,

           0.00069,           0.00316,          -0.00087,           0.00022,

           0.05381,           0.03791,           0.05011,          -0.15168,
          -0.16315,           0.03037,

           0.00068,          -0.00067,

          -0.00457,          -0.00146,          -0.00643,          -0.00451,

           0.07806,           0.00729,           0.03356,          -0.16465,
          -0.20388,          -0.04854,

          -0.00163,          -0.00178,           0.00185,           0.00405,

          -0.00009,           0.00068,          -0.00003,           0.00005,

          -0.01186,           0.00347,          -0.01776,           0.00258,

           0.00081,          -0.00014,           0.00003,          -0.00021,

          -0.01218,          -0.03048,          -0.03109,           0.01387,

          -0.00740,          -0.00113,

          -0.00155,           0.00679,

          -0.00053,          -0.00007,

          -0.00004,          -0.00002,

           0.00248,           0.00127,

          -0.00386,           0.00394,           0.01213,           0.00748,

          -0.04669,          -0.00319,           0.00315,           0.00010,

          85.02966,         -55.85765,         215.62111,         519.00334,
       -1941.10461,         508.68393,        -419.80123,       -4679.60117,
          -0.00916,           0.00204,

          -0.13900,          -0.08473,          -0.07614,          -0.03445,
           0.00359,          -0.00136,

          -0.00111,           0.01028,

           0.00021,          -0.00002,

           0.00039,           0.00246,          -0.00084,          -0.00007,

          -0.00191,           0.00491,

           0.00474,          -0.00676,

          -0.00549,           0.02234,           0.02087,           0.00575,

          -0.00011,           0.00079,          -0.00060,           0.00029,

          -0.00239,          -0.00257,

           0.00020,           0.00163,

           0.00301,          -0.01723,

           0.00049,           0.00086,          -0.00046,           0.00057,

          -0.00049,           0.00024,

           0.00103,          -0.00072,          -0.00005,           0.00095,

           0.00598,          -0.01127,

          -0.00538,           0.00317,          -0.00178,          -0.00010,
           0.00061,           0.00132,

          -0.00001,           0.00318,          -0.00206,           0.00113,
           0.00153,           0.00097,

           0.00161,          -0.00363,

           0.00142,          -0.00047,

          -0.00281,           0.03085,           0.02895,           0.00688,

           0.00025,          -0.00016,

          -0.00197,          -0.08112,           0.02859,          -0.00683,

           0.00004,           0.00016,           0.00158,          -0.00065,

           0.00004,          -0.00001,

           0.00002,          -0.00008,           0.00019,           0.00039,

          -0.00344,           0.00364,           0.00579,          -0.00144,

           0.00031,          -0.00190,

           0.00066,           0.00025,

           0.00011,          -0.00069,           0.00001,          -0.00011,

          -0.01202,           0.00842,           0.00067,          -0.00297,

          -0.00000,           0.00008,           0.00005,           0.00000,

           0.00086,          -0.00057,

           0.00354,          -0.00548,

           0.00009,          -0.00003,

           0.00179,           0.07922,           0.00490,           0.00065,

          -0.00005,          -0.00059,

           0.00061,          -0.00319,

           0.00007,          -0.00048,

           3.49661,          -1.52414,          -6.26431,          -1.76193,
         -26.45666,           7.62583,          77.77395,          10.67040,
           0.00032,           0.00090,

          -0.00026,           0.00680,           0.00827,           0.00199,

          -0.00271,           0.04278,           0.02257,          -0.00532,

           0.00006,           0.00011,

           0.00006,           0.00010,

          -0.00017,          -0.00081,

           0.00050,           0.00001,

           0.00012,           0.00082,

           0.00326,           0.00040,

          -0.00003,          -0.03209,           0.00042,           0.00008,

           0.01059,          -0.00218,

          -0.87557,          -1.06369,          -0.52928,           1.38498,
           0.00082,          -0.00040,

           0.00009,          -0.00047,

           0.00007,           0.00007,

           0.00155,           0.00019,

           0.00002,           0.00008,

           0.00001,           0.00023,

           0.00010,          -0.00029,

          -0.03336,          -0.00987,           0.00012,          -0.00006,

          -0.00198,           0.00333,          -0.00004,           0.00026,

           0.00042,           0.00006,

           0.00025,           0.00021,

  };
  static final double eartabr[] = {
           0.64577,          -2.90183,         -14.50280,          28.85196,

           0.08672,          -0.05643,           0.02353,          -0.00404,
           0.00019,          -0.00137,

           0.00128,          -0.00310,           0.00143,           0.00050,

           0.00000,           0.00000,

          -0.00023,          -0.00003,          -0.00057,          -0.00032,

          -0.00002,           0.00009,

          -0.09716,           0.04111,          -0.03108,           0.00633,
          -0.00220,          -0.00595,          -0.00279,           0.00491,

          -0.00004,          -0.00003,

          -0.00010,          -0.00004,

          -0.00013,          -0.00010,

           0.00017,          -0.00010,

          -0.00075,           0.00002,          -0.00054,          -0.00025,

           0.12572,           0.00948,           0.05937,           0.04900,
          -0.00785,           0.01815,          -0.00303,          -0.00120,

          -0.00010,           0.00010,

          -0.00317,          -0.00143,           0.00068,           0.00213,

          -0.00043,          -0.00420,           0.00406,          -0.00041,
           0.00048,           0.00062,

          -0.00005,           0.00029,

           0.00043,          -0.00002,

          -0.00126,          -0.00009,

          -0.00040,           0.00000,

           0.03557,           0.02143,          -0.02196,           0.04671,
          -0.05571,          -0.03425,

           0.00016,           0.00031,

           0.00020,          -0.00153,          -0.00142,          -0.00051,

          -0.00214,           0.00001,           0.00002,          -0.00061,

          -0.06824,           0.00030,          -0.05717,           0.04196,
           0.05887,           0.07531,           0.12313,          -0.04113,

           0.00025,           0.00021,

           0.02218,           0.01747,           0.00011,           0.01367,
          -0.00247,           0.00029,

           0.00120,          -0.00003,

           0.13373,          -0.02072,           0.06706,          -0.01009,
          -0.09515,          -0.01901,

           0.01767,           0.06939,          -0.06702,           0.04159,
          -0.02809,          -0.03968,

           0.00257,           0.00553,           0.00411,          -0.01309,

           0.00139,           0.01591,          -0.00322,           0.00245,

          -0.00202,           0.00093,

           0.01845,          -0.00018,          -0.00247,          -0.00771,

          -0.02834,          -0.00691,          -0.00154,          -0.01244,

           0.01512,           0.01884,          -0.00359,           0.00731,

          -0.05395,          -0.18108,           0.36303,          -0.12751,
           0.01877,           0.43653,

          -0.00725,          -0.00692,           0.00115,          -0.00327,

           0.04030,           0.01171,           0.00107,           0.01793,

           0.06335,          -0.02171,           0.02229,           0.03533,

          -0.06038,          -0.00356,           0.01325,          -0.03798,

           0.04963,          -0.06258,           0.08931,           0.04904,

           0.07115,          -0.00073,

          -0.00104,           0.00354,

          -0.01549,           0.00647,           0.04418,           0.01061,

           0.00568,           0.00957,           0.01102,          -0.00819,

          -0.00089,           0.00368,

          -0.00214,           0.00031,

          -1.11935,          -0.00029,

           0.00457,           0.00550,

           0.01409,           0.01664,          -0.00306,           0.00629,

           0.04531,           0.01460,           0.00092,           0.02074,

           0.07900,          -0.03241,           0.05122,           0.06151,

           0.01319,           0.03075,          -0.02814,           0.00329,

           0.00208,          -0.00681,

           0.09887,          -0.02956,           0.03410,           0.05617,

           0.00295,           0.00022,

           0.01727,          -0.00666,           0.00255,           0.00256,

          -0.14161,          -0.20656,           0.36936,          -0.35793,
           0.40122,           0.54675,

          -0.00109,          -0.00135,

           0.11179,          -0.13803,           0.19591,           0.11327,

          -0.08785,          -0.29929,           0.60319,          -0.20484,
           0.01418,           0.71392,

          -0.01039,          -0.01041,           0.00694,          -0.00183,

           0.00707,          -0.03745,           0.00943,          -0.00174,

           0.01781,           0.00069,           3.35806,          -0.06731,

          -0.01015,          -0.03402,          -0.00913,          -0.00094,

           0.01682,          -0.01066,           0.01361,           0.04752,

           0.97349,           0.00504,

           0.20303,          -0.00206,

           0.00012,           0.00327,

           0.00504,           0.00040,

          -0.01599,          -0.00570,

          -0.19375,          -0.14714,           0.03820,          -0.08283,

          -0.07716,           0.10543,          -0.06772,           0.01131,

         163.23023,        -126.90743,        -183.43441,        -201.49515,
        -559.82622,         698.28238,        1696.58461,        1279.45831,
         771.51923,       -3358.57619,

          -0.05911,           0.89279,          -0.15861,           0.28577,
          -0.06958,           0.02406,

           0.01999,           0.00382,

          -0.00934,           0.00014,

           0.01792,          -0.04249,           0.01019,          -0.00210,

          -0.00386,           0.00009,

          -0.01353,           0.00101,

          -0.03828,          -0.01677,          -0.02026,           0.03079,

          -0.00285,          -0.02484,           0.00537,          -0.00397,

          -0.00064,           0.00906,

          -0.00411,           0.00100,

          -0.06940,          -0.01482,

          -0.01966,          -0.02171,           0.00388,          -0.00840,

          -0.00621,          -0.00597,

          -0.03690,          -0.00959,          -0.00115,          -0.01557,

           3.24906,          -0.00580,

           0.00745,           0.03347,          -0.04023,           0.02174,
          -0.01544,          -0.02389,

           0.00935,          -0.00141,          -0.02018,           0.03258,
          -0.04479,          -0.02360,

          -0.00542,          -0.00194,

          -0.07906,           0.00273,

          -0.08439,           0.01534,          -0.00264,          -0.09205,

          -0.00539,           0.00220,

           0.01263,           0.01593,           0.01103,          -0.03324,

          -0.02720,           0.04749,          -0.05099,           0.01807,

          -0.00443,           0.00024,

          -0.01386,           0.00029,          -0.00443,          -0.00591,

          -0.11899,           0.15817,          -0.37728,           0.06552,

          -0.00669,          -0.00140,

          -0.01168,          -0.00690,

          -0.01032,           0.04315,          -0.01082,           0.00123,

           0.01192,          -0.01071,          -1.90746,           0.00700,

           0.00779,           0.04261,           0.01052,           0.00173,

          -0.02138,           0.00307,

           0.50118,          -0.00330,

          -0.00111,           0.01624,

          -0.02601,           0.00305,           0.02348,           0.07058,

          -0.07622,           0.00006,

          -0.00183,           0.01636,

          -0.00037,           0.00564,

           4.72127,           3.53639,          13.37363,          -6.68745,
         -12.29946,         -22.51893,         -27.18616,          22.85033,
          25.89912,          12.56594,

          -0.02566,           0.00307,          -0.00064,          -0.02727,

          -0.02634,          -0.01101,          -0.01029,           0.04755,

          -0.00372,          -0.00292,

          -0.00582,          -0.00053,

           0.17840,           0.00027,

          -0.03400,           0.00357,

          -0.13428,          -0.00611,

           0.00099,          -0.01169,

           0.01909,           0.01338,           0.01302,          -0.03071,

          -0.00051,           0.00577,

           0.61945,          -0.32627,          -0.30811,          -0.60197,
          -0.22597,           0.28183,

           0.07739,           0.00011,

           0.01336,          -0.00010,

           0.00049,          -0.00592,

          -0.01407,          -0.00081,

           0.00146,          -0.00280,

           0.03795,           0.00003,

           0.01173,          -0.00655,          -0.00344,          -0.00403,

           0.00036,          -0.00047,           0.02000,           0.00001,

           0.01105,           0.00002,

           0.00620,          -0.00052,

  };

  static final byte earargs[] = {
  (byte)0,  (byte)3,
  (byte)3,  (byte)4,  (byte)3, (byte)-8,  (byte)4,  (byte)3,  (byte)5,  (byte)2,
  (byte)2,  (byte)2,  (byte)5, (byte)-5,  (byte)6,  (byte)1,
  (byte)3,  (byte)2,  (byte)2,  (byte)1,  (byte)3, (byte)-8,  (byte)4,  (byte)0,
  (byte)3,  (byte)3,  (byte)2, (byte)-7,  (byte)3,  (byte)4,  (byte)4,  (byte)1,
  (byte)3,  (byte)7,  (byte)3,(byte)-13,  (byte)4, (byte)-1,  (byte)5,  (byte)0,
  (byte)2,  (byte)8,  (byte)2,(byte)-13,  (byte)3,  (byte)3,
  (byte)3,  (byte)1,  (byte)2, (byte)-8,  (byte)3, (byte)12,  (byte)4,  (byte)0,
  (byte)1,  (byte)1,  (byte)8,  (byte)0,
  (byte)1,  (byte)1,  (byte)7,  (byte)0,
  (byte)2,  (byte)1,  (byte)5, (byte)-2,  (byte)6,  (byte)0,
  (byte)3,  (byte)3,  (byte)3, (byte)-6,  (byte)4,  (byte)2,  (byte)5,  (byte)1,
  (byte)2,  (byte)8,  (byte)3,(byte)-15,  (byte)4,  (byte)3,
  (byte)2,  (byte)2,  (byte)5, (byte)-4,  (byte)6,  (byte)0,
  (byte)1,  (byte)1,  (byte)6,  (byte)1,
  (byte)2,  (byte)9,  (byte)3,(byte)-17,  (byte)4,  (byte)2,
  (byte)3,  (byte)3,  (byte)2, (byte)-5,  (byte)3,  (byte)1,  (byte)5,  (byte)0,
  (byte)3,  (byte)2,  (byte)3, (byte)-4,  (byte)4,  (byte)2,  (byte)5,  (byte)0,
  (byte)3,  (byte)3,  (byte)2, (byte)-5,  (byte)3,  (byte)2,  (byte)5,  (byte)0,
  (byte)2,  (byte)1,  (byte)5, (byte)-1,  (byte)6,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-2,  (byte)4,  (byte)2,
  (byte)2,  (byte)2,  (byte)5, (byte)-3,  (byte)6,  (byte)0,
  (byte)1,  (byte)2,  (byte)6,  (byte)1,
  (byte)2,  (byte)3,  (byte)5, (byte)-5,  (byte)6,  (byte)1,
  (byte)1,  (byte)1,  (byte)5,  (byte)3,
  (byte)2,  (byte)1,  (byte)5, (byte)-5,  (byte)6,  (byte)0,
  (byte)2,  (byte)7,  (byte)3,(byte)-13,  (byte)4,  (byte)2,
  (byte)2,  (byte)2,  (byte)5, (byte)-2,  (byte)6,  (byte)0,
  (byte)2,  (byte)3,  (byte)2, (byte)-5,  (byte)3,  (byte)2,
  (byte)2,  (byte)2,  (byte)3, (byte)-4,  (byte)4,  (byte)2,
  (byte)2,  (byte)5,  (byte)2, (byte)-8,  (byte)3,  (byte)1,
  (byte)2,  (byte)6,  (byte)3,(byte)-11,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)1, (byte)-4,  (byte)3,  (byte)0,
  (byte)1,  (byte)2,  (byte)5,  (byte)1,
  (byte)2,  (byte)3,  (byte)3, (byte)-6,  (byte)4,  (byte)1,
  (byte)2,  (byte)5,  (byte)3, (byte)-9,  (byte)4,  (byte)1,
  (byte)2,  (byte)2,  (byte)2, (byte)-3,  (byte)3,  (byte)2,
  (byte)2,  (byte)4,  (byte)3, (byte)-8,  (byte)4,  (byte)1,
  (byte)2,  (byte)4,  (byte)3, (byte)-7,  (byte)4,  (byte)1,
  (byte)2,  (byte)3,  (byte)3, (byte)-5,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)2, (byte)-2,  (byte)3,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-3,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-1,  (byte)4,  (byte)0,
  (byte)2,  (byte)4,  (byte)2, (byte)-7,  (byte)3,  (byte)0,
  (byte)2,  (byte)4,  (byte)2, (byte)-6,  (byte)3,  (byte)1,
  (byte)1,  (byte)1,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-3,  (byte)4,  (byte)0,
  (byte)2,  (byte)7,  (byte)3,(byte)-12,  (byte)4,  (byte)0,
  (byte)2,  (byte)1,  (byte)2, (byte)-1,  (byte)3,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-4,  (byte)5,  (byte)0,
  (byte)2,  (byte)6,  (byte)3,(byte)-10,  (byte)4,  (byte)1,
  (byte)2,  (byte)5,  (byte)3, (byte)-8,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-3,  (byte)5,  (byte)1,
  (byte)2,  (byte)2,  (byte)2, (byte)-4,  (byte)3,  (byte)1,
  (byte)2,  (byte)6,  (byte)2, (byte)-9,  (byte)3,  (byte)0,
  (byte)2,  (byte)4,  (byte)3, (byte)-6,  (byte)4,  (byte)1,
  (byte)3,  (byte)1,  (byte)3, (byte)-3,  (byte)5,  (byte)2,  (byte)6,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-5,  (byte)6,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-2,  (byte)5,  (byte)2,
  (byte)3,  (byte)1,  (byte)3, (byte)-4,  (byte)5,  (byte)5,  (byte)6,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-4,  (byte)4,  (byte)1,
  (byte)2,  (byte)3,  (byte)2, (byte)-4,  (byte)3,  (byte)2,
  (byte)2,  (byte)1,  (byte)3, (byte)-3,  (byte)6,  (byte)1,
  (byte)3,  (byte)1,  (byte)3,  (byte)1,  (byte)5, (byte)-5,  (byte)6,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-1,  (byte)5,  (byte)1,
  (byte)3,  (byte)1,  (byte)3, (byte)-3,  (byte)5,  (byte)5,  (byte)6,  (byte)1,
  (byte)2,  (byte)1,  (byte)3, (byte)-2,  (byte)6,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-2,  (byte)4,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-1,  (byte)6,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-2,  (byte)7,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-1,  (byte)7,  (byte)0,
  (byte)2,  (byte)8,  (byte)2,(byte)-14,  (byte)3,  (byte)0,
  (byte)3,  (byte)1,  (byte)3,  (byte)2,  (byte)5, (byte)-5,  (byte)6,  (byte)1,
  (byte)3,  (byte)5,  (byte)3, (byte)-8,  (byte)4,  (byte)3,  (byte)5,  (byte)1,
  (byte)1,  (byte)1,  (byte)3,  (byte)4,
  (byte)3,  (byte)3,  (byte)3, (byte)-8,  (byte)4,  (byte)3,  (byte)5,  (byte)2,
  (byte)2,  (byte)8,  (byte)2,(byte)-12,  (byte)3,  (byte)0,
  (byte)3,  (byte)1,  (byte)3,  (byte)1,  (byte)5, (byte)-2,  (byte)6,  (byte)0,
  (byte)2,  (byte)9,  (byte)3,(byte)-15,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)3,  (byte)1,  (byte)6,  (byte)0,
  (byte)1,  (byte)2,  (byte)4,  (byte)0,
  (byte)2,  (byte)1,  (byte)3,  (byte)1,  (byte)5,  (byte)1,
  (byte)2,  (byte)8,  (byte)3,(byte)-13,  (byte)4,  (byte)1,
  (byte)2,  (byte)3,  (byte)2, (byte)-6,  (byte)3,  (byte)0,
  (byte)2,  (byte)1,  (byte)3, (byte)-4,  (byte)4,  (byte)0,
  (byte)2,  (byte)5,  (byte)2, (byte)-7,  (byte)3,  (byte)0,
  (byte)2,  (byte)7,  (byte)3,(byte)-11,  (byte)4,  (byte)1,
  (byte)2,  (byte)1,  (byte)1, (byte)-3,  (byte)3,  (byte)0,
  (byte)2,  (byte)6,  (byte)3, (byte)-9,  (byte)4,  (byte)1,
  (byte)2,  (byte)2,  (byte)2, (byte)-2,  (byte)3,  (byte)0,
  (byte)2,  (byte)5,  (byte)3, (byte)-7,  (byte)4,  (byte)2,
  (byte)2,  (byte)4,  (byte)3, (byte)-5,  (byte)4,  (byte)2,
  (byte)2,  (byte)1,  (byte)2, (byte)-3,  (byte)3,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-3,  (byte)4,  (byte)0,
  (byte)2,  (byte)4,  (byte)2, (byte)-5,  (byte)3,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-5,  (byte)5,  (byte)0,
  (byte)1,  (byte)1,  (byte)2,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-4,  (byte)5,  (byte)1,
  (byte)3,  (byte)2,  (byte)3, (byte)-4,  (byte)5,  (byte)2,  (byte)6,  (byte)0,
  (byte)2,  (byte)6,  (byte)3, (byte)-8,  (byte)4,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-3,  (byte)5,  (byte)1,
  (byte)2,  (byte)6,  (byte)2, (byte)-8,  (byte)3,  (byte)0,
  (byte)2,  (byte)5,  (byte)3, (byte)-6,  (byte)4,  (byte)0,
  (byte)2,  (byte)2,  (byte)3, (byte)-5,  (byte)6,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-2,  (byte)5,  (byte)1,
  (byte)3,  (byte)2,  (byte)3, (byte)-4,  (byte)5,  (byte)5,  (byte)6,  (byte)1,
  (byte)2,  (byte)4,  (byte)3, (byte)-4,  (byte)4,  (byte)0,
  (byte)2,  (byte)3,  (byte)2, (byte)-3,  (byte)3,  (byte)0,
  (byte)2,  (byte)2,  (byte)3, (byte)-3,  (byte)6,  (byte)0,
  (byte)2,  (byte)2,  (byte)3, (byte)-1,  (byte)5,  (byte)1,
  (byte)2,  (byte)2,  (byte)3, (byte)-2,  (byte)6,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-2,  (byte)4,  (byte)0,
  (byte)2,  (byte)2,  (byte)3, (byte)-1,  (byte)6,  (byte)0,
  (byte)1,  (byte)2,  (byte)3,  (byte)4,
  (byte)2,  (byte)5,  (byte)2, (byte)-6,  (byte)3,  (byte)1,
  (byte)2,  (byte)2,  (byte)2, (byte)-1,  (byte)3,  (byte)1,
  (byte)2,  (byte)6,  (byte)3, (byte)-7,  (byte)4,  (byte)0,
  (byte)2,  (byte)5,  (byte)3, (byte)-5,  (byte)4,  (byte)0,
  (byte)2,  (byte)4,  (byte)2, (byte)-4,  (byte)3,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-4,  (byte)5,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-3,  (byte)5,  (byte)0,
  (byte)2,  (byte)6,  (byte)2, (byte)-7,  (byte)3,  (byte)0,
  (byte)2,  (byte)3,  (byte)3, (byte)-2,  (byte)5,  (byte)1,
  (byte)2,  (byte)3,  (byte)2, (byte)-2,  (byte)3,  (byte)0,
  (byte)1,  (byte)3,  (byte)3,  (byte)2,
  (byte)2,  (byte)5,  (byte)2, (byte)-5,  (byte)3,  (byte)0,
  (byte)2,  (byte)1,  (byte)1, (byte)-1,  (byte)3,  (byte)0,
  (byte)2,  (byte)7,  (byte)2, (byte)-8,  (byte)3,  (byte)0,
  (byte)2,  (byte)4,  (byte)3, (byte)-4,  (byte)5,  (byte)0,
  (byte)2,  (byte)4,  (byte)3, (byte)-3,  (byte)5,  (byte)0,
  (byte)2,  (byte)6,  (byte)2, (byte)-6,  (byte)3,  (byte)0,
  (byte)1,  (byte)4,  (byte)3,  (byte)1,
  (byte)2,  (byte)7,  (byte)2, (byte)-7,  (byte)3,  (byte)1,
  (byte)2,  (byte)8,  (byte)2, (byte)-8,  (byte)3,  (byte)0,
  (byte)2,  (byte)9,  (byte)2, (byte)-9,  (byte)3,  (byte)0,
 (byte)-1
  };
  /* Total terms = 135, small = 134 */
  static Plantbl ear404 = new Plantbl(
                               new short[]{1,  9, 14, 17,  5,  5,  2,  1,  0},
                               (short)4,
                               earargs,
                               eartabl,
                               eartabb,
                               eartabr,
                               1.0
                              );

  /*
  First date in file = 1228000.50
  Number of records = 397276.0
  Days per record = 4.0
        Julian Years      Lon    Lat    Rad
   -1349.9 to  -1000.0:   0.42   0.18   0.25
   -1000.0 to   -500.0:   0.45   0.14   0.21
    -500.0 to      0.0:   0.37   0.10   0.20
       0.0 to    500.0:   0.33   0.09   0.22
     500.0 to   1000.0:   0.48   0.07   0.22
    1000.0 to   1500.0:   0.40   0.07   0.19
    1500.0 to   2000.0:   0.36   0.11   0.19
    2000.0 to   2500.0:   0.38   0.14   0.20
    2500.0 to   3000.0:   0.45   0.15   0.24
    3000.0 to   3000.8:  0.182  0.125  0.087
  */
}
