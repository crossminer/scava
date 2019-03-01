package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUserIdSubscribitions {

	public UserUserIdSubscribitions() {
	}

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

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("svn_url")
	private String svnUrl;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("git_url")
	private String gitUrl;

	@JsonProperty("master_branch")
	private Integer masterBranch;

	@JsonProperty("forks")
	private Integer forks;

	@JsonProperty("ssh_url")
	private String sshUrl;

	@JsonProperty("url")
	private String url;

	@JsonProperty("mirror_url")
	private String mirrorUrl;

	@JsonProperty("fork")
	private Boolean fork;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("clone_url")
	private String cloneUrl;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("open_issues")
	private Integer openIssues;

	@JsonProperty("watchers_count")
	private Integer watchersCount;

	@JsonProperty("forks_count")
	private Integer forksCount;

	@JsonProperty("homepage")
	private String homepage;

	@JsonProperty("owner")
	private Owner owner;

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

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getSvnUrl() {
		return this.svnUrl;
	}

	public Integer getId() {
		return this.id;
	}

	public String getGitUrl() {
		return this.gitUrl;
	}

	public Integer getMasterBranch() {
		return this.masterBranch;
	}

	public Integer getForks() {
		return this.forks;
	}

	public String getSshUrl() {
		return this.sshUrl;
	}

	public String getUrl() {
		return this.url;
	}

	public String getMirrorUrl() {
		return this.mirrorUrl;
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

	public String getCloneUrl() {
		return this.cloneUrl;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public Integer getOpenIssues() {
		return this.openIssues;
	}

	public Integer getWatchersCount() {
		return this.watchersCount;
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
		return "UserUserIdSubscribitions [ " + "privateSanitized = " + this.privateSanitized + ", " + "pushedAt = "
				+ this.pushedAt + ", " + "openIssuesCount = " + this.openIssuesCount + ", " + "createdAt = "
				+ this.createdAt + ", " + "description = " + this.description + ", " + "watchers = " + this.watchers
				+ ", " + "language = " + this.language + ", " + "updatedAt = " + this.updatedAt + ", " + "svnUrl = "
				+ this.svnUrl + ", " + "id = " + this.id + ", " + "gitUrl = " + this.gitUrl + ", " + "masterBranch = "
				+ this.masterBranch + ", " + "forks = " + this.forks + ", " + "sshUrl = " + this.sshUrl + ", "
				+ "url = " + this.url + ", " + "mirrorUrl = " + this.mirrorUrl + ", " + "fork = " + this.fork + ", "
				+ "fullName = " + this.fullName + ", " + "size = " + this.size + ", " + "cloneUrl = " + this.cloneUrl
				+ ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name + ", " + "openIssues = "
				+ this.openIssues + ", " + "watchersCount = " + this.watchersCount + ", " + "forksCount = "
				+ this.forksCount + ", " + "homepage = " + this.homepage + ", " + "owner = " + this.owner + ", " + "]";
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
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
