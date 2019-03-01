package org.eclipse.scava.workflow.restmule.generated.client.github.client;

import java.util.List;

import org.eclipse.scava.workflow.restmule.generated.client.github.model.*;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface IEntityEndpoint {

	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}")
		Observable<Organization> getOrgsOrganizationByOrg(			
				@Path(value="org", encoded=true) String org);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/emojis")
		Observable<Emojis> getEmojis();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/repos")
		Call<List<Repos>> getOrgsRepos(			
				@Path(value="org", encoded=true) String org,			
				@Query(value="type", encoded=true) String type,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/watchers")
		Call<List<Users>> getReposWatchersUsers(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/users/{username}/gists")
		Call<List<Gists>> getUsersGists(			
				@Path(value="username", encoded=true) String username,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repositories")
		Call<List<Repositories>> getRepositories(			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/hooks")
		Call<List<Hook>> getReposHooksHook(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/keys/{keyId}")
		Observable<UserKeysKeyId> getReposKeysUserKeysKeyIdByKeyId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="keyId", encoded=true) Integer keyId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/downloads")
		Observable<Downloads> getReposDownloads(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/comments")
		Call<List<IssuesComments>> getReposIssuesComments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/deployments")
		Call<List<RepoDeployments>> getReposDeploymentsRepoDeployments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/releases/{id}")
		Observable<Release> getReposReleasesReleaseById(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="id", encoded=true) String id);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/releases/{id}/assets")
		Call<List<Assets>> getReposReleasesAssets(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="id", encoded=true) String id,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/refs")
		Call<List<Refs>> getReposGitRefs(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/commits/{shaCode}")
		Observable<Commit> getReposCommitsCommitByShaCode(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/milestones")
		Observable<Milestone> getReposMilestonesMilestone(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="sort", encoded=true) String sort);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/languages")
		Observable<Languages> getReposLanguages(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}")
		Observable<Repo> getReposRepoByRepo(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/teams/{teamId}/memberships/{username}")
		Observable<TeamMembership> getTeamsMembershipsTeamMembershipByUsername(			
				@Path(value="teamId", encoded=true) Integer teamId,			
				@Path(value="username", encoded=true) String username);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/tags/{shaCode}")
		Observable<Tag> getReposGitTagsTagByShaCode(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/readme")
		Observable<ContentsPath> getReposReadmeContentsPath(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="ref", encoded=true) String ref);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists/{id}/comments")
		Call<List<Comments>> getGistsComments(			
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/comments")
		Call<List<RepoComments>> getReposCommentsRepoComments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/repos")
		Call<List<Repos>> getUserRepos(			
				@Query(value="type", encoded=true) String type,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/legacy/user/email/{email}")
		Observable<SearchUserByEmail> getLegacyUserEmailSearchUserByEmailByEmail(			
				@Path(value="email", encoded=true) String email);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/events")
		Observable<Events> getEvents();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/legacy/issues/search/{owner}/{repository}/{state}/{keyword}")
		Observable<SearchIssuesByKeyword> getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(			
				@Path(value="keyword", encoded=true) String keyword,			
				@Path(value="state", encoded=true) String state,			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repository", encoded=true) String repository);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/deployments/{id}/statuses")
		Call<List<DeploymentStatuses>> getReposDeploymentsStatusesDeploymentStatuses(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls")
		Call<List<Pulls>> getReposPulls(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="head", encoded=true) String head,			
				@Query(value="base", encoded=true) String base,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists")
		Call<List<Gists>> getGists(			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/followers")
		Call<List<Users>> getUserFollowersUsers(			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists/{id}")
		Observable<Gist> getGistsGistById(			
				@Path(value="id", encoded=true) Integer id);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists/{id}/comments/{commentId}")
		Observable<Comment> getGistsCommentsCommentByCommentId(			
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="commentId", encoded=true) Integer commentId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/issues")
		Call<List<Issues>> getIssues(			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/events/{eventId}")
		Observable<Event> getReposIssuesEventsEventByEventId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="eventId", encoded=true) Integer eventId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/networks/{owner}/{repo}/events")
		Observable<Events> getNetworksEvents(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/teams/{teamId}/members")
		Call<List<Users>> getTeamsMembersUsers(			
				@Path(value="teamId", encoded=true) Integer teamId,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/milestones/{number}/labels")
		Call<List<Labels>> getReposMilestonesLabels(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/members")
		Call<List<Users>> getOrgsMembersUsers(			
				@Path(value="org", encoded=true) String org,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/hooks/{hookId}")
		Call<List<Hook>> getReposHooksHookByHookId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="hookId", encoded=true) Integer hookId,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/refs/{ref}")
		Observable<HeadBranch> getReposGitRefsHeadBranchByRef(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="ref", encoded=true) String ref);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists/starred")
		Call<List<Gists>> getGistsStarredGists(			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/subscribers")
		Call<List<Users>> getReposSubscribersUsers(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/collaborators")
		Call<List<Users>> getReposCollaboratorsUsers(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/branches/{branch}")
		Observable<Branch> getReposBranchesBranchByBranch(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="branch", encoded=true) String branch);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/labels/{name}")
		Observable<Label> getReposLabelsLabelByName(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="name", encoded=true) String name);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/{number}/comments")
		Observable<PullsComment> getReposPullsCommentsPullsComment(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/labels")
		Call<List<Labels>> getReposLabels(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/subscription")
		Observable<Subscribition> getReposSubscriptionSubscribition(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/teams/{teamId}")
		Observable<Team> getTeamsTeamByTeamId(			
				@Path(value="teamId", encoded=true) Integer teamId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/comments/{commentId}")
		Observable<PullsComment> getReposPullsCommentsPullsCommentByCommentId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="commentId", encoded=true) Integer commentId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/commits")
		Call<List<Commits>> getReposCommits(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="sha", encoded=true) String sha,			
				@Query(value="path", encoded=true) String path,			
				@Query(value="author", encoded=true) String author,			
				@Query(value="until", encoded=true) String until,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/downloads/{downloadId}")
		Observable<Downloads> getReposDownloadsByDownloadId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="downloadId", encoded=true) Integer downloadId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/{number}/events")
		Observable<Events> getReposIssuesEvents(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/teams")
		Call<List<Teams>> getReposTeams(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/{number}/comments")
		Call<List<IssuesComments>> getReposIssuesComments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/events")
		Observable<Events> getOrgsEvents(			
				@Path(value="org", encoded=true) String org);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/public_members")
		Call<List<Users>> getOrgsPublic_membersUsers(			
				@Path(value="org", encoded=true) String org,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/meta")
		Observable<Meta> getMeta();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/events")
		Observable<Events> getReposIssuesEvents(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stats/commit_activity")
		Call<List<CommitActivityStats>> getReposStatsCommit_activityCommitActivityStats(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/branches")
		Call<List<Branches>> getReposBranches(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/users/{username}/repos")
		Call<List<Repos>> getUsersRepos(			
				@Path(value="username", encoded=true) String username,			
				@Query(value="type", encoded=true) String type,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/statuses/{ref}")
		Call<List<Ref>> getReposStatusesRefByRef(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="ref", encoded=true) String ref,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/legacy/repos/search/{keyword}")
		Observable<SearchRepositoriesByKeyword> getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(			
				@Path(value="keyword", encoded=true) String keyword,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="language", encoded=true) String language,			
				@Query(value="start_page", encoded=true) String startPage,			
				@Query(value="sort", encoded=true) String sort);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/milestones/{number}")
		Observable<Milestone> getReposMilestonesMilestoneByNumber(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/notifications")
		Observable<Notifications> getReposNotifications(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="all", encoded=true) Boolean all,			
				@Query(value="participating", encoded=true) Boolean participating,			
				@Query(value="since", encoded=true) String since);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/tags")
		Observable<Tags> getReposTags(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gists/public")
		Call<List<Gists>> getGistsPublicGists(			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/issues")
		Call<List<Issues>> getUserIssues(			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/teams")
		Call<List<TeamsList>> getUserTeamsTeamsList(			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/subscriptions")
		Call<List<UserUserIdSubscribitions>> getUserSubscriptionsUserUserIdSubscribitions(			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/notifications/threads/{id}")
		Observable<Notifications> getNotificationsThreadsNotificationsById(			
				@Path(value="id", encoded=true) Integer id);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/notifications")
		Observable<Notifications> getNotifications(			
				@Query(value="all", encoded=true) Boolean all,			
				@Query(value="participating", encoded=true) Boolean participating,			
				@Query(value="since", encoded=true) String since);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/releases")
		Call<List<Releases>> getReposReleases(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/{number}")
		Observable<PullRequest> getReposPullsPullRequestByNumber(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stargazers")
		Call<List<Users>> getReposStargazersUsers(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stats/code_frequency")
		Call<List<Integer>> getReposStatsCode_frequencyCodeFrequencyStats(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/assignees")
		Call<List<Assignees>> getReposAssignees(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/following")
		Call<List<Users>> getUserFollowingUsers(			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/gitignore/templates/{language}")
		Observable<GitignoreLang> getGitignoreTemplatesGitignoreLangByLanguage(			
				@Path(value="language", encoded=true) String language);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues")
		Call<List<Issues>> getReposIssues(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/comments/{commentId}")
		Observable<CommitComments> getReposCommentsCommitCommentsByCommentId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="commentId", encoded=true) Integer commentId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/{number}/files")
		Call<List<Pulls>> getReposPullsFilesPulls(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/contents/{path}")
		Observable<ContentsPath> getReposContentsContentsPathByPath(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="path", encoded=true) String path,			
				@Query(value="path", encoded=true) String path_0,			
				@Query(value="ref", encoded=true) String ref);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/rate_limit")
		Observable<RateLimit> getRate_limitRateLimit();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user")
		Observable<User> getUser();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stats/punch_card")
		Call<List<Integer>> getReposStatsPunch_cardCodeFrequencyStats(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stats/participation")
		Observable<ParticipationStats> getReposStatsParticipationParticipationStats(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/stats/contributors")
		Call<List<ContributorsStats>> getReposStatsContributorsContributorsStats(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/users/{username}")
		Call<List<Users>> getUsersByUsername(			
				@Path(value="username", encoded=true) String username,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/emails")
		Call<List<String>> getUserEmails(			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/events")
		Observable<Events> getReposEvents(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/commits/{shaCode}")
		Observable<RepoCommit> getReposGitCommitsRepoCommitByShaCode(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/feeds")
		Observable<Feeds> getFeeds();
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/{number}")
		Observable<Issue> getReposIssuesIssueByNumber(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/issues")
		Call<List<Issues>> getOrgsIssues(			
				@Path(value="org", encoded=true) String org,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="state", encoded=true) String state,			
				@Query(value="labels", encoded=true) String labels,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/users/{username}/followers")
		Call<List<Users>> getUsersFollowersUsers(			
				@Path(value="username", encoded=true) String username,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/contributors")
		Call<List<Contributors>> getReposContributors(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="anon", encoded=true) String anon,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/comments")
		Call<List<IssuesComments>> getReposPullsCommentsIssuesComments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="direction", encoded=true) String direction,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="since", encoded=true) String since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/{number}/labels")
		Call<List<Labels>> getReposIssuesLabels(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/pulls/{number}/commits")
		Call<List<Commits>> getReposPullsCommits(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="number", encoded=true) Integer number,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/commits/{ref}/status")
		Call<List<RefStatus>> getReposCommitsStatusRefStatus(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="ref", encoded=true) String ref,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/commits/{shaCode}/comments")
		Call<List<RepoComments>> getReposCommitsCommentsRepoComments(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/users")
		Call<List<Users>> getUsers(			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/blobs/{shaCode}")
		Observable<Blob> getReposGitBlobsBlobByShaCode(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/teams/{teamId}/repos")
		Call<List<TeamRepos>> getTeamsReposTeamRepos(			
				@Path(value="teamId", encoded=true) Integer teamId,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/user/keys/{keyId}")
		Observable<UserKeysKeyId> getUserKeysUserKeysKeyIdByKeyId(			
				@Path(value="keyId", encoded=true) Integer keyId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/forks")
		Call<List<Forks>> getReposForks(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/legacy/user/search/{keyword}")
		Observable<SearchUsersByKeyword> getLegacyUserSearchSearchUsersByKeywordByKeyword(			
				@Path(value="keyword", encoded=true) String keyword,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="start_page", encoded=true) String startPage,			
				@Query(value="sort", encoded=true) String sort);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/issues/comments/{commentId}")
		Observable<IssuesComment> getReposIssuesCommentsIssuesCommentByCommentId(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="commentId", encoded=true) Integer commentId);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/releases/assets/{id}")
		Observable<Asset> getReposReleasesAssetsAssetById(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="id", encoded=true) String id);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/orgs/{org}/teams")
		Call<List<Teams>> getOrgsTeams(			
				@Path(value="org", encoded=true) String org,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/git/trees/{shaCode}")
		Observable<Tree> getReposGitTreesTreeByShaCode(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Path(value="shaCode", encoded=true) String shaCode,			
				@Query(value="recursive", encoded=true) Integer recursive);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/repos/{owner}/{repo}/keys")
		Call<List<Keys>> getReposKeys(			
				@Path(value="owner", encoded=true) String owner,			
				@Path(value="repo", encoded=true) String repo,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/notifications/threads/{id}/subscription")
		Observable<Subscription> getNotificationsThreadsSubscription(			
				@Path(value="id", encoded=true) Integer id);
	
}