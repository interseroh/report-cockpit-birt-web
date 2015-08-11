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

import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface ScalarParameter<T> extends Parameter {

	/**
	 * The current value of the parameter
	 * 
	 * @return T current value or null
	 */
	T getValue();

	/**
	 * Setting the value of the parameter
	 * 
	 * @param value
	 *            or null of the parameter.
	 */
	void setValue(T value);

	/**
	 * String representation of value
	 * 
	 * @return String or empty
	 */
	String getValueAsString();

	/**
	 * The default value
	 * 
	 * @return default value or null if no default value is defined
	 */
	T getDefaultValue();

	/**
	 *
	 * @return Html Input Type or null
	 */
	String getHtmlFieldType();

	/**
	 *
	 * @return BirtDataType
	 */
	BirtDataType getDataType();

	void setDataType(BirtDataType dataType);

	/**
	 * True if a value is needed
	 * 
	 * @return
	 */
	boolean isRequired();

	/**
	 *
	 * @return true if the user input should be hidden.
	 */
	boolean isConcealed();

	/**
	 * Not simple parameters must be wrapped as an array when calling a report.
	 * 
	 * @return true if it is a simple parameter type.
	 */
	boolean isSimpleValue();

	void setSimpleValue(boolean simple);

}
