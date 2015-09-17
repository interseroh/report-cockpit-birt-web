package de.interseroh.report.services;

import de.interseroh.report.auth.UserRole;
import de.interseroh.report.auth.UserService;
import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * this class give back all reports for current user with correspond role.
 * Created by hhopf on 06.07.15.
 */
@Service("birtFileReaderServiceBean")
public class BirtFileReaderServiceBean implements BirtFileReaderService {

	public static final int SUFFIXCOUNT = 10;
	private Logger logger = Logger.getLogger(BirtFileReaderServiceBean.class.getName());

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityHelper securityHelper;

	/**
	 * deliver all file names (report names) in specified directory.
	 *
	 * @param directory file as directory
	 * @return names of all files in this directory (only reports)
	 */
	@Override
	public List<ReportReference> getReportReferences(final File directory) {
		logger.info(String.format("call to get role of current user in directory: %s",
				directory));
		if (directory == null) {
			return null;
		}

		List<ReportReference> reportReferences = new ArrayList<>();
		List<String> roles = getRoles();

		try {
			if (directory.exists() && directory.canRead() && directory
					.isDirectory()) {
				File[] files = directory.listFiles();
				if (files != null) {
					for (File file : files) {
						String fileName = file.getName()
								.substring(0, (file.getName().length() - SUFFIXCOUNT));
						for(String role : roles) {
							if(role.contains(fileName.toUpperCase())) {
								reportReferences.add(new ReportReference(fileName,
									"reports..."));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new BirtSystemException(String.format(
					"Error during to fill report list in directory: %s",
					directory), e.getCause());
		}

		return reportReferences;
	}

	private List<String> getRoles() {

		String userName = securityHelper.getPrincipalName();

		Collection<UserRole> rolesCollection = userService.
				findUserRolesByUserEmail(userName);

		List<String> roles = new ArrayList<>();
		for (UserRole role : rolesCollection) {
			roles.add(role.getRole().getName());
		}
		return roles;
	}

}
