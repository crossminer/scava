package org.eclipse.scava.crossflow.restmule.client.stackexchange.cache;

import org.eclipse.scava.crossflow.restmule.core.cache.AbstractCacheManager;
import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.cache.LocalCache;

public class StackExchangeCacheManager extends AbstractCacheManager {
	
	public void setLocalInstance() {
		if (cache == null)
			cache = new LocalCache();
	}

	public AbstractCacheManager getInstance() {
		return manager;
	}

	public ICache getCacheInstance() {
		return cache;
	}
}
