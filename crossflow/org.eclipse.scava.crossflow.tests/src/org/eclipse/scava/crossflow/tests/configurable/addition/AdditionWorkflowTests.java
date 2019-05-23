package org.eclipse.scava.crossflow.tests.configurable.addition;

import java.util.Arrays;

import org.eclipse.scava.crossflow.tests.WorkflowTests;
import org.junit.Test;

public class AdditionWorkflowTests extends WorkflowTests {
	
	@Test
	public void testOutput() throws Exception {
		AdditionWorkflow workflow = new AdditionWorkflow();
		workflow.createBroker(createBroker);
		workflow.getNumberPairSource().setNumbers(Arrays.asList(1, 2));
		workflow.setTerminationTimeout(0);
		workflow.run();
		waitFor(workflow);
		//assertArrayEquals(new Integer[] { 2, 4 }, workflow.getAdditionResultsSink().getNumbers().toArray());
	}

}
