package de.interseroh.report.model;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class RadioSelectParameter<T>
		extends AbstractSelectionParameter<RadioSelectParameter<T>, T> {

	@Override
	public String getParameterType() {
		return "RADIOSELECT";
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

}
