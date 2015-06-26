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

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import de.interseroh.report.server.birt.BirtReportService;
import de.interseroh.report.server.service.BirtEngineFactory;
import de.interseroh.report.util.RestProviderBeanScanner;
import de.interseroh.report.util.RestServiceBeanScanner;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.List;

@Configuration
@ComponentScan("de.interseroh.report.server.service")
@PropertySource("classpath:config.properties")
public class ReportConfig {

	@Bean
	public BirtEngineFactory birtEngineFactory() {
		return new BirtEngineFactory();
	}

	@Bean
	public BirtReportService birtReportService() {
		return new BirtReportService();
	}


}
