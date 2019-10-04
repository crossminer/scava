package org.eclipse.scava.crossflow.restmule.client.gitlab.client;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.gitlab.model.*;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPaged;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface IEntityEndpoint {

	
		@GET("/v3/projects/{id}/keys")
		Observable<SSHKey> getV3ProjectsKeysSSHKey( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/templates/licenses")
		Observable<RepoLicense> getV3TemplatesLicensesRepoLicense( 
				
				@Query(value="popular", encoded=true) Boolean popular);
	
		@GET("/v3/gitlab_ci_ymls")
		Observable<TemplatesList> getV3Gitlab_ci_ymlsTemplatesList();
	
		@GET("/v3/projects/starred")
		Observable<BasicProjectDetails> getV3ProjectsStarredBasicProjectDetails( 
				
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="simple", encoded=true) Boolean simple);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmoji( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/issues/{issue_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmoji( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="issue_id", encoded=true) Integer issueId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/merge_request/{merge_request_id}/changes")
		Observable<MergeRequestChanges> getV3ProjectsMerge_requestChangesMergeRequestChanges( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/notification_settings")
		Observable<GlobalNotificationSetting> getV3Notification_settingsGlobalNotificationSetting();
	
		@GET("/v3/projects/{id}/environments")
		Observable<Environment> getV3ProjectsEnvironmentsEnvironment( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/pipelines")
		Observable<Pipeline> getV3ProjectsPipelinesPipeline( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="scope", encoded=true) String scope);
	
		@GET("/v3/projects/{id}/merge_request/{merge_request_id}/closes_issues")
		Observable<MRNote> getV3ProjectsMerge_requestCloses_issuesMRNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/repository/tags")
		Observable<RepoTag> getV3ProjectsRepositoryTagsRepoTag( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/groups/{id}/issues")
		Observable<Issue> getV3GroupsIssuesIssue( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="milestone", encoded=true) String milestone,			
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/templates/gitignores")
		Observable<TemplatesList> getV3TemplatesGitignoresTemplatesList();
	
		@GET("/v3/users")
		Observable<UserBasic> getV3UsersUserBasic( 
				
				@Query(value="username", encoded=true) String username,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="active", encoded=true) Boolean active,			
				@Query(value="external", encoded=true) Boolean external,			
				@Query(value="blocked", encoded=true) Boolean blocked,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/access_requests")
		Observable<AccessRequester> getV3ProjectsAccess_requestsAccessRequester( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/issues/{noteable_id}/notes")
		Observable<Note> getV3ProjectsIssuesNotesNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="noteable_id", encoded=true) Integer noteableId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/notes/{note_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/licenses")
		Observable<RepoLicense> getV3LicensesRepoLicense( 
				
				@Query(value="popular", encoded=true) Boolean popular);
	
		@GET("/v3/groups/{id}")
		Observable<GroupDetail> getV3GroupsGroupDetailById( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/projects/{id}/notification_settings")
		Observable<NotificationSetting> getV3ProjectsNotification_settingsNotificationSetting( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/templates/dockerfiles")
		Observable<TemplatesList> getV3TemplatesDockerfilesTemplatesList();
	
		@GET("/v3/projects/{id}/boards/{board_id}/lists")
		Observable<List> getV3ProjectsBoardsListsList( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="board_id", encoded=true) Integer boardId);
	
		@GET("/v3/snippets")
		Observable<PersonalSnippet> getV3SnippetsPersonalSnippet( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/users/{id}/keys")
		Observable<SSHKey> getV3UsersKeysSSHKey( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/templates/gitlab_ci_ymls")
		Observable<TemplatesList> getV3TemplatesGitlab_ci_ymlsTemplatesList();
	
		@GET("/v3/projects/{id}/builds/{build_id}")
		Observable<Build> getV3ProjectsBuildsBuildByBuildId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="build_id", encoded=true) Integer buildId);
	
		@GET("/v3/projects/{id}/issues/{issue_id}/notes/{note_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="issue_id", encoded=true) Integer issueId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/groups/{id}/access_requests")
		Observable<AccessRequester> getV3GroupsAccess_requestsAccessRequester( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/variables/{key}")
		Observable<Variable> getV3ProjectsVariablesVariableByKey( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="key", encoded=true) String key);
	
		@GET("/v3/projects/{id}/pipelines/{pipeline_id}")
		Observable<Pipeline> getV3ProjectsPipelinesPipelineByPipelineId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="pipeline_id", encoded=true) Integer pipelineId);
	
		@GET("/v3/projects/visible")
		Observable<BasicProjectDetails> getV3ProjectsVisibleBasicProjectDetails( 
				
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="simple", encoded=true) Boolean simple);
	
		@GET("/v3/projects")
		Observable<BasicProjectDetails> getV3ProjectsBasicProjectDetails( 
				
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="simple", encoded=true) Boolean simple);
	
		@GET("/v3/projects/{id}/merge_request/{merge_request_id}/comments")
		Observable<MRNote> getV3ProjectsMerge_requestCommentsMRNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/events")
		Observable<Event> getV3ProjectsEventsEvent( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/labels")
		Observable<Label> getV3ProjectsLabelsLabel( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/projects/{id}/deployments")
		Observable<Deployment> getV3ProjectsDeploymentsDeployment( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/search/{query}")
		Observable<Project> getV3ProjectsSearchProjectByQuery( 
				
				@Path(value="query", encoded=true) String query,			
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/hooks")
		Observable<Hook> getV3HooksHook();
	
		@GET("/v3/projects/{id}/hooks/{hook_id}")
		Observable<ProjectHook> getV3ProjectsHooksProjectHookByHookId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="hook_id", encoded=true) Integer hookId);
	
		@GET("/v3/user/emails/{email_id}")
		Observable<Email> getV3UserEmailsEmailByEmailId( 
				
				@Path(value="email_id", encoded=true) Integer emailId);
	
		@GET("/v3/dockerfiles")
		Observable<TemplatesList> getV3DockerfilesTemplatesList();
	
		@GET("/v3/groups/{id}/members/{user_id}")
		Observable<Member> getV3GroupsMembersMemberByUserId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="user_id", encoded=true) Integer userId);
	
		@GET("/v3/runners/all")
		Observable<Runner> getV3RunnersAllRunner( 
				
				@Query(value="scope", encoded=true) String scope,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/milestones/{milestone_id}/issues")
		Observable<Issue> getV3ProjectsMilestonesIssuesIssue( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="milestone_id", encoded=true) Integer milestoneId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/user/emails")
		Observable<Email> getV3UserEmailsEmail();
	
		@GET("/v3/application/settings")
		Observable<ApplicationSetting> getV3ApplicationSettingsApplicationSetting();
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/comments")
		Observable<MRNote> getV3ProjectsMerge_requestsCommentsMRNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/repository/tree")
		Observable<RepoTreeObject> getV3ProjectsRepositoryTreeRepoTreeObject( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="ref_name", encoded=true) String refName,			
				@Query(value="path", encoded=true) String path,			
				@Query(value="recursive", encoded=true) Boolean recursive);
	
		@GET("/v3/projects/{id}/snippets/{noteable_id}/notes/{note_id}")
		Observable<Note> getV3ProjectsSnippetsNotesNoteByNoteId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="note_id", encoded=true) Integer noteId,			
				@Path(value="noteable_id", encoded=true) Integer noteableId);
	
		@GET("/v3/projects/{id}/hooks")
		Observable<ProjectHook> getV3ProjectsHooksProjectHook( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/repository/branches")
		Observable<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranch( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/user/keys")
		Observable<SSHKey> getV3UserKeysSSHKey();
	
		@GET("/v3/licenses/{name}")
		Observable<RepoLicense> getV3LicensesRepoLicenseByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}")
		Observable<MergeRequest> getV3ProjectsMerge_requestsMergeRequestByMergeRequestId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/builds")
		Observable<Build> getV3ProjectsBuildsBuild( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="scope", encoded=true) String scope,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/users/{id}/emails")
		Observable<Email> getV3UsersEmailsEmail( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/snippets/{id}")
		Observable<PersonalSnippet> getV3SnippetsPersonalSnippetById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/projects/{id}/boards")
		Observable<Board> getV3ProjectsBoardsBoard( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/groups/owned")
		Observable<Group> getV3GroupsOwnedGroup( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="statistics", encoded=true) Boolean statistics);
	
		@GET("/v3/projects/{id}/issues/{issue_id}/notes/{note_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmoji( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="issue_id", encoded=true) Integer issueId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/templates/dockerfiles/{name}")
		Observable<Template> getV3TemplatesDockerfilesTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/versions/{version_id}")
		Observable<MergeRequestDiffFull> getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId,			
				@Path(value="version_id", encoded=true) Integer versionId);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/commits")
		Observable<RepoCommit> getV3ProjectsMerge_requestsCommitsRepoCommit( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/changes")
		Observable<MergeRequestChanges> getV3ProjectsMerge_requestsChangesMergeRequestChanges( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/repository/commits/{sha}/builds")
		Observable<Build> getV3ProjectsRepositoryCommitsBuildsBuild( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="sha", encoded=true) String sha,			
				@Query(value="scope", encoded=true) String scope,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/repository/commits")
		Observable<RepoCommit> getV3ProjectsRepositoryCommitsRepoCommit( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="ref_name", encoded=true) String refName,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="until", encoded=true) String until,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="path", encoded=true) String path);
	
		@GET("/v3/projects/{id}/variables")
		Observable<Variable> getV3ProjectsVariablesVariable( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/todos")
		Observable<Todo> getV3TodosTodo( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/issues/{noteable_id}/notes/{note_id}")
		Observable<Note> getV3ProjectsIssuesNotesNoteByNoteId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="note_id", encoded=true) Integer noteId,			
				@Path(value="noteable_id", encoded=true) Integer noteableId);
	
		@GET("/v3/runners")
		Observable<Runner> getV3RunnersRunner( 
				
				@Query(value="scope", encoded=true) String scope,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/user/keys/{key_id}")
		Observable<SSHKey> getV3UserKeysSSHKeyByKeyId( 
				
				@Path(value="key_id", encoded=true) Integer keyId);
	
		@GET("/v3/templates/licenses/{name}")
		Observable<RepoLicense> getV3TemplatesLicensesRepoLicenseByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/users/{id}")
		Observable<UserBasic> getV3UsersUserBasicById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/projects/{id}/issues/{issue_id}")
		Observable<Issue> getV3ProjectsIssuesIssueByIssueId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="issue_id", encoded=true) Integer issueId);
	
		@GET("/v3/projects/{id}/repository/commits/{sha}/comments")
		Observable<CommitNote> getV3ProjectsRepositoryCommitsCommentsCommitNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="sha", encoded=true) String sha);
	
		@GET("/v3/projects/{id}/merge_request/{merge_request_id}")
		Observable<MergeRequest> getV3ProjectsMerge_requestMergeRequestByMergeRequestId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/gitlab_ci_ymls/{name}")
		Observable<Template> getV3Gitlab_ci_ymlsTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/dockerfiles/{name}")
		Observable<Template> getV3DockerfilesTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/namespaces")
		Observable<Namespace> getV3NamespacesNamespace( 
				
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/snippets/{snippet_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmoji( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="snippet_id", encoded=true) Integer snippetId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/repository/tags/{tag_name}")
		Observable<RepoTag> getV3ProjectsRepositoryTagsRepoTagByTagName( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="tag_name", encoded=true) String tagName);
	
		@GET("/v3/user")
		Observable<UserPublic> getV3UserUserPublic();
	
		@GET("/v3/gitignores")
		Observable<TemplatesList> getV3GitignoresTemplatesList();
	
		@GET("/v3/projects/{id}/merge_request/{merge_request_id}/commits")
		Observable<RepoCommit> getV3ProjectsMerge_requestCommitsRepoCommit( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/versions")
		Observable<MergeRequestDiff> getV3ProjectsMerge_requestsVersionsMergeRequestDiff( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/snippets/{snippet_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="snippet_id", encoded=true) Integer snippetId);
	
		@GET("/v3/projects/{id}/deployments/{deployment_id}")
		Observable<Deployment> getV3ProjectsDeploymentsDeploymentByDeploymentId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="deployment_id", encoded=true) Integer deploymentId);
	
		@GET("/v3/projects/{id}/repository/commits/{sha}/statuses")
		Observable<CommitStatus> getV3ProjectsRepositoryCommitsStatusesCommitStatus( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="sha", encoded=true) String sha,			
				@Query(value="ref", encoded=true) String ref,			
				@Query(value="stage", encoded=true) String stage,			
				@Query(value="name", encoded=true) String name,			
				@Query(value="all", encoded=true) String all,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/keys/{id}")
		Observable<SSHKeyWithUser> getV3KeysSSHKeyWithUserById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/projects/{id}/triggers/{token}")
		Observable<Trigger> getV3ProjectsTriggersTriggerByToken( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="token", encoded=true) String token);
	
		@GET("/v3/projects/{id}/issues/{issue_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="issue_id", encoded=true) Integer issueId);
	
		@GET("/v3/hooks/{id}")
		Observable<Hook> getV3HooksHookById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/projects/{id}/boards/{board_id}/lists/{list_id}")
		Observable<List> getV3ProjectsBoardsListsListByListId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="board_id", encoded=true) Integer boardId,			
				@Path(value="list_id", encoded=true) Integer listId);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/users/{id}/events")
		Observable<Event> getV3UsersEventsEvent( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/groups/{id}/projects")
		Observable<Project> getV3GroupsProjectsProject( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="simple", encoded=true) Boolean simple,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/templates/gitignores/{name}")
		Observable<Template> getV3TemplatesGitignoresTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/projects/{id}/keys/{key_id}")
		Observable<SSHKey> getV3ProjectsKeysSSHKeyByKeyId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="key_id", encoded=true) Integer keyId);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/closes_issues")
		Observable<MRNote> getV3ProjectsMerge_requestsCloses_issuesMRNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId);
	
		@GET("/v3/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmoji( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="snippet_id", encoded=true) Integer snippetId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/projects/owned")
		Observable<BasicProjectDetails> getV3ProjectsOwnedBasicProjectDetails( 
				
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="simple", encoded=true) Boolean simple,			
				@Query(value="statistics", encoded=true) Boolean statistics);
	
		@GET("/v3/projects/{id}/services/{service_slug}")
		Observable<ProjectService> getV3ProjectsServicesProjectServiceByServiceSlug( 
				
				@Path(value="service_slug", encoded=true) String serviceSlug,			
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/templates/gitlab_ci_ymls/{name}")
		Observable<Template> getV3TemplatesGitlab_ci_ymlsTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/groups/{id}/notification_settings")
		Observable<NotificationSetting> getV3GroupsNotification_settingsNotificationSetting( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/projects/{id}/merge_requests/{noteable_id}/notes")
		Observable<Note> getV3ProjectsMerge_requestsNotesNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="noteable_id", encoded=true) Integer noteableId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/triggers")
		Observable<Trigger> getV3ProjectsTriggersTrigger( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/groups/{id}/members")
		Observable<Member> getV3GroupsMembersMember( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="query", encoded=true) String query,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}")
		Observable<ProjectWithAccess> getV3ProjectsProjectWithAccessById( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/projects/{id}/repository/commits/{sha}")
		Observable<RepoCommitDetail> getV3ProjectsRepositoryCommitsRepoCommitDetailBySha( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="sha", encoded=true) String sha);
	
		@GET("/v3/projects/{id}/issues")
		Observable<Issue> getV3ProjectsIssuesIssue( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="iid", encoded=true) Integer iid,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="milestone", encoded=true) String milestone,			
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/repository/compare")
		Observable<Compare> getV3ProjectsRepositoryCompare( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="from", encoded=true) String from,			
				@Query(value="to", encoded=true) String to);
	
		@GET("/v3/projects/{id}/repository/branches/{branch}")
		Observable<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranchByBranch( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="branch", encoded=true) String branch);
	
		@GET("/v3/projects/{id}/merge_requests/{merge_request_id}/notes/{note_id}/award_emoji")
		Observable<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="merge_request_id", encoded=true) Integer mergeRequestId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/snippets/public")
		Observable<PersonalSnippet> getV3SnippetsPublicPersonalSnippet( 
				
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/milestones/{milestone_id}")
		Observable<Milestone> getV3ProjectsMilestonesMilestoneByMilestoneId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="milestone_id", encoded=true) Integer milestoneId);
	
		@GET("/v3/projects/{id}/snippets/{snippet_id}/notes/{note_id}/award_emoji/{award_id}")
		Observable<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId( 
				
				@Path(value="award_id", encoded=true) Integer awardId,			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="snippet_id", encoded=true) Integer snippetId,			
				@Path(value="note_id", encoded=true) Integer noteId);
	
		@GET("/v3/projects/{id}/deploy_keys")
		Observable<SSHKey> getV3ProjectsDeploy_keysSSHKey( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/projects/{id}/repository/contributors")
		Observable<Contributor> getV3ProjectsRepositoryContributorsContributor( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/v3/runners/{id}")
		Observable<RunnerDetails> getV3RunnersRunnerDetailsById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/v3/projects/{id}/deploy_keys/{key_id}")
		Observable<SSHKey> getV3ProjectsDeploy_keysSSHKeyByKeyId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="key_id", encoded=true) Integer keyId);
	
		@GET("/v3/issues")
		Observable<Issue> getV3IssuesIssue( 
				
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="milestone", encoded=true) String milestone,			
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/snippets/{snippet_id}")
		Observable<ProjectSnippet> getV3ProjectsSnippetsProjectSnippetBySnippetId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="snippet_id", encoded=true) Integer snippetId);
	
		@GET("/v3/projects/{id}/users")
		Observable<UserBasic> getV3ProjectsUsersUserBasic( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/members/{user_id}")
		Observable<Member> getV3ProjectsMembersMemberByUserId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="user_id", encoded=true) Integer userId);
	
		@GET("/v3/projects/all")
		Observable<BasicProjectDetails> getV3ProjectsAllBasicProjectDetails( 
				
				@Query(value="order_by", encoded=true) String orderBy,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="archived", encoded=true) Boolean archived,			
				@Query(value="visibility", encoded=true) String visibility,			
				@Query(value="search", encoded=true) String search,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage,			
				@Query(value="simple", encoded=true) Boolean simple,			
				@Query(value="statistics", encoded=true) Boolean statistics);
	
		@GET("/v3/projects/{id}/snippets")
		Observable<ProjectSnippet> getV3ProjectsSnippetsProjectSnippet( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/runners")
		Observable<Runner> getV3ProjectsRunnersRunner( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="scope", encoded=true) String scope,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/members")
		Observable<Member> getV3ProjectsMembersMember( 
				
				@Path(value="id", encoded=true) String id,			
				@Query(value="query", encoded=true) String query,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/projects/{id}/snippets/{noteable_id}/notes")
		Observable<Note> getV3ProjectsSnippetsNotesNote( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="noteable_id", encoded=true) Integer noteableId,			
				@Query(value="page", encoded=true) Integer page,			
				@Query(value="per_page", encoded=true) Integer perPage);
	
		@GET("/v3/gitignores/{name}")
		Observable<Template> getV3GitignoresTemplateByName( 
				
				@Path(value="name", encoded=true) String name);
	
		@GET("/v3/projects/{id}/merge_requests/{noteable_id}/notes/{note_id}")
		Observable<Note> getV3ProjectsMerge_requestsNotesNoteByNoteId( 
				
				@Path(value="id", encoded=true) String id,			
				@Path(value="note_id", encoded=true) Integer noteId,			
				@Path(value="noteable_id", encoded=true) Integer noteableId);
	
}