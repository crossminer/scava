package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamsList {

	public TeamsList() {
	}

	@JsonProperty("repos_count")
	private Integer reposCount;

	@JsonProperty("name")
	private String name;

	@JsonProperty("members_count")
	private Integer membersCount;

	@JsonProperty("permission")
	private String permission;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("organization")
	private Organization organization;

	public Integer getReposCount() {
		return this.reposCount;
	}

	public String getName() {
		return this.name;
	}

	public Integer getMembersCount() {
		return this.membersCount;
	}

	public String getPermission() {
		return this.permission;
	}

	public Integer getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	@Override
	public String toString() {
		return "TeamsList [ " + "reposCount = " + this.reposCount + ", " + "name = " + this.name + ", "
				+ "membersCount = " + this.membersCount + ", " + "permission = " + this.permission + ", " + "id = "
				+ this.id + ", " + "url = " + this.url + ", " + "organization = " + this.organization + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Organization {

		public Organization() {
		}

		@JsonProperty("avatar_url")
		private String avatarUrl;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("login")
		private String login;

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

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Organization [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "url = " + this.url + ", " + "]";
		}
	}

}
