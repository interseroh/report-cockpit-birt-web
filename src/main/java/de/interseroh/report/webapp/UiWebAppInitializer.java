/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.webapp;

import de.interseroh.report.webconfig.SecurityConfig;
import de.interseroh.report.webconfig.UiJaxRsConfig;
import de.interseroh.report.webconfig.WebMvcConfig;
import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class UiWebAppInitializer implements WebApplicationInitializer {

    private static final Logger logger = Logger
            .getLogger(UiWebAppInitializer.class);

    private static final String VIEWS_BASE = "/";

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

//	private static final String CXF_SERVLET_NAME = "CXFServlet";

    private static final String SPRING_SECURITY_FILTER_NAME = "springSecurityFilterChain";

//	private static final String GWT_CACHE_CONTROL_FILTER_NAME = "gwtCacheControlFilterChain";

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        logger.info("WebApp *report-cockpit-birt-web* starts...");

        WebApplicationContext rootContext = getRootContext();
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new SessionListener());

//		addApacheCxfServlet(servletContext);
        addMvcServlet(servletContext);

        addSecurityFilter(servletContext);
//		addGwtCacheControlFilter(servletContext);

        logger.info("WebApp *report-cockpit-birt-web* ready...");
    }

//	private void addGwtCacheControlFilter(ServletContext servletContext) {
//		servletContext.addFilter(GWT_CACHE_CONTROL_FILTER_NAME,
//				GwtCacheControlFilter.class).addMappingForUrlPatterns(
//				EnumSet.<DispatcherType> of(DispatcherType.REQUEST), false,
//				"/*");
//	}

    private void addMvcServlet(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebMvcConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                DISPATCHER_SERVLET_NAME, dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(VIEWS_BASE);
    }

    private void addSecurityFilter(ServletContext servletContext) {
        servletContext.addFilter(SPRING_SECURITY_FILTER_NAME,
                DelegatingFilterProxy.class).addMappingForUrlPatterns(
                EnumSet.<DispatcherType>of(DispatcherType.REQUEST,
                        DispatcherType.FORWARD), false, "/*");
    }

    private AnnotationConfigWebApplicationContext getRootContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SecurityConfig.class, UiJaxRsConfig.class);

        return context;
    }

//	private void addApacheCxfServlet(ServletContext servletContext) {
//		CXFServlet cxfServlet = new CXFServlet();
//
//		ServletRegistration.Dynamic appServlet = servletContext.addServlet(
//				CXF_SERVLET_NAME, cxfServlet);
//		appServlet.setLoadOnStartup(1);
//		appServlet.addMapping(UiJaxRsConfig.API_BASE);
//	}

}
