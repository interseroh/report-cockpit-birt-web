package de.interseroh.report.controller;

import org.junit.Test;

import static de.interseroh.report.controller.ParameterFormFormatter.isNotNullOrEmptyArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterFormFormatterTest {

    @Test
    public void testIsNotNullOrEmptyWithObjects() throws Exception {
        assertThat(isNotNullOrEmptyArray(null), is(false));
        assertThat(isNotNullOrEmptyArray(1L), is(true));
    }

    @Test
    public void testIsNotNullOrEmptyWithArrays() throws Exception {
        assertThat(isNotNullOrEmptyArray(new Integer[]{}), is(false));
        assertThat(isNotNullOrEmptyArray(new Integer[]{1}), is(true));
    }

    @Test
    public void testIsNotNullOrEmptyWithStrings() throws Exception {
        assertThat(isNotNullOrEmptyArray(""),is(true));
        assertThat(isNotNullOrEmptyArray("   "),is(true));
        assertThat(isNotNullOrEmptyArray(" a "),is(true));
    }
}