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
package de.interseroh.report.webconfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SecurityConfigAuthTest {

	@InjectMocks
	SecurityConfig securityConfig = new SecurityConfig();

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Environment env;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private AuthenticationManagerBuilder auth;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPrepareLdapAuthTrue() throws Exception {
		// Prepare
		Mockito.when(env.getProperty("ldap.authentication")).thenReturn("true");
		// CUT
		String result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("true", result);
	}

	@Test
	public void testPrepareLdapAuthFalse() throws Exception {
		// Prepare
		Mockito.when(env.getProperty("ldap.authentication"))
				.thenReturn("false");
		// CUT
		String result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("false", result);
	}

	@Test
	public void testPrepareLdapAuthNull() throws Exception {
		// Prepare
		Mockito.when(env.getProperty("ldap.authentication")).thenReturn(null);
		// CUT
		String result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("true", result);
	}

	@Test
	public void testPrepareLdapAuthEmpty() throws Exception {
		// Prepare
		Mockito.when(env.getProperty("ldap.authentication")).thenReturn("");
		// CUT
		String result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("true", result);
	}

	@Test
	public void testPrepareLdapAuthWrongValue() throws Exception {
		// Prepare
		Mockito.when(env.getProperty("ldap.authentication")).thenReturn("null");
		// CUT
		String result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("true", result);

		// Prepare
		Mockito.when(env.getProperty("ldap.authentication"))
				.thenReturn("aloha");
		// CUT
		result = securityConfig.prepareLdapAuth();
		// Asserts
		assertEquals("true", result);
	}

}
