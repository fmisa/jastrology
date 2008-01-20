/*
 * $Id$
 */
package com.ivstars.astrology.util;

import junit.framework.TestCase;

import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * TestDegreeUtil
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestDegreeUtil extends TestCase {
    public void testFormat() {
        double angle = 10.205;
        String s = DegreeUtil.format(angle);
        System.out.println("10.2:" + s);
        assertEquals("10Ari12", s);
        s = DegreeUtil.format(angle, "hpm's");
        System.out.println(s);
        assertEquals("10Ari12'18", s);
        angle = 35.395;
        s = DegreeUtil.format(angle);
        System.out.println("35.4:" + s);
        assertEquals("5Tau24", s);
        s = DegreeUtil.format(angle, "hpm's");
        System.out.println(s);
        assertEquals("5Tau23'42", s);
        s = DegreeUtil.format(angle, "+ h:m's");
        System.out.println(s);
        assertEquals("+ 35:23'42", s);
        s = DegreeUtil.format(0 - angle, "+h:m's");
        assertEquals("-35:23'42", s);
        s = DegreeUtil.format(angle, "h:m:sw");
        System.out.println(s);
        assertEquals("35:23:42E", s);
        s = DegreeUtil.format(0 - angle, "h:m:sn");
        assertEquals("35:23:42S", s);

    }

    public void testParseAxis() throws ParseException {
        double d = DegreeUtil.parseAxis("35:30S");
        System.out.println(d);
        assertEquals(-35.5,d);
        d = DegreeUtil.parseAxis("36:15E");
        assertEquals(36.25,d);
        d = DegreeUtil.parseAxis("36:30W");
        assertEquals(-36.5,d);
        d = DegreeUtil.parseAxis("34:12N");
        assertEquals(34.2,d);
        d = DegreeUtil.parseAxis("34:20N");
        System.out.println(d);
    }

    public void testGroup(){
        String pattern = "(\\d+)([\\p{Punct}WENS])";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher("-34:20:40-df-123W");
        while(m.find()){
            System.out.println("group count:"+m.groupCount());
            for(int i=0;i<=m.groupCount();i++){
                System.out.println("Group "+i+" "+m.group(i));
            }
        }
    }
}
