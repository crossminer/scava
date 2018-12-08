package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class AdderBase implements AdditionsConsumer, Task{
		
	protected BaseCase workflow;
	
	public void setWorkflow(BaseCase workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "Adder:"+workflow.getName();
	}
	
	protected AdditionResults additionResults;
	
	protected void setAdditionResults(AdditionResults additionResults) {
		this.additionResults = additionResults;
	}
	
	private AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	public void sendToAdditionResults(Number number) {
		getAdditionResults().send(number, this.getClass().getName());
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	protected void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	private ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	public void sendToResultsBroadcaster(Object[] row){
		getResultsBroadcaster().send(row);
	}
	
	
	
	@Override
	public void consumeAdditionsActual(NumberPair numberPair) {

		workflow.setTaskInProgess(this);
		
		consumeAdditions(numberPair);
		
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