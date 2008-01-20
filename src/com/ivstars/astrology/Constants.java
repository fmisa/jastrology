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

/**
 * Constants
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Constants  {
    public static final String BASE_PACKAGE="com.ivstars.astrology";
    public static final String VERSION_INFO="0.1 alpha";
    public static final int VP_OFFSET=-100;
    public static final int VP_ASC=-99;
    public static final int VP_2_CUSP=-98;
    public static final int VP_3_CUSP=-97;
    public static final int VP_IC=-96;
    public static final int VP_5_CUSP=-95;
    public static final int VP_6_CUSP=-94;
     public static final int VP_DES=-93;
    public static final int VP_8_CUSP=-92;
    public static final int VP_9_CUSP=-91;
    public static final int VP_MC=-90;
    public static final int VP_11_CUSP=-89;
    public static final int VP_12_CUSP=-88;
    public static final int VP_FORT=-86;
    public static final int VP_VERT=-85;
    public static final int VP_EAST=-84;
    public static final int VP_SONO=-83;
    public static final int VP_NONO=-82;
    
    public static final int[] MINOR_OBJECTS = new int[]{15,16,17,18,19,20,12,-86,-85,-84,-83,-82};
    public static int[] PLANETS = new int[]{0,1,2,3,4,5,6,7,8,9};
    static final String[] vp_names={
            "Offset",
            "Asc","2nd","3rd","IC","5th","6th","Desc","8th","9th","MC","11th","12th",
            "",
            "Fort","Vert","East","SoNo","NoNo"
    };
    
    static final String[] planet_names={
            "Sun","Moon","Merc","Venu","Mars",
            "Jupi","Satu","Uran","Nept","Plut",
            "mNde","tNde","Lili","oApo","Eart",
            "Chir","Phol","Cere","Pall","Juno",
            "Vest"};

    /**
     * All variables are static, so there is no sense in instantiating this class.
     */
    private Constants() {
    }

}
