package org.eclipse.scava.crossflow.restmule.client.gitlab.api;

import org.apache.commons.lang3.Validate;
import org.eclipse.scava.crossflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.gitlab.client.EntityApi;
import org.eclipse.scava.crossflow.restmule.client.gitlab.client.IEntityApi;
import org.eclipse.scava.crossflow.restmule.client.gitlab.client.SearchApi;
import org.eclipse.scava.crossflow.restmule.client.gitlab.client.ISearchApi;
import org.eclipse.scava.crossflow.restmule.client.gitlab.model.*;
import org.eclipse.scava.crossflow.restmule.client.gitlab.session.GitlabSession;

public class GitlabApi  {

	public static GitlabBuilder create(){
		return new GitlabBuilder(); 
	}
	
	public static IGitlabApi createDefault(){ 
		return new GitlabBuilder().setSession(GitlabSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class GitlabBuilder 
	implements IClientBuilder<IGitlabApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public IGitlabApi build() {
			return (IGitlabApi) new GitlabClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<IGitlabApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<IGitlabApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class GitlabClient implements IGitlabApi {
		
		private IEntityApi entityClient;
		private ISearchApi searchClient;
		
		GitlabClient(ISession session, boolean activeCaching) {
			if (session == null) {
				session = GitlabSession.createPublic(); 
			}	
			entityClient = EntityApi.create()
				.setSession(GitlabSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
			searchClient = SearchApi.create()
				.setSession(GitlabSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
		}

		/** WRAPED METHODS */
		 
		@Override
		public IData<SSHKey> getV3ProjectsKeysSSHKey(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsKeysSSHKey(id);
		}
		 
		@Override
		public IData<RepoLicense> getV3TemplatesLicensesRepoLicense(Boolean popular){ 
			
			return entityClient.getV3TemplatesLicensesRepoLicense(popular);
		}
		 
		@Override
		public IData<TemplatesList> getV3Gitlab_ci_ymlsTemplatesList(){ 
			return entityClient.getV3Gitlab_ci_ymlsTemplatesList();
		}
		 
		@Override
		public IData<BasicProjectDetails> getV3ProjectsStarredBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){ 
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			
			
			
			return entityClient.getV3ProjectsStarredBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmoji(String id, Integer mergeRequestId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			
			
			return entityClient.getV3ProjectsMerge_requestsAward_emojiAwardEmoji(id, mergeRequestId, page, perPage);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmoji(String id, Integer issueId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(issueId);
			
			
			return entityClient.getV3ProjectsIssuesAward_emojiAwardEmoji(id, issueId, page, perPage);
		}
		 
		@Override
		public IData<MergeRequestChanges> getV3ProjectsMerge_requestChangesMergeRequestChanges(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestChangesMergeRequestChanges(id, mergeRequestId);
		}
		 
		@Override
		public IData<GlobalNotificationSetting> getV3Notification_settingsGlobalNotificationSetting(){ 
			return entityClient.getV3Notification_settingsGlobalNotificationSetting();
		}
		 
		@Override
		public IData<Environment> getV3ProjectsEnvironmentsEnvironment(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsEnvironmentsEnvironment(id, page, perPage);
		}
		 
		@Override
		public IData<Pipeline> getV3ProjectsPipelinesPipeline(String id, Integer page, Integer perPage, String scope){ 
			Validate.notNull(id);
			
			
			if (scope != null) {
				Validate.matchesPattern(scope,"(running|branches|tags)");
			}
			return entityClient.getV3ProjectsPipelinesPipeline(id, page, perPage, scope);
		}
		 
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){ 
			Validate.notNull(id);
			
			
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestCloses_issuesMRNote(id, page, perPage, mergeRequestId);
		}
		 
		@Override
		public IData<RepoTag> getV3ProjectsRepositoryTagsRepoTag(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsRepositoryTagsRepoTag(id);
		}
		 
		@Override
		public IData<Issue> getV3GroupsIssuesIssue(String id, String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){ 
			Validate.notNull(id);
			if (state != null) {
				Validate.matchesPattern(state,"(opened|closed|all)");
			}
			
			
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(created_at|updated_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			
			return entityClient.getV3GroupsIssuesIssue(id, state, labels, milestone, orderBy, sort, page, perPage);
		}
		 
		@Override
		public IData<TemplatesList> getV3TemplatesGitignoresTemplatesList(){ 
			return entityClient.getV3TemplatesGitignoresTemplatesList();
		}
		 
		@Override
		public IData<UserBasic> getV3UsersUserBasic(String username, String search, Boolean active, Boolean external, Boolean blocked, Integer page, Integer perPage){ 
			
			
			
			
			
			
			
			return entityClient.getV3UsersUserBasic(username, search, active, external, blocked, page, perPage);
		}
		 
		@Override
		public IData<AccessRequester> getV3ProjectsAccess_requestsAccessRequester(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsAccess_requestsAccessRequester(id, page, perPage);
		}
		 
		@Override
		public IData<Note> getV3ProjectsIssuesNotesNote(String id, Integer noteableId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(noteableId);
			
			
			return entityClient.getV3ProjectsIssuesNotesNote(id, noteableId, page, perPage);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId, Integer noteId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId(awardId, id, mergeRequestId, noteId);
		}
		 
		@Override
		public IData<RepoLicense> getV3LicensesRepoLicense(Boolean popular){ 
			
			return entityClient.getV3LicensesRepoLicense(popular);
		}
		 
		@Override
		public IData<GroupDetail> getV3GroupsGroupDetailById(String id){ 
			Validate.notNull(id);
			return entityClient.getV3GroupsGroupDetailById(id);
		}
		 
		@Override
		public IData<NotificationSetting> getV3ProjectsNotification_settingsNotificationSetting(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsNotification_settingsNotificationSetting(id);
		}
		 
		@Override
		public IData<TemplatesList> getV3TemplatesDockerfilesTemplatesList(){ 
			return entityClient.getV3TemplatesDockerfilesTemplatesList();
		}
		 
		@Override
		public IData<PersonalSnippet> getV3SnippetsPersonalSnippet(Integer page, Integer perPage){ 
			
			
			return entityClient.getV3SnippetsPersonalSnippet(page, perPage);
		}
		 
		@Override
		public IData<SSHKey> getV3UsersKeysSSHKey(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3UsersKeysSSHKey(id);
		}
		 
		@Override
		public IData<TemplatesList> getV3TemplatesGitlab_ci_ymlsTemplatesList(){ 
			return entityClient.getV3TemplatesGitlab_ci_ymlsTemplatesList();
		}
		 
		@Override
		public IData<Build> getV3ProjectsBuildsBuildByBuildId(String id, Integer buildId){ 
			Validate.notNull(id);
			Validate.notNull(buildId);
			return entityClient.getV3ProjectsBuildsBuildByBuildId(id, buildId);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId, Integer noteId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(issueId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId(awardId, id, issueId, noteId);
		}
		 
		@Override
		public IData<AccessRequester> getV3GroupsAccess_requestsAccessRequester(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3GroupsAccess_requestsAccessRequester(id, page, perPage);
		}
		 
		@Override
		public IData<Variable> getV3ProjectsVariablesVariableByKey(String id, String key){ 
			Validate.notNull(id);
			Validate.notNull(key);
			return entityClient.getV3ProjectsVariablesVariableByKey(id, key);
		}
		 
		@Override
		public IData<Pipeline> getV3ProjectsPipelinesPipelineByPipelineId(String id, Integer pipelineId){ 
			Validate.notNull(id);
			Validate.notNull(pipelineId);
			return entityClient.getV3ProjectsPipelinesPipelineByPipelineId(id, pipelineId);
		}
		 
		@Override
		public IData<BasicProjectDetails> getV3ProjectsVisibleBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){ 
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			
			
			
			return entityClient.getV3ProjectsVisibleBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple);
		}
		 
		@Override
		public IData<BasicProjectDetails> getV3ProjectsBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){ 
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			
			
			
			return entityClient.getV3ProjectsBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple);
		}
		 
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){ 
			Validate.notNull(id);
			
			
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestCommentsMRNote(id, page, perPage, mergeRequestId);
		}
		 
		@Override
		public IData<Event> getV3ProjectsEventsEvent(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsEventsEvent(id, page, perPage);
		}
		 
		@Override
		public IData<Label> getV3ProjectsLabelsLabel(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsLabelsLabel(id);
		}
		 
		@Override
		public IData<Deployment> getV3ProjectsDeploymentsDeployment(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsDeploymentsDeployment(id, page, perPage);
		}
		 
		@Override
		public IData<Project> getV3ProjectsSearchProjectByQuery(String query, String orderBy, String sort, Integer page, Integer perPage){ 
			Validate.notNull(query);
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			
			return entityClient.getV3ProjectsSearchProjectByQuery(query, orderBy, sort, page, perPage);
		}
		 
		@Override
		public IData<Hook> getV3HooksHook(){ 
			return entityClient.getV3HooksHook();
		}
		 
		@Override
		public IData<ProjectHook> getV3ProjectsHooksProjectHookByHookId(String id, Integer hookId){ 
			Validate.notNull(id);
			Validate.notNull(hookId);
			return entityClient.getV3ProjectsHooksProjectHookByHookId(id, hookId);
		}
		 
		@Override
		public IData<Email> getV3UserEmailsEmailByEmailId(Integer emailId){ 
			Validate.notNull(emailId);
			return entityClient.getV3UserEmailsEmailByEmailId(emailId);
		}
		 
		@Override
		public IData<TemplatesList> getV3DockerfilesTemplatesList(){ 
			return entityClient.getV3DockerfilesTemplatesList();
		}
		 
		@Override
		public IData<Member> getV3GroupsMembersMemberByUserId(String id, Integer userId){ 
			Validate.notNull(id);
			Validate.notNull(userId);
			return entityClient.getV3GroupsMembersMemberByUserId(id, userId);
		}
		 
		@Override
		public IData<Runner> getV3RunnersAllRunner(String scope, Integer page, Integer perPage){ 
			if (scope != null) {
				Validate.matchesPattern(scope,"(active|paused|online|specific|shared)");
			}
			
			
			return entityClient.getV3RunnersAllRunner(scope, page, perPage);
		}
		 
		@Override
		public IData<Issue> getV3ProjectsMilestonesIssuesIssue(String id, Integer milestoneId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(milestoneId);
			
			
			return entityClient.getV3ProjectsMilestonesIssuesIssue(id, milestoneId, page, perPage);
		}
		 
		@Override
		public IData<Email> getV3UserEmailsEmail(){ 
			return entityClient.getV3UserEmailsEmail();
		}
		 
		@Override
		public IData<ApplicationSetting> getV3ApplicationSettingsApplicationSetting(){ 
			return entityClient.getV3ApplicationSettingsApplicationSetting();
		}
		 
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestsCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){ 
			Validate.notNull(id);
			
			
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsCommentsMRNote(id, page, perPage, mergeRequestId);
		}
		 
		@Override
		public IData<RepoTreeObject> getV3ProjectsRepositoryTreeRepoTreeObject(String id, String refName, String path, Boolean recursive){ 
			Validate.notNull(id);
			
			
			
			return entityClient.getV3ProjectsRepositoryTreeRepoTreeObject(id, refName, path, recursive);
		}
		 
		@Override
		public IData<Note> getV3ProjectsSnippetsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){ 
			Validate.notNull(id);
			Validate.notNull(noteId);
			Validate.notNull(noteableId);
			return entityClient.getV3ProjectsSnippetsNotesNoteByNoteId(id, noteId, noteableId);
		}
		 
		@Override
		public IData<ProjectHook> getV3ProjectsHooksProjectHook(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsHooksProjectHook(id, page, perPage);
		}
		 
		@Override
		public IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranch(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsRepositoryBranchesRepoBranch(id);
		}
		 
		@Override
		public IData<SSHKey> getV3UserKeysSSHKey(){ 
			return entityClient.getV3UserKeysSSHKey();
		}
		 
		@Override
		public IData<RepoLicense> getV3LicensesRepoLicenseByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3LicensesRepoLicenseByName(name);
		}
		 
