package org.eclipse.scava.crossflow.restmule.client.stackexchange.client;

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
import org.eclipse.scava.crossflow.restmule.client.stackexchange.cache.StackExchangeCacheManager;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.interceptor.StackExchangeInterceptor;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.*;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Error;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePaged;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;

import okhttp3.OkHttpClient.Builder;

public class EntityApi  {

	private static final Logger LOG = LogManager.getLogger(SearchApi.class);

	public static EntityBuilder create(){
		return new EntityBuilder(); 
	}
	
	public static IEntityApi createDefault(){ 
		return new EntityBuilder().setSession(StackExchangeSession.createPublic()).build(); 
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
		private StackExchangePagination paginationPolicy;
		
		EntityClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, StackExchangeSession.class, session.id());
			StackExchangeInterceptor interceptors = new StackExchangeInterceptor(session);
			String baseurl = StackExchangePropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/")) baseurl += "/"; // FIXME Validate in Model with EVL 

			Builder clientBuilder = AbstractClient.okHttp(executor);
			
			ICache localcache = new StackExchangeCacheManager().getCacheInstance();
			if (activeCaching && localcache != null && !localcache.isDistributed()) {
				clientBuilder = clientBuilder.cache(localcache.initializeLocal());
				LOG.info("enabling local okhttp cache");
			}
						
