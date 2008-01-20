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
package com.ivstars.astrology;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import java.awt.*;
import java.util.Calendar;
import java.io.StringWriter;

import com.ivstars.astrology.util.ChineseCalendarGB;
import com.ivstars.astrology.util.DegreeUtil;



/**
 * StringRender
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class StringRender implements ChartRender{
    private String info;
    public Object render(ChartModel model) {
        String[] positions = model.getHousesPosition();
        VelocityContext context = new VelocityContext();
        context.put("model",model);
        Calendar calendar = Calendar.getInstance(model.getTimezone());
        calendar.setTime(model.getDate());
        context.put("chineseDate", ChineseCalendarGB.computeChinese(
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE)).getChineseDateString());
        String[] pos = new String[12];
        HousesInfo h=model.getHousesInfo();
        for(int i=0;i<12;i++){
            pos[i]= DegreeUtil.format(h.get(i+1),"P h度m分");
        }
        context.put("houses",pos);
        context.put("longitude",DegreeUtil.format(model.getLongitude(),"W h度m分"));
        context.put("latitude",DegreeUtil.format(model.getLatitude(),"N h度m分"));
        PlanetInfo[] pi=model.getPlanets();
        String[] planets = new String[pi.length];
        for (int i = 0; i < pi.length; i++) {
            PlanetInfo info = pi[i];
            planets[i]=info.getPlanetName()+"\t"+DegreeUtil.format(info.getLongitude(),"P h度m分");
            if(info.getLongitudeSpeed()<0)
                planets[i]+="(逆行)";
        }
        context.put("planets",planets);
        context.put("ascsign", DegreeUtil.format(model.getHousesInfo().getAscendant(),"P"));
        try {
            Template template = Velocity.getTemplate("info.vm");
            StringWriter writer = new StringWriter();
            template.merge(context,writer);
            writer.flush();
            info=writer.toString();
        } catch (Exception e) {
            info=e.getMessage();
        }
        return info;
    }

    public String toString(){
        return info;
    }
    public String getName(){
        return "StringRender";
    }
}
