package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

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



public class BaseCase extends Workflow {
	
	public static void main(String[] args) throws Exception {
		BaseCase app = new BaseCase();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Additions additions;
	protected AdditionResults additionResults;
	
	private boolean createBroker = true;
	
	// tasks
	protected NumberPairCsvSource numberPairCsvSource;
	protected Adder adder;
	protected PrinterCsvSink printerCsvSink;
	
	public BaseCase() {
		super();
		this.name = "BaseCase";
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
						cache = new Cache(BaseCase.this);
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
					
					additions = new Additions(BaseCase.this);
					activeQueues.add(additions);
					additionResults = new AdditionResults(BaseCase.this);
					activeQueues.add(additionResults);
					
					if (isMaster()) {
					}
					
					if (!isMaster() || (isMaster() && !getMode().equals(Mode.MASTER_BARE))) {
						if (!tasksToExclude.contains("NumberPairCsvSource")) {
							numberPairCsvSource = new NumberPairCsvSource();
							numberPairCsvSource.setWorkflow(BaseCase.this);
							numberPairCsvSource.setResultsBroadcaster(resultsBroadcaster);
							numberPairCsvSource.setAdditions(additions);
						}
						if (!tasksToExclude.contains("Adder")) {
							adder = new Adder();
							adder.setWorkflow(BaseCase.this);
							adder.setResultsBroadcaster(resultsBroadcaster);
							additions.addConsumer(adder, "Adder");			
							adder.setAdditionResults(additionResults);
						}
						if (!tasksToExclude.contains("PrinterCsvSink")) {
							printerCsvSink = new PrinterCsvSink();
							printerCsvSink.setWorkflow(BaseCase.this);
							printerCsvSink.setResultsBroadcaster(resultsBroadcaster);
							additionResults.addConsumer(printerCsvSink, "PrinterCsvSink");			
						}
	
					}
					
					
					if (isMaster()){
						numberPairCsvSource.produce();
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
	
	public NumberPairCsvSource getNumberPairCsvSource() {
		return numberPairCsvSource;
	}
	public Adder getAdder() {
		return adder;
	}
	public PrinterCsvSink getPrinterCsvSink() {
		return printerCsvSink;
	}

}

