package org.eclipse.scava.crossflow.tests.exceptions;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class ExceptionsWorkflowTests extends WorkflowTests {

	@Test
	public void testFailedJobs() throws Exception {

		ExceptionsWorkflow master = new ExceptionsWorkflow(Mode.MASTER_BARE);
		master.createBroker(createBroker);

		ExceptionsWorkflow worker = master.createWorker();
		worker.setFailOnNumber(1);

		master.run();
		worker.run();

		waitFor(master);
		waitFor(worker);

		assertEquals(1, master.getFailedJobs().size());
		assertEquals(Arrays.asList(2, 3), master.getResultsSink().getNumbers());

	}

}
