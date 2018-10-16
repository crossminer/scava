package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class NumberPairSourceBase {
	
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected Additions additions;
	
	public void setAdditions(Additions additions) {
		this.additions = additions;
	}
	
	public Additions getAdditions() {
		return additions;
	}
	
	
	public abstract void produce();
	
}