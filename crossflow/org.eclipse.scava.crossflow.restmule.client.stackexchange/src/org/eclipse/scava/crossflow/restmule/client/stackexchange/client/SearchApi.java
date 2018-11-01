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

public class SearchApi  {

	private static final Logger LOG = LogManager.getLogger(SearchApi.class);

	public static SearchBuilder create(){
		return new SearchBuilder(); 
	}
	
	public static ISearchApi createDefault(){ 
		return new SearchBuilder().setSession(StackExchangeSession.createPublic()).build(); 
	}
	
	/** BUILDER */
	public static class SearchBuilder 
	implements IClientBuilder<ISearchApi> { 
	
		private ISession session;
		private boolean activeCaching = true;
	
		@Override
		public ISearchApi build() {
			return (ISearchApi) new SearchClient(session, activeCaching);
		}
	
		@Override
		public IClientBuilder<ISearchApi> setSession(ISession session){
			this.session = session;
			return this;
		}
		
		@Override
		public IClientBuilder<ISearchApi> setActiveCaching(boolean activeCaching) {
			this.activeCaching = activeCaching;
			return this;
		}
	
	}
	
	/** CLIENT */
	private static class SearchClient extends AbstractClient<ISearchEndpoint> 
	implements ISearchApi 
	{
		private StackExchangePagination paginationPolicy;
		
		SearchClient(ISession session, boolean activeCaching) {
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

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(ISearchEndpoint.class);
			this.paginationPolicy = StackExchangePagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		@Override
		public IDataSet<Questions> getSearchAdvancedQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site, String accepted, Integer answers, String body, String closed, String migrated, String notice, String nottagged, String q, String title, String url, Integer user, Integer views, String wiki){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site, accepted, answers, body, closed, migrated, notice, nottagged, q, title, url, user, views, wiki};
			return paginationPolicy.<Questions, ISearchEndpoint> 
				traverseList("getSearchAdvancedQuestions", types, vals, callbackEndpoint);
		}
		
		@Override
		public IDataSet<Questions> getSearchQuestions(String tagged, String order, String max, String min, String sort, Integer fromdate, Integer todate, Integer pagesize, Integer page, String filter, String callback, String site, String intitle, String nottagged){
			Class<?>[] types = { String.class, String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class};
			Object[] vals = { tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site, intitle, nottagged};
			return paginationPolicy.<Questions, ISearchEndpoint> 
				traverseList("getSearchQuestions", types, vals, callbackEndpoint);
		}
		
		
	}
}
