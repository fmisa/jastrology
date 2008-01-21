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
import swisseph.SweConst;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.awt.*;

import net.sf.anole.MessagerFactory;

import com.ivstars.astrology.util.DegreeUtil;
import com.ivstars.astrology.util.CommonUtil;
import com.ivstars.astrology.util.Location;

/**
 * ChartModel
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class ChartModel {
    private Date date;
    private double julianDay;
    private boolean cflag=false;
    private HousesInfo housesInfo;
    private PlanetInfo[] planets;
    private PlanetInfo sun,moon;
    //private double latitude;
    //private double longitude;
    private Location location;
    private PlanetInfo nono,sono;
    private String name;
    private TimeZone timezone;
    private ChartOptions options;
    Calculator calc;
    public ChartModel() {
        calc = new Calculator();
        options = ChartOptions.defaultOptions;
        this.timezone = TimeZone.getDefault();
        name = MessagerFactory.getMessager(Constants.BASE_PACKAGE).getMessage("ChartModel.name.Unknown");
    }

    public ChartModel(Date date, double latitude, double longitude) {
        this();
        this.date = date;
        this.location = new Location(latitude,longitude,"Earth","");
    }
    public ChartModel(Date date, String timezone, double latitude, double longitude) {
        this(date,latitude,longitude);
        this.timezone = TimeZone.getTimeZone(timezone);
    }

    public ChartModel(String dateStr, String timezone,double latitude,double longitude) {
        this(CommonUtil.parseDate(dateStr,timezone),latitude,longitude);
        this.timezone = TimeZone.getTimeZone(timezone);        
    }
    public Location getLocation() {
        return location;
    }
    public Dimension getSize(){
        return options.size;
    }
    public void setLocation(Location location) {
        this.location = location;
    }



    /**
     * set ChartOptions
     * @param options
     */
    public void setOptions(ChartOptions options) {
        this.options = options;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        cflag = false;
    }
    public String getFormatDate(){
        return SimpleDateFormat.getInstance().format(this.date);
    }
    public String getHouseSystem() {
        return options.getHouseSystemName();
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public HousesInfo getHousesInfo() {
        if(!cflag)
            calc();
        return housesInfo;
    }
    public String[] getHousesPosition(){
        String[] pos = new String[12];
        getHousesInfo();
        for(int i=0;i<12;i++){
            pos[i]= DegreeUtil.format(housesInfo.get(i+1));
        }
        return pos;
    }
    public PlanetInfo getSouthNode(){
        if(!cflag)
            calc();
        return sono;
    }
    public double getJulianDay() {
        if(!cflag)
            calc();
        return julianDay;
    }

    public PlanetInfo[] getPlanets() {
        if(!cflag)
            calc();
        return planets;
    }

    protected boolean isCflag() {
        return cflag;
    }

    /**
     * calculate houses and planets
     */
    public void calc(){

        SweDate sd = calc.calcSweDate(date);
        julianDay = sd.getJulDay();
        housesInfo = calc.houses(sd, this.getLatitude(), this.getLongitude(), options.getHouseSystem());
        sun = calc.calc(julianDay,SweConst.SE_SUN);
        moon = calc.calc(julianDay,SweConst.SE_MOON);
        nono= calc.calc(julianDay,options.nono_true_mode?SweConst.SE_TRUE_NODE:SweConst.SE_MEAN_NODE);
        sono = new PlanetInfo(Constants.VP_SONO,nono.getLongitude()+180);
                    sono.setDistance(nono.getDistance());
                    sono.setLatitude(nono.getLatitude());
                    sono.setLatitudeSpeed(nono.getLatitudeSpeed());
                    sono.setLongitudeSpeed(nono.getLongitudeSpeed());
                    sono.setDistanceSpeed(nono.getDistanceSpeed());

        int pl=options.planets.size();
        int cl=options.cusps.size();
        planets = new PlanetInfo[pl+cl];

        for (int i = 0; i < pl; i++) {
            try {
                planets[i] = calc(options.planets.get(i));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        for (int i = 0; i < cl; i++) {
            try {
                planets[i+pl] = calc(options.cusps.get(i));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        cflag = true;
    }

    public ChartOptions.Aspect relation(PlanetInfo p1,PlanetInfo p2){
        double r=Math.abs(DegreeUtil.fixAngle(p1.getLongitude())-DegreeUtil.fixAngle(p2.getLongitude()));
        if(r>180) r=360-r;
        double k;
        for(int i=0;i<options.aspects.size();i++){
            ChartOptions.Aspect aspect = options.aspects.get(i);
            k=Math.abs(aspect.angle-r);
            if(k<aspect.orb) return new ChartOptions.Aspect(aspect.angle, aspect.influence, aspect.name, k);
        }
        return null;
    }
    private PlanetInfo calc(int id){
        if(id==SweConst.SE_SUN){
            return sun;
        }else if(id == SweConst.SE_MOON){
            return moon;
        }
        else if(id<0){
            switch(id){
                case Constants.VP_ASC: return new PlanetInfo(Constants.VP_ASC,housesInfo.getAscendant());
                case Constants.VP_DES: return new PlanetInfo(Constants.VP_DES,housesInfo.getAscendant()+180);
                case Constants.VP_MC: return new PlanetInfo(Constants.VP_MC,housesInfo.getMc());
                case Constants.VP_IC: return new PlanetInfo(Constants.VP_IC,housesInfo.getMc()+180);
                case Constants.VP_FORT: return new PlanetInfo(Constants.VP_FORT,calc.calcFort(housesInfo,sun,moon));
                case Constants.VP_VERT: return new PlanetInfo(Constants.VP_VERT,housesInfo.getVertex());
                case Constants.VP_EAST: return new PlanetInfo(Constants.VP_EAST,housesInfo.getEquatorialAscendant());
                case Constants.VP_SONO: return sono;
                case Constants.VP_NONO: return nono;
                default: return new PlanetInfo(id,housesInfo.get(100+id));
            }
        }
        return calc.calc(julianDay,id);
    }


}
