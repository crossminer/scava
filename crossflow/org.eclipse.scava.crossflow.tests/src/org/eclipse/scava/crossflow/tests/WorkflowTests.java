package org.eclipse.scava.crossflow.tests;

import org.eclipse.scava.crossflow.runtime.Workflow;

public class WorkflowTests {
	
	public void waitFor(Workflow workflow) throws Exception {
		while (!workflow.hasTerminated()) {
			Thread.sleep(100);
		}
	}
	
}
