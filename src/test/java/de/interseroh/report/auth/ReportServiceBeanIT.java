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
package de.interseroh.report.auth;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.interseroh.report.webconfig.DatabaseConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@Rollback
public class ReportServiceBeanIT {

	@Autowired
	private UsersRolesAndReportsCreator usersRolesAndReportsCreator;

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
	}

	@Transactional
	@Test
	public void testFindReportsByRoleId() {
		// We need to make this transactional so we can rollback at the end
		// Prepare
		createUserRolesAndReports();

		String email = "lofi@dewanto.com";
		Collection<UserRole> userRoles = userService
				.findUserRolesByUserEmail(email);
		Long roleId = null;
		for (UserRole userRole : userRoles) {
			if (userRole.getRole().getName().equals("USER_INTERSEROH")) {
				roleId = userRole.getRole().getId();
				break;
			}
		}

		// CUT
		if (roleId != null) {
			Collection<Report> reports = reportService
					.findReportsByRoleId(roleId);

			// Asserts
			assertEquals(2, reports.size());
		}
	}

	private void createUserRolesAndReports() {
		usersRolesAndReportsCreator.createUserRolesAndReports();
	}

}
