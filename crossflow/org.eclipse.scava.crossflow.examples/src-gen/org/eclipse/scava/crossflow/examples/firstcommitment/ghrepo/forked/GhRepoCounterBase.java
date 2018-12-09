package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class GhRepoCounterBase extends Task  implements GhReposConsumer{
		
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "GhRepoCounter:"+workflow.getName();
	}
	
	protected ResultsPublisher resultsPublisher;
	
	protected void setResultsPublisher(ResultsPublisher resultsPublisher) {
		this.resultsPublisher = resultsPublisher;
	}
	
	private ResultsPublisher getResultsPublisher() {
		return resultsPublisher;
	}
	
	public void sendToResultsPublisher(Result result) {
		getResultsPublisher().send(result, this.getClass().getName());
	}
	
	
	
	@Override
	public void consumeGhReposActual(GhRepo ghRepo) {

		workflow.setTaskInProgess(this);
		
		consumeGhRepos(ghRepo);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}