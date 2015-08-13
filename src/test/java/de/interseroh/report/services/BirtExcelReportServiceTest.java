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

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

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
public class BirtExcelReportServiceTest {

	private static final String OUTPUT_SUFFIX = ".xlsx";
	private static final String OUTPUT_PREFIX = "target/";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BirtReportService reportService;

	@Test
	public void testHelloWorldReport() throws Exception {
		renderPdfReport("hello_world");
	}

	@Test
	public void testSalesInvoiceReport() throws Exception {
		renderPdfReport("salesinvoice");
	}

	@Test
	public void testProductCatalogReport() throws Exception {
		renderPdfReport("productcatalog");
	}

	@Test
	public void testProductListAfterReport() throws Exception {
		renderPdfReport("productlistafter");
	}

	@Test
	public void testEmployeeAfterReport() throws Exception {
		renderPdfReport("employeeafter");
	}

	@Test
	public void testStaticCrossTableReport() throws Exception {
		renderPdfReport("staticcrosstable");
	}

	private void renderPdfReport(String reportName) throws Exception {
		String reportOutputName = OUTPUT_PREFIX + reportName + OUTPUT_SUFFIX;

		// just for printing
		reportService.getParameterGroups(reportName);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("OrderNumber", 10110);

		try (FileOutputStream out = new FileOutputStream(reportOutputName)) {
			reportService.renderExcelReport(reportName, parameters, out);
		}
	}

}
