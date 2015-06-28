package de.interseroh.report.server.birt;


/**
 * @author Ingo Düppe (Crowdcode)
 */
public class UnknownParameterTypeException extends BirtSystemException {

    public UnknownParameterTypeException(int parameterType) {
        super("The Parameter with the id " + parameterType + " is unknown. Please check " + BirtParameterType.class.getName() + "!");
    }
}
