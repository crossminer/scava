package org.eclipse.scava.crossflow.restmule.core.interceptor;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.cache.AbstractCacheManager;
import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.session.AbstractSession;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.core.util.OkHttpUtil;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * {@link AbstractInterceptor}
 * <p>
 * 
 * @version 1.0.0
 *
 */
public abstract class AbstractInterceptor {
	
	private static final CacheControl FORCE_NETWORK = new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build();
	private static final String TAG_FORCE_NETWORK = "FORCE NETWORK";

	private static final Logger LOG = LogManager.getLogger(AbstractInterceptor.class);
	protected static final String TAG_FROM_CACHE = "FROM CACHE";

	protected static String headerLimit;
	protected static String headerRemaining;
	protected static String headerReset;
	protected static String userAgent;
	protected static String accept;

	protected static ICache cache;
	protected static Class<? extends AbstractSession> sessionClass;

	public static final Interceptor mainInterceptor(boolean activateCaching, String sessionId, AbstractCacheManager c) {
		return new Interceptor() {

			private AtomicInteger remainingRequestCounter = new AtomicInteger(1);

			@Override
			public Response intercept(Chain chain) throws IOException {

				if (activateCaching)
					cache = c.getCacheInstance();

				remainingRequestCounter.decrementAndGet();
				Request request = chain.request();

				ISession session = AbstractSession.getSession(sessionClass, sessionId);
				Headers headers = headers(accept, userAgent, cache, session, request);

				LOG.info(session.id() + " INTERCEPTOR (" + remainingRequestCounter.get() + ")");

				Request.Builder requestBuilder = request.newBuilder().headers(headers);
				Request networkRequest = null;

				//
				// request.headers();
				// request.body();
				//

				// end of loop -- terminate
				if (session.isSet().get() && (remainingRequestCounter.get() + session.cacheCounter().get()) == 0) {
					LOG.info("UNSETTING SESSION");
					session.unset();
				}
				Response response = null;
				// session not set -- go to network to get session info
				if (!session.isSet().get()) {
					requestBuilder.cacheControl(FORCE_NETWORK).tag(TAG_FORCE_NETWORK);
					networkRequest = requestBuilder.build();
					// LOG.debug("networkRequest.headers() = " +
					// networkRequest.headers());

					response = chain.proceed(networkRequest);
				}
				// session set -- try to use cache or go to network
				else {

					if (cache != null) {
						if (cache.isDistributed()) {
							try {
								response = cache.load(request);
							} catch (Exception e) {
								LOG.error("Unable to load cache entry for request: " + request.url());
								LOG.error(e.getMessage());
							}
							if (response != null) {
								session.cacheCounter().incrementAndGet();
								LOG.info("Response obtained from distributed CACHE");
								LOG.info(TAG_FROM_CACHE + ", cacheCounter=" + session.cacheCounter().get());
								LOG.info(response.message());
								return response;
							}
							LOG.info("Tried to get response from distributed CACHE but it is not there");
							requestBuilder.cacheControl(FORCE_NETWORK).tag(TAG_FORCE_NETWORK);
							networkRequest = requestBuilder.build();
							response = chain.proceed(networkRequest);

						} else {
							// local cache code
							requestBuilder.tag(TAG_FROM_CACHE);
							networkRequest = requestBuilder.build();
							response = chain.proceed(networkRequest);
							if (response.cacheResponse() != null
									&& response.cacheResponse().code() != HttpStatus.SC_GATEWAY_TIMEOUT) {
								session.cacheCounter().incrementAndGet();
								LOG.info("Response obtained from local CACHE");
								LOG.info(TAG_FROM_CACHE + ", cacheCounter=" + session.cacheCounter().get());
								LOG.info(response.message());
								peekResponse(response);
								return response;
							}
							LOG.info("Tried to get responce from local CACHE but it is not there");
						}
					} else {
						LOG.warn("Cache disabled, if this is not intended make sure you initialise the cache.");
						// not in cache
						requestBuilder.cacheControl(FORCE_NETWORK).tag(TAG_FORCE_NETWORK);
						networkRequest = requestBuilder.build();
						response = chain.proceed(networkRequest);
					}
				}

				LOG.info("DEALING WITH NETWORK RESPONSE");

				if (response.networkResponse() != null) {
					// erroneous response
					if (!response.networkResponse().isSuccessful()) {
						int code = response.networkResponse().code();
						if (code != HttpStatus.SC_NOT_MODIFIED) {
							while (code == HttpStatus.SC_FORBIDDEN) {
								peekResponse(response);
								try {
									long ms = 1000 * 60;
									if (session.isSet().get()) {
										ms = session.getRateLimitResetInMilliSeconds() - System.currentTimeMillis();
									}
									if (ms > 0) {
										LOG.info("RETRYING IN " + (ms / 1000) + " s");
										TimeUnit.MILLISECONDS.sleep(ms);
									}
									LOG.info("RETRYING NOW");
									response = chain.proceed(networkRequest);
									code = response.code();
								} catch (InterruptedException e) {
									LOG.info(e.getMessage());
									e.printStackTrace();
								}
							}
							if (!response.networkResponse().isSuccessful()) {
								LOG.error("UNKNOWN ERROR CODE: " + code);
								throw new UnsupportedOperationException(
										"Do not know how to handle this type of error code: " + code);
							}
						}
					}
					// normal response
					peekResponse(response);
					LOG.info("UPDATING SESSION DETAILS FROM NETWORK RESPONSE");
					// LOG.info(networkResponse.networkResponse().headers());
					session.setRateLimit(response.networkResponse().header(headerLimit));
					session.setRateLimitReset(response.networkResponse().header(headerReset));
					session.setRateLimitRemaining(response.networkResponse().header(headerRemaining));
					remainingRequestCounter.set(session.getRateLimitRemaining().get() + 1);
					LOG.info(session);

					Response networkResponseClone = OkHttpUtil.clone(response);

					if (cache != null) {
						if (cache.isDistributed())
							cache.put(networkRequest, networkResponseClone);
						else {
							// local cache code - handled by okhttp
						}
					}

					return response;
				}

				LOG.info("SOMETHING WENT WRONG");
				return null;
			}

			private void peekResponse(Response response) throws IOException {
				if (response.networkResponse() != null) {
					try {
						LOG.debug(
								"NW:" + response.networkResponse().code() + ":" + response.networkResponse().message());
					} catch (Exception e) {
						LOG.error(e.getMessage());
						e.printStackTrace();
					}
				}
				if (response.cacheResponse() != null) {
					try {
						LOG.debug(
								"CACHE:" + response.cacheResponse().code() + ":" + response.cacheResponse().message());
					} catch (Exception e) {
						LOG.error(e.getMessage());
						e.printStackTrace();
					}
				}
				LOG.debug(response.code() + ":" + response.message());

				ResponseBody body = response.peekBody(1000000); // increase to
																// see more
																// debug output
				Reader bodyStream = body.charStream();

				BufferedReader reader = new BufferedReader(bodyStream);
				reader.lines().forEach(l -> LOG.info(l));

				// Close Body and Readers
				bodyStream.close();
				body.close();
				reader.close();
			}

			private Headers headers(final String accept, final String userAgent, final ICache cache, ISession session,
					Request originalRequest) {

				Headers.Builder headerBuilder = new Headers.Builder();
				headerBuilder = headerBuilder.add(USER_AGENT, userAgent);
				headerBuilder = headerBuilder.add(ACCEPT, String.valueOf(accept));
				// headerBuilder = headerBuilder.add(ACCEPT,
				// "application/vnd.github.v3+json"); // TODO should not be
				// hardcoded here ..
				Headers headers = headerBuilder.build();

				return OkHttpUtil.headers(session, originalRequest, headers);
			}
		};

	}

