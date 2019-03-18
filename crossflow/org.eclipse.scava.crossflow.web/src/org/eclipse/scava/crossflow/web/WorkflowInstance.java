package org.eclipse.scava.crossflow.web;

import org.eclipse.scava.crossflow.runtime.Workflow;

public class WorkflowInstance {
	
	protected Workflow workflow;
	protected String jar;
	
	public WorkflowInstance(Workflow workflow, String jar) {
		super();
		this.workflow = workflow;
		this.jar = jar;
	}

	public Workflow getWorkflow() {
		return workflow;
	}
	
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
	public String getJar() {
		return jar;
	}
	
	public void setJar(String jar) {
		this.jar = jar;
	}
	
	
	
}
