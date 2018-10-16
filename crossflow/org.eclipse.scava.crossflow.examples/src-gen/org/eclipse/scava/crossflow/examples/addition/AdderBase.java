package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class AdderBase implements AdditionsConsumer{
	
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected AdditionResults additionResults;
	
	public void setAdditionResults(AdditionResults additionResults) {
		this.additionResults = additionResults;
	}
	
	public AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	
	
}