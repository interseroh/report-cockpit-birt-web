package de.interseroh.report.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import de.interseroh.report.auth.*;
import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 *
 * Created by hhopf on 07.07.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirtFileReaderServiceBeanTest {//todo autowire with problems (securityhelper)

    @InjectMocks
    BirtFileReaderService serviceFileReader = new BirtFileReaderServiceBean();

    @Mock
    UserService userService;

    @Mock
    private SecurityHelper securityHelper;


    @Test
    public void testGetAllFileNamesWithNull() throws BirtSystemException {

        List<ReportReference>list =  serviceFileReader.getReportReferences(null);

        assertNull("null is null", list);
    }

    @Test
    public void testGetFileNameWithRole() throws BirtSystemException {

        File directory = new File(getClass().getResource("/reports").getFile());//target folder
        UserRole userRole = getUserRole();
        Collection<UserRole>  roles = new ArrayList<>();
        roles.add(userRole);

        when(securityHelper.getPrincipalName()).thenReturn("userName");

        when(userService.findUserRolesByUserEmail(eq("userName"))).thenReturn(roles);

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("1 report in directory with its role available", 1,
                list.size());
    }

    @Test
    public void testGetTwoFilesNameWithRole() throws BirtSystemException {

        File directory = new File(getClass().getResource("/reports").getFile());//target folder
        Collection<UserRole>  roles = getUserRoles();

        when(securityHelper.getPrincipalName()).thenReturn("userName");

        when(userService.findUserRolesByUserEmail(eq("userName"))).thenReturn(roles);

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("2 report in directory with its role available", 2, list.size());
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


    @Test
    public void testGetAllFileNamesWithException() throws BirtSystemException {

        File directory = new File("/onlyfortest");//target folder

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("directory not available", 0, list.size());
    }
}