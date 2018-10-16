package org.eclipse.scava.crossflow.examples.opinionated;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class OccurencesMonitorBase implements WordsConsumer{
	
	protected OpinionatedExample workflow;
	
	public void setWorkflow(OpinionatedExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}