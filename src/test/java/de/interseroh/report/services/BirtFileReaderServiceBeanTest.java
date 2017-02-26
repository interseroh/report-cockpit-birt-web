package de.interseroh.report.services;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import de.interseroh.report.controller.SecurityServiceMock;
import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;
import de.interseroh.report.webconfig.ReportConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 *
 *
 * Created by hhopf on 07.07.15.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ReportConfig.class, SecurityServiceMock.class})
@PropertySource("classpath:config.properties")
public class BirtFileReaderServiceBeanTest {

	@InjectMocks
	BirtFileReaderService serviceFileReader = new BirtFileReaderServiceBean();

	@Mock
	private SecurityService securityControl;

	@Test
	public void testGetAllFileNamesWithNull() throws BirtSystemException {

		File directory = new File(getClass().getResource("/reports").getFile());

		when(securityControl.getTmpDirectory()).thenReturn(directory);

		List<ReportReference> list = serviceFileReader
				.getReportReferences();

		assertTrue("nichts drin", list.size() == 0);
	}

	@Test
	public void testGetFileNameWithRole() throws BirtSystemException {

		File directory = new File(getClass().getResource("/reports").getFile());// target
																				// folder

        when(securityControl.getTmpDirectory()).thenReturn(directory);
		when(securityControl.getStripRoleNames())
				.thenReturn(Collections.singletonList("SALESINVOICE"));

		List<ReportReference> list = serviceFileReader.getReportReferences();

		assertEquals("1 report in directory with its role available", 1,
				list.size());
	}

	@Test
	public void testGetTwoFilesNameWithRole() throws BirtSystemException {

		File directory = new File(getClass().getResource("/reports").getFile());//

		List<String> roles = Arrays.asList("SALESINVOICE","PRODUCTCATALOG");

		when(securityControl.getTmpDirectory()).thenReturn(directory);
		when(securityControl.getStripRoleNames()).thenReturn(roles);

		List<ReportReference> list = serviceFileReader
				.getReportReferences();

		assertEquals("2 report in directory with its role available", 2,
				list.size());
	}
}