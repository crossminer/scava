package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

	public Organization() {
	}

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("blog")
	private String blog;

	@JsonProperty("login")
	private String login;

	@JsonProperty("type")
	private String type;

	@JsonProperty("public_gists")
	private Integer publicGists;

	@JsonProperty("url")
	private String url;

	@JsonProperty("followers")
	private Integer followers;

	@JsonProperty("avatar_url")
	private String avatarUrl;

	@JsonProperty("following")
	private Integer following;

	@JsonProperty("html_url")
	private String htmlUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("company")
	private String company;

	@JsonProperty("location")
	private String location;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("public_repos")
	private Integer publicRepos;

	@JsonProperty("email")
	private String email;

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getBlog() {
		return this.blog;
	}

	public String getLogin() {
		return this.login;
	}

	public String getType() {
		return this.type;
	}

	public Integer getPublicGists() {
		return this.publicGists;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getFollowers() {
		return this.followers;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
	}

	public Integer getFollowing() {
		return this.following;
	}

	public String getHtmlUrl() {
		return this.htmlUrl;
	}

	public String getName() {
		return this.name;
	}

	public String getCompany() {
		return this.company;
	}

	public String getLocation() {
		return this.location;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getPublicRepos() {
		return this.publicRepos;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public String toString() {
		return "Organization [ " + "createdAt = " + this.createdAt + ", " + "blog = " + this.blog + ", " + "login = "
				+ this.login + ", " + "type = " + this.type + ", " + "publicGists = " + this.publicGists + ", "
				+ "url = " + this.url + ", " + "followers = " + this.followers + ", " + "avatarUrl = " + this.avatarUrl
				+ ", " + "following = " + this.following + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = "
				+ this.name + ", " + "company = " + this.company + ", " + "location = " + this.location + ", " + "id = "
				+ this.id + ", " + "publicRepos = " + this.publicRepos + ", " + "email = " + this.email + ", " + "]";
	}
}
