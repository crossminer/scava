package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssuesByKeyword {

	public SearchIssuesByKeyword() {
	}

	@JsonProperty("issues")
	private List<Issues> issues = new ArrayList<Issues>();

	public List<Issues> getIssues() {
		return this.issues;
	}

	@Override
	public String toString() {
		return "SearchIssuesByKeyword [ " + "issues = " + this.issues + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Issues {

		public Issues() {
		}

		@JsonProperty("comments")
		private Integer comments;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("body")
		private String body;

		@JsonProperty("title")
		private String title;

		@JsonProperty("number")
		private Integer number;

		@JsonProperty("updated_at")
		private String updatedAt;

		@JsonProperty("html_url")
		private String htmlUrl;

		@JsonProperty("votes")
		private Integer votes;

		@JsonProperty("position")
		private Integer position;

		@JsonProperty("state")
		private String state;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("user")
		private String user;

		@JsonProperty("labels")
		private List<String> labels = new ArrayList<String>();

		public Integer getComments() {
			return this.comments;
		}

		public String getCreatedAt() {
			return this.createdAt;
		}

		public String getBody() {
			return this.body;
		}

		public String getTitle() {
			return this.title;
		}

		public Integer getNumber() {
			return this.number;
		}

		public String getUpdatedAt() {
			return this.updatedAt;
		}

		public String getHtmlUrl() {
			return this.htmlUrl;
		}

		public Integer getVotes() {
			return this.votes;
		}

		public Integer getPosition() {
			return this.position;
		}

		public String getState() {
			return this.state;
		}

		public String getGravatarId() {
			return this.gravatarId;
		}

		public String getUser() {
			return this.user;
		}

		public List<String> getLabels() {
			return this.labels;
		}

		@Override
		public String toString() {
			return "Issues [ " + "comments = " + this.comments + ", " + "createdAt = " + this.createdAt + ", "
					+ "body = " + this.body + ", " + "title = " + this.title + ", " + "number = " + this.number + ", "
					+ "updatedAt = " + this.updatedAt + ", " + "htmlUrl = " + this.htmlUrl + ", " + "votes = "
					+ this.votes + ", " + "position = " + this.position + ", " + "state = " + this.state + ", "
					+ "gravatarId = " + this.gravatarId + ", " + "user = " + this.user + ", " + "labels = "
					+ this.labels + ", " + "]";
		}
	}

}
