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

import org.apache.commons.lang.StringUtils;

import de.interseroh.report.services.BirtControlType;
import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class Parameter {

	private String name;
	private String value;
	private String defaultValue;
	private String displayLabel;

	private BirtDataType dataType;
	private BirtControlType controlType;

	private boolean required;
	private boolean concealed;

	public boolean isUnset() {
		return !(isValid());
	}

	public boolean isValid() {
		return !required //
				|| StringUtils.isNotBlank(value)
				|| StringUtils.isNotBlank(defaultValue);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtmlFieldType() {
		if (dataType == BirtDataType.TYPE_STRING && concealed) {
			return "password";
		} else {
			return dataType.getHtmlFieldType();
		}
	}

	public String requestURI() {
		return name + "=" + valueOrDefault();
	}

	private String valueOrDefault() {
		return (StringUtils.isNotBlank(value)) ? value : defaultValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public BirtDataType getDataType() {
		return dataType;
	}

	public void setDataType(BirtDataType dataType) {
		this.dataType = dataType;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public Parameter withName(final String name) {
		this.name = name;
		return this;
	}

	public Parameter withValue(final String value) {
		this.value = value;
		return this;
	}

	public Parameter withDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public Parameter withDataType(final BirtDataType dataType) {
		this.dataType = dataType;
		return this;
	}

	public Parameter withDisplayLabel(final String displayLabel) {
		this.displayLabel = displayLabel;
		return this;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isConcealed() {
		return concealed;
	}

	public void setConcealed(boolean concealed) {
		this.concealed = concealed;
	}

	public Parameter withRequired(final boolean required) {
		this.required = required;
		return this;
	}

	public Parameter withConcealed(final boolean concealed) {
		this.concealed = concealed;
		return this;
	}

	@Override
	public String toString() {
		return "Parameter{" + "name='" + name + '\'' + ", value='" + value
				+ '\'' + ", defaultValue='" + defaultValue + '\''
				+ ", dataType='" + dataType + '\'' + ", displayLabel='"
				+ displayLabel + '\'' + ", required=" + required
				+ ", concealed=" + concealed + '}';
	}

}
