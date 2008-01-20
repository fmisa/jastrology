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

import swisseph.SweConst;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * ChartOptions
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class ChartOptions {

    public List<Integer> planets;
    public List<Aspect> aspects;
    public List<Integer> cusps;
    public Dimension size ;
    private int houseSystem = 'P';
    public int getHouseSystem() {
        return houseSystem;
    }

    public ChartOptions() {
        size = new Dimension(480,480);
    }

    /**
     * set the house system
     * @param houseSystem  The possible
  * house systems are:<P><CODE><BLOCKQUOTE>
  * (int)'P'&nbsp;&nbsp;Placidus<BR>
  * (int)'K'&nbsp;&nbsp;Koch<BR>
  * (int)'O'&nbsp;&nbsp;Porphyrius<BR>
  * (int)'R'&nbsp;&nbsp;Regiomontanus<BR>
  * (int)'C'&nbsp;&nbsp;Campanus<BR>
  * (int)'A'&nbsp;&nbsp;equal (cusp 1 is ascendent)<BR>
  * (int)'E'&nbsp;&nbsp;equal (cusp 1 is ascendent)<BR>
  * (int)'V'&nbsp;&nbsp;Vehlow equal (asc. in middle of house 1)<BR>
  * (int)'X'&nbsp;&nbsp;axial rotation system/ Meridian houses<BR>
  * (int)'H'&nbsp;&nbsp;azimuthal or horizontal system<BR>
  * (int)'T'&nbsp;&nbsp;Polich/Page ('topocentric' system)<BR>
  * (int)'B'&nbsp;&nbsp;Alcabitius
  * </BLOCKQUOTE></CODE><P>
     */
    public void setHouseSystem(int houseSystem) {
        this.houseSystem = houseSystem;
    }
    public String getHouseSystemName(){
        return getHouseSystemName(this.houseSystem);
    }
    public static String getHouseSystemName(int system){
        switch(system){
            case 'P': return "Placidus";
            case 'K': return "Koch";
            case 'O': return "Porphyrius";
            case 'R': return "Regiomontanus";
            case 'C': return "Campanus";
            case 'A': return "equal (cusp 1 is ascendent)";
            case 'E': return "equal (cusp 1 is ascendent)";
            case 'V': return "Vehlow equal (asc. in middle of house 1)";
            case 'X': return "axial rotation system/ Meridian houses";
            case 'H': return "azimuthal or horizontal system";
            case 'T': return "Polich/Page ('topocentric' system)";
            case 'B': return "Alcabitius";
            default: return "unknown";
        }
    }
    /**
     * use true node or mean node to calculate north node.
     * true for SweConst.SE_TRUE_NODE, otherwise SweConst.SE_MEAN_NODE
     */
    public boolean nono_true_mode = false;
    /**
     * a class represent aspect setting
     */
    public static class Aspect{
        public String name;
        public double angle;
        public double orb;
        public float influence;

        public Aspect() {
        }

        public Aspect(double angle, float influence, String name, double orb) {
            this.angle = angle;
            this.influence = influence;
            this.name = name;
            this.orb = orb;
        }


    }

    public static final ChartOptions defaultOptions;
    public static final int[] default_planet_ids=new int[]{0,1,2,3,4,5,6,7,8,9,Constants.VP_NONO,
                SweConst.SE_CHIRON,SweConst.SE_CERES,SweConst.SE_PALLAS,SweConst.SE_JUNO,
                SweConst.SE_VESTA,SweConst.SE_MEAN_APOG,
                Constants.VP_FORT,Constants.VP_EAST,Constants.VP_VERT
        };
    static{
        defaultOptions = new ChartOptions();
        defaultOptions.planets = new ArrayList<Integer>(17);

        for (int i = 0; i < default_planet_ids.length; i++) {
            int id = default_planet_ids[i];
            defaultOptions.planets.add(new Integer(id));
        }
        defaultOptions.aspects = new ArrayList<Aspect>(5);
        defaultOptions.aspects.add(new Aspect(0,1.0f,"Con",6));
        defaultOptions.aspects.add(new Aspect(60,1.0f,"Sex",3));
        defaultOptions.aspects.add(new Aspect(90,1.0f,"Squ",5));
        defaultOptions.aspects.add(new Aspect(120,1.0f,"Tri",5));
        defaultOptions.aspects.add(new Aspect(180,1.0f,"Opp",5));

        defaultOptions.cusps = new ArrayList<Integer>();
        defaultOptions.cusps.add(new Integer(Constants.VP_ASC));
        defaultOptions.cusps.add(new Integer(Constants.VP_DES));
        defaultOptions.cusps.add(new Integer(Constants.VP_MC));
        defaultOptions.cusps.add(new Integer(Constants.VP_IC));

    }
    public static ChartOptions buildOptions(int[] planet_ids){
        ChartOptions options = new ChartOptions();
        options.planets = new ArrayList<Integer>(17);

        for (int i = 0; i < planet_ids.length; i++) {
            int id = planet_ids[i];
            options.planets.add(new Integer(id));
        }
        options.aspects = new ArrayList<Aspect>(5);
        options.aspects.add(new Aspect(0,1.0f,"Con",6));
        options.aspects.add(new Aspect(60,1.0f,"Sex",3));
        options.aspects.add(new Aspect(90,1.0f,"Squ",5));
        options.aspects.add(new Aspect(120,1.0f,"Tri",5));
        options.aspects.add(new Aspect(180,1.0f,"Opp",5));

        options.cusps = new ArrayList<Integer>();
        options.cusps.add(new Integer(Constants.VP_ASC));
        options.cusps.add(new Integer(Constants.VP_DES));
        options.cusps.add(new Integer(Constants.VP_MC));
        options.cusps.add(new Integer(Constants.VP_IC));

        return options;
    }
    


}
