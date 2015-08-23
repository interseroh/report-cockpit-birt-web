package de.interseroh.report.formatters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.Locale;

import org.junit.Test;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class DateFormatterTest {

    private static final String TEST_DATE = "2015-12-13";
	private static final Date testDate = new Date(1449961200000L);

    private static final DateFormatter formatter = new DateFormatter();

	@Test
	public void testParse() throws Exception {
		Date date = formatter.parse("2015-12-13", Locale.getDefault());
		assertThat(date, is(testDate));
	}

	@Test
	public void testPrint() throws Exception {
        String print = formatter.print(testDate, Locale.getDefault());
		assertThat(print, is(TEST_DATE));
    }
}