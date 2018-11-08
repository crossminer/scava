package org.eclipse.scava.crossflow.restmule.core.cache;

public abstract class AbstractCacheManager {

	protected static ICache cache;
	protected static AbstractCacheManager manager;

	public AbstractCacheManager getInstance() {
		return manager;
	}

	public ICache getCacheInstance() {
		return cache;
	}

}
