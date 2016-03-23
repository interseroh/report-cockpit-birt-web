package de.interseroh.report.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserService;

/**
 * Created by hhopf on 26.09.15.
 */
@Component
public class SecurityService {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtFileReaderServiceBean.class);

    private static final String ROLE_PREFIX = "ROLE_";

    private static final int ROLE_PREFIX_LENGTH = ROLE_PREFIX.length();

    @Autowired
	private SecurityHelper securityHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;

	@Autowired
	private ResourceLoader resourceLoader;

	/**
	 * roles from the logged User.
	 *
	 * @return all roles from user
	 */
	public List<String> getRoles() {
		String userName = securityHelper.getPrincipalName();

		Collection<UserRole> rolesCollection = userService
				.findUserRolesByUserEmail(userName);

		List<String> roles = new ArrayList<>();
		for (UserRole role : rolesCollection) {
			roles.add(role.getRole().getName());
		}

		return roles;
	}


    public List<String> getStripRoleNames() {
        List<String> roles = getRoles();
        List<String> stripRoleNames = new ArrayList<>(roles.size());
        for (String role : roles) {
            stripRoleNames.add(stripRolePrefix(role));
        }
        return roles;
    }

	public boolean hasUserValidRole(String reportName) {
		List<String> roles = getRoles();

        for (String role : roles) {
            if (stripRolePrefix(role).equalsIgnoreCase(reportName)) {
				return true;
			}
		}

		return false;
	}

    private String stripRolePrefix(String role) {
        if (role.startsWith(ROLE_PREFIX)) {
            return role.substring(ROLE_PREFIX_LENGTH);
        } else {
            return role;
        }
    }

    // TODO idueppe - separation of concerns violation. the method is not used here.
	public File getTmpDirectory() {
		String tmpDirectory = environment.getProperty("java.io.tmpdir");
		String location = environment.getProperty(
				BirtReportService.REPORT_SOURCE_URL_KEY, tmpDirectory);
		Resource resource = resourceLoader.getResource(location);

		File directory = null;
		try {
			directory = resource.getFile().getAbsoluteFile();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return directory;
	}
}
