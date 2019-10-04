package org.eclipse.scava.crossflow.restmule.client.bitbucket.client;

import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.*;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.Error;

public interface IEntityApi {
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/watchers 
	 */		
	IData<Error> getRepositoriesWatchersError();
	
	/**
	 * null
	 * @return The milestones that have been defined in the issue tracker.
	 * @path /repositories/{username}/{repo_slug}/milestones 
	 */		
	IData<PaginatedMilestones> getRepositoriesMilestonesPaginatedMilestones();
	
	/**
	 * null
	 * @return If the specified repository does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/milestones 
	 */		
	IData<Error> getRepositoriesMilestonesError();
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param role Filters the result based on the authenticated user's role on each repository.
	               
	               * **member**: returns repositories to which the user has explicit read access
	               * **contributor**: returns repositories to which the user has explicit write access
	               * **admin**: returns repositories to which the user has explicit administrator access
	               * **owner**: returns all repositories owned by the current user
	 * @return The repositories owned by the specified account.
	 * @path /repositories/{username} 
	 */		
	IData<PaginatedRepositories> getRepositoriesPaginatedRepositoriesByUsername(String username, String role);
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param role Filters the result based on the authenticated user's role on each repository.
	               
	               * **member**: returns repositories to which the user has explicit read access
	               * **contributor**: returns repositories to which the user has explicit write access
	               * **admin**: returns repositories to which the user has explicit administrator access
	               * **owner**: returns all repositories owned by the current user
	 * @return If the specified account does not exist.
	 * @path /repositories/{username} 
	 */		
	IData<Error> getRepositoriesErrorByUsername(String username, String role);
	
	/**
	 * null
	 * @param encodedId The snippet's id.
	 * @return If the snippet is private and the request was not authenticated.
	 * @path /snippets/{username}/{encoded_id} 
	 */		
	IData<Error> getSnippetsErrorByEncodedId(String encodedId);
	
	/**
	 * null
	 * @param context Generate diffs with <n> lines of context instead of the usual three
	 * @param path Limit the diff to a single file
	 * @return If the diff was too large and timed out.
	           
	           Since this endpoint does not employ any form of pagination, but
	           instead returns the diff as a single document, it can run into
	           trouble on very large diffs. If Bitbucket times out in cases
	           like these, a 555 status code is returned.
	 * @path /repositories/{username}/{repo_slug}/diff/{spec} 
	 */		
	IData<Error> getRepositoriesDiffErrorBySpec(Integer context, String path);
	
	/**
	 * null
	 * @param role Filter down the result based on the authenticated user's role (`owner`, `contributor`, or `member`).
	 * @param username Limits the result to snippets owned by this user.
	 * @return A paginated list of snippets.
	 * @path /snippets/{username} 
	 */		
	IData<PaginatedSnippets> getSnippetsPaginatedSnippetsByUsername(String role, String username);
	
	/**
	 * null
	 * @param role Filter down the result based on the authenticated user's role (`owner`, `contributor`, or `member`).
	 * @param username Limits the result to snippets owned by this user.
	 * @return If the user does not exist.
	 * @path /snippets/{username} 
	 */		
	IData<Error> getSnippetsErrorByUsername(String role, String username);
	
	/**
	 * null
	 * @param role Filter down the result based on the authenticated user's role (`owner`, `contributor`, or `member`).
	 * @return A paginated list of snippets.
	 * @path /snippets 
	 */		
	IData<PaginatedSnippets> getSnippetsPaginatedSnippets(String role);
	
	/**
	 * null
	 * @param role Filter down the result based on the authenticated user's role (`owner`, `contributor`, or `member`).
	 * @return If the snippet does not exist.
	 * @path /snippets 
	 */		
	IData<Error> getSnippetsError(String role);
	
	/**
	 * null
	 * @param format Instead of returning the file's contents, return the (json) meta data for it.
	 * @return If the path or commit in the URL does not exist.
	 * @path /repositories/{username}/{repo_slug}/src/{node}/{path} 
	 */		
	IData<Error> getRepositoriesSrcErrorByPath(String format);
	
	/**
	 * null
	 * @return All forks.
	 * @path /repositories/{username}/{repo_slug}/forks 
	 */		
	IData<PaginatedRepositories> getRepositoriesForksPaginatedRepositories();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/refs/tags/{name} 
	 */		
	IData<Error> getRepositoriesRefsTagsErrorByName();
	
