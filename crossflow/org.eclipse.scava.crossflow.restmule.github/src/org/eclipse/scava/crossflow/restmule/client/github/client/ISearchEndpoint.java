package org.eclipse.scava.crossflow.restmule.client.github.client;

import java.util.List;

import org.eclipse.scava.crossflow.restmule.client.github.model.*;
import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubPaged;

import io.reactivex.Observable;
import retrofit2.Call; 
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearchEndpoint {

	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/search/users")
		Call<GitHubPaged<SearchUsers>> getSearchUsers(			
				@Query(value="order", encoded=true) String order,			
				@Query(value="q", encoded=true) String q,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/search/code")
		Call<GitHubPaged<SearchCode>> getSearchCode(			
				@Query(value="order", encoded=true) String order,			
				@Query(value="q", encoded=true) String q,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/search/issues")
		Call<GitHubPaged<SearchIssues>> getSearchIssues(			
				@Query(value="order", encoded=true) String order,			
				@Query(value="q", encoded=true) String q,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
		// FIXME using endocde=true caused Lucene (cache) to break
		@GET("/search/repositories")
		Call<GitHubPaged<SearchRepositories>> getSearchRepositories(			
				@Query(value="order", encoded=true) String order,			
				@Query(value="q", encoded=true) String q,			
				@Query(value="sort", encoded=true) String sort,			
				@Query(value="per_page", encoded=true) Integer per_page,			
				@Query(value="page", encoded=true) Integer page);
	
}