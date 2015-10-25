package de.interseroh.report.services;

import de.interseroh.report.exception.BirtSystemException;
import de.interseroh.report.model.ReportReference;

import java.io.File;
import java.util.List;

/**
 * class to read filesystem.
 * Created by hhopf on 06.07.15.
 */
public interface BirtFileReaderService {

	List<ReportReference> getReportReferences()
			throws BirtSystemException;
}
