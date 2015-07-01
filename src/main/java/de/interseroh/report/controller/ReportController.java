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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.Parameter;
import de.interseroh.report.model.ParameterForm;
import de.interseroh.report.services.BirtReportService;

@Controller
public class ReportController {

	private static final Logger logger = Logger
			.getLogger(ReportController.class);

	@Autowired
	private BirtReportService reportService;

	@ModelAttribute("parameterForm")
	public ParameterForm provideParameterForm() {
		logger.debug("Provide new ParameterForm");
		return new ParameterForm();
	}

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
			@PathVariable("reportName") String reportName,
			@ModelAttribute("parameterForm") ParameterForm form,
			ModelAndView modelAndView, BindingResult bindingResult)
			throws BirtReportException {

		logger.debug("GET parameter dialog for " + reportName + " report.");

		modelAndView.setViewName("/parameters");

		modelAndView.addObject("reportName", reportName);
		modelAndView.addObject("reportApiUrl", "/api/render/" + reportName);

		Collection<Parameter> parameters = reportService
				.getParameterDefinitions(reportName);

		form.setParameters(new ArrayList<>(parameters));

		modelAndView.addObject("parameterForm", form);
		modelAndView.addObject("parameters", parameters);

		return modelAndView;
	}

	@RequestMapping(value = "/form", method = { RequestMethod.POST })
	public ModelAndView formPOST(
			@ModelAttribute("parameterForm") ParameterForm form,
			BindingResult bindingResult, ModelAndView modelAndView) throws BirtReportException {
		logger.debug("executing posting form...");

		if (form.getParameters().size() > 0) {
			logger.info("Value: " + form.getParameters().iterator().next().getValue
                    ());
		} else {
            form.setParameters(reportService.getParameterDefinitions
                    ("salesinvoice"));
		}
		modelAndView.setViewName("/form");

		return modelAndView;
	}

    @RequestMapping(value = "/form", method = { RequestMethod.GET })
    public ModelAndView formGET(
            @ModelAttribute("parameterForm") ParameterForm form,
             ModelAndView modelAndView)
            throws BirtReportException {
        logger.debug("executing posting form...");

        form.setParameters(reportService.getParameterDefinitions("salesinvoice"));
        modelAndView.setViewName("/form");
        modelAndView.addObject("parameterForm", form);
        return modelAndView;
    }

    @RequestMapping(value = "/report/{reportName}/param", method = RequestMethod.POST)
	public ModelAndView postParameterForm(
			@PathVariable("reportName") String reportName,
			@ModelAttribute("parameterForm") ParameterForm form,
			ModelAndView modelAndView, BindingResult bindingResult)
			throws BirtReportException {
		logger.debug("POST parameter dialog for " + reportName + " report.");

		modelAndView.setViewName("/parameters");
		return modelAndView;
	}
}
