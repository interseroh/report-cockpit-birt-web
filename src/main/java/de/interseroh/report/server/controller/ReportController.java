/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.server.controller;

import de.interseroh.report.server.birt.BirtOutputFormat;
import de.interseroh.report.server.birt.BirtReportException;
import de.interseroh.report.server.birt.BirtReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Ingo Düppe (Crowdcode)
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = Logger.getLogger(ReportController.class);

    @Autowired
    private BirtReportService reportService;

    @RequestMapping(value = "/api/{reportName}", method = RequestMethod.GET)
    public void renderReportInDefaultFormat(@PathVariable("reportName") String reportName, @PathVariable("format") String format, HttpServletResponse response) throws IOException, BirtReportException {
        renderReport(reportName, BirtOutputFormat.HTML5.getFormatName(), response);
    }

    @RequestMapping(value = "/api/{reportName}/{format}", method = RequestMethod.GET)
    public void renderReport(@PathVariable("reportName") String reportName, @PathVariable("format") String format, HttpServletResponse response) throws IOException, BirtReportException {
        logger.debug("Rendering " + reportName + " in " + format + ".");
        BirtOutputFormat outputFormat = BirtOutputFormat.from(format);
        response.setContentType(outputFormat.getContentType());

        // TODO idueppe - need configurable folder
        String reportFileName = "/reports/" + reportName + ".rptdesign";

        HashMap<String, Object> parameters = new HashMap<String, Object>();

        switch (outputFormat) {
            case HTML5:
                reportService.renderHtmlReport(reportFileName, parameters, response.getOutputStream());
                break;
            case PDF:
                response.setHeader("Content-disposition", "inline; filename=" + reportName + ".pdf");
                reportService.renderPDFReport(reportFileName, parameters, response.getOutputStream());
                break;
            case EXCEL2010:
                response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".xlsx");
                reportService.renderExcelReport(reportFileName, parameters, response.getOutputStream());
            case EXCEL:
                response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".xls");
                reportService.renderExcelReport(reportFileName, parameters, response.getOutputStream());
        }
        // TODO idueppe - need exception handling
    }

    @RequestMapping(value = "/{reportName}", method = RequestMethod.GET)
    public ModelAndView reportView(@PathVariable("reportName") String reportName, HttpServletRequest request, HttpServletResponse reponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/secured/report");
        modelAndView.addObject("reportUrl", "/api/" + reportName);
        return modelAndView;
    }
}
