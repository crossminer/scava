package org.eclipse.scava.crossflow.runtime;

import org.eclipse.scava.crossflow.runtime.utils.Result;

public abstract class Task {

	protected boolean cacheable = true;
	
	public abstract String getId();
	
	public abstract Workflow getWorkflow();
	
	protected BuiltinStream<Result> resultsTopic;
	
	public void setResultsTopic(BuiltinStream<Result> resultsTopic) {
		this.resultsTopic = resultsTopic;
	}
	
	public BuiltinStream<Result> getResultsTopic() {
		return resultsTopic;
	}
	
	public void sendToResultsTopic(Result res) throws Exception {
		getResultsTopic().send(res);
	}
	
	public boolean isCacheable() {
		return cacheable;
	}
	
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
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
