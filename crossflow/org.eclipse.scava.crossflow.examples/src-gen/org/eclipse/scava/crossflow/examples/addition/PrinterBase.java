package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class PrinterBase implements AdditionResultsConsumer{
	
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}