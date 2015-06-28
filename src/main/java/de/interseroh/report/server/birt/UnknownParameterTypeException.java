package de.interseroh.report.server.birt;


/**
 * @author Ingo Düppe (Crowdcode)
 */
public class UnknownParameterTypeException extends BirtSystemException {

    public UnknownParameterTypeException(int dataType) {
        super("The Parameter with the id " + dataType + " is unknown. Please check " + BirtDataType.class.getName() + "!");
    }
}
