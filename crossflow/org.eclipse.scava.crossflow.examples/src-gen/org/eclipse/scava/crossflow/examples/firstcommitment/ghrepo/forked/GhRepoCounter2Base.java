package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class GhRepoCounter2Base extends Task  implements GhReposConsumer{
		
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "GhRepoCounter2:"+workflow.getName();
	}
	
	protected ResultsPublisher2 resultsPublisher2;
	
	protected void setResultsPublisher2(ResultsPublisher2 resultsPublisher2) {
		this.resultsPublisher2 = resultsPublisher2;
	}
	
	private ResultsPublisher2 getResultsPublisher2() {
		return resultsPublisher2;
	}
	
	public void sendToResultsPublisher2(Result result) {
		getResultsPublisher2().send(result, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeGhReposWithNotifications(GhRepo ghRepo) {
		workflow.setTaskInProgess(this);
		consumeGhRepos(ghRepo);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeGhRepos(GhRepo ghRepo);
	
	
	
}