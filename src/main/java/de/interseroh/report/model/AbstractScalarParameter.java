package de.interseroh.report.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.interseroh.report.services.BirtControlType;
import de.interseroh.report.services.BirtDataType;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractScalarParameter<SUB extends AbstractScalarParameter<SUB, T>, T>
		extends AbstractParameter<SUB>implements ScalarParameter<T> {

	private T value;
	private T defaultValue;

	private BirtDataType dataType = BirtDataType.TYPE_STRING;
	private BirtControlType controlType;
	private boolean simple;

	private boolean required;
	private boolean concealed;

	@Override
	public boolean isUnset() {
		return !(isValid());
	}

	@Override
	public boolean isValid() {
		return !isRequired() //
				|| value != null || defaultValue != null;
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
	public List<String> asRequestParameter() {
		if (!isUnset()) {
			String param = "params[" + urlEncode(getName()) + "].value="
					+ urlEncode(getValueAsString());
			return Arrays.asList(param);
		} else {
			return super.asRequestParameter();
		}
	}

	@Override
	public Map<String, Object> asReportParameter() {
		if (!isUnset()) {
			Map<String, Object> param = new HashMap<>();
			param.put(getName(), (simple) ? getValueAsObject()
					: new Object[] { getValueAsObject() });
			return param;
		} else {
			return Collections.emptyMap();
		}
	}

	protected String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(
					"Report Parameter Encoding did not work.", e);
		}
	}

	protected T valueOrDefault() {
		return (value != null) ? value : defaultValue;
	}

	@Override
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public SUB withValue(T value) {
		setValue(value);
		return (SUB) this;
	}

	public String getValueAsString() {
		return (value == null) ? "" : value.toString();
	}

	public Object getValueAsObject() {
		Object paramValue = null;

		switch (getDataType()) {
		case TYPE_INTEGER:
			try {
				paramValue = Integer.valueOf(getValueAsString());
			} catch (NumberFormatException nfe) {
				paramValue = getValueAsString();
			}
			break;
		case TYPE_FLOAT:
		case TYPE_DECIMAL:
			try {
				paramValue = Double.valueOf(getValueAsString());
			} catch (NumberFormatException nfe) {
				paramValue = getValueAsString();
			}
			break;
		case TYPE_TIME:
		case TYPE_DATE_TIME:
		case TYPE_DATE:
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			try {
				paramValue = sdf.parse(getValueAsString());
			} catch (ParseException e) {
				paramValue = getValueAsString();
			}
			break;
		case TYPE_ANY:
		case TYPE_STRING:
			paramValue = getValueAsString();
			break;
		}
		return paramValue;
	}

	@Override
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public BirtDataType getDataType() {
		return dataType;
	}

	public void setDataType(BirtDataType dataType) {
		this.dataType = dataType;
	}

	public SUB withDefaultValue(final T defaultValue) {
		this.defaultValue = defaultValue;
		return (SUB) this;
	}

	public SUB withDataType(final BirtDataType dataType) {
		this.dataType = dataType;
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

	@Override
	public boolean isSimpleValue() {
		return simple;
	}

	@Override
	public void setSimpleValue(boolean simple) {
		this.simple = simple;
	}

	public SUB withSimple(final boolean simple) {
		this.simple = simple;
		return (SUB) this;
	}

	public SUB withConcealed(final boolean concealed) {
		this.concealed = concealed;
		return (SUB) this;
	}

	@Override
	public String toString() {
		return "ScalarParameter{" + "name='" + getName() + '\'' + ", value='"
				+ value + '\'' + ", defaultValue='" + defaultValue + '\''
				+ ", displayLabel='" + getDisplayLabel() + '\'' + ", dataType="
				+ dataType + ", controlType=" + controlType + ", required="
				+ required + ", concealed=" + concealed + '}';
	}

}
