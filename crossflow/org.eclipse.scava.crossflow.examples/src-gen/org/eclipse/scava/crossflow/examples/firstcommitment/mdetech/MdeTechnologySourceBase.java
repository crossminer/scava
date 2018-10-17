package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologySourceBase {
	
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected MdeTechnologies mdeTechnologies;
	
	public void setMdeTechnologies(MdeTechnologies mdeTechnologies) {
		this.mdeTechnologies = mdeTechnologies;
	}
	
	public MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	
	
	
	public abstract void produce();
	
}