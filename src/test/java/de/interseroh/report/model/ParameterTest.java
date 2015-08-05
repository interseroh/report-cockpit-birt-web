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
package de.interseroh.report.model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterTest {

	@Test
	public void testEmptyParameter() throws Exception {
		StringParameter parameter = new StringParameter();
		assertThat(parameter.isUnset(), is(false));
	}

	@Test
	public void testParameterWithDefaultValue() throws Exception {
		StringParameter parameter = new StringParameter().withRequired(true)
				.withDefaultValue("1000");

		assertThat(parameter.isUnset(), is(false));
	}

	@Test
	public void testParameterWithValue() throws Exception {
		StringParameter parameter = new StringParameter().withRequired(true)
				.withValue("1000");

		assertThat(parameter.isUnset(), is(false));
	}

	@Test
	public void testParameterNotRequired() throws Exception {
		StringParameter parameter = new StringParameter().withRequired(false)
				.withDefaultValue("1000").withValue("1000");
		assertThat(parameter.isUnset(), is(false));
	}

	@Test
	public void testParameters() throws Exception {
		ParameterForm form = new ParameterForm()
				.addGroupParameter(new DefaultGroupParameter()
						.addScalarParameter(new StringParameter()
								.withRequired(true).withValue("1000")));
		assertThat(form.isValid(), is(true));
	}

	@Test
	public void testAsRequestParameter() throws Exception {
		ParameterForm form = new ParameterForm().addGroupParameter(new DefaultGroupParameter()
				.addScalarParameter(new SingleSelectParameter<Long>()
						.withName("customer").withValue(123l))
				.addScalarParameter(new SingleSelectParameter<Long>()
						.withName("order").withValue(321l)));
        assertThat(form.asRequestParams(),is("?params[customer]=123&params[order]=321"));
	}
}