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
package de.interseroh.report.webconfig;

import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import de.interseroh.report.util.RestProviderBeanScanner;
import de.interseroh.report.util.RestServiceBeanScanner;

@Configuration
@ComponentScan("de.interseroh.entsorgung.server")
@PropertySource("classpath:config.properties")
public class UiJaxRsConfig {

	public static final String API_BASE = "/location/v1/*";

	private static final String REPORT_SERVER = "de.interseroh.report.server";

	@ApplicationPath("/")
	public class JaxRsApiApplication extends Application {
	}

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@Bean
	@DependsOn("cxf")
	public Server jaxRsServer(ApplicationContext appContext) {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance()
				.createEndpoint(jaxRsApiApplication(),
						JAXRSServerFactoryBean.class);
		factory.setServiceBeans(restServiceList(appContext));
		factory.setAddress("/" + factory.getAddress());
		factory.setProviders(restProviderList(appContext, jsonProvider()));
		return factory.create();
	}

	@Bean
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}

	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}

	private List<Object> restServiceList(ApplicationContext appContext) {
		return RestServiceBeanScanner.scan(appContext, REPORT_SERVER);
	}

	private List<Object> restProviderList(final ApplicationContext appContext,
			final JacksonJsonProvider jsonProvider) {
		final List<Object> providers = RestProviderBeanScanner.scan(appContext,
				REPORT_SERVER);
		providers.add(jsonProvider);
		return providers;
	}

}
