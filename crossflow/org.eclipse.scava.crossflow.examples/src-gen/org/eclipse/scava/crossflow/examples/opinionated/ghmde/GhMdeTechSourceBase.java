package org.eclipse.scava.crossflow.examples.opinionated.ghmde;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class GhMdeTechSourceBase {
	
	protected GhMdeTechExample workflow;
	
	public void setWorkflow(GhMdeTechExample ghMdeTechExample) {
		this.workflow = ghMdeTechExample;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected GhMdeTechs ghMdeTechs;
	
	public void setWords(GhMdeTechs ghMdeTechs) {
		this.ghMdeTechs = ghMdeTechs;
	}
	
	public GhMdeTechs getGhMdeTechs() {
		return ghMdeTechs;
	}
	
	
	public abstract void produce();
	
}