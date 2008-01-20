/*
 * $Id$
 */
package com.ivstars.astrology;

import junit.framework.TestCase;

/**
 * TestConstellation
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestConstellation extends TestCase {
    public void testPulluxs(){
        for(int i=0;i<Constellation.POLLUXS.length;i++){
            System.out.println(Constellation.POLLUXS[i]+".color=");
        }
    }

    public void testPlanet(){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<21;i++){
            String name =Calculator.getPlanetName(i);
            String sname;
            if(name.length()>4)
                sname = name.substring(0,4);
            else sname=name;
            System.out.println(sname+"="+name);
            sb.append("\""+sname+"\",");
        }
        System.out.println(sb);
    }

}
