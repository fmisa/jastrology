

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

class Swemmoon {

  SwissData swed;
  SwissLib sl;

  Swemmoon() {
    this(null,null);
// //#ifdef TRACE0
//     System.out.println(System.currentTimeMillis()+" Swemmoon()");
// //#endif /* TRACE0 */
  }

  Swemmoon(SwissData swed, SwissLib sl) {
    this.swed=swed;
    this.sl=sl;
    if (this.swed ==null) { this.swed =new SwissData(); }
    if (this.sl   ==null) { this.sl   =new SwissLib(); }
  }


  /* The following coefficients were calculated by a simultaneous least
   * squares fit between the analytical theory and DE404 on the finite
   * interval from -3000 to +3000.
   * The coefficients were estimated from 34,247 Lunar positions.
   */
  static final double z[] = {
    /* The following are scaled in arc seconds, time in Julian centuries.
       They replace the corresponding terms in the mean elements.  */
    -1.312045233711e+01, /* F, t^2 */
    -1.138215912580e-03, /* F, t^3 */
    -9.646018347184e-06, /* F, t^4 */
     3.146734198839e+01, /* l, t^2 */
     4.768357585780e-02, /* l, t^3 */
    -3.421689790404e-04, /* l, t^4 */
    -6.847070905410e+00, /* D, t^2 */
    -5.834100476561e-03, /* D, t^3 */
    -2.905334122698e-04, /* D, t^4 */
    -5.663161722088e+00, /* L, t^2 */
     5.722859298199e-03, /* L, t^3 */
    -8.466472828815e-05, /* L, t^4 */
    /* The following longitude terms are in arc seconds times 10^5.  */
    -8.429817796435e+01, /* t^2 Math.cos(18V - 16E - l) */
    -2.072552484689e+02, /* t^2 Math.sin(18V - 16E - l) */
     7.876842214863e+00, /* t^2 Math.cos(10V - 3E - l) */
     1.836463749022e+00, /* t^2 Math.sin(10V - 3E - l) */
    -1.557471855361e+01, /* t^2 Math.cos(8V - 13E) */
    -2.006969124724e+01, /* t^2 Math.sin(8V - 13E) */
     2.152670284757e+01, /* t^2 Math.cos(4E - 8M + 3J) */
    -6.179946916139e+00, /* t^2 Math.sin(4E - 8M + 3J) */
    -9.070028191196e-01, /* t^2 Math.cos(18V - 16E) */
    -1.270848233038e+01, /* t^2 Math.sin(18V - 16E) */
    -2.145589319058e+00, /* t^2 Math.cos(2J - 5S) */
     1.381936399935e+01, /* t^2 Math.sin(2J - 5S) */
    -1.999840061168e+00, /* t^3 Math.sin(l') */
  };



  /* Orbit calculation begins.
   */
  double LP;
  double M;
  double MP;
  double D;
  double NF;
  double T;
  double T2;


  /* mean lunar node
   * J            julian day
   * pol          return array for position and velocity
   *              (polar coordinates of ecliptic of date)
   */
  int swi_mean_node(double J, double pol[], StringBuffer serr) {
    return swi_mean_node(J, pol, 0, serr);
  }
  int swi_mean_node(double J, double pol[], int offs, StringBuffer serr) {
    String s;
    T = (J-SwephData.J2000)/36525.0;
    T2 = T*T;
    /* with elements from swi_moshmoon2(), which are fitted to jpl-ephemeris */
    if (J < SwephData.MOSHNDEPH_START || J > SwephData.MOSHNDEPH_END) {
      if (serr != null) {
        s="jd "+J+" outside mean node range "+
                SwephData.MOSHNDEPH_START+" .. "+
                SwephData.MOSHNDEPH_END+" ";
        serr.append(s);
      }
      return SweConst.ERR;
    }
    mean_elements();
    /* longitude */
    pol[offs] = sl.swi_mod2PI((LP - NF) * SwephData.STR);
    /* latitude */
    pol[offs+1] = 0.0;
    /* distance */
    pol[offs+2] = SwephData.MOON_MEAN_DIST / SweConst.AUNIT; /* or should it be derived from mean
                                      * orbital ellipse? */
    return SweConst.OK;
  }

  /* mean lunar apogee ('dark moon', 'lilith')
   * J            julian day
   * pol          return array for position
   *              (polar coordinates of ecliptic of date)
   * serr         error return string
   */
  int swi_mean_apog(double J, double pol[], StringBuffer serr) {
    return swi_mean_apog(J, pol, 0, serr);
  }
  int swi_mean_apog(double J, double pol[], int offs, StringBuffer serr) {
    double node;
    String s;
    T = (J-SwephData.J2000)/36525.0;
    T2 = T*T;
    /* with elements from swi_moshmoon2(), which are fitted to jpl-ephemeris */
    if (J < SwephData.MOSHNDEPH_START || J > SwephData.MOSHNDEPH_END) {
      if (serr != null) {
        s="jd "+J+" outside mean apogee range "+
                SwephData.MOSHNDEPH_START+" .. "+
                SwephData.MOSHNDEPH_END+" ";
        if (serr.length()+s.length() < SwissData.AS_MAXCH) {
          serr.append(s);
        }
      }
      return SweConst.ERR;
    }
    mean_elements();
    pol[offs] = sl.swi_mod2PI((LP - MP) * SwephData.STR + SwephData.PI);
    pol[offs+1] = 0;
    pol[offs+2] = SwephData.MOON_MEAN_DIST * (1 + SwephData.MOON_MEAN_ECC) /
                                                 SweConst.AUNIT; /* apogee */
    /* Lilith or Dark Moon is either the empty focal point of the mean
     * lunar ellipse or, for some people, its apogee ("aphelion").
     * This is 180 degrees from the perigee.
     *
     * Since the lunar orbit is not in the ecliptic, the apogee must be
     * projected onto the ecliptic.
     * Joelle de Gravelaine has in her book "Lilith der schwarze Mond"
     * (Astrodata, 1990) an ephemeris which gives noon (12.00) positions
     * but does not project them onto the ecliptic.
     * This results in a mistake of several arc minutes.
     *
     * There is also another problem. The other focal point doesn't
     * coincide with the geocenter but with the barycenter of the
     * earth-moon-system. The difference is about 4700 km. If one
     * took this into account, it would result in an oscillation
     * of the Black Moon. If defined as the apogee, this oscillation
     * would be about +/- 40 arcmin.
     * If defined as the second focus, the effect is very large:
     * +/- 6 deg!
     * We neglect this influence.
     */
    /* apogee is now projected onto ecliptic */
    node = (LP - NF) * SwephData.STR;
    pol[offs] = sl.swi_mod2PI(pol[offs] - node);
    sl.swi_polcart(pol, offs, pol, offs);
    sl.swi_coortrf(pol, offs, pol, offs, -SwephData.MOON_MEAN_INCL * SwissData.DEGTORAD);
    sl.swi_cartpol(pol, offs, pol, offs);
    pol[offs] = sl.swi_mod2PI(pol[offs] + node);
    return SweConst.OK;
  }


  /* Reduce arc seconds modulo 360 degrees
   * answer in arc seconds
   */
  double mods3600(double x) {
    double lx;
    lx = x;
    lx = lx - 1296000.0 * Math.floor( lx/1296000.0 );
    return( lx );
  }


  void swi_mean_lunar_elements(double tjd,
                               DblObj node, DblObj dnode,
                               DblObj peri, DblObj dperi) {
    T = (tjd - SwephData.J2000) / 36525.0;
    T2 = T*T;
    mean_elements();
    node.val = sl.swe_degnorm((LP - NF) * SwephData.STR * SwissData.RADTODEG);
    peri.val = sl.swe_degnorm((LP - MP) * SwephData.STR * SwissData.RADTODEG);
    T -= 1.0 / 36525;
    mean_elements();
    dnode.val = sl.swe_degnorm(node.val - (LP-NF) * SwephData.STR * SwissData.RADTODEG);
    dnode.val -= 360;
    dperi.val = sl.swe_degnorm(peri.val - (LP-MP) * SwephData.STR * SwissData.RADTODEG);
  }

  void mean_elements() {
    double fracT = T%1.;
    /* Mean anomaly of sun = l' (J. Laskar) */
    M =  mods3600(129600000.0 * fracT - 3418.961646 * T +  1287104.76154);
    M += ((((((((
      1.62e-20 * T
    - 1.0390e-17 ) * T
    - 3.83508e-15 ) * T
    + 4.237343e-13 ) * T
    + 8.8555011e-11 ) * T
    - 4.77258489e-8 ) * T
    - 1.1297037031e-5 ) * T
    + 1.4732069041e-4 ) * T
    - 0.552891801772 ) * T2;
    /* Mean distance of moon from its ascending node = F */
    /*NF = mods3600((1739527263.0983 - 2.079419901760e-01) * T +335779.55755);*/
    NF = mods3600(1739232000.0 * fracT + 295263.0983 * T -
                  2.079419901760e-01 * T + 335779.55755);
    /* Mean anomaly of moon = l */
    MP = mods3600(1717200000.0 * fracT + 715923.4728 * T -
                  2.035946368532e-01 * T + 485868.28096);
    /* Mean elongation of moon = D */
    D = mods3600(1601856000.0 * fracT + 1105601.4603 * T +
                 3.962893294503e-01 * T + 1072260.73512);
    /* Mean longitude of moon, referred to the mean ecliptic and equinox of date */
    LP = mods3600(1731456000.0 * fracT + 1108372.83264 * T - 6.784914260953e-01 * T
    +  785939.95571);
    /* Higher degree secular terms found by least squares fit */
    NF += ((z[2]*T + z[1])*T + z[0])*T2;
    MP += ((z[5]*T + z[4])*T + z[3])*T2;
    D  += ((z[8]*T + z[7])*T + z[6])*T2;
    LP += ((z[11]*T + z[10])*T + z[9])*T2;
    /* sensitivity of mean elements
     *    delta argument = scale factor times delta amplitude (arcsec)
     * cos l  9.0019 = mean eccentricity
     * cos 2D 43.6
     * cos F  11.2 (latitude term)
     */
  }

} // End of class Swemmoon
