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

import java.util.ArrayList;
import java.util.List;

import de.interseroh.report.domain.visitors.ParameterVisitor;
import de.interseroh.report.services.BirtControlType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class SelectionParameter<T>
		extends AbstractScalarParameter<SelectionParameter, T> {

	private List<SelectionOption> options;

	public SelectionParameter(Class<T> valueType) {
		super(valueType);
	}

	public static <T> SelectionParameter<T> newInstance(Class<T> valueType) {
		return new SelectionParameter(valueType);
	}

	@Override
	public String getParameterType() {
		if (isMultiValue()) {
			return "MULTISELECT";
		} else if (getControlType() == BirtControlType.RADIO_BUTTON) {
			return "RADIOSELECT";
		} else {
			return "SINGLESELECT";
		}
	}

	public int getRowCount() {
		if (isMultiValue()) {
			return (options.size() < 5) ? options.size() : 5;
		} else {
			return 1;
		}
	}

	public List<SelectionOption> getOptions() {
		if (options == null) {
			options = new ArrayList<>(10);
		}
		return options;
	}

	public void setOptions(List<SelectionOption> options) {
		this.options = options;
	}

	public SelectionParameter withOptions(final List<SelectionOption> options) {
		setOptions(options);
		return this;
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return super.toString() + "\n\t {" + "options=" + options + "} ";
	}
}
