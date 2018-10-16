package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class OccurencesMonitorBase implements GhMdeTechsConsumer{
	
	protected GhMdeTechExample workflow;
	
	public void setWorkflow(GhMdeTechExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}