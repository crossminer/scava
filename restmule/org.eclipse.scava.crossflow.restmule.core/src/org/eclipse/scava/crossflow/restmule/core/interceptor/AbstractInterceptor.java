package org.eclipse.scava.crossflow.restmule.core.interceptor;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.cache.AbstractCacheManager;
import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers.OkHttp3Response;
import org.eclipse.scava.crossflow.restmule.core.session.AbstractSession;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.core.util.OkHttpUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Request.Builder;
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
			private ISession session;

			@Override
			public Response intercept(Chain chain) throws IOException {

				System.out.println("AbstractInterceptor.intercept( " +chain.request().url() + " )" );
				
				if (activateCaching)
					cache = c.getCacheInstance();

				remainingRequestCounter.decrementAndGet();
				Request request = chain.request();

				session = AbstractSession.getSession(sessionClass, sessionId);
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
					networkRequest = getFilteredRequest(request, requestBuilder, false);
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
							networkRequest = getFilteredRequest(request, requestBuilder, true);
							response = chain.proceed(networkRequest);

						} else {
							// local cache code
							requestBuilder.tag(TAG_FROM_CACHE);
							networkRequest = getFilteredRequest(request, requestBuilder, true);
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
						networkRequest = getFilteredRequest(request, requestBuilder, true);
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
					updateSessionDetails(response);

					LOG.info(session);

					if (cache != null) {
						if (cache.isDistributed()) {
							Response networkResponseClone = OkHttpUtil.clone(response);
							cache.put(networkRequest, networkResponseClone);
						} else {
							// local cache code - handled by okhttp
						}
					}
					return response;
				}

				LOG.info("SOMETHING WENT WRONG");
				return null;
			}

			/**
			 * @param response
			 * @throws IOException
			 */
			private void updateSessionDetails(Response response) throws IOException {
				JsonElement jsonResponse = peekResponse(response);

				LOG.info("UPDATING SESSION DETAILS FROM NETWORK RESPONSE");
				// LOG.info(networkResponse.networkResponse().headers());

				String knownHeaderLimit = response.networkResponse().header(headerLimit);
				String knownHeaderRemaining = response.networkResponse().header(headerRemaining);
				String knownHeaderReset = response.networkResponse().header(headerReset);

				if ( knownHeaderLimit != null ) {
					session.setRateLimit( knownHeaderLimit );
				}// knownHeaderLimit

				if ( knownHeaderRemaining != null) {
					session.setRateLimitRemaining( knownHeaderRemaining );
					remainingRequestCounter.set(session.getRateLimitRemaining().get() + 1);
				}// knownHeaderRemaining
				
				if ( knownHeaderReset != null) {
					session.setRateLimitReset( knownHeaderReset );
				}// knownHeaderReset

				if ( headerReset.contentEquals("midnight") ) {
					session.setRateLimitReset( getMidnightPlusOne() );
				}// headerReset = midnight
				LOG.info("Rate limit reset will occur at " + session.getRateLimitReset() );
				
				// ---- START: STACKEXCHANGE API-SPECIFIC (TODO: move to API-specific client?) -----
				if ( response.request().url().toString().startsWith("https://api.stackexchange.com") && jsonResponse instanceof JsonObject ) {
					JsonObject jsonObject = (JsonObject) jsonResponse;
					Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
					
					for (Map.Entry<String, JsonElement> entry : entrySet) {
						
						JsonElement entryValue = entry.getValue();
						if ( entry.getKey().equals("has_more") ) {
							LOG.info("Response indicates that there are (!) still items to be retrieved.");
							if ( !entry.getValue().getAsBoolean() ) {
								LOG.info("NO MORE RESULTS --- UNSETTING SESSION.");
								session.unset();
							}
							
						} else if ( entry.getKey().equals(headerLimit) ) {
							session.setRateLimit( entryValue.getAsString() );
							LOG.info("Rate limit set from response: " + entryValue);
							
						} else if ( entry.getKey().equals(headerRemaining) ) {
							session.setRateLimitRemaining( entryValue.getAsString() );
							remainingRequestCounter.set(session.getRateLimitRemaining().get() + 1);
							
						} 
						else if ( entry.getKey().equals("total") ) {
							LOG.info("Total number of items to be retrieved: " + entryValue );
							
							if ( session.getRateLimitRemaining().intValue() <= 0 ) {
								// assuming that there are calls remaining 
								LOG.info("Setting rate limit by assuming (!) that the total number of items (" + entryValue + ") can be retrieved.");
								String totalPlusOne = String.valueOf(entryValue.getAsInt()+1);
								session.setRateLimitRemaining( totalPlusOne );
								session.setRateLimit( totalPlusOne );
								remainingRequestCounter.set( Integer.parseInt(totalPlusOne) );
								
							} else {
								session.setRateLimitRemaining( String.valueOf(session.getRateLimitRemaining().get() + 1) );
								session.setRateLimit( entryValue.getAsString() );
								remainingRequestCounter.set( entryValue.getAsInt() );
							}

						}
					}
				} else if ( jsonResponse instanceof JsonArray ) {
					// not required
				}
				// ---- END: STACKEXCHANGE API-SPECIFIC -----
			}

			private String getMidnightPlusOne() {
				LocalTime midnight = LocalTime.MIDNIGHT;
				LocalDate today = LocalDate.now(ZoneId.of("UTC"));
				LocalDateTime tomorrowMidnight = LocalDateTime.of(today, midnight).plusDays(1);
				String todayMidnightPlusOneString = String.valueOf( tomorrowMidnight.toEpochSecond(ZoneOffset.UTC)+1);
				return todayMidnightPlusOneString;
			}

			/**
			 * Make sure to include asking for the total number of items on the first call to StackExchange API
			 * 
			 * @param request
			 * @param requestBuilder
			 * @param session 
			 * @return
			 */
			private Request getFilteredRequest(Request request, Builder requestBuilder, boolean filterTotal) {
				String requestUrl = request.url().toString();
				
				// ---- START: STACKEXCHANGE API-SPECIFIC -----
				// TODO: eventually move API-specific code from core to API-specific project interceptor
				if ( requestUrl.startsWith("https://api.stackexchange.com") ) {
					
					if ( filterTotal ) {
						// remove filter=total (only required for initial request)
						requestUrl = requestUrl.replace("filter=total", "");
						
					} else if ( !filterTotal && !requestUrl.contains("filter=total") ) {
						requestUrl = requestUrl.concat("&filter=total");
						
					}
					
					if ( !session.isHeader() && session.key() != null && !session.key().isEmpty() && !requestUrl.contains("key=") ) {
						// attach api key to url if non-header session
						requestUrl = requestUrl.concat("&key="+session.key());
					}
				}
				// ---- END: STACKEXCHANGE API-SPECIFIC -----
//				System.out.println("requestUrl="+requestUrl);
				return requestBuilder.url(requestUrl).build();						
			}// getFilteredRequest

			private JsonElement peekResponse(Response response) throws IOException {
				JsonElement jsonResponse = null;
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

				BufferedReader bufferedReader = new BufferedReader(bodyStream);
				String bodyString = bufferedReader.lines().collect(Collectors.joining());
//				System.out.println(
//						"RESPONSE PEEK BODY:   " + bodyString);
				Gson gson = new Gson();
				JsonReader jsonReader = new JsonReader(new StringReader(bodyString));
				jsonReader.setLenient(true);
				jsonResponse = gson.fromJson(new JsonReader(new StringReader(bodyString)), JsonElement.class);

				// Close Body and Readers
				bodyStream.close();
				body.close();
				bufferedReader.close();
				jsonReader.close();
				
				return jsonResponse;
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
				 * chain.proceed(request); Response cacheResponse = response.cacheResponse();
				 * LOG.info("b"); if (cacheResponse != null){
				 * LOG.info("RETURNING RESPONSE FROM CACHE");
				 * cacheKeys.add(Cache.key(request.url())); return cacheResponse; } else {
				 * return response; }
				 */
				return response;

				// TODO uncomment
				/*
				 * Request request = chain.request(); LOG.info(request.url()); ISession session
				 * = AbstractSession.getSession(sessionClass, sessionId); LOG.info(session); if
				 * (cache.exists(request, session)) { Response response = cache.load(request,
				 * session); LOG.info("RETURNING RESPONSE FROM CACHE"); return response; }
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
				 * String code = String.valueOf(response.code()); if (code.startsWith("2") ||
				 * code.startsWith("3")){ if (response.code() == SC_NOT_MODIFIED) {
				 * LOG.info(SC_NOT_MODIFIED); return cache.load(request, session); } else {
				 * LOG.info("PERSISTING RESPONSE IN CACHE"); Response clonedResponse =
				 * OkHttpUtil.clone(response); cache.put(clonedResponse, session); return
				 * clonedResponse; } } else { LOG.error("Something went wrong : "
				 * +response.code() + " " + response.message() ) ; return response; }
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