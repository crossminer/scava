package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

	public Project(){}

	@JsonProperty("issues_enabled") 
	private String issuesEnabled;
	
	@JsonProperty("ssh_url_to_repo") 
	private String sshUrlToRepo;
	
	@JsonProperty("only_allow_merge_if_all_discussions_are_resolved") 
	private String onlyAllowMergeIfAllDiscussionsAreResolved;
	
	@JsonProperty("request_access_enabled") 
	private String requestAccessEnabled;
	
	@JsonProperty("open_issues_count") 
	private String openIssuesCount;
	
	@JsonProperty("snippets_enabled") 
	private String snippetsEnabled;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("archived") 
	private String archived;
	
	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("public") 
	private String publicSanitized;
	
	@JsonProperty("tag_list") 
	private String tagList;
	
	@JsonProperty("last_activity_at") 
	private String lastActivityAt;
	
	@JsonProperty("shared_runners_enabled") 
	private String sharedRunnersEnabled;
	
	@JsonProperty("visibility_level") 
	private String visibilityLevel;
	
	@JsonProperty("container_registry_enabled") 
	private String containerRegistryEnabled;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("only_allow_merge_if_build_succeeds") 
	private String onlyAllowMergeIfBuildSucceeds;
	
	@JsonProperty("lfs_enabled") 
	private String lfsEnabled;
	
	@JsonProperty("owner") 
	private Object owner;
	
	@JsonProperty("public_builds") 
	private String publicBuilds;
	
	@JsonProperty("path_with_namespace") 
	private String pathWithNamespace;
	
	@JsonProperty("builds_enabled") 
	private String buildsEnabled;
	
	@JsonProperty("merge_requests_enabled") 
	private String mergeRequestsEnabled;
	
	@JsonProperty("shared_with_groups") 
	private String sharedWithGroups;
	
	@JsonProperty("forked_from_project") 
	private Object forkedFromProject;
	
	@JsonProperty("http_url_to_repo") 
	private String httpUrlToRepo;
	
	@JsonProperty("avatar_url") 
	private String avatarUrl;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("wiki_enabled") 
	private String wikiEnabled;
	
	@JsonProperty("creator_id") 
	private String creatorId;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("namespace") 
	private Object namespace;
	
	@JsonProperty("default_branch") 
	private String defaultBranch;
	
	@JsonProperty("name_with_namespace") 
	private String nameWithNamespace;
	
	@JsonProperty("star_count") 
	private String starCount;
	
	@JsonProperty("forks_count") 
	private String forksCount;
	
	@JsonProperty("runners_token") 
	private String runnersToken;
	
	@JsonProperty("statistics") 
	private Object statistics;
	
	public String getIssuesEnabled() {
		return this.issuesEnabled;
	}
	
	public String getSshUrlToRepo() {
		return this.sshUrlToRepo;
	}
	
	public String getOnlyAllowMergeIfAllDiscussionsAreResolved() {
		return this.onlyAllowMergeIfAllDiscussionsAreResolved;
	}
	
	public String getRequestAccessEnabled() {
		return this.requestAccessEnabled;
	}
	
	public String getOpenIssuesCount() {
		return this.openIssuesCount;
	}
	
	public String getSnippetsEnabled() {
		return this.snippetsEnabled;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getArchived() {
		return this.archived;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getPublicSanitized() {
		return this.publicSanitized;
	}
	
	public String getTagList() {
		return this.tagList;
	}
	
	public String getLastActivityAt() {
		return this.lastActivityAt;
	}
	
	public String getSharedRunnersEnabled() {
		return this.sharedRunnersEnabled;
	}
	
	public String getVisibilityLevel() {
		return this.visibilityLevel;
	}
	
	public String getContainerRegistryEnabled() {
		return this.containerRegistryEnabled;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getOnlyAllowMergeIfBuildSucceeds() {
		return this.onlyAllowMergeIfBuildSucceeds;
	}
	
	public String getLfsEnabled() {
		return this.lfsEnabled;
	}
	
	public Object getOwner() {
		return this.owner;
	}
	
	public String getPublicBuilds() {
		return this.publicBuilds;
	}
	
	public String getPathWithNamespace() {
		return this.pathWithNamespace;
	}
	
	public String getBuildsEnabled() {
		return this.buildsEnabled;
	}
	
	public String getMergeRequestsEnabled() {
		return this.mergeRequestsEnabled;
	}
	
	public String getSharedWithGroups() {
		return this.sharedWithGroups;
	}
	
	public Object getForkedFromProject() {
		return this.forkedFromProject;
	}
	
	public String getHttpUrlToRepo() {
		return this.httpUrlToRepo;
	}
	
	public String getAvatarUrl() {
		return this.avatarUrl;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getWikiEnabled() {
		return this.wikiEnabled;
	}
	
	public String getCreatorId() {
		return this.creatorId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object getNamespace() {
		return this.namespace;
	}
	
	public String getDefaultBranch() {
		return this.defaultBranch;
	}
	
	public String getNameWithNamespace() {
		return this.nameWithNamespace;
	}
	
	public String getStarCount() {
		return this.starCount;
	}
	
	public String getForksCount() {
		return this.forksCount;
	}
	
	public String getRunnersToken() {
		return this.runnersToken;
	}
	
	public Object getStatistics() {
		return this.statistics;
	}
	
	@Override
	public String toString() {
		return "Project [ "
			+ "issuesEnabled = " + this.issuesEnabled + ", "
			+ "sshUrlToRepo = " + this.sshUrlToRepo + ", "
			+ "onlyAllowMergeIfAllDiscussionsAreResolved = " + this.onlyAllowMergeIfAllDiscussionsAreResolved + ", "
			+ "requestAccessEnabled = " + this.requestAccessEnabled + ", "
			+ "openIssuesCount = " + this.openIssuesCount + ", "
			+ "snippetsEnabled = " + this.snippetsEnabled + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "description = " + this.description + ", "
			+ "archived = " + this.archived + ", "
			+ "path = " + this.path + ", "
			+ "publicSanitized = " + this.publicSanitized + ", "
			+ "tagList = " + this.tagList + ", "
			+ "lastActivityAt = " + this.lastActivityAt + ", "
			+ "sharedRunnersEnabled = " + this.sharedRunnersEnabled + ", "
			+ "visibilityLevel = " + this.visibilityLevel + ", "
			+ "containerRegistryEnabled = " + this.containerRegistryEnabled + ", "
			+ "id = " + this.id + ", "
			+ "onlyAllowMergeIfBuildSucceeds = " + this.onlyAllowMergeIfBuildSucceeds + ", "
			+ "lfsEnabled = " + this.lfsEnabled + ", "
			+ "owner = " + this.owner + ", "
			+ "publicBuilds = " + this.publicBuilds + ", "
			+ "pathWithNamespace = " + this.pathWithNamespace + ", "
			+ "buildsEnabled = " + this.buildsEnabled + ", "
			+ "mergeRequestsEnabled = " + this.mergeRequestsEnabled + ", "
			+ "sharedWithGroups = " + this.sharedWithGroups + ", "
			+ "forkedFromProject = " + this.forkedFromProject + ", "
			+ "httpUrlToRepo = " + this.httpUrlToRepo + ", "
			+ "avatarUrl = " + this.avatarUrl + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "wikiEnabled = " + this.wikiEnabled + ", "
			+ "creatorId = " + this.creatorId + ", "
			+ "name = " + this.name + ", "
			+ "namespace = " + this.namespace + ", "
			+ "defaultBranch = " + this.defaultBranch + ", "
			+ "nameWithNamespace = " + this.nameWithNamespace + ", "
			+ "starCount = " + this.starCount + ", "
			+ "forksCount = " + this.forksCount + ", "
			+ "runnersToken = " + this.runnersToken + ", "
			+ "statistics = " + this.statistics + ", "
			+ "]"; 
	}	
}	
