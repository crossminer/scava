package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gist {

	public Gist() {
	}

	@JsonProperty("comments")
	private Integer comments;

	@JsonProperty("git_push_url")
	private String gitPushUrl;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("url")
	private String url;

	@JsonProperty("public")
	private Boolean publicSanitized;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("comments_url")
	private String commentsUrl;

	@JsonProperty("git_pull_url")
	private String gitPullUrl;

	@JsonProperty("id")
	private String id;

	@JsonProperty("files")
	private Files files;

	@JsonProperty("user")
	private User user;

	@JsonProperty("forks")
	private List<Forks> forks = new ArrayList<Forks>();

	@JsonProperty("history")
	private List<History> history = new ArrayList<History>();

	public Integer getComments() {
		return this.comments;
	}

	public String getGitPushUrl() {
		return this.gitPushUrl;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public String getUrl() {
		return this.url;
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

	public String getId() {
		return this.id;
	}

	public Files getFiles() {
		return this.files;
	}

	public User getUser() {
		return this.user;
	}

	public List<Forks> getForks() {
		return this.forks;
	}

	public List<History> getHistory() {
		return this.history;
	}

	@Override
	public String toString() {
		return "Gist [ " + "comments = " + this.comments + ", " + "gitPushUrl = " + this.gitPushUrl + ", "
				+ "createdAt = " + this.createdAt + ", " + "description = " + this.description + ", " + "url = "
				+ this.url + ", " + "publicSanitized = " + this.publicSanitized + ", " + "htmlUrl = " + this.htmlUrl
				+ ", " + "commentsUrl = " + this.commentsUrl + ", " + "gitPullUrl = " + this.gitPullUrl + ", " + "id = "
				+ this.id + ", " + "files = " + this.files + ", " + "user = " + this.user + ", " + "forks = "
				+ this.forks + ", " + "history = " + this.history + ", " + "]";
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

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Forks {

		public Forks() {
		}

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("url")
		private String url;

		@JsonProperty("user")
		private UserInnerInner userInnerInner;

		public String getCreatedAt() {
			return this.createdAt;
		}

		public String getUrl() {
			return this.url;
		}

		public UserInnerInner getUserInnerInner() {
			return this.userInnerInner;
		}

		@Override
		public String toString() {
			return "Forks [ " + "createdAt = " + this.createdAt + ", " + "url = " + this.url + ", "
					+ "userInnerInner = " + this.userInnerInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class UserInnerInner {

			public UserInnerInner() {
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
				return "UserInnerInner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", "
						+ "login = " + this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = "
						+ this.url + ", " + "]";
			}
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class History {

		public History() {
		}

		@JsonProperty("committed_at")
		private String committedAt;

		@JsonProperty("version")
		private String version;

		@JsonProperty("url")
		private String url;

		@JsonProperty("change_status")
		private ChangeStatus changeStatus;

		@JsonProperty("user")
		private UserInner userInner;

		public String getCommittedAt() {
			return this.committedAt;
		}

		public String getVersion() {
			return this.version;
		}

		public String getUrl() {
			return this.url;
		}

		public ChangeStatus getChangeStatus() {
			return this.changeStatus;
		}

		public UserInner getUserInner() {
			return this.userInner;
		}

		@Override
		public String toString() {
			return "History [ " + "committedAt = " + this.committedAt + ", " + "version = " + this.version + ", "
					+ "url = " + this.url + ", " + "changeStatus = " + this.changeStatus + ", " + "userInner = "
					+ this.userInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class ChangeStatus {

			public ChangeStatus() {
			}

			@JsonProperty("total")
			private Integer total;

			@JsonProperty("additions")
			private Integer additions;

			@JsonProperty("deletions")
			private Integer deletions;

			public Integer getTotal() {
				return this.total;
			}

			public Integer getAdditions() {
				return this.additions;
			}

			public Integer getDeletions() {
				return this.deletions;
			}

			@Override
			public String toString() {
				return "ChangeStatus [ " + "total = " + this.total + ", " + "additions = " + this.additions + ", "
						+ "deletions = " + this.deletions + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class UserInner {

			public UserInner() {
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
				return "UserInner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

	}

}
