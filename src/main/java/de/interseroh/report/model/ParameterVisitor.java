package de.interseroh.report.model;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface ParameterVisitor {

    void visit(DefaultGroupParameter parameter);

    void visit(SingleSelectParameter parameter);

    void visit(RadioSelectParameter parameter);

    void visit(MultiSelectParameter parameter);

	void visit(StringParameter parameter);

    void visit(BooleanParameter parameter);
}
