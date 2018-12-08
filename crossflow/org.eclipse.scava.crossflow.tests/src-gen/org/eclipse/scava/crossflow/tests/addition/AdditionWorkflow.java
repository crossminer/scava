package org.eclipse.scava.crossflow.tests.addition;

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



public class AdditionWorkflow extends Workflow {
	
	public static void main(String[] args) throws Exception {
		AdditionWorkflow app = new AdditionWorkflow();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Additions additions;
	protected AdditionResults additionResults;
	
	private boolean createBroker = true;
	
	// tasks
	protected NumberPairSource numberPairSource;
	protected Adder adder;
	protected AdditionResultsSink additionResultsSink;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public AdditionWorkflow() {
		super();
		this.name = "AdditionWorkflow";
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
						cache = new Cache(AdditionWorkflow.this);
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
					
					additions = new Additions(AdditionWorkflow.this);
					activeQueues.add(additions);
					additionResults = new AdditionResults(AdditionWorkflow.this);
					activeQueues.add(additionResults);
					
		
	
				
					numberPairSource = new NumberPairSource();
					numberPairSource.setWorkflow(AdditionWorkflow.this);
		
					numberPairSource.setAdditions(additions);
		
				
		
					if (!getMode().equals(Mode.MASTER_BARE) && !tasksToExclude.contains("Adder")) {
	
				
					adder = new Adder();
					adder.setWorkflow(AdditionWorkflow.this);
		
						additions.addConsumer(adder, Adder.class.getName());			
	
					adder.setAdditionResults(additionResults);
					}
					else if(isMaster()){
						additions.addConsumer(adder, Adder.class.getName());			
					}
		
				
		
	
					if (isMaster()) {
				
					additionResultsSink = new AdditionResultsSink();
					additionResultsSink.setWorkflow(AdditionWorkflow.this);
					}
		
						additionResults.addConsumer(additionResultsSink, AdditionResultsSink.class.getName());			
					if(adder!=null)		
						adder.setResultsBroadcaster(resultsBroadcaster);
	
		
				
		
					if (isMaster()){
						numberPairSource.produce();
					}
	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}).start();
	
	}				
	
	public Additions getAdditions() {
		return additions;
	}
	public AdditionResults getAdditionResults() {
		return additionResults;
	}
	
	public NumberPairSource getNumberPairSource() {
		return numberPairSource;
	}
	public Adder getAdder() {
		return adder;
	}
	public AdditionResultsSink getAdditionResultsSink() {
		return additionResultsSink;
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