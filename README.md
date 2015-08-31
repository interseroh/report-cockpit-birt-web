# Report Cockpit for BIRT Reports

This projects provides an easy to integrate and enhanced BIRT Web-Viewer based on Spring WebMVC, Thymeleaf and Bootstrap. The Projects consist of two parts. First, is the REST-API to render reports. Second, it provides an modern Front-End based on Bootstrap and JQuery to be easily customized.

## Documentation



### Configuration

For security and branding configuration see `[src/main/resources/config.properties](https://github.com/interseroh/report-cockpit-birt-web/blob/master/src/main/resources/config.properties_example)`. Be aware that the file will generated on the first `mvn initialize`.

Per default inmemory authentication is activated.
`ldap.authentication=false`
The default username and passwords are:
`ldap.inmemory.user=birt`
`ldap.inmemory.password=birt`

For report engine specific configuration see `report-config.properties` and `BirtReportService.java`.

```
report.base.image.url=/reportimages
report.image.directory=/${java.io.tmpdir}/reportimages/
report.image.contextpath=/report-cockpit-birt-web
report.source.url=classpath:/reports/
```

### Authorisation and the Security Domain Model

*This is currently under development!*

The simple authorization mechanism is based on a match between a user, a role and a report. Each user that wants to access a report should be included in a specific role which in turn has an access to that given report. For instance, to open a report with the name `multiselect` the user `birt` must be a member of the role `role_company` and this specific role has access to `multiselect` report. 

For details see the uml class diagram.
![Domain Model](https://github.com/interseroh/report-cockpit-birt-web/blob/master/src/main/resources/model/report-cockpit-birt.jpg)

To generate the source code from the model we use [NoMagic MagicDraw](http://www.nomagic.com/products/magicdraw.html) and [KissMDA](http://www.kissmda.org).

### RESTful-API

- Render Report with GET-Method: `/report-cockpit-birt-web/api/render/{reportName}/{format}`
- For Instance:
 - for html: [http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/html](http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/html)
 - for pdf:
 [http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/pdf](http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/pdf)
 - for xls:
 [http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/xlsx](http://localhost:8080/report-cockpit-birt/api/render/salesinvoice/xlsx)
 - for xlsx:
 [http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/xlsx](http://localhost:8080/report-cockpit-birt-web/api/render/salesinvoice/xlsx)


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
