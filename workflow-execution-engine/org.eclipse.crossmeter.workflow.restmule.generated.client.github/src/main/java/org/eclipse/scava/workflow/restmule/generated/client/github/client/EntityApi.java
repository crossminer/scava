package org.eclipse.scava.workflow.restmule.generated.client.github.client;

import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.API_BASE_URL;

import java.util.concurrent.ExecutorService;

import org.eclipse.scava.workflow.restmule.core.client.AbstractClient;
import org.eclipse.scava.workflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.workflow.restmule.core.data.Data;
import org.eclipse.scava.workflow.restmule.core.data.IData;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.core.session.ISession;
import org.eclipse.scava.workflow.restmule.core.session.RateLimitExecutor;
import org.eclipse.scava.workflow.restmule.generated.client.github.cache.GitHubCacheManager;
import org.eclipse.scava.workflow.restmule.generated.client.github.interceptor.GitHubInterceptor;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.*;
import org.eclipse.scava.workflow.restmule.generated.client.github.page.GitHubPagination;
import org.eclipse.scava.workflow.restmule.generated.client.github.session.GitHubSession;
import org.eclipse.scava.workflow.restmule.generated.client.github.util.GitHubPropertiesUtil;

import okhttp3.OkHttpClient.Builder;

public class EntityApi  {

	public static EntityBuilder create(){
		return new EntityBuilder(); 
	}
	
