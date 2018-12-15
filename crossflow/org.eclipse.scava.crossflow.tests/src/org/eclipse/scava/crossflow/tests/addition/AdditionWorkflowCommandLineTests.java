package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowCommandLineTests extends WorkflowTests {
	
	@Test
	public void testMasterWorker() throws Exception {
		
		AdditionWorkflow master = AdditionWorkflow.run(new String[] {"-instance", "aw1"});
		AdditionWorkflow worker = AdditionWorkflow.run(new String[] {"-mode", "worker", "-instance", "aw1"});
		
		waitFor(master);
		assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, master.getAdditionResultsSink().getNumbers().toArray());
		assertTrue(master.getAdder().getExecutions() < 5);
		assertTrue(worker.getAdder().getExecutions() > 0);
		
	}
	
	/*
	@Test(expected = Exception.class)
	public void testWorkerWithoutMaster() throws Exception {
		AdditionWorkflow worker = AdditionWorkflow.run(new String[] {"-mode", "worker"});
		waitFor(worker);
	}*/
	
}
