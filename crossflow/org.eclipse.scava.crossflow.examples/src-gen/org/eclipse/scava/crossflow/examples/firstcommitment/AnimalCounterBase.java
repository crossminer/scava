package org.eclipse.scava.crossflow.examples.firstcommitment;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class AnimalCounterBase extends Task  implements AnimalsConsumer{
		
	protected FirstCommitmentExample workflow;
	
	public void setWorkflow(FirstCommitmentExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "AnimalCounter:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeAnimalsWithNotifications(Animal animal) {
		workflow.setTaskInProgess(this);
		consumeAnimals(animal);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeAnimals(Animal animal);
	
	
	
}