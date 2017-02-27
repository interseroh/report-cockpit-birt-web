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
package de.interseroh.report.domain.visitors;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.interseroh.report.controller.ParameterFormFormatter;
import de.interseroh.report.controller.RequestParamsBuilder;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.parameter.RequestParamsFixture;
import de.interseroh.report.webconfig.WebMvcConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class RequestParamsBuilderTest {

	@Autowired
	private RequestParamsBuilder requestParamsBuilder;

    @Autowired
    private ParameterFormFormatter parameterFormFormatter;

	@Test
	public void testConversionService() throws Exception {
        LocaleContextHolder.setLocale(Locale.GERMAN);

		ParameterForm form = RequestParamsFixture.buildTestData();
		parameterFormFormatter.format(form);

        String requestParams = requestParamsBuilder
				.asRequestParams(form);

		assertThat(requestParams, is(
				"?double=55.5&boolean=false&string=value&dateTime=05.09.15&scalarMULTI=1&scalarMULTI=2"));
	}


}