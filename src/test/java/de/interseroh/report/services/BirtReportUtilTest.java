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
package de.interseroh.report.services;

import java.util.Collections;

import org.eclipse.birt.report.engine.api.ICascadingParameterGroup;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by idueppe on 27.02.17.
 */
public class BirtReportUtilTest {

	@Test
	public void testPrintParameter() throws Exception {
		BirtReportUtil.printParameter(Mockito.mock(IParameterDefnBase.class));
	}

	@Test
	public void testPrintScalarParameter() throws Exception {
		BirtReportUtil
				.printScalarParameter(Mockito.mock(IScalarParameterDefn.class));
	}

	@Test
	public void testPrintParameterGroup() throws Exception {
		BirtReportUtil.printParameterGroup(
				Mockito.mock(IGetParameterDefinitionTask.class),
				Mockito.mock(ICascadingParameterGroup.class));
	}

	@Test
	public void testParameterDefinitions() throws Exception {
		BirtReportUtil.printParameterDefinitions(Collections
						.singletonList(Mockito.mock(IParameterDefnBase.class)),
				Mockito.mock(IGetParameterDefinitionTask.class));
	}

	@Test
	public void testSelectionChoices() throws Exception {
		BirtReportUtil.printSelectionChoices(Collections
				.singleton(Mockito.mock(IParameterSelectionChoice.class)));
	}

	@Test
	public void testSectionList() throws Exception {
		BirtReportUtil
				.printSelectionList(Mockito.mock(IParameterDefnBase.class),
						Mockito.mock(IGetParameterDefinitionTask.class));

	}
}