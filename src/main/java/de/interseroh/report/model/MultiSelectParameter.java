package de.interseroh.report.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class MultiSelectParameter<T>
		extends AbstractSelectionParameter<MultiSelectParameter<T>, List<T>> {

	private static final int MAX_VISIBLE_ROWS = 5;

	/**
	 *
	 * @return max visible rows for multiselect controls.
	 */
	public int getRowCount() {
		return (getOptions().size() <= MAX_VISIBLE_ROWS) ? getOptions().size()
				: MAX_VISIBLE_ROWS;
	}

	@Override
	public String getParameterType() {
		return "MULTISELECT";
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<String> asRequestParameter() {
		if (!isUnset()) {
			List<String> params = new ArrayList<>(4);
			for (T value : getValue()) {
				params.add("params[" + getName() + "].value="
						+ urlEncode(asString(value)));
			}
			return params;
		} else {
			return super.asRequestParameter();
		}
	}

	@Override
	public Map<String, Object> asReportParameter() {
		if (!isUnset()) {
            final List values = new ArrayList();
            for (T value : getValue()) {
                values.add(asObject(asString(value)));
            }
			return new HashMap<String, Object>() {
				{
					put(getName(), values.toArray());
				}
			};
		} else {
			return super.asReportParameter();
		}
	}

    @Override
    public List<T> getValue() {
        if (super.getValue() == null) {
            setValue(new ArrayList<T>());
        }
        return super.getValue();
    }

    /**
     * Just a workaround until we have a generic converter solution
     * @param value
     * @return
     */
	private String asString(T value) {
		return (value != null) ? value.toString() : "";
	}

}
