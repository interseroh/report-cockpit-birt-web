package de.interseroh.report.model;

/**
 * class to encapsulate the reference of a report in frontend.
 * necessary?
 * Created by hhopf on 07.07.15.
 */
public class ReportReference {

	private String name;

	private String link;

	public ReportReference(String name, String link) {
		this.name = name;
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
