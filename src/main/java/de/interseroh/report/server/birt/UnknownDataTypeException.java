package de.interseroh.report.server.birt;


/**
 * @author Ingo Düppe (Crowdcode)
 */
public class UnknownDataTypeException extends BirtSystemException {

    public UnknownDataTypeException(int dataType) {
        super("The DataType with the id " + dataType + " is unknown. Please check " + BirtDataType.class.getName() + "!");
    }
}
