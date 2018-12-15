package org.eclipse.scava.crossflow.restmule.client.bitbucket.client;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.API_BASE_URL;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.client.AbstractClient;
import org.eclipse.scava.crossflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.interceptor.CacheControlInterceptor;
import org.eclipse.scava.crossflow.restmule.core.data.Data;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.core.session.RateLimitExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.cache.BitbucketCacheManager;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.interceptor.BitbucketInterceptor;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.*;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.Error;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.page.BitbucketPaged;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.page.BitbucketPagination;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.session.BitbucketSession;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.util.BitbucketPropertiesUtil;

import okhttp3.OkHttpClient.Builder;

public class EntityApi  {

	private static final Logger LOG = LogManager.getLogger(SearchApi.class);

	public static EntityBuilder create(){
		return new EntityBuilder(); 
	}
	
	public static IEntityApi createDefault(){ 
		return new EntityBuilder().setSession(BitbucketSession.createPublic()).build(); 
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
		private BitbucketPagination paginationPolicy;
		
		EntityClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, BitbucketSession.class, session.id());
			BitbucketInterceptor interceptors = new BitbucketInterceptor(session);
			String baseurl = BitbucketPropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/")) baseurl += "/"; // FIXME Validate in Model with EVL 

			Builder clientBuilder = AbstractClient.okHttp(executor);
			
			ICache localcache = new BitbucketCacheManager().getCacheInstance();
			if (activeCaching && localcache != null && !localcache.isDistributed()) {
				clientBuilder = clientBuilder.cache(localcache.initializeLocal());
				LOG.info("enabling local okhttp cache");
			}
						
