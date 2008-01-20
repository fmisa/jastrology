/*
 * $Id$
 */
package com.ivstars.astrology;

import com.ivstars.astrology.util.DegreeUtil;
import junit.framework.TestCase;

import java.util.Calendar;

import swisseph.SweDate;
import swisseph.SweConst;

/**
 * TestCalculator
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestCalculator extends TestCase {
    public void testCalcSweDate() {
        Calendar now = Calendar.getInstance();
        Calculator calc = new Calculator();
        SweDate swd = calc.calcSweDate(now.getTime());
        System.out.println(now.getTime());
        System.out.println(swd);
        swd = calc.calcSweDate(2006, 2, 16, 22, 55, 0, "GMT+8");
        System.out.println("2006-2-16 22:55 GMT+8 ");
        System.out.println(swd);
    }

    public void testCalc() {
        Calculator calc = new Calculator();
        Calendar now = Calendar.getInstance();
        PlanetInfo pi = calc.calc(calc.calcSweDate(2006, 2, 16, 22, 55, 0, "GMT+8"), SweConst.SE_PLUTO, SweConst.SEFLG_SPEED);
        pi = calc.calc(calc.calcSweDate(now.getTime()), SweConst.SE_SUN, SweConst.SEFLG_SPEED);
        System.out.println(
                pi.getPlanetName() + ":" +
                        "\n\tLongitude:          " + pi.getLongitude() +
                        "\n\tLatitude:           " + pi.getLatitude() +
                        "\n\tDistance:           " + pi.getDistance() + " AU" +
                        "\n\tLongitudinal speed: " + pi.getLongitudeSpeed() + " degs/day" +
                        "\n\tLatitudinal speed: " + pi.getLatitudeSpeed() + " degs/day");
    }

    public void testHouses() {
        Calculator calc = new Calculator();
        SweDate date = new SweDate();
        HousesInfo hi = calc.houses(date, 30.52, 114.31, 'P');
        System.out.println(date.toString());
        for (int i = 1; i < 12; i++) {
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
        System.out.println("Extra 8:\t" + DegreeUtil.format(hi.getExtra(8)));
        System.out.println("Extra 9:\t" + DegreeUtil.format(hi.getExtra(9)));
        
        date = calc.calcSweDate(2006, 2, 19, 0, 0, 0, "GMT+8");
        hi = calc.houses(date, 30.52, 114.31, 'P');
        System.out.println(date.toString());
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
        System.out.println("Extra 8:\t" + DegreeUtil.format(hi.getExtra(8)));
        System.out.println("Extra 9:\t" + DegreeUtil.format(hi.getExtra(9)));
    }

    public void testPlanets() {
        Calculator calc = new Calculator();
        SweDate date = calc.calcSweDate(2006, 2, 19, 0, 0, 0, "GMT+8");
        System.out.println(date.toString());

        for (int i = 1; i < 21; i++) {
            try {
                PlanetInfo pi = calc.calc(date, i);
                System.out.println(pi.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //astroids
        for (int i = 0; i < 21; i++) {
            try {
                PlanetInfo pi = calc.calc(date, i + SweConst.SE_AST_OFFSET);
                System.out.println(pi.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void testCalcFort() {
        Calculator calc = new Calculator();
        String[] forts = {"15Pis43"
                , "27Pis54"
                , "10Ari11"
                , "22Ari49"
                , "6Tau13"
                , "20Tau52"
                , "7Gem19"
                , "25Gem59"
                , "23Sco14"
                , "15Sag38"
                , "7Cap07"
                , "26Cap32"
                , "13Aqu46"
                , "29Aqu13"
                , "13Pis28"
                , "27Pis00"
                , "10Ari12"
                , "23Ari20"
                , "6Tau33"
                , "2Cap59"
                , "15Cap31"
                , "28Cap05"
                , "10Aqu37"
                , "23Aqu00"};
        for (int i = 0; i < 24; i++) {
            SweDate date = calc.calcSweDate(2006, 2, 19, i, 0, 0, "GMT+8");
            //System.out.println(date.toString());
            HousesInfo hi = calc.houses(date, 30.52, 114.31, 'P');
            PlanetInfo sun = calc.calc(date, SweConst.SE_SUN);
            PlanetInfo moon = calc.calc(date, SweConst.SE_MOON);
            double fort = calc.calcFort(hi, sun, moon);
            assertEquals(forts[i],DegreeUtil.format(fort));
        }
    }

    public void testFort() {
        Calculator calc = new Calculator();
        System.out.println("No.\tASC-SUN\tASC-MON\tnightd" +
                "\tnightc\tdayd\tdayc");
        for (int i = 0; i < 24; i++) {
            SweDate date = calc.calcSweDate(2006, 2, 19, i, 0, 0, "GMT+8");
            //System.out.println(date.toString());
            HousesInfo hi = calc.houses(date, 30.52, 114.31, 'P');
            PlanetInfo sun = calc.calc(date, SweConst.SE_SUN);
            PlanetInfo moon = calc.calc(date, SweConst.SE_MOON);
            PlanetInfo fortuna = calc.calc(date, SweConst.SE_AST_OFFSET + 19);
            //System.out.println(fortuna);
            double k = hi.getAscendant() - sun.getLongitude();

            //System.out.println(k);
            System.out.print(i + "\t");
            System.out.print(DegreeUtil.format(k, "+h.m"));
            k = hi.getAscendant() - moon.getLongitude();
            System.out.print("\t" + DegreeUtil.format(k, "+h.m"));
            k = hi.getAscendant() + sun.getLongitude() - moon.getLongitude();
            System.out.print("\t" + DegreeUtil.format(k, "+h.m"));
            String s = DegreeUtil.format(k);
            System.out.print("\t" + s);
            //System.out.println("night:\t"+k+"\t"+s);
            k = hi.getAscendant() - sun.getLongitude() + moon.getLongitude();
            System.out.print("\t" + DegreeUtil.format(k, "+h.m"));
            s = DegreeUtil.format(k);
            System.out.println("\t" + s);
            //System.out.println("day:\t"+k+"\t"+s);

        }
    }

    
}
