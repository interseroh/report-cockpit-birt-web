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
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.test.security;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DemoSecurityTestConfig.class })
public class LdapServerTest {

	private static final String PASSWORD_LDAP = "test";
	private static final String USER_LDAP = "test";
	private static final Logger logger = LoggerFactory
			.getLogger(LdapServerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testJndiSpring() throws Exception {
		DefaultSpringSecurityContextSource ctxSrc = new DefaultSpringSecurityContextSource(
				"ldap://ldap.xxx:389/OU=xxx");

		ctxSrc.setUserDn(USER_LDAP);
		ctxSrc.setPassword(PASSWORD_LDAP);

		ctxSrc.afterPropertiesSet();

		logger.info("Base LDAP Path: " + ctxSrc.getBaseLdapPath());
		logger.info("Principal: "
				+ ctxSrc.getAuthenticationSource().getPrincipal().toString());
		logger.info("Credentials: "
				+ ctxSrc.getAuthenticationSource().getCredentials());

		Authentication bob = new UsernamePasswordAuthenticationToken("bob",
				"bob");

		BindAuthenticator authenticator = new BindAuthenticator(ctxSrc);
		authenticator.setUserSearch(new FilterBasedLdapUserSearch("",
				"(&(objectCategory=Person)(sAMAccountName={0}))", ctxSrc));
		authenticator.afterPropertiesSet();

		authenticator.authenticate(bob);

		DirContextOperations user = authenticator.authenticate(bob);

		logger.info("User: {}", user);
	}

	@Test
	public void testJndiSun() throws NamingException {
		Hashtable<String, String> contextParams = new Hashtable<String, String>();
		contextParams.put(Context.PROVIDER_URL, "ldap://ldap.xxx:389");
		contextParams.put(Context.SECURITY_PRINCIPAL, USER_LDAP);
		contextParams.put(Context.SECURITY_CREDENTIALS, PASSWORD_LDAP);
		contextParams.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");

		DirContext dirContext = new InitialDirContext(contextParams);

		Attributes attributes = dirContext.getAttributes("",
				new String[] { "namingContexts" });
		Attribute attribute = attributes.get("namingContexts");
		NamingEnumeration<?> all = attribute.getAll();
		while (all.hasMore()) {
			String next = (String) all.next();
			logger.info(next);
		}
	}
}
