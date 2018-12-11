package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoOwnerPopularityCounterBase extends Task  implements MdeTechnologyClonedRepoEntriesForOwnerPopularityCounterConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoOwnerPopularityCounter:"+workflow.getName();
	}
	
	protected MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries;
	
	protected void setMdeTechnologyRepoOwnerPopularityCountEntries(MdeTechnologyRepoOwnerPopularityCountEntries mdeTechnologyRepoOwnerPopularityCountEntries) {
		this.mdeTechnologyRepoOwnerPopularityCountEntries = mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	private MdeTechnologyRepoOwnerPopularityCountEntries getMdeTechnologyRepoOwnerPopularityCountEntries() {
		return mdeTechnologyRepoOwnerPopularityCountEntries;
	}
	
	public void sendToMdeTechnologyRepoOwnerPopularityCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		getMdeTechnologyRepoOwnerPopularityCountEntries().send(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeMdeTechnologyClonedRepoEntriesForOwnerPopularityCounterWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(extensionKeywordStargazersRemoteRepoUrlTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple);
	
	
	
}