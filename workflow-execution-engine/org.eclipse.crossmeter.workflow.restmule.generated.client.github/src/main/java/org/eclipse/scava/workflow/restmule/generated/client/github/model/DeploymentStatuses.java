package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeploymentStatuses {

	public DeploymentStatuses() {
	}

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("payload")
	private String payload;

	@JsonProperty("target_url")
	private String targetUrl;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("state")
	private String state;

	@JsonProperty("url")
	private String url;

	@JsonProperty("creator")
	private Creator creator;

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getPayload() {
		return this.payload;
	}

	public String getTargetUrl() {
		return this.targetUrl;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getState() {
		return this.state;
	}

	public String getUrl() {
		return this.url;
	}

	public Creator getCreator() {
		return this.creator;
	}

	@Override
	public String toString() {
		return "DeploymentStatuses [ " + "updatedAt = " + this.updatedAt + ", " + "payload = " + this.payload + ", "
				+ "targetUrl = " + this.targetUrl + ", " + "createdAt = " + this.createdAt + ", " + "description = "
				+ this.description + ", " + "id = " + this.id + ", " + "state = " + this.state + ", " + "url = "
				+ this.url + ", " + "creator = " + this.creator + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Creator {

		public Creator() {
		}

		@JsonProperty("gists_url")
		private String gistsUrl;

		@JsonProperty("repos_url")
		private String reposUrl;

		@JsonProperty("following_url")
		private String followingUrl;

		@JsonProperty("starred_url")
		private String starredUrl;

		@JsonProperty("followers_url")
		private String followersUrl;

		@JsonProperty("login")
		private String login;

		@JsonProperty("type")
		private String type;

		@JsonProperty("url")
		private String url;

		@JsonProperty("subscriptions_url")
		private String subscriptionsUrl;

		@JsonProperty("received_events_url")
		private String receivedEventsUrl;

		@JsonProperty("avatar_url")
		private String avatarUrl;

		@JsonProperty("events_url")
		private String eventsUrl;

		@JsonProperty("html_url")
		private String htmlUrl;

		@JsonProperty("site_admin")
		private Boolean siteAdmin;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("organizations_url")
		private String organizationsUrl;

		public String getGistsUrl() {
			return this.gistsUrl;
		}

		public String getReposUrl() {
			return this.reposUrl;
		}

		public String getFollowingUrl() {
			return this.followingUrl;
		}

		public String getStarredUrl() {
			return this.starredUrl;
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

		public String getSubscriptionsUrl() {
			return this.subscriptionsUrl;
		}

		public String getReceivedEventsUrl() {
			return this.receivedEventsUrl;
		}

		public String getAvatarUrl() {
			return this.avatarUrl;
		}

		public String getEventsUrl() {
			return this.eventsUrl;
		}

		public String getHtmlUrl() {
			return this.htmlUrl;
		}

		public Boolean getSiteAdmin() {
			return this.siteAdmin;
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
			return "Creator [ " + "gistsUrl = " + this.gistsUrl + ", " + "reposUrl = " + this.reposUrl + ", "
					+ "followingUrl = " + this.followingUrl + ", " + "starredUrl = " + this.starredUrl + ", "
					+ "followersUrl = " + this.followersUrl + ", " + "login = " + this.login + ", " + "type = "
					+ this.type + ", " + "url = " + this.url + ", " + "subscriptionsUrl = " + this.subscriptionsUrl
					+ ", " + "receivedEventsUrl = " + this.receivedEventsUrl + ", " + "avatarUrl = " + this.avatarUrl
					+ ", " + "eventsUrl = " + this.eventsUrl + ", " + "htmlUrl = " + this.htmlUrl + ", "
					+ "siteAdmin = " + this.siteAdmin + ", " + "id = " + this.id + ", " + "gravatarId = "
					+ this.gravatarId + ", " + "organizationsUrl = " + this.organizationsUrl + ", " + "]";
		}
	}

}
