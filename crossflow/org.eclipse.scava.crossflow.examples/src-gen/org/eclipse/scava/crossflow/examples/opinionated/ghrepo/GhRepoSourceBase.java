package org.eclipse.scava.crossflow.examples.opinionated.ghrepo;

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
	
	
	public abstract void produce();
	
}