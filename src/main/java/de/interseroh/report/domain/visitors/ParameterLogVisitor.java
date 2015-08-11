package de.interseroh.report.domain.visitors;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.ScalarParameter;


/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterLogVisitor implements ParameterVisitor {

    private static final Logger logger = LoggerFactory
            .getLogger(ParameterLogVisitor.class);

    private StringBuilder output;

    private String indent = "\n\t";


    public void printParameters(Collection<? extends Parameter> params) {
        output = new StringBuilder();
        visitParameters(params);
        logger.debug(output.toString());
    }

    private void visitParameters(Collection<? extends Parameter> params) {
        for (Parameter param : params) {
            param.accept(this);
        }
    }

    private void print(String title, Parameter parameter) {
        output.append(indent);
        output.append(title);
        output.append(indent);
        output.append("\t" + parameter.toString());
    }

    @Override
    public void visit(ScalarParameter parameter) {
        print("ScalarParameter", parameter);
    }

    @Override
    public void visit(ParameterGroup group) {
        output.append("\n\n");
        output.append(group.getName());
        output.append("{" + group.isCascading() + "}");
        visitParameters(group.getParameters());
    }
}
