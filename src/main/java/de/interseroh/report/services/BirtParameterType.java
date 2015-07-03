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

import de.interseroh.report.exception.UnknownParameterTypeException;

public enum BirtParameterType {

	SCALAR_PARAMETER(0), //
	FILTER_PARAMETER(1), //
	LIST_PARAMETER(2), //
	TABLE_PARAMETER(3), //
	PARAMETER_GROUP(4), //
	CASCADING_PARAMETER_GROUP(5); //

	private int parameterType;

	BirtParameterType(int parameterType) {
		this.parameterType = parameterType;
	}

	public static BirtParameterType valueOf(int parameterType) {
		for (BirtParameterType type : BirtParameterType.values()) {
			if (type.parameterType == parameterType)
				return type;

		}
		throw new UnknownParameterTypeException(parameterType);
	}

	public int getType() {
		return parameterType;
	}

}
