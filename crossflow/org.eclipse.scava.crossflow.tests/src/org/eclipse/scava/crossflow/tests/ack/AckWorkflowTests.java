package org.eclipse.scava.crossflow.tests.ack;

import static org.junit.Assert.assertTrue;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.eclipse.scava.crossflow.tests.util.Retry;
import org.eclipse.scava.crossflow.tests.util.RetryRule;
import org.junit.Rule;
import org.junit.Test;

public class AckWorkflowTests extends WorkflowTests {

	// define the number of retries for tests in this class (annotated with @Retry)
	@Rule
	public RetryRule rule = new RetryRule(3);

	@Test
	public void testPrefetchEnabled() throws Exception {
		prefetchTest(true);
	}

	@Test
	public void testPrefetchDisabled() throws Exception {
		prefetchTest(false);
	}

	private void prefetchTest(boolean enable) throws Exception {

		AckWorkflow masterbare = new AckWorkflow(Mode.MASTER_BARE);
		if (singleBroker)
			masterbare.createBroker(false);
		masterbare.setInstanceId("Pre-" + enable);
		masterbare.setName("Master");
		masterbare.setEnablePrefetch(enable);
		masterbare.run();

		AckWorkflow worker1 = new AckWorkflow(Mode.WORKER);
		worker1.setMaster("localhost");
		worker1.setInstanceId("Pre-" + enable);
		worker1.setName("Worker1");
		worker1.setEnablePrefetch(enable);
		worker1.run();

		Thread.sleep(3000);

		AckWorkflow worker2 = new AckWorkflow(Mode.WORKER);
		worker2.setMaster("localhost");
		worker2.setInstanceId("Pre-" + enable);
		worker2.setName("Worker2");
		worker2.setEnablePrefetch(enable);
		worker2.run();

		waitFor(masterbare);

		System.out.println("results: " + masterbare.getSink().results);

		assertTrue(masterbare.isEnablePrefetch() ? !masterbare.getSink().results.contains(worker2.getName())
				: masterbare.getSink().results.contains(worker2.getName()));

	}

	@Test
	@Retry
	public void testTerminationEnabled() throws Exception {

		// run execution with termination to estimate termination time
		long init = System.currentTimeMillis();

		AckWorkflow master = new AckWorkflow(Mode.MASTER);
		if (singleBroker)
			master.createBroker(false);
		master.setInstanceId("Termination-" + "true");
		master.setName("Master");
		master.getProcessingTask().setLag(100);
		master.setEnableTermination(true);
		master.run();

		waitFor(master);

		long execTime = System.currentTimeMillis() - init;
		System.out.println("normal execution time: " + execTime / 1000 + "s");

		// run execution without termination, giving a timeout for when to check if the
		// workflow has terminated

		master = new AckWorkflow(Mode.MASTER);
		if (singleBroker)
			master.createBroker(false);
		master.setInstanceId("Termination-" + "true");
		master.setName("Master");
		master.getProcessingTask().setLag(100);
		master.setEnableTermination(false);
		master.run();

		// wait for "3 x execTime" seconds to ensure automatic termination is disabled
		Thread.sleep(3 * execTime);

		System.out.println("waited for: " + execTime * 3 / 1000 + "s");

		assertTrue(!master.hasTerminated());

		if (!master.hasTerminated())
			master.terminate();

	}

}
