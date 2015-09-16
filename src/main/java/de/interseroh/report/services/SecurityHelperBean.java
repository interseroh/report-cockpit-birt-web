package de.interseroh.report.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by hhopf on 16.09.15.
 */
public class SecurityHelperBean implements SecurityHelper {

	public String getPrincipalName() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return auth.getName();

	}
}
