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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.interseroh.report.webconfig.WebMvcConfig;

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

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testCustomParameterView() throws Exception {
		this.mockMvc
				.perform(get("/reports/custom/params"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewDateParameter() throws Exception {
		this.mockMvc
				.perform(get("/reports/custom/params"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("Parameter")))
				.andExpect(content().string(containsString("radio")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithMissingParameter() throws Exception {
		this.mockMvc
				.perform(
						get("/reports/cascade_parameters/params?params[customer].value=278"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("278")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithMissingParameter_Request()
			throws Exception {
		this.mockMvc
				.perform(get("/reports/cascade_parameters/params?customer=278"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("278")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithWrongType() throws Exception {
		this.mockMvc
				.perform(
						get("/reports/cascade_parameters/params?params[customer].value=ABC"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("help-block")))
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewWithWrongType_Request() throws Exception {
		this.mockMvc
				.perform(get("/reports/cascade_parameters/params?customer=ABC"))
				//
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("help-block")))
				.andDo(print());
	}

	@Test
	public void testCascadingParameterView() throws Exception {
		this.mockMvc
				.perform(
						get("/reports/cascade_parameters/params/cascade/customerorders?params[customer].value=278")) //
				.andExpect(status().isOk()) //
				.andDo(print());
	}

	@Test
	public void testCustomParameterViewPost() throws Exception {
		this.mockMvc.perform(post("/reports/custom/params"))
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("Parameter")))
				.andDo(print());
	}

	@Test
	public void testMultiSelectGet() throws Exception {
		this.mockMvc
				.perform(
						get("/reports/multiselect?order=10123&order=10298&order=10345&customer=103"))
				.andExpect(status().isOk())
				//
				.andExpect(content().string(containsString("10123")))
				.andExpect(content().string(containsString("10298")))
				.andExpect(content().string(containsString("10345")))
				.andDo(print());
	}
}
