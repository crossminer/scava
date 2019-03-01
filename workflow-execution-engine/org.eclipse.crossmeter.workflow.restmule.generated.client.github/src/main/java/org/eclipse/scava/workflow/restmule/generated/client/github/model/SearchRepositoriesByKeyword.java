package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRepositoriesByKeyword {

	public SearchRepositoriesByKeyword() {
	}

	@JsonProperty("repositories")
	private List<Repositories> repositories = new ArrayList<Repositories>();

	public List<Repositories> getRepositories() {
		return this.repositories;
	}

	@Override
	public String toString() {
		return "SearchRepositoriesByKeyword [ " + "repositories = " + this.repositories + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repositories {

		public Repositories() {
		}

		@JsonProperty("forks")
		private Integer forks;

		@JsonProperty("owner")
		private String owner;

		@JsonProperty("private")
		private Boolean privateSanitized;

		@JsonProperty("has_downloads")
		private Boolean hasDownloads;

		@JsonProperty("pushed_at")
		private String pushedAt;

		@JsonProperty("created")
		private String created;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("description")
		private String description;

		@JsonProperty("watchers")
		private Integer watchers;

		@JsonProperty("language")
		private String language;

		@JsonProperty("type")
		private String type;

		@JsonProperty("has_issues")
		private Boolean hasIssues;

		@JsonProperty("url")
		private String url;

		@JsonProperty("score")
		private Float score;

		@JsonProperty("fork")
		private Boolean fork;

		@JsonProperty("followers")
		private Integer followers;

		@JsonProperty("has_wiki")
		private Boolean hasWiki;

		@JsonProperty("size")
		private Integer size;

		@JsonProperty("name")
		private String name;

		@JsonProperty("open_issues")
		private Integer openIssues;

		@JsonProperty("pushed")
		private String pushed;

		@JsonProperty("homepage")
		private String homepage;

		@JsonProperty("username")
		private String username;

		public Integer getForks() {
			return this.forks;
		}

		public String getOwner() {
			return this.owner;
		}

		public Boolean getPrivateSanitized() {
			return this.privateSanitized;
		}

		public Boolean getHasDownloads() {
			return this.hasDownloads;
		}

		public String getPushedAt() {
			return this.pushedAt;
		}

		public String getCreated() {
			return this.created;
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

		public String getType() {
			return this.type;
		}

		public Boolean getHasIssues() {
			return this.hasIssues;
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

		public Integer getFollowers() {
			return this.followers;
		}

		public Boolean getHasWiki() {
			return this.hasWiki;
		}

		public Integer getSize() {
			return this.size;
		}

		public String getName() {
			return this.name;
		}

		public Integer getOpenIssues() {
			return this.openIssues;
		}

		public String getPushed() {
			return this.pushed;
		}

		public String getHomepage() {
			return this.homepage;
		}

		public String getUsername() {
			return this.username;
		}

		@Override
		public String toString() {
			return "Repositories [ " + "forks = " + this.forks + ", " + "owner = " + this.owner + ", "
					+ "privateSanitized = " + this.privateSanitized + ", " + "hasDownloads = " + this.hasDownloads
					+ ", " + "pushedAt = " + this.pushedAt + ", " + "created = " + this.created + ", " + "createdAt = "
					+ this.createdAt + ", " + "description = " + this.description + ", " + "watchers = " + this.watchers
					+ ", " + "language = " + this.language + ", " + "type = " + this.type + ", " + "hasIssues = "
					+ this.hasIssues + ", " + "url = " + this.url + ", " + "score = " + this.score + ", " + "fork = "
					+ this.fork + ", " + "followers = " + this.followers + ", " + "hasWiki = " + this.hasWiki + ", "
					+ "size = " + this.size + ", " + "name = " + this.name + ", " + "openIssues = " + this.openIssues
					+ ", " + "pushed = " + this.pushed + ", " + "homepage = " + this.homepage + ", " + "username = "
					+ this.username + ", " + "]";
		}
	}

}
