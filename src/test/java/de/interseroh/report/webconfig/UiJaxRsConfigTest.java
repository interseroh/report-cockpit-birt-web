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

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.interseroh.report.webconfig.UiJaxRsConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UiJaxRsConfig.class)
@PropertySource("classpath:config.properties")
public class UiJaxRsConfigTest {

	@Inject
	ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Test
	public void testContext() {
		applicationContext.getBean("jaxRsServer");
		applicationContext.getBean("jaxRsApiApplication");
	}
}
