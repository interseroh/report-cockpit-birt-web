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
package de.interseroh.report.domain.visitors;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterLogVisitor implements ParameterVisitor {

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterLogVisitor.class);
	private final String indent = "\n\t";
	private StringBuilder output;

	public static void printParameters(ParameterForm form) {
		printParameters(form.getGroups());
	}

	public static void printParameters(Collection<? extends Parameter> params) {
		if (logger.isDebugEnabled()) {
			new ParameterLogVisitor().print(params);
		}
	}

	private void print(Collection<? extends Parameter> params) {
		output = new StringBuilder();
		visitParameters(params);
		logger.debug(output.toString());
	}

	private void visitParameters(Collection<? extends Parameter> params) {
		for (Parameter param : params) {
			param.accept(this);
		}
	}

	private void print(String title, Parameter parameter) {
		output.append(indent);
		output.append(title);
		output.append(indent);
		output.append('\t');
		output.append(parameter.toString());
	}

	@Override
	public void visit(ScalarParameter parameter) {
		print("ScalarParameter", parameter);
	}

	@Override
	public void visit(ParameterGroup group) {
		output.append("\n\n");
		output.append(group.getName());
		output.append('{').append(group.isCascading()).append('}');
		visitParameters(group.getParameters());
	}
}
