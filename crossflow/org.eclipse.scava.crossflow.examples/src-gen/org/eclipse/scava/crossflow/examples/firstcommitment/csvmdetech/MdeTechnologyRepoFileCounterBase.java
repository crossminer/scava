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
	
	public void setMdeTechnologyRepoFileCountEntries(MdeTechnologyRepoFileCountEntries mdeTechnologyRepoFileCountEntries) {
		this.mdeTechnologyRepoFileCountEntries = mdeTechnologyRepoFileCountEntries;
	}
	
	public MdeTechnologyRepoFileCountEntries getMdeTechnologyRepoFileCountEntries() {
		return mdeTechnologyRepoFileCountEntries;
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	public void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	public ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
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