			clientBuilder = clientBuilder.addNetworkInterceptor(CacheControlInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR);
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));
						
			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(IEntityEndpoint.class);
			this.paginationPolicy = StackExchangePagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		@Override
		public IDataSet<ReputationResponse> getMeReputationReputationResponse(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<ReputationResponse, StackExchangePaged<ReputationResponse>, IEntityEndpoint> 
				traverse("getMeReputationReputationResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<TagSynonyms, StackExchangePaged<TagSynonyms>, IEntityEndpoint> 
				traverse("getTagsSynonymsTagSynonyms", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<User> getMeUser(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Data<User> data = new Data<User>();
			data.addElement(callbackEndpoint.getMeUser(order, max, min, sort, fromdate, todate, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsUnacceptedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersQuestionsUnacceptedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<NetworkUsers> getMeAssociatedNetworkUsers(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<NetworkUsers, StackExchangePaged<NetworkUsers>, IEntityEndpoint> 
				traverse("getMeAssociatedNetworkUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getMeInboxInboxItems(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getMeInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getMeInboxUnreadInboxItems(String filter, String callback, String site, Integer since){
			Class<?>[] types = { String.class, String.class, String.class, Integer.class};
			Object[] vals = { filter, callback, site, since};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getMeInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsFeaturedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeQuestionsFeaturedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getMePrivileges(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<Privileges, StackExchangePaged<Privileges>, IEntityEndpoint> 
				traverse("getMePrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getUsersAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getUsersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Posts> getPostsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Posts, StackExchangePaged<Posts>, IEntityEndpoint> 
				traverse("getPostsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsUnansweredQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersQuestionsUnansweredQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistoryResponse> getUsersReputation_historyFullReputationHistoryResponse(Integer id, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, filter, callback, site};
			return paginationPolicy.<ReputationHistoryResponse, StackExchangePaged<ReputationHistoryResponse>, IEntityEndpoint> 
				traverse("getUsersReputation_historyFullReputationHistoryResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsInfoTags(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getTagsInfoTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, StackExchangePaged<SuggestedEdits>, IEntityEndpoint> 
				traverse("getSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistoryResponse> getMeReputation_historyReputationHistoryResponse(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<ReputationHistoryResponse, StackExchangePaged<ReputationHistoryResponse>, IEntityEndpoint> 
				traverse("getMeReputation_historyReputationHistoryResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getUsersSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, StackExchangePaged<SuggestedEdits>, IEntityEndpoint> 
				traverse("getUsersSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<TopTagObjects> getMeTop_question_tagsTopTagObjects(String filter, String callback, String site){
			Data<TopTagObjects> data = new Data<TopTagObjects>();
			data.addElement(callbackEndpoint.getMeTop_question_tagsTopTagObjects(filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsQuestionsResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsQuestionsResponseByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsFeaturedQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsFeaturedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getPrivileges(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<Privileges, StackExchangePaged<Privileges>, IEntityEndpoint> 
				traverse("getPrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getCommentsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getCommentsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesTagsBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadgesTagsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getMeTagsTop_answersAnswers(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getMeTagsTop_answersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getMeMentionedComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getMeMentionedComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccountMerge> getMeMergesAccountMerge(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<AccountMerge, StackExchangePaged<AccountMerge>, IEntityEndpoint> 
				traverse("getMeMergesAccountMerge", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadgesRecipientsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Errors> getErrors(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<Errors, StackExchangePaged<Errors>, IEntityEndpoint> 
				traverse("getErrors", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getMeBadges(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getMeBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Events> getEvents(String filter, String callback, String site, Integer since){
			Class<?>[] types = { String.class, String.class, String.class, Integer.class};
			Object[] vals = { filter, callback, site, since};
			return paginationPolicy.<Events, StackExchangePaged<Events>, IEntityEndpoint> 
				traverse("getEvents", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<SingleFilter> getFiltersCreateSingleFilter(String base, String exclude, String include, Boolean unsafe){
			Data<SingleFilter> data = new Data<SingleFilter>();
			data.addElement(callbackEndpoint.getFiltersCreateSingleFilter(base, exclude, include, unsafe));
			return data;
		}
		
		@Override
		public IDataSet<UsersResponse> getUsersModeratorsUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<UsersResponse, StackExchangePaged<UsersResponse>, IEntityEndpoint> 
				traverse("getUsersModeratorsUsersResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getUsersInboxUnreadInboxItems(Integer id, String filter, String callback, String site, Integer since){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, Integer.class};
			Object[] vals = { id, filter, callback, site, since};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getUsersInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccountMerge> getUsersMergesAccountMerge(String ids, String filter, String callback){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { ids, filter, callback};
			return paginationPolicy.<AccountMerge, StackExchangePaged<AccountMerge>, IEntityEndpoint> 
				traverse("getUsersMergesAccountMerge", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<WritePermissions> getUsersWrite_permissionsWritePermissions(Integer id, String filter, String callback, String site){
			Data<WritePermissions> data = new Data<WritePermissions>();
			data.addElement(callbackEndpoint.getUsersWrite_permissionsWritePermissions(id, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<Comments> getMeCommentsByToId(Integer toId, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { toId, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getMeCommentsByToId", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<TopTagObjects> getMeTop_answer_tagsTopTagObjects(String filter, String callback, String site){
			Data<TopTagObjects> data = new Data<TopTagObjects>();
			data.addElement(callbackEndpoint.getMeTop_answer_tagsTopTagObjects(filter, callback, site));
			return data;
		}
		
		@Override
		public IData<TopTagObjects> getUsersTop_answer_tagsTopTagObjects(Integer id, String filter, String callback, String site){
			Data<TopTagObjects> data = new Data<TopTagObjects>();
			data.addElement(callbackEndpoint.getUsersTop_answer_tagsTopTagObjects(id, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersQuestionsQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesNameBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadgesNameBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Revisions> getPostsRevisions(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Revisions, StackExchangePaged<Revisions>, IEntityEndpoint> 
				traverse("getPostsRevisions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeTagsTop_questionsQuestionsResponse(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeTagsTop_questionsQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getMeComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getMeComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsNo_answersQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeQuestionsNo_answersQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getMeNotificationsUnreadNotifications(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getMeNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeQuestionsQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistoryResponse> getMeReputation_historyFullReputationHistoryResponse(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<ReputationHistoryResponse, StackExchangePaged<ReputationHistoryResponse>, IEntityEndpoint> 
				traverse("getMeReputation_historyFullReputationHistoryResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<WritePermissions> getMeWrite_permissionsWritePermissions(String filter, String callback, String site){
			Data<WritePermissions> data = new Data<WritePermissions>();
			data.addElement(callbackEndpoint.getMeWrite_permissionsWritePermissions(filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<Answers> getUsersTagsTop_answersAnswers(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getUsersTagsTop_answersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsRelatedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsRelatedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersMentionedComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getUsersMentionedComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getAnswersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getAnswersComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsModerator_onlyTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getTagsModerator_onlyTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getUsersComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsUnansweredQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeQuestionsUnansweredQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAccess_tokensInvalidateAccessTokens(String accessTokens, String filter, String callback){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { accessTokens, filter, callback};
			return paginationPolicy.<AccessTokens, StackExchangePaged<AccessTokens>, IEntityEndpoint> 
				traverse("getAccess_tokensInvalidateAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsRelatedTags(String tags, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class};
			Object[] vals = { tags, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getTagsRelatedTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getMeAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getMeAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getUsersTags(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getUsersTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getQuestionsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getQuestionsComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersFavoritesQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersFavoritesQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAccess_tokensAccessTokensByAccessTokens(String accessTokens, String filter, String callback){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { accessTokens, filter, callback};
			return paginationPolicy.<AccessTokens, StackExchangePaged<AccessTokens>, IEntityEndpoint> 
				traverse("getAccess_tokensAccessTokensByAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsUnacceptedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeQuestionsUnacceptedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Sites> getSites(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<Sites, StackExchangePaged<Sites>, IEntityEndpoint> 
				traverse("getSites", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<UserTimelineObjects> getMeTimelineUserTimelineObjects(Integer fromdate, Integer todate, String filter, String callback, String site){
			Data<UserTimelineObjects> data = new Data<UserTimelineObjects>();
			data.addElement(callbackEndpoint.getMeTimelineUserTimelineObjects(fromdate, todate, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<ReputationHistoryResponse> getUsersReputation_historyReputationHistoryResponse(String ids, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class};
			Object[] vals = { ids, filter, callback, site};
			return paginationPolicy.<ReputationHistoryResponse, StackExchangePaged<ReputationHistoryResponse>, IEntityEndpoint> 
				traverse("getUsersReputation_historyReputationHistoryResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getMeNotifications(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getMeNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getUsersInboxInboxItems(Integer id, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, filter, callback, site};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getUsersInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadgesByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsRequiredTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getTagsRequiredTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEditsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, StackExchangePaged<SuggestedEdits>, IEntityEndpoint> 
				traverse("getSuggested_editsSuggestedEditsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAppsDe_authenticateAccessTokens(String accessTokens, String filter, String callback){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { accessTokens, filter, callback};
			return paginationPolicy.<AccessTokens, StackExchangePaged<AccessTokens>, IEntityEndpoint> 
				traverse("getAppsDe_authenticateAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Posts> getPosts(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Posts, StackExchangePaged<Posts>, IEntityEndpoint> 
				traverse("getPosts", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getNotifications(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UsersResponse> getUsersUsersResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<UsersResponse, StackExchangePaged<UsersResponse>, IEntityEndpoint> 
				traverse("getUsersUsersResponseByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getInboxInboxItems(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<InfoObject> getInfoInfoObject(String site){
			Data<InfoObject> data = new Data<InfoObject>();
			data.addElement(callbackEndpoint.getInfoInfoObject(site));
			return data;
		}
		
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_answerersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { tag, period, filter, callback, site};
			return paginationPolicy.<TagScoreObjects, StackExchangePaged<TagScoreObjects>, IEntityEndpoint> 
				traverse("getTagsTop_answerersTagScoreObjectsByPeriod", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationResponse> getUsersReputationReputationResponse(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, filter, callback, site};
			return paginationPolicy.<ReputationResponse, StackExchangePaged<ReputationResponse>, IEntityEndpoint> 
				traverse("getUsersReputationReputationResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getTagsFaqQuestionsResponse(String tags, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class};
			Object[] vals = { tags, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getTagsFaqQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getBadgesRecipientsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getMeTags(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Tags, StackExchangePaged<Tags>, IEntityEndpoint> 
				traverse("getMeTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getUsersPrivileges(Integer id, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, filter, callback, site};
			return paginationPolicy.<Privileges, StackExchangePaged<Privileges>, IEntityEndpoint> 
				traverse("getUsersPrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_askersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { tag, period, filter, callback, site};
			return paginationPolicy.<TagScoreObjects, StackExchangePaged<TagScoreObjects>, IEntityEndpoint> 
				traverse("getTagsTop_askersTagScoreObjectsByPeriod", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getMeSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, StackExchangePaged<SuggestedEdits>, IEntityEndpoint> 
				traverse("getMeSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsFeaturedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersQuestionsFeaturedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getMeFavoritesQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getMeFavoritesQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getInboxUnreadInboxItems(String filter, String callback, Integer since){
			Class<?>[] types = { String.class, String.class, Integer.class};
			Object[] vals = { filter, callback, since};
			return paginationPolicy.<InboxItems, StackExchangePaged<InboxItems>, IEntityEndpoint> 
				traverse("getInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getQuestionsAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getQuestionsAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getUsersBadges(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Badges, StackExchangePaged<Badges>, IEntityEndpoint> 
				traverse("getUsersBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getAnswersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Answers, StackExchangePaged<Answers>, IEntityEndpoint> 
				traverse("getAnswersByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Filters> getFiltersByFilters(String filters){
			Class<?>[] types = { String.class};
			Object[] vals = { filters};
			return paginationPolicy.<Filters, StackExchangePaged<Filters>, IEntityEndpoint> 
				traverse("getFiltersByFilters", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getPostsSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, StackExchangePaged<SuggestedEdits>, IEntityEndpoint> 
				traverse("getPostsSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UsersResponse> getUsersModeratorsElectedUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<UsersResponse, StackExchangePaged<UsersResponse>, IEntityEndpoint> 
				traverse("getUsersModeratorsElectedUsersResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Revisions> getRevisionsByIds(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Revisions, StackExchangePaged<Revisions>, IEntityEndpoint> 
				traverse("getRevisionsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsLinkedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsLinkedQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Errordefinition> getErrorsErrordefinitionById(Integer id){
			Data<Errordefinition> data = new Data<Errordefinition>();
			data.addElement(callbackEndpoint.getErrorsErrordefinitionById(id));
			return data;
		}
		
		@Override
		public IDataSet<Comments> getPostsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getPostsComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<TagSynonyms, StackExchangePaged<TagSynonyms>, IEntityEndpoint> 
				traverse("getTagsSynonymsTagSynonyms", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getNotificationsUnreadNotifications(String filter, String callback){
			Class<?>[] types = { String.class, String.class};
			Object[] vals = { filter, callback};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersCommentsByToid(String ids, Integer toid, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, toid, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<Comments, StackExchangePaged<Comments>, IEntityEndpoint> 
				traverse("getUsersCommentsByToid", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagWikis> getTagsWikisTagWikis(String tags, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class};
			Object[] vals = { tags, filter, callback, site};
			return paginationPolicy.<TagWikis, StackExchangePaged<TagWikis>, IEntityEndpoint> 
				traverse("getTagsWikisTagWikis", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UsersResponse> getUsersUsersResponse(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<UsersResponse, StackExchangePaged<UsersResponse>, IEntityEndpoint> 
				traverse("getUsersUsersResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<UserTimelineObjects> getUsersTimelineUserTimelineObjects(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Data<UserTimelineObjects> data = new Data<UserTimelineObjects>();
			data.addElement(callbackEndpoint.getUsersTimelineUserTimelineObjects(ids, fromdate, todate, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<QuestionTimelineEvents> getQuestionsTimelineQuestionTimelineEvents(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionTimelineEvents, StackExchangePaged<QuestionTimelineEvents>, IEntityEndpoint> 
				traverse("getQuestionsTimelineQuestionTimelineEvents", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<NetworkUsers> getUsersAssociatedNetworkUsers(String ids, String filter, String callback){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { ids, filter, callback};
			return paginationPolicy.<NetworkUsers, StackExchangePaged<NetworkUsers>, IEntityEndpoint> 
				traverse("getUsersAssociatedNetworkUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsUnansweredQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsUnansweredQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersTagsTop_questionsQuestionsResponse(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, tags, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersTagsTop_questionsQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getUsersNotifications(Integer id, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, filter, callback, site};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getUsersNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<TopTagObjects> getUsersTop_question_tagsTopTagObjects(Integer id, String filter, String callback, String site){
			Data<TopTagObjects> data = new Data<TopTagObjects>();
			data.addElement(callbackEndpoint.getUsersTop_question_tagsTopTagObjects(id, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsNo_answersQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getUsersQuestionsNo_answersQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getUsersNotificationsUnreadNotifications(Integer id, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, filter, callback, site};
			return paginationPolicy.<Notifications, StackExchangePaged<Notifications>, IEntityEndpoint> 
				traverse("getUsersNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getQuestionsNo_answersQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, filter, callback, site};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getQuestionsNo_answersQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionsResponse> getSimilarQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String nottagged, String title){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, filter, callback, site, nottagged, title};
			return paginationPolicy.<QuestionsResponse, StackExchangePaged<QuestionsResponse>, IEntityEndpoint> 
				traverse("getSimilarQuestionsResponse", types, vals, callbackEndpoint);
		}
		
		
	}
}
