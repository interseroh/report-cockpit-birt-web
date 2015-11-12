/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * (c) 2015 - Interseroh and Crowdcode
 */
package de.interseroh.report.formatters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.Test;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class TimeFormatterTest {

    private static final Time testTime = new Time(45900000L);

    private static final TimeFormatter formatter = new TimeFormatter();

    @Test
    public void testParse_DE() throws Exception {
        Time parsed = formatter.parse("13:45", Locale.GERMAN);
        assertThat(parsed, is(testTime));
    }

    @Test
    public void testPrint_DE() throws Exception {
        String print = formatter.print(testTime, Locale.GERMAN);
        assertThat(print, is("13:45"));
    }

    @Test
    public void testPrint_US() throws Exception {
        String print = formatter.print(testTime, Locale.US);
        assertThat(print, is("1:45 PM"));
    }

    @Test
    public void testParse_US() throws Exception {
        Time parsed = formatter.parse("1:45 PM", Locale.US);
        assertThat(parsed, is(testTime));
    }

    @Test
    public void testDateFormat() throws Exception {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
        System.out.println(((SimpleDateFormat)dateFormat).toLocalizedPattern());
    }
}