package org.eclipse.scava.crossflow.examples.addition;

import java.util.LinkedList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public class AdditionExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		AdditionExample app = new AdditionExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Additions additions;
	protected AdditionResults additionResults;
	protected EclipseResultPublisher eclipseResultPublisher;
	protected EclipseTaskStatusPublisher eclipseTaskStatusPublisher;
	
	// tasks
	protected NumberPairSource numberPairSource;
	protected Adder adder;
	protected Printer printer;
	
	// excluded tasks from workers
	protected Collection<String> tasksToExclude = new LinkedList<String>();
	
	public void excludeTasks(Collection<String> tasks){
		tasksToExclude = tasks;
	}
	
	public AdditionExample() {
		this.name = "AdditionExample";
	}
	
	public void run() throws Exception {
	
		if (isMaster()) {
			cache = new Cache(this);
			BrokerService broker = new BrokerService();
			broker.setUseJmx(true);
			broker.addConnector(getBroker());
			broker.start();
		}

		eclipseResultPublisher = new EclipseResultPublisher(this);
		eclipseTaskStatusPublisher = new EclipseTaskStatusPublisher(this);
		
		additions = new Additions(this);
		additionResults = new AdditionResults(this);
		
		if(isMaster() || !tasksToExclude.contains("NumberPairSource")) {
		numberPairSource = new NumberPairSource();
		numberPairSource.setWorkflow(this);
		numberPairSource.setAdditions(additions);
		}
	
		if(isMaster() || !tasksToExclude.contains("Adder")) {
		adder = new Adder();
		adder.setWorkflow(this);
		
			additions.addConsumer(adder);
			
	
		adder.setAdditionResults(additionResults);
		}
	
		if(isMaster() || !tasksToExclude.contains("Printer")) {
		printer = new Printer();
		printer.setWorkflow(this);
		if (isMaster()) 		
			additionResults.addConsumer(printer);
			
		if(adder!=null)		
			adder.setEclipseResultPublisher(eclipseResultPublisher);
	
		}
	
		
		if (isMaster()){
			numberPairSource.produce();
		}
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