	public static IEntityApi createDefault(){ 
		return new EntityBuilder().setSession(GitHubSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class EntityBuilder 
	implements IClientBuilder<IEntityApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public IEntityApi build() {
			return (IEntityApi) new EntityClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<IEntityApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<IEntityApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class EntityClient extends AbstractClient<IEntityEndpoint> 
	implements IEntityApi 
	{
		private GitHubPagination paginationPolicy;
		
		EntityClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, GitHubSession.class, session.id());
			GitHubInterceptor interceptors = new GitHubInterceptor(session.id());
			String baseurl = GitHubPropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/")) baseurl += "/"; // FIXME Validate in Model with EVL 

			Builder clientBuilder = AbstractClient.okHttp(executor);
			
			if (activeCaching) clientBuilder = clientBuilder.cache(GitHubCacheManager.getInstance().getOkHttpCache()); // FIXME Use Lucene Instead
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));
						
			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(IEntityEndpoint.class);
			this.paginationPolicy = GitHubPagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		@Override
		public IData<Organization> getOrgsOrganizationByOrg(String org){
			Data<Organization> data = new Data<Organization>();
			data.addElement(callbackEndpoint.getOrgsOrganizationByOrg(org));
			return data;
		}
		
		@Override
		public IData<Emojis> getEmojis(){
			Data<Emojis> data = new Data<Emojis>();
			data.addElement(callbackEndpoint.getEmojis());
			return data;
		}
		
		@Override
		public IDataSet<Repos> getOrgsRepos(String org, String type){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { org, type};
			return paginationPolicy.<Repos, IEntityEndpoint> 
				traverseList("getOrgsRepos", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getReposWatchersUsers(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getReposWatchersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Gists> getUsersGists(String username, String since){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { username, since};
			return paginationPolicy.<Gists, IEntityEndpoint> 
				traverseList("getUsersGists", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Repositories> getRepositories(String since){
			Class<?>[] types = { String.class};
			Object[] vals = { since};
			return paginationPolicy.<Repositories, IEntityEndpoint> 
				traverseList("getRepositories", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Hook> getReposHooksHook(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Hook, IEntityEndpoint> 
				traverseList("getReposHooksHook", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<UserKeysKeyId> getReposKeysUserKeysKeyIdByKeyId(String owner, String repo, Integer keyId){
			Data<UserKeysKeyId> data = new Data<UserKeysKeyId>();
			data.addElement(callbackEndpoint.getReposKeysUserKeysKeyIdByKeyId(owner, repo, keyId));
			return data;
		}
		
		@Override
		public IData<Downloads> getReposDownloads(String owner, String repo){
			Data<Downloads> data = new Data<Downloads>();
			data.addElement(callbackEndpoint.getReposDownloads(owner, repo));
			return data;
		}
		
		@Override
		public IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, String direction, String sort, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { owner, repo, direction, sort, since};
			return paginationPolicy.<IssuesComments, IEntityEndpoint> 
				traverseList("getReposIssuesComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<RepoDeployments> getReposDeploymentsRepoDeployments(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<RepoDeployments, IEntityEndpoint> 
				traverseList("getReposDeploymentsRepoDeployments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Release> getReposReleasesReleaseById(String owner, String repo, String id){
			Data<Release> data = new Data<Release>();
			data.addElement(callbackEndpoint.getReposReleasesReleaseById(owner, repo, id));
			return data;
		}
		
		@Override
		public IDataSet<Assets> getReposReleasesAssets(String owner, String repo, String id){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, id};
			return paginationPolicy.<Assets, IEntityEndpoint> 
				traverseList("getReposReleasesAssets", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Refs> getReposGitRefs(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Refs, IEntityEndpoint> 
				traverseList("getReposGitRefs", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Commit> getReposCommitsCommitByShaCode(String owner, String repo, String shaCode){
			Data<Commit> data = new Data<Commit>();
			data.addElement(callbackEndpoint.getReposCommitsCommitByShaCode(owner, repo, shaCode));
			return data;
		}
		
		@Override
		public IData<Milestone> getReposMilestonesMilestone(String owner, String repo, String state, String direction, String sort){
			Data<Milestone> data = new Data<Milestone>();
			data.addElement(callbackEndpoint.getReposMilestonesMilestone(owner, repo, state, direction, sort));
			return data;
		}
		
		@Override
		public IData<Languages> getReposLanguages(String owner, String repo){
			Data<Languages> data = new Data<Languages>();
			data.addElement(callbackEndpoint.getReposLanguages(owner, repo));
			return data;
		}
		
		@Override
		public IData<Repo> getReposRepoByRepo(String owner, String repo){
			Data<Repo> data = new Data<Repo>();
			data.addElement(callbackEndpoint.getReposRepoByRepo(owner, repo));
			return data;
		}
		
		@Override
		public IData<TeamMembership> getTeamsMembershipsTeamMembershipByUsername(Integer teamId, String username){
			Data<TeamMembership> data = new Data<TeamMembership>();
			data.addElement(callbackEndpoint.getTeamsMembershipsTeamMembershipByUsername(teamId, username));
			return data;
		}
		
		@Override
		public IData<Tag> getReposGitTagsTagByShaCode(String owner, String repo, String shaCode){
			Data<Tag> data = new Data<Tag>();
			data.addElement(callbackEndpoint.getReposGitTagsTagByShaCode(owner, repo, shaCode));
			return data;
		}
		
		@Override
		public IData<ContentsPath> getReposReadmeContentsPath(String owner, String repo, String ref){
			Data<ContentsPath> data = new Data<ContentsPath>();
			data.addElement(callbackEndpoint.getReposReadmeContentsPath(owner, repo, ref));
			return data;
		}
		
		@Override
		public IDataSet<Comments> getGistsComments(Integer id){
			Class<?>[] types = { Integer.class};
			Object[] vals = { id};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getGistsComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<RepoComments> getReposCommentsRepoComments(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<RepoComments, IEntityEndpoint> 
				traverseList("getReposCommentsRepoComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Repos> getUserRepos(String type){
			Class<?>[] types = { String.class};
			Object[] vals = { type};
			return paginationPolicy.<Repos, IEntityEndpoint> 
				traverseList("getUserRepos", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<SearchUserByEmail> getLegacyUserEmailSearchUserByEmailByEmail(String email){
			Data<SearchUserByEmail> data = new Data<SearchUserByEmail>();
			data.addElement(callbackEndpoint.getLegacyUserEmailSearchUserByEmailByEmail(email));
			return data;
		}
		
		@Override
		public IData<Events> getEvents(){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getEvents());
			return data;
		}
		
		@Override
		public IData<SearchIssuesByKeyword> getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(String keyword, String state, String owner, String repository){
			Data<SearchIssuesByKeyword> data = new Data<SearchIssuesByKeyword>();
			data.addElement(callbackEndpoint.getLegacyIssuesSearchSearchIssuesByKeywordByKeyword(keyword, state, owner, repository));
			return data;
		}
		
		@Override
		public IDataSet<DeploymentStatuses> getReposDeploymentsStatusesDeploymentStatuses(String owner, String repo, Integer id){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, id};
			return paginationPolicy.<DeploymentStatuses, IEntityEndpoint> 
				traverseList("getReposDeploymentsStatusesDeploymentStatuses", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Pulls> getReposPulls(String owner, String repo, String state, String head, String base){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { owner, repo, state, head, base};
			return paginationPolicy.<Pulls, IEntityEndpoint> 
				traverseList("getReposPulls", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Gists> getGists(String since){
			Class<?>[] types = { String.class};
			Object[] vals = { since};
			return paginationPolicy.<Gists, IEntityEndpoint> 
				traverseList("getGists", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUserFollowersUsers(){
			Class<?>[] types = null;
			Object[] vals = null;
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUserFollowersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Gist> getGistsGistById(Integer id){
			Data<Gist> data = new Data<Gist>();
			data.addElement(callbackEndpoint.getGistsGistById(id));
			return data;
		}
		
		@Override
		public IData<Comment> getGistsCommentsCommentByCommentId(Integer id, Integer commentId){
			Data<Comment> data = new Data<Comment>();
			data.addElement(callbackEndpoint.getGistsCommentsCommentByCommentId(id, commentId));
			return data;
		}
		
		@Override
		public IDataSet<Issues> getIssues(String filter, String state, String labels, String sort, String direction, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { filter, state, labels, sort, direction, since};
			return paginationPolicy.<Issues, IEntityEndpoint> 
				traverseList("getIssues", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Event> getReposIssuesEventsEventByEventId(String owner, String repo, Integer eventId){
			Data<Event> data = new Data<Event>();
			data.addElement(callbackEndpoint.getReposIssuesEventsEventByEventId(owner, repo, eventId));
			return data;
		}
		
		@Override
		public IData<Events> getNetworksEvents(String owner, String repo){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getNetworksEvents(owner, repo));
			return data;
		}
		
		@Override
		public IDataSet<Users> getTeamsMembersUsers(Integer teamId){
			Class<?>[] types = { Integer.class};
			Object[] vals = { teamId};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getTeamsMembersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Labels> getReposMilestonesLabels(String owner, String repo, Integer number){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, number};
			return paginationPolicy.<Labels, IEntityEndpoint> 
				traverseList("getReposMilestonesLabels", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getOrgsMembersUsers(String org){
			Class<?>[] types = { String.class};
			Object[] vals = { org};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getOrgsMembersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Hook> getReposHooksHookByHookId(String owner, String repo, Integer hookId){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, hookId};
			return paginationPolicy.<Hook, IEntityEndpoint> 
				traverseList("getReposHooksHookByHookId", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<HeadBranch> getReposGitRefsHeadBranchByRef(String owner, String repo, String ref){
			Data<HeadBranch> data = new Data<HeadBranch>();
			data.addElement(callbackEndpoint.getReposGitRefsHeadBranchByRef(owner, repo, ref));
			return data;
		}
		
		@Override
		public IDataSet<Gists> getGistsStarredGists(String since){
			Class<?>[] types = { String.class};
			Object[] vals = { since};
			return paginationPolicy.<Gists, IEntityEndpoint> 
				traverseList("getGistsStarredGists", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getReposSubscribersUsers(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getReposSubscribersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getReposCollaboratorsUsers(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getReposCollaboratorsUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Branch> getReposBranchesBranchByBranch(String owner, String repo, String branch){
			Data<Branch> data = new Data<Branch>();
			data.addElement(callbackEndpoint.getReposBranchesBranchByBranch(owner, repo, branch));
			return data;
		}
		
		@Override
		public IData<Label> getReposLabelsLabelByName(String owner, String repo, String name){
			Data<Label> data = new Data<Label>();
			data.addElement(callbackEndpoint.getReposLabelsLabelByName(owner, repo, name));
			return data;
		}
		
		@Override
		public IData<PullsComment> getReposPullsCommentsPullsComment(String owner, String repo, Integer number){
			Data<PullsComment> data = new Data<PullsComment>();
			data.addElement(callbackEndpoint.getReposPullsCommentsPullsComment(owner, repo, number));
			return data;
		}
		
		@Override
		public IDataSet<Labels> getReposLabels(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Labels, IEntityEndpoint> 
				traverseList("getReposLabels", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Subscribition> getReposSubscriptionSubscribition(String owner, String repo){
			Data<Subscribition> data = new Data<Subscribition>();
			data.addElement(callbackEndpoint.getReposSubscriptionSubscribition(owner, repo));
			return data;
		}
		
		@Override
		public IData<Team> getTeamsTeamByTeamId(Integer teamId){
			Data<Team> data = new Data<Team>();
			data.addElement(callbackEndpoint.getTeamsTeamByTeamId(teamId));
			return data;
		}
		
		@Override
		public IData<PullsComment> getReposPullsCommentsPullsCommentByCommentId(String owner, String repo, Integer commentId){
			Data<PullsComment> data = new Data<PullsComment>();
			data.addElement(callbackEndpoint.getReposPullsCommentsPullsCommentByCommentId(owner, repo, commentId));
			return data;
		}
		
		@Override
		public IDataSet<Commits> getReposCommits(String owner, String repo, String since, String sha, String path, String author, String until){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { owner, repo, since, sha, path, author, until};
			return paginationPolicy.<Commits, IEntityEndpoint> 
				traverseList("getReposCommits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Downloads> getReposDownloadsByDownloadId(String owner, String repo, Integer downloadId){
			Data<Downloads> data = new Data<Downloads>();
			data.addElement(callbackEndpoint.getReposDownloadsByDownloadId(owner, repo, downloadId));
			return data;
		}
		
		@Override
		public IData<Events> getReposIssuesEvents(String owner, String repo, Integer number){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getReposIssuesEvents(owner, repo, number));
			return data;
		}
		
		@Override
		public IDataSet<Teams> getReposTeams(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Teams, IEntityEndpoint> 
				traverseList("getReposTeams", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<IssuesComments> getReposIssuesComments(String owner, String repo, Integer number){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, number};
			return paginationPolicy.<IssuesComments, IEntityEndpoint> 
				traverseList("getReposIssuesComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Events> getOrgsEvents(String org){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getOrgsEvents(org));
			return data;
		}
		
		@Override
		public IDataSet<Users> getOrgsPublic_membersUsers(String org){
			Class<?>[] types = { String.class};
			Object[] vals = { org};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getOrgsPublic_membersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Meta> getMeta(){
			Data<Meta> data = new Data<Meta>();
			data.addElement(callbackEndpoint.getMeta());
			return data;
		}
		
		@Override
		public IData<Events> getReposIssuesEvents(String owner, String repo){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getReposIssuesEvents(owner, repo));
			return data;
		}
		
		@Override
		public IDataSet<CommitActivityStats> getReposStatsCommit_activityCommitActivityStats(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<CommitActivityStats, IEntityEndpoint> 
				traverseList("getReposStatsCommit_activityCommitActivityStats", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Branches> getReposBranches(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Branches, IEntityEndpoint> 
				traverseList("getReposBranches", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Repos> getUsersRepos(String username, String type){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { username, type};
			return paginationPolicy.<Repos, IEntityEndpoint> 
				traverseList("getUsersRepos", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Ref> getReposStatusesRefByRef(String owner, String repo, String ref){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, ref};
			return paginationPolicy.<Ref, IEntityEndpoint> 
				traverseList("getReposStatusesRefByRef", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<SearchRepositoriesByKeyword> getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(String keyword, String order, String language, String startPage, String sort){
			Data<SearchRepositoriesByKeyword> data = new Data<SearchRepositoriesByKeyword>();
			data.addElement(callbackEndpoint.getLegacyReposSearchSearchRepositoriesByKeywordByKeyword(keyword, order, language, startPage, sort));
			return data;
		}
		
		@Override
		public IData<Milestone> getReposMilestonesMilestoneByNumber(String owner, String repo, Integer number){
			Data<Milestone> data = new Data<Milestone>();
			data.addElement(callbackEndpoint.getReposMilestonesMilestoneByNumber(owner, repo, number));
			return data;
		}
		
		@Override
		public IData<Notifications> getReposNotifications(String owner, String repo, Boolean all, Boolean participating, String since){
			Data<Notifications> data = new Data<Notifications>();
			data.addElement(callbackEndpoint.getReposNotifications(owner, repo, all, participating, since));
			return data;
		}
		
		@Override
		public IData<Tags> getReposTags(String owner, String repo){
			Data<Tags> data = new Data<Tags>();
			data.addElement(callbackEndpoint.getReposTags(owner, repo));
			return data;
		}
		
		@Override
		public IDataSet<Gists> getGistsPublicGists(String since){
			Class<?>[] types = { String.class};
			Object[] vals = { since};
			return paginationPolicy.<Gists, IEntityEndpoint> 
				traverseList("getGistsPublicGists", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Issues> getUserIssues(String filter, String state, String labels, String sort, String direction, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { filter, state, labels, sort, direction, since};
			return paginationPolicy.<Issues, IEntityEndpoint> 
				traverseList("getUserIssues", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TeamsList> getUserTeamsTeamsList(){
			Class<?>[] types = null;
			Object[] vals = null;
			return paginationPolicy.<TeamsList, IEntityEndpoint> 
				traverseList("getUserTeamsTeamsList", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UserUserIdSubscribitions> getUserSubscriptionsUserUserIdSubscribitions(){
			Class<?>[] types = null;
			Object[] vals = null;
			return paginationPolicy.<UserUserIdSubscribitions, IEntityEndpoint> 
				traverseList("getUserSubscriptionsUserUserIdSubscribitions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Notifications> getNotificationsThreadsNotificationsById(Integer id){
			Data<Notifications> data = new Data<Notifications>();
			data.addElement(callbackEndpoint.getNotificationsThreadsNotificationsById(id));
			return data;
		}
		
		@Override
		public IData<Notifications> getNotifications(Boolean all, Boolean participating, String since){
			Data<Notifications> data = new Data<Notifications>();
			data.addElement(callbackEndpoint.getNotifications(all, participating, since));
			return data;
		}
		
		@Override
		public IDataSet<Releases> getReposReleases(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Releases, IEntityEndpoint> 
				traverseList("getReposReleases", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<PullRequest> getReposPullsPullRequestByNumber(String owner, String repo, Integer number){
			Data<PullRequest> data = new Data<PullRequest>();
			data.addElement(callbackEndpoint.getReposPullsPullRequestByNumber(owner, repo, number));
			return data;
		}
		
		@Override
		public IDataSet<Users> getReposStargazersUsers(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getReposStargazersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Integer> getReposStatsCode_frequencyCodeFrequencyStats(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Integer, IEntityEndpoint> 
				traverseList("getReposStatsCode_frequencyCodeFrequencyStats", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Assignees> getReposAssignees(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Assignees, IEntityEndpoint> 
				traverseList("getReposAssignees", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUserFollowingUsers(){
			Class<?>[] types = null;
			Object[] vals = null;
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUserFollowingUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<GitignoreLang> getGitignoreTemplatesGitignoreLangByLanguage(String language){
			Data<GitignoreLang> data = new Data<GitignoreLang>();
			data.addElement(callbackEndpoint.getGitignoreTemplatesGitignoreLangByLanguage(language));
			return data;
		}
		
		@Override
		public IDataSet<Issues> getReposIssues(String owner, String repo, String filter, String state, String labels, String sort, String direction, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { owner, repo, filter, state, labels, sort, direction, since};
			return paginationPolicy.<Issues, IEntityEndpoint> 
				traverseList("getReposIssues", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<CommitComments> getReposCommentsCommitCommentsByCommentId(String owner, String repo, Integer commentId){
			Data<CommitComments> data = new Data<CommitComments>();
			data.addElement(callbackEndpoint.getReposCommentsCommitCommentsByCommentId(owner, repo, commentId));
			return data;
		}
		
		@Override
		public IDataSet<Pulls> getReposPullsFilesPulls(String owner, String repo, Integer number){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, number};
			return paginationPolicy.<Pulls, IEntityEndpoint> 
				traverseList("getReposPullsFilesPulls", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<ContentsPath> getReposContentsContentsPathByPath(String owner, String repo, String path, String path_0, String ref){
			Data<ContentsPath> data = new Data<ContentsPath>();
			data.addElement(callbackEndpoint.getReposContentsContentsPathByPath(owner, repo, path, path_0, ref));
			return data;
		}
		
		@Override
		public IData<RateLimit> getRate_limitRateLimit(){
			Data<RateLimit> data = new Data<RateLimit>();
			data.addElement(callbackEndpoint.getRate_limitRateLimit());
			return data;
		}
		
		@Override
		public IData<User> getUser(){
			Data<User> data = new Data<User>();
			data.addElement(callbackEndpoint.getUser());
			return data;
		}
		
		@Override
		public IDataSet<Integer> getReposStatsPunch_cardCodeFrequencyStats(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Integer, IEntityEndpoint> 
				traverseList("getReposStatsPunch_cardCodeFrequencyStats", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<ParticipationStats> getReposStatsParticipationParticipationStats(String owner, String repo){
			Data<ParticipationStats> data = new Data<ParticipationStats>();
			data.addElement(callbackEndpoint.getReposStatsParticipationParticipationStats(owner, repo));
			return data;
		}
		
		@Override
		public IDataSet<ContributorsStats> getReposStatsContributorsContributorsStats(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<ContributorsStats, IEntityEndpoint> 
				traverseList("getReposStatsContributorsContributorsStats", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsersByUsername(String username){
			Class<?>[] types = { String.class};
			Object[] vals = { username};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsersByUsername", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<String> getUserEmails(){
			Class<?>[] types = null;
			Object[] vals = null;
			return paginationPolicy.<String, IEntityEndpoint> 
				traverseList("getUserEmails", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Events> getReposEvents(String owner, String repo){
			Data<Events> data = new Data<Events>();
			data.addElement(callbackEndpoint.getReposEvents(owner, repo));
			return data;
		}
		
		@Override
		public IData<RepoCommit> getReposGitCommitsRepoCommitByShaCode(String owner, String repo, String shaCode){
			Data<RepoCommit> data = new Data<RepoCommit>();
			data.addElement(callbackEndpoint.getReposGitCommitsRepoCommitByShaCode(owner, repo, shaCode));
			return data;
		}
		
		@Override
		public IData<Feeds> getFeeds(){
			Data<Feeds> data = new Data<Feeds>();
			data.addElement(callbackEndpoint.getFeeds());
			return data;
		}
		
		@Override
		public IData<Issue> getReposIssuesIssueByNumber(String owner, String repo, Integer number){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getReposIssuesIssueByNumber(owner, repo, number));
			return data;
		}
		
		@Override
		public IDataSet<Issues> getOrgsIssues(String org, String filter, String state, String labels, String sort, String direction, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { org, filter, state, labels, sort, direction, since};
			return paginationPolicy.<Issues, IEntityEndpoint> 
				traverseList("getOrgsIssues", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsersFollowersUsers(String username){
			Class<?>[] types = { String.class};
			Object[] vals = { username};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsersFollowersUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Contributors> getReposContributors(String owner, String repo, String anon){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, anon};
			return paginationPolicy.<Contributors, IEntityEndpoint> 
				traverseList("getReposContributors", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<IssuesComments> getReposPullsCommentsIssuesComments(String owner, String repo, String direction, String sort, String since){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { owner, repo, direction, sort, since};
			return paginationPolicy.<IssuesComments, IEntityEndpoint> 
				traverseList("getReposPullsCommentsIssuesComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Labels> getReposIssuesLabels(String owner, String repo, Integer number){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, number};
			return paginationPolicy.<Labels, IEntityEndpoint> 
				traverseList("getReposIssuesLabels", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Commits> getReposPullsCommits(String owner, String repo, Integer number){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { owner, repo, number};
			return paginationPolicy.<Commits, IEntityEndpoint> 
				traverseList("getReposPullsCommits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<RefStatus> getReposCommitsStatusRefStatus(String owner, String repo, String ref){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, ref};
			return paginationPolicy.<RefStatus, IEntityEndpoint> 
				traverseList("getReposCommitsStatusRefStatus", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<RepoComments> getReposCommitsCommentsRepoComments(String owner, String repo, String shaCode){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, shaCode};
			return paginationPolicy.<RepoComments, IEntityEndpoint> 
				traverseList("getReposCommitsCommentsRepoComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsers(Integer since){
			Class<?>[] types = { Integer.class};
			Object[] vals = { since};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Blob> getReposGitBlobsBlobByShaCode(String owner, String repo, String shaCode){
			Data<Blob> data = new Data<Blob>();
			data.addElement(callbackEndpoint.getReposGitBlobsBlobByShaCode(owner, repo, shaCode));
			return data;
		}
		
		@Override
		public IDataSet<TeamRepos> getTeamsReposTeamRepos(Integer teamId){
			Class<?>[] types = { Integer.class};
			Object[] vals = { teamId};
			return paginationPolicy.<TeamRepos, IEntityEndpoint> 
				traverseList("getTeamsReposTeamRepos", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<UserKeysKeyId> getUserKeysUserKeysKeyIdByKeyId(Integer keyId){
			Data<UserKeysKeyId> data = new Data<UserKeysKeyId>();
			data.addElement(callbackEndpoint.getUserKeysUserKeysKeyIdByKeyId(keyId));
			return data;
		}
		
		@Override
		public IDataSet<Forks> getReposForks(String owner, String repo, String sort){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { owner, repo, sort};
			return paginationPolicy.<Forks, IEntityEndpoint> 
				traverseList("getReposForks", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<SearchUsersByKeyword> getLegacyUserSearchSearchUsersByKeywordByKeyword(String keyword, String order, String startPage, String sort){
			Data<SearchUsersByKeyword> data = new Data<SearchUsersByKeyword>();
			data.addElement(callbackEndpoint.getLegacyUserSearchSearchUsersByKeywordByKeyword(keyword, order, startPage, sort));
			return data;
		}
		
		@Override
		public IData<IssuesComment> getReposIssuesCommentsIssuesCommentByCommentId(String owner, String repo, Integer commentId){
			Data<IssuesComment> data = new Data<IssuesComment>();
			data.addElement(callbackEndpoint.getReposIssuesCommentsIssuesCommentByCommentId(owner, repo, commentId));
			return data;
		}
		
		@Override
		public IData<Asset> getReposReleasesAssetsAssetById(String owner, String repo, String id){
			Data<Asset> data = new Data<Asset>();
			data.addElement(callbackEndpoint.getReposReleasesAssetsAssetById(owner, repo, id));
			return data;
		}
		
		@Override
		public IDataSet<Teams> getOrgsTeams(String org){
			Class<?>[] types = { String.class};
			Object[] vals = { org};
			return paginationPolicy.<Teams, IEntityEndpoint> 
				traverseList("getOrgsTeams", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Tree> getReposGitTreesTreeByShaCode(String owner, String repo, String shaCode, Integer recursive){
			Data<Tree> data = new Data<Tree>();
			data.addElement(callbackEndpoint.getReposGitTreesTreeByShaCode(owner, repo, shaCode, recursive));
			return data;
		}
		
		@Override
		public IDataSet<Keys> getReposKeys(String owner, String repo){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { owner, repo};
			return paginationPolicy.<Keys, IEntityEndpoint> 
				traverseList("getReposKeys", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Subscription> getNotificationsThreadsSubscription(Integer id){
			Data<Subscription> data = new Data<Subscription>();
			data.addElement(callbackEndpoint.getNotificationsThreadsSubscription(id));
			return data;
		}
		
		
	}
}
