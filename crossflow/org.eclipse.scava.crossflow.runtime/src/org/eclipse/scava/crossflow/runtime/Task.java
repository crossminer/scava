package org.eclipse.scava.crossflow.runtime;

public abstract class Task {

	public abstract String getId();
	
	public abstract Workflow getWorkflow();
	
	protected BuiltinTopic<Object[]> resultsBroadcaster;
	
	public void setResultsBroadcaster(BuiltinTopic<Object[]> resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	public BuiltinTopic<Object[]> getResultsBroadcaster() {
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
		
		getWorkflow().setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		getWorkflow().setTaskUnblocked(this);
		
	}
}
