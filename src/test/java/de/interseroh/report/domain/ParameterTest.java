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
package de.interseroh.report.domain;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterTest {

	@Test
	public void testEmptyParameter() throws Exception {
		GenericParameter<String> parameter = GenericParameter
				.newInstance(String.class).withRequired(true);
		assertThat(parameter.isValid(), is(false));
	}

	@Test
	public void testParameterWithDefaultValue() throws Exception {
		Parameter parameter = GenericParameter.newInstance(String[].class)
				.withRequired(true).withDefaultValue(new String[] { "1000" });

		assertThat(parameter.isValid(), is(true));
	}

	@Test
	public void testParameterWithValue() throws Exception {
		Parameter parameter = GenericParameter.newInstance(Integer.class)
				.withRequired(true).withValue(1000);
		assertThat(parameter.isValid(), is(true));
	}

	@Test
	public void testParameterNotRequired() throws Exception {
		Parameter parameter = GenericParameter.newInstance(String.class)
				.withRequired(false);
		assertThat(parameter.isValid(), is(true));
	}

	@Test
	public void testParameterWithEmptyStringMulti() throws Exception {
		Parameter parameter = GenericParameter.newInstance(String[].class)
				.withRequired(true).withValue(new String[] { " ", "  " });
		assertThat(parameter.isValid(), is(false));
	}

	@Test
	public void testParameterWithEmptyStringMultiWithDefault()
			throws Exception {
		Parameter parameter = GenericParameter.newInstance(String[].class)
				.withRequired(true).withValue(new String[] { " ", "  " })
				.withDefaultValue(new String[] { "junittest" });
		assertThat(parameter.isValid(), is(true));
	}

	@Test
	public void testParameters() throws Exception {
		ParameterForm form = new ParameterForm()
				.addParameterGroup(new ParameterGroup().addScalarParameter(
						GenericParameter.newInstance(String.class)
								.withRequired(true).withValue("1000")));
		assertThat(form.isValid(), is(true));
	}

	@Test
	public void testAssignable() throws Exception {
		assertThat(Number.class.isAssignableFrom(Integer.class), is(true));
	}
}