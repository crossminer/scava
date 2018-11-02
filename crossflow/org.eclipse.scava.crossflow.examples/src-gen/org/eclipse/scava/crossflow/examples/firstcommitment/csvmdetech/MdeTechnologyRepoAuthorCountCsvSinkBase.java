package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.utils.CsvWriter;

public abstract class MdeTechnologyRepoAuthorCountCsvSinkBase implements MdeTechnologyRepoAuthorCountEntriesConsumer{
	
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	
	
	protected EclipseResultPublisher eclipseResultPublisher;
	
	public void setEclipseResultPublisher(EclipseResultPublisher eclipseResultPublisher) {
		this.eclipseResultPublisher = eclipseResultPublisher;
	}
	
	public EclipseResultPublisher getEclipseResultPublisher() {
		return eclipseResultPublisher;
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoAuthorCountEntriesActual(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoAuthorCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		
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
	protected CsvWriter writer0 = new CsvWriter("MDE-authors.csv", "field0", "field1", "field2", "field3", "field4",  "cached");
	
	public void flushAll() {
		writer0.flush();
	}
}