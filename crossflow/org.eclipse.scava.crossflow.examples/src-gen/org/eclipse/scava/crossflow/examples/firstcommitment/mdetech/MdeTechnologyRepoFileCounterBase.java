package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoFileCounterBase extends Task  implements MdeTechnologyClonedRepoEntriesForFileCounterConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
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
	
	
	@Override
	public final void consumeMdeTechnologyClonedRepoEntriesForFileCounterWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyClonedRepoEntriesForFileCounter(extensionKeywordStargazersRemoteRepoUrlTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyClonedRepoEntriesForFileCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple);
	
	
	
}