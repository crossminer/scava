package org.eclipse.scava.crossflow.tests.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class NumberPairSourceBase extends Task {
		
	protected AdditionWorkflow workflow;
	
	public void setWorkflow(AdditionWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "NumberPairSource:"+workflow.getName();
	}
	
	protected Additions additions;
	
	protected void setAdditions(Additions additions) {
		this.additions = additions;
	}
	
	private Additions getAdditions() {
		return additions;
	}
	
	public void sendToAdditions(NumberPair numberPair) {
		getAdditions().send(numberPair, this.getClass().getName());
	}
	
	
	
	public abstract void produce();
	
	
}