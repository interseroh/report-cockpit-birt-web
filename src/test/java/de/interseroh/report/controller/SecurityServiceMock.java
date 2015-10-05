package de.interseroh.report.controller;

import de.interseroh.report.annotation.BeanMock;
import de.interseroh.report.auth.UserService;
import de.interseroh.report.services.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

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
