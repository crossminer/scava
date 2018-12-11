package org.eclipse.scava.crossflow.examples.firstcommitment.csvmdetech;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;

public abstract class MdeTechnologyRepoFileCountCsvSinkBase extends Task  implements MdeTechnologyRepoFileCountEntriesConsumer{
		
	protected MdeTechnologyCsvExample workflow;
	
	public void setWorkflow(MdeTechnologyCsvExample workflow) {
		this.workflow = workflow;
	}
	
	public Workflow getWorkflow() {
		return workflow;
	}
	
	public String getId(){
		return "MdeTechnologyRepoFileCountCsvSink:"+workflow.getName();
	}
	
	
	@Override
	public final void consumeMdeTechnologyRepoFileCountEntriesWithNotifications(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple) {
		workflow.setTaskInProgess(this);
		consumeMdeTechnologyRepoFileCountEntries(extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
		workflow.setTaskWaiting(this);
	}
	
	public abstract void consumeMdeTechnologyRepoFileCountEntries(ExtensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple extensionKeywordStargazersRemoteRepoUrlLocalRepoPathTuple);
	
	
	
}