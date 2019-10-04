package org.eclipse.scava.crossflow.restmule.client.bitbucket.client;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.*;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.Error;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.page.BitbucketPaged;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface IEntityEndpoint {

	
		@GET("/repositories/{username}/{repo_slug}/watchers")
		Observable<Error> getRepositoriesWatchersError();
	
		@GET("/repositories/{username}/{repo_slug}/milestones")
		Observable<PaginatedMilestones> getRepositoriesMilestonesPaginatedMilestones();
	
		@GET("/repositories/{username}/{repo_slug}/milestones")
		Observable<Error> getRepositoriesMilestonesError();
	
		@GET("/repositories/{username}")
		Observable<PaginatedRepositories> getRepositoriesPaginatedRepositoriesByUsername( 
				
				@Path(value="username", encoded=true) String username,			
				@Query(value="role", encoded=true) String role);
	
		@GET("/repositories/{username}")
		Observable<Error> getRepositoriesErrorByUsername( 
				
				@Path(value="username", encoded=true) String username,			
				@Query(value="role", encoded=true) String role);
	
		@GET("/snippets/{username}/{encoded_id}")
		Observable<Error> getSnippetsErrorByEncodedId( 
				
				@Path(value="encoded_id", encoded=true) String encodedId);
	
		@GET("/repositories/{username}/{repo_slug}/diff/{spec}")
		Observable<Error> getRepositoriesDiffErrorBySpec( 
				
				@Query(value="context", encoded=true) Integer context,			
				@Query(value="path", encoded=true) String path);
	
		@GET("/snippets/{username}")
		Observable<PaginatedSnippets> getSnippetsPaginatedSnippetsByUsername( 
				
				@Query(value="role", encoded=true) String role,			
				@Path(value="username", encoded=true) String username);
	
		@GET("/snippets/{username}")
		Observable<Error> getSnippetsErrorByUsername( 
				
				@Query(value="role", encoded=true) String role,			
				@Path(value="username", encoded=true) String username);
	
		@GET("/snippets")
		Observable<PaginatedSnippets> getSnippetsPaginatedSnippets( 
				
				@Query(value="role", encoded=true) String role);
	
		@GET("/snippets")
		Observable<Error> getSnippetsError( 
				
				@Query(value="role", encoded=true) String role);
	
		@GET("/repositories/{username}/{repo_slug}/src/{node}/{path}")
		Observable<Error> getRepositoriesSrcErrorByPath( 
				
				@Query(value="format", encoded=true) String format);
	
		@GET("/repositories/{username}/{repo_slug}/forks")
		Observable<PaginatedRepositories> getRepositoriesForksPaginatedRepositories();
	
		@GET("/repositories/{username}/{repo_slug}/refs/tags/{name}")
		Observable<Error> getRepositoriesRefsTagsErrorByName();
	
		@GET("/repositories")
		Observable<PaginatedRepositories> getRepositoriesPaginatedRepositories( 
				
				@Query(value="after", encoded=true) String after);
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/comments/{comment_id}")
		Observable<Error> getRepositoriesIssuesCommentsErrorByCommentId();
	
		@GET("/teams")
		Observable<PaginatedTeams> getTeamsPaginatedTeams( 
				
				@Query(value="role", encoded=true) String role);
	
		@GET("/teams")
		Observable<Error> getTeamsError( 
				
				@Query(value="role", encoded=true) String role);
	
		@GET("/repositories/{username}/{repo_slug}/commits")
		Observable<Error> getRepositoriesCommitsError();
	
		@GET("/repositories/{username}/{repo_slug}/issues")
		Observable<PaginatedIssues> getRepositoriesIssuesPaginatedIssues();
	
		@GET("/repositories/{username}/{repo_slug}/issues")
		Observable<Error> getRepositoriesIssuesError();
	
		@GET("/repositories/{username}/{repo_slug}/refs/branches/{name}")
		Observable<Error> getRepositoriesRefsBranchesErrorByName();
	
		@GET("/teams/{username}/repositories")
		Observable<Error> getTeamsRepositoriesError();
	
		@GET("/snippets/{username}/{encoded_id}/comments")
		Observable<PaginatedSnippetComments> getSnippetsCommentsPaginatedSnippetComments();
	
		@GET("/snippets/{username}/{encoded_id}/comments")
		Observable<Error> getSnippetsCommentsError();
	
		@GET("/repositories/{username}/{repo_slug}")
		Observable<Error> getRepositoriesErrorByRepoSlug( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
	
		@GET("/users/{username}/repositories")
		Observable<Error> getUsersRepositoriesError();
	
		@GET("/repositories/{username}/{repo_slug}/refs")
		Observable<Error> getRepositoriesRefsError();
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}")
		Observable<Error> getRepositoriesPullrequestsErrorByPullRequestId( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pull_request_id", encoded=true) Integer pullRequestId);
	
		@GET("/users/{username}/following")
		Observable<PaginatedUsers> getUsersFollowingPaginatedUsers( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/users/{username}/following")
		Observable<Error> getUsersFollowingError( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/vote")
		Observable<Error> getRepositoriesIssuesVoteError( 
				
				@Path(value="issue_id", encoded=true) Integer issueId);
	
		@GET("/snippets/{username}/{encoded_id}/{node_id}/files/{path}")
		Observable<Error> getSnippetsFilesErrorByPath();
	
		@GET("/teams/{username}/pipelines_config/variables/{variable_uuid}")
		Observable<Error> getTeamsPipelines_configVariablesErrorByVariableUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="variable_uuid", encoded=true) String variableUuid);
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/comments")
		Observable<Error> getRepositoriesIssuesCommentsError();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/")
		Observable<PaginatedPipelineSteps> getRepositoriesPipelinesStepsPaginatedPipelineSteps( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pipeline_uuid", encoded=true) String pipelineUuid);
	
		@GET("/addon/linkers/{linker_key}")
		Observable<Error> getAddonLinkersErrorByLinkerKey();
	
		@GET("/repositories/{username}/{repo_slug}/commit/{node}/statuses")
		Observable<PaginatedCommitstatuses> getRepositoriesCommitStatusesPaginatedCommitstatuses( 
				
				@Path(value="node", encoded=true) String node);
	
		@GET("/repositories/{username}/{repo_slug}/commit/{node}/statuses")
		Observable<Error> getRepositoriesCommitStatusesError( 
				
				@Path(value="node", encoded=true) String node);
	
		@GET("/teams/{username}/followers")
		Observable<PaginatedUsers> getTeamsFollowersPaginatedUsers( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/teams/{username}/followers")
		Observable<Error> getTeamsFollowersError( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/repositories/{username}/{repo_slug}/refs/tags")
		Observable<Error> getRepositoriesRefsTagsError();
	
		@GET("/teams/{username}/hooks")
		Observable<PaginatedWebhookSubscriptions> getTeamsHooksPaginatedWebhookSubscriptions();
	
		@GET("/teams/{username}/hooks")
		Observable<Error> getTeamsHooksError();
	
		@GET("/repositories/{username}/{repo_slug}/commit/{sha}/comments/{comment_id}")
		Observable<Error> getRepositoriesCommitCommentsErrorByCommentId();
	
		@GET("/snippets/{username}/{encoded_id}/watchers")
		Observable<PaginatedUsers> getSnippetsWatchersPaginatedUsers( 
				
				@Path(value="encoded_id", encoded=true) String encodedId);
	
		@GET("/snippets/{username}/{encoded_id}/watchers")
		Observable<Error> getSnippetsWatchersError( 
				
				@Path(value="encoded_id", encoded=true) String encodedId);
	
		@GET("/repositories/{username}/{repo_slug}/pipelines_config/variables/")
		Observable<PaginatedPipelineVariables> getRepositoriesPipelines_configVariablesPaginatedPipelineVariables( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
	
		@GET("/snippets/{username}/{encoded_id}/{node_id}")
		Observable<Error> getSnippetsErrorByNodeId( 
				
				@Path(value="encoded_id", encoded=true) String encodedId,			
				@Path(value="node_id", encoded=true) String nodeId);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests")
		Observable<PaginatedPullrequests> getRepositoriesPullrequestsPaginatedPullrequests( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Query(value="state", encoded=true) String state);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests")
		Observable<Error> getRepositoriesPullrequestsError( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Query(value="state", encoded=true) String state);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/patch")
		Observable<Error> getRepositoriesPullrequestsPatchError();
	
		@GET("/addon/linkers/{linker_key}/values/")
		Observable<Error> getAddonLinkersValuesError();
	
		@GET("/hook_events/{subject_type}")
		Observable<PaginatedHookEvents> getHook_eventsPaginatedHookEventsBySubjectType( 
				
				@Path(value="subject_type", encoded=true) String subjectType);
	
		@GET("/hook_events/{subject_type}")
		Observable<Error> getHook_eventsErrorBySubjectType( 
				
				@Path(value="subject_type", encoded=true) String subjectType);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/statuses")
		Observable<PaginatedCommitstatuses> getRepositoriesPullrequestsStatusesPaginatedCommitstatuses( 
				
				@Path(value="pull_request_id", encoded=true) Integer pullRequestId);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/statuses")
		Observable<Error> getRepositoriesPullrequestsStatusesError( 
				
				@Path(value="pull_request_id", encoded=true) Integer pullRequestId);
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}")
		Observable<Error> getRepositoriesIssuesErrorByIssueId();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines/")
		Observable<PaginatedPipelines> getRepositoriesPipelinesPaginatedPipelines( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
	
		@GET("/repositories/{username}/{repo_slug}/versions")
		Observable<PaginatedVersions> getRepositoriesVersionsPaginatedVersions();
	
		@GET("/repositories/{username}/{repo_slug}/versions")
		Observable<Error> getRepositoriesVersionsError();
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/attachments/{path}")
		Observable<Error> getRepositoriesIssuesAttachmentsErrorByPath();
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/watch")
		Observable<Error> getRepositoriesIssuesWatchError( 
				
				@Path(value="issue_id", encoded=true) Integer issueId);
		
		@GET("/teams/{owner}/projects/{project_key}")
		Observable<Error> getTeamsProjectsErrorByProjectKey();
	
		@GET("/snippets/{username}/{encoded_id}/commits")
		Observable<PaginatedSnippetCommit> getSnippetsCommitsPaginatedSnippetCommit();
	
		@GET("/snippets/{username}/{encoded_id}/commits")
		Observable<Error> getSnippetsCommitsError();
	
		@GET("/repositories/{username}/{repo_slug}/branch-restrictions/{id}")
		Observable<Error> getRepositoriesBranch_restrictionsErrorById( 
				
				@Path(value="id", encoded=true) String id);
	
		@GET("/repositories/{username}/{repo_slug}/pipelines_config/ssh/known_hosts/")
		Observable<PaginatedPipelineKnownHosts> getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
		
		@GET("/repositories/{username}/{repo_slug}/branch-restrictions")
		Observable<Error> getRepositoriesBranch_restrictionsError();
	
		@GET("/users/{username}/pipelines_config/variables/")
		Observable<PaginatedPipelineVariables> getUsersPipelines_configVariablesPaginatedPipelineVariables( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/repositories/{username}/{repo_slug}/versions/{version_id}")
		Observable<Error> getRepositoriesVersionsErrorByVersionId( 
				
				@Path(value="version_id", encoded=true) Integer versionId);
	
		@GET("/users/{username}/hooks")
		Observable<PaginatedWebhookSubscriptions> getUsersHooksPaginatedWebhookSubscriptions();
	
		@GET("/users/{username}/hooks")
		Observable<Error> getUsersHooksError();
	
		@GET("/repositories/{username}/{repo_slug}/commit/{revision}")
		Observable<Error> getRepositoriesCommitErrorByRevision( 
				
				@Path(value="revision", encoded=true) String revision);
		
		@GET("/repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/{step_uuid}/log")
		Observable<Error> getRepositoriesPipelinesStepsLogError( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pipeline_uuid", encoded=true) String pipelineUuid,			
				@Path(value="step_uuid", encoded=true) String stepUuid);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/commits")
		Observable<Error> getRepositoriesPullrequestsCommitsError();
	
		@GET("/repositories/{username}/{repo_slug}/downloads/{filename}")
		Observable<Error> getRepositoriesDownloadsErrorByFilename();
	
		@GET("/snippets/{username}/{encoded_id}/watch")
		Observable<PaginatedUsers> getSnippetsWatchPaginatedUsers( 
				
				@Path(value="encoded_id", encoded=true) String encodedId);
	
		@GET("/snippets/{username}/{encoded_id}/watch")
		Observable<Error> getSnippetsWatchError( 
				
				@Path(value="encoded_id", encoded=true) String encodedId);
	
		@GET("/repositories/{username}/{repo_slug}/components/{component_id}")
		Observable<Error> getRepositoriesComponentsErrorByComponentId( 
				
				@Path(value="component_id", encoded=true) Integer componentId);
	
		@GET("/users/{username}/pipelines_config/variables/{variable_uuid}")
		Observable<Error> getUsersPipelines_configVariablesErrorByVariableUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="variable_uuid", encoded=true) String variableUuid);
	
		@GET("/repositories/{username}/{repo_slug}/pipelines_config/ssh/key_pair")
		Observable<Error> getRepositoriesPipelines_configSshKey_pairError( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
	
		@GET("/repositories/{username}/{repo_slug}/commit/{sha}/comments")
		Observable<Error> getRepositoriesCommitCommentsError();
	
		@GET("/repositories/{username}/{repo_slug}/branch-restrictions")
		Observable<PaginatedBranchrestrictions> getRepositoriesBranch_restrictionsPaginatedBranchrestrictions();
	
		@GET("/snippets/{username}/{encoded_id}/commits/{revision}")
		Observable<Error> getSnippetsCommitsErrorByRevision();
	
		@GET("/repositories/{username}/{repo_slug}/patch/{spec}")
		Observable<Error> getRepositoriesPatchErrorBySpec();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines_config/variables/{variable_uuid}")
		Observable<Error> getRepositoriesPipelines_configVariablesErrorByVariableUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="variable_uuid", encoded=true) String variableUuid);
	
		@GET("/addon/linkers")
		Observable<Error> getAddonLinkersError();
	
		@GET("/teams/{username}")
		Observable<Error> getTeamsErrorByUsername( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/snippets/{username}/{encoded_id}/comments/{comment_id}")
		Observable<Error> getSnippetsCommentsErrorByCommentId();
	
		@GET("/snippets/{username}/{encoded_id}/{revision}/diff")
		Observable<Error> getSnippetsDiffError( 
				
				@Query(value="path", encoded=true) String path,			
				@Path(value="encoded_id", encoded=true) String encodedId,			
				@Path(value="revision", encoded=true) String revision);
	
		@GET("/user")
		Observable<Error> getUserError();
	
		@GET("/hook_events")
		Observable<SubjectTypes> getHook_eventsSubjectTypes();
	
		@GET("/repositories/{username}/{repo_slug}/milestones/{milestone_id}")
		Observable<Error> getRepositoriesMilestonesErrorByMilestoneId( 
				
				@Path(value="milestone_id", encoded=true) Integer milestoneId);
	
		@GET("/teams/{username}/members")
		Observable<Error> getTeamsMembersError();
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/attachments")
		Observable<PaginatedIssueAttachments> getRepositoriesIssuesAttachmentsPaginatedIssueAttachments( 
				
				@Path(value="issue_id", encoded=true) Integer issueId);
	
		@GET("/repositories/{username}/{repo_slug}/issues/{issue_id}/attachments")
		Observable<Error> getRepositoriesIssuesAttachmentsError( 
				
				@Path(value="issue_id", encoded=true) Integer issueId);
	
		@GET("/repositories/{username}/{repo_slug}/commits/{revision}")
		Observable<Error> getRepositoriesCommitsErrorByRevision();
	
		@GET("/repositories/{username}/{repo_slug}/default-reviewers/{target_username}")
		Observable<Error> getRepositoriesDefault_reviewersErrorByTargetUsername();
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/comments/{comment_id}")
		Observable<Error> getRepositoriesPullrequestsCommentsErrorByCommentId();
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/comments")
		Observable<Error> getRepositoriesPullrequestsCommentsError();
	
		@GET("/repositories/{username}/{repo_slug}/hooks")
		Observable<PaginatedWebhookSubscriptions> getRepositoriesHooksPaginatedWebhookSubscriptions();
	
		@GET("/repositories/{username}/{repo_slug}/hooks")
		Observable<Error> getRepositoriesHooksError();
	
		@GET("/users/{username}")
		Observable<Error> getUsersErrorByUsername( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/user/emails")
		Observable<Error> getUserEmailsError();
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/diff")
		Observable<Error> getRepositoriesPullrequestsDiffError();
	
		@GET("/snippets/{username}/{encoded_id}/{revision}/patch")
		Observable<Error> getSnippetsPatchError( 
				
				@Path(value="encoded_id", encoded=true) String encodedId,			
				@Path(value="revision", encoded=true) String revision);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/activity")
		Observable<Error> getRepositoriesPullrequestsActivityError( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug);
	
		@GET("/users/{username}/followers")
		Observable<PaginatedUsers> getUsersFollowersPaginatedUsers( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/users/{username}/followers")
		Observable<Error> getUsersFollowersError( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/repositories/{username}/{repo_slug}/commit/{node}/statuses/build/{key}")
		Observable<Error> getRepositoriesCommitStatusesBuildErrorByKey( 
				
				@Path(value="node", encoded=true) String node,			
				@Path(value="key", encoded=true) String key);
	
		@GET("/teams/{username}/pipelines_config/variables/")
		Observable<PaginatedPipelineVariables> getTeamsPipelines_configVariablesPaginatedPipelineVariables( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/repositories/{username}/{repo_slug}/hooks/{uid}")
		Observable<Error> getRepositoriesHooksErrorByUid( 
				
				@Path(value="uid", encoded=true) String uid);
	
		@GET("/teams/{owner}/projects/")
		Observable<PaginatedProjects> getTeamsProjectsPaginatedProjects();
	
		@GET("/teams/{owner}/projects/")
		Observable<Error> getTeamsProjectsError();
	
		@GET("/repositories/{username}/{repo_slug}/components")
		Observable<PaginatedComponents> getRepositoriesComponentsPaginatedComponents();
	
		@GET("/repositories/{username}/{repo_slug}/components")
		Observable<Error> getRepositoriesComponentsError();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}")
		Observable<Error> getRepositoriesPipelinesErrorByPipelineUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pipeline_uuid", encoded=true) String pipelineUuid);
	
		@GET("/teams/{username}/following")
		Observable<PaginatedUsers> getTeamsFollowingPaginatedUsers( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/teams/{username}/following")
		Observable<Error> getTeamsFollowingError( 
				
				@Path(value="username", encoded=true) String username);
	
		@GET("/teams/{username}/hooks/{uid}")
		Observable<Error> getTeamsHooksErrorByUid( 
				
				@Path(value="uid", encoded=true) String uid);
	
		@GET("/repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/activity")
		Observable<Error> getRepositoriesPullrequestsActivityError( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pull_request_id", encoded=true) Integer pullRequestId);
	
		@GET("/user/emails/{email}")
		Observable<Error> getUserEmailsErrorByEmail();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/{step_uuid}")
		Observable<Error> getRepositoriesPipelinesStepsErrorByStepUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="pipeline_uuid", encoded=true) String pipelineUuid,			
				@Path(value="step_uuid", encoded=true) String stepUuid);
	
		@GET("/repositories/{username}/{repo_slug}/refs/branches")
		Observable<Error> getRepositoriesRefsBranchesError();
	
		@GET("/repositories/{username}/{repo_slug}/downloads")
		Observable<Error> getRepositoriesDownloadsError();
	
		@GET("/repositories/{username}/{repo_slug}/pipelines_config/ssh/known_hosts/{known_host_uuid}")
		Observable<Error> getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid( 
				
				@Path(value="username", encoded=true) String username,			
				@Path(value="repo_slug", encoded=true) String repoSlug,			
				@Path(value="known_host_uuid", encoded=true) String knownHostUuid);
	
		@GET("/users/{username}/hooks/{uid}")
		Observable<Error> getUsersHooksErrorByUid( 
				
				@Path(value="uid", encoded=true) String uid);
	
}