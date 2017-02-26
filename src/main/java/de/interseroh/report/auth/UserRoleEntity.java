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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * JPA Membership entity.
 *
 * @author Lofi Dewanto (Interseroh)
 */
@Entity
@Table(name = "RCB_USER_ROLE")
public class UserRoleEntity extends AbstractPersistable<Long> implements
		UserRole {

	private static final long serialVersionUID = -2552585409665169353L;

	@ManyToOne(optional = false, targetEntity = UserEntity.class)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@ManyToOne(optional = false, targetEntity = RoleEntity.class)
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
	private Role role;

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Role getRole() {
		return role;
	}

	@Override
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		UserRoleEntity that = (UserRoleEntity) o;

		if (user != null ? !user.equals(that.user) : that.user != null)
			return false;
		return role != null ? role.equals(that.role) : that.role == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (role != null ? role.hashCode() : 0);
		return result;
	}
}
