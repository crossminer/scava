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
	
	
	
	
	@Override
	public void consumeAnimalsActual(Animal animal) {

		workflow.setTaskInProgess(this);
		
		consumeAnimals(animal);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) {
		
		workflow.setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		workflow.setTaskUnblocked(this);
		
	}
	
	
}