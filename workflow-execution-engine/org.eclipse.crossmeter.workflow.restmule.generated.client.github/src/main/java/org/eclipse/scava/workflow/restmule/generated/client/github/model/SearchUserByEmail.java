package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUserByEmail {

	public SearchUserByEmail() {
	}

	@JsonProperty("user")
	private User user;

	public User getUser() {
		return this.user;
	}

	@Override
	public String toString() {
		return "SearchUserByEmail [ " + "user = " + this.user + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {

		public User() {
		}

		@JsonProperty("created")
		private String created;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("blog")
		private String blog;

		@JsonProperty("login")
		private String login;

		@JsonProperty("type")
		private String type;

		@JsonProperty("public_repo_count")
		private Integer publicRepoCount;

		@JsonProperty("following_count")
		private Integer followingCount;

		@JsonProperty("followers_count")
		private Integer followersCount;

		@JsonProperty("name")
		private String name;

		@JsonProperty("company")
		private String company;

		@JsonProperty("location")
		private String location;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("public_gist_count")
		private Integer publicGistCount;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("email")
		private String email;

		public String getCreated() {
			return this.created;
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

		public Integer getPublicRepoCount() {
			return this.publicRepoCount;
		}

		public Integer getFollowingCount() {
			return this.followingCount;
		}

		public Integer getFollowersCount() {
			return this.followersCount;
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

		public Integer getPublicGistCount() {
			return this.publicGistCount;
		}

		public String getGravatarId() {
			return this.gravatarId;
		}

		public String getEmail() {
			return this.email;
		}

		@Override
		public String toString() {
			return "User [ " + "created = " + this.created + ", " + "createdAt = " + this.createdAt + ", " + "blog = "
					+ this.blog + ", " + "login = " + this.login + ", " + "type = " + this.type + ", "
					+ "publicRepoCount = " + this.publicRepoCount + ", " + "followingCount = " + this.followingCount
					+ ", " + "followersCount = " + this.followersCount + ", " + "name = " + this.name + ", "
					+ "company = " + this.company + ", " + "location = " + this.location + ", " + "id = " + this.id
					+ ", " + "publicGistCount = " + this.publicGistCount + ", " + "gravatarId = " + this.gravatarId
					+ ", " + "email = " + this.email + ", " + "]";
		}
	}

}
