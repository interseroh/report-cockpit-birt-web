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
package de.interseroh.report.parameter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.interseroh.report.services.BirtReportService;
import de.interseroh.report.webconfig.WebMvcConfig;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class BirtConvertingTest {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtConvertingTest.class);

	@Autowired
	private BirtReportService reportService;

	@Autowired
	private ConversionService conversionService;

	@Test
	public void testSalesInvoiceReport() throws Exception {
		renderPdfReport("salesinvoice");
	}

	@Test
	public void testCascadeParameters() throws Exception {
		renderPdfReport("cascade_parameters");
	}

	@Test
	public void testFromStringToDate() throws Exception {
		Date converted = conversionService.convert("2015-01-22", Date.class);
		assertThat(converted, is(new Date(115, 0, 22)));
	}

	@Test
	public void testFromDateToString() throws Exception {
		String converted = conversionService.convert(new Date(115, 0, 22),
				String.class);
		assertThat(converted, is("2015-01-22"));
	}

	public void testConversion() throws Exception {
		BigDecimal convert = conversionService.convert("12312",
				BigDecimal.class);

		logger.info((" " + convert));
	}

	private void renderPdfReport(String reportName) throws Exception {
		String outputFileName = "target/" + reportName + ".pdf";

		reportService.getParameterGroups(reportName);

		// Map<String, Object> params = new HashMap<>();
		// for (Parameter parameter : parameterDefinitions) {
		// if ("OrderNumber".equals(parameter.getName()))
		// params.put("OrderNumber", 10110);
		// }
		// params.put("order", 10298);
		// params.put("customer", 112);
		// try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
		// reportService.renderPDFReport(reportName, params, fos);
		// }
	}

}
