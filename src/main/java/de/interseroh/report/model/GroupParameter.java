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

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface GroupParameter extends Parameter {

	/**
	 * A List of ScalarParameters that belongs to the group
	 * 
	 * @return List-Object and never null.
	 */
	List<ScalarParameter> getParameters();

	/**
	 * @param parameters
	 *            List or null to clear parameter list
	 */
	void setParameters(List<ScalarParameter> parameters);

	/**
	 *
	 * @param parameters
	 *            List or null to clear parameter list
	 * @return the same ParameterGroup instance
	 */
	GroupParameter withParameters(List<ScalarParameter> parameters);

	/**
	 * Appends an ScalarParameter to the end of the list
	 * 
	 * @param parameter
	 *            ScalarParameters
	 * @return this object
	 */
	GroupParameter addScalarParameter(ScalarParameter parameter);

	/**
	 * @return true if it is an cascading group.
	 */
	boolean isCascading();

	void setCascading(boolean cascading);

	/**
	 * @param cascading
	 *            true if it is an cascading group
	 * @return the same GroupParameter instance
	 */
	GroupParameter withCascading(boolean cascading);

	/**
	 * @return true if the group is not visible
	 */
	boolean isInvisible();

	/**
	 * Indicates that this group is not configured within the reports. Synthetic
	 * parameter groups wrappes parameters without any group within a report. So
	 * that every scalar parameter is in a group and on the first level only
	 * parameter group exists.
	 * 
	 * @return
	 */
	boolean isSynthetic();

	void setSynthetic(boolean synthetic);

	/**
	 *
	 * @param synthetic
	 *            true if it is synthetic
	 * @return the same GroupParameter instance
	 */
	DefaultGroupParameter withSynthetic(boolean synthetic);
}
