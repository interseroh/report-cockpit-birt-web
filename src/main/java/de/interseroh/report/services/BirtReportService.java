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
package de.interseroh.report.services;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.exception.BirtReportException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface BirtReportService {

	String REPORT_SOURCE_URL_KEY = "report.source.url";
	String REPORT_BASE_IMAGE_URL_KEY = "report.base.image.url";
	String REPORT_IMAGE_DIRECTORY_KEY = "report.image.directory";
	String REPORT_BASE_IMAGE_CONTEXT_PATH_KEY = "report.image.contextpath";
	String REPORT_FILE_SUFFIX = ".rptdesign";
    String DOCUMENT_FILE_SUFFIX = ".rptdocument";

	/**
	 *
	 * @param reportName
	 *            name of the report
	 * @return collection of synthetic and real parameter groups if the report
	 * @throws BirtReportException
	 */
	List<ParameterGroup> getParameterGroups(String reportName)
			throws BirtReportException;

	/**
	 * Verifies which scalar parameters of the cascading group has values and
	 * tries to load choices for the next not yet selected scalar parameter.
	 *
	 * @param reportName
	 *            name of the report
	 * @param group
	 *            where the options are injected
	 * @throws BirtReportException
	 */
	void loadOptionsForCascadingGroup(String reportName, ParameterGroup group)
			throws BirtReportException;

	void renderHtmlReport(String reportName, Map<String, Object> parameters,
			OutputStream out) throws BirtReportException;

    void renderHtmlReport(String reportName, Map<String, Object> parameters,
                          OutputStream out, long pageNumber) throws BirtReportException;

	void renderPDFReport(String reportName, Map<String, Object> parameters,
			OutputStream out) throws BirtReportException;

    void renderExcelReport(String reportName, Map<String, Object> parameters,
			OutputStream out) throws BirtReportException;


}
