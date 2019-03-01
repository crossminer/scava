package org.eclipse.scava.workflow.restmule.generated.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feeds {

	public Feeds() {
	}

	@JsonProperty("current_user_organization_url")
	private String currentUserOrganizationUrl;

	@JsonProperty("current_user_url")
	private String currentUserUrl;

	@JsonProperty("user_url")
	private String userUrl;

	@JsonProperty("current_user_actor_url")
	private String currentUserActorUrl;

	@JsonProperty("current_user_public")
	private String currentUserPublic;

	@JsonProperty("timeline_url")
	private String timelineUrl;

	@JsonProperty("_links")
	private Links links;

	public String getCurrentUserOrganizationUrl() {
		return this.currentUserOrganizationUrl;
	}

	public String getCurrentUserUrl() {
		return this.currentUserUrl;
	}

	public String getUserUrl() {
		return this.userUrl;
	}

	public String getCurrentUserActorUrl() {
		return this.currentUserActorUrl;
	}

	public String getCurrentUserPublic() {
		return this.currentUserPublic;
	}

	public String getTimelineUrl() {
		return this.timelineUrl;
	}

	public Links getLinks() {
		return this.links;
	}

	@Override
	public String toString() {
		return "Feeds [ " + "currentUserOrganizationUrl = " + this.currentUserOrganizationUrl + ", "
				+ "currentUserUrl = " + this.currentUserUrl + ", " + "userUrl = " + this.userUrl + ", "
				+ "currentUserActorUrl = " + this.currentUserActorUrl + ", " + "currentUserPublic = "
				+ this.currentUserPublic + ", " + "timelineUrl = " + this.timelineUrl + ", " + "links = " + this.links
				+ ", " + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Links {

		public Links() {
		}

		@JsonProperty("current_user_organization")
		private CurrentUserOrganization currentUserOrganization;

		@JsonProperty("current_user_actor")
		private CurrentUserActor currentUserActor;

		@JsonProperty("timeline")
		private Timeline timeline;

		@JsonProperty("user")
		private User user;

		@JsonProperty("current_user_public")
		private CurrentUserPublic currentUserPublic;

		@JsonProperty("current_user")
		private CurrentUser currentUser;

		public CurrentUserOrganization getCurrentUserOrganization() {
			return this.currentUserOrganization;
		}

		public CurrentUserActor getCurrentUserActor() {
			return this.currentUserActor;
		}

		public Timeline getTimeline() {
			return this.timeline;
		}

		public User getUser() {
			return this.user;
		}

		public CurrentUserPublic getCurrentUserPublic() {
			return this.currentUserPublic;
		}

		public CurrentUser getCurrentUser() {
			return this.currentUser;
		}

		@Override
		public String toString() {
			return "Links [ " + "currentUserOrganization = " + this.currentUserOrganization + ", "
					+ "currentUserActor = " + this.currentUserActor + ", " + "timeline = " + this.timeline + ", "
					+ "user = " + this.user + ", " + "currentUserPublic = " + this.currentUserPublic + ", "
					+ "currentUser = " + this.currentUser + ", " + "]";
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class CurrentUserOrganization {

			public CurrentUserOrganization() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "CurrentUserOrganization [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class CurrentUserActor {

			public CurrentUserActor() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "CurrentUserActor [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Timeline {

			public Timeline() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "Timeline [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class User {

			public User() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "User [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class CurrentUserPublic {

			public CurrentUserPublic() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "CurrentUserPublic [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class CurrentUser {

			public CurrentUser() {
			}

			@JsonProperty("href")
			private String href;

			@JsonProperty("type")
			private String type;

			public String getHref() {
				return this.href;
			}

			public String getType() {
				return this.type;
			}

			@Override
			public String toString() {
				return "CurrentUser [ " + "href = " + this.href + ", " + "type = " + this.type + ", " + "]";
			}
		}

	}

}
