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
package de.interseroh.report.webapp;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionListener implements HttpSessionListener {

	private static final Logger logger = Logger
			.getLogger(SessionListener.class);

	private static final String DEFAULT_TIMEOUT_INTERVAL = "21600";

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ApplicationContext applicationContext = getApplicationContext(event);
		Environment env = applicationContext.getEnvironment();

		String timeout = env.getProperty("session.timeout.interval");

		if (timeout == null || timeout.equals("")) {
			// Default seconds 21600 == 6 hours
			timeout = DEFAULT_TIMEOUT_INTERVAL;
		}

		event.getSession().setMaxInactiveInterval(Integer.parseInt(timeout));

		if (logger.isDebugEnabled()) {
			logger.debug("Session created, timeout: " + timeout);
		}
	}

	private ApplicationContext getApplicationContext(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(session.getServletContext());
		return ctx;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// Do nothing
		if (logger.isDebugEnabled()) {
			logger.debug("Session destroyed.");
		}
	}

}
