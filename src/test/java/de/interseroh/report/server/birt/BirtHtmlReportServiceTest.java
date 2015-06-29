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
package de.interseroh.report.server.birt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.interseroh.report.webconfig.ReportConfig;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportConfig.class)
@PropertySource("classpath:config.properties")
public class BirtHtmlReportServiceTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BirtReportService reportService;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Test
	public void testHelloWorldReport() throws EngineException,
			FileNotFoundException, BirtReportException {
		renderHtmlReport("hello_world");
	}

	@Test
	public void testSalesInvoiceReport() throws EngineException,
			FileNotFoundException, BirtReportException {
		renderHtmlReport("salesinvoice");
	}

	@Test
	public void testProductCatalogReport() throws EngineException,
			FileNotFoundException, BirtReportException {
		renderHtmlReport("productcatalog");
	}

	private void renderHtmlReport(String reportName) throws EngineException,
			FileNotFoundException, BirtReportException {
		String reportFileName = reportName + ".rptdesign";
		String outputFileName = "target/" + reportName + ".html";

		Collection<IParameterDefn> parameterDefinitions = reportService
				.getParameterDefinitions(reportFileName);
		Map<String, Object> params = new HashMap<>();
		for (IParameterDefn definition : parameterDefinitions) {
			if ("OrderNumber".equals(definition.getName()))
				params.put("OrderNumber", 10110);
		}
		reportService.renderHtmlReport(reportFileName, params,
				new FileOutputStream(outputFileName));
	}

}
