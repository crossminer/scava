package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class PrinterCsvSinkBase extends Task  implements AdditionResultsConsumer{
		
	protected BaseCase workflow;
	
	public void setWorkflow(BaseCase workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "PrinterCsvSink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeAdditionResultsWithNotifications(Number number) {
		workflow.setTaskInProgess(this);
		consumeAdditionResults(number);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeAdditionResults(Number number);
	
	
	
}