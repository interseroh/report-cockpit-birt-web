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

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.visitors.ParameterLogVisitor;
import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.exception.BirtUnauthorizedException;
import de.interseroh.report.services.BirtOutputFormat;
import de.interseroh.report.services.BirtReportService;
import de.interseroh.report.services.SecurityService;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Controller
@RequestMapping("/api")
public class ReportRestApiController {

	public static final String CONTENT_DISPOSITION = "Content-disposition";
	private static final Logger logger = LoggerFactory
			.getLogger(ReportRestApiController.class);
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
	private SecurityService securityService;

	@ModelAttribute("parameterForm")
	public ParameterForm populateForm(
			@PathVariable("reportName") String reportName)
					throws BirtReportException {

		ParameterForm form = new ParameterForm() //
				.withReportName(reportName) //
				.withParameterGroups(
						reportService.getParameterGroups(reportName));

		ParameterLogVisitor.printParameters(form.getGroups());
		return form;

	}

	@RequestMapping(value = "/render/{reportName}", method = RequestMethod.GET)
	public void renderReportInDefaultFormat(
			@ModelAttribute("parameterForm") ParameterForm parameterForm,
			@PathVariable("reportName") String reportName,
			@RequestParam MultiValueMap<String, String> requestParams,
			HttpServletResponse response, BindingResult errors)
					throws IOException, BirtReportException, ParseException {
		if(securityService.hasUserValidRole(reportName)) {
			renderReport(parameterForm, reportName, requestParams, null, true,
					BirtOutputFormat.HTML5.getFormatName(), response, errors);

		} else {
			throw new BirtReportException(String.format("User has no role for %s", reportName));
		}
	}


	@RequestMapping(value = "/render/{reportName}/{format}", method = RequestMethod.GET)
	public void renderReport(
			@ModelAttribute("parameterForm") ParameterForm parameterForm,
			@PathVariable("reportName") String reportName, //
			@RequestParam MultiValueMap<String, String> requestParams,
            @RequestParam(value = "__pageNumber", required = false) Long pageNumber,
            @RequestParam(value = "__overwrite", required = false) Boolean overwrite,
			@PathVariable("format") String format, //
			HttpServletResponse response, BindingResult errors)
					throws IOException, BirtReportException, ParseException {

		logger.debug("Rendering {} in {}.", reportName, format);

        checkPermissionFor(reportName);

        parameterFormBinder.bind(parameterForm, requestParams, errors);
		parameterFormConverter.convert(parameterForm, errors);
		parameterFormFormatter.format(parameterForm);
		parameterFormValidator.validate(parameterForm, errors);

		BirtOutputFormat outputFormat = BirtOutputFormat.from(format);
		response.setContentType(outputFormat.getContentType());

		Map<String, Object> parameters = parameterForm.asReportParameters();

		logger.debug("ParameterMap {}", parameters);

		switch (outputFormat) {
		case HTML5:
            if (pageNumber == null) {
				pageNumber = 1L;
				overwrite = true;
            }
			reportService.renderHtmlReport(reportName, parameters,
					response.getOutputStream(), pageNumber,
					overwrite == null || overwrite);
			break;
		case PDF:
			response.setHeader(CONTENT_DISPOSITION,
					"inline; filename=" + reportName + ".pdf");
			reportService.renderPDFReport(reportName, parameters,
					response.getOutputStream());
			break;
		case EXCEL2010:
			response.setHeader(CONTENT_DISPOSITION,
					"attachment; filename=" + reportName + ".xlsx");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		case EXCEL:
			response.setHeader(CONTENT_DISPOSITION,
					"attachment; filename=" + reportName + ".xls");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		}

	}

    private void checkPermissionFor(String reportName) throws BirtReportException {
        if(!securityService.hasUserValidRole(reportName)) {
            throw new BirtUnauthorizedException(reportName);
        }
    }

}
