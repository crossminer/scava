package org.eclipse.scava.crossflow.restmule.client.github.util;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_PASSWORD;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_SERVER;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_USER;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PASSWORD;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PERSONAL_ACCESS_TOKEN;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USERNAME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.github.api.GitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.cache.GitHubCacheManager;

public class GitHubUtils {

	private static final Logger LOG = LogManager.getLogger(GitHubUtils.class);

	public static String cacheServer;
	public static String cacheUser;
	public static String cachePassword;

	// private static ICache cache;

	private static String token;
	private static String username;
	private static String password;

	private static IGitHubApi publicApi;
	private static IGitHubApi basicApi;
	private static IGitHubApi oauthApi;

	protected static ISession publicSession;
	protected static ISession OAuthSessionWithToken;
	protected static ISession basicSession;

	public static IGitHubApi getOAuthClient() {

		setup();

		if (OAuthSessionWithToken != null && oauthApi == null) {
			oauthApi = GitHubApi.create().setSession(OAuthSessionWithToken).build();
			LOG.warn("Returning New OAuth client");
			return oauthApi;
		} else if (oauthApi != null) {
			LOG.warn("Returning OAuth client");
			return oauthApi;
		} else {
			LOG.warn("Returning Public client");
			return getPublicClient();
		}

	}

	/**
	 * By default, distributed caching is used.
	 *
	 * @param forceLocal if set 'true', local caching will be used rather than distributed caching
	 */
	public static void forceLocalCaching(boolean forceLocal) {

		setupCache(forceLocal);

		// LOG.debug("Returning Cache");
		// return cache;

	}

	private static void setup() {

		if (publicSession == null && basicSession == null) {

			LOG.info("setting up properties");

			if (PrivateProperties.exists()) {
				token = PrivateProperties.get(PERSONAL_ACCESS_TOKEN);
				username = PrivateProperties.get(USERNAME);
				password = PrivateProperties.get(PASSWORD);

				OAuthSessionWithToken = org.eclipse.scava.crossflow.restmule.client.github.session.GitHubSession
						.createWithBasicAuth(username, token);
				basicSession = org.eclipse.scava.crossflow.restmule.client.github.session.GitHubSession
						.createWithBasicAuth(username, password);
				LOG.info("set up authentication from properties file with github user: " + username);
			} else {
				LOG.info("authentication from properties file not set up");
			}

			publicSession = org.eclipse.scava.crossflow.restmule.client.github.session.GitHubSession.createPublic();

		}

	}

	private static void setupCache(boolean forceLocal) {

		if (cacheServer == null && cacheUser == null && cachePassword == null) {

			LOG.info("settings up cache ...");

			if (CacheProperties.exists() && !forceLocal) {
				LOG.info("found distributed cache properties file, using it for initializing a distribute cache");

				cacheServer = CacheProperties.get(CACHE_SERVER);
				cacheUser = CacheProperties.get(CACHE_USER);
				cachePassword = CacheProperties.get(CACHE_PASSWORD);

				//FIXME removed distributed cache
				System.err.println("distributed cache is removed, running local cache instead!");
				//
				//new GitHubCacheManager().setDistributedInstance();
				//new GitHubCacheManager().getCacheInstance().initializeDistributed(cacheServer, cacheUser, cachePassword);
				//
				new GitHubCacheManager().setLocalInstance();
				new GitHubCacheManager().getCacheInstance().initializeLocal();
				//
			} else {
				LOG.info("distributed cache properties file not found, initialising local cache");
				new GitHubCacheManager().setLocalInstance();
				new GitHubCacheManager().getCacheInstance().initializeLocal();
			}

			LOG.info("... finished setting up cache");

		}

	}

	/**
	 * (!) WARNING: THIS WILL WIPE OUT THE ENTIRE DISTRIBUTED CACHE
	 */
	protected static void clearGitHubCache() {
		new GitHubCacheManager().getCacheInstance().clear();
	}

	protected static void tearDownGitHubCache() {
		new GitHubCacheManager().getCacheInstance().tearDown();
	}

	public static IGitHubApi getBasicClient() {
		if (basicSession != null && basicApi == null) {
			basicApi = GitHubApi.create().setSession(basicSession).build();
			return basicApi;
		} else if (basicApi != null) {
			return basicApi;
		} else {
			LOG.warn("Returning Public client");
			return getPublicClient();
		}

	}

	// FIXME Public session crashes on handleTotal and handleResponse in
	// GitHubWrappedCallback
	public static IGitHubApi getPublicClient() {
		if (publicApi == null)
			publicApi = GitHubApi.create().setSession(publicSession).build();
		return publicApi;
	}

}
