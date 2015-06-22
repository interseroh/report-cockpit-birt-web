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
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.webconfig;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.inject.Inject;

@PropertySource("classpath:config.properties")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String MANAGER_PASSWORD = "xxx";

    private static final String MANAGER_DN = "xxx";

    private static final String LDAP_URL = "ldap://ldap.xxx:389/OU=xxx";

    private static final String IN_MEMORY_USER = "birt";

    private static final String IN_MEMORY_PASSWORD = "birt";

    private static final String USER_SEARCH_FILTER = "(&(objectCategory=Person)(sAMAccountName={0}))";

    private static final Logger logger = Logger.getLogger(SecurityConfig.class);

    @Inject
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/", "/index", "/images/**")
                .permitAll()
                    .anyRequest().authenticated().and()
                .formLogin()
                    .loginPage("/login").failureUrl("/login?error")
                .defaultSuccessUrl("/index").permitAll().and().logout()
                .logoutSuccessUrl("/logout").permitAll();
        http.csrf().disable();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        logger.debug("configureGlobal: mapping login to LDAP");

        // LDAP or InMemory
        String ldapAuth = prepareLdapAuth();
        if (!ldapAuth.equalsIgnoreCase("true")) {
            // In memory authentication
            String inMemoryUser = prepareInMemoryUser();
            String inMemoryPassword = prepareInMemoryPassword();

            auth.inMemoryAuthentication().withUser(inMemoryUser)
                    .password(inMemoryPassword).roles("USER");
        } else {
            // LDAP authentication
            String ldapUrl = prepareLdapUrl();
            String managerDn = prepareManagerDn();
            String managerPassword = prepareManagerPassword();

            auth.ldapAuthentication().userSearchFilter(USER_SEARCH_FILTER)
                    .contextSource().managerDn(managerDn)
                    .managerPassword(managerPassword).url(ldapUrl);
        }
    }

    String prepareLdapAuth() {
        String ldapAuth = "true";
        String confLdapAuth = env.getProperty("ldap.authentication");

        if (confLdapAuth != null && !confLdapAuth.equals("")) {
            ldapAuth = confLdapAuth;
        }

        if (!ldapAuth.equalsIgnoreCase("true")
                && !ldapAuth.equalsIgnoreCase("false")) {
            ldapAuth = "true";
        }

        return ldapAuth;
    }

    String prepareManagerPassword() {
        String managerPassword = MANAGER_PASSWORD;
        String confManagerPassword = env.getProperty("ldap.managerPassword");
        if (confManagerPassword != null && !confManagerPassword.equals("")) {
            managerPassword = confManagerPassword;
        }
        return managerPassword;
    }

    String prepareManagerDn() {
        String managerDn = MANAGER_DN;
        String confManagerDn = env.getProperty("ldap.managerDn");
        if (confManagerDn != null && !confManagerDn.equals("")) {
            managerDn = confManagerDn;
        }
        return managerDn;
    }

    String prepareLdapUrl() {
        String ldapUrl = LDAP_URL;
        String confLdapUrl = env.getProperty("ldap.url");
        if (confLdapUrl != null && !confLdapUrl.equals("")) {
            ldapUrl = confLdapUrl;
        }
        return ldapUrl;
    }

    String prepareInMemoryPassword() {
        String inMemoryPassword = IN_MEMORY_PASSWORD;
        String confInMemoryPassword = env.getProperty("ldap.inmemory.password");
        if (confInMemoryPassword != null && !confInMemoryPassword.equals("")) {
            inMemoryPassword = confInMemoryPassword;
        }
        return inMemoryPassword;
    }

    String prepareInMemoryUser() {
        String inMemoryUser = IN_MEMORY_USER;
        String confInMemoryUser = env.getProperty("ldap.inmemory.user");
        if (confInMemoryUser != null && !confInMemoryUser.equals("")) {
            inMemoryUser = confInMemoryUser;
        }
        return inMemoryUser;
    }
}
