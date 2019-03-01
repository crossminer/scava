package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

	public Repo() {
	}

	@JsonProperty("private")
	private Boolean privateSanitized;

	@JsonProperty("has_downloads")
	private Boolean hasDownloads;

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

	@JsonProperty("has_wiki")
	private Boolean hasWiki;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("svn_url")
	private String svnUrl;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("git_url")
	private String gitUrl;

	@JsonProperty("master_branch")
	private String masterBranch;

	@JsonProperty("forks")
	private Integer forks;

	@JsonProperty("ssh_url")
	private String sshUrl;

	@JsonProperty("has_issues")
	private Boolean hasIssues;

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

	@JsonProperty("parent")
	private Parent parent;

	@JsonProperty("source")
	private Source source;

	@JsonProperty("owner")
	private Owner owner;

	@JsonProperty("organization")
	private Organization organization;

	public Boolean getPrivateSanitized() {
		return this.privateSanitized;
	}

	public Boolean getHasDownloads() {
		return this.hasDownloads;
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

	public Boolean getHasWiki() {
		return this.hasWiki;
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

	public String getMasterBranch() {
		return this.masterBranch;
	}

	public Integer getForks() {
		return this.forks;
	}

	public String getSshUrl() {
		return this.sshUrl;
	}

	public Boolean getHasIssues() {
		return this.hasIssues;
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

	public Parent getParent() {
		return this.parent;
	}

	public Source getSource() {
		return this.source;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	@Override
	public String toString() {
		return "Repo [ " + "privateSanitized = " + this.privateSanitized + ", " + "hasDownloads = " + this.hasDownloads
				+ ", " + "pushedAt = " + this.pushedAt + ", " + "openIssuesCount = " + this.openIssuesCount + ", "
				+ "createdAt = " + this.createdAt + ", " + "description = " + this.description + ", " + "watchers = "
				+ this.watchers + ", " + "language = " + this.language + ", " + "hasWiki = " + this.hasWiki + ", "
				+ "updatedAt = " + this.updatedAt + ", " + "svnUrl = " + this.svnUrl + ", " + "id = " + this.id + ", "
				+ "gitUrl = " + this.gitUrl + ", " + "masterBranch = " + this.masterBranch + ", " + "forks = "
				+ this.forks + ", " + "sshUrl = " + this.sshUrl + ", " + "hasIssues = " + this.hasIssues + ", "
				+ "url = " + this.url + ", " + "mirrorUrl = " + this.mirrorUrl + ", " + "fork = " + this.fork + ", "
				+ "fullName = " + this.fullName + ", " + "size = " + this.size + ", " + "cloneUrl = " + this.cloneUrl
				+ ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name + ", " + "openIssues = "
				+ this.openIssues + ", " + "watchersCount = " + this.watchersCount + ", " + "forksCount = "
				+ this.forksCount + ", " + "homepage = " + this.homepage + ", " + "parent = " + this.parent + ", "
				+ "source = " + this.source + ", " + "owner = " + this.owner + ", " + "organization = "
				+ this.organization + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Parent {

		public Parent() {
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
		private String masterBranch;

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
		private OwnerInner ownerInner;

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

		public String getMasterBranch() {
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

		public OwnerInner getOwnerInner() {
			return this.ownerInner;
		}

		@Override
		public String toString() {
			return "Parent [ " + "privateSanitized = " + this.privateSanitized + ", " + "pushedAt = " + this.pushedAt
					+ ", " + "openIssuesCount = " + this.openIssuesCount + ", " + "createdAt = " + this.createdAt + ", "
					+ "description = " + this.description + ", " + "watchers = " + this.watchers + ", " + "language = "
					+ this.language + ", " + "updatedAt = " + this.updatedAt + ", " + "svnUrl = " + this.svnUrl + ", "
					+ "id = " + this.id + ", " + "gitUrl = " + this.gitUrl + ", " + "masterBranch = "
					+ this.masterBranch + ", " + "forks = " + this.forks + ", " + "sshUrl = " + this.sshUrl + ", "
					+ "url = " + this.url + ", " + "mirrorUrl = " + this.mirrorUrl + ", " + "fork = " + this.fork + ", "
					+ "fullName = " + this.fullName + ", " + "size = " + this.size + ", " + "cloneUrl = "
					+ this.cloneUrl + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name + ", "
					+ "openIssues = " + this.openIssues + ", " + "watchersCount = " + this.watchersCount + ", "
					+ "forksCount = " + this.forksCount + ", " + "homepage = " + this.homepage + ", " + "ownerInner = "
					+ this.ownerInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class OwnerInner {

			public OwnerInner() {
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
				return "OwnerInner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Source {

		public Source() {
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
		private String masterBranch;

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
		private OwnerInnerInner ownerInnerInner;

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

		public String getMasterBranch() {
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

		public OwnerInnerInner getOwnerInnerInner() {
			return this.ownerInnerInner;
		}

		@Override
		public String toString() {
			return "Source [ " + "privateSanitized = " + this.privateSanitized + ", " + "pushedAt = " + this.pushedAt
					+ ", " + "openIssuesCount = " + this.openIssuesCount + ", " + "createdAt = " + this.createdAt + ", "
					+ "description = " + this.description + ", " + "watchers = " + this.watchers + ", " + "language = "
					+ this.language + ", " + "updatedAt = " + this.updatedAt + ", " + "svnUrl = " + this.svnUrl + ", "
					+ "id = " + this.id + ", " + "gitUrl = " + this.gitUrl + ", " + "masterBranch = "
					+ this.masterBranch + ", " + "forks = " + this.forks + ", " + "sshUrl = " + this.sshUrl + ", "
					+ "url = " + this.url + ", " + "mirrorUrl = " + this.mirrorUrl + ", " + "fork = " + this.fork + ", "
					+ "fullName = " + this.fullName + ", " + "size = " + this.size + ", " + "cloneUrl = "
					+ this.cloneUrl + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name + ", "
					+ "openIssues = " + this.openIssues + ", " + "watchersCount = " + this.watchersCount + ", "
					+ "forksCount = " + this.forksCount + ", " + "homepage = " + this.homepage + ", "
					+ "ownerInnerInner = " + this.ownerInnerInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class OwnerInnerInner {

			public OwnerInnerInner() {
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
				return "OwnerInnerInner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", "
						+ "login = " + this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = "
						+ this.url + ", " + "]";
			}
		}

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

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Organization {

		public Organization() {
		}

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
			return "Organization [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "type = " + this.type + ", " + "gravatarId = " + this.gravatarId + ", "
					+ "url = " + this.url + ", " + "]";
		}
	}

}
