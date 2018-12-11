package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoOwnerPopularityCountPrinterBase extends Task  implements MdeTechnologyRepoOwnerPopularityCountEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoOwnerPopularityCountPrinter:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeMdeTechnologyRepoOwnerPopularityCountEntriesWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyRepoOwnerPopularityCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyRepoOwnerPopularityCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	
	
}