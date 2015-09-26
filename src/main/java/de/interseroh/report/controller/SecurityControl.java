package de.interseroh.report.controller;

import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserService;
import de.interseroh.report.services.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hhopf on 26.09.15.
 */
@Component
public class SecurityControl {

	@Autowired
	private SecurityHelper securityHelper;

	@Autowired
	private UserService userService;

	/**
	 * roles from the logged User.
	 *
	 * @return all roles from user
	 */
	public List<String> getRoles() {

		String userName = securityHelper.getPrincipalName();

		Collection<UserRole> rolesCollection = userService.
				findUserRolesByUserEmail(userName);

		List<String> roles = new ArrayList<>();
		for (UserRole role : rolesCollection) {
			roles.add(role.getRole().getName());
		}
		return roles;
	}
}
