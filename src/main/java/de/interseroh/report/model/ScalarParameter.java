package de.interseroh.report.model;

import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface ScalarParameter<T> extends Parameter {

	/**
	 * The current value of the parameter
	 * 
	 * @return T current value or null
	 */
	T getValue();

	/**
	 * Setting the value of the parameter
	 * 
	 * @param value
	 *            or null of the parameter.
	 */
	void setValue(T value);

    /**
     * String representation of value
     * @return String or empty
     */
    String getValueAsString();

    /**
	 * The default value
	 * 
	 * @return default value or null if no default value is defined
	 */
	T getDefaultValue();

    /**
     *
     * @return Html Input Type or null
     */
	String getHtmlFieldType();


    /**
     *
     * @return BirtDataType
     */
	BirtDataType getDataType();

    void setDataType(BirtDataType dataType);

	/**
	 * True if a value is needed
	 * 
	 * @return
	 */
	boolean isRequired();

	/**
	 *
	 * @return true if the user input should be hidden.
	 */
	boolean isConcealed();


    /**
     * Not simple parameters must be wrapped as an array when calling a report.
     * @return true if it is a simple parameter type.
     */
    boolean isSimpleValue();

    void setSimpleValue(boolean simple);


}
