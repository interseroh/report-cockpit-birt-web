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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractSelectionParameter<SUB extends AbstractSelectionParameter<SUB, T>, T>
		extends AbstractScalarParameter<SUB, T> implements
		SelectionParameter<SUB, T> {

	private List<SelectionOption> options;

	@Override
	public List<SelectionOption> getOptions() {
		if (options == null) {
			options = new ArrayList<>(10);
		}
		return options;
	}

	@Override
	public void setOptions(List<SelectionOption> options) {
		this.options = options;
	}

	@Override
	public SUB withOptions(final List<SelectionOption> options) {
		this.options = options;
		return (SUB) this;
	}

	@Override
	public String toString() {
		return super.toString() + "\n\t {" + "options=" + options + "} ";
	}

}
