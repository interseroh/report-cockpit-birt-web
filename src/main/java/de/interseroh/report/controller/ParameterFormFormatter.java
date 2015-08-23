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

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ScalarParameter;
import de.interseroh.report.domain.visitors.AbstractParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class ParameterFormFormatter {

	@Autowired
	private ConversionService conversionService;

	public void format(ParameterForm parameterForm) {

		// Convert parameter values to required type
		parameterForm.accept(new AbstractParameterVisitor() {
			@Override
			public <V, T> void visit(ScalarParameter<V, T> parameter) {
				Class<V> valueType = parameter.getValueType();
				Class<T> textType = parameter.getTextType();

				V value = parameter.getValue();
				if (value != null) {
					if (conversionService.canConvert(valueType, textType)) {
						try {
							T formatted = conversionService.convert(value,
									textType);
							parameter.setText(formatted);
						} catch (ConversionException ce) {
							parameter.setText((T) value.toString());
						}
					}
				}
			}
		});
	}

}
