package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologySourceBase extends Task {
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologySource:"+workflow.getName();
	}
	
	protected MdeTechnologies mdeTechnologies;
	
	protected void setMdeTechnologies(MdeTechnologies mdeTechnologies) {
		this.mdeTechnologies = mdeTechnologies;
	}
	
	private MdeTechnologies getMdeTechnologies() {
		return mdeTechnologies;
	}
	
	public void sendToMdeTechnologies(ExtensionKeywordTuple extensionKeywordTuple) {
		getMdeTechnologies().send(extensionKeywordTuple, this.getClass().getName());
	}
	
	
	
	public abstract void produce();
	
	
}