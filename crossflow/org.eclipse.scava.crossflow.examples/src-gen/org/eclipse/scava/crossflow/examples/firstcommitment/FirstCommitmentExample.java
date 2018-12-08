package org.eclipse.scava.crossflow.examples.firstcommitment;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.Mode;
import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;



public class FirstCommitmentExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		FirstCommitmentExample app = new FirstCommitmentExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Animals animals;
	
	private boolean createBroker = true;
	
	// tasks
	protected AnimalSource animalSource;
	protected AnimalCounter animalCounter;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public FirstCommitmentExample() {
		super();
		this.name = "FirstCommitmentExample";
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
					if(isCacheEnabled())
						cache = new Cache(FirstCommitmentExample.this);
						if (createBroker) {
							brokerService = new BrokerService();
							brokerService.setUseJmx(true);
							brokerService.addConnector(getBroker());
							brokerService.start();
						}
					}

					connect();

					Thread.sleep(delay);
					
//TODO test of task status until it is integrated to ui
//		taskStatusPublisher.addConsumer(new TaskStatusPublisherConsumer() {
//			@Override
//			public void consumeTaskStatusPublisher(TaskStatus status) {
//				System.err.println(status.getCaller()+" : "+status.getStatus()+" : "+status.getReason());
//			}
//		});
//
					
					animals = new Animals(FirstCommitmentExample.this);
					activeQueues.add(animals);
					
		
	
				
					animalSource = new AnimalSource();
					animalSource.setWorkflow(FirstCommitmentExample.this);
		
					animalSource.setAnimals(animals);
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("AnimalCounter")) {
	
				
					animalCounter = new AnimalCounter();
					animalCounter.setWorkflow(FirstCommitmentExample.this);
		
						animals.addConsumer(animalCounter, AnimalCounter.class.getName());			
	
					}
					else if(isMaster()){
						animals.addConsumer(animalCounter, AnimalCounter.class.getName());			
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
	
	public void setTaskInProgess(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

	public void setTaskWaiting(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.WAITING, caller.getId(), ""));
	}

	public void setTaskBlocked(Task caller, String reason) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.BLOCKED, caller.getId(), reason));
	}

	public void setTaskUnblocked(Task caller) {
		taskStatusPublisher.send(new TaskStatus(TaskStatuses.INPROGRESS, caller.getId(), ""));
	}

}