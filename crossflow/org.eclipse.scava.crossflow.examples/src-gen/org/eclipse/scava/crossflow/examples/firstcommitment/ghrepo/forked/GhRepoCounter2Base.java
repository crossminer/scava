package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class GhRepoCounter2Base implements GhReposConsumer{
	
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected ResultsPublisher2 resultsPublisher2;
	
	public void setResultsPublisher2(ResultsPublisher2 resultsPublisher2) {
		this.resultsPublisher2 = resultsPublisher2;
	}
	
	public ResultsPublisher2 getResultsPublisher2() {
		return resultsPublisher2;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
	@Override
	public void consumeGhReposActual(GhRepo ghRepo) {

		workflow.setTaskInProgess(this);
		
		consumeGhRepos(ghRepo);
		
		workflow.setTaskWaiting(this);
		
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
	
	
	
}