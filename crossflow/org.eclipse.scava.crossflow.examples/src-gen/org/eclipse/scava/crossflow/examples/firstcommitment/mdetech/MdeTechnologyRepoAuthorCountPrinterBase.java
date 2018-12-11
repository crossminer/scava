package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoAuthorCountPrinterBase extends Task  implements MdeTechnologyRepoAuthorCountEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoAuthorCountPrinter:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeMdeTechnologyRepoAuthorCountEntriesWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyRepoAuthorCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyRepoAuthorCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	
	
}