/*
 * $Id$
 */
package com.ivstars.astrology.util;

import junit.framework.TestCase;

import java.util.TimeZone;
import java.text.ParseException;

import com.ivstars.astrology.ChartModel;

/**
 * TestCommandParser
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestCommandParser extends TestCase {
    public void testTimeZoneId(){
        System.out.println(TimeZone.getTimeZone("-8:00"));

        System.out.println(TimeZone.getTimeZone("GMT-8:00"));
    }
    public void testParse() throws ParseException {
        ChartModel model =CommandParser.parse("-qb 10 28 1955 22:00 ST +8:00 122:20W 47:36N");
        System.out.println("TimeZone:\t"+model.getTimezone());
        assertEquals(TimeZone.getTimeZone("GMT-8:00"),model.getTimezone());
        System.out.println("Date:\t"+model.getFormatDate());
        System.out.println("Location:\t"+model.getLocation());
        assertEquals(47.6,model.getLatitude());
        assertTrue(Math.abs(-122.33-model.getLongitude())<0.01);

    }
}
