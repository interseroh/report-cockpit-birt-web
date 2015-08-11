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
package de.interseroh.report.model;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterLogVisitor implements ParameterVisitor {

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterLogVisitor.class);

	private StringBuilder output;

	private final String indent = "\n\t";

	public void printParameters(Collection<? extends Parameter> params) {
		output = new StringBuilder();
		visitParameters(params);
		logger.debug(output.toString());
	}

	private void visitParameters(Collection<? extends Parameter> params) {
		for (Parameter param : params) {
			param.accept(this);
		}
	}

	@Override
	public void visit(StringParameter parameter) {
		print("StringParameter:", parameter);

	}

	@Override
	public void visit(BooleanParameter parameter) {
		print("BooleanParameter:", parameter);
	}

	@Override
	public void visit(SingleSelectParameter parameter) {
		print("SingleSelect:", parameter);
	}

	@Override
	public void visit(RadioSelectParameter parameter) {
		print("RadioSelect:", parameter);
	}

	@Override
	public void visit(MultiSelectParameter parameter) {
		print("MultiSelect:", parameter);
	}

	@Override
	public void visit(DefaultGroupParameter parameter) {
		output.append("\n\n");
		output.append(parameter.getName());
		output.append("{" + parameter.isCascading() + "}");
		visitParameters(parameter.getParameters());
	}

	private void print(String title, Parameter parameter) {
		output.append(indent);
		output.append(title);
		output.append(indent);
		output.append("\t" + parameter.toString());
	}

}
