package de.interseroh.report.model;

import java.util.List;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface SelectionParameter<SUB, T> extends ScalarParameter<T> {

	List<SelectionOption> getOptions();

	void setOptions(List<SelectionOption> options);

	SUB withOptions(final List<SelectionOption> options);

}
