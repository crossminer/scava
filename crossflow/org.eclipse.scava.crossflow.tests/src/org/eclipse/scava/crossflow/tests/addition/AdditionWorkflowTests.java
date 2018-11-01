package org.eclipse.scava.crossflow.tests.addition;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AdditionWorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.run();
		Thread.sleep(1000);
		workflow.stop();
		assertArrayEquals(new Integer[] {2, 4}, workflow.getAdditionResultsSink().getNumbers().toArray());
		assertEquals(2, workflow.getAdder().getExecutions());
	}
	
}
