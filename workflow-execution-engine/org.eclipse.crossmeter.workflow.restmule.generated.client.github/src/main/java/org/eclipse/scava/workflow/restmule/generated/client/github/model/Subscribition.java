package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscribition {

	public Subscribition() {
	}

	@JsonProperty("ignored")
	private Boolean ignored;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("subscribed")
	private Boolean subscribed;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("repository_url")
	private String repositoryUrl;

	@JsonProperty("url")
	private String url;

	public Boolean getIgnored() {
		return this.ignored;
	}

	public String getReason() {
		return this.reason;
	}

	public Boolean getSubscribed() {
		return this.subscribed;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getRepositoryUrl() {
		return this.repositoryUrl;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return "Subscribition [ " + "ignored = " + this.ignored + ", " + "reason = " + this.reason + ", "
				+ "subscribed = " + this.subscribed + ", " + "createdAt = " + this.createdAt + ", " + "repositoryUrl = "
				+ this.repositoryUrl + ", " + "url = " + this.url + ", " + "]";
	}
}
