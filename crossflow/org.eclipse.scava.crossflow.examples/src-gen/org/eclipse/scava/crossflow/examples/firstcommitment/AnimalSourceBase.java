package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class AnimalSourceBase {
	
	protected FirstCommitmentExample workflow;
	
	public void setWorkflow(FirstCommitmentExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	protected Animals animals;
	
	public void setAnimals(Animals animals) {
		this.animals = animals;
	}
	
	public Animals getAnimals() {
		return animals;
	}
	
	
	public abstract void produce();
	
}