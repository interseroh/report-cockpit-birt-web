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
package de.interseroh.report.server.location.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import de.interseroh.report.server.location.entity.Location;
import de.interseroh.report.server.location.service.LocationService;
import de.interseroh.report.server.location.service.LocationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceImplTest {

	@InjectMocks
	LocationService locationService = new LocationServiceImpl();

	@Mock
	Environment env;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetDomainServiceUrl() {
		Mockito.when(env.getProperty(Mockito.anyString())).thenReturn(
				"http://localhost:8080/entsorgung/v1");

		Location url = locationService.getDomainServiceUrl();

		assertEquals("http://localhost:8080/entsorgung/v1", url.getUrl());
	}

}
