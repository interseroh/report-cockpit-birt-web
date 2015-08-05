package de.interseroh.report.model;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@Override
	public void visit(StringParameter parameter) {
		print("StringParameter:", parameter);

	}

	@Override
	public void visit(BooleanParameter parameter) {
		print("BooleanParameter:", parameter);
	}

	@Override
	public void visit(SingleSelectParameter parameter) {
		print("SingleSelect:", parameter);
	}

	@Override
	public void visit(RadioSelectParameter parameter) {
		print("RadioSelect:", parameter);
	}

	@Override
	public void visit(MultiSelectParameter parameter) {
		print("MultiSelect:", parameter);
	}

	@Override
	public void visit(DefaultGroupParameter parameter) {
		output.append("\n\n");
		output.append(parameter.getName());
		output.append("{" + parameter.isCascading() + "}");
		visitParameters(parameter.getParameters());
	}

	private void print(String title, Parameter parameter) {
		output.append(indent);
		output.append(title);
		output.append(indent);
		output.append("\t" + parameter.toString());
	}

}