		@Override
		public IData<MergeRequest> getV3ProjectsMerge_requestsMergeRequestByMergeRequestId(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsMergeRequestByMergeRequestId(id, mergeRequestId);
		}
		 
		@Override
		public IData<Build> getV3ProjectsBuildsBuild(String id, String scope, Integer page, Integer perPage){ 
			Validate.notNull(id);
			if (scope != null) {
				Validate.matchesPattern(scope,"(pending|running|failed|success|canceled)");
			}
			
			
			return entityClient.getV3ProjectsBuildsBuild(id, scope, page, perPage);
		}
		 
		@Override
		public IData<Email> getV3UsersEmailsEmail(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3UsersEmailsEmail(id);
		}
		 
		@Override
		public IData<PersonalSnippet> getV3SnippetsPersonalSnippetById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3SnippetsPersonalSnippetById(id);
		}
		 
		@Override
		public IData<Board> getV3ProjectsBoardsBoard(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsBoardsBoard(id);
		}
		 
		@Override
		public IData<Group> getV3GroupsOwnedGroup(Integer page, Integer perPage, Boolean statistics){ 
			
			
			
			return entityClient.getV3GroupsOwnedGroup(page, perPage, statistics);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer issueId, Integer noteId){ 
			
			
			Validate.notNull(id);
			Validate.notNull(issueId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsIssuesNotesAward_emojiAwardEmoji(page, perPage, id, issueId, noteId);
		}
		 
		@Override
		public IData<Template> getV3TemplatesDockerfilesTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3TemplatesDockerfilesTemplateByName(name);
		}
		 
		@Override
		public IData<MergeRequestDiffFull> getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId(String id, Integer mergeRequestId, Integer versionId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			Validate.notNull(versionId);
			return entityClient.getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId(id, mergeRequestId, versionId);
		}
		 
		@Override
		public IData<RepoCommit> getV3ProjectsMerge_requestsCommitsRepoCommit(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsCommitsRepoCommit(id, mergeRequestId);
		}
		 
		@Override
		public IData<MergeRequestChanges> getV3ProjectsMerge_requestsChangesMergeRequestChanges(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsChangesMergeRequestChanges(id, mergeRequestId);
		}
		 
		@Override
		public IData<Build> getV3ProjectsRepositoryCommitsBuildsBuild(String id, String sha, String scope, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(sha);
			if (scope != null) {
				Validate.matchesPattern(scope,"(pending|running|failed|success|canceled)");
			}
			
			
			return entityClient.getV3ProjectsRepositoryCommitsBuildsBuild(id, sha, scope, page, perPage);
		}
		 
		@Override
		public IData<RepoCommit> getV3ProjectsRepositoryCommitsRepoCommit(String id, String refName, String since, String until, Integer page, Integer perPage, String path){ 
			Validate.notNull(id);
			
			
			
			
			
			
			return entityClient.getV3ProjectsRepositoryCommitsRepoCommit(id, refName, since, until, page, perPage, path);
		}
		 
		@Override
		public IData<Variable> getV3ProjectsVariablesVariable(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsVariablesVariable(id, page, perPage);
		}
		 
		@Override
		public IData<Todo> getV3TodosTodo(Integer page, Integer perPage){ 
			
			
			return entityClient.getV3TodosTodo(page, perPage);
		}
		 
		@Override
		public IData<Note> getV3ProjectsIssuesNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){ 
			Validate.notNull(id);
			Validate.notNull(noteId);
			Validate.notNull(noteableId);
			return entityClient.getV3ProjectsIssuesNotesNoteByNoteId(id, noteId, noteableId);
		}
		 
		@Override
		public IData<Runner> getV3RunnersRunner(String scope, Integer page, Integer perPage){ 
			if (scope != null) {
				Validate.matchesPattern(scope,"(active|paused|online)");
			}
			
			
			return entityClient.getV3RunnersRunner(scope, page, perPage);
		}
		 
		@Override
		public IData<SSHKey> getV3UserKeysSSHKeyByKeyId(Integer keyId){ 
			Validate.notNull(keyId);
			return entityClient.getV3UserKeysSSHKeyByKeyId(keyId);
		}
		 
		@Override
		public IData<RepoLicense> getV3TemplatesLicensesRepoLicenseByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3TemplatesLicensesRepoLicenseByName(name);
		}
		 
		@Override
		public IData<UserBasic> getV3UsersUserBasicById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3UsersUserBasicById(id);
		}
		 
		@Override
		public IData<Issue> getV3ProjectsIssuesIssueByIssueId(String id, Integer issueId){ 
			Validate.notNull(id);
			Validate.notNull(issueId);
			return entityClient.getV3ProjectsIssuesIssueByIssueId(id, issueId);
		}
		 
		@Override
		public IData<CommitNote> getV3ProjectsRepositoryCommitsCommentsCommitNote(String id, Integer page, Integer perPage, String sha){ 
			Validate.notNull(id);
			
			
			Validate.notNull(sha);
			return entityClient.getV3ProjectsRepositoryCommitsCommentsCommitNote(id, page, perPage, sha);
		}
		 
		@Override
		public IData<MergeRequest> getV3ProjectsMerge_requestMergeRequestByMergeRequestId(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestMergeRequestByMergeRequestId(id, mergeRequestId);
		}
		 
		@Override
		public IData<Template> getV3Gitlab_ci_ymlsTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3Gitlab_ci_ymlsTemplateByName(name);
		}
		 
		@Override
		public IData<Template> getV3DockerfilesTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3DockerfilesTemplateByName(name);
		}
		 
		@Override
		public IData<Namespace> getV3NamespacesNamespace(String search, Integer page, Integer perPage){ 
			
			
			
			return entityClient.getV3NamespacesNamespace(search, page, perPage);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmoji(String id, Integer snippetId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(snippetId);
			
			
			return entityClient.getV3ProjectsSnippetsAward_emojiAwardEmoji(id, snippetId, page, perPage);
		}
		 
		@Override
		public IData<RepoTag> getV3ProjectsRepositoryTagsRepoTagByTagName(String id, String tagName){ 
			Validate.notNull(id);
			Validate.notNull(tagName);
			return entityClient.getV3ProjectsRepositoryTagsRepoTagByTagName(id, tagName);
		}
		 
		@Override
		public IData<UserPublic> getV3UserUserPublic(){ 
			return entityClient.getV3UserUserPublic();
		}
		 
		@Override
		public IData<TemplatesList> getV3GitignoresTemplatesList(){ 
			return entityClient.getV3GitignoresTemplatesList();
		}
		 
		@Override
		public IData<RepoCommit> getV3ProjectsMerge_requestCommitsRepoCommit(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestCommitsRepoCommit(id, mergeRequestId);
		}
		 
		@Override
		public IData<MergeRequestDiff> getV3ProjectsMerge_requestsVersionsMergeRequestDiff(String id, Integer mergeRequestId){ 
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsVersionsMergeRequestDiff(id, mergeRequestId);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(snippetId);
			return entityClient.getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId(awardId, id, snippetId);
		}
		 
		@Override
		public IData<Deployment> getV3ProjectsDeploymentsDeploymentByDeploymentId(String id, Integer deploymentId){ 
			Validate.notNull(id);
			Validate.notNull(deploymentId);
			return entityClient.getV3ProjectsDeploymentsDeploymentByDeploymentId(id, deploymentId);
		}
		 
		@Override
		public IData<CommitStatus> getV3ProjectsRepositoryCommitsStatusesCommitStatus(String id, String sha, String ref, String stage, String name, String all, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(sha);
			
			
			
			
			
			
			return entityClient.getV3ProjectsRepositoryCommitsStatusesCommitStatus(id, sha, ref, stage, name, all, page, perPage);
		}
		 
		@Override
		public IData<SSHKeyWithUser> getV3KeysSSHKeyWithUserById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3KeysSSHKeyWithUserById(id);
		}
		 
		@Override
		public IData<Trigger> getV3ProjectsTriggersTriggerByToken(String id, String token){ 
			Validate.notNull(id);
			Validate.notNull(token);
			return entityClient.getV3ProjectsTriggersTriggerByToken(id, token);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(issueId);
			return entityClient.getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId(awardId, id, issueId);
		}
		 
		@Override
		public IData<Hook> getV3HooksHookById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3HooksHookById(id);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId(awardId, id, mergeRequestId);
		}
		 
		@Override
		public IData<Event> getV3UsersEventsEvent(Integer id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3UsersEventsEvent(id, page, perPage);
		}
		 
		@Override
		public IData<Project> getV3GroupsProjectsProject(String id, Boolean archived, String visibility, String search, String orderBy, String sort, Boolean simple, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			
			
			return entityClient.getV3GroupsProjectsProject(id, archived, visibility, search, orderBy, sort, simple, page, perPage);
		}
		 
		@Override
		public IData<Template> getV3TemplatesGitignoresTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3TemplatesGitignoresTemplateByName(name);
		}
		 
		@Override
		public IData<SSHKey> getV3ProjectsKeysSSHKeyByKeyId(String id, Integer keyId){ 
			Validate.notNull(id);
			Validate.notNull(keyId);
			return entityClient.getV3ProjectsKeysSSHKeyByKeyId(id, keyId);
		}
		 
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestsCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){ 
			Validate.notNull(id);
			
			
			Validate.notNull(mergeRequestId);
			return entityClient.getV3ProjectsMerge_requestsCloses_issuesMRNote(id, page, perPage, mergeRequestId);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer snippetId, Integer noteId){ 
			
			
			Validate.notNull(id);
			Validate.notNull(snippetId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsSnippetsNotesAward_emojiAwardEmoji(page, perPage, id, snippetId, noteId);
		}
		 
		@Override
		public IData<BasicProjectDetails> getV3ProjectsOwnedBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics){ 
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			
			
			
			
			return entityClient.getV3ProjectsOwnedBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple, statistics);
		}
		 
		@Override
		public IData<ProjectService> getV3ProjectsServicesProjectServiceByServiceSlug(String serviceSlug, Integer id){ 
			Validate.notNull(serviceSlug);
			Validate.matchesPattern(serviceSlug,"(asana|assembla|bamboo|bugzilla|buildkite|builds-email|campfire|custom-issue-tracker|drone-ci|emails-on-push|external-wiki|flowdock|gemnasium|hipchat|irker|jira|kubernetes|mattermost-slash-commands|slack-slash-commands|pipelines-email|pivotaltracker|pushover|redmine|slack|mattermost|teamcity)");
			Validate.notNull(id);
			return entityClient.getV3ProjectsServicesProjectServiceByServiceSlug(serviceSlug, id);
		}
		 
		@Override
		public IData<Template> getV3TemplatesGitlab_ci_ymlsTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3TemplatesGitlab_ci_ymlsTemplateByName(name);
		}
		 
		@Override
		public IData<NotificationSetting> getV3GroupsNotification_settingsNotificationSetting(String id){ 
			Validate.notNull(id);
			return entityClient.getV3GroupsNotification_settingsNotificationSetting(id);
		}
		 
		@Override
		public IData<Note> getV3ProjectsMerge_requestsNotesNote(String id, Integer noteableId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(noteableId);
			
			
			return entityClient.getV3ProjectsMerge_requestsNotesNote(id, noteableId, page, perPage);
		}
		 
		@Override
		public IData<Trigger> getV3ProjectsTriggersTrigger(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsTriggersTrigger(id, page, perPage);
		}
		 
		@Override
		public IData<Member> getV3GroupsMembersMember(String id, String query, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			
			return entityClient.getV3GroupsMembersMember(id, query, page, perPage);
		}
		 
		@Override
		public IData<ProjectWithAccess> getV3ProjectsProjectWithAccessById(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsProjectWithAccessById(id);
		}
		 
		@Override
		public IData<RepoCommitDetail> getV3ProjectsRepositoryCommitsRepoCommitDetailBySha(String id, String sha){ 
			Validate.notNull(id);
			Validate.notNull(sha);
			return entityClient.getV3ProjectsRepositoryCommitsRepoCommitDetailBySha(id, sha);
		}
		 
		@Override
		public IData<Issue> getV3ProjectsIssuesIssue(String id, String state, Integer iid, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){ 
			Validate.notNull(id);
			if (state != null) {
				Validate.matchesPattern(state,"(opened|closed|all)");
			}
			
			
			
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(created_at|updated_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			
			return entityClient.getV3ProjectsIssuesIssue(id, state, iid, labels, milestone, orderBy, sort, page, perPage);
		}
		 
		@Override
		public IData<Compare> getV3ProjectsRepositoryCompare(String id, String from, String to){ 
			Validate.notNull(id);
			Validate.notNull(from);
			Validate.notNull(to);
			return entityClient.getV3ProjectsRepositoryCompare(id, from, to);
		}
		 
		@Override
		public IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranchByBranch(String id, String branch){ 
			Validate.notNull(id);
			Validate.notNull(branch);
			return entityClient.getV3ProjectsRepositoryBranchesRepoBranchByBranch(id, branch);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer mergeRequestId, Integer noteId){ 
			
			
			Validate.notNull(id);
			Validate.notNull(mergeRequestId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji(page, perPage, id, mergeRequestId, noteId);
		}
		 
		@Override
		public IData<PersonalSnippet> getV3SnippetsPublicPersonalSnippet(Integer page, Integer perPage){ 
			
			
			return entityClient.getV3SnippetsPublicPersonalSnippet(page, perPage);
		}
		 
		@Override
		public IData<Milestone> getV3ProjectsMilestonesMilestoneByMilestoneId(String id, Integer milestoneId){ 
			Validate.notNull(id);
			Validate.notNull(milestoneId);
			return entityClient.getV3ProjectsMilestonesMilestoneByMilestoneId(id, milestoneId);
		}
		 
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId, Integer noteId){ 
			Validate.notNull(awardId);
			Validate.notNull(id);
			Validate.notNull(snippetId);
			Validate.notNull(noteId);
			return entityClient.getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId(awardId, id, snippetId, noteId);
		}
		 
		@Override
		public IData<SSHKey> getV3ProjectsDeploy_keysSSHKey(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsDeploy_keysSSHKey(id);
		}
		 
		@Override
		public IData<Contributor> getV3ProjectsRepositoryContributorsContributor(String id){ 
			Validate.notNull(id);
			return entityClient.getV3ProjectsRepositoryContributorsContributor(id);
		}
		 
		@Override
		public IData<RunnerDetails> getV3RunnersRunnerDetailsById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getV3RunnersRunnerDetailsById(id);
		}
		 
		@Override
		public IData<SSHKey> getV3ProjectsDeploy_keysSSHKeyByKeyId(String id, Integer keyId){ 
			Validate.notNull(id);
			Validate.notNull(keyId);
			return entityClient.getV3ProjectsDeploy_keysSSHKeyByKeyId(id, keyId);
		}
		 
		@Override
		public IData<Issue> getV3IssuesIssue(String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){ 
			if (state != null) {
				Validate.matchesPattern(state,"(opened|closed|all)");
			}
			
			
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(created_at|updated_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			
			return entityClient.getV3IssuesIssue(state, labels, milestone, orderBy, sort, page, perPage);
		}
		 
		@Override
		public IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippetBySnippetId(String id, Integer snippetId){ 
			Validate.notNull(id);
			Validate.notNull(snippetId);
			return entityClient.getV3ProjectsSnippetsProjectSnippetBySnippetId(id, snippetId);
		}
		 
		@Override
		public IData<UserBasic> getV3ProjectsUsersUserBasic(String id, String search, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			
			return entityClient.getV3ProjectsUsersUserBasic(id, search, page, perPage);
		}
		 
		@Override
		public IData<Member> getV3ProjectsMembersMemberByUserId(String id, Integer userId){ 
			Validate.notNull(id);
			Validate.notNull(userId);
			return entityClient.getV3ProjectsMembersMemberByUserId(id, userId);
		}
		 
		@Override
		public IData<BasicProjectDetails> getV3ProjectsAllBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics){ 
			if (orderBy != null) {
				Validate.matchesPattern(orderBy,"(id|name|path|created_at|updated_at|last_activity_at)");
			}
			if (sort != null) {
				Validate.matchesPattern(sort,"(asc|desc)");
			}
			
			if (visibility != null) {
				Validate.matchesPattern(visibility,"(public|internal|private)");
			}
			
			
			
			
			
			return entityClient.getV3ProjectsAllBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple, statistics);
		}
		 
		@Override
		public IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippet(String id, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			return entityClient.getV3ProjectsSnippetsProjectSnippet(id, page, perPage);
		}
		 
		@Override
		public IData<Runner> getV3ProjectsRunnersRunner(String id, String scope, Integer page, Integer perPage){ 
			Validate.notNull(id);
			if (scope != null) {
				Validate.matchesPattern(scope,"(active|paused|online|specific|shared)");
			}
			
			
			return entityClient.getV3ProjectsRunnersRunner(id, scope, page, perPage);
		}
		 
		@Override
		public IData<Member> getV3ProjectsMembersMember(String id, String query, Integer page, Integer perPage){ 
			Validate.notNull(id);
			
			
			
			return entityClient.getV3ProjectsMembersMember(id, query, page, perPage);
		}
		 
		@Override
		public IData<Note> getV3ProjectsSnippetsNotesNote(String id, Integer noteableId, Integer page, Integer perPage){ 
			Validate.notNull(id);
			Validate.notNull(noteableId);
			
			
			return entityClient.getV3ProjectsSnippetsNotesNote(id, noteableId, page, perPage);
		}
		 
		@Override
		public IData<Template> getV3GitignoresTemplateByName(String name){ 
			Validate.notNull(name);
			return entityClient.getV3GitignoresTemplateByName(name);
		}
		 
		@Override
		public IData<Note> getV3ProjectsMerge_requestsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){ 
			Validate.notNull(id);
			Validate.notNull(noteId);
			Validate.notNull(noteableId);
			return entityClient.getV3ProjectsMerge_requestsNotesNoteByNoteId(id, noteId, noteableId);
		}
	}
}
