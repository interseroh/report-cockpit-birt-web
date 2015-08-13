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
package de.interseroh.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.services.BirtReportService;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Component
public class CascadingGroupLoader {

	@Autowired
	private BirtReportService reportService;

	public void load(final ParameterForm parameterForm)
			throws BirtReportException {
		for (ParameterGroup group : parameterForm.getGroups()) {
			String reportName = parameterForm.getReportName();
			if (group.isCascading()) {
				reportService.loadOptionsForCascadingGroup(reportName, group);
			}
		}

		parameterForm.resetParams();
	}

}
