package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullsComment {

	public PullsComment() {
	}

	@JsonProperty("path")
	private String path;

	@JsonProperty("updated_at")
	private String updatedAt;

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

	@JsonProperty("_links")
	private Links links;

	@JsonProperty("user")
	private User user;

	public String getPath() {
		return this.path;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
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

	public Links getLinks() {
		return this.links;
	}

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "PullsComment [ " + "path = " + this.path + ", " + "updatedAt = " + this.updatedAt + ", "
				+ "createdAt = " + this.createdAt + ", " + "id = " + this.id + ", " + "position = " + this.position
				+ ", " + "body = " + this.body + ", " + "commitId = " + this.commitId + ", " + "url = " + this.url
				+ ", " + "links = " + this.links + ", " + "user = " + this.user + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Links {

		public Links() {
		}

		@JsonProperty("pull_request")
		private PullRequest pullRequest;

		@JsonProperty("self")
		private Self self;

		@JsonProperty("html")
		private Html html;

		public PullRequest getPullRequest() {
			return this.pullRequest;
		}

		public Self getSelf() {
			return this.self;
		}

		public Html getHtml() {
			return this.html;
		}

		@Override
		public String toString() {
			return "Links [ " + "pullRequest = " + this.pullRequest + ", " + "self = " + this.self + ", " + "html = "
					+ this.html + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class PullRequest {

			public PullRequest() {
			}

			@JsonProperty("href")
			private String href;

			public String getHref() {
				return this.href;
			}

			@Override
			public String toString() {
				return "PullRequest [ " + "href = " + this.href + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Self {

			public Self() {
			}

			@JsonProperty("href")
			private String href;

			public String getHref() {
				return this.href;
			}

			@Override
			public String toString() {
				return "Self [ " + "href = " + this.href + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Html {

			public Html() {
			}

			@JsonProperty("href")
			private String href;

			public String getHref() {
				return this.href;
			}

			@Override
			public String toString() {
				return "Html [ " + "href = " + this.href + ", " + "]";
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
