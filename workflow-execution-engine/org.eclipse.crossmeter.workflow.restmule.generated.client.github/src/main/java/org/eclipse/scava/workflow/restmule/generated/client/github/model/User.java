package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	public User() {
	}

	@JsonProperty("bio")
	private String bio;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("blog")
	private String blog;

	@JsonProperty("login")
	private String login;

	@JsonProperty("type")
	private String type;

	@JsonProperty("private_gists")
	private Integer privateGists;

	@JsonProperty("total_private_repos")
	private Integer totalPrivateRepos;

	@JsonProperty("collaborators")
	private Integer collaborators;

	@JsonProperty("disk_usage")
	private Integer diskUsage;

	@JsonProperty("company")
	private String company;

	@JsonProperty("owned_private_repos")
	private Integer ownedPrivateRepos;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("public_repos")
	private Integer publicRepos;

	@JsonProperty("gravatar_id")
	private String gravatarId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("hireable")
	private Boolean hireable;

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

	@JsonProperty("location")
	private String location;

	@JsonProperty("plan")
	private Plan plan;

	public String getBio() {
		return this.bio;
	}

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

	public Integer getPrivateGists() {
		return this.privateGists;
	}

	public Integer getTotalPrivateRepos() {
		return this.totalPrivateRepos;
	}

	public Integer getCollaborators() {
		return this.collaborators;
	}

	public Integer getDiskUsage() {
		return this.diskUsage;
	}

	public String getCompany() {
		return this.company;
	}

	public Integer getOwnedPrivateRepos() {
		return this.ownedPrivateRepos;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getPublicRepos() {
		return this.publicRepos;
	}

	public String getGravatarId() {
		return this.gravatarId;
	}

	public String getEmail() {
		return this.email;
	}

	public Boolean getHireable() {
		return this.hireable;
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

	public String getLocation() {
		return this.location;
	}

	public Plan getPlan() {
		return this.plan;
	}

	@Override
	public String toString() {
		return "User [ " + "bio = " + this.bio + ", " + "createdAt = " + this.createdAt + ", " + "blog = " + this.blog
				+ ", " + "login = " + this.login + ", " + "type = " + this.type + ", " + "privateGists = "
				+ this.privateGists + ", " + "totalPrivateRepos = " + this.totalPrivateRepos + ", " + "collaborators = "
				+ this.collaborators + ", " + "diskUsage = " + this.diskUsage + ", " + "company = " + this.company
				+ ", " + "ownedPrivateRepos = " + this.ownedPrivateRepos + ", " + "id = " + this.id + ", "
				+ "publicRepos = " + this.publicRepos + ", " + "gravatarId = " + this.gravatarId + ", " + "email = "
				+ this.email + ", " + "hireable = " + this.hireable + ", " + "publicGists = " + this.publicGists + ", "
				+ "url = " + this.url + ", " + "followers = " + this.followers + ", " + "avatarUrl = " + this.avatarUrl
				+ ", " + "following = " + this.following + ", " + "htmlUrl = " + this.htmlUrl + ", " + "name = "
				+ this.name + ", " + "location = " + this.location + ", " + "plan = " + this.plan + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Plan {

		public Plan() {
		}

		@JsonProperty("private_repos")
		private Integer privateRepos;

		@JsonProperty("name")
		private String name;

		@JsonProperty("collaborators")
		private Integer collaborators;

		@JsonProperty("space")
		private Integer space;

		public Integer getPrivateRepos() {
			return this.privateRepos;
		}

		public String getName() {
			return this.name;
		}

		public Integer getCollaborators() {
			return this.collaborators;
		}

		public Integer getSpace() {
			return this.space;
		}

		@Override
		public String toString() {
			return "Plan [ " + "privateRepos = " + this.privateRepos + ", " + "name = " + this.name + ", "
					+ "collaborators = " + this.collaborators + ", " + "space = " + this.space + ", " + "]";
		}
	}

}