	protected static final Interceptor cacheRequestInterceptor(final ICache cache, final String sessionId) {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				// TODO remove. It is a temporary fix using okhttp
				Request request = chain.request();
				Response response = chain.proceed(request);// getOkttpResponse(session,
				// chain);
				/*
				 * chain.proceed(request); Response cacheResponse =
				 * response.cacheResponse(); LOG.info("b"); if (cacheResponse !=
				 * null){ LOG.info("RETURNING RESPONSE FROM CACHE");
				 * cacheKeys.add(Cache.key(request.url())); return
				 * cacheResponse; } else { return response; }
				 */
				return response;

				// TODO uncomment
				/*
				 * Request request = chain.request(); LOG.info(request.url());
				 * ISession session = AbstractSession.getSession(sessionClass,
				 * sessionId); LOG.info(session); if (cache.exists(request,
				 * session)) { Response response = cache.load(request, session);
				 * LOG.info("RETURNING RESPONSE FROM CACHE"); return response; }
				 * return chain.proceed(request);
				 */
			}
		};
	}

	protected static final Interceptor sessionRequestInterceptor(final String userAgent, final String accept,
			final String sessionId) {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				ISession session = AbstractSession.getSession(sessionClass, sessionId);
				return chain.proceed(chain.request().newBuilder().header(USER_AGENT, userAgent).header(ACCEPT, accept)
						.headers(session.getHeaders()).build());
			}
		};
	}

	protected static final Interceptor sessionResponseInterceptor(final String limit, final String remaining,
			final String reset, final String sessionId) {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				ISession session = AbstractSession.getSession(sessionClass, sessionId);

				Response response = chain.proceed(chain.request());// getOkttpResponse(session,
				// chain);

				session.setRateLimit(response.header(limit));
				session.setRateLimitReset(response.header(reset));
				session.setRateLimitRemaining(response.header(remaining));

				LOG.info(session);
				return response;
			}
		};
	}

	protected static final Interceptor cacheResponseInterceptor(final ICache cache, final String sessionId) {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

				// TODO comment this section. This is a temporary fix
				return chain.proceed(chain.request());

				// TODO Delegate this to an entity to do this asynchronously and
				// DONT wait until the cache persistence is finished

				/*
				 * Request request = chain.request(); Response response =
				 * chain.proceed(request); ISession session =
				 * AbstractSession.getSession(sessionClass, sessionId);
				 *
				 * String code = String.valueOf(response.code()); if
				 * (code.startsWith("2") || code.startsWith("3")){ if
				 * (response.code() == SC_NOT_MODIFIED) {
				 * LOG.info(SC_NOT_MODIFIED); return cache.load(request,
				 * session); } else { LOG.info("PERSISTING RESPONSE IN CACHE");
				 * Response clonedResponse = OkHttpUtil.clone(response);
				 * cache.put(clonedResponse, session); return clonedResponse; }
				 * } else { LOG.error("Something went wrong : " +response.code()
				 * + " " + response.message() ) ; return response; }
				 */
			}
		};
	}

	protected static final Interceptor sessionResponseRetryInterceptor(final String limit, final String remaining,
			final String reset) {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Response response = chain.proceed(chain.request());
				if (response.code() == SC_FORBIDDEN && response.header(remaining).equals("0")) {
					return chain.proceed(chain.request().newBuilder().build());
				}
				return response;
			}
		};
	}

	protected static Response getOkttpResponse(ISession session, Chain chain) {
		Response response = null;
		try {
			response = chain.proceed(chain.request());
			if (// session.isCacheable() && FIXME allow this
			response.cacheResponse() != null) {
				response = response.cacheResponse();
			} else {
				response = response.networkResponse();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}
}