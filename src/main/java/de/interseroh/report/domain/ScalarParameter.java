package de.interseroh.report.domain;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface ScalarParameter<T> extends Parameter {

	String getHtmlFieldType();

	T getDefaultValue();

	T getValue();

	void setValue(T value);

	boolean isMultiValue();

	boolean isRequired();

	boolean isConcealed();
}
