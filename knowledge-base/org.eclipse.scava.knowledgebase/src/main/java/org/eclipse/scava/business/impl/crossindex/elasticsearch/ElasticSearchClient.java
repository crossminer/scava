package org.eclipse.scava.business.impl.crossindex.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticSearchClient {

	private static RestHighLevelClient client;
	
	static {
		client = ElasticSearchSingleton.getInstance().getClient();
	}
	
	public static SearchHits queryBoostedQueryIndex(String boostedQuery, String field, int resultsSize) throws IOException
	{
		if(client==null)
			return null;
		
		QueryBuilder query = QueryBuilders.queryStringQuery(boostedQuery).defaultField(field);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(query);
		sourceBuilder.size(resultsSize);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest);
		return searchResponse.getHits();
	}
	
	
}
