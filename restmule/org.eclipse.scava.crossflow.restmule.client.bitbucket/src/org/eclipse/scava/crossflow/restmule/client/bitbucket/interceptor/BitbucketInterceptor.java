package org.eclipse.scava.crossflow.restmule.client.bitbucket.interceptor;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.ACCEPT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_LIMIT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_REMAINING;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_RESET;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USER_AGENT;

import org.eclipse.scava.crossflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.cache.BitbucketCacheManager;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.session.BitbucketSession;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.util.BitbucketPropertiesUtil;

import okhttp3.Interceptor;

public class BitbucketInterceptor extends AbstractInterceptor {

	private final ISession session;
	
	public BitbucketInterceptor(ISession session){
		this.session = session;
	}

	static {
		sessionClass = BitbucketSession.class;
		headerLimit = BitbucketPropertiesUtil.get(RATE_LIMIT_LIMIT);
		headerRemaining = BitbucketPropertiesUtil.get(RATE_LIMIT_REMAINING);
		headerReset = BitbucketPropertiesUtil.get(RATE_LIMIT_RESET);
		userAgent = BitbucketPropertiesUtil.get(USER_AGENT);
		accept = BitbucketPropertiesUtil.get(ACCEPT);
	}

	public Interceptor mainInterceptor(boolean activeCaching){
		return mainInterceptor(activeCaching, session.id(), new BitbucketCacheManager());
	}
	
}