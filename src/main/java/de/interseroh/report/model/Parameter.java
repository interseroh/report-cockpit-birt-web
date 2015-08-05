package de.interseroh.report.model;

import de.interseroh.report.common.Visitable;

import java.util.List;
import java.util.Map;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface Parameter extends Visitable<ParameterVisitor> {
	/**
	 * A string that unique identify the parameter type within the parameter
	 * hierarchy
	 * 
	 * @return String identifying the concrete parameter type
	 */
	String getParameterType();

	/**
	 * <b>Unique</b> name of the parameter in the scope of a report.
	 * 
	 * @return unique name.
	 */
	String getName();

	/**
	 * <b>Unique</b> name of the parameter in the scope of a report
	 * 
	 * @param unique
	 *            name.
	 */
	void setName(String name);

	/**
	 *
	 * @return String or null if no display label is configured
	 */
	String getDisplayLabel();

	/**
	 *
	 * @param displayLabel
	 */
	void setDisplayLabel(String displayLabel);

	/**
	 * A helpttext to be presented as a tooltip.
	 * 
	 * @return String or null if no helptext is defined.
	 */
	String getTooltip();

	/**
	 *
	 * @param tooltip
	 *            or null if no helptext is defined.
	 */
	void setTooltip(String tooltip);

	/**
	 *
	 * @return true if this parameter and all sub parameters are unset.
	 */
	boolean isUnset();

	/**
	 * Checks whether the constrains are fulfilled. if a value is required and
	 * no default value is defined a valid value must be assigned.
	 * 
	 * @return true if the parameter is valid
	 */
	boolean isValid();

    /**
     * @return List of "key=value" strings as request parameter
     */
    List<String> asRequestParameter();

    /**
     * @return Map of parameter name and values
     */
    Map<String, Object> asReportParameter();

}
