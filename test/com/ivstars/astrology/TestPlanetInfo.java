/*
 * $Id$
 */
package com.ivstars.astrology;

import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * TestPlanetInfo
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestPlanetInfo extends TestCase {
    public void testGetPlanetNameInt(){
        String pn = PlanetInfo.getPlanetName(0);
        Assert.assertEquals(pn,"Sun");
        pn = PlanetInfo.getPlanetName(1);
        Assert.assertEquals(pn,"Moon");
        pn = PlanetInfo.getPlanetName(Constants.VP_ASC);
        Assert.assertEquals(pn,"Asc");
        pn = PlanetInfo.getPlanetName(Constants.VP_FORT);
        Assert.assertEquals(pn,"Fort");
        pn = PlanetInfo.getPlanetName(Constants.VP_11_CUSP);
        Assert.assertEquals(pn,"11th");
        pn = PlanetInfo.getPlanetName(Constants.VP_2_CUSP);
        Assert.assertEquals(pn,"2nd");
    }
}
