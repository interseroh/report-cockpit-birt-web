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
import java.util.Collection;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterForm {

	private Collection<Parameter> parameters = new ArrayList<>();

	/**
	 * Checks whether or not all parameters has either a value or a default
	 * value.
	 * 
	 * @return false if not all required parameter has a value
	 */
	public boolean isValid() {
		boolean foundInvalid = false;

		for (Parameter parameter : parameters) {
			foundInvalid = foundInvalid || parameter.isUnset();
		}

		return !foundInvalid;
	}

	public String buildRequestParams() {
		StringBuilder builder = new StringBuilder();
		for (Parameter parameter : parameters) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(parameter.requestURI());
		}

		return ((builder.length() > 0) ? "?" : "") + builder.toString();
	}

	public Collection<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Collection<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "ParameterForm{" + "parameters=" + parameters + '}';
	}

	public boolean hasNoParameters() {
		return parameters.size() == 0;
	}
}
