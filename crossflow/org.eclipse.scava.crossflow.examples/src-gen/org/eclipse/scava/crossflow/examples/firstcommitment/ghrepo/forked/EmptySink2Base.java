package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class EmptySink2Base extends Task  implements ResultsPublisher2Consumer{
		
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "EmptySink2:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeResultsPublisher2WithNotifications(Result result) {
		workflow.setTaskInProgess(this);
		consumeResultsPublisher2(result);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeResultsPublisher2(Result result);
	
	
	
}