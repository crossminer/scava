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
		public IDataSet<ReputationChanges> getMeReputationReputationChanges(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputationReputationChanges(filter, callback, site);
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
		public IDataSet<Questions> getUsersQuestionsUnacceptedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsUnacceptedQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getMeQuestionsFeaturedQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsFeaturedQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getUsersQuestionsUnansweredQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsUnansweredQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<ReputationHistory> getUsersReputation_historyFullReputationHistory(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputation_historyFullReputationHistory(id, filter, callback, site);
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
		public IDataSet<ReputationHistory> getMeReputation_historyReputationHistory(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputation_historyReputationHistory(filter, callback, site);
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
		public IDataSet<TopTagObjects> getMeTop_question_tagsTopTagObjects(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeTop_question_tagsTopTagObjects(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getQuestionsByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getQuestionsFeaturedQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsFeaturedQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Users> getUsersModeratorsUsers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersModeratorsUsers(order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<WritePermissions> getUsersWrite_permissionsWritePermissions(Integer id, String filter, String callback, String site){ 
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
		public IDataSet<TopTagObjects> getMeTop_answer_tagsTopTagObjects(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeTop_answer_tagsTopTagObjects(filter, callback, site);
		}
		 
		@Override
		public IDataSet<TopTagObjects> getUsersTop_answer_tagsTopTagObjects(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersTop_answer_tagsTopTagObjects(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getUsersQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getMeTagsTop_questionsQuestions(String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|hot|week|month|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTagsTop_questionsQuestions(tags, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getMeQuestionsNo_answersQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsNo_answersQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getMeNotificationsUnreadNotifications(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeNotificationsUnreadNotifications(filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getMeQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistory> getMeReputation_historyFullReputationHistory(String filter, String callback, String site){ 
			
			
			Validate.notNull(site);
			return entityClient.getMeReputation_historyFullReputationHistory(filter, callback, site);
		}
		 
		@Override
		public IDataSet<WritePermissions> getMeWrite_permissionsWritePermissions(String filter, String callback, String site){ 
			
			
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
		public IDataSet<Questions> getQuestionsRelatedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|rank)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsRelatedQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getMeQuestionsUnansweredQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsUnansweredQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getUsersFavoritesQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|added)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersFavoritesQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<AccessTokens> getAccess_tokensAccessTokensByAccessTokens(String accessTokens, String filter, String callback){ 
			Validate.notNull(accessTokens);
			
			
			return entityClient.getAccess_tokensAccessTokensByAccessTokens(accessTokens, filter, callback);
		}
		 
		@Override
		public IDataSet<Questions> getMeQuestionsUnacceptedQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeQuestionsUnacceptedQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Sites> getSites(String filter, String callback){ 
			
			
			return entityClient.getSites(filter, callback);
		}
		 
		@Override
		public IDataSet<UserTimelineObjects> getMeTimelineUserTimelineObjects(Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeTimelineUserTimelineObjects(fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<ReputationHistory> getUsersReputation_historyReputationHistory(String ids, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputation_historyReputationHistory(ids, filter, callback, site);
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
		public IDataSet<Users> getUsersByIds(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersByIds(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<ReputationChanges> getUsersReputationReputationChanges(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersReputationReputationChanges(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getTagsFaqQuestions(String tags, String filter, String callback, String site){ 
			Validate.notNull(tags);
			
			
			Validate.notNull(site);
			return entityClient.getTagsFaqQuestions(tags, filter, callback, site);
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
		public IDataSet<Questions> getUsersQuestionsFeaturedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsFeaturedQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getMeFavoritesQuestions(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|added)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getMeFavoritesQuestions(order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Questions> getQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|hot|week|month|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
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
		public IDataSet<Users> getUsersModeratorsElectedUsers(String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersModeratorsElectedUsers(order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Revisions> getRevisionsByIds(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			
			
			
			
			Validate.notNull(site);
			return entityClient.getRevisionsByIds(ids, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getQuestionsLinkedQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|rank)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsLinkedQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IData<Error> getErrorsErrorById(Integer id){ 
			Validate.notNull(id);
			return entityClient.getErrorsErrorById(id);
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
		public IDataSet<Users> getUsers(String inname, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(reputation|creation|name|modified)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsers(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<UserTimelineObjects> getUsersTimelineUserTimelineObjects(String ids, Integer fromdate, Integer todate, String filter, String callback, String site){ 
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
		public IDataSet<Questions> getQuestionsUnansweredQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsUnansweredQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getUsersTagsTop_questionsQuestions(Integer id, String tags, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(id);
			Validate.notNull(tags);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersTagsTop_questionsQuestions(id, tags, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getUsersNotifications(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersNotifications(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<TopTagObjects> getUsersTop_question_tagsTopTagObjects(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersTop_question_tagsTopTagObjects(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getUsersQuestionsNo_answersQuestions(String ids, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			Validate.notNull(ids);
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getUsersQuestionsNo_answersQuestions(ids, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Notifications> getUsersNotificationsUnreadNotifications(Integer id, String filter, String callback, String site){ 
			Validate.notNull(id);
			
			
			Validate.notNull(site);
			return entityClient.getUsersNotificationsUnreadNotifications(id, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getQuestionsNo_answersQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes)");
			}
			
			
			
			
			Validate.notNull(site);
			return entityClient.getQuestionsNo_answersQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		}
		 
		@Override
		public IDataSet<Questions> getSimilarQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String nottagged, String title){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			
			
			return entityClient.getSimilarQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, nottagged, title);
		}
		 
		@Override
		public IDataSet<Questions> getSearchAdvancedQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String accepted, Integer answers, String body, String closed, String migrated, String notice, String nottagged, String q, String title, String url, Integer user, Integer views, String wiki){ 
			
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
			return searchClient.getSearchAdvancedQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, accepted, answers, body, closed, migrated, notice, nottagged, q, title, url, user, views, wiki);
		}
		 
		@Override
		public IDataSet<Questions> getSearchQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, String filter, String callback, String site, String intitle, String nottagged){ 
			
			if (order != null) {
				Validate.matchesPattern(order,"(desc|asc)");
			}
			
			
			if (sort != null) {
				Validate.matchesPattern(sort,"(activity|creation|votes|relevance)");
			}
			
			
			
			
			Validate.notNull(site);
			
			
			return searchClient.getSearchQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site, intitle, nottagged);
		}
	}
}
