package de.interseroh.report.domain.visitors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

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
	public void visit(ScalarParameter parameter) {
        addScalarParameter(parameter);
	}

	@Override
	public void visit(ParameterGroup group) {
        if (group.isCascading()) {
            parameterMap.put(group.getName(), group);
        }
        acceptParameters(group.getParameters());
    }
}
