package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRepositories {

	public SearchRepositories() {
	}

	@JsonProperty("forks")
	private Integer forks;

	@JsonProperty("private")
	private Boolean privateSanitized;

	@JsonProperty("pushed_at")
	private String pushedAt;

	@JsonProperty("open_issues_count")
	private Integer openIssuesCount;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("watchers")
	private Integer watchers;

	@JsonProperty("language")
	private String language;

	@JsonProperty("url")
	private String url;

	@JsonProperty("score")
	private Float score;

	@JsonProperty("fork")
	private Boolean fork;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("default_branch")
	private String defaultBranch;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("open_issues")
	private Integer openIssues;

	@JsonProperty("watchers_count")
	private Integer watchersCount;

	@JsonProperty("master_branch")
	private String masterBranch;

	@JsonProperty("forks_count")
	private Integer forksCount;

	@JsonProperty("homepage")
	private String homepage;

	@JsonProperty("owner")
	private Owner owner;

	public Integer getForks() {
		return this.forks;
	}

	public Boolean getPrivateSanitized() {
		return this.privateSanitized;
	}

	public String getPushedAt() {
		return this.pushedAt;
	}

	public Integer getOpenIssuesCount() {
		return this.openIssuesCount;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getWatchers() {
		return this.watchers;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getUrl() {
		return this.url;
	}

	public Float getScore() {
		return this.score;
	}

	public Boolean getFork() {
		return this.fork;
	}

	public String getFullName() {
		return this.fullName;
	}

	public Integer getSize() {
		return this.size;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getDefaultBranch() {
		return this.defaultBranch;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getOpenIssues() {
		return this.openIssues;
	}

	public Integer getWatchersCount() {
		return this.watchersCount;
	}

	public String getMasterBranch() {
		return this.masterBranch;
	}

	public Integer getForksCount() {
		return this.forksCount;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public Owner getOwner() {
		return this.owner;
	}

	@Override
	public String toString() {
		return "SearchRepositories [ " + "forks = " + this.forks + ", " + "privateSanitized = " + this.privateSanitized
				+ ", " + "pushedAt = " + this.pushedAt + ", " + "openIssuesCount = " + this.openIssuesCount + ", "
				+ "createdAt = " + this.createdAt + ", " + "description = " + this.description + ", " + "watchers = "
				+ this.watchers + ", " + "language = " + this.language + ", " + "url = " + this.url + ", " + "score = "
				+ this.score + ", " + "fork = " + this.fork + ", " + "fullName = " + this.fullName + ", " + "size = "
				+ this.size + ", " + "updatedAt = " + this.updatedAt + ", " + "htmlUrl = " + this.htmlUrl + ", "
				+ "name = " + this.name + ", " + "defaultBranch = " + this.defaultBranch + ", " + "id = " + this.id
				+ ", " + "openIssues = " + this.openIssues + ", " + "watchersCount = " + this.watchersCount + ", "
				+ "masterBranch = " + this.masterBranch + ", " + "forksCount = " + this.forksCount + ", "
				+ "homepage = " + this.homepage + ", " + "owner = " + this.owner + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Owner {

		public Owner() {
		}

		@JsonProperty("received_events_url")
		private String receivedEventsUrl;

		@JsonProperty("avatar_url")
		private String avatarUrl;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("login")
		private String login;

		@JsonProperty("type")
		private String type;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("url")
		private String url;

		public String getReceivedEventsUrl() {
			return this.receivedEventsUrl;
		}

		public String getAvatarUrl() {
			return this.avatarUrl;
		}

		public Integer getId() {
			return this.id;
		}

		public String getLogin() {
			return this.login;
		}

		public String getType() {
			return this.type;
		}

		public String getGravatarId() {
			return this.gravatarId;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Owner [ " + "receivedEventsUrl = " + this.receivedEventsUrl + ", " + "avatarUrl = " + this.avatarUrl
					+ ", " + "id = " + this.id + ", " + "login = " + this.login + ", " + "type = " + this.type + ", "
					+ "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
