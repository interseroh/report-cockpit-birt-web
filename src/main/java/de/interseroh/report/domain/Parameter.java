package de.interseroh.report.domain;

import de.interseroh.report.common.Visitable;
import de.interseroh.report.domain.visitors.ParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface Parameter extends Visitable<ParameterVisitor> {

	String getParameterType();

	/**
	 * Checks whether the constrains are fulfilled. if a value is required and
	 * no default value is defined a valid value must be assigned.
	 *
	 * @return true if the parameter is valid
	 */
	boolean isValid();

	String getName();

	String getDisplayLabel();

	String getTooltip();

}
