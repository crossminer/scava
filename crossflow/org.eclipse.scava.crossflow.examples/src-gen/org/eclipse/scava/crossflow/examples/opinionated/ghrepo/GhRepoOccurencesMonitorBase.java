package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class GhRepoOccurencesMonitorBase implements GhReposConsumer{
	
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}