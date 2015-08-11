package de.interseroh.report.domain;

import java.util.ArrayList;
import java.util.List;

import de.interseroh.report.domain.visitors.ParameterVisitor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterGroup extends AbstractParameter<ParameterGroup> {

	private List<ScalarParameter> parameters;
	private boolean cascading;
	private boolean synthetic;

	@Override
	public String getParameterType() {
		return "GROUP";
	}

    @Override
    public boolean isValid() {
        boolean valid = true;
        for (ScalarParameter parameter : parameters) {
            valid &= parameter.isValid();
        }
        return valid;
    }

    public List<ScalarParameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<>();
		}
		return parameters;
	}

	public void setParameters(List<ScalarParameter> parameters) {
		this.parameters = parameters;
	}

	public ParameterGroup withParameters(List<ScalarParameter> parameters) {
		setParameters(parameters);
		return this;
	}

	public ParameterGroup addScalarParameter(ScalarParameter parameter) {
		getParameters().add(parameter);
		return this;
	}

	public boolean isCascading() {
		return cascading;
	}

	public void setCascading(boolean cascading) {
		this.cascading = cascading;
	}

	public ParameterGroup withCascading(boolean cascading) {
		setCascading(cascading);
		return this;
	}

	public boolean isInvisible() {
		return synthetic || StringUtils.isBlank(getDisplayLabel());
	}

	public boolean isSynthetic() {
		return synthetic;
	}

	public void setSynthetic(boolean synthetic) {
		this.synthetic = synthetic;
	}

	public ParameterGroup withSynthetic(final boolean synthetic) {
		this.synthetic = synthetic;
		return this;
	}

    @Override
    public void accept(ParameterVisitor visitor) {
        visitor.visit(this);
    }
}
