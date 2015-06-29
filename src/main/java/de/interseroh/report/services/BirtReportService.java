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
package de.interseroh.report.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import de.interseroh.report.exception.BirtReportException;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@PropertySource({ "classpath:report-config.properties" })
public class BirtReportService {

	private static final Logger logger = Logger
			.getLogger(BirtReportService.class);

	public static final String REPORT_SOURCE_URL_KEY = "report.source.url";
	public static final String REPORT_BASE_IMAGE_URL_KEY = "report.base.image.url";
	public static final String REPORT_IMAGE_DIRECTORY_KEY = "report.image.directory";
	public static final String REPORT_BASE_IMAGE_CONTEXT_PATH_KEY = "report.image.contextpath";

	@Autowired
	private Environment environment;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private IReportEngine reportEngine;

	private String baseImageURL;
	private String imageDirectory;

	@PostConstruct
	public void init() {
		logger.info("Initializing Birt Report Service URLs");

		String baseImageContextPath = environment.getProperty(
				REPORT_BASE_IMAGE_CONTEXT_PATH_KEY, "/report-cockpit-birt");
		baseImageURL = baseImageContextPath
				+ environment.getProperty(REPORT_BASE_IMAGE_URL_KEY);
		String defaultDirectory = environment.getProperty("java.io.tmpdir");
		imageDirectory = environment.getProperty(REPORT_IMAGE_DIRECTORY_KEY,
				defaultDirectory);

		logger.info("\tBaseImageUrl:   " + baseImageURL);
		logger.info("\tImageDirectory: " + imageDirectory);
	}

	public Collection<IParameterDefn> getParameterDefinitions(String reportName)
			throws BirtReportException {
		try {
			IReportRunnable iReportRunnable = reportEngine
					.openReportDesign(absolutePathOf(reportName));
			IGetParameterDefinitionTask parameterDefinitionTask = reportEngine
					.createGetParameterDefinitionTask(iReportRunnable);

			boolean includeParameterGroups = true;
			Collection<IParameterDefn> parameterDefns = parameterDefinitionTask
					.getParameterDefns(includeParameterGroups);
			printParameterDefinitions(parameterDefns, parameterDefinitionTask);

			return parameterDefns;
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while getting parameter definition for "
							+ reportName + ".", e);
		}
	}

	public void renderHtmlReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(reportName);

			injectParameters(parameters, runAndRenderTask);

			HTMLRenderOption htmlOptions = new HTMLRenderOption();
			htmlOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
			htmlOptions.setOutputStream(out);
			htmlOptions.setImageHandler(new HTMLServerImageHandler());
			htmlOptions.setEmbeddable(true);

			htmlOptions.setBaseImageURL(baseImageURL);
			htmlOptions.setImageDirectory(imageDirectory);

			runAndRender(runAndRenderTask, htmlOptions);
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while rendering pdf for report " + reportName + ".",
					e);
		}
	}

	public void renderPDFReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(reportName);

			injectParameters(parameters, runAndRenderTask);

			PDFRenderOption pdfOptions = new PDFRenderOption();
			pdfOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
			pdfOptions.setOutputStream(out);
			pdfOptions.setEmbededFont(true); // TODO idueppe - should be
												// configurable from cockpit
			pdfOptions.setImageHandler(new HTMLServerImageHandler());

			runAndRender(runAndRenderTask, pdfOptions);

		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while rendering pdf for report " + reportName + ".",
					e);
		}
	}

	public void renderExcelReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(reportName);

			injectParameters(parameters, runAndRenderTask);

			EXCELRenderOption excelRenderOptions = new EXCELRenderOption();
			excelRenderOptions.setOutputFormat("xlsx");
			excelRenderOptions.setOutputStream(out);
			excelRenderOptions.setEnableMultipleSheet(true); // TODO idueppe -
																// should be
																// configurable
																// from cockpit
			excelRenderOptions.setHideGridlines(true); // TODO idueppe - should
														// be configurable from
														// cockpit
			// excelRenderOptions.setOfficeVersion(); // TODO idueppe - should
			// be configurable from cockpit
			excelRenderOptions.setImageHandler(new HTMLServerImageHandler());

			runAndRender(runAndRenderTask, excelRenderOptions);
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while rendering excel export for report "
							+ reportName + ".", e);
		}
	}

	private void runAndRender(IRunAndRenderTask runAndRenderTask,
			IRenderOption renderOptions) throws EngineException {
		runAndRenderTask.setRenderOption(renderOptions);
		runAndRenderTask.run();
		runAndRenderTask.close();
	}

	private IRunAndRenderTask createRunAndRenderTask(String reportName)
			throws EngineException, IOException {
		IReportRunnable iReportRunnable = reportEngine
				.openReportDesign(absolutePathOf(reportName));
		return reportEngine.createRunAndRenderTask(iReportRunnable);
	}

	private void injectParameters(Map<String, Object> parameters,
			IEngineTask runAndRenderTask) {
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			runAndRenderTask.setParameterValue(parameter.getKey(),
					parameter.getValue());
		}
	}

	private String absolutePathOf(String reportName) throws IOException {
		String location = environment.getProperty(REPORT_SOURCE_URL_KEY) + "/"
				+ reportName;
		Resource resource = resourceLoader.getResource(location);
		return resource.getFile().getAbsolutePath();
	}

	private void printParameterDefinitions(
			Collection<IParameterDefn> parameterDefinitions,
			IGetParameterDefinitionTask task) {
		for (IParameterDefn parameterDefn : parameterDefinitions) {
			System.out
					.println("Displayname: " + parameterDefn.getDisplayName());
			System.out.println("Helptext: " + parameterDefn.getHelpText());
			System.out.println("Name: " + parameterDefn.getName());
			System.out.println("Typename: " + parameterDefn.getTypeName());
			System.out.println("ParameterType: "
					+ BirtParameterType.valueOf(parameterDefn
							.getParameterType()));
			System.out.println("DataType: "
					+ BirtDataType.valueOf(parameterDefn.getDataType()));
			System.out.println("PromptText: " + parameterDefn.getPromptText());
			System.out.println("DefaultValue: "
					+ task.getDefaultValue(parameterDefn));
			System.out.println("Required: " + parameterDefn.isRequired());

		}

	}

}