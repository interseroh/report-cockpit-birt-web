package de.interseroh.report.services;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import de.interseroh.report.exception.BirtReportException;
import de.interseroh.report.model.Parameter;

/**
 * @author Ingo Düppe (Crowdcode)
 */
public interface BirtReportService {

	public static final String REPORT_SOURCE_URL_KEY = "report.source.url";
	public static final String REPORT_BASE_IMAGE_URL_KEY = "report.base.image.url";
	public static final String REPORT_IMAGE_DIRECTORY_KEY = "report.image.directory";
	public static final String REPORT_BASE_IMAGE_CONTEXT_PATH_KEY = "report.image.contextpath";
	public static final String REPORT_FILE_SUFFIX = ".rptdesign";

	public Collection<Parameter> getParameterDefinitions(String reportName)
			throws BirtReportException;

	public void renderHtmlReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException;

	public void renderPDFReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException;

	public void renderExcelReport(String reportName,
			Map<String, Object> parameters, OutputStream out)
			throws BirtReportException;
}
