package org.eclipse.scava.crossflow.restmule.client.stackexchange.client;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.*;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Error;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePaged;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface ISearchEndpoint {

	
		@GET("/search/advanced")
		Call<StackExchangePaged<QuestionsResponse>> getSearchAdvancedQuestionsResponse( 
				
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
				@Query(value="accepted", encoded=true) String accepted,			
				@Query(value="answers", encoded=true) Integer answers,			
				@Query(value="body", encoded=true) String body,			
				@Query(value="closed", encoded=true) String closed,			
				@Query(value="migrated", encoded=true) String migrated,			
				@Query(value="notice", encoded=true) String notice,			
				@Query(value="nottagged", encoded=true) String nottagged,			
				@Query(value="q", encoded=true) String q,			
				@Query(value="title", encoded=true) String title,			
				@Query(value="url", encoded=true) String url,			
				@Query(value="user", encoded=true) Integer user,			
				@Query(value="views", encoded=true) Integer views,			
				@Query(value="wiki", encoded=true) String wiki,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
		@GET("/search")
		Call<StackExchangePaged<QuestionsResponse>> getSearchQuestionsResponse( 
				
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
				@Query(value="intitle", encoded=true) String intitle,			
				@Query(value="nottagged", encoded=true) String nottagged,			
				@Query(value="pagesize", encoded=true) Integer pagesize,			
				@Query(value="page", encoded=true) Integer page);
	
}