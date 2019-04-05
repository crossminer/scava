package org.eclipse.scava.crossflow.tests.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.addition.AdditionWorkflow;
import org.junit.Test;

public class DirectoryCacheCommandLineTests extends WorkflowTests {

	@Test
	public void testCacheDeletion() throws Exception {
		testCacheDeletionActual(true, false);
		testCacheDeletionActual(true, true);
		testCacheDeletionActual(false, false);
	}

	public void testCacheDeletionActual(boolean deleteCache, boolean deleteQueue) throws Exception {

		String[] broker = new String[] { "-instance", "aw1", "-cacheEnabled", "true" };
		String[] noBroker = new String[] { "-createBroker", "false", "-instance", "aw1", "-cacheEnabled", "true" };
		String[] brokerDeleteCache = new String[] { "-instance", "aw1", "-cacheEnabled", "true", "-deleteCache", "" };
		String[] noBrokerDeleteCache = new String[] { "-createBroker", "false", "-instance", "aw1", "-cacheEnabled",
				"true", "-deleteCache", "" };
		String[] brokerDeleteCacheQueue = new String[] { "-instance", "aw1", "-cacheEnabled", "true", "-deleteCache",
				"Additions" };
		String[] noBrokerDeleteCacheQueue = new String[] { "-createBroker", "false", "-instance", "aw1",
				"-cacheEnabled", "true", "-deleteCache", "Additions" };

		AdditionWorkflow master = AdditionWorkflow
				.run(createBroker ? (deleteCache ? (deleteQueue ? brokerDeleteCacheQueue : brokerDeleteCache) : broker)
						: (deleteCache ? (deleteQueue ? noBrokerDeleteCacheQueue : noBrokerDeleteCache) : noBroker));

		master.getAdder().setCaching(true);
		DirectoryCache cache = (DirectoryCache) master.getCache();

		waitFor(master);

		System.out.println(DirectoryCacheTests.FileAssert.printDirectoryTree(cache.getDirectory()));

		assertTrue(cache.hasCachedOutputs(null));

		if (deleteCache)
			cache.clear(deleteQueue ? "Additions" : "");

		System.out.println(DirectoryCacheTests.FileAssert.printDirectoryTree(cache.getDirectory()));

		assertTrue(deleteCache ? !cache.hasCachedOutputs(null) : cache.hasCachedOutputs(null));

	}

}
