package de.interseroh.report.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * currently without roles!
 *
 * Created by hhopf on 07.07.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirtFileReaderServiceBeanTest {//todo autowire with problems (securityhelper)

    @InjectMocks
    BirtFileReaderService serviceFileReader = new BirtFileReaderServiceBean();



    @Test
    public void testGetAllFileNamesWithNull() throws BirtSystemException {

        List<ReportReference>list =  serviceFileReader.getReportReferences(null);

        assertNull("null is null", list);
    }

    @Test
    public void testGetAllFileNames() throws BirtSystemException {

        File directory = new File(getClass().getResource("/reports").getFile());//target folder
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_SALESINVOICE");

        //when(securityHelper.getRoles()).thenReturn(roles); in the future :-)

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("8 reports in directory available", 8, list.size());
        assertEquals("hello world is in list on position 4", "hello_world",
                list.get(3).getName());
    }

/*    @Ignore
    @Test
    public void testGetAllFileNamesWithRoles() throws BirtSystemException {

        File directory = new File(getClass().getResource("/reports").getFile());//target folder
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_SALESINVOICE");

        when(securityHelper.getRoles()).thenReturn(roles); //in the future :-)

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("1 reports in directory available with rolename", 1, list.size());
    }*/

    @Test
    public void testGetAllFileNamesWithException() throws BirtSystemException {

        File directory = new File("/onlyfortest");//target folder

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("directory not available", 0, list.size());
    }

    @Test
    public void testGetAllFileNamesWithFilename() throws BirtSystemException {

        File directory = new File(getClass().getResource("/reports/hello_world.rptdesign").getFile());//target folder
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_SALESINVOICE");

        //when(securityHelper.getRoles()).thenReturn(roles); in the future :-)

        List<ReportReference>list =  serviceFileReader.getReportReferences(directory);

        assertEquals("file is not a directory", 0, list.size());
    }
}