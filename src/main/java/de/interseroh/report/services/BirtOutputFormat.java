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

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtOutputFormat {

	HTML5("html", "text/html; charset=UTF-8"), //
	PDF("pdf", "application/pdf"), //
	EXCEL("xls", "application/vnd.ms-excel"), //
	EXCEL2010("xlsx", "application/vnd.ms-excel");

	private final String formatName;
	private final String contentType;

	private BirtOutputFormat(String formatName, String contentType) {
		this.formatName = formatName;
		this.contentType = contentType;
	}

	public static BirtOutputFormat from(String formatName) {
		for (BirtOutputFormat format : BirtOutputFormat.values()) {
			if (format.formatName.equals(formatName)) {
				return format;
			}
		}
		return HTML5; // default;
	}

	public String getFormatName() {
		return formatName;
	}

	public String getContentType() {
		return contentType;
	}
}
