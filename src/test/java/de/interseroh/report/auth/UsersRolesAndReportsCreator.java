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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersRolesAndReportsCreator {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private ReportRepository reportRepository;

	public void createUserRolesAndReports() {
		Role roleUser1 = new RoleEntity();
		roleUser1.setName("USER_INTERSEROH");
		User user = new UserEntity();
		user.setEmail("lofi@dewanto.com");
		UserRole userRole1 = userService.createUserRole(user, roleUser1);

		Role roleUser2 = new RoleEntity();
		roleUser2.setName("USER_CROWDCODE");
		userService.createUserRole(user, roleUser2);

		ReportEntity report1 = new ReportEntity();
		report1.setName("salesinvoice");
		report1.setRole(userRole1.getRole());
		ReportEntity reportEntity1 = reportRepository.save(report1);

		assert (reportEntity1 != null);

		userRole1.getRole().addReport(reportEntity1);

		ReportEntity report2 = new ReportEntity();
		report2.setName("multiselect");
		report2.setRole(userRole1.getRole());
		ReportEntity reportEntity2 = reportRepository.save(report2);

		assert (reportEntity2 != null);

		userRole1.getRole().addReport(reportEntity2);
	}
}
