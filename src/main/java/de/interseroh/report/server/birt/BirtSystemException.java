package de.interseroh.report.server.birt;

import de.interseroh.report.server.exception.ApplicationException;
import de.interseroh.report.server.exception.SystemException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtSystemException extends SystemException {

    public BirtSystemException() {
    }

    public BirtSystemException(String message) {
        super(message);
    }

    public BirtSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public BirtSystemException(Throwable cause) {
        super(cause);
    }

    public BirtSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
