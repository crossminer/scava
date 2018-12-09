package org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.forked;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class GhRepoSourceBase extends Task {
		
	protected GhRepoExample workflow;
	
	public void setWorkflow(GhRepoExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "GhRepoSource:"+workflow.getName();
	}
	
	protected GhRepos ghRepos;
	
	protected void setGhRepos(GhRepos ghRepos) {
		this.ghRepos = ghRepos;
	}
	
	private GhRepos getGhRepos() {
		return ghRepos;
	}
	
	public void sendToGhRepos(GhRepo ghRepo) {
		getGhRepos().send(ghRepo, this.getClass().getName());
	}
	
	
	
	public abstract void produce();
	
	
}