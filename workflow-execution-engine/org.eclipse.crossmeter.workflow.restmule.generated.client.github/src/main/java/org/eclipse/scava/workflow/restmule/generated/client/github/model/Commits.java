package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commits {

	public Commits() {
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
	private Commit commit;

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

	public Commit getCommit() {
		return this.commit;
	}

	public List<Parents> getParents() {
		return this.parents;
	}

	@Override
	public String toString() {
		return "Commits [ " + "sha = " + this.sha + ", " + "url = " + this.url + ", " + "committer = " + this.committer
				+ ", " + "author = " + this.author + ", " + "commit = " + this.commit + ", " + "parents = "
				+ this.parents + ", " + "]";
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
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
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
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Commit {

		public Commit() {
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
			return "Commit [ " + "message = " + this.message + ", " + "url = " + this.url + ", " + "committerInner = "
					+ this.committerInner + ", " + "authorInner = " + this.authorInner + ", " + "tree = " + this.tree
					+ ", " + "]";
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
				return "CommitterInner [ " + "date = " + this.date + ", " + "name = " + this.name + ", " + "email = "
						+ this.email + ", " + "]";
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
