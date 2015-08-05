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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterForm {

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterForm.class);

	private String reportName;
	private Collection<GroupParameter> groups;
    private Map<String, Parameter> parameterMap;

	/**
	 * Checks whether or not all parameters has either a value or a default
	 * value.
	 * 
	 * @return false if not all required parameter has a value
	 */
	public boolean isValid() {
		boolean foundInvalid = false;

		for (GroupParameter parameter : groups) {
			foundInvalid = foundInvalid || parameter.isUnset();
		}

		return !foundInvalid;
	}

	public String asRequestParams() {
       List<String> params = new ArrayList<>();

        for (GroupParameter group : groups) {
            params.addAll(group.asRequestParameter());
        }

		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(param);
		}

		return (builder.length() > 0) ? "?"+builder.toString() : "";
	}

    /**
     * Builds a map for all scalar parameter based on parameter name and value.
     * Not multi-value and adhoc scalar parameter will be transformed to an array of values.
     * For instance, <code>name={value1, value2}</code>
     * @return
     */
    public Map<String, Object> asReportParameters() {
        Map<String, Object> parameters = new HashMap<>();
        for (GroupParameter group : groups) {
            parameters.putAll(group.asReportParameter());
        }
        return parameters;
    }


    /**
     * Returns a map of all cascading group and scalar parameters of the ParameterForm.
     * The map do not contain synthetic and simple groups.
     *
     * @return Map<String, Parameter>
     */
	public Map<String, Parameter> getParams() {
        if (parameterMap == null) {
            parameterMap = new ParameterToMapVisitor(groups).build();
        }
		return Collections.synchronizedMap(parameterMap);
	}

    /**
     * This will clear the cached map between parameter names and objects.
     * The map will be rebuild lazy on the next request
     * @see ParameterForm#getParams()
     * @return same instance of ParameterForm
     */
    public ParameterForm resetParams() {
        parameterMap = null;
        return this;
    }

	public void setParams(Map<String, ScalarParameter> params) {
		logger.info("Got:{}", params);
		// nothing to do read only
	}

	public ParameterForm withReportName(final String reportName) {
		this.reportName = reportName;
		return this;
	}

	public ParameterForm withGroupParameters(
			final Collection<GroupParameter> groups) {
		this.groups = groups;
		return this;
	}

    public ParameterForm addGroupParameter(GroupParameter group) {
        getGroups().add(group);
        return this;
    }

	public boolean hasNoParameters() {
		return groups.size() == 0;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Collection<GroupParameter> getGroups() {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups;
	}

	public void setGroups(Collection<GroupParameter> groups) {
		this.groups = groups;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ParameterForm that = (ParameterForm) o;

		return !(reportName != null ? !reportName.equals(that.reportName)
				: that.reportName != null);

	}

	@Override
	public int hashCode() {
		return reportName != null ? reportName.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "ParameterForm{" + "groups=" + groups + '}';
	}

}
