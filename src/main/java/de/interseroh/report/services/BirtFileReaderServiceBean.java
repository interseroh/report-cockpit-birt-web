package de.interseroh.report.services;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.helper.SecurityHelper;
import de.interseroh.report.model.ReportReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * this class give back all reports for current user with correspond role.
 * Created by hhopf on 06.07.15.
 */
@Service("birtFileReaderServiceBean")
public class BirtFileReaderServiceBean implements BirtFileReaderService {

	private Logger logger = Logger
			.getLogger(BirtFileReaderServiceBean.class.getName());

	//@Autowired
	//private SecurityHelper securityHelper;

	/**
	 * deliver all file names (report names) in specified directory.
	 *
	 * @param directory file as directory
	 * @return names of all files in this directory (only reports)
	 */
	@Override
	public List<ReportReference> getReportReferences(
			final File directory) {
		logger.info(String.format(
				"call to get role of current user in directory: %s",
				directory));
		if (directory == null) {
			return null;
		}
		//final List<String> roles = securityHelper.getRoles();

		List<ReportReference> reportReferences = new ArrayList<>();

		try {
			if (directory.exists() && directory.canRead() && directory
					.isDirectory()) {
				File[] files = directory.listFiles();
				if (files != null) {
					for (File file : files) {
						String fileName = file.getName()
								.substring(0, (file.getName().length() - 10));
						//for(String role : roles) {
						//if(role.contains(fileName.toUpperCase())) {//todo roles not implemented yet
						reportReferences.add(new ReportReference(fileName,
								"reports..."));
						//}
						//}
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
