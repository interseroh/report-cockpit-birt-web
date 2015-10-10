package de.interseroh.report.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import de.interseroh.report.model.ReportReference;
import de.interseroh.report.services.BirtFileReaderService;

/**
 * get report references per default with no restriction. spring security is
 * responsible for this. Created by hhopf on 07.07.15.
 */
@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
@RequestMapping("/reports")
public class DefaultReportController {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultReportController.class);

	@Autowired
	BirtFileReaderService birtFileReaderService;

	@Autowired
	private Environment environment;

	@Autowired
	private ResourceLoader resourceLoader;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("initializing WebDataBinder");

		DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView allReportView(ModelAndView modelAndView) {

		logger.debug("executing allReportView for default");

		// TODO move this to service layer and replace the string with
		// BirtReportService.REPORT_SOURCE_URL_KEY
		String tmpDirectory = environment.getProperty("java.io.tmpdir");
		String location = environment.getProperty("report.source.url",
				tmpDirectory);
		Resource resource = resourceLoader.getResource(location);

		File directory = null;
		try {
			directory = resource.getFile().getAbsoluteFile();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		List<ReportReference> reportReferencesList = birtFileReaderService
				.getReportReferences(directory);

		modelAndView.setViewName("/allreports");
		modelAndView.addObject("reportReferencesList", reportReferencesList);

		return modelAndView;
	}

}
