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

import java.io.PrintStream;
import java.util.Collection;

import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtReportUtil {

	public static void printSelectionChoices(
			Collection<IParameterSelectionChoice> selectionChoices) {
		System.out.println("---------- CHOICES");
		if (selectionChoices != null) {
			for (IParameterSelectionChoice selectionChoice : selectionChoices) {
				System.out.print("Label: " + selectionChoice.getLabel());
				System.out.println("/ Value: " + selectionChoice.getValue());
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

	@SuppressWarnings("unchecked")
	public static void printParameterDefinitions(
			Collection<IParameterDefnBase> parameterDefinitions,
			IGetParameterDefinitionTask task) {

		for (IParameterDefnBase definition : parameterDefinitions) {
			PrintStream out = System.out;

            printParameter(definition, out);
            printScalarParameter(definition, out);
            printScalarParameterGroup(task, definition, out);
            printSelectionList(definition, task);

		}
	}

	@SuppressWarnings("unchecked")
    public static void printParameter(IParameterDefnBase definition,
                                      PrintStream out) {
        out.println("----------------------------------------------");
        out.println("Displayname: " + definition.getDisplayName());
        out.println("Helptext: " + definition.getHelpText());
        out.println("Name: " + definition.getName());
        out.println("Typename: " + definition.getTypeName());
        out.println("ParameterType: "
                + BirtParameterType.valueOf(definition.getParameterType()));
    }

    @SuppressWarnings("unchecked")
    public static void printScalarParameterGroup(IGetParameterDefinitionTask
                                                     task, IParameterDefnBase definition, PrintStream out) {
        if (definition instanceof IParameterGroupDefn) {
            IParameterGroupDefn group = (IParameterGroupDefn) definition;
            printParameterDefinitions(group.getContents(), task);

            Collection<IParameterSelectionChoice> cascadingGroup = task
                    .getSelectionListForCascadingGroup(group.getName(),
                            new Object[]{});
            printSelectionChoices(cascadingGroup);

            out.println(".......");
            cascadingGroup = task.getSelectionListForCascadingGroup(
                    group.getName(), new Object[] { 103 });
            printSelectionChoices(cascadingGroup);
        }
    }

	@SuppressWarnings("unchecked")
    public static void printScalarParameter(IParameterDefnBase definition,
                                        PrintStream out) {
        if (definition instanceof IScalarParameterDefn) {
            IScalarParameterDefn scalar = (IScalarParameterDefn) definition;
            out.println("DataType: "
                    + BirtDataType.valueOf(scalar.getDataType()));
            out.println("PromptText: " + scalar.getPromptText());
            out.println("Required: " + scalar.isRequired());
            out.println("AllowNewValues: " + scalar.allowNewValues());
            out.println("DisplayInFixedOrder: "
                    + scalar.displayInFixedOrder());
            out.println("IsValueConcealed: " + scalar.isValueConcealed());
            out.println("DisplayFormat: " + scalar.getDisplayFormat());
            out.println("ControlType: " + scalar.getControlType());
            out.println("DefaultValue: " + scalar.getDefaultValue());
            out.println("ScalarParameterType: "
                    + scalar.getScalarParameterType());
        }
    }
}
