package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUsers {

	public SearchUsers() {
	}

	@JsonProperty("repos_url")
	private String reposUrl;

	@JsonProperty("followers_url")
	private String followersUrl;

	@JsonProperty("login")
	private String login;

	@JsonProperty("type")
	private String type;

	@JsonProperty("url")
	private String url;

	@JsonProperty("score")
	private Float score;

	@JsonProperty("subscriptions_url")
	private String subscriptionsUrl;

	@JsonProperty("received_events_url")
	private String receivedEventsUrl;

	@JsonProperty("avatar_url")
	private String avatarUrl;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("gravatar_id")
	private String gravatarId;

	@JsonProperty("organizations_url")
	private String organizationsUrl;

	public String getReposUrl() {
		return this.reposUrl;
	}

	public String getFollowersUrl() {
		return this.followersUrl;
	}

	public String getLogin() {
		return this.login;
	}

	public String getType() {
		return this.type;
	}

	public String getUrl() {
		return this.url;
	}

	public Float getScore() {
		return this.score;
	}

	public String getSubscriptionsUrl() {
		return this.subscriptionsUrl;
	}

	public String getReceivedEventsUrl() {
		return this.receivedEventsUrl;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public Integer getId() {
		return this.id;
	}

	public String getGravatarId() {
		return this.gravatarId;
	}

	public String getOrganizationsUrl() {
		return this.organizationsUrl;
	}

	@Override
	public String toString() {
		return "SearchUsers [ " + "reposUrl = " + this.reposUrl + ", " + "followersUrl = " + this.followersUrl + ", "
				+ "login = " + this.login + ", " + "type = " + this.type + ", " + "url = " + this.url + ", "
				+ "score = " + this.score + ", " + "subscriptionsUrl = " + this.subscriptionsUrl + ", "
				+ "receivedEventsUrl = " + this.receivedEventsUrl + ", " + "avatarUrl = " + this.avatarUrl + ", "
				+ "htmlUrl = " + this.htmlUrl + ", " + "id = " + this.id + ", " + "gravatarId = " + this.gravatarId
				+ ", " + "organizationsUrl = " + this.organizationsUrl + ", " + "]";
	}
}
