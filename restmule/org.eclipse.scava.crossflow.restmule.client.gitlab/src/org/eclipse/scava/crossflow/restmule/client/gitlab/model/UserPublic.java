package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPublic {

	public UserPublic(){}

	@JsonProperty("can_create_project") 
	private String canCreateProject;
	
	@JsonProperty("theme_id") 
	private String themeId;
	
	@JsonProperty("bio") 
	private String bio;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("projects_limit") 
	private String projectsLimit;
	
	@JsonProperty("linkedin") 
	private String linkedin;
	
	@JsonProperty("can_create_group") 
	private String canCreateGroup;
	
	@JsonProperty("is_admin") 
	private String isAdmin;
	
	@JsonProperty("skype") 
	private String skype;
	
	@JsonProperty("twitter") 
	private String twitter;
	
	@JsonProperty("identities") 
	private Object identities;
	
	@JsonProperty("color_scheme_id") 
	private String colorSchemeId;
	
	@JsonProperty("last_sign_in_at") 
	private String lastSignInAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("confirmed_at") 
	private String confirmedAt;
	
	@JsonProperty("email") 
	private String email;
	
	@JsonProperty("current_sign_in_at") 
	private String currentSignInAt;
	
	@JsonProperty("two_factor_enabled") 
	private String twoFactorEnabled;
	
	@JsonProperty("external") 
	private String external;
	
	@JsonProperty("avatar_url") 
	private String avatarUrl;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("website_url") 
	private String websiteUrl;
	
	@JsonProperty("organization") 
	private String organization;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("location") 
	private String location;
	
	@JsonProperty("username") 
	private String username;
	
	public String getCanCreateProject() {
		return this.canCreateProject;
	}
	
	public String getThemeId() {
		return this.themeId;
	}
	
	public String getBio() {
		return this.bio;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getProjectsLimit() {
		return this.projectsLimit;
	}
	
	public String getLinkedin() {
		return this.linkedin;
	}
	
	public String getCanCreateGroup() {
		return this.canCreateGroup;
	}
	
	public String getIsAdmin() {
		return this.isAdmin;
	}
	
	public String getSkype() {
		return this.skype;
	}
	
	public String getTwitter() {
		return this.twitter;
	}
	
	public Object getIdentities() {
		return this.identities;
	}
	
	public String getColorSchemeId() {
		return this.colorSchemeId;
	}
	
	public String getLastSignInAt() {
		return this.lastSignInAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getConfirmedAt() {
		return this.confirmedAt;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCurrentSignInAt() {
		return this.currentSignInAt;
	}
	
	public String getTwoFactorEnabled() {
		return this.twoFactorEnabled;
	}
	
	public String getExternal() {
		return this.external;
	}
	
	public String getAvatarUrl() {
		return this.avatarUrl;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getWebsiteUrl() {
		return this.websiteUrl;
	}
	
	public String getOrganization() {
		return this.organization;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString() {
		return "UserPublic [ "
			+ "canCreateProject = " + this.canCreateProject + ", "
			+ "themeId = " + this.themeId + ", "
			+ "bio = " + this.bio + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "projectsLimit = " + this.projectsLimit + ", "
			+ "linkedin = " + this.linkedin + ", "
			+ "canCreateGroup = " + this.canCreateGroup + ", "
			+ "isAdmin = " + this.isAdmin + ", "
			+ "skype = " + this.skype + ", "
			+ "twitter = " + this.twitter + ", "
			+ "identities = " + this.identities + ", "
			+ "colorSchemeId = " + this.colorSchemeId + ", "
			+ "lastSignInAt = " + this.lastSignInAt + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "confirmedAt = " + this.confirmedAt + ", "
			+ "email = " + this.email + ", "
			+ "currentSignInAt = " + this.currentSignInAt + ", "
			+ "twoFactorEnabled = " + this.twoFactorEnabled + ", "
			+ "external = " + this.external + ", "
			+ "avatarUrl = " + this.avatarUrl + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "websiteUrl = " + this.websiteUrl + ", "
			+ "organization = " + this.organization + ", "
			+ "name = " + this.name + ", "
			+ "location = " + this.location + ", "
			+ "username = " + this.username + ", "
			+ "]"; 
	}	
}	
