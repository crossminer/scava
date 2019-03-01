package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {

	public Comments() {
	}

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("body")
	private String body;

	@JsonProperty("url")
	private String url;

	@JsonProperty("user")
	private User user;

	public String getCreatedAt() {
		return this.createdAt;
	}

	public Integer getId() {
		return this.id;
	}

	public String getBody() {
		return this.body;
	}

	public String getUrl() {
		return this.url;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "Comments [ " + "createdAt = " + this.createdAt + ", " + "id = " + this.id + ", " + "body = " + this.body
				+ ", " + "url = " + this.url + ", " + "user = " + this.user + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {

		public User() {
		}

		@JsonProperty("avatar_url")
		private String avatarUrl;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("login")
		private String login;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("url")
		private String url;

		public String getAvatarUrl() {
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
			return "User [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
