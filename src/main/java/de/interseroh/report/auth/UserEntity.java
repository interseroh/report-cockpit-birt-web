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
@Table(name = "RCB_USER")
public class UserEntity extends AbstractPersistable<Long> implements User {

	private static final long serialVersionUID = -8469721636610148266L;

	@Column(name = "USER_EMAIL")
	private String email;

	@OneToMany(mappedBy = "user", targetEntity = UserRoleEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<UserRole> userRoles;

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}

	@Override
	public void addUserRole(UserRole userRole) {
		userRoles.add(userRole);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		UserEntity that = (UserEntity) o;

		return email != null ? email.equals(that.email) : that.email == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}
}
