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

import java.lang.reflect.Array;
import java.util.Date;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtDataType {

	TYPE_ANY(0, "text", String.class), //
	TYPE_STRING(1, "text", String.class), //
	TYPE_FLOAT(2, "number", Float.class), //
	TYPE_DECIMAL(3, "number", Double.class), //
	TYPE_DATE_TIME(4, "datetime", Date.class), //
	TYPE_BOOLEAN(5, "checkbox", Boolean.class), //
	TYPE_INTEGER(6, "number", Integer.class), //
	TYPE_DATE(7, "date", Date.class), //
	TYPE_TIME(8, "time", Date.class); //

	private int dataType;
	private String htmlFieldType;
    private Class valueType;
    private Class valueArrayType;

	<T> BirtDataType(int dataType, String htmlFieldType, Class<T> valueType) {
        this.dataType = dataType;
		this.htmlFieldType = htmlFieldType;
        this.valueType = valueType;
        this.valueArrayType = Array.newInstance(valueType,0).getClass();
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

    public Class getValueType() {
        return valueType;
    }

    public Class getValueArrayType() {
        return valueArrayType;
    }
}
