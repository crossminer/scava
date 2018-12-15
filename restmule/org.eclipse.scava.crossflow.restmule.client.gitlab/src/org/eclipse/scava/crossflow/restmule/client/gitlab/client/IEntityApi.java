package org.eclipse.scava.crossflow.restmule.client.gitlab.client;

import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.client.gitlab.model.*;

public interface IEntityApi {
	
	/**
	 * null
	 * @param id The ID of the project
	 * @return Get a specific project's deploy keys
	 * @path /v3/projects/{id}/keys 
	 */		
	IData<SSHKey> getV3ProjectsKeysSSHKey(String id);
	
	/**
	 * null
	 * @param popular If passed, returns only popular licenses
	 * @return Get the list of the available license template
	 * @path /v3/templates/licenses 
	 */		
	IData<RepoLicense> getV3TemplatesLicensesRepoLicense(Boolean popular);
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/gitlab_ci_ymls 
	 */		
	IData<TemplatesList> getV3Gitlab_ci_ymlsTemplatesList();
	
	/**
	 * null
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @return Gets starred project for the authenticated user
	 * @path /v3/projects/starred 
	 */		
	IData<BasicProjectDetails> getV3ProjectsStarredBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId The ID of an Issue, Merge Request or Snippet
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmoji(String id, Integer mergeRequestId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param issueId The ID of an Issue, Merge Request or Snippet
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/issues/{issue_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmoji(String id, Integer issueId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId null
	 * @return Show the merge request changes
	 * @path /v3/projects/{id}/merge_request/{merge_request_id}/changes 
	 */		
	IData<MergeRequestChanges> getV3ProjectsMerge_requestChangesMergeRequestChanges(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @return Get global notification level settings and email, defaults to Participate
	 * @path /v3/notification_settings 
	 */		
	IData<GlobalNotificationSetting> getV3Notification_settingsGlobalNotificationSetting();
	
	/**
	 * null
	 * @param id The project ID
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get all environments of the project
	 * @path /v3/projects/{id}/environments 
	 */		
	IData<Environment> getV3ProjectsEnvironmentsEnvironment(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The project ID
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param scope Either running, branches, or tags
	 * @return Get all Pipelines of the project
	 * @path /v3/projects/{id}/pipelines 
	 */		
	IData<Pipeline> getV3ProjectsPipelinesPipeline(String id, Integer page, Integer perPage, String scope);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param mergeRequestId null
	 * @return List issues that will be closed on merge
	 * @path /v3/projects/{id}/merge_request/{merge_request_id}/closes_issues 
	 */		
	IData<MRNote> getV3ProjectsMerge_requestCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get a project repository tags
	 * @path /v3/projects/{id}/repository/tags 
	 */		
	IData<RepoTag> getV3ProjectsRepositoryTagsRepoTag(String id);
	
	/**
	 * null
	 * @param id The ID of a group
	 * @param state Return opened, closed, or all issues
	 * @param labels Comma-separated list of label names
	 * @param milestone Return issues for a specific milestone
	 * @param orderBy Return issues ordered by `created_at` or `updated_at` fields.
	 * @param sort Return issues sorted in `asc` or `desc` order.
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of group issues
	 * @path /v3/groups/{id}/issues 
	 */		
	IData<Issue> getV3GroupsIssuesIssue(String id, String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage);
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/templates/gitignores 
	 */		
	IData<TemplatesList> getV3TemplatesGitignoresTemplatesList();
	
	/**
	 * null
	 * @param username Get a single user with a specific username
	 * @param search Search for a username
	 * @param active Filters only active users
	 * @param external Filters only external users
	 * @param blocked Filters only blocked users
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get the list of users
	 * @path /v3/users 
	 */		
	IData<UserBasic> getV3UsersUserBasic(String username, String search, Boolean active, Boolean external, Boolean blocked, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The project ID
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Gets a list of access requests for a project.
	 * @path /v3/projects/{id}/access_requests 
	 */		
	IData<AccessRequester> getV3ProjectsAccess_requestsAccessRequester(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteableId The ID of the noteable
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +noteable+ notes
	 * @path /v3/projects/{id}/issues/{noteable_id}/notes 
	 */		
	IData<Note> getV3ProjectsIssuesNotesNote(String id, Integer noteableId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param mergeRequestId null
	 * @param noteId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/notes/{note_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId, Integer noteId);
	
	/**
	 * null
	 * @param popular If passed, returns only popular licenses
	 * @return Get the list of the available license template
	 * @path /v3/licenses 
	 */		
	IData<RepoLicense> getV3LicensesRepoLicense(Boolean popular);
	
	/**
	 * null
	 * @param id The ID of a group
	 * @return Get a single group, with containing projects.
	 * @path /v3/groups/{id} 
	 */		
	IData<GroupDetail> getV3GroupsGroupDetailById(String id);
	
	/**
	 * null
	 * @param id The group ID or project ID or project NAMESPACE/PROJECT_NAME
	 * @return Get project level notification level settings, defaults to Global
	 * @path /v3/projects/{id}/notification_settings 
	 */		
	IData<NotificationSetting> getV3ProjectsNotification_settingsNotificationSetting(String id);
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/templates/dockerfiles 
	 */		
	IData<TemplatesList> getV3TemplatesDockerfilesTemplatesList();
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a snippets list for authenticated user
	 * @path /v3/snippets 
	 */		
	IData<PersonalSnippet> getV3SnippetsPersonalSnippet(Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of the user
	 * @return Get the SSH keys of a specified user. Available only for admins.
	 * @path /v3/users/{id}/keys 
	 */		
	IData<SSHKey> getV3UsersKeysSSHKey(Integer id);
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/templates/gitlab_ci_ymls 
	 */		
	IData<TemplatesList> getV3TemplatesGitlab_ci_ymlsTemplatesList();
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param buildId The ID of a build
	 * @return Get a specific build of a project
	 * @path /v3/projects/{id}/builds/{build_id} 
	 */		
	IData<Build> getV3ProjectsBuildsBuildByBuildId(String id, Integer buildId);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param issueId null
	 * @param noteId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/issues/{issue_id}/notes/{note_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId, Integer noteId);
	
	/**
	 * null
	 * @param id The group ID
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Gets a list of access requests for a group.
	 * @path /v3/groups/{id}/access_requests 
	 */		
	IData<AccessRequester> getV3GroupsAccess_requestsAccessRequester(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param key The key of the variable
	 * @return Get a specific variable from a project
	 * @path /v3/projects/{id}/variables/{key} 
	 */		
	IData<Variable> getV3ProjectsVariablesVariableByKey(String id, String key);
	
	/**
	 * null
	 * @param id The project ID
	 * @param pipelineId The pipeline ID
	 * @return Gets a specific pipeline for the project
	 * @path /v3/projects/{id}/pipelines/{pipeline_id} 
	 */		
	IData<Pipeline> getV3ProjectsPipelinesPipelineByPipelineId(String id, Integer pipelineId);
	
	/**
	 * null
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @return Get a list of visible projects for authenticated user
	 * @path /v3/projects/visible 
	 */		
	IData<BasicProjectDetails> getV3ProjectsVisibleBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple);
	
	/**
	 * null
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @return Get a projects list for authenticated user
	 * @path /v3/projects 
	 */		
	IData<BasicProjectDetails> getV3ProjectsBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param mergeRequestId null
	 * @return Get the comments of a merge request
	 * @path /v3/projects/{id}/merge_request/{merge_request_id}/comments 
	 */		
	IData<MRNote> getV3ProjectsMerge_requestCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get events for a single project
	 * @path /v3/projects/{id}/events 
	 */		
	IData<Event> getV3ProjectsEventsEvent(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get all labels of the project
	 * @path /v3/projects/{id}/labels 
	 */		
	IData<Label> getV3ProjectsLabelsLabel(String id);
	
	/**
	 * null
	 * @param id The project ID
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get all deployments of the project
	 * @path /v3/projects/{id}/deployments 
	 */		
	IData<Deployment> getV3ProjectsDeploymentsDeployment(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param query The project name to be searched
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Search for projects the current user has access to
	 * @path /v3/projects/search/{query} 
	 */		
	IData<Project> getV3ProjectsSearchProjectByQuery(String query, String orderBy, String sort, Integer page, Integer perPage);
	
	/**
	 * null
	 * @return Get the list of system hooks
	 * @path /v3/hooks 
	 */		
	IData<Hook> getV3HooksHook();
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param hookId The ID of a project hook
	 * @return Get a project hook
	 * @path /v3/projects/{id}/hooks/{hook_id} 
	 */		
	IData<ProjectHook> getV3ProjectsHooksProjectHookByHookId(String id, Integer hookId);
	
	/**
	 * null
	 * @param emailId The ID of the email
	 * @return Get a single email address owned by the currently authenticated user
	 * @path /v3/user/emails/{email_id} 
	 */		
	IData<Email> getV3UserEmailsEmailByEmailId(Integer emailId);
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/dockerfiles 
	 */		
	IData<TemplatesList> getV3DockerfilesTemplatesList();
	
	/**
	 * null
	 * @param id The group ID
	 * @param userId The user ID of the member
	 * @return Gets a member of a group or project.
	 * @path /v3/groups/{id}/members/{user_id} 
	 */		
	IData<Member> getV3GroupsMembersMemberByUserId(String id, Integer userId);
	
	/**
	 * null
	 * @param scope The scope of specific runners to show
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get all runners - shared and specific
	 * @path /v3/runners/all 
	 */		
	IData<Runner> getV3RunnersAllRunner(String scope, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param milestoneId The ID of a project milestone
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get all issues for a single project milestone
	 * @path /v3/projects/{id}/milestones/{milestone_id}/issues 
	 */		
	IData<Issue> getV3ProjectsMilestonesIssuesIssue(String id, Integer milestoneId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @return Get the currently authenticated user's email addresses
	 * @path /v3/user/emails 
	 */		
	IData<Email> getV3UserEmailsEmail();
	
	/**
	 * null
	 * @return Get the current application settings
	 * @path /v3/application/settings 
	 */		
	IData<ApplicationSetting> getV3ApplicationSettingsApplicationSetting();
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param mergeRequestId null
	 * @return Get the comments of a merge request
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/comments 
	 */		
	IData<MRNote> getV3ProjectsMerge_requestsCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param refName The name of a repository branch or tag, if not given the default branch is used
	 * @param path The path of the tree
	 * @param recursive Used to get a recursive tree
	 * @return Get a project repository tree
	 * @path /v3/projects/{id}/repository/tree 
	 */		
	IData<RepoTreeObject> getV3ProjectsRepositoryTreeRepoTreeObject(String id, String refName, String path, Boolean recursive);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteId The ID of a note
	 * @param noteableId The ID of the noteable
	 * @return Get a single +noteable+ note
	 * @path /v3/projects/{id}/snippets/{noteable_id}/notes/{note_id} 
	 */		
	IData<Note> getV3ProjectsSnippetsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get project hooks
	 * @path /v3/projects/{id}/hooks 
	 */		
	IData<ProjectHook> getV3ProjectsHooksProjectHook(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get a project repository branches
	 * @path /v3/projects/{id}/repository/branches 
	 */		
	IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranch(String id);
	
	/**
	 * null
	 * @return Get the currently authenticated user's SSH keys
	 * @path /v3/user/keys 
	 */		
	IData<SSHKey> getV3UserKeysSSHKey();
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific license
	 * @path /v3/licenses/{name} 
	 */		
	IData<RepoLicense> getV3LicensesRepoLicenseByName(String name);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId null
	 * @return Get a single merge request
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id} 
	 */		
	IData<MergeRequest> getV3ProjectsMerge_requestsMergeRequestByMergeRequestId(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param scope The scope of builds to show
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a project builds
	 * @path /v3/projects/{id}/builds 
	 */		
	IData<Build> getV3ProjectsBuildsBuild(String id, String scope, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of the user
	 * @return Get the emails addresses of a specified user. Available only for admins.
	 * @path /v3/users/{id}/emails 
	 */		
	IData<Email> getV3UsersEmailsEmail(Integer id);
	
	/**
	 * null
	 * @param id The ID of a snippet
	 * @return Get a single snippet
	 * @path /v3/snippets/{id} 
	 */		
	IData<PersonalSnippet> getV3SnippetsPersonalSnippetById(Integer id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get all project boards
	 * @path /v3/projects/{id}/boards 
	 */		
	IData<Board> getV3ProjectsBoardsBoard(String id);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param statistics Include project statistics
	 * @return Get list of owned groups for authenticated user
	 * @path /v3/groups/owned 
	 */		
	IData<Group> getV3GroupsOwnedGroup(Integer page, Integer perPage, Boolean statistics);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param id null
	 * @param issueId null
	 * @param noteId null
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/issues/{issue_id}/notes/{note_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer issueId, Integer noteId);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/templates/dockerfiles/{name} 
	 */		
	IData<Template> getV3TemplatesDockerfilesTemplateByName(String name);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId The ID of a merge request
	 * @param versionId The ID of a merge request diff version
	 * @return Get a single merge request diff version
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/versions/{version_id} 
	 */		
	IData<MergeRequestDiffFull> getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId(String id, Integer mergeRequestId, Integer versionId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId null
	 * @return Get the commits of a merge request
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/commits 
	 */		
	IData<RepoCommit> getV3ProjectsMerge_requestsCommitsRepoCommit(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId null
	 * @return Show the merge request changes
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/changes 
	 */		
	IData<MergeRequestChanges> getV3ProjectsMerge_requestsChangesMergeRequestChanges(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param sha The SHA id of a commit
	 * @param scope The scope of builds to show
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get builds for a specific commit of a project
	 * @path /v3/projects/{id}/repository/commits/{sha}/builds 
	 */		
	IData<Build> getV3ProjectsRepositoryCommitsBuildsBuild(String id, String sha, String scope, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param refName The name of a repository branch or tag, if not given the default branch is used
	 * @param since Only commits after or in this date will be returned
	 * @param until Only commits before or in this date will be returned
	 * @param page The page for pagination
	 * @param perPage The number of results per page
	 * @param path The file path
	 * @return Get a project repository commits
	 * @path /v3/projects/{id}/repository/commits 
	 */		
	IData<RepoCommit> getV3ProjectsRepositoryCommitsRepoCommit(String id, String refName, String since, String until, Integer page, Integer perPage, String path);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get project variables
	 * @path /v3/projects/{id}/variables 
	 */		
	IData<Variable> getV3ProjectsVariablesVariable(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a todo list
	 * @path /v3/todos 
	 */		
	IData<Todo> getV3TodosTodo(Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteId The ID of a note
	 * @param noteableId The ID of the noteable
	 * @return Get a single +noteable+ note
	 * @path /v3/projects/{id}/issues/{noteable_id}/notes/{note_id} 
	 */		
	IData<Note> getV3ProjectsIssuesNotesNoteByNoteId(String id, Integer noteId, Integer noteableId);
	
	/**
	 * null
	 * @param scope The scope of specific runners to show
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get runners available for user
	 * @path /v3/runners 
	 */		
	IData<Runner> getV3RunnersRunner(String scope, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param keyId The ID of the SSH key
	 * @return Get a single key owned by currently authenticated user
	 * @path /v3/user/keys/{key_id} 
	 */		
	IData<SSHKey> getV3UserKeysSSHKeyByKeyId(Integer keyId);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific license
	 * @path /v3/templates/licenses/{name} 
	 */		
	IData<RepoLicense> getV3TemplatesLicensesRepoLicenseByName(String name);
	
	/**
	 * null
	 * @param id The ID of the user
	 * @return Get a single user
	 * @path /v3/users/{id} 
	 */		
	IData<UserBasic> getV3UsersUserBasicById(Integer id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param issueId The ID of a project issue
	 * @return Get a single project issue
	 * @path /v3/projects/{id}/issues/{issue_id} 
	 */		
	IData<Issue> getV3ProjectsIssuesIssueByIssueId(String id, Integer issueId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param sha A commit sha, or the name of a branch or tag
	 * @return Get a commit's comments
	 * @path /v3/projects/{id}/repository/commits/{sha}/comments 
	 */		
	IData<CommitNote> getV3ProjectsRepositoryCommitsCommentsCommitNote(String id, Integer page, Integer perPage, String sha);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId The ID of a merge request
	 * @return Get a single merge request
	 * @path /v3/projects/{id}/merge_request/{merge_request_id} 
	 */		
	IData<MergeRequest> getV3ProjectsMerge_requestMergeRequestByMergeRequestId(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/gitlab_ci_ymls/{name} 
	 */		
	IData<Template> getV3Gitlab_ci_ymlsTemplateByName(String name);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/dockerfiles/{name} 
	 */		
	IData<Template> getV3DockerfilesTemplateByName(String name);
	
	/**
	 * null
	 * @param search Search query for namespaces
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a namespaces list
	 * @path /v3/namespaces 
	 */		
	IData<Namespace> getV3NamespacesNamespace(String search, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param snippetId The ID of an Issue, Merge Request or Snippet
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/snippets/{snippet_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmoji(String id, Integer snippetId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param tagName The name of the tag
	 * @return Get a single repository tag
	 * @path /v3/projects/{id}/repository/tags/{tag_name} 
	 */		
	IData<RepoTag> getV3ProjectsRepositoryTagsRepoTagByTagName(String id, String tagName);
	
	/**
	 * null
	 * @return Get the currently authenticated user
	 * @path /v3/user 
	 */		
	IData<UserPublic> getV3UserUserPublic();
	
	/**
	 * null
	 * @return Get the list of the available template
	 * @path /v3/gitignores 
	 */		
	IData<TemplatesList> getV3GitignoresTemplatesList();
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId null
	 * @return Get the commits of a merge request
	 * @path /v3/projects/{id}/merge_request/{merge_request_id}/commits 
	 */		
	IData<RepoCommit> getV3ProjectsMerge_requestCommitsRepoCommit(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param mergeRequestId The ID of a merge request
	 * @return Get a list of merge request diff versions
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/versions 
	 */		
	IData<MergeRequestDiff> getV3ProjectsMerge_requestsVersionsMergeRequestDiff(String id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param snippetId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/snippets/{snippet_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId);
	
	/**
	 * null
	 * @param id The project ID
	 * @param deploymentId The deployment ID
	 * @return Gets a specific deployment
	 * @path /v3/projects/{id}/deployments/{deployment_id} 
	 */		
	IData<Deployment> getV3ProjectsDeploymentsDeploymentByDeploymentId(String id, Integer deploymentId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param sha The commit hash
	 * @param ref The ref
	 * @param stage The stage
	 * @param name The name
	 * @param all Show all statuses, default: false
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a commit's statuses
	 * @path /v3/projects/{id}/repository/commits/{sha}/statuses 
	 */		
	IData<CommitStatus> getV3ProjectsRepositoryCommitsStatusesCommitStatus(String id, String sha, String ref, String stage, String name, String all, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id null
	 * @return Get single ssh key by id. Only available to admin users
	 * @path /v3/keys/{id} 
	 */		
	IData<SSHKeyWithUser> getV3KeysSSHKeyWithUserById(Integer id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param token The unique token of trigger
	 * @return Get specific trigger of a project
	 * @path /v3/projects/{id}/triggers/{token} 
	 */		
	IData<Trigger> getV3ProjectsTriggersTriggerByToken(String id, String token);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param issueId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/issues/{issue_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId);
	
	/**
	 * null
	 * @param id The ID of the system hook
	 * @return Test a hook
	 * @path /v3/hooks/{id} 
	 */		
	IData<Hook> getV3HooksHookById(Integer id);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param mergeRequestId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId);
	
	/**
	 * null
	 * @param id The ID of the user
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get the contribution events of a specified user
	 * @path /v3/users/{id}/events 
	 */		
	IData<Event> getV3UsersEventsEvent(Integer id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a group
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of projects in this group.
	 * @path /v3/groups/{id}/projects 
	 */		
	IData<Project> getV3GroupsProjectsProject(String id, Boolean archived, String visibility, String search, String orderBy, String sort, Boolean simple, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/templates/gitignores/{name} 
	 */		
	IData<Template> getV3TemplatesGitignoresTemplateByName(String name);
	
	/**
	 * null
	 * @param id The ID of the project
	 * @param keyId The ID of the deploy key
	 * @return Get single deploy key
	 * @path /v3/projects/{id}/keys/{key_id} 
	 */		
	IData<SSHKey> getV3ProjectsKeysSSHKeyByKeyId(String id, Integer keyId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param mergeRequestId null
	 * @return List issues that will be closed on merge
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/closes_issues 
	 */		
	IData<MRNote> getV3ProjectsMerge_requestsCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param id null
	 * @param snippetId null
	 * @param noteId null
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer snippetId, Integer noteId);
	
	/**
	 * null
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @param statistics Include project statistics
	 * @return Get an owned projects list for authenticated user
	 * @path /v3/projects/owned 
	 */		
	IData<BasicProjectDetails> getV3ProjectsOwnedBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics);
	
	/**
	 * null
	 * @param serviceSlug The name of the service
	 * @param id null
	 * @return Get the service settings for project
	 * @path /v3/projects/{id}/services/{service_slug} 
	 */		
	IData<ProjectService> getV3ProjectsServicesProjectServiceByServiceSlug(String serviceSlug, Integer id);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/templates/gitlab_ci_ymls/{name} 
	 */		
	IData<Template> getV3TemplatesGitlab_ci_ymlsTemplateByName(String name);
	
	/**
	 * null
	 * @param id The group ID or project ID or project NAMESPACE/PROJECT_NAME
	 * @return Get group level notification level settings, defaults to Global
	 * @path /v3/groups/{id}/notification_settings 
	 */		
	IData<NotificationSetting> getV3GroupsNotification_settingsNotificationSetting(String id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteableId The ID of the noteable
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +noteable+ notes
	 * @path /v3/projects/{id}/merge_requests/{noteable_id}/notes 
	 */		
	IData<Note> getV3ProjectsMerge_requestsNotesNote(String id, Integer noteableId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get triggers list
	 * @path /v3/projects/{id}/triggers 
	 */		
	IData<Trigger> getV3ProjectsTriggersTrigger(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The group ID
	 * @param query A query string to search for members
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Gets a list of group or project members viewable by the authenticated user.
	 * @path /v3/groups/{id}/members 
	 */		
	IData<Member> getV3GroupsMembersMember(String id, String query, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get a single project
	 * @path /v3/projects/{id} 
	 */		
	IData<ProjectWithAccess> getV3ProjectsProjectWithAccessById(String id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param sha A commit sha, or the name of a branch or tag
	 * @return Get a specific commit of a project
	 * @path /v3/projects/{id}/repository/commits/{sha} 
	 */		
	IData<RepoCommitDetail> getV3ProjectsRepositoryCommitsRepoCommitDetailBySha(String id, String sha);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param state Return opened, closed, or all issues
	 * @param iid Return the issue having the given `iid`
	 * @param labels Comma-separated list of label names
	 * @param milestone Return issues for a specific milestone
	 * @param orderBy Return issues ordered by `created_at` or `updated_at` fields.
	 * @param sort Return issues sorted in `asc` or `desc` order.
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project issues
	 * @path /v3/projects/{id}/issues 
	 */		
	IData<Issue> getV3ProjectsIssuesIssue(String id, String state, Integer iid, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param from The commit, branch name, or tag name to start comparison
	 * @param to The commit, branch name, or tag name to stop comparison
	 * @return Compare two branches, tags, or commits
	 * @path /v3/projects/{id}/repository/compare 
	 */		
	IData<Compare> getV3ProjectsRepositoryCompare(String id, String from, String to);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param branch The name of the branch
	 * @return Get a single branch
	 * @path /v3/projects/{id}/repository/branches/{branch} 
	 */		
	IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranchByBranch(String id, String branch);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param id null
	 * @param mergeRequestId null
	 * @param noteId null
	 * @return Get a list of project +awardable+ award emoji
	 * @path /v3/projects/{id}/merge_requests/{merge_request_id}/notes/{note_id}/award_emoji 
	 */		
	IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer mergeRequestId, Integer noteId);
	
	/**
	 * null
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return List all public snippets current_user has access to
	 * @path /v3/snippets/public 
	 */		
	IData<PersonalSnippet> getV3SnippetsPublicPersonalSnippet(Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param milestoneId The ID of a project milestone
	 * @return Get a single project milestone
	 * @path /v3/projects/{id}/milestones/{milestone_id} 
	 */		
	IData<Milestone> getV3ProjectsMilestonesMilestoneByMilestoneId(String id, Integer milestoneId);
	
	/**
	 * null
	 * @param awardId The ID of the award
	 * @param id null
	 * @param snippetId null
	 * @param noteId null
	 * @return Get a specific award emoji
	 * @path /v3/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji/{award_id} 
	 */		
	IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId, Integer noteId);
	
	/**
	 * null
	 * @param id The ID of the project
	 * @return Get a specific project's deploy keys
	 * @path /v3/projects/{id}/deploy_keys 
	 */		
	IData<SSHKey> getV3ProjectsDeploy_keysSSHKey(String id);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @return Get repository contributors
	 * @path /v3/projects/{id}/repository/contributors 
	 */		
	IData<Contributor> getV3ProjectsRepositoryContributorsContributor(String id);
	
	/**
	 * null
	 * @param id The ID of the runner
	 * @return Get runner's details
	 * @path /v3/runners/{id} 
	 */		
	IData<RunnerDetails> getV3RunnersRunnerDetailsById(Integer id);
	
	/**
	 * null
	 * @param id The ID of the project
	 * @param keyId The ID of the deploy key
	 * @return Get single deploy key
	 * @path /v3/projects/{id}/deploy_keys/{key_id} 
	 */		
	IData<SSHKey> getV3ProjectsDeploy_keysSSHKeyByKeyId(String id, Integer keyId);
	
	/**
	 * null
	 * @param state Return opened, closed, or all issues
	 * @param labels Comma-separated list of label names
	 * @param milestone Return issues for a specific milestone
	 * @param orderBy Return issues ordered by `created_at` or `updated_at` fields.
	 * @param sort Return issues sorted in `asc` or `desc` order.
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get currently authenticated user's issues
	 * @path /v3/issues 
	 */		
	IData<Issue> getV3IssuesIssue(String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param snippetId The ID of a project snippet
	 * @return Get a single project snippet
	 * @path /v3/projects/{id}/snippets/{snippet_id} 
	 */		
	IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippetBySnippetId(String id, Integer snippetId);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param search Return list of users matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get the users list of a project
	 * @path /v3/projects/{id}/users 
	 */		
	IData<UserBasic> getV3ProjectsUsersUserBasic(String id, String search, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The project ID
	 * @param userId The user ID of the member
	 * @return Gets a member of a group or project.
	 * @path /v3/projects/{id}/members/{user_id} 
	 */		
	IData<Member> getV3ProjectsMembersMemberByUserId(String id, Integer userId);
	
	/**
	 * null
	 * @param orderBy Return projects ordered by field
	 * @param sort Return projects sorted in ascending and descending order
	 * @param archived Limit by archived status
	 * @param visibility Limit by visibility
	 * @param search Return list of authorized projects matching the search criteria
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @param simple Return only the ID, URL, name, and path of each project
	 * @param statistics Include project statistics
	 * @return Get all projects for admin user
	 * @path /v3/projects/all 
	 */		
	IData<BasicProjectDetails> getV3ProjectsAllBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get all project snippets
	 * @path /v3/projects/{id}/snippets 
	 */		
	IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippet(String id, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param scope The scope of specific runners to show
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get runners available for project
	 * @path /v3/projects/{id}/runners 
	 */		
	IData<Runner> getV3ProjectsRunnersRunner(String id, String scope, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The project ID
	 * @param query A query string to search for members
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Gets a list of group or project members viewable by the authenticated user.
	 * @path /v3/projects/{id}/members 
	 */		
	IData<Member> getV3ProjectsMembersMember(String id, String query, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteableId The ID of the noteable
	 * @param page Current page number
	 * @param perPage Number of items per page
	 * @return Get a list of project +noteable+ notes
	 * @path /v3/projects/{id}/snippets/{noteable_id}/notes 
	 */		
	IData<Note> getV3ProjectsSnippetsNotesNote(String id, Integer noteableId, Integer page, Integer perPage);
	
	/**
	 * null
	 * @param name The name of the template
	 * @return Get the text for a specific template present in local filesystem
	 * @path /v3/gitignores/{name} 
	 */		
	IData<Template> getV3GitignoresTemplateByName(String name);
	
	/**
	 * null
	 * @param id The ID of a project
	 * @param noteId The ID of a note
	 * @param noteableId The ID of the noteable
	 * @return Get a single +noteable+ note
	 * @path /v3/projects/{id}/merge_requests/{noteable_id}/notes/{note_id} 
	 */		
	IData<Note> getV3ProjectsMerge_requestsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId);
	
}