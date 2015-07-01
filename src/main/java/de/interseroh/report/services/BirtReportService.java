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
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.exception.RenderReportException;
import de.interseroh.report.model.Parameter;

@Service
@PropertySource({ "classpath:report-config.properties" })
public class BirtReportService {

	private static final Logger logger = Logger
			.getLogger(BirtReportService.class);

	public static final String REPORT_SOURCE_URL_KEY = "report.source.url";
	public static final String REPORT_BASE_IMAGE_URL_KEY = "report.base.image.url";
	public static final String REPORT_IMAGE_DIRECTORY_KEY = "report.image.directory";
	public static final String REPORT_BASE_IMAGE_CONTEXT_PATH_KEY = "report.image.contextpath";
	public static final String REPORT_FILE_SUFFIX = ".rptdesign";

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

	public Collection<Parameter> getParameterDefinitions(String reportName)
			throws BirtReportException {
		try {
			String reportFileName = absolutePathOf(reportFileName(reportName));

			IReportRunnable iReportRunnable = reportEngine
					.openReportDesign(reportFileName);
			IGetParameterDefinitionTask task = reportEngine
					.createGetParameterDefinitionTask(iReportRunnable);

			boolean includeParameterGroups = true;
			Collection<IParameterDefnBase> parameterDefinitions = task
					.getParameterDefns(includeParameterGroups);

			Collection<Parameter> params = extractParameters(task,
					parameterDefinitions);

			printParameterDefinitions(parameterDefinitions, task);

			return params;
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while getting parameter definition for "
							+ reportName + ".", e);
		}
	}

	private Collection<Parameter> extractParameters(
			IGetParameterDefinitionTask task,
			Collection<IParameterDefnBase> parameterDefinitions) {
		Collection<Parameter> params = new ArrayList<>();
		for (IParameterDefnBase definition : parameterDefinitions) {
			Parameter parameter = new Parameter();
			parameter.setName(definition.getName());
			parameter.setDisplayLabel(definition.getDisplayName());
			if (definition instanceof IParameterDefn) {
				IParameterDefn param = (IParameterDefn) definition;
				parameter.setDataType(BirtDataType.valueOf(param.getDataType())
						.getHtmlFieldType());
				Object defaultValue = task.getDefaultValue(definition);
				// TODO idueppe - convert default value to string
				if (defaultValue != null)
					defaultValue = defaultValue.toString();
				parameter.setDefaultValue((String) defaultValue);
			}

			params.add(parameter);
		}
		return params;
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

			runAndRenderTask(runAndRenderTask, htmlOptions);
		} catch (EngineException | IOException e) {
			throw new RenderReportException("html", reportName, e);
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

			runAndRenderTask(runAndRenderTask, pdfOptions);

		} catch (EngineException | IOException e) {
			throw new RenderReportException("pdf", reportName, e);
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
			excelRenderOptions.setEnableMultipleSheet(true);
			// TODO idueppe - should be configurable from cockpit
			excelRenderOptions.setHideGridlines(true);
			// TODO idueppe - should be configurable from cockpit
			// excelRenderOptions.setOfficeVersion();
			// TODO idueppe - should be configurable from cockpit
			excelRenderOptions.setImageHandler(new HTMLServerImageHandler());

			runAndRenderTask(runAndRenderTask, excelRenderOptions);
		} catch (EngineException | IOException e) {
			throw new RenderReportException("excel", reportName, e);
		}
	}

	private String reportFileName(String reportName) {
		return reportName + REPORT_FILE_SUFFIX;
	}

	private void runAndRenderTask(IRunAndRenderTask runAndRenderTask,
			IRenderOption renderOptions) throws EngineException {
		runAndRenderTask.setRenderOption(renderOptions);
		runAndRenderTask.run();
		runAndRenderTask.close();
	}

	private IRunAndRenderTask createRunAndRenderTask(String reportName)
			throws EngineException, IOException {
		String reportFileName = absolutePathOf(reportFileName(reportName));
		IReportRunnable iReportRunnable = reportEngine
				.openReportDesign(reportFileName);
		return reportEngine.createRunAndRenderTask(iReportRunnable);
	}

	private void injectParameters(Map<String, Object> parameters,
			IEngineTask runAndRenderTask) {
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			runAndRenderTask.setParameterValue(parameter.getKey(),
					parameter.getValue());
		}
	}

	private String absolutePathOf(String reportFileName) throws IOException {
		String location = environment.getProperty(REPORT_SOURCE_URL_KEY) + "/"
				+ reportFileName;
		Resource resource = resourceLoader.getResource(location);
		return resource.getFile().getAbsolutePath();
	}

	private void printParameterDefinitions(
			Collection<IParameterDefnBase> parameterDefinitions,
			IGetParameterDefinitionTask task) {
		for (IParameterDefnBase definition : parameterDefinitions) {
			PrintStream out = System.out;
            out.println("----------------------------------------------");
			out.println("Displayname: " + definition.getDisplayName());
			out.println("Helptext: " + definition.getHelpText());
			out.println("Name: " + definition.getName());
			out.println("Typename: " + definition.getTypeName());
			out.println("ParameterType: "
					+ BirtParameterType.valueOf(definition
							.getParameterType()));

			if (definition instanceof IScalarParameterDefn) {
				IScalarParameterDefn scalar = (IScalarParameterDefn) definition;
                out.println("DataType: "
                        + BirtDataType.valueOf(scalar.getDataType()));
                out.println("PromptText: " + scalar.getPromptText());
                out.println("Required: "+ scalar.isRequired());
				out.println("AllowNewValues: " + scalar.allowNewValues());
				out.println("DisplayInFixedOrder: " + scalar.displayInFixedOrder());
				out.println("IsValueConcealed: " + scalar.isValueConcealed());
				out.println("DisplayFormat: " + scalar.getDisplayFormat());
				out.println("ControlType: " + scalar.getControlType());
				out.println("DefaultValue: " + scalar.getDefaultValue());
				out.println("ScalarParameterType: " + scalar.getScalarParameterType());


			}

            if (definition instanceof IParameterGroupDefn) {
                IParameterGroupDefn group = (IParameterGroupDefn) definition;
                printParameterDefinitions(group.getContents(), task);

                Collection<IParameterSelectionChoice> cascadingGroup = task
                        .getSelectionListForCascadingGroup
                        (group.getName(), new Object[]{});
                printSelectionChoices(cascadingGroup);

                System.out.println(".......");
                cascadingGroup = task.getSelectionListForCascadingGroup(group
                        .getName(), new Object[]{103});
                printSelectionChoices(cascadingGroup);
            }

            printSelectionList(definition,task);

		}
	}

    private void printSelectionList(IParameterDefnBase paramDefn,
                                    IGetParameterDefinitionTask task) {

        Collection<IParameterSelectionChoice> selectionChoices = task
                .getSelectionList
                        (paramDefn.getName());

        printSelectionChoices(selectionChoices);




    }

    private void printSelectionChoices(Collection<IParameterSelectionChoice> selectionChoices) {
        System.out.println("---------- CHOICES");
        if (selectionChoices != null) {
            for (IParameterSelectionChoice selectionChoice : selectionChoices) {
                System.out.print("Label: " + selectionChoice.getLabel());
                System.out.println("/ Value: " + selectionChoice.getValue());
            }
        }
    }

}