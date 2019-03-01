package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {

	public Subscription() {
	}

	@JsonProperty("ignored")
	private Boolean ignored;

	@JsonProperty("reason")
	private Boolean reason;

	@JsonProperty("subscribed")
	private Boolean subscribed;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("thread_url")
	private String threadUrl;

	@JsonProperty("url")
	private String url;

	public Boolean getIgnored() {
		return this.ignored;
	}

	public Boolean getReason() {
		return this.reason;
	}

	public Boolean getSubscribed() {
		return this.subscribed;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getThreadUrl() {
		return this.threadUrl;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return "Subscription [ " + "ignored = " + this.ignored + ", " + "reason = " + this.reason + ", "
				+ "subscribed = " + this.subscribed + ", " + "createdAt = " + this.createdAt + ", " + "threadUrl = "
				+ this.threadUrl + ", " + "url = " + this.url + ", " + "]";
	}
}
