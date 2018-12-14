package org.eclipse.scava.crossflow.restmule.client.gitlab.client;

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
import org.eclipse.scava.crossflow.restmule.client.gitlab.cache.GitlabCacheManager;
import org.eclipse.scava.crossflow.restmule.client.gitlab.interceptor.GitlabInterceptor;
import org.eclipse.scava.crossflow.restmule.client.gitlab.model.*;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPaged;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPagination;
import org.eclipse.scava.crossflow.restmule.client.gitlab.session.GitlabSession;
import org.eclipse.scava.crossflow.restmule.client.gitlab.util.GitlabPropertiesUtil;

import okhttp3.OkHttpClient.Builder;

public class SearchApi  {

	private static final Logger LOG = LogManager.getLogger(SearchApi.class);

	public static SearchBuilder create(){
		return new SearchBuilder(); 
	}
	
	public static ISearchApi createDefault(){ 
		return new SearchBuilder().setSession(GitlabSession.createPublic()).build(); 
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
		private GitlabPagination paginationPolicy;
		
		SearchClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, GitlabSession.class, session.id());
			GitlabInterceptor interceptors = new GitlabInterceptor(session);
			String baseurl = GitlabPropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/")) baseurl += "/"; // FIXME Validate in Model with EVL 

			Builder clientBuilder = AbstractClient.okHttp(executor);
			
			ICache localcache = new GitlabCacheManager().getCacheInstance();
			if (activeCaching && localcache != null && !localcache.isDistributed()) {
				clientBuilder = clientBuilder.cache(localcache.initializeLocal());
				LOG.info("enabling local okhttp cache");
			}
						
			clientBuilder = clientBuilder.addNetworkInterceptor(CacheControlInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR);
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));
						
			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(ISearchEndpoint.class);
			this.paginationPolicy = GitlabPagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */
	
		
	}
}
