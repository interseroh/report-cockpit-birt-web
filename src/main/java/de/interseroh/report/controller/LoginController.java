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
 * (c) 2015 - Interseroh and Crowdcode
 */
package de.interseroh.report.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	@Autowired
	private ConfigSetter configSetter;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public ModelAndView index() {
		logger.debug("Index view executed");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/index");
		configSetter.setBranding(modelAndView);
		configSetter.setVersion(modelAndView);

		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "autherror", required = false) String authError,
			HttpServletRequest request) throws ServletException {
		logger.debug("Login executing...");

		ModelAndView modelAndView = new ModelAndView();
		if (error != null) {
			modelAndView.addObject("error",
					"Benutzername und/oder Passwort falsch!");
		}
		if (logout != null) {
			request.logout();
			modelAndView.addObject("msg", "Logout war erfolgreich.");
		}
		if (authError != null) {
			request.logout();
			modelAndView.addObject("error",
					"Sie haben keinen Zugriff auf dieser Anwendung.");
		}
		modelAndView.setViewName("/login");

		configSetter.setBranding(modelAndView);
		configSetter.setVersion(modelAndView);

		return modelAndView;
	}

}
