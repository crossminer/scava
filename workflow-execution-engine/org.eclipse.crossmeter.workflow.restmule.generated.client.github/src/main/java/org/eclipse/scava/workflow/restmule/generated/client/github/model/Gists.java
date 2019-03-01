package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gists {

	public Gists() {
	}

	@JsonProperty("comments")
	private Integer comments;

	@JsonProperty("git_push_url")
	private String gitPushUrl;

	@JsonProperty("public")
	private Boolean publicSanitized;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("comments_url")
	private String commentsUrl;

	@JsonProperty("git_pull_url")
	private String gitPullUrl;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private String id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("files")
	private Files files;

	@JsonProperty("user")
	private User user;

	public Integer getComments() {
		return this.comments;
	}

	public String getGitPushUrl() {
		return this.gitPushUrl;
	}

	public Boolean getPublicSanitized() {
		return this.publicSanitized;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getCommentsUrl() {
		return this.commentsUrl;
	}

	public String getGitPullUrl() {
		return this.gitPullUrl;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public String getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public Files getFiles() {
		return this.files;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "Gists [ " + "comments = " + this.comments + ", " + "gitPushUrl = " + this.gitPushUrl + ", "
				+ "publicSanitized = " + this.publicSanitized + ", " + "htmlUrl = " + this.htmlUrl + ", "
				+ "commentsUrl = " + this.commentsUrl + ", " + "gitPullUrl = " + this.gitPullUrl + ", " + "createdAt = "
				+ this.createdAt + ", " + "description = " + this.description + ", " + "id = " + this.id + ", "
				+ "url = " + this.url + ", " + "files = " + this.files + ", " + "user = " + this.user + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Files {

		public Files() {
		}

		@JsonProperty("ring.erl")
		private RingErl ringErl;

		public RingErl getRingErl() {
			return this.ringErl;
		}

		@Override
		public String toString() {
			return "Files [ " + "ringErl = " + this.ringErl + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class RingErl {

			public RingErl() {
			}

			@JsonProperty("filename")
			private String filename;

			@JsonProperty("size")
			private Integer size;

			@JsonProperty("raw_url")
			private String rawUrl;

			public String getFilename() {
				return this.filename;
			}

			public Integer getSize() {
				return this.size;
			}

			public String getRawUrl() {
				return this.rawUrl;
			}

			@Override
			public String toString() {
				return "RingErl [ " + "filename = " + this.filename + ", " + "size = " + this.size + ", " + "rawUrl = "
						+ this.rawUrl + ", " + "]";
			}
		}

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
