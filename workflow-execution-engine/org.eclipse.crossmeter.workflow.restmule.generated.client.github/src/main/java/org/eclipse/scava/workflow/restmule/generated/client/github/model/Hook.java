package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hook {

	public Hook() {
	}

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("name")
	private String name;

	@JsonProperty("active")
	private Boolean active;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("config")
	private Config config;

	@JsonProperty("events")
	private List<String> events = new ArrayList<String>();

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getActive() {
		return this.active;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public Config getConfig() {
		return this.config;
	}

	public List<String> getEvents() {
		return this.events;
	}

	@Override
	public String toString() {
		return "Hook [ " + "updatedAt = " + this.updatedAt + ", " + "name = " + this.name + ", " + "active = "
				+ this.active + ", " + "createdAt = " + this.createdAt + ", " + "id = " + this.id + ", " + "url = "
				+ this.url + ", " + "config = " + this.config + ", " + "events = " + this.events + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Config {

		public Config() {
		}

		@JsonProperty("content_type")
		private String contentType;

		@JsonProperty("url")
		private String url;

		public String getContentType() {
			return this.contentType;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Config [ " + "contentType = " + this.contentType + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
