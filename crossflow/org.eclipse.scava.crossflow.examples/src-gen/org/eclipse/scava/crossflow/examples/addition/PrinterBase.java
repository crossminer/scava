package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class PrinterBase extends Task  implements AdditionResultsConsumer{
		
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "Printer:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeAdditionResultsWithNotifications(Number number) {
		workflow.setTaskInProgess(this);
		consumeAdditionResults(number);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeAdditionResults(Number number);
	
	
	
}