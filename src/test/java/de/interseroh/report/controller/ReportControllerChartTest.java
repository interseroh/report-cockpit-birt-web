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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.interseroh.report.services.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
public class ReportControllerChartTest {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SecurityService securityService;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLocalizedDateInCharReport_DE() throws Exception {
		String chartdate = "chartdate";

		when(securityService.hasUserValidRole(Matchers.eq(chartdate))).thenReturn(true);
        // TODO idueppe - charts are embedded as svg. Need to extract the url and load the svg to check
        this.mockMvc.perform(get("/api/render/chartdate/html").param("language","de"))//
                .andExpect(status().isOk()) //
                .andExpect(content().string(containsString("type=\"image/svg+xml\"")));
//				.andDo(print());
//                .andExpect(content().string(containsString("Januar")))
//                .andExpect(content().string(containsString("Dienstag")))
//                .andExpect(content().string(containsString("18.3.11")))
//                .andReturn();
	}

	@Test
	public void testLocalizedDateInCharReport_EN() throws Exception {
		String chartdate = "chartdate";

		when(securityService.hasUserValidRole(Matchers.eq(chartdate))).thenReturn(true);
		this.mockMvc.perform(get("/api/render/chartdate/html").param("language","en")) //
				.andExpect(status().isOk()) //
                .andExpect(content().string(containsString("type=\"image/svg+xml\"")));
//				.andDo(print());
//				.andExpect(content().string(containsString("January")))
//				.andExpect(content().string(containsString("Tuesday")))
//				.andExpect(content().string(containsString("3/18/11")));
	}


}
