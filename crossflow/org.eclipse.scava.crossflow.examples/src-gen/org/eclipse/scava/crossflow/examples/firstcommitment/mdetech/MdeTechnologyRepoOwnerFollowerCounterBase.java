package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoOwnerFollowerCounterBase implements MdeTechnologyRepoEntriesConsumer{
	
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}