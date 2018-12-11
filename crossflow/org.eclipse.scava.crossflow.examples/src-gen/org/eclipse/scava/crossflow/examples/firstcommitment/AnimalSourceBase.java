package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class AnimalSourceBase extends Task {
		
	protected FirstCommitmentExample workflow;
	
	public void setWorkflow(FirstCommitmentExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "AnimalSource:"+workflow.getName();
	}
	
	protected Animals animals;
	
	protected void setAnimals(Animals animals) {
		this.animals = animals;
	}
	
	private Animals getAnimals() {
		return animals;
	}
	
	public void sendToAnimals(Animal animal) {
		getAnimals().send(animal, this.getClass().getName());
	}
	
	
	
	
	public abstract void produce();
	
}