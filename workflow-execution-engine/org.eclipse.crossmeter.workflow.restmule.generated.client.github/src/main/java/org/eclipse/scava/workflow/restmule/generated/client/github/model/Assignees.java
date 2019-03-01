package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Assignees {

	public Assignees() {
	}

	@JsonProperty("avatar_url")
	private Integer avatarUrl;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("login")
	private String login;

	@JsonProperty("gravatar_id")
	private String gravatarId;

	@JsonProperty("url")
	private String url;

	public Integer getAvatarUrl() {
		return this.avatarUrl;
	}

	public Integer getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public String getGravatarId() {
		return this.gravatarId;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return "Assignees [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
				+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
	}
}
