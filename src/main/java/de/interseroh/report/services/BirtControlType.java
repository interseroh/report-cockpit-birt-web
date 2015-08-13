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

import de.interseroh.report.exception.UnknownDataTypeException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtControlType {

	TEXT_BOX(0), //
	SELECTION(1), //
	RADIO_BUTTON(2), //
	CHECK_BOX(3), //
	AUTO_SUGGEST(4);

	private int controlType;

	BirtControlType(int controlType) {
		this.controlType = controlType;
	}

	public static BirtControlType valueOf(int controlType) {
		for (BirtControlType type : BirtControlType.values()) {
			if (type.controlType == controlType)
				return type;

		}
		throw new UnknownDataTypeException(controlType);
	}

	public int getControlType() {
		return controlType;
	}

}
