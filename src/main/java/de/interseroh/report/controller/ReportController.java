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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.Parameter;
import de.interseroh.report.model.ParameterForm;
import de.interseroh.report.services.BirtReportService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
@RequestMapping("/reports/{reportName}")
public class ReportController {

	private static final Logger logger = Logger
			.getLogger(ReportController.class);

	@Autowired
	private BirtReportService reportService;

	public ReportController() {
		logger.info("Creating new instanz auf ReportController.");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("initializing WebDataBinder");

		DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	@ModelAttribute("parameterForm")
	public ParameterForm populateForm(
			@PathVariable("reportName") String reportName)
			throws BirtReportException {
		logger.debug("Populate new ParameterForm for Report " + reportName);
		ParameterForm parameterForm = new ParameterForm();

		Collection<Parameter> parameters = reportService
				.getParameterDefinitions(reportName);

		parameterForm.setParameters(new ArrayList<>(parameters));

		return parameterForm;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView reportView(@ModelAttribute ParameterForm form,
			ModelAndView modelAndView,
			@PathVariable("reportName") String reportName) {

		logger.debug("executing report view for " + reportName);

		if (form.hasNoParameters()) {
            // show report
			modelAndView.setViewName("/report");
			injectReportUri(form, modelAndView, reportName);
		} else {
            // show parameters
			modelAndView.setViewName("/parameters");
			modelAndView.addObject("parameterForm", form);
		}

		return modelAndView;
	}

	private void injectReportUri(@ModelAttribute ParameterForm form,
			ModelAndView modelAndView,
			@PathVariable("reportName") String reportName) {
		modelAndView.addObject("reportName", reportName);
		String url = "/api/render/" + reportName;
		modelAndView.addObject("reportApiUrl", url);
		modelAndView.addObject("reportParams", form.buildRequestParams());
	}

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView paramPOST(
			@PathVariable("reportName") String reportName, //
			@ModelAttribute("parameterForm") ParameterForm form, //
			BindingResult bindingResult, //
			RedirectAttributes redirectAttributes, //
			ModelAndView modelAndView) throws BirtReportException {

		logger.debug("Executing POST of form for " + reportName);

		if (form.isValid()) {
			modelAndView.setViewName("/report");
			injectReportUri(form, modelAndView, reportName);
		} else {
			modelAndView.setViewName("/parameters");
			modelAndView.addObject("parameterForm", form);
		}

		return modelAndView;
	}
}
