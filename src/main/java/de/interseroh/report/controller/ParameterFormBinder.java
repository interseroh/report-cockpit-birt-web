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

import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterUtils;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.visitors.AbstractParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class ParameterFormBinder {

	public void bind(final ParameterForm parameterForm,
			final MultiValueMap<String, String> requestParameters,
			BindingResult bindingResult) {

		final MutablePropertyValues mpvs = createPropertyValues(parameterForm,
				requestParameters);

		DataBinder dataBinder = new DataBinder(parameterForm,
				bindingResult.getObjectName());
		dataBinder.bind(mpvs);
		bindingResult.addAllErrors(dataBinder.getBindingResult());
	}

	private MutablePropertyValues createPropertyValues(
			ParameterForm parameterForm,
			final MultiValueMap<String, String> requestParameters) {
		final MutablePropertyValues mpvs = new MutablePropertyValues();

		parameterForm.accept(new AbstractParameterVisitor() {
			@Override
			public <T> void visit(ScalarParameter<T> parameter) {
				String name = parameter.getName();
				String propertyPath = ParameterUtils.nameToPath(name);
				Class<T> valueType = parameter.getValueType();

				if (requestParameters.containsKey(name)) {
					addValues(name, propertyPath, valueType);
				} else if (requestParameters.containsKey('_' + name)) {
					addValues('_' + name, propertyPath, valueType);
				}
			}

			private <T> void addValues(String name, String propertyPath,
					Class<T> valueType) {
				List<String> values = requestParameters.get(name);
				for (String requestValue : values) {
					mpvs.add(propertyPath, requestValue);
				}
			}
		});
		return mpvs;
	}

}
