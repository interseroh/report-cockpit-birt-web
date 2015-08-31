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

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "RCB_ROLE")
public class RoleEntity extends AbstractPersistable<Long> implements Role {

	private static final long serialVersionUID = 3878644102882809215L;

	@Column(name = "ROLE_NAME")
	private String name;

	@OneToMany(mappedBy = "role", targetEntity = UserRoleEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<UserRole> userRoles;

	@Override
	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}

	@Override
	public void addUserRole(UserRole userRole) {
		userRoles.add(userRole);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Collection<Report> getReports() {
		return null;
	}

	@Override
	public void addReport(Report report) {
		// TODO Auto-generated method stub
	}

}
