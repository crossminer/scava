package org.eclipse.scava.crossflow.runtime;

public abstract class Task {

	public abstract String getId();
	
	public abstract Workflow getWorkflow();
	
	protected BuiltinChannel<Object[]> resultsTopic;
	
	public void setResultsTopic(BuiltinChannel<Object[]> resultsTopic) {
		this.resultsTopic = resultsTopic;
	}
	
	public BuiltinChannel<Object[]> getResultsTopic() {
		return resultsTopic;
	}
	
	public void sendToResultsTopic(Object[] row) throws Exception {
		getResultsTopic().send(row);
	}
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) throws Exception {
		
		getWorkflow().setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() throws Exception {
		getWorkflow().setTaskUnblocked(this);
	}
}
