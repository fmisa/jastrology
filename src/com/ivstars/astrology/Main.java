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

import java.util.Date;
import java.util.Properties;


import com.ivstars.astrology.util.DegreeUtil;
import com.ivstars.astrology.util.CommonUtil;

/**
 * Main
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class Main {
    public static void main(String[] args) {
        Properties argmap = processArgs(args);
        if (argmap.getProperty("h") != null || argmap.getProperty("help") != null) {
            usage();
            System.exit(0);
        }

        try {
            String time = argmap.getProperty("t");
            String tz = argmap.getProperty("z");
            String longi = argmap.getProperty("long");
            String lat = argmap.getProperty("lat");
            Date d;
            if (time != null) {
                d = CommonUtil.parseDate(time, tz);
            } else {
                d = new Date();
            }
            System.out.println(d);
            //String f=argmap.getProperty("f");

            Calculator calc = new Calculator();
            SweDate sd = calc.calcSweDate(d);
            System.out.println(sd);
            if (longi != null && lat != null) {
                System.out.println("longitude "+DegreeUtil.format(Double.parseDouble(longi),"+h\u00b0m")
                        +" latitude "+DegreeUtil.format(Double.parseDouble(lat),"+h\u00b0m"));
            }
            PlanetInfo sun = calc.calc(sd, 0);
            System.out.println(sun);
            PlanetInfo moon = calc.calc(sd, 1);
            System.out.println(moon);
            for (int i = 2; i < 21; i++) {
                PlanetInfo pi = calc.calc(sd, i);
                System.out.println(pi.toString());
            }

            if (longi != null && lat != null) {
                String hys = argmap.getProperty("hys");
                if (hys == null) hys = "P";
                HousesInfo hi = calc.houses(sd, Double.parseDouble(lat), Double.parseDouble(longi), (int) hys.toUpperCase().charAt(0));
                double fort = calc.calcFort(hi, sun, moon);
                for (int i = 1; i < 13; i++) {
                    System.out.println(i + " house:\t" + hi.getAsString(i));
                }
                System.out.println("ASC:\t" + DegreeUtil.format(hi.getAscendant()));
                System.out.println("MC:\t" + DegreeUtil.format(hi.getMc()));
                System.out.println("Coasc1:\t" + DegreeUtil.format(hi.getCoasc1()));
                System.out.println("Coasc2:\t" + DegreeUtil.format(hi.getCoasc2()));
                System.out.println("Armc:\t" + DegreeUtil.format(hi.getArmc()));
                System.out.println("EqAsc:\t" + DegreeUtil.format(hi.getEquatorialAscendant()));
                System.out.println("PolarAsc:\t" + DegreeUtil.format(hi.getPolarAscendant()));
                System.out.println("Vertex:\t" + DegreeUtil.format(hi.getVertex()));
                System.out.println("Fort:\t"+DegreeUtil.format(fort));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Date formate error,required format yyyy-MM-dd h:m:s ");
        }
    }

    public static void usage() {
        System.out.println("java " + Main.class.getName() + " [-t=time] [-z=timezone] [-f=format] [-long=longitude] [-lat=latitude] [-hys=house systems]");
        System.out.println("time format yyyy-M-d h:m:s");
    }

    /**
     * Process arguments, put options and its value to a properties,
     * option name as key and its value as property. If no value specified, its property will be set to 'true'.
     *
     * @param args
     * @return
     */
    public static Properties processArgs(String[] args) {
        Properties argmap = new Properties();
        int j = 0;
        // process command line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                int index = args[i].indexOf("=");
                if (index > 0) {
                    if (index < args[i].length() - 1)
                        argmap.setProperty(args[i].substring(1, index), args[i].substring(index + 1));
                    else argmap.setProperty(args[i].substring(1, index), "true");
                } else if (i < args.length - 1 && !args[i + 1].startsWith("-")) {
                    argmap.setProperty(args[i].substring(1), args[++i]);
                } else {
                    argmap.setProperty(args[i].substring(1), "true");
                }
            } else {
                argmap.put("PLAIN_ARG_" + j, args[i]);
                j++;
            }
        }
        //loading additional properties if specified by command line argument '-properties'.
        String propfile = argmap.getProperty("properties");
        if (propfile != null) {
            try {
                java.io.InputStream propin;
                try {
                    propin = new java.io.FileInputStream(propfile);
                } catch (java.io.FileNotFoundException fnfe) {
                    //file not found in the work dir, using classpath to search it.
                    propin = Main.class.getResourceAsStream(propfile);
                }
                if (propin != null)
                    argmap.load(propin);
            } catch (java.io.IOException e) {
                System.err.println("Exception while loading properties from file " + propfile);
            }
        }
        return argmap;
    }
}
