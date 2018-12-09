package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		//master.setEnableCache(false);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		
		AdditionWorkflow worker = new AdditionWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		System.err.println(master.getAdder().getExecutions() + "/" + worker.getAdder().getExecutions());
		//TODO: When executed individually this passes but when the whole class is executed it fails (i.e the master does all the work). setEnableCache() seems to be related - when set to false it works well.  
		//assertEquals(true, master.getAdder().getExecutions() < 5);
		assertEquals(5, worker.getAdder().getExecutions() + master.getAdder().getExecutions());
		
		assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, 
				master.getAdditionResultsSink().getNumbers().toArray());
	}
	
	public static void main(String[] args) throws Exception {
		new AdditionWorkflowTests().testBareMasterWorker();
	}
	
	@Test
	public void testBareMasterWorker() throws Exception {
		AdditionWorkflow master = new AdditionWorkflow(Mode.MASTER_BARE);
		//master.setEnableCache(false);
		master.setTerminationTimeout(5000);
		master.getNumberPairSource().setNumbers(Arrays.asList(1, 2, 3, 4, 5));
		
		AdditionWorkflow worker = new AdditionWorkflow(Mode.WORKER);
		
		master.run();
		worker.run();
		
		waitFor(master);
		
		assertNull(master.getAdder());
		assertEquals(5, worker.getAdder().getExecutions());
		
		assertArrayEquals(new Integer[] {2, 4, 6, 8, 10}, 
				master.getAdditionResultsSink().getNumbers().toArray());
	}
	
}
