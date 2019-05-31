package org.eclipse.scava.crossflow.tests.multiflow;

import static org.junit.Assert.*;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class MultiflowTests extends WorkflowTests {

	@Test
	public void testOutput() throws Exception {

		Multiflow workflow = new Multiflow();
		workflow.createBroker(createBroker);
		workflow.getMultiSource().setNumbers(2);
		workflow.run();

		waitFor(workflow);

		assertEquals(8, workflow.getMultiSink().getNumbers().size());
	}

	@Test
	public void testCache() throws Exception {
		testCache(true, true, 0);
		testCache(true, false, 2);
		testCache(false, true, 2);
		testCache(false, false, 4);
	}

	public void testCache(boolean cacheIn1, boolean cacheIn2, int executions) throws Exception {
		System.out.println(cacheIn1 + " : " + cacheIn2 + " : " + executions);
		Multiflow workflow = new Multiflow();
		workflow.createBroker(createBroker);
		workflow.getMultiTask().configureCache(cacheIn1, cacheIn2);
		DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		workflow.getMultiSource().setNumbers(2);
		workflow.run();
		waitFor(workflow);

		workflow = new Multiflow();
		workflow.createBroker(createBroker);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.getMultiSource().setNumbers(2);
		workflow.run();
		waitFor(workflow);

		System.out.println(workflow.getMultiSink().getNumbers());
		assertEquals(executions, workflow.getMultiTask().getExecutions());
		assertEquals(8, workflow.getMultiSink().getNumbers().size());
	}

}
