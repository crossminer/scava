package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyCsvSourceBase extends Task {
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyCsvSource:"+workflow.getName();
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