package org.eclipse.scava.crossflow.tests.ack;

import static org.junit.Assert.assertTrue;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AckWorkflowTests extends WorkflowTests {

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

	// XXX removed to reduce redundant parameters in engine, after being already
	// tested
//	@Test
//	public void testAcknowledgementTermination() throws Exception {
//
//		// run execution with acknowledgements to estimate termination time (needed for
//		// non-ack test)
//		long init = System.currentTimeMillis();
//		ackTerminationTest(true, 0);
//		long execTime = System.currentTimeMillis() - init;
//		System.out.println("normal execution time: " + execTime / 1000 + "s");
//
//		// run execution without acknowledgements, giving a timeout for when to check if
//		// the workflow has terminated
//		ackTerminationTest(false, execTime);
//
//	}

//	private void ackTerminationTest(boolean ack, long timeout) throws Exception {
//
//		AckWorkflow wf = new AckWorkflow();
//		wf.setInstanceId("Ack-" + ack);
//		wf.getProcessingTask().setLag(1);
//		wf.setEnableAck(ack);
//		wf.run();
//
//		if (ack) {
//			waitFor(wf);
//			// workflow should terminate properly with ack enabled
//		} else {
//			// wait for "2 x timeout" seconds, and if the workflow is not terminated there
//			// are pending acknowledgements keeping it alive
//			Thread.sleep(2 * timeout);
//			assertTrue(!wf.hasTerminated());
//			if (!wf.hasTerminated())
//				wf.terminate();
//		}
//
//	}

}
