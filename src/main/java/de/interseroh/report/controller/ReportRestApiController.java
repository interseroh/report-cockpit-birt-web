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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.interseroh.report.model.ParameterForm;
import de.interseroh.report.model.ParameterLogVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.ScalarParameter;
import de.interseroh.report.services.BirtOutputFormat;
import de.interseroh.report.services.BirtReportService;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Controller
@RequestMapping("/api")
public class ReportRestApiController {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportRestApiController.class);

	@Autowired
	private BirtReportService reportService;

	@Autowired
	private ConversionService conversionService;

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
        logger.debug("New ParameterForm for Report {}. ", reportName);
        ParameterForm form = new ParameterForm() //
                .withReportName(reportName) //
                .withGroupParameters(
                        reportService.getParameterGroups(reportName));

        new ParameterLogVisitor().printParameters(form.getGroups());
        return form;

    }

	@RequestMapping(value = "/render/{reportName}", method = RequestMethod.GET)
	public void renderReportInDefaultFormat(
			@ModelAttribute("parameterForm") ParameterForm parameterForm,
			@PathVariable("reportName") String reportName,
			HttpServletResponse response)
					throws IOException, BirtReportException, ParseException {
		renderReport(parameterForm, reportName, BirtOutputFormat.HTML5.getFormatName(), response);
	}

    @RequestMapping(value = "/render/{reportName}/{format}", method = RequestMethod.GET)
	public void renderReport(
			@ModelAttribute("parameterForm") ParameterForm parameterForm,
			@PathVariable("reportName") String reportName, //
			@PathVariable("format") String format, //
            HttpServletResponse response)
					throws IOException, BirtReportException, ParseException {
		logger.debug("Rendering " + reportName + " in " + format + ".");

		BirtOutputFormat outputFormat = BirtOutputFormat.from(format);
		response.setContentType(outputFormat.getContentType());

		Map<String, Object> parameters = parameterForm.asReportParameters();

        logger.debug("ParameterMap {}", parameters);

		switch (outputFormat) {
		case HTML5:
			reportService.renderHtmlReport(reportName, parameters,
					response.getOutputStream());
			break;
		case PDF:
			response.setHeader("Content-disposition",
					"inline; filename=" + reportName + ".pdf");
			reportService.renderPDFReport(reportName, parameters,
					response.getOutputStream());
			break;
		case EXCEL2010:
			response.setHeader("Content-disposition",
					"attachment; filename=" + reportName + ".xlsx");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		case EXCEL:
			response.setHeader("Content-disposition",
					"attachment; filename=" + reportName + ".xls");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		}
		// TODO idueppe - need exception handling
	}



}
