/*
 * $Id$
 */
package com.ivstars.astrology.util;

import junit.framework.TestCase;
import junit.framework.Assert;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * TestLocationProvider
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestLocationProvider extends TestCase {
    private LocationProvider lp;
    public void setUp(){
        try {
            lp = new LocationProvider(new FileInputStream("resource/CN.xls"));
        } catch (IOException e) {
            Assert.fail(e.toString());
            e.printStackTrace();
        }
    }

    public void testListProvince(){
        String[] provinces=lp.listProvinces();
        //Assert.assertTrue(provinces.length>10);
        System.out.println(provinces.length+" provinces");
        for (int i = 0; i < provinces.length; i++) {
            System.out.println("<option value=\""+provinces[i]+"\">"+provinces[i]+"</option>");
        }
    }

    public void testListLocation(){
       Location[] locs = lp.listLocations("湖南省");
        for (int i = 0; i < locs.length; i++) {
            Location loc = locs[i];
            System.out.println(loc);
        }
    }
}
