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

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService Implementation.
 *
 * @author Lofi Dewanto (Interseroh)
 */
@Service
public class UserServiceBean implements UserService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	@Override
	public Collection<UserRole> findUserRolesByUserEmail(String email) {
		Collection<UserRoleEntity> userRoles = userRoleRepository
				.findByUserEmail(email);

		Collection<UserRole> returnUserRoles = new ArrayList<>();
		returnUserRoles.addAll(userRoles);

		return returnUserRoles;
	}

	@Transactional
	@Override
	public UserRole createUserRole(User user, Role role) {
		UserRole userRole = new UserRoleEntity();
		userRole.setRole(role);
		userRole.setUser(user);

		userRepository.save((UserEntity) user);
		roleRepository.save((RoleEntity) role);

		return userRoleRepository
				.save((UserRoleEntity) userRole);
	}
}
