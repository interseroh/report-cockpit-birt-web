package de.interseroh.report.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterToMapVisitor implements ParameterVisitor {

	private final Collection<? extends Parameter> parameters;
	private Map<String, Parameter> parameterMap = new HashMap<>();

	public ParameterToMapVisitor(Collection<? extends Parameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Parameter> build() {
		parameterMap.clear();
		acceptParameters(parameters);
		return parameterMap;
	}

	private void acceptParameters(Collection<? extends Parameter> parameters) {
		for (Parameter parameter : parameters) {
			parameter.accept(this);
		}
	}

	private void addScalarParameter(ScalarParameter scalarParameter) {
		parameterMap.put(scalarParameter.getName(), scalarParameter);
	}

	@Override
	public void visit(DefaultGroupParameter group) {
		if (group.isCascading()) {
			parameterMap.put(group.getName(), group);
		}
		acceptParameters(group.getParameters());
	}

	@Override
	public void visit(StringParameter parameter) {
		addScalarParameter(parameter);
	}

	@Override
	public void visit(BooleanParameter parameter) {
		addScalarParameter(parameter);
	}

	@Override
	public void visit(SingleSelectParameter parameter) {
		addScalarParameter(parameter);
	}

	@Override
	public void visit(RadioSelectParameter parameter) {
		addScalarParameter(parameter);
	}

	@Override
	public void visit(MultiSelectParameter parameter) {
		addScalarParameter(parameter);
	}
}
