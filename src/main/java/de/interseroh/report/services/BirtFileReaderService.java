package de.interseroh.report.services;

import java.util.List;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;

/**
 * class to read filesystem. Created by hhopf on 06.07.15.
 */
public interface BirtFileReaderService {

	List<ReportReference> getReportReferences() throws BirtSystemException;
}
