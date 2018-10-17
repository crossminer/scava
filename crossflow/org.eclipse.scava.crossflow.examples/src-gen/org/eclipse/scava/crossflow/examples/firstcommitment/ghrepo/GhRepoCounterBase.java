package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class GhRepoCounterBase implements GhReposConsumer{
	
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected ResultsPublisher resultsPublisher;
	
	public void setResultsPublisher(ResultsPublisher resultsPublisher) {
		this.resultsPublisher = resultsPublisher;
	}
	
	public ResultsPublisher getResultsPublisher() {
		return resultsPublisher;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
}