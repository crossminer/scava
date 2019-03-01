package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notifications {

	public Notifications() {
	}

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("unread")
	private Boolean unread;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("last_read_at")
	private String lastReadAt;

	@JsonProperty("url")
	private String url;

	@JsonProperty("subject")
	private Subject subject;

	@JsonProperty("repository")
	private Repository repository;

	public String getReason() {
		return this.reason;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public Boolean getUnread() {
		return this.unread;
	}

	public Integer getId() {
		return this.id;
	}

	public String getLastReadAt() {
		return this.lastReadAt;
	}

	public String getUrl() {
		return this.url;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public Repository getRepository() {
		return this.repository;
	}

	@Override
	public String toString() {
		return "Notifications [ " + "reason = " + this.reason + ", " + "updatedAt = " + this.updatedAt + ", "
				+ "unread = " + this.unread + ", " + "id = " + this.id + ", " + "lastReadAt = " + this.lastReadAt + ", "
				+ "url = " + this.url + ", " + "subject = " + this.subject + ", " + "repository = " + this.repository
				+ ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Subject {

		public Subject() {
		}

		@JsonProperty("latest_comment_url")
		private String latestCommentUrl;

		@JsonProperty("title")
		private String title;

		@JsonProperty("type")
		private String type;

		@JsonProperty("url")
		private String url;

		public String getLatestCommentUrl() {
			return this.latestCommentUrl;
		}

		public String getTitle() {
			return this.title;
		}

		public String getType() {
			return this.type;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Subject [ " + "latestCommentUrl = " + this.latestCommentUrl + ", " + "title = " + this.title + ", "
					+ "type = " + this.type + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repository {

		public Repository() {
		}

		@JsonProperty("fork")
		private Boolean fork;

		@JsonProperty("private")
		private Boolean privateSanitized;

		@JsonProperty("full_name")
		private String fullName;

		@JsonProperty("html_url")
		private String htmlUrl;

		@JsonProperty("name")
		private String name;

		@JsonProperty("description")
		private String description;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("url")
		private String url;

		@JsonProperty("owner")
		private Owner owner;

		public Boolean getFork() {
			return this.fork;
		}

		public Boolean getPrivateSanitized() {
			return this.privateSanitized;
		}

		public String getFullName() {
			return this.fullName;
		}

		public String getHtmlUrl() {
			return this.htmlUrl;
		}

		public String getName() {
			return this.name;
		}

		public String getDescription() {
			return this.description;
		}

		public Integer getId() {
			return this.id;
		}

		public String getUrl() {
			return this.url;
		}

		public Owner getOwner() {
			return this.owner;
		}

		@Override
		public String toString() {
			return "Repository [ " + "fork = " + this.fork + ", " + "privateSanitized = " + this.privateSanitized + ", "
					+ "fullName = " + this.fullName + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name
					+ ", " + "description = " + this.description + ", " + "id = " + this.id + ", " + "url = " + this.url
					+ ", " + "owner = " + this.owner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Owner {

			public Owner() {
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
				return "Owner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

	}

}
