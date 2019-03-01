package org.eclipse.scava.workflow.restmule.generated.client.github.client;

import org.eclipse.scava.workflow.restmule.core.data.IData;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.*;

public interface IEntityApi {
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @return OK
	 * @path /orgs/{org} 
	 */		
	IData<Organization> getOrgsOrganizationByOrg(String org);
	
	/**
	 * null
	 * @return OK
	 * @path /emojis 
	 */		
	IData<Emojis> getEmojis();
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @param type null
	 * @return OK
	 * @path /orgs/{org}/repos 
	 */		
	IDataSet<Repos> getOrgsRepos(String org, String type);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/watchers 
	 */		
	IDataSet<Users> getReposWatchersUsers(String owner, String repo);
	
	/**
	 * null
	 * @param username Name of user.
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /users/{username}/gists 
	 */		
	IDataSet<Gists> getUsersGists(String username, String since);
	
	/**
	 * null
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /repositories 
	 */		
	IDataSet<Repositories> getRepositories(String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/hooks 
	 */		
	IDataSet<Hook> getReposHooksHook(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param keyId Id of key.
	 * @return OK
	 * @path /repos/{owner}/{repo}/keys/{keyId} 
	 */		
	IData<UserKeysKeyId> getReposKeysUserKeysKeyIdByKeyId(String owner, String repo, Integer keyId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/downloads 
	 */		
	IData<Downloads> getReposDownloads(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param direction Ignored without 'sort' parameter.
	 * @param sort 
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/comments 
	 */		
	IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, String direction, String sort, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/deployments 
	 */		
	IDataSet<RepoDeployments> getReposDeploymentsRepoDeployments(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param id null
	 * @return OK
	 * @path /repos/{owner}/{repo}/releases/{id} 
	 */		
	IData<Release> getReposReleasesReleaseById(String owner, String repo, String id);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param id null
	 * @return OK
	 * @path /repos/{owner}/{repo}/releases/{id}/assets 
	 */		
	IDataSet<Assets> getReposReleasesAssets(String owner, String repo, String id);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/refs 
	 */		
	IDataSet<Refs> getReposGitRefs(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode SHA-1 code of the commit.
	 * @return OK
	 * @path /repos/{owner}/{repo}/commits/{shaCode} 
	 */		
	IData<Commit> getReposCommitsCommitByShaCode(String owner, String repo, String shaCode);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param state String to filter by state.
	 * @param direction Ignored without 'sort' parameter.
	 * @param sort 
	 * @return OK
	 * @path /repos/{owner}/{repo}/milestones 
	 */		
	IData<Milestone> getReposMilestonesMilestone(String owner, String repo, String state, String direction, String sort);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/languages 
	 */		
	IData<Languages> getReposLanguages(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo} 
	 */		
	IData<Repo> getReposRepoByRepo(String owner, String repo);
	
	/**
	 * null
	 * @param teamId Id of team.
	 * @param username Name of a member.
	 * @return User is a member.
	 * @path /teams/{teamId}/memberships/{username} 
	 */		
	IData<TeamMembership> getTeamsMembershipsTeamMembershipByUsername(Integer teamId, String username);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode null
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/tags/{shaCode} 
	 */		
	IData<Tag> getReposGitTagsTagByShaCode(String owner, String repo, String shaCode);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param ref The String name of the Commit/Branch/Tag. Defaults to master.
	 * @return OK
	 * @path /repos/{owner}/{repo}/readme 
	 */		
	IData<ContentsPath> getReposReadmeContentsPath(String owner, String repo, String ref);
	
	/**
	 * null
	 * @param id Id of gist.
	 * @return OK
	 * @path /gists/{id}/comments 
	 */		
	IDataSet<Comments> getGistsComments(Integer id);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/comments 
	 */		
	IDataSet<RepoComments> getReposCommentsRepoComments(String owner, String repo);
	
	/**
	 * null
	 * @param type null
	 * @return OK
	 * @path /user/repos 
	 */		
	IDataSet<Repos> getUserRepos(String type);
	
	/**
	 * null
	 * @param email The email address
	 * @return OK
	 * @path /legacy/user/email/{email} 
	 */		
	IData<SearchUserByEmail> getLegacyUserEmailSearchUserByEmailByEmail(String email);
	
	/**
	 * null
	 * @return OK
	 * @path /events 
	 */		
	IData<Events> getEvents();
	
	/**
	 * null
	 * @param keyword The search term.
	 * @param state Indicates the state of the issues to return. Can be either open or closed.
	 * @param owner null
	 * @param repository null
	 * @return OK
	 * @path /legacy/issues/search/{owner}/{repository}/{state}/{keyword} 
	 */		
	IData<SearchIssuesByKeyword> getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(String keyword, String state, String owner, String repository);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param id The Deployment ID to list the statuses from.
	 * @return OK
	 * @path /repos/{owner}/{repo}/deployments/{id}/statuses 
	 */		
	IDataSet<DeploymentStatuses> getReposDeploymentsStatusesDeploymentStatuses(String owner, String repo, Integer id);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param state String to filter by state.
	 * @param head Filter pulls by head user and branch name in the format of 'user:ref-name'.
	               Example: github:new-script-format.
	 * @param base Filter pulls by base branch name. Example - gh-pages.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls 
	 */		
	IDataSet<Pulls> getReposPulls(String owner, String repo, String state, String head, String base);
	
	/**
	 * null
	 * @param since Timestamp in ISO 8601 format YYYY-MM-DDTHH:MM:SSZ.
	                Only gists updated at or after this time are returned.
	 * @return OK
	 * @path /gists 
	 */		
	IDataSet<Gists> getGists(String since);
	
	/**
	 * null
	 * @return OK
	 * @path /user/followers 
	 */		
	IDataSet<Users> getUserFollowersUsers();
	
	/**
	 * null
	 * @param id Id of gist.
	 * @return OK
	 * @path /gists/{id} 
	 */		
	IData<Gist> getGistsGistById(Integer id);
	
	/**
	 * null
	 * @param id Id of gist.
	 * @param commentId Id of comment.
	 * @return OK
	 * @path /gists/{id}/comments/{commentId} 
	 */		
	IData<Comment> getGistsCommentsCommentByCommentId(Integer id, Integer commentId);
	
	/**
	 * null
	 * @param filter Issues assigned to you / created by you / mentioning you / you're
	                 subscribed to updates for / All issues the authenticated user can see
	 * @param state null
	 * @param labels String list of comma separated Label names. Example - bug,ui,@high.
	 * @param sort null
	 * @param direction null
	 * @param since Optional string of a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Only issues updated at or after this time are returned.
	 * @return OK
	 * @path /issues 
	 */		
	IDataSet<Issues> getIssues(String filter, String state, String labels, String sort, String direction, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param eventId Id of the event.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/events/{eventId} 
	 */		
	IData<Event> getReposIssuesEventsEventByEventId(String owner, String repo, Integer eventId);
	
	/**
	 * null
	 * @param owner Name of the owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /networks/{owner}/{repo}/events 
	 */		
	IData<Events> getNetworksEvents(String owner, String repo);
	
	/**
	 * null
	 * @param teamId Id of team.
	 * @return OK
	 * @path /teams/{teamId}/members 
	 */		
	IDataSet<Users> getTeamsMembersUsers(Integer teamId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of milestone.
	 * @return OK
	 * @path /repos/{owner}/{repo}/milestones/{number}/labels 
	 */		
	IDataSet<Labels> getReposMilestonesLabels(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @return OK
	 * @path /orgs/{org}/members 
	 */		
	IDataSet<Users> getOrgsMembersUsers(String org);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param hookId Id of hook.
	 * @return OK
	 * @path /repos/{owner}/{repo}/hooks/{hookId} 
	 */		
	IDataSet<Hook> getReposHooksHookByHookId(String owner, String repo, Integer hookId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param ref null
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/refs/{ref} 
	 */		
	IData<HeadBranch> getReposGitRefsHeadBranchByRef(String owner, String repo, String ref);
	
	/**
	 * null
	 * @param since Timestamp in ISO 8601 format YYYY-MM-DDTHH:MM:SSZ.
	                Only gists updated at or after this time are returned.
	 * @return OK
	 * @path /gists/starred 
	 */		
	IDataSet<Gists> getGistsStarredGists(String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/subscribers 
	 */		
	IDataSet<Users> getReposSubscribersUsers(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/collaborators 
	 */		
	IDataSet<Users> getReposCollaboratorsUsers(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param branch Name of the branch.
	 * @return OK
	 * @path /repos/{owner}/{repo}/branches/{branch} 
	 */		
	IData<Branch> getReposBranchesBranchByBranch(String owner, String repo, String branch);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param name Name of the label.
	 * @return OK
	 * @path /repos/{owner}/{repo}/labels/{name} 
	 */		
	IData<Label> getReposLabelsLabelByName(String owner, String repo, String name);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Id of pull.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/{number}/comments 
	 */		
	IData<PullsComment> getReposPullsCommentsPullsComment(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/labels 
	 */		
	IDataSet<Labels> getReposLabels(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/subscription 
	 */		
	IData<Subscribition> getReposSubscriptionSubscribition(String owner, String repo);
	
	/**
	 * null
	 * @param teamId Id of team.
	 * @return OK
	 * @path /teams/{teamId} 
	 */		
	IData<Team> getTeamsTeamByTeamId(Integer teamId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param commentId Id of comment.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/comments/{commentId} 
	 */		
	IData<PullsComment> getReposPullsCommentsPullsCommentByCommentId(String owner, String repo, Integer commentId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @param sha Sha or branch to start listing commits from.
	 * @param path Only commits containing this file path will be returned.
	 * @param author GitHub login, name, or email by which to filter by commit author.
	 * @param until ISO 8601 Date - Only commits before this date will be returned.
	 * @return OK
	 * @path /repos/{owner}/{repo}/commits 
	 */		
	IDataSet<Commits> getReposCommits(String owner, String repo, String since, String sha, String path, String author, String until);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param downloadId Id of download.
	 * @return OK
	 * @path /repos/{owner}/{repo}/downloads/{downloadId} 
	 */		
	IData<Downloads> getReposDownloadsByDownloadId(String owner, String repo, Integer downloadId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of issue.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/{number}/events 
	 */		
	IData<Events> getReposIssuesEvents(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/teams 
	 */		
	IDataSet<Teams> getReposTeams(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of issue.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/{number}/comments 
	 */		
	IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @return OK
	 * @path /orgs/{org}/events 
	 */		
	IData<Events> getOrgsEvents(String org);
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @return OK
	 * @path /orgs/{org}/public_members 
	 */		
	IDataSet<Users> getOrgsPublic_membersUsers(String org);
	
	/**
	 * null
	 * @return OK
	 * @path /meta 
	 */		
	IData<Meta> getMeta();
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/events 
	 */		
	IData<Events> getReposIssuesEvents(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stats/commit_activity 
	 */		
	IDataSet<CommitActivityStats> getReposStatsCommit_activityCommitActivityStats(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/branches 
	 */		
	IDataSet<Branches> getReposBranches(String owner, String repo);
	
	/**
	 * null
	 * @param username Name of user.
	 * @param type null
	 * @return OK
	 * @path /users/{username}/repos 
	 */		
	IDataSet<Repos> getUsersRepos(String username, String type);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param ref Ref to list the statuses from. It can be a SHA, a branch name, or a tag name.
	 * @return OK
	 * @path /repos/{owner}/{repo}/statuses/{ref} 
	 */		
	IDataSet<Ref> getReposStatusesRefByRef(String owner, String repo, String ref);
	
	/**
	 * null
	 * @param keyword The search term
	 * @param order The sort field. if sort param is provided. Can be either asc or desc.
	 * @param language Filter results by language
	 * @param startPage The page number to fetch
	 * @param sort The sort field. One of stars, forks, or updated. Default: results are sorted by best match.
	 * @return OK
	 * @path /legacy/repos/search/{keyword} 
	 */		
	IData<SearchRepositoriesByKeyword> getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(String keyword, String order, String language, String startPage, String sort);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of milestone.
	 * @return OK
	 * @path /repos/{owner}/{repo}/milestones/{number} 
	 */		
	IData<Milestone> getReposMilestonesMilestoneByNumber(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param all True to show notifications marked as read.
	 * @param participating True to show only notifications in which the user is directly participating
	                        or mentioned.
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /repos/{owner}/{repo}/notifications 
	 */		
	IData<Notifications> getReposNotifications(String owner, String repo, Boolean all, Boolean participating, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/tags 
	 */		
	IData<Tags> getReposTags(String owner, String repo);
	
	/**
	 * null
	 * @param since Timestamp in ISO 8601 format YYYY-MM-DDTHH:MM:SSZ.
	                Only gists updated at or after this time are returned.
	 * @return OK
	 * @path /gists/public 
	 */		
	IDataSet<Gists> getGistsPublicGists(String since);
	
	/**
	 * null
	 * @param filter Issues assigned to you / created by you / mentioning you / you're
	                 subscribed to updates for / All issues the authenticated user can see
	 * @param state null
	 * @param labels String list of comma separated Label names. Example - bug,ui,@high.
	 * @param sort null
	 * @param direction null
	 * @param since Optional string of a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Only issues updated at or after this time are returned.
	 * @return OK
	 * @path /user/issues 
	 */		
	IDataSet<Issues> getUserIssues(String filter, String state, String labels, String sort, String direction, String since);
	
	/**
	 * null
	 * @return OK
	 * @path /user/teams 
	 */		
	IDataSet<TeamsList> getUserTeamsTeamsList();
	
	/**
	 * null
	 * @return OK
	 * @path /user/subscriptions 
	 */		
	IDataSet<UserUserIdSubscribitions> getUserSubscriptionsUserUserIdSubscribitions();
	
	/**
	 * null
	 * @param id Id of thread.
	 * @return OK
	 * @path /notifications/threads/{id} 
	 */		
	IData<Notifications> getNotificationsThreadsNotificationsById(Integer id);
	
	/**
	 * null
	 * @param all True to show notifications marked as read.
	 * @param participating True to show only notifications in which the user is directly participating
	                        or mentioned.
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /notifications 
	 */		
	IData<Notifications> getNotifications(Boolean all, Boolean participating, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/releases 
	 */		
	IDataSet<Releases> getReposReleases(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Id of pull.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/{number} 
	 */		
	IData<PullRequest> getReposPullsPullRequestByNumber(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stargazers 
	 */		
	IDataSet<Users> getReposStargazersUsers(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stats/code_frequency 
	 */		
	IDataSet<Integer> getReposStatsCode_frequencyCodeFrequencyStats(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/assignees 
	 */		
	IDataSet<Assignees> getReposAssignees(String owner, String repo);
	
	/**
	 * null
	 * @return OK
	 * @path /user/following 
	 */		
	IDataSet<Users> getUserFollowingUsers();
	
	/**
	 * null
	 * @param language null
	 * @return OK
	 * @path /gitignore/templates/{language} 
	 */		
	IData<GitignoreLang> getGitignoreTemplatesGitignoreLangByLanguage(String language);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param filter Issues assigned to you / created by you / mentioning you / you're
	                 subscribed to updates for / All issues the authenticated user can see
	 * @param state null
	 * @param labels String list of comma separated Label names. Example - bug,ui,@high.
	 * @param sort null
	 * @param direction null
	 * @param since Optional string of a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Only issues updated at or after this time are returned.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues 
	 */		
	IDataSet<Issues> getReposIssues(String owner, String repo, String filter, String state, String labels, String sort, String direction, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param commentId Id of comment.
	 * @return OK
	 * @path /repos/{owner}/{repo}/comments/{commentId} 
	 */		
	IData<CommitComments> getReposCommentsCommitCommentsByCommentId(String owner, String repo, Integer commentId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Id of pull.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/{number}/files 
	 */		
	IDataSet<Pulls> getReposPullsFilesPulls(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param path null
	 * @param path_0 The content path.
	 * @param ref The String name of the Commit/Branch/Tag. Defaults to 'master'.
	 * @return OK
	 * @path /repos/{owner}/{repo}/contents/{path} 
	 */		
	IData<ContentsPath> getReposContentsContentsPathByPath(String owner, String repo, String path, String path_0, String ref);
	
	/**
	 * null
	 * @return OK
	 * @path /rate_limit 
	 */		
	IData<RateLimit> getRate_limitRateLimit();
	
	/**
	 * null
	 * @return OK
	 * @path /user 
	 */		
	IData<User> getUser();
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stats/punch_card 
	 */		
	IDataSet<Integer> getReposStatsPunch_cardCodeFrequencyStats(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stats/participation 
	 */		
	IData<ParticipationStats> getReposStatsParticipationParticipationStats(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/stats/contributors 
	 */		
	IDataSet<ContributorsStats> getReposStatsContributorsContributorsStats(String owner, String repo);
	
	/**
	 * null
	 * @param username Name of user.
	 * @return OK
	 * @path /users/{username} 
	 */		
	IDataSet<Users> getUsersByUsername(String username);
	
	/**
	 * null
	 * @return OK
	 * @path /user/emails 
	 */		
	IDataSet<String> getUserEmails();
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/events 
	 */		
	IData<Events> getReposEvents(String owner, String repo);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode SHA-1 code.
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/commits/{shaCode} 
	 */		
	IData<RepoCommit> getReposGitCommitsRepoCommitByShaCode(String owner, String repo, String shaCode);
	
	/**
	 * null
	 * @return OK
	 * @path /feeds 
	 */		
	IData<Feeds> getFeeds();
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of issue.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/{number} 
	 */		
	IData<Issue> getReposIssuesIssueByNumber(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @param filter Issues assigned to you / created by you / mentioning you / you're
	                 subscribed to updates for / All issues the authenticated user can see
	 * @param state null
	 * @param labels String list of comma separated Label names. Example - bug,ui,@high.
	 * @param sort null
	 * @param direction null
	 * @param since Optional string of a timestamp in ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Only issues updated at or after this time are returned.
	 * @return OK
	 * @path /orgs/{org}/issues 
	 */		
	IDataSet<Issues> getOrgsIssues(String org, String filter, String state, String labels, String sort, String direction, String since);
	
	/**
	 * null
	 * @param username Name of user.
	 * @return OK
	 * @path /users/{username}/followers 
	 */		
	IDataSet<Users> getUsersFollowersUsers(String username);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param anon Set to 1 or true to include anonymous contributors in results.
	 * @return OK
	 * @path /repos/{owner}/{repo}/contributors 
	 */		
	IDataSet<Contributors> getReposContributors(String owner, String repo, String anon);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param direction Ignored without 'sort' parameter.
	 * @param sort 
	 * @param since The time should be passed in as UTC in the ISO 8601 format: YYYY-MM-DDTHH:MM:SSZ.
	                Example: "2012-10-09T23:39:01Z".
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/comments 
	 */		
	IDataSet<IssuesComments> getReposPullsCommentsIssuesComments(String owner, String repo, String direction, String sort, String since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Number of issue.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/{number}/labels 
	 */		
	IDataSet<Labels> getReposIssuesLabels(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param number Id of pull.
	 * @return OK
	 * @path /repos/{owner}/{repo}/pulls/{number}/commits 
	 */		
	IDataSet<Commits> getReposPullsCommits(String owner, String repo, Integer number);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param ref null
	 * @return OK
	 * @path /repos/{owner}/{repo}/commits/{ref}/status 
	 */		
	IDataSet<RefStatus> getReposCommitsStatusRefStatus(String owner, String repo, String ref);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode SHA-1 code of the commit.
	 * @return OK
	 * @path /repos/{owner}/{repo}/commits/{shaCode}/comments 
	 */		
	IDataSet<RepoComments> getReposCommitsCommentsRepoComments(String owner, String repo, String shaCode);
	
	/**
	 * null
	 * @param since The integer ID of the last User that you've seen.
	 * @return OK
	 * @path /users 
	 */		
	IDataSet<Users> getUsers(Integer since);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode SHA-1 code.
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/blobs/{shaCode} 
	 */		
	IData<Blob> getReposGitBlobsBlobByShaCode(String owner, String repo, String shaCode);
	
	/**
	 * null
	 * @param teamId Id of team.
	 * @return OK
	 * @path /teams/{teamId}/repos 
	 */		
	IDataSet<TeamRepos> getTeamsReposTeamRepos(Integer teamId);
	
	/**
	 * null
	 * @param keyId ID of key.
	 * @return OK
	 * @path /user/keys/{keyId} 
	 */		
	IData<UserKeysKeyId> getUserKeysUserKeysKeyIdByKeyId(Integer keyId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param sort null
	 * @return OK
	 * @path /repos/{owner}/{repo}/forks 
	 */		
	IDataSet<Forks> getReposForks(String owner, String repo, String sort);
	
	/**
	 * null
	 * @param keyword The search term
	 * @param order The sort field. if sort param is provided. Can be either asc or desc.
	 * @param startPage The page number to fetch
	 * @param sort The sort field. One of stars, forks, or updated. Default: results are sorted by best match.
	 * @return OK
	 * @path /legacy/user/search/{keyword} 
	 */		
	IData<SearchUsersByKeyword> getLegacyUserSearchSearchUsersByKeywordByKeyword(String keyword, String order, String startPage, String sort);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param commentId ID of comment.
	 * @return OK
	 * @path /repos/{owner}/{repo}/issues/comments/{commentId} 
	 */		
	IData<IssuesComment> getReposIssuesCommentsIssuesCommentByCommentId(String owner, String repo, Integer commentId);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param id null
	 * @return OK
	 * @path /repos/{owner}/{repo}/releases/assets/{id} 
	 */		
	IData<Asset> getReposReleasesAssetsAssetById(String owner, String repo, String id);
	
	/**
	 * null
	 * @param org Name of organisation.
	 * @return OK
	 * @path /orgs/{org}/teams 
	 */		
	IDataSet<Teams> getOrgsTeams(String org);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @param shaCode Tree SHA.
	 * @param recursive Get a Tree Recursively. (0 or 1)
	 * @return OK
	 * @path /repos/{owner}/{repo}/git/trees/{shaCode} 
	 */		
	IData<Tree> getReposGitTreesTreeByShaCode(String owner, String repo, String shaCode, Integer recursive);
	
	/**
	 * null
	 * @param owner Name of repository owner.
	 * @param repo Name of repository.
	 * @return OK
	 * @path /repos/{owner}/{repo}/keys 
	 */		
	IDataSet<Keys> getReposKeys(String owner, String repo);
	
	/**
	 * null
	 * @param id Id of thread.
	 * @return OK
	 * @path /notifications/threads/{id}/subscription 
	 */		
	IData<Subscription> getNotificationsThreadsSubscription(Integer id);
	
}