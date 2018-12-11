package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.eclipse.scava.crossflow.runtime.permanentqueues.*;

public abstract class MdeTechnologyRepoOwnerPopularityCountCsvSinkBase extends Task  implements MdeTechnologyRepoOwnerPopularityCountEntriesConsumer{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoOwnerPopularityCountCsvSink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeMdeTechnologyRepoOwnerPopularityCountEntriesWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyRepoOwnerPopularityCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyRepoOwnerPopularityCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	
	
}