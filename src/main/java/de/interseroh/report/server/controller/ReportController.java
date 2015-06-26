package de.interseroh.report.server.controller;

import com.ibm.wsdl.extensions.mime.MIMEContentImpl;
import de.interseroh.report.server.birt.BirtReportService;
import org.eclipse.birt.report.engine.api.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.wsdl.extensions.mime.MIMEContent;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private BirtReportService reportService;

    @RequestMapping(value="/api/{reportName}", method = RequestMethod.GET)
    public void generateReport(@PathVariable("reportName") String reportName, HttpServletRequest request, HttpServletResponse response) throws IOException, EngineException {
        response.setContentType("text/html; charset=UTF-8");
        String reportFileName = "/reports/" + reportName + ".rptdesign";
        reportService.renderHtmlReport(reportFileName, new HashMap<String, Object>(), response.getOutputStream());
    }

    @RequestMapping(value="/{reportName}", method = RequestMethod.GET)
    public ModelAndView reportView(@PathVariable("reportName") String reportName, HttpServletRequest request, HttpServletResponse reponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/secured/report");
        modelAndView.addObject("reportUrl", "/api/"+reportName);
        return modelAndView;
    }
}
