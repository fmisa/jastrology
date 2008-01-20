/*
 * $Id$
 */
package com.ivstars.astrology.util;

import junit.framework.TestCase;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;

/**
 * TestCommonUtil
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestCommonUtil extends TestCase {
    int count =10000;
    public void testParseDate(){
        Date d = CommonUtil.parseDate("2002-01-02",null);
        System.out.println(d);
        Date d2 = CommonUtil.parseDate("2002-1-2 0:00:00", TimeZone.getDefault().getID());
        assertEquals(d,d2);
        d2 = CommonUtil.parseDate("2002/01/02 0:0:0",null);
        assertEquals(d,d2);
    }
    public void testFormatDate(){
        String pattern = "yyyyMMdd";
        for(int i=0;i<count;i++){
            CommonUtil.formatDate(new Date(),pattern);
        }
    }
    public void testFormatDate2(){
        String pattern = "yyyyMMdd";
        SimpleDateFormat sdf =new SimpleDateFormat(pattern);
        for(int i=0;i<count;i++){
            sdf.format(new Date());
        }
    }

    public void testFormatString(){
        MessageFormat mf = new MessageFormat("{0}-{1}-{2}-{3}");

    }
}
