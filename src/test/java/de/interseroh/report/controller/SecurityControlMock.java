package de.interseroh.report.controller;

import de.interseroh.report.annotation.BeanMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * Created by hhopf on 26.09.15.
 */
@Configuration
@BeanMock
public class SecurityControlMock {

	@Bean
	@Primary
	public SecurityControl registerSecurityControlMock() {
		return mock(SecurityControl.class);
	}
}
