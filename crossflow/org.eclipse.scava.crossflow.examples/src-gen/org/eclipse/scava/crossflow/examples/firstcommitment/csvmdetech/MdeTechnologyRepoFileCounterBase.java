package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoFileCounterBase implements MdeTechnologyClonedRepoEntriesForFileCounterConsumer, Task{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFileCounter:"+workflow.getName();
	}
	
	protected MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries;
	
	protected void setMdeTechnologyRepoFileCountEntries(MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries) {
		this.mdeTechnologyRepoFileCountEntries = mdeTechnologyRepoFileCountEntries;
	}
	
	private MdeTechnologyRepoFileCountEntries getMdeTechnologyRepoFileCountEntries() {
		return mdeTechnologyRepoFileCountEntries;
	}
	
	public void sendToMdeTechnologyRepoFileCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		getMdeTechnologyRepoFileCountEntries().send(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple, this.getClass().getName());
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
	public void consumeMdeTechnologyClonedRepoEntriesForFileCounterActual(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyClonedRepoEntriesForFileCounter(extensionKeywordStargazersRemoteRepoUrlTuple);
		
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
	
	
	
}