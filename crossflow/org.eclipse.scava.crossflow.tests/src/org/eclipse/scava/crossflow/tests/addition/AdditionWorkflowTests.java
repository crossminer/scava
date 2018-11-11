package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class AdditionWorkflowTests {
	
	private final static long SLEEP_DURATION = 1000;
	
	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.run();
		Thread.sleep(workflow.getTerminationTimeout() + SLEEP_DURATION);
		assertArrayEquals(new Integer[] {2, 4}, workflow.getAdditionResultsSink().getNumbers().toArray());
		assertEquals(2, workflow.getAdder().getExecutions());
		assertTrue(workflow.hasTerminated());
	}
	
}
