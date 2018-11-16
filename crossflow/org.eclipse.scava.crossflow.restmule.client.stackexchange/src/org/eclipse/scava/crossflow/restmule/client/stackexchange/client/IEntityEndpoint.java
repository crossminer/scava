package org.eclipse.scava.crossflow.restmule.client.stackexchange.client;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.*;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Error;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePaged;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface IEntityEndpoint {

	
		@GET("/me/reputation")
		Call<StackExchangePaged<ReputationResponse>> getMeReputationReputationResponse( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags")
		Call<StackExchangePaged<Tags>> getTags( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/synonyms")
		Call<StackExchangePaged<TagSynonyms>> getTagsSynonymsTagSynonyms( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me")
		Observable<User> getMeUser( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{ids}/questions/unaccepted")
		Call<StackExchangePaged<QuestionsResponse>> getUsersQuestionsUnacceptedQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/associated")
		Call<StackExchangePaged<NetworkUsers>> getMeAssociatedNetworkUsers( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/inbox")
		Call<StackExchangePaged<InboxItems>> getMeInboxInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/inbox/unread")
		Call<StackExchangePaged<InboxItems>> getMeInboxUnreadInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/featured")
		Call<StackExchangePaged<QuestionsResponse>> getMeQuestionsFeaturedQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/privileges")
		Call<StackExchangePaged<Privileges>> getMePrivileges( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/answers")
		Call<StackExchangePaged<Answers>> getUsersAnswers( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/posts/{ids}")
		Call<StackExchangePaged<Posts>> getPostsByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/questions/unanswered")
		Call<StackExchangePaged<QuestionsResponse>> getUsersQuestionsUnansweredQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges")
		Call<StackExchangePaged<Badges>> getBadges( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/reputation-history/full")
		Call<StackExchangePaged<ReputationHistoryResponse>> getUsersReputation_historyFullReputationHistoryResponse( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/info")
		Call<StackExchangePaged<Tags>> getTagsInfoTags( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/suggested-edits")
		Call<StackExchangePaged<SuggestedEdits>> getSuggested_editsSuggestedEdits( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/reputation-history")
		Call<StackExchangePaged<ReputationHistoryResponse>> getMeReputation_historyReputationHistoryResponse( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/suggested-edits")
		Call<StackExchangePaged<SuggestedEdits>> getUsersSuggested_editsSuggestedEdits( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/top-question-tags")
		Observable<TopTagObjects> getMeTop_question_tagsTopTagObjects( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/questions/{ids}")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsQuestionsResponseByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/featured")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsFeaturedQuestionsResponse( 
				
				@Query(value="tagged", encoded=true) String tagged,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/privileges")
		Call<StackExchangePaged<Privileges>> getPrivileges( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/comments/{ids}")
		Call<StackExchangePaged<Comments>> getCommentsByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/tags")
		Call<StackExchangePaged<Badges>> getBadgesTagsBadges( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/tags/{tags}/top-answers")
		Call<StackExchangePaged<Answers>> getMeTagsTop_answersAnswers( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/mentioned")
		Call<StackExchangePaged<Comments>> getMeMentionedComments( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/merges")
		Call<StackExchangePaged<AccountMerge>> getMeMergesAccountMerge( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/{ids}/recipients")
		Call<StackExchangePaged<Badges>> getBadgesRecipientsBadges( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/errors")
		Call<StackExchangePaged<Errors>> getErrors( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/badges")
		Call<StackExchangePaged<Badges>> getMeBadges( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/events")
		Call<StackExchangePaged<Events>> getEvents( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/filters/create")
		Observable<SingleFilter> getFiltersCreateSingleFilter( 
				
				@Query(value="base", encoded=true) String base,			
				@Query(value="exclude", encoded=true) String exclude,			
				@Query(value="include", encoded=true) String include,			
				@Query(value="unsafe", encoded=true) Boolean unsafe);
	
		@GET("/users/moderators")
		Call<StackExchangePaged<UsersResponse>> getUsersModeratorsUsersResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/inbox/unread")
		Call<StackExchangePaged<InboxItems>> getUsersInboxUnreadInboxItems( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/merges")
		Call<StackExchangePaged<AccountMerge>> getUsersMergesAccountMerge( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/write-permissions")
		Observable<WritePermissions> getUsersWrite_permissionsWritePermissions( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/me/comments/{toId}")
		Call<StackExchangePaged<Comments>> getMeCommentsByToId( 
				
				@Path(value="toId", encoded=true) Integer toId,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/top-answer-tags")
		Observable<TopTagObjects> getMeTop_answer_tagsTopTagObjects( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{id}/top-answer-tags")
		Observable<TopTagObjects> getUsersTop_answer_tagsTopTagObjects( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{ids}/questions")
		Call<StackExchangePaged<QuestionsResponse>> getUsersQuestionsQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/name")
		Call<StackExchangePaged<Badges>> getBadgesNameBadges( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/posts/{ids}/revisions")
		Call<StackExchangePaged<Revisions>> getPostsRevisions( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/tags/{tags}/top-questions")
		Call<StackExchangePaged<QuestionsResponse>> getMeTagsTop_questionsQuestionsResponse( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/comments")
		Call<StackExchangePaged<Comments>> getMeComments( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/no-answers")
		Call<StackExchangePaged<QuestionsResponse>> getMeQuestionsNo_answersQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/notifications/unread")
		Call<StackExchangePaged<Notifications>> getMeNotificationsUnreadNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions")
		Call<StackExchangePaged<QuestionsResponse>> getMeQuestionsQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/reputation-history/full")
		Call<StackExchangePaged<ReputationHistoryResponse>> getMeReputation_historyFullReputationHistoryResponse( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/write-permissions")
		Observable<WritePermissions> getMeWrite_permissionsWritePermissions( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{id}/tags/{tags}/top-answers")
		Call<StackExchangePaged<Answers>> getUsersTagsTop_answersAnswers( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/related")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsRelatedQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/mentioned")
		Call<StackExchangePaged<Comments>> getUsersMentionedComments( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/answers/{ids}/comments")
		Call<StackExchangePaged<Comments>> getAnswersComments( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/moderator-only")
		Call<StackExchangePaged<Tags>> getTagsModerator_onlyTags( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/comments")
		Call<StackExchangePaged<Comments>> getUsersComments( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/unanswered")
		Call<StackExchangePaged<QuestionsResponse>> getMeQuestionsUnansweredQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/access-tokens/{accessTokens}/invalidate")
		Call<StackExchangePaged<AccessTokens>> getAccess_tokensInvalidateAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/related")
		Call<StackExchangePaged<Tags>> getTagsRelatedTags( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/answers")
		Call<StackExchangePaged<Answers>> getMeAnswers( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/tags")
		Call<StackExchangePaged<Tags>> getUsersTags( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/comments")
		Call<StackExchangePaged<Comments>> getQuestionsComments( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/favorites")
		Call<StackExchangePaged<QuestionsResponse>> getUsersFavoritesQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/access-tokens/{accessTokens}")
		Call<StackExchangePaged<AccessTokens>> getAccess_tokensAccessTokensByAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/unaccepted")
		Call<StackExchangePaged<QuestionsResponse>> getMeQuestionsUnacceptedQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/sites")
		Call<StackExchangePaged<Sites>> getSites( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/timeline")
		Observable<UserTimelineObjects> getMeTimelineUserTimelineObjects( 
				
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{ids}/reputation-history")
		Call<StackExchangePaged<ReputationHistoryResponse>> getUsersReputation_historyReputationHistoryResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/notifications")
		Call<StackExchangePaged<Notifications>> getMeNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/comments")
		Call<StackExchangePaged<Comments>> getComments( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/inbox")
		Call<StackExchangePaged<InboxItems>> getUsersInboxInboxItems( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/{ids}")
		Call<StackExchangePaged<Badges>> getBadgesByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/required")
		Call<StackExchangePaged<Tags>> getTagsRequiredTags( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/suggested-edits/{ids}")
		Call<StackExchangePaged<SuggestedEdits>> getSuggested_editsSuggestedEditsByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/apps/{accessTokens}/de-authenticate")
		Call<StackExchangePaged<AccessTokens>> getAppsDe_authenticateAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/posts")
		Call<StackExchangePaged<Posts>> getPosts( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/notifications")
		Call<StackExchangePaged<Notifications>> getNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}")
		Call<StackExchangePaged<UsersResponse>> getUsersUsersResponseByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/inbox")
		Call<StackExchangePaged<InboxItems>> getInboxInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/info")
		Observable<InfoObject> getInfoInfoObject( 
				
				@Query(value="site", encoded=true) String site);
	
		@GET("/tags/{tag}/top-answerers/{period}")
		Call<StackExchangePaged<TagScoreObjects>> getTagsTop_answerersTagScoreObjectsByPeriod( 
				
				@Path(value="tag", encoded=true) String tag,			
				@Path(value="period", encoded=true) String period,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/reputation")
		Call<StackExchangePaged<ReputationResponse>> getUsersReputationReputationResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/faq")
		Call<StackExchangePaged<QuestionsResponse>> getTagsFaqQuestionsResponse( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/recipients")
		Call<StackExchangePaged<Badges>> getBadgesRecipientsBadges( 
				
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/tags")
		Call<StackExchangePaged<Tags>> getMeTags( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/privileges")
		Call<StackExchangePaged<Privileges>> getUsersPrivileges( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tag}/top-askers/{period}")
		Call<StackExchangePaged<TagScoreObjects>> getTagsTop_askersTagScoreObjectsByPeriod( 
				
				@Path(value="tag", encoded=true) String tag,			
				@Path(value="period", encoded=true) String period,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/suggested-edits")
		Call<StackExchangePaged<SuggestedEdits>> getMeSuggested_editsSuggestedEdits( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/questions/featured")
		Call<StackExchangePaged<QuestionsResponse>> getUsersQuestionsFeaturedQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/favorites")
		Call<StackExchangePaged<QuestionsResponse>> getMeFavoritesQuestionsResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/answers")
		Call<StackExchangePaged<Answers>> getAnswers( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/inbox/unread")
		Call<StackExchangePaged<InboxItems>> getInboxUnreadInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/answers")
		Call<StackExchangePaged<Answers>> getQuestionsAnswers( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/badges")
		Call<StackExchangePaged<Badges>> getUsersBadges( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/answers/{ids}")
		Call<StackExchangePaged<Answers>> getAnswersByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/filters/{filters}")
		Call<StackExchangePaged<Filters>> getFiltersByFilters( 
				
				@Path(value="filters", encoded=true) String filters,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsQuestionsResponse( 
				
				@Query(value="tagged", encoded=true) String tagged,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/posts/{ids}/suggested-edits")
		Call<StackExchangePaged<SuggestedEdits>> getPostsSuggested_editsSuggestedEdits( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/moderators/elected")
		Call<StackExchangePaged<UsersResponse>> getUsersModeratorsElectedUsersResponse( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/revisions/{ids}")
		Call<StackExchangePaged<Revisions>> getRevisionsByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/linked")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsLinkedQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/errors/{id}")
		Observable<Errordefinition> getErrorsErrordefinitionById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/posts/{ids}/comments")
		Call<StackExchangePaged<Comments>> getPostsComments( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/synonyms")
		Call<StackExchangePaged<TagSynonyms>> getTagsSynonymsTagSynonyms( 
				
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/notifications/unread")
		Call<StackExchangePaged<Notifications>> getNotificationsUnreadNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/comments/{toid}")
		Call<StackExchangePaged<Comments>> getUsersCommentsByToid( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Path(value="toid", encoded=true) Integer toid,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/wikis")
		Call<StackExchangePaged<TagWikis>> getTagsWikisTagWikis( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users")
		Call<StackExchangePaged<UsersResponse>> getUsersUsersResponse( 
				
				@Query(value="inname", encoded=true) String inname,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/timeline")
		Observable<UserTimelineObjects> getUsersTimelineUserTimelineObjects( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/questions/{ids}/timeline")
		Call<StackExchangePaged<QuestionTimelineEvents>> getQuestionsTimelineQuestionTimelineEvents( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/associated")
		Call<StackExchangePaged<NetworkUsers>> getUsersAssociatedNetworkUsers( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/unanswered")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsUnansweredQuestionsResponse( 
				
				@Query(value="tagged", encoded=true) String tagged,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/tags/{tags}/top-questions")
		Call<StackExchangePaged<QuestionsResponse>> getUsersTagsTop_questionsQuestionsResponse( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/notifications")
		Call<StackExchangePaged<Notifications>> getUsersNotifications( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/top-question-tags")
		Observable<TopTagObjects> getUsersTop_question_tagsTopTagObjects( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site);
	
		@GET("/users/{ids}/questions/no-answers")
		Call<StackExchangePaged<QuestionsResponse>> getUsersQuestionsNo_answersQuestionsResponse( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/notifications/unread")
		Call<StackExchangePaged<Notifications>> getUsersNotificationsUnreadNotifications( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/no-answers")
		Call<StackExchangePaged<QuestionsResponse>> getQuestionsNo_answersQuestionsResponse( 
				
				@Query(value="tagged", encoded=true) String tagged,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/similar")
		Call<StackExchangePaged<QuestionsResponse>> getSimilarQuestionsResponse( 
				
				@Query(value="tagged", encoded=true) String tagged,			
				@Query(value="order", encoded=true) String order,			
				@Query(value="max", encoded=true) String max,			
				@Query(value="min", encoded=true) String min,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="nottagged", encoded=true) String nottagged,			
				@Query(value="title", encoded=true) String title,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
}