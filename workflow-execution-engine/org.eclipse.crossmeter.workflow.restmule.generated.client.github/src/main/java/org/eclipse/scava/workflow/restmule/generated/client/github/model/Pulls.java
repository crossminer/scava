package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pulls {

	public Pulls() {
	}

	@JsonProperty("issue_url")
	private String issueUrl;

	@JsonProperty("closed_at")
	private String closedAt;

	@JsonProperty("merged_at")
	private String mergedAt;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("diff_url")
	private String diffUrl;

	@JsonProperty("body")
	private String body;

	@JsonProperty("title")
	private String title;

	@JsonProperty("url")
	private String url;

	@JsonProperty("number")
	private Integer number;

	@JsonProperty("patch_url")
	private String patchUrl;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("state")
	private String state;

	@JsonProperty("_links")
	private Links links;

	@JsonProperty("head")
	private Head head;

	@JsonProperty("user")
	private User user;

	@JsonProperty("base")
	private Base base;

	public String getIssueUrl() {
		return this.issueUrl;
	}

	public String getClosedAt() {
		return this.closedAt;
	}

	public String getMergedAt() {
		return this.mergedAt;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDiffUrl() {
		return this.diffUrl;
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

	public String getPatchUrl() {
		return this.patchUrl;
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

	public Links getLinks() {
		return this.links;
	}

	public Head getHead() {
		return this.head;
	}

	public User getUser() {
		return this.user;
	}

	public Base getBase() {
		return this.base;
	}

	@Override
	public String toString() {
		return "Pulls [ " + "issueUrl = " + this.issueUrl + ", " + "closedAt = " + this.closedAt + ", " + "mergedAt = "
				+ this.mergedAt + ", " + "createdAt = " + this.createdAt + ", " + "diffUrl = " + this.diffUrl + ", "
				+ "body = " + this.body + ", " + "title = " + this.title + ", " + "url = " + this.url + ", "
				+ "number = " + this.number + ", " + "patchUrl = " + this.patchUrl + ", " + "updatedAt = "
				+ this.updatedAt + ", " + "htmlUrl = " + this.htmlUrl + ", " + "state = " + this.state + ", "
				+ "links = " + this.links + ", " + "head = " + this.head + ", " + "user = " + this.user + ", "
				+ "base = " + this.base + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Links {

		public Links() {
		}

		@JsonProperty("comments")
		private Comments comments;

		@JsonProperty("review_comments")
		private ReviewComments reviewComments;

		@JsonProperty("self")
		private Self self;

		@JsonProperty("html")
		private Html html;

		public Comments getComments() {
			return this.comments;
		}

		public ReviewComments getReviewComments() {
			return this.reviewComments;
		}

		public Self getSelf() {
			return this.self;
		}

		public Html getHtml() {
			return this.html;
		}

		@Override
		public String toString() {
			return "Links [ " + "comments = " + this.comments + ", " + "reviewComments = " + this.reviewComments + ", "
					+ "self = " + this.self + ", " + "html = " + this.html + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Comments {

			public Comments() {
			}

			@JsonProperty("href")
			private String href;

			public String getHref() {
				return this.href;
			}

			@Override
			public String toString() {
				return "Comments [ " + "href = " + this.href + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class ReviewComments {

			public ReviewComments() {
			}

			@JsonProperty("href")
			private String href;

			public String getHref() {
				return this.href;
			}

			@Override
			public String toString() {
				return "ReviewComments [ " + "href = " + this.href + ", " + "]";
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
	public static class Head {

		public Head() {
		}

		@JsonProperty("ref")
		private String ref;

		@JsonProperty("label")
		private String label;

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("repo")
		private Repo repo;

		@JsonProperty("user")
		private UserInnerInner userInnerInner;

		public String getRef() {
			return this.ref;
		}

		public String getLabel() {
			return this.label;
		}

		public String getSha() {
			return this.sha;
		}

		public Repo getRepo() {
			return this.repo;
		}

		public UserInnerInner getUserInnerInner() {
			return this.userInnerInner;
		}

		@Override
		public String toString() {
			return "Head [ " + "ref = " + this.ref + ", " + "label = " + this.label + ", " + "sha = " + this.sha + ", "
					+ "repo = " + this.repo + ", " + "userInnerInner = " + this.userInnerInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Repo {

			public Repo() {
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
				return "Repo [ " + "privateSanitized = " + this.privateSanitized + ", " + "pushedAt = " + this.pushedAt
						+ ", " + "openIssuesCount = " + this.openIssuesCount + ", " + "createdAt = " + this.createdAt
						+ ", " + "description = " + this.description + ", " + "watchers = " + this.watchers + ", "
						+ "language = " + this.language + ", " + "updatedAt = " + this.updatedAt + ", " + "svnUrl = "
						+ this.svnUrl + ", " + "id = " + this.id + ", " + "gitUrl = " + this.gitUrl + ", "
						+ "masterBranch = " + this.masterBranch + ", " + "forks = " + this.forks + ", " + "sshUrl = "
						+ this.sshUrl + ", " + "url = " + this.url + ", " + "mirrorUrl = " + this.mirrorUrl + ", "
						+ "fork = " + this.fork + ", " + "fullName = " + this.fullName + ", " + "size = " + this.size
						+ ", " + "cloneUrl = " + this.cloneUrl + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = "
						+ this.name + ", " + "openIssues = " + this.openIssues + ", " + "watchersCount = "
						+ this.watchersCount + ", " + "forksCount = " + this.forksCount + ", " + "homepage = "
						+ this.homepage + ", " + "ownerInner = " + this.ownerInner + ", " + "]";
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
					return "OwnerInner [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", "
							+ "login = " + this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = "
							+ this.url + ", " + "]";
				}
			}

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
	public static class Base {

		public Base() {
		}

		@JsonProperty("ref")
		private String ref;

		@JsonProperty("label")
		private String label;

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("repo")
		private RepoInner repoInner;

		@JsonProperty("user")
		private UserInner userInner;

		public String getRef() {
			return this.ref;
		}

		public String getLabel() {
			return this.label;
		}

		public String getSha() {
			return this.sha;
		}

		public RepoInner getRepoInner() {
			return this.repoInner;
		}

		public UserInner getUserInner() {
			return this.userInner;
		}

		@Override
		public String toString() {
			return "Base [ " + "ref = " + this.ref + ", " + "label = " + this.label + ", " + "sha = " + this.sha + ", "
					+ "repoInner = " + this.repoInner + ", " + "userInner = " + this.userInner + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class RepoInner {

			public RepoInner() {
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

			public Owner getOwner() {
				return this.owner;
			}

			@Override
			public String toString() {
				return "RepoInner [ " + "privateSanitized = " + this.privateSanitized + ", " + "pushedAt = "
						+ this.pushedAt + ", " + "openIssuesCount = " + this.openIssuesCount + ", " + "createdAt = "
						+ this.createdAt + ", " + "description = " + this.description + ", " + "watchers = "
						+ this.watchers + ", " + "language = " + this.language + ", " + "updatedAt = " + this.updatedAt
						+ ", " + "svnUrl = " + this.svnUrl + ", " + "id = " + this.id + ", " + "gitUrl = " + this.gitUrl
						+ ", " + "masterBranch = " + this.masterBranch + ", " + "forks = " + this.forks + ", "
						+ "sshUrl = " + this.sshUrl + ", " + "url = " + this.url + ", " + "mirrorUrl = "
						+ this.mirrorUrl + ", " + "fork = " + this.fork + ", " + "fullName = " + this.fullName + ", "
						+ "size = " + this.size + ", " + "cloneUrl = " + this.cloneUrl + ", " + "htmlUrl = "
						+ this.htmlUrl + ", " + "name = " + this.name + ", " + "openIssues = " + this.openIssues + ", "
						+ "watchersCount = " + this.watchersCount + ", " + "forksCount = " + this.forksCount + ", "
						+ "homepage = " + this.homepage + ", " + "owner = " + this.owner + ", " + "]";
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
