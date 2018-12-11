package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoFileCountPrinterBase extends Task  implements MdeTechnologyRepoFileCountEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFileCountPrinter:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeMdeTechnologyRepoFileCountEntriesWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyRepoFileCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyRepoFileCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	
	
}