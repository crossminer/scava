package org.eclipse.scava.workflow.restmule.core.session;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * {@link IRateLimiter}
 * <p>
 * @version 1.0.0
 *
 */
public interface IRateLimiter {

	Integer getRateLimit();
	
	AtomicInteger getRateLimitRemaining();
	
	long getRateLimitResetInMilliSeconds();
	
	Date getRateLimitReset();
	
	AtomicBoolean isSet();

	void resetCacheCounter();
	
	AtomicInteger cacheCounter();
	
	void unset();
	
}
