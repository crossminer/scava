package org.eclipse.scava.crossflow.restmule.client.bitbucket.session;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.OAUTH2_AUTH_URL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.OAUTH2_TOKEN_URL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.session.AbstractSession;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.util.BitbucketPropertiesUtil;

@SuppressWarnings("unused")
public class BitbucketSession extends AbstractSession {

	private static final Logger LOG = LogManager.getLogger(BitbucketSession.class);
	private static final String TOKEN_ACCESSOR = UUID.randomUUID().toString();

	public static final String CLIENT = "bitbucket";
	private static final String AUTHORIZATION_URL = BitbucketPropertiesUtil.get(OAUTH2_AUTH_URL);
	private static final String ACCESS_TOKEN_URL = BitbucketPropertiesUtil.get(OAUTH2_TOKEN_URL);

	/** STATIC SESSION MANAGEMENT */
	
	private static HashMap<String, ISession> sessions;
	
	private static HashMap<String, ISession> getSessions() {
		if (sessions == null)
			sessions = new HashMap<String, ISession>();
		return sessions;
	}

	private static void addSession(BitbucketSession session) {
		BitbucketSession.getSessions().put(session.id(), session);
	}
	
	public static ISession get(String id) {
		return BitbucketSession.getSessions().get(id);
	}
	
	/** PREDEFINED SESSION TYPES */
	
	public static ISession createPublic() {
		BitbucketSession session = new BitbucketSession();
		BitbucketSession.addSession(session);
		return session;
	}

	public static ISession createWithBasicAuth(String username, String password) {
		BitbucketSession session = new BitbucketSession(username, password);
		BitbucketSession.addSession(session);
		return session;
	}

	public static ISession createWithBasicAuth(String token) {
		BitbucketSession session = new BitbucketSession(token);
		BitbucketSession.addSession(session);
		return session;
	}

	public static ISession createWithOAuth(
			String clientId, 
			String clientSecret, 
			List<String> scopes, 
			String username) {
		BitbucketSession session = new BitbucketSession(clientId, clientSecret, scopes, username);
		BitbucketSession.addSession(session);
		return session;
	}

	/** TYPE CONSTRUCTORS */
	
	private BitbucketSession() {
		super(); 
	}

	private BitbucketSession(String username, String password) {
		super(username, password);
	}

	private BitbucketSession(String token) {
		super(token);
	}

	private BitbucketSession(String clientId, 
			String clientSecret, 
			List<String> scopes,
			String username	) {
		super();
		setOAuthToken(ACCESS_TOKEN_URL, AUTHORIZATION_URL, clientId, clientSecret, scopes, username);
	}
	
	/** METHODS FROM ISESSION */

	@Override
	public void setRateLimit(@Nonnull String rateLimit) {
		if (rateLimit != null) {
 			this.rateLimit = Integer.valueOf(rateLimit);
		}	
	}

	@Override
	public void setRateLimitRemaining(@Nonnull String rateLimitRemaining) {
		if (rateLimitRemaining != null) {
			Integer remaining = Integer.valueOf(rateLimitRemaining);
			if (remaining != this.rateLimitRemaining.get()) {
				this.rateLimitRemaining = new AtomicInteger(remaining);		
			}
		}
	}

	@Override
	public void setRateLimitReset(@Nonnull String rateLimitReset) {
		if (rateLimitReset != null) {
			Date newRateLimitReset = new Date(Long.valueOf(rateLimitReset) * 1000);
			if (getRateLimitReset() == null || newRateLimitReset.after(getRateLimitReset())) {
				this.rateLimitReset = newRateLimitReset;
				resetCacheCounter();
			}
		}
	}
	
	@Override
	public String token(String permission) throws Exception {
		if (permission.equals(TOKEN_ACCESSOR)){
			return this.token;
		} else {
			throw new Exception("Invalid permission");
		}
		
	}
	
	/** METHODS FROM ABSTRACT */
	
	@Override
	protected void setOAuthToken(String clientId, String clientSecret, List<String> scopes, String username) {
		super.setOAuthToken(ACCESS_TOKEN_URL, AUTHORIZATION_URL, clientId, clientSecret, scopes, username);		
	}
	
	@Override 
	public String toString() {
		return CLIENT + super.toString();
	}

	public static class Factory {
		
		public static ISession copy(ISession session){
			BitbucketSession bitbucketSession = new BitbucketSession();
			try {
				bitbucketSession.copy(session, TOKEN_ACCESSOR);
			} catch (Exception e) {
				String INVALID_COPYING_PERMISSION = "Invalid permission";
				LOG.error(INVALID_COPYING_PERMISSION);
			}
			BitbucketSession.addSession(bitbucketSession);
			return bitbucketSession;
		}	
	}

	//FIXME is this fixed by the new generator? if so please push the updated code
	@Override
	public String key() {
		// TODO Auto-generated method stub
		return null;
	}
	

}