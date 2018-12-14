package org.eclipse.scava.crossflow.restmule.client.gitlab.client;

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
import org.eclipse.scava.crossflow.restmule.client.gitlab.cache.GitlabCacheManager;
import org.eclipse.scava.crossflow.restmule.client.gitlab.interceptor.GitlabInterceptor;
import org.eclipse.scava.crossflow.restmule.client.gitlab.model.*;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPaged;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPagination;
import org.eclipse.scava.crossflow.restmule.client.gitlab.session.GitlabSession;
import org.eclipse.scava.crossflow.restmule.client.gitlab.util.GitlabPropertiesUtil;

import okhttp3.OkHttpClient.Builder;

public class EntityApi  {

	private static final Logger LOG = LogManager.getLogger(SearchApi.class);

	public static EntityBuilder create(){
		return new EntityBuilder(); 
	}
	
	public static IEntityApi createDefault(){ 
		return new EntityBuilder().setSession(GitlabSession.createPublic()).build(); 
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
		private GitlabPagination paginationPolicy;
		
		EntityClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, GitlabSession.class, session.id());
			GitlabInterceptor interceptors = new GitlabInterceptor(session);
			String baseurl = GitlabPropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/")) baseurl += "/"; // FIXME Validate in Model with EVL 

			Builder clientBuilder = AbstractClient.okHttp(executor);
			
			ICache localcache = new GitlabCacheManager().getCacheInstance();
			if (activeCaching && localcache != null && !localcache.isDistributed()) {
				clientBuilder = clientBuilder.cache(localcache.initializeLocal());
				LOG.info("enabling local okhttp cache");
			}
						
