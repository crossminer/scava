package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

	public Issue() {
	}

	@JsonProperty("milestone")
	private Float milestone;

	@JsonProperty("assignee")
	private String assignee;

	@JsonProperty("body")
	private String body;

	@JsonProperty("title")
	private String title;

	@JsonProperty("labels")
	private List<String> labels = new ArrayList<String>();

	public Float getMilestone() {
		return this.milestone;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public String getBody() {
		return this.body;
	}

	public String getTitle() {
		return this.title;
	}

	public List<String> getLabels() {
		return this.labels;
	}

	@Override
	public String toString() {
		return "Issue [ " + "milestone = " + this.milestone + ", " + "assignee = " + this.assignee + ", " + "body = "
				+ this.body + ", " + "title = " + this.title + ", " + "labels = " + this.labels + ", " + "]";
	}
}
