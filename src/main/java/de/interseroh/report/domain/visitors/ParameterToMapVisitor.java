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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterToMapVisitor implements ParameterVisitor {

	private final Collection<? extends Parameter> parameters;
	private final Map<String, Parameter> parameterMap = new HashMap<>();

	public ParameterToMapVisitor(Collection<? extends Parameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Parameter> build() {
		parameterMap.clear();
		acceptParameters(parameters);
		return parameterMap;
	}

	private void acceptParameters(Collection<? extends Parameter> parameters) {
		for (Parameter parameter : parameters) {
			parameter.accept(this);
		}
	}

	private void addScalarParameter(ScalarParameter scalarParameter) {
		parameterMap.put(scalarParameter.getName(), scalarParameter);
	}

	@Override
	public void visit(ScalarParameter parameter) {
		addScalarParameter(parameter);
	}

	@Override
	public void visit(ParameterGroup group) {
		if (group.isCascading()) {
			parameterMap.put(group.getName(), group);
		}
		acceptParameters(group.getParameters());
	}
}
