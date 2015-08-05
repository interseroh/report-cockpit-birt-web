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

import java.util.Collection;

import org.eclipse.birt.report.engine.api.ICascadingParameterGroup;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtReportUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtReportUtil.class);

	public static void printSelectionChoices(
			Collection<IParameterSelectionChoice> selectionChoices) {
		logger.debug("---------- CHOICES:");
		if (selectionChoices != null) {
			for (IParameterSelectionChoice selectionChoice : selectionChoices) {
				logger.debug("Label: " + selectionChoice.getLabel());
				logger.debug(" |  Value: " + selectionChoice.getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void printSelectionList(IParameterDefnBase paramDefn,
			IGetParameterDefinitionTask task) {

		Collection<IParameterSelectionChoice> selectionChoices = task
				.getSelectionList(paramDefn.getName());

		printSelectionChoices(selectionChoices);
	}

	public static void printParameterDefinitions(
			Collection<IParameterDefnBase> parameterDefinitions,
			IGetParameterDefinitionTask task) {

		for (IParameterDefnBase definition : parameterDefinitions) {
			logger.debug("-------------------------------------------------");
			printInterfaces(definition);
			logger.debug("");
			printParameter(definition);
			printScalarParameter(definition);
			printParameterGroup(task, definition);
			printSelectionList(definition, task);
		}
	}

	private static void printInterfaces(IParameterDefnBase definition) {
		logger.debug("Class:" + definition.getClass().getCanonicalName());
		for (Class<?> anInterface : definition.getClass().getInterfaces()) {
			logger.debug("\t" + anInterface);
		}
		logger.debug("");
	}

	public static void printParameter(IParameterDefnBase definition) {
		logger.debug("Displayname: " + definition.getDisplayName());
		logger.debug("Helptext: " + definition.getHelpText());
		logger.debug("Name: " + definition.getName());
		logger.debug("Typename: " + definition.getTypeName());
		logger.debug("ParameterType: "
				+ BirtParameterType.valueOf(definition.getParameterType()));
	}

	@SuppressWarnings("unchecked")
	public static void printParameterGroup(IGetParameterDefinitionTask task,
			IParameterDefnBase definition) {
		if (definition instanceof IParameterGroupDefn) {
			IParameterGroupDefn group = (IParameterGroupDefn) definition;
			printParameterDefinitions(group.getContents(), task);

			Collection<IParameterSelectionChoice> selectionChoices = task
					.getSelectionListForCascadingGroup(group.getName(),
							new Object[] {});

			if (definition instanceof ICascadingParameterGroup) {
				ICascadingParameterGroup cascadingParameterGroup = (ICascadingParameterGroup) definition;
				logger.debug("DataSet: " + cascadingParameterGroup.getDataSet());
			}

			printSelectionChoices(selectionChoices);

			logger.debug(".......");
			selectionChoices = task.getSelectionListForCascadingGroup(
					group.getName(), new Object[] { 103 });
			printSelectionChoices(selectionChoices);
		}
	}

	public static void printScalarParameter(IParameterDefnBase definition) {
		if (definition instanceof IScalarParameterDefn) {
			IScalarParameterDefn scalar = (IScalarParameterDefn) definition;
			logger.debug("DataType: "
					+ BirtDataType.valueOf(scalar.getDataType()));
			logger.debug("PromptText: " + scalar.getPromptText());
			logger.debug("Required: " + scalar.isRequired());
			logger.debug("AllowNewValues: " + scalar.allowNewValues());
			logger.debug("DisplayInFixedOrder: " + scalar.displayInFixedOrder());
			logger.debug("IsValueConcealed: " + scalar.isValueConcealed());
			logger.debug("DisplayFormat: " + scalar.getDisplayFormat());
			logger.debug("ControlType: "
					+ BirtControlType.valueOf(scalar.getControlType()));
			logger.debug("DefaultValue: " + scalar.getDefaultValue());
			logger.debug("ScalarParameterType: "
					+ scalar.getScalarParameterType());
			logger.debug("SelectionListType: " + scalar.getSelectionListType());
		}
	}
}
