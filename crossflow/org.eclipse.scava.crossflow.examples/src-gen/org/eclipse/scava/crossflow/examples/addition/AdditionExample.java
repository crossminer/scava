package org.eclipse.scava.crossflow.examples.addition;

import com.beust.jcommander.JCommander;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.Cache;
import com.beust.jcommander.Parameter;

public class AdditionExample extends Workflow {
	
	public static void main(String[] args) throws Exception {
		AdditionExample app = new AdditionExample();
		new JCommander(app, args);
		app.run();
	}
	
	
	// streams
	protected Additions additions;
	protected AdditionResults additionResults;
	
	// tasks
	protected NumberPairSource numberPairSource;
	protected Adder adder;
	protected Printer printer;
	
	
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
		
		additions = new Additions(this);
		additionResults = new AdditionResults(this);
		
		numberPairSource = new NumberPairSource();
		numberPairSource.setWorkflow(this);
		numberPairSource.setAdditions(additions);
		
		adder = new Adder();
		adder.setWorkflow(this);
		additions.addConsumer(adder);
		adder.setAdditionResults(additionResults);
		
		printer = new Printer();
		printer.setWorkflow(this);
		if (isMaster()) 		additionResults.addConsumer(printer);
		
		
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
	
}