package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoOwnerPopularityCounterBase implements MdeTechnologyClonedRepoEntriesForOwnerPopularityCounterConsumer, Task{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoOwnerPopularityCounter:"+workflow.getName();
	}
	
	protected MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries;
	
	public void setMdeTechnologyRepoOwnerPopularityCountEntries(MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries) {
		this.mdeTechnologyRepoOwnerPopularityCountEntries = mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	public MdeTechnologyRepoOwnerPopularityCountEntries getMdeTechnologyRepoOwnerPopularityCountEntries() {
		return mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	public void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	public ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	
	
	@Override
	public void consumeMdeTechnologyClonedRepoEntriesForOwnerPopularityCounterActual(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(extensionKeywordStargazersRemoteRepoUrlTuple);
		
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