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
		public IDataSet<ReputationChanges> getMeReputationReputationChanges(String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class};
			Object[] vals = { filter, callback, site};
			return paginationPolicy.<ReputationChanges, IEntityEndpoint> 
				traverseList("getMeReputationReputationChanges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<TagSynonyms, IEntityEndpoint> 
				traverseList("getTagsSynonymsTagSynonyms", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<User> getMeUser(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Data<User> data = new Data<User>();
			data.addElement(callbackEndpoint.getMeUser(order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site));
			return data;
		}
		
		@Override
		public IDataSet<Questions> getUsersQuestionsUnacceptedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersQuestionsUnacceptedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<NetworkUsers> getMeAssociatedNetworkUsers(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<NetworkUsers, IEntityEndpoint> 
				traverseList("getMeAssociatedNetworkUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getMeInboxInboxItems(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getMeInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getMeInboxUnreadInboxItems(Integer pagesize, Integer page, String filter, String callback, String site, Integer since){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class, Integer.class};
			Object[] vals = { pagesize, page, filter, callback, site, since};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getMeInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeQuestionsFeaturedQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeQuestionsFeaturedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getMePrivileges(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<Privileges, IEntityEndpoint> 
				traverseList("getMePrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getUsersAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getUsersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Posts> getPostsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Posts, IEntityEndpoint> 
				traverseList("getPostsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersQuestionsUnansweredQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersQuestionsUnansweredQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistory> getUsersReputation_historyFullReputationHistory(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<ReputationHistory, IEntityEndpoint> 
				traverseList("getUsersReputation_historyFullReputationHistory", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsInfoTags(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getTagsInfoTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, IEntityEndpoint> 
				traverseList("getSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistory> getMeReputation_historyReputationHistory(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<ReputationHistory, IEntityEndpoint> 
				traverseList("getMeReputation_historyReputationHistory", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getUsersSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, IEntityEndpoint> 
				traverseList("getUsersSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TopTagObjects> getMeTop_question_tagsTopTagObjects(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<TopTagObjects, IEntityEndpoint> 
				traverseList("getMeTop_question_tagsTopTagObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsFeaturedQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsFeaturedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getPrivileges(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<Privileges, IEntityEndpoint> 
				traverseList("getPrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getCommentsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getCommentsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesTagsBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadgesTagsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getMeTagsTop_answersAnswers(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getMeTagsTop_answersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getMeMentionedComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getMeMentionedComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccountMerge> getMeMergesAccountMerge(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<AccountMerge, IEntityEndpoint> 
				traverseList("getMeMergesAccountMerge", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadgesRecipientsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Errors> getErrors(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<Errors, IEntityEndpoint> 
				traverseList("getErrors", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getMeBadges(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getMeBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Events> getEvents(Integer pagesize, Integer page, String filter, String callback, String site, Integer since){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class, Integer.class};
			Object[] vals = { pagesize, page, filter, callback, site, since};
			return paginationPolicy.<Events, IEntityEndpoint> 
				traverseList("getEvents", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<SingleFilter> getFiltersCreateSingleFilter(String base, String exclude, String include, Boolean unsafe){
			Data<SingleFilter> data = new Data<SingleFilter>();
			data.addElement(callbackEndpoint.getFiltersCreateSingleFilter(base, exclude, include, unsafe));
			return data;
		}
		
		@Override
		public IDataSet<Users> getUsersModeratorsUsers(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsersModeratorsUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getUsersInboxUnreadInboxItems(Integer id, Integer pagesize, Integer page, String filter, String callback, String site, Integer since){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, Integer.class};
			Object[] vals = { id, pagesize, page, filter, callback, site, since};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getUsersInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccountMerge> getUsersMergesAccountMerge(String ids, Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { ids, pagesize, page, filter, callback};
			return paginationPolicy.<AccountMerge, IEntityEndpoint> 
				traverseList("getUsersMergesAccountMerge", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<WritePermissions> getUsersWrite_permissionsWritePermissions(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<WritePermissions, IEntityEndpoint> 
				traverseList("getUsersWrite_permissionsWritePermissions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getMeCommentsByToId(Integer toId, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { toId, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getMeCommentsByToId", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TopTagObjects> getMeTop_answer_tagsTopTagObjects(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<TopTagObjects, IEntityEndpoint> 
				traverseList("getMeTop_answer_tagsTopTagObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TopTagObjects> getUsersTop_answer_tagsTopTagObjects(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<TopTagObjects, IEntityEndpoint> 
				traverseList("getUsersTop_answer_tagsTopTagObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesNameBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadgesNameBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Revisions> getPostsRevisions(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Revisions, IEntityEndpoint> 
				traverseList("getPostsRevisions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeTagsTop_questionsQuestions(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeTagsTop_questionsQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getMeComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getMeComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeQuestionsNo_answersQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeQuestionsNo_answersQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getMeNotificationsUnreadNotifications(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getMeNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistory> getMeReputation_historyFullReputationHistory(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<ReputationHistory, IEntityEndpoint> 
				traverseList("getMeReputation_historyFullReputationHistory", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<WritePermissions> getMeWrite_permissionsWritePermissions(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<WritePermissions, IEntityEndpoint> 
				traverseList("getMeWrite_permissionsWritePermissions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getUsersTagsTop_answersAnswers(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getUsersTagsTop_answersAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsRelatedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsRelatedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersMentionedComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getUsersMentionedComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getAnswersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getAnswersComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsModerator_onlyTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getTagsModerator_onlyTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getUsersComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeQuestionsUnansweredQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeQuestionsUnansweredQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAccess_tokensInvalidateAccessTokens(String accessTokens, Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { accessTokens, pagesize, page, filter, callback};
			return paginationPolicy.<AccessTokens, IEntityEndpoint> 
				traverseList("getAccess_tokensInvalidateAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsRelatedTags(String tags, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getTagsRelatedTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getMeAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getMeAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getUsersTags(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getUsersTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getQuestionsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getQuestionsComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersFavoritesQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersFavoritesQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAccess_tokensAccessTokensByAccessTokens(String accessTokens, Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { accessTokens, pagesize, page, filter, callback};
			return paginationPolicy.<AccessTokens, IEntityEndpoint> 
				traverseList("getAccess_tokensAccessTokensByAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeQuestionsUnacceptedQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeQuestionsUnacceptedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Sites> getSites(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<Sites, IEntityEndpoint> 
				traverseList("getSites", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UserTimelineObjects> getMeTimelineUserTimelineObjects(Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<UserTimelineObjects, IEntityEndpoint> 
				traverseList("getMeTimelineUserTimelineObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationHistory> getUsersReputation_historyReputationHistory(String ids, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, pagesize, page, filter, callback, site};
			return paginationPolicy.<ReputationHistory, IEntityEndpoint> 
				traverseList("getUsersReputation_historyReputationHistory", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getMeNotifications(Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback, site};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getMeNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getUsersInboxInboxItems(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getUsersInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadgesByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getTagsRequiredTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getTagsRequiredTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEditsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, IEntityEndpoint> 
				traverseList("getSuggested_editsSuggestedEditsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<AccessTokens> getAppsDe_authenticateAccessTokens(String accessTokens, Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { accessTokens, pagesize, page, filter, callback};
			return paginationPolicy.<AccessTokens, IEntityEndpoint> 
				traverseList("getAppsDe_authenticateAccessTokens", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Posts> getPosts(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Posts, IEntityEndpoint> 
				traverseList("getPosts", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getNotifications(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsersByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getInboxInboxItems(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getInboxInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<InfoObject> getInfoInfoObject(String site){
			Data<InfoObject> data = new Data<InfoObject>();
			data.addElement(callbackEndpoint.getInfoInfoObject(site));
			return data;
		}
		
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_answerersTagScoreObjectsByPeriod(String tag, String period, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tag, period, pagesize, page, filter, callback, site};
			return paginationPolicy.<TagScoreObjects, IEntityEndpoint> 
				traverseList("getTagsTop_answerersTagScoreObjectsByPeriod", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<ReputationChanges> getUsersReputationReputationChanges(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<ReputationChanges, IEntityEndpoint> 
				traverseList("getUsersReputationReputationChanges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getTagsFaqQuestions(String tags, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getTagsFaqQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getBadgesRecipientsBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Tags> getMeTags(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Tags, IEntityEndpoint> 
				traverseList("getMeTags", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Privileges> getUsersPrivileges(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<Privileges, IEntityEndpoint> 
				traverseList("getUsersPrivileges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_askersTagScoreObjectsByPeriod(String tag, String period, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tag, period, pagesize, page, filter, callback, site};
			return paginationPolicy.<TagScoreObjects, IEntityEndpoint> 
				traverseList("getTagsTop_askersTagScoreObjectsByPeriod", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getMeSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, IEntityEndpoint> 
				traverseList("getMeSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersQuestionsFeaturedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersQuestionsFeaturedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getMeFavoritesQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getMeFavoritesQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<InboxItems> getInboxUnreadInboxItems(Integer pagesize, Integer page, String filter, String callback, Integer since){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class, Integer.class};
			Object[] vals = { pagesize, page, filter, callback, since};
			return paginationPolicy.<InboxItems, IEntityEndpoint> 
				traverseList("getInboxUnreadInboxItems", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getQuestionsAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getQuestionsAnswers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Badges> getUsersBadges(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Badges, IEntityEndpoint> 
				traverseList("getUsersBadges", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Answers> getAnswersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Answers, IEntityEndpoint> 
				traverseList("getAnswersByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Filters> getFiltersByFilters(String filters){
			Class<?>[] types = { String.class};
			Object[] vals = { filters};
			return paginationPolicy.<Filters, IEntityEndpoint> 
				traverseList("getFiltersByFilters", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<SuggestedEdits> getPostsSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<SuggestedEdits, IEntityEndpoint> 
				traverseList("getPostsSuggested_editsSuggestedEdits", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsersModeratorsElectedUsers(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsersModeratorsElectedUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Revisions> getRevisionsByIds(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Revisions, IEntityEndpoint> 
				traverseList("getRevisionsByIds", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsLinkedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsLinkedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IData<Error> getErrorsErrorById(Integer id){
			Data<Error> data = new Data<Error>();
			data.addElement(callbackEndpoint.getErrorsErrorById(id));
			return data;
		}
		
		@Override
		public IDataSet<Comments> getPostsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getPostsComments", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<TagSynonyms, IEntityEndpoint> 
				traverseList("getTagsSynonymsTagSynonyms", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getNotificationsUnreadNotifications(Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { pagesize, page, filter, callback};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Comments> getUsersCommentsByToid(String ids, Integer toid, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, toid, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Comments, IEntityEndpoint> 
				traverseList("getUsersCommentsByToid", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TagWikis> getTagsWikisTagWikis(String tags, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tags, pagesize, page, filter, callback, site};
			return paginationPolicy.<TagWikis, IEntityEndpoint> 
				traverseList("getTagsWikisTagWikis", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Users> getUsers(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { inname, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Users, IEntityEndpoint> 
				traverseList("getUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<UserTimelineObjects> getUsersTimelineUserTimelineObjects(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<UserTimelineObjects, IEntityEndpoint> 
				traverseList("getUsersTimelineUserTimelineObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<QuestionTimelineEvents> getQuestionsTimelineQuestionTimelineEvents(String ids, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<QuestionTimelineEvents, IEntityEndpoint> 
				traverseList("getQuestionsTimelineQuestionTimelineEvents", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<NetworkUsers> getUsersAssociatedNetworkUsers(String ids, Integer pagesize, Integer page, String filter, String callback){
			Class<?>[] types = { String.class, Integer.class, Integer.class, String.class, String.class};
			Object[] vals = { ids, pagesize, page, filter, callback};
			return paginationPolicy.<NetworkUsers, IEntityEndpoint> 
				traverseList("getUsersAssociatedNetworkUsers", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsUnansweredQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsUnansweredQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersTagsTop_questionsQuestions(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, tags, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersTagsTop_questionsQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getUsersNotifications(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getUsersNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<TopTagObjects> getUsersTop_question_tagsTopTagObjects(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<TopTagObjects, IEntityEndpoint> 
				traverseList("getUsersTop_question_tagsTopTagObjects", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getUsersQuestionsNo_answersQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { ids, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getUsersQuestionsNo_answersQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Notifications> getUsersNotificationsUnreadNotifications(Integer id, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { id, pagesize, page, filter, callback, site};
			return paginationPolicy.<Notifications, IEntityEndpoint> 
				traverseList("getUsersNotificationsUnreadNotifications", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getQuestionsNo_answersQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getQuestionsNo_answersQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getSimilarQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site, String nottagged, String title){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site, nottagged, title};
			return paginationPolicy.<Questions, IEntityEndpoint> 
				traverseList("getSimilarQuestions", types, vals, callbackEndpoint);
		}
		
		
	}
}
