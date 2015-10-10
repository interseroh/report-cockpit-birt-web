package de.interseroh.report.formatters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public abstract class AbstractDateFormatter {

	protected DateFormat getDateFormat(Locale locale) {
		if (DisplayFormatHolder.isDisplayFormatSet()) {
			return new SimpleDateFormat(DisplayFormatHolder.getDisplayFormat(),
					locale);
		} else {
            return getFormatterInstance(locale);
		}
	}

	protected abstract DateFormat getFormatterInstance(Locale locale);
}
