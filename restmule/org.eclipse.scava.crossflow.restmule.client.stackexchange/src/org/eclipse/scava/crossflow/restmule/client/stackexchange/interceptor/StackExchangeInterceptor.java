package org.eclipse.scava.crossflow.restmule.client.stackexchange.interceptor;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.ACCEPT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_LIMIT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_REMAINING;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.RATE_LIMIT_RESET;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.USER_AGENT;

import org.eclipse.scava.crossflow.restmule.core.interceptor.AbstractInterceptor;
import org.eclipse.scava.crossflow.restmule.core.session.ISession;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.cache.StackExchangeCacheManager;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.session.StackExchangeSession;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;

import okhttp3.Interceptor;

public class StackExchangeInterceptor extends AbstractInterceptor {

	private final ISession session;
	
	public StackExchangeInterceptor(ISession session){
		this.session = session;
	}

	static {
		sessionClass = StackExchangeSession.class;
		headerLimit = StackExchangePropertiesUtil.get(RATE_LIMIT_LIMIT);
		headerRemaining = StackExchangePropertiesUtil.get(RATE_LIMIT_REMAINING);
		headerReset = StackExchangePropertiesUtil.get(RATE_LIMIT_RESET);
		userAgent = StackExchangePropertiesUtil.get(USER_AGENT);
		accept = StackExchangePropertiesUtil.get(ACCEPT);
	}

	public Interceptor mainInterceptor(boolean activeCaching){
		return mainInterceptor(activeCaching, session.id(), new StackExchangeCacheManager());
	}
	
}