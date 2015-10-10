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
package de.interseroh.report.domain;

/**
 *
 * A ScalarParameter represents a parameter that contains one or multiple
 * values. The type of the value dependence on the underlying report parameter.
 *
 * The scalar parameters are defined to handle the request response cycle of a
 * web application.
 *
 * The value contains the current strongly typed value from the report or
 * previously submitted values.
 *
 * After a externally trigert formatting all typed values are print out
 * formatted to the text value. The views could fetch the text values of the
 * scalar parameters
 *
 * @author Ingo Düppe (Crowdcode)
 */
public interface ScalarParameter<V, T> extends Parameter {

	String getHtmlFieldType();

	Class<V> getValueType();

	Class<T> getTextType();

    String getDisplayFormat();

    /**
     * The current set parameter value
     * @return value or null
     */
	V getValue();

    /**
     *
     * @param value
     */
    void setValue(V value);

    /**
     * Default value of the parameter
     * @return the default value or null
     */
	V getDefaultValue();

    /**
     * Defines the value of the defaultValue or null if defined none.
     * @param defaultValue
     */
    void setDefaultValue(V defaultValue);

	/**
	 * The value of the parameter as formatted text
	 * 
	 * @return value as Text or empty string
	 */
	T getText();

	/**
     * Setting the parameter
	 * @param text
	 *            new value as text
	 */
	void setText(T text);

    /**
     * Formatted default value
     * @return default value or empty string
     */
    T getDefaultText();

    /**
     * Defines the formatted default value of the parameter or null
     * @param defaultText, null or empty string.
     */
    void setDefaultText(T defaultText);

    void setDisplayFormat(String displayFormat);

    boolean isMultiValue();

	boolean isRequired();

	boolean isConcealed();

	V getValueOrDefault();

}
