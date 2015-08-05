package de.interseroh.report.model;

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
public class GroupParameterBuilder {

	private static final Logger log = LoggerFactory
			.getLogger(GroupParameterBuilder.class);

	private IGetParameterDefinitionTask task;
	private Collection<IParameterDefnBase> definitions;

	private GroupParameter group;

	public GroupParameterBuilder(IGetParameterDefinitionTask task,
                                 Collection<IParameterDefnBase> definitions) {
		this.task = task;
		this.definitions = definitions;
	}

	public List<GroupParameter> build() {

		GroupParameter wrapperGroup = null;

		List<GroupParameter> groups = new ArrayList<>(definitions.size());
		for (IParameterDefnBase definition : definitions) {
			if (definition instanceof IParameterGroupDefn) {
				wrapperGroup = null;
				groups.add(buildGroup((IParameterGroupDefn) definition));
			} else if (definition instanceof IScalarParameterDefn) {
				wrapperGroup = buildWrapperGroup(wrapperGroup, groups);
				wrapperGroup.addScalarParameter(buildScalarParameter(
						(IScalarParameterDefn) definition));
			} else {
				log.error("Parameter Definition is not supported: {}",
						definition);
			}

		}
		return groups;
	}

	private GroupParameter buildWrapperGroup(
            GroupParameter wrapperGroup, List<GroupParameter> groups) {
		if (wrapperGroup == null) {
			wrapperGroup = new DefaultGroupParameter();
			groups.add(wrapperGroup);
		}
		return wrapperGroup;
	}

	public GroupParameter buildGroup(IParameterGroupDefn definition) {
		return new DefaultGroupParameter()
                .withName(definition.getName())
				.withDisplayLabel(orNull(definition.getDisplayName(),
						definition.getPromptText()))
                .withCascading(definition instanceof ICascadingParameterGroup)
				.withParameters(buildScalarParameters(
						(Collection<IParameterDefnBase>) definition
								.getContents()));
	}

	private List<ScalarParameter> buildScalarParameters(
			Collection<IParameterDefnBase> definitions) {
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

		ScalarParameter parameter;

		switch (controlType) {
		case SELECTION:
			parameter = createSelectionParameter(definition);
			break;
		case RADIO_BUTTON:
			parameter = new RadioSelectParameter().withOptions(of(definition));
			break;
		case TEXT_BOX:
			if (dataType == BirtDataType.TYPE_BOOLEAN) {
				parameter = new BooleanParameter();
			} else {
				parameter = new StringParameter();
			}
			break;
		default:
			parameter = new StringParameter();
		}

		parameter.setName(definition.getName());
		parameter.setDisplayLabel(orNull(definition.getDisplayName(),
				definition.getPromptText(), definition.getName()));
		parameter.setTooltip(definition.getHelpText());

        parameter.setSimpleValue("simple".equals(definition.getScalarParameterType()));
        parameter.setDataType(BirtDataType.valueOf(definition.getDataType()));

		return parameter;
	}

	private ScalarParameter createSelectionParameter(
			IScalarParameterDefn definition) {
		if ("simple".equals(definition.getScalarParameterType())) {
			return new SingleSelectParameter().withOptions(of(definition));
		} else {
			return new MultiSelectParameter().withOptions(of(definition));
		}
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
