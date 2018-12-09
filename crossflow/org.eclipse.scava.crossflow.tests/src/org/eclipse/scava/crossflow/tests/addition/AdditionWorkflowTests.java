package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.setTerminationTimeout(0);
		workflow.run();
		waitFor(workflow);
		assertArrayEquals(new Integer[] {2, 4}, workflow.getAdditionResultsSink().getNumbers().toArray());
	}
	
	@Test
	public void testMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		
		AdditionWorkflow worker = new AdditionWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		// Fails because messages sent from the worker Adder end up nowhere
		assertArrayEquals(new Integer[] {2, 4}, master.getAdditionResultsSink().getNumbers().toArray());
	}
	
}
