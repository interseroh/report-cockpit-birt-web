package de.interseroh.report.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.interseroh.report.auth.Role;
import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserService;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTest {

    @InjectMocks
    private SecurityService securityService;

    @Mock
    private SecurityHelper securityHelper;

    @Mock
    private UserService userService;

    @Mock
    private UserRole userRole;

    @Mock
    private Role role;

    @Test
    public void testStrippingRoleNames() throws Exception {

        when(userRole.getRole()).thenReturn(role);
        when(role.getName()).thenReturn("ROLE_REPORT1");
        when(userService.findUserRolesByUserEmail(anyString())).thenReturn(Arrays.asList(userRole));

        List<String> stripRoleNames = securityService.getStripRoleNames();


        assertThat(stripRoleNames, hasItems("REPORT1"));
    }


}