package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;


public class FirstCommitmentExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		FirstCommitmentExample app = new FirstCommitmentExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Animals animals;
	
	// tasks
	protected AnimalSource animalSource;
	protected AnimalCounter animalCounter;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude;
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public FirstCommitmentExample() {
		this.name = "FirstCommitmentExample";
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			BrokerService broker = new BrokerService();
			broker.setUseJmx(true);
			broker.addConnector(getBroker());
			broker.start();
		}

		
		animals = new Animals(this);
		
		if(isMaster() || !tasksToExclude.contains("AnimalSource")) {
		animalSource = new AnimalSource();
		animalSource.setWorkflow(this);
		animalSource.setAnimals(animals);
		}
	
		if(isMaster() || !tasksToExclude.contains("AnimalCounter")) {
		animalCounter = new AnimalCounter();
		animalCounter.setWorkflow(this);
		
			animals.addConsumer(animalCounter);
			
	
		}
	
		
		if (isMaster()){
			animalSource.produce();
		}
	}
	
	public Animals getAnimals() {
		return animals;
	}
	
	public AnimalSource getAnimalSource() {
		return animalSource;
	}
	public AnimalCounter getAnimalCounter() {
		return animalCounter;
	}
	
}