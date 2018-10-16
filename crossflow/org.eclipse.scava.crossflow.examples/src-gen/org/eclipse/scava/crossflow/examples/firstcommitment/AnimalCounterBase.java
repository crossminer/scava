package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class AnimalCounterBase implements AnimalsConsumer{
	
	protected FirstCommitmentExample workflow;
	
	public void setWorkflow(FirstCommitmentExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
}