package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.DirectoryCache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Moded;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;



public class FirstCommitmentExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		Moded moded = new Moded();
		new JCommander(moded, args);
		FirstCommitmentExample app = new FirstCommitmentExample(moded.getMode());
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Animals animals;
	
	private boolean createBroker = true;
	
	// tasks
	protected AnimalSource animalSource;
	protected AnimalCounter animalCounter;
	
	public FirstCommitmentExample() {
		this(Mode.MASTER);
	}
	
	public FirstCommitmentExample(Mode mode) {
		super();
		this.name = "FirstCommitmentExample";
		this.mode = mode;
		if (isMaster()) {
		animalSource = new AnimalSource();
		animalSource.setWorkflow(this);
		}
		
		if (isWorker()) {
			if (!tasksToExclude.contains("AnimalCounter")) {
				animalCounter = new AnimalCounter();
				animalCounter.setWorkflow(this);
			}
		}
	}
	
	public void createBroker(boolean createBroker) {
		this.createBroker = createBroker;
	}
	
	/**
	 * Run with initial delay in ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param delay
	 */
	@Override
	public void run(int delay) throws Exception {
	
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					
					if (isMaster()) {
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
					animals = new Animals(FirstCommitmentExample.this);
					activeQueues.add(animals);
					
					if (isMaster()) {
							animalSource.setResultsBroadcaster(resultsBroadcaster);
							animalSource.setAnimals(animals);
					}
					
					if (isWorker()) {
						if (!tasksToExclude.contains("AnimalCounter")) {
								animalCounter.setResultsBroadcaster(resultsBroadcaster);
								animals.addConsumer(animalCounter, "AnimalCounter");			
						}
					}
					
					
					if (isMaster()){
						animalSource.produce();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
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

