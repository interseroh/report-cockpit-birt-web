package de.interseroh.report.services;

import de.interseroh.report.webconfig.ReportConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportConfig.class)
@PropertySource("classpath:config.properties")
public class BirtReportServiceBeanTest {

    @Autowired
    private BirtReportServiceBean reportService;

    @Test
    public void testPrintParameterTests() throws Exception {
        reportService.getParameterGroups("custom");

    }
}