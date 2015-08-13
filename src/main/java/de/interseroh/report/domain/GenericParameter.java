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

import de.interseroh.report.domain.visitors.ParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class GenericParameter<T>
		extends AbstractScalarParameter<GenericParameter<T>, T> {

	public GenericParameter(Class<T> valueType) {
		super(valueType);
	}

	public static final <T> GenericParameter<T> newInstance(
			Class<T> valueType) {
		return new GenericParameter<>(valueType);
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean isMultiValue() {
		return getValueType().isArray();
	}
}
