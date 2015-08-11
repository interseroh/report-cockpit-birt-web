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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class DefaultGroupParameter extends
		AbstractParameter<DefaultGroupParameter> implements GroupParameter {

	private List<ScalarParameter> parameters;
	private boolean cascading;
	private boolean synthetic;

	@Override
	public Map<String, Object> asReportParameter() {
		Map<String, Object> params = new HashMap<>();
		for (ScalarParameter parameter : parameters) {
			params.putAll(parameter.asReportParameter());
		}
		return params;
	}

	@Override
	public List<String> asRequestParameter() {
		List<String> params = new ArrayList<>();
		for (ScalarParameter parameter : parameters) {
			params.addAll(parameter.asRequestParameter());
		}
		return params;
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getParameterType() {
		return "GROUP";
	}

	@Override
	public boolean isUnset() {
		boolean unset = true;
		for (ScalarParameter param : parameters) {
			unset &= param.isUnset();
		}
		return unset;
	}

	@Override
	public boolean isValid() {
		boolean valid = true;
		for (ScalarParameter param : parameters) {
			valid &= param.isValid();
		}
		return valid;
	}

	@Override
	public List<ScalarParameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<>();
		}
		return parameters;
	}

	@Override
	public void setParameters(List<ScalarParameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public DefaultGroupParameter withParameters(List<ScalarParameter> parameters) {
		setParameters(parameters);
		return this;
	}

	@Override
	public DefaultGroupParameter addScalarParameter(ScalarParameter parameter) {
		getParameters().add(parameter);
		return this;
	}

	@Override
	public boolean isCascading() {
		return cascading;
	}

	@Override
	public void setCascading(boolean cascading) {
		this.cascading = cascading;
	}

	@Override
	public GroupParameter withCascading(boolean cascading) {
		setCascading(cascading);
		return this;
	}

	@Override
	public boolean isInvisible() {
		return synthetic || StringUtils.isBlank(getDisplayLabel());
	}

	@Override
	public boolean isSynthetic() {
		return synthetic;
	}

	@Override
	public void setSynthetic(boolean synthetic) {
		this.synthetic = synthetic;
	}

	@Override
	public DefaultGroupParameter withSynthetic(final boolean synthetic) {
		this.synthetic = synthetic;
		return this;
	}

}