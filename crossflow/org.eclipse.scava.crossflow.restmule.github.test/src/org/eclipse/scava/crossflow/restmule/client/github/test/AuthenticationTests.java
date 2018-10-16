package org.eclipse.scava.crossflow.restmule.client.github.test;
import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.github.api.GitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.api.IGitHubApi;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubUtils;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AuthenticationTests extends GitHubUtils {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(AuthenticationTests.class);

	private static IGitHubApi api;

	@Test
	public void testPublicSession() {
		api = GitHubApi.create()
				.setSession(publicSession)
				.build();
		api.getRate_limitRateLimit().observe().map(rate -> rate.getRate().getLimit()).blockingSingle();
		/*blockingSubscribe(rate -> {
			assertNotNull(rate);
			assertEquals(new Integer(60), rate.getRate().getLimit());
		});*/
	}

	@Test @Ignore
	public void testBasicSession() {
		api = GitHubApi.create()
				.setSession(basicSession)
				.build();

		api.getRate_limitRateLimit().observe().blockingSubscribe(rate -> {
			assertNotNull(rate);
			assertEquals(new Integer(5000), rate.getRate().getLimit());
		});
	}

	@Test @Ignore
	public void testOAuthSession() {
		api = GitHubApi.create()
				.setSession(basicSession)
				.build();

		api.getRate_limitRateLimit().observe().blockingSubscribe(rate -> {
			assertNotNull(rate);
			assertEquals(new Integer(5000), rate.getRate().getLimit());
		});
	}

}
