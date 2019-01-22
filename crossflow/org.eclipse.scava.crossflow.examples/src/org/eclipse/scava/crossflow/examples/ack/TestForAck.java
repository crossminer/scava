package org.eclipse.scava.crossflow.examples.ack;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Workflow;

public class TestForAck extends AckExample {

	public static void main(String[] args) throws Exception {
		new TestForAck().execute();
	}

	public void execute() throws Exception {
		// alternate to test for needing an acknowledgement before next message is
		// dispatched
		boolean prefetch = false;

		AckExample masterbear = new AckExample(Mode.MASTER_BARE);
		masterbear.setInstanceId("MasterID");
		masterbear.setName("Master");
		masterbear.setEnablePrefetch(prefetch);
		masterbear.run();

		AckExample worker1 = new AckExample(Mode.WORKER);
		worker1.setMaster("localhost");
		worker1.setInstanceId("MasterID");
		worker1.setName("Worker1");
		worker1.setEnablePrefetch(prefetch);
		worker1.run();

		Thread.sleep(3000);

		AckExample worker2 = new AckExample(Mode.WORKER);
		worker2.setMaster("localhost");
		worker2.setInstanceId("MasterID");
		worker2.setName("Worker2");
		worker2.setEnablePrefetch(prefetch);
		worker2.run();

		waitFor(masterbear);

		System.out.println("results: " + masterbear.getSink().results);
		System.out.println(masterbear.isEnablePrefetch() ? !masterbear.getSink().results.contains(worker2.getName())
				: masterbear.getSink().results.contains(worker2.getName()));

	}

	public static void waitFor(Workflow workflow) throws Exception {
		while (!workflow.hasTerminated()) {
			Thread.sleep(100);
		}
	}

}
