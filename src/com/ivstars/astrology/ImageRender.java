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

import com.ivstars.astrology.util.DegreeUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

/**
 * ImageRender
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class ImageRender extends DefaultRender{

    public ImageRender() {
        this("/magic.properties");
    }

    public ImageRender(String config) {
        super(config);
    }

    protected void drawPlanets(ChartModel model) {
        HousesInfo hi=model.getHousesInfo();
        PlanetInfo[] planets=model.getPlanets();
        float r5=getConfig().getFloat("r5",0.5f);
        if(r5<1.0)
            r5=cx*r5;
        float r6=getConfig().getFloat("r6",0.45f);
        if(r6<1.0)
            r6=cx*r6;
        Color color;
        Font font = getConfig().getFont("star");
        Color basecolor = getConfig().getColor("base");
        Arrays.sort(planets);
        int count = planets.length;
        PlanetInfo p0,p2;
        double asc = hi.getAscendant();
        for(int i=0;i<count;i++){
            double angle=planets[i].getTransferedLongitude(asc);
            double a2=angle;
            if(i==0){
               p0=planets[count-1];
               p2=planets[i+1];
            }else if(i==(count-1)){
               p0=planets[i-1];
               p2=planets[0];
            }else{
                p0=planets[i-1];
                p2=planets[i+1];
            }
               if(Math.abs(planets[i].compareTo(p0))<10){
                   a2+=5;
               }
               if(Math.abs(planets[i].compareTo(p2))<10){
                   a2-=5;
               }

            color = getConfig().getColor(planets[i].getPlanetName());
            this.drawCross(2.0f,r6,angle,normalStroke,color);
            //this.drawString("o",r6,angle,color,font);
            this.drawLine(r5,a2,r6+5,angle,normalStroke,basecolor);

            BufferedImage image = getConfig().getImage("p"+planets[i].getId());
            if(image==null){
                this.drawString(planets[i].getPlanetName(),r5,a2,color,font);
            }else{
                this.drawImage(image,r5,a2);
            }
        }
    }
    public void render(ChartModel model, Graphics g, float width, float height) {
        this.g = (Graphics2D)g;
        this.height = getConfig().getFloat("height",width);
        this.width = getConfig().getFloat("width",height);
        cy = this.height/2;
        cx = this.width/2;
        ratio = this.height/this.width;

        this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        this.g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        
        this.height = height;
        this.width = width;
        drawBackground();
        this.height = getConfig().getFloat("height",width);
        this.width = getConfig().getFloat("width",height);
        drawConstellations(model);
        drawPlanets(model);
        drawRelations(model);
    }
    protected void drawConstellations(ChartModel model) {
        HousesInfo hi = model.getHousesInfo();
        Config cfg = getConfig();
        float r1=cfg.getFloat("r1",0.95f);
        if(r1<1.0)
            r1=cx*r1;
        float r2=cfg.getFloat("r2",0.8f);
        if(r2<1.0)
            r2=cx*r2;
        float r3=cfg.getFloat("r3",0.75f);
        if(r3<1.0)
            r3=cx*r3;
        float r4=cfg.getFloat("r4",0.65f);
        if(r4<1.0)
            r4=cx*r4;
        Color basecolor=cfg.getColor("base");
        Color weakcolor=cfg.getColor("weak");
        double asc=hi.getAscendant();
        Font basefont = cfg.getFont("base"); //new Font("Dialog",Font.PLAIN,8);
        Image img=getConfig().getImage("houseimage");
        double a0 = DegreeUtil.d2R(DegreeUtil.transfer(0,asc));
        AffineTransform at = AffineTransform.getRotateInstance(0-a0,cx,cy);
        g.transform(at);
        g.drawImage(img,0,0,(int)width,(int)height,null);
        at = AffineTransform.getRotateInstance(a0,cx,cy);
        g.transform(at);
        //this.drawOval(r1,normalStroke,basecolor);
        //this.drawOval(r2,normalStroke,basecolor);
        this.drawOval(r3,normalStroke,basecolor);
        //this.drawOval(r4,normalStroke,basecolor);


        //draw ascendant line
        Line2D line = new Line2D.Float(0.0f,cy,width,cy);
        this.draw(line,normalStroke,basecolor);
        //draw mc-ic line
        double mc= DegreeUtil.transfer(hi.getMc(),asc);
        this.drawLine(cx,0-cx,mc,normalStroke,basecolor);
        //draw polluxs
        String name;
        for(int i=0;i<Constellation.POLLUXS.length;i++){
            name = Constellation.POLLUXS[i];
           double angle=DegreeUtil.transfer(30*i,asc);
            //draw polluxs line
            this.drawLine(r1,r3,angle,normalStroke,basecolor);
            //draw pollux name
            //this.drawString(name,(r1+r2)/2,angle+15,cfg.getColor(name),basefont);
            //draw degree lines
            for(int j=1;j<30;j++){
                if(j%5 == 0){
                    this.drawLine(r2,r3,angle+j,normalStroke,basecolor);
                }else{
                    this.drawLine(r2,r3,angle+j,halfStroke,weakcolor);
                }
            }
        }

        //draw houses
        HousesInfo thi = hi.transfer();
        for(int i=1;i<13;i++){
            double angle=thi.get(i);
            double angle2;
            if(i<12)
            angle2=thi.get(i+1);
            else angle2=thi.get(1);
            double delta=angle2-angle;
            delta =DegreeUtil.fixAngle(delta);
            this.drawLine(r3,r4,angle,normalStroke,basecolor);
            if(i%3!=1){
                this.drawLine(r4,0,angle,dashStroke,weakcolor);
            }
            this.drawString(Integer.toString(i),(r3+r4)/2,angle+delta/2,cfg.getColor(Constellation.POLLUXS[i-1]),basefont);
        }
    }
    
    public String getName(){
        return "ImageRender";
    }
}
