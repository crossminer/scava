package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Events {

	public Events() {
	}

	@JsonProperty("public")
	private Boolean publicSanitized;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("type")
	private String type;

	@JsonProperty("actor")
	private Actor actor;

	@JsonProperty("org")
	private Org org;

	@JsonProperty("payload")
	private Payload payload;

	@JsonProperty("repo")
	private Repo repo;

	@JsonProperty("created_at")
	private CreatedAt createdAt;

	public Boolean getPublicSanitized() {
		return this.publicSanitized;
	}

	public Integer getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public Actor getActor() {
		return this.actor;
	}

	public Org getOrg() {
		return this.org;
	}

	public Payload getPayload() {
		return this.payload;
	}

	public Repo getRepo() {
		return this.repo;
	}

	public CreatedAt getCreatedAt() {
		return this.createdAt;
	}

	@Override
	public String toString() {
		return "Events [ " + "publicSanitized = " + this.publicSanitized + ", " + "id = " + this.id + ", " + "type = "
				+ this.type + ", " + "actor = " + this.actor + ", " + "org = " + this.org + ", " + "payload = "
				+ this.payload + ", " + "repo = " + this.repo + ", " + "createdAt = " + this.createdAt + ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Actor {

		public Actor() {
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
			return "Actor [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Org {

		public Org() {
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
			return "Org [ " + "avatarUrl = " + this.avatarUrl + ", " + "id = " + this.id + ", " + "login = "
					+ this.login + ", " + "gravatarId = " + this.gravatarId + ", " + "url = " + this.url + ", " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Payload {

		public Payload() {
		}

		@Override
		public String toString() {
			return "Payload [ " + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Repo {

		public Repo() {
		}

		@JsonProperty("name")
		private String name;

		@JsonProperty("id")
		private Integer id;

		@JsonProperty("url")
		private String url;

		public String getName() {
			return this.name;
		}

		public Integer getId() {
			return this.id;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public String toString() {
			return "Repo [ " + "name = " + this.name + ", " + "id = " + this.id + ", " + "url = " + this.url + ", "
					+ "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CreatedAt {

		public CreatedAt() {
		}

		@Override
		public String toString() {
			return "CreatedAt [ " + "]";
		}
	}

}
