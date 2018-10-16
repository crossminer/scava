package org.eclipse.scava.crossflow.restmule.client.github.interceptor;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.ACCEPT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_LIMIT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_REMAINING;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_RESET;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USER_AGENT;

import org.eclipse.scava.crossflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.github.cache.GitHubCacheManager;
import org.eclipse.scava.crossflow.restmule.client.github.session.GitHubSession;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubPropertiesUtil;

import okhttp3.Interceptor;

public class GitHubInterceptor extends AbstractInterceptor {

	private final ISession session;

	public GitHubInterceptor(ISession session) {
		this.session = session;
	}

	static {
		sessionClass = GitHubSession.class;
		headerLimit = GitHubPropertiesUtil.get(RATE_LIMIT_LIMIT);
		headerRemaining = GitHubPropertiesUtil.get(RATE_LIMIT_REMAINING);
		headerReset = GitHubPropertiesUtil.get(RATE_LIMIT_RESET);
		userAgent = GitHubPropertiesUtil.get(USER_AGENT);
		accept = GitHubPropertiesUtil.get(ACCEPT);
	}

	public Interceptor mainInterceptor(boolean activeCaching) {
		return mainInterceptor(activeCaching, session.id(), new GitHubCacheManager());
	}

}