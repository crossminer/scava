package org.eclipse.scava.crossflow.examples.github.techrank;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.FailedJob;

public abstract class TechnologySourceBase extends Task {
		
	protected TechrankWorkflow workflow;
	
	public void setWorkflow(TechrankWorkflow workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "TechnologySource:"+workflow.getName();
	}
	
	protected Technologies technologies;
	
	protected void setTechnologies(Technologies technologies) {
		this.technologies = technologies;
	}
	
	private Technologies getTechnologies() {
		return technologies;
	}
	
	public void sendToTechnologies(Technology technology) {
		getTechnologies().send(technology, this.getClass().getName());
	}
	
	
	
	
	public abstract void produce() throws Exception;
	
}