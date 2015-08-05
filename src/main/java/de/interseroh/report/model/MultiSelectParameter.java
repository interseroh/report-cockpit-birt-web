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
			List<String> params = new ArrayList<>(getValue().size());
			for (T value : getValue()) {
				params.add(getName() + '=' + urlEncode(getValueAsString()));
			}
			return params;
		} else {
			return super.asRequestParameter();
		}
	}

	@Override
	public Map<String, Object> asReportParameter() {
		if (!isUnset()) {
			return new HashMap<String, Object>() {
				{
					put(getName(), getValue().toArray());
				}
			};
		} else {
			return super.asReportParameter();
		}
	}


}
