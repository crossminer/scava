package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class GhRepoSourceBase {
	
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected GhRepos ghRepos;
	
	public void setGhRepos(GhRepos ghRepos) {
		this.ghRepos = ghRepos;
	}
	
	public GhRepos getGhRepos() {
		return ghRepos;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	public abstract void produce();
	
}