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
package de.interseroh.report.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IPageHandler;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportDocumentInfo;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.ULocale;

import de.interseroh.report.domain.ParameterBuilder;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.SelectionParameter;
import de.interseroh.report.domain.visitors.ParameterLogVisitor;
import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.exception.RenderReportException;
import de.interseroh.report.pagination.Pagination;

@Service
@PropertySource({ "classpath:report-config.properties" })
public class BirtReportServiceBean implements BirtReportService {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtReportServiceBean.class);

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

		String baseImageContextPath = environment
				.getProperty(REPORT_BASE_IMAGE_CONTEXT_PATH_KEY,
						"/report-cockpit-birt");
		baseImageURL = baseImageContextPath + environment
				.getProperty(REPORT_BASE_IMAGE_URL_KEY);
		String defaultDirectory = environment.getProperty("java.io.tmpdir");
		imageDirectory = environment
				.getProperty(REPORT_IMAGE_DIRECTORY_KEY, defaultDirectory);

		logger.info("\tBaseImageUrl:   " + baseImageURL);
		logger.info("\tImageDirectory: " + imageDirectory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterGroup> getParameterGroups(String reportName)
			throws BirtReportException {
		try {
			String reportFileName = absolutePathOf(reportFileName(reportName));

			IReportRunnable iReportRunnable = reportEngine
					.openReportDesign(reportFileName);
			IGetParameterDefinitionTask task = reportEngine
					.createGetParameterDefinitionTask(iReportRunnable);

			boolean includeParameterGroups = true;
			Collection<IParameterDefnBase> definitions = task
					.getParameterDefns(includeParameterGroups);

			if (logger.isTraceEnabled()) {
				BirtReportUtil.printParameterDefinitions(definitions, task);
			}

			List<ParameterGroup> groups = new ParameterBuilder(task)
					.build(definitions);

			ParameterLogVisitor.printParameters(groups);

			return groups;
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while getting parameter definition for " + reportName
							+ ".", e);
		}
	}

	@Override
	public Pagination getPageInfos(final String reportName,
			final ParameterForm parameters) throws BirtReportException {
		try {
			String reportFileName = absolutePathOf(reportFileName(reportName));
			String documentFileName = absoluteTempPath(
					documentFileName(reportName));

			if (parameters.isOverwrite()) {
				recreateReportDocument(reportName,
						parameters.asReportParameters(), true, reportFileName,
						documentFileName);
			}

			IReportDocument reportDocument = reportEngine
					.openReportDocument(documentFileName);

			long pages = reportDocument.getPageCount();
			long currentPage = (parameters.getPageNumber() != null) ?
					parameters.getPageNumber() :
					1l;
			return new Pagination(currentPage, pages);
		} catch (EngineException | IOException ex) {
			logger.error(String.format(
					"Report corrupt or path not valid - page information for %s not available",
					reportName));
			throw new BirtReportException(String.format(
					"Report corrupt or path not valid - page information for %s not available",
					reportName), ex);
		}
	}

	private void recreateReportDocument(String reportName,
			Map<String, Object> parameters, boolean overwrite,
			String reportFileName, String documentFileName)
			throws IOException, EngineException {
		if (needToCreateNewDocumentFile(reportName, overwrite)) {
			logger.info("Need to create document file for {} report.",
					reportName);
			IReportRunnable reportRunnable = reportEngine
					.openReportDesign(reportFileName);
			IRunTask runTask = reportEngine.createRunTask(reportRunnable);
			injectParameters(parameters, runTask);
			runTask.run(documentFileName);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void loadOptionsForCascadingGroup(String reportName,
			ParameterGroup group) throws BirtReportException {
		try {
			String reportFileName = absolutePathOf(reportFileName(reportName));

			IReportRunnable iReportRunnable = reportEngine
					.openReportDesign(reportFileName);
			IGetParameterDefinitionTask task = reportEngine
					.createGetParameterDefinitionTask(iReportRunnable);

			ParameterBuilder builder = new ParameterBuilder(task);

			List params = new ArrayList();

			for (ScalarParameter parameter : group.getParameters()) {
				if (parameter instanceof SelectionParameter) {
					SelectionParameter selection = (SelectionParameter) parameter;
					Collection<IParameterSelectionChoice> choices = task
							.getSelectionListForCascadingGroup(group.getName(),
									params.toArray());
					selection.setOptions(builder.toOptions(choices));
					params.add(selection.getValue());
				}
			}
		} catch (EngineException | IOException e) {
			throw new BirtReportException(
					"Error while getting cascading parameters for " + reportName
							+ ".", e);
		}
	}

	@Override
	public void renderHtmlReport(final String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(
					reportName);

			injectParameters(parameters, runAndRenderTask);
			injectLocale(runAndRenderTask);

			HTMLRenderOption htmlOptions = new HTMLRenderOption();
			htmlOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
			htmlOptions.setOutputStream(out);
			htmlOptions.setImageHandler(new HTMLServerImageHandler());
			htmlOptions.setEmbeddable(true);

			htmlOptions.setBaseImageURL(baseImageURL);
			htmlOptions.setImageDirectory(imageDirectory);

			htmlOptions.setHtmlPagination(true);
			htmlOptions.setMasterPageContent(true);
			htmlOptions.setPageFooterFloatFlag(true);

			runAndRenderTask.setPageHandler(new IPageHandler() {
				@Override
				public void onPage(int pageNumber, boolean checkpoint,
						IReportDocumentInfo doc) {
					logger.info("Number: {} ({})", pageNumber, checkpoint);
					try {
						if (doc != null) {
							IReportDocument reportDocument = doc
									.openReportDocument();
							logger.info(reportDocument.toString());
						}
					} catch (BirtException e) {
						logger.error(
								"Error while trying to render html report of {}",
								reportName);
						logger.error(e.getMessage(), e);
					}

				}
			});

			runAndRenderTask(runAndRenderTask, htmlOptions);
		} catch (EngineException | IOException e) {
			throw new RenderReportException("html", reportName, e);
		}
	}

	@Override
	public void renderPDFReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(
					reportName);

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

	@Override
	public void renderExcelReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException {
		try {
			IRunAndRenderTask runAndRenderTask = createRunAndRenderTask(
					reportName);
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

	private void runAndRenderTask(IRunAndRenderTask runAndRenderTask,
			IRenderOption renderOptions) throws EngineException {
		injectLocale(runAndRenderTask);
		runAndRenderTask.setRenderOption(renderOptions);
		runAndRenderTask.run();
		runAndRenderTask.close();
	}

	private IRunAndRenderTask createRunAndRenderTask(String reportName)
			throws EngineException, IOException {
		String reportFileName = absolutePathOf(reportFileName(reportName));
		IReportRunnable iReportRunnable = reportEngine
				.openReportDesign(reportFileName);
		IRunAndRenderTask task = reportEngine
				.createRunAndRenderTask(iReportRunnable);
		injectLocale(task);

		return task;
	}

	@Override
	public void renderHtmlReport(String reportName,
			Map<String, Object> parameters, OutputStream out, long pageNumber,
			boolean overwrite) throws BirtReportException {
		try {
			String reportFileName = absolutePathOf(reportFileName(reportName));
			String documentFileName = absoluteTempPath(
					documentFileName(reportName));

			recreateReportDocument(reportName, parameters, overwrite,
					reportFileName, documentFileName);

			IReportDocument reportDocument = reportEngine
					.openReportDocument(documentFileName);

			HTMLRenderOption htmlOptions = new HTMLRenderOption();
			htmlOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
			htmlOptions.setOutputStream(out);
			htmlOptions.setImageHandler(new HTMLServerImageHandler());
			htmlOptions.setEmbeddable(true);

			htmlOptions.setBaseImageURL(baseImageURL);
			htmlOptions.setImageDirectory(imageDirectory);

			htmlOptions.setHtmlPagination(true);
			htmlOptions.setMasterPageContent(false);
			htmlOptions.setPageFooterFloatFlag(true);

			IRenderTask renderTask = reportEngine
					.createRenderTask(reportDocument,
							reportDocument.getReportRunnable());
			renderTask.setRenderOption(htmlOptions);
			renderTask.setPageNumber(pageNumber);

			injectLocale(renderTask);

			renderTask.render();
			renderTask.close();

		} catch (EngineException | IOException e) {
			throw new BirtReportException(e);
		}
	}

	private boolean needToCreateNewDocumentFile(String reportName,
			boolean overwrite) throws IOException {
		String reportFileName = absolutePathOf(reportFileName(reportName));
		String documentFileName = absoluteTempPath(
				documentFileName(reportName));

		File reportFile = new File(reportFileName);
		File documentFile = new File(documentFileName);

		boolean outdated =
				reportFile.lastModified() > documentFile.lastModified();

		return overwrite || !documentFile.exists() || outdated;
	}

	private String reportFileName(String reportName) {
		return reportName + REPORT_FILE_SUFFIX;
	}

	private String documentFileName(String reportName) {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = (authentication != null) ?
				authentication.getName() :
				UUID.randomUUID().toString();

		return reportName + '_' + name + '_' + DOCUMENT_FILE_SUFFIX;
	}

	private void injectParameters(Map<String, Object> parameters,
			IEngineTask runAndRenderTask) {
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			runAndRenderTask.setParameterValue(parameter.getKey(),
					parameter.getValue());
		}
	}

	private void injectLocale(IEngineTask task) {
		Locale locale = LocaleContextHolder.getLocale();

		logger.debug("Setting Report Locale to " + locale);

		task.setLocale(locale);
		task.setLocale(ULocale.forLocale(locale));

		logger.debug("Report Language is " + task.getULocale().getBaseName());
	}

	private String absolutePathOf(String reportFileName) throws IOException {
		String location = appendSeparatorIfNeeded(
				environment.getProperty(REPORT_SOURCE_URL_KEY))
				+ reportFileName;
		Resource resource = resourceLoader.getResource(location);
		return resource.getFile().getAbsolutePath();
	}

	private String absoluteTempPath(String fileName) throws IOException {
		return appendSeparatorIfNeeded(
				environment.getProperty("java.io.tmpdir")) + fileName;
	}

	private String appendSeparatorIfNeeded(String path) {
		if (path != null && !path.isEmpty()
				&& path.charAt(path.length() - 1) != File.separatorChar) {
			return path + File.separatorChar;
		} else {
			return path;
		}
	}

}
