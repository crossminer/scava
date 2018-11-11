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
		Call<List<ReputationChanges>> getMeReputationReputationChanges( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags")
		Call<List<Tags>> getTags( 
				
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
		Call<List<TagSynonyms>> getTagsSynonymsTagSynonyms( 
				
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
		Call<List<Questions>> getUsersQuestionsUnacceptedQuestions( 
				
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
		Call<List<NetworkUsers>> getMeAssociatedNetworkUsers( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/inbox")
		Call<List<InboxItems>> getMeInboxInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/inbox/unread")
		Call<List<InboxItems>> getMeInboxUnreadInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/featured")
		Call<List<Questions>> getMeQuestionsFeaturedQuestions( 
				
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
		Call<List<Privileges>> getMePrivileges( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/answers")
		Call<List<Answers>> getUsersAnswers( 
				
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
		Call<List<Posts>> getPostsByIds( 
				
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
		Call<List<Questions>> getUsersQuestionsUnansweredQuestions( 
				
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
		Call<List<Badges>> getBadges( 
				
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
		Call<List<ReputationHistory>> getUsersReputation_historyFullReputationHistory( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/info")
		Call<List<Tags>> getTagsInfoTags( 
				
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
		Call<List<SuggestedEdits>> getSuggested_editsSuggestedEdits( 
				
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
		Call<List<ReputationHistory>> getMeReputation_historyReputationHistory( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/suggested-edits")
		Call<List<SuggestedEdits>> getUsersSuggested_editsSuggestedEdits( 
				
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
		Call<List<TopTagObjects>> getMeTop_question_tagsTopTagObjects( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}")
		Call<List<Questions>> getQuestionsByIds( 
				
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
		Call<List<Questions>> getQuestionsFeaturedQuestions( 
				
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
		Call<List<Privileges>> getPrivileges( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/comments/{ids}")
		Call<List<Comments>> getCommentsByIds( 
				
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
		Call<List<Badges>> getBadgesTagsBadges( 
				
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
		Call<List<Answers>> getMeTagsTop_answersAnswers( 
				
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
		Call<List<Comments>> getMeMentionedComments( 
				
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
		Call<List<AccountMerge>> getMeMergesAccountMerge( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/{ids}/recipients")
		Call<List<Badges>> getBadgesRecipientsBadges( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/errors")
		Call<List<Errors>> getErrors( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/badges")
		Call<List<Badges>> getMeBadges( 
				
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
		Call<List<Events>> getEvents( 
				
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
		Call<List<Users>> getUsersModeratorsUsers( 
				
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
		Call<List<InboxItems>> getUsersInboxUnreadInboxItems( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/merges")
		Call<List<AccountMerge>> getUsersMergesAccountMerge( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/write-permissions")
		Call<List<WritePermissions>> getUsersWrite_permissionsWritePermissions( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/comments/{toId}")
		Call<List<Comments>> getMeCommentsByToId( 
				
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
		Call<List<TopTagObjects>> getMeTop_answer_tagsTopTagObjects( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/top-answer-tags")
		Call<List<TopTagObjects>> getUsersTop_answer_tagsTopTagObjects( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/questions")
		Call<List<Questions>> getUsersQuestions( 
				
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
		Call<List<Badges>> getBadgesNameBadges( 
				
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
		Call<List<Revisions>> getPostsRevisions( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/tags/{tags}/top-questions")
		Call<List<Questions>> getMeTagsTop_questionsQuestions( 
				
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
		Call<List<Comments>> getMeComments( 
				
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
		Call<List<Questions>> getMeQuestionsNo_answersQuestions( 
				
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
		Call<List<Notifications>> getMeNotificationsUnreadNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions")
		Call<List<Questions>> getMeQuestions( 
				
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
		Call<List<ReputationHistory>> getMeReputation_historyFullReputationHistory( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/write-permissions")
		Call<List<WritePermissions>> getMeWrite_permissionsWritePermissions( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/tags/{tags}/top-answers")
		Call<List<Answers>> getUsersTagsTop_answersAnswers( 
				
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
		Call<List<Questions>> getQuestionsRelatedQuestions( 
				
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
		Call<List<Comments>> getUsersMentionedComments( 
				
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
		Call<List<Comments>> getAnswersComments( 
				
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
		Call<List<Tags>> getTagsModerator_onlyTags( 
				
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
		Call<List<Comments>> getUsersComments( 
				
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
		Call<List<Questions>> getMeQuestionsUnansweredQuestions( 
				
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
		Call<List<AccessTokens>> getAccess_tokensInvalidateAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/related")
		Call<List<Tags>> getTagsRelatedTags( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/answers")
		Call<List<Answers>> getMeAnswers( 
				
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
		Call<List<Tags>> getUsersTags( 
				
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
		Call<List<Comments>> getQuestionsComments( 
				
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
		Call<List<Questions>> getUsersFavoritesQuestions( 
				
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
		Call<List<AccessTokens>> getAccess_tokensAccessTokensByAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/questions/unaccepted")
		Call<List<Questions>> getMeQuestionsUnacceptedQuestions( 
				
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
		Call<List<Sites>> getSites( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/timeline")
		Call<List<UserTimelineObjects>> getMeTimelineUserTimelineObjects( 
				
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/reputation-history")
		Call<List<ReputationHistory>> getUsersReputation_historyReputationHistory( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/notifications")
		Call<List<Notifications>> getMeNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/comments")
		Call<List<Comments>> getComments( 
				
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
		Call<List<InboxItems>> getUsersInboxInboxItems( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/{ids}")
		Call<List<Badges>> getBadgesByIds( 
				
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
		Call<List<Tags>> getTagsRequiredTags( 
				
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
		Call<List<SuggestedEdits>> getSuggested_editsSuggestedEditsByIds( 
				
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
		Call<List<AccessTokens>> getAppsDe_authenticateAccessTokens( 
				
				@Path(value="accessTokens", encoded=true) String accessTokens,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/posts")
		Call<List<Posts>> getPosts( 
				
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
		Call<List<Notifications>> getNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}")
		Call<List<Users>> getUsersByIds( 
				
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
		Call<List<InboxItems>> getInboxInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/info")
		Observable<InfoObject> getInfoInfoObject( 
				
				@Query(value="site", encoded=true) String site);
	
		@GET("/tags/{tag}/top-answerers/{period}")
		Call<List<TagScoreObjects>> getTagsTop_answerersTagScoreObjectsByPeriod( 
				
				@Path(value="tag", encoded=true) String tag,			
				@Path(value="period", encoded=true) String period,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/reputation")
		Call<List<ReputationChanges>> getUsersReputationReputationChanges( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tags}/faq")
		Call<List<Questions>> getTagsFaqQuestions( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/badges/recipients")
		Call<List<Badges>> getBadgesRecipientsBadges( 
				
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/tags")
		Call<List<Tags>> getMeTags( 
				
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
		Call<List<Privileges>> getUsersPrivileges( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/tags/{tag}/top-askers/{period}")
		Call<List<TagScoreObjects>> getTagsTop_askersTagScoreObjectsByPeriod( 
				
				@Path(value="tag", encoded=true) String tag,			
				@Path(value="period", encoded=true) String period,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/me/suggested-edits")
		Call<List<SuggestedEdits>> getMeSuggested_editsSuggestedEdits( 
				
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
		Call<List<Questions>> getUsersQuestionsFeaturedQuestions( 
				
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
		Call<List<Questions>> getMeFavoritesQuestions( 
				
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
		Call<List<Answers>> getAnswers( 
				
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
		Call<List<InboxItems>> getInboxUnreadInboxItems( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="since", encoded=true) Integer since,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/answers")
		Call<List<Answers>> getQuestionsAnswers( 
				
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
		Call<List<Badges>> getUsersBadges( 
				
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
		Call<List<Answers>> getAnswersByIds( 
				
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
		Call<List<Filters>> getFiltersByFilters( 
				
				@Path(value="filters", encoded=true) String filters,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions")
		Call<List<Questions>> getQuestions( 
				
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
		Call<List<SuggestedEdits>> getPostsSuggested_editsSuggestedEdits( 
				
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
		Call<List<Users>> getUsersModeratorsElectedUsers( 
				
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
		Call<List<Revisions>> getRevisionsByIds( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/linked")
		Call<List<Questions>> getQuestionsLinkedQuestions( 
				
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
		Observable<Error> getErrorsErrorById( 
				
				@Path(value="id", encoded=true) Integer id);
	
		@GET("/posts/{ids}/comments")
		Call<List<Comments>> getPostsComments( 
				
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
		Call<List<TagSynonyms>> getTagsSynonymsTagSynonyms( 
				
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
		Call<List<Notifications>> getNotificationsUnreadNotifications( 
				
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/comments/{toid}")
		Call<List<Comments>> getUsersCommentsByToid( 
				
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
		Call<List<TagWikis>> getTagsWikisTagWikis( 
				
				@Path(value="tags", encoded=true) String tags,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users")
		Call<List<Users>> getUsers( 
				
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
		Call<List<UserTimelineObjects>> getUsersTimelineUserTimelineObjects( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/{ids}/timeline")
		Call<List<QuestionTimelineEvents>> getQuestionsTimelineQuestionTimelineEvents( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="fromdate", encoded=true) Integer fromdate,			
				@Query(value="todate", encoded=true) Integer todate,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/associated")
		Call<List<NetworkUsers>> getUsersAssociatedNetworkUsers( 
				
				@Path(value="ids", encoded=true) String ids,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/unanswered")
		Call<List<Questions>> getQuestionsUnansweredQuestions( 
				
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
		Call<List<Questions>> getUsersTagsTop_questionsQuestions( 
				
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
		Call<List<Notifications>> getUsersNotifications( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{id}/top-question-tags")
		Call<List<TopTagObjects>> getUsersTop_question_tagsTopTagObjects( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/users/{ids}/questions/no-answers")
		Call<List<Questions>> getUsersQuestionsNo_answersQuestions( 
				
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
		Call<List<Notifications>> getUsersNotificationsUnreadNotifications( 
				
				@Path(value="id", encoded=true) Integer id,			
				@Query(value="filter", encoded=true) String filter,			
				@Query(value="callback", encoded=true) String callback,			
				@Query(value="site", encoded=true) String site,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/questions/no-answers")
		Call<List<Questions>> getQuestionsNo_answersQuestions( 
				
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
		Call<List<Questions>> getSimilarQuestions( 
				
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