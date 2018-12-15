package org.eclipse.scava.crossflow.restmule.client.bitbucket.api;

import org.apache.commons.lang3.Validate;
import org.eclipse.scava.crossflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.client.EntityApi;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.client.IEntityApi;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.client.SearchApi;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.client.ISearchApi;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.*;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.model.Error;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.session.BitbucketSession;

public class BitbucketApi  {

	public static BitbucketBuilder create(){
		return new BitbucketBuilder(); 
	}
	
	public static IBitbucketApi createDefault(){ 
		return new BitbucketBuilder().setSession(BitbucketSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class BitbucketBuilder 
	implements IClientBuilder<IBitbucketApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public IBitbucketApi build() {
			return (IBitbucketApi) new BitbucketClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<IBitbucketApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<IBitbucketApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class BitbucketClient implements IBitbucketApi {
		
		private IEntityApi entityClient;
		private ISearchApi searchClient;
		
		BitbucketClient(ISession session, boolean activeCaching) {
			if (session == null) {
				session = BitbucketSession.createPublic(); 
			}	
			entityClient = EntityApi.create()
				.setSession(BitbucketSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
			searchClient = SearchApi.create()
				.setSession(BitbucketSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
		}

		/** WRAPED METHODS */
		 
		@Override
		public IData<Error> getRepositoriesWatchersError(){ 
			return entityClient.getRepositoriesWatchersError();
		}
		 
		@Override
		public IData<PaginatedMilestones> getRepositoriesMilestonesPaginatedMilestones(){ 
			return entityClient.getRepositoriesMilestonesPaginatedMilestones();
		}
		 
		@Override
		public IData<Error> getRepositoriesMilestonesError(){ 
			return entityClient.getRepositoriesMilestonesError();
		}
		 
		@Override
		public IData<PaginatedRepositories> getRepositoriesPaginatedRepositoriesByUsername(String username, String role){ 
			Validate.notNull(username);
			if (role != null) {
				Validate.matchesPattern(role,"(admin|contributor|member|owner)");
			}
			return entityClient.getRepositoriesPaginatedRepositoriesByUsername(username, role);
		}
		 
		@Override
		public IData<Error> getRepositoriesErrorByUsername(String username, String role){ 
			Validate.notNull(username);
			if (role != null) {
				Validate.matchesPattern(role,"(admin|contributor|member|owner)");
			}
			return entityClient.getRepositoriesErrorByUsername(username, role);
		}
		 
		@Override
		public IData<Error> getSnippetsErrorByEncodedId(String encodedId){ 
			Validate.notNull(encodedId);
			return entityClient.getSnippetsErrorByEncodedId(encodedId);
		}
		 
		@Override
		public IData<Error> getRepositoriesDiffErrorBySpec(Integer context, String path){ 
			
			
			return entityClient.getRepositoriesDiffErrorBySpec(context, path);
		}
		 
		@Override
		public IData<PaginatedSnippets> getSnippetsPaginatedSnippetsByUsername(String role, String username){ 
			if (role != null) {
				Validate.matchesPattern(role,"(owner|contributor|member)");
			}
			Validate.notNull(username);
			return entityClient.getSnippetsPaginatedSnippetsByUsername(role, username);
		}
		 
		@Override
		public IData<Error> getSnippetsErrorByUsername(String role, String username){ 
			if (role != null) {
				Validate.matchesPattern(role,"(owner|contributor|member)");
			}
			Validate.notNull(username);
			return entityClient.getSnippetsErrorByUsername(role, username);
		}
		 
		@Override
		public IData<PaginatedSnippets> getSnippetsPaginatedSnippets(String role){ 
			if (role != null) {
				Validate.matchesPattern(role,"(owner|contributor|member)");
			}
			return entityClient.getSnippetsPaginatedSnippets(role);
		}
		 
		@Override
		public IData<Error> getSnippetsError(String role){ 
			if (role != null) {
				Validate.matchesPattern(role,"(owner|contributor|member)");
			}
			return entityClient.getSnippetsError(role);
		}
		 
		@Override
		public IData<Error> getRepositoriesSrcErrorByPath(String format){ 
			if (format != null) {
				Validate.matchesPattern(format,"(meta)");
			}
			return entityClient.getRepositoriesSrcErrorByPath(format);
		}
		 
		@Override
		public IData<PaginatedRepositories> getRepositoriesForksPaginatedRepositories(){ 
			return entityClient.getRepositoriesForksPaginatedRepositories();
		}
		 
		@Override
		public IData<Error> getRepositoriesRefsTagsErrorByName(){ 
			return entityClient.getRepositoriesRefsTagsErrorByName();
		}
		 
		@Override
		public IData<PaginatedRepositories> getRepositoriesPaginatedRepositories(String after){ 
			
			return entityClient.getRepositoriesPaginatedRepositories(after);
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesCommentsErrorByCommentId(){ 
			return entityClient.getRepositoriesIssuesCommentsErrorByCommentId();
		}
		 
		@Override
		public IData<PaginatedTeams> getTeamsPaginatedTeams(String role){ 
			if (role != null) {
				Validate.matchesPattern(role,"(admin|contributor|member)");
			}
			return entityClient.getTeamsPaginatedTeams(role);
		}
		 
		@Override
		public IData<Error> getTeamsError(String role){ 
			if (role != null) {
				Validate.matchesPattern(role,"(admin|contributor|member)");
			}
			return entityClient.getTeamsError(role);
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitsError(){ 
			return entityClient.getRepositoriesCommitsError();
		}
		 
		@Override
		public IData<PaginatedIssues> getRepositoriesIssuesPaginatedIssues(){ 
			return entityClient.getRepositoriesIssuesPaginatedIssues();
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesError(){ 
			return entityClient.getRepositoriesIssuesError();
		}
		 
		@Override
		public IData<Error> getRepositoriesRefsBranchesErrorByName(){ 
			return entityClient.getRepositoriesRefsBranchesErrorByName();
		}
		 
		@Override
		public IData<Error> getTeamsRepositoriesError(){ 
			return entityClient.getTeamsRepositoriesError();
		}
		 
		@Override
		public IData<PaginatedSnippetComments> getSnippetsCommentsPaginatedSnippetComments(){ 
			return entityClient.getSnippetsCommentsPaginatedSnippetComments();
		}
		 
		@Override
		public IData<Error> getSnippetsCommentsError(){ 
			return entityClient.getSnippetsCommentsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesErrorByRepoSlug(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesErrorByRepoSlug(username, repoSlug);
		}
		 
		@Override
		public IData<Error> getUsersRepositoriesError(){ 
			return entityClient.getUsersRepositoriesError();
		}
		 
		@Override
		public IData<Error> getRepositoriesRefsError(){ 
			return entityClient.getRepositoriesRefsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsErrorByPullRequestId(String username, String repoSlug, Integer pullRequestId){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pullRequestId);
			return entityClient.getRepositoriesPullrequestsErrorByPullRequestId(username, repoSlug, pullRequestId);
		}
		 
		@Override
		public IData<PaginatedUsers> getUsersFollowingPaginatedUsers(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersFollowingPaginatedUsers(username);
		}
		 
		@Override
		public IData<Error> getUsersFollowingError(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersFollowingError(username);
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesVoteError(Integer issueId){ 
			Validate.notNull(issueId);
			return entityClient.getRepositoriesIssuesVoteError(issueId);
		}
		 
		@Override
		public IData<Error> getSnippetsFilesErrorByPath(){ 
			return entityClient.getSnippetsFilesErrorByPath();
		}
		 
		@Override
		public IData<Error> getTeamsPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid){ 
			Validate.notNull(username);
			Validate.notNull(variableUuid);
			return entityClient.getTeamsPipelines_configVariablesErrorByVariableUuid(username, variableUuid);
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesCommentsError(){ 
			return entityClient.getRepositoriesIssuesCommentsError();
		}
		 
		@Override
		public IData<PaginatedPipelineSteps> getRepositoriesPipelinesStepsPaginatedPipelineSteps(String username, String repoSlug, String pipelineUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pipelineUuid);
			return entityClient.getRepositoriesPipelinesStepsPaginatedPipelineSteps(username, repoSlug, pipelineUuid);
		}
		 
		@Override
		public IData<Error> getAddonLinkersErrorByLinkerKey(){ 
			return entityClient.getAddonLinkersErrorByLinkerKey();
		}
		 
		@Override
		public IData<PaginatedCommitstatuses> getRepositoriesCommitStatusesPaginatedCommitstatuses(String node){ 
			Validate.notNull(node);
			return entityClient.getRepositoriesCommitStatusesPaginatedCommitstatuses(node);
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitStatusesError(String node){ 
			Validate.notNull(node);
			return entityClient.getRepositoriesCommitStatusesError(node);
		}
		 
		@Override
		public IData<PaginatedUsers> getTeamsFollowersPaginatedUsers(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsFollowersPaginatedUsers(username);
		}
		 
		@Override
		public IData<Error> getTeamsFollowersError(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsFollowersError(username);
		}
		 
		@Override
		public IData<Error> getRepositoriesRefsTagsError(){ 
			return entityClient.getRepositoriesRefsTagsError();
		}
		 
		@Override
		public IData<PaginatedWebhookSubscriptions> getTeamsHooksPaginatedWebhookSubscriptions(){ 
			return entityClient.getTeamsHooksPaginatedWebhookSubscriptions();
		}
		 
		@Override
		public IData<Error> getTeamsHooksError(){ 
			return entityClient.getTeamsHooksError();
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitCommentsErrorByCommentId(){ 
			return entityClient.getRepositoriesCommitCommentsErrorByCommentId();
		}
		 
		@Override
		public IData<PaginatedUsers> getSnippetsWatchersPaginatedUsers(String encodedId){ 
			Validate.notNull(encodedId);
			return entityClient.getSnippetsWatchersPaginatedUsers(encodedId);
		}
		 
		@Override
		public IData<Error> getSnippetsWatchersError(String encodedId){ 
			Validate.notNull(encodedId);
			return entityClient.getSnippetsWatchersError(encodedId);
		}
		 
		@Override
		public IData<PaginatedPipelineVariables> getRepositoriesPipelines_configVariablesPaginatedPipelineVariables(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesPipelines_configVariablesPaginatedPipelineVariables(username, repoSlug);
		}
		 
		@Override
		public IData<Error> getSnippetsErrorByNodeId(String encodedId, String nodeId){ 
			Validate.notNull(encodedId);
			Validate.notNull(nodeId);
			return entityClient.getSnippetsErrorByNodeId(encodedId, nodeId);
		}
		 
		@Override
		public IData<PaginatedPullrequests> getRepositoriesPullrequestsPaginatedPullrequests(String username, String repoSlug, String state){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			if (state != null) {
				Validate.matchesPattern(state,"(MERGED|SUPERSEDED|OPEN|DECLINED)");
			}
			return entityClient.getRepositoriesPullrequestsPaginatedPullrequests(username, repoSlug, state);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsError(String username, String repoSlug, String state){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			if (state != null) {
				Validate.matchesPattern(state,"(MERGED|SUPERSEDED|OPEN|DECLINED)");
			}
			return entityClient.getRepositoriesPullrequestsError(username, repoSlug, state);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsPatchError(){ 
			return entityClient.getRepositoriesPullrequestsPatchError();
		}
		 
		@Override
		public IData<PaginatedHookEvents> getHook_eventsPaginatedHookEventsBySubjectType(String subjectType){ 
			Validate.notNull(subjectType);
			Validate.matchesPattern(subjectType,"(user|repository|team)");
			return entityClient.getHook_eventsPaginatedHookEventsBySubjectType(subjectType);
		}
		 
		@Override
		public IData<Error> getHook_eventsErrorBySubjectType(String subjectType){ 
			Validate.notNull(subjectType);
			Validate.matchesPattern(subjectType,"(user|repository|team)");
			return entityClient.getHook_eventsErrorBySubjectType(subjectType);
		}
		 
		@Override
		public IData<PaginatedCommitstatuses> getRepositoriesPullrequestsStatusesPaginatedCommitstatuses(Integer pullRequestId){ 
			Validate.notNull(pullRequestId);
			return entityClient.getRepositoriesPullrequestsStatusesPaginatedCommitstatuses(pullRequestId);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsStatusesError(Integer pullRequestId){ 
			Validate.notNull(pullRequestId);
			return entityClient.getRepositoriesPullrequestsStatusesError(pullRequestId);
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesErrorByIssueId(){ 
			return entityClient.getRepositoriesIssuesErrorByIssueId();
		}
		 
		@Override
		public IData<PaginatedPipelines> getRepositoriesPipelinesPaginatedPipelines(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesPipelinesPaginatedPipelines(username, repoSlug);
		}
		 
		@Override
		public IData<PaginatedVersions> getRepositoriesVersionsPaginatedVersions(){ 
			return entityClient.getRepositoriesVersionsPaginatedVersions();
		}
		 
		@Override
		public IData<Error> getRepositoriesVersionsError(){ 
			return entityClient.getRepositoriesVersionsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesAttachmentsErrorByPath(){ 
			return entityClient.getRepositoriesIssuesAttachmentsErrorByPath();
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesWatchError(Integer issueId){ 
			Validate.notNull(issueId);
			return entityClient.getRepositoriesIssuesWatchError(issueId);
		}
		 
		@Override
		public IData<Error> getTeamsProjectsErrorByProjectKey(){ 
			return entityClient.getTeamsProjectsErrorByProjectKey();
		}
		 
		@Override
		public IData<PaginatedSnippetCommit> getSnippetsCommitsPaginatedSnippetCommit(){ 
			return entityClient.getSnippetsCommitsPaginatedSnippetCommit();
		}
		 
		@Override
		public IData<Error> getSnippetsCommitsError(){ 
			return entityClient.getSnippetsCommitsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesBranch_restrictionsErrorById(String id){ 
			Validate.notNull(id);
			return entityClient.getRepositoriesBranch_restrictionsErrorById(id);
		}
		 
		@Override
		public IData<PaginatedPipelineKnownHosts> getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesPipelines_configSshKnown_hostsPaginatedPipelineKnownHosts(username, repoSlug);
		}
		 
		@Override
		public IData<PaginatedPipelineVariables> getUsersPipelines_configVariablesPaginatedPipelineVariables(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersPipelines_configVariablesPaginatedPipelineVariables(username);
		}
		 
		@Override
		public IData<Error> getAddonLinkersValuesError(){ 
			return entityClient.getAddonLinkersValuesError();
		}
		 
		@Override
		public IData<Error> getRepositoriesVersionsErrorByVersionId(Integer versionId){ 
			Validate.notNull(versionId);
			return entityClient.getRepositoriesVersionsErrorByVersionId(versionId);
		}
		 
		@Override
		public IData<PaginatedWebhookSubscriptions> getUsersHooksPaginatedWebhookSubscriptions(){ 
			return entityClient.getUsersHooksPaginatedWebhookSubscriptions();
		}
		 
		@Override
		public IData<Error> getUsersHooksError(){ 
			return entityClient.getUsersHooksError();
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitErrorByRevision(String revision){ 
			Validate.notNull(revision);
			return entityClient.getRepositoriesCommitErrorByRevision(revision);
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelinesStepsLogError(String username, String repoSlug, String pipelineUuid, String stepUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pipelineUuid);
			Validate.notNull(stepUuid);
			return entityClient.getRepositoriesPipelinesStepsLogError(username, repoSlug, pipelineUuid, stepUuid);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsCommitsError(){ 
			return entityClient.getRepositoriesPullrequestsCommitsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesDownloadsErrorByFilename(){ 
			return entityClient.getRepositoriesDownloadsErrorByFilename();
		}
		 
		@Override
		public IData<PaginatedUsers> getSnippetsWatchPaginatedUsers(String encodedId){ 
			Validate.notNull(encodedId);
			return entityClient.getSnippetsWatchPaginatedUsers(encodedId);
		}
		 
		@Override
		public IData<Error> getSnippetsWatchError(String encodedId){ 
			Validate.notNull(encodedId);
			return entityClient.getSnippetsWatchError(encodedId);
		}
		 
		@Override
		public IData<Error> getRepositoriesComponentsErrorByComponentId(Integer componentId){ 
			Validate.notNull(componentId);
			return entityClient.getRepositoriesComponentsErrorByComponentId(componentId);
		}
		 
		@Override
		public IData<Error> getUsersPipelines_configVariablesErrorByVariableUuid(String username, String variableUuid){ 
			Validate.notNull(username);
			Validate.notNull(variableUuid);
			return entityClient.getUsersPipelines_configVariablesErrorByVariableUuid(username, variableUuid);
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelines_configSshKey_pairError(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesPipelines_configSshKey_pairError(username, repoSlug);
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitCommentsError(){ 
			return entityClient.getRepositoriesCommitCommentsError();
		}
		 
		@Override
		public IData<PaginatedBranchrestrictions> getRepositoriesBranch_restrictionsPaginatedBranchrestrictions(){ 
			return entityClient.getRepositoriesBranch_restrictionsPaginatedBranchrestrictions();
		}
		 
		@Override
		public IData<Error> getRepositoriesBranch_restrictionsError(){ 
			return entityClient.getRepositoriesBranch_restrictionsError();
		}
		 
		@Override
		public IData<Error> getSnippetsCommitsErrorByRevision(){ 
			return entityClient.getSnippetsCommitsErrorByRevision();
		}
		 
		@Override
		public IData<Error> getRepositoriesPatchErrorBySpec(){ 
			return entityClient.getRepositoriesPatchErrorBySpec();
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelines_configVariablesErrorByVariableUuid(String username, String repoSlug, String variableUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(variableUuid);
			return entityClient.getRepositoriesPipelines_configVariablesErrorByVariableUuid(username, repoSlug, variableUuid);
		}
		 
		@Override
		public IData<Error> getAddonLinkersError(){ 
			return entityClient.getAddonLinkersError();
		}
		 
		@Override
		public IData<Error> getTeamsErrorByUsername(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsErrorByUsername(username);
		}
		 
		@Override
		public IData<Error> getSnippetsCommentsErrorByCommentId(){ 
			return entityClient.getSnippetsCommentsErrorByCommentId();
		}
		 
		@Override
		public IData<Error> getSnippetsDiffError(String path, String encodedId, String revision){ 
			
			Validate.notNull(encodedId);
			Validate.notNull(revision);
			return entityClient.getSnippetsDiffError(path, encodedId, revision);
		}
		 
		@Override
		public IData<Error> getUserError(){ 
			return entityClient.getUserError();
		}
		 
		@Override
		public IData<SubjectTypes> getHook_eventsSubjectTypes(){ 
			return entityClient.getHook_eventsSubjectTypes();
		}
		 
		@Override
		public IData<Error> getRepositoriesMilestonesErrorByMilestoneId(Integer milestoneId){ 
			Validate.notNull(milestoneId);
			return entityClient.getRepositoriesMilestonesErrorByMilestoneId(milestoneId);
		}
		 
		@Override
		public IData<Error> getTeamsMembersError(){ 
			return entityClient.getTeamsMembersError();
		}
		 
		@Override
		public IData<PaginatedIssueAttachments> getRepositoriesIssuesAttachmentsPaginatedIssueAttachments(Integer issueId){ 
			Validate.notNull(issueId);
			return entityClient.getRepositoriesIssuesAttachmentsPaginatedIssueAttachments(issueId);
		}
		 
		@Override
		public IData<Error> getRepositoriesIssuesAttachmentsError(Integer issueId){ 
			Validate.notNull(issueId);
			return entityClient.getRepositoriesIssuesAttachmentsError(issueId);
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitsErrorByRevision(){ 
			return entityClient.getRepositoriesCommitsErrorByRevision();
		}
		 
		@Override
		public IData<Error> getRepositoriesDefault_reviewersErrorByTargetUsername(){ 
			return entityClient.getRepositoriesDefault_reviewersErrorByTargetUsername();
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsCommentsErrorByCommentId(){ 
			return entityClient.getRepositoriesPullrequestsCommentsErrorByCommentId();
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsCommentsError(){ 
			return entityClient.getRepositoriesPullrequestsCommentsError();
		}
		 
		@Override
		public IData<PaginatedWebhookSubscriptions> getRepositoriesHooksPaginatedWebhookSubscriptions(){ 
			return entityClient.getRepositoriesHooksPaginatedWebhookSubscriptions();
		}
		 
		@Override
		public IData<Error> getRepositoriesHooksError(){ 
			return entityClient.getRepositoriesHooksError();
		}
		 
		@Override
		public IData<Error> getUsersErrorByUsername(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersErrorByUsername(username);
		}
		 
		@Override
		public IData<Error> getUserEmailsError(){ 
			return entityClient.getUserEmailsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsDiffError(){ 
			return entityClient.getRepositoriesPullrequestsDiffError();
		}
		 
		@Override
		public IData<Error> getSnippetsPatchError(String encodedId, String revision){ 
			Validate.notNull(encodedId);
			Validate.notNull(revision);
			return entityClient.getSnippetsPatchError(encodedId, revision);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			return entityClient.getRepositoriesPullrequestsActivityError(username, repoSlug);
		}
		 
		@Override
		public IData<PaginatedUsers> getUsersFollowersPaginatedUsers(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersFollowersPaginatedUsers(username);
		}
		 
		@Override
		public IData<Error> getUsersFollowersError(String username){ 
			Validate.notNull(username);
			return entityClient.getUsersFollowersError(username);
		}
		 
		@Override
		public IData<Error> getRepositoriesCommitStatusesBuildErrorByKey(String node, String key){ 
			Validate.notNull(node);
			Validate.notNull(key);
			return entityClient.getRepositoriesCommitStatusesBuildErrorByKey(node, key);
		}
		 
		@Override
		public IData<PaginatedPipelineVariables> getTeamsPipelines_configVariablesPaginatedPipelineVariables(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsPipelines_configVariablesPaginatedPipelineVariables(username);
		}
		 
		@Override
		public IData<Error> getRepositoriesHooksErrorByUid(String uid){ 
			Validate.notNull(uid);
			return entityClient.getRepositoriesHooksErrorByUid(uid);
		}
		 
		@Override
		public IData<PaginatedProjects> getTeamsProjectsPaginatedProjects(){ 
			return entityClient.getTeamsProjectsPaginatedProjects();
		}
		 
		@Override
		public IData<Error> getTeamsProjectsError(){ 
			return entityClient.getTeamsProjectsError();
		}
		 
		@Override
		public IData<PaginatedComponents> getRepositoriesComponentsPaginatedComponents(){ 
			return entityClient.getRepositoriesComponentsPaginatedComponents();
		}
		 
		@Override
		public IData<Error> getRepositoriesComponentsError(){ 
			return entityClient.getRepositoriesComponentsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelinesErrorByPipelineUuid(String username, String repoSlug, String pipelineUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pipelineUuid);
			return entityClient.getRepositoriesPipelinesErrorByPipelineUuid(username, repoSlug, pipelineUuid);
		}
		 
		@Override
		public IData<PaginatedUsers> getTeamsFollowingPaginatedUsers(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsFollowingPaginatedUsers(username);
		}
		 
		@Override
		public IData<Error> getTeamsFollowingError(String username){ 
			Validate.notNull(username);
			return entityClient.getTeamsFollowingError(username);
		}
		 
		@Override
		public IData<Error> getTeamsHooksErrorByUid(String uid){ 
			Validate.notNull(uid);
			return entityClient.getTeamsHooksErrorByUid(uid);
		}
		 
		@Override
		public IData<Error> getRepositoriesPullrequestsActivityError(String username, String repoSlug, Integer pullRequestId){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pullRequestId);
			return entityClient.getRepositoriesPullrequestsActivityError(username, repoSlug, pullRequestId);
		}
		 
		@Override
		public IData<Error> getUserEmailsErrorByEmail(){ 
			return entityClient.getUserEmailsErrorByEmail();
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelinesStepsErrorByStepUuid(String username, String repoSlug, String pipelineUuid, String stepUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(pipelineUuid);
			Validate.notNull(stepUuid);
			return entityClient.getRepositoriesPipelinesStepsErrorByStepUuid(username, repoSlug, pipelineUuid, stepUuid);
		}
		 
		@Override
		public IData<Error> getRepositoriesRefsBranchesError(){ 
			return entityClient.getRepositoriesRefsBranchesError();
		}
		 
		@Override
		public IData<Error> getRepositoriesDownloadsError(){ 
			return entityClient.getRepositoriesDownloadsError();
		}
		 
		@Override
		public IData<Error> getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid(String username, String repoSlug, String knownHostUuid){ 
			Validate.notNull(username);
			Validate.notNull(repoSlug);
			Validate.notNull(knownHostUuid);
			return entityClient.getRepositoriesPipelines_configSshKnown_hostsErrorByKnownHostUuid(username, repoSlug, knownHostUuid);
		}
		 
		@Override
		public IData<Error> getUsersHooksErrorByUid(String uid){ 
			Validate.notNull(uid);
			return entityClient.getUsersHooksErrorByUid(uid);
		}
	}
}
