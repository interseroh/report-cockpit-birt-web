package de.interseroh.report.parameter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.interseroh.report.domain.GenericParameter;
import de.interseroh.report.domain.ParameterForm;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.SelectionParameter;

/**
 * Created by idueppe on 27.02.17.
 */
public class RequestParamsFixture {
	public static ParameterForm buildTestData() {
		List<ParameterGroup> groups = new ArrayList<>();

		groups.add(new ParameterGroup() //
				.withName("group1").withSynthetic(false).addScalarParameter(
						GenericParameter.newInstance(Double.class) //
								.withName("double").withValue(55.5))
				.addScalarParameter(
						GenericParameter.newInstance(Boolean.class) //
								.withName("boolean").withValue(false)));
		groups.add(new ParameterGroup() //
				.withName("group2").withSynthetic(true).addScalarParameter(
						GenericParameter.newInstance(String.class) //
								.withName("string").withValue("value"))
				.addScalarParameter(GenericParameter.newInstance(Date.class) //
						.withName("dateTime").withValue(fixedDate())));
		groups.add(new ParameterGroup() //
				.withName("group3").withCascading(true).addScalarParameter(
						SelectionParameter.newInstance(Boolean.class)
								.withName("radioNULL")).addScalarParameter(
						SelectionParameter.newInstance(String.class)
								.withName("selectNULL")).addScalarParameter(
						SelectionParameter.newMultiInstance(Integer[].class)
								.withName("scalarMULTI")
								.withValue(new Integer[] { 1, 2 })));
		return new ParameterForm().withParameterGroups(groups);
	}

	private static Date fixedDate() {
		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.set(2015, 8, 5, 21, 12, 23);
		return new Date(calendar.getTimeInMillis());
	}

}
