package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repositories {

	public Repositories() {
	}

	@JsonProperty("fork")
	private Boolean fork;

	@JsonProperty("private")
	private Boolean privateSanitized;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("owner")
	private Owner owner;

	public Boolean getFork() {
		return this.fork;
	}

	public Boolean getPrivateSanitized() {
		return this.privateSanitized;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public Owner getOwner() {
		return this.owner;
	}

	@Override
	public String toString() {
		return "Repositories [ " + "fork = " + this.fork + ", " + "privateSanitized = " + this.privateSanitized + ", "
				+ "fullName = " + this.fullName + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = " + this.name
				+ ", " + "description = " + this.description + ", " + "id = " + this.id + ", " + "url = " + this.url
				+ ", " + "owner = " + this.owner + ", " + "]";
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

}
