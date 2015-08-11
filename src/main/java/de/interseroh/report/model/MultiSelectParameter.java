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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class MultiSelectParameter<T> extends
		AbstractSelectionParameter<MultiSelectParameter<T>, List<T>> {

	private static final int MAX_VISIBLE_ROWS = 5;

	/**
	 *
	 * @return max visible rows for multiselect controls.
	 */
	public int getRowCount() {
		return (getOptions().size() <= MAX_VISIBLE_ROWS) ? getOptions().size()
				: MAX_VISIBLE_ROWS;
	}

	@Override
	public String getParameterType() {
		return "MULTISELECT";
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<String> asRequestParameter() {
		if (!isUnset()) {
			List<String> params = new ArrayList<>(4);
			for (T value : getValue()) {
				params.add("params[" + getName() + "].value="
						+ urlEncode(asString(value)));
			}
			return params;
		} else {
			return super.asRequestParameter();
		}
	}

	@Override
	public Map<String, Object> asReportParameter() {
		if (!isUnset()) {
			final List values = new ArrayList();
			for (T value : getValue()) {
				values.add(asObject(asString(value)));
			}
			return new HashMap<String, Object>() {
				{
					put(getName(), values.toArray());
				}
			};
		} else {
			return super.asReportParameter();
		}
	}

	@Override
	public List<T> getValue() {
		if (super.getValue() == null) {
			setValue(new ArrayList<T>());
		}
		return super.getValue();
	}

	/**
	 * Just a workaround until we have a generic converter solution
	 * 
	 * @param value
	 * @return
	 */
	private String asString(T value) {
		return (value != null) ? value.toString() : "";
	}

}
