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
import java.util.Arrays;

/**
 * DefaultRender
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class DefaultRender implements ChartRender{
    public static Stroke normalStroke = new BasicStroke(1.0f);
    public static Stroke halfStroke = new BasicStroke(0.5f);
    public static Stroke boldStroke = new BasicStroke(1.5f);
    public static Stroke dashStroke = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,new float[]{1.5f},0.0f);
    protected Graphics2D g;
    protected float width;
    protected float height;
    protected float cx;
    protected float cy;
    protected float ratio;
    //private BufferedImage bgimg;
    private String config;
    private Config cfg;
    /**
     * constructs a cordinatograph object
     */
    public DefaultRender() {
        this(Config.DEFAULT_CONFIG_FILE);
    }
    public DefaultRender(String config){
        this.config = config;
        cfg = Config.getConfig(config);
    }
    public void render(ChartModel model, Graphics g, float width, float height) {
        this.g = (Graphics2D)g;
        this.height = height;
        this.width = width;
        cy = height/2;
        cx = width/2;
        ratio = height/width;

        this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        this.g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        drawBackground();
        drawConstellations(model);
        drawPlanets(model);
        drawRelations(model);
    }
    public Config getConfig() {
        return  cfg;
    }

    protected void drawBackground(){
        g.setColor(getConfig().getColor("background"));
        BufferedImage bgimg = getConfig().getImage("background.image");
        if(bgimg!=null){
            g.fillRect(0,0,(int)width,(int)height);
            g.drawImage(bgimg,0,0,(int)width,(int)height,null);
        }else{
            BufferedImage texture = getConfig().getImage("background.texture");
            if(texture!=null){
                g.setPaint(new TexturePaint(texture,new Rectangle(0,0,texture.getWidth(),texture.getHeight())));
            }
            g.fillRect(0,0,(int)width,(int)height);
        }
        BufferedImage wm = getConfig().getImage("watermark");
        if(wm!=null){
            int w= wm.getWidth();
            int h=wm.getHeight();
            int x=getConfig().getInt("watermark.x",(int)cx-w/2);
            int y=getConfig().getInt("watermark.y",(int)cy-h/2);
            g.drawImage(wm,x,y,w,h,null);
        }
    }
    /**
     * draw constellations and houses
     * @param model
     */
    protected void drawConstellations(ChartModel model){
        HousesInfo hi = model.getHousesInfo();
        //Config cfg = getConfig();
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
        Font basefont = cfg.getFont("base"); //new Font("Dialog",Font.PLAIN,8);
        this.drawOval(r1,normalStroke,basecolor);
        this.drawOval(r2,normalStroke,basecolor);
        this.drawOval(r3,normalStroke,basecolor);
        this.drawOval(r4,normalStroke,basecolor);
        //AffineTransform at = AffineTransform.getRotateInstance();
        double asc=hi.getAscendant();
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
            this.drawString(name,(r1+r2)/2,angle+15,cfg.getColor(name),basefont);
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

    /**
     * draw planets
     * @param model
     */
    protected void drawPlanets(ChartModel model){
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
            this.drawString(planets[i].getPlanetName(),r5,a2,color,font);
        }
    }

    /**
     * draw relationship of planets
     * @param model
     */
    protected void drawRelations(ChartModel model){
        HousesInfo hi=model.getHousesInfo();
        PlanetInfo[] planets=model.getPlanets();
        int count = planets.length;
        float r6=getConfig().getFloat("r6",0.45f);
        if(r6<1.0)
            r6=cx*r6;
        double asc = hi.getAscendant();
        // draw relationship of planets
        ChartOptions.Aspect r;
        Stroke stroke;
        Color color;
        for(int i=0;i<count;i++)
            for (int j = i + 1; j < count; j++) {
                if (planets[i].getId() < 0 && (planets[i].getId() != Constants.VP_ASC && planets[i].getId() != Constants.VP_MC)) {
                        continue;
                }
                if (planets[j].getId() < 0 && (planets[j].getId() != Constants.VP_ASC && planets[j].getId() != Constants.VP_MC)) {
                        continue;
                }
                if(planets[i].getId() == Constants.VP_ASC && planets[j].getId() == Constants.VP_MC){
                      continue;
                }
                if(planets[j].getId() == Constants.VP_ASC && planets[i].getId() == Constants.VP_MC){
                      continue;
                }
                r = model.relation(planets[i], planets[j]);
                if (null != r) {
                    if (r.orb > 1) {
                        stroke = dashStroke;
                    } else {
                        stroke = boldStroke;
                    }
                    color = getConfig().getColor(r.name);
                    this.drawLine(r6, planets[i].getTransferedLongitude(asc),
                            r6, planets[j].getTransferedLongitude(asc), stroke, color);
                }
            }
    }
    //--------------- individal draw methods
    protected void drawOval(float r,Stroke stroke,Color color){
        Shape s = new java.awt.geom.Ellipse2D.Float(cx-r,cy-r*ratio,r*2,r*ratio*2);
        draw(s,stroke,color);
    }
    /**
     * Draw a circle or an ellipse
     * @param rw  radius of width
     * @param rh  radius of height
     * @param stroke drawing stroke
     * @param color  drawing color
     */
    protected void drawOval(float rw,float rh,Stroke stroke,Color color){
        Shape s = new java.awt.geom.Ellipse2D.Float(cx-rw,cy-rh,rw*2,rh*2);
        draw(s,stroke,color);
    }

    /**
     * draw a line between two circle
     * @param r1   one radius
     * @param r2   the other radius
     * @param angle the angle (in degree)
     * @param stroke paint brush stroke
     * @param color  paint brush color
     */
    protected void drawLine(float r1,float r2,double angle,Stroke stroke,Color color){
        double da=DegreeUtil.d2R(angle);
        double hor=Math.cos(da);
        double vet=Math.sin(da);
        Shape shape =new java.awt.geom.Line2D.Double(cx+r1*hor,cy-r1*ratio*vet,cx+r2*hor,cy-r2*ratio*vet);
        draw(shape,stroke,color);
    }

    /**
     * draw a line from one position to another
     * @param r1  the first point radius
     * @param a1  the first point angle
     * @param r2  the second point radius
     * @param a2  the second point angle
     * @param stroke  paint brush stroke
     * @param color   paint brush color
     */
    protected void drawLine(float r1, double a1, float r2, double a2,Stroke stroke,Color color){
        double da1=DegreeUtil.d2R(a1);
        double da2=DegreeUtil.d2R(a2);
        Shape shape =new java.awt.geom.Line2D.Double(cx+r1*Math.cos(da1),cy-r1*ratio*Math.sin(da1),
                cx+r2*Math.cos(da2),cy-r2*ratio*Math.sin(da2));
        draw(shape,stroke,color);
    }
    protected void drawString(String str,float r,double angle,Color color,Font font){
        g.setColor(color);
        if(font !=null)
            g.setFont(font);
        FontMetrics metrics =  g.getFontMetrics();
        int fh=metrics.getHeight();
        int fw = metrics.stringWidth(str);
        double da=DegreeUtil.d2R(angle);
        double hor=Math.cos(da);
        double vet=Math.sin(da);
        float x=(float)(cx+r*hor-fw/2.0);
        float y=(float)(cy-r*ratio*vet);
        if(vet<0)
            y+=fh/2.0f;
        g.drawString(str,x,y);
    }
    protected void drawImage(Image img,float r, double angle){
        int fh=img.getHeight(null);
        int fw =img.getWidth(null);
        drawImage(img,r,angle,fw,fh);
    }
    protected void drawImage(Image img, float r, double angle, int width,int height){
        double da=DegreeUtil.d2R(angle);
        double hor=Math.cos(da);
        double vet=Math.sin(da);
        float x=(float)(cx+r*hor-width/2.0);
        float y=(float)(cy-r*ratio*vet);
        if(vet>0)
            y-=height;
        g.drawImage(img,(int)x,(int)y,width,height,null);
    }
    /**
     * draw a cross "+" at the specified position with specified size
     * @param size  half line length
     * @param r
     * @param angle r and angle specify the position where to draw the cross
     * @param stroke
     * @param color
     */
    protected void drawCross(float size,float r,double angle,Stroke stroke,Color color){
        g.setColor(color);
        g.setStroke(stroke);
        double da=DegreeUtil.d2R(angle);
        double hor=Math.cos(da);
        double vet=Math.sin(da);
        float x=(float)(cx+r*hor);
        float y=(float)(cy-r*ratio*vet);
        Shape line = new Line2D.Float(x-size,y,x+size,y);
        g.draw(line);
        line = new Line2D.Float(x,y-size,x,y+size);
        g.draw(line);
    }
    protected void draw(Shape shape,Stroke stroke,Color color){
        g.setStroke(stroke);
        g.setColor(color);
        g.draw(shape);
    }


    public Object render(ChartModel model) {
        Dimension size = model.getSize();
        BufferedImage image = new BufferedImage(size.width,size.height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        render(model,g,size.width,size.height);
        g.dispose();
        return image;
    }

    public String getName() {
        return "DefaultRender";
    }

}
