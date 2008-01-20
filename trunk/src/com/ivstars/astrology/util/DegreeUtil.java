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

import com.ivstars.astrology.Constellation;
import com.ivstars.astrology.Constants;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.ParseException;

import net.sf.anole.Messager;
import net.sf.anole.MessagerFactory;

/**
 * DegreeUtil
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class DegreeUtil {
    private static Messager messager = MessagerFactory.getMessager(Constants.BASE_PACKAGE);
    /**
     *
     * @param degree
     * @return format using default pattern "hpm"
     */
    public static String format(double degree){
        return format(degree,"hpm");
    }

    /**
     *
     * @param degree
     * @param pattern format pattern, such as "hpm's''";
     *        char 'h' will be replaced with degree number and
     *        'm' for minutes, 's' for seconds
     *        'p' for pollux(3chars short name)
     *        'P' for localized pollux
     *        'w' for west or east longitude
     *        'n' for north or south latitude
     * @return a string represnts the formatted degree
     */
    public static String format(double degree,String pattern){
        StringBuffer sb = new StringBuffer();
        String direct="";
        String upattern = pattern.toLowerCase();
        if(pattern.indexOf("+")==0){
            if(degree<0){
                degree =0 -degree;
                sb.append("-");
            }else{
                sb.append("+");
            }
            pattern = pattern.substring(1);
        }else if(upattern.indexOf("w")>=0){
            if(degree<0){
                degree = 0-degree;
                direct="W";
            }else{
                direct="E";
            }
        }else if(upattern.indexOf("n")>=0){
            if(degree<0){
                degree = 0-degree;
                direct="S";
            }else{
                direct="N";
            }
        }
        else if(upattern.indexOf("p")>=0){
            degree=fixAngle(degree);
        }
        double r;
        int d=(int)(degree /30);
        if(upattern.indexOf("p")==-1){
           r = degree;
        }else{
           r= degree %30;
        }
        int h = (int)r;
        double dm = (r-h)*60;
        int m = (int)dm;
        double ds = (dm-m)*60;
        int s = (int)ds;

        if(s>30 && pattern.indexOf("s")==-1){
            m++;
            if(m==60){
                m=0;
                h++;
            }
        }

        char[] pchars = pattern.toCharArray();
        for (int i = 0; i < pchars.length; i++) {
            char c = pchars[i];
            switch(c){
                case 'h':sb.append(h);break;
                case 'm':
                    if(m<10) sb.append('0');
                    sb.append(m);
                    break;
                case 's':
                    if(s<10) sb.append('0');
                    sb.append(s);
                    break;
                case 'p':sb.append(Constellation.POLLUXS[d]);break;
                case 'P':sb.append(messager.getMessage(Constellation.POLLUXS[d]));break;
                case 'w':
                case 'n':
                    sb.append(direct);
                    break;
                case 'W':
                case 'N':
                    sb.append(messager.getMessage("axis."+direct));
                    break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Parse longitude or latitude
     * @param degree  format like 130:20W
     * @return the double degreen number that presents the string one
     * @throws ParseException
     */
    public static double parseAxis(String degree) throws ParseException {
        String pattern = "(\\d+)([\\p{Punct}WENS])";//"\\A(\\d+)\\p{Punct}+(\\d+)([WENS])\\z";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(degree);
        double result=0;
        String d="";
        int count=0;
        while(m.find()){
            result+=Double.parseDouble(m.group(1))/Math.pow(60.0,count);
            d=m.group(2);
            count++;
        }
        if(count==0) throw new java.text.ParseException(degree+" does not matches the pattern "+pattern,0);
        if(d.equals("W")||d.equals("S")||degree.startsWith("-")){
                result=0-result;
            }
            return result;
    }
    public static double transfer(double orgi, double asc){
         return (orgi-asc+180);
    }
    /**
     * degree To Radians
     * @param degree
     * @return degree in Radians
     */
    public static double d2R(double degree){
        return degree*Math.PI/180;
    }
    /**
     * fix any angle in 0~360 degree
     * @param angle
     * @return  the angle fixed in 0~360
     */
    public static double fixAngle(double angle){
         while(angle<0){
                angle+=360;
         }
         while(angle>360){
                angle-=360;
         }
        return angle;
    }
}
