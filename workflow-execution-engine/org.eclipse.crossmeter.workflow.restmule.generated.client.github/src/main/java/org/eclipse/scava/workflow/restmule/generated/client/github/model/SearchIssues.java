package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssues {

	public SearchIssues() {
	}

	@JsonProperty("closed_at")
	private Object closedAt;

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

	@JsonProperty("labels_url")
	private String labelsUrl;

	@JsonProperty("number")
	private Integer number;

	@JsonProperty("score")
	private Float score;

	@JsonProperty("milestone")
	private Object milestone;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("events_url")
	private String eventsUrl;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("comments_url")
	private String commentsUrl;

	@JsonProperty("assignee")
	private Object assignee;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("state")
	private String state;

	@JsonProperty("pull_request")
	private PullRequest pullRequest;

	@JsonProperty("user")
	private User user;

	@JsonProperty("labels")
	private List<Labels> labels = new ArrayList<Labels>();

	public Object getClosedAt() {
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

	public String getLabelsUrl() {
		return this.labelsUrl;
	}

	public Integer getNumber() {
		return this.number;
	}

	public Float getScore() {
		return this.score;
	}

	public Object getMilestone() {
		return this.milestone;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getEventsUrl() {
		return this.eventsUrl;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getCommentsUrl() {
		return this.commentsUrl;
	}

	public Object getAssignee() {
		return this.assignee;
	}

	public Integer getId() {
		return this.id;
	}

	public String getState() {
		return this.state;
	}

	public PullRequest getPullRequest() {
		return this.pullRequest;
	}

	public User getUser() {
		return this.user;
	}

	public List<Labels> getLabels() {
		return this.labels;
	}

	@Override
	public String toString() {
		return "SearchIssues [ " + "closedAt = " + this.closedAt + ", " + "comments = " + this.comments + ", "
				+ "createdAt = " + this.createdAt + ", " + "body = " + this.body + ", " + "title = " + this.title + ", "
				+ "url = " + this.url + ", " + "labelsUrl = " + this.labelsUrl + ", " + "number = " + this.number + ", "
				+ "score = " + this.score + ", " + "milestone = " + this.milestone + ", " + "updatedAt = "
				+ this.updatedAt + ", " + "eventsUrl = " + this.eventsUrl + ", " + "htmlUrl = " + this.htmlUrl + ", "
				+ "commentsUrl = " + this.commentsUrl + ", " + "assignee = " + this.assignee + ", " + "id = " + this.id
				+ ", " + "state = " + this.state + ", " + "pullRequest = " + this.pullRequest + ", " + "user = "
				+ this.user + ", " + "labels = " + this.labels + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PullRequest {

		public PullRequest() {
		}

		@JsonProperty("patch_url")
		private Object patchUrl;

		@JsonProperty("html_url")
		private Object htmlUrl;

		@JsonProperty("diff_url")
		private Object diffUrl;

		public Object getPatchUrl() {
			return this.patchUrl;
		}

		public Object getHtmlUrl() {
			return this.htmlUrl;
		}

		public Object getDiffUrl() {
			return this.diffUrl;
		}

		@Override
		public String toString() {
			return "PullRequest [ " + "patchUrl = " + this.patchUrl + ", " + "htmlUrl = " + this.htmlUrl + ", "
					+ "diffUrl = " + this.diffUrl + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {

		public User() {
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
			return "User [ " + "gistsUrl = " + this.gistsUrl + ", " + "reposUrl = " + this.reposUrl + ", "
					+ "followingUrl = " + this.followingUrl + ", " + "starredUrl = " + this.starredUrl + ", "
					+ "followersUrl = " + this.followersUrl + ", " + "login = " + this.login + ", " + "type = "
					+ this.type + ", " + "url = " + this.url + ", " + "subscriptionsUrl = " + this.subscriptionsUrl
					+ ", " + "receivedEventsUrl = " + this.receivedEventsUrl + ", " + "avatarUrl = " + this.avatarUrl
					+ ", " + "eventsUrl = " + this.eventsUrl + ", " + "htmlUrl = " + this.htmlUrl + ", " + "id = "
					+ this.id + ", " + "gravatarId = " + this.gravatarId + ", " + "organizationsUrl = "
					+ this.organizationsUrl + ", " + "]";
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
