package de.interseroh.report.server.birt;

import de.interseroh.report.server.exception.ApplicationException;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtReportException extends ApplicationException {

    public BirtReportException() {
    }

    public BirtReportException(String message) {
        super(message);
    }

    public BirtReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public BirtReportException(Throwable cause) {
        super(cause);
    }

    public BirtReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
