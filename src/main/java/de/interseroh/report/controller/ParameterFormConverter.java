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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterUtils;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.visitors.AbstractParameterVisitor;
import de.interseroh.report.formatters.DisplayFormatHolder;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class ParameterFormConverter {

	@Autowired
	private ConversionService conversionService;

	public void convert(ParameterForm parameterForm, final Errors errors) {

		// Convert parameter values to required type
		parameterForm.accept(new AbstractParameterVisitor() {
			@Override
			public <V, T> void visit(ScalarParameter<V, T> parameter) {
				String name = parameter.getName();
				String propertyPath = ParameterUtils.nameToTextPath(name);
				Class<V> requiredType = parameter.getValueType();
				T textValue = parameter.getText();
				if (isConversionNeeded(requiredType, textValue)) {
					try {
                        DisplayFormatHolder.setDisplayFormat(parameter.getDisplayFormat());

                        V converted = conversionService.convert(textValue,
								requiredType);
						parameter.setValue(converted);
					} catch (ConversionException ce) {
						// TODO idueppe - here we need a more user friendly
						// error code with parameters
						errors.rejectValue(propertyPath,
								"conversion.error.unknown_format",
								ce.getMessage());
					}
				}
			}

			private <V, T> boolean isConversionNeeded(Class<V> requiredType,
					T textValue) {
				return textValue != null && conversionService
						.canConvert(textValue.getClass(), requiredType);
			}

		});
	}

}
