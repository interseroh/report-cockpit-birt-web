package de.interseroh.report.model;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class SelectionOption {

	private String displayName;
	private String value;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public SelectionOption withDisplayName(final String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SelectionOption withValue(final String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return "{" + "'" + value + "='" + displayName + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SelectionOption that = (SelectionOption) o;

		return !(value != null ? !value.equals(that.value)
				: that.value != null);

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
