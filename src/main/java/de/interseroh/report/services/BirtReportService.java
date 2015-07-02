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

	String REPORT_SOURCE_URL_KEY = "report.source.url";
	String REPORT_BASE_IMAGE_URL_KEY = "report.base.image.url";
	String REPORT_IMAGE_DIRECTORY_KEY = "report.image.directory";
	String REPORT_BASE_IMAGE_CONTEXT_PATH_KEY = "report.image.contextpath";
	String REPORT_FILE_SUFFIX = ".rptdesign";

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
