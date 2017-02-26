package de.interseroh.report.formatters;

import java.sql.Date;
import java.util.Locale;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class DateFormatterTest {

    private static final DateFormatter formatter = new DateFormatter();

	@Test
	public void testParse() throws Exception {
		Date date = formatter.parse("02/26/17", Locale.US);
		assertThat(formatter.print(date, Locale.US), is("2/26/17"));
	}

    @Test
    public void testParseGermanDate() throws Exception {
		Date date = formatter.parse("26.02.2017", Locale.GERMAN);
		assertThat(formatter.print(date, Locale.GERMAN), is("26.02.17"));
	}

}