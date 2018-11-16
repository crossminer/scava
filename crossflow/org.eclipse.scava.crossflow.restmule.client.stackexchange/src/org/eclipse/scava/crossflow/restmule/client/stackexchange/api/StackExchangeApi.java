package org.eclipse.scava.crossflow.restmule.client.stackexchange.api;

import org.apache.commons.lang3.Validate;
import org.eclipse.scava.crossflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.client.EntityApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.client.IEntityApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.client.SearchApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.client.ISearchApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.*;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Error;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession;

public class StackExchangeApi  {

	public static StackExchangeBuilder create(){
		return new StackExchangeBuilder(); 
	}
	
	public static IStackExchangeApi createDefault(){ 
		return new StackExchangeBuilder().setSession(StackExchangeSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class StackExchangeBuilder 
	implements IClientBuilder<IStackExchangeApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public IStackExchangeApi build() {
			return (IStackExchangeApi) new StackExchangeClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<IStackExchangeApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<IStackExchangeApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class StackExchangeClient implements IStackExchangeApi {
		
		private IEntityApi entityClient;
		private ISearchApi searchClient;
		
		StackExchangeClient(ISession session, boolean activeCaching) {
			if (session == null) {
				session = StackExchangeSession.createPublic(); 
			}	
			entityClient = EntityApi.create()
				.setSession(StackExchangeSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
			searchClient = SearchApi.create()
				.setSession(StackExchangeSession.Factory.copy(session))
				.setActiveCaching(activeCaching)
				.build();
		}

		/** WRAPED METHODS */
		 
		@Override
		public IDataSet<ReputationResponse> getMeReputationReputationResponse(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputationReputationResponse(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|applied|activity)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTagsSynonymsTagSynonyms(tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<User> getMeUser(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeUser(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsUnacceptedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsUnacceptedQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<NetworkUsers> getMeAssociatedNetworkUsers(String filter, String callback){ 
			
			
			return entityClient.getMeAssociatedNetworkUsers(filter, callback);
		}
		 
		@Override
		public IDataSet<InboxItems> getMeInboxInboxItems(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeInboxInboxItems(filter, callback, site);
		}
		 
		@Override
		public IDataSet<InboxItems> getMeInboxUnreadInboxItems(String filter, String callback, String site, Integer since){ 
			
			
			Validate.notNull(site);
			
			return entityClient.getMeInboxUnreadInboxItems(filter, callback, site, since);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsFeaturedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsFeaturedQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Privileges> getMePrivileges(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMePrivileges(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getUsersAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersAnswers(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Posts> getPostsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getPostsByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsUnansweredQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsUnansweredQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name|type)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadges(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistoryResponse> getUsersReputation_historyFullReputationHistoryResponse(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputation_historyFullReputationHistoryResponse(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getTagsInfoTags(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTagsInfoTags(tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|approval|rejection)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getSuggested_editsSuggestedEdits(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistoryResponse> getMeReputation_historyReputationHistoryResponse(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputation_historyReputationHistoryResponse(filter, callback, site);
		}
		 
		@Override
		public IDataSet<SuggestedEdits> getUsersSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|approval|rejection)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersSuggested_editsSuggestedEdits(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<TopTagObjects> getMeTop_question_tagsTopTagObjects(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeTop_question_tagsTopTagObjects(filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsQuestionsResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsQuestionsResponseByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsFeaturedQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsFeaturedQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Privileges> getPrivileges(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getPrivileges(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getCommentsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getCommentsByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getBadgesTagsBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadgesTagsBadges(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getMeTagsTop_answersAnswers(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTagsTop_answersAnswers(tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getMeMentionedComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeMentionedComments(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<AccountMerge> getMeMergesAccountMerge(String filter, String callback){ 
			
			
			return entityClient.getMeMergesAccountMerge(filter, callback);
		}
		 
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadgesRecipientsBadges(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Errors> getErrors(String filter, String callback){ 
			
			
			return entityClient.getErrors(filter, callback);
		}
		 
		@Override
		public IDataSet<Badges> getMeBadges(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name|type)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeBadges(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Events> getEvents(String filter, String callback, String site, Integer since){ 
			
			
			Validate.notNull(site);
			
			return entityClient.getEvents(filter, callback, site, since);
		}
		 
		@Override
		public IData<SingleFilter> getFiltersCreateSingleFilter(String base, String exclude, String include, Boolean unsafe){ 
			
			
			
			
			return entityClient.getFiltersCreateSingleFilter(base, exclude, include, unsafe);
		}
		 
		@Override
		public IDataSet<UsersResponse> getUsersModeratorsUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersModeratorsUsersResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<InboxItems> getUsersInboxUnreadInboxItems(Integer id, String filter, String callback, String site, Integer since){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			
			return entityClient.getUsersInboxUnreadInboxItems(id, filter, callback, site, since);
		}
		 
		@Override
		public IDataSet<AccountMerge> getUsersMergesAccountMerge(String ids, String filter, String callback){ 
			Validate.notNull(ids);
			
			
			return entityClient.getUsersMergesAccountMerge(ids, filter, callback);
		}
		 
		@Override
		public IData<WritePermissions> getUsersWrite_permissionsWritePermissions(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersWrite_permissionsWritePermissions(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getMeCommentsByToId(Integer toId, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(toId);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeCommentsByToId(toId, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<TopTagObjects> getMeTop_answer_tagsTopTagObjects(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeTop_answer_tagsTopTagObjects(filter, callback, site);
		}
		 
		@Override
		public IData<TopTagObjects> getUsersTop_answer_tagsTopTagObjects(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersTop_answer_tagsTopTagObjects(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getBadgesNameBadges(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadgesNameBadges(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Revisions> getPostsRevisions(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getPostsRevisions(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeTagsTop_questionsQuestionsResponse(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|hot|week|month|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTagsTop_questionsQuestionsResponse(tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getMeComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeComments(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsNo_answersQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsNo_answersQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getMeNotificationsUnreadNotifications(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeNotificationsUnreadNotifications(filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistoryResponse> getMeReputation_historyFullReputationHistoryResponse(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputation_historyFullReputationHistoryResponse(filter, callback, site);
		}
		 
		@Override
		public IData<WritePermissions> getMeWrite_permissionsWritePermissions(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeWrite_permissionsWritePermissions(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getUsersTagsTop_answersAnswers(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(id);
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersTagsTop_answersAnswers(id, tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsRelatedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|rank)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsRelatedQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getUsersMentionedComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersMentionedComments(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getAnswersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getAnswersComments(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getTagsModerator_onlyTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTagsModerator_onlyTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getUsersComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersComments(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsUnansweredQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsUnansweredQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<AccessTokens> getAccess_tokensInvalidateAccessTokens(String accessTokens, String filter, String callback){ 
			Validate.notNull(accessTokens);
			
			
			return entityClient.getAccess_tokensInvalidateAccessTokens(accessTokens, filter, callback);
		}
		 
		@Override
		public IDataSet<Tags> getTagsRelatedTags(String tags, String filter, String callback, String site){ 
			Validate.notNull(tags);
			
			
			Validate.notNull(site);
			return entityClient.getTagsRelatedTags(tags, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getMeAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeAnswers(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getUsersTags(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersTags(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getQuestionsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsComments(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersFavoritesQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|added)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersFavoritesQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<AccessTokens> getAccess_tokensAccessTokensByAccessTokens(String accessTokens, String filter, String callback){ 
			Validate.notNull(accessTokens);
			
			
			return entityClient.getAccess_tokensAccessTokensByAccessTokens(accessTokens, filter, callback);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeQuestionsUnacceptedQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsUnacceptedQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Sites> getSites(String filter, String callback){ 
			
			
			return entityClient.getSites(filter, callback);
		}
		 
		@Override
		public IData<UserTimelineObjects> getMeTimelineUserTimelineObjects(Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTimelineUserTimelineObjects(fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistoryResponse> getUsersReputation_historyReputationHistoryResponse(String ids, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputation_historyReputationHistoryResponse(ids, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getMeNotifications(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeNotifications(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Comments> getComments(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getComments(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<InboxItems> getUsersInboxInboxItems(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersInboxInboxItems(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getBadgesByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name|type)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadgesByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getTagsRequiredTags(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTagsRequiredTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<SuggestedEdits> getSuggested_editsSuggestedEditsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|approval|rejection)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getSuggested_editsSuggestedEditsByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<AccessTokens> getAppsDe_authenticateAccessTokens(String accessTokens, String filter, String callback){ 
			Validate.notNull(accessTokens);
			
			
			return entityClient.getAppsDe_authenticateAccessTokens(accessTokens, filter, callback);
		}
		 
		@Override
		public IDataSet<Posts> getPosts(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getPosts(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getNotifications(String filter, String callback){ 
			
			
			return entityClient.getNotifications(filter, callback);
		}
		 
		@Override
		public IDataSet<UsersResponse> getUsersUsersResponseByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersUsersResponseByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<InboxItems> getInboxInboxItems(String filter, String callback){ 
			
			
			return entityClient.getInboxInboxItems(filter, callback);
		}
		 
		@Override
		public IData<InfoObject> getInfoInfoObject(String site){ 
			Validate.notNull(site);
			return entityClient.getInfoInfoObject(site);
		}
		 
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_answerersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site){ 
			Validate.notNull(tag);
			Validate.notNull(period);
			Validate.matchesPattern(period,"(all_time|month)");
			
			
			Validate.notNull(site);
			return entityClient.getTagsTop_answerersTagScoreObjectsByPeriod(tag, period, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationResponse> getUsersReputationReputationResponse(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputationReputationResponse(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getTagsFaqQuestionsResponse(String tags, String filter, String callback, String site){ 
			Validate.notNull(tags);
			
			
			Validate.notNull(site);
			return entityClient.getTagsFaqQuestionsResponse(tags, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getBadgesRecipientsBadges(Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			
			
			
			Validate.notNull(site);
			return entityClient.getBadgesRecipientsBadges(fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Tags> getMeTags(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(popular|activity|name)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTags(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Privileges> getUsersPrivileges(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersPrivileges(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<TagScoreObjects> getTagsTop_askersTagScoreObjectsByPeriod(String tag, String period, String filter, String callback, String site){ 
			Validate.notNull(tag);
			Validate.notNull(period);
			Validate.matchesPattern(period,"(all_time|month)");
			
			
			Validate.notNull(site);
			return entityClient.getTagsTop_askersTagScoreObjectsByPeriod(tag, period, filter, callback, site);
		}
		 
		@Override
		public IDataSet<SuggestedEdits> getMeSuggested_editsSuggestedEdits(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|approval|rejection)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeSuggested_editsSuggestedEdits(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsFeaturedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsFeaturedQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getMeFavoritesQuestionsResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|added)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeFavoritesQuestionsResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getAnswers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getAnswers(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<InboxItems> getInboxUnreadInboxItems(String filter, String callback, Integer since){ 
			
			
			
			return entityClient.getInboxUnreadInboxItems(filter, callback, since);
		}
		 
		@Override
		public IDataSet<Answers> getQuestionsAnswers(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsAnswers(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Badges> getUsersBadges(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(rank|name|type|awarded)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersBadges(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Answers> getAnswersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getAnswersByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Filters> getFiltersByFilters(String filters){ 
			Validate.notNull(filters);
			return entityClient.getFiltersByFilters(filters);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|hot|week|month|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<SuggestedEdits> getPostsSuggested_editsSuggestedEdits(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|approval|rejection)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getPostsSuggested_editsSuggestedEdits(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<UsersResponse> getUsersModeratorsElectedUsersResponse(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersModeratorsElectedUsersResponse(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Revisions> getRevisionsByIds(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getRevisionsByIds(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsLinkedQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|rank)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsLinkedQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<Errordefinition> getErrorsErrordefinitionById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getErrorsErrordefinitionById(id);
		}
		 
		@Override
		public IDataSet<Comments> getPostsComments(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getPostsComments(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<TagSynonyms> getTagsSynonymsTagSynonyms(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|applied|activity)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getTagsSynonymsTagSynonyms(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getNotificationsUnreadNotifications(String filter, String callback){ 
			
			
			return entityClient.getNotificationsUnreadNotifications(filter, callback);
		}
		 
		@Override
		public IDataSet<Comments> getUsersCommentsByToid(String ids, Integer toid, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			Validate.notNull(toid);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersCommentsByToid(ids, toid, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<TagWikis> getTagsWikisTagWikis(String tags, String filter, String callback, String site){ 
			Validate.notNull(tags);
			
			
			Validate.notNull(site);
			return entityClient.getTagsWikisTagWikis(tags, filter, callback, site);
		}
		 
		@Override
		public IDataSet<UsersResponse> getUsersUsersResponse(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersUsersResponse(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<UserTimelineObjects> getUsersTimelineUserTimelineObjects(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersTimelineUserTimelineObjects(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionTimelineEvents> getQuestionsTimelineQuestionTimelineEvents(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsTimelineQuestionTimelineEvents(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<NetworkUsers> getUsersAssociatedNetworkUsers(String ids, String filter, String callback){ 
			Validate.notNull(ids);
			
			
			return entityClient.getUsersAssociatedNetworkUsers(ids, filter, callback);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsUnansweredQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsUnansweredQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersTagsTop_questionsQuestionsResponse(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(id);
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersTagsTop_questionsQuestionsResponse(id, tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getUsersNotifications(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersNotifications(id, filter, callback, site);
		}
		 
		@Override
		public IData<TopTagObjects> getUsersTop_question_tagsTopTagObjects(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersTop_question_tagsTopTagObjects(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getUsersQuestionsNo_answersQuestionsResponse(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsNo_answersQuestionsResponse(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getUsersNotificationsUnreadNotifications(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersNotificationsUnreadNotifications(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getQuestionsNo_answersQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsNo_answersQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getSimilarQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String nottagged, String title){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			
			
			return entityClient.getSimilarQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, nottagged, title);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getSearchAdvancedQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String accepted, Integer answers, String body, String closed, String migrated, String notice, String nottagged, String q, String title, String url, Integer user, Integer views, String wiki){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			if (accepted != null) {
				Validate.matchesPattern(accepted,"(true|false)");
			}
			
			
			if (closed != null) {
				Validate.matchesPattern(closed,"(true|false)");
			}
			if (migrated != null) {
				Validate.matchesPattern(migrated,"(true|false)");
			}
			if (notice != null) {
				Validate.matchesPattern(notice,"(true|false)");
			}
			
			
			
			
			
			
			if (wiki != null) {
				Validate.matchesPattern(wiki,"(true|false)");
			}
			return searchClient.getSearchAdvancedQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, accepted, answers, body, closed, migrated, notice, nottagged, q, title, url, user, views, wiki);
		}
		 
		@Override
		public IDataSet<QuestionsResponse> getSearchQuestionsResponse(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String intitle, String nottagged){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			
			
			return searchClient.getSearchQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, intitle, nottagged);
		}
	}
}
