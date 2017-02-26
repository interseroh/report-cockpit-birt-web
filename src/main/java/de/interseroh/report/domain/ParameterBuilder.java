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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.birt.report.engine.api.ICascadingParameterGroup;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interseroh.report.services.BirtControlType;
import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterBuilder {

	private static final Logger log = LoggerFactory
			.getLogger(ParameterBuilder.class);

	private final IGetParameterDefinitionTask task;

	public ParameterBuilder(IGetParameterDefinitionTask task) {
		this.task = task;
	}

	public List<ParameterGroup> build(
			Collection<IParameterDefnBase> definitions) {

		ParameterGroup syntheticGroup = null;

		List<ParameterGroup> groups = new ArrayList<>(definitions.size());
		for (IParameterDefnBase definition : definitions) {
			if (definition instanceof IParameterGroupDefn) {
				syntheticGroup = null;
				groups.add(buildGroupFromDefinition(
						(IParameterGroupDefn) definition));
			} else if (definition instanceof IScalarParameterDefn) {
				syntheticGroup = buildIfNeededASyntheticGroup(syntheticGroup,
						groups);
				syntheticGroup.addScalarParameter(buildScalarParameter(
						(IScalarParameterDefn) definition));
			} else {
				log.error("Parameter Definition is not supported: {}",
						definition);
			}

		}
		return groups;
	}

	private ParameterGroup buildIfNeededASyntheticGroup(
			ParameterGroup syntheticGroup, List<ParameterGroup> groups) {
		if (syntheticGroup == null) {
			syntheticGroup = new ParameterGroup().withSynthetic(true);
			groups.add(syntheticGroup);
		}
		return syntheticGroup;
	}

	public ParameterGroup buildGroupFromDefinition(
			IParameterGroupDefn definition) {

		List<ScalarParameter> parameters = buildScalarParameters(
				definition.getContents());

		String displayLabel = orNull( //
				definition.getDisplayName(), //
				definition.getPromptText()); //

		return new ParameterGroup()
				//
				.withName(definition.getName()).withDisplayLabel(displayLabel)
				.withCascading(definition instanceof ICascadingParameterGroup)
				.withParameters(parameters);
	}

	private List<ScalarParameter> buildScalarParameters(
			List<IParameterDefnBase> definitions) {

		List<ScalarParameter> parameters = new ArrayList<>(definitions.size());
		for (IParameterDefnBase definition : definitions) {
			if (definition instanceof IScalarParameterDefn) {
				parameters.add(buildScalarParameter(
						(IScalarParameterDefn) definition));
			}
		}
		return parameters;
	}

	private ScalarParameter buildScalarParameter(
			IScalarParameterDefn definition) {

		BirtControlType controlType = BirtControlType
				.valueOf(definition.getControlType());
		BirtDataType dataType = BirtDataType.valueOf(definition.getDataType());

		AbstractScalarParameter parameter;

		Class valueType = isSingleValue(definition) ?
				dataType.getValueType() :
				dataType.getValueArrayType();

		Class textType = isSingleValue(definition) ?
				String.class :
				String[].class;

		switch (controlType) {
		case SELECTION:
		case RADIO_BUTTON:
			parameter = SelectionParameter.newInstance(valueType, textType)
					.withOptions(of(definition));
			break;
		default:
			parameter = GenericParameter.newInstance(valueType, textType);
		}

		String displayLabel = orNull(definition.getDisplayName(),
				definition.getPromptText(), definition.getName());

		parameter.setName(definition.getName());
		parameter.setDefaultValue(task.getDefaultValue(definition.getName()));
		parameter.setRequired(definition.isRequired());
		parameter.setDisplayLabel(displayLabel);
		parameter.setTooltip(definition.getHelpText());
		parameter.setControlType(controlType);
		parameter.setDataType(dataType);
		parameter.setDisplayFormat(definition.getDisplayFormat());

		return parameter;
	}

	private boolean isSingleValue(IScalarParameterDefn definition) {
		return "simple".equals(definition.getScalarParameterType());
	}

	private List<SelectionOption> of(IScalarParameterDefn definition) {
		return toOptions(task.getSelectionList(definition.getName()));
	}

	public List<SelectionOption> toOptions(
			Collection<IParameterSelectionChoice> choices) {

		List<SelectionOption> options = new ArrayList<>(choices.size());
		for (IParameterSelectionChoice choice : choices) {
			options.add( //
					new SelectionOption() //
							.withDisplayName(choice.getLabel()) //
							.withValue(choice.getValue().toString())); //
		}

		return options;
	}

	private String orNull(String... values) {
		for (String value : values) {
			if (value != null && !value.trim().isEmpty()) {
				return value;
			}
		}
		return null;
	}

}
