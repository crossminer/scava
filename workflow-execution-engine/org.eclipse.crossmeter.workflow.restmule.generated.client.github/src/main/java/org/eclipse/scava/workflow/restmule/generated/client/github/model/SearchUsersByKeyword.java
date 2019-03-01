package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchUsersByKeyword {

	public SearchUsersByKeyword() {
	}

	@JsonProperty("users")
	private List<Users> users = new ArrayList<Users>();

	public List<Users> getUsers() {
		return this.users;
	}

	@Override
	public String toString() {
		return "SearchUsersByKeyword [ " + "users = " + this.users + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Users {

		public Users() {
		}

		@JsonProperty("created")
		private String created;

		@JsonProperty("created_at")
		private String createdAt;

		@JsonProperty("language")
		private String language;

		@JsonProperty("login")
		private String login;

		@JsonProperty("type")
		private String type;

		@JsonProperty("public_repo_count")
		private Integer publicRepoCount;

		@JsonProperty("score")
		private Float score;

		@JsonProperty("followers")
		private Integer followers;

		@JsonProperty("followers_count")
		private Integer followersCount;

		@JsonProperty("repos")
		private Integer repos;

		@JsonProperty("name")
		private String name;

		@JsonProperty("location")
		private String location;

		@JsonProperty("fullname")
		private String fullname;

		@JsonProperty("id")
		private String id;

		@JsonProperty("gravatar_id")
		private String gravatarId;

		@JsonProperty("username")
		private String username;

		public String getCreated() {
			return this.created;
		}

		public String getCreatedAt() {
			return this.createdAt;
		}

		public String getLanguage() {
			return this.language;
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

		public Float getScore() {
			return this.score;
		}

		public Integer getFollowers() {
			return this.followers;
		}

		public Integer getFollowersCount() {
			return this.followersCount;
		}

		public Integer getRepos() {
			return this.repos;
		}

		public String getName() {
			return this.name;
		}

		public String getLocation() {
			return this.location;
		}

		public String getFullname() {
			return this.fullname;
		}

		public String getId() {
			return this.id;
		}

		public String getGravatarId() {
			return this.gravatarId;
		}

		public String getUsername() {
			return this.username;
		}

		@Override
		public String toString() {
			return "Users [ " + "created = " + this.created + ", " + "createdAt = " + this.createdAt + ", "
					+ "language = " + this.language + ", " + "login = " + this.login + ", " + "type = " + this.type
					+ ", " + "publicRepoCount = " + this.publicRepoCount + ", " + "score = " + this.score + ", "
					+ "followers = " + this.followers + ", " + "followersCount = " + this.followersCount + ", "
					+ "repos = " + this.repos + ", " + "name = " + this.name + ", " + "location = " + this.location
					+ ", " + "fullname = " + this.fullname + ", " + "id = " + this.id + ", " + "gravatarId = "
					+ this.gravatarId + ", " + "username = " + this.username + ", " + "]";
		}
	}

}
