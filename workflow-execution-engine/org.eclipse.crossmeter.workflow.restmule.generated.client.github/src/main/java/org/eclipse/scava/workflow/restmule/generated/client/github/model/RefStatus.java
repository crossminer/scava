package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefStatus {

	public RefStatus() {
	}

	@JsonProperty("commit_url")
	private String commitUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("repository_url")
	private String repositoryUrl;

	@JsonProperty("state")
	private String state;

	@JsonProperty("sha")
	private String sha;

	@JsonProperty("statuses")
	private List<Statuses> statuses = new ArrayList<Statuses>();

	public String getCommitUrl() {
		return this.commitUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getRepositoryUrl() {
		return this.repositoryUrl;
	}

	public String getState() {
		return this.state;
	}

	public String getSha() {
		return this.sha;
	}

	public List<Statuses> getStatuses() {
		return this.statuses;
	}

	@Override
	public String toString() {
		return "RefStatus [ " + "commitUrl = " + this.commitUrl + ", " + "name = " + this.name + ", "
				+ "repositoryUrl = " + this.repositoryUrl + ", " + "state = " + this.state + ", " + "sha = " + this.sha
				+ ", " + "statuses = " + this.statuses + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Statuses {

		public Statuses() {
		}

		@JsonProperty("updated_at")
		private String updatedAt;

		@JsonProperty("target_url")
		private String targetUrl;

		@JsonProperty("context")
		private String context;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("description")
		private String description;

		@JsonProperty("id")
		private Float id;

		@JsonProperty("state")
		private String state;

		@JsonProperty("url")
		private String url;

		public String getUpdatedAt() {
			return this.updatedAt;
		}

		public String getTargetUrl() {
			return this.targetUrl;
		}

		public String getContext() {
			return this.context;
		}

		public String getCreatedAt() {
			return this.createdAt;
		}

		public String getDescription() {
			return this.description;
		}

		public Float getId() {
			return this.id;
		}

		public String getState() {
			return this.state;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Statuses [ " + "updatedAt = " + this.updatedAt + ", " + "targetUrl = " + this.targetUrl + ", "
					+ "context = " + this.context + ", " + "createdAt = " + this.createdAt + ", " + "description = "
					+ this.description + ", " + "id = " + this.id + ", " + "state = " + this.state + ", " + "url = "
					+ this.url + ", " + "]";
		}
	}

}
