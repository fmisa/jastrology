/*
 * $Id$
 */
package com.ivstars.astrology.util;

import junit.framework.TestCase;

/**
 * TestChineseCalendar
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestChineseCalendar extends TestCase {
    public void testGetDateString(){
        System.out.println("");
        ChineseCalendarGB c = new ChineseCalendarGB();
        for(int i=1;i<31;i++){
        c.setGregorian(2006,5,i);
        c.computeChineseFields();
        c.computeSolarTerms();
        System.out.println("2006-5-"+i+"\t"+c.getChineseDateString());
        }
    }
    public void testChineseYear(){
        System.out.println("");
        ChineseCalendarGB c = new ChineseCalendarGB();
        for(int i=1;i<31;i++){
        c.setGregorian(2006,1,i);
        c.computeChineseFields();
        c.computeSolarTerms();
        //System.out.println("2006-1-"+i+"\t"+c.getChineseYear()+"-"+c.getChineseMonth()+"-"+c.getChineseDate());
        }
    }
    public void testConvertChineseToGe(){
        System.out.println("");
         ChineseCalendarGB c0 =new ChineseCalendarGB();
        for(int i=1;i<31;i++){
            c0.setGregorian(2006,1,i);
            c0.computeChineseFields();
            c0.computeSolarTerms();
            ChineseCalendarGB c = ChineseCalendarGB.computeChineseToGregorian(c0.getChineseYear(),c0.getChineseMonth(),c0.getChineseDate());
            //System.out.println(c.getChineseDateString()+"\t"+c.getGregorianYear()+"-"
              //      +c.getGregorianMonth()+"-"+c.getGregorianDate());
            //assertEquals(c0.getChineseMonth(),c.getChineseMonth());
            //assertEquals(c0.getChineseDate(),c.getChineseDate());
        }
    }
}
