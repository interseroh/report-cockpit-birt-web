# Report Cockpit for BIRT Reports

This projects provides an easy to integrate and enhanced BIRT Web-Viewer based on Spring WebMVC, Thymeleaf and Bootstrap. The Projects consist of two parts. First, is the REST-API to render reports. Second, it provides an modern Front-End based on Bootstrap and JQuery to be easily customized.

## Documentation

- TBD

### Configuration

- TBD

### Rest-API

- GET method to render a Report

`../report-cockpit-birt/api/render/report?name={reportName}&format={format}`

- JSon to customize Reports
```json
{
    "reportName": <REPORT_NAME>,
    "format": <"html" | "pdf" | "xls" | "xlsx">,
    "parameters": [{<key>:<value>}...]
};
```

## Build Status

Our current build status at BuildHive CloudBees: [![Build Status](https://buildhive.cloudbees.com/job/interseroh/job/report-cockpit-birt-web/badge/icon)](https://buildhive.cloudbees.com/job/interseroh/job/report-cockpit-birt-web/)
