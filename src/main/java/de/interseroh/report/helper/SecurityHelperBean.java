package de.interseroh.report.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * helper class to get athentications of current user.
 * Created by hhopf on 07.07.15.
 */
@Service("securityHelperBean") public class SecurityHelperBean
		implements SecurityHelper {

	/**
	 * return role of current user.
	 *
	 * @return role of current user
	 */
	@Override public List<String> getRoles() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth
				.getAuthorities();
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority grant : authorities) {
			if (grant.getAuthority().contains("ROLE")) {
				roles.add(grant.getAuthority());
			}
		}
		return roles;
	}
}
