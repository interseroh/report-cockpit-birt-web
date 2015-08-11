package de.interseroh.report.domain;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractParameter<SUB extends AbstractParameter>
		implements Parameter {

	private String name;
	private String displayLabel;
	private String tooltip;

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

	@Override
	public String getDisplayLabel() {
		return displayLabel;
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

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public SUB withTooltip(final String tooltip) {
		this.tooltip = tooltip;
		return (SUB) this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AbstractParameter parameter = (AbstractParameter) o;
		return !(name != null ? !name.equals(parameter.name)
				: parameter.name != null);
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Parameter{" + "tooltip='" + tooltip + '\'' + ", displayLabel='"
				+ displayLabel + '\'' + ", name='" + name + "'}";
	}
}
