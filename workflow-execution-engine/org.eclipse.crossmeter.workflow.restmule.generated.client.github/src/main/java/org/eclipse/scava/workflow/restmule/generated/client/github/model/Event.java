package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	public Event() {
	}

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("event")
	private String event;

	@JsonProperty("commit_id")
	private String commitId;

	@JsonProperty("url")
	private String url;

	@JsonProperty("actor")
	private Actor actor;

	@JsonProperty("issue")
	private Issue issue;

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getEvent() {
		return this.event;
	}

	public String getCommitId() {
		return this.commitId;
	}

	public String getUrl() {
		return this.url;
	}

	public Actor getActor() {
		return this.actor;
	}

	public Issue getIssue() {
		return this.issue;
	}

	@Override
	public String toString() {
		return "Event [ " + "createdAt = " + this.createdAt + ", " + "event = " + this.event + ", " + "commitId = "
				+ this.commitId + ", " + "url = " + this.url + ", " + "actor = " + this.actor + ", " + "issue = "
				+ this.issue + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Actor {

		public Actor() {
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
			return "Actor [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Issue {

		public Issue() {
		}

		@JsonProperty("closed_at")
		private String closedAt;

		@JsonProperty("comments")
		private Integer comments;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("body")
		private String body;

		@JsonProperty("title")
		private String title;

		@JsonProperty("url")
		private String url;

		@JsonProperty("number")
		private Integer number;

		@JsonProperty("updated_at")
		private String updatedAt;

		@JsonProperty("html_url")
		private String htmlUrl;

		@JsonProperty("state")
		private String state;

		@JsonProperty("pull_request")
		private PullRequest pullRequest;

		@JsonProperty("milestone")
		private Milestone milestone;

		@JsonProperty("assignee")
		private Assignee assignee;

		@JsonProperty("user")
		private User user;

		@JsonProperty("labels")
		private List<Labels> labels = new ArrayList<Labels>();

		public String getClosedAt() {
			return this.closedAt;
		}

		public Integer getComments() {
			return this.comments;
		}

		public String getCreatedAt() {
			return this.createdAt;
		}

		public String getBody() {
			return this.body;
		}

		public String getTitle() {
			return this.title;
		}

		public String getUrl() {
			return this.url;
		}

		public Integer getNumber() {
			return this.number;
		}

		public String getUpdatedAt() {
			return this.updatedAt;
		}

		public String getHtmlUrl() {
			return this.htmlUrl;
		}

		public String getState() {
			return this.state;
		}

		public PullRequest getPullRequest() {
			return this.pullRequest;
		}

		public Milestone getMilestone() {
			return this.milestone;
		}

		public Assignee getAssignee() {
			return this.assignee;
		}

		public User getUser() {
			return this.user;
		}

		public List<Labels> getLabels() {
			return this.labels;
		}

		@Override
		public String toString() {
			return "Issue [ " + "closedAt = " + this.closedAt + ", " + "comments = " + this.comments + ", "
					+ "createdAt = " + this.createdAt + ", " + "body = " + this.body + ", " + "title = " + this.title
					+ ", " + "url = " + this.url + ", " + "number = " + this.number + ", " + "updatedAt = "
					+ this.updatedAt + ", " + "htmlUrl = " + this.htmlUrl + ", " + "state = " + this.state + ", "
					+ "pullRequest = " + this.pullRequest + ", " + "milestone = " + this.milestone + ", "
					+ "assignee = " + this.assignee + ", " + "user = " + this.user + ", " + "labels = " + this.labels
					+ ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class PullRequest {

			public PullRequest() {
			}

			@JsonProperty("patch_url")
			private String patchUrl;

			@JsonProperty("html_url")
			private String htmlUrl;

			@JsonProperty("diff_url")
			private String diffUrl;

			public String getPatchUrl() {
				return this.patchUrl;
			}

			public String getHtmlUrl() {
				return this.htmlUrl;
			}

			public String getDiffUrl() {
				return this.diffUrl;
			}

			@Override
			public String toString() {
				return "PullRequest [ " + "patchUrl = " + this.patchUrl + ", " + "htmlUrl = " + this.htmlUrl + ", "
						+ "diffUrl = " + this.diffUrl + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Milestone {

			public Milestone() {
			}

			@JsonProperty("number")
			private Integer number;

			@JsonProperty("created_at")
			private String createdAt;

			@JsonProperty("description")
			private String description;

			@JsonProperty("state")
			private String state;

			@JsonProperty("closed_issues")
			private Integer closedIssues;

			@JsonProperty("open_issues")
			private Integer openIssues;

			@JsonProperty("title")
			private String title;

			@JsonProperty("due_on")
			private String dueOn;

			@JsonProperty("url")
			private String url;

			@JsonProperty("creator")
			private Creator creator;

			public Integer getNumber() {
				return this.number;
			}

			public String getCreatedAt() {
				return this.createdAt;
			}

			public String getDescription() {
				return this.description;
			}

			public String getState() {
				return this.state;
			}

			public Integer getClosedIssues() {
				return this.closedIssues;
			}

			public Integer getOpenIssues() {
				return this.openIssues;
			}

			public String getTitle() {
				return this.title;
			}

			public String getDueOn() {
				return this.dueOn;
			}

			public String getUrl() {
				return this.url;
			}

			public Creator getCreator() {
				return this.creator;
			}

			@Override
			public String toString() {
				return "Milestone [ " + "number = " + this.number + ", " + "createdAt = " + this.createdAt + ", "
						+ "description = " + this.description + ", " + "state = " + this.state + ", "
						+ "closedIssues = " + this.closedIssues + ", " + "openIssues = " + this.openIssues + ", "
						+ "title = " + this.title + ", " + "dueOn = " + this.dueOn + ", " + "url = " + this.url + ", "
						+ "creator = " + this.creator + ", " + "]";
			}

			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class Creator {

				public Creator() {
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
					return "Creator [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
							+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
							+ "]";
				}
			}

		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Assignee {

			public Assignee() {
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
				return "Assignee [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
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
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Labels {

			public Labels() {
			}

			@JsonProperty("color")
			private String color;

			@JsonProperty("name")
			private String name;

			@JsonProperty("url")
			private String url;

			public String getColor() {
				return this.color;
			}

			public String getName() {
				return this.name;
			}

			public String getUrl() {
				return this.url;
			}

			@Override
			public String toString() {
				return "Labels [ " + "color = " + this.color + ", " + "name = " + this.name + ", " + "url = " + this.url
						+ ", " + "]";
			}
		}

	}

}
