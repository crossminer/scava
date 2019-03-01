package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoCommit {

	public RepoCommit() {
	}

	@JsonProperty("message")
	private String message;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("url")
	private String url;

	@JsonProperty("committer")
	private Committer committer;

	@JsonProperty("author")
	private Author author;

	@JsonProperty("tree")
	private Tree tree;

	@JsonProperty("parents")
	private List<Parents> parents = new ArrayList<Parents>();

	public String getMessage() {
		return this.message;
	}

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

	public Tree getTree() {
		return this.tree;
	}

	public List<Parents> getParents() {
		return this.parents;
	}

	@Override
	public String toString() {
		return "RepoCommit [ " + "message = " + this.message + ", " + "sha = " + this.sha + ", " + "url = " + this.url
				+ ", " + "committer = " + this.committer + ", " + "author = " + this.author + ", " + "tree = "
				+ this.tree + ", " + "parents = " + this.parents + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Committer {

		public Committer() {
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
			return "Committer [ " + "date = " + this.date + ", " + "name = " + this.name + ", " + "email = "
					+ this.email + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Author {

		public Author() {
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
			return "Author [ " + "date = " + this.date + ", " + "name = " + this.name + ", " + "email = " + this.email
					+ ", " + "]";
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
