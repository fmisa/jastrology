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

import com.ivstars.astrology.util.DegreeUtil;

/**
 * HousesInfo
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class HousesInfo {
    private double[] houses;
    private double[] otherPoint;
    private double ascendant;
    private double mc;
    private double armc; //sidereal time
    private double vertex;
    private double equatorialAscendant;
    /**
     * This is a constant to access the value of the &quot;co-ascendant&quot; of
     * W. Koch
     */
    private double coasc1;
    /**
     * This is a constant to access the value of the &quot;co-ascendant&quot; of
     * M. Munkasey
     */
    private double coasc2;
    private double polarAscendant;
    /**
     * Create a new HousesInfo instance using calculate result by swisseph.SwissEph.sw_houses()
     * @param cusp (double[13]) The house cusps are returned here in cusp[1...12] for the houses 1 to 12.
     * @param ascmc The parameter ascmc is defined as double[10] and contains the following points:
     *              ascmc[0] = ascendant
     *              ascmc[1] = mc
     *              ascmc[2] = armc (= sidereal time)
     *              ascmc[3] = vertex
     *              ascmc[4] = equatorial ascendant
     *              ascmc[5] = co-ascendant (Walter Koch)
     *              ascmc[6] = co-ascendant (Michael Munkasey)
     *              ascmc[7] = polar ascendant (Michael Munkasey)
     *              ascmc[8] = reserved for future use
     *              ascmc[9] = reserved for future use
     */
    public HousesInfo(double[] cusp, double[] ascmc) {
        houses = cusp;
        this.otherPoint = ascmc;
        ascendant = ascmc[0];
        mc=ascmc[1];
        armc=ascmc[2];
        vertex=ascmc[3];
        equatorialAscendant = ascmc[4];
        coasc1=ascmc[5];
        coasc2=ascmc[6];
        polarAscendant=ascmc[7];
    }

    /**
     * get a specified house cusp
     *
     * @param houseIndex house index from 1-12
     * @return
     */
    public double get(int houseIndex) {
        return houses[houseIndex];
    }

    public String getAsString(int houseIndex) {
        double cusp = houses[houseIndex];
        return DegreeUtil.format(cusp);
    }
    public double getExtra(int index){
        return otherPoint[index];
    }
    public double getArmc() {
        return armc;
    }

    public double getAscendant() {
        return ascendant;
    }

    public double getCoasc1() {
        return coasc1;
    }

    public double getCoasc2() {
        return coasc2;
    }

    public double getEquatorialAscendant() {
        return equatorialAscendant;
    }

    public double getMc() {
        return mc;
    }

    public double getPolarAscendant() {
        return polarAscendant;
    }

    public double getVertex() {
        return vertex;
    }

    /**
     * transfer ascendant to 180 degree
     * @return
     */
    public HousesInfo transfer(){
        double[] cusp = new double[13];
        for(int i=0;i<cusp.length;i++){
            cusp[i]=DegreeUtil.transfer(houses[i],ascendant);
        }
        double[] ascmc = new double[10];
        for(int i=0;i<ascmc.length;i++){
            ascmc[i]= DegreeUtil.transfer(otherPoint[i],ascendant);
        }
        return new HousesInfo(cusp,ascmc);
    }
}
