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

import java.util.Collections;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractParameter<SUB extends AbstractParameter<SUB>>
		implements Parameter {

	private String name;
	private String displayLabel;
	private String tooltip;

	@Override
	public String getParameterType() {
		return "TEXTBOX";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public SUB withName(final String name) {
		this.name = name;
		return (SUB) this;
	}

	@Override
	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StringParameter parameter = (StringParameter) o;

		return !(getName() != null ? !getName().equals(parameter.getName())
				: parameter.getName() != null);

	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}

	public SUB withDisplayLabel(final String displayLabel) {
		this.displayLabel = displayLabel;
		return (SUB) this;
	}

	@Override
	public String getTooltip() {
		return tooltip;
	}

	@Override
	public void setTooltip(String toolTip) {
		this.tooltip = toolTip;
	}

	public SUB withTooltip(final String toolTip) {
		this.tooltip = toolTip;
		return (SUB) this;
	}

	@Override
	public List<String> asRequestParameter() {
		return Collections.emptyList();
	}
}
