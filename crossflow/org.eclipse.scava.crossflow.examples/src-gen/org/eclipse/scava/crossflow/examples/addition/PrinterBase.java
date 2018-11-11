package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class PrinterBase implements AdditionResultsConsumer, Task{
		
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
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	public void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	public ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	
	
	@Override
	public void consumeAdditionResultsActual(Number number) {

		workflow.setTaskInProgess(this);
		
		consumeAdditionResults(number);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) {
		
		workflow.setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		workflow.setTaskUnblocked(this);
		
	}
	
	
}