package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCode {

	public SearchCode() {
	}

	@JsonProperty("path")
	private String path;

	@JsonProperty("score")
	private Float score;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("git_url")
	private String gitUrl;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("url")
	private String url;

	@JsonProperty("repository")
	private Repository repository;

	public String getPath() {
		return this.path;
	}

	public Float getScore() {
		return this.score;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getGitUrl() {
		return this.gitUrl;
	}

	public String getSha() {
		return this.sha;
	}

	public String getUrl() {
		return this.url;
	}

	public Repository getRepository() {
		return this.repository;
	}

	@Override
	public String toString() {
		return "SearchCode [ " + "path = " + this.path + ", " + "score = " + this.score + ", " + "htmlUrl = "
				+ this.htmlUrl + ", " + "name = " + this.name + ", " + "gitUrl = " + this.gitUrl + ", " + "sha = "
				+ this.sha + ", " + "url = " + this.url + ", " + "repository = " + this.repository + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repository {

		public Repository() {
		}

		@JsonProperty("tags_url")
		private String tagsUrl;

		@JsonProperty("contributors_url")
		private String contributorsUrl;

		@JsonProperty("private")
		private Boolean privateSanitized;

		@JsonProperty("notifications_url")
		private String notificationsUrl;

		@JsonProperty("description")
		private String description;

		@JsonProperty("subscription_url")
		private String subscriptionUrl;

		@JsonProperty("branches_url")
		private String branchesUrl;

		@JsonProperty("keys_url")
		private String keysUrl;

		@JsonProperty("issue_comment_url")
		private String issueCommentUrl;

		@JsonProperty("labels_url")
		private String labelsUrl;

		@JsonProperty("subscribers_url")
		private String subscribersUrl;

		@JsonProperty("comments_url")
		private String commentsUrl;

		@JsonProperty("stargazers_url")
		private String stargazersUrl;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("archive_url")
		private String archiveUrl;

		@JsonProperty("commits_url")
		private String commitsUrl;

		@JsonProperty("git_refs_url")
		private String gitRefsUrl;

		@JsonProperty("compare_url")
		private String compareUrl;

		@JsonProperty("forks_url")
		private String forksUrl;

		@JsonProperty("statuses_url")
		private String statusesUrl;

		@JsonProperty("git_commits_url")
		private String gitCommitsUrl;

		@JsonProperty("blobs_url")
		private String blobsUrl;

		@JsonProperty("git_tags_url")
		private String gitTagsUrl;

		@JsonProperty("merges_url")
		private String mergesUrl;

		@JsonProperty("downloads_url")
		private String downloadsUrl;

		@JsonProperty("url")
		private String url;

		@JsonProperty("contents_url")
		private String contentsUrl;

		@JsonProperty("milestones_url")
		private String milestonesUrl;

		@JsonProperty("teams_url")
		private String teamsUrl;

		@JsonProperty("fork")
		private Boolean fork;

		@JsonProperty("issues_url")
		private String issuesUrl;

		@JsonProperty("full_name")
		private String fullName;

		@JsonProperty("events_url")
		private String eventsUrl;

		@JsonProperty("issue_events_url")
		private String issueEventsUrl;

		@JsonProperty("languages_url")
		private String languagesUrl;

		@JsonProperty("collaborators_url")
		private String collaboratorsUrl;

		@JsonProperty("html_url")
		private String htmlUrl;

		@JsonProperty("name")
		private String name;

		@JsonProperty("pulls_url")
		private String pullsUrl;

		@JsonProperty("hooks_url")
		private String hooksUrl;

		@JsonProperty("assignees_url")
		private String assigneesUrl;

		@JsonProperty("trees_url")
		private String treesUrl;

		@JsonProperty("owner")
		private Owner owner;

		public String getTagsUrl() {
			return this.tagsUrl;
		}

		public String getContributorsUrl() {
			return this.contributorsUrl;
		}

		public Boolean getPrivateSanitized() {
			return this.privateSanitized;
		}

		public String getNotificationsUrl() {
			return this.notificationsUrl;
		}

		public String getDescription() {
			return this.description;
		}

		public String getSubscriptionUrl() {
			return this.subscriptionUrl;
		}

		public String getBranchesUrl() {
			return this.branchesUrl;
		}

		public String getKeysUrl() {
			return this.keysUrl;
		}

		public String getIssueCommentUrl() {
			return this.issueCommentUrl;
		}

		public String getLabelsUrl() {
			return this.labelsUrl;
		}

		public String getSubscribersUrl() {
			return this.subscribersUrl;
		}

		public String getCommentsUrl() {
			return this.commentsUrl;
		}

		public String getStargazersUrl() {
			return this.stargazersUrl;
		}

		public Integer getId() {
			return this.id;
		}

		public String getArchiveUrl() {
			return this.archiveUrl;
		}

		public String getCommitsUrl() {
			return this.commitsUrl;
		}

		public String getGitRefsUrl() {
			return this.gitRefsUrl;
		}

		public String getCompareUrl() {
			return this.compareUrl;
		}

		public String getForksUrl() {
			return this.forksUrl;
		}

		public String getStatusesUrl() {
			return this.statusesUrl;
		}

		public String getGitCommitsUrl() {
			return this.gitCommitsUrl;
		}

		public String getBlobsUrl() {
			return this.blobsUrl;
		}

		public String getGitTagsUrl() {
			return this.gitTagsUrl;
		}

		public String getMergesUrl() {
			return this.mergesUrl;
		}

		public String getDownloadsUrl() {
			return this.downloadsUrl;
		}

		public String getUrl() {
			return this.url;
		}

		public String getContentsUrl() {
			return this.contentsUrl;
		}

		public String getMilestonesUrl() {
			return this.milestonesUrl;
		}

		public String getTeamsUrl() {
			return this.teamsUrl;
		}

		public Boolean getFork() {
			return this.fork;
		}

		public String getIssuesUrl() {
			return this.issuesUrl;
		}

		public String getFullName() {
			return this.fullName;
		}

		public String getEventsUrl() {
			return this.eventsUrl;
		}

		public String getIssueEventsUrl() {
			return this.issueEventsUrl;
		}

		public String getLanguagesUrl() {
			return this.languagesUrl;
		}

		public String getCollaboratorsUrl() {
			return this.collaboratorsUrl;
		}

		public String getHtmlUrl() {
			return this.htmlUrl;
		}

		public String getName() {
			return this.name;
		}

		public String getPullsUrl() {
			return this.pullsUrl;
		}

		public String getHooksUrl() {
			return this.hooksUrl;
		}

		public String getAssigneesUrl() {
			return this.assigneesUrl;
		}

		public String getTreesUrl() {
			return this.treesUrl;
		}

		public Owner getOwner() {
			return this.owner;
		}

		@Override
		public String toString() {
			return "Repository [ " + "tagsUrl = " + this.tagsUrl + ", " + "contributorsUrl = " + this.contributorsUrl
					+ ", " + "privateSanitized = " + this.privateSanitized + ", " + "notificationsUrl = "
					+ this.notificationsUrl + ", " + "description = " + this.description + ", " + "subscriptionUrl = "
					+ this.subscriptionUrl + ", " + "branchesUrl = " + this.branchesUrl + ", " + "keysUrl = "
					+ this.keysUrl + ", " + "issueCommentUrl = " + this.issueCommentUrl + ", " + "labelsUrl = "
					+ this.labelsUrl + ", " + "subscribersUrl = " + this.subscribersUrl + ", " + "commentsUrl = "
					+ this.commentsUrl + ", " + "stargazersUrl = " + this.stargazersUrl + ", " + "id = " + this.id
					+ ", " + "archiveUrl = " + this.archiveUrl + ", " + "commitsUrl = " + this.commitsUrl + ", "
					+ "gitRefsUrl = " + this.gitRefsUrl + ", " + "compareUrl = " + this.compareUrl + ", "
					+ "forksUrl = " + this.forksUrl + ", " + "statusesUrl = " + this.statusesUrl + ", "
					+ "gitCommitsUrl = " + this.gitCommitsUrl + ", " + "blobsUrl = " + this.blobsUrl + ", "
					+ "gitTagsUrl = " + this.gitTagsUrl + ", " + "mergesUrl = " + this.mergesUrl + ", "
					+ "downloadsUrl = " + this.downloadsUrl + ", " + "url = " + this.url + ", " + "contentsUrl = "
					+ this.contentsUrl + ", " + "milestonesUrl = " + this.milestonesUrl + ", " + "teamsUrl = "
					+ this.teamsUrl + ", " + "fork = " + this.fork + ", " + "issuesUrl = " + this.issuesUrl + ", "
					+ "fullName = " + this.fullName + ", " + "eventsUrl = " + this.eventsUrl + ", "
					+ "issueEventsUrl = " + this.issueEventsUrl + ", " + "languagesUrl = " + this.languagesUrl + ", "
					+ "collaboratorsUrl = " + this.collaboratorsUrl + ", " + "htmlUrl = " + this.htmlUrl + ", "
					+ "name = " + this.name + ", " + "pullsUrl = " + this.pullsUrl + ", " + "hooksUrl = "
					+ this.hooksUrl + ", " + "assigneesUrl = " + this.assigneesUrl + ", " + "treesUrl = "
					+ this.treesUrl + ", " + "owner = " + this.owner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Owner {

			public Owner() {
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
				return "Owner [ " + "gistsUrl = " + this.gistsUrl + ", " + "reposUrl = " + this.reposUrl + ", "
						+ "followingUrl = " + this.followingUrl + ", " + "starredUrl = " + this.starredUrl + ", "
						+ "followersUrl = " + this.followersUrl + ", " + "login = " + this.login + ", " + "type = "
						+ this.type + ", " + "url = " + this.url + ", " + "subscriptionsUrl = " + this.subscriptionsUrl
						+ ", " + "receivedEventsUrl = " + this.receivedEventsUrl + ", " + "avatarUrl = "
						+ this.avatarUrl + ", " + "eventsUrl = " + this.eventsUrl + ", " + "htmlUrl = " + this.htmlUrl
						+ ", " + "id = " + this.id + ", " + "gravatarId = " + this.gravatarId + ", "
						+ "organizationsUrl = " + this.organizationsUrl + ", " + "]";
			}
		}

	}

}
