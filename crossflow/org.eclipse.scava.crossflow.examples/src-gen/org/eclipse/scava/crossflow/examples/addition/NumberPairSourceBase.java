package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class NumberPairSourceBase implements Task{
		
	protected AdditionExample workflow;
	
	public void setWorkflow(AdditionExample workflow) {
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
	
	public abstract void produce();
	
	
}