package org.eclipse.scava.workflow.restmule.generated.client.github.cache;

import org.eclipse.scava.workflow.restmule.core.cache.AbstractCacheManager;
import org.eclipse.scava.workflow.restmule.core.cache.ICache;

public class GitHubCacheManager extends AbstractCacheManager {

	private static final String AGENT_NAME = "github"; 

	private static GitHubCacheManager agent;

	public static ICache getInstance() {
		if (GitHubCacheManager.agent == null){
			GitHubCacheManager.agent = new GitHubCacheManager();
		} 
		return GitHubCacheManager.agent;
	}

	private GitHubCacheManager() { 
		super(AGENT_NAME);
	}
}
