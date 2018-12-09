package org.eclipse.scava.crossflow.examples.firstcommitment.mdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoClonerBase extends Task  implements MdeTechnologyRepoEntriesConsumer{
		
	protected MdeTechnologyExample workflow;
	
	public void setWorkflow(MdeTechnologyExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoCloner:"+workflow.getName();
	}
	
	protected MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForAuthorCounter(MdeTechnologyClonedRepoEntriesForAuthorCounter mdeTechnologyClonedRepoEntriesForAuthorCounter) {
		this.mdeTechnologyClonedRepoEntriesForAuthorCounter = mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForAuthorCounter getMdeTechnologyClonedRepoEntriesForAuthorCounter() {
		return mdeTechnologyClonedRepoEntriesForAuthorCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForAuthorCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForAuthorCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	protected MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForFileCounter(MdeTechnologyClonedRepoEntriesForFileCounter mdeTechnologyClonedRepoEntriesForFileCounter) {
		this.mdeTechnologyClonedRepoEntriesForFileCounter = mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForFileCounter getMdeTechnologyClonedRepoEntriesForFileCounter() {
		return mdeTechnologyClonedRepoEntriesForFileCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForFileCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForFileCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	protected MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	
	protected void setMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter) {
		this.mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter = mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	private MdeTechnologyClonedRepoEntriesForOwnerPopularityCounter getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter() {
		return mdeTechnologyClonedRepoEntriesForOwnerPopularityCounter;
	}
	
	public void sendToMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter(ExtensionKeywordStargazersRemoteRepoUrlTuple extensionKeywordStargazersRemoteRepoUrlTuple) {
		getMdeTechnologyClonedRepoEntriesForOwnerPopularityCounter().send(extensionKeywordStargazersRemoteRepoUrlTuple, this.getClass().getName());
	}
	
	
	
	@Override
	public void consumeMdeTechnologyRepoEntriesActual(ExtensionKeywordStargazersTuple extensionKeywordStargazersTuple) {

		workflow.setTaskInProgess(this);
		
		consumeMdeTechnologyRepoEntries(extensionKeywordStargazersTuple);
		
		workflow.setTaskWaiting(this);
		
	}
	
	
	
	
}