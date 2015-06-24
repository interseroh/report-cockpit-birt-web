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
package de.interseroh.report.server.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@PropertySource({"classpath:config.properties", "classpath:version.properties"})
@Controller
public class EntsorgungController {

    private static final Logger logger = Logger.getLogger(EntsorgungController.class);

    @Inject
    private Environment env;

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public ModelAndView homePage() {
        logger.debug("Home executed");

        ModelAndView model = new ModelAndView();
        model.setViewName("/home");

        String interserohText = env
                .getProperty("text.interseroh.reportcockpit");
        if (interserohText == null || interserohText.equals("")) {
            interserohText = "ReportingCockpit";
        }
        model.addObject("interserohText", interserohText);

        String version = env.getProperty("version");
        model.addObject("version", version);

        return model;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        logger.debug("Index view executed");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @RequestMapping(value = {"/secured/report"}, method = RequestMethod.GET)
    public ModelAndView blocked() {
        logger.debug("Report view executed");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/secured/report");
        return modelAndView;
    }

    @RequestMapping(value = {"/entsorgung"}, method = RequestMethod.GET)
    public ModelAndView entsorgungPage() {
        logger.debug("Entsorgung executed");

        ModelAndView model = new ModelAndView();
        model.setViewName("/entsorgung");

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "autherror", required = false) String authError,
            HttpServletRequest request) throws ServletException {
        logger.debug("Login executed");

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Benutzername und/oder Passwort falsch!");
        }
        if (logout != null) {
            request.logout();
            model.addObject("msg", "Logout war erfolgreich.");
        }
        if (authError != null) {
            request.logout();
            model.addObject("error",
                    "Sie haben keinen Zugriff auf dieser Anwendung.");
        }
        model.setViewName("/login");

        return model;
    }

}
