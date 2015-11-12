package de.interseroh.report.controller;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import de.interseroh.report.annotation.BeanMock;
import de.interseroh.report.auth.UserService;
import de.interseroh.report.services.SecurityService;

/**
 * Created by hhopf on 26.09.15.
 */
@Configuration
@BeanMock
public class SecurityServiceMock {

	@Bean
	@Primary
	public SecurityService registerSecurityControlMock() {
		return mock(SecurityService.class);
	}

    @Bean
    @Primary
    public UserService registerUserServiceMock() {
        return mock(UserService.class);
    }
}
