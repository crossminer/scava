package org.eclipse.scava.crossflow.restmule.client.github.cache;

import org.eclipse.scava.crossflow.restmule.core.cache.AbstractCacheManager;
//import org.eclipse.scava.crossflow.restmule.core.cache.DistributedCache;
import org.eclipse.scava.crossflow.restmule.core.cache.ICache;
import org.eclipse.scava.crossflow.restmule.core.cache.LocalCache;

public class GitHubCacheManager extends AbstractCacheManager {

	// private static final String AGENT_NAME = ".restmule";

	//FIXME disabled distributed cache
//	public void setDistributedInstance() {
//		if (cache == null)
//			cache = new DistributedCache();
//
//		// return GitHubCacheManager.dc;
//	}

	public void setLocalInstance() {
		if (cache == null)
			cache = new LocalCache();

		// return GitHubCacheManager.lc;
	}

	public AbstractCacheManager getInstance() {
		return manager;
	}

	public ICache getCacheInstance() {
		return cache;
	}

}
