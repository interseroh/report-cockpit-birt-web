package de.interseroh.report.model;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class SingleSelectParameter<T>
		extends AbstractSelectionParameter<SingleSelectParameter<T>, T>
		implements SelectionParameter<SingleSelectParameter<T>, T> {

	@Override
	public String getParameterType() {
		return "SINGLESELECT";
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

}
