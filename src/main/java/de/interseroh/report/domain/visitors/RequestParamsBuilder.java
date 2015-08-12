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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.convert.ConversionService;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class RequestParamsBuilder extends AbstractParameterVisitor implements ParameterVisitor {

	private final List<String> params = new ArrayList<>();

	private final ConversionService conversionService;

	public RequestParamsBuilder(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/**
	 * @param parameters
	 * @return encoded request parameters
	 */
	public String asRequestParams(Collection<Parameter> parameters) {
		parse(parameters);
		return conjoin();
	}

	private void parse(Collection<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			parameter.accept(this);
		}
	}

    @Override
    public <T> void visit(ScalarParameter<T> parameter) {
        String paramName = parameter.getName();

        if (parameter.isMultiValue()) {
            for (T paramValue : (T[]) parameter.getValue()) {
                addKeyValueParam(paramName, paramValue);
            }
        } else {
            addKeyValueParam(paramName, parameter.getValue());
        }
    }

    private String conjoin() {
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(param);
		}
		return (builder.length() > 0) ? "?" + builder.toString() : "";
	}


	private <T> void addKeyValueParam(String paramName, T paramValue) {
		if (paramValue != null) {
			String stringValue = conversionService.convert(paramValue,
					String.class);
			params.add(paramName + "=" + urlEncode(stringValue));
		}
	}

	protected String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(
					"Report Parameter Encoding did not work.", e);
		}
	}
}
