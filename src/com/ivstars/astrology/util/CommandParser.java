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

import com.ivstars.astrology.ChartModel;

import java.util.StringTokenizer;
import java.text.ParseException;

/**
 * CommandParser
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class CommandParser {
    public static ChartModel parse(String command) throws ParseException {
        // -qb <month> <date> <year> <time> <daylight> <zone> <long> <lat>
        // /qb 10 28 1955 22:00 ST +8:00 122:20W 47:36N
        // -qa <month> <date> <year> <time> <zone> <long> <lat>
        String[] args=command.split("\\s");
        return parse(args);
    }
    public static ChartModel parse(String[] args) throws ParseException {
        String op = args[0].substring(1);
        String time,timezone,longi,lati;

        if("qa".equals(op)){
           time = args[3]+"-"+args[1]+"-"+args[2]+" "+args[4];
           timezone=args[5];
            longi=args[6];
            lati=args[7];
        }else if("qb".equals(op)){
           time = args[3]+"-"+args[1]+"-"+args[2]+" "+args[4];
            //TODO useing daylight time provided by args[5] 
           timezone=args[6];
            longi=args[7];
            lati=args[8];
        }else{
            throw new java.lang.IllegalArgumentException(args[0]);
        }
        if(timezone.charAt(0)=='+'){
            timezone="GMT"+timezone.replace('+','-');
        }else if(timezone.charAt(0)=='-'){
           timezone="GMT"+ timezone.replace('-','+');
        }
        ChartModel model = new ChartModel(time,timezone,DegreeUtil.parseAxis(lati),DegreeUtil.parseAxis(longi));
        return model;
    }
}
