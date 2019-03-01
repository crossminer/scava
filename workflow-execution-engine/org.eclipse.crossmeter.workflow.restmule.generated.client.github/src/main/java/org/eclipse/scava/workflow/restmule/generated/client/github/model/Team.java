package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

	public Team() {
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

	@Override
	public String toString() {
		return "Team [ " + "reposCount = " + this.reposCount + ", " + "name = " + this.name + ", " + "membersCount = "
				+ this.membersCount + ", " + "permission = " + this.permission + ", " + "id = " + this.id + ", "
				+ "url = " + this.url + ", " + "]";
	}
}
