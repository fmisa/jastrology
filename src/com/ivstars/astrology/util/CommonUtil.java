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
package com.ivstars.astrology.util;

import java.util.Date;
import java.util.TimeZone;
import java.util.StringTokenizer;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * CommonUtil
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class CommonUtil {
    /**
     *
     * Parse a string to a Date.
     *
     * Multi formats of date string are supportted. such as :
     *
     * 2003/3/2 12:32
     *
     * 2003-08-02 10:24:30.100
     *
     * @param dateString string to parse
     * @param timezone TimeZone id such as "PST","CST" or "GMT+8",use the default time zone if this parameter is null
     * @return a Date
     */

    public static Date parseDate (String dateString,String timezone)
            throws IllegalArgumentException {

        if(isEmpty(dateString)){
            throw new IllegalArgumentException("Date string can not be empty");
        }
        TimeZone tz;
        if(isEmpty(timezone))
            tz =TimeZone.getDefault();
        else{
            tz = TimeZone.getTimeZone(timezone);
        }
        StringTokenizer st = new StringTokenizer(dateString, " -:./");
        java.util.Calendar cal = Calendar.getInstance(tz);
        int[] flds = new int[7];
        flds[0] = Calendar.YEAR;
        flds[1] = Calendar.MONTH;
        flds[2] = Calendar.DATE;
        flds[3] = Calendar.HOUR_OF_DAY;
        flds[4] = Calendar.MINUTE;
        flds[5] = Calendar.SECOND;
        flds[6] = Calendar.MILLISECOND;

        int index = 0;
        while (st.hasMoreTokens() && index < 7) {
            try {
                int value = Integer.parseInt(st.nextToken());
                if (index == 1)
                    value -= 1;
                cal.set(flds[index], value);
                index++;
            } catch (Exception e) {
                throw new IllegalArgumentException(dateString);
            }
        }
        while(index<7){
            cal.set(flds[index],0);
            index++;
        }
        return cal.getTime();
    }

    /**
     * check string is empty or not
     * @param s
     * @return true if the string is null or only contains whitespace, otherwise false
     */
    public static boolean isEmpty(String s){
        return (s==null||s.trim().length()==0);
    }
    public static String formatDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

}
