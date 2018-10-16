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
	
	
	public abstract void produce();
	
}