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
package de.interseroh.report.exception;

import de.interseroh.report.services.BirtParameterType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class UnknownParameterTypeException extends BirtSystemException {

	private static final long serialVersionUID = -84859661520137757L;

	public UnknownParameterTypeException(int parameterType) {
		super("The Parameter with the id " + parameterType
				+ " is unknown. Please check "
				+ BirtParameterType.class.getName() + "!");
	}
}
