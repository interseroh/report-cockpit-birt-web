package de.interseroh.report.model;

import java.util.Collections;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractParameter<SUB extends AbstractParameter<SUB>> implements Parameter {

	private String name;
    private String displayLabel;
    private String tooltip;

    @Override
	public String getParameterType() {
		return "TEXTBOX";
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SUB withName(final String name) {
		this.name = name;
		return (SUB) this;
	}

    public String getDisplayLabel() {
        return displayLabel;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StringParameter parameter = (StringParameter) o;

		return !(getName() != null ? !getName().equals(parameter.getName())
				: parameter.getName() != null);

	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public SUB withDisplayLabel(final String displayLabel) {
        this.displayLabel = displayLabel;
        return (SUB) this;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String toolTip) {
        this.tooltip = toolTip;
    }

    public SUB withTooltip(final String toolTip) {
        this.tooltip = toolTip;
        return (SUB) this;
    }

    @Override
    public List<String> asRequestParameter() {
        return Collections.emptyList();
    }
}
