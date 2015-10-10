package de.interseroh.report.controller;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static de.interseroh.report.controller.ParameterFormFormatter.isNotBlank;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class ParameterFormFormatterTest {

    @Test
    public void testIsNotBlank() throws Exception {
        assertThat(isNotBlank(null), is(false));
        assertThat(isNotBlank(new Integer[]{}), is(false));
        assertThat(isNotBlank(new Integer[]{1}), is(true));
        assertThat(isNotBlank(1l), is(true));
        assertThat(isNotBlank(" "),is(false));
    }

    @Test
    public void testIsNotBlankWithStrings() throws Exception {
        assertThat(isNotBlank(""),is(false));
        assertThat(isNotBlank("   "),is(false));
        assertThat(isNotBlank(" a "),is(true));
    }
}