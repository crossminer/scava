package org.eclipse.scava.workflow.restmule.generated.client.github.api;

import org.apache.commons.lang3.Validate;
import org.eclipse.scava.workflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.workflow.restmule.core.data.IData;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.core.session.ISession;
import org.eclipse.scava.workflow.restmule.generated.client.github.client.EntityApi;
import org.eclipse.scava.workflow.restmule.generated.client.github.client.IEntityApi;
import org.eclipse.scava.workflow.restmule.generated.client.github.client.ISearchApi;
import org.eclipse.scava.workflow.restmule.generated.client.github.client.SearchApi;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.*;
import org.eclipse.scava.workflow.restmule.generated.client.github.session.GitHubSession;

public class GitHubApi  {

	public static GitHubBuilder create(){
		return new GitHubBuilder(); 
	}
	
	public static IGitHubApi createDefault(){ 
		return new GitHubBuilder().setSession(GitHubSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class GitHubBuilder 
	implements IClientBuilder<IGitHubApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public IGitHubApi build() {
			return (IGitHubApi) new GitHubClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<IGitHubApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<IGitHubApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class GitHubClient implements IGitHubApi {
		
		private IEntityApi entityClient;
		private ISearchApi searchClient;
		
		GitHubClient(ISession session, boolean activeCaching) {
			if (session == null) {
				session = GitHubSession.createPublic(); 
			}	
			entityClient = EntityApi.create()
				.setSession(GitHubSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
			searchClient = SearchApi.create()
				.setSession(GitHubSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
		}

		/** WRAPED METHODS */
		 
		@Override
		public IData<Organization> getOrgsOrganizationByOrg(String org){ 
			Validate.notNull(org);
			return entityClient.getOrgsOrganizationByOrg(org);
		}
		 
		@Override
		public IData<Emojis> getEmojis(){ 
			return entityClient.getEmojis();
		}
		 
		@Override
		public IDataSet<Repos> getOrgsRepos(String org, String type){ 
			Validate.notNull(org);
			if (type != null) {
				Validate.matchesPattern(type,"(all|public|private|forks|sources|member)");
			}
			return entityClient.getOrgsRepos(org, type);
		}
		 
		@Override
		public IDataSet<Users> getReposWatchersUsers(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposWatchersUsers(owner, repo);
		}
		 
		@Override
		public IDataSet<Gists> getUsersGists(String username, String since){ 
			Validate.notNull(username);
			
			return entityClient.getUsersGists(username, since);
		}
		 
		@Override
		public IDataSet<Repositories> getRepositories(String since){ 
			
			return entityClient.getRepositories(since);
		}
		 
		@Override
		public IDataSet<Hook> getReposHooksHook(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposHooksHook(owner, repo);
		}
		 
		@Override
		public IData<UserKeysKeyId> getReposKeysUserKeysKeyIdByKeyId(String owner, String repo, Integer keyId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(keyId);
			return entityClient.getReposKeysUserKeysKeyIdByKeyId(owner, repo, keyId);
		}
		 
		@Override
		public IData<Downloads> getReposDownloads(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposDownloads(owner, repo);
		}
		 
		@Override
		public IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, String direction, String sort, String since){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(created|updated)");
			}
			
			return entityClient.getReposIssuesComments(owner, repo, direction, sort, since);
		}
		 
		@Override
		public IDataSet<RepoDeployments> getReposDeploymentsRepoDeployments(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposDeploymentsRepoDeployments(owner, repo);
		}
		 
		@Override
		public IData<Release> getReposReleasesReleaseById(String owner, String repo, String id){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(id);
			return entityClient.getReposReleasesReleaseById(owner, repo, id);
		}
		 
		@Override
		public IDataSet<Assets> getReposReleasesAssets(String owner, String repo, String id){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(id);
			return entityClient.getReposReleasesAssets(owner, repo, id);
		}
		 
		@Override
		public IDataSet<Refs> getReposGitRefs(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposGitRefs(owner, repo);
		}
		 
		@Override
		public IData<Commit> getReposCommitsCommitByShaCode(String owner, String repo, String shaCode){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			return entityClient.getReposCommitsCommitByShaCode(owner, repo, shaCode);
		}
		 
		@Override
		public IData<Milestone> getReposMilestonesMilestone(String owner, String repo, String state, String direction, String sort){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			if (state != null) {
				Validate.matchesPattern(state,"(open|closed)");
			}
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(due_date|completeness)");
			}
			return entityClient.getReposMilestonesMilestone(owner, repo, state, direction, sort);
		}
		 
		@Override
		public IData<Languages> getReposLanguages(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposLanguages(owner, repo);
		}
		 
		@Override
		public IData<Repo> getReposRepoByRepo(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposRepoByRepo(owner, repo);
		}
		 
		@Override
		public IData<TeamMembership> getTeamsMembershipsTeamMembershipByUsername(Integer teamId, String username){ 
			Validate.notNull(teamId);
			Validate.notNull(username);
			return entityClient.getTeamsMembershipsTeamMembershipByUsername(teamId, username);
		}
		 
		@Override
		public IData<Tag> getReposGitTagsTagByShaCode(String owner, String repo, String shaCode){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			return entityClient.getReposGitTagsTagByShaCode(owner, repo, shaCode);
		}
		 
		@Override
		public IData<ContentsPath> getReposReadmeContentsPath(String owner, String repo, String ref){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			
			return entityClient.getReposReadmeContentsPath(owner, repo, ref);
		}
		 
		@Override
		public IDataSet<Comments> getGistsComments(Integer id){ 
			Validate.notNull(id);
			return entityClient.getGistsComments(id);
		}
		 
		@Override
		public IDataSet<RepoComments> getReposCommentsRepoComments(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposCommentsRepoComments(owner, repo);
		}
		 
		@Override
		public IDataSet<Repos> getUserRepos(String type){ 
			if (type != null) {
				Validate.matchesPattern(type,"(all|public|private|forks|sources|member)");
			}
			return entityClient.getUserRepos(type);
		}
		 
		@Override
		public IData<SearchUserByEmail> getLegacyUserEmailSearchUserByEmailByEmail(String email){ 
			Validate.notNull(email);
			return entityClient.getLegacyUserEmailSearchUserByEmailByEmail(email);
		}
		 
		@Override
		public IData<Events> getEvents(){ 
			return entityClient.getEvents();
		}
		 
		@Override
		public IData<SearchIssuesByKeyword> getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(String keyword, String state, String owner, String repository){ 
			Validate.notNull(keyword);
			Validate.notNull(state);
			Validate.matchesPattern(state,"(open|closed)");
			Validate.notNull(owner);
			Validate.notNull(repository);
			return entityClient.getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(keyword, state, owner, repository);
		}
		 
		@Override
		public IDataSet<DeploymentStatuses> getReposDeploymentsStatusesDeploymentStatuses(String owner, String repo, Integer id){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(id);
			return entityClient.getReposDeploymentsStatusesDeploymentStatuses(owner, repo, id);
		}
		 
		@Override
		public IDataSet<Pulls> getReposPulls(String owner, String repo, String state, String head, String base){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			if (state != null) {
				Validate.matchesPattern(state,"(open|closed|all)");//Modified by DC
			}
			
			
			return entityClient.getReposPulls(owner, repo, state, head, base);
		}
		 
		@Override
		public IDataSet<Gists> getGists(String since){ 
			
			return entityClient.getGists(since);
		}
		 
		@Override
		public IDataSet<Users> getUserFollowersUsers(){ 
			return entityClient.getUserFollowersUsers();
		}
		 
		@Override
		public IData<Gist> getGistsGistById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getGistsGistById(id);
		}
		 
		@Override
		public IData<Comment> getGistsCommentsCommentByCommentId(Integer id, Integer commentId){ 
			Validate.notNull(id);
			Validate.notNull(commentId);
			return entityClient.getGistsCommentsCommentByCommentId(id, commentId);
		}
		 
		@Override
		public IDataSet<Issues> getIssues(String filter, String state, String labels, String sort, String direction, String since){ 
			Validate.notNull(filter);
			Validate.matchesPattern(filter,"(assigned|created|mentioned|subscribed|all)");
			Validate.notNull(state);
			Validate.matchesPattern(state,"(open|closed)");
			Validate.notNull(labels);
			Validate.notNull(sort);
			Validate.matchesPattern(sort,"(created|updated|comments)");
			Validate.notNull(direction);
			Validate.matchesPattern(direction,"(asc|desc)");
			
			return entityClient.getIssues(filter, state, labels, sort, direction, since);
		}
		 
		@Override
		public IData<Event> getReposIssuesEventsEventByEventId(String owner, String repo, Integer eventId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(eventId);
			return entityClient.getReposIssuesEventsEventByEventId(owner, repo, eventId);
		}
		 
		@Override
		public IData<Events> getNetworksEvents(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getNetworksEvents(owner, repo);
		}
		 
		@Override
		public IDataSet<Users> getTeamsMembersUsers(Integer teamId){ 
			Validate.notNull(teamId);
			return entityClient.getTeamsMembersUsers(teamId);
		}
		 
		@Override
		public IDataSet<Labels> getReposMilestonesLabels(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposMilestonesLabels(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Users> getOrgsMembersUsers(String org){ 
			Validate.notNull(org);
			return entityClient.getOrgsMembersUsers(org);
		}
		 
		@Override
		public IDataSet<Hook> getReposHooksHookByHookId(String owner, String repo, Integer hookId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(hookId);
			return entityClient.getReposHooksHookByHookId(owner, repo, hookId);
		}
		 
		@Override
		public IData<HeadBranch> getReposGitRefsHeadBranchByRef(String owner, String repo, String ref){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(ref);
			return entityClient.getReposGitRefsHeadBranchByRef(owner, repo, ref);
		}
		 
		@Override
		public IDataSet<Gists> getGistsStarredGists(String since){ 
			
			return entityClient.getGistsStarredGists(since);
		}
		 
		@Override
		public IDataSet<Users> getReposSubscribersUsers(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposSubscribersUsers(owner, repo);
		}
		 
		@Override
		public IDataSet<Users> getReposCollaboratorsUsers(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposCollaboratorsUsers(owner, repo);
		}
		 
		@Override
		public IData<Branch> getReposBranchesBranchByBranch(String owner, String repo, String branch){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(branch);
			return entityClient.getReposBranchesBranchByBranch(owner, repo, branch);
		}
		 
		@Override
		public IData<Label> getReposLabelsLabelByName(String owner, String repo, String name){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(name);
			return entityClient.getReposLabelsLabelByName(owner, repo, name);
		}
		 
		@Override
		public IData<PullsComment> getReposPullsCommentsPullsComment(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposPullsCommentsPullsComment(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Labels> getReposLabels(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposLabels(owner, repo);
		}
		 
		@Override
		public IData<Subscribition> getReposSubscriptionSubscribition(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposSubscriptionSubscribition(owner, repo);
		}
		 
		@Override
		public IData<Team> getTeamsTeamByTeamId(Integer teamId){ 
			Validate.notNull(teamId);
			return entityClient.getTeamsTeamByTeamId(teamId);
		}
		 
		@Override
		public IData<PullsComment> getReposPullsCommentsPullsCommentByCommentId(String owner, String repo, Integer commentId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(commentId);
			return entityClient.getReposPullsCommentsPullsCommentByCommentId(owner, repo, commentId);
		}
		 
		@Override
		public IDataSet<Commits> getReposCommits(String owner, String repo, String since, String sha, String path, String author, String until){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			
			
			
			
			
			return entityClient.getReposCommits(owner, repo, since, sha, path, author, until);
		}
		 
		@Override
		public IData<Downloads> getReposDownloadsByDownloadId(String owner, String repo, Integer downloadId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(downloadId);
			return entityClient.getReposDownloadsByDownloadId(owner, repo, downloadId);
		}
		 
		@Override
		public IData<Events> getReposIssuesEvents(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposIssuesEvents(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Teams> getReposTeams(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposTeams(owner, repo);
		}
		 
		@Override
		public IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposIssuesComments(owner, repo, number);
		}
		 
		@Override
		public IData<Events> getOrgsEvents(String org){ 
			Validate.notNull(org);
			return entityClient.getOrgsEvents(org);
		}
		 
		@Override
		public IDataSet<Users> getOrgsPublic_membersUsers(String org){ 
			Validate.notNull(org);
			return entityClient.getOrgsPublic_membersUsers(org);
		}
		 
		@Override
		public IData<Meta> getMeta(){ 
			return entityClient.getMeta();
		}
		 
		@Override
		public IData<Events> getReposIssuesEvents(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposIssuesEvents(owner, repo);
		}
		 
		@Override
		public IDataSet<CommitActivityStats> getReposStatsCommit_activityCommitActivityStats(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStatsCommit_activityCommitActivityStats(owner, repo);
		}
		 
		@Override
		public IDataSet<Branches> getReposBranches(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposBranches(owner, repo);
		}
		 
		@Override
		public IDataSet<Repos> getUsersRepos(String username, String type){ 
			Validate.notNull(username);
			if (type != null) {
				Validate.matchesPattern(type,"(all|public|private|forks|sources|member)");
			}
			return entityClient.getUsersRepos(username, type);
		}
		 
		@Override
		public IDataSet<Ref> getReposStatusesRefByRef(String owner, String repo, String ref){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(ref);
			return entityClient.getReposStatusesRefByRef(owner, repo, ref);
		}
		 
		@Override
		public IData<SearchRepositoriesByKeyword> getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(String keyword, String order, String language, String startPage, String sort){ 
			Validate.notNull(keyword);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(updated|stars|forks)");
			}
			return entityClient.getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(keyword, order, language, startPage, sort);
		}
		 
		@Override
		public IData<Milestone> getReposMilestonesMilestoneByNumber(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposMilestonesMilestoneByNumber(owner, repo, number);
		}
		 
		@Override
		public IData<Notifications> getReposNotifications(String owner, String repo, Boolean all, Boolean participating, String since){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			
			
			
			return entityClient.getReposNotifications(owner, repo, all, participating, since);
		}
		 
		@Override
		public IData<Tags> getReposTags(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposTags(owner, repo);
		}
		 
		@Override
		public IDataSet<Gists> getGistsPublicGists(String since){ 
			
			return entityClient.getGistsPublicGists(since);
		}
		 
		@Override
		public IDataSet<Issues> getUserIssues(String filter, String state, String labels, String sort, String direction, String since){ 
			Validate.notNull(filter);
			Validate.matchesPattern(filter,"(assigned|created|mentioned|subscribed|all)");
			Validate.notNull(state);
			Validate.matchesPattern(state,"(open|closed)");
			Validate.notNull(labels);
			Validate.notNull(sort);
			Validate.matchesPattern(sort,"(created|updated|comments)");
			Validate.notNull(direction);
			Validate.matchesPattern(direction,"(asc|desc)");
			
			return entityClient.getUserIssues(filter, state, labels, sort, direction, since);
		}
		 
		@Override
		public IDataSet<TeamsList> getUserTeamsTeamsList(){ 
			return entityClient.getUserTeamsTeamsList();
		}
		 
		@Override
		public IDataSet<UserUserIdSubscribitions> getUserSubscriptionsUserUserIdSubscribitions(){ 
			return entityClient.getUserSubscriptionsUserUserIdSubscribitions();
		}
		 
		@Override
		public IData<Notifications> getNotificationsThreadsNotificationsById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getNotificationsThreadsNotificationsById(id);
		}
		 
		@Override
		public IData<Notifications> getNotifications(Boolean all, Boolean participating, String since){ 
			
			
			
			return entityClient.getNotifications(all, participating, since);
		}
		 
		@Override
		public IDataSet<Releases> getReposReleases(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposReleases(owner, repo);
		}
		 
		@Override
		public IData<PullRequest> getReposPullsPullRequestByNumber(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposPullsPullRequestByNumber(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Users> getReposStargazersUsers(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStargazersUsers(owner, repo);
		}
		 
		@Override
		public IDataSet<Integer> getReposStatsCode_frequencyCodeFrequencyStats(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStatsCode_frequencyCodeFrequencyStats(owner, repo);
		}
		 
		@Override
		public IDataSet<Assignees> getReposAssignees(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposAssignees(owner, repo);
		}
		 
		@Override
		public IDataSet<Users> getUserFollowingUsers(){ 
			return entityClient.getUserFollowingUsers();
		}
		 
		@Override
		public IData<GitignoreLang> getGitignoreTemplatesGitignoreLangByLanguage(String language){ 
			Validate.notNull(language);
			return entityClient.getGitignoreTemplatesGitignoreLangByLanguage(language);
		}
		 
		@Override
		public IDataSet<Issues> getReposIssues(String owner, String repo, String filter, String state, String labels, String sort, String direction, String since){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(filter);
			Validate.matchesPattern(filter,"(assigned|created|mentioned|subscribed|all)");
			Validate.notNull(state);
			Validate.matchesPattern(state,"(open|closed|all)");
			Validate.notNull(labels);
			Validate.notNull(sort);
			Validate.matchesPattern(sort,"(created|updated|comments)");
			Validate.notNull(direction);
			Validate.matchesPattern(direction,"(asc|desc)");
			
			return entityClient.getReposIssues(owner, repo, filter, state, labels, sort, direction, since);
		}
		 
		@Override
		public IData<CommitComments> getReposCommentsCommitCommentsByCommentId(String owner, String repo, Integer commentId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(commentId);
			return entityClient.getReposCommentsCommitCommentsByCommentId(owner, repo, commentId);
		}
		 
		@Override
		public IDataSet<Pulls> getReposPullsFilesPulls(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposPullsFilesPulls(owner, repo, number);
		}
		 
		@Override
		public IData<ContentsPath> getReposContentsContentsPathByPath(String owner, String repo, String path, String path_0, String ref){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(path);
			
			
			return entityClient.getReposContentsContentsPathByPath(owner, repo, path, path_0, ref);
		}
		 
		@Override
		public IData<RateLimit> getRate_limitRateLimit(){ 
			return entityClient.getRate_limitRateLimit();
		}
		 
		@Override
		public IData<User> getUser(){ 
			return entityClient.getUser();
		}
		 
		@Override
		public IDataSet<Integer> getReposStatsPunch_cardCodeFrequencyStats(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStatsPunch_cardCodeFrequencyStats(owner, repo);
		}
		 
		@Override
		public IData<ParticipationStats> getReposStatsParticipationParticipationStats(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStatsParticipationParticipationStats(owner, repo);
		}
		 
		@Override
		public IDataSet<ContributorsStats> getReposStatsContributorsContributorsStats(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposStatsContributorsContributorsStats(owner, repo);
		}
		 
		@Override
		public IDataSet<Users> getUsersByUsername(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersByUsername(username);
		}
		 
		@Override
		public IDataSet<String> getUserEmails(){ 
			return entityClient.getUserEmails();
		}
		 
		@Override
		public IData<Events> getReposEvents(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposEvents(owner, repo);
		}
		 
		@Override
		public IData<RepoCommit> getReposGitCommitsRepoCommitByShaCode(String owner, String repo, String shaCode){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			return entityClient.getReposGitCommitsRepoCommitByShaCode(owner, repo, shaCode);
		}
		 
		@Override
		public IData<Feeds> getFeeds(){ 
			return entityClient.getFeeds();
		}
		 
		@Override
		public IData<Issue> getReposIssuesIssueByNumber(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposIssuesIssueByNumber(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Issues> getOrgsIssues(String org, String filter, String state, String labels, String sort, String direction, String since){ 
			Validate.notNull(org);
			Validate.notNull(filter);
			Validate.matchesPattern(filter,"(assigned|created|mentioned|subscribed|all)");
			Validate.notNull(state);
			Validate.matchesPattern(state,"(open|closed)");
			Validate.notNull(labels);
			Validate.notNull(sort);
			Validate.matchesPattern(sort,"(created|updated|comments)");
			Validate.notNull(direction);
			Validate.matchesPattern(direction,"(asc|desc)");
			
			return entityClient.getOrgsIssues(org, filter, state, labels, sort, direction, since);
		}
		 
		@Override
		public IDataSet<Users> getUsersFollowersUsers(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersFollowersUsers(username);
		}
		 
		@Override
		public IDataSet<Contributors> getReposContributors(String owner, String repo, String anon){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(anon);
			return entityClient.getReposContributors(owner, repo, anon);
		}
		 
		@Override
		public IDataSet<IssuesComments> getReposPullsCommentsIssuesComments(String owner, String repo, String direction, String sort, String since){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(created|updated)");
			}
			
			return entityClient.getReposPullsCommentsIssuesComments(owner, repo, direction, sort, since);
		}
		 
		@Override
		public IDataSet<Labels> getReposIssuesLabels(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposIssuesLabels(owner, repo, number);
		}
		 
		@Override
		public IDataSet<Commits> getReposPullsCommits(String owner, String repo, Integer number){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(number);
			return entityClient.getReposPullsCommits(owner, repo, number);
		}
		 
		@Override
		public IDataSet<RefStatus> getReposCommitsStatusRefStatus(String owner, String repo, String ref){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(ref);
			return entityClient.getReposCommitsStatusRefStatus(owner, repo, ref);
		}
		 
		@Override
		public IDataSet<RepoComments> getReposCommitsCommentsRepoComments(String owner, String repo, String shaCode){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			return entityClient.getReposCommitsCommentsRepoComments(owner, repo, shaCode);
		}
		 
		@Override
		public IDataSet<Users> getUsers(Integer since){ 
			
			return entityClient.getUsers(since);
		}
		 
		@Override
		public IData<Blob> getReposGitBlobsBlobByShaCode(String owner, String repo, String shaCode){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			return entityClient.getReposGitBlobsBlobByShaCode(owner, repo, shaCode);
		}
		 
		@Override
		public IDataSet<TeamRepos> getTeamsReposTeamRepos(Integer teamId){ 
			Validate.notNull(teamId);
			return entityClient.getTeamsReposTeamRepos(teamId);
		}
		 
		@Override
		public IData<UserKeysKeyId> getUserKeysUserKeysKeyIdByKeyId(Integer keyId){ 
			Validate.notNull(keyId);
			return entityClient.getUserKeysUserKeysKeyIdByKeyId(keyId);
		}
		 
		@Override
		public IDataSet<Forks> getReposForks(String owner, String repo, String sort){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			if (sort != null) {
				Validate.matchesPattern(sort,"(newes|oldes|watchers)");
			}
			return entityClient.getReposForks(owner, repo, sort);
		}
		 
		@Override
		public IData<SearchUsersByKeyword> getLegacyUserSearchSearchUsersByKeywordByKeyword(String keyword, String order, String startPage, String sort){ 
			Validate.notNull(keyword);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(updated|stars|forks)");
			}
			return entityClient.getLegacyUserSearchSearchUsersByKeywordByKeyword(keyword, order, startPage, sort);
		}
		 
		@Override
		public IData<IssuesComment> getReposIssuesCommentsIssuesCommentByCommentId(String owner, String repo, Integer commentId){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(commentId);
			return entityClient.getReposIssuesCommentsIssuesCommentByCommentId(owner, repo, commentId);
		}
		 
		@Override
		public IData<Asset> getReposReleasesAssetsAssetById(String owner, String repo, String id){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(id);
			return entityClient.getReposReleasesAssetsAssetById(owner, repo, id);
		}
		 
		@Override
		public IDataSet<Teams> getOrgsTeams(String org){ 
			Validate.notNull(org);
			return entityClient.getOrgsTeams(org);
		}
		 
		@Override
		public IData<Tree> getReposGitTreesTreeByShaCode(String owner, String repo, String shaCode, Integer recursive){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			Validate.notNull(shaCode);
			
			return entityClient.getReposGitTreesTreeByShaCode(owner, repo, shaCode, recursive);
		}
		 
		@Override
		public IDataSet<Keys> getReposKeys(String owner, String repo){ 
			Validate.notNull(owner);
			Validate.notNull(repo);
			return entityClient.getReposKeys(owner, repo);
		}
		 
		@Override
		public IData<Subscription> getNotificationsThreadsSubscription(Integer id){ 
			Validate.notNull(id);
			return entityClient.getNotificationsThreadsSubscription(id);
		}
		 
		@Override
		public IDataSet<SearchUsers> getSearchUsers(String order, String q, String sort){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			Validate.notNull(q);
			if (sort != null) {
				Validate.matchesPattern(sort,"(followers|repositories|joined)");
			}
			return searchClient.getSearchUsers(order, q, sort);
		}
		 
		@Override
		public IDataSet<SearchCode> getSearchCode(String order, String q, String sort){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			Validate.notNull(q);
			if (sort != null) {
				Validate.matchesPattern(sort,"(indexed)");
			}
			return searchClient.getSearchCode(order, q, sort);
		}
		 
		@Override
		public IDataSet<SearchIssues> getSearchIssues(String order, String q, String sort){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			Validate.notNull(q);
			if (sort != null) {
				Validate.matchesPattern(sort,"(updated|created|comments)");
			}
			return searchClient.getSearchIssues(order, q, sort);
		}
		 
		@Override
		public IDataSet<SearchRepositories> getSearchRepositories(String order, String q, String sort){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			Validate.notNull(q);
			if (sort != null) {
				Validate.matchesPattern(sort,"(stars|forks|updated)");
			}
			return searchClient.getSearchRepositories(order, q, sort);
		}
	}
}
