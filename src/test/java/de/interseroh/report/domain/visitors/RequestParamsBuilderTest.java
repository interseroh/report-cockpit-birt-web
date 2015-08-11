package de.interseroh.report.domain.visitors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.interseroh.report.domain.GenericParameter;
import de.interseroh.report.domain.Parameter;
import de.interseroh.report.domain.ParameterGroup;
import de.interseroh.report.domain.SelectionParameter;
import de.interseroh.report.webconfig.WebMvcConfig;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class RequestParamsBuilderTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    public void testConversionService() throws Exception {

        String requestParams = new RequestParamsBuilder(conversionService).asRequestParams(buildTestData());

        assertThat(requestParams, is("?double=55.5&boolean=false&string=value&dateTime=2015-09-05T19%3A12%3A23.344%2B0000&scalarMULTI=1&scalarMULTI=2"));
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
                        .withName("scalarMULTI").withValue(new Integer[]{1,2})));
        return params;
    }

    private Date fixedDate() {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.set(2015, 8, 5, 21, 12, 23);
        return calendar.getTime();
    }


}