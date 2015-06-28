package de.interseroh.report.server.birt;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public enum BirtOutputFormat {

    HTML5("html"),
    PDF("pdf"),
    EXCEL("xls"),
    EXCEL2010("xlsx");

    private final String urlParameterValue;

    private BirtOutputFormat(String urlParameterValue) {
        this.urlParameterValue = urlParameterValue;
    }

    public String getUrlParameterValue() {
        return urlParameterValue;
    }

    public static BirtOutputFormat from(String urlParameterValue) {
        for (BirtOutputFormat format : BirtOutputFormat.values()) {
            if (format.urlParameterValue.equals(urlParameterValue)) {
                return format;
            }
        }
        return HTML5; // default;
    }
}
