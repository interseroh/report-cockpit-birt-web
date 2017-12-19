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
package de.interseroh.report.services;

import java.util.Collections;
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
        when(userService.findUserRolesByUserEmail(anyString()))
                .thenReturn(Collections.singletonList(userRole));

        List<String> stripRoleNames = securityService.getStripRoleNames();


        assertThat(stripRoleNames, hasItems("REPORT1"));
    }


}