	/**
	 * null
	 * @param after Filter the results to include only repositories create on or
	                after this [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601)
	                 timestamp. Example: `YYYY-MM-DDTHH:mm:ss.sssZ`
	 * @return All public repositories.
	 * @path /repositories 
	 */		
	IData<PaginatedRepositories> getRepositoriesPaginatedRepositories(String after);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/comments/{comment_id} 
	 */		
	IData<Error> getRepositoriesIssuesCommentsErrorByCommentId();
	
	/**
	 * null
	 * @param role Filters the teams based on the authenticated user's role on each team.
	               
	               * **member**: returns a list of all the teams which the caller is a member of
	                 at least one team group or repository owned by the team
	               * **contributor**: returns a list of teams which the caller has write access
	                 to at least one repository owned by the team
	               * **admin**: returns a list teams which the caller has team administrator access
	 * @return A paginated list of teams.
	 * @path /teams 
	 */		
	IData<PaginatedTeams> getTeamsPaginatedTeams(String role);
	
	/**
	 * null
	 * @param role Filters the teams based on the authenticated user's role on each team.
	               
	               * **member**: returns a list of all the teams which the caller is a member of
	                 at least one team group or repository owned by the team
	               * **contributor**: returns a list of teams which the caller has write access
	                 to at least one repository owned by the team
	               * **admin**: returns a list teams which the caller has team administrator access
	 * @return When the request wasn't authenticated.
	 * @path /teams 
	 */		
	IData<Error> getTeamsError(String role);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/commits 
	 */		
	IData<Error> getRepositoriesCommitsError();
	
	/**
	 * null
	 * @return A paginated list of the issues matching any filter criteria that were provided.
	 * @path /repositories/{username}/{repo_slug}/issues 
	 */		
	IData<PaginatedIssues> getRepositoriesIssuesPaginatedIssues();
	
	/**
	 * null
	 * @return If the specified repository or version does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/issues 
	 */		
	IData<Error> getRepositoriesIssuesError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/refs/branches/{name} 
	 */		
	IData<Error> getRepositoriesRefsBranchesErrorByName();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /teams/{username}/repositories 
	 */		
	IData<Error> getTeamsRepositoriesError();
	
	/**
	 * null
	 * @return A paginated list of snippet comments, ordered by creation date.
	 * @path /snippets/{username}/{encoded_id}/comments 
	 */		
	IData<PaginatedSnippetComments> getSnippetsCommentsPaginatedSnippetComments();
	
