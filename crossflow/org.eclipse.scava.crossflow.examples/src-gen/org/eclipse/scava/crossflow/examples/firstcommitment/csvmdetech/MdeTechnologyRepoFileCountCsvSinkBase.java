package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoFileCountCsvSinkBase implements MdeTechnologyRepoFileCountEntriesConsumer, Task{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFileCountCsvSink:"+workflow.getName();
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
	public void consumeMdeTechnologyRepoFileCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoFileCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		
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
	protected CsvWriter writer0 = new CsvWriter("csvs/MDE-files.csv", "field0", "field1", "field2", "field3", "field4",  "cached");
	
	public void flushAll() {
		writer0.flush();
	}
}