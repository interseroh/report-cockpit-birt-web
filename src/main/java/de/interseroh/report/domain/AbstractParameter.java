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

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractParameter<SUB extends AbstractParameter>
		implements Parameter {

	private String name;
	private String displayLabel;
	private String tooltip;

	@Override
	public String getName() {
		return name;
	}

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

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public SUB withDisplayLabel(final String displayLabel) {
		this.displayLabel = displayLabel;
		return (SUB) this;
	}

	@Override
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public SUB withTooltip(final String tooltip) {
		this.tooltip = tooltip;
		return (SUB) this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AbstractParameter parameter = (AbstractParameter) o;
		return !(name != null ? !name.equals(parameter.name)
				: parameter.name != null);
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Parameter{" + "tooltip='" + tooltip + '\'' + ", displayLabel='"
				+ displayLabel + '\'' + ", name='" + name + "'}";
	}
}
