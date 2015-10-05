package de.interseroh.report.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;

/**
 *
 *
 * Created by hhopf on 07.07.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirtFileReaderServiceBeanTest {// todo autowire with problems
											// (securityhelper)

	@InjectMocks
	BirtFileReaderService serviceFileReader = new BirtFileReaderServiceBean();

	@Mock
	private SecurityService securityControl;

	@Test
	public void testGetAllFileNamesWithNull() throws BirtSystemException {

		List<ReportReference> list = serviceFileReader
				.getReportReferences(null);

		assertNull("null is null", list);
	}

	@Test
	public void testGetFileNameWithRole() throws BirtSystemException {

		File directory = new File(getClass().getResource("/reports").getFile());// target
																				// folder
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_SALESINVOICE");

		when(securityControl.getRoles()).thenReturn(roles);

		List<ReportReference> list = serviceFileReader
				.getReportReferences(directory);

		assertEquals("1 report in directory with its role available", 1,
				list.size());
	}

	@Test
	public void testGetTwoFilesNameWithRole() throws BirtSystemException {

		File directory = new File(getClass().getResource("/reports").getFile());// target
																				// folder
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_SALESINVOICE");
		roles.add("ROLE_PRODUCTCATALOG");

		when(securityControl.getRoles()).thenReturn(roles);

		List<ReportReference> list = serviceFileReader
				.getReportReferences(directory);

		assertEquals("2 report in directory with its role available", 2,
				list.size());
	}

	@Test
	public void testGetAllFileNamesWithException() throws BirtSystemException {

		File directory = new File("/onlyfortest");// target folder

		List<ReportReference> list = serviceFileReader
				.getReportReferences(directory);

		assertEquals("directory not available", 0, list.size());
	}
}