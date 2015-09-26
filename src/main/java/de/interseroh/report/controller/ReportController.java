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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.visitors.ParameterLogVisitor;
import de.interseroh.report.domain.visitors.ParameterValueMapBuilder;
import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.services.BirtReportService;

import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
@RequestMapping("/reports/{reportName}")
public class ReportController {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportController.class);
	public static final int SUFFIXCOUNT = 10;

	@Autowired
	private ConfigSetter configSetter;

	@Autowired
	private BirtReportService reportService;

	@Autowired
	private ParameterFormValidator parameterFormValidator;

	@Autowired
	private ParameterFormBinder parameterFormBinder;

	@Autowired
	private ParameterFormConverter parameterFormConverter;

	@Autowired
	private ParameterFormFormatter parameterFormFormatter;

	@Autowired
	private RequestParamsBuilder requestParamsBuilder;

	@Autowired
	private CascadingGroupLoader cascadingGroupLoader;

	@Autowired
	private SecurityControl securityControl;

	public ReportController() {
		logger.debug("Creating new Instance of ReportController.");
	}

	@ModelAttribute("parameterForm")
	public ParameterForm populateForm(
			@PathVariable("reportName") String reportName)
					throws BirtReportException {
		if(!hasUserValidRole(reportName)) {

		}
		return new ParameterForm() //
				.withReportName(reportName) //
				.withParameterGroups(
						reportService.getParameterGroups(reportName));
	}

	@RequestMapping(value = "/params", method = RequestMethod.GET)
	public ModelAndView showParameterForm( //
			@ModelAttribute ParameterForm parameterForm, //
			@RequestParam MultiValueMap<String, String> requestParams,
			@PathVariable("reportName") String reportName, BindingResult errors)
					throws BirtReportException {

		logger.debug("executing show parameter form for " + reportName);

		if(!hasUserValidRole(reportName)) {
			throw new BirtReportException(String.format("User has no role for %s", reportName));
		}

		ModelAndView modelAndView = new ModelAndView();

		parameterFormBinder.bind(parameterForm, requestParams, errors);
		parameterFormConverter.convert(parameterForm, errors);

		cascadingGroupLoader.load(parameterForm);

		modelAndView.setViewName("/parameters");
		modelAndView.addObject("parameterForm", parameterForm);

		parameterFormFormatter.format(parameterForm);

		injectReportUri(parameterForm, modelAndView, reportName);

		configSetter.setBranding(modelAndView);
		configSetter.setVersion(modelAndView);

		return modelAndView;
	}

    @RequestMapping(value = "/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView showReportPage(
            @ModelAttribute ParameterForm parameterForm,
            @RequestParam MultiValueMap<String, String> requestParams,
            @RequestParam(value = "__recreate", required =  false, defaultValue = "false") boolean recreate,
            @PathVariable("reportName") String reportName,
            @PathVariable("pageNumber") Long pageNumber,
            BindingResult errors
    ) throws  BirtReportException {

		if(!hasUserValidRole(reportName)) {
			throw new BirtReportException(String.format("User has no role for %s", reportName));
		}
        // if requesting a specific page reuse existing report instead of creating a new one.
        parameterForm.setOverwrite(recreate);
        parameterForm.setPageNumber(pageNumber);
        return showReport(parameterForm, requestParams, reportName, errors);
    }

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showReport( //
			@ModelAttribute ParameterForm parameterForm, //
			@RequestParam MultiValueMap<String, String> requestParams,
			@PathVariable("reportName") String reportName,
			BindingResult errors) throws BirtReportException {

		logger.debug("executing show report for " + reportName);

		if(!hasUserValidRole(reportName)) {
			throw new BirtReportException(String.format("User has no role for %s", reportName));
		}

		ModelAndView modelAndView = new ModelAndView();

		parameterFormBinder.bind(parameterForm, requestParams, errors);
		parameterFormConverter.convert(parameterForm, errors);

		if (parameterForm.isValid()) {
			// show report
			modelAndView.setViewName("/report");
			injectReportUri(parameterForm, modelAndView, reportName);
			configSetter.setVersion(modelAndView);
			configSetter.setBranding(modelAndView);
		} else {
			// show parameters
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("/reports/{reportName}/params");
			redirectView.setContextRelative(true);
			redirectView.setPropagateQueryParams(false);
			redirectView.setExposeModelAttributes(true);
			modelAndView.setView(redirectView);
			modelAndView.addAllObjects(
					new ParameterValueMapBuilder().build(parameterForm));
		}

		parameterFormFormatter.format(parameterForm);
		modelAndView.addObject("reportName", reportName);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/params/cascade/{groupName}")
	public String cascadingGroup( //
			@PathVariable("reportName") String reportName, //
			@PathVariable("groupName") String groupName, //
			@ModelAttribute ParameterForm form, //
			@RequestParam MultiValueMap<String, String> requestParams, //
			BindingResult bindingResult) throws BirtReportException {

		if(!hasUserValidRole(reportName)) {
			throw new BirtReportException(String.format("User has no role for %s", reportName));
		}

		// filter by cascading group name
		parameterFormBinder.bind(form, requestParams, bindingResult);
		parameterFormConverter.convert(form, bindingResult);

		Parameter parameter = form.getParams().get(groupName);

		if (parameter instanceof ParameterGroup) {
			ParameterGroup group = (ParameterGroup) parameter;
			reportService.loadOptionsForCascadingGroup(reportName, group);
			form.resetParams();
		}

		parameterFormFormatter.format(form);

		ParameterLogVisitor.printParameters(form.getGroups());

		return "/parameters :: form#parameters";
	}

	private void injectReportUri(@ModelAttribute ParameterForm form,
			ModelAndView modelAndView,
			@PathVariable("reportName") String reportName) {
		modelAndView.addObject("reportName", reportName);
		String url = "/api/render/" + reportName;
		modelAndView.addObject("reportApiUrl", url);

		modelAndView.addObject("reportParams",
				requestParamsBuilder.asRequestParams(form));
	}

	@RequestMapping(value = "/params", method = { RequestMethod.POST })
	public ModelAndView paramPOST(@PathVariable("reportName") String reportName, //
			@ModelAttribute("parameterForm") ParameterForm form, //
			@RequestParam MultiValueMap<String, String> requestParams,
			BindingResult errors, //
			ModelAndView modelAndView) throws BirtReportException {

		logger.debug("Executing POST of form for {} ", reportName);
		if(!hasUserValidRole(reportName)) {
			throw new BirtReportException(String.format("User has no role for %s",  reportName));
		}

		parameterFormBinder.bind(form, requestParams, errors);
		parameterFormConverter.convert(form, errors);
		cascadingGroupLoader.load(form);
		parameterFormFormatter.format(form);
		parameterFormValidator.validate(form, errors);

		ParameterLogVisitor.printParameters(form.getGroups());

		if (form.isValid() && !errors.hasErrors()) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("/reports/{reportName}");
			redirectView.setContextRelative(true);
			redirectView.setPropagateQueryParams(false);
			redirectView.setExposeModelAttributes(true);
			modelAndView
					.addAllObjects(new ParameterValueMapBuilder().build(form));
			modelAndView.addObject("reportName", reportName);
			modelAndView.setView(redirectView);
		} else {
			modelAndView.setViewName("/parameters");
			configSetter.setBranding(modelAndView);
			configSetter.setVersion(modelAndView);
			modelAndView.addObject("parameterForm", form);
			injectReportUri(form, modelAndView, reportName);
		}

		return modelAndView;

	}

	private boolean hasUserValidRole(String reportName) {

		List<String> roles = securityControl.getRoles();
		/*String fileName = reportName.substring(0,
				(reportName.length() - SUFFIXCOUNT));
		fileName = fileName.toUpperCase();*/
		reportName = reportName.toUpperCase();

		for(String role : roles) {
			if(role.contains(reportName)) {
				return true;
			}
		}
		return false;
	}

}