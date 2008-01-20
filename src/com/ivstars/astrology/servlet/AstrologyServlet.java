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
package com.ivstars.astrology.servlet;

import com.ivstars.astrology.*;
import com.ivstars.astrology.util.CommonUtil;
import com.ivstars.astrology.util.DegreeUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;

import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;

import java.text.SimpleDateFormat;
import java.text.MessageFormat;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import nl.loremipsum.gif.filters.NeuQuantQuantizerOP;


/**
 * AstrologyServlet<br>
 *Severlet init parameter:
 *         <pre>
 *         datepattern  pattern to format date ,default pattern is yyyy/M/d
 *         basefolder   [required]the direcoty to store image files, e.g. "/etc/httpd/www/astroimages" or "c:/public/www/astroimages/"
 *         baseurl      [required]the basic url for browsering image
 *         filepattern  pattern to format filename ,
 *                      arguments for this pattern is {date,time,longitude,latitude,format}
 *                      default pattern is  {0}/{1}-{2}-{3}.{4}
 *         directmode   true|false, if this parameter set to true,
 *                      write image to response output stream other than to a file on the server.
 *                      default is false
 *         render       class name of default render, client can specify another one
 *         image.width  default image width, client can specify other values
 *         image.height  default image height, client can specify other values
 *         planets      planets and minor objects will show in the horoscope
 *         </pre>
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 *         
 */
public class AstrologyServlet extends HttpServlet {
    private Log log = LogFactory.getLog(getClass());
    private SimpleDateFormat sdf;
    private SimpleDateFormat tdf;
    private String longpat = "h_m_sw";
    private String latipat = "h_m_sn";
    private String tpat = "H_mm_ss";
    private MessageFormat mf;
    private boolean directmode;
    private String basefolder;
    private String baseurl;
    private Class renderClass;
    private int defaultHeight;
    private int defaultWidth;
    private NeuQuantQuantizerOP op ;
    private int[] planets;

    /**
     * A convenience method which can be overridden so that there's no need
     * to call <code>super.init(config)</code>.
     * <p/>
     * <p>Instead of overriding {@link #init(javax.servlet.ServletConfig)}, simply override
     * this method and it will be called by
     * <code>GenericServlet.init(ServletConfig config)</code>.
     * The <code>ServletConfig</code> object can still be retrieved via {@link
     * #getServletConfig}.
     *
     * @throws javax.servlet.ServletException if an exception occurs that
     *                                        interrupts the servlet's
     *                                        normal operation
     */

    public void init() throws ServletException {
        super.init();
        log("initial velocity...");
        try {
            Config.initVelocity();
        } catch (Exception e) {
            throw new ServletException("Error while initializing velocity.", e);
        }
        ServletConfig config = this.getServletConfig();
        String ephepath = this.getServletContext().getRealPath("/WEB-INF/ephe");
        Calculator.setEphe_path(ephepath);
        log("set ephe path to " + ephepath);
        String datepattern = config.getInitParameter("datepattern");
        if (CommonUtil.isEmpty(datepattern))
            datepattern = "yyyy/M/d";
        sdf = new SimpleDateFormat(datepattern);
        tdf = new SimpleDateFormat(tpat);
        String filepattern = config.getInitParameter("filepattern");
        if (CommonUtil.isEmpty(filepattern))
            filepattern = "{0}/{1}-{2}-{3}.{4}";
        mf = new MessageFormat(filepattern);
        directmode = Boolean.parseBoolean(config.getInitParameter("directmode"));
        log.info("directmode " + directmode);
        baseurl = config.getInitParameter("baseurl");
        log.info("Using base url " + baseurl);
        basefolder = config.getInitParameter("basefolder");
        log.info("Using base folder " + basefolder);
        String rcn = config.getInitParameter("render");
        ChartRender render =null;
        if(!CommonUtil.isEmpty(rcn)){
            try {
                renderClass = Class.forName(rcn);
                render = (ChartRender) renderClass.newInstance();
            } catch (ClassNotFoundException e) {
                log.error("Render class "+rcn+" not found");
            } catch (IllegalAccessException e) {
                log.error("Can not access render class "+rcn,e);
            } catch (InstantiationException e) {
                log.error("Can not create instance of render class rcn",e);
            } catch(ClassCastException e){
                log.error(rcn+" is not a render class(implementation of com.ivstars.astrology.ChartRender");
            }

        }
        if(null==render){
            renderClass =  DefaultRender.class;
        }
        log.info("Using render class "+renderClass.getName());
        
        try {
                defaultWidth = Integer.parseInt(config.getInitParameter("image.width"));
            } catch (Exception e) {
                defaultWidth = 480;
            }
            try {
                defaultHeight = Integer.parseInt(config.getInitParameter("image.height"));
            } catch (Exception e) {
                defaultHeight = 480;
            }
        String p = config.getInitParameter("planets");
        if(CommonUtil.isEmpty(p)){
            planets = ChartOptions.default_planet_ids;
        }else{
            String[] ps = p.split(",");
            planets = new int[ps.length];
            for (int i = 0; i < ps.length; i++) {
                String s = ps[i];
                planets[i]=Integer.parseInt(s);
            }
        }
    }

