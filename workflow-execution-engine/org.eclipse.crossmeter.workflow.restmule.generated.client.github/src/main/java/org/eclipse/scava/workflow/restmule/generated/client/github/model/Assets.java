package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Assets {

	public Assets() {
	}

	@JsonProperty("content_type")
	private String contentType;

	@JsonProperty("size")
	private Float size;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("name")
	private String name;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private Float id;

	@JsonProperty("label")
	private String label;

	@JsonProperty("state")
	private String state;

	@JsonProperty("url")
	private String url;

	@JsonProperty("download_count")
	private Float downloadCount;

	@JsonProperty("uploader")
	private Uploader uploader;

	public String getContentType() {
		return this.contentType;
	}

	public Float getSize() {
		return this.size;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getName() {
		return this.name;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public Float getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public String getState() {
		return this.state;
	}

	public String getUrl() {
		return this.url;
	}

	public Float getDownloadCount() {
		return this.downloadCount;
	}

	public Uploader getUploader() {
		return this.uploader;
	}

	@Override
	public String toString() {
		return "Assets [ " + "contentType = " + this.contentType + ", " + "size = " + this.size + ", " + "updatedAt = "
				+ this.updatedAt + ", " + "name = " + this.name + ", " + "createdAt = " + this.createdAt + ", "
				+ "id = " + this.id + ", " + "label = " + this.label + ", " + "state = " + this.state + ", " + "url = "
				+ this.url + ", " + "downloadCount = " + this.downloadCount + ", " + "uploader = " + this.uploader
				+ ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Uploader {

		public Uploader() {
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
		private Float id;

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

		public Float getId() {
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
			return "Uploader [ " + "gistsUrl = " + this.gistsUrl + ", " + "reposUrl = " + this.reposUrl + ", "
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
