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
package de.interseroh.report.controller;

import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.webconfig.ReportConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.interseroh.report.webconfig.WebMvcConfig;
import org.springframework.web.util.NestedServletException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, SecurityControlMock.class})
@WebAppConfiguration
public class ReportRestApiControllerTest {

	protected MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SecurityControl securityControl;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testCascadingParameterView() throws Exception {

		List<String> roles = Arrays.asList("ROLE_CASCADE_PARAMETERS");
		String cascade = "cascade_parameters";

		when(securityControl.hasUserValidRole(Matchers.eq(cascade))).thenReturn(true);
		this.mockMvc.perform(get(
				"/api/render/cascade_parameters/html?params[customer].text=112&params[order].text=10124")) //
				.andExpect(status().isOk()) //
				.andDo(print());
	}

	@Test(expected = NestedServletException.class) //BirtReportException.class)
	public void testCascadingParameterViewException() throws Exception {

		List<String> roles = Arrays.asList("ROLE_SALESINVOICE");
		String cascade = "salesinvoic";

		when(securityControl.hasUserValidRole(Matchers.eq(cascade))).thenReturn(true);
		this.mockMvc.perform(get(
				"/api/render/cascade_parameters/html?params[customer].text=112&params[order].text=10124")) //
				.andExpect(status().isOk()) //
				.andDo(print());
	}

	@Ignore//TODO schlägt auch fehl, wenn keine Rolle validiert wird.
    @Test
    public void testMultiSelectParameterView() throws Exception {
		List<String> roles = Arrays.asList("ROLE_CHART");

		when(securityControl.getRoles()).thenReturn(roles);
        this.mockMvc.perform(get(
                "/api/render/chart/html")) //
                .andExpect(status().isOk()) //
                .andDo(print());
    }


}
