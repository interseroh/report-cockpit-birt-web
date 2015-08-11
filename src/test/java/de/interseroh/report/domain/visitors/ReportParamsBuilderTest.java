package de.interseroh.report.domain.visitors;

import de.interseroh.report.domain.GenericParameter;
import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.SelectionParameter;
import de.interseroh.report.webconfig.WebMvcConfig;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ReportParamsBuilderTest {

    @Test
    public void testBuild() throws Exception {
        Map<String, Object> map = new ReportParamsBuilder().build(buildTestData());

        assertThat((Double)map.get("double"), is(55.5));
        assertThat((Boolean)map.get("boolean"), is(false));
        assertThat((Integer[])map.get("selectMULTI"), is(new Integer[]{1,2}));
    }


    private List<Parameter> buildTestData() {
        List<Parameter> params = new ArrayList<>();

        params.add(new ParameterGroup() //
                .withName("group1").withSynthetic(false)
                .addScalarParameter(GenericParameter.newInstance(Double.class) //
                        .withName("double").withValue(55.5))
                .addScalarParameter(GenericParameter.newInstance(Boolean.class) //
                        .withName("boolean").withValue(false)));
        params.add(new ParameterGroup() //
                .withName("group2").withSynthetic(true)
                .addScalarParameter(GenericParameter.newInstance(String.class) //
                        .withName("string").withValue("value"))
                .addScalarParameter(GenericParameter.newInstance(Date.class) //
                        .withName("dateTime").withValue(fixedDate())));
        params.add(new ParameterGroup() //
                .withName("group3").withCascading(true)
                .addScalarParameter(SelectionParameter.newInstance(Boolean.class)
                        .withName("radioNULL"))
                .addScalarParameter(SelectionParameter.newInstance(String.class)
                        .withName("selectNULL"))
                .addScalarParameter(SelectionParameter.newInstance(Integer[].class)
                        .withName("selectMULTI").withValue(new Integer[]{1,2})));
        return params;
    }

    private Date fixedDate() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(2015, 8, 5, 21, 12, 23);
        return calendar.getTime();
    }
}