			clientBuilder = clientBuilder.addNetworkInterceptor(CacheControlInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR);
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));
						
			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(IEntityEndpoint.class);
			this.paginationPolicy = GitlabPagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		@Override
		public IData<SSHKey> getV3ProjectsKeysSSHKey(String id){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3ProjectsKeysSSHKey(id));
			return data;
		}
		
		@Override
		public IData<RepoLicense> getV3TemplatesLicensesRepoLicense(Boolean popular){
			Data<RepoLicense> data = new Data<RepoLicense>();
			data.addElement(callbackEndpoint.getV3TemplatesLicensesRepoLicense(popular));
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3Gitlab_ci_ymlsTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3Gitlab_ci_ymlsTemplatesList());
			return data;
		}
		
		@Override
		public IData<BasicProjectDetails> getV3ProjectsStarredBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){
			Data<BasicProjectDetails> data = new Data<BasicProjectDetails>();
			data.addElement(callbackEndpoint.getV3ProjectsStarredBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmoji(String id, Integer mergeRequestId, Integer page, Integer perPage){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsAward_emojiAwardEmoji(id, mergeRequestId, page, perPage));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmoji(String id, Integer issueId, Integer page, Integer perPage){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesAward_emojiAwardEmoji(id, issueId, page, perPage));
			return data;
		}
		
		@Override
		public IData<MergeRequestChanges> getV3ProjectsMerge_requestChangesMergeRequestChanges(String id, Integer mergeRequestId){
			Data<MergeRequestChanges> data = new Data<MergeRequestChanges>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestChangesMergeRequestChanges(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<GlobalNotificationSetting> getV3Notification_settingsGlobalNotificationSetting(){
			Data<GlobalNotificationSetting> data = new Data<GlobalNotificationSetting>();
			data.addElement(callbackEndpoint.getV3Notification_settingsGlobalNotificationSetting());
			return data;
		}
		
		@Override
		public IData<Environment> getV3ProjectsEnvironmentsEnvironment(String id, Integer page, Integer perPage){
			Data<Environment> data = new Data<Environment>();
			data.addElement(callbackEndpoint.getV3ProjectsEnvironmentsEnvironment(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Pipeline> getV3ProjectsPipelinesPipeline(String id, Integer page, Integer perPage, String scope){
			Data<Pipeline> data = new Data<Pipeline>();
			data.addElement(callbackEndpoint.getV3ProjectsPipelinesPipeline(id, page, perPage, scope));
			return data;
		}
		
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){
			Data<MRNote> data = new Data<MRNote>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestCloses_issuesMRNote(id, page, perPage, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<RepoTag> getV3ProjectsRepositoryTagsRepoTag(String id){
			Data<RepoTag> data = new Data<RepoTag>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryTagsRepoTag(id));
			return data;
		}
		
		@Override
		public IData<Issue> getV3GroupsIssuesIssue(String id, String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getV3GroupsIssuesIssue(id, state, labels, milestone, orderBy, sort, page, perPage));
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3TemplatesGitignoresTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3TemplatesGitignoresTemplatesList());
			return data;
		}
		
		@Override
		public IData<UserBasic> getV3UsersUserBasic(String username, String search, Boolean active, Boolean external, Boolean blocked, Integer page, Integer perPage){
			Data<UserBasic> data = new Data<UserBasic>();
			data.addElement(callbackEndpoint.getV3UsersUserBasic(username, search, active, external, blocked, page, perPage));
			return data;
		}
		
		@Override
		public IData<AccessRequester> getV3ProjectsAccess_requestsAccessRequester(String id, Integer page, Integer perPage){
			Data<AccessRequester> data = new Data<AccessRequester>();
			data.addElement(callbackEndpoint.getV3ProjectsAccess_requestsAccessRequester(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsIssuesNotesNote(String id, Integer noteableId, Integer page, Integer perPage){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesNotesNote(id, noteableId, page, perPage));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsNotesAward_emojiAwardEmojiByAwardId(awardId, id, mergeRequestId, noteId));
			return data;
		}
		
		@Override
		public IData<RepoLicense> getV3LicensesRepoLicense(Boolean popular){
			Data<RepoLicense> data = new Data<RepoLicense>();
			data.addElement(callbackEndpoint.getV3LicensesRepoLicense(popular));
			return data;
		}
		
		@Override
		public IData<GroupDetail> getV3GroupsGroupDetailById(String id){
			Data<GroupDetail> data = new Data<GroupDetail>();
			data.addElement(callbackEndpoint.getV3GroupsGroupDetailById(id));
			return data;
		}
		
		@Override
		public IData<NotificationSetting> getV3ProjectsNotification_settingsNotificationSetting(String id){
			Data<NotificationSetting> data = new Data<NotificationSetting>();
			data.addElement(callbackEndpoint.getV3ProjectsNotification_settingsNotificationSetting(id));
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3TemplatesDockerfilesTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3TemplatesDockerfilesTemplatesList());
			return data;
		}
		
		@Override
		public IData<PersonalSnippet> getV3SnippetsPersonalSnippet(Integer page, Integer perPage){
			Data<PersonalSnippet> data = new Data<PersonalSnippet>();
			data.addElement(callbackEndpoint.getV3SnippetsPersonalSnippet(page, perPage));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3UsersKeysSSHKey(Integer id){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3UsersKeysSSHKey(id));
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3TemplatesGitlab_ci_ymlsTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3TemplatesGitlab_ci_ymlsTemplatesList());
			return data;
		}
		
		@Override
		public IData<Build> getV3ProjectsBuildsBuildByBuildId(String id, Integer buildId){
			Data<Build> data = new Data<Build>();
			data.addElement(callbackEndpoint.getV3ProjectsBuildsBuildByBuildId(id, buildId));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesNotesAward_emojiAwardEmojiByAwardId(awardId, id, issueId, noteId));
			return data;
		}
		
		@Override
		public IData<AccessRequester> getV3GroupsAccess_requestsAccessRequester(String id, Integer page, Integer perPage){
			Data<AccessRequester> data = new Data<AccessRequester>();
			data.addElement(callbackEndpoint.getV3GroupsAccess_requestsAccessRequester(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Variable> getV3ProjectsVariablesVariableByKey(String id, String key){
			Data<Variable> data = new Data<Variable>();
			data.addElement(callbackEndpoint.getV3ProjectsVariablesVariableByKey(id, key));
			return data;
		}
		
		@Override
		public IData<Pipeline> getV3ProjectsPipelinesPipelineByPipelineId(String id, Integer pipelineId){
			Data<Pipeline> data = new Data<Pipeline>();
			data.addElement(callbackEndpoint.getV3ProjectsPipelinesPipelineByPipelineId(id, pipelineId));
			return data;
		}
		
		@Override
		public IData<BasicProjectDetails> getV3ProjectsVisibleBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){
			Data<BasicProjectDetails> data = new Data<BasicProjectDetails>();
			data.addElement(callbackEndpoint.getV3ProjectsVisibleBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple));
			return data;
		}
		
		@Override
		public IData<BasicProjectDetails> getV3ProjectsBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple){
			Data<BasicProjectDetails> data = new Data<BasicProjectDetails>();
			data.addElement(callbackEndpoint.getV3ProjectsBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple));
			return data;
		}
		
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){
			Data<MRNote> data = new Data<MRNote>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestCommentsMRNote(id, page, perPage, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<Event> getV3ProjectsEventsEvent(String id, Integer page, Integer perPage){
			Data<Event> data = new Data<Event>();
			data.addElement(callbackEndpoint.getV3ProjectsEventsEvent(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Label> getV3ProjectsLabelsLabel(String id){
			Data<Label> data = new Data<Label>();
			data.addElement(callbackEndpoint.getV3ProjectsLabelsLabel(id));
			return data;
		}
		
		@Override
		public IData<Deployment> getV3ProjectsDeploymentsDeployment(String id, Integer page, Integer perPage){
			Data<Deployment> data = new Data<Deployment>();
			data.addElement(callbackEndpoint.getV3ProjectsDeploymentsDeployment(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Project> getV3ProjectsSearchProjectByQuery(String query, String orderBy, String sort, Integer page, Integer perPage){
			Data<Project> data = new Data<Project>();
			data.addElement(callbackEndpoint.getV3ProjectsSearchProjectByQuery(query, orderBy, sort, page, perPage));
			return data;
		}
		
		@Override
		public IData<Hook> getV3HooksHook(){
			Data<Hook> data = new Data<Hook>();
			data.addElement(callbackEndpoint.getV3HooksHook());
			return data;
		}
		
		@Override
		public IData<ProjectHook> getV3ProjectsHooksProjectHookByHookId(String id, Integer hookId){
			Data<ProjectHook> data = new Data<ProjectHook>();
			data.addElement(callbackEndpoint.getV3ProjectsHooksProjectHookByHookId(id, hookId));
			return data;
		}
		
		@Override
		public IData<Email> getV3UserEmailsEmailByEmailId(Integer emailId){
			Data<Email> data = new Data<Email>();
			data.addElement(callbackEndpoint.getV3UserEmailsEmailByEmailId(emailId));
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3DockerfilesTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3DockerfilesTemplatesList());
			return data;
		}
		
		@Override
		public IData<Member> getV3GroupsMembersMemberByUserId(String id, Integer userId){
			Data<Member> data = new Data<Member>();
			data.addElement(callbackEndpoint.getV3GroupsMembersMemberByUserId(id, userId));
			return data;
		}
		
		@Override
		public IData<Runner> getV3RunnersAllRunner(String scope, Integer page, Integer perPage){
			Data<Runner> data = new Data<Runner>();
			data.addElement(callbackEndpoint.getV3RunnersAllRunner(scope, page, perPage));
			return data;
		}
		
		@Override
		public IData<Issue> getV3ProjectsMilestonesIssuesIssue(String id, Integer milestoneId, Integer page, Integer perPage){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getV3ProjectsMilestonesIssuesIssue(id, milestoneId, page, perPage));
			return data;
		}
		
		@Override
		public IData<Email> getV3UserEmailsEmail(){
			Data<Email> data = new Data<Email>();
			data.addElement(callbackEndpoint.getV3UserEmailsEmail());
			return data;
		}
		
		@Override
		public IData<ApplicationSetting> getV3ApplicationSettingsApplicationSetting(){
			Data<ApplicationSetting> data = new Data<ApplicationSetting>();
			data.addElement(callbackEndpoint.getV3ApplicationSettingsApplicationSetting());
			return data;
		}
		
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestsCommentsMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){
			Data<MRNote> data = new Data<MRNote>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsCommentsMRNote(id, page, perPage, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<RepoTreeObject> getV3ProjectsRepositoryTreeRepoTreeObject(String id, String refName, String path, Boolean recursive){
			Data<RepoTreeObject> data = new Data<RepoTreeObject>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryTreeRepoTreeObject(id, refName, path, recursive));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsSnippetsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsNotesNoteByNoteId(id, noteId, noteableId));
			return data;
		}
		
		@Override
		public IData<ProjectHook> getV3ProjectsHooksProjectHook(String id, Integer page, Integer perPage){
			Data<ProjectHook> data = new Data<ProjectHook>();
			data.addElement(callbackEndpoint.getV3ProjectsHooksProjectHook(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranch(String id){
			Data<RepoBranch> data = new Data<RepoBranch>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryBranchesRepoBranch(id));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3UserKeysSSHKey(){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3UserKeysSSHKey());
			return data;
		}
		
		@Override
		public IData<RepoLicense> getV3LicensesRepoLicenseByName(String name){
			Data<RepoLicense> data = new Data<RepoLicense>();
			data.addElement(callbackEndpoint.getV3LicensesRepoLicenseByName(name));
			return data;
		}
		
		@Override
		public IData<MergeRequest> getV3ProjectsMerge_requestsMergeRequestByMergeRequestId(String id, Integer mergeRequestId){
			Data<MergeRequest> data = new Data<MergeRequest>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsMergeRequestByMergeRequestId(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<Build> getV3ProjectsBuildsBuild(String id, String scope, Integer page, Integer perPage){
			Data<Build> data = new Data<Build>();
			data.addElement(callbackEndpoint.getV3ProjectsBuildsBuild(id, scope, page, perPage));
			return data;
		}
		
		@Override
		public IData<Email> getV3UsersEmailsEmail(Integer id){
			Data<Email> data = new Data<Email>();
			data.addElement(callbackEndpoint.getV3UsersEmailsEmail(id));
			return data;
		}
		
		@Override
		public IData<PersonalSnippet> getV3SnippetsPersonalSnippetById(Integer id){
			Data<PersonalSnippet> data = new Data<PersonalSnippet>();
			data.addElement(callbackEndpoint.getV3SnippetsPersonalSnippetById(id));
			return data;
		}
		
		@Override
		public IData<Board> getV3ProjectsBoardsBoard(String id){
			Data<Board> data = new Data<Board>();
			data.addElement(callbackEndpoint.getV3ProjectsBoardsBoard(id));
			return data;
		}
		
		@Override
		public IData<Group> getV3GroupsOwnedGroup(Integer page, Integer perPage, Boolean statistics){
			Data<Group> data = new Data<Group>();
			data.addElement(callbackEndpoint.getV3GroupsOwnedGroup(page, perPage, statistics));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer issueId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesNotesAward_emojiAwardEmoji(page, perPage, id, issueId, noteId));
			return data;
		}
		
		@Override
		public IData<Template> getV3TemplatesDockerfilesTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3TemplatesDockerfilesTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<MergeRequestDiffFull> getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId(String id, Integer mergeRequestId, Integer versionId){
			Data<MergeRequestDiffFull> data = new Data<MergeRequestDiffFull>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsVersionsMergeRequestDiffFullByVersionId(id, mergeRequestId, versionId));
			return data;
		}
		
		@Override
		public IData<RepoCommit> getV3ProjectsMerge_requestsCommitsRepoCommit(String id, Integer mergeRequestId){
			Data<RepoCommit> data = new Data<RepoCommit>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsCommitsRepoCommit(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<MergeRequestChanges> getV3ProjectsMerge_requestsChangesMergeRequestChanges(String id, Integer mergeRequestId){
			Data<MergeRequestChanges> data = new Data<MergeRequestChanges>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsChangesMergeRequestChanges(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<Build> getV3ProjectsRepositoryCommitsBuildsBuild(String id, String sha, String scope, Integer page, Integer perPage){
			Data<Build> data = new Data<Build>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCommitsBuildsBuild(id, sha, scope, page, perPage));
			return data;
		}
		
		@Override
		public IData<RepoCommit> getV3ProjectsRepositoryCommitsRepoCommit(String id, String refName, String since, String until, Integer page, Integer perPage, String path){
			Data<RepoCommit> data = new Data<RepoCommit>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCommitsRepoCommit(id, refName, since, until, page, perPage, path));
			return data;
		}
		
		@Override
		public IData<Variable> getV3ProjectsVariablesVariable(String id, Integer page, Integer perPage){
			Data<Variable> data = new Data<Variable>();
			data.addElement(callbackEndpoint.getV3ProjectsVariablesVariable(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Todo> getV3TodosTodo(Integer page, Integer perPage){
			Data<Todo> data = new Data<Todo>();
			data.addElement(callbackEndpoint.getV3TodosTodo(page, perPage));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsIssuesNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesNotesNoteByNoteId(id, noteId, noteableId));
			return data;
		}
		
		@Override
		public IData<Runner> getV3RunnersRunner(String scope, Integer page, Integer perPage){
			Data<Runner> data = new Data<Runner>();
			data.addElement(callbackEndpoint.getV3RunnersRunner(scope, page, perPage));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3UserKeysSSHKeyByKeyId(Integer keyId){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3UserKeysSSHKeyByKeyId(keyId));
			return data;
		}
		
		@Override
		public IData<RepoLicense> getV3TemplatesLicensesRepoLicenseByName(String name){
			Data<RepoLicense> data = new Data<RepoLicense>();
			data.addElement(callbackEndpoint.getV3TemplatesLicensesRepoLicenseByName(name));
			return data;
		}
		
		@Override
		public IData<UserBasic> getV3UsersUserBasicById(Integer id){
			Data<UserBasic> data = new Data<UserBasic>();
			data.addElement(callbackEndpoint.getV3UsersUserBasicById(id));
			return data;
		}
		
		@Override
		public IData<Issue> getV3ProjectsIssuesIssueByIssueId(String id, Integer issueId){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesIssueByIssueId(id, issueId));
			return data;
		}
		
		@Override
		public IData<CommitNote> getV3ProjectsRepositoryCommitsCommentsCommitNote(String id, Integer page, Integer perPage, String sha){
			Data<CommitNote> data = new Data<CommitNote>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCommitsCommentsCommitNote(id, page, perPage, sha));
			return data;
		}
		
		@Override
		public IData<MergeRequest> getV3ProjectsMerge_requestMergeRequestByMergeRequestId(String id, Integer mergeRequestId){
			Data<MergeRequest> data = new Data<MergeRequest>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestMergeRequestByMergeRequestId(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<Template> getV3Gitlab_ci_ymlsTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3Gitlab_ci_ymlsTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<Template> getV3DockerfilesTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3DockerfilesTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<Namespace> getV3NamespacesNamespace(String search, Integer page, Integer perPage){
			Data<Namespace> data = new Data<Namespace>();
			data.addElement(callbackEndpoint.getV3NamespacesNamespace(search, page, perPage));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmoji(String id, Integer snippetId, Integer page, Integer perPage){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsAward_emojiAwardEmoji(id, snippetId, page, perPage));
			return data;
		}
		
		@Override
		public IData<RepoTag> getV3ProjectsRepositoryTagsRepoTagByTagName(String id, String tagName){
			Data<RepoTag> data = new Data<RepoTag>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryTagsRepoTagByTagName(id, tagName));
			return data;
		}
		
		@Override
		public IData<UserPublic> getV3UserUserPublic(){
			Data<UserPublic> data = new Data<UserPublic>();
			data.addElement(callbackEndpoint.getV3UserUserPublic());
			return data;
		}
		
		@Override
		public IData<TemplatesList> getV3GitignoresTemplatesList(){
			Data<TemplatesList> data = new Data<TemplatesList>();
			data.addElement(callbackEndpoint.getV3GitignoresTemplatesList());
			return data;
		}
		
		@Override
		public IData<RepoCommit> getV3ProjectsMerge_requestCommitsRepoCommit(String id, Integer mergeRequestId){
			Data<RepoCommit> data = new Data<RepoCommit>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestCommitsRepoCommit(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<MergeRequestDiff> getV3ProjectsMerge_requestsVersionsMergeRequestDiff(String id, Integer mergeRequestId){
			Data<MergeRequestDiff> data = new Data<MergeRequestDiff>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsVersionsMergeRequestDiff(id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsAward_emojiAwardEmojiByAwardId(awardId, id, snippetId));
			return data;
		}
		
		@Override
		public IData<Deployment> getV3ProjectsDeploymentsDeploymentByDeploymentId(String id, Integer deploymentId){
			Data<Deployment> data = new Data<Deployment>();
			data.addElement(callbackEndpoint.getV3ProjectsDeploymentsDeploymentByDeploymentId(id, deploymentId));
			return data;
		}
		
		@Override
		public IData<CommitStatus> getV3ProjectsRepositoryCommitsStatusesCommitStatus(String id, String sha, String ref, String stage, String name, String all, Integer page, Integer perPage){
			Data<CommitStatus> data = new Data<CommitStatus>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCommitsStatusesCommitStatus(id, sha, ref, stage, name, all, page, perPage));
			return data;
		}
		
		@Override
		public IData<SSHKeyWithUser> getV3KeysSSHKeyWithUserById(Integer id){
			Data<SSHKeyWithUser> data = new Data<SSHKeyWithUser>();
			data.addElement(callbackEndpoint.getV3KeysSSHKeyWithUserById(id));
			return data;
		}
		
		@Override
		public IData<Trigger> getV3ProjectsTriggersTriggerByToken(String id, String token){
			Data<Trigger> data = new Data<Trigger>();
			data.addElement(callbackEndpoint.getV3ProjectsTriggersTriggerByToken(id, token));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer issueId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesAward_emojiAwardEmojiByAwardId(awardId, id, issueId));
			return data;
		}
		
		@Override
		public IData<Hook> getV3HooksHookById(Integer id){
			Data<Hook> data = new Data<Hook>();
			data.addElement(callbackEndpoint.getV3HooksHookById(id));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer mergeRequestId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsAward_emojiAwardEmojiByAwardId(awardId, id, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<Event> getV3UsersEventsEvent(Integer id, Integer page, Integer perPage){
			Data<Event> data = new Data<Event>();
			data.addElement(callbackEndpoint.getV3UsersEventsEvent(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Project> getV3GroupsProjectsProject(String id, Boolean archived, String visibility, String search, String orderBy, String sort, Boolean simple, Integer page, Integer perPage){
			Data<Project> data = new Data<Project>();
			data.addElement(callbackEndpoint.getV3GroupsProjectsProject(id, archived, visibility, search, orderBy, sort, simple, page, perPage));
			return data;
		}
		
		@Override
		public IData<Template> getV3TemplatesGitignoresTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3TemplatesGitignoresTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3ProjectsKeysSSHKeyByKeyId(String id, Integer keyId){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3ProjectsKeysSSHKeyByKeyId(id, keyId));
			return data;
		}
		
		@Override
		public IData<MRNote> getV3ProjectsMerge_requestsCloses_issuesMRNote(String id, Integer page, Integer perPage, Integer mergeRequestId){
			Data<MRNote> data = new Data<MRNote>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsCloses_issuesMRNote(id, page, perPage, mergeRequestId));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer snippetId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsNotesAward_emojiAwardEmoji(page, perPage, id, snippetId, noteId));
			return data;
		}
		
		@Override
		public IData<BasicProjectDetails> getV3ProjectsOwnedBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics){
			Data<BasicProjectDetails> data = new Data<BasicProjectDetails>();
			data.addElement(callbackEndpoint.getV3ProjectsOwnedBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple, statistics));
			return data;
		}
		
		@Override
		public IData<ProjectService> getV3ProjectsServicesProjectServiceByServiceSlug(String serviceSlug, Integer id){
			Data<ProjectService> data = new Data<ProjectService>();
			data.addElement(callbackEndpoint.getV3ProjectsServicesProjectServiceByServiceSlug(serviceSlug, id));
			return data;
		}
		
		@Override
		public IData<Template> getV3TemplatesGitlab_ci_ymlsTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3TemplatesGitlab_ci_ymlsTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<NotificationSetting> getV3GroupsNotification_settingsNotificationSetting(String id){
			Data<NotificationSetting> data = new Data<NotificationSetting>();
			data.addElement(callbackEndpoint.getV3GroupsNotification_settingsNotificationSetting(id));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsMerge_requestsNotesNote(String id, Integer noteableId, Integer page, Integer perPage){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsNotesNote(id, noteableId, page, perPage));
			return data;
		}
		
		@Override
		public IData<Trigger> getV3ProjectsTriggersTrigger(String id, Integer page, Integer perPage){
			Data<Trigger> data = new Data<Trigger>();
			data.addElement(callbackEndpoint.getV3ProjectsTriggersTrigger(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Member> getV3GroupsMembersMember(String id, String query, Integer page, Integer perPage){
			Data<Member> data = new Data<Member>();
			data.addElement(callbackEndpoint.getV3GroupsMembersMember(id, query, page, perPage));
			return data;
		}
		
		@Override
		public IData<ProjectWithAccess> getV3ProjectsProjectWithAccessById(String id){
			Data<ProjectWithAccess> data = new Data<ProjectWithAccess>();
			data.addElement(callbackEndpoint.getV3ProjectsProjectWithAccessById(id));
			return data;
		}
		
		@Override
		public IData<RepoCommitDetail> getV3ProjectsRepositoryCommitsRepoCommitDetailBySha(String id, String sha){
			Data<RepoCommitDetail> data = new Data<RepoCommitDetail>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCommitsRepoCommitDetailBySha(id, sha));
			return data;
		}
		
		@Override
		public IData<Issue> getV3ProjectsIssuesIssue(String id, String state, Integer iid, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getV3ProjectsIssuesIssue(id, state, iid, labels, milestone, orderBy, sort, page, perPage));
			return data;
		}
		
		@Override
		public IData<Compare> getV3ProjectsRepositoryCompare(String id, String from, String to){
			Data<Compare> data = new Data<Compare>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryCompare(id, from, to));
			return data;
		}
		
		@Override
		public IData<RepoBranch> getV3ProjectsRepositoryBranchesRepoBranchByBranch(String id, String branch){
			Data<RepoBranch> data = new Data<RepoBranch>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryBranchesRepoBranchByBranch(id, branch));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji(Integer page, Integer perPage, Integer id, Integer mergeRequestId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsNotesAward_emojiAwardEmoji(page, perPage, id, mergeRequestId, noteId));
			return data;
		}
		
		@Override
		public IData<PersonalSnippet> getV3SnippetsPublicPersonalSnippet(Integer page, Integer perPage){
			Data<PersonalSnippet> data = new Data<PersonalSnippet>();
			data.addElement(callbackEndpoint.getV3SnippetsPublicPersonalSnippet(page, perPage));
			return data;
		}
		
		@Override
		public IData<Milestone> getV3ProjectsMilestonesMilestoneByMilestoneId(String id, Integer milestoneId){
			Data<Milestone> data = new Data<Milestone>();
			data.addElement(callbackEndpoint.getV3ProjectsMilestonesMilestoneByMilestoneId(id, milestoneId));
			return data;
		}
		
		@Override
		public IData<AwardEmoji> getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId(Integer awardId, Integer id, Integer snippetId, Integer noteId){
			Data<AwardEmoji> data = new Data<AwardEmoji>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsNotesAward_emojiAwardEmojiByAwardId(awardId, id, snippetId, noteId));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3ProjectsDeploy_keysSSHKey(String id){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3ProjectsDeploy_keysSSHKey(id));
			return data;
		}
		
		@Override
		public IData<Contributor> getV3ProjectsRepositoryContributorsContributor(String id){
			Data<Contributor> data = new Data<Contributor>();
			data.addElement(callbackEndpoint.getV3ProjectsRepositoryContributorsContributor(id));
			return data;
		}
		
		@Override
		public IData<RunnerDetails> getV3RunnersRunnerDetailsById(Integer id){
			Data<RunnerDetails> data = new Data<RunnerDetails>();
			data.addElement(callbackEndpoint.getV3RunnersRunnerDetailsById(id));
			return data;
		}
		
		@Override
		public IData<SSHKey> getV3ProjectsDeploy_keysSSHKeyByKeyId(String id, Integer keyId){
			Data<SSHKey> data = new Data<SSHKey>();
			data.addElement(callbackEndpoint.getV3ProjectsDeploy_keysSSHKeyByKeyId(id, keyId));
			return data;
		}
		
		@Override
		public IData<Issue> getV3IssuesIssue(String state, String labels, String milestone, String orderBy, String sort, Integer page, Integer perPage){
			Data<Issue> data = new Data<Issue>();
			data.addElement(callbackEndpoint.getV3IssuesIssue(state, labels, milestone, orderBy, sort, page, perPage));
			return data;
		}
		
		@Override
		public IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippetBySnippetId(String id, Integer snippetId){
			Data<ProjectSnippet> data = new Data<ProjectSnippet>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsProjectSnippetBySnippetId(id, snippetId));
			return data;
		}
		
		@Override
		public IData<UserBasic> getV3ProjectsUsersUserBasic(String id, String search, Integer page, Integer perPage){
			Data<UserBasic> data = new Data<UserBasic>();
			data.addElement(callbackEndpoint.getV3ProjectsUsersUserBasic(id, search, page, perPage));
			return data;
		}
		
		@Override
		public IData<Member> getV3ProjectsMembersMemberByUserId(String id, Integer userId){
			Data<Member> data = new Data<Member>();
			data.addElement(callbackEndpoint.getV3ProjectsMembersMemberByUserId(id, userId));
			return data;
		}
		
		@Override
		public IData<BasicProjectDetails> getV3ProjectsAllBasicProjectDetails(String orderBy, String sort, Boolean archived, String visibility, String search, Integer page, Integer perPage, Boolean simple, Boolean statistics){
			Data<BasicProjectDetails> data = new Data<BasicProjectDetails>();
			data.addElement(callbackEndpoint.getV3ProjectsAllBasicProjectDetails(orderBy, sort, archived, visibility, search, page, perPage, simple, statistics));
			return data;
		}
		
		@Override
		public IData<ProjectSnippet> getV3ProjectsSnippetsProjectSnippet(String id, Integer page, Integer perPage){
			Data<ProjectSnippet> data = new Data<ProjectSnippet>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsProjectSnippet(id, page, perPage));
			return data;
		}
		
		@Override
		public IData<Runner> getV3ProjectsRunnersRunner(String id, String scope, Integer page, Integer perPage){
			Data<Runner> data = new Data<Runner>();
			data.addElement(callbackEndpoint.getV3ProjectsRunnersRunner(id, scope, page, perPage));
			return data;
		}
		
		@Override
		public IData<Member> getV3ProjectsMembersMember(String id, String query, Integer page, Integer perPage){
			Data<Member> data = new Data<Member>();
			data.addElement(callbackEndpoint.getV3ProjectsMembersMember(id, query, page, perPage));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsSnippetsNotesNote(String id, Integer noteableId, Integer page, Integer perPage){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsSnippetsNotesNote(id, noteableId, page, perPage));
			return data;
		}
		
		@Override
		public IData<Template> getV3GitignoresTemplateByName(String name){
			Data<Template> data = new Data<Template>();
			data.addElement(callbackEndpoint.getV3GitignoresTemplateByName(name));
			return data;
		}
		
		@Override
		public IData<Note> getV3ProjectsMerge_requestsNotesNoteByNoteId(String id, Integer noteId, Integer noteableId){
			Data<Note> data = new Data<Note>();
			data.addElement(callbackEndpoint.getV3ProjectsMerge_requestsNotesNoteByNoteId(id, noteId, noteableId));
			return data;
		}
		
		
	}
}
