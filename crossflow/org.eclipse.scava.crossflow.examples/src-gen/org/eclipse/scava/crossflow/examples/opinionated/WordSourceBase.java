package org.eclipse.scava.crossflow.examples.opinionated;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class WordSourceBase {
	
	protected OpinionatedExample workflow;
	
	public void setWorkflow(OpinionatedExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected Words words;
	
	public void setWords(Words words) {
		this.words = words;
	}
	
	public Words getWords() {
		return words;
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