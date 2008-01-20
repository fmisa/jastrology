/*
 * Jastrology - Copyright 2006 Fengbo Xie, All Rights Reserved.
 *
 * http://www.ivstars.com/jastrology
 * http://sourceforge.net/projects/jastrology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ivstars.astrology;

import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.SweConst;
import swisseph.SwissephException;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.StringTokenizer;

/**
 * Calculator
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Calculator {
    static SwissEph sw =null;
    private static String ephe_path=null;
    public static final TimeZone GMT0 = TimeZone.getTimeZone("GMT+0");

    public static String getEphe_path() {
        return ephe_path;
    }

    public static void setEphe_path(String path) {
        ephe_path = path;
    }

    public Calculator() {
        if(sw==null){
           initSwissEph();
        }
    }

    private static SwissEph initSwissEph() {
         sw = new SwissEph(ephe_path);
        return sw;
    }

    /**
     * Calculate SweDate by Java Date, which will be used to get Julian Day in the future calculation
     *
     * @param date Java Date
     * @return
     */
    public SweDate calcSweDate(Date date) {
        Calendar calendar = Calendar.getInstance(GMT0);
        calendar.setTime(date);
        SweDate sd = new SweDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY) +
                        calendar.get(Calendar.MINUTE) / 60. +
                        calendar.get(Calendar.SECOND) / 3600. +
                        calendar.get(Calendar.MILLISECOND) / 3600000.);
        return sd;
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minutes
     * @param timezone FOR EXAMPLE: GMT+8:00 OR GMT-8:00 east timezones are positive and westerns are negitive,
     * @return
     */
    public SweDate calcSweDate(int year, int month, int day, int hour, int minutes,int second, String timezone) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,0);

        return calcSweDate(calendar.getTime());
    }

    /**
     * calculate planet information
     *
     * @param sd
     * @param planet The body to be calculated. See
     *               <A HREF="SweConst.html">SweConst</A> for a list of bodies
     * @param iflag  A flag that contains detailed specification on how the body
     *               is to be computed. See <A HREF="SweConst.html">SweConst</A>
     *               for a list of valid flags (SEFLG_*).
     * @return PlanetInfo
     */
    public PlanetInfo calc(SweDate sd, int planet, int iflag) {
         return calc(sd.getJulDay(),planet,iflag);
    }
    public PlanetInfo calc(double julianDay, int planet, int iflag) {

        // In this array, the values will be returned:
        double[] res = new double[6];
        StringBuffer sbErr = new StringBuffer();

        int rc = sw.swe_calc_ut(julianDay,
                planet,
                iflag,
                res,
                sbErr);

        if (sbErr.length() > 0) {
            System.err.println(sbErr.toString());
        }
        if (rc == SweConst.ERR) {
            throw new SwissephException(julianDay, sbErr.toString());
        }

        return new PlanetInfo(planet, res);
    }

    /**
     * calculate using the SweConst.SEFLG_SPEED flag
     * @see #calc(swisseph.SweDate,int,int)
     * @param sd
     * @param planet
     * @return
     */
    public PlanetInfo calc(SweDate sd, int planet){
        return calc(sd,planet, SweConst.SEFLG_SPEED);
    }
    public PlanetInfo calc(double julianDay, int planet){
        return calc(julianDay,planet, SweConst.SEFLG_SPEED);
    }
    /**
     * calculate houses 
     * @param sd
     * @param latitude
     * @param longitude
     * @param hsy
     */
    public HousesInfo houses(SweDate sd, double latitude,double longitude,int hsy){
        double[] cusp = new double[13];
        double[] ascmc = new double[10];
        int result= sw.swe_houses(sd.getJulDay(), 0, latitude, longitude, hsy, cusp, ascmc);
        if(result == SweConst.ERR){
            throw new SwissephException(sd.getJulDay(),"Calculation was not possible due to nearness to the polar circle in Koch or Placidus house system or when requesting Gauquelin sectors. " +
                    "Calculation automatically switched to Porphyry house calculation method in this case");
        }
        return new HousesInfo(cusp,ascmc);
    }

    /**
     * calculate part of fortune
     * @return
     */
    public double calcFort(HousesInfo hi,PlanetInfo sun,PlanetInfo moon){
        double k=sun.getLongitude()-hi.getAscendant();
        if(k<0) k+=360;
        double f;
        if(k>180){ //day
            f=hi.getAscendant()-sun.getLongitude()+moon.getLongitude();
        }else{ //night
            f=hi.getAscendant()+sun.getLongitude()-moon.getLongitude();
        }
        return f;
    }
    //--------------------static methods
    /**
     * get planet name by id
     * @param planet
     * @return
     */
    public static String getPlanetName(int planet){       
        return sw.swe_get_planet_name(planet);
    }

}
