package de.interseroh.report.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractSelectionParameter<SUB extends AbstractSelectionParameter<SUB, T>, T>
		extends AbstractScalarParameter<SUB, T>
		implements SelectionParameter<SUB, T> {

	private List<SelectionOption> options;

	public List<SelectionOption> getOptions() {
		if (options == null) {
			options = new ArrayList<>(10);
		}
		return options;
	}

	public void setOptions(List<SelectionOption> options) {
		this.options = options;
	}

	public SUB withOptions(final List<SelectionOption> options) {
		this.options = options;
		return (SUB) this;
	}

	@Override
	public String toString() {
		return super.toString() + "\n\t {" + "options=" + options + "} ";
	}

}
