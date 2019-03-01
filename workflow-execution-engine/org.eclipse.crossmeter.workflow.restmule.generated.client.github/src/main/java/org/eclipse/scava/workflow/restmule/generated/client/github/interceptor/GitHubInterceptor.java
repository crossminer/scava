package org.eclipse.scava.workflow.restmule.generated.client.github.interceptor;

import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.ACCEPT;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_LIMIT;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_REMAINING;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_RESET;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.USER_AGENT;

import org.eclipse.scava.workflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.workflow.restmule.generated.client.github.session.GitHubSession;
import org.eclipse.scava.workflow.restmule.generated.client.github.util.GitHubPropertiesUtil;

import okhttp3.Interceptor;

public class GitHubInterceptor extends AbstractInterceptor {
	
	public GitHubInterceptor(String session){
		this.sessionId = session;
	}

	static {
		sessionClass = GitHubSession.class;
		headerLimit = GitHubPropertiesUtil.get(RATE_LIMIT_LIMIT);
		headerRemaining = GitHubPropertiesUtil.get(RATE_LIMIT_REMAINING);
		headerReset = GitHubPropertiesUtil.get(RATE_LIMIT_RESET);
		userAgent = GitHubPropertiesUtil.get(USER_AGENT);
		accept = GitHubPropertiesUtil.get(ACCEPT);
	}

	public Interceptor mainInterceptor(boolean activeCaching){
		return mainInterceptor(userAgent, accept,cache, sessionId, headerLimit, headerRemaining, headerReset);
	}
	
}