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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.junit.Test;

import de.interseroh.report.exception.UnknownDataTypeException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtDataTypeTest {

	@Test
	public void testGetType() throws Exception {
		assertThat(BirtDataType.valueOf(IParameterDefn.TYPE_FLOAT).getType(),
				is(IParameterDefn.TYPE_FLOAT));
	}

	@Test
	public void testHtmlType() throws Exception {
		assertThat(BirtDataType.TYPE_DATE.getHtmlFieldType(), is("text"));
	}

	@Test(expected = UnknownDataTypeException.class)
	public void testUnknownDataTypeException() throws Exception {
		BirtDataType.valueOf(-1);
	}
}