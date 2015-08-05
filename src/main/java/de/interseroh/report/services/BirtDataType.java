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
package de.interseroh.report.services;

import de.interseroh.report.exception.UnknownDataTypeException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtDataType {

	TYPE_ANY(0, "text"), //
	TYPE_STRING(1, "text"), //
	TYPE_FLOAT(2, "number"), //
	TYPE_DECIMAL(3, "number"), //
	TYPE_DATE_TIME(4, "datetime"), //
	TYPE_BOOLEAN(5, "checkbox"), //
	TYPE_INTEGER(6, "number"), //
	TYPE_DATE(7, "date"), //
	TYPE_TIME(8, "time"); //

	private int dataType;
	private String htmlFieldType;

	BirtDataType(int dataType, String htmlFieldType) {
		this.dataType = dataType;
		this.htmlFieldType = htmlFieldType;
	}

	public static BirtDataType valueOf(int dataType) {
		for (BirtDataType type : BirtDataType.values()) {
			if (type.dataType == dataType)
				return type;

		}
		throw new UnknownDataTypeException(dataType);
	}

	public String getHtmlFieldType() {
		return htmlFieldType;
	}

	public int getType() {
		return dataType;
	}

}
