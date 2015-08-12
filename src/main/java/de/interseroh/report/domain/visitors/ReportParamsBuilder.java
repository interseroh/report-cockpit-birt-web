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
package de.interseroh.report.domain.visitors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ReportParamsBuilder extends AbstractParameterVisitor implements ParameterVisitor {

	private final Map<String, Object> params = new HashMap<>();

	/**
	 * Builds a map for all scalar parameter based on parameter name and value.
	 * Multi-value and adhoc scalar parameter will be transformed to an array of
	 * values. For instance, <code>name={value1, value2}</code>
	 *
	 * @return
	 */
	public Map<String, Object> build(Collection<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			parameter.accept(this);
		}
		return params;
	}

	@Override
	public <T> void visit(ScalarParameter<T> parameter) {
		String paramName = parameter.getName();
		T paramValue = parameter.getValue();
		params.put(paramName, paramValue);
	}

}
