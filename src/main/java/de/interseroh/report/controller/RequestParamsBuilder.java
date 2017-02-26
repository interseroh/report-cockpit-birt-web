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
package de.interseroh.report.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.visitors.AbstractParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class RequestParamsBuilder {

	public String asRequestParams(ParameterForm parameterForm) {
		List<String> params = buildParamList(parameterForm);
		if (!parameterForm.isOverwrite()) {
			params.add("__overwrite=false");
		}
		if (parameterForm.isPageSelected()) {
			params.add("__pageNumber=" + parameterForm.getPageNumber());
		}
		return conjoin(params);
	}

	private List<String> buildParamList(ParameterForm parameterForm) {
		final List<String> params = new ArrayList<>();

		parameterForm.accept(new AbstractParameterVisitor() {
			@SuppressWarnings("unchecked")
			@Override
			public <V, T> void visit(ScalarParameter<V, T> parameter) {
				String paramName = parameter.getName();

				// FIXME - should be done by Generic Text Converter
				if (parameter.getText() != null) {
					if (parameter.isMultiValue()
							&& (parameter.getText() != null && parameter
									.getText().getClass().isArray())) {
						for (T paramText : (T[]) parameter.getText()) {
							addKeyValueParam(paramName, (String) paramText);
						}
					} else {
						addKeyValueParam(paramName,
								(String) parameter.getText());
					}
				}
			}

			private void addKeyValueParam(String paramName, String paramValue) {
				if (paramValue != null && !paramValue.trim().isEmpty()) {
					params.add(paramName + "=" + paramValue);
				}
			}
		});
		return params;
	}

	private String conjoin(List<String> params) {
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(param);
		}
		return (builder.length() > 0) ? "?" + builder.toString() : "";
	}

}
