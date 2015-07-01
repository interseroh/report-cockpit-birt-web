package de.interseroh.report.exception;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class RenderReportException extends BirtReportException {

	private static final long serialVersionUID = -6262330423837485443L;

	public RenderReportException(String format, String reportName,
			Exception cause) {
		super("Error while trying to " + format + " rendering of " + reportName
				+ ".", cause);
	}
}
