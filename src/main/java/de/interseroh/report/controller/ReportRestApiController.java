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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.Parameter;
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

	@RequestMapping(value = "/render/{reportName}", method = RequestMethod.GET)
	public void renderReportInDefaultFormat(
			@PathVariable("reportName") String reportName,
			@RequestParam Map<String, String> parameters,
			HttpServletResponse response) throws IOException,
			BirtReportException, ParseException {
		renderReport(reportName, BirtOutputFormat.HTML5.getFormatName(),
				parameters, response);
	}

	@RequestMapping(value = "/render/{reportName}/{format}", method = RequestMethod.GET)
	public void renderReport(
			//
			@PathVariable("reportName") String reportName, //
			@PathVariable("format") String format, //
			@RequestParam Map<String, String> requestParams,
			HttpServletResponse response) throws IOException,
			BirtReportException, ParseException {
		logger.debug("Rendering " + reportName + " in " + format + ".");

		BirtOutputFormat outputFormat = BirtOutputFormat.from(format);
		response.setContentType(outputFormat.getContentType());

		Map<String, Object> parameters = convert(requestParams, reportName);

		switch (outputFormat) {
		case HTML5:
			reportService.renderHtmlReport(reportName, parameters,
					response.getOutputStream());
			break;
		case PDF:
			response.setHeader("Content-disposition", "inline; filename="
					+ reportName + ".pdf");
			reportService.renderPDFReport(reportName, parameters,
					response.getOutputStream());
			break;
		case EXCEL2010:
			response.setHeader("Content-disposition", "attachment; filename="
					+ reportName + ".xlsx");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		case EXCEL:
			response.setHeader("Content-disposition", "attachment; filename="
					+ reportName + ".xls");
			reportService.renderExcelReport(reportName, parameters,
					response.getOutputStream());
		}
		// TODO idueppe - need exception handling
	}

	public Map<String, Object> convert(Map<String, String> requestParameters,
			String reportName) throws BirtReportException, ParseException {

		Map<String, Object> params = new HashMap<>();

		// TODO idueppe - fix this poooor man converting
		Collection<Parameter> definitions = reportService
				.getParameterDefinitions(reportName);
		for (Parameter param : definitions) {
			String paramName = param.getName();
			if (requestParameters.containsKey(paramName)) {
				String paramValueStr = requestParameters.get(paramName);

				logger.debug("Trying to convert parameter " + "" + paramName
						+ "=" + paramValueStr + " to " + param.getDataType());
				Object paramValue = convert(param, paramValueStr);
				params.put(paramName, paramValue);

			}
		}

		return params;
	}

	private Object convert(Parameter param, String paramValueStr)
			throws ParseException {
		Object paramValue = null;

		switch (param.getDataType()) {
		case TYPE_FLOAT:
		case TYPE_DECIMAL:
			try {
				paramValue = Double.valueOf(paramValueStr);
			} catch (NumberFormatException nfe) {
				paramValue = paramValueStr;
			}
			break;
		case TYPE_TIME:
		case TYPE_DATE_TIME:
		case TYPE_DATE:
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			paramValue = sdf.parse(paramValueStr);
			break;
		case TYPE_ANY:
		case TYPE_STRING:
			paramValue = paramValueStr;
			break;
		}
		return paramValue;
	}

}
