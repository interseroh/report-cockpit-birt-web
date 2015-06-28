package de.interseroh.report.server.birt;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

@Service
public class BirtReportService {

    @Autowired
    private IReportEngine reportEngine;

    public Collection<IParameterDefn> getParameterDefinitions(String reportName) throws EngineException {
        IReportRunnable iReportRunnable = reportEngine.openReportDesign(absolutePathOf(reportName));
        IGetParameterDefinitionTask parameterDefinitionTask = reportEngine.createGetParameterDefinitionTask(iReportRunnable);

        boolean includeParameterGroups = true;
        Collection<IParameterDefn> parameterDefns = parameterDefinitionTask.getParameterDefns(includeParameterGroups);
        printParameterDefinitions(parameterDefns, parameterDefinitionTask);

        return parameterDefns;
    }

    public void renderHtmlReport(String reportName, Map<String, Object> parameters, OutputStream out) throws EngineException, FileNotFoundException {
        IReportRunnable iReportRunnable = reportEngine.openReportDesign(absolutePathOf(reportName));

        IRunAndRenderTask runAndRenderTask = reportEngine.createRunAndRenderTask(iReportRunnable);

        injectParameters(parameters, runAndRenderTask);

        IRenderOption options = new RenderOption();

        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
        htmlOptions.setOutputStream(out);
        htmlOptions.setImageHandler(new HTMLServerImageHandler());
        htmlOptions.setBaseImageURL("images");
        htmlOptions.setImageDirectory("target/images");
        runAndRenderTask.setRenderOption(htmlOptions);
        runAndRenderTask.run();
        runAndRenderTask.close();
    }


    public void renderPDFReport(String reportName, Map<String, Object> parameters, OutputStream out) throws BirtReportException {
        try {
            IReportRunnable iReportRunnable = reportEngine.openReportDesign(absolutePathOf(reportName));
            IRunAndRenderTask runAndRenderTask = reportEngine.createRunAndRenderTask(iReportRunnable);

            injectParameters(parameters, runAndRenderTask);

            IRenderOption options = new RenderOption();
            PDFRenderOption pdfOptions = new PDFRenderOption(options);
            pdfOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
            pdfOptions.setEmbededFont(true); // TODO extract embedded font option to configuration
            pdfOptions.setOutputStream(out);
            pdfOptions.setImageHandler(new HTMLServerImageHandler());

            runAndRenderTask.setRenderOption(pdfOptions);
            runAndRenderTask.run();
            runAndRenderTask.close();

        } catch (EngineException e) {
            throw new BirtReportException("Error while rendering pdf for Report "+ reportName, e);
        }

    }

    private void injectParameters(Map<String, Object> parameters, IEngineTask runAndRenderTask) {
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            runAndRenderTask.setParameterValue(parameter.getKey(), parameter.getValue());
        }
    }

    private String absolutePathOf(String reportName) {
        return Thread.currentThread().getContextClassLoader().getResource(reportName).getPath();
//        return this.getClass().getResource(reportName).getPath();
    }

    private void printParameterDefinitions(Collection<IParameterDefn> parameterDefinitions, IGetParameterDefinitionTask task) {
        for (IParameterDefn parameterDefn : parameterDefinitions) {
            System.out.println("Displayname: " + parameterDefn.getDisplayName());
            System.out.println("Helptext: " + parameterDefn.getHelpText());
            System.out.println("Name: " + parameterDefn.getName());
            System.out.println("Typename: " + parameterDefn.getTypeName());
            System.out.println("ParameterType: " + BirtParameterType.valueOf(parameterDefn.getParameterType()));
            System.out.println("DataType: " + BirtDataType.valueOf(parameterDefn.getDataType()));
            System.out.println("PromptText: " + parameterDefn.getPromptText());
            System.out.println("DefaultValue: " + task.getDefaultValue(parameterDefn));
            System.out.println("Required: " + parameterDefn.isRequired());

        }

    }

}