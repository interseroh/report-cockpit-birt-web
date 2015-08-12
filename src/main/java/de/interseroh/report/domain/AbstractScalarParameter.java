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
package de.interseroh.report.domain;

import de.interseroh.report.services.BirtControlType;
import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractScalarParameter<SUB extends AbstractScalarParameter, T>
		extends AbstractParameter<SUB>implements ScalarParameter<T> {

	private final Class<T> valueType;
	private T value;
	private T defaultValue;

	private BirtDataType dataType = BirtDataType.TYPE_STRING;
	private BirtControlType controlType;

	private boolean required;
	private boolean concealed;

	public AbstractScalarParameter(Class<T> valueType) {
		this.valueType = valueType;
	}

	public Class<T> getValueType() {
		return valueType;
	}

	@Override
	public boolean isMultiValue() {
		return valueType.isArray();
	}

	public BirtDataType getDataType() {
		return dataType;
	}

	public void setDataType(BirtDataType dataType) {
		this.dataType = dataType;
	}

	public BirtControlType getControlType() {
		return controlType;
	}

	public void setControlType(BirtControlType controlType) {
		this.controlType = controlType;
	}

	@Override
	public String getParameterType() {
		return "SCALAR_PARAMETER";
	}

	@Override
	public String getHtmlFieldType() {
		if (dataType == BirtDataType.TYPE_STRING && concealed) {
			return "password";
		} else if (dataType != null) {
			return dataType.getHtmlFieldType();
		} else {
			return BirtDataType.TYPE_STRING.getHtmlFieldType();
		}
	}

	@Override
	public boolean isValid() {
		return !required || !isNullOrEmpty(value) || defaultValue != null;
	}

	private boolean isNullOrEmpty(T value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			return ((String) value).trim().isEmpty();
		} else if (value.getClass().isArray()) {
			boolean empty = true;
			for (T v : (T[]) value) {
				empty &= isNullOrEmpty(v);
			}
            return empty;
		} else {
			return false;
		}
	}

	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public SUB withDefaultValue(final T defaultValue) {
		this.defaultValue = defaultValue;
		return (SUB) this;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	public SUB withValue(final T value) {
		this.value = value;
		return (SUB) this;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public SUB withRequired(final boolean required) {
		this.required = required;
		return (SUB) this;
	}

	@Override
	public boolean isConcealed() {
		return concealed;
	}

	public void setConcealed(boolean concealed) {
		this.concealed = concealed;
	}

	public SUB withConcealed(final boolean concealed) {
		this.concealed = concealed;
		return (SUB) this;
	}

	@Override
	public String toString() {
		return "AbstractScalarParameter{" + " value=" + value + ", valueType="
				+ valueType + ", defaultValue=" + defaultValue + ", dataType="
				+ dataType + ", controlType=" + controlType + ", required="
				+ required + ", concealed=" + concealed + "} "
				+ super.toString();
	}
}
