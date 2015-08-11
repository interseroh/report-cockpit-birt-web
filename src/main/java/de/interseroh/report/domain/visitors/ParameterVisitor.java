package de.interseroh.report.domain.visitors;

import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface ParameterVisitor {

	<T> void visit(ScalarParameter<T> parameter);

    void visit(ParameterGroup parameter);

}
