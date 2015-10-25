package de.interseroh.report.services;

import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hhopf on 26.09.15.
 */
@Component
public class SecurityService {

	private static final Logger logger = LoggerFactory
			.getLogger(BirtFileReaderServiceBean.class);

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

		Collection<UserRole> rolesCollection = userService.
				findUserRolesByUserEmail(userName);

		List<String> roles = new ArrayList<>();
		for (UserRole role : rolesCollection) {
			roles.add(role.getRole().getName());
		}
		roles.add("ROLE_SALESINVOICE");
		roles.add("ROLE_PRODUCTCATALOG");
		roles.add("ROLE_MULTISELECT");
		return roles;
	}

	public boolean hasUserValidRole(String reportName) {
		List<String> roles = getRoles();
		reportName = reportName.toUpperCase();

		for(String role : roles) {
			if(role.contains(reportName)) {
				return true;
			}
		}
		return false;
	}

	public File getTmpDirectory() {

		String tmpDirectory = environment.getProperty("java.io.tmpdir");
		String location = environment.getProperty(BirtReportService.REPORT_SOURCE_URL_KEY,
				tmpDirectory);
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
