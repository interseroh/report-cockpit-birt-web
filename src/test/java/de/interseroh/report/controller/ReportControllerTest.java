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

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.interseroh.report.exception.BirtReportException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.Locale;

/**
 * @author Ingo Düppe (Crowdcode)
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class ReportControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SecurityControl securityControl;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test(expected = NestedServletException.class)//BirtReportException.class)
	public void testCustomParameterViewException() throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(
				true);
		this.mockMvc.perform(get("/reports/custom/params")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterView() throws Exception {

		when(securityControl.hasUserValidRole(eq("custom"))).thenReturn(true);
		this.mockMvc.perform(get("/reports/custom/params")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewDateParameter() throws Exception {

		when(securityControl.hasUserValidRole(eq("custom"))).thenReturn(true);
		this.mockMvc
				.perform(get("/reports/custom/params")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithMissingParameter() throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(true);
		this.mockMvc.perform(
				get("/reports/cascade_parameters/params?params[customer].text=278")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("278")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithMissingParameter_Request()
			throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(true);
		this.mockMvc.perform(get("/reports/cascade_parameters/params?customer=278")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("278")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithWrongType() throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(true);
		this.mockMvc.perform(
				get("/reports/cascade_parameters/params?params[customer].text=ABC")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("help-block")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithWrongType_Request()
			throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(true);
		this.mockMvc.perform(get("/reports/cascade_parameters/params?customer=ABC")) //
				.andExpect(status().isOk()) //
				.andExpect(content().string(containsString("help-block")))
				.andDo(print());
	}

	@Test
	public void testCascadingParameterView() throws Exception {

		when(securityControl.hasUserValidRole(eq("cascade_parameters"))).thenReturn(true);
		this.mockMvc.perform(get(
				"/reports/cascade_parameters/params/cascade/customerorders?params[customer].text=278")) //
				.andExpect(status().isOk()) //
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewPost() throws Exception {

		when(securityControl.hasUserValidRole(eq("custom"))).thenReturn(true);
		this.mockMvc.perform(post("/reports/custom/params")).andExpect(status().isOk()) //
				.andExpect(content().string(containsString("Parameter")))
				.andDo(print());
	}

	@Test
	public void testMultiSelectGet() throws Exception {
		List<String> roles = Arrays.asList("ROLE_CASCADE_PARAMETERS", "ROLE_MULTISELECT");

		when(securityControl.getRoles()).thenReturn(roles);
		this.mockMvc
				.perform(
						get("/reports/multiselect?order=10123&order=10298&order=10345&customer=103"))
				.andExpect(status().is3xxRedirection()) //
				// .andExpect(content().string(containsString("10123")))
				// .andExpect(content().string(containsString("10298")))
				// .andExpect(content().string(containsString("10345")))
				.andDo(print());
	}

    @Test
    public void testDateFormField() throws Exception {
		List<String> roles = Arrays.asList("ROLE_CASCADE_PARAMETERS", "ROLE_MULTISELECT");

		when(securityControl.hasUserValidRole(eq("multiselect"))).thenReturn(true);
        this.mockMvc.perform(get("/reports/multiselect/params?dateWithFunctionParam=2015-08-17"))
                .andExpect(status().isOk()) //
				.andExpect(content().string(containsString("2015-08-17")))
                .andDo(print());

    }

    @Test
    public void testDateFormFieldWithScript() throws Exception {
		List<String> roles = Arrays.asList("ROLE_CASCADE_PARAMETERS", "ROLE_MULTISELECT");

		when(securityControl.getRoles()).thenReturn(roles);
        LocaleContextHolder.setLocale(Locale.GERMANY);
        this.mockMvc.perform(get("/reports/multiselect/params?language=DE"))
                .andExpect(status().isOk()) //
                .andExpect(content().string(containsString("01.01.15")))
                .andDo(print());

    }

}
