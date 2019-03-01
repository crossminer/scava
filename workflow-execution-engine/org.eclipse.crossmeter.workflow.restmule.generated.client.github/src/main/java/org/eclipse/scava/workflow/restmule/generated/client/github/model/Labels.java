package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Labels {

	public Labels() {
	}

	@JsonProperty("color")
	private String color;

	@JsonProperty("name")
	private String name;

	@JsonProperty("url")
	private String url;

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return "Labels [ " + "color = " + this.color + ", " + "name = " + this.name + ", " + "url = " + this.url + ", "
				+ "]";
	}
}
