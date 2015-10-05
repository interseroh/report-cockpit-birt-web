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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

import de.interseroh.report.controller.ParameterFormFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.interseroh.report.controller.RequestParamsBuilder;
import de.interseroh.report.domain.GenericParameter;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.SelectionParameter;
import de.interseroh.report.webconfig.WebMvcConfig;

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

        ParameterForm form = buildTestData();
        parameterFormFormatter.format(form);

        String requestParams = requestParamsBuilder
				.asRequestParams(form);

		assertThat(requestParams, is(
				"?double=55.5&boolean=false&string=value&dateTime=05.09.15&scalarMULTI=1&scalarMULTI=2"));
	}

	private ParameterForm buildTestData() {
		List<ParameterGroup> groups = new ArrayList<>();

		groups.add(new ParameterGroup() //
				.withName("group1").withSynthetic(false)
				.addScalarParameter(GenericParameter.newInstance(Double.class) //
						.withName("double").withValue(55.5))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("boolean").withValue(false)));
		groups.add(new ParameterGroup() //
				.withName("group2").withSynthetic(true)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("string").withValue("value"))
				.addScalarParameter(GenericParameter.newInstance(Date.class) //
						.withName("dateTime").withValue(fixedDate())));
		groups.add(new ParameterGroup() //
				.withName("group3").withCascading(true)
				.addScalarParameter(SelectionParameter
						.newInstance(Boolean.class).withName("radioNULL"))
				.addScalarParameter(SelectionParameter.newInstance(String.class)
						.withName("selectNULL"))
				.addScalarParameter(SelectionParameter
						.newMultiInstance(Integer[].class).withName("scalarMULTI")
						.withValue(new Integer[] { 1, 2 })));
		return new ParameterForm().withParameterGroups(groups);
	}

	private Date fixedDate() {
		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.set(2015, 8, 5, 21, 12, 23);
		return new Date(calendar.getTimeInMillis());
	}

}