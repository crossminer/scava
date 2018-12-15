package org.eclipse.scava.crossflow.restmule.client.gitlab.interceptor;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.ACCEPT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_LIMIT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_REMAINING;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_RESET;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USER_AGENT;

import org.eclipse.scava.crossflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.gitlab.cache.GitlabCacheManager;
import org.eclipse.scava.crossflow.restmule.client.gitlab.session.GitlabSession;
import org.eclipse.scava.crossflow.restmule.client.gitlab.util.GitlabPropertiesUtil;

import okhttp3.Interceptor;

public class GitlabInterceptor extends AbstractInterceptor {

	private final ISession session;
	
	public GitlabInterceptor(ISession session){
		this.session = session;
	}

	static {
		sessionClass = GitlabSession.class;
		headerLimit = GitlabPropertiesUtil.get(RATE_LIMIT_LIMIT);
		headerRemaining = GitlabPropertiesUtil.get(RATE_LIMIT_REMAINING);
		headerReset = GitlabPropertiesUtil.get(RATE_LIMIT_RESET);
		userAgent = GitlabPropertiesUtil.get(USER_AGENT);
		accept = GitlabPropertiesUtil.get(ACCEPT);
	}

	public Interceptor mainInterceptor(boolean activeCaching){
		return mainInterceptor(activeCaching, session.id(), new GitlabCacheManager());
	}
	
}