package org.eclipse.scava.crossflow.tests.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class AdditionResultsSinkBase extends Task  implements AdditionResultsConsumer{
		
	protected AdditionWorkflow workflow;
	
	public void setWorkflow(AdditionWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "AdditionResultsSink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeAdditionResultsWithNotifications(Number number) {
		workflow.setTaskInProgess(this);
		consumeAdditionResults(number);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeAdditionResults(Number number);
	
	
	
}