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

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class ParameterFormConverter {

	@Autowired
	private ConversionService conversionService;

	public void convertToRequiredTypes(ParameterForm parameterForm,
			final Errors errors) {

		// Convert parameter values to required type
		parameterForm.accept(new AbstractParameterVisitor() {
			@Override
			public <T> void visit(ScalarParameter<T> parameter) {
				String name = parameter.getName();
				String propertyPath = ParameterUtils.nameToPath(name);
				Class<T> requiredType = parameter.getValueType();
				T value = parameter.getValue();
				if (notAssignable(requiredType, value)) {
					if (conversionService.canConvert(value.getClass(),
							requiredType)) {
						try {
							T converted = conversionService.convert(value,
									requiredType);
							parameter.setValue(converted);
						} catch (ConversionException ce) {
							// TODO idueppe - here we need a more user friendly
							// error code with parameters
							errors.rejectValue(propertyPath,
									"conversion.error.unknown_format",
									ce.getMessage());
						}
					} else {
						errors.rejectValue(propertyPath,
								"conversion.error.unknown_type",
								"No converter for " + requiredType.getName());
					}
				}
			}

			private <T> boolean notAssignable(Class<T> valueType, T value) {
				return value != null
						&& !valueType.isAssignableFrom(value.getClass());
			}
		});
	}

}
