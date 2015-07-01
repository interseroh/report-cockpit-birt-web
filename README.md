# Report Cockpit for BIRT Reports

This projects provides an easy to integrate and enhanced BIRT Web-Viewer based on Spring WebMVC, Thymeleaf and Bootstrap. The Projects consist of two parts. First, is the REST-API to render reports. Second, it provides an modern Front-End based on Bootstrap and JQuery to be easily customized.

## Documentation

- IN PROGRESS!!!

### Configuration

- For configuration see `report-config.properties` and `BirtReportService.java`.

```
report.base.image.url=/reportimages
report.image.directory=/${java.io.tmpdir}/reportimages/
report.image.contextpath=/report-cockpit-birt-web
report.source.url=classpath:/reports/
```

### Rest-API

- Render Report with GET-Method: `/report-cockpit-birt/api/render/{reportName}/{format}`
- For Instance:
 - for html: [http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/html](http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/html)
 - for pdf:
 [http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/pdf](http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/pdf)
 - for xls:
 [http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/xlsx](http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/xlsx)
 - for xlsx:
 [http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/xlsx](http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/xlsx)


- JSON to customize Reports
```json
{
    "reportName": <REPORT_NAME>,
    "format": <"html" | "pdf" | "xls" | "xlsx">,
    "parameters": [{<key>:<value>}...]
};
```

## Build Status

Our current build status at BuildHive CloudBees: [![Build Status](https://buildhive.cloudbees.com/job/interseroh/job/report-cockpit-birt-web/badge/icon)](https://buildhive.cloudbees.com/job/interseroh/job/report-cockpit-birt-web/)
