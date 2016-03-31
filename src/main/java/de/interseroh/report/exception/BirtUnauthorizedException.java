package de.interseroh.report.exception;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class BirtUnauthorizedException extends BirtReportException {

    private static final long serialVersionUID = -611721969725624716L;

    public BirtUnauthorizedException(String reportName) {
        super(String.format("User has no role for %s.", reportName));
    }
}
