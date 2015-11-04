package de.interseroh.report.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;

/**
 * this class give back all reports for current user with correspond role.
 * Created by hhopf on 06.07.15.
 */
@Service
public class BirtFileReaderServiceBean implements BirtFileReaderService {

	public static final int SUFFIXCOUNT = 10;

	private static final Logger logger = LoggerFactory
			.getLogger(BirtFileReaderServiceBean.class);

	@Autowired
	private SecurityService securityControl;

	/**
	 * deliver all file names (report names) in specified directory.
	 *
	 * @return names of all files in this directory (only reports)
	 */
	@Override
	public List<ReportReference> getReportReferences() {

		final File directory = securityControl.getTmpDirectory();
		logger.debug("call to get role of current user in directory {}",
				directory);

		if (directory == null) {
			return null;
		}

		List<ReportReference> reportReferences = new ArrayList<>();
		List<String> roles = securityControl.getRoles();

		try {
			if (directory.exists() && directory.canRead()
					&& directory.isDirectory()) {
				File[] files = directory.listFiles();
				String splitRoleName;
				if (files != null) {
					for (File file : files) {
						String fileName = file.getName().substring(0,
								(file.getName().length() - SUFFIXCOUNT));
						for (String role : roles) {
							splitRoleName = role.substring(5);
							if (splitRoleName.equalsIgnoreCase(fileName)) {
								reportReferences.add(new ReportReference(
										fileName, "reports..."));
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

}
