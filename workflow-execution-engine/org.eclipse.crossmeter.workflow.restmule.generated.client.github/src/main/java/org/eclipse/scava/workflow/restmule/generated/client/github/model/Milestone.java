package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Milestone {

	public Milestone() {
	}

	@JsonProperty("number")
	private Integer number;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("state")
	private String state;

	@JsonProperty("closed_issues")
	private Integer closedIssues;

	@JsonProperty("open_issues")
	private Integer openIssues;

	@JsonProperty("title")
	private String title;

	@JsonProperty("due_on")
	private String dueOn;

	@JsonProperty("url")
	private String url;

	@JsonProperty("creator")
	private Creator creator;

	public Integer getNumber() {
		return this.number;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public String getState() {
		return this.state;
	}

	public Integer getClosedIssues() {
		return this.closedIssues;
	}

	public Integer getOpenIssues() {
		return this.openIssues;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDueOn() {
		return this.dueOn;
	}

	public String getUrl() {
		return this.url;
	}

	public Creator getCreator() {
		return this.creator;
	}

	@Override
	public String toString() {
		return "Milestone [ " + "number = " + this.number + ", " + "createdAt = " + this.createdAt + ", "
				+ "description = " + this.description + ", " + "state = " + this.state + ", " + "closedIssues = "
				+ this.closedIssues + ", " + "openIssues = " + this.openIssues + ", " + "title = " + this.title + ", "
				+ "dueOn = " + this.dueOn + ", " + "url = " + this.url + ", " + "creator = " + this.creator + ", "
				+ "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Creator {

		public Creator() {
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
			return "Creator [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
