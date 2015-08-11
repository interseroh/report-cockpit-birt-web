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
public class ReportParamsBuilder implements ParameterVisitor {

    private Map<String, Object> params = new HashMap<>();

    /**
     * Builds a map for all scalar parameter based on parameter name and value.
     * Multi-value and adhoc scalar parameter will be transformed to an
     * array of values. For instance, <code>name={value1, value2}</code>
     *
     * @return
     */
    public Map<String, Object> build(Collection<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            parameter.accept(this);
        }
        return params;
    }

    @Override
    public <T> void visit(ScalarParameter<T> parameter) {
        String paramName = parameter.getName();
        T paramValue = parameter.getValue();
        params.put(paramName, paramValue);
    }

    @Override
    public void visit(ParameterGroup group) {
        for (ScalarParameter parameter : group.getParameters()) {
            visit(parameter);
        }
    }

}
