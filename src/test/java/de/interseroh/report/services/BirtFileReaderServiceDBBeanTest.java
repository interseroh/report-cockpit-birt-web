package de.interseroh.report.services;

import de.interseroh.report.auth.*;
import de.interseroh.report.model.ReportReference;
import de.interseroh.report.webconfig.DatabaseConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Scheitert daran, dass teilweise gemocked wird und daran, dass der Spring Context nicht geladen werden kann.
 * Problem ist das repository zum Speichern sowie zum auslesen der Entities.
 * Created by hhopf on 17.09.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@Rollback
public class BirtFileReaderServiceDBBeanTest {

	BirtFileReaderService serviceFileReader = new BirtFileReaderServiceBean();

	UserService userService = new UserServiceBean();

	@Mock
	private SecurityHelper securityHelper;// = new SecurityHelperBean();

	@Before
	public void init() throws Exception {
		//serviceFileReader = new BirtFileReaderServiceBean();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(serviceFileReader, "securityHelper",
				securityHelper, SecurityHelper.class);
		ReflectionTestUtils.setField(serviceFileReader, "userService",
				userService, UserService.class);
	}


	@Ignore
	@Transactional
	@Test
	public void testWithDB() {
		Role role = new RoleEntity();
		role.setName("ROLE_SALESINVOICE");
		User user = new UserEntity();
		String email = "birt@test.de";
		user.setEmail(email);

		when(securityHelper.getPrincipalName()).thenReturn(email);
		userService.createUserRole(user, role);

		File directory = new File(getClass().getResource("/reports").getFile());//target folder

		List<ReportReference> list =  serviceFileReader.getReportReferences(directory);

		assertEquals("1 report in directory with its role available", 1, list.size());

	}
}
