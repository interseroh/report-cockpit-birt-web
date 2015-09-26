package de.interseroh.report.controller;

import de.interseroh.report.auth.*;
import de.interseroh.report.services.SecurityHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by hhopf on 26.09.15.
 */

@RunWith(MockitoJUnitRunner.class)
public class SecurityControlTest {

	@InjectMocks
	private SecurityControl securityControl = new SecurityControl();

	@Mock
	UserService userService;

	@Mock
	private SecurityHelper securityHelper;


	@Test
	public void testForLoop1() {
		UserRole userRole = getUserRole();
		Collection<UserRole>  roles = new ArrayList<>();
		roles.add(userRole);

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName"))).thenReturn(roles);

		List<String> rolesResult = securityControl.getRoles();

		assertEquals("1 role is in Array", 1, rolesResult.size());

	}

	@Test
	public void testForLoop2() {
		Collection<UserRole>  roles = getUserRoles();

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName"))).thenReturn(roles);

		List<String> rolesResult = securityControl.getRoles();

		assertEquals("2 role is in Array", 2, rolesResult.size());
	}

	private UserRole getUserRole() {
		Role role = new RoleEntity();
		role.setName("ROLE_SALESINVOICE");
		UserRole uRole = new UserRoleEntity();
		uRole.setRole(role);
		return uRole;
	}

	private Collection<UserRole> getUserRoles() {
		Role role = new RoleEntity();
		role.setName("ROLE_SALESINVOICE");
		UserRole uRole = new UserRoleEntity();
		uRole.setRole(role);
		Role role1 = new RoleEntity();
		role1.setName("ROLE_PRODUCTCATALOG");
		UserRole uRole1 = new UserRoleEntity();
		uRole1.setRole(role1);


		Collection<UserRole>  roles = new ArrayList<>();
		roles.add(uRole);
		roles.add(uRole1);
		return roles;
	}
}
