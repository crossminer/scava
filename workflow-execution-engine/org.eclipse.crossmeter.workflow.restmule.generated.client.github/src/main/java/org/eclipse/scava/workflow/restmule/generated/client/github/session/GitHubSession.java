package org.eclipse.scava.workflow.restmule.generated.client.github.session;

import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.OAUTH2_AUTH_URL;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.OAUTH2_TOKEN_URL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.workflow.restmule.core.session.AbstractSession;
import org.eclipse.scava.workflow.restmule.core.session.ISession;
import org.eclipse.scava.workflow.restmule.generated.client.github.util.GitHubPropertiesUtil;

@SuppressWarnings("unused")
public class GitHubSession extends AbstractSession {

	private static final Logger LOG = LogManager.getLogger(GitHubSession.class);
	private static final String TOKEN_ACCESSOR = UUID.randomUUID().toString();

	public static final String CLIENT = "github";
	private static final String AUTHORIZATION_URL = GitHubPropertiesUtil.get(OAUTH2_AUTH_URL);
	private static final String ACCESS_TOKEN_URL = GitHubPropertiesUtil.get(OAUTH2_TOKEN_URL);

	/** STATIC SESSION MANAGEMENT */
	
	private static HashMap<String, ISession> sessions;
	
	private static HashMap<String, ISession> getSessions() {
		if (sessions == null)
			sessions = new HashMap<String, ISession>();
		return sessions;
	}

	private static void addSession(GitHubSession session) {
		GitHubSession.getSessions().put(session.id(), session);
	}
	
	public static ISession get(String id) {
		return GitHubSession.getSessions().get(id);
	}
	
	/** PREDEFINED SESSION TYPES */
	
	public static ISession createPublic() {
		GitHubSession session = new GitHubSession();
		GitHubSession.addSession(session);
		return session;
	}

	public static ISession createWithBasicAuth(String username, String password) {
		GitHubSession session = new GitHubSession(username, password);
		GitHubSession.addSession(session);
		return session;
	}

	public static ISession createWithBasicAuth(String token) {
		GitHubSession session = new GitHubSession(token);
		GitHubSession.addSession(session);
		return session;
	}

	public static ISession createWithOAuth(
			String clientId, 
			String clientSecret, 
			List<String> scopes, 
			String username) {
		GitHubSession session = new GitHubSession(clientId, clientSecret, scopes, username);
		GitHubSession.addSession(session);
		return session;
	}

	/** TYPE CONSTRUCTORS */
	
	private GitHubSession() {
		super(); 
	}

	private GitHubSession(String username, String password) {
		super(username, password);
	}

	private GitHubSession(String token) {
		super(token);
	}

	private GitHubSession(String clientId, 
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
			GitHubSession githubSession = new GitHubSession();
			try {
				githubSession.copy(session, TOKEN_ACCESSOR);
			} catch (Exception e) {
				String INVALID_COPYING_PERMISSION = "Invalid permission";
				LOG.error(INVALID_COPYING_PERMISSION);
			}
			GitHubSession.addSession(githubSession);
			return githubSession;
		}	
	}
	

}