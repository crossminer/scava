package org.eclipse.scava.workflow.restmule.core.session;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import okhttp3.Cache;
import okhttp3.Headers;

/**
 * 
 * {@link AbstractSession}
 * <p>
 * @version 1.0.0
 *
 */
public abstract class AbstractSession implements ISession {

	/** FIELDS */

	private static final Logger LOG = LogManager.getLogger(AbstractSession.class);
	
	private String id;

	protected Integer rateLimit = -1;
	protected AtomicInteger rateLimitRemaining = new AtomicInteger(-1);
	protected Date rateLimitReset;
	protected AtomicBoolean isSet = new AtomicBoolean(false);
	private AtomicInteger cachedCounter = new AtomicInteger(0);

	protected Auth type = Auth.NO_AUTH;
	protected boolean isHeader = true;
	protected String token = "";

	protected String hash;

	private String receiverHost = "localhost";
	private int receiverPort = 9000;
	
	
	protected AbstractSession() {
		setPublic();
		this.id = generateRandomId();
	}

	protected AbstractSession(@Nonnull String username, @Nonnull String password) {
		setBasicUsernamePassword(username, password);
		this.id = generateRandomId();
	}

	protected AbstractSession(@Nonnull String token) {
		setBasicAccessTokenInHeader(token);
		this.id = generateRandomId();
	}

	protected AbstractSession(@Nonnull String accessTokenUrl, @Nonnull String authorizationUrl,
			@Nonnull String clientId, @Nonnull String clientSecret, @Nonnull List<String> scopes,
			@Nonnull String username) {
		setOAuthToken(accessTokenUrl, authorizationUrl, clientId, clientSecret, scopes, username);
		this.id = generateRandomId();
	}

	/** METHODS */

	@Override
	public Integer getRateLimit() {
		return rateLimit;
	}

	@Override
	public Headers getHeaders() {
		return type.header(this.token);
	}

	@Override
	public AtomicInteger getRateLimitRemaining() {
		return rateLimitRemaining;
	}

	@Override
	public Date getRateLimitReset() {
		return rateLimitReset;
	}

	@Override
	public long getRateLimitResetInMilliSeconds() {
		if (rateLimitReset != null){
			return rateLimitReset.getTime();
		} else{
			return -1;
		}
	}

	@Override
	public AtomicBoolean isSet() {
		if (rateLimit != -1 && rateLimitRemaining.get() != -1 && rateLimitReset != null){
			isSet.set(true);
		} else {
			isSet.set(false);
		}
		return this.isSet;
	}
	
	@Override
	public void unset() {
		rateLimit = -1;
		rateLimitRemaining.set(-1);
		rateLimitReset = null;
		isSet.set(false);
	}

	@Override
	public Auth type() {
		return this.type;
	}

	@Override
	public String hash() {
		if (this.hash == null) {
			this.hash = String.valueOf(String.valueOf(token).hashCode());
		}
		return this.hash;
	}

	@Override
	public boolean isHeader() {
		return this.isHeader;
	}

	@Override
	public String id() {
		return this.id;
	}

	
	Cache cache; // FIXME
	@Override
	public Cache cache() { // FIXME
		return this.cache;
	}
	
	@Override
	public boolean isCacheable() {// FIXME
		return this.cache != null;
	}
	
	@Override
	public AtomicInteger cacheCounter() {// FIXME
		if (cachedCounter== null){
			this.cachedCounter = new AtomicInteger(0);
		}
		return cachedCounter;
	}
	
	@Override
	public void resetCacheCounter() {// FIXME
		cachedCounter.set(0);
	}
	
	protected String getToken() {
		return this.token;
	}

	protected void setPublic() {
		this.type = Auth.NO_AUTH;
	}

	protected void setReceiverHost(String host) {
		this.receiverHost = host;
	}

	protected void setReceiverPort(int port) {
		this.receiverPort = port;
	}

	protected void setBasicAccessTokenInHeader(@Nonnull final String token) {
		this.type = Auth.OAUTH;
		this.token = token;
	}

	protected void setBasicAccessTokenInQuery(@Nonnull final String token) {
		this.type = Auth.BASIC_AUTH;
		this.token = token;
		this.isHeader = false;
	}

	protected void setBasicUsernamePassword(@Nonnull final String username, @Nonnull final String password) {
		this.type = Auth.BASIC_AUTH;
		this.token = AbstractSession.getEncoded(username, password);
	}

	protected static String getEncoded(@Nonnull final String username, @Nonnull final String password) {
		if (username.length() > 0 && password.length() > 0) {
			String credentials = username + ":" + password;
			return Base64.encodeBase64String(credentials.getBytes());
		} else {
			return "";
		}
	}

	protected abstract void setOAuthToken(@Nonnull String clientId, @Nonnull String clientSecret,
			@Nonnull List<String> scopes, @Nonnull String username);

	protected void setOAuthToken(@Nonnull String accessTokenUrl, @Nonnull String authorizationUrl,
			@Nonnull String clientId, @Nonnull String clientSecret, @Nonnull List<String> scopes,
			@Nonnull String username) {
		try {
			this.token = retrieveAccessToken(accessTokenUrl, authorizationUrl, clientId, clientSecret, scopes,
					username);
			this.type = Auth.OAUTH;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String retrieveAccessToken(String accessTokenUrl, String authorizationUrl, String clientId,
			String clientSecret, List<String> scopes, String user) throws IOException {
		JsonFactory jsonFactory = new JacksonFactory();
		HttpTransport httpTransport = new NetHttpTransport();
		AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
				httpTransport, jsonFactory, new GenericUrl(accessTokenUrl),
				new ClientParametersAuthentication(clientId, clientSecret), clientId, authorizationUrl)
						.setScopes(scopes).build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(receiverHost).setPort(receiverPort)
				.build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize(user).getAccessToken();
	}

	private static String generateRandomId() {
		return UUID.randomUUID().toString();
	}

	protected void copy(ISession session, String permission) throws Exception {
		this.rateLimit = session.getRateLimit();
		this.rateLimitRemaining = session.getRateLimitRemaining();
		this.rateLimitReset = session.getRateLimitReset();
		this.isSet = session.isSet();
		this.type = session.type();
		this.isHeader = session.isHeader();
		this.hash = session.hash();
		
		this.token = session.token(permission);
	}

	public static ISession getSession(Class<? extends AbstractSession> clazz, String id) {
		LOG.trace("Loading session " + id);
		try {
			return (ISession) clazz.getMethod("get", String.class).invoke(null, id);
		} catch (Exception e) {
			LOG.error(e.getClass().getName() + e.getMessage());
			return null;
		}
	}
	
	@Override
	public String toString() {
		return " session ["
				+ id + ", "
				+ "rateLimit=" + rateLimit + ", "
				+ "rateLimitRemaining=" + rateLimitRemaining + ", "
				+ "rateLimitReset=" + rateLimitReset + ", "
				+ "headers=" + getHeaders().get(AUTHORIZATION) 
				+ "]";
	}

}
