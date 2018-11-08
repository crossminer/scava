package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class FirstCommitmentExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		FirstCommitmentExample app = new FirstCommitmentExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Animals animals;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	// tasks
	protected AnimalSource animalSource;
	protected AnimalCounter animalCounter;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
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

		eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(this);
		
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
	
	public void setTaskInProgess(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

	public void setTaskWaiting(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getClass().getName(), ""));
	}

	public void setTaskBlocked(Object caller, String reason) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getClass().getName(), reason));
	}

	public void setTaskUnblocked(Object caller) {
		eclipseTaskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getClass().getName(), ""));
	}

}