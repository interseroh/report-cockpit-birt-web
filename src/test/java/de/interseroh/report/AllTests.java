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
package de.interseroh.report;

import de.interseroh.report.services.SecurityServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.interseroh.report.controller.CustomReportControllerTest;
import de.interseroh.report.controller.ParameterFormFormatterTest;
import de.interseroh.report.controller.ReportControllerTest;
import de.interseroh.report.controller.ReportRestApiControllerTest;
import de.interseroh.report.controller.SecurityControlTest;
import de.interseroh.report.domain.ParameterTest;
import de.interseroh.report.domain.ParameterToMapVisitorTest;
import de.interseroh.report.domain.visitors.ReportParamsBuilderTest;
import de.interseroh.report.parameter.BirtConvertingTest;
import de.interseroh.report.services.BirtDataTypeTest;
import de.interseroh.report.services.BirtExcelReportServiceTest;
import de.interseroh.report.services.BirtHtmlReportServiceTest;
import de.interseroh.report.services.BirtOutputFormatTest;
import de.interseroh.report.services.BirtParameterTypeTest;
import de.interseroh.report.services.BirtPdfReportServiceTest;
import de.interseroh.report.services.BirtReportGenerateTest;
import de.interseroh.report.services.BirtReportServiceBeanTest;
import de.interseroh.report.webconfig.SecurityConfigAuthTest;
import de.interseroh.report.webconfig.SecurityConfigTest;

@RunWith(Suite.class)
@SuiteClasses({ //
SecurityConfigTest.class, //
		SecurityConfigAuthTest.class, //
		BirtReportGenerateTest.class, //
		BirtPdfReportServiceTest.class, //
		BirtHtmlReportServiceTest.class, //
		BirtExcelReportServiceTest.class, //
		BirtDataTypeTest.class, //
		BirtParameterTypeTest.class, //
		BirtOutputFormatTest.class, //
		ParameterTest.class, //
		ParameterToMapVisitorTest.class, //
		BirtReportServiceBeanTest.class, //
		ReportControllerTest.class, //
		ReportRestApiControllerTest.class, //
		ReportParamsBuilderTest.class, //
		CustomReportControllerTest.class,//
		SecurityControlTest.class,//
		ParameterFormFormatterTest.class,//
		BirtConvertingTest.class, //
        SecurityServiceTest.class

})
public class AllTests {
}
