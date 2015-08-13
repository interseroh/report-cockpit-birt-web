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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.interseroh.report.domain.visitors.ParameterToMapVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterToMapVisitorTest {

	@Test
	public void testBuild() throws Exception {
		List<Parameter> params = buildTestData();

		Map<String, Parameter> map = new ParameterToMapVisitor(params).build();

		assertThat(map, hasKey("Group1Scalar1String"));
		assertThat(map, hasKey("Group1Scalar2Boolean"));
		assertThat(map, hasKey("Group2Scalar1String"));
		assertThat(map, hasKey("Group2Scalar2Boolean"));
		assertThat(map, hasKey("Group3Scalar1Radio"));
		assertThat(map, hasKey("Group3Scalar2Select"));
		assertThat(map, hasKey("Group3Scalar3Multi"));

		assertThat(map, not(hasKey("group1")));
		assertThat(map, not(hasKey("group2")));
		assertThat(map, hasKey("group3"));

	}

	private List<Parameter> buildTestData() {
		List<Parameter> params = new ArrayList<>();

		params.add(new ParameterGroup() //
				.withName("group1").withSynthetic(false)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("Group1Scalar1String"))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("Group1Scalar2Boolean")));
		params.add(new ParameterGroup() //
				.withName("group2").withSynthetic(true)
				.addScalarParameter(GenericParameter.newInstance(String.class) //
						.withName("Group2Scalar1String"))
				.addScalarParameter(GenericParameter.newInstance(Boolean.class) //
						.withName("Group2Scalar2Boolean")));
		params.add(new ParameterGroup() //
				.withName("group3").withCascading(true)
				.addScalarParameter(
						SelectionParameter.newInstance(Boolean.class)
								.withName("Group3Scalar1Radio"))
				.addScalarParameter(SelectionParameter.newInstance(String.class)
						.withName("Group3Scalar2Select"))
				.addScalarParameter(
						SelectionParameter.newInstance(Integer[].class)
								.withName("Group3Scalar3Multi")));
		return params;
	}
}