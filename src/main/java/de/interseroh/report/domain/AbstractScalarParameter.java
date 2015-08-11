package de.interseroh.report.domain;

import de.interseroh.report.services.BirtControlType;
import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractScalarParameter<SUB extends AbstractScalarParameter, T>
		extends AbstractParameter<SUB>implements ScalarParameter<T> {

	private final Class<T> valueType;
	private T value;
	private T defaultValue;

	private BirtDataType dataType = BirtDataType.TYPE_STRING;
	private BirtControlType controlType;

	private boolean required;
	private boolean concealed;

	public AbstractScalarParameter(Class<T> valueType) {
		this.valueType = valueType;
	}

	public Class<T> getValueType() {
		return valueType;
	}

    public boolean isMultiValue() {
        return valueType.isArray();
    }


    public BirtDataType getDataType() {
		return dataType;
	}

	public void setDataType(BirtDataType dataType) {
		this.dataType = dataType;
	}

	public BirtControlType getControlType() {
		return controlType;
	}

	public void setControlType(BirtControlType controlType) {
		this.controlType = controlType;
	}

	@Override
	public String getParameterType() {
		return "SCALAR_PARAMETER";
	}

	@Override
	public String getHtmlFieldType() {
		if (dataType == BirtDataType.TYPE_STRING && concealed) {
			return "password";
		} else if (dataType != null) {
			return dataType.getHtmlFieldType();
		} else {
			return BirtDataType.TYPE_STRING.getHtmlFieldType();
		}
	}

	@Override
	public boolean isValid() {
		return !required || value != null || defaultValue != null;
	}

	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public SUB withDefaultValue(final T defaultValue) {
		this.defaultValue = defaultValue;
		return (SUB) this;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	public SUB withValue(final T value) {
		this.value = value;
		return (SUB) this;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public SUB withRequired(final boolean required) {
		this.required = required;
		return (SUB) this;
	}

	@Override
	public boolean isConcealed() {
		return concealed;
	}

	public void setConcealed(boolean concealed) {
		this.concealed = concealed;
	}

	public SUB withConcealed(final boolean concealed) {
		this.concealed = concealed;
		return (SUB) this;
	}

}
