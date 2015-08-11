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

import java.util.List;
import java.util.Map;

import de.interseroh.report.common.Visitable;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface Parameter extends Visitable<ParameterVisitor> {
	/**
	 * A string that unique identify the parameter type within the parameter
	 * hierarchy
	 * 
	 * @return String identifying the concrete parameter type
	 */
	String getParameterType();

	/**
	 * <b>Unique</b> name of the parameter in the scope of a report.
	 * 
	 * @return unique name.
	 */
	String getName();

	/**
	 * <b>Unique</b> name of the parameter in the scope of a report
	 * 
	 * @param unique
	 *            name.
	 */
	void setName(String name);

	/**
	 *
	 * @return String or null if no display label is configured
	 */
	String getDisplayLabel();

	/**
	 *
	 * @param displayLabel
	 */
	void setDisplayLabel(String displayLabel);

	/**
	 * A helpttext to be presented as a tooltip.
	 * 
	 * @return String or null if no helptext is defined.
	 */
	String getTooltip();

	/**
	 *
	 * @param tooltip
	 *            or null if no helptext is defined.
	 */
	void setTooltip(String tooltip);

	/**
	 *
	 * @return true if this parameter and all sub parameters are unset.
	 */
	boolean isUnset();

	/**
	 * Checks whether the constrains are fulfilled. if a value is required and
	 * no default value is defined a valid value must be assigned.
	 * 
	 * @return true if the parameter is valid
	 */
	boolean isValid();

	/**
	 * @return List of "key=value" strings as request parameter
	 */
	List<String> asRequestParameter();

	/**
	 * @return Map of parameter name and values
	 */
	Map<String, Object> asReportParameter();

}
