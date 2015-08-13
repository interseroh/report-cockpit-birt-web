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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.interseroh.report.parameter.BirtConvertingTest;
import de.interseroh.report.webconfig.ReportConfig;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportConfig.class)
@PropertySource("classpath:config.properties")
public class BirtReportGenerateTest {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtConvertingTest.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	IReportEngine reportEngine;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Test
	public void testHelloWorldReport()
			throws EngineException, FileNotFoundException {
		assertThat(reportEngine, is(notNullValue()));

		String reportName = "/reports/hello_world.rptdesign";
		String outputFile = "target/hello_world.html";

		renderHtmlReport(outputFile, reportName);
	}

	@Test
	public void testSalesInvoiceReport()
			throws EngineException, FileNotFoundException {
		assertThat(reportEngine, is(notNullValue()));

		String reportName = "/reports/salesinvoice.rptdesign";
		String outputFile = "target/salesinvoice.html";

		renderHtmlReport(outputFile, reportName);
	}

	@Test
	public void testProductCatalogReport()
			throws EngineException, FileNotFoundException {
		assertThat(reportEngine, is(notNullValue()));

		String reportName = "/reports/productcatalog.rptdesign";
		String outputFile = "target/productcatalog.html";

		renderHtmlReport(outputFile, reportName);

	}

	private void renderHtmlReport(String outputFile, String reportName)
			throws EngineException, FileNotFoundException {
		String file = this.getClass().getResource(reportName).getPath();
		IReportRunnable iReportRunnable = reportEngine.openReportDesign(file);

		IGetParameterDefinitionTask parameterDefinitionTask = reportEngine
				.createGetParameterDefinitionTask(iReportRunnable);
		IRunAndRenderTask runAndRenderTask = reportEngine
				.createRunAndRenderTask(iReportRunnable);
		Collection<IParameterDefnBase> parameterDefns = parameterDefinitionTask
				.getParameterDefns(true);
		for (IParameterDefnBase parameterDefn : parameterDefns) {
			System.out
					.println("Displayname: " + parameterDefn.getDisplayName());
			logger.info("Helptext: " + parameterDefn.getHelpText());
			logger.info("Name: " + parameterDefn.getName());
			logger.info("Typename: " + parameterDefn.getTypeName());
			logger.info("ParameterType: " + BirtParameterType
					.valueOf(parameterDefn.getParameterType()));
			logger.info("PromptText: " + parameterDefn.getPromptText());
			logger.info("DefaultValue: "
					+ parameterDefinitionTask.getDefaultValue(parameterDefn));
			logger.info("Required: " + parameterDefn.getHandle());

			runAndRenderTask.setParameterValue(parameterDefn.getName(), 10101);
		}

		IRenderOption options = new RenderOption();

		HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
		htmlOptions.setOutputFormat("html");
		htmlOptions.setOutputStream(new FileOutputStream(outputFile));
		htmlOptions.setImageHandler(new HTMLServerImageHandler());
		htmlOptions.setBaseImageURL("images");
		htmlOptions.setImageDirectory("target/images");
		runAndRenderTask.setRenderOption(htmlOptions);
		runAndRenderTask.run();
		runAndRenderTask.close();
	}

}
