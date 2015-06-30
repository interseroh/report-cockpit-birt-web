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
package de.interseroh.report.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import de.interseroh.report.model.ParameterForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.Parameter;
import de.interseroh.report.services.BirtReportService;

@Controller
@PropertySource({ "classpath:config.properties", "classpath:version.properties" })
public class ReportController {

	private static final Logger logger = Logger
			.getLogger(ReportController.class);

	@Autowired
	private BirtReportService reportService;

	@RequestMapping(value = "/report/{reportName}", method = RequestMethod.GET)
	public ModelAndView reportView(
			@PathVariable("reportName") String reportName,
			HttpServletResponse reponse) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/report");
		modelAndView.addObject("reportApiUrl", "/api/render/" + reportName);
		return modelAndView;
	}

	@RequestMapping(value = "/report/{reportName}/param", method = RequestMethod.GET)
	public ModelAndView reportParameter(
			@PathVariable("reportName") String reportName)
			throws BirtReportException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/parameters");

		modelAndView.addObject("reportName", reportName);
        modelAndView.addObject("reportApiUrl", "/api/render/" + reportName);


		Collection<Parameter> parameters = reportService
				.getParameterDefinitions(reportName);

        ParameterForm form = new ParameterForm();
        form.setParameters(new ArrayList<>(parameters));

        modelAndView.addObject("parameterForm", form);
		modelAndView.addObject("parameters", parameters);

		return modelAndView;
	}
}
