package de.interseroh.report.domain;

import de.interseroh.report.domain.visitors.ParameterVisitor;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class GenericParameter<T>
		extends AbstractScalarParameter<GenericParameter<T>, T> {

	public GenericParameter(Class<T> valueType) {
		super(valueType);
	}

	public static final <T> GenericParameter<T> newInstance(
			Class<T> valueType) {
		return new GenericParameter<>(valueType);
	}

	@Override
	public void accept(ParameterVisitor visitor) {
		visitor.visit(this);
	}

    @Override
    public boolean isMultiValue() {
        return getValueType().isArray();
    }
}