	/**
	 * null
	 * @return If the authenticated user does not have access to the snippet.
	 * @path /snippets/{username}/{encoded_id}/comments 
	 */		
	IData<Error> getSnippetsCommentsError();
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @return If the repository is private and the authenticated user does not have access to it.
	 * @path /repositories/{username}/{repo_slug} 
	 */		
	IData<Error> getRepositoriesErrorByRepoSlug(String username, String repoSlug);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /users/{username}/repositories 
	 */		
	IData<Error> getUsersRepositoriesError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/refs 
	 */		
	IData<Error> getRepositoriesRefsError();
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the account,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @param pullRequestId The id of the pull request.
	 * @return If the repository or pull request does not exist
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id} 
	 */		
	IData<Error> getRepositoriesPullrequestsErrorByPullRequestId(String username, String repoSlug, Integer pullRequestId);
	
	/**
	 * null
	 * @param username The user's username
	 * @return A paginated list of user objects.
	 * @path /users/{username}/following 
	 */		
	IData<PaginatedUsers> getUsersFollowingPaginatedUsers(String username);
	
	/**
	 * null
	 * @param username The user's username
	 * @return If no user exists for the specified name, or if the specified account is a team account, not a personal account.
	 * @path /users/{username}/following 
	 */		
	IData<Error> getUsersFollowingError(String username);
	
	/**
	 * null
	 * @param issueId The issue's id
	 * @return When the request wasn't authenticated.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/vote 
	 */		
	IData<Error> getRepositoriesIssuesVoteError(Integer issueId);
	
	/**
	 * null
	 * @return If the file or snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/{node_id}/files/{path} 
	 */		
	IData<Error> getSnippetsFilesErrorByPath();
	
	/**
	 * null
	 * @param username The account.
	 * @param variableUuid The UUID of the variable to retrieve.
	 * @return The account or variable with the given UUID was not found.
	 * @path /teams/{username}/pipelines_config/variables/{variable_uuid} 
	 */		
	IData<Error> getTeamsPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/comments 
	 */		
	IData<Error> getRepositoriesIssuesCommentsError();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param pipelineUuid The UUID of the pipeline.
	 * @return The steps.
	 * @path /repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/ 
	 */		
	IData<PaginatedPipelineSteps> getRepositoriesPipelinesStepsPaginatedPipelineSteps(String username, String repoSlug, String pipelineUuid);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /addon/linkers/{linker_key} 
	 */		
	IData<Error> getAddonLinkersErrorByLinkerKey();
	
	/**
	 * null
	 * @param node The commit's SHA1
	 * @return A paginated list of all commit statuses for this commit.
	 * @path /repositories/{username}/{repo_slug}/commit/{node}/statuses 
	 */		
	IData<PaginatedCommitstatuses> getRepositoriesCommitStatusesPaginatedCommitstatuses(String node);
	
	/**
	 * null
	 * @param node The commit's SHA1
	 * @return If the repository or commit does not exist
	 * @path /repositories/{username}/{repo_slug}/commit/{node}/statuses 
	 */		
	IData<Error> getRepositoriesCommitStatusesError(String node);
	
	/**
	 * null
	 * @param username The team's username
	 * @return A paginated list of user objects.
	 * @path /teams/{username}/followers 
	 */		
	IData<PaginatedUsers> getTeamsFollowersPaginatedUsers(String username);
	
	/**
	 * null
	 * @param username The team's username
	 * @return If no team exists for the specified name, or if the specified account is a personal account, not a team account.
	 * @path /teams/{username}/followers 
	 */		
	IData<Error> getTeamsFollowersError(String username);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/refs/tags 
	 */		
	IData<Error> getRepositoriesRefsTagsError();
	
	/**
	 * null
	 * @return The paginated list of installed webhooks.
	 * @path /teams/{username}/hooks 
	 */		
	IData<PaginatedWebhookSubscriptions> getTeamsHooksPaginatedWebhookSubscriptions();
	
	/**
	 * null
	 * @return If the specified team does not exist.
	 * @path /teams/{username}/hooks 
	 */		
	IData<Error> getTeamsHooksError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/commit/{sha}/comments/{comment_id} 
	 */		
	IData<Error> getRepositoriesCommitCommentsErrorByCommentId();
	
	/**
	 * null
	 * @param encodedId The snippet id.
	 * @return The paginated list of users watching this snippet
	 * @path /snippets/{username}/{encoded_id}/watchers 
	 */		
	IData<PaginatedUsers> getSnippetsWatchersPaginatedUsers(String encodedId);
	
	/**
	 * null
	 * @param encodedId The snippet id.
	 * @return If the snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/watchers 
	 */		
	IData<Error> getSnippetsWatchersError(String encodedId);
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @return The retrieved variables.
	 * @path /repositories/{username}/{repo_slug}/pipelines_config/variables/ 
	 */		
	IData<PaginatedPipelineVariables> getRepositoriesPipelines_configVariablesPaginatedPipelineVariables(String username, String repoSlug);
	
	/**
	 * null
	 * @param encodedId The snippet's id.
	 * @param nodeId A commit revision (SHA1).
	 * @return If the snippet, or the revision does not exist.
	 * @path /snippets/{username}/{encoded_id}/{node_id} 
	 */		
	IData<Error> getSnippetsErrorByNodeId(String encodedId, String nodeId);
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @param state Only return pull requests that are in this state. This parameter can be repeated.
	 * @return All pull requests on the specified repository.
	 * @path /repositories/{username}/{repo_slug}/pullrequests 
	 */		
	IData<PaginatedPullrequests> getRepositoriesPullrequestsPaginatedPullrequests(String username, String repoSlug, String state);
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @param state Only return pull requests that are in this state. This parameter can be repeated.
	 * @return If the specified repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/pullrequests 
	 */		
	IData<Error> getRepositoriesPullrequestsError(String username, String repoSlug, String state);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/patch 
	 */		
	IData<Error> getRepositoriesPullrequestsPatchError();
	
	/**
	 * null
	 * @param subjectType A resource or subject type.
	 * @return A paginated list of webhook types available to subscribe on.
	 * @path /hook_events/{subject_type} 
	 */		
	IData<PaginatedHookEvents> getHook_eventsPaginatedHookEventsBySubjectType(String subjectType);
	
	/**
	 * null
	 * @param subjectType A resource or subject type.
	 * @return If an invalid `{subject_type}` value was specified.
	 * @path /hook_events/{subject_type} 
	 */		
	IData<Error> getHook_eventsErrorBySubjectType(String subjectType);
	
	/**
	 * null
	 * @param pullRequestId The pull request's id
	 * @return A paginated list of all commit statuses for this pull request.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/statuses 
	 */		
	IData<PaginatedCommitstatuses> getRepositoriesPullrequestsStatusesPaginatedCommitstatuses(Integer pullRequestId);
	
	/**
	 * null
	 * @param pullRequestId The pull request's id
	 * @return If the specified repository or pull request does not exist.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/statuses 
	 */		
	IData<Error> getRepositoriesPullrequestsStatusesError(Integer pullRequestId);
	
	/**
	 * null
	 * @return If the specified repository or issue does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id} 
	 */		
	IData<Error> getRepositoriesIssuesErrorByIssueId();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @return The matching pipelines.
	 * @path /repositories/{username}/{repo_slug}/pipelines/ 
	 */		
	IData<PaginatedPipelines> getRepositoriesPipelinesPaginatedPipelines(String username, String repoSlug);
	
	/**
	 * null
	 * @return The versions that have been defined in the issue tracker.
	 * @path /repositories/{username}/{repo_slug}/versions 
	 */		
	IData<PaginatedVersions> getRepositoriesVersionsPaginatedVersions();
	
	/**
	 * null
	 * @return If the specified repository does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/versions 
	 */		
	IData<Error> getRepositoriesVersionsError();
	
	/**
	 * null
	 * @return If the specified repository or issue does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/attachments/{path} 
	 */		
	IData<Error> getRepositoriesIssuesAttachmentsErrorByPath();
	
	/**
	 * null
	 * @param issueId The issue's id
	 * @return If the authenticated user is not watching this issue, or when the repo does not exist, or does not have an issue tracker.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/watch 
	 */		
	IData<Error> getRepositoriesIssuesWatchError(Integer issueId);
	
	/**
	 * null
	 * @return A project isn't hosted at this location.
	 * @path /teams/{owner}/projects/{project_key} 
	 */		
	IData<Error> getTeamsProjectsErrorByProjectKey();
	
	/**
	 * null
	 * @return The paginated list of snippet commits.
	 * @path /snippets/{username}/{encoded_id}/commits 
	 */		
	IData<PaginatedSnippetCommit> getSnippetsCommitsPaginatedSnippetCommit();
	
	/**
	 * null
	 * @return If the snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/commits 
	 */		
	IData<Error> getSnippetsCommitsError();
	
	/**
	 * null
	 * @param id The restriction rule's id
	 * @return If the authenticated user does not have admin access to the repository
	 * @path /repositories/{username}/{repo_slug}/branch-restrictions/{id} 
	 */		
	IData<Error> getRepositoriesBranch_restrictionsErrorById(String id);
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @return The retrieved known hosts.
	 * @path /repositories/{username}/{repo_slug}/pipelines_config/ssh/known_hosts/ 
	 */		
	IData<PaginatedPipelineKnownHosts> getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts(String username, String repoSlug);
	
	/**
	 * null
	 * @param username The account.
	 * @return The found user level variables.
	 * @path /users/{username}/pipelines_config/variables/ 
	 */		
	IData<PaginatedPipelineVariables> getUsersPipelines_configVariablesPaginatedPipelineVariables(String username);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /addon/linkers/{linker_key}/values 
	 */		
	IData<Error> getAddonLinkersValuesError();
	
	/**
	 * null
	 * @param versionId The version's id
	 * @return If the specified repository or version does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/versions/{version_id} 
	 */		
	IData<Error> getRepositoriesVersionsErrorByVersionId(Integer versionId);
	
	/**
	 * null
	 * @return The paginated list of installed webhooks.
	 * @path /users/{username}/hooks 
	 */		
	IData<PaginatedWebhookSubscriptions> getUsersHooksPaginatedWebhookSubscriptions();
	
	/**
	 * null
	 * @return If the specified account does not exist.
	 * @path /users/{username}/hooks 
	 */		
	IData<Error> getUsersHooksError();
	
	/**
	 * null
	 * @param revision The commit's SHA1.
	 * @return If the specified commit or repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/commit/{revision} 
	 */		
	IData<Error> getRepositoriesCommitErrorByRevision(String revision);

	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param pipelineUuid The UUID of the pipeline.
	 * @param stepUuid The UUID of the step.
	 * @return The requested range does not exist for requests that specified the [HTTP Range header](https://tools.ietf.org/html/rfc7233#section-3.1).
	 * @path /repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/{step_uuid}/log 
	 */		
	IData<Error> getRepositoriesPipelinesStepsLogError(String username, String repoSlug, String pipelineUuid, String stepUuid);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/commits 
	 */		
	IData<Error> getRepositoriesPullrequestsCommitsError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/downloads/{filename} 
	 */		
	IData<Error> getRepositoriesDownloadsErrorByFilename();
	
	/**
	 * null
	 * @param encodedId The snippet id.
	 * @return If the authenticated user is watching the snippet.
	 * @path /snippets/{username}/{encoded_id}/watch 
	 */		
	IData<PaginatedUsers> getSnippetsWatchPaginatedUsers(String encodedId);
	
	/**
	 * null
	 * @param encodedId The snippet id.
	 * @return If the snippet does not exist, or if the authenticated user is not watching the snippet.
	 * @path /snippets/{username}/{encoded_id}/watch 
	 */		
	IData<Error> getSnippetsWatchError(String encodedId);
	
	/**
	 * null
	 * @param componentId The component's id
	 * @return If the specified repository or component does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/components/{component_id} 
	 */		
	IData<Error> getRepositoriesComponentsErrorByComponentId(Integer componentId);
	
	/**
	 * null
	 * @param username The account.
	 * @param variableUuid The UUID of the variable to retrieve.
	 * @return The account or variable with the given UUID was not found.
	 * @path /users/{username}/pipelines_config/variables/{variable_uuid} 
	 */		
	IData<Error> getUsersPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid);
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @return The account, repository or SSH key pair was not found.
	 * @path /repositories/{username}/{repo_slug}/pipelines_config/ssh/key_pair 
	 */		
	IData<Error> getRepositoriesPipelines_configSshKey_pairError(String username, String repoSlug);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/commit/{sha}/comments 
	 */		
	IData<Error> getRepositoriesCommitCommentsError();
	
	/**
	 * null
	 * @return A paginated list of branch restrictions
	 * @path /repositories/{username}/{repo_slug}/branch-restrictions 
	 */		
	IData<PaginatedBranchrestrictions> getRepositoriesBranch_restrictionsPaginatedBranchrestrictions();
	
	/**
	 * null
	 * @return If the repository does not exist
	 * @path /repositories/{username}/{repo_slug}/branch-restrictions 
	 */		
	IData<Error> getRepositoriesBranch_restrictionsError();
	
	/**
	 * null
	 * @return If the commit or the snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/commits/{revision} 
	 */		
	IData<Error> getSnippetsCommitsErrorByRevision();
	
	/**
	 * null
	 * @return If the diff was too large and timed out.
	           
	           Since this endpoint does not employ any form of pagination, but
	           instead returns the diff as a single document, it can run into
	           trouble on very large diffs. If Bitbucket times out in cases
	           like these, a 555 status code is returned.
	 * @path /repositories/{username}/{repo_slug}/patch/{spec} 
	 */		
	IData<Error> getRepositoriesPatchErrorBySpec();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param variableUuid The UUID of the variable to retrieve.
	 * @return The account, repository or variable with the specified UUID was not found.
	 * @path /repositories/{username}/{repo_slug}/pipelines_config/variables/{variable_uuid} 
	 */		
	IData<Error> getRepositoriesPipelines_configVariablesErrorByVariableUuid(String username, String repoSlug, String variableUuid);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /addon/linkers 
	 */		
	IData<Error> getAddonLinkersError();
	
	/**
	 * null
	 * @param username The team's username or UUID.
	 * @return If no team exists for the specified name or UUID, or if the specified account is a personal account, not a team account.
	 * @path /teams/{username} 
	 */		
	IData<Error> getTeamsErrorByUsername(String username);
	
	/**
	 * null
	 * @return If the comment or snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/comments/{comment_id} 
	 */		
	IData<Error> getSnippetsCommentsErrorByCommentId();
	
	/**
	 * null
	 * @param path When used, only one the diff of the specified file will be returned.
	 * @param encodedId The snippet id.
	 * @param revision A revspec expression. This can simply be a commit SHA1, a ref name, or a compare expression like `staging..production`.
	 * @return If the snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/{revision}/diff 
	 */		
	IData<Error> getSnippetsDiffError(String path, String encodedId, String revision);
	
	/**
	 * null
	 * @return When the request wasn't authenticated.
	 * @path /user 
	 */		
	IData<Error> getUserError();
	
	/**
	 * null
	 * @return A mapping of resource/subject types pointing to their individual event types.
	 * @path /hook_events 
	 */		
	IData<SubjectTypes> getHook_eventsSubjectTypes();
	
	/**
	 * null
	 * @param milestoneId The milestone's id
	 * @return If the specified repository or milestone does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/milestones/{milestone_id} 
	 */		
	IData<Error> getRepositoriesMilestonesErrorByMilestoneId(Integer milestoneId);
	
	/**
	 * null
	 * @return When the team does not exist, or multiple teams with the same name exist that differ only in casing and the URL did not match the exact casing of a particular one.
	 * @path /teams/{username}/members 
	 */		
	IData<Error> getTeamsMembersError();
	
	/**
	 * null
	 * @param issueId The issue's id
	 * @return A paginated list of all attachments for this issue.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/attachments 
	 */		
	IData<PaginatedIssueAttachments> getRepositoriesIssuesAttachmentsPaginatedIssueAttachments(Integer issueId);
	
	/**
	 * null
	 * @param issueId The issue's id
	 * @return If the specified repository or version does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/issues/{issue_id}/attachments 
	 */		
	IData<Error> getRepositoriesIssuesAttachmentsError(Integer issueId);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/commits/{revision} 
	 */		
	IData<Error> getRepositoriesCommitsErrorByRevision();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/default-reviewers/{target_username} 
	 */		
	IData<Error> getRepositoriesDefault_reviewersErrorByTargetUsername();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/comments/{comment_id} 
	 */		
	IData<Error> getRepositoriesPullrequestsCommentsErrorByCommentId();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/comments 
	 */		
	IData<Error> getRepositoriesPullrequestsCommentsError();
	
	/**
	 * null
	 * @return The paginated list of installed webhooks.
	 * @path /repositories/{username}/{repo_slug}/hooks 
	 */		
	IData<PaginatedWebhookSubscriptions> getRepositoriesHooksPaginatedWebhookSubscriptions();
	
	/**
	 * null
	 * @return If the repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/hooks 
	 */		
	IData<Error> getRepositoriesHooksError();
	
	/**
	 * null
	 * @param username The account's username or UUID.
	 * @return If no user exists for the specified name or UUID, or if the specified account is a team account, not a personal account.
	 * @path /users/{username} 
	 */		
	IData<Error> getUsersErrorByUsername(String username);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /user/emails 
	 */		
	IData<Error> getUserEmailsError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/diff 
	 */		
	IData<Error> getRepositoriesPullrequestsDiffError();
	
	/**
	 * null
	 * @param encodedId The snippet id.
	 * @param revision A revspec expression. This can simply be a commit SHA1, a ref name, or a compare expression like `staging..production`.
	 * @return If the snippet does not exist.
	 * @path /snippets/{username}/{encoded_id}/{revision}/patch 
	 */		
	IData<Error> getSnippetsPatchError(String encodedId, String revision);
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @return If the specified repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/activity 
	 */		
	IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug);
	
	/**
	 * null
	 * @param username The account's username
	 * @return A paginated list of user objects.
	 * @path /users/{username}/followers 
	 */		
	IData<PaginatedUsers> getUsersFollowersPaginatedUsers(String username);
	
	/**
	 * null
	 * @param username The account's username
	 * @return If no account exists for the specified name, or if the specified account is a team account, not a personal account.
	 * @path /users/{username}/followers 
	 */		
	IData<Error> getUsersFollowersError(String username);
	
	/**
	 * null
	 * @param node The commit's SHA1
	 * @param key The build status' unique key
	 * @return If the repository, commit, or build status key does not exist
	 * @path /repositories/{username}/{repo_slug}/commit/{node}/statuses/build/{key} 
	 */		
	IData<Error> getRepositoriesCommitStatusesBuildErrorByKey(String node, String key);
	
	/**
	 * null
	 * @param username The account.
	 * @return The found account level variables.
	 * @path /teams/{username}/pipelines_config/variables/ 
	 */		
	IData<PaginatedPipelineVariables> getTeamsPipelines_configVariablesPaginatedPipelineVariables(String username);
	
	/**
	 * null
	 * @param uid The installed webhook's id.
	 * @return If the webhook or repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/hooks/{uid} 
	 */		
	IData<Error> getRepositoriesHooksErrorByUid(String uid);
	
	/**
	 * null
	 * @return A paginated list of projects that belong to the specified team.
	 * @path /teams/{owner}/projects/ 
	 */		
	IData<PaginatedProjects> getTeamsProjectsPaginatedProjects();
	
	/**
	 * null
	 * @return A team doesn't exist at this location.
	 * @path /teams/{owner}/projects/ 
	 */		
	IData<Error> getTeamsProjectsError();
	
	/**
	 * null
	 * @return The components that have been defined in the issue tracker.
	 * @path /repositories/{username}/{repo_slug}/components 
	 */		
	IData<PaginatedComponents> getRepositoriesComponentsPaginatedComponents();
	
	/**
	 * null
	 * @return If the specified repository does not exist, or if the repository doesn't have the issue tracker enabled.
	 * @path /repositories/{username}/{repo_slug}/components 
	 */		
	IData<Error> getRepositoriesComponentsError();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param pipelineUuid The pipeline UUID.
	 * @return No account, repository or pipeline with the UUID provided exists.
	 * @path /repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid} 
	 */		
	IData<Error> getRepositoriesPipelinesErrorByPipelineUuid(String username, String repoSlug, String pipelineUuid);
	
	/**
	 * null
	 * @param username The team's username
	 * @return A paginated list of user objects.
	 * @path /teams/{username}/following 
	 */		
	IData<PaginatedUsers> getTeamsFollowingPaginatedUsers(String username);
	
	/**
	 * null
	 * @param username The team's username
	 * @return If no team exists for the specified name, or if the specified account is a personal account, not a team account.
	 * @path /teams/{username}/following 
	 */		
	IData<Error> getTeamsFollowingError(String username);
	
	/**
	 * null
	 * @param uid The installed webhook's id.
	 * @return If the webhook or team does not exist.
	 * @path /teams/{username}/hooks/{uid} 
	 */		
	IData<Error> getTeamsHooksErrorByUid(String uid);
	
	/**
	 * null
	 * @param username This can either be the username or the UUID of the user,
	                   surrounded by curly-braces, for example: `{user UUID}`.
	 * @param repoSlug This can either be the repository slug or the UUID of the repository,
	                   surrounded by curly-braces, for example: `{repository UUID}`.
	 * @param pullRequestId The id of the pull request.
	 * @return If the specified repository does not exist.
	 * @path /repositories/{username}/{repo_slug}/pullrequests/{pull_request_id}/activity 
	 */		
	IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug, Integer pullRequestId);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /user/emails/{email} 
	 */		
	IData<Error> getUserEmailsErrorByEmail();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param pipelineUuid The UUID of the pipeline.
	 * @param stepUuid The UUID of the step.
	 * @return No account, repository, pipeline or step with the UUID provided exists for the pipeline with the UUID provided.
	 * @path /repositories/{username}/{repo_slug}/pipelines/{pipeline_uuid}/steps/{step_uuid} 
	 */		
	IData<Error> getRepositoriesPipelinesStepsErrorByStepUuid(String username, String repoSlug, String pipelineUuid, String stepUuid);
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/refs/branches 
	 */		
	IData<Error> getRepositoriesRefsBranchesError();
	
	/**
	 * null
	 * @return Unexpected error.
	 * @path /repositories/{username}/{repo_slug}/downloads 
	 */		
	IData<Error> getRepositoriesDownloadsError();
	
	/**
	 * null
	 * @param username The account.
	 * @param repoSlug The repository.
	 * @param knownHostUuid The UUID of the known host to retrieve.
	 * @return The account, repository or known host with the specified UUID was not found.
	 * @path /repositories/{username}/{repo_slug}/pipelines_config/ssh/known_hosts/{known_host_uuid} 
	 */		
	IData<Error> getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid(String username, String repoSlug, String knownHostUuid);
	
	/**
	 * null
	 * @param uid The installed webhook's id.
	 * @return If the webhook or user does not exist.
	 * @path /users/{username}/hooks/{uid} 
	 */		
	IData<Error> getUsersHooksErrorByUid(String uid);
	
}