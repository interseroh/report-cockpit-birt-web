package de.interseroh.report.domain.visitors;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractParameterVisitor implements ParameterVisitor {

	@Override
	public void visit(ParameterGroup group) {
		for (Parameter param : group.getParameters()) {
			param.accept(this);
		}
	}
}
