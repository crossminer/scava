package org.eclipse.scava.crossflow.examples.opinionated;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class WordSourceBase implements Task{
		
	protected OpinionatedExample workflow;
	
	public void setWorkflow(OpinionatedExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "WordSource:"+workflow.getName();
	}
	
	protected Words words;
	
	protected void setWords(Words words) {
		this.words = words;
	}
	
	private Words getWords() {
		return words;
	}
	
	public void sendToWords(Word word) {
		getWords().send(word, this.getClass().getName());
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