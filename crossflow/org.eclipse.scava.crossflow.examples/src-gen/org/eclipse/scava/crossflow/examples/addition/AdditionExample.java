package org.eclipse.scava.crossflow.examples.addition;

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



public class AdditionExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		AdditionExample app = new AdditionExample();
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
	protected Printer printer;
	
	public AdditionExample() {
		super();
		this.name = "AdditionExample";
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
						cache = new Cache(AdditionExample.this);
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
					
					additions = new Additions(AdditionExample.this);
					activeQueues.add(additions);
					additionResults = new AdditionResults(AdditionExample.this);
					activeQueues.add(additionResults);
					
					if (isMaster()) {
						numberPairSource = new NumberPairSource();
						numberPairSource.setWorkflow(AdditionExample.this);
						numberPairSource.setResultsBroadcaster(resultsBroadcaster);
						numberPairSource.setAdditions(additions);
						printer = new Printer();
						printer.setWorkflow(AdditionExample.this);
						printer.setResultsBroadcaster(resultsBroadcaster);
						additionResults.addConsumer(printer, "Printer");			
					}
					
					if (!isMaster() || (isMaster() && !getMode().equals(Mode.MASTER_BARE))) {
						if (!tasksToExclude.contains("Adder")) {
							adder = new Adder();
							adder.setWorkflow(AdditionExample.this);
							adder.setResultsBroadcaster(resultsBroadcaster);
							additions.addConsumer(adder, "Adder");			
							adder.setAdditionResults(additionResults);
						}
	
					}
					
					
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
	public Printer getPrinter() {
		return printer;
	}

}

