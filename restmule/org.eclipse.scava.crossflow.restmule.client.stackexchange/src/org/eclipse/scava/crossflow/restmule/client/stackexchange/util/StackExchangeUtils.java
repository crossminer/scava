package org.eclipse.scava.crossflow.restmule.client.stackexchange.util;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_PASSWORD;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_SERVER;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CACHE_USER;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PASSWORD;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PERSONAL_ACCESS_TOKEN;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USERNAME;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.KEY;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.ACCESS_TOKEN;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CLIENT_ID;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.CLIENT_SECRET;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.StackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.IStackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.cache.StackExchangeCacheManager;

public class StackExchangeUtils {

	private static final Logger LOG = LogManager.getLogger(StackExchangeUtils.class);

	public static String cacheServer;
	public static String cacheUser;
	public static String cachePassword;

	// private static ICache cache;

	private static String token;
	private static String username;
	private static String password;
	private static String key;
	private static String client_id;
	private static String client_secret;
	private static String access_token;

	private static IStackExchangeApi publicApi;
	private static IStackExchangeApi basicApi;
	private static IStackExchangeApi oauthApi;

	protected static ISession publicSession;
	protected static ISession OAuthSessionWithToken;
	protected static ISession basicSession;

	public static IStackExchangeApi getOAuthClient() {

		setup();

		if (OAuthSessionWithToken != null && oauthApi == null) {
			oauthApi = StackExchangeApi.create().setSession(OAuthSessionWithToken).build();
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

	protected static void setup() {

		if (publicSession == null && basicSession == null) {

			LOG.info("setting up properties");

			if (PrivateProperties.exists()) {
				token = PrivateProperties.get(PERSONAL_ACCESS_TOKEN);
				username = PrivateProperties.get(USERNAME);
				password = PrivateProperties.get(PASSWORD);
				key = PrivateProperties.get(KEY);
				client_id = PrivateProperties.get(CLIENT_ID);
				client_secret = PrivateProperties.get(CLIENT_SECRET);
				access_token = PrivateProperties.get(ACCESS_TOKEN);

			
				OAuthSessionWithToken = org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession
					.createWithBasicAuth(key, access_token); // this will only work if token has manually been obtained via https server running on the same domain as api client
				
				basicSession = org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession
					.createWithBasicAuth(key);
				
				if ( key != null && !key.isEmpty() )
					LOG.info("set up authentication from properties file with key: " + key); // TODO: remove this (should not be logged !)
				if ( access_token != null && !access_token.isEmpty() )
					LOG.info("set up authentication from properties file with access_token: " + access_token); // TODO: remove this (should not be logged !)
			} else {
				LOG.info("authentication from properties file not set up");
			}

			publicSession = org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession.createPublic();

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
				//new StackExchangeCacheManager().setDistributedInstance();
				//new StackExchangeCacheManager().getCacheInstance().initializeDistributed(cacheServer, cacheUser, cachePassword);
				//
				new StackExchangeCacheManager().setLocalInstance();
				new StackExchangeCacheManager().getCacheInstance().initializeLocal();
				//
			} else {
				LOG.info("distributed cache properties file not found, initialising local cache");
				new StackExchangeCacheManager().setLocalInstance();
				new StackExchangeCacheManager().getCacheInstance().initializeLocal();
			}

			LOG.info("... finished setting up cache");

		}

	}

	/**
	 * (!) WARNING: THIS WILL WIPE OUT THE ENTIRE DISTRIBUTED CACHE
	 */
	protected static void clearStackExchangeCache() {
		new StackExchangeCacheManager().getCacheInstance().clear();
	}

	protected static void tearDownStackExchangeCache() {
		new StackExchangeCacheManager().getCacheInstance().tearDown();
	}

	public static IStackExchangeApi getBasicClient() {
		if (basicSession != null && basicApi == null) {
			basicApi = StackExchangeApi.create().setSession(basicSession).build();
			return basicApi;
		} else if (basicApi != null) {
			return basicApi;
		} else {
			LOG.warn("Returning Public client");
			return getPublicClient();
		}

	}

	// FIXME Public session crashes on handleTotal and handleResponse in
	// StackExchangeWrappedCallback
	public static IStackExchangeApi getPublicClient() {
		if (publicApi == null)
			publicApi = StackExchangeApi.create().setSession(publicSession).build();
		return publicApi;
	}

}
