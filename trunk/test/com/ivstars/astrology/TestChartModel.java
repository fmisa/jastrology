/*
 * $Id$
 */
package com.ivstars.astrology;

import junit.framework.TestCase;
import junit.framework.Assert;

import java.util.Date;
import java.util.Properties;
import java.util.Calendar;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import com.ivstars.astrology.util.DegreeUtil;
import com.ivstars.astrology.util.ChineseCalendarGB;

/**
 * TestChartModel
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class TestChartModel extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
        //Config.init();
    }

    public void testChartModel(){
        ChartModel model = new ChartModel(new Date(),39.92,116.46);
        Assert.assertFalse(model.isCflag());
        double jd=model.getJulianDay();
        Assert.assertTrue(model.isCflag());
        model.setDate(new Date());
        Assert.assertFalse(model.isCflag());
        HousesInfo hi = model.getHousesInfo();
        Assert.assertTrue(model.isCflag());
    }

    public void testVelocity() throws Exception {
        Config.initVelocity();
        ChartModel model = new ChartModel(new Date(),39.92,116.46);
        model.setName("张三");
        String[] positions = model.getHousesPosition();
        VelocityContext context = new VelocityContext();
        context.put("julianDay",model.getJulianDay());
        context.put("latitude",model.getLatitude());
        context.put("longitude",model.getLongitude());
        context.put("houses",model.getHousesPosition());
        context.put("planets",model.getPlanets());

        try {
            Template template = Velocity.getTemplate("astrology.xml.vm");
            Writer writer = new PrintWriter(System.out);
            template.merge(context,writer);
            writer.flush();
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
    public void testInfo() throws Exception{
        Config.initVelocity();
        ChartModel model = new ChartModel(new Date(),39.92,116.46);
        model.setName("张三");
        StringRender render = new StringRender();         
        System.out.println(render.render(model));
    }
}
