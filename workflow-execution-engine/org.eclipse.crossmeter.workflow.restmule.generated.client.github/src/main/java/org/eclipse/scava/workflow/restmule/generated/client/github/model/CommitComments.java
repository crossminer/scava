package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitComments {

	public CommitComments() {
	}

	@JsonProperty("path")
	private String path;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("line")
	private Integer line;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("position")
	private Integer position;

	@JsonProperty("body")
	private String body;

	@JsonProperty("commit_id")
	private String commitId;

	@JsonProperty("url")
	private String url;

	@JsonProperty("user")
	private User user;

	public String getPath() {
		return this.path;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public Integer getLine() {
		return this.line;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getPosition() {
		return this.position;
	}

	public String getBody() {
		return this.body;
	}

	public String getCommitId() {
		return this.commitId;
	}

	public String getUrl() {
		return this.url;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "CommitComments [ " + "path = " + this.path + ", " + "updatedAt = " + this.updatedAt + ", " + "line = "
				+ this.line + ", " + "htmlUrl = " + this.htmlUrl + ", " + "createdAt = " + this.createdAt + ", "
				+ "id = " + this.id + ", " + "position = " + this.position + ", " + "body = " + this.body + ", "
				+ "commitId = " + this.commitId + ", " + "url = " + this.url + ", " + "user = " + this.user + ", "
				+ "]";
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
