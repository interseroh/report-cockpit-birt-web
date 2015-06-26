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
package de.interseroh.report;

import de.interseroh.report.server.birt.BirtDataType;
import de.interseroh.report.server.birt.BirtDataTypeTest;
import de.interseroh.report.server.location.service.LocationServiceImplTest;
import de.interseroh.report.server.reports.BirtReportGenerateTest;
import de.interseroh.report.webconfig.SecurityConfigAuthTest;
import de.interseroh.report.webconfig.SecurityConfigTest;
import de.interseroh.report.webconfig.UiJaxRsConfigTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        SecurityConfigTest.class,
        SecurityConfigAuthTest.class,
        UiJaxRsConfigTest.class,
        LocationServiceImplTest.class,
        BirtReportGenerateTest.class,
        BirtDataTypeTest.class})
public class AllUiTests {
}
