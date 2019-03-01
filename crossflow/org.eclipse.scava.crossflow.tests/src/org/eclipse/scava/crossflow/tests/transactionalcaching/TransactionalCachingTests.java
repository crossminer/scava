package org.eclipse.scava.crossflow.tests.transactionalcaching;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class TransactionalCachingTests extends WorkflowTests {

	int parallelization = Math.max(Runtime.getRuntime().availableProcessors(), 2);

	@Test
	public void testCacheMasterWorker() throws Exception {

		List<String> elems = Arrays.asList("e1", "e2", "e3", "e4", "e5");

		MinimalWorkflow mb = new MinimalWorkflow(Mode.MASTER_BARE);
		mb.createBroker(createBroker);
		mb.setInstanceId("minimalwfcacheteststransactional");
		mb.getMinimalSource().setElements(elems);
		DirectoryCache cache = new DirectoryCache();
		mb.setCache(cache);
		mb.run();

		MinimalWorkflow w = new MinimalWorkflow(Mode.WORKER);
		w.setInstanceId(mb.getInstanceId());
		w.setMaster("localhost");
		w.getClonerTask().setFail(true);
		w.run();

		waitFor(mb);

		System.out.println(mb.getMinimalSink().getElements());
		assertEquals(5, mb.getMinimalSink().getElements().size());
		assertEquals(5, w.getClonerTask().getExecutions());

		//
		mb = new MinimalWorkflow(Mode.MASTER_BARE);
		mb.createBroker(createBroker);
		mb.setInstanceId("minimalwfcacheteststransactional");
		mb.getMinimalSource().setElements(elems);
		mb.setCache(new DirectoryCache(cache.getDirectory()));
		mb.run();

		w = new MinimalWorkflow(Mode.WORKER);
		w.setInstanceId(mb.getInstanceId());
		w.setMaster("localhost");
		w.getClonerTask().setFail(false);
		w.run();

		waitFor(mb);

		System.out.println(mb.getMinimalSink().getElements());
		assertEquals(10, mb.getMinimalSink().getElements().size());
		assertEquals(5, w.getClonerTask().getExecutions());

		//
		mb = new MinimalWorkflow(Mode.MASTER_BARE);
		mb.createBroker(createBroker);
		mb.setInstanceId("minimalwfcacheteststransactional");
		mb.getMinimalSource().setElements(elems);
		mb.setCache(new DirectoryCache(cache.getDirectory()));
		mb.run();

		w = new MinimalWorkflow(Mode.WORKER);
		w.setInstanceId(mb.getInstanceId());
		w.setMaster("localhost");
		w.getClonerTask().setFail(false);
		w.run();

		waitFor(mb);

		System.out.println(mb.getMinimalSink().getElements());
		assertEquals(10, mb.getMinimalSink().getElements().size());
		assertEquals(0, w.getClonerTask().getExecutions());

	}

	@Test
	public void testCacheParallel() throws Exception {

		List<String> elems = Arrays.asList("e1", "e2", "e3", "e4", "e5");

		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.setParallelization(parallelization);
		workflow.createBroker(createBroker);
		workflow.setInstanceId("minimalwfcacheteststransactional");
		workflow.getMinimalSource().setElements(elems);
		workflow.getClonerTasks().forEach(c -> c.setFail(true));
		DirectoryCache cache = new DirectoryCache();
		workflow.setCache(cache);
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getElements());
		assertEquals(5, workflow.getMinimalSink().getElements().size());
		assertEquals(5,
				(int) workflow.getClonerTasks().stream().collect(Collectors.summingInt(c -> c.getExecutions())));

		//
		workflow = new MinimalWorkflow();
		workflow.setParallelization(parallelization);
		workflow.createBroker(createBroker);
		workflow.setInstanceId("minimalwfcacheteststransactional");
		workflow.getMinimalSource().setElements(elems);
		workflow.getClonerTasks().forEach(c -> c.setFail(false));
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getElements());
		assertEquals(10, workflow.getMinimalSink().getElements().size());
		assertEquals(5,
				(int) workflow.getClonerTasks().stream().collect(Collectors.summingInt(c -> c.getExecutions())));

	}

	@Test
	public void testCache() throws Exception {

		testCacheTransactional(false, false, false);
		testCacheTransactional(false, false, true);

		testCacheTransactional(true, false, false);
		testCacheTransactional(true, true, false);

		testCacheTransactional(false, true, false);
		testCacheTransactional(true, false, true);

		testCacheTransactional(false, true, true);
		testCacheTransactional(true, true, true);

	}

	public void testCacheTransactional(boolean fail1, boolean fail2, boolean fail3) throws Exception {

		System.out.println("running test: " + fail1 + " : " + fail2 + " : " + fail3);
		// File dir = new File("cftest");
		// dir.mkdirs();

		List<String> elems = Arrays.asList("e1", "e2", "e3", "e4", "e5");

		MinimalWorkflow workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		workflow.setInstanceId("minimalwfcacheteststransactional");
		workflow.getMinimalSource().setElements(elems);
		workflow.getClonerTask().setFail(fail1);
		DirectoryCache cache = new DirectoryCache();// dir);
		workflow.setCache(cache);
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getElements());
		assertEquals(fail1 ? 5 : 10, workflow.getMinimalSink().getElements().size());
		assertEquals(5, workflow.getClonerTask().getExecutions());

		//
		workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		workflow.getMinimalSource().setElements(elems);
		workflow.getClonerTask().setFail(fail2);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getElements());
		assertEquals(fail1 && fail2 ? 5 : 10, workflow.getMinimalSink().getElements().size());
		assertEquals(fail1 ? 5 : 0, workflow.getClonerTask().getExecutions());

		//
		workflow = new MinimalWorkflow();
		workflow.createBroker(createBroker);
		workflow.getMinimalSource().setElements(elems);
		workflow.getClonerTask().setFail(fail3);
		workflow.setCache(new DirectoryCache(cache.getDirectory()));
		workflow.run();

		waitFor(workflow);

		System.out.println(workflow.getMinimalSink().getElements());
		assertEquals(fail1 && fail2 && fail3 ? 5 : 10, workflow.getMinimalSink().getElements().size());
		assertEquals(fail1 && fail2 ? 5 : 0, workflow.getClonerTask().getExecutions());

	}

}
