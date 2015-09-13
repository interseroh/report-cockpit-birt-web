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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import de.interseroh.report.domain.visitors.ParameterToMapVisitor;
import de.interseroh.report.domain.visitors.ParameterVisitor;
import de.interseroh.report.domain.visitors.ReportParamsBuilder;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterForm {

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterForm.class);

	private String reportName;
	private Collection<ParameterGroup> groups;
	private Map<String, Parameter> parameterMap;
	private MultiValueMap<String, String> requestParameters;
    private boolean overwrite = true;
    private Long pageNumber;

	public void accept(ParameterVisitor visitor) {
		for (ParameterGroup group : groups) {
			group.accept(visitor);
		}
	}

	/**
	 * Checks whether or not all parameters has either a value or a default
	 * value.
	 *
	 * @return false if not all required parameters has a valid value
	 */
	public boolean isValid() {
		boolean valid = true;

		for (ParameterGroup parameter : groups) {
			valid &= parameter.isValid();
		}

		return valid;
	}

	/**
	 * Builds a map for all scalar parameter based on parameter name and value.
	 * Not multi-value and adhoc scalar parameter will be transformed to an
	 * array of values. For instance, <code>name={value1, value2}</code>
	 *
	 * @return
	 */
	public Map<String, Object> asReportParameters() {
		return new ReportParamsBuilder().build(groups);

	}

	/**
	 * Returns a map of all cascading group and scalar parameters of the
	 * ParameterForm. The map do not contain synthetic and simple groups.
	 *
	 * @return Map<String, Parameter>
	 */
	public Map<String, Parameter> getParams() {
		if (parameterMap == null) {
			parameterMap = new ParameterToMapVisitor(groups).build();
		}
		return Collections.synchronizedMap(parameterMap);
	}

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isPageSelected() {
        return pageNumber != null;
    }

    public void setParams(Map<String, ScalarParameter> params) {
		logger.info("Got:{}", params);
		// nothing to do read only
	}

	public MultiValueMap<String, String> getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(
			MultiValueMap<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	public ParameterForm withRequestParameters(
			MultiValueMap<String, String> requestParameters) {
		this.requestParameters = requestParameters;
		return this;
	}

	/**
	 * This will clear the cached map between parameter names and objects. The
	 * map will be rebuild lazy on the next request
	 *
	 * @see ParameterForm#getParams()
	 * @return same instance of ParameterForm
	 */
	public ParameterForm resetParams() {
		parameterMap = null;
		return this;
	}

	public ParameterForm withReportName(final String reportName) {
		this.reportName = reportName;
		return this;
	}

	public ParameterForm withParameterGroups(List<ParameterGroup> groups) {
		this.groups = groups;
		return this;
	}

	public ParameterForm addParameterGroup(ParameterGroup group) {
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

	public Collection<ParameterGroup> getGroups() {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups;
	}

	public void setGroups(Collection<ParameterGroup> groups) {
		this.groups = groups;
	}

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
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
