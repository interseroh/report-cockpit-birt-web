package de.interseroh.report.server.birt;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtOutputFormat {

	HTML5("html", "text/html; charset=UTF-8"), PDF("pdf", "application/pdf"), EXCEL(
			"xls", "application/vnd.ms-excel"), EXCEL2010("xlsx",
			"application/vnd.ms-excel");

	private final String formatName;
	private final String contentType;

	private BirtOutputFormat(String formatName, String contentType) {
		this.formatName = formatName;
		this.contentType = contentType;
	}

	public String getFormatName() {
		return formatName;
	}

	public String getContentType() {
		return contentType;
	}

	public static BirtOutputFormat from(String formatName) {
		for (BirtOutputFormat format : BirtOutputFormat.values()) {
			if (format.formatName.equals(formatName)) {
				return format;
			}
		}
		return HTML5; // default;
	}
}
