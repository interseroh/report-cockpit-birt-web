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
package de.interseroh.report.server.location.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import de.interseroh.report.server.location.entity.Location;
import de.interseroh.report.server.location.service.LocationService;
import de.interseroh.report.shared.location.LocationValue;
import de.interseroh.report.util.annotation.RestService;

@RestService
@Path("/locations")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class LocationResource {

	private static final Logger logger = Logger
			.getLogger(LocationResource.class);

	@Inject
	private LocationService locationService;

	public LocationResource() {
	}

	@GET
	@Path("/domainservice")
	public LocationValue getDomainServiceUrl() {
		logger.debug("getDomainServiceUrl");
		Location location = locationService.getDomainServiceUrl();
		LocationValue locationValue = build(location);

		return locationValue;
	}

	@GET
	@Path("/interserohlogo")
	public LocationValue getInterserohLogoUrl() {
		logger.debug("getInterserohLogoUrl");
		Location location = locationService.getInterserohLogoUrl();
		LocationValue locationValue = build(location);

		return locationValue;
	}

	private LocationValue build(Location location) {
		LocationValue locationValue = new LocationValue();
		locationValue.setUrl(location.getUrl());

		return locationValue;
	}

}