			clientBuilder = clientBuilder.addNetworkInterceptor(CacheControlInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR);
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));
						
			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(IEntityEndpoint.class);
			this.paginationPolicy = BitbucketPagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		@Override
		public IData<Error> getRepositoriesWatchersError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesWatchersError());
			return data;
		}
		
		@Override
		public IData<PaginatedMilestones> getRepositoriesMilestonesPaginatedMilestones(){
			Data<PaginatedMilestones> data = new Data<PaginatedMilestones>();
			data.addElement(callbackEndpoint.getRepositoriesMilestonesPaginatedMilestones());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesMilestonesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesMilestonesError());
			return data;
		}
		
		@Override
		public IData<PaginatedRepositories> getRepositoriesPaginatedRepositoriesByUsername(String username, String role){
			Data<PaginatedRepositories> data = new Data<PaginatedRepositories>();
			data.addElement(callbackEndpoint.getRepositoriesPaginatedRepositoriesByUsername(username, role));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesErrorByUsername(String username, String role){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesErrorByUsername(username, role));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsErrorByEncodedId(String encodedId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsErrorByEncodedId(encodedId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesDiffErrorBySpec(Integer context, String path){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesDiffErrorBySpec(context, path));
			return data;
		}
		
		@Override
		public IData<PaginatedSnippets> getSnippetsPaginatedSnippetsByUsername(String role, String username){
			Data<PaginatedSnippets> data = new Data<PaginatedSnippets>();
			data.addElement(callbackEndpoint.getSnippetsPaginatedSnippetsByUsername(role, username));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsErrorByUsername(String role, String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsErrorByUsername(role, username));
			return data;
		}
		
		@Override
		public IData<PaginatedSnippets> getSnippetsPaginatedSnippets(String role){
			Data<PaginatedSnippets> data = new Data<PaginatedSnippets>();
			data.addElement(callbackEndpoint.getSnippetsPaginatedSnippets(role));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsError(String role){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsError(role));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesSrcErrorByPath(String format){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesSrcErrorByPath(format));
			return data;
		}
		
		@Override
		public IData<PaginatedRepositories> getRepositoriesForksPaginatedRepositories(){
			Data<PaginatedRepositories> data = new Data<PaginatedRepositories>();
			data.addElement(callbackEndpoint.getRepositoriesForksPaginatedRepositories());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesRefsTagsErrorByName(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesRefsTagsErrorByName());
			return data;
		}
		
		@Override
		public IData<PaginatedRepositories> getRepositoriesPaginatedRepositories(String after){
			Data<PaginatedRepositories> data = new Data<PaginatedRepositories>();
			data.addElement(callbackEndpoint.getRepositoriesPaginatedRepositories(after));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesCommentsErrorByCommentId(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesCommentsErrorByCommentId());
			return data;
		}
		
		@Override
		public IData<PaginatedTeams> getTeamsPaginatedTeams(String role){
			Data<PaginatedTeams> data = new Data<PaginatedTeams>();
			data.addElement(callbackEndpoint.getTeamsPaginatedTeams(role));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsError(String role){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsError(role));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitsError());
			return data;
		}
		
		@Override
		public IData<PaginatedIssues> getRepositoriesIssuesPaginatedIssues(){
			Data<PaginatedIssues> data = new Data<PaginatedIssues>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesPaginatedIssues());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesRefsBranchesErrorByName(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesRefsBranchesErrorByName());
			return data;
		}
		
		@Override
		public IData<Error> getTeamsRepositoriesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsRepositoriesError());
			return data;
		}
		
		@Override
		public IData<PaginatedSnippetComments> getSnippetsCommentsPaginatedSnippetComments(){
			Data<PaginatedSnippetComments> data = new Data<PaginatedSnippetComments>();
			data.addElement(callbackEndpoint.getSnippetsCommentsPaginatedSnippetComments());
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsCommentsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsCommentsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesErrorByRepoSlug(String username, String repoSlug){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesErrorByRepoSlug(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<Error> getUsersRepositoriesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersRepositoriesError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesRefsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesRefsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsErrorByPullRequestId(String username, String repoSlug, Integer pullRequestId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsErrorByPullRequestId(username, repoSlug, pullRequestId));
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getUsersFollowingPaginatedUsers(String username){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getUsersFollowingPaginatedUsers(username));
			return data;
		}
		
		@Override
		public IData<Error> getUsersFollowingError(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersFollowingError(username));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesVoteError(Integer issueId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesVoteError(issueId));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsFilesErrorByPath(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsFilesErrorByPath());
			return data;
		}
		
		@Override
		public IData<Error> getTeamsPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsPipelines_configVariablesErrorByVariableUuid(username, variableUuid));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesCommentsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesCommentsError());
			return data;
		}
		
		@Override
		public IData<PaginatedPipelineSteps> getRepositoriesPipelinesStepsPaginatedPipelineSteps(String username, String repoSlug, String pipelineUuid){
			Data<PaginatedPipelineSteps> data = new Data<PaginatedPipelineSteps>();
			data.addElement(callbackEndpoint.getRepositoriesPipelinesStepsPaginatedPipelineSteps(username, repoSlug, pipelineUuid));
			return data;
		}
		
		@Override
		public IData<Error> getAddonLinkersErrorByLinkerKey(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getAddonLinkersErrorByLinkerKey());
			return data;
		}
		
		@Override
		public IData<PaginatedCommitstatuses> getRepositoriesCommitStatusesPaginatedCommitstatuses(String node){
			Data<PaginatedCommitstatuses> data = new Data<PaginatedCommitstatuses>();
			data.addElement(callbackEndpoint.getRepositoriesCommitStatusesPaginatedCommitstatuses(node));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitStatusesError(String node){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitStatusesError(node));
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getTeamsFollowersPaginatedUsers(String username){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getTeamsFollowersPaginatedUsers(username));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsFollowersError(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsFollowersError(username));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesRefsTagsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesRefsTagsError());
			return data;
		}
		
		@Override
		public IData<PaginatedWebhookSubscriptions> getTeamsHooksPaginatedWebhookSubscriptions(){
			Data<PaginatedWebhookSubscriptions> data = new Data<PaginatedWebhookSubscriptions>();
			data.addElement(callbackEndpoint.getTeamsHooksPaginatedWebhookSubscriptions());
			return data;
		}
		
		@Override
		public IData<Error> getTeamsHooksError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsHooksError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitCommentsErrorByCommentId(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitCommentsErrorByCommentId());
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getSnippetsWatchersPaginatedUsers(String encodedId){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getSnippetsWatchersPaginatedUsers(encodedId));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsWatchersError(String encodedId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsWatchersError(encodedId));
			return data;
		}
		
		@Override
		public IData<PaginatedPipelineVariables> getRepositoriesPipelines_configVariablesPaginatedPipelineVariables(String username, String repoSlug){
			Data<PaginatedPipelineVariables> data = new Data<PaginatedPipelineVariables>();
			data.addElement(callbackEndpoint.getRepositoriesPipelines_configVariablesPaginatedPipelineVariables(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsErrorByNodeId(String encodedId, String nodeId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsErrorByNodeId(encodedId, nodeId));
			return data;
		}
		
		@Override
		public IData<PaginatedPullrequests> getRepositoriesPullrequestsPaginatedPullrequests(String username, String repoSlug, String state){
			Data<PaginatedPullrequests> data = new Data<PaginatedPullrequests>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsPaginatedPullrequests(username, repoSlug, state));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsError(String username, String repoSlug, String state){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsError(username, repoSlug, state));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsPatchError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsPatchError());
			return data;
		}
		
		@Override
		public IData<PaginatedHookEvents> getHook_eventsPaginatedHookEventsBySubjectType(String subjectType){
			Data<PaginatedHookEvents> data = new Data<PaginatedHookEvents>();
			data.addElement(callbackEndpoint.getHook_eventsPaginatedHookEventsBySubjectType(subjectType));
			return data;
		}
		
		@Override
		public IData<Error> getHook_eventsErrorBySubjectType(String subjectType){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getHook_eventsErrorBySubjectType(subjectType));
			return data;
		}
		
		@Override
		public IData<PaginatedCommitstatuses> getRepositoriesPullrequestsStatusesPaginatedCommitstatuses(Integer pullRequestId){
			Data<PaginatedCommitstatuses> data = new Data<PaginatedCommitstatuses>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsStatusesPaginatedCommitstatuses(pullRequestId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsStatusesError(Integer pullRequestId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsStatusesError(pullRequestId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesErrorByIssueId(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesErrorByIssueId());
			return data;
		}
		
		@Override
		public IData<PaginatedPipelines> getRepositoriesPipelinesPaginatedPipelines(String username, String repoSlug){
			Data<PaginatedPipelines> data = new Data<PaginatedPipelines>();
			data.addElement(callbackEndpoint.getRepositoriesPipelinesPaginatedPipelines(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<PaginatedVersions> getRepositoriesVersionsPaginatedVersions(){
			Data<PaginatedVersions> data = new Data<PaginatedVersions>();
			data.addElement(callbackEndpoint.getRepositoriesVersionsPaginatedVersions());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesVersionsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesVersionsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesAttachmentsErrorByPath(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesAttachmentsErrorByPath());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesWatchError(Integer issueId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesWatchError(issueId));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsProjectsErrorByProjectKey(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsProjectsErrorByProjectKey());
			return data;
		}
		
		@Override
		public IData<PaginatedSnippetCommit> getSnippetsCommitsPaginatedSnippetCommit(){
			Data<PaginatedSnippetCommit> data = new Data<PaginatedSnippetCommit>();
			data.addElement(callbackEndpoint.getSnippetsCommitsPaginatedSnippetCommit());
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsCommitsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsCommitsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesBranch_restrictionsErrorById(String id){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesBranch_restrictionsErrorById(id));
			return data;
		}
		
		@Override
		public IData<PaginatedPipelineKnownHosts> getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts(String username, String repoSlug){
			Data<PaginatedPipelineKnownHosts> data = new Data<PaginatedPipelineKnownHosts>();
			data.addElement(callbackEndpoint.getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<PaginatedPipelineVariables> getUsersPipelines_configVariablesPaginatedPipelineVariables(String username){
			Data<PaginatedPipelineVariables> data = new Data<PaginatedPipelineVariables>();
			data.addElement(callbackEndpoint.getUsersPipelines_configVariablesPaginatedPipelineVariables(username));
			return data;
		}
		
		@Override
		public IData<Error> getAddonLinkersValuesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getAddonLinkersValuesError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesVersionsErrorByVersionId(Integer versionId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesVersionsErrorByVersionId(versionId));
			return data;
		}
		
		@Override
		public IData<PaginatedWebhookSubscriptions> getUsersHooksPaginatedWebhookSubscriptions(){
			Data<PaginatedWebhookSubscriptions> data = new Data<PaginatedWebhookSubscriptions>();
			data.addElement(callbackEndpoint.getUsersHooksPaginatedWebhookSubscriptions());
			return data;
		}
		
		@Override
		public IData<Error> getUsersHooksError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersHooksError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitErrorByRevision(String revision){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitErrorByRevision(revision));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelinesStepsLogError(String username, String repoSlug, String pipelineUuid, String stepUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelinesStepsLogError(username, repoSlug, pipelineUuid, stepUuid));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsCommitsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsCommitsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesDownloadsErrorByFilename(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesDownloadsErrorByFilename());
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getSnippetsWatchPaginatedUsers(String encodedId){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getSnippetsWatchPaginatedUsers(encodedId));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsWatchError(String encodedId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsWatchError(encodedId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesComponentsErrorByComponentId(Integer componentId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesComponentsErrorByComponentId(componentId));
			return data;
		}
		
		@Override
		public IData<Error> getUsersPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersPipelines_configVariablesErrorByVariableUuid(username, variableUuid));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelines_configSshKey_pairError(String username, String repoSlug){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelines_configSshKey_pairError(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitCommentsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitCommentsError());
			return data;
		}
		
		@Override
		public IData<PaginatedBranchrestrictions> getRepositoriesBranch_restrictionsPaginatedBranchrestrictions(){
			Data<PaginatedBranchrestrictions> data = new Data<PaginatedBranchrestrictions>();
			data.addElement(callbackEndpoint.getRepositoriesBranch_restrictionsPaginatedBranchrestrictions());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesBranch_restrictionsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesBranch_restrictionsError());
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsCommitsErrorByRevision(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsCommitsErrorByRevision());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPatchErrorBySpec(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPatchErrorBySpec());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelines_configVariablesErrorByVariableUuid(String username, String repoSlug, String variableUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelines_configVariablesErrorByVariableUuid(username, repoSlug, variableUuid));
			return data;
		}
		
		@Override
		public IData<Error> getAddonLinkersError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getAddonLinkersError());
			return data;
		}
		
		@Override
		public IData<Error> getTeamsErrorByUsername(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsErrorByUsername(username));
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsCommentsErrorByCommentId(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsCommentsErrorByCommentId());
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsDiffError(String path, String encodedId, String revision){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsDiffError(path, encodedId, revision));
			return data;
		}
		
		@Override
		public IData<Error> getUserError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUserError());
			return data;
		}
		
		@Override
		public IData<SubjectTypes> getHook_eventsSubjectTypes(){
			Data<SubjectTypes> data = new Data<SubjectTypes>();
			data.addElement(callbackEndpoint.getHook_eventsSubjectTypes());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesMilestonesErrorByMilestoneId(Integer milestoneId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesMilestonesErrorByMilestoneId(milestoneId));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsMembersError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsMembersError());
			return data;
		}
		
		@Override
		public IData<PaginatedIssueAttachments> getRepositoriesIssuesAttachmentsPaginatedIssueAttachments(Integer issueId){
			Data<PaginatedIssueAttachments> data = new Data<PaginatedIssueAttachments>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesAttachmentsPaginatedIssueAttachments(issueId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesIssuesAttachmentsError(Integer issueId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesIssuesAttachmentsError(issueId));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitsErrorByRevision(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitsErrorByRevision());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesDefault_reviewersErrorByTargetUsername(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesDefault_reviewersErrorByTargetUsername());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsCommentsErrorByCommentId(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsCommentsErrorByCommentId());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsCommentsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsCommentsError());
			return data;
		}
		
		@Override
		public IData<PaginatedWebhookSubscriptions> getRepositoriesHooksPaginatedWebhookSubscriptions(){
			Data<PaginatedWebhookSubscriptions> data = new Data<PaginatedWebhookSubscriptions>();
			data.addElement(callbackEndpoint.getRepositoriesHooksPaginatedWebhookSubscriptions());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesHooksError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesHooksError());
			return data;
		}
		
		@Override
		public IData<Error> getUsersErrorByUsername(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersErrorByUsername(username));
			return data;
		}
		
		@Override
		public IData<Error> getUserEmailsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUserEmailsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsDiffError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsDiffError());
			return data;
		}
		
		@Override
		public IData<Error> getSnippetsPatchError(String encodedId, String revision){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getSnippetsPatchError(encodedId, revision));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsActivityError(username, repoSlug));
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getUsersFollowersPaginatedUsers(String username){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getUsersFollowersPaginatedUsers(username));
			return data;
		}
		
		@Override
		public IData<Error> getUsersFollowersError(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersFollowersError(username));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesCommitStatusesBuildErrorByKey(String node, String key){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesCommitStatusesBuildErrorByKey(node, key));
			return data;
		}
		
		@Override
		public IData<PaginatedPipelineVariables> getTeamsPipelines_configVariablesPaginatedPipelineVariables(String username){
			Data<PaginatedPipelineVariables> data = new Data<PaginatedPipelineVariables>();
			data.addElement(callbackEndpoint.getTeamsPipelines_configVariablesPaginatedPipelineVariables(username));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesHooksErrorByUid(String uid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesHooksErrorByUid(uid));
			return data;
		}
		
		@Override
		public IData<PaginatedProjects> getTeamsProjectsPaginatedProjects(){
			Data<PaginatedProjects> data = new Data<PaginatedProjects>();
			data.addElement(callbackEndpoint.getTeamsProjectsPaginatedProjects());
			return data;
		}
		
		@Override
		public IData<Error> getTeamsProjectsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsProjectsError());
			return data;
		}
		
		@Override
		public IData<PaginatedComponents> getRepositoriesComponentsPaginatedComponents(){
			Data<PaginatedComponents> data = new Data<PaginatedComponents>();
			data.addElement(callbackEndpoint.getRepositoriesComponentsPaginatedComponents());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesComponentsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesComponentsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelinesErrorByPipelineUuid(String username, String repoSlug, String pipelineUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelinesErrorByPipelineUuid(username, repoSlug, pipelineUuid));
			return data;
		}
		
		@Override
		public IData<PaginatedUsers> getTeamsFollowingPaginatedUsers(String username){
			Data<PaginatedUsers> data = new Data<PaginatedUsers>();
			data.addElement(callbackEndpoint.getTeamsFollowingPaginatedUsers(username));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsFollowingError(String username){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsFollowingError(username));
			return data;
		}
		
		@Override
		public IData<Error> getTeamsHooksErrorByUid(String uid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getTeamsHooksErrorByUid(uid));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug, Integer pullRequestId){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPullrequestsActivityError(username, repoSlug, pullRequestId));
			return data;
		}
		
		@Override
		public IData<Error> getUserEmailsErrorByEmail(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUserEmailsErrorByEmail());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelinesStepsErrorByStepUuid(String username, String repoSlug, String pipelineUuid, String stepUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelinesStepsErrorByStepUuid(username, repoSlug, pipelineUuid, stepUuid));
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesRefsBranchesError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesRefsBranchesError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesDownloadsError(){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesDownloadsError());
			return data;
		}
		
		@Override
		public IData<Error> getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid(String username, String repoSlug, String knownHostUuid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid(username, repoSlug, knownHostUuid));
			return data;
		}
		
		@Override
		public IData<Error> getUsersHooksErrorByUid(String uid){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getUsersHooksErrorByUid(uid));
			return data;
		}
		
		
	}
}
