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

import java.sql.Timestamp;
import java.util.Locale;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public class TimestampFormatterTest {

    private static final Timestamp testTime = new Timestamp(1450010700000L);

    private static final TimestampFormatter formatter = new TimestampFormatter();

    @Test
    public void testParse_de() throws Exception {
        Timestamp parsed = formatter.parse("13.12.2015 13:45", Locale.GERMAN);
		assertThat(formatter.print(parsed, Locale.GERMAN),
				is("13.12.15 13:45"));
	}
}