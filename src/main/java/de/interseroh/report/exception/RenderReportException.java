package de.interseroh.report.exception;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class RenderReportException extends BirtReportException {

	public RenderReportException(String format, String reportName,
			Exception cause) {
		super("Error while trying to " + format + " rendering of " + reportName
				+ ".", cause);
	}
}
