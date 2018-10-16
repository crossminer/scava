package org.eclipse.scava.crossflow.examples.firstcommitment;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

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
		
		animalSource = new AnimalSource();
		animalSource.setWorkflow(this);
		animalSource.setAnimals(animals);
		
		animalCounter = new AnimalCounter();
		animalCounter.setWorkflow(this);
		animals.addConsumer(animalCounter);
		
		
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