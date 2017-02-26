package de.interseroh.report.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by hhopf on 16.09.15.
 */
@Service
public class SecurityHelperBean implements SecurityHelper {

	@Override
	public String getPrincipalName() {

		return SecurityContextHolder.getContext().getAuthentication().getName();

	}
}
