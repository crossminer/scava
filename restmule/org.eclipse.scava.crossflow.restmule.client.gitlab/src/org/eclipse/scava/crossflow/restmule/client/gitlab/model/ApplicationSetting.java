package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationSetting {

	public ApplicationSetting(){}

	@JsonProperty("repository_storage") 
	private String repositoryStorage;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("container_registry_token_expire_delay") 
	private String containerRegistryTokenExpireDelay;
	
	@JsonProperty("default_snippet_visibility") 
	private String defaultSnippetVisibility;
	
	@JsonProperty("domain_whitelist") 
	private String domainWhitelist;
	
	@JsonProperty("default_projects_limit") 
	private String defaultProjectsLimit;
	
	@JsonProperty("koding_enabled") 
	private String kodingEnabled;
	
	@JsonProperty("user_oauth_applications") 
	private String userOauthApplications;
	
	@JsonProperty("default_project_visibility") 
	private String defaultProjectVisibility;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("repository_storages") 
	private String repositoryStorages;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("koding_url") 
	private String kodingUrl;
	
	@JsonProperty("home_page_url") 
	private String homePageUrl;
	
	@JsonProperty("signup_enabled") 
	private String signupEnabled;
	
	@JsonProperty("session_expire_delay") 
	private String sessionExpireDelay;
	
	@JsonProperty("signin_enabled") 
	private String signinEnabled;
	
	@JsonProperty("domain_blacklist") 
	private String domainBlacklist;
	
	@JsonProperty("after_sign_out_path") 
	private String afterSignOutPath;
	
	@JsonProperty("restricted_visibility_levels") 
	private String restrictedVisibilityLevels;
	
	@JsonProperty("domain_blacklist_enabled") 
	private String domainBlacklistEnabled;
	
	@JsonProperty("max_attachment_size") 
	private String maxAttachmentSize;
	
	@JsonProperty("plantuml_enabled") 
	private String plantumlEnabled;
	
	@JsonProperty("default_group_visibility") 
	private String defaultGroupVisibility;
	
	@JsonProperty("plantuml_url") 
	private String plantumlUrl;
	
	@JsonProperty("gravatar_enabled") 
	private String gravatarEnabled;
	
	@JsonProperty("sign_in_text") 
	private String signInText;
	
	@JsonProperty("after_sign_up_text") 
	private String afterSignUpText;
	
	@JsonProperty("default_branch_protection") 
	private String defaultBranchProtection;
	
	public String getRepositoryStorage() {
		return this.repositoryStorage;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getContainerRegistryTokenExpireDelay() {
		return this.containerRegistryTokenExpireDelay;
	}
	
	public String getDefaultSnippetVisibility() {
		return this.defaultSnippetVisibility;
	}
	
	public String getDomainWhitelist() {
		return this.domainWhitelist;
	}
	
	public String getDefaultProjectsLimit() {
		return this.defaultProjectsLimit;
	}
	
	public String getKodingEnabled() {
		return this.kodingEnabled;
	}
	
	public String getUserOauthApplications() {
		return this.userOauthApplications;
	}
	
	public String getDefaultProjectVisibility() {
		return this.defaultProjectVisibility;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getRepositoryStorages() {
		return this.repositoryStorages;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getKodingUrl() {
		return this.kodingUrl;
	}
	
	public String getHomePageUrl() {
		return this.homePageUrl;
	}
	
	public String getSignupEnabled() {
		return this.signupEnabled;
	}
	
	public String getSessionExpireDelay() {
		return this.sessionExpireDelay;
	}
	
	public String getSigninEnabled() {
		return this.signinEnabled;
	}
	
	public String getDomainBlacklist() {
		return this.domainBlacklist;
	}
	
	public String getAfterSignOutPath() {
		return this.afterSignOutPath;
	}
	
	public String getRestrictedVisibilityLevels() {
		return this.restrictedVisibilityLevels;
	}
	
	public String getDomainBlacklistEnabled() {
		return this.domainBlacklistEnabled;
	}
	
	public String getMaxAttachmentSize() {
		return this.maxAttachmentSize;
	}
	
	public String getPlantumlEnabled() {
		return this.plantumlEnabled;
	}
	
	public String getDefaultGroupVisibility() {
		return this.defaultGroupVisibility;
	}
	
	public String getPlantumlUrl() {
		return this.plantumlUrl;
	}
	
	public String getGravatarEnabled() {
		return this.gravatarEnabled;
	}
	
	public String getSignInText() {
		return this.signInText;
	}
	
	public String getAfterSignUpText() {
		return this.afterSignUpText;
	}
	
	public String getDefaultBranchProtection() {
		return this.defaultBranchProtection;
	}
	
	@Override
	public String toString() {
		return "ApplicationSetting [ "
			+ "repositoryStorage = " + this.repositoryStorage + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "containerRegistryTokenExpireDelay = " + this.containerRegistryTokenExpireDelay + ", "
			+ "defaultSnippetVisibility = " + this.defaultSnippetVisibility + ", "
			+ "domainWhitelist = " + this.domainWhitelist + ", "
			+ "defaultProjectsLimit = " + this.defaultProjectsLimit + ", "
			+ "kodingEnabled = " + this.kodingEnabled + ", "
			+ "userOauthApplications = " + this.userOauthApplications + ", "
			+ "defaultProjectVisibility = " + this.defaultProjectVisibility + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "repositoryStorages = " + this.repositoryStorages + ", "
			+ "id = " + this.id + ", "
			+ "kodingUrl = " + this.kodingUrl + ", "
			+ "homePageUrl = " + this.homePageUrl + ", "
			+ "signupEnabled = " + this.signupEnabled + ", "
			+ "sessionExpireDelay = " + this.sessionExpireDelay + ", "
			+ "signinEnabled = " + this.signinEnabled + ", "
			+ "domainBlacklist = " + this.domainBlacklist + ", "
			+ "afterSignOutPath = " + this.afterSignOutPath + ", "
			+ "restrictedVisibilityLevels = " + this.restrictedVisibilityLevels + ", "
			+ "domainBlacklistEnabled = " + this.domainBlacklistEnabled + ", "
			+ "maxAttachmentSize = " + this.maxAttachmentSize + ", "
			+ "plantumlEnabled = " + this.plantumlEnabled + ", "
			+ "defaultGroupVisibility = " + this.defaultGroupVisibility + ", "
			+ "plantumlUrl = " + this.plantumlUrl + ", "
			+ "gravatarEnabled = " + this.gravatarEnabled + ", "
			+ "signInText = " + this.signInText + ", "
			+ "afterSignUpText = " + this.afterSignUpText + ", "
			+ "defaultBranchProtection = " + this.defaultBranchProtection + ", "
			+ "]"; 
	}	
}	
