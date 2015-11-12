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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import de.interseroh.report.domain.GenericParameter;
import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.SelectionParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ReportParamsBuilderTest {

	@Test
	public void testBuild() throws Exception {
		Map<String, Object> map = new ReportParamsBuilder()
				.build(buildTestData());

		assertThat((Double) map.get("double"), is(55.5));
		assertThat((Boolean) map.get("boolean"), is(false));
		assertThat((Integer[]) map.get("selectMULTI"),
				is(new Integer[] { 1, 2 }));
	}

	private List<Parameter> buildTestData() {
		List<Parameter> params = new ArrayList<>();

		params.add(new ParameterGroup() //
				.withName("group1").withSynthetic(false)
				.addScalarParameter(GenericParameter.newInstance(Double.class) //
						.withName("double").withValue(55.5))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("boolean").withValue(false)));
		params.add(new ParameterGroup() //
				.withName("group2").withSynthetic(true)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("string").withValue("value"))
				.addScalarParameter(GenericParameter.newInstance(Date.class) //
						.withName("dateTime").withValue(fixedDate())));
		params.add(new ParameterGroup() //
				.withName("group3").withCascading(true)
				.addScalarParameter(SelectionParameter
						.newInstance(Boolean.class).withName("radioNULL"))
				.addScalarParameter(SelectionParameter.newInstance(String.class)
						.withName("selectNULL"))
				.addScalarParameter(SelectionParameter
						.newInstance(Integer[].class).withName("selectMULTI")
						.withValue(new Integer[] { 1, 2 })));
		return params;
	}

	private Date fixedDate() {
		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.set(2015, 8, 5, 21, 12, 23);
		return new Date(calendar.getTimeInMillis());
	}
}