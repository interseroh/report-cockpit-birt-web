package de.interseroh.report.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.interseroh.report.auth.Role;
import de.interseroh.report.auth.RoleEntity;
import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserRoleEntity;
import de.interseroh.report.auth.UserService;
import de.interseroh.report.services.SecurityHelper;
import de.interseroh.report.services.SecurityService;

/**
 * Created by hhopf on 26.09.15.
 */

@RunWith(MockitoJUnitRunner.class)
public class SecurityControlTest {

	@Mock
	UserService userService;

	@InjectMocks
	private SecurityService securityControl = new SecurityService();

	@Mock
	private SecurityHelper securityHelper;

	@Test
	public void testForLoop1() {
		UserRole userRole = getUserRole();
		Collection<UserRole> roles = new ArrayList<>();
		roles.add(userRole);

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName")))
				.thenReturn(roles);

		List<String> rolesResult = securityControl.getRoles();

		assertEquals("1 role is in Array", 1, rolesResult.size());

	}

	@Test
	public void testForLoop2() {
		Collection<UserRole> roles = getUserRoles();

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName")))
				.thenReturn(roles);

		List<String> rolesResult = securityControl.getRoles();

		assertEquals("2 role is in Array", 2, rolesResult.size());
	}

	@Test
	public void testHasUserRoleValid() {
		Collection<UserRole> roles = getUserRoles();
		roles.add(getUserRoleForCascade());

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName")))
				.thenReturn(roles);
		boolean visible = securityControl
				.hasUserValidRole("cascade_parameters");

		assertTrue(visible);

	}

	@Test
	public void testHasUserRoleValidWITHPräfixFalse() {
		Collection<UserRole> roles = getUserRolesWithEqualsPräfix();
		roles.add(getUserRoleForCascade());

		when(securityHelper.getPrincipalName()).thenReturn("userName");

		when(userService.findUserRolesByUserEmail(eq("userName")))
				.thenReturn(roles);
		boolean visible = securityControl
				.hasUserValidRole("CHART");

		assertFalse(visible);

	}

	private UserRole getUserRole() {
		return createUserRole("ROLE_SALESINVOICE");
	}

	private UserRole getUserRoleForCascade() {
		return createUserRole("ROLE_CASCADE_PARAMETERS");
	}

	private Collection<UserRole> getUserRoles() {
		Collection<UserRole> userRoles = new ArrayList<>();
		userRoles.add(createUserRole("ROLE_SALESINVOICE"));
		userRoles.add(createUserRole("ROLE_PRODUCTCATALOG"));
		return userRoles;
	}

	private Collection<UserRole> getUserRolesWithEqualsPräfix() {
		Collection<UserRole> userRoles = new ArrayList<>();
		userRoles.add(createUserRole("ROLE_CHARTI"));
		userRoles.add(createUserRole("ROLE_CHARTDATE"));
		return userRoles;
	}

	private UserRole createUserRole(String roleName) {
		Role role = new RoleEntity();
		role.setName(roleName);
		UserRole userRole = new UserRoleEntity();
		userRole.setRole(role);
		return userRole;
	}

}
