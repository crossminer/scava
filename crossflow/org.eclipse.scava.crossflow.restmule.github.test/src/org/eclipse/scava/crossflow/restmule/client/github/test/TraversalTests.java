package org.eclipse.scava.crossflow.restmule.client.github.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TraversalTests extends GitHubUtils {

	private static final String ERROR = "something went wrong";

	private static final Logger LOG = LogManager.getLogger(DataAccessObjectTests.class);

	private static IGitHubApi api;

	@BeforeClass
	public static void setup(){
		api = GitHubUtils.getOAuthClient();
	}

	@Test
	public void test() {
		//api.getReposCommits("epsilonlabs", "emc-json", null, null, null, null, null)
		api.getReposCommits("square", "okhttp", null, null, null, null, null)
			.observe()
			.doOnNext(item -> LOG.info(item.getCommit().getAuthorInner()))
			.doOnError(e -> e.printStackTrace())
			.blockingSubscribe();

	}
	
}
