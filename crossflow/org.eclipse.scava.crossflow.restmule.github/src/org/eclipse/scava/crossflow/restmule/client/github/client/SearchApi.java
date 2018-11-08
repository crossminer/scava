package org.eclipse.scava.crossflow.restmule.client.github.client;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.API_BASE_URL;

import java.util.concurrent.ExecutorService;

import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.client.AbstractClient;
import org.eclipse.scava.crossflow.restmule.core.client.IClientBuilder;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.crossflow.restmule.core.interceptor.CacheControlInterceptor;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.core.session.RateLimitExecutor;
import org.eclipse.scava.crossflow.restmule.client.github.cache.GitHubCacheManager;
import org.eclipse.scava.crossflow.restmule.client.github.interceptor.GitHubInterceptor;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchCode;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchIssues;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchRepositories;
import org.eclipse.scava.crossflow.restmule.client.github.model.SearchUsers;
import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubPaged;
import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubPagination;
import org.eclipse.scava.crossflow.restmule.client.github.session.GitHubSession;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubPropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.OkHttpClient.Builder;

public class SearchApi {

	private static final Logger LOG = LogManager.getLogger(AbstractInterceptor.class);

	public static SearchBuilder create() {
		return new SearchBuilder();
	}

	public static ISearchApi createDefault() {
		return new SearchBuilder().setSession(GitHubSession.createPublic()).build();
	}

	/** BUILDER */
	public static class SearchBuilder implements IClientBuilder<ISearchApi> {

		private ISession session;
		private boolean activeCaching = true;

		@Override
		public ISearchApi build() {
			return (ISearchApi) new SearchClient(session, activeCaching);
		}

		@Override
		public IClientBuilder<ISearchApi> setSession(ISession session) {
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
	private static class SearchClient extends AbstractClient<ISearchEndpoint> implements ISearchApi {
		private GitHubPagination paginationPolicy;

		SearchClient(ISession session, boolean activeCaching) {
			super();

			ExecutorService executor = RateLimitExecutor.create(30, GitHubSession.class, session.id());
			GitHubInterceptor interceptors = new GitHubInterceptor(session);
			String baseurl = GitHubPropertiesUtil.get(API_BASE_URL);

			if (!baseurl.endsWith("/"))
				baseurl += "/"; // FIXME Validate in Model with EVL

			Builder clientBuilder = AbstractClient.okHttp(executor);

			ICache localcache = new GitHubCacheManager().getCacheInstance();
			if (activeCaching && localcache != null && !localcache.isDistributed()) {
				clientBuilder = clientBuilder.cache(localcache.initializeLocal());
				LOG.info("enabling local okhttp cache");
			}

			// FIXME update generator
			clientBuilder = clientBuilder
					.addNetworkInterceptor(CacheControlInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR);
			clientBuilder = clientBuilder.addInterceptor(interceptors.mainInterceptor(activeCaching));

			this.client = clientBuilder.build();

			this.callbackEndpoint = AbstractClient.retrofit(client, baseurl).create(ISearchEndpoint.class);
			this.paginationPolicy = GitHubPagination.get();
		}

		/** WRAPED METHODS FOR PAGINATION */

		@Override
		public IDataSet<SearchUsers> getSearchUsers(String order, String q, String sort) {
			Class<?>[] types = { String.class, String.class, String.class };
			Object[] vals = { order, q, sort };
			return paginationPolicy.<SearchUsers, GitHubPaged<SearchUsers>, ISearchEndpoint> traverse("getSearchUsers",
					types, vals, callbackEndpoint);
		}

		@Override
		public IDataSet<SearchCode> getSearchCode(String order, String q, String sort) {
			Class<?>[] types = { String.class, String.class, String.class };
			Object[] vals = { order, q, sort };
			return paginationPolicy.<SearchCode, GitHubPaged<SearchCode>, ISearchEndpoint> traverse("getSearchCode",
					types, vals, callbackEndpoint);
		}

		@Override
		public IDataSet<SearchIssues> getSearchIssues(String order, String q, String sort) {
			Class<?>[] types = { String.class, String.class, String.class };
			Object[] vals = { order, q, sort };
			return paginationPolicy.<SearchIssues, GitHubPaged<SearchIssues>, ISearchEndpoint> traverse(
					"getSearchIssues", types, vals, callbackEndpoint);
		}

		@Override
		public IDataSet<SearchRepositories> getSearchRepositories(String order, String q, String sort) {
			Class<?>[] types = { String.class, String.class, String.class };
			Object[] vals = { order, q, sort };
			return paginationPolicy.<SearchRepositories, GitHubPaged<SearchRepositories>, ISearchEndpoint> traverse(
					"getSearchRepositories", types, vals, callbackEndpoint);
		}

	}
}
