package org.eclipse.scava.crossflow.tests.csvsourcesinkcase;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class PrinterCsvSinkBase implements AdditionResultsConsumer, Task{
		
	protected BaseCase workflow;
	
	public void setWorkflow(BaseCase workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "PrinterCsvSink:"+workflow.getName();
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	protected void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	private ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	public void sendToResultsBroadcaster(Object[] row){
		getResultsBroadcaster().send(row);
	}
	
	
	
	@Override
	public void consumeAdditionResultsActual(Number number) {

		workflow.setTaskInProgess(this);
		
		consumeAdditionResults(number);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	/**
	 * Call this within consumeXYZ() to denote task blocked due to some reason
	 * @param reason
	 */
	protected void taskBlocked(String reason) {
		
		workflow.setTaskBlocked(this,reason);
		
	}
	
	/**
	 * Call this within consumeXYZ() to denote task is now unblocked
	 * @param reason
	 */
	protected void taskUnblocked() {
		
		workflow.setTaskUnblocked(this);
		
	}
	
	
	
	// CSV file writer(s)
	protected CsvWriter writer0 = new CsvWriter("csvs/AdditionResultsCsvSink.csv", "n",  "cached");
	
	public void flushAll() {
		writer0.flush();
	}
}