    /**
     * Request parameters:
     * date -- [required]a string that presents the date which to be calculate, e.g. 1980-3-21 17:25:00
     * timezone -- a string that presents the timezone e.g. GMT+8, if this parameter is empty, server side default timezone will be used.
     * longitude -- [required]a double , east is positive and west is negative
     * latitude -- [required]a double , north is positive and south is negative
     * Additional param for image
     * width --  int
     * height -- int
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double longitude = Double.parseDouble(request.getParameter("longitude"));
        double latitude = Double.parseDouble(request.getParameter("latitude"));
        String dateStr = request.getParameter("date");
        String timezone = request.getParameter("timezone");
        ChartModel model = new ChartModel(dateStr, timezone, latitude, longitude);

        model.calc();
        String path = request.getServletPath();
        String format;
        //boolean isImg = false;
        if (path.endsWith(".jpg")) {
            format = "jpg";
        } else if (path.endsWith("png")) {
            format = "png";
        } else if (path.endsWith("gif")) {
            format = "gif";
        } else {
            format = request.getParameter("type");
        }
        if ("jpg".equals(format) || "png".equals(format) || "gif".equals(format)) {
            //check if image exist or not
            String filepath = getFilepath(model, format);
            File imagefile = new File(basefolder, filepath);
            if (imagefile.exists() && !directmode) {
                sendRedirect(response, filepath);
                return;
            }
            int w,h;
            try {
                w = Integer.parseInt(request.getParameter("width"));
            } catch (Exception e) {
                w = defaultWidth;
            }
            try {
                h = Integer.parseInt(request.getParameter("height"));
            } catch (Exception e) {
                h = defaultHeight;
            }
            ChartOptions options = ChartOptions.buildOptions(planets);
            options.size = new Dimension(w, h);
            model.setOptions(options);
            RenderedImage image = renderImage(model, response, request, format);
            OutputStream out;
            if (directmode) {
                response.setContentType("image/" + format);
                out = response.getOutputStream();
            } else {
                File parent = imagefile.getParentFile();
                if ((!parent.exists()) || (!parent.isDirectory()))
                    parent.mkdirs();
                out = new FileOutputStream(imagefile);
            }
            ImageIO.write(image, format, out);
            out.flush();
            out.close();
            if(!directmode){
                sendRedirect(response,filepath);
            }
        } else {  // render text
            renderText(model, response);
        }

    }

    private RenderedImage renderImage(ChartModel model, HttpServletResponse response, HttpServletRequest request, String format) throws IOException {
          //disable customizing of render due to performance tunning, may be we'll enable this later
        //you can comment/uncomment the following block to fit your requirement
        ChartRender render = null;
        try {
            String renderClassName = request.getParameter("render");
            if (!CommonUtil.isEmpty(renderClassName)) render = (ChartRender) Class.forName(renderClassName).newInstance();
            else render=(ChartRender)renderClass.newInstance();
        } catch (Exception e) {
             log.error("Can not create required instance of render, will using default render.",e);
             render = new DefaultRender();
        }
        BufferedImage image = (BufferedImage) render.render(model);
        if("gif".equals(format)){
            if(null==op) op = new NeuQuantQuantizerOP();
            op.filter(image,image);
        }
        return  image;

    }

    /**
     * the same as doGet
     */

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected String getFilepath(ChartModel model, String format) {
        //{date,time,longitude,latitude,format}
        Object[] objs = new String[]{sdf.format(model.getDate()), tdf.format(model.getDate()),
                DegreeUtil.format(model.getLongitude(), longpat), DegreeUtil.format(model.getLatitude(), latipat), format};
        return mf.format(objs);
    }
    private void sendRedirect(HttpServletResponse response, String filepath) throws IOException {
         response.sendRedirect(baseurl+filepath);
    }
    private void renderText(ChartModel model, HttpServletResponse response) throws ServletException {
        VelocityContext context = new VelocityContext();
        context.put("julianDay", model.getJulianDay());
        context.put("latitude", model.getLatitude());
        context.put("longitude", model.getLongitude());
        context.put("houses", model.getHousesPosition());
        context.put("planets", model.getPlanets());
        try {
            response.setContentType("text/xml");
            Template template = Velocity.getTemplate("astrology.xml.vm");
            Writer writer = response.getWriter();
            template.merge(context, writer);
            writer.flush();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
