package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoClonerBase implements MdeTechnologyRepoEntriesConsumer, Task{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoCloner:"+workflow.getName();
	}
	
	protected MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter;
	
	public void setMdeTechnologyClonedRepoEntriesForAuthorCounter(MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter) {
		this.mdeTechnologyClonedRepoEntriesForAuthorCounter = mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	public MdeTechnologyClonedRepoEntriesForAuthorCounter getMdeTechnologyClonedRepoEntriesForAuthorCounter() {
		return mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	protected MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter;
	
	public void setMdeTechnologyClonedRepoEntriesForFileCounter(MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter) {
		this.mdeTechnologyClonedRepoEntriesForFileCounter = mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	public MdeTechnologyClonedRepoEntriesForFileCounter getMdeTechnologyClonedRepoEntriesForFileCounter() {
		return mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	protected MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	
	public void setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter) {
		this.mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	public MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter() {
		return mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	
	
	protected ResultsBroadcaster resultsBroadcaster;
	
	public void setResultsBroadcaster(ResultsBroadcaster resultsBroadcaster) {
		this.resultsBroadcaster = resultsBroadcaster;
	}
	
	public ResultsBroadcaster getResultsBroadcaster() {
		return resultsBroadcaster;
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoEntriesActual(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoEntries(extensionKeywordStargazersTuple);
		
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