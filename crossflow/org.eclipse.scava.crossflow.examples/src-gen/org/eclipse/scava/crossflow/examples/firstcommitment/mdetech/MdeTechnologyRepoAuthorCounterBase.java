package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoAuthorCounterBase extends Task  implements MdeTechnologyClonedRepoEntriesForAuthorCounterConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoAuthorCounter:"+workflow.getName();
	}
	
	protected MdeTechnologyRepoAuthorCountEntries mdeTechnologyRepoAuthorCountEntries;
	
	protected void setMdeTechnologyRepoAuthorCountEntries(MdeTechnologyRepoAuthorCountEntries mdeTechnologyRepoAuthorCountEntries) {
		this.mdeTechnologyRepoAuthorCountEntries = mdeTechnologyRepoAuthorCountEntries;
	}
	
	private MdeTechnologyRepoAuthorCountEntries getMdeTechnologyRepoAuthorCountEntries() {
		return mdeTechnologyRepoAuthorCountEntries;
	}
	
	public void sendToMdeTechnologyRepoAuthorCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		getMdeTechnologyRepoAuthorCountEntries().send(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple, this.getClass().getName());
	}
	
	
	@Override
	public final void consumeMdeTechnologyClonedRepoEntriesForAuthorCounterWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyClonedRepoEntriesForAuthorCounter(extensionKeywordStargazersRemoteRepoUrlTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyClonedRepoEntriesForAuthorCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple);
	
	
	
}