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

	private static final Date testDate = new Date(2015, 12, 13);

    private static final DateFormatter formatter = new DateFormatter();

	@Test
	public void testParse() throws Exception {
		Date date = formatter.parse("12/13/15", Locale.US);
		assertThat(date, is(testDate));
	}

	@Test
	public void testPrint() throws Exception {
        String print = formatter.print(testDate, Locale.US);
		assertThat(print, is("12/13/15"));
    }

    @Test
    public void testParseGermanDate() throws Exception {
        Date date = formatter.parse("13.12.2015", Locale.GERMAN);
        assertThat(date, is(testDate));
    }

    @Test
    public void testPrintGermanDate() throws Exception {
        String print = formatter.print(testDate, Locale.GERMAN);
        assertThat(print, is("13.12.15"));

    }
}