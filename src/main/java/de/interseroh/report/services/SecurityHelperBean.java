package de.interseroh.report.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by hhopf on 16.09.15.
 */
@Service("securityHelperBean")
public class SecurityHelperBean implements SecurityHelper {

	public String getPrincipalName() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return auth.getName();

	}
}
