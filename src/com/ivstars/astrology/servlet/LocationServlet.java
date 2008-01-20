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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.ivstars.astrology.util.LocationProvider;

/**
 * LocationServlet
 *
 * @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class LocationServlet extends HttpServlet {
    LocationProvider lp;
    public void init() throws ServletException {
        super.init();
        try {
            lp = new LocationProvider(this.getServletContext().getResourceAsStream("/WEB-INF/CN.xls"));
        } catch (IOException e) {
            throw new ServletException("Error while initializing LocationProvider.",e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        String province = request.getParameter("province");

        VelocityContext context = new VelocityContext();        
         try {
            context.put("locations",lp.listLocations(province));
            Template template = Velocity.getTemplate("location.xml.vm");
            Writer writer =response.getWriter();
            template.merge(context,writer);
            writer.flush();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
