package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class EmptySinkBase extends Task  implements ResultsPublisherConsumer{
		
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "EmptySink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeResultsPublisherWithNotifications(Result result) {
		workflow.setTaskInProgess(this);
		consumeResultsPublisher(result);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeResultsPublisher(Result result);
	
	
	
}