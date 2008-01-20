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

import static com.ivstars.astrology.Constants.*;
import com.ivstars.astrology.util.DegreeUtil;

/**
 * PlanetInfo
 * 此类将swisseph计算的行星参数转换成对应的数据
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class PlanetInfo implements Comparable{
    private int id;
    private double longitude;
    private double latitude;
    private double distance;
    private double longitudeSpeed;
    private double latitudeSpeed;
    private double distanceSpeed;

    public PlanetInfo() {
    }
    public PlanetInfo(int id){
        this.id = id;
    }
    public PlanetInfo(int id, double longitude){
        this.id = id;
        this.longitude = longitude;
    }
    /**
     * construct the calclatue result using an array which length is 6.
     * The parameter xx is used as an output parameter containing the following info:
     * <ul>
     * <li>xx[0]:   longitude</li>
     * <li>xx[1]:   latitude</li>
     * <li>xx[2]:   distance in AU</li>
     * <li>xx[3]:   speed in longitude (degree / day)</li>
     * <li>xx[4]:   speed in latitude (degree / day)</li>
     * <li>xx[5]:   speed in distance (AU / day)</li>
     * @param xx
     */
    public PlanetInfo(int planetId,double[] xx) {
        this.id = planetId;
        longitude = xx[0];
        latitude = xx[1];
        distance = xx[2];
        longitudeSpeed =xx [3];
        latitudeSpeed = xx[4];
        distanceSpeed = xx[5];
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistanceSpeed() {
        return distanceSpeed;
    }

    public void setDistanceSpeed(double distanceSpeed) {
        this.distanceSpeed = distanceSpeed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitudeSpeed() {
        return latitudeSpeed;
    }

    public void setLatitudeSpeed(double latitudeSpeed) {
        this.latitudeSpeed = latitudeSpeed;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitudeSpeed() {
        return longitudeSpeed;
    }

    public void setLongitudeSpeed(double longitudeSpeed) {
        this.longitudeSpeed = longitudeSpeed;
    }

    public int getId() {
        return id;
    }

    public String getPlanetName(){
            return getPlanetName(id);
    }
    public String getPosition(){
        return DegreeUtil.format(this.getLongitude());
    }
    /**
     *
     */
    public String toString() {
        String s= this.getPlanetName()+"\t"+DegreeUtil.format(this.getLongitude());
        if(this.getLongitudeSpeed()<0)
            s+="R";
        s+="\t"+DegreeUtil.format(this.getLatitude(),"+h\u00b0m");
        return s;
    }

    /**
     * longitude relate to ascentent
     * @param asc
     * @return
     */
    public double getTransferedLongitude(double asc){
        return DegreeUtil.transfer(this.longitude,asc);
    }


    public static String getPlanetName(int id){
         if(id>=0 && id<21){
             return planet_names[id];
         }
        if(id<0){
            return vp_names[id- VP_OFFSET];
        }
        return Calculator.getPlanetName(id);
    }

    
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.<p>
     * <p/>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of <i>expression</i>
     * is negative, zero or positive.
     * <p/>
     * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)<p>
     * <p/>
     * The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.<p>
     * <p/>
     * Finally, the implementer must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.<p>
     * <p/>
     * It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * @param o the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
    public int compareTo(Object o) {
        double longi=((PlanetInfo)o).getLongitude();
        double d=DegreeUtil.fixAngle(this.getLongitude())-DegreeUtil.fixAngle(longi);
        if(d>0 && d<1) return 1;
        if(d<0 && d>-1) return -1;
        return (int)d;
    }
}
