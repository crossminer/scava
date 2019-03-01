package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Branch {

	public Branch() {
	}

	@JsonProperty("name")
	private String name;

	@JsonProperty("_links")
	private Links links;

	@JsonProperty("commit")
	private Commit commit;

	public String getName() {
		return this.name;
	}

	public Links getLinks() {
		return this.links;
	}

	public Commit getCommit() {
		return this.commit;
	}

	@Override
	public String toString() {
		return "Branch [ " + "name = " + this.name + ", " + "links = " + this.links + ", " + "commit = " + this.commit
				+ ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Links {

		public Links() {
		}

		@JsonProperty("self")
		private String self;

		@JsonProperty("html")
		private String html;

		public String getSelf() {
			return this.self;
		}

		public String getHtml() {
			return this.html;
		}

		@Override
		public String toString() {
			return "Links [ " + "self = " + this.self + ", " + "html = " + this.html + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Commit {

		public Commit() {
		}

		@JsonProperty("sha")
		private String sha;

		@JsonProperty("url")
		private String url;

		@JsonProperty("committer")
		private Committer committer;

		@JsonProperty("author")
		private Author author;

		@JsonProperty("commit")
		private CommitInner commitInner;

		@JsonProperty("parents")
		private List<Parents> parents = new ArrayList<Parents>();

		public String getSha() {
			return this.sha;
		}

		public String getUrl() {
			return this.url;
		}

		public Committer getCommitter() {
			return this.committer;
		}

		public Author getAuthor() {
			return this.author;
		}

		public CommitInner getCommitInner() {
			return this.commitInner;
		}

		public List<Parents> getParents() {
			return this.parents;
		}

		@Override
		public String toString() {
			return "Commit [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "committer = "
					+ this.committer + ", " + "author = " + this.author + ", " + "commitInner = " + this.commitInner
					+ ", " + "parents = " + this.parents + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Committer {

			public Committer() {
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
				return "Committer [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Author {

			public Author() {
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
				return "Author [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
						+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", "
						+ "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class CommitInner {

			public CommitInner() {
			}

			@JsonProperty("message")
			private String message;

			@JsonProperty("url")
			private String url;

			@JsonProperty("committer")
			private CommitterInner committerInner;

			@JsonProperty("author")
			private AuthorInner authorInner;

			@JsonProperty("tree")
			private Tree tree;

			public String getMessage() {
				return this.message;
			}

			public String getUrl() {
				return this.url;
			}

			public CommitterInner getCommitterInner() {
				return this.committerInner;
			}

			public AuthorInner getAuthorInner() {
				return this.authorInner;
			}

			public Tree getTree() {
				return this.tree;
			}

			@Override
			public String toString() {
				return "CommitInner [ " + "message = " + this.message + ", " + "url = " + this.url + ", "
						+ "committerInner = " + this.committerInner + ", " + "authorInner = " + this.authorInner + ", "
						+ "tree = " + this.tree + ", " + "]";
			}

			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class CommitterInner {

				public CommitterInner() {
				}

				@JsonProperty("date")
				private String date;

				@JsonProperty("name")
				private String name;

				@JsonProperty("email")
				private String email;

				public String getDate() {
					return this.date;
				}

				public String getName() {
					return this.name;
				}

				public String getEmail() {
					return this.email;
				}

				@Override
				public String toString() {
					return "CommitterInner [ " + "date = " + this.date + ", " + "name = " + this.name + ", "
							+ "email = " + this.email + ", " + "]";
				}
			}

			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class AuthorInner {

				public AuthorInner() {
				}

				@JsonProperty("date")
				private String date;

				@JsonProperty("name")
				private String name;

				@JsonProperty("email")
				private String email;

				public String getDate() {
					return this.date;
				}

				public String getName() {
					return this.name;
				}

				public String getEmail() {
					return this.email;
				}

				@Override
				public String toString() {
					return "AuthorInner [ " + "date = " + this.date + ", " + "name = " + this.name + ", " + "email = "
							+ this.email + ", " + "]";
				}
			}

			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class Tree {

				public Tree() {
				}

				@JsonProperty("sha")
				private String sha;

				@JsonProperty("url")
				private String url;

				public String getSha() {
					return this.sha;
				}

				public String getUrl() {
					return this.url;
				}

				@Override
				public String toString() {
					return "Tree [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "]";
				}
			}

		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Parents {

			public Parents() {
			}

			@JsonProperty("sha")
			private String sha;

			@JsonProperty("url")
			private String url;

			public String getSha() {
				return this.sha;
			}

			public String getUrl() {
				return this.url;
			}

			@Override
			public String toString() {
				return "Parents [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "]";
			}
		}

	}

}
