package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ref {

	public Ref() {
	}

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("target_url")
	private String targetUrl;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("state")
	private String state;

	@JsonProperty("url")
	private String url;

	@JsonProperty("creator")
	private Creator creator;

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public String getTargetUrl() {
		return this.targetUrl;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getState() {
		return this.state;
	}

	public String getUrl() {
		return this.url;
	}

	public Creator getCreator() {
		return this.creator;
	}

	@Override
	public String toString() {
		return "Ref [ " + "updatedAt = " + this.updatedAt + ", " + "targetUrl = " + this.targetUrl + ", "
				+ "createdAt = " + this.createdAt + ", " + "description = " + this.description + ", " + "id = "
				+ this.id + ", " + "state = " + this.state + ", " + "url = " + this.url + ", " + "creator = "
				+ this.creator + ", " + "